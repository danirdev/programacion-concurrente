package tp5.actividad4;

/**
 * Clase base Operaciones que proporciona métodos de cálculo matemático.
 * Según el enunciado, contiene un método para cálculo de raíces que será
 * heredado por la clase CalculoConcurrente.
 */
public class Operaciones {
    
    /**
     * Método para cálculo de raíces cuadradas.
     * Este método será heredado por las clases que extiendan Operaciones.
     * 
     * @param numero Número del cual calcular la raíz cuadrada
     * @return Raíz cuadrada del número
     * @throws IllegalArgumentException Si el número es negativo
     */
    protected double calcularRaiz(double numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo: " + numero);
        }
        return Math.sqrt(numero);
    }
    
    /**
     * Método para cálculo de raíces de cualquier índice.
     * Extensión del método básico para mayor flexibilidad.
     * 
     * @param numero Número del cual calcular la raíz
     * @param indice Índice de la raíz (2 = cuadrada, 3 = cúbica, etc.)
     * @return Raíz del número con el índice especificado
     * @throws IllegalArgumentException Si el índice es 0 o si el número es negativo y el índice es par
     */
    protected double calcularRaiz(double numero, int indice) {
        if (indice == 0) {
            throw new IllegalArgumentException("El índice de la raíz no puede ser 0");
        }
        
        if (numero < 0 && indice % 2 == 0) {
            throw new IllegalArgumentException("No se puede calcular una raíz par de un número negativo");
        }
        
        return Math.pow(numero, 1.0 / indice);
    }
    
    /**
     * Método auxiliar para calcular el valor absoluto.
     * Útil para operaciones matemáticas adicionales.
     * 
     * @param numero Número del cual calcular el valor absoluto
     * @return Valor absoluto del número
     */
    protected double valorAbsoluto(double numero) {
        return Math.abs(numero);
    }
    
    /**
     * Método auxiliar para calcular la potencia de un número.
     * Complementa las operaciones matemáticas disponibles.
     * 
     * @param base Base de la potencia
     * @param exponente Exponente de la potencia
     * @return Resultado de base^exponente
     */
    protected double calcularPotencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }
    
    /**
     * Método para redondear un número a un número específico de decimales.
     * Útil para presentar resultados de cálculos de matrices.
     * 
     * @param numero Número a redondear
     * @param decimales Número de decimales a mantener
     * @return Número redondeado
     */
    protected double redondear(double numero, int decimales) {
        double factor = Math.pow(10, decimales);
        return Math.round(numero * factor) / factor;
    }
    
    /**
     * Método para verificar si un número está dentro de un rango.
     * Útil para validaciones en operaciones de matrices.
     * 
     * @param numero Número a verificar
     * @param minimo Valor mínimo del rango (inclusive)
     * @param maximo Valor máximo del rango (inclusive)
     * @return true si el número está en el rango, false en caso contrario
     */
    protected boolean estaEnRango(double numero, double minimo, double maximo) {
        return numero >= minimo && numero <= maximo;
    }
    
    /**
     * Método para calcular el logaritmo natural de un número.
     * Operación matemática adicional disponible para las clases herederas.
     * 
     * @param numero Número del cual calcular el logaritmo natural
     * @return Logaritmo natural del número
     * @throws IllegalArgumentException Si el número es menor o igual a 0
     */
    protected double calcularLogaritmo(double numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El logaritmo solo está definido para números positivos");
        }
        return Math.log(numero);
    }
    
    /**
     * Método para calcular el logaritmo en base 10 de un número.
     * 
     * @param numero Número del cual calcular el logaritmo base 10
     * @return Logaritmo base 10 del número
     * @throws IllegalArgumentException Si el número es menor o igual a 0
     */
    protected double calcularLogaritmo10(double numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El logaritmo solo está definido para números positivos");
        }
        return Math.log10(numero);
    }
    
    /**
     * Método para obtener información sobre las operaciones disponibles.
     * Útil para debugging y documentación.
     * 
     * @return String con la lista de operaciones disponibles
     */
    public String obtenerOperacionesDisponibles() {
        return "Operaciones matemáticas disponibles:\n" +
               "• calcularRaiz(numero) - Raíz cuadrada\n" +
               "• calcularRaiz(numero, indice) - Raíz de cualquier índice\n" +
               "• valorAbsoluto(numero) - Valor absoluto\n" +
               "• calcularPotencia(base, exponente) - Potenciación\n" +
               "• redondear(numero, decimales) - Redondeo\n" +
               "• estaEnRango(numero, min, max) - Verificación de rango\n" +
               "• calcularLogaritmo(numero) - Logaritmo natural\n" +
               "• calcularLogaritmo10(numero) - Logaritmo base 10";
    }
    
    /**
     * Método toString para representación de la clase.
     * 
     * @return Representación string de la clase Operaciones
     */
    @Override
    public String toString() {
        return "Operaciones[Clase base con métodos matemáticos para herencia]";
    }
}
