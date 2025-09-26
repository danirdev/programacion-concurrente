package tp3.actividad5;

/**
 * Implementación secuencial del algoritmo SumRootN
 * Ejecuta los cálculos uno tras otro para medir rendimiento base
 * @author PC2025
 */
public class CalculadoraSecuencial {
    
    private static final int ITERACIONES = 10000000;
    private static final int MIN_ROOT = 1;
    private static final int MAX_ROOT = 20;
    
    /**
     * Implementación del algoritmo SumRootN según el enunciado
     * @param root valor de la raíz (denominador del exponente)
     * @return suma calculada
     */
    public static double SumRootN(int root) {
        double result = 0;
        for (int i = 0; i < ITERACIONES; i++) {
            result += Math.exp(Math.log(i) / root);
        }
        return result;
    }
    
    /**
     * Ejecuta el algoritmo de forma secuencial para todos los valores de root
     * @return array con los resultados de cada cálculo
     */
    public static double[] ejecutarSecuencial() {
        System.out.println("=== EJECUCIÓN SECUENCIAL ===");
        System.out.println("Algoritmo: SumRootN");
        System.out.println("Iteraciones por cálculo: " + String.format("%,d", ITERACIONES));
        System.out.println("Valores de root: " + MIN_ROOT + " a " + MAX_ROOT);
        System.out.println("Total de cálculos: " + (MAX_ROOT - MIN_ROOT + 1));
        System.out.println("-".repeat(50));
        
        double[] resultados = new double[MAX_ROOT - MIN_ROOT + 1];
        
        // Medir tiempo total
        long tiempoInicioTotal = System.nanoTime();
        
        // Ejecutar cada cálculo secuencialmente
        for (int root = MIN_ROOT; root <= MAX_ROOT; root++) {
            System.out.print("Calculando SumRootN(" + root + ")... ");
            
            long tiempoInicio = System.nanoTime();
            double resultado = SumRootN(root);
            long tiempoFin = System.nanoTime();
            
            double tiempoSegundos = (tiempoFin - tiempoInicio) / 1_000_000_000.0;
            resultados[root - MIN_ROOT] = resultado;
            
            System.out.printf("%.2f segundos (resultado: %.2e)%n", tiempoSegundos, resultado);
        }
        
        long tiempoFinTotal = System.nanoTime();
        double tiempoTotalSegundos = (tiempoFinTotal - tiempoInicioTotal) / 1_000_000_000.0;
        
        System.out.println("-".repeat(50));
        System.out.printf("✅ Ejecución secuencial completada en %.2f segundos%n", tiempoTotalSegundos);
        System.out.printf("Tiempo promedio por cálculo: %.2f segundos%n", 
                         tiempoTotalSegundos / (MAX_ROOT - MIN_ROOT + 1));
        
        return resultados;
    }
    
    /**
     * Ejecuta múltiples iteraciones para obtener estadísticas más precisas
     * @param numIteraciones número de veces que ejecutar el conjunto completo
     * @return tiempo promedio en segundos
     */
    public static double ejecutarConEstadisticas(int numIteraciones) {
        System.out.println("=== ANÁLISIS ESTADÍSTICO SECUENCIAL ===");
        System.out.println("Iteraciones del análisis: " + numIteraciones);
        System.out.println("-".repeat(50));
        
        double[] tiempos = new double[numIteraciones];
        
        for (int iter = 0; iter < numIteraciones; iter++) {
            System.out.println("Iteración " + (iter + 1) + "/" + numIteraciones);
            
            long tiempoInicio = System.nanoTime();
            
            // Ejecutar todos los cálculos
            for (int root = MIN_ROOT; root <= MAX_ROOT; root++) {
                SumRootN(root);
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
        System.out.println("ESTADÍSTICAS SECUENCIALES:");
        System.out.printf("Tiempo promedio: %.2f ± %.2f segundos%n", promedio, desviacionEstandar);
        System.out.printf("Tiempo mínimo: %.2f segundos%n", min);
        System.out.printf("Tiempo máximo: %.2f segundos%n", max);
        System.out.printf("Variabilidad: %.1f%%%n", (desviacionEstandar / promedio) * 100);
        
        return promedio;
    }
    
    /**
     * Ejecuta un solo cálculo para prueba rápida
     * @param root valor de root a probar
     */
    public static void pruebaRapida(int root) {
        System.out.println("=== PRUEBA RÁPIDA ===");
        System.out.println("Calculando SumRootN(" + root + ") con " + 
                          String.format("%,d", ITERACIONES) + " iteraciones");
        
        long tiempoInicio = System.nanoTime();
        double resultado = SumRootN(root);
        long tiempoFin = System.nanoTime();
        
        double tiempoSegundos = (tiempoFin - tiempoInicio) / 1_000_000_000.0;
        
        System.out.printf("Resultado: %.6e%n", resultado);
        System.out.printf("Tiempo: %.3f segundos%n", tiempoSegundos);
        System.out.printf("Operaciones por segundo: %.0f%n", ITERACIONES / tiempoSegundos);
    }
    
    /**
     * Proporciona información sobre el uso de CPU durante ejecución secuencial
     */
    public static void informacionCPU() {
        System.out.println("\n=== INFORMACIÓN SOBRE USO DE CPU ===");
        System.out.println("Durante la ejecución secuencial:");
        System.out.println("• Solo UN núcleo de CPU será utilizado al 100%");
        System.out.println("• Los demás núcleos permanecerán inactivos");
        System.out.println("• Abra el Administrador de Tareas > Rendimiento > CPU");
        System.out.println("• Observe el gráfico de utilización por núcleo");
        System.out.println("• Debería ver actividad en solo uno de los núcleos");
        
        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("• Núcleos disponibles en este sistema: " + nucleos);
        System.out.println("• Utilización teórica máxima: " + String.format("%.1f%%", 100.0 / nucleos));
    }
    
    /**
     * Método principal para ejecutar la versión secuencial
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 5 - CALCULADORA SECUENCIAL");
        System.out.println("=".repeat(60));
        
        // Mostrar información sobre CPU
        informacionCPU();
        
        System.out.println("\nPresione Enter para comenzar la ejecución secuencial...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignorar
        }
        
        // Ejecutar cálculo secuencial
        double[] resultados = ejecutarSecuencial();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RECOMENDACIONES:");
        System.out.println("1. Revise el Administrador de Tareas durante la ejecución");
        System.out.println("2. Compare con la versión concurrente (CalculadoraConcurrente)");
        System.out.println("3. Observe la diferencia en utilización de CPU");
        System.out.println("=".repeat(60));
    }
}
