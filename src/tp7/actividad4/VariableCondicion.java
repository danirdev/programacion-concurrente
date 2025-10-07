package tp7.actividad4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🔄 VariableCondicion - Implementación de variables de condición para monitores
 * 
 * Esta clase implementa variables de condición utilizando semáforos para
 * proporcionar sincronización de alto nivel dentro de monitores. Permite
 * que los procesos esperen por condiciones específicas y sean notificados
 * cuando esas condiciones se cumplen.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class VariableCondicion {
    
    // 🏷️ Identificación de la variable de condición
    private final String nombre;
    
    // 🚦 Semáforo para bloquear procesos en espera
    private final Semaphore semaforoEspera;
    
    // 📊 Contador de procesos esperando
    private final AtomicInteger procesosEsperando;
    
    // 📈 Estadísticas de uso
    private final AtomicInteger totalWaits;
    private final AtomicInteger totalSignals;
    private final AtomicInteger maxProcesosEsperando;
    
    // ⏱️ Control de tiempo
    private long tiempoCreacion;
    private long tiempoUltimaOperacion;
    
    /**
     * 🏗️ Constructor de VariableCondicion
     * 
     * @param nombre Nombre descriptivo de la condición
     */
    public VariableCondicion(String nombre) {
        this.nombre = nombre;
        this.semaforoEspera = new Semaphore(0, true); // Inicializado en 0, fairness habilitado
        this.procesosEsperando = new AtomicInteger(0);
        this.totalWaits = new AtomicInteger(0);
        this.totalSignals = new AtomicInteger(0);
        this.maxProcesosEsperando = new AtomicInteger(0);
        this.tiempoCreacion = System.currentTimeMillis();
        this.tiempoUltimaOperacion = tiempoCreacion;
        
        System.out.printf("🔄 Variable de condición '%s' creada%n", nombre);
    }
    
    /**
     * ⏳ Esperar por la condición (wait)
     * El proceso se bloquea hasta que otro proceso haga signal()
     * 
     * @param procesoId ID del proceso que espera
     * @param monitorMutex Semáforo mutex del monitor (debe liberarse durante espera)
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void esperar(String procesoId, Semaphore monitorMutex) throws InterruptedException {
        System.out.printf("[%s] ⏳ Esperando condición '%s'%n", procesoId, nombre);
        
        // 📊 Actualizar estadísticas
        int esperandoActual = procesosEsperando.incrementAndGet();
        totalWaits.incrementAndGet();
        tiempoUltimaOperacion = System.currentTimeMillis();
        
        // 📈 Actualizar máximo de procesos esperando
        if (esperandoActual > maxProcesosEsperando.get()) {
            maxProcesosEsperando.set(esperandoActual);
        }
        
        System.out.printf("[%s] 📊 Procesos esperando '%s': %d%n", procesoId, nombre, esperandoActual);
        
        try {
            // 🔓 PASO CRÍTICO: Liberar mutex del monitor antes de esperar
            monitorMutex.release();
            System.out.printf("[%s] 🔓 Liberando monitor para esperar '%s'%n", procesoId, nombre);
            
            // ⏳ Esperar señal (bloqueo hasta signal())
            semaforoEspera.acquire();
            
            System.out.printf("[%s] 🔔 Recibida señal para '%s'%n", procesoId, nombre);
            
        } finally {
            // 📊 Decrementar contador de procesos esperando
            procesosEsperando.decrementAndGet();
            
            // 🔒 PASO CRÍTICO: Re-adquirir mutex del monitor después de despertar
            monitorMutex.acquire();
            System.out.printf("[%s] 🔒 Re-adquiriendo monitor después de '%s'%n", procesoId, nombre);
        }
    }
    
    /**
     * 🔔 Señalar la condición (signal)
     * Despierta a UN proceso que esté esperando por esta condición
     * 
     * @param procesoId ID del proceso que señala
     */
    public void señalar(String procesoId) {
        int esperando = procesosEsperando.get();
        
        if (esperando > 0) {
            totalSignals.incrementAndGet();
            tiempoUltimaOperacion = System.currentTimeMillis();
            
            System.out.printf("[%s] 🔔 Señalando condición '%s' (%d procesos esperando)%n", 
                             procesoId, nombre, esperando);
            
            // 🔓 Liberar un permiso para despertar a UN proceso
            semaforoEspera.release();
            
            System.out.printf("[%s] ✅ Señal enviada para '%s'%n", procesoId, nombre);
        } else {
            System.out.printf("[%s] ℹ️ Señal para '%s' ignorada (nadie esperando)%n", procesoId, nombre);
        }
    }
    
    /**
     * 📢 Señalar a todos (signalAll/broadcast)
     * Despierta a TODOS los procesos que estén esperando por esta condición
     * 
     * @param procesoId ID del proceso que señala
     */
    public void señalarTodos(String procesoId) {
        int esperando = procesosEsperando.get();
        
        if (esperando > 0) {
            totalSignals.addAndGet(esperando); // Contar como múltiples signals
            tiempoUltimaOperacion = System.currentTimeMillis();
            
            System.out.printf("[%s] 📢 Señalando a TODOS para '%s' (%d procesos)%n", 
                             procesoId, nombre, esperando);
            
            // 🔓 Liberar permisos para despertar a TODOS los procesos
            semaforoEspera.release(esperando);
            
            System.out.printf("[%s] ✅ Señal broadcast enviada para '%s'%n", procesoId, nombre);
        } else {
            System.out.printf("[%s] ℹ️ Broadcast para '%s' ignorado (nadie esperando)%n", procesoId, nombre);
        }
    }
    
    /**
     * 🔍 Verificar si hay procesos esperando
     * 
     * @return true si hay al menos un proceso esperando
     */
    public boolean hayProcesosEsperando() {
        return procesosEsperando.get() > 0;
    }
    
    /**
     * 📊 Obtener número de procesos esperando actualmente
     * 
     * @return Número de procesos en espera
     */
    public int getProcesosEsperando() {
        return procesosEsperando.get();
    }
    
    /**
     * 📈 Obtener estadísticas de la variable de condición
     * 
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        long tiempoVida = System.currentTimeMillis() - tiempoCreacion;
        
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 📊 ESTADÍSTICAS VARIABLE '%s' ===\n", nombre));
        stats.append(String.format("⏳ Procesos esperando: %d%n", procesosEsperando.get()));
        stats.append(String.format("📊 Total waits: %d%n", totalWaits.get()));
        stats.append(String.format("🔔 Total signals: %d%n", totalSignals.get()));
        stats.append(String.format("📈 Máximo esperando: %d%n", maxProcesosEsperando.get()));
        stats.append(String.format("⏱️ Tiempo de vida: %dms%n", tiempoVida));
        
        if (totalWaits.get() > 0) {
            double ratioSignalWait = (double) totalSignals.get() / totalWaits.get();
            stats.append(String.format("📊 Ratio Signal/Wait: %.2f%n", ratioSignalWait));
        }
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad de la variable de condición
     * 
     * @return true si está en estado consistente
     */
    public boolean verificarIntegridad() {
        // Verificar que el contador no sea negativo
        boolean contadorValido = procesosEsperando.get() >= 0;
        
        // Verificar que el semáforo esté en estado consistente
        boolean semaforoValido = semaforoEspera.availablePermits() >= 0;
        
        // Verificar que las estadísticas sean coherentes
        boolean estadisticasValidas = totalWaits.get() >= 0 && totalSignals.get() >= 0;
        
        boolean integra = contadorValido && semaforoValido && estadisticasValidas;
        
        if (!integra) {
            System.err.printf("❌ INTEGRIDAD COMPROMETIDA EN VARIABLE '%s':%n", nombre);
            System.err.printf("   Contador válido: %s (%d)%n", contadorValido, procesosEsperando.get());
            System.err.printf("   Semáforo válido: %s (permisos: %d)%n", 
                             semaforoValido, semaforoEspera.availablePermits());
            System.err.printf("   Estadísticas válidas: %s%n", estadisticasValidas);
        }
        
        return integra;
    }
    
    /**
     * 📊 Obtener métricas de rendimiento
     * 
     * @return Array con [waits, signals, esperandoActual, maxEsperando, eficiencia]
     */
    public double[] getMetricasRendimiento() {
        double eficiencia = totalWaits.get() > 0 ? 
            (double) totalSignals.get() / totalWaits.get() * 100 : 100;
        
        return new double[]{
            totalWaits.get(),
            totalSignals.get(),
            procesosEsperando.get(),
            maxProcesosEsperando.get(),
            Math.min(eficiencia, 100) // Cap at 100%
        };
    }
    
    /**
     * 🧹 Resetear estadísticas (para testing)
     */
    public void resetearEstadisticas() {
        totalWaits.set(0);
        totalSignals.set(0);
        maxProcesosEsperando.set(0);
        tiempoCreacion = System.currentTimeMillis();
        tiempoUltimaOperacion = tiempoCreacion;
        
        System.out.printf("🔄 Estadísticas de variable '%s' reseteadas%n", nombre);
    }
    
    /**
     * 🚦 Información del estado del semáforo interno
     * 
     * @return String con información del semáforo
     */
    public String getInfoSemaforo() {
        return String.format("🚦 Variable '%s' - Permisos: %d, Cola: %d, Esperando: %d", 
                           nombre,
                           semaforoEspera.availablePermits(),
                           semaforoEspera.getQueueLength(),
                           procesosEsperando.get());
    }
    
    /**
     * ⏱️ Obtener tiempo desde última operación
     * 
     * @return Tiempo en milisegundos
     */
    public long getTiempoDesdeUltimaOperacion() {
        return System.currentTimeMillis() - tiempoUltimaOperacion;
    }
    
    // 🔧 Getters
    
    public String getNombre() { return nombre; }
    
    public int getTotalWaits() { return totalWaits.get(); }
    
    public int getTotalSignals() { return totalSignals.get(); }
    
    public int getMaxProcesosEsperando() { return maxProcesosEsperando.get(); }
    
    public long getTiempoCreacion() { return tiempoCreacion; }
    
    /**
     * 📝 Representación en string de la variable de condición
     * 
     * @return Información básica de la variable
     */
    @Override
    public String toString() {
        return String.format("VariableCondicion{nombre='%s', esperando=%d, waits=%d, signals=%d}", 
                           nombre, procesosEsperando.get(), totalWaits.get(), totalSignals.get());
    }
}
