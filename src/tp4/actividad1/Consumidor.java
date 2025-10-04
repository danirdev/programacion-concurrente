package tp4.actividad1;

import java.util.Random;

/**
 * Clase Consumidor que hereda de Thread.
 * Consume elementos de la cola compartida.
 */
public class Consumidor extends Thread {
    private Cola cola;
    private int id;
    private int tiempoMinimo;
    private int tiempoMaximo;
    private Random random;
    private int elementosConsumidos;
    
    /**
     * Constructor del Consumidor
     * @param cola Cola compartida de donde consumir elementos
     * @param id Identificador único del consumidor
     * @param tiempoMinimo Tiempo mínimo de consumo en ms
     * @param tiempoMaximo Tiempo máximo de consumo en ms
     */
    public Consumidor(Cola cola, int id, int tiempoMinimo, int tiempoMaximo) {
        this.cola = cola;
        this.id = id;
        this.tiempoMinimo = tiempoMinimo;
        this.tiempoMaximo = tiempoMaximo;
        this.random = new Random();
        this.elementosConsumidos = 0;
        this.setName("Consumidor-" + id);
    }
    
    @Override
    public void run() {
        System.out.println("Consumidor " + id + " iniciado. Tiempo de consumo: " + 
                          tiempoMinimo + "-" + tiempoMaximo + "ms");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Consumir elemento de la cola
                int elemento = cola.consumir();
                
                if (elemento != -1) {
                    elementosConsumidos++;
                    
                    // Simular tiempo de consumo
                    int tiempoConsumo = tiempoMinimo + random.nextInt(tiempoMaximo - tiempoMinimo + 1);
                    Thread.sleep(tiempoConsumo);
                    
                    System.out.println("Consumidor " + id + " consumió elemento " + elemento + 
                                     " (total consumido: " + elementosConsumidos + 
                                     ") - Tiempo: " + tiempoConsumo + "ms");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor " + id + " interrumpido. Total elementos consumidos: " + elementosConsumidos);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el número de elementos consumidos por este consumidor
     * @return Número de elementos consumidos
     */
    public int getElementosConsumidos() {
        return elementosConsumidos;
    }
}
