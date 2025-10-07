package tp8.actividad4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 📁 MonitorDirectorioSimulacion - Simulación de Monitor de Directorio
 * 
 * Esta clase implementa un sistema de monitoreo de directorio que detecta
 * automáticamente archivos nuevos usando ScheduledExecutorService.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class MonitorDirectorioSimulacion {
    
    // ⚙️ Configuración del monitoreo
    private static final String DIRECTORIO_DEFAULT = "./monitor_test/";
    private static final int FRECUENCIA_ESCANEO = 5; // segundos
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos (opcional: ruta del directorio)
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("📁 MONITOR DE DIRECTORIO CON TAREAS PERIÓDICAS");
        System.out.println(SEPARADOR);
        
        // Determinar directorio a monitorear
        String directorio = (args.length > 0) ? args[0] : DIRECTORIO_DEFAULT;
        
        MonitorDirectorioSimulacion simulacion = new MonitorDirectorioSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial(directorio);
        
        // 🏃‍♂️ Ejecutar monitor
        simulacion.ejecutarMonitor(directorio);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ MONITOR DETENIDO");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información inicial
     * 
     * @param directorio Directorio a monitorear
     */
    private void mostrarInformacionInicial(String directorio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Detectar automáticamente archivos nuevos");
        System.out.println("🔬 Framework: ScheduledExecutorService con scheduleWithFixedDelay");
        System.out.println();
        
        System.out.println("📁 CONFIGURACIÓN:");
        System.out.printf("   📂 Directorio monitoreado: %s%n", directorio);
        System.out.printf("   🔄 Frecuencia de escaneo: %d segundos%n", FRECUENCIA_ESCANEO);
        System.out.printf("   👁️ Modo: Detección de archivos nuevos%n");
        System.out.println();
        
        System.out.println("💡 INSTRUCCIONES:");
        System.out.println("   1. El monitor escaneará el directorio cada 5 segundos");
        System.out.println("   2. Copia/crea archivos en el directorio para probar");
        System.out.println("   3. Presiona Enter para detener el monitor");
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar el monitor de directorio
     * 
     * @param directorio Directorio a monitorear
     */
    private void ejecutarMonitor(String directorio) {
        System.out.println("🚀 INICIANDO MONITOR...\n");
        
        // 📁 Crear tarea de monitoreo
        TareaMonitoreoDirectorio tarea = new TareaMonitoreoDirectorio(directorio);
        
        // 🏗️ Crear ScheduledExecutorService
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        try {
            // ⏰ Programar tarea con delay fijo (espera 5s después de cada ejecución)
            scheduler.scheduleWithFixedDelay(
                tarea,
                0,                      // Delay inicial: 0 (ejecutar inmediatamente)
                FRECUENCIA_ESCANEO,     // Delay: 5 segundos entre fin y nuevo inicio
                TimeUnit.SECONDS
            );
            
            System.out.printf("✅ Monitor iniciado - Escaneando cada %d segundos%n", FRECUENCIA_ESCANEO);
            System.out.println("⏸️ Presiona Enter para detener el monitor...\n");
            System.out.println(SEPARADOR);
            
            // ⏸️ Esperar a que el usuario presione Enter
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            
            System.out.println(SEPARADOR);
            System.out.println("\n⏹️ Deteniendo monitor...\n");
            
            // 🛑 Detener el scheduler
            scheduler.shutdown();
            
            // ⏳ Esperar terminación
            boolean terminado = scheduler.awaitTermination(10, TimeUnit.SECONDS);
            
            if (terminado) {
                System.out.println("✅ Monitor detenido correctamente\n");
            } else {
                System.out.println("⚠️ Timeout - Forzando detención\n");
                scheduler.shutdownNow();
            }
            
            // 📊 Mostrar estadísticas finales
            mostrarEstadisticasFinales(tarea);
            
        } catch (InterruptedException e) {
            System.err.println("❌ Monitor interrumpido: " + e.getMessage());
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Error en monitor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 📊 Mostrar estadísticas finales
     * 
     * @param tarea Tarea de monitoreo ejecutada
     */
    private void mostrarEstadisticasFinales(TareaMonitoreoDirectorio tarea) {
        System.out.println("📊 === ESTADÍSTICAS FINALES ===");
        
        // Estadísticas del monitor
        System.out.println(tarea.getEstadisticas());
        
        // Lista de archivos actuales
        System.out.println(tarea.listarArchivosActuales());
        
        // Análisis educativo
        mostrarAnalisisEducativo(tarea);
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     * 
     * @param tarea Tarea de monitoreo
     */
    private void mostrarAnalisisEducativo(TareaMonitoreoDirectorio tarea) {
        System.out.println("🎓 === ANÁLISIS EDUCATIVO ===");
        
        System.out.println("\n⏰ SCHEDULED EXECUTOR SERVICE:");
        System.out.println("   ✅ Ejecuta monitoreo periódico automáticamente");
        System.out.println("   ✅ scheduleWithFixedDelay() garantiza pausa entre escaneos");
        System.out.println("   ✅ Evita solapamiento si escaneo demora más de 5s");
        System.out.println("   ✅ Robusto ante excepciones (continúa ejecutando)");
        
        System.out.println("\n🔍 ALGORITMO DE DETECCIÓN:");
        System.out.println("   1. Mantener Set de archivos conocidos");
        System.out.println("   2. Escanear directorio periódicamente");
        System.out.println("   3. Comparar archivos actuales con conocidos");
        System.out.println("   4. Identificar diferencias (nuevos archivos)");
        System.out.println("   5. Notificar y actualizar Set de conocidos");
        
        System.out.println("\n📊 RESULTADOS:");
        System.out.printf("   🔢 Escaneos realizados: %d%n", tarea.getTotalEscaneos());
        System.out.printf("   🆕 Archivos nuevos detectados: %d%n", tarea.getArchivosNuevosDetectados());
        System.out.printf("   📁 Archivos finales en directorio: %d%n", tarea.getArchivosActuales());
        
        if (tarea.getTotalEscaneos() > 0) {
            double eficiencia = (tarea.getArchivosNuevosDetectados() * 100.0) / tarea.getTotalEscaneos();
            System.out.printf("   📈 Archivos nuevos por escaneo: %.1f%%%n", eficiencia);
        }
        
        System.out.println("\n💡 APLICACIONES PRÁCTICAS:");
        System.out.println("   📁 Monitoreo de directorios de entrada");
        System.out.println("   📥 Detección de archivos subidos por usuarios");
        System.out.println("   🔄 Sincronización automática de archivos");
        System.out.println("   📊 Sistemas de backup automático");
        System.out.println("   🔍 Vigilancia de cambios en sistema de archivos");
        
        System.out.println("\n🔄 scheduleWithFixedDelay vs scheduleAtFixedRate:");
        System.out.println("   scheduleWithFixedDelay:");
        System.out.println("     • Espera X segundos DESPUÉS de terminar tarea");
        System.out.println("     • Mejor para I/O variable (como este caso)");
        System.out.println("     • Evita solapamiento garantizado");
        System.out.println("   scheduleAtFixedRate:");
        System.out.println("     • Ejecuta cada X segundos desde INICIO");
        System.out.println("     • Mejor para tareas de duración fija");
        System.out.println("     • Puede solaparse si tarea demora mucho");
    }
}
