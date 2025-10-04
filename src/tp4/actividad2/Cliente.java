package tp4.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase Cliente que hereda de Thread.
 * Representa un cliente que llega a la panadería para comprar productos.
 * Cada cliente necesita 1 bizcocho y 1 factura.
 * Tiempo de compra y retirada: 200-400ms.
 */
public class Cliente extends Thread {
    private Mostrador mostrador;
    private int clienteId;
    private Random random;
    private final int TIEMPO_MIN_COMPRA = 200;  // 200ms mínimo
    private final int TIEMPO_MAX_COMPRA = 400;  // 400ms máximo
    private final DateTimeFormatter timeFormatter;
    private boolean compraExitosa;
    
    /**
     * Constructor del Cliente
     * @param mostrador Mostrador donde comprar productos
     * @param clienteId Identificador único del cliente
     */
    public Cliente(Mostrador mostrador, int clienteId) {
        this.mostrador = mostrador;
        this.clienteId = clienteId;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.compraExitosa = false;
        this.setName("Cliente-" + clienteId);
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Cliente-" + clienteId + " llegó a la panadería");
        
        try {
            // Intentar comprar productos
            if (mostrador.comprarProductos(clienteId)) {
                // Simular tiempo de compra y retirada
                int tiempoCompra = TIEMPO_MIN_COMPRA + random.nextInt(TIEMPO_MAX_COMPRA - TIEMPO_MIN_COMPRA + 1);
                Thread.sleep(tiempoCompra);
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                                 " completó la compra y retirada (" + tiempoCompra + "ms)");
                System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                                 " se retiró satisfecho de la panadería");
                
                compraExitosa = true;
            } else {
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                                 " no pudo completar la compra (interrumpido)");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                             " interrumpido durante la compra");
            Thread.currentThread().interrupt();
        }
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
}
