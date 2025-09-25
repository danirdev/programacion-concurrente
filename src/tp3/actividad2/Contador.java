package tp3.actividad2;

/**
 * Clase Contador sin sincronización - demuestra race conditions
 * Esta implementación NO es thread-safe y puede producir resultados incorrectos
 * @author PC2025
 */
public class Contador {
    
    private int valor;
    
    /**
     * Constructor que inicializa el contador
     * @param valorInicial valor inicial del contador
     */
    public Contador(int valorInicial) {
        this.valor = valorInicial;
    }
    
    /**
     * Incrementa el contador en 1
     * ATENCIÓN: Esta operación NO es atómica y puede causar race conditions
     */
    public void incrementar() {
        // Esta operación se descompone en:
        // 1. Leer valor actual
        // 2. Incrementar en 1
        // 3. Escribir nuevo valor
        valor++;
    }
    
    /**
     * Decrementa el contador en 1
     * ATENCIÓN: Esta operación NO es atómica y puede causar race conditions
     */
    public void decrementar() {
        // Esta operación se descompone en:
        // 1. Leer valor actual
        // 2. Decrementar en 1
        // 3. Escribir nuevo valor
        valor--;
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
     * Representación en String del contador
     * @return valor del contador como String
     */
    @Override
    public String toString() {
        return "Contador{valor=" + valor + "}";
    }
}
