package tp7.actividad6;

import java.util.Random;

/**
 * 🚶 Cliente - Thread que representa a un cliente
 * 
 * Esta clase implementa el comportamiento de un cliente que llega a la barbería,
 * intenta entrar (puede ser rechazado si no hay espacio), espera su turno,
 * recibe el corte de pelo y se retira.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Cliente extends Thread {
    
    // 🏷️ Identificación del cliente
    private final String clienteId;
    private final int numeroCliente;
    
    // 💈 Referencia a la barbería
    private final Barberia barberia;
    
    // ⚙️ Configuración
    private final int tiempoCorteEsperado;
    
    // 🎲 Generador aleatorio
    private final Random random;
    
    // 📊 Estado del cliente
    private boolean atendido;
    private boolean rechazado;
    private long tiempoEspera;
    private long tiempoTotal;
    
    /**
     * 🏗️ Constructor del Cliente
     * 
     * @param numeroCliente Número del cliente
     * @param barberia Referencia a la barbería
     * @param tiempoCorteEsperado Tiempo esperado de corte (ms)
     */
    public Cliente(int numeroCliente, Barberia barberia, int tiempoCorteEsperado) {
        this.numeroCliente = numeroCliente;
        this.clienteId = "Cliente #" + numeroCliente;
        this.barberia = barberia;
        this.tiempoCorteEsperado = tiempoCorteEsperado;
        
        this.random = new Random();
        this.atendido = false;
        this.rechazado = false;
        this.tiempoEspera = 0;
        this.tiempoTotal = 0;
        
        this.setName("Cliente-" + numeroCliente);
    }
    
    /**
     * 🏃‍♂️ Método principal del thread cliente
     */
    @Override
    public void run() {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // 🚶 Intentar entrar a la barbería
            boolean puedeEntrar = barberia.clienteIntentaEntrar(clienteId);
            
            if (puedeEntrar) {
                // ✅ Entró a la barbería
                long tiempoAntesCorte = System.currentTimeMillis();
                tiempoEspera = tiempoAntesCorte - tiempoInicio;
                
                // ✂️ Recibir corte de pelo
                barberia.clienteRecibeCorte(clienteId, tiempoCorteEsperado);
                
                atendido = true;
                rechazado = false;
                
            } else {
                // ❌ Fue rechazado
                atendido = false;
                rechazado = true;
            }
            
        } catch (InterruptedException e) {
            System.err.printf("⚠️ %s interrumpido: %s%n", clienteId, e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            tiempoTotal = System.currentTimeMillis() - tiempoInicio;
        }
    }
    
    /**
     * 📊 Obtener estadísticas del cliente
     * 
     * @return Array con [atendido, rechazado, tiempoEspera, tiempoTotal]
     */
    public double[] getEstadisticas() {
        return new double[]{
            atendido ? 1.0 : 0.0,
            rechazado ? 1.0 : 0.0,
            tiempoEspera,
            tiempoTotal
        };
    }
    
    // 🔧 Getters
    
    public String getClienteId() { return clienteId; }
    
    public int getNumeroCliente() { return numeroCliente; }
    
    public boolean isAtendido() { return atendido; }
    
    public boolean isRechazado() { return rechazado; }
    
    public long getTiempoEspera() { return tiempoEspera; }
    
    public long getTiempoTotal() { return tiempoTotal; }
    
    /**
     * 📝 Representación en string del cliente
     * 
     * @return Información del cliente
     */
    @Override
    public String toString() {
        String estado = atendido ? "ATENDIDO" : (rechazado ? "RECHAZADO" : "ESPERANDO");
        return String.format("Cliente{#%d, estado=%s, espera=%dms}", 
                           numeroCliente, estado, tiempoEspera);
    }
}
