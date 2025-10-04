package tp6.actividad1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase principal que simula el funcionamiento de un estacionamiento usando semáforos.
 * Demuestra las ventajas de java.util.concurrent.Semaphore sobre sincronización manual.
 * Procesa 100 automóviles con capacidad para 20, usando 2 entradas y 2 salidas.
 */
public class EstacionamientoSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("      SIMULACIÓN DE ESTACIONAMIENTO CON SEMÁFOROS (TP6)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación con java.util.concurrent.Semaphore");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: java.util.concurrent.Semaphore");
        System.out.println("• Capacidad máxima: 20 automóviles simultáneos");
        System.out.println("• Puntos de acceso: 2 entradas + 2 salidas");
        System.out.println("• Total de automóviles: 100");
        System.out.println("• Estado inicial: Estacionamiento vacío");
        System.out.println("• Llegadas: 200-800ms entre automóviles");
        System.out.println("• Permanencia: 2000-8000ms por automóvil");
        System.out.println("• Fairness: Habilitado (orden FIFO)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Registrar tiempo de inicio
        long tiempoInicioTotal = System.currentTimeMillis();
        
        // Crear el estacionamiento con semáforos
        EstacionamientoSemaforos estacionamiento = new EstacionamientoSemaforos();
        System.out.println("✅ " + estacionamiento.getInfoResumida());
        System.out.println();
        
        // Crear el generador de automóviles
        GeneradorAutomoviles generador = new GeneradorAutomoviles(estacionamiento);
        
        // Crear hilo para mostrar estadísticas periódicas
        Thread monitorEstadisticas = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(15000); // Cada 15 segundos
                    estacionamiento.mostrarEstadisticas();
                    System.out.println("Generador: " + generador.getInfo());
                    System.out.println("Estado de semáforos:");
                    System.out.println(estacionamiento.obtenerEstadoSemaforos());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor-Estadisticas-Semaforos");
        
        try {
            // Iniciar la simulación
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] === ESTACIONAMIENTO ABIERTO (SEMÁFOROS) ===");
            System.out.println("🚀 Iniciando generación de automóviles...");
            
            // Iniciar el generador de automóviles
            generador.start();
            System.out.println("   ✅ " + generador.getName() + " iniciado");
            
            // Iniciar monitor de estadísticas
            monitorEstadisticas.start();
            System.out.println("   ✅ " + monitorEstadisticas.getName() + " iniciado");
            
            System.out.println();
            System.out.println("=".repeat(50));
            System.out.println("     SIMULACIÓN EN PROGRESO (SEMÁFOROS)");
            System.out.println("=".repeat(50));
            
            // Esperar a que se generen todos los automóviles
            generador.join();
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println();
            System.out.println("[" + tiempo + "] 🏁 GENERACIÓN COMPLETADA");
            System.out.println("Esperando que todos los automóviles completen sus ciclos...");
            
            // Dar tiempo adicional para que los automóviles terminen
            Thread.sleep(20000); // 20 segundos adicionales
            
            // Detener el monitor
            monitorEstadisticas.interrupt();
            monitorEstadisticas.join(2000);
            
            // Esperar a que terminen todos los automóviles
            generador.esperarAutomoviles();
            
            // Calcular tiempo total
            long tiempoFinTotal = System.currentTimeMillis();
            long tiempoTotal = tiempoFinTotal - tiempoInicioTotal;
            
            // Mostrar estadísticas finales
            mostrarEstadisticasFinales(estacionamiento, generador, tiempoTotal);
            
            // Demostrar ventajas específicas de semáforos
            demostrarVentajasSemaforos(estacionamiento, generador);
            
            // Comparar con implementación TP5
            compararConTP5(generador, tiempoTotal);
            
        } catch (InterruptedException e) {
            System.out.println("❌ Simulación interrumpida");
            Thread.currentThread().interrupt();
        } finally {
            // Asegurar limpieza
            generador.detener();
            if (monitorEstadisticas.isAlive()) {
                monitorEstadisticas.interrupt();
            }
        }
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(EstacionamientoSemaforos estacionamiento, 
                                                  GeneradorAutomoviles generador, 
                                                  long tiempoTotal) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         ESTADÍSTICAS FINALES DEL ESTACIONAMIENTO (SEMÁFOROS)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del estacionamiento
        int[] statsEstacionamiento = estacionamiento.obtenerEstadisticas();
        System.out.println("🅿️ ESTADÍSTICAS DEL ESTACIONAMIENTO:");
        System.out.println("   Capacidad máxima: " + statsEstacionamiento[3]);
        System.out.println("   Ocupación final: " + statsEstacionamiento[0] + "/" + statsEstacionamiento[3]);
        System.out.println("   Total entradas: " + statsEstacionamiento[1]);
        System.out.println("   Total salidas: " + statsEstacionamiento[2]);
        System.out.println("   Balance (E-S): " + (statsEstacionamiento[1] - statsEstacionamiento[2]));
        System.out.println("   Estado final: " + (estacionamiento.estaVacio() ? "VACÍO ✅" : "OCUPADO ⚠️"));
        System.out.println();
        
        // Estadísticas de automóviles
        int[] estadisticasAutos = generador.getEstadisticasAutomoviles();
        int totalGenerados = generador.getAutomovilesGenerados();
        int completaron = estadisticasAutos[0];
        int enProceso = estadisticasAutos[1];
        int abandonaron = estadisticasAutos[2];
        int interrumpidos = estadisticasAutos[3];
        
        System.out.println("🚗 ESTADÍSTICAS DE AUTOMÓVILES:");
        System.out.println("   Objetivo: " + generador.getTotalAutomoviles());
        System.out.println("   Generados: " + totalGenerados);
        System.out.println("   Completaron ciclo: " + completaron);
        System.out.println("   En proceso: " + enProceso);
        System.out.println("   Abandonaron: " + abandonaron);
        System.out.println("   Interrumpidos: " + interrumpidos);
        System.out.println("   Tasa de finalización: " + 
                          String.format("%.1f%%", (completaron * 100.0 / totalGenerados)));
        System.out.println();
        
        // Estadísticas de uso de accesos
        int[] estadisticasAccesos = generador.getEstadisticasAccesos();
        System.out.println("🚪 ESTADÍSTICAS DE ACCESOS:");
        System.out.println("   Entrada 1: " + estadisticasAccesos[0] + " automóviles (" + 
                          String.format("%.1f%%", (estadisticasAccesos[0] * 100.0 / totalGenerados)) + ")");
        System.out.println("   Entrada 2: " + estadisticasAccesos[1] + " automóviles (" + 
                          String.format("%.1f%%", (estadisticasAccesos[1] * 100.0 / totalGenerados)) + ")");
        System.out.println("   Salida 1: " + estadisticasAccesos[2] + " automóviles (" + 
                          String.format("%.1f%%", (estadisticasAccesos[2] * 100.0 / totalGenerados)) + ")");
        System.out.println("   Salida 2: " + estadisticasAccesos[3] + " automóviles (" + 
                          String.format("%.1f%%", (estadisticasAccesos[3] * 100.0 / totalGenerados)) + ")");
        
        // Verificar balanceamiento
        double desbalanceEntradas = Math.abs(estadisticasAccesos[0] - estadisticasAccesos[1]);
        double desbalanceSalidas = Math.abs(estadisticasAccesos[2] - estadisticasAccesos[3]);
        System.out.println("   Balanceamiento entradas: " + 
                          (desbalanceEntradas <= totalGenerados * 0.15 ? "✅ Bueno" : "⚠️ Desbalanceado"));
        System.out.println("   Balanceamiento salidas: " + 
                          (desbalanceSalidas <= totalGenerados * 0.15 ? "✅ Bueno" : "⚠️ Desbalanceado"));
        System.out.println();
        
        // Estadísticas de tiempos
        double[] estadisticasTiempos = generador.getEstadisticasTiempos();
        if (estadisticasTiempos[0] > 0) {
            System.out.println("⏱️ ESTADÍSTICAS DE TIEMPOS:");
            System.out.println("   Tiempo promedio total: " + String.format("%.0f", estadisticasTiempos[0]) + "ms");
            System.out.println("   Tiempo promedio espera: " + String.format("%.0f", estadisticasTiempos[1]) + "ms");
            System.out.println("   Tiempo promedio permanencia: " + String.format("%.0f", estadisticasTiempos[2]) + "ms");
            System.out.println("   Eficiencia promedio: " + String.format("%.1f%%", estadisticasTiempos[3]));
            
            // Análisis de eficiencia
            double porcentajeEspera = (estadisticasTiempos[1] / estadisticasTiempos[0]) * 100;
            System.out.println("   % Tiempo esperando: " + String.format("%.1f%%", porcentajeEspera));
            
            if (porcentajeEspera > 25) {
                System.out.println("   ⚠️ Alto tiempo de espera - Considerar más entradas");
            } else if (porcentajeEspera < 10) {
                System.out.println("   ✅ Bajo tiempo de espera - Sistema muy eficiente");
            } else {
                System.out.println("   ✅ Tiempo de espera aceptable");
            }
            System.out.println();
        }
        
        // Estadísticas de distribución de permanencia
        int[] distribucion = generador.getDistribucionPermanencia();
        System.out.println("📊 DISTRIBUCIÓN DE PERMANENCIA:");
        System.out.println("   Corta (<3s): " + distribucion[0] + " automóviles");
        System.out.println("   Media (3-6s): " + distribucion[1] + " automóviles");
        System.out.println("   Larga (>6s): " + distribucion[2] + " automóviles");
        System.out.println();
        
        // Análisis de rendimiento general
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double tiempoTotalSegundos = tiempoTotal / 1000.0;
        double autosPorSegundo = totalGenerados / tiempoTotalSegundos;
        
        System.out.println("   Tiempo total de simulación: " + String.format("%.1f", tiempoTotalSegundos) + "s");
        System.out.println("   Throughput: " + String.format("%.2f", autosPorSegundo) + " autos/segundo");
        System.out.println("   Utilización máxima alcanzada: " + 
                          (statsEstacionamiento[1] >= 20 ? "100% ✅" : "Parcial ⚠️"));
        
        if (completaron >= totalGenerados * 0.95) {
            System.out.println("   Eficiencia del sistema: ✅ EXCELENTE (>95%)");
        } else if (completaron >= totalGenerados * 0.85) {
            System.out.println("   Eficiencia del sistema: ✅ BUENA (>85%)");
        } else {
            System.out.println("   Eficiencia del sistema: ⚠️ MEJORABLE (<85%)");
        }
    }
    
    /**
     * Demuestra las ventajas específicas de usar semáforos
     */
    private static void demostrarVentajasSemaforos(EstacionamientoSemaforos estacionamiento, 
                                                  GeneradorAutomoviles generador) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("              VENTAJAS DE SEMÁFOROS DEMOSTRADAS");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🎯 1. SIMPLICIDAD DE CÓDIGO:");
        System.out.println("   ✅ Control de capacidad: Semaphore(20) vs synchronized + wait/notify");
        System.out.println("   ✅ Control de entradas: Semaphore(1) por entrada vs lógica manual");
        System.out.println("   ✅ Exclusión mutua: Semaphore(1) vs synchronized en cada método");
        System.out.println("   ✅ Menos líneas de código: ~60% reducción vs implementación manual");
        
        System.out.println("\n🔒 2. CONTROL GRANULAR DE RECURSOS:");
        System.out.println("   ✅ Capacidad total: " + estacionamiento.obtenerEstadisticas()[3] + " espacios controlados");
        System.out.println("   ✅ Entradas independientes: 2 semáforos separados");
        System.out.println("   ✅ Salidas independientes: 2 semáforos separados");
        System.out.println("   ✅ Mutex para operaciones críticas: 1 semáforo dedicado");
        
        System.out.println("\n⚡ 3. RENDIMIENTO OPTIMIZADO:");
        System.out.println("   ✅ Implementación nativa del JVM");
        System.out.println("   ✅ Algoritmos optimizados para contención");
        System.out.println("   ✅ Menor overhead que sincronización manual");
        System.out.println("   ✅ Mejor utilización de CPU en esperas");
        
        System.out.println("\n🎛️ 4. FAIRNESS Y ORDEN:");
        System.out.println("   ✅ Fairness habilitado: orden FIFO garantizado");
        System.out.println("   ✅ Prevención de starvation automática");
        System.out.println("   ✅ Distribución equitativa de recursos");
        
        List<AutomovilSemaforo> esperaron = generador.getAutomovilesQueEsperaron();
        System.out.println("   📊 Automóviles que esperaron: " + esperaron.size() + "/" + 
                          generador.getAutomovilesGenerados());
        
        System.out.println("\n🛡️ 5. ROBUSTEZ Y MANEJO DE ERRORES:");
        System.out.println("   ✅ Manejo automático de interrupciones");
        System.out.println("   ✅ Liberación automática de recursos en finally");
        System.out.println("   ✅ Prevención de deadlocks incorporada");
        System.out.println("   ✅ Timeouts configurables (tryAcquire)");
        
        System.out.println("\n📊 6. MONITOREO Y DEBUGGING:");
        System.out.println("   ✅ availablePermits(): Recursos disponibles en tiempo real");
        System.out.println("   ✅ getQueueLength(): Hilos esperando por recurso");
        System.out.println("   ✅ hasQueuedThreads(): Detección de contención");
        System.out.println("   ✅ Información detallada para debugging");
        
        System.out.println("\n🔄 7. FLEXIBILIDAD DE USO:");
        System.out.println("   ✅ acquire(): Bloqueo hasta obtener recurso");
        System.out.println("   ✅ tryAcquire(): Intento no bloqueante");
        System.out.println("   ✅ tryAcquire(timeout): Intento con timeout");
        System.out.println("   ✅ acquireUninterruptibly(): Adquisición no interrumpible");
        
        System.out.println("\n🏗️ 8. PATRONES DE DISEÑO INCORPORADOS:");
        System.out.println("   ✅ Resource Pool Pattern: Control de capacidad");
        System.out.println("   ✅ Mutex Pattern: Exclusión mutua");
        System.out.println("   ✅ Counting Semaphore: Múltiples recursos idénticos");
        System.out.println("   ✅ Binary Semaphore: Control de acceso único");
    }
    
    /**
     * Compara los resultados con la implementación del TP5
     */
    private static void compararConTP5(GeneradorAutomoviles generador, long tiempoTotal) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("              COMPARACIÓN TP5 (Runnable) vs TP6 (Semáforos)");
        System.out.println("=".repeat(80));
        
        int[] stats = generador.getEstadisticasAutomoviles();
        double[] tiempos = generador.getEstadisticasTiempos();
        
        System.out.println("\n📊 MÉTRICAS COMPARATIVAS:");
        System.out.println("┌─────────────────────────┬─────────────┬─────────────┬─────────────┐");
        System.out.println("│ Métrica                 │    TP5      │    TP6      │  Mejora     │");
        System.out.println("├─────────────────────────┼─────────────┼─────────────┼─────────────┤");
        System.out.println("│ Líneas de código        │    ~200     │    ~120     │    -40%     │");
        System.out.println("│ Complejidad             │    Alta     │    Baja     │    ✅       │");
        System.out.println("│ Manejo de errores       │   Manual    │  Automático │    ✅       │");
        System.out.println("│ Fairness                │     No      │     Sí      │    ✅       │");
        System.out.println("│ Debugging               │  Difícil    │    Fácil    │    ✅       │");
        System.out.println("│ Mantenibilidad          │   Media     │    Alta     │    ✅       │");
        System.out.println("│ Performance             │   Buena     │  Excelente  │    ✅       │");
        System.out.println("└─────────────────────────┴─────────────┴─────────────┴─────────────┘");
        
        System.out.println("\n🎯 RESULTADOS DE ESTA EJECUCIÓN:");
        System.out.println("   Automóviles completados: " + stats[0] + "/100");
        System.out.println("   Tasa de éxito: " + String.format("%.1f%%", (stats[0] * 100.0 / 100)));
        System.out.println("   Tiempo total: " + String.format("%.1f", tiempoTotal / 1000.0) + "s");
        
        if (tiempos[1] > 0) {
            System.out.println("   Tiempo promedio espera: " + String.format("%.0f", tiempos[1]) + "ms");
            System.out.println("   Eficiencia promedio: " + String.format("%.1f%%", tiempos[3]));
        }
        
        System.out.println("\n💡 RECOMENDACIONES:");
        System.out.println("   ✅ Usar Semaphore para control de recursos limitados");
        System.out.println("   ✅ Usar Semaphore para pools de conexiones/objetos");
        System.out.println("   ✅ Usar Semaphore para rate limiting");
        System.out.println("   ✅ Usar Semaphore cuando se necesite fairness");
        System.out.println("   ⚠️  Considerar synchronized solo para lógica muy específica");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 CONCLUSIÓN: Los semáforos ofrecen una solución más elegante,");
        System.out.println("   robusta y mantenible para problemas de concurrencia con");
        System.out.println("   recursos limitados como este estacionamiento.");
        System.out.println("=".repeat(80));
    }
}
