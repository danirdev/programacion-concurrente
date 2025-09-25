package tp1.actividad6;

/**
 * Clase CirculoInterfaz que implementa IForma
 * Equivalente a la clase Circulo de la Actividad 5, pero usando interfaz
 * @author PC2025
 */
public class CirculoInterfaz implements IForma {
    
    // Atributos de instancia
    private String nombreForma;
    private double radio;
    
    // Constante PI para los cálculos
    private static final double PI = Math.PI;
    
    /**
     * Constructor por defecto
     * Crea un círculo de radio 1
     */
    public CirculoInterfaz() {
        this.nombreForma = "Círculo";
        this.radio = 1.0;
    }
    
    /**
     * Constructor con radio
     * @param radio Radio del círculo
     */
    public CirculoInterfaz(double radio) {
        this.nombreForma = "Círculo";
        this.radio = Math.abs(radio); // Asegurar que el radio sea positivo
    }
    
    /**
     * Constructor completo
     * @param nombreForma Nombre de la forma
     * @param radio Radio del círculo
     */
    public CirculoInterfaz(String nombreForma, double radio) {
        this.nombreForma = nombreForma != null ? nombreForma : "Círculo";
        this.radio = Math.abs(radio); // Asegurar que el radio sea positivo
    }
    
    /**
     * Implementación del método area() de la interfaz IForma
     * Calcula el área del círculo usando la fórmula: π × r²
     * @return área del círculo
     */
    @Override
    public double area() {
        return PI * radio * radio;
    }
    
    /**
     * Implementación del método perimetro() de la interfaz IForma
     * Calcula el perímetro (circunferencia) del círculo usando la fórmula: 2 × π × r
     * @return perímetro del círculo
     */
    @Override
    public double perimetro() {
        return 2 * PI * radio;
    }
    
    /**
     * Implementación del método getNombreForma() de la interfaz IForma
     * @return nombre de la forma
     */
    @Override
    public String getNombreForma() {
        return nombreForma;
    }
    
    /**
     * Implementación del método setNombreForma() de la interfaz IForma
     * @param nombreForma nuevo nombre para la forma
     */
    @Override
    public void setNombreForma(String nombreForma) {
        this.nombreForma = nombreForma != null ? nombreForma : "Círculo";
    }
    
    /**
     * Sobrescribe el método mostrarInfo() por defecto de la interfaz
     */
    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL CÍRCULO (INTERFAZ) ===");
        System.out.println("Tipo: " + TIPO_GEOMETRICO);
        System.out.println("Nombre: " + nombreForma);
        System.out.println("Radio: " + String.format("%.2f", radio) + " " + UNIDAD_MEDIDA);
        System.out.println("Diámetro: " + String.format("%.2f", diametro()) + " " + UNIDAD_MEDIDA);
        System.out.println("Área: " + String.format("%.2f", area()) + " " + UNIDAD_MEDIDA + "² (π × r²)");
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()) + " " + UNIDAD_MEDIDA + " (2 × π × r)");
        System.out.println("Valor de π utilizado: " + String.format("%.6f", PI));
        System.out.println("Precisión: " + PRECISION_CALCULO);
        System.out.println("==========================================");
    }
    
    /**
     * Método para calcular el diámetro del círculo
     * @return diámetro del círculo
     */
    public double diametro() {
        return 2 * radio;
    }
    
    /**
     * Método para verificar si dos círculos son iguales (mismo radio)
     * @param otroCirculo Otro círculo para comparar
     * @return true si tienen el mismo radio, false en caso contrario
     */
    public boolean esIgual(CirculoInterfaz otroCirculo) {
        if (otroCirculo == null) return false;
        return Math.abs(this.radio - otroCirculo.radio) < PRECISION_CALCULO;
    }
    
    /**
     * Método para escalar el círculo (multiplicar el radio por un factor)
     * @param factor Factor de escalado
     */
    public void escalar(double factor) {
        if (IForma.esValorPositivo(factor)) {
            this.radio *= factor;
            System.out.println("Círculo escalado por factor " + factor);
            System.out.println("Nuevo radio: " + String.format("%.2f", radio) + " " + UNIDAD_MEDIDA);
        } else {
            System.out.println("Error: El factor de escalado debe ser positivo");
        }
    }
    
    /**
     * Método para validar usando método estático de la interfaz
     * @return true si el radio es válido (positivo)
     */
    public boolean tieneValorValido() {
        return IForma.esValorPositivo(radio);
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
    public static CirculoInterfaz crearPorDiametro(String nombreForma, double diametro) {
        return new CirculoInterfaz(nombreForma, diametro / 2.0);
    }
    
    /**
     * Método estático para crear un círculo a partir del área
     * @param nombreForma Nombre de la forma
     * @param area Área deseada del círculo
     * @return nuevo círculo con el radio calculado
     */
    public static CirculoInterfaz crearPorArea(String nombreForma, double area) {
        double radio = Math.sqrt(area / PI);
        return new CirculoInterfaz(nombreForma, radio);
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
