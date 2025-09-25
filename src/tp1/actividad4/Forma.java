package tp1.actividad4;

/**
 * Clase abstracta Forma
 * Define la estructura base para todas las formas geométricas
 * @author PC2025
 */
public abstract class Forma {
    
    // Atributo requerido
    protected String nombreForma;
    
    /**
     * Constructor por defecto
     */
    public Forma() {
        this.nombreForma = "Forma genérica";
    }
    
    /**
     * Constructor con parámetro
     * @param nombreForma Nombre de la forma geométrica
     */
    public Forma(String nombreForma) {
        this.nombreForma = nombreForma != null ? nombreForma : "Forma genérica";
    }
    
    /**
     * Método abstracto para calcular el área
     * Debe ser implementado por las clases hijas
     * @return área de la forma
     */
    public abstract double area();
    
    /**
     * Método abstracto para calcular el perímetro
     * Debe ser implementado por las clases hijas
     * @return perímetro de la forma
     */
    public abstract double perimetro();
    
    /**
     * Método concreto para mostrar información básica de la forma
     */
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DE LA FORMA ===");
        System.out.println("Nombre: " + nombreForma);
        System.out.println("Área: " + String.format("%.2f", area()));
        System.out.println("Perímetro: " + String.format("%.2f", perimetro()));
        System.out.println("===============================");
    }
    
    // Getter y Setter para nombreForma
    public String getNombreForma() {
        return nombreForma;
    }
    
    public void setNombreForma(String nombreForma) {
        this.nombreForma = nombreForma != null ? nombreForma : "Forma genérica";
    }
}
