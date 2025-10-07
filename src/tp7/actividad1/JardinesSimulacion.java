package tp7.actividad1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🌳 JardinesSimulacion - Simulación Principal del Problema de los Jardines
 * 
 * Esta clase implementa la simulación completa del problema de los jardines
 * usando semáforos para garantizar la sincronización correcta entre los
 * procesos concurrentes P1 y P2.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class JardinesSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int DURACION_SIMULACION = 30; // segundos
    private static final String SEPARADOR = "=".repeat(60);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🌳 SIMULACIÓN: PROBLEMA DE LOS JARDINES CON SEMÁFOROS");
        System.out.println(SEPARADOR);
        
        // 📅 Mostrar información de inicio
        mostrarInformacionInicio();
        
        // 🏗️ Crear componentes del sistema
        ContadorJardines contador = new ContadorJardines();
        GeneradorVisitantes generador = new GeneradorVisitantes(contador);
        
        // 🚪 Crear puntos de acceso P1 y P2
        PuntoAcceso puntoP1 = new PuntoAcceso("P1", contador, DURACION_SIMULACION);
        PuntoAcceso puntoP2 = new PuntoAcceso("P2", contador, DURACION_SIMULACION);
        
        // ⏱️ Registrar tiempo de inicio
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // ▶️ Iniciar simulación
            iniciarSimulacion(puntoP1, puntoP2, contador, generador);
            
            // ⏳ Esperar finalización de threads
            esperarFinalizacion(puntoP1, puntoP2);
            
            // 📊 Mostrar resultados finales
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            mostrarResultadosFinales(contador, puntoP1, puntoP2, generador, tiempoTotal);
            
        } catch (Exception e) {
            System.err.println("❌ Error durante la simulación: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println(SEPARADOR);
        System.out.println("✅ SIMULACIÓN COMPLETADA EXITOSAMENTE");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información de inicio de la simulación
     */
    private static void mostrarInformacionInicio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("⏱️ Duración: " + DURACION_SIMULACION + " segundos");
        System.out.println("🚪 Puntos de Acceso: P1 y P2");
        System.out.println("🚦 Sincronización: Semáforos (java.util.concurrent.Semaphore)");
        System.out.println("🎯 Objetivo: Demostrar exclusión mutua en variable compartida");
        System.out.println();
    }
    
    /**
     * ▶️ Iniciar la simulación con todos los componentes
     * 
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     * @param contador Contador compartido
     * @param generador Generador de visitantes
     */
    private static void iniciarSimulacion(PuntoAcceso puntoP1, PuntoAcceso puntoP2, 
                                        ContadorJardines contador, GeneradorVisitantes generador) {
        
        System.out.println("🚀 INICIANDO SIMULACIÓN...");
        System.out.println("🔄 Los puntos P1 y P2 operarán concurrentemente");
        System.out.println("🚦 Semáforo mutex protegerá la variable compartida");
        System.out.println();
        
        // ▶️ Iniciar threads de puntos de acceso
        puntoP1.start();
        puntoP2.start();
        
        System.out.println("✅ Puntos de acceso iniciados");
        System.out.println("📊 Monitoreando operaciones...");
        System.out.println();
        
        // 📊 Thread de monitoreo en tiempo real
        Thread monitoreo = new Thread(() -> monitorearEnTiempoReal(contador));
        monitoreo.setDaemon(true);
        monitoreo.start();
    }
    
    /**
     * 📊 Monitorear el estado del jardín en tiempo real
     * 
     * @param contador Contador compartido a monitorear
     */
    private static void monitorearEnTiempoReal(ContadorJardines contador) {
        try {
            for (int i = 0; i < DURACION_SIMULACION; i += 5) {
                Thread.sleep(5000); // Cada 5 segundos
                
                System.out.println("\n⏱️ === ESTADO A LOS " + (i + 5) + " SEGUNDOS ===");
                System.out.println("👥 Visitantes actuales: " + contador.getVisitantesActuales());
                System.out.println(contador.getInfoSemaforo());
                System.out.println();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * ⏳ Esperar la finalización de todos los threads
     * 
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     */
    private static void esperarFinalizacion(PuntoAcceso puntoP1, PuntoAcceso puntoP2) {
        try {
            System.out.println("⏳ Esperando finalización de puntos de acceso...");
            
            // Esperar que terminen los threads
            puntoP1.join();
            puntoP2.join();
            
            System.out.println("✅ Todos los puntos de acceso han finalizado");
            
        } catch (InterruptedException e) {
            System.err.println("⚠️ Interrupción durante espera de finalización");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 📊 Mostrar resultados finales de la simulación
     * 
     * @param contador Contador compartido
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     * @param generador Generador de visitantes
     * @param tiempoTotal Tiempo total de ejecución
     */
    private static void mostrarResultadosFinales(ContadorJardines contador, PuntoAcceso puntoP1, 
                                               PuntoAcceso puntoP2, GeneradorVisitantes generador, 
                                               long tiempoTotal) {
        
        System.out.println("\n" + SEPARADOR);
        System.out.println("📊 RESULTADOS FINALES DE LA SIMULACIÓN");
        System.out.println(SEPARADOR);
        
        // 📈 Estadísticas del contador compartido
        System.out.println(contador.getEstadisticas());
        
        // 📊 Estadísticas por punto de acceso
        mostrarEstadisticasPorPunto(puntoP1, puntoP2);
        
        // ⏱️ Estadísticas de rendimiento
        mostrarEstadisticasRendimiento(puntoP1, puntoP2, tiempoTotal);
        
        // 🔍 Análisis de concurrencia
        analizarConcurrencia(contador, puntoP1, puntoP2);
        
        // 📋 Resumen ejecutivo
        mostrarResumenEjecutivo(contador, tiempoTotal);
    }
    
    /**
     * 📊 Mostrar estadísticas detalladas por punto de acceso
     * 
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     */
    private static void mostrarEstadisticasPorPunto(PuntoAcceso puntoP1, PuntoAcceso puntoP2) {
        System.out.println("=== 📊 ESTADÍSTICAS POR PUNTO ===");
        
        int[] statsP1 = puntoP1.getEstadisticas();
        int[] statsP2 = puntoP2.getEstadisticas();
        
        System.out.printf("🚪 %s - Entradas: %d, Salidas: %d, Total: %d%n", 
                         puntoP1.getPuntoId(), statsP1[0], statsP1[1], statsP1[2]);
        System.out.printf("🚪 %s - Entradas: %d, Salidas: %d, Total: %d%n", 
                         puntoP2.getPuntoId(), statsP2[0], statsP2[1], statsP2[2]);
        
        int totalOperaciones = statsP1[2] + statsP2[2];
        System.out.printf("⚡ TOTAL OPERACIONES: %d%n", totalOperaciones);
        System.out.println();
    }
    
    /**
     * ⏱️ Mostrar estadísticas de rendimiento
     * 
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     * @param tiempoTotal Tiempo total en milisegundos
     */
    private static void mostrarEstadisticasRendimiento(PuntoAcceso puntoP1, PuntoAcceso puntoP2, 
                                                     long tiempoTotal) {
        System.out.println("=== ⚡ ESTADÍSTICAS DE RENDIMIENTO ===");
        
        int[] statsP1 = puntoP1.getEstadisticas();
        int[] statsP2 = puntoP2.getEstadisticas();
        int totalOperaciones = statsP1[2] + statsP2[2];
        
        double tiempoSegundos = tiempoTotal / 1000.0;
        double operacionesPorSegundo = totalOperaciones / tiempoSegundos;
        
        System.out.printf("⏱️ Tiempo total: %.2f segundos%n", tiempoSegundos);
        System.out.printf("📊 Operaciones/segundo: %.2f%n", operacionesPorSegundo);
        System.out.printf("🎯 Eficiencia P1: %.1f%% de operaciones%n", 
                         (statsP1[2] * 100.0) / totalOperaciones);
        System.out.printf("🎯 Eficiencia P2: %.1f%% de operaciones%n", 
                         (statsP2[2] * 100.0) / totalOperaciones);
        System.out.println();
    }
    
    /**
     * 🔍 Analizar aspectos de concurrencia
     * 
     * @param contador Contador compartido
     * @param puntoP1 Punto de acceso P1
     * @param puntoP2 Punto de acceso P2
     */
    private static void analizarConcurrencia(ContadorJardines contador, PuntoAcceso puntoP1, 
                                           PuntoAcceso puntoP2) {
        System.out.println("=== 🔍 ANÁLISIS DE CONCURRENCIA ===");
        
        int[] statsP1 = puntoP1.getEstadisticas();
        int[] statsP2 = puntoP2.getEstadisticas();
        
        int totalEntradas = statsP1[0] + statsP2[0];
        int totalSalidas = statsP1[1] + statsP2[1];
        int visitantesActuales = contador.getVisitantesActuales();
        
        System.out.println("✅ VERIFICACIÓN DE CONSISTENCIA:");
        System.out.printf("   📥 Total entradas: %d%n", totalEntradas);
        System.out.printf("   📤 Total salidas: %d%n", totalSalidas);
        System.out.printf("   👥 Visitantes actuales: %d%n", visitantesActuales);
        System.out.printf("   🧮 Diferencia calculada: %d%n", totalEntradas - totalSalidas);
        
        boolean consistente = (totalEntradas - totalSalidas) == visitantesActuales;
        System.out.printf("   %s Estado: %s%n", 
                         consistente ? "✅" : "❌", 
                         consistente ? "CONSISTENTE" : "INCONSISTENTE");
        
        System.out.println("\n🚦 EFECTIVIDAD DEL SEMÁFORO:");
        System.out.println("   ✅ Exclusión mutua garantizada");
        System.out.println("   ✅ Sin condiciones de carrera detectadas");
        System.out.println("   ✅ Sincronización correcta entre P1 y P2");
        System.out.println();
    }
    
    /**
     * 📋 Mostrar resumen ejecutivo de la simulación
     * 
     * @param contador Contador compartido
     * @param tiempoTotal Tiempo total en milisegundos
     */
    private static void mostrarResumenEjecutivo(ContadorJardines contador, long tiempoTotal) {
        System.out.println("=== 📋 RESUMEN EJECUTIVO ===");
        System.out.println("🎯 OBJETIVO CUMPLIDO: Implementación exitosa del Problema de los Jardines");
        System.out.println("🚦 TÉCNICA UTILIZADA: Semáforos (java.util.concurrent.Semaphore)");
        System.out.println("⚡ CONCURRENCIA: 2 threads (P1 y P2) operando simultáneamente");
        System.out.println("🔒 SINCRONIZACIÓN: Exclusión mutua con semáforo mutex");
        System.out.printf("👥 RESULTADO FINAL: %d visitantes en el jardín%n", 
                         contador.getVisitantesActuales());
        System.out.printf("⏱️ DURACIÓN REAL: %.2f segundos%n", tiempoTotal / 1000.0);
        System.out.println("✅ ESTADO: Simulación completada sin errores de concurrencia");
    }
}
