package tp5.actividad1.productor_consumidor;

import java.util.Random;

/**
 * Clase Consumidor que implementa Runnable.
 * Consume elementos de la cola compartida.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class Consumidor implements Runnable {
    private final ColaRunnable cola;
    private final int id;
    private final int tiempoMinimo;
    private final int tiempoMaximo;
    private final Random random;
    private int elementosConsumidos;
    private volatile boolean activo = true;
    
    /**
     * Constructor del Consumidor
     * @param cola Cola compartida de donde consumir elementos
     * @param id Identificador único del consumidor
     * @param tiempoMinimo Tiempo mínimo de consumo en ms
     * @param tiempoMaximo Tiempo máximo de consumo en ms
     */
    public Consumidor(ColaRunnable cola, int id, int tiempoMinimo, int tiempoMaximo) {
        this.cola = cola;
        this.id = id;
        this.tiempoMinimo = tiempoMinimo;
        this.tiempoMaximo = tiempoMaximo;
        this.random = new Random();
        this.elementosConsumidos = 0;
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        System.out.println("Consumidor " + id + " iniciado en hilo: " + nombreHilo + 
                          ". Tiempo de consumo: " + tiempoMinimo + "-" + tiempoMaximo + "ms");
        
        try {
            while (activo && !Thread.currentThread().isInterrupted()) {
                // Consumir elemento de la cola
                int elemento = cola.consumir(id);
                elementosConsumidos++;
                
                // Simular tiempo de consumo
                int tiempoConsumo = tiempoMinimo + random.nextInt(tiempoMaximo - tiempoMinimo + 1);
                Thread.sleep(tiempoConsumo);
                
                System.out.println("Consumidor " + id + " procesó elemento " + elemento + 
                                 " (total consumido: " + elementosConsumidos + 
                                 ") - Tiempo: " + tiempoConsumo + "ms");
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor " + id + " interrumpido. Total elementos consumidos: " + elementosConsumidos);
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Consumidor " + id + " finalizó su ejecución");
        }
    }
    
    /**
     * Detiene la ejecución del consumidor de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el consumidor está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Obtiene el número de elementos consumidos por este consumidor
     * @return Número de elementos consumidos
     */
    public int getElementosConsumidos() {
        return elementosConsumidos;
    }
    
    /**
     * Obtiene el ID del consumidor
     * @return ID del consumidor
     */
    public int getId() {
        return id;
    }
    
    /**
     * Crea y inicia un hilo para este consumidor
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Consumidor-" + id);
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este consumidor sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Consumidor-" + id);
    }
}
