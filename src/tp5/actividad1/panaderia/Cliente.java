package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase Cliente que implementa Runnable.
 * Representa un cliente que llega a la panadería para comprar productos.
 * Cada cliente necesita 1 bizcocho y 1 factura.
 * Tiempo de compra y retirada: 200-400ms.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class Cliente implements Runnable {
    private final MostradorRunnable mostrador;
    private final int clienteId;
    private final Random random;
    private final int TIEMPO_MIN_COMPRA = 200;  // 200ms mínimo
    private final int TIEMPO_MAX_COMPRA = 400;  // 400ms máximo
    private final DateTimeFormatter timeFormatter;
    private boolean compraExitosa;
    private volatile boolean activo = true;
    
    /**
     * Constructor del Cliente
     * @param mostrador Mostrador donde comprar productos
     * @param clienteId Identificador único del cliente
     */
    public Cliente(MostradorRunnable mostrador, int clienteId) {
        this.mostrador = mostrador;
        this.clienteId = clienteId;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.compraExitosa = false;
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                         " llegó a la panadería (Hilo: " + nombreHilo + ")");
        
        try {
            if (activo) {
                // Intentar comprar productos
                mostrador.comprarProductos(clienteId);
                
                // Simular tiempo de compra y retirada
                int tiempoCompra = TIEMPO_MIN_COMPRA + random.nextInt(TIEMPO_MAX_COMPRA - TIEMPO_MIN_COMPRA + 1);
                Thread.sleep(tiempoCompra);
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                                 " completó la compra y retirada (" + tiempoCompra + "ms)");
                System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                                 " se retiró satisfecho de la panadería");
                
                compraExitosa = true;
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                             " interrumpido durante la compra");
            Thread.currentThread().interrupt();
        } finally {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Cliente-" + clienteId + " finalizó su proceso");
        }
    }
    
    /**
     * Detiene al cliente de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el cliente está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Verifica si el cliente completó su compra exitosamente
     * @return true si la compra fue exitosa, false en caso contrario
     */
    public boolean isCompraExitosa() {
        return compraExitosa;
    }
    
    /**
     * Obtiene el ID del cliente
     * @return ID del cliente
     */
    public int getClienteId() {
        return clienteId;
    }
    
    /**
     * Crea y inicia un hilo para este cliente
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Cliente-" + clienteId);
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este cliente sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Cliente-" + clienteId);
    }
    
    /**
     * Obtiene información del estado del cliente
     * @return String con información del cliente
     */
    public String getInfo() {
        String estado = compraExitosa ? "Compra exitosa" : 
                       activo ? "En proceso" : "Detenido";
        return String.format("Cliente-%d[%s]", clienteId, estado);
    }
}
