package tp4.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase ProductorBizcochos que hereda de Thread.
 * Representa el horno que produce bizcochos continuamente.
 * Tiempo de producción: 400-600ms por bizcocho.
 */
public class ProductorBizcochos extends Thread {
    private Mostrador mostrador;
    private Random random;
    private int bizcochosProcidos;
    private final int TIEMPO_MIN = 400;  // 400ms mínimo
    private final int TIEMPO_MAX = 600;  // 600ms máximo
    private final DateTimeFormatter timeFormatter;
    
    /**
     * Constructor del ProductorBizcochos
     * @param mostrador Mostrador compartido donde colocar los bizcochos
     */
    public ProductorBizcochos(Mostrador mostrador) {
        this.mostrador = mostrador;
        this.random = new Random();
        this.bizcochosProcidos = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.setName("Horno-Bizcochos");
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno Bizcochos iniciado (Producción: " + 
                          TIEMPO_MIN + "-" + TIEMPO_MAX + "ms)");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Calcular tiempo de producción aleatorio
                int tiempoProduccion = TIEMPO_MIN + random.nextInt(TIEMPO_MAX - TIEMPO_MIN + 1);
                
                tiempo = LocalTime.now().format(timeFormatter);
                bizcochosProcidos++;
                System.out.println("[" + tiempo + "] Horno Bizcochos: Produciendo bizcocho #" + 
                                 bizcochosProcidos + " (" + tiempoProduccion + "ms)");
                
                // Simular tiempo de producción
                Thread.sleep(tiempoProduccion);
                
                // Agregar bizcocho al mostrador
                mostrador.agregarBizcocho();
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] Horno Bizcochos: Bizcocho #" + 
                                 bizcochosProcidos + " listo (" + tiempoProduccion + "ms)");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Horno Bizcochos detenido. Total producido: " + 
                             bizcochosProcidos);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el número de bizcochos producidos
     * @return Número de bizcochos producidos por este horno
     */
    public int getBizcochosProcidos() {
        return bizcochosProcidos;
    }
}
