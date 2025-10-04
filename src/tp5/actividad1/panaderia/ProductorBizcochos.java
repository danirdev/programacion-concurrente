package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase ProductorBizcochos que implementa Runnable.
 * Representa el horno que produce bizcochos continuamente.
 * Tiempo de producción: 400-600ms por bizcocho.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class ProductorBizcochos implements Runnable {
    private final MostradorRunnable mostrador;
    private final int id;
    private final Random random;
    private int bizcochosProcidos;
    private final int TIEMPO_MIN = 400;  // 400ms mínimo
    private final int TIEMPO_MAX = 600;  // 600ms máximo
    private final DateTimeFormatter timeFormatter;
    private volatile boolean activo = true;
    
    /**
     * Constructor del ProductorBizcochos
     * @param mostrador Mostrador compartido donde colocar los bizcochos
     * @param id Identificador del horno
     */
    public ProductorBizcochos(MostradorRunnable mostrador, int id) {
        this.mostrador = mostrador;
        this.id = id;
        this.random = new Random();
        this.bizcochosProcidos = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno Bizcochos-" + id + " iniciado en hilo: " + nombreHilo + 
                          " (Producción: " + TIEMPO_MIN + "-" + TIEMPO_MAX + "ms)");
        
        try {
            while (activo && !Thread.currentThread().isInterrupted()) {
                // Calcular tiempo de producción aleatorio
                int tiempoProduccion = TIEMPO_MIN + random.nextInt(TIEMPO_MAX - TIEMPO_MIN + 1);
                
                tiempo = LocalTime.now().format(timeFormatter);
                bizcochosProcidos++;
                System.out.println("[" + tiempo + "] Horno Bizcochos-" + id + 
                                 ": Produciendo bizcocho #" + bizcochosProcidos + 
                                 " (" + tiempoProduccion + "ms)");
                
                // Simular tiempo de producción
                Thread.sleep(tiempoProduccion);
                
                // Agregar bizcocho al mostrador
                mostrador.agregarBizcocho(id);
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Horno Bizcochos-" + id + 
                                 ": Bizcocho #" + bizcochosProcidos + " listo");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Bizcochos-" + id + 
                             " interrumpido. Total producido: " + bizcochosProcidos);
            Thread.currentThread().interrupt();
        } finally {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Bizcochos-" + id + " finalizó su ejecución");
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
     * Obtiene el número de bizcochos producidos
     * @return Número de bizcochos producidos por este horno
     */
    public int getBizcochosProcidos() {
        return bizcochosProcidos;
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
        Thread hilo = new Thread(this, "Horno-Bizcochos-" + id);
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este productor sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Horno-Bizcochos-" + id);
    }
    
    /**
     * Obtiene información del estado del horno
     * @return String con información del horno
     */
    public String getInfo() {
        return String.format("Horno-Bizcochos-%d[Producidos:%d, Activo:%s]", 
                           id, bizcochosProcidos, activo ? "Sí" : "No");
    }
}
