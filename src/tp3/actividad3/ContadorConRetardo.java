package tp3.actividad3;

import java.util.Random;

/**
 * Clase Contador con retardo aleatorio - demuestra race conditions amplificadas
 * Esta implementación NO es thread-safe y el retardo aumenta la probabilidad de race conditions
 * @author PC2025
 */
public class ContadorConRetardo {
    
    private int valor;
    private Random random;
    private static final int RETARDO_MIN = 50;  // 50ms mínimo
    private static final int RETARDO_MAX = 150; // 150ms máximo
    
    /**
     * Constructor que inicializa el contador
     * @param valorInicial valor inicial del contador
     */
    public ContadorConRetardo(int valorInicial) {
        this.valor = valorInicial;
        this.random = new Random();
    }
    
    /**
     * Genera un retardo aleatorio entre 50-150ms
     * @return tiempo de retardo en milisegundos
     */
    private int generarRetardoAleatorio() {
        return RETARDO_MIN + random.nextInt(RETARDO_MAX - RETARDO_MIN + 1);
    }
    
    /**
     * Incrementa el contador en 1 con retardo aleatorio
     * ATENCIÓN: Esta operación NO es atómica y el retardo aumenta las race conditions
     */
    public void incrementar() {
        // Leer valor actual
        int valorActual = valor;
        
        // Simular procesamiento con retardo aleatorio
        int retardo = generarRetardoAleatorio();
        try {
            Thread.sleep(retardo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Incrementar y escribir (ventana crítica ampliada por el retardo)
        valor = valorActual + 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Incrementó: " + valorActual + " → " + valor + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Decrementa el contador en 1 con retardo aleatorio
     * ATENCIÓN: Esta operación NO es atómica y el retardo aumenta las race conditions
     */
    public void decrementar() {
        // Leer valor actual
        int valorActual = valor;
        
        // Simular procesamiento con retardo aleatorio
        int retardo = generarRetardoAleatorio();
        try {
            Thread.sleep(retardo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Decrementar y escribir (ventana crítica ampliada por el retardo)
        valor = valorActual - 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Decrementó: " + valorActual + " → " + valor + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Obtiene el valor actual del contador
     * @return valor actual
     */
    public int getValor() {
        return valor;
    }
    
    /**
     * Reinicia el contador al valor especificado
     * @param nuevoValor nuevo valor para el contador
     */
    public void reiniciar(int nuevoValor) {
        this.valor = nuevoValor;
    }
    
    /**
     * Obtiene información sobre el rango de retardo
     * @return información del retardo
     */
    public String getInfoRetardo() {
        return "Retardo aleatorio: " + RETARDO_MIN + "-" + RETARDO_MAX + "ms";
    }
    
    /**
     * Representación en String del contador
     * @return valor del contador como String
     */
    @Override
    public String toString() {
        return "ContadorConRetardo{valor=" + valor + ", " + getInfoRetardo() + "}";
    }
}
