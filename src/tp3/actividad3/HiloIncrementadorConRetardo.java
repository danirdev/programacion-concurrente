package tp3.actividad3;

/**
 * Hilo que incrementa un contador con retardo aleatorio
 * Hereda de Thread para implementación con hilos
 * @author PC2025
 */
public class HiloIncrementadorConRetardo extends Thread {
    
    private ContadorConRetardo contador;
    private String nombre;
    private int numOperaciones;
    
    /**
     * Constructor del hilo incrementador con retardo
     * @param contador referencia al contador compartido
     * @param nombre nombre identificador del hilo
     * @param numOperaciones número de operaciones a realizar (default: 100)
     */
    public HiloIncrementadorConRetardo(ContadorConRetardo contador, String nombre, int numOperaciones) {
        this.contador = contador;
        this.nombre = nombre;
        this.numOperaciones = numOperaciones;
        this.setName(nombre); // Establecer nombre del hilo para logging
    }
    
    /**
     * Constructor simplificado con 100 operaciones por defecto
     * @param contador referencia al contador compartido
     * @param nombre nombre identificador del hilo
     */
    public HiloIncrementadorConRetardo(ContadorConRetardo contador, String nombre) {
        this(contador, nombre, 100);
    }
    
    /**
     * Constructor más simplificado
     * @param contador referencia al contador compartido
     */
    public HiloIncrementadorConRetardo(ContadorConRetardo contador) {
        this(contador, "HiloIncrementadorConRetardo", 100);
    }
    
    /**
     * Método run que ejecuta el hilo
     * Incrementa el contador el número especificado de veces con retardo aleatorio
     */
    @Override
    public void run() {
        System.out.println("[" + nombre + "] Iniciando " + numOperaciones + 
                          " incrementos con retardo aleatorio...");
        
        long tiempoInicio = System.currentTimeMillis();
        
        for (int i = 0; i < numOperaciones; i++) {
            try {
                contador.incrementar(); // Incluye retardo aleatorio interno
                
                // Mostrar progreso cada 25 operaciones
                if ((i + 1) % 25 == 0) {
                    System.out.println("[" + nombre + "] Progreso: " + (i + 1) + 
                                     "/" + numOperaciones + " - Valor actual: " + 
                                     contador.getValor());
                }
                
            } catch (Exception e) {
                System.err.println("[" + nombre + "] Error en operación " + (i + 1) + ": " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        
        System.out.println("[" + nombre + "] COMPLETADO - " + numOperaciones + 
                          " incrementos en " + tiempoTotal + "ms");
        System.out.println("[" + nombre + "] Valor final observado: " + contador.getValor());
    }
    
    /**
     * Obtiene el nombre del hilo
     * @return nombre del hilo
     */
    public String getNombreHilo() {
        return nombre;
    }
    
    /**
     * Obtiene el número de operaciones configuradas
     * @return número de operaciones
     */
    public int getNumOperaciones() {
        return numOperaciones;
    }
}
