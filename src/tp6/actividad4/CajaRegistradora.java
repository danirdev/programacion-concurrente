package tp6.actividad4;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase CajaRegistradora que representa una caja individual del supermercado.
 * Aunque el control principal se hace con semáforos en la clase Supermercado,
 * esta clase permite análisis detallado por caja individual.
 */
public class CajaRegistradora {
    
    private final int numeroCaja;
    private final DateTimeFormatter timeFormatter;
    
    // Estado de la caja
    private boolean ocupada;
    private int clientesAtendidos;
    private long tiempoTotalAtencion;
    private long tiempoInicioOperacion;
    private int clienteActual;
    
    /**
     * Constructor de CajaRegistradora
     * 
     * @param numeroCaja Número de la caja (1, 2, o 3)
     */
    public CajaRegistradora(int numeroCaja) {
        this.numeroCaja = numeroCaja;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.ocupada = false;
        this.clientesAtendidos = 0;
        this.tiempoTotalAtencion = 0;
        this.tiempoInicioOperacion = System.currentTimeMillis();
        this.clienteActual = -1;
        
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🏪 Caja-" + numeroCaja + " inicializada y disponible");
    }
    
    /**
     * Método para procesar el pago de un cliente en esta caja específica.
     * 
     * @param clienteId ID del cliente
     * @param tiempoPago Tiempo que tardará el pago en milisegundos
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public synchronized void procesarPago(int clienteId, long tiempoPago) throws InterruptedException {
        if (ocupada) {
            throw new IllegalStateException("Caja-" + numeroCaja + " ya está ocupada");
        }
        
        // Marcar caja como ocupada
        ocupada = true;
        clienteActual = clienteId;
        
        try {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 💳 Caja-" + numeroCaja + 
                             ": Procesando pago de Cliente " + clienteId + 
                             " (" + String.format("%.1f", tiempoPago/1000.0) + "s)");
            
            // Simular tiempo de pago
            Thread.sleep(tiempoPago);
            
            // Actualizar estadísticas
            clientesAtendidos++;
            tiempoTotalAtencion += tiempoPago;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ Caja-" + numeroCaja + 
                             ": Cliente " + clienteId + " completó el pago " +
                             "(Total atendidos: " + clientesAtendidos + ")");
            
        } finally {
            // Liberar caja
            ocupada = false;
            clienteActual = -1;
        }
    }
    
    /**
     * Verifica si la caja está disponible
     * 
     * @return true si está disponible, false si está ocupada
     */
    public synchronized boolean estaDisponible() {
        return !ocupada;
    }
    
    /**
     * Verifica si la caja está ocupada
     * 
     * @return true si está ocupada, false si está disponible
     */
    public synchronized boolean estaOcupada() {
        return ocupada;
    }
    
    /**
     * Obtiene el número de la caja
     * 
     * @return Número de la caja
     */
    public int getNumeroCaja() {
        return numeroCaja;
    }
    
    /**
     * Obtiene el número de clientes atendidos
     * 
     * @return Número de clientes atendidos
     */
    public synchronized int getClientesAtendidos() {
        return clientesAtendidos;
    }
    
    /**
     * Obtiene el tiempo total de atención acumulado
     * 
     * @return Tiempo total en milisegundos
     */
    public synchronized long getTiempoTotalAtencion() {
        return tiempoTotalAtencion;
    }
    
    /**
     * Calcula el tiempo promedio de atención por cliente
     * 
     * @return Tiempo promedio en milisegundos
     */
    public synchronized double getTiempoPromedioAtencion() {
        if (clientesAtendidos == 0) {
            return 0.0;
        }
        return tiempoTotalAtencion / (double) clientesAtendidos;
    }
    
    /**
     * Calcula la utilización de la caja (tiempo ocupada vs tiempo total)
     * 
     * @return Porcentaje de utilización (0-100)
     */
    public synchronized double calcularUtilizacion() {
        long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
        
        if (tiempoOperacion == 0) {
            return 0.0;
        }
        
        return (tiempoTotalAtencion * 100.0) / tiempoOperacion;
    }
    
    /**
     * Calcula el throughput de la caja (clientes por minuto)
     * 
     * @return Throughput en clientes/minuto
     */
    public synchronized double calcularThroughput() {
        long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
        
        if (tiempoOperacion == 0) {
            return 0.0;
        }
        
        return (clientesAtendidos * 60000.0) / tiempoOperacion;
    }
    
