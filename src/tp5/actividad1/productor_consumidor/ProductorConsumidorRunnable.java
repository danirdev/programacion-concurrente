package tp5.actividad1.productor_consumidor;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del problema Productor-Consumidor usando interfaz Runnable.
 * Caso 1.a del TP4: Cola infinita con productores más lentos que consumidores.
 * Demuestra las ventajas de usar Runnable vs heredar de Thread.
 */
public class ProductorConsumidorRunnable {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("    PROBLEMA PRODUCTOR-CONSUMIDOR CON INTERFAZ RUNNABLE");
        System.out.println("=".repeat(80));
        System.out.println("Implementación: Interfaz Runnable (vs Thread del TP4)");
        System.out.println("Cola: Infinita");
        System.out.println("Productores: 10 (1000-1500ms por elemento) - MÁS LENTOS");
        System.out.println("Consumidores: 10 (400-800ms por elemento) - MÁS RÁPIDOS");
        System.out.println("Expectativa: Los consumidores esperarán elementos");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Crear cola infinita
        ColaRunnable cola = new ColaRunnable();
        System.out.println("✅ Cola creada: " + cola.getInfo());
        
        // Crear listas para almacenar las tareas Runnable
        List<Productor> productores = new ArrayList<>();
        List<Consumidor> consumidores = new ArrayList<>();
        List<Thread> hilosProductores = new ArrayList<>();
        List<Thread> hilosConsumidores = new ArrayList<>();
        
        // Crear 10 productores (más lentos: 1000-1500ms)
        System.out.println("\n📦 Creando productores...");
        for (int i = 1; i <= 10; i++) {
            Productor productor = new Productor(cola, i, 1000, 1500);
            productores.add(productor);
            
            // Crear hilo para el productor usando Runnable
            Thread hiloProductor = new Thread(productor, "Hilo-Productor-" + i);
            hilosProductores.add(hiloProductor);
        }
        
        // Crear 10 consumidores (más rápidos: 400-800ms)
        System.out.println("🔄 Creando consumidores...");
        for (int i = 1; i <= 10; i++) {
            Consumidor consumidor = new Consumidor(cola, i, 400, 800);
            consumidores.add(consumidor);
            
            // Crear hilo para el consumidor usando Runnable
            Thread hiloConsumidor = new Thread(consumidor, "Hilo-Consumidor-" + i);
            hilosConsumidores.add(hiloConsumidor);
        }
        
        // Iniciar todos los hilos de productores
        System.out.println("\n🚀 Iniciando hilos de productores...");
        for (Thread hilo : hilosProductores) {
            hilo.start();
            System.out.println("   Iniciado: " + hilo.getName());
        }
        
