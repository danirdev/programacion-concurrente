package tp1.actividad6;

/**
 * Interfaz IForma
 * Reemplaza la clase abstracta Forma de la Actividad 4
 * Define la estructura base para todas las formas geométricas usando interfaz
 * @author PC2025
 */
public interface IForma {
    
    // Constantes de la interfaz (públicas, estáticas y finales por defecto)
    String TIPO_GEOMETRICO = "Forma Geométrica";
    double PRECISION_CALCULO = 0.01; // Precisión para comparaciones
    String UNIDAD_MEDIDA = "unidades"; // Unidad de medida por defecto
    
    /**
     * Método para calcular el área
     * Debe ser implementado por las clases que implementen esta interfaz
     * @return área de la forma
     */
    double area();
    
    /**
     * Método para calcular el perímetro
     * Debe ser implementado por las clases que implementen esta interfaz
     * @return perímetro de la forma
     */
    double perimetro();
    
    /**
     * Método para obtener el nombre de la forma
     * Debe ser implementado por las clases que implementen esta interfaz
     * @return nombre de la forma
     */
    String getNombreForma();
    
    /**
     * Método para establecer el nombre de la forma
     * Debe ser implementado por las clases que implementen esta interfaz
     * @param nombreForma nuevo nombre para la forma
     */
    void setNombreForma(String nombreForma);
    
    /**
     * Método por defecto para mostrar información básica de la forma
     * Implementación por defecto que puede ser sobrescrita
     */
    default void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DE LA FORMA (INTERFAZ) ===");
        System.out.println("Tipo: " + TIPO_GEOMETRICO);
        System.out.println("Nombre: " + getNombreForma());
        System.out.println("Área: " + String.format("%.2f", area()) + " " + UNIDAD_MEDIDA + "²");
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()) + " " + UNIDAD_MEDIDA);
        System.out.println("Precisión de cálculo: " + PRECISION_CALCULO);
        System.out.println("==========================================");
    }
    
    /**
     * Método por defecto para comparar áreas entre formas
     * @param otraForma otra forma para comparar
     * @return 1 si esta forma es mayor, -1 si es menor, 0 si son iguales
     */
    default int compararArea(IForma otraForma) {
        if (otraForma == null) return 1;
        double diferencia = this.area() - otraForma.area();
        if (Math.abs(diferencia) < PRECISION_CALCULO) return 0;
        return diferencia > 0 ? 1 : -1;
    }
    
    /**
     * Método por defecto para comparar perímetros entre formas
     * @param otraForma otra forma para comparar
     * @return 1 si esta forma es mayor, -1 si es menor, 0 si son iguales
     */
    default int compararPerimetro(IForma otraForma) {
        if (otraForma == null) return 1;
        double diferencia = this.perimetro() - otraForma.perimetro();
        if (Math.abs(diferencia) < PRECISION_CALCULO) return 0;
        return diferencia > 0 ? 1 : -1;
    }
    
    /**
     * Método estático para obtener información sobre la interfaz
     * @return información sobre la interfaz
     */
    static String getInfoInterfaz() {
        return "Interfaz IForma - Define métodos para formas geométricas";
    }
    
    /**
     * Método estático para validar si un valor es positivo
     * @param valor valor a validar
     * @return true si es positivo, false en caso contrario
     */
    static boolean esValorPositivo(double valor) {
        return valor > 0;
    }
}
