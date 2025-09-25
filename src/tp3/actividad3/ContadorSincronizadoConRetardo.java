package tp3.actividad3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Contador sincronizado con retardo aleatorio - versión thread-safe
 * Demuestra cómo la sincronización previene race conditions incluso con retardos
 * @author PC2025
 */
public class ContadorSincronizadoConRetardo {
    
    private int valor;
    private AtomicInteger valorAtomico;
    private Random random;
    private static final int RETARDO_MIN = 50;  // 50ms mínimo
    private static final int RETARDO_MAX = 150; // 150ms máximo
    
    /**
     * Constructor que inicializa el contador
     * @param valorInicial valor inicial del contador
     */
    public ContadorSincronizadoConRetardo(int valorInicial) {
        this.valor = valorInicial;
        this.valorAtomico = new AtomicInteger(valorInicial);
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
     * Incrementa el contador usando synchronized con retardo aleatorio
     * Esta versión ES thread-safe
     */
    public synchronized void incrementarSincronizado() {
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
        
        // Incrementar (protegido por synchronized)
        valor = valorActual + 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Incrementó (sync): " + valorActual + " → " + valor + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Decrementa el contador usando synchronized con retardo aleatorio
     * Esta versión ES thread-safe
     */
    public synchronized void decrementarSincronizado() {
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
        
        // Decrementar (protegido por synchronized)
        valor = valorActual - 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Decrementó (sync): " + valorActual + " → " + valor + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Incrementa el contador usando AtomicInteger con retardo aleatorio
     * Esta versión ES thread-safe y más eficiente
     */
    public void incrementarAtomico() {
        // Simular procesamiento con retardo aleatorio ANTES de la operación atómica
        int retardo = generarRetardoAleatorio();
        try {
            Thread.sleep(retardo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Operación atómica
        int valorAnterior = valorAtomico.getAndIncrement();
        int valorNuevo = valorAnterior + 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Incrementó (atomic): " + valorAnterior + " → " + valorNuevo + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Decrementa el contador usando AtomicInteger con retardo aleatorio
     * Esta versión ES thread-safe y más eficiente
     */
    public void decrementarAtomico() {
        // Simular procesamiento con retardo aleatorio ANTES de la operación atómica
        int retardo = generarRetardoAleatorio();
        try {
            Thread.sleep(retardo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Operación atómica
        int valorAnterior = valorAtomico.getAndDecrement();
        int valorNuevo = valorAnterior - 1;
        
        System.out.println("[" + Thread.currentThread().getName() + 
                          "] Decrementó (atomic): " + valorAnterior + " → " + valorNuevo + 
                          " (retardo: " + retardo + "ms)");
    }
    
    /**
     * Obtiene el valor del contador sincronizado
     * @return valor actual
     */
    public synchronized int getValorSincronizado() {
        return valor;
    }
    
    /**
     * Obtiene el valor del contador atómico
     * @return valor actual
     */
    public int getValorAtomico() {
        return valorAtomico.get();
    }
    
    /**
     * Reinicia ambos contadores al valor especificado
     * @param nuevoValor nuevo valor para los contadores
     */
    public synchronized void reiniciar(int nuevoValor) {
        this.valor = nuevoValor;
        this.valorAtomico.set(nuevoValor);
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
     * @return valores de ambos contadores
     */
    @Override
    public String toString() {
        return "ContadorSincronizadoConRetardo{" +
               "valorSincronizado=" + getValorSincronizado() +
               ", valorAtomico=" + getValorAtomico() +
               ", " + getInfoRetardo() +
               "}";
    }
}
