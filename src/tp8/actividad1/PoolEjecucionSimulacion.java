package tp8.actividad1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 🔄 PoolEjecucionSimulacion - Simulación de Pool de Hilos con ExecutorService
 * 
 * Esta clase demuestra el uso de Executor Framework con un pool fijo de 3 threads
 * para ejecutar 10 tareas de cálculo intensivo concurrentemente.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class PoolEjecucionSimulacion {
    
    // ⚙️ Configuración del pool
    private static final int POOL_SIZE = 3;
    private static final int NUMERO_TAREAS = 10;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🔄 POOL DE EJECUCIÓN CON EXECUTOR FRAMEWORK");
        System.out.println(SEPARADOR);
        
        PoolEjecucionSimulacion simulacion = new PoolEjecucionSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar simulación con pool
        ResultadoSimulacion resultado = simulacion.ejecutarConPool();
        
        // 📊 Analizar rendimiento
        simulacion.analizarRendimiento(resultado);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ SIMULACIÓN COMPLETADA");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información inicial
     */
    private void mostrarInformacionInicial() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Ejecutar tareas con pool fijo de threads");
        System.out.println("🔬 Framework: Java ExecutorService");
        System.out.println();
        
        System.out.println("⚙️ CONFIGURACIÓN:");
        System.out.printf("   🔢 Pool size: %d threads%n", POOL_SIZE);
        System.out.printf("   📋 Número de tareas: %d%n", NUMERO_TAREAS);
        System.out.printf("   🔄 Iteraciones por tarea: 10,000,000%n");
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar tareas con pool de threads
     * 
     * @return Resultado de la simulación con tiempos
     */
    private ResultadoSimulacion ejecutarConPool() {
        System.out.println("🚀 INICIANDO EJECUCIÓN CON POOL...\n");
        
        long tiempoInicio = System.currentTimeMillis();
        
        // 🏗️ Crear ExecutorService con pool fijo
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        
        // 📋 Lista para almacenar tareas
        List<TareaCalculoRaiz> tareas = new ArrayList<>();
        
        try {
            // 📤 Crear y enviar tareas al pool
            for (int i = 1; i <= NUMERO_TAREAS; i++) {
                TareaCalculoRaiz tarea = new TareaCalculoRaiz(i, i); // root = numeroTarea
                tareas.add(tarea);
                executor.submit(tarea);
            }
            
            // 🛑 Iniciar shutdown ordenado (no acepta más tareas)
            executor.shutdown();
            System.out.println("📢 Pool cerrado - No se aceptan más tareas\n");
            
            // ⏳ Esperar a que todas las tareas terminen
            boolean terminado = executor.awaitTermination(5, TimeUnit.MINUTES);
            
            if (terminado) {
                System.out.println("\n✅ TODAS LAS TAREAS COMPLETADAS\n");
            } else {
                System.out.println("\n⚠️ Timeout - Algunas tareas no terminaron\n");
            }
            
        } catch (InterruptedException e) {
            System.err.println("❌ Ejecución interrumpida: " + e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
        
        // 📊 Crear resultado
        ResultadoSimulacion resultado = new ResultadoSimulacion();
        resultado.tiempoTotalPool = tiempoTotal;
        resultado.tareas = tareas;
        resultado.poolSize = POOL_SIZE;
        resultado.numeroTareas = NUMERO_TAREAS;
        
        System.out.printf("⏱️ TIEMPO TOTAL CON POOL: %dms (%.2fs)%n", tiempoTotal, tiempoTotal / 1000.0);
        
        return resultado;
    }
    
    /**
     * 📊 Analizar rendimiento del pool
     * 
     * @param resultado Resultado de la simulación
     */
    private void analizarRendimiento(ResultadoSimulacion resultado) {
        System.out.println("\n📊 === ANÁLISIS DE RENDIMIENTO ===\n");
        
        // 📋 Calcular estadísticas de tareas
        long tiempoMinTarea = Long.MAX_VALUE;
        long tiempoMaxTarea = 0;
        long tiempoTotalTareas = 0;
        
        for (TareaCalculoRaiz tarea : resultado.tareas) {
            long tiempo = tarea.getTiempoEjecucion();
            tiempoTotalTareas += tiempo;
            if (tiempo < tiempoMinTarea) tiempoMinTarea = tiempo;
            if (tiempo > tiempoMaxTarea) tiempoMaxTarea = tiempo;
        }
        
        double tiempoPromedioTarea = (double) tiempoTotalTareas / resultado.numeroTareas;
        
        // 🔢 Calcular tiempo secuencial estimado
        long tiempoSecuencialEstimado = tiempoTotalTareas;
        
        // 🚀 Calcular speedup y eficiencia
        double speedup = (double) tiempoSecuencialEstimado / resultado.tiempoTotalPool;
        double eficiencia = (speedup / resultado.poolSize) * 100;
        double mejora = ((tiempoSecuencialEstimado - resultado.tiempoTotalPool) * 100.0) / tiempoSecuencialEstimado;
        
        // 📊 Mostrar resultados
        System.out.println("⏱️ TIEMPOS DE EJECUCIÓN:");
        System.out.printf("   Pool (%d threads): %.2fs%n", resultado.poolSize, resultado.tiempoTotalPool / 1000.0);
        System.out.printf("   Secuencial (estimado): %.2fs%n", tiempoSecuencialEstimado / 1000.0);
        System.out.println();
        
        System.out.println("📊 ESTADÍSTICAS DE TAREAS:");
        System.out.printf("   Tiempo mínimo: %dms%n", tiempoMinTarea);
        System.out.printf("   Tiempo máximo: %dms%n", tiempoMaxTarea);
        System.out.printf("   Tiempo promedio: %.2fms%n", tiempoPromedioTarea);
        System.out.println();
        
        System.out.println("🚀 MÉTRICAS DE PARALELIZACIÓN:");
        System.out.printf("   Speedup: %.2fx%n", speedup);
        System.out.printf("   Eficiencia: %.1f%%%n", eficiencia);
        System.out.printf("   Mejora: %.1f%% más rápido%n", mejora);
        System.out.println();
        
        // 📋 Mostrar tabla de tareas
        mostrarTablaTareas(resultado.tareas);
        
        // 🎓 Conclusiones
        mostrarConclusiones(speedup, eficiencia, resultado.poolSize);
    }
    
    /**
     * 📋 Mostrar tabla de tareas ejecutadas
     * 
     * @param tareas Lista de tareas
     */
    private void mostrarTablaTareas(List<TareaCalculoRaiz> tareas) {
        System.out.println("📋 TABLA DE TAREAS EJECUTADAS:");
        System.out.printf("%-10s %-10s %-20s %-15s %-20s%n", 
                         "TAREA", "ROOT", "RESULTADO", "TIEMPO(ms)", "THREAD");
        System.out.println("-".repeat(80));
        
        for (TareaCalculoRaiz tarea : tareas) {
            System.out.printf("%-10s %-10d %-20.6e %-15d %-20s%n",
                             "#" + tarea.getNumeroTarea(),
                             tarea.getRoot(),
                             tarea.getResultado(),
                             tarea.getTiempoEjecucion(),
                             tarea.getNombreThread());
        }
        
        System.out.println("-".repeat(80));
        System.out.println();
    }
    
    /**
     * 🎓 Mostrar conclusiones del análisis
     * 
     * @param speedup Speedup obtenido
     * @param eficiencia Eficiencia del pool
     * @param poolSize Tamaño del pool
     */
    private void mostrarConclusiones(double speedup, double eficiencia, int poolSize) {
        System.out.println("🎓 CONCLUSIONES:");
        
        if (speedup >= poolSize * 0.8) {
            System.out.printf("   🏆 EXCELENTE: Speedup de %.2fx muy cercano al ideal (%d)%n", speedup, poolSize);
        } else if (speedup >= poolSize * 0.6) {
            System.out.printf("   ✅ BUENO: Speedup de %.2fx aceptable para pool de %d threads%n", speedup, poolSize);
        } else {
            System.out.printf("   ⚠️ MEJORABLE: Speedup de %.2fx por debajo del esperado%n", speedup);
        }
        
        if (eficiencia >= 80) {
            System.out.printf("   🏆 Alta eficiencia (%.1f%%) - Excelente uso de threads%n", eficiencia);
        } else if (eficiencia >= 60) {
            System.out.printf("   ✅ Buena eficiencia (%.1f%%) - Uso aceptable de threads%n", eficiencia);
        } else {
            System.out.printf("   ⚠️ Eficiencia baja (%.1f%%) - Threads subutilizados%n", eficiencia);
        }
        
        System.out.println("\n💡 VENTAJAS DE EXECUTOR FRAMEWORK:");
        System.out.println("   ✅ Gestión automática de threads (creación, reutilización)");
        System.out.println("   ✅ Pool limitado evita crear threads ilimitados");
        System.out.println("   ✅ Cola de tareas maneja trabajo pendiente eficientemente");
        System.out.println("   ✅ API simple: submit(), shutdown(), awaitTermination()");
        System.out.println("   ✅ Reutilización de threads reduce overhead");
    }
    
    /**
     * 📊 Clase interna para resultado de simulación
     */
    private static class ResultadoSimulacion {
        long tiempoTotalPool;
        List<TareaCalculoRaiz> tareas;
        int poolSize;
        int numeroTareas;
        
        @Override
        public String toString() {
            return String.format("ResultadoSimulacion{pool=%d, tareas=%d, tiempo=%dms}", 
                               poolSize, numeroTareas, tiempoTotalPool);
        }
    }
}
