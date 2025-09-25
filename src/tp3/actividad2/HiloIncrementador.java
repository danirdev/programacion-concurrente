package tp3.actividad2;

/**
 * Hilo que incrementa un contador 100 veces
 * Hereda de Thread para implementación con hilos
 * @author PC2025
 */
public class HiloIncrementador extends Thread {
    
    private Contador contador;
    private String nombre;
    
    /**
     * Constructor del hilo incrementador
     * @param contador referencia al contador compartido
     * @param nombre nombre identificador del hilo
     */
    public HiloIncrementador(Contador contador, String nombre) {
        this.contador = contador;
        this.nombre = nombre;
    }
    
    /**
     * Constructor simplificado
     * @param contador referencia al contador compartido
     */
    public HiloIncrementador(Contador contador) {
        this(contador, "HiloIncrementador");
    }
    
    /**
     * Método run que ejecuta el hilo
     * Incrementa el contador 100 veces
     */
    @Override
    public void run() {
        System.out.println("[" + nombre + "] Iniciando incrementos...");
        
        for (int i = 0; i < 100; i++) {
            int valorAntes = contador.getValor();
            contador.incrementar();
            int valorDespues = contador.getValor();
            
            // Opcional: mostrar progreso cada 25 incrementos
            if ((i + 1) % 25 == 0) {
                System.out.println("[" + nombre + "] Incremento " + (i + 1) + 
                                 "/100 - Valor: " + valorDespues);
            }
            
            // Pequeña pausa para aumentar la probabilidad de race conditions
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[" + nombre + "] Hilo interrumpido");
                return;
            }
        }
        
        System.out.println("[" + nombre + "] Completado - Valor final: " + 
                          contador.getValor());
    }
    
    /**
     * Obtiene el nombre del hilo
     * @return nombre del hilo
     */
    public String getNombreHilo() {
        return nombre;
    }
}
