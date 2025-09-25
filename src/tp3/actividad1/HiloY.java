package tp3.actividad1;

/**
 * Hilo que imprime el patrón Y de forma concurrente
 * Hereda de Thread para implementación con hilos
 * @author PC2025
 */
public class HiloY extends Thread {
    
    private String nombre;
    
    /**
     * Constructor del hilo Y
     * @param nombre nombre identificador del hilo
     */
    public HiloY(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método run que ejecuta el hilo
     * Imprime el patrón .0Y. .1Y. .2Y. ... .99Y.
     */
    @Override
    public void run() {
        System.out.println("[" + nombre + "] Iniciando patrón Y");
        
        for (int i = 0; i <= 99; i++) {
            System.out.print("." + i + "Y.");
            
            // Pequeña pausa para simular trabajo y permitir intercalación
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[" + nombre + "] Hilo interrumpido");
                return;
            }
        }
        
        System.out.println("\n[" + nombre + "] Patrón Y completado");
    }
    
    /**
     * Obtiene el nombre del hilo
     * @return nombre del hilo
     */
    public String getNombreHilo() {
        return nombre;
    }
}
