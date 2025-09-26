package tp3.actividad5;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concurrente del algoritmo SumRootN usando múltiples hilos
 * Ejecuta los cálculos en paralelo para aprovechar múltiples núcleos de CPU
 * @author PC2025
 */
public class CalculadoraConcurrente {
    
    private static final int MIN_ROOT = 1;
    private static final int MAX_ROOT = 20;
    private static final int NUM_CALCULOS = MAX_ROOT - MIN_ROOT + 1;
    
    /**
     * Ejecuta el algoritmo de forma concurrente usando un hilo por cálculo
     * @return array con los resultados de cada cálculo
     */
    public static double[] ejecutarConcurrente() {
        System.out.println("=== EJECUCIÓN CONCURRENTE ===");
        System.out.println("Algoritmo: SumRootN");
        System.out.println("Valores de root: " + MIN_ROOT + " a " + MAX_ROOT);
        System.out.println("Hilos simultáneos: " + NUM_CALCULOS);
        System.out.println("Estrategia: Un hilo por cálculo");
        System.out.println("-".repeat(50));
        
        // Crear lista de hilos
        List<HiloCalculador> hilos = new ArrayList<>();
        
        // Medir tiempo total
        long tiempoInicioTotal = System.nanoTime();
        
        // Crear e iniciar todos los hilos
        System.out.println("Iniciando " + NUM_CALCULOS + " hilos simultáneos...");
        for (int root = MIN_ROOT; root <= MAX_ROOT; root++) {
            HiloCalculador hilo = new HiloCalculador(root);
            hilos.add(hilo);
            hilo.start();
        }
        
        // Esperar que todos los hilos terminen y recopilar resultados
        double[] resultados = new double[NUM_CALCULOS];
        System.out.println("Esperando que todos los hilos terminen...");
        
        try {
            for (int i = 0; i < hilos.size(); i++) {
                HiloCalculador hilo = hilos.get(i);
                hilo.join(); // Esperar que termine
                
                if (hilo.tieneError()) {
                    System.err.println("Error en " + hilo.getName() + ": " + hilo.getError().getMessage());
                    resultados[i] = Double.NaN;
                } else {
                    resultados[i] = hilo.getResultado();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ejecución concurrente interrumpida");
            return resultados;
        }
        
        long tiempoFinTotal = System.nanoTime();
        double tiempoTotalSegundos = (tiempoFinTotal - tiempoInicioTotal) / 1_000_000_000.0;
        
        // Mostrar estadísticas de los hilos
        mostrarEstadisticasHilos(hilos);
        
        System.out.println("-".repeat(50));
        System.out.printf("✅ Ejecución concurrente completada en %.2f segundos%n", tiempoTotalSegundos);
        
        return resultados;
    }
    
    /**
     * Muestra estadísticas detalladas de todos los hilos
     * @param hilos lista de hilos ejecutados
     */
    private static void mostrarEstadisticasHilos(List<HiloCalculador> hilos) {
        System.out.println("\nESTADÍSTICAS DE HILOS:");
        
        double tiempoMin = Double.MAX_VALUE;
        double tiempoMax = Double.MIN_VALUE;
        double tiempoTotal = 0;
        int hilosCompletados = 0;
        
        for (HiloCalculador hilo : hilos) {
            if (hilo.isCompletado() && !hilo.tieneError()) {
                double tiempo = hilo.getTiempoEjecucionSegundos();
                tiempoMin = Math.min(tiempoMin, tiempo);
                tiempoMax = Math.max(tiempoMax, tiempo);
                tiempoTotal += tiempo;
                hilosCompletados++;
                
                System.out.printf("Root %2d: %.2f segundos (%.2e)%n", 
                                 hilo.getRoot(), tiempo, hilo.getResultado());
            }
        }
        
        if (hilosCompletados > 0) {
            double tiempoPromedio = tiempoTotal / hilosCompletados;
            System.out.printf("Tiempo mínimo: %.2f segundos%n", tiempoMin);
            System.out.printf("Tiempo máximo: %.2f segundos%n", tiempoMax);
            System.out.printf("Tiempo promedio: %.2f segundos%n", tiempoPromedio);
            System.out.printf("Variación: %.1f%%%n", ((tiempoMax - tiempoMin) / tiempoPromedio) * 100);
        }
    }
    
    /**
     * Ejecuta múltiples iteraciones para obtener estadísticas más precisas
     * @param numIteraciones número de veces que ejecutar el conjunto completo
     * @return tiempo promedio en segundos
     */
    public static double ejecutarConEstadisticas(int numIteraciones) {
        System.out.println("=== ANÁLISIS ESTADÍSTICO CONCURRENTE ===");
        System.out.println("Iteraciones del análisis: " + numIteraciones);
        System.out.println("-".repeat(50));
        
        double[] tiempos = new double[numIteraciones];
        
        for (int iter = 0; iter < numIteraciones; iter++) {
            System.out.println("Iteración " + (iter + 1) + "/" + numIteraciones);
            
            long tiempoInicio = System.nanoTime();
            
            // Crear y ejecutar hilos
            List<HiloCalculador> hilos = new ArrayList<>();
            for (int root = MIN_ROOT; root <= MAX_ROOT; root++) {
                HiloCalculador hilo = new HiloCalculador(root);
                hilos.add(hilo);
                hilo.start();
            }
            
            // Esperar que terminen todos
            try {
                for (HiloCalculador hilo : hilos) {
                    hilo.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            long tiempoFin = System.nanoTime();
            tiempos[iter] = (tiempoFin - tiempoInicio) / 1_000_000_000.0;
            
            System.out.printf("Tiempo: %.2f segundos%n", tiempos[iter]);
        }
        
        // Calcular estadísticas
        double suma = 0;
        double min = tiempos[0];
        double max = tiempos[0];
        
        for (double tiempo : tiempos) {
            suma += tiempo;
            if (tiempo < min) min = tiempo;
            if (tiempo > max) max = tiempo;
        }
        
        double promedio = suma / numIteraciones;
        
        // Calcular desviación estándar
        double sumaCuadrados = 0;
        for (double tiempo : tiempos) {
            sumaCuadrados += Math.pow(tiempo - promedio, 2);
        }
        double desviacionEstandar = Math.sqrt(sumaCuadrados / numIteraciones);
        
        System.out.println("-".repeat(50));
        System.out.println("ESTADÍSTICAS CONCURRENTES:");
        System.out.printf("Tiempo promedio: %.2f ± %.2f segundos%n", promedio, desviacionEstandar);
        System.out.printf("Tiempo mínimo: %.2f segundos%n", min);
        System.out.printf("Tiempo máximo: %.2f segundos%n", max);
        System.out.printf("Variabilidad: %.1f%%%n", (desviacionEstandar / promedio) * 100);
        
        return promedio;
    }
    
    /**
     * Ejecuta con un número limitado de hilos (pool de hilos)
     * @param maxHilosSimultaneos número máximo de hilos simultáneos
     * @return array con los resultados
     */
    public static double[] ejecutarConPoolLimitado(int maxHilosSimultaneos) {
        System.out.println("=== EJECUCIÓN CON POOL LIMITADO ===");
        System.out.println("Máximo hilos simultáneos: " + maxHilosSimultaneos);
        System.out.println("-".repeat(50));
        
        double[] resultados = new double[NUM_CALCULOS];
        List<HiloCalculador> hilosActivos = new ArrayList<>();
        int siguienteRoot = MIN_ROOT;
        
        long tiempoInicio = System.nanoTime();
        
        while (siguienteRoot <= MAX_ROOT || !hilosActivos.isEmpty()) {
            // Iniciar nuevos hilos si hay espacio y trabajo pendiente
            while (hilosActivos.size() < maxHilosSimultaneos && siguienteRoot <= MAX_ROOT) {
                HiloCalculador hilo = new HiloCalculador(siguienteRoot);
                hilosActivos.add(hilo);
                hilo.start();
                siguienteRoot++;
            }
            
            // Verificar hilos completados
            for (int i = hilosActivos.size() - 1; i >= 0; i--) {
                HiloCalculador hilo = hilosActivos.get(i);
                if (!hilo.isAlive()) {
                    try {
                        hilo.join();
                        if (!hilo.tieneError()) {
                            resultados[hilo.getRoot() - MIN_ROOT] = hilo.getResultado();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    hilosActivos.remove(i);
                }
            }
            
            // Pequeña pausa para evitar busy waiting
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        long tiempoFin = System.nanoTime();
        double tiempoTotal = (tiempoFin - tiempoInicio) / 1_000_000_000.0;
        
        System.out.printf("✅ Ejecución con pool limitado completada en %.2f segundos%n", tiempoTotal);
        
        return resultados;
    }
    
    /**
     * Proporciona información sobre el uso de CPU durante ejecución concurrente
     */
    public static void informacionCPU() {
        System.out.println("\n=== INFORMACIÓN SOBRE USO DE CPU ===");
        System.out.println("Durante la ejecución concurrente:");
        System.out.println("• MÚLTIPLES núcleos de CPU serán utilizados al 100%");
        System.out.println("• La carga se distribuirá entre los núcleos disponibles");
        System.out.println("• Abra el Administrador de Tareas > Rendimiento > CPU");
        System.out.println("• Observe el gráfico de utilización por núcleo");
        System.out.println("• Debería ver actividad en múltiples núcleos simultáneamente");
        
        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("• Núcleos disponibles en este sistema: " + nucleos);
        System.out.println("• Hilos a crear: " + NUM_CALCULOS);
        System.out.println("• Utilización teórica máxima: 100% (todos los núcleos)");
        
        if (NUM_CALCULOS > nucleos) {
            System.out.println("• Nota: Hay más hilos que núcleos, habrá competencia por recursos");
        }
    }
    
    /**
     * Método principal para ejecutar la versión concurrente
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 5 - CALCULADORA CONCURRENTE");
        System.out.println("=".repeat(60));
        
        // Mostrar información sobre CPU
        informacionCPU();
        
        System.out.println("\nPresione Enter para comenzar la ejecución concurrente...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignorar
        }
        
        // Ejecutar cálculo concurrente
        double[] resultados = ejecutarConcurrente();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RECOMENDACIONES:");
        System.out.println("1. Compare el tiempo con la versión secuencial");
        System.out.println("2. Observe la utilización de múltiples núcleos en el Administrador de Tareas");
        System.out.println("3. Ejecute ComparadorRendimiento para análisis completo");
        System.out.println("=".repeat(60));
    }
}
