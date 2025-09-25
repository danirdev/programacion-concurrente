package tp1.actividad4;

/**
 * Clase Cuadrilatero que hereda de Forma
 * Implementa los métodos abstractos area() y perimetro()
 * Representa un cuadrilátero con 4 lados
 * @author PC2025
 */
public class Cuadrilatero extends Forma {
    
    // Atributos para los 4 lados del cuadrilátero
    private double lado1;
    private double lado2;
    private double lado3;
    private double lado4;
    
    // Atributos adicionales para cálculo de área (base y altura para rectángulos/cuadrados)
    private double base;
    private double altura;
    
    /**
     * Constructor por defecto
     * Crea un cuadrado de lado 1
     */
    public Cuadrilatero() {
        super("Cuadrado");
        this.lado1 = this.lado2 = this.lado3 = this.lado4 = 1.0;
        this.base = this.altura = 1.0;
    }
    
    /**
     * Constructor para cuadrado
     * @param nombreForma Nombre de la forma
     * @param lado Longitud del lado del cuadrado
     */
    public Cuadrilatero(String nombreForma, double lado) {
        super(nombreForma);
        this.lado1 = this.lado2 = this.lado3 = this.lado4 = Math.abs(lado);
        this.base = this.altura = Math.abs(lado);
    }
    
    /**
     * Constructor para rectángulo
     * @param nombreForma Nombre de la forma
     * @param base Base del rectángulo
     * @param altura Altura del rectángulo
     */
    public Cuadrilatero(String nombreForma, double base, double altura) {
        super(nombreForma);
        this.base = Math.abs(base);
        this.altura = Math.abs(altura);
        this.lado1 = this.lado3 = this.base;  // lados horizontales
        this.lado2 = this.lado4 = this.altura; // lados verticales
    }
    
    /**
     * Constructor para cuadrilátero general
     * @param nombreForma Nombre de la forma
     * @param lado1 Primer lado
     * @param lado2 Segundo lado
     * @param lado3 Tercer lado
     * @param lado4 Cuarto lado
     */
    public Cuadrilatero(String nombreForma, double lado1, double lado2, double lado3, double lado4) {
        super(nombreForma);
        this.lado1 = Math.abs(lado1);
        this.lado2 = Math.abs(lado2);
        this.lado3 = Math.abs(lado3);
        this.lado4 = Math.abs(lado4);
        
        // Para el cálculo del área, asumimos que es un rectángulo si los lados opuestos son iguales
        if (this.lado1 == this.lado3 && this.lado2 == this.lado4) {
            this.base = this.lado1;
            this.altura = this.lado2;
        } else {
            // Para cuadriláteros irregulares, usamos una aproximación
            this.base = (this.lado1 + this.lado3) / 2.0;
            this.altura = (this.lado2 + this.lado4) / 2.0;
        }
    }
    
    /**
     * Implementación del método abstracto area()
     * Calcula el área del cuadrilátero
     * @return área del cuadrilátero
     */
    @Override
    public double area() {
        // Para rectángulos y cuadrados: base * altura
        // Para cuadriláteros irregulares: aproximación usando base y altura promedio
        return base * altura;
    }
    
    /**
     * Implementación del método abstracto perimetro()
     * Calcula el perímetro del cuadrilátero
     * @return perímetro del cuadrilátero
     */
    @Override
    public double perimetro() {
        return lado1 + lado2 + lado3 + lado4;
    }
    
    /**
     * Método para determinar el tipo de cuadrilátero
     * @return tipo de cuadrilátero
     */
    public String determinarTipo() {
        if (lado1 == lado2 && lado2 == lado3 && lado3 == lado4) {
            return "Cuadrado";
        } else if (lado1 == lado3 && lado2 == lado4) {
            return "Rectángulo";
        } else if (lado1 == lado2 && lado3 == lado4) {
            return "Paralelogramo";
        } else {
            return "Cuadrilátero irregular";
        }
    }
    
    /**
     * Sobrescribe el método mostrarInfo para incluir información específica del cuadrilátero
     */
    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL CUADRILÁTERO ===");
        System.out.println("Nombre: " + nombreForma);
        System.out.println("Tipo detectado: " + determinarTipo());
        System.out.println("Lado 1: " + String.format("%.2f", lado1));
        System.out.println("Lado 2: " + String.format("%.2f", lado2));
        System.out.println("Lado 3: " + String.format("%.2f", lado3));
        System.out.println("Lado 4: " + String.format("%.2f", lado4));
        System.out.println("Base (para área): " + String.format("%.2f", base));
        System.out.println("Altura (para área): " + String.format("%.2f", altura));
        System.out.println("Área: " + String.format("%.2f", area()));
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()));
        System.out.println("====================================");
    }
    
    /**
     * Método para verificar si es un cuadrado
     * @return true si es un cuadrado, false en caso contrario
     */
    public boolean esCuadrado() {
        return lado1 == lado2 && lado2 == lado3 && lado3 == lado4;
    }
    
    /**
     * Método para verificar si es un rectángulo
     * @return true si es un rectángulo, false en caso contrario
     */
    public boolean esRectangulo() {
        return lado1 == lado3 && lado2 == lado4;
    }
    
    // Getters y Setters
    public double getLado1() {
        return lado1;
    }
    
    public void setLado1(double lado1) {
        this.lado1 = Math.abs(lado1);
        actualizarBaseAltura();
    }
    
    public double getLado2() {
        return lado2;
    }
    
    public void setLado2(double lado2) {
        this.lado2 = Math.abs(lado2);
        actualizarBaseAltura();
    }
    
    public double getLado3() {
        return lado3;
    }
    
    public void setLado3(double lado3) {
        this.lado3 = Math.abs(lado3);
        actualizarBaseAltura();
    }
    
    public double getLado4() {
        return lado4;
    }
    
    public void setLado4(double lado4) {
        this.lado4 = Math.abs(lado4);
        actualizarBaseAltura();
    }
    
    public double getBase() {
        return base;
    }
    
    public double getAltura() {
        return altura;
    }
    
    /**
     * Método privado para actualizar base y altura cuando se modifican los lados
     */
    private void actualizarBaseAltura() {
        if (lado1 == lado3 && lado2 == lado4) {
            this.base = lado1;
            this.altura = lado2;
        } else {
            this.base = (lado1 + lado3) / 2.0;
            this.altura = (lado2 + lado4) / 2.0;
        }
    }
}