    /**
     * Obtiene el ID del cliente que está siendo atendido actualmente
     * 
     * @return ID del cliente actual, -1 si no hay cliente
     */
    public synchronized int getClienteActual() {
        return clienteActual;
    }
    
    /**
     * Obtiene estadísticas detalladas de la caja
     * 
     * @return Array con [clientesAtendidos, tiempoTotalAtencion, tiempoPromedioAtencion, utilizacion, throughput]
     */
    public synchronized double[] getEstadisticasDetalladas() {
        return new double[]{
            clientesAtendidos,
            tiempoTotalAtencion,
            getTiempoPromedioAtencion(),
            calcularUtilizacion(),
            calcularThroughput()
        };
    }
    
    /**
     * Muestra el estado actual de la caja
     */
    public synchronized void mostrarEstado() {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("\n[" + tiempo + "] === ESTADO CAJA-" + numeroCaja + " ===");
        System.out.println("Estado: " + (ocupada ? "🔴 OCUPADA" : "🟢 DISPONIBLE"));
        
        if (ocupada) {
            System.out.println("Cliente actual: " + clienteActual);
        }
        
        System.out.println("Clientes atendidos: " + clientesAtendidos);
        System.out.println("Tiempo total atención: " + String.format("%.1f", tiempoTotalAtencion/1000.0) + "s");
        
        if (clientesAtendidos > 0) {
            System.out.println("Tiempo promedio: " + String.format("%.1f", getTiempoPromedioAtencion()) + "ms");
            System.out.println("Utilización: " + String.format("%.1f%%", calcularUtilizacion()));
            System.out.println("Throughput: " + String.format("%.1f", calcularThroughput()) + " clientes/min");
        }
        
        System.out.println("=".repeat(20 + String.valueOf(numeroCaja).length()));
    }
    
    /**
     * Obtiene el estado actual como texto
     * 
     * @return Estado actual de la caja
     */
    public synchronized String getEstadoTexto() {
        if (ocupada) {
            return "🔴 OCUPADA (Cliente " + clienteActual + ")";
        } else {
            return "🟢 DISPONIBLE";
        }
    }
    
    /**
     * Obtiene información resumida de la caja
     * 
     * @return String con información resumida
     */
    public synchronized String getInfoResumida() {
        return String.format("Caja-%d[%s, Atendidos:%d, Util:%.1f%%]", 
                           numeroCaja, ocupada ? "OCUPADA" : "LIBRE", 
                           clientesAtendidos, calcularUtilizacion());
    }
    
    /**
     * Compara el rendimiento con otra caja
     * 
     * @param otraCaja Caja a comparar
     * @return String con comparación de rendimiento
     */
    public synchronized String compararCon(CajaRegistradora otraCaja) {
        double miThroughput = calcularThroughput();
        double otroThroughput = otraCaja.calcularThroughput();
        
        if (miThroughput > otroThroughput * 1.1) {
            return "🏆 Caja-" + numeroCaja + " supera a Caja-" + otraCaja.getNumeroCaja();
        } else if (otroThroughput > miThroughput * 1.1) {
            return "📉 Caja-" + numeroCaja + " por debajo de Caja-" + otraCaja.getNumeroCaja();
        } else {
            return "⚖️ Caja-" + numeroCaja + " similar a Caja-" + otraCaja.getNumeroCaja();
        }
    }
    
    /**
     * Obtiene el tiempo de operación desde que se inicializó
     * 
     * @return Tiempo en milisegundos
     */
    public long getTiempoOperacion() {
        return System.currentTimeMillis() - tiempoInicioOperacion;
    }
    
    /**
     * Reinicia las estadísticas de la caja
     */
    public synchronized void reiniciarEstadisticas() {
        clientesAtendidos = 0;
        tiempoTotalAtencion = 0;
        tiempoInicioOperacion = System.currentTimeMillis();
        
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🔄 Caja-" + numeroCaja + " estadísticas reiniciadas");
    }
    
    /**
     * Método toString para representación de la caja
     * 
     * @return Representación string de la caja
     */
    @Override
    public String toString() {
        return String.format("CajaRegistradora[Num:%d, Ocupada:%s, Atendidos:%d, ClienteActual:%d]", 
                           numeroCaja, ocupada, clientesAtendidos, clienteActual);
    }
}
