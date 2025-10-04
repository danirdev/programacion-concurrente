package tp6.actividad2.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para la simulación del Semáforo Interno - Implementación A.
 * Crea 10 hilos que utilizan un semáforo general inicializado en 3.
 * Cada hilo duerme por 5 segundos y muestra el orden de ejecución.
 */
public class SemaforoInternoSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("        SEMÁFORO GENERAL - IMPLEMENTACIÓN A (INTERNO)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación con semáforo interno");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: A - Semáforo definido dentro de la clase");
        System.out.println("• Valor inicial del semáforo: 3");
        System.out.println("• Número de hilos: 10");
        System.out.println("• Tiempo de sueño por hilo: 5 segundos");
        System.out.println("• Tipo de acceso: Métodos estáticos");
        System.out.println("• Acoplamiento: Alto (semáforo interno)");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Mostrar configuración del semáforo
        System.out.println(SemaforoInterno.getConfiguracion());
        System.out.println();
        
        // Registrar tiempo de inicio
        long tiempoInicioTotal = System.currentTimeMillis();
        
        // Crear lista de hilos
        List<HiloSemaforoInterno> hilos = new ArrayList<>();
        
        // Crear 10 hilos
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🔧 Creando 10 hilos...");
        
        for (int i = 1; i <= 10; i++) {
            String nombreHilo = "Hilo-" + String.format("%02d", i);
            HiloSemaforoInterno hilo = new HiloSemaforoInterno(nombreHilo);
            hilos.add(hilo);
            System.out.println("   ✅ " + nombreHilo + " creado");
        }
        
        System.out.println();
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🚀 INICIANDO TODOS LOS HILOS");
        System.out.println("Estado inicial: " + SemaforoInterno.getInfoResumida());
        System.out.println("=".repeat(50));
        
        // Iniciar todos los hilos
        for (HiloSemaforoInterno hilo : hilos) {
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
                    SemaforoInterno.mostrarEstado();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor-Estadisticas");
        
        monitor.start();
        
        System.out.println("⏳ Esperando que todos los hilos completen su ejecución...");
        System.out.println("📊 Observar el orden de ejecución y el comportamiento del semáforo");
        System.out.println();
        
        // Esperar a que todos los hilos terminen
        for (HiloSemaforoInterno hilo : hilos) {
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
        mostrarEstadisticasFinales(hilos, tiempoTotal);
        
        // Demostrar características de la Implementación A
        demostrarCaracteristicasImplementacionA();
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(List<HiloSemaforoInterno> hilos, long tiempoTotal) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("           ESTADÍSTICAS FINALES - IMPLEMENTACIÓN A");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del semáforo
        int[] statsSemaforo = SemaforoInterno.getEstadisticas();
        System.out.println("🔒 ESTADÍSTICAS DEL SEMÁFORO INTERNO:");
        System.out.println("   Valor final: " + statsSemaforo[0]);
        System.out.println("   Total adquisiciones: " + statsSemaforo[1]);
        System.out.println("   Total liberaciones: " + statsSemaforo[2]);
        System.out.println("   Hilos esperando al final: " + statsSemaforo[3]);
        System.out.println("   Balance (A-L): " + (statsSemaforo[1] - statsSemaforo[2]));
        System.out.println("   Estado final: " + (statsSemaforo[0] == 3 ? "✅ RESTAURADO" : "⚠️ INCONSISTENTE"));
        System.out.println();
        
        // Estadísticas de hilos
        int completados = 0;
        int esperaron = 0;
        long tiempoEsperaTotal = 0;
        long tiempoEsperaMaximo = 0;
        long tiempoEsperaMinimo = Long.MAX_VALUE;
        
        System.out.println("🧵 ESTADÍSTICAS DE HILOS:");
        for (HiloSemaforoInterno hilo : hilos) {
            if (hilo.isCompletoEjecucion()) {
                completados++;
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
        
        // Detalles por hilo
        System.out.println("📋 DETALLES POR HILO:");
        for (HiloSemaforoInterno hilo : hilos) {
            System.out.println("   " + hilo.getEstadisticasDetalladas());
        }
        System.out.println();
        
        // Análisis del comportamiento del semáforo
        System.out.println("🔍 ANÁLISIS DEL COMPORTAMIENTO:");
        System.out.println("   Máximo hilos simultáneos: 3 (limitado por semáforo)");
        System.out.println("   Hilos que accedieron inmediatamente: " + (hilos.size() - esperaron));
        System.out.println("   Hilos que tuvieron que esperar: " + esperaron);
        
        if (esperaron == 7) { // 10 - 3 = 7 hilos deberían esperar
            System.out.println("   Comportamiento: ✅ CORRECTO (7 hilos esperaron como se esperaba)");
        } else {
            System.out.println("   Comportamiento: ⚠️ INESPERADO (" + esperaron + " hilos esperaron)");
        }
    }
    
    /**
     * Demuestra las características específicas de la Implementación A
     */
    private static void demostrarCaracteristicasImplementacionA() {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         CARACTERÍSTICAS DE LA IMPLEMENTACIÓN A");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🏗️ ARQUITECTURA:");
        System.out.println("   ✅ Semáforo definido dentro de la clase SemaforoInterno");
        System.out.println("   ✅ Acceso mediante métodos estáticos");
        System.out.println("   ✅ Singleton implícito (una sola instancia global)");
        System.out.println("   ✅ Inicialización automática en valor 3");
        
        System.out.println("\n👍 VENTAJAS:");
        System.out.println("   ✅ Simplicidad de uso - No requiere pasar parámetros");
        System.out.println("   ✅ Acceso global automático desde cualquier hilo");
        System.out.println("   ✅ No hay riesgo de perder la referencia al semáforo");
        System.out.println("   ✅ Menos código de configuración en el main");
        
        System.out.println("\n👎 DESVENTAJAS:");
        System.out.println("   ❌ Alto acoplamiento - Hilos dependen de clase específica");
        System.out.println("   ❌ Difícil de testear - No se puede inyectar mock");
        System.out.println("   ❌ No reutilizable - Valor fijo en 3");
        System.out.println("   ❌ Configuración inflexible - No se puede cambiar en runtime");
        System.out.println("   ❌ Violación del principio de inversión de dependencias");
        
        System.out.println("\n🔧 CASOS DE USO APROPIADOS:");
        System.out.println("   ✅ Aplicaciones simples con un solo semáforo");
        System.out.println("   ✅ Prototipos rápidos y demos");
        System.out.println("   ✅ Sistemas donde la configuración nunca cambia");
        System.out.println("   ✅ Cuando la simplicidad es más importante que la flexibilidad");
        
        System.out.println("\n❌ CASOS DE USO NO RECOMENDADOS:");
        System.out.println("   ❌ Aplicaciones que requieren múltiples semáforos");
        System.out.println("   ❌ Sistemas que necesitan configuración dinámica");
        System.out.println("   ❌ Código que requiere testing unitario extensivo");
        System.out.println("   ❌ Aplicaciones con diferentes valores de semáforo");
        
        System.out.println("\n📊 MÉTRICAS DE CALIDAD:");
        System.out.println("   Acoplamiento:     🔴 Alto");
        System.out.println("   Cohesión:         🟢 Alta");
        System.out.println("   Testabilidad:     🔴 Baja");
        System.out.println("   Reutilización:    🔴 Baja");
        System.out.println("   Mantenibilidad:   🟡 Media");
        System.out.println("   Simplicidad:      🟢 Alta");
        
        System.out.println("\n💡 RECOMENDACIONES:");
        System.out.println("   • Usar solo para aplicaciones simples y prototipos");
        System.out.println("   • Considerar Implementación B para sistemas más complejos");
        System.out.println("   • Evaluar el uso de java.util.concurrent.Semaphore para producción");
        System.out.println("   • Documentar claramente la dependencia estática");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🎯 CONCLUSIÓN: La Implementación A es ideal para casos simples");
        System.out.println("   donde la simplicidad es más importante que la flexibilidad.");
        System.out.println("=".repeat(80));
    }
}
