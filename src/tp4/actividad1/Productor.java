package tp4.actividad1;

import java.util.Random;

/**
 * Clase Productor que hereda de Thread.
 * Produce elementos y los agrega a la cola compartida.
 */
public class Productor extends Thread {
    private Cola cola;
    private int id;
    private int tiempoMinimo;
    private int tiempoMaximo;
    private Random random;
    private int elementosProducidos;
    private static int contadorElementos = 1;
    
    /**
     * Constructor del Productor
     * @param cola Cola compartida donde agregar elementos
     * @param id Identificador único del productor
     * @param tiempoMinimo Tiempo mínimo de producción en ms
     * @param tiempoMaximo Tiempo máximo de producción en ms
     */
    public Productor(Cola cola, int id, int tiempoMinimo, int tiempoMaximo) {
        this.cola = cola;
        this.id = id;
        this.tiempoMinimo = tiempoMinimo;
        this.tiempoMaximo = tiempoMaximo;
        this.random = new Random();
        this.elementosProducidos = 0;
        this.setName("Productor-" + id);
    }
    
    @Override
    public void run() {
        System.out.println("Productor " + id + " iniciado. Tiempo de producción: " + 
                          tiempoMinimo + "-" + tiempoMaximo + "ms");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Simular tiempo de producción
                int tiempoProduccion = tiempoMinimo + random.nextInt(tiempoMaximo - tiempoMinimo + 1);
                Thread.sleep(tiempoProduccion);
                
                // Producir elemento
                int elemento;
                synchronized (Productor.class) {
                    elemento = contadorElementos++;
                }
                
                // Agregar elemento a la cola
                cola.agregar(elemento);
                elementosProducidos++;
                
                System.out.println("Productor " + id + " produjo elemento " + elemento + 
                                 " (total producido: " + elementosProducidos + 
                                 ") - Tiempo: " + tiempoProduccion + "ms");
            }
        } catch (InterruptedException e) {
            System.out.println("Productor " + id + " interrumpido. Total elementos producidos: " + elementosProducidos);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el número de elementos producidos por este productor
     * @return Número de elementos producidos
     */
    public int getElementosProducidos() {
        return elementosProducidos;
    }
}
