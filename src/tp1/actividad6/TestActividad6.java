package tp1.actividad6;

import tp1.actividad4.Forma;
import tp1.actividad4.Cuadrilatero;
import tp1.actividad5.Circulo;

/**
 * Clase de prueba para demostrar el funcionamiento de la interfaz IForma
 * y comparar con la implementación de clase abstracta de las actividades 4 y 5
 * @author PC2025
 */
public class TestActividad6 {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA ACTIVIDAD 6 - REFACTORIZACIÓN CON INTERFAZ ===\n");
        
        // Mostrar información de la interfaz
        System.out.println("1. Información de la interfaz IForma:");
        System.out.println(IForma.getInfoInterfaz());
        System.out.println("Constantes de la interfaz:");
        System.out.println("- TIPO_GEOMETRICO: " + IForma.TIPO_GEOMETRICO);
        System.out.println("- PRECISION_CALCULO: " + IForma.PRECISION_CALCULO);
        System.out.println("- UNIDAD_MEDIDA: " + IForma.UNIDAD_MEDIDA);
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Crear objetos usando la interfaz (equivalente al punto 4)
        System.out.println("2. Creando formas usando interfaz IForma:");
        
        System.out.println("--- CuadrilateroInterfaz (equivalente a Actividad 4) ---");
        CuadrilateroInterfaz cuadrilateroInterfaz = new CuadrilateroInterfaz("Rectángulo Interfaz", 6.0, 4.0);
        cuadrilateroInterfaz.mostrarInfo();
        
        System.out.println("\n--- CirculoInterfaz (equivalente a Actividad 5) ---");
        CirculoInterfaz circuloInterfaz = new CirculoInterfaz("Círculo Interfaz", 5.0);
        circuloInterfaz.mostrarInfo();
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Demostrar polimorfismo con interfaz
        System.out.println("3. Polimorfismo usando interfaz IForma:");
        
        // Array de IForma (equivalente a Forma[] en actividades anteriores)
        IForma[] formasInterfaz = {
            new CuadrilateroInterfaz("Cuadrado Interfaz", 4.0),
            new CirculoInterfaz("Círculo Interfaz", 3.0),
            new CuadrilateroInterfaz("Rectángulo Interfaz", 7.0, 3.0),
            new CirculoInterfaz("Círculo Grande Interfaz", 6.0)
        };
        
        System.out.println("Procesando array de IForma[]:");
        for (int i = 0; i < formasInterfaz.length; i++) {
            System.out.println("\n--- Forma " + (i + 1) + " ---");
            System.out.println("Tipo de clase: " + formasInterfaz[i].getClass().getSimpleName());
            System.out.println("Nombre: " + formasInterfaz[i].getNombreForma());
            System.out.println("Área: " + String.format("%.2f", formasInterfaz[i].area()));
            System.out.println("Perímetro: " + String.format("%.2f", formasInterfaz[i].perimetro()));
        }
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Comparación directa entre implementaciones (Clase Abstracta vs Interfaz)
        System.out.println("4. Comparación: Clase Abstracta vs Interfaz:");
        
        // Crear formas equivalentes con ambas implementaciones
        System.out.println("--- Creando formas equivalentes ---");
        
        // Cuadriláteros
        Cuadrilatero cuadrilateroAbstracto = new Cuadrilatero("Rectángulo Abstracto", 6.0, 4.0);
        CuadrilateroInterfaz cuadrilateroInterfazComp = new CuadrilateroInterfaz("Rectángulo Interfaz", 6.0, 4.0);
        
        // Círculos
        Circulo circuloAbstracto = new Circulo("Círculo Abstracto", 5.0);
        CirculoInterfaz circuloInterfazComp = new CirculoInterfaz("Círculo Interfaz", 5.0);
        
        System.out.println("\n--- Comparación de Cuadriláteros ---");
        System.out.println("Clase Abstracta - Área: " + String.format("%.2f", cuadrilateroAbstracto.area()) + 
                          ", Perímetro: " + String.format("%.2f", cuadrilateroAbstracto.perimetro()));
        System.out.println("Interfaz        - Área: " + String.format("%.2f", cuadrilateroInterfazComp.area()) + 
                          ", Perímetro: " + String.format("%.2f", cuadrilateroInterfazComp.perimetro()));
        System.out.println("¿Resultados iguales? " + 
                          (Math.abs(cuadrilateroAbstracto.area() - cuadrilateroInterfazComp.area()) < 0.01));
        
