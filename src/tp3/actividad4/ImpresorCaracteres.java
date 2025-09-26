package tp3.actividad4;

/**
 * Hilo que imprime una frase carácter por carácter
 * Demuestra concurrencia en la salida de texto
 * @author PC2025
 */
public class ImpresorCaracteres extends Thread {
    
    private String frase;
    private int numeroHilo;
    private int pausaEntreCar;
    private boolean mostrarInfo;
    
    /**
     * Constructor completo del impresor de caracteres
     * @param frase frase a imprimir
     * @param numeroHilo número identificador del hilo
     * @param pausaEntreCar pausa en ms entre cada carácter
     * @param mostrarInfo si mostrar información adicional del hilo
     */
    public ImpresorCaracteres(String frase, int numeroHilo, int pausaEntreCar, boolean mostrarInfo) {
        this.frase = frase;
        this.numeroHilo = numeroHilo;
        this.pausaEntreCar = pausaEntreCar;
        this.mostrarInfo = mostrarInfo;
        this.setName("Hilo-" + numeroHilo);
    }
    
    /**
     * Constructor simplificado con pausa por defecto
     * @param frase frase a imprimir
     * @param numeroHilo número identificador del hilo
     */
    public ImpresorCaracteres(String frase, int numeroHilo) {
        this(frase, numeroHilo, 50, false);
    }
    
    /**
     * Constructor con control de información
     * @param frase frase a imprimir
     * @param numeroHilo número identificador del hilo
     * @param mostrarInfo si mostrar información del hilo
     */
    public ImpresorCaracteres(String frase, int numeroHilo, boolean mostrarInfo) {
        this(frase, numeroHilo, 50, mostrarInfo);
    }
    
    /**
     * Método run que ejecuta el hilo
     * Imprime la frase carácter por carácter
     */
    @Override
    public void run() {
        if (mostrarInfo) {
            System.out.println("[Hilo-" + numeroHilo + "] Iniciando impresión de: \"" + frase + "\"");
        }
        
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Imprimir cada carácter de la frase
            for (int i = 0; i < frase.length(); i++) {
                char caracter = frase.charAt(i);
                
                // Imprimir el carácter
                System.out.print(caracter);
                
                // Pausa entre caracteres para hacer visible la concurrencia
                if (pausaEntreCar > 0) {
                    Thread.sleep(pausaEntreCar);
                }
            }
            
            // Agregar salto de línea al final de la frase
            System.out.println(); // Nueva línea después de completar la frase
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (mostrarInfo) {
                System.err.println("[Hilo-" + numeroHilo + "] Interrumpido durante la impresión");
            }
            return;
        }
        
        long tiempoFin = System.currentTimeMillis();
        
        if (mostrarInfo) {
            System.out.println("[Hilo-" + numeroHilo + "] Completado en " + 
                              (tiempoFin - tiempoInicio) + "ms");
        }
    }
    
    /**
     * Versión que imprime con identificador de hilo para cada carácter
     * Útil para debugging y observar intercalación
     */
    public void runConIdentificador() {
        if (mostrarInfo) {
            System.out.println("[Hilo-" + numeroHilo + "] Iniciando impresión con identificador");
        }
        
        try {
            for (int i = 0; i < frase.length(); i++) {
                char caracter = frase.charAt(i);
                
                // Imprimir carácter con identificador de hilo
                System.out.print("[H" + numeroHilo + ":" + caracter + "]");
                
                if (pausaEntreCar > 0) {
                    Thread.sleep(pausaEntreCar);
                }
            }
            
            System.out.println(" [H" + numeroHilo + ":FIN]");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Hilo-" + numeroHilo + "] Interrumpido");
        }
    }
    
    /**
     * Obtiene la frase que imprime este hilo
     * @return frase del hilo
     */
    public String getFrase() {
        return frase;
    }
    
    /**
     * Obtiene el número del hilo
     * @return número del hilo
     */
    public int getNumeroHilo() {
        return numeroHilo;
    }
    
    /**
     * Obtiene la pausa configurada entre caracteres
     * @return pausa en milisegundos
     */
    public int getPausaEntreCar() {
        return pausaEntreCar;
    }
    
    /**
     * Establece una nueva pausa entre caracteres
     * @param pausaEntreCar nueva pausa en milisegundos
     */
    public void setPausaEntreCar(int pausaEntreCar) {
        this.pausaEntreCar = pausaEntreCar;
    }
}
