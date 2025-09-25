package tp3.actividad1;

/**
 * Clase que implementa la impresión secuencial de patrones X e Y
 * @author PC2025
 */
public class ImpresorSecuencial {
    
    /**
     * Imprime el patrón de X: .0X. .1X. .2X. ... .99X.
     */
    public void imprimirPatronX() {
        System.out.println("=== Iniciando patrón X ===");
        for (int i = 0; i <= 99; i++) {
            System.out.print("." + i + "X.");
        }
        System.out.println("\n=== Fin patrón X ===");
    }
    
    /**
     * Imprime el patrón de Y: .0Y. .1Y. .2Y. ... .99Y.
     */
    public void imprimirPatronY() {
        System.out.println("=== Iniciando patrón Y ===");
        for (int i = 0; i <= 99; i++) {
            System.out.print("." + i + "Y.");
        }
        System.out.println("\n=== Fin patrón Y ===");
    }
    
    /**
     * Ejecuta ambos patrones de forma secuencial
     */
    public void ejecutarSecuencial() {
        imprimirPatronX();
        imprimirPatronY();
    }
    
    /**
     * Método principal para probar la clase
     */
    public static void main(String[] args) {
        ImpresorSecuencial impresor = new ImpresorSecuencial();
        System.out.println("EJECUCIÓN SECUENCIAL:");
        impresor.ejecutarSecuencial();
    }
}
