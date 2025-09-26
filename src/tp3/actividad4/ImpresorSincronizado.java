package tp3.actividad4;

/**
 * Versión sincronizada del impresor de caracteres
 * Demuestra control de concurrencia para salida ordenada
 * @author PC2025
 */
public class ImpresorSincronizado extends Thread {
    
    private String frase;
    private int numeroHilo;
    private static Object lockGlobal = new Object();
    private int pausaEntreCar;
    
    /**
     * Constructor del impresor sincronizado
     * @param frase frase a imprimir
     * @param numeroHilo número identificador del hilo
     * @param pausaEntreCar pausa entre caracteres en ms
     */
    public ImpresorSincronizado(String frase, int numeroHilo, int pausaEntreCar) {
        this.frase = frase;
        this.numeroHilo = numeroHilo;
        this.pausaEntreCar = pausaEntreCar;
        this.setName("HiloSync-" + numeroHilo);
    }
    
    /**
     * Constructor simplificado
     * @param frase frase a imprimir
     * @param numeroHilo número identificador del hilo
     */
    public ImpresorSincronizado(String frase, int numeroHilo) {
        this(frase, numeroHilo, 20);
    }
    
    /**
     * Método run sincronizado
     * Solo un hilo puede imprimir a la vez
     */
    @Override
    public void run() {
        synchronized (lockGlobal) {
            System.out.print("[Hilo-" + numeroHilo + "] ");
            
            try {
                for (int i = 0; i < frase.length(); i++) {
                    char caracter = frase.charAt(i);
                    System.out.print(caracter);
                    
                    if (pausaEntreCar > 0) {
                        Thread.sleep(pausaEntreCar);
                    }
                }
                
                System.out.println(); // Nueva línea al final
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Hilo-" + numeroHilo + "] Interrumpido");
            }
        }
    }
    
    /**
     * Método para imprimir con separadores visuales
     */
    public void runConSeparadores() {
        synchronized (lockGlobal) {
            System.out.print("┌─ Hilo-" + numeroHilo + " ─┐ ");
            
            try {
                for (int i = 0; i < frase.length(); i++) {
                    char caracter = frase.charAt(i);
                    System.out.print(caracter);
                    
                    if (pausaEntreCar > 0) {
                        Thread.sleep(pausaEntreCar);
                    }
                }
                
                System.out.println(" └─────────┘");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Hilo-" + numeroHilo + "] Interrumpido");
            }
        }
    }
    
    /**
     * Ejecuta múltiples hilos sincronizados
     * @param frase frase a imprimir
     * @param numHilos número de hilos a crear
     */
    public static void ejecutarHilosSincronizados(String frase, int numHilos) {
        System.out.println("=== EJECUCIÓN SINCRONIZADA ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("Hilos: " + numHilos);
        System.out.println("-".repeat(40));
        
        ImpresorSincronizado[] hilos = new ImpresorSincronizado[numHilos];
        
        // Crear hilos
        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new ImpresorSincronizado(frase, i + 1);
        }
        
        // Iniciar hilos
        long tiempoInicio = System.currentTimeMillis();
        
        for (ImpresorSincronizado hilo : hilos) {
            hilo.start();
        }
        
        // Esperar terminación
        try {
            for (ImpresorSincronizado hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long tiempoFin = System.currentTimeMillis();
        
        System.out.println("-".repeat(40));
        System.out.println("✅ Ejecución sincronizada completada en " + 
                          (tiempoFin - tiempoInicio) + "ms");
    }
    
    /**
     * Ejecuta hilos con separadores visuales
     * @param frase frase a imprimir
     * @param numHilos número de hilos a crear
     */
    public static void ejecutarConSeparadores(String frase, int numHilos) {
        System.out.println("=== EJECUCIÓN CON SEPARADORES VISUALES ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("-".repeat(50));
        
        Thread[] hilos = new Thread[numHilos];
        
        // Crear hilos especiales con separadores
        for (int i = 0; i < numHilos; i++) {
            final int numeroHilo = i + 1;
            hilos[i] = new Thread(() -> {
                synchronized (lockGlobal) {
                    System.out.print("┌─ Hilo-" + numeroHilo + " ─┐ ");
                    
                    try {
                        for (int j = 0; j < frase.length(); j++) {
                            char caracter = frase.charAt(j);
                            System.out.print(caracter);
                            Thread.sleep(15);
                        }
                        
                        System.out.println(" └─────────┘");
                        
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            
            hilos[i].setName("HiloSeparador-" + (i + 1));
        }
        
        // Ejecutar
        for (Thread hilo : hilos) {
            hilo.start();
        }
        
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("-".repeat(50));
        System.out.println("✅ Ejecución con separadores completada");
    }
    
    /**
     * Método principal para prueba independiente
     */
    public static void main(String[] args) {
        String frase = "Sincronización Java";
        
        System.out.println("PRUEBA DE IMPRESOR SINCRONIZADO");
        System.out.println("=".repeat(50));
        
        // Prueba básica
        ejecutarHilosSincronizados(frase, 10);
        
        System.out.println();
        
        // Prueba con separadores
        ejecutarConSeparadores(frase, 5);
    }
}