        // Iniciar todos los hilos de consumidores
        System.out.println("🚀 Iniciando hilos de consumidores...");
        for (Thread hilo : hilosConsumidores) {
            hilo.start();
            System.out.println("   Iniciado: " + hilo.getName());
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          SIMULACIÓN EN EJECUCIÓN");
        System.out.println("=".repeat(50));
        
        // Ejecutar por 30 segundos
        try {
            Thread.sleep(30000); // 30 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Detener todas las tareas de forma controlada
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        FINALIZANDO SIMULACIÓN");
        System.out.println("=".repeat(50));
        
        // Detener productores usando el método detener() de Runnable
        System.out.println("🛑 Deteniendo productores...");
        for (Productor productor : productores) {
            productor.detener();
        }
        
        // Detener consumidores usando el método detener() de Runnable
        System.out.println("🛑 Deteniendo consumidores...");
        for (Consumidor consumidor : consumidores) {
            consumidor.detener();
        }
        
        // Interrumpir hilos como respaldo
        for (Thread hilo : hilosProductores) {
            hilo.interrupt();
        }
        for (Thread hilo : hilosConsumidores) {
            hilo.interrupt();
        }
        
        // Esperar a que todos los hilos terminen
        try {
            System.out.println("⏳ Esperando finalización de hilos...");
            
            for (Thread hilo : hilosProductores) {
                hilo.join(2000); // Esperar máximo 2 segundos
            }
            
            for (Thread hilo : hilosConsumidores) {
                hilo.join(2000); // Esperar máximo 2 segundos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Mostrar estadísticas finales
        mostrarEstadisticas(productores, consumidores, cola);
        
        // Demostrar ventajas de Runnable
        demostrarVentajasRunnable(productores, consumidores);
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticas(List<Productor> productores, 
                                          List<Consumidor> consumidores, 
                                          ColaRunnable cola) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    ESTADÍSTICAS FINALES");
        System.out.println("=".repeat(80));
        
        int totalProducido = 0;
        int totalConsumido = 0;
        
        System.out.println("\n📦 PRODUCTORES (Runnable):");
        for (Productor productor : productores) {
            int producidos = productor.getElementosProducidos();
            totalProducido += producidos;
            System.out.println("  Productor-" + productor.getId() + ": " + producidos + " elementos");
        }
        
        System.out.println("\n🔄 CONSUMIDORES (Runnable):");
        for (Consumidor consumidor : consumidores) {
            int consumidos = consumidor.getElementosConsumidos();
            totalConsumido += consumidos;
            System.out.println("  Consumidor-" + consumidor.getId() + ": " + consumidos + " elementos");
        }
        
        System.out.println("\n📊 RESUMEN GENERAL:");
        System.out.println("  Total producido: " + totalProducido);
        System.out.println("  Total consumido: " + totalConsumido);
        System.out.println("  Elementos en cola: " + cola.tamaño());
        System.out.println("  Diferencia (P-C): " + (totalProducido - totalConsumido));
        System.out.println("  Tipo de cola: " + cola.getInfo());
        
        // Análisis del comportamiento
        System.out.println("\n🔍 ANÁLISIS:");
        if (totalProducido < totalConsumido) {
            System.out.println("  ✅ RESULTADO ESPERADO: Los consumidores esperaron elementos");
            System.out.println("     (productores más lentos que consumidores)");
        } else if (totalProducido > totalConsumido) {
            System.out.println("  ⚠️  Se acumularon elementos en la cola");
        } else {
            System.out.println("  ⚖️  Producción y consumo equilibrados");
        }
        
        double promedioProduccion = totalProducido / 10.0;
        double promedioConsumo = totalConsumido / 10.0;
        System.out.println("  Promedio producción por hilo: " + String.format("%.1f", promedioProduccion));
        System.out.println("  Promedio consumo por hilo: " + String.format("%.1f", promedioConsumo));
    }
    
    /**
     * Demuestra las ventajas específicas de usar Runnable vs Thread
     */
    private static void demostrarVentajasRunnable(List<Productor> productores, 
                                                List<Consumidor> consumidores) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("              VENTAJAS DE RUNNABLE DEMOSTRADAS");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🎯 1. FLEXIBILIDAD DE IMPLEMENTACIÓN:");
        System.out.println("   ✅ Las clases Productor y Consumidor implementan Runnable");
        System.out.println("   ✅ Pueden heredar de otras clases si es necesario");
        System.out.println("   ✅ Separación clara entre tarea y ejecución");
        
        System.out.println("\n🔄 2. REUTILIZACIÓN DE CÓDIGO:");
        System.out.println("   ✅ Mismo objeto Runnable puede usarse en múltiples hilos");
        System.out.println("   ✅ Ejemplo: Un Productor puede crear múltiples hilos");
        
        // Demostrar reutilización
        Productor productorReutilizable = new Productor(new ColaRunnable(), 999, 500, 1000);
        Thread hilo1 = new Thread(productorReutilizable, "Hilo-Reutilizado-1");
        Thread hilo2 = new Thread(productorReutilizable, "Hilo-Reutilizado-2");
        
        System.out.println("   📝 Demostración: Mismo Productor en 2 hilos diferentes:");
        System.out.println("      - " + hilo1.getName());
        System.out.println("      - " + hilo2.getName());
        
        System.out.println("\n🎛️  3. CONTROL MEJORADO:");
        System.out.println("   ✅ Método detener() personalizado para parada controlada");
        System.out.println("   ✅ Estado activo/inactivo independiente del hilo");
        System.out.println("   ✅ Mejor gestión del ciclo de vida");
        
        System.out.println("\n🧪 4. TESTABILIDAD:");
        System.out.println("   ✅ Métodos run() pueden ejecutarse directamente en tests");
        System.out.println("   ✅ No requiere crear hilos reales para testing");
        System.out.println("   ✅ Mejor aislamiento de la lógica de negocio");
        
        System.out.println("\n⚡ 5. COMPATIBILIDAD CON FRAMEWORKS:");
        System.out.println("   ✅ Compatible con ExecutorService y pools de hilos");
        System.out.println("   ✅ Mejor integración con frameworks modernos");
        System.out.println("   ✅ Escalabilidad mejorada");
        
        System.out.println("\n📈 6. ESTADÍSTICAS DE IMPLEMENTACIÓN:");
        int productoresActivos = (int) productores.stream().filter(Productor::estaActivo).count();
        int consumidoresActivos = (int) consumidores.stream().filter(Consumidor::estaActivo).count();
        
        System.out.println("   Productores aún activos: " + productoresActivos + "/10");
        System.out.println("   Consumidores aún activos: " + consumidoresActivos + "/10");
        System.out.println("   Control de estado: ✅ Funcional");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 CONCLUSIÓN: Runnable ofrece mayor flexibilidad y mejor diseño");
        System.out.println("   que la herencia directa de Thread, manteniendo la funcionalidad.");
        System.out.println("=".repeat(80));
    }
}
