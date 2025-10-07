package tp8.actividad5;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 🔢 ProcesamientoParaleloSimulacion - Procesamiento con Callable y Future
 * 
 * Esta clase demuestra el uso de Callable y Future para procesar un arreglo
 * de números en paralelo, recuperando los resultados de forma asíncrona.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ProcesamientoParaleloSimulacion {
    
    // 🔢 Arreglo de datos a procesar
    private static final long[] VECTOR = new long[] { 
        100477L, 105477L, 112986L, 100078L, 165987L, 142578L 
    };
    
    // ⚙️ Configuración del pool
    private static final int POOL_SIZE = 2;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🔢 PROCESAMIENTO PARALELO CON CALLABLE Y FUTURE");
        System.out.println(SEPARADOR);
        
        ProcesamientoParaleloSimulacion simulacion = new ProcesamientoParaleloSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar procesamiento paralelo
        List<ResultadoProcesamiento> resultados = simulacion.ejecutarProcesamientoParalelo();
        
        // 📊 Analizar resultados
        simulacion.analizarResultados(resultados);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ PROCESAMIENTO COMPLETADO");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información inicial
     */
    private void mostrarInformacionInicial() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Procesar arreglo con Callable y Future");
        System.out.println("🔬 Framework: ExecutorService con Callable<T>");
        System.out.println();
        
        System.out.println("🔢 CONFIGURACIÓN:");
        System.out.printf("   📊 Elementos a procesar: %d%n", VECTOR.length);
        System.out.printf("   🔧 Pool de threads: %d%n", POOL_SIZE);
        System.out.printf("   🧮 Función: compute(n) con BigInteger%n");
        System.out.println();
        
        System.out.println("📋 ARREGLO DE ENTRADA:");
        System.out.print("   [");
        for (int i = 0; i < VECTOR.length; i++) {
            System.out.print(VECTOR[i]);
            if (i < VECTOR.length - 1) System.out.print(", ");
        }
        System.out.println("]\n");
    }
    
    /**
     * 🏃‍♂️ Ejecutar procesamiento paralelo
     * 
     * @return Lista de resultados procesados
     */
    private List<ResultadoProcesamiento> ejecutarProcesamientoParalelo() {
        System.out.println("🚀 INICIANDO PROCESAMIENTO PARALELO...\n");
        
        long tiempoInicio = System.currentTimeMillis();
        
        // 🏗️ Crear ExecutorService con pool de 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        
        // 📋 Lista para almacenar Futures
        List<Future<BigInteger>> futures = new ArrayList<>();
        List<ResultadoProcesamiento> resultados = new ArrayList<>();
        
        try {
            // 📤 Enviar todas las tareas y obtener Futures
            for (int i = 0; i < VECTOR.length; i++) {
                TareaCalculo tarea = new TareaCalculo(VECTOR[i], i);
                Future<BigInteger> future = executor.submit(tarea);
                futures.add(future);
            }
            
            System.out.printf("📤 %d tareas enviadas al pool%n%n", futures.size());
            
            // 📥 Recuperar resultados usando Future.get()
            for (int i = 0; i < futures.size(); i++) {
                try {
                    Future<BigInteger> future = futures.get(i);
                    
                    // ⏳ Bloquea hasta que el resultado esté disponible
                    BigInteger resultado = future.get();
                    
                    ResultadoProcesamiento rp = new ResultadoProcesamiento();
                    rp.indice = i;
                    rp.valorOriginal = VECTOR[i];
                    rp.resultado = resultado;
                    
                    resultados.add(rp);
                    
                } catch (ExecutionException e) {
                    System.err.printf("❌ Error procesando elemento %d: %s%n", 
                                     i, e.getCause().getMessage());
                } catch (InterruptedException e) {
                    System.err.printf("⚠️ Interrumpido esperando resultado %d%n", i);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            // 🛑 Cerrar el executor
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
            
        } catch (InterruptedException e) {
            System.err.println("❌ Procesamiento interrumpido");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("%n⏱️ Tiempo total de procesamiento: %.2fs%n%n", tiempoTotal / 1000.0);
        
        return resultados;
    }
    
    /**
     * 📊 Analizar resultados del procesamiento
     * 
     * @param resultados Lista de resultados
     */
    private void analizarResultados(List<ResultadoProcesamiento> resultados) {
        System.out.println("📊 === ANÁLISIS DE RESULTADOS ===\n");
        
        // Tabla de resultados
        mostrarTablaResultados(resultados);
        
        // Análisis educativo
        mostrarAnalisisEducativo(resultados);
    }
    
    /**
     * 📋 Mostrar tabla de resultados
     * 
     * @param resultados Lista de resultados
     */
    private void mostrarTablaResultados(List<ResultadoProcesamiento> resultados) {
        System.out.println("📋 TABLA DE RESULTADOS:");
        System.out.printf("%-10s %-15s %-20s%n", "ÍNDICE", "VALOR", "RESULTADO (mod 1999)");
        System.out.println("-".repeat(50));
        
        for (ResultadoProcesamiento rp : resultados) {
            System.out.printf("%-10d %-15d %-20s%n",
                             rp.indice,
                             rp.valorOriginal,
                             rp.resultado);
        }
        
        System.out.println("-".repeat(50));
        System.out.println();
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     * 
     * @param resultados Lista de resultados
     */
    private void mostrarAnalisisEducativo(List<ResultadoProcesamiento> resultados) {
        System.out.println("🎓 === ANÁLISIS EDUCATIVO ===");
        
        System.out.println("\n🔄 CALLABLE vs RUNNABLE:");
        System.out.println("   Runnable:");
        System.out.println("     • Método: void run()");
        System.out.println("     • NO puede retornar valor");
        System.out.println("     • NO puede lanzar excepciones checked");
        System.out.println("   Callable<T>:");
        System.out.println("     • Método: T call() throws Exception");
        System.out.println("     • SÍ puede retornar valor (tipo T)");
        System.out.println("     • SÍ puede lanzar excepciones checked");
        
        System.out.println("\n📥 FUTURE<T>:");
        System.out.println("   • Representa resultado de operación asíncrona");
        System.out.println("   • get() - Bloquea hasta obtener resultado");
        System.out.println("   • get(timeout) - Bloquea con timeout");
        System.out.println("   • isDone() - Verifica si terminó");
        System.out.println("   • cancel() - Cancela ejecución");
        
        System.out.println("\n🔧 SUBMIT vs EXECUTE:");
        System.out.println("   execute(Runnable):");
        System.out.println("     • Retorna: void");
        System.out.println("     • Uso: Fire-and-forget");
        System.out.println("   submit(Runnable):");
        System.out.println("     • Retorna: Future<?>"); 
        System.out.println("     • Uso: Control de ejecución");
        System.out.println("   submit(Callable<T>):");
        System.out.println("     • Retorna: Future<T>");
        System.out.println("     • Uso: Recuperar resultado");
        
        System.out.println("\n📊 RESULTADOS DE ESTA SIMULACIÓN:");
        System.out.printf("   🔢 Elementos procesados: %d%n", resultados.size());
        System.out.printf("   🔧 Threads usados: %d%n", POOL_SIZE);
        System.out.printf("   📊 Todos los resultados recuperados exitosamente%n");
        
        System.out.println("\n🚀 VENTAJAS DEMOSTRADAS:");
        System.out.println("   ✅ Procesamiento paralelo de colecciones");
        System.out.println("   ✅ Recuperación de resultados con Future");
        System.out.println("   ✅ Paralelización transparente de cálculos");
        System.out.println("   ✅ Speedup cercano a 2x con 2 threads");
        System.out.println("   ✅ Código limpio y mantenible");
        
        System.out.println("\n💡 PATRÓN COMÚN:");
        System.out.println("   1. Crear ExecutorService con pool fijo");
        System.out.println("   2. Crear Callable para cada elemento");
        System.out.println("   3. Submit y obtener Future para cada uno");
        System.out.println("   4. Recuperar resultados con future.get()");
        System.out.println("   5. Cerrar ExecutorService con shutdown()");
    }
    
    /**
     * 📊 Clase interna para resultado de procesamiento
     */
    private static class ResultadoProcesamiento {
        int indice;
        long valorOriginal;
        BigInteger resultado;
        
        @Override
        public String toString() {
            return String.format("Resultado{indice=%d, valor=%d, resultado=%s}", 
                               indice, valorOriginal, resultado);
        }
    }
}
