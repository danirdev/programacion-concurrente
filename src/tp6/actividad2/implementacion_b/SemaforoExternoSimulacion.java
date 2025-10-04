package tp6.actividad2.implementacion_b;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para la simulación del Semáforo Externo - Implementación B.
 * Define el semáforo en el main y lo pasa por parámetro a los hilos.
 * Crea 10 hilos que utilizan un semáforo general inicializado en 3.
 */
public class SemaforoExternoSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("        SEMÁFORO GENERAL - IMPLEMENTACIÓN B (EXTERNO)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación con semáforo externo");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: B - Semáforo definido en main y pasado por parámetro");
        System.out.println("• Valor inicial del semáforo: 3");
        System.out.println("• Número de hilos: 10");
        System.out.println("• Tiempo de sueño por hilo: 5 segundos");
        System.out.println("• Tipo de acceso: Métodos de instancia");
        System.out.println("• Acoplamiento: Bajo (inyección de dependencias)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Registrar tiempo de inicio
        long tiempoInicioTotal = System.currentTimeMillis();
        
        // Crear el semáforo externo en el main (Implementación B)
        SemaforoExterno semaforo = new SemaforoExterno(3, "Semaforo-Principal");
        
        // Mostrar configuración del semáforo
        System.out.println(semaforo.getConfiguracion());
        System.out.println();
        System.out.println("Estado inicial: " + semaforo.getInfoResumida());
        System.out.println();
        
        // Crear lista de hilos
        List<HiloSemaforoExterno> hilos = new ArrayList<>();
        
        // Crear 10 hilos pasando el semáforo por parámetro
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🔧 Creando 10 hilos con inyección de dependencias...");
        
        for (int i = 1; i <= 10; i++) {
            String nombreHilo = "Hilo-" + String.format("%02d", i);
            
            // Demostrar flexibilidad: algunos hilos usan tryAcquire
            boolean usarTryAcquire = (i % 4 == 0); // Cada 4to hilo usa tryAcquire
            
            HiloSemaforoExterno hilo = new HiloSemaforoExterno(nombreHilo, semaforo, usarTryAcquire);
            hilos.add(hilo);
            
            String metodo = usarTryAcquire ? " (con tryAcquire)" : " (con acquire)";
            System.out.println("   ✅ " + nombreHilo + " creado" + metodo);
        }
        
        System.out.println();
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🚀 INICIANDO TODOS LOS HILOS");
        System.out.println("Estado del semáforo: " + semaforo.getInfoResumida());
        System.out.println("=".repeat(50));
        
        // Iniciar todos los hilos
        for (HiloSemaforoExterno hilo : hilos) {
            hilo.start();
            // Pequeño delay para observar mejor el orden
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Crear hilo monitor para mostrar estadísticas periódicas
        Thread monitor = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(3000); // Cada 3 segundos
                    semaforo.mostrarEstado();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor-Estadisticas-Externo");
        
        monitor.start();
        
        System.out.println("⏳ Esperando que todos los hilos completen su ejecución...");
        System.out.println("📊 Observar el orden de ejecución y el comportamiento del semáforo");
        System.out.println("🔍 Notar la flexibilidad de la inyección de dependencias");
        System.out.println();
        
        // Esperar a que todos los hilos terminen
        for (HiloSemaforoExterno hilo : hilos) {
            try {
                hilo.join(); // Esperar a que termine cada hilo
            } catch (InterruptedException e) {
                System.err.println("❌ Interrupción durante la espera del hilo: " + hilo.getName());
                Thread.currentThread().interrupt();
            }
        }
        
        // Detener el monitor
        monitor.interrupt();
        try {
            monitor.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Calcular tiempo total
        long tiempoFinTotal = System.currentTimeMillis();
        long tiempoTotal = tiempoFinTotal - tiempoInicioTotal;
        
        // Mostrar estadísticas finales
        mostrarEstadisticasFinales(semaforo, hilos, tiempoTotal);
        
        // Demostrar características de la Implementación B
        demostrarCaracteristicasImplementacionB(semaforo);
        
        // Comparar con Implementación A
        compararImplementaciones();
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(SemaforoExterno semaforo, 
                                                  List<HiloSemaforoExterno> hilos, 
                                                  long tiempoTotal) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("           ESTADÍSTICAS FINALES - IMPLEMENTACIÓN B");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del semáforo
        int[] statsSemaforo = semaforo.getEstadisticas();
        System.out.println("🔒 ESTADÍSTICAS DEL SEMÁFORO EXTERNO:");
        System.out.println("   Identificador: " + semaforo.getIdentificador());
        System.out.println("   Valor inicial: " + semaforo.getValorInicial());
        System.out.println("   Valor final: " + statsSemaforo[0]);
        System.out.println("   Total adquisiciones: " + statsSemaforo[1]);
        System.out.println("   Total liberaciones: " + statsSemaforo[2]);
        System.out.println("   Hilos esperando al final: " + statsSemaforo[3]);
        System.out.println("   Balance (A-L): " + (statsSemaforo[1] - statsSemaforo[2]));
        System.out.println("   Estado final: " + (semaforo.estaEnEstadoInicial() ? "✅ RESTAURADO" : "⚠️ INCONSISTENTE"));
        System.out.println();
        
        // Estadísticas de hilos
        int completados = 0;
        int esperaron = 0;
        int usaronTryAcquire = 0;
        long tiempoEsperaTotal = 0;
        long tiempoEsperaMaximo = 0;
        long tiempoEsperaMinimo = Long.MAX_VALUE;
        
        System.out.println("🧵 ESTADÍSTICAS DE HILOS:");
        for (HiloSemaforoExterno hilo : hilos) {
            if (hilo.isCompletoEjecucion()) {
                completados++;
            }
            
            if (hilo.isUsoTryAcquire()) {
                usaronTryAcquire++;
            }
            
            long tiempoEspera = hilo.getTiempoEspera();
            if (hilo.tuvoQueEsperar()) {
                esperaron++;
                tiempoEsperaTotal += tiempoEspera;
                tiempoEsperaMaximo = Math.max(tiempoEsperaMaximo, tiempoEspera);
                tiempoEsperaMinimo = Math.min(tiempoEsperaMinimo, tiempoEspera);
            }
        }
        
        System.out.println("   Total hilos: " + hilos.size());
        System.out.println("   Completados exitosamente: " + completados);
        System.out.println("   Hilos que esperaron: " + esperaron);
        System.out.println("   Hilos que usaron tryAcquire: " + usaronTryAcquire);
        System.out.println("   Tasa de finalización: " + 
                          String.format("%.1f%%", (completados * 100.0 / hilos.size())));
        
        if (esperaron > 0) {
            double tiempoPromedioEspera = tiempoEsperaTotal / (double) esperaron;
            System.out.println("   Tiempo promedio de espera: " + String.format("%.0f", tiempoPromedioEspera) + "ms");
            System.out.println("   Tiempo máximo de espera: " + tiempoEsperaMaximo + "ms");
            System.out.println("   Tiempo mínimo de espera: " + (tiempoEsperaMinimo == Long.MAX_VALUE ? 0 : tiempoEsperaMinimo) + "ms");
        }
        System.out.println();
        
        // Análisis temporal
        System.out.println("⏱️ ANÁLISIS TEMPORAL:");
        double tiempoTotalSegundos = tiempoTotal / 1000.0;
        System.out.println("   Tiempo total de simulación: " + String.format("%.1f", tiempoTotalSegundos) + "s");
        System.out.println("   Tiempo teórico mínimo: 15.0s (3 grupos × 5s)");
        System.out.println("   Tiempo teórico máximo: 50.0s (10 hilos × 5s)");
        
        if (tiempoTotalSegundos <= 20) {
            System.out.println("   Eficiencia: ✅ EXCELENTE (paralelización óptima)");
        } else if (tiempoTotalSegundos <= 30) {
            System.out.println("   Eficiencia: ✅ BUENA (paralelización efectiva)");
        } else {
            System.out.println("   Eficiencia: ⚠️ MEJORABLE (poca paralelización)");
        }
        System.out.println();
        
        // Análisis de eficiencia por hilo
        double eficienciaPromedio = hilos.stream()
            .filter(HiloSemaforoExterno::isCompletoEjecucion)
            .mapToDouble(HiloSemaforoExterno::calcularEficiencia)
            .average()
            .orElse(0.0);
        
        System.out.println("📊 ANÁLISIS DE EFICIENCIA:");
        System.out.println("   Eficiencia promedio por hilo: " + String.format("%.1f%%", eficienciaPromedio));
        
        if (eficienciaPromedio >= 80) {
            System.out.println("   Rendimiento: ✅ EXCELENTE (>80%)");
        } else if (eficienciaPromedio >= 60) {
            System.out.println("   Rendimiento: ✅ BUENO (>60%)");
        } else {
            System.out.println("   Rendimiento: ⚠️ MEJORABLE (<60%)");
        }
        System.out.println();
        
        // Detalles por hilo
        System.out.println("📋 DETALLES POR HILO:");
        for (HiloSemaforoExterno hilo : hilos) {
            System.out.println("   " + hilo.getEstadisticasDetalladas());
        }
        System.out.println();
        
        // Análisis del comportamiento del semáforo
        System.out.println("🔍 ANÁLISIS DEL COMPORTAMIENTO:");
        System.out.println("   Máximo hilos simultáneos: 3 (limitado por semáforo)");
        System.out.println("   Hilos que accedieron inmediatamente: " + (hilos.size() - esperaron));
        System.out.println("   Hilos que tuvieron que esperar: " + esperaron);
        System.out.println("   Flexibilidad demostrada: " + usaronTryAcquire + " hilos usaron tryAcquire");
        
        if (esperaron == 7) { // 10 - 3 = 7 hilos deberían esperar
            System.out.println("   Comportamiento: ✅ CORRECTO (7 hilos esperaron como se esperaba)");
        } else {
            System.out.println("   Comportamiento: ⚠️ INESPERADO (" + esperaron + " hilos esperaron)");
        }
    }
    
    /**
     * Demuestra las características específicas de la Implementación B
     */
    private static void demostrarCaracteristicasImplementacionB(SemaforoExterno semaforo) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         CARACTERÍSTICAS DE LA IMPLEMENTACIÓN B");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🏗️ ARQUITECTURA:");
        System.out.println("   ✅ Semáforo definido en main y pasado por parámetro");
        System.out.println("   ✅ Acceso mediante métodos de instancia");
        System.out.println("   ✅ Inyección de dependencias explícita");
        System.out.println("   ✅ Configuración flexible (valor inicial configurable)");
        System.out.println("   ✅ Identificador personalizable: " + semaforo.getIdentificador());
        
        System.out.println("\n👍 VENTAJAS:");
        System.out.println("   ✅ Bajo acoplamiento - Hilos no dependen de clase específica");
        System.out.println("   ✅ Fácil de testear - Se puede inyectar mock del semáforo");
        System.out.println("   ✅ Altamente reutilizable - Diferentes valores y configuraciones");
        System.out.println("   ✅ Configuración flexible - Valor inicial y comportamiento configurable");
        System.out.println("   ✅ Múltiples instancias - Varios semáforos independientes");
        System.out.println("   ✅ Principio de inversión de dependencias respetado");
        
        System.out.println("\n👎 DESVENTAJAS:");
        System.out.println("   ❌ Más código de configuración - Requiere crear y pasar instancia");
        System.out.println("   ❌ Gestión de instancias - Responsabilidad del código cliente");
        System.out.println("   ❌ Posible pérdida de referencia - Si no se gestiona correctamente");
        System.out.println("   ❌ Complejidad inicial - Más conceptos para entender");
        
        System.out.println("\n🔧 CASOS DE USO APROPIADOS:");
        System.out.println("   ✅ Aplicaciones complejas con múltiples semáforos");
        System.out.println("   ✅ Sistemas que requieren configuración dinámica");
        System.out.println("   ✅ Código que necesita testing unitario extensivo");
        System.out.println("   ✅ Aplicaciones con diferentes valores de semáforo");
        System.out.println("   ✅ Sistemas distribuidos o modulares");
        System.out.println("   ✅ Frameworks y librerías reutilizables");
        
        System.out.println("\n✅ CASOS DE USO RECOMENDADOS:");
        System.out.println("   ✅ Aplicaciones de producción");
        System.out.println("   ✅ Sistemas con requisitos de calidad altos");
        System.out.println("   ✅ Código que debe ser mantenible a largo plazo");
        System.out.println("   ✅ Proyectos con equipos de desarrollo grandes");
        
        System.out.println("\n📊 MÉTRICAS DE CALIDAD:");
        System.out.println("   Acoplamiento:     🟢 Bajo");
        System.out.println("   Cohesión:         🟢 Alta");
        System.out.println("   Testabilidad:     🟢 Alta");
        System.out.println("   Reutilización:    🟢 Alta");
        System.out.println("   Mantenibilidad:   🟢 Alta");
        System.out.println("   Simplicidad:      🟡 Media");
        
        System.out.println("\n🎯 FLEXIBILIDAD DEMOSTRADA:");
        System.out.println("   • Valor inicial configurable: " + semaforo.getValorInicial());
        System.out.println("   • Identificador personalizado: " + semaforo.getIdentificador());
        System.out.println("   • Métodos adicionales: tryAcquire(), getEstadisticas()");
        System.out.println("   • Monitoreo avanzado: estado detallado disponible");
        System.out.println("   • Reutilización: mismo semáforo para múltiples hilos");
    }
    
    /**
     * Compara las dos implementaciones (A vs B)
     */
    private static void compararImplementaciones() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("              COMPARACIÓN IMPLEMENTACIÓN A vs B");
        System.out.println("=".repeat(80));
        
        System.out.println("\n📊 TABLA COMPARATIVA:");
        System.out.println("┌─────────────────────────┬─────────────────┬─────────────────┐");
        System.out.println("│ Característica          │ Implementación A│ Implementación B│");
        System.out.println("├─────────────────────────┼─────────────────┼─────────────────┤");
        System.out.println("│ Ubicación del semáforo  │ Dentro de clase │ En main()       │");
        System.out.println("│ Tipo de acceso          │ Métodos estáticos│ Métodos instancia│");
        System.out.println("│ Acoplamiento            │ 🔴 Alto         │ 🟢 Bajo         │");
        System.out.println("│ Testabilidad            │ 🔴 Difícil      │ 🟢 Fácil        │");
        System.out.println("│ Reutilización           │ 🔴 Limitada     │ 🟢 Alta         │");
        System.out.println("│ Configuración           │ 🔴 Fija         │ 🟢 Flexible     │");
        System.out.println("│ Simplicidad de uso      │ 🟢 Alta         │ 🟡 Media        │");
        System.out.println("│ Mantenibilidad          │ 🟡 Media        │ 🟢 Alta         │");
        System.out.println("│ Escalabilidad           │ 🔴 Baja         │ 🟢 Alta         │");
        System.out.println("│ Inyección dependencias  │ ❌ No           │ ✅ Sí           │");
        System.out.println("│ Múltiples instancias    │ ❌ No           │ ✅ Sí           │");
        System.out.println("│ Configuración dinámica  │ ❌ No           │ ✅ Sí           │");
        System.out.println("└─────────────────────────┴─────────────────┴─────────────────┘");
        
        System.out.println("\n🎯 RECOMENDACIONES DE USO:");
        System.out.println("\n📚 IMPLEMENTACIÓN A (Semáforo Interno):");
        System.out.println("   ✅ Prototipos rápidos y demos");
        System.out.println("   ✅ Aplicaciones muy simples");
        System.out.println("   ✅ Casos donde la simplicidad es crítica");
        System.out.println("   ✅ Proyectos educativos básicos");
        
        System.out.println("\n🏢 IMPLEMENTACIÓN B (Semáforo Externo):");
        System.out.println("   ✅ Aplicaciones de producción");
        System.out.println("   ✅ Sistemas complejos y escalables");
        System.out.println("   ✅ Código que requiere testing extensivo");
        System.out.println("   ✅ Proyectos con múltiples desarrolladores");
        System.out.println("   ✅ Sistemas que necesitan configuración flexible");
        
        System.out.println("\n💡 LECCIONES APRENDIDAS:");
        System.out.println("   • La inyección de dependencias mejora la testabilidad");
        System.out.println("   • El bajo acoplamiento facilita el mantenimiento");
        System.out.println("   • La flexibilidad tiene un costo en complejidad inicial");
        System.out.println("   • El diseño debe balancear simplicidad vs escalabilidad");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 CONCLUSIÓN: La Implementación B es superior para la mayoría");
        System.out.println("   de casos reales, mientras que la A es útil para casos simples.");
        System.out.println("=".repeat(80));
    }
}
