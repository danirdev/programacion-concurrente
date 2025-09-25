package tp3.actividad1;

/**
 * Hilo que imprime el patrón X de forma concurrente
 * Hereda de Thread para implementación con hilos
 * @author PC2025
 */
public class HiloX extends Thread {
    
    private String nombre;
    
    /**
     * Constructor del hilo X
     * @param nombre nombre identificador del hilo
     */
    public HiloX(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método run que ejecuta el hilo
     * Imprime el patrón .0X. .1X. .2X. ... .99X.
     */
    @Override
    public void run() {
        System.out.println("[" + nombre + "] Iniciando patrón X");
        
        for (int i = 0; i <= 99; i++) {
            System.out.print("." + i + "X.");
            
            // Pequeña pausa para simular trabajo y permitir intercalación
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[" + nombre + "] Hilo interrumpido");
                return;
            }
        }
        
        System.out.println("\n[" + nombre + "] Patrón X completado");
    }
    
    /**
     * Obtiene el nombre del hilo
     * @return nombre del hilo
     */
    public String getNombreHilo() {
        return nombre;
    }
}
