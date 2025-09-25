package tp3.actividad2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Contador con sincronización - versión thread-safe
 * Demuestra diferentes métodos de sincronización
 * @author PC2025
 */
public class ContadorSincronizado {
    
    private int valor;
    private AtomicInteger valorAtomico;
    
    /**
     * Constructor que inicializa el contador
     * @param valorInicial valor inicial del contador
     */
    public ContadorSincronizado(int valorInicial) {
        this.valor = valorInicial;
        this.valorAtomico = new AtomicInteger(valorInicial);
    }
    
    /**
     * Incrementa el contador usando synchronized
     * Esta versión ES thread-safe
     */
    public synchronized void incrementarSincronizado() {
        valor++;
    }
    
    /**
     * Decrementa el contador usando synchronized
     * Esta versión ES thread-safe
     */
    public synchronized void decrementarSincronizado() {
        valor--;
    }
    
    /**
     * Incrementa el contador usando AtomicInteger
     * Esta versión ES thread-safe y más eficiente
     */
    public void incrementarAtomico() {
        valorAtomico.incrementAndGet();
    }
    
    /**
     * Decrementa el contador usando AtomicInteger
     * Esta versión ES thread-safe y más eficiente
     */
    public void decrementarAtomico() {
        valorAtomico.decrementAndGet();
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
     * Representación en String del contador
     * @return valores de ambos contadores
     */
    @Override
    public String toString() {
        return "ContadorSincronizado{" +
               "valorSincronizado=" + getValorSincronizado() +
               ", valorAtomico=" + getValorAtomico() +
               "}";
    }
}
