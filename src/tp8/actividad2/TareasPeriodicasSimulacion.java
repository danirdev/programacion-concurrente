package tp8.actividad2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ⏰ TareasPeriodicasSimulacion - Simulación de Tareas Periódicas
 * 
 * Esta clase demuestra el uso de ScheduledExecutorService para ejecutar
 * tareas periódicas cada 2 segundos: captura de timestamps y procesamiento
 * de números primos.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareasPeriodicasSimulacion {
    
    // ⚙️ Configuración de scheduling
    private static final int DELAY_INICIAL = 2;      // segundos
    private static final int PERIODO = 2;            // segundos
    private static final int DURACION_SIMULACION = 30; // segundos
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("⏰ TAREAS PERIÓDICAS CON SCHEDULED EXECUTOR SERVICE");
        System.out.println(SEPARADOR);
        
        TareasPeriodicasSimulacion simulacion = new TareasPeriodicasSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar simulación
        simulacion.ejecutarSimulacion();
        
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
        System.out.println("🎯 Objetivo: Ejecutar tareas periódicas con ScheduledExecutorService");
        System.out.println("🔬 Framework: Java ScheduledExecutorService");
        System.out.println();
        
        System.out.println("⚙️ CONFIGURACIÓN:");
        System.out.printf("   ⏰ Delay inicial: %d segundos%n", DELAY_INICIAL);
        System.out.printf("   🔄 Período: %d segundos%n", PERIODO);
        System.out.printf("   ⏱️ Duración simulación: %d segundos%n", DURACION_SIMULACION);
        System.out.printf("   📋 Tareas: 2 (Tarea1: FechaHora, Tarea2: Procesamiento)%n");
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar la simulación con tareas periódicas
     */
    private void ejecutarSimulacion() {
        System.out.println("🚀 INICIANDO TAREAS PERIÓDICAS...\n");
        
        // 📋 Crear lista compartida
        ListaCompartida lista = new ListaCompartida();
        
        // 📅 Crear tareas
        TareaFechaHora tarea1 = new TareaFechaHora(lista);
        TareaProcesamiento tarea2 = new TareaProcesamiento(lista);
        
        // 🏗️ Crear ScheduledExecutorService con 2 threads
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        try {
            // ⏰ Programar Tarea1: ejecutar cada 2 segundos
            scheduler.scheduleAtFixedRate(
                tarea1,
                DELAY_INICIAL,
                PERIODO,
                TimeUnit.SECONDS
            );
            System.out.printf("✅ Tarea1 (FechaHora) programada: delay=%ds, período=%ds%n", 
                             DELAY_INICIAL, PERIODO);
            
            // ⏰ Programar Tarea2: ejecutar cada 2 segundos
            scheduler.scheduleAtFixedRate(
                tarea2,
                DELAY_INICIAL,
                PERIODO,
                TimeUnit.SECONDS
            );
            System.out.printf("✅ Tarea2 (Procesamiento) programada: delay=%ds, período=%ds%n", 
                             DELAY_INICIAL, PERIODO);
            
            System.out.println("\n⏳ Tareas ejecutándose periódicamente...\n");
            System.out.println("-".repeat(80));
            
            // ⏱️ Dejar que las tareas se ejecuten por un tiempo
            Thread.sleep(DURACION_SIMULACION * 1000);
            
            System.out.println("-".repeat(80));
            System.out.println("\n⏹️ Deteniendo tareas periódicas...\n");
            
            // 🛑 Detener el scheduler
            scheduler.shutdown();
            
            // ⏳ Esperar terminación
            boolean terminado = scheduler.awaitTermination(5, TimeUnit.SECONDS);
            
            if (terminado) {
                System.out.println("✅ Scheduler detenido correctamente\n");
            } else {
                System.out.println("⚠️ Timeout - Forzando detención\n");
                scheduler.shutdownNow();
            }
            
            // 📊 Mostrar resultados
            mostrarResultados(lista, tarea1, tarea2);
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Error en simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 📊 Mostrar resultados de la simulación
     * 
     * @param lista Lista compartida utilizada
     * @param tarea1 Tarea de captura de fecha/hora
     * @param tarea2 Tarea de procesamiento
     */
    private void mostrarResultados(ListaCompartida lista, TareaFechaHora tarea1, 
                                   TareaProcesamiento tarea2) {
        System.out.println("📊 === RESULTADOS DE LA SIMULACIÓN ===\n");
        
        // Estadísticas de la lista
        System.out.println(lista.getEstadisticas());
        
        // Estadísticas de Tarea2
        System.out.println(tarea2.getEstadisticas());
        
        // Resumen general
        System.out.println("\n📋 RESUMEN GENERAL:");
        System.out.printf("   📅 Ejecuciones Tarea1: %d%n", tarea1.getNumeroEjecucion());
        System.out.printf("   🔢 Timestamps en lista: %d%n", lista.size());
        System.out.printf("   ✨ Números primos encontrados: %d%n", tarea2.getPrimosEncontrados());
        System.out.printf("   ❌ Números no primos: %d%n", tarea2.getNoPrimosEncontrados());
        
        int total = tarea2.getPrimosEncontrados() + tarea2.getNoPrimosEncontrados();
        if (total > 0) {
            double porcentajePrimos = (tarea2.getPrimosEncontrados() * 100.0) / total;
            System.out.printf("   📊 Porcentaje de primos: %.1f%%%n", porcentajePrimos);
        }
        
        System.out.println("\n📄 ARCHIVOS GENERADOS:");
        System.out.println("   📄 Primos.txt - Números primos encontrados");
        System.out.println("   📄 NoPrimos.txt - Números no primos");
        
        // Análisis educativo
        mostrarAnalisisEducativo(tarea1.getNumeroEjecucion());
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     * 
     * @param ejecuciones Número de ejecuciones realizadas
     */
    private void mostrarAnalisisEducativo(int ejecuciones) {
        System.out.println("\n🎓 === ANÁLISIS EDUCATIVO ===");
        
        System.out.println("\n⏰ SCHEDULED EXECUTOR SERVICE:");
        System.out.println("   ✅ Ejecuta tareas periódicamente de forma automática");
        System.out.println("   ✅ Gestiona pool de threads para múltiples tareas");
        System.out.println("   ✅ Más robusto que Timer (maneja excepciones)");
        System.out.println("   ✅ API flexible: scheduleAtFixedRate, scheduleWithFixedDelay");
        
        System.out.println("\n🔄 scheduleAtFixedRate():");
        System.out.println("   • Ejecuta cada X tiempo desde el INICIO de la anterior");
        System.out.println("   • No espera a que termine la tarea anterior");
        System.out.println("   • Útil para tareas de duración fija y predecible");
        
        System.out.println("\n📊 RESULTADOS DE LA SIMULACIÓN:");
        System.out.printf("   🔢 Ejecuciones esperadas: ~%d%n", DURACION_SIMULACION / PERIODO);
        System.out.printf("   ✅ Ejecuciones reales: %d%n", ejecuciones);
        
        double precision = (ejecuciones * 100.0) / (DURACION_SIMULACION / PERIODO);
        System.out.printf("   📊 Precisión: %.1f%%%n", Math.min(precision, 100));
        
        System.out.println("\n💡 VENTAJAS DEMOSTRADAS:");
        System.out.println("   ✅ Sincronización automática de tareas periódicas");
        System.out.println("   ✅ No es necesario gestionar threads manualmente");
        System.out.println("   ✅ Lista compartida thread-safe (sincronizada)");
        System.out.println("   ✅ Procesamiento concurrente de timestamps");
        System.out.println("   ✅ Escritura a archivos desde múltiples threads");
    }
}
