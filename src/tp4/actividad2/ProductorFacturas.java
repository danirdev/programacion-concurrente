package tp4.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase ProductorFacturas que hereda de Thread.
 * Representa el horno que produce facturas continuamente.
 * Tiempo de producción: 1000-1300ms por factura.
 */
public class ProductorFacturas extends Thread {
    private Mostrador mostrador;
    private Random random;
    private int facturasProducidas;
    private final int TIEMPO_MIN = 1000;  // 1000ms mínimo
    private final int TIEMPO_MAX = 1300;  // 1300ms máximo
    private final DateTimeFormatter timeFormatter;
    
    /**
     * Constructor del ProductorFacturas
     * @param mostrador Mostrador compartido donde colocar las facturas
     */
    public ProductorFacturas(Mostrador mostrador) {
        this.mostrador = mostrador;
        this.random = new Random();
        this.facturasProducidas = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.setName("Horno-Facturas");
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno Facturas iniciado (Producción: " + 
                          TIEMPO_MIN + "-" + TIEMPO_MAX + "ms)");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Calcular tiempo de producción aleatorio
                int tiempoProduccion = TIEMPO_MIN + random.nextInt(TIEMPO_MAX - TIEMPO_MIN + 1);
                
                tiempo = LocalTime.now().format(timeFormatter);
                facturasProducidas++;
                System.out.println("[" + tiempo + "] Horno Facturas: Produciendo factura #" + 
                                 facturasProducidas + " (" + tiempoProduccion + "ms)");
                
                // Simular tiempo de producción
                Thread.sleep(tiempoProduccion);
                
                // Agregar factura al mostrador
                mostrador.agregarFactura();
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Horno Facturas: Factura #" + 
                                 facturasProducidas + " lista (" + tiempoProduccion + "ms)");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Facturas detenido. Total producido: " + 
                             facturasProducidas);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el número de facturas producidas
     * @return Número de facturas producidas por este horno
     */
    public int getFacturasProducidas() {
        return facturasProducidas;
    }
}
