package tp1.actividad5;

import tp1.actividad4.Forma;
import tp1.actividad4.Cuadrilatero;

/**
 * Clase de prueba para demostrar el funcionamiento de la clase Circulo
 * @author PC2025
 */
public class TestActividad5 {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA ACTIVIDAD 5 - CLASE CIRCULO ===\n");
        
        // Crear objeto de tipo Circulo como pide el enunciado
        System.out.println("1. Creando objeto de tipo Circulo:");
        Circulo miCirculo = new Circulo("Mi Círculo", 5.0);
        
        System.out.println("Círculo creado: " + miCirculo.getNombreForma());
        System.out.println("Radio: " + miCirculo.getRadio());
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Mostrar ejecución de los métodos area() y perimetro() como pide el enunciado
        System.out.println("2. Ejecución de métodos area() y perimetro():");
        
        System.out.println("--- Ejecución del método area() ---");
        double area = miCirculo.area();
        System.out.println("Fórmula: π × r²");
        System.out.println("Cálculo: π × " + miCirculo.getRadio() + "²");
        System.out.println("Resultado de area(): " + String.format("%.2f", area));
        
        System.out.println("\n--- Ejecución del método perimetro() ---");
        double perimetro = miCirculo.perimetro();
        System.out.println("Fórmula: 2 × π × r");
        System.out.println("Cálculo: 2 × π × " + miCirculo.getRadio());
        System.out.println("Resultado de perimetro(): " + String.format("%.2f", perimetro));
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Mostrar información completa del círculo
        System.out.println("3. Información completa del círculo:");
        miCirculo.mostrarInfo();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Crear diferentes círculos para demostrar funcionalidad
        System.out.println("4. Creando diferentes círculos:");
        
        System.out.println("--- Círculo pequeño ---");
        Circulo circuloPequeno = new Circulo("Círculo Pequeño", 2.0);
        circuloPequeno.mostrarInfo();
        
        System.out.println("\n--- Círculo grande ---");
        Circulo circuloGrande = new Circulo("Círculo Grande", 10.0);
        circuloGrande.mostrarInfo();
        
        System.out.println("\n--- Círculo por defecto ---");
        Circulo circuloDefault = new Circulo();
        circuloDefault.mostrarInfo();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar polimorfismo con la clase abstracta Forma
        System.out.println("5. Demostración de polimorfismo con Forma:");
        
        // Array de formas que incluye círculos y cuadriláteros
        Forma[] formas = {
            new Circulo("Círculo 1", 3.0),
            new Circulo("Círculo 2", 4.5),
            new Cuadrilatero("Cuadrado", 6.0),
            new Cuadrilatero("Rectángulo", 8.0, 5.0)
        };
        
        System.out.println("Procesando array de formas (círculos y cuadriláteros):");
        for (int i = 0; i < formas.length; i++) {
            System.out.println("\n--- Forma " + (i + 1) + " ---");
            System.out.println("Tipo: " + formas[i].getClass().getSimpleName());
            System.out.println("Nombre: " + formas[i].getNombreForma());
            System.out.println("Área: " + String.format("%.2f", formas[i].area()));
            System.out.println("Perímetro: " + String.format("%.2f", formas[i].perimetro()));
        }
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar métodos adicionales específicos de Circulo
        System.out.println("6. Métodos adicionales de Circulo:");
        
        Circulo circulo1 = new Circulo("Círculo A", 5.0);
        Circulo circulo2 = new Circulo("Círculo B", 5.0);
        Circulo circulo3 = new Circulo("Círculo C", 7.0);
        
        System.out.println("--- Comparación de círculos ---");
        System.out.println("Círculo A (radio 5.0) == Círculo B (radio 5.0): " + circulo1.esIgual(circulo2));
        System.out.println("Círculo A (radio 5.0) == Círculo C (radio 7.0): " + circulo1.esIgual(circulo3));
        
        System.out.println("\n--- Comparación de áreas ---");
        int comparacion = circulo1.compararArea(circulo3);
        String resultado = comparacion > 0 ? "mayor" : (comparacion < 0 ? "menor" : "igual");
        System.out.println("Área de Círculo A es " + resultado + " que Círculo C");
        
        System.out.println("\n--- Escalado de círculo ---");
        System.out.println("Círculo antes del escalado:");
        System.out.println("Radio: " + String.format("%.2f", circulo1.getRadio()));
        System.out.println("Área: " + String.format("%.2f", circulo1.area()));
        
        circulo1.escalar(1.5);
        System.out.println("Círculo después del escalado (factor 1.5):");
        System.out.println("Radio: " + String.format("%.2f", circulo1.getRadio()));
        System.out.println("Área: " + String.format("%.2f", circulo1.area()));
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar métodos estáticos de creación
        System.out.println("7. Métodos estáticos de creación:");
        
        System.out.println("--- Círculo creado por diámetro ---");
        Circulo circuloPorDiametro = Circulo.crearPorDiametro("Círculo por Diámetro", 12.0);
        System.out.println("Diámetro especificado: 12.0");
        System.out.println("Radio calculado: " + String.format("%.2f", circuloPorDiametro.getRadio()));
        System.out.println("Área: " + String.format("%.2f", circuloPorDiametro.area()));
        
        System.out.println("\n--- Círculo creado por área ---");
        Circulo circuloPorArea = Circulo.crearPorArea("Círculo por Área", 50.0);
        System.out.println("Área especificada: 50.0");
        System.out.println("Radio calculado: " + String.format("%.2f", circuloPorArea.getRadio()));
        System.out.println("Área verificada: " + String.format("%.2f", circuloPorArea.area()));
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Resumen final
        System.out.println("8. Resumen de la implementación:");
        System.out.println("✅ Clase Circulo hereda de Forma (clase abstracta)");
        System.out.println("✅ Métodos abstractos area() y perimetro() implementados");
        System.out.println("✅ Fórmulas matemáticas correctas aplicadas");
        System.out.println("✅ Objeto Circulo creado desde main()");
        System.out.println("✅ Métodos area() y perimetro() ejecutados y mostrados");
        System.out.println("✅ Polimorfismo demostrado con clase abstracta Forma");
        System.out.println("✅ Funcionalidades adicionales implementadas");
        
        System.out.println("\n--- Comparación con Cuadrilatero ---");
        Forma circuloForma = new Circulo("Círculo Comparación", 5.0);
        Forma cuadradoForma = new Cuadrilatero("Cuadrado Comparación", 5.0);
        
        System.out.println("Círculo (radio 5.0) - Área: " + String.format("%.2f", circuloForma.area()));
        System.out.println("Cuadrado (lado 5.0) - Área: " + String.format("%.2f", cuadradoForma.area()));
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
