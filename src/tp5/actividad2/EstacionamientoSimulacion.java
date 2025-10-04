package tp5.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase principal que simula el funcionamiento de un estacionamiento usando interfaz Runnable.
 * Coordina la generación de 100 automóviles y su flujo a través de un estacionamiento
 * con capacidad para 20 vehículos, 2 entradas y 2 salidas.
 */
public class EstacionamientoSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("        SIMULACIÓN DE ESTACIONAMIENTO CON INTERFAZ RUNNABLE");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación de estacionamiento...");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: Interfaz Runnable");
        System.out.println("• Capacidad máxima: 20 automóviles simultáneos");
        System.out.println("• Puntos de acceso: 2 entradas + 2 salidas");
        System.out.println("• Total de automóviles: 100");
        System.out.println("• Estado inicial: Estacionamiento vacío");
        System.out.println("• Llegadas: 300-1000ms entre automóviles");
        System.out.println("• Permanencia: 2000-8000ms por automóvil");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Crear el estacionamiento compartido
        Estacionamiento estacionamiento = new Estacionamiento();
        System.out.println("✅ Estacionamiento creado: " + estacionamiento.getInfoResumida());
        
        // Crear el generador de automóviles
        GeneradorAutomoviles generadorAutomoviles = new GeneradorAutomoviles(estacionamiento);
        
        // Crear hilo para mostrar estadísticas periódicas
        Runnable monitorEstadisticas = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(10000); // Cada 10 segundos
                    estacionamiento.mostrarEstado();
                    System.out.println("Generador: " + generadorAutomoviles.getInfo());
                    
                    // Mostrar estadísticas de automóviles cada 20 segundos
                    if (System.currentTimeMillis() % 20000 < 10000) {
                        mostrarEstadisticasIntermedia(generadorAutomoviles);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Thread hiloMonitor = new Thread(monitorEstadisticas, "Monitor-Estadisticas-Estacionamiento");
        
        try {
            // Iniciar la simulación
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] === ESTACIONAMIENTO ABIERTO ===");
            System.out.println("🚀 Iniciando generación de automóviles...");
            
            // Iniciar el generador de automóviles
            Thread hiloGenerador = generadorAutomoviles.iniciarEnHilo();
            System.out.println("   ✅ " + hiloGenerador.getName() + " iniciado");
            
            // Iniciar monitor de estadísticas
            hiloMonitor.start();
            System.out.println("   ✅ " + hiloMonitor.getName() + " iniciado");
            
            System.out.println();
            System.out.println("=".repeat(50));
            System.out.println("     SIMULACIÓN EN PROGRESO");
            System.out.println("=".repeat(50));
            
            // Esperar a que se generen todos los automóviles
            hiloGenerador.join();
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println();
            System.out.println("[" + tiempo + "] 🏁 GENERACIÓN COMPLETADA");
            System.out.println("Esperando que todos los automóviles completen sus ciclos...");
            
            // Dar tiempo adicional para que los automóviles terminen
            Thread.sleep(15000); // 15 segundos adicionales
            
            // Detener el monitor
            hiloMonitor.interrupt();
            hiloMonitor.join(2000);
            
            // Esperar a que terminen todos los automóviles
            generadorAutomoviles.esperarAutomoviles();
            
            // Mostrar estadísticas finales
            mostrarEstadisticasFinales(estacionamiento, generadorAutomoviles);
            
            // Demostrar ventajas específicas de Runnable
            demostrarVentajasRunnable(generadorAutomoviles);
            
        } catch (InterruptedException e) {
            System.out.println("❌ Simulación interrumpida");
            Thread.currentThread().interrupt();
        } finally {
            // Asegurar limpieza
            generadorAutomoviles.detener();
            if (hiloMonitor.isAlive()) {
                hiloMonitor.interrupt();
            }
        }
    }
    
    /**
     * Muestra estadísticas intermedias durante la simulación
     */
    private static void mostrarEstadisticasIntermedia(GeneradorAutomoviles generador) {
        int[] stats = generador.getEstadisticasAutomoviles();
        int[] accesos = generador.getEstadisticasAccesos();
        
        System.out.println("📊 ESTADÍSTICAS INTERMEDIAS:");
        System.out.println("   Automóviles completados: " + stats[0]);
        System.out.println("   En proceso: " + stats[1]);
        System.out.println("   Uso de entradas: E1=" + accesos[0] + ", E2=" + accesos[1]);
        System.out.println("   Uso de salidas: S1=" + accesos[2] + ", S2=" + accesos[3]);
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(Estacionamiento estacionamiento, 
                                                  GeneradorAutomoviles generadorAutomoviles) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("           ESTADÍSTICAS FINALES DEL ESTACIONAMIENTO");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del estacionamiento
        double[] statsEstacionamiento = estacionamiento.getEstadisticas();
        System.out.println("🅿️ ESTADÍSTICAS DEL ESTACIONAMIENTO:");
        System.out.println("   Capacidad máxima: " + estacionamiento.getCapacidadMaxima());
        System.out.println("   Ocupación final: " + (int)statsEstacionamiento[0] + "/" + 
                          estacionamiento.getCapacidadMaxima());
        System.out.println("   Total entradas: " + (int)statsEstacionamiento[1]);
        System.out.println("   Total salidas: " + (int)statsEstacionamiento[2]);
        System.out.println("   Automóviles esperando: " + (int)statsEstacionamiento[3]);
        System.out.println("   Ocupación máxima alcanzada: " + String.format("%.1f%%", statsEstacionamiento[4]));
        System.out.println("   Balance (E-S): " + ((int)statsEstacionamiento[1] - (int)statsEstacionamiento[2]));
        System.out.println();
        
        // Estadísticas de automóviles
        int[] estadisticasAutos = generadorAutomoviles.getEstadisticasAutomoviles();
        int totalGenerados = generadorAutomoviles.getAutomovilesGenerados();
        int completaron = estadisticasAutos[0];
        int enProceso = estadisticasAutos[1];
        int noIniciados = estadisticasAutos[2];
        int detenidos = estadisticasAutos[3];
        
        System.out.println("🚗 ESTADÍSTICAS DE AUTOMÓVILES:");
        System.out.println("   Objetivo: " + generadorAutomoviles.getTotalAutomoviles());
        System.out.println("   Generados: " + totalGenerados);
        System.out.println("   Completaron ciclo: " + completaron);
        System.out.println("   En proceso: " + enProceso);
        System.out.println("   No iniciados: " + noIniciados);
        System.out.println("   Detenidos: " + detenidos);
        System.out.println("   Tasa de finalización: " + 
                          String.format("%.1f%%", (double)completaron / totalGenerados * 100));
        System.out.println();
        
        // Estadísticas de uso de accesos
        int[] estadisticasAccesos = generadorAutomoviles.getEstadisticasAccesos();
        System.out.println("🚪 ESTADÍSTICAS DE ACCESOS:");
        System.out.println("   Entrada 1: " + estadisticasAccesos[0] + " automóviles (" + 
                          String.format("%.1f%%", (double)estadisticasAccesos[0] / totalGenerados * 100) + ")");
        System.out.println("   Entrada 2: " + estadisticasAccesos[1] + " automóviles (" + 
                          String.format("%.1f%%", (double)estadisticasAccesos[1] / totalGenerados * 100) + ")");
        System.out.println("   Salida 1: " + estadisticasAccesos[2] + " automóviles (" + 
                          String.format("%.1f%%", (double)estadisticasAccesos[2] / totalGenerados * 100) + ")");
        System.out.println("   Salida 2: " + estadisticasAccesos[3] + " automóviles (" + 
                          String.format("%.1f%%", (double)estadisticasAccesos[3] / totalGenerados * 100) + ")");
        
        // Verificar balanceamiento
        double desbalanceEntradas = Math.abs(estadisticasAccesos[0] - estadisticasAccesos[1]);
        double desbalanceSalidas = Math.abs(estadisticasAccesos[2] - estadisticasAccesos[3]);
        System.out.println("   Balanceamiento entradas: " + 
                          (desbalanceEntradas <= totalGenerados * 0.1 ? "✅ Bueno" : "⚠️ Desbalanceado"));
        System.out.println("   Balanceamiento salidas: " + 
                          (desbalanceSalidas <= totalGenerados * 0.1 ? "✅ Bueno" : "⚠️ Desbalanceado"));
        System.out.println();
        
        // Estadísticas de tiempos
        double[] estadisticasTiempos = generadorAutomoviles.getEstadisticasTiempos();
        if (estadisticasTiempos[0] > 0) {
            System.out.println("⏱️ ESTADÍSTICAS DE TIEMPOS:");
            System.out.println("   Tiempo promedio total: " + String.format("%.0f", estadisticasTiempos[0]) + "ms");
            System.out.println("   Tiempo promedio espera: " + String.format("%.0f", estadisticasTiempos[1]) + "ms");
            System.out.println("   Tiempo promedio permanencia: " + String.format("%.0f", estadisticasTiempos[2]) + "ms");
            
            // Análisis de eficiencia
            double porcentajeEspera = (estadisticasTiempos[1] / estadisticasTiempos[0]) * 100;
            System.out.println("   % Tiempo esperando: " + String.format("%.1f%%", porcentajeEspera));
            
            if (porcentajeEspera > 30) {
                System.out.println("   ⚠️ Alto tiempo de espera - Considerar más entradas");
            } else if (porcentajeEspera < 10) {
                System.out.println("   ✅ Bajo tiempo de espera - Sistema eficiente");
            } else {
                System.out.println("   ✅ Tiempo de espera aceptable");
            }
            System.out.println();
        }
        
        // Análisis de rendimiento
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double tasaEntrada = (double)estadisticasAutos[1] / 60.0; // Asumiendo 1 minuto de simulación
        double capacidadTeorica = 20.0; // Capacidad máxima
        double utilizacion = ((double)statsEstacionamiento[0] / capacidadTeorica) * 100;
        
        System.out.println("   Utilización final: " + String.format("%.1f%%", utilizacion));
        System.out.println("   Eficiencia del sistema: " + 
                          (completaron >= totalGenerados * 0.9 ? "✅ Alta" : 
                           completaron >= totalGenerados * 0.7 ? "⚠️ Media" : "❌ Baja"));
        
        if (estacionamiento.estaVacio()) {
            System.out.println("   ✅ Estacionamiento vacío al final");
        } else {
            System.out.println("   ⚠️ " + estacionamiento.getAutomovilesActuales() + 
                             " automóviles aún en el estacionamiento");
        }
    }
    
    /**
     * Demuestra las ventajas específicas de usar Runnable vs Thread
     */
    private static void demostrarVentajasRunnable(GeneradorAutomoviles generador) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("        VENTAJAS DE RUNNABLE DEMOSTRADAS EN ESTACIONAMIENTO");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🎯 1. GESTIÓN MASIVA DE HILOS:");
        System.out.println("   ✅ Generación dinámica de " + generador.getAutomovilesGenerados() + " hilos de automóviles");
        System.out.println("   ✅ Control centralizado de todos los hilos desde el generador");
        System.out.println("   ✅ Gestión eficiente de recursos del sistema");
        
        System.out.println("\n🔄 2. FLEXIBILIDAD DE IMPLEMENTACIÓN:");
        System.out.println("   ✅ Separación clara entre lógica de negocio y gestión de hilos");
        System.out.println("   ✅ Automóviles como tareas reutilizables");
        System.out.println("   ✅ Fácil integración con pools de hilos si fuera necesario");
        
        System.out.println("\n🎛️ 3. CONTROL GRANULAR:");
        System.out.println("   ✅ Método detener() personalizado para parada controlada");
        System.out.println("   ✅ Estados independientes del ciclo de vida del hilo");
        System.out.println("   ✅ Información detallada de cada automóvil");
        
        // Demostrar información detallada
        int hilosVivos = (int) generador.getHilosAutomoviles().stream().filter(Thread::isAlive).count();
        System.out.println("\n📊 4. MONITOREO AVANZADO:");
        System.out.println("   ✅ Hilos aún vivos: " + hilosVivos + "/" + generador.getAutomovilesGenerados());
        System.out.println("   ✅ Estadísticas detalladas por automóvil");
        System.out.println("   ✅ Seguimiento de uso de entradas/salidas");
        
        System.out.println("\n🧪 5. TESTABILIDAD MEJORADA:");
        System.out.println("   ✅ Lógica de automóviles testeable sin hilos reales");
        System.out.println("   ✅ Simulación de estacionamiento sin concurrencia para tests");
        System.out.println("   ✅ Mocking sencillo de dependencias");
        
        System.out.println("\n⚡ 6. ESCALABILIDAD:");
        System.out.println("   ✅ Preparado para ExecutorService y pools de hilos");
        System.out.println("   ✅ Manejo eficiente de gran cantidad de tareas concurrentes");
        System.out.println("   ✅ Mejor gestión de memoria y recursos del SO");
        
        System.out.println("\n🏗️ 7. ARQUITECTURA LIMPIA:");
        System.out.println("   ✅ Responsabilidades bien definidas por clase");
        System.out.println("   ✅ Bajo acoplamiento entre componentes");
        System.out.println("   ✅ Fácil mantenimiento y extensión");
        
        System.out.println("\n📈 8. COMPARACIÓN CON HERENCIA DE THREAD:");
        System.out.println("   Flexibilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Gestión masiva:   Thread ⭐⭐   vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Reutilización:    Thread ⭐⭐   vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Testabilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Escalabilidad:    Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 CONCLUSIÓN: Para sistemas con muchos hilos concurrentes como este");
        System.out.println("   estacionamiento, Runnable ofrece ventajas significativas en gestión,");
        System.out.println("   escalabilidad y mantenibilidad del código.");
        System.out.println("=".repeat(80));
    }
}
