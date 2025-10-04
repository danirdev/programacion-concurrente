package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase ProductorFacturas que implementa Runnable.
 * Representa el horno que produce facturas continuamente.
 * Tiempo de producción: 1000-1300ms por factura.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class ProductorFacturas implements Runnable {
    private final MostradorRunnable mostrador;
    private final int id;
    private final Random random;
    private int facturasProducidas;
    private final int TIEMPO_MIN = 1000;  // 1000ms mínimo
    private final int TIEMPO_MAX = 1300;  // 1300ms máximo
    private final DateTimeFormatter timeFormatter;
    private volatile boolean activo = true;
    
    /**
     * Constructor del ProductorFacturas
     * @param mostrador Mostrador compartido donde colocar las facturas
     * @param id Identificador del horno
     */
    public ProductorFacturas(MostradorRunnable mostrador, int id) {
        this.mostrador = mostrador;
        this.id = id;
        this.random = new Random();
        this.facturasProducidas = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno Facturas-" + id + " iniciado en hilo: " + nombreHilo + 
                          " (Producción: " + TIEMPO_MIN + "-" + TIEMPO_MAX + "ms)");
        
        try {
            while (activo && !Thread.currentThread().isInterrupted()) {
                // Calcular tiempo de producción aleatorio
                int tiempoProduccion = TIEMPO_MIN + random.nextInt(TIEMPO_MAX - TIEMPO_MIN + 1);
                
                tiempo = LocalTime.now().format(timeFormatter);
                facturasProducidas++;
                System.out.println("[" + tiempo + "] Horno Facturas-" + id + 
                                 ": Produciendo factura #" + facturasProducidas + 
                                 " (" + tiempoProduccion + "ms)");
                
                // Simular tiempo de producción
                Thread.sleep(tiempoProduccion);
                
                // Agregar factura al mostrador
                mostrador.agregarFactura(id);
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Horno Facturas-" + id + 
                                 ": Factura #" + facturasProducidas + " lista");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Facturas-" + id + 
                             " interrumpido. Total producido: " + facturasProducidas);
            Thread.currentThread().interrupt();
        } finally {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Facturas-" + id + " finalizó su ejecución");
        }
    }
    
    /**
     * Detiene la producción de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el horno está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Obtiene el número de facturas producidas
     * @return Número de facturas producidas por este horno
     */
    public int getFacturasProducidas() {
        return facturasProducidas;
    }
    
    /**
     * Obtiene el ID del horno
     * @return ID del horno
     */
    public int getId() {
        return id;
    }
    
    /**
     * Crea y inicia un hilo para este productor
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Horno-Facturas-" + id);
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este productor sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Horno-Facturas-" + id);
    }
    
    /**
     * Obtiene información del estado del horno
     * @return String con información del horno
     */
    public String getInfo() {
        return String.format("Horno-Facturas-%d[Producidas:%d, Activo:%s]", 
                           id, facturasProducidas, activo ? "Sí" : "No");
    }
}
