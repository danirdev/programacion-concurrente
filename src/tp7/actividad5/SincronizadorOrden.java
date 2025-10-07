package tp7.actividad5;

import java.util.concurrent.Semaphore;

/**
 * 🔄 SincronizadorOrden - Coordinador de sincronización para R I O OK OK OK
 * 
 * Esta clase coordina la sincronización entre los tres hilos para garantizar
 * el orden específico: R → I → O para las letras, y una barrera para los OK.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class SincronizadorOrden {
    
    // 🚦 Semáforos para orden de letras
    private final Semaphore semI;  // I espera a R
    private final Semaphore semO;  // O espera a I
    
    // 🚧 Barrera para sincronizar OK
    private final Barrera barrera;
    
    // 📊 Buffer para capturar salida
    private final StringBuilder salida;
    private final Object lockSalida;
    
    // 📈 Estadísticas
    private int totalEjecuciones;
    private int ejecucionesExitosas;
    
    /**
     * 🏗️ Constructor del SincronizadorOrden
     */
    public SincronizadorOrden() {
        this.semI = new Semaphore(0, true); // I espera a R
        this.semO = new Semaphore(0, true); // O espera a I
        this.barrera = new Barrera(3);      // 3 hilos deben sincronizarse
        
        this.salida = new StringBuilder();
        this.lockSalida = new Object();
        
        this.totalEjecuciones = 0;
        this.ejecucionesExitosas = 0;
        
        System.out.println("🔄 SincronizadorOrden inicializado");
    }
    
    /**
     * 🔤 Hilo R: Imprime R y habilita I
     * 
     * @param hiloId ID del hilo
     */
    public void ejecutarHiloR(String hiloId) {
        try {
            // Fase 1: Imprimir letra R
            System.out.printf("[%s] 🔤 Imprimiendo: R%n", hiloId);
            agregarSalida(" R ");
            
            // Habilitar I para que pueda ejecutar
            System.out.printf("[%s] 🔔 Señalando a HiloI%n", hiloId);
            semI.release();
            
            // Fase 2: Esperar en barrera
            System.out.printf("[%s] 🚧 Esperando en barrera%n", hiloId);
            barrera.esperar(hiloId);
            
            // Fase 3: Imprimir OK
            System.out.printf("[%s] 🔤 Imprimiendo: OK%n", hiloId);
            agregarSalida(" OK ");
            
        } catch (InterruptedException e) {
            System.err.printf("[%s] ⚠️ Interrumpido: %s%n", hiloId, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🔤 Hilo I: Espera R, imprime I y habilita O
     * 
     * @param hiloId ID del hilo
     */
    public void ejecutarHiloI(String hiloId) {
        try {
            // Fase 1: Esperar a R
            System.out.printf("[%s] ⏳ Esperando señal de HiloR...%n", hiloId);
            semI.acquire();
            System.out.printf("[%s] ✅ Señal recibida de HiloR%n", hiloId);
            
            // Imprimir letra I
            System.out.printf("[%s] 🔤 Imprimiendo: I%n", hiloId);
            agregarSalida(" I ");
            
            // Habilitar O para que pueda ejecutar
            System.out.printf("[%s] 🔔 Señalando a HiloO%n", hiloId);
            semO.release();
            
            // Fase 2: Esperar en barrera
            System.out.printf("[%s] 🚧 Esperando en barrera%n", hiloId);
            barrera.esperar(hiloId);
            
            // Fase 3: Imprimir OK
            System.out.printf("[%s] 🔤 Imprimiendo: OK%n", hiloId);
            agregarSalida(" OK ");
            
        } catch (InterruptedException e) {
            System.err.printf("[%s] ⚠️ Interrumpido: %s%n", hiloId, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🔤 Hilo O: Espera I, imprime O
     * 
     * @param hiloId ID del hilo
     */
    public void ejecutarHiloO(String hiloId) {
        try {
            // Fase 1: Esperar a I
            System.out.printf("[%s] ⏳ Esperando señal de HiloI...%n", hiloId);
            semO.acquire();
            System.out.printf("[%s] ✅ Señal recibida de HiloI%n", hiloId);
            
            // Imprimir letra O
            System.out.printf("[%s] 🔤 Imprimiendo: O%n", hiloId);
            agregarSalida(" O ");
            
            // Fase 2: Esperar en barrera
            System.out.printf("[%s] 🚧 Esperando en barrera%n", hiloId);
            barrera.esperar(hiloId);
            
            // Fase 3: Imprimir OK
            System.out.printf("[%s] 🔤 Imprimiendo: OK%n", hiloId);
            agregarSalida(" OK ");
            
        } catch (InterruptedException e) {
            System.err.printf("[%s] ⚠️ Interrumpido: %s%n", hiloId, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 📝 Agregar texto a la salida (thread-safe)
     * 
     * @param texto Texto a agregar
     */
    private void agregarSalida(String texto) {
        synchronized (lockSalida) {
            salida.append(texto);
        }
    }
    
    /**
     * 📊 Obtener salida actual
     * 
     * @return Salida capturada
     */
    public String getSalida() {
        synchronized (lockSalida) {
            return salida.toString();
        }
    }
    
    /**
     * 🧹 Limpiar salida para nueva ejecución
     */
    public void limpiarSalida() {
        synchronized (lockSalida) {
            salida.setLength(0);
        }
    }
    
    /**
     * 🔄 Resetear sincronizador para nueva ejecución
     */
    public void resetear() {
        limpiarSalida();
        barrera.resetear();
        
        // Drenar semáforos residuales
        semI.drainPermits();
        semO.drainPermits();
        
        System.out.println("🔄 Sincronizador reseteado");
    }
    
    /**
     * ✅ Registrar ejecución exitosa
     */
    public void registrarEjecucionExitosa() {
        totalEjecuciones++;
        ejecucionesExitosas++;
    }
    
    /**
     * ❌ Registrar ejecución fallida
     */
    public void registrarEjecucionFallida() {
        totalEjecuciones++;
    }
    
    /**
     * 📊 Obtener estadísticas del sincronizador
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 🔄 ESTADÍSTICAS SINCRONIZADOR ===\n");
        stats.append(String.format("🔢 Total ejecuciones: %d%n", totalEjecuciones));
        stats.append(String.format("✅ Ejecuciones exitosas: %d%n", ejecucionesExitosas));
        stats.append(String.format("❌ Ejecuciones fallidas: %d%n", totalEjecuciones - ejecucionesExitosas));
        
        if (totalEjecuciones > 0) {
            double tasaExito = (ejecucionesExitosas * 100.0) / totalEjecuciones;
            stats.append(String.format("📊 Tasa de éxito: %.1f%%%n", tasaExito));
        }
        
        stats.append(barrera.getEstadisticas());
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad del sincronizador
     * 
     * @return true si está íntegro
     */
    public boolean verificarIntegridad() {
        boolean barreraIntegra = barrera.verificarIntegridad();
        boolean semaforosValidos = semI.availablePermits() >= 0 && semO.availablePermits() >= 0;
        
        boolean integro = barreraIntegra && semaforosValidos;
        
        if (!integro) {
            System.err.println("❌ INTEGRIDAD SINCRONIZADOR COMPROMETIDA");
        }
        
        return integro;
    }
    
    /**
     * 🚦 Obtener información de semáforos
     * 
     * @return String con información
     */
    public String getInfoSemaforos() {
        return String.format("🚦 Semáforos - semI: %d, semO: %d", 
                           semI.availablePermits(), semO.availablePermits());
    }
    
    // 🔧 Getters
    
    public int getTotalEjecuciones() { return totalEjecuciones; }
    
    public int getEjecucionesExitosas() { return ejecucionesExitosas; }
    
    public Barrera getBarrera() { return barrera; }
    
    /**
     * 📝 Representación en string del sincronizador
     * 
     * @return Información básica
     */
    @Override
    public String toString() {
        return String.format("SincronizadorOrden{ejecuciones=%d, exitosas=%d, salida='%s'}", 
                           totalEjecuciones, ejecucionesExitosas, getSalida().trim());
    }
}
