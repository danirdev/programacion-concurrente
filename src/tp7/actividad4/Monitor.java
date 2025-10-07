package tp7.actividad4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🖥️ Monitor - Clase base abstracta para implementar monitores con semáforos
 * 
 * Esta clase proporciona la infraestructura básica para implementar monitores
 * que garantizan exclusión mutua implícita utilizando semáforos como mecanismo
 * subyacente. Encapsula el estado interno y proporciona sincronización automática.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public abstract class Monitor {
    
    // 🚦 Semáforo para exclusión mutua implícita
    private final Semaphore mutex;
    
    // 🏷️ Identificación del monitor
    private final String monitorId;
    
    // 📊 Estadísticas de uso
    private final AtomicInteger accesosActuales;
    private final AtomicInteger totalAccesos;
    private final AtomicInteger tiempoEsperaTotal;
    
    // 🔍 Control de integridad
    private volatile String procesoActual;
    private volatile long tiempoUltimoAcceso;
    private volatile boolean monitorActivo;
    
    /**
     * 🏗️ Constructor del Monitor
     * 
     * @param monitorId Identificador único del monitor
     */
    protected Monitor(String monitorId) {
        this.monitorId = monitorId;
        this.mutex = new Semaphore(1, true); // Fairness habilitado
        this.accesosActuales = new AtomicInteger(0);
        this.totalAccesos = new AtomicInteger(0);
        this.tiempoEsperaTotal = new AtomicInteger(0);
        this.procesoActual = null;
        this.tiempoUltimoAcceso = 0;
        this.monitorActivo = true;
        
        System.out.printf("🖥️ Monitor '%s' inicializado con exclusión mutua implícita%n", monitorId);
    }
    
    /**
     * 🔒 Entrar al monitor (exclusión mutua implícita)
     * Este método es llamado automáticamente por todos los procedimientos públicos
     * 
     * @param procesoId ID del proceso que solicita acceso
     */
    protected final void entrarMonitor(String procesoId) {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            System.out.printf("[%s] 🔄 Solicitando acceso al monitor '%s'%n", procesoId, monitorId);
            
            // 🚦 Adquirir exclusión mutua (bloqueo implícito)
            mutex.acquire();
            
            long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
            tiempoEsperaTotal.addAndGet((int) tiempoEspera);
            
            // ⚡ ENTRANDO AL MONITOR
            procesoActual = procesoId;
            tiempoUltimoAcceso = System.currentTimeMillis();
            accesosActuales.incrementAndGet();
            totalAccesos.incrementAndGet();
            
            System.out.printf("[%s] ✅ ENTRANDO al monitor '%s' - Espera: %dms%n", 
                             procesoId, monitorId, tiempoEspera);
            
        } catch (InterruptedException e) {
            System.err.printf("⚠️ [%s] Interrumpido esperando acceso al monitor '%s'%n", 
                             procesoId, monitorId);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Monitor access interrupted", e);
        }
    }
    
    /**
     * 🔓 Salir del monitor (liberación automática)
     * Este método es llamado automáticamente al finalizar procedimientos públicos
     * 
     * @param procesoId ID del proceso que sale
     */
    protected final void salirMonitor(String procesoId) {
        try {
            // ⚡ SALIENDO DEL MONITOR
            if (!procesoId.equals(procesoActual)) {
                System.err.printf("❌ [%s] ERROR: Intento de salir sin estar en monitor '%s'%n", 
                                 procesoId, monitorId);
                return;
            }
            
            procesoActual = null;
            accesosActuales.decrementAndGet();
            
            System.out.printf("[%s] 🔓 SALIENDO del monitor '%s'%n", procesoId, monitorId);
            
        } finally {
            // 🚦 Liberar exclusión mutua (siempre ejecutar)
            mutex.release();
        }
    }
    
    /**
     * 🔧 Ejecutar procedimiento del monitor con exclusión mutua implícita
     * Template method que garantiza sincronización automática
     * 
     * @param procesoId ID del proceso
     * @param procedimiento Código a ejecutar dentro del monitor
     */
    protected final void ejecutarProcedimiento(String procesoId, Runnable procedimiento) {
        entrarMonitor(procesoId);
        try {
            // ⚡ EJECUTAR CÓDIGO DENTRO DEL MONITOR
            procedimiento.run();
        } finally {
            salirMonitor(procesoId);
        }
    }
    
    /**
     * 🔧 Ejecutar función del monitor con exclusión mutua implícita y retorno
     * 
     * @param <T> Tipo de retorno
     * @param procesoId ID del proceso
     * @param funcion Función a ejecutar dentro del monitor
     * @return Resultado de la función
     */
    protected final <T> T ejecutarFuncion(String procesoId, java.util.function.Supplier<T> funcion) {
        entrarMonitor(procesoId);
        try {
            // ⚡ EJECUTAR FUNCIÓN DENTRO DEL MONITOR
            return funcion.get();
        } finally {
            salirMonitor(procesoId);
        }
    }
    
    /**
     * 🔍 Verificar si el proceso actual tiene acceso al monitor
     * 
     * @param procesoId ID del proceso a verificar
     * @return true si el proceso tiene acceso exclusivo
     */
    protected final boolean tieneAccesoExclusivo(String procesoId) {
        return procesoId.equals(procesoActual);
    }
    
    /**
     * ⏱️ Obtener tiempo desde último acceso
     * 
     * @return Tiempo en milisegundos
     */
    protected final long getTiempoDesdeUltimoAcceso() {
        return tiempoUltimoAcceso > 0 ? System.currentTimeMillis() - tiempoUltimoAcceso : 0;
    }
    
    /**
     * 📊 Obtener estadísticas del monitor
     * 
     * @return String con estadísticas detalladas
     */
    public final String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 📊 ESTADÍSTICAS MONITOR '%s' ===\n", monitorId));
        stats.append(String.format("🔢 Total accesos: %d%n", totalAccesos.get()));
        stats.append(String.format("👤 Proceso actual: %s%n", 
                                  procesoActual != null ? procesoActual : "NINGUNO"));
        stats.append(String.format("⏱️ Tiempo promedio espera: %.2fms%n", 
                                  totalAccesos.get() > 0 ? 
                                  (double) tiempoEsperaTotal.get() / totalAccesos.get() : 0));
        stats.append(String.format("🚦 Permisos disponibles: %d%n", mutex.availablePermits()));
        stats.append(String.format("⏳ Procesos en cola: %d%n", mutex.getQueueLength()));
        stats.append(String.format("✅ Estado: %s%n", monitorActivo ? "ACTIVO" : "INACTIVO"));
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad del monitor
     * 
     * @return true si el monitor está íntegro
     */
    public final boolean verificarIntegridad() {
        // Verificar que solo hay un proceso en el monitor
        boolean accesoExclusivo = accesosActuales.get() <= 1;
        
        // Verificar consistencia del semáforo
        boolean semaforoConsistente = mutex.availablePermits() >= 0 && mutex.availablePermits() <= 1;
        
        // Verificar estado del proceso actual
        boolean estadoConsistente = (procesoActual == null) == (accesosActuales.get() == 0);
        
        boolean integro = accesoExclusivo && semaforoConsistente && estadoConsistente;
        
        if (!integro) {
            System.err.printf("❌ INTEGRIDAD COMPROMETIDA EN MONITOR '%s':%n", monitorId);
            System.err.printf("   Acceso exclusivo: %s (actuales: %d)%n", 
                             accesoExclusivo, accesosActuales.get());
            System.err.printf("   Semáforo consistente: %s (permisos: %d)%n", 
                             semaforoConsistente, mutex.availablePermits());
            System.err.printf("   Estado consistente: %s%n", estadoConsistente);
        }
        
        return integro;
    }
    
    /**
     * 🚦 Información del estado del semáforo
     * 
     * @return String con información del semáforo
     */
    public final String getInfoSemaforo() {
        return String.format("🚦 Monitor '%s' - Permisos: %d, Cola: %d, Fairness: %s", 
                           monitorId,
                           mutex.availablePermits(), 
                           mutex.getQueueLength(),
                           mutex.isFair() ? "✅" : "❌");
    }
    
    /**
     * 🛑 Desactivar monitor (para cleanup)
     */
    public final void desactivar() {
        monitorActivo = false;
        System.out.printf("🛑 Monitor '%s' desactivado%n", monitorId);
    }
    
    /**
     * 📈 Obtener métricas de rendimiento
     * 
     * @return Array con [totalAccesos, tiempoEsperaPromedio, eficiencia]
     */
    public final double[] getMetricasRendimiento() {
        double tiempoEsperaPromedio = totalAccesos.get() > 0 ? 
            (double) tiempoEsperaTotal.get() / totalAccesos.get() : 0;
        
        // Eficiencia basada en tiempo de espera (menos espera = más eficiencia)
        double eficiencia = tiempoEsperaPromedio > 0 ? 
            Math.max(0, 100 - (tiempoEsperaPromedio / 10)) : 100;
        
        return new double[]{
            totalAccesos.get(),
            tiempoEsperaPromedio,
            eficiencia
        };
    }
    
    // 🔧 Getters protegidos para subclases
    
    protected final String getMonitorId() { return monitorId; }
    
    protected final Semaphore getMutexSemaforo() { return mutex; }
    
    protected final String getProcesoActual() { return procesoActual; }
    
    protected final boolean isMonitorActivo() { return monitorActivo; }
    
    protected final int getTotalAccesos() { return totalAccesos.get(); }
    
    /**
     * 📝 Representación en string del monitor
     * 
     * @return Información básica del monitor
     */
    @Override
    public String toString() {
        return String.format("Monitor{id='%s', accesos=%d, procesoActual='%s', activo=%s}", 
                           monitorId, totalAccesos.get(), 
                           procesoActual != null ? procesoActual : "NINGUNO", 
                           monitorActivo);
    }
    
    // 🎯 Métodos abstractos que deben implementar las subclases
    
    /**
     * 🔧 Inicializar estado específico del monitor
     * Llamado durante la construcción del monitor
     */
    protected abstract void inicializarEstado();
    
    /**
     * 📋 Obtener descripción específica del monitor
     * 
     * @return Descripción del tipo de monitor
     */
    public abstract String getDescripcion();
    
    /**
     * 🧪 Ejecutar prueba de funcionalidad específica
     * 
     * @param procesoId ID del proceso de prueba
     * @return true si la prueba fue exitosa
     */
    public abstract boolean ejecutarPrueba(String procesoId);
}
