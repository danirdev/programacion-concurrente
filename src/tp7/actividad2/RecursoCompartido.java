package tp7.actividad2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🔒 RecursoCompartido - Recurso protegido por semáforo para exclusión mutua
 * 
 * Esta clase representa un recurso compartido que debe ser accedido de forma
 * exclusiva por los procesos concurrentes. Utiliza un semáforo mutex para
 * garantizar que solo un proceso pueda estar en la región crítica a la vez.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class RecursoCompartido {
    
    // 🚦 Semáforo mutex para exclusión mutua (inicializado en 1)
    private final Semaphore mutex;
    
    // 📊 Variables compartidas protegidas
    private int contadorAccesos;
    private String ultimoProcesoEnRegionCritica;
    private long tiempoUltimoAcceso;
    
    // 📈 Estadísticas de uso
    private final AtomicInteger totalAccesos;
    private final AtomicInteger accesosSimultaneos; // Para detectar violaciones
    private final AtomicInteger tiempoEsperaTotal;
    
    // 🔍 Control de integridad
    private volatile boolean recursoEnUso;
    private volatile String procesoActual;
    
    /**
     * 🏗️ Constructor - Inicializa el recurso compartido
     */
    public RecursoCompartido() {
        this.mutex = new Semaphore(1, true); // Fairness habilitado
        this.contadorAccesos = 0;
        this.ultimoProcesoEnRegionCritica = "NINGUNO";
        this.tiempoUltimoAcceso = 0;
        
        this.totalAccesos = new AtomicInteger(0);
        this.accesosSimultaneos = new AtomicInteger(0);
        this.tiempoEsperaTotal = new AtomicInteger(0);
        
        this.recursoEnUso = false;
        this.procesoActual = null;
        
        System.out.println("🔒 RecursoCompartido inicializado con semáforo mutex");
    }
    
    /**
     * 🚪 Entrar en región crítica de forma segura
     * 
     * @param procesoId Identificador del proceso que solicita acceso
     * @param tiempoTrabajo Tiempo que permanecerá en región crítica (ms)
     * @return true si el acceso fue exitoso
     */
    public boolean entrarRegionCritica(String procesoId, int tiempoTrabajo) {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            System.out.printf("[%s] 🔄 Solicitando acceso a región crítica%n", procesoId);
            
            // 🔒 Adquirir el semáforo (bloqueo eficiente, sin busy wait)
            mutex.acquire();
            
            long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
            tiempoEsperaTotal.addAndGet((int) tiempoEspera);
            
            // ⚡ ENTRANDO EN REGIÓN CRÍTICA
            if (recursoEnUso) {
                // 🚨 VIOLACIÓN DETECTADA - No debería pasar con semáforos
                accesosSimultaneos.incrementAndGet();
                System.err.printf("❌ [%s] VIOLACIÓN: Recurso ya en uso por %s%n", 
                                procesoId, procesoActual);
                return false;
            }
            
            // 🔐 Marcar recurso como en uso
            recursoEnUso = true;
            procesoActual = procesoId;
            contadorAccesos++;
            ultimoProcesoEnRegionCritica = procesoId;
            tiempoUltimoAcceso = System.currentTimeMillis();
            totalAccesos.incrementAndGet();
            
            System.out.printf("[%s] ✅ ENTRANDO en región crítica (acceso #%d) - Espera: %dms%n", 
                            procesoId, contadorAccesos, tiempoEspera);
            
            // 🔧 Simular trabajo en región crítica
            trabajarEnRegionCritica(procesoId, tiempoTrabajo);
            
            return true;
            
        } catch (InterruptedException e) {
            System.err.printf("⚠️ [%s] Interrumpido mientras esperaba acceso%n", procesoId);
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * 🚪 Salir de región crítica
     * 
     * @param procesoId Identificador del proceso que sale
     */
    public void salirRegionCritica(String procesoId) {
        try {
            // ⚡ SALIENDO DE REGIÓN CRÍTICA
            if (!recursoEnUso || !procesoId.equals(procesoActual)) {
                System.err.printf("❌ [%s] ERROR: Intento de salir sin estar en región crítica%n", procesoId);
                return;
            }
            
            // 🔓 Marcar recurso como libre
            recursoEnUso = false;
            procesoActual = null;
            
            System.out.printf("[%s] 🔓 SALIENDO de región crítica%n", procesoId);
            
        } finally {
            // 🔓 Liberar el semáforo (permitir que otro proceso entre)
            mutex.release();
        }
    }
    
    /**
     * 🔧 Simular trabajo dentro de la región crítica
     * 
     * @param procesoId Identificador del proceso
     * @param tiempoTrabajo Tiempo de trabajo en milisegundos
     */
    private void trabajarEnRegionCritica(String procesoId, int tiempoTrabajo) {
        try {
            System.out.printf("[%s] 🔧 Trabajando en región crítica por %dms%n", 
                            procesoId, tiempoTrabajo);
            
            // Simular trabajo crítico
            Thread.sleep(tiempoTrabajo);
            
            // Verificar integridad durante el trabajo
            if (!procesoId.equals(procesoActual)) {
                System.err.printf("❌ [%s] CORRUPCIÓN: Proceso actual cambió durante trabajo%n", procesoId);
            }
            
        } catch (InterruptedException e) {
            System.err.printf("⚠️ [%s] Trabajo interrumpido en región crítica%n", procesoId);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 📊 Obtener estadísticas del recurso compartido
     * 
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📊 ESTADÍSTICAS DEL RECURSO COMPARTIDO ===\n");
        stats.append(String.format("🔢 Total accesos: %d%n", totalAccesos.get()));
        stats.append(String.format("🔒 Último proceso: %s%n", ultimoProcesoEnRegionCritica));
        stats.append(String.format("⏱️ Tiempo promedio espera: %.2fms%n", 
                                  totalAccesos.get() > 0 ? (double) tiempoEsperaTotal.get() / totalAccesos.get() : 0));
        stats.append(String.format("🚨 Violaciones detectadas: %d%n", accesosSimultaneos.get()));
        stats.append(String.format("🚦 Permisos disponibles: %d%n", mutex.availablePermits()));
        stats.append(String.format("⏳ Procesos en cola: %d%n", mutex.getQueueLength()));
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad del recurso
     * 
     * @return true si el recurso está íntegro
     */
    public boolean verificarIntegridad() {
        boolean integro = accesosSimultaneos.get() == 0;
        
        if (!integro) {
            System.err.printf("❌ INTEGRIDAD COMPROMETIDA: %d violaciones detectadas%n", 
                            accesosSimultaneos.get());
        } else {
            System.out.println("✅ INTEGRIDAD VERIFICADA: Sin violaciones de exclusión mutua");
        }
        
        return integro;
    }
    
    /**
     * 📈 Obtener métricas de rendimiento
     * 
     * @return Array con [totalAccesos, tiempoEsperaPromedio, violaciones]
     */
    public double[] getMetricasRendimiento() {
        double tiempoEsperaPromedio = totalAccesos.get() > 0 ? 
            (double) tiempoEsperaTotal.get() / totalAccesos.get() : 0;
        
        return new double[]{
            totalAccesos.get(),
            tiempoEsperaPromedio,
            accesosSimultaneos.get()
        };
    }
    
    /**
     * 🔄 Resetear estadísticas (para testing)
     */
    public void resetearEstadisticas() {
        contadorAccesos = 0;
        ultimoProcesoEnRegionCritica = "NINGUNO";
        tiempoUltimoAcceso = 0;
        totalAccesos.set(0);
        accesosSimultaneos.set(0);
        tiempoEsperaTotal.set(0);
        recursoEnUso = false;
        procesoActual = null;
        
        System.out.println("🔄 Estadísticas del recurso reseteadas");
    }
    
    /**
     * 🚦 Información del estado actual del semáforo
     * 
     * @return String con información del semáforo
     */
    public String getInfoSemaforo() {
        return String.format("🚦 Semáforo - Permisos: %d, Cola: %d, Fairness: %s", 
                           mutex.availablePermits(), 
                           mutex.getQueueLength(),
                           mutex.isFair() ? "✅" : "❌");
    }
    
    /**
     * 📝 Representación en string del recurso
     * 
     * @return Información básica del recurso
     */
    @Override
    public String toString() {
        return String.format("RecursoCompartido{accesos=%d, enUso=%s, procesoActual='%s'}", 
                           totalAccesos.get(), recursoEnUso, procesoActual);
    }
}
