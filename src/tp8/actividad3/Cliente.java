package tp8.actividad3;

/**
 * 👤 Cliente - Representa un cliente del supermercado
 * 
 * Esta clase encapsula la información de un cliente que está siendo
 * atendido en el supermercado.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Cliente {
    
    // 🏷️ Identificación del cliente
    private final int numeroCliente;
    
    // ⏱️ Tiempos
    private final long tiempoLlegada;
    private long tiempoInicioAtencion;
    private long tiempoFinAtencion;
    private long tiempoAtencion;
    
    // 💳 Caja que lo atendió
    private int numeroCaja;
    
    /**
     * 🏗️ Constructor del Cliente
     * 
     * @param numeroCliente Número identificador del cliente (1-50)
     */
    public Cliente(int numeroCliente) {
        this.numeroCliente = numeroCliente;
        this.tiempoLlegada = System.currentTimeMillis();
        this.tiempoInicioAtencion = 0;
        this.tiempoFinAtencion = 0;
        this.tiempoAtencion = 0;
        this.numeroCaja = 0;
    }
    
    /**
     * 📍 Registrar inicio de atención
     * 
     * @param numeroCaja Número de la caja que lo atiende
     */
    public void iniciarAtencion(int numeroCaja) {
        this.numeroCaja = numeroCaja;
        this.tiempoInicioAtencion = System.currentTimeMillis();
    }
    
    /**
     * ✅ Registrar fin de atención
     */
    public void finalizarAtencion() {
        this.tiempoFinAtencion = System.currentTimeMillis();
        this.tiempoAtencion = tiempoFinAtencion - tiempoInicioAtencion;
    }
    
    /**
     * ⏱️ Obtener tiempo de espera
     * 
     * @return Tiempo de espera en milisegundos
     */
    public long getTiempoEspera() {
        if (tiempoInicioAtencion > 0) {
            return tiempoInicioAtencion - tiempoLlegada;
        }
        return 0;
    }
    
    // 🔧 Getters
    
    public int getNumeroCliente() { return numeroCliente; }
    
    public long getTiempoLlegada() { return tiempoLlegada; }
    
    public long getTiempoInicioAtencion() { return tiempoInicioAtencion; }
    
    public long getTiempoFinAtencion() { return tiempoFinAtencion; }
    
    public long getTiempoAtencion() { return tiempoAtencion; }
    
    public int getNumeroCaja() { return numeroCaja; }
    
    /**
     * 📝 Representación en string del cliente
     * 
     * @return Información del cliente
     */
    @Override
    public String toString() {
        return String.format("Cliente{#%d, caja=%d, atención=%.2fs}", 
                           numeroCliente, numeroCaja, tiempoAtencion / 1000.0);
    }
}
