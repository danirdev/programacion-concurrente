package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que simula el funcionamiento de una panadería usando interfaz Runnable.
 * Coordina la producción de bizcochos y facturas, y la llegada de clientes.
 * Versión mejorada del TP4 demostrando las ventajas de Runnable vs Thread.
 */
public class PanaderiaRunnableSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("        SIMULACIÓN DE PANADERÍA CON INTERFAZ RUNNABLE");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando panadería...");
        System.out.println();
        System.out.println("CONFIGURACIÓN:");
        System.out.println("• Implementación: Interfaz Runnable (vs Thread del TP4)");
        System.out.println("• Horno Bizcochos: 400-600ms por unidad");
        System.out.println("• Horno Facturas: 1000-1300ms por unidad");
        System.out.println("• Llegada Clientes: 800-1500ms entre llegadas");
        System.out.println("• Compra Cliente: 200-400ms (1 Bizcocho + 1 Factura)");
        System.out.println("• Duración: 45 segundos de simulación");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Crear el mostrador compartido
        MostradorRunnable mostrador = new MostradorRunnable();
        
        // Crear las tareas Runnable
        ProductorBizcochos hornoBizcochos = new ProductorBizcochos(mostrador, 1);
        ProductorFacturas hornoFacturas = new ProductorFacturas(mostrador, 1);
        GeneradorClientes generadorClientes = new GeneradorClientes(mostrador);
        
        // Listas para gestionar hilos
        List<Thread> hilosPrincipales = new ArrayList<>();
        List<Runnable> tareasRunnable = new ArrayList<>();
        
        // Agregar tareas a la lista para demostrar flexibilidad
        tareasRunnable.add(hornoBizcochos);
        tareasRunnable.add(hornoFacturas);
        tareasRunnable.add(generadorClientes);
        
        // Crear hilos usando las tareas Runnable
        Thread hiloHornoBizcochos = new Thread(hornoBizcochos, "Hilo-Horno-Bizcochos");
        Thread hiloHornoFacturas = new Thread(hornoFacturas, "Hilo-Horno-Facturas");
        Thread hiloGeneradorClientes = new Thread(generadorClientes, "Hilo-Generador-Clientes");
        
        hilosPrincipales.add(hiloHornoBizcochos);
        hilosPrincipales.add(hiloHornoFacturas);
        hilosPrincipales.add(hiloGeneradorClientes);
        
        // Crear hilo para mostrar estadísticas periódicas
        Runnable monitorEstadisticas = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(15000); // Cada 15 segundos
                    mostrador.mostrarEstado();
                    System.out.println("Hornos activos: " + hornoBizcochos.getInfo() + " | " + hornoFacturas.getInfo());
                    System.out.println("Generador: " + generadorClientes.getInfo());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Thread hiloMonitor = new Thread(monitorEstadisticas, "Monitor-Estadisticas-Runnable");
        
        try {
            // Iniciar todos los hilos
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] === PANADERÍA EN FUNCIONAMIENTO (RUNNABLE) ===");
            
            // Demostrar flexibilidad de Runnable: iniciar hilos de diferentes maneras
            System.out.println("🚀 Iniciando hilos usando diferentes métodos Runnable:");
            
            // Método 1: Thread tradicional con Runnable
            hiloHornoBizcochos.start();
            System.out.println("   ✅ " + hiloHornoBizcochos.getName() + " - Thread(Runnable)");
            
            // Método 2: Usando método helper de la clase
            Thread hiloFacturas = hornoFacturas.iniciarEnHilo();
            System.out.println("   ✅ " + hiloFacturas.getName() + " - Método helper");
            
            // Método 3: Thread directo con Runnable
            hiloGeneradorClientes.start();
            System.out.println("   ✅ " + hiloGeneradorClientes.getName() + " - Thread directo");
            
            hiloMonitor.start();
            System.out.println("   ✅ " + hiloMonitor.getName() + " - Lambda Runnable");
            
            // Ejecutar simulación por 45 segundos
            Thread.sleep(45000); // 45 segundos
            
            // Detener todos los hilos usando métodos de control de Runnable
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println();
            System.out.println("[" + tiempo + "] === CERRANDO PANADERÍA (RUNNABLE) ===");
            
            // Demostrar control granular con Runnable
            System.out.println("🛑 Deteniendo tareas usando métodos de control Runnable:");
            
            hornoBizcochos.detener();
            System.out.println("   ✅ Horno Bizcochos detenido (método detener())");
            
            hornoFacturas.detener();
            System.out.println("   ✅ Horno Facturas detenido (método detener())");
            
            generadorClientes.detener();
            System.out.println("   ✅ Generador Clientes detenido (método detener())");
            
            hiloMonitor.interrupt();
            System.out.println("   ✅ Monitor detenido (interrupt())");
            
            // Esperar a que terminen los hilos principales
            for (Thread hilo : hilosPrincipales) {
                hilo.join(2000);
            }
            hiloFacturas.join(2000);
            hiloMonitor.join(1000);
            
            // Esperar a que terminen los clientes
            generadorClientes.esperarClientes();
            
            // Mostrar estadísticas finales
            mostrarEstadisticasFinales(mostrador, hornoBizcochos, hornoFacturas, generadorClientes);
            
            // Demostrar ventajas específicas de Runnable
            demostrarVentajasRunnable(tareasRunnable, generadorClientes);
            
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(MostradorRunnable mostrador, 
                                                  ProductorBizcochos hornoBizcochos,
                                                  ProductorFacturas hornoFacturas,
                                                  GeneradorClientes generadorClientes) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("              ESTADÍSTICAS FINALES (RUNNABLE)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas de producción
        System.out.println("📊 PRODUCCIÓN (Runnable):");
        System.out.println("   Bizcochos producidos: " + hornoBizcochos.getBizcochosProcidos());
        System.out.println("   Facturas producidas:  " + hornoFacturas.getFacturasProducidas());
        System.out.println("   Ratio B/F:            " + 
                          String.format("%.2f", (double)hornoBizcochos.getBizcochosProcidos() / 
                                               hornoFacturas.getFacturasProducidas()));
        System.out.println("   Estado hornos:        " + hornoBizcochos.getInfo() + " | " + hornoFacturas.getInfo());
        System.out.println();
        
        // Estadísticas de inventario
        System.out.println("📦 INVENTARIO FINAL:");
        System.out.println("   " + mostrador.getInfoResumida());
        System.out.println();
        
        // Estadísticas de clientes
        int[] estadisticasClientes = generadorClientes.getEstadisticasClientes();
        int clientesAtendidos = estadisticasClientes[0];
        int clientesNoAtendidos = estadisticasClientes[1];
        int clientesActivos = estadisticasClientes[2];
        int totalClientes = generadorClientes.getClientesGenerados();
        
        System.out.println("👥 CLIENTES (Runnable):");
        System.out.println("   Clientes generados:    " + totalClientes);
        System.out.println("   Clientes atendidos:    " + clientesAtendidos);
        System.out.println("   Clientes no atendidos: " + clientesNoAtendidos);
        System.out.println("   Clientes activos:      " + clientesActivos);
        System.out.println("   Tasa de atención:      " + 
                          String.format("%.1f%%", (double)clientesAtendidos / totalClientes * 100));
        System.out.println("   Hilos de clientes:     " + generadorClientes.getHilosClientes().size());
        System.out.println();
        
        // Análisis de rendimiento
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double bizcochosPorMinuto = hornoBizcochos.getBizcochosProcidos() * (60.0 / 45.0); // Extrapolado a 1 minuto
        double facturasPorMinuto = hornoFacturas.getFacturasProducidas() * (60.0 / 45.0);
        double clientesPorMinuto = totalClientes * (60.0 / 45.0);
        
        System.out.println("   Bizcochos/minuto:      " + String.format("%.1f", bizcochosPorMinuto));
        System.out.println("   Facturas/minuto:       " + String.format("%.1f", facturasPorMinuto));
        System.out.println("   Clientes/minuto:       " + String.format("%.1f", clientesPorMinuto));
        System.out.println("   Capacidad teórica:     " + String.format("%.1f ventas/min", 
                                                                        Math.min(bizcochosPorMinuto, facturasPorMinuto)));
    }
    
    /**
     * Demuestra las ventajas específicas de usar Runnable vs Thread
     */
    private static void demostrarVentajasRunnable(List<Runnable> tareas, 
                                                GeneradorClientes generadorClientes) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("           VENTAJAS DE RUNNABLE DEMOSTRADAS EN PANADERÍA");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🎯 1. FLEXIBILIDAD DE CREACIÓN DE HILOS:");
        System.out.println("   ✅ Múltiples formas de crear hilos con la misma tarea");
        System.out.println("   ✅ Thread(Runnable), métodos helper, lambdas");
        System.out.println("   ✅ Reutilización de tareas en diferentes contextos");
        
        System.out.println("\n🔄 2. GESTIÓN MEJORADA DE TAREAS:");
        System.out.println("   ✅ Lista unificada de tareas Runnable: " + tareas.size() + " tareas principales");
        System.out.println("   ✅ Control granular con métodos detener() personalizados");
        System.out.println("   ✅ Estado independiente del hilo (activo/inactivo)");
        
        System.out.println("\n🏗️  3. SEPARACIÓN DE RESPONSABILIDADES:");
        System.out.println("   ✅ Lógica de negocio separada de la gestión de hilos");
        System.out.println("   ✅ Clases más enfocadas en su propósito específico");
        System.out.println("   ✅ Mejor encapsulación y mantenibilidad");
        
        System.out.println("\n🎛️  4. CONTROL AVANZADO:");
        System.out.println("   ✅ Métodos de información: getInfo() en cada tarea");
        System.out.println("   ✅ Estados personalizados más allá de Thread.State");
        System.out.println("   ✅ Gestión de hilos hijos (clientes generados)");
        
        // Demostrar gestión de hilos hijos
        List<Thread> hilosClientes = generadorClientes.getHilosClientes();
        long hilosVivos = hilosClientes.stream().filter(Thread::isAlive).count();
        
        System.out.println("\n👥 5. GESTIÓN DE HILOS DINÁMICOS:");
        System.out.println("   ✅ Generación dinámica de " + hilosClientes.size() + " hilos de clientes");
        System.out.println("   ✅ Hilos aún vivos: " + hilosVivos);
        System.out.println("   ✅ Control centralizado de hilos hijos");
        
        System.out.println("\n🧪 6. TESTABILIDAD MEJORADA:");
        System.out.println("   ✅ Métodos run() ejecutables directamente");
        System.out.println("   ✅ Lógica testeable sin crear hilos reales");
        System.out.println("   ✅ Mocking más sencillo de dependencias");
        
        System.out.println("\n⚡ 7. ESCALABILIDAD:");
        System.out.println("   ✅ Compatible con ExecutorService para pools");
        System.out.println("   ✅ Mejor gestión de recursos del sistema");
        System.out.println("   ✅ Preparado para frameworks de concurrencia modernos");
        
        System.out.println("\n📊 8. COMPARACIÓN CON TP4 (Thread):");
        System.out.println("   Flexibilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Reutilización:    Thread ⭐⭐   vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Testabilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Mantenibilidad:   Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Simplicidad:      Thread ⭐⭐⭐⭐⭐ vs Runnable ⭐⭐⭐⭐");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 RESULTADO: Runnable ofrece ventajas significativas en proyectos");
        System.out.println("   complejos, mientras que Thread es más simple para casos básicos.");
        System.out.println("=".repeat(80));
    }
}
