package tp1.actividad5;

import tp1.actividad4.Forma;

/**
 * Clase Circulo que hereda de Forma
 * Implementa los métodos abstractos area() y perimetro() para un círculo
 * @author PC2025
 */
public class Circulo extends Forma {
    
    // Atributo para el radio del círculo
    private double radio;
    
    // Constante PI para los cálculos
    private static final double PI = Math.PI;
    
    /**
     * Constructor por defecto
     * Crea un círculo de radio 1
     */
    public Circulo() {
        super("Círculo");
        this.radio = 1.0;
    }
    
    /**
     * Constructor con radio
     * @param radio Radio del círculo
     */
    public Circulo(double radio) {
        super("Círculo");
        this.radio = Math.abs(radio); // Asegurar que el radio sea positivo
    }
    
    /**
     * Constructor completo
     * @param nombreForma Nombre de la forma
     * @param radio Radio del círculo
     */
    public Circulo(String nombreForma, double radio) {
        super(nombreForma);
        this.radio = Math.abs(radio); // Asegurar que el radio sea positivo
    }
    
    /**
     * Implementación del método abstracto area()
     * Calcula el área del círculo usando la fórmula: π × r²
     * @return área del círculo
     */
    @Override
    public double area() {
        return PI * radio * radio;
    }
    
    /**
     * Implementación del método abstracto perimetro()
     * Calcula el perímetro (circunferencia) del círculo usando la fórmula: 2 × π × r
     * @return perímetro del círculo
     */
    @Override
    public double perimetro() {
        return 2 * PI * radio;
    }
    
    /**
     * Método para calcular el diámetro del círculo
     * @return diámetro del círculo
     */
    public double diametro() {
        return 2 * radio;
    }
    
    /**
     * Sobrescribe el método mostrarInfo para incluir información específica del círculo
     */
    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL CÍRCULO ===");
        System.out.println("Nombre: " + nombreForma);
        System.out.println("Radio: " + String.format("%.2f", radio));
        System.out.println("Diámetro: " + String.format("%.2f", diametro()));
        System.out.println("Área: " + String.format("%.2f", area()) + " (π × r²)");
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()) + " (2 × π × r)");
        System.out.println("Valor de π utilizado: " + String.format("%.6f", PI));
        System.out.println("===============================");
    }
    
    /**
     * Método para verificar si dos círculos son iguales (mismo radio)
     * @param otroCirculo Otro círculo para comparar
     * @return true si tienen el mismo radio, false en caso contrario
     */
    public boolean esIgual(Circulo otroCirculo) {
        if (otroCirculo == null) return false;
        return Math.abs(this.radio - otroCirculo.radio) < 0.001; // Tolerancia para comparación de doubles
    }
    
    /**
     * Método para comparar áreas con otro círculo
     * @param otroCirculo Otro círculo para comparar
     * @return 1 si este círculo es mayor, -1 si es menor, 0 si son iguales
     */
    public int compararArea(Circulo otroCirculo) {
        if (otroCirculo == null) return 1;
        double diferencia = this.area() - otroCirculo.area();
        if (Math.abs(diferencia) < 0.001) return 0;
        return diferencia > 0 ? 1 : -1;
    }
    
    /**
     * Método para escalar el círculo (multiplicar el radio por un factor)
     * @param factor Factor de escalado
     */
    public void escalar(double factor) {
        if (factor > 0) {
            this.radio *= factor;
            System.out.println("Círculo escalado por factor " + factor);
            System.out.println("Nuevo radio: " + String.format("%.2f", radio));
        } else {
            System.out.println("Error: El factor de escalado debe ser positivo");
        }
    }
    
    // Getter y Setter para radio
    public double getRadio() {
        return radio;
    }
    
    public void setRadio(double radio) {
        this.radio = Math.abs(radio); // Asegurar que el radio sea positivo
    }
    
    /**
     * Método estático para crear un círculo a partir del diámetro
     * @param nombreForma Nombre de la forma
     * @param diametro Diámetro del círculo
     * @return nuevo círculo con el radio calculado
     */
    public static Circulo crearPorDiametro(String nombreForma, double diametro) {
        return new Circulo(nombreForma, diametro / 2.0);
    }
    
    /**
     * Método estático para crear un círculo a partir del área
     * @param nombreForma Nombre de la forma
     * @param area Área deseada del círculo
     * @return nuevo círculo con el radio calculado
     */
    public static Circulo crearPorArea(String nombreForma, double area) {
        double radio = Math.sqrt(area / PI);
        return new Circulo(nombreForma, radio);
    }
    
    /**
     * Override del método toString para representación en cadena
     * @return representación en cadena del círculo
     */
    @Override
    public String toString() {
        return String.format("%s [radio=%.2f, área=%.2f, perímetro=%.2f]", 
                           nombreForma, radio, area(), perimetro());
    }
}
