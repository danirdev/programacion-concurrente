package tp1.actividad6;

/**
 * Clase CuadrilateroInterfaz que implementa IForma
 * Equivalente a la clase Cuadrilatero de la Actividad 4, pero usando interfaz
 * @author PC2025
 */
public class CuadrilateroInterfaz implements IForma {
    
    // Atributos de instancia
    private String nombreForma;
    private double lado1;
    private double lado2;
    private double lado3;
    private double lado4;
    private double base;
    private double altura;
    
    /**
     * Constructor por defecto
     * Crea un cuadrado de lado 1
     */
    public CuadrilateroInterfaz() {
        this.nombreForma = "Cuadrado";
        this.lado1 = this.lado2 = this.lado3 = this.lado4 = 1.0;
        this.base = this.altura = 1.0;
    }
    
    /**
     * Constructor para cuadrado
     * @param nombreForma Nombre de la forma
     * @param lado Longitud del lado del cuadrado
     */
    public CuadrilateroInterfaz(String nombreForma, double lado) {
        this.nombreForma = nombreForma != null ? nombreForma : "Cuadrado";
        this.lado1 = this.lado2 = this.lado3 = this.lado4 = Math.abs(lado);
        this.base = this.altura = Math.abs(lado);
    }
    
    /**
     * Constructor para rectángulo
     * @param nombreForma Nombre de la forma
     * @param base Base del rectángulo
     * @param altura Altura del rectángulo
     */
    public CuadrilateroInterfaz(String nombreForma, double base, double altura) {
        this.nombreForma = nombreForma != null ? nombreForma : "Rectángulo";
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
    public CuadrilateroInterfaz(String nombreForma, double lado1, double lado2, double lado3, double lado4) {
        this.nombreForma = nombreForma != null ? nombreForma : "Cuadrilátero";
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
     * Implementación del método area() de la interfaz IForma
     * @return área del cuadrilátero
     */
    @Override
    public double area() {
        return base * altura;
    }
    
    /**
     * Implementación del método perimetro() de la interfaz IForma
     * @return perímetro del cuadrilátero
     */
    @Override
    public double perimetro() {
        return lado1 + lado2 + lado3 + lado4;
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
        this.nombreForma = nombreForma != null ? nombreForma : "Cuadrilátero";
    }
    
    /**
     * Sobrescribe el método mostrarInfo() por defecto de la interfaz
     */
    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL CUADRILÁTERO (INTERFAZ) ===");
        System.out.println("Tipo: " + TIPO_GEOMETRICO);
        System.out.println("Nombre: " + nombreForma);
        System.out.println("Tipo detectado: " + determinarTipo());
        System.out.println("Lado 1: " + String.format("%.2f", lado1) + " " + UNIDAD_MEDIDA);
        System.out.println("Lado 2: " + String.format("%.2f", lado2) + " " + UNIDAD_MEDIDA);
        System.out.println("Lado 3: " + String.format("%.2f", lado3) + " " + UNIDAD_MEDIDA);
        System.out.println("Lado 4: " + String.format("%.2f", lado4) + " " + UNIDAD_MEDIDA);
        System.out.println("Base (para área): " + String.format("%.2f", base) + " " + UNIDAD_MEDIDA);
        System.out.println("Altura (para área): " + String.format("%.2f", altura) + " " + UNIDAD_MEDIDA);
        System.out.println("Área: " + String.format("%.2f", area()) + " " + UNIDAD_MEDIDA + "²");
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()) + " " + UNIDAD_MEDIDA);
        System.out.println("Precisión: " + PRECISION_CALCULO);
        System.out.println("===============================================");
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
    
    /**
     * Método para validar usando método estático de la interfaz
     * @return true si todos los lados son válidos (positivos)
     */
    public boolean tieneValoresValidos() {
        return IForma.esValorPositivo(lado1) && 
               IForma.esValorPositivo(lado2) && 
               IForma.esValorPositivo(lado3) && 
               IForma.esValorPositivo(lado4);
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
    
    /**
     * Override del método toString
     * @return representación en cadena del cuadrilátero
     */
    @Override
    public String toString() {
        return String.format("%s [tipo=%s, área=%.2f, perímetro=%.2f]", 
                           nombreForma, determinarTipo(), area(), perimetro());
    }
}