        System.out.println("\n--- Comparación de Círculos ---");
        System.out.println("Clase Abstracta - Área: " + String.format("%.2f", circuloAbstracto.area()) + 
                          ", Perímetro: " + String.format("%.2f", circuloAbstracto.perimetro()));
        System.out.println("Interfaz        - Área: " + String.format("%.2f", circuloInterfazComp.area()) + 
                          ", Perímetro: " + String.format("%.2f", circuloInterfazComp.perimetro()));
        System.out.println("¿Resultados iguales? " + 
                          (Math.abs(circuloAbstracto.area() - circuloInterfazComp.area()) < 0.01));
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Demostrar métodos por defecto de la interfaz
        System.out.println("5. Métodos por defecto de la interfaz:");
        
        IForma forma1 = new CirculoInterfaz("Círculo Test", 4.0);
        IForma forma2 = new CuadrilateroInterfaz("Cuadrado Test", 4.0);
        
        System.out.println("--- Comparación usando métodos por defecto ---");
        System.out.println("Forma 1: " + forma1.getNombreForma() + " (Área: " + String.format("%.2f", forma1.area()) + ")");
        System.out.println("Forma 2: " + forma2.getNombreForma() + " (Área: " + String.format("%.2f", forma2.area()) + ")");
        
        int comparacionArea = forma1.compararArea(forma2);
        String resultadoArea = comparacionArea > 0 ? "mayor" : (comparacionArea < 0 ? "menor" : "igual");
        System.out.println("Área de Forma 1 es " + resultadoArea + " que Forma 2");
        
        int comparacionPerimetro = forma1.compararPerimetro(forma2);
        String resultadoPerimetro = comparacionPerimetro > 0 ? "mayor" : (comparacionPerimetro < 0 ? "menor" : "igual");
        System.out.println("Perímetro de Forma 1 es " + resultadoPerimetro + " que Forma 2");
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Demostrar métodos estáticos de la interfaz
        System.out.println("6. Métodos estáticos de la interfaz:");
        
        System.out.println("--- Validación usando método estático ---");
        double[] valoresPrueba = {5.0, -3.0, 0.0, 10.5};
        for (double valor : valoresPrueba) {
            System.out.println("Valor " + valor + " es positivo: " + IForma.esValorPositivo(valor));
        }
        
        System.out.println("\n--- Validación en objetos ---");
        CuadrilateroInterfaz cuadrilateroTest = new CuadrilateroInterfaz("Test", 3.0, 4.0);
        CirculoInterfaz circuloTest = new CirculoInterfaz("Test", 2.5);
        
        System.out.println("Cuadrilátero tiene valores válidos: " + cuadrilateroTest.tieneValoresValidos());
        System.out.println("Círculo tiene valor válido: " + circuloTest.tieneValorValido());
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Resumen de diferencias
        System.out.println("7. Resumen de diferencias - Clase Abstracta vs Interfaz:");
        
        System.out.println("\n--- CLASE ABSTRACTA (Actividades 4 y 5) ---");
        System.out.println("✅ Herencia: extends Forma");
        System.out.println("✅ Puede tener métodos concretos y abstractos");
        System.out.println("✅ Puede tener atributos de instancia");
        System.out.println("✅ Constructor disponible");
        System.out.println("✅ Herencia simple (solo una clase padre)");
        
        System.out.println("\n--- INTERFAZ (Actividad 6) ---");
        System.out.println("✅ Implementación: implements IForma");
        System.out.println("✅ Métodos abstractos por defecto (hasta Java 8)");
        System.out.println("✅ Métodos por defecto (default) desde Java 8");
        System.out.println("✅ Métodos estáticos desde Java 8");
        System.out.println("✅ Solo constantes (public static final)");
        System.out.println("✅ Herencia múltiple (múltiples interfaces)");
        System.out.println("✅ No tiene constructor");
        
        System.out.println("\n--- FUNCIONALIDAD EQUIVALENTE ---");
        System.out.println("✅ Ambas implementaciones producen los mismos resultados");
        System.out.println("✅ Polimorfismo funciona en ambos casos");
        System.out.println("✅ Métodos area() y perimetro() implementados correctamente");
        System.out.println("✅ Flexibilidad de diseño mantenida");
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
