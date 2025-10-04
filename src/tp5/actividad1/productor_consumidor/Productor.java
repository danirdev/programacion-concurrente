package tp5.actividad1.productor_consumidor;

import java.util.Random;

/**
 * Clase Productor que implementa Runnable.
 * Produce elementos y los agrega a la cola compartida.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class Productor implements Runnable {
    private final ColaRunnable cola;
    private final int id;
    private final int tiempoMinimo;
    private final int tiempoMaximo;
    private final Random random;
    private int elementosProducidos;
    private static int contadorElementos = 1;
    private volatile boolean activo = true;
    
    /**
     * Constructor del Productor
     * @param cola Cola compartida donde agregar elementos
     * @param id Identificador único del productor
     * @param tiempoMinimo Tiempo mínimo de producción en ms
     * @param tiempoMaximo Tiempo máximo de producción en ms
     */
    public Productor(ColaRunnable cola, int id, int tiempoMinimo, int tiempoMaximo) {
        this.cola = cola;
        this.id = id;
        this.tiempoMinimo = tiempoMinimo;
        this.tiempoMaximo = tiempoMaximo;
        this.random = new Random();
        this.elementosProducidos = 0;
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        System.out.println("Productor " + id + " iniciado en hilo: " + nombreHilo + 
                          ". Tiempo de producción: " + tiempoMinimo + "-" + tiempoMaximo + "ms");
        
        try {
            while (activo && !Thread.currentThread().isInterrupted()) {
                // Simular tiempo de producción
                int tiempoProduccion = tiempoMinimo + random.nextInt(tiempoMaximo - tiempoMinimo + 1);
                Thread.sleep(tiempoProduccion);
                
                // Producir elemento
                int elemento;
                synchronized (Productor.class) {
                    elemento = contadorElementos++;
                }
                
                // Agregar elemento a la cola
                cola.agregar(elemento, id);
                elementosProducidos++;
                
                System.out.println("Productor " + id + " produjo elemento " + elemento + 
                                 " (total producido: " + elementosProducidos + 
                                 ") - Tiempo: " + tiempoProduccion + "ms");
            }
        } catch (InterruptedException e) {
            System.out.println("Productor " + id + " interrumpido. Total elementos producidos: " + elementosProducidos);
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Productor " + id + " finalizó su ejecución");
        }
    }
    
    /**
     * Detiene la ejecución del productor de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el productor está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Obtiene el número de elementos producidos por este productor
     * @return Número de elementos producidos
     */
    public int getElementosProducidos() {
        return elementosProducidos;
    }
    
    /**
     * Obtiene el ID del productor
     * @return ID del productor
     */
    public int getId() {
        return id;
    }
    
    /**
     * Crea y inicia un hilo para este productor
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Productor-" + id);
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este productor sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Productor-" + id);
    }
}
