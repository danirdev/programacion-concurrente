package tp1.actividad4;

/**
 * Clase de prueba para demostrar el funcionamiento de la clase abstracta Forma y Cuadrilatero
 * @author PC2025
 */
public class TestActividad4 {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA ACTIVIDAD 4 - CLASE ABSTRACTA FORMA ===\n");
        
        // Demostrar que no se puede instanciar la clase abstracta Forma
        System.out.println("1. Demostración de clase abstracta:");
        System.out.println("   - No se puede crear: new Forma() // Error de compilación");
        System.out.println("   - Solo se pueden crear instancias de clases que hereden de Forma\n");
        
        System.out.println("=".repeat(70) + "\n");
        
        // Crear diferentes tipos de cuadriláteros
        System.out.println("2. Creando diferentes cuadriláteros:");
        
        // Cuadrado usando constructor específico
        System.out.println("--- CUADRADO ---");
        Cuadrilatero cuadrado = new Cuadrilatero("Cuadrado", 4.0);
        cuadrado.mostrarInfo();
        
        System.out.println("\n--- RECTÁNGULO ---");
        Cuadrilatero rectangulo = new Cuadrilatero("Rectángulo", 6.0, 4.0);
        rectangulo.mostrarInfo();
        
        System.out.println("\n--- CUADRILÁTERO IRREGULAR ---");
        Cuadrilatero irregular = new Cuadrilatero("Cuadrilátero Irregular", 3.0, 5.0, 4.0, 6.0);
        irregular.mostrarInfo();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar ejecución específica de métodos area() y perimetro() como pide el enunciado
        System.out.println("3. Ejecución específica de métodos area() y perimetro():");
        
        // Crear objeto de tipo Cuadrilatero como pide el enunciado
        Cuadrilatero miCuadrilatero = new Cuadrilatero("Mi Cuadrilátero", 5.0, 3.0);
        
        System.out.println("Cuadrilátero creado: " + miCuadrilatero.getNombreForma());
        System.out.println("Tipo: " + miCuadrilatero.determinarTipo());
        
        // Mostrar ejecución de los métodos como pide el enunciado
        System.out.println("\n--- Ejecución del método area() ---");
        double area = miCuadrilatero.area();
        System.out.println("Resultado de area(): " + String.format("%.2f", area));
        
        System.out.println("\n--- Ejecución del método perimetro() ---");
        double perimetro = miCuadrilatero.perimetro();
        System.out.println("Resultado de perimetro(): " + String.format("%.2f", perimetro));
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar polimorfismo con la clase abstracta
        System.out.println("4. Demostración de polimorfismo:");
        
        // Array de formas (polimorfismo)
        Forma[] formas = {
            new Cuadrilatero("Cuadrado Pequeño", 2.0),
            new Cuadrilatero("Rectángulo Grande", 8.0, 5.0),
            new Cuadrilatero("Paralelogramo", 4.0, 3.0, 4.0, 3.0)
        };
        
        System.out.println("Procesando array de formas usando polimorfismo:");
        for (int i = 0; i < formas.length; i++) {
            System.out.println("\n--- Forma " + (i + 1) + " ---");
            System.out.println("Nombre: " + formas[i].getNombreForma());
            System.out.println("Área: " + String.format("%.2f", formas[i].area()));
            System.out.println("Perímetro: " + String.format("%.2f", formas[i].perimetro()));
        }
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar métodos adicionales específicos de Cuadrilatero
        System.out.println("5. Métodos adicionales de Cuadrilatero:");
        
        Cuadrilatero testCuadrilatero = new Cuadrilatero("Test", 3.0, 3.0, 3.0, 3.0);
        System.out.println("¿Es cuadrado? " + testCuadrilatero.esCuadrado());
        System.out.println("¿Es rectángulo? " + testCuadrilatero.esRectangulo());
        System.out.println("Tipo determinado: " + testCuadrilatero.determinarTipo());
        
        // Modificar un lado y ver cómo cambia
        System.out.println("\n--- Modificando lado 2 a 4.0 ---");
        testCuadrilatero.setLado2(4.0);
        testCuadrilatero.setLado4(4.0);
        System.out.println("¿Es cuadrado? " + testCuadrilatero.esCuadrado());
        System.out.println("¿Es rectángulo? " + testCuadrilatero.esRectangulo());
        System.out.println("Tipo determinado: " + testCuadrilatero.determinarTipo());
        System.out.println("Nueva área: " + String.format("%.2f", testCuadrilatero.area()));
        System.out.println("Nuevo perímetro: " + String.format("%.2f", testCuadrilatero.perimetro()));
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Resumen final
        System.out.println("6. Resumen de la implementación:");
        System.out.println("✅ Clase abstracta Forma creada con atributo NombreForma");
        System.out.println("✅ Métodos abstractos area() y perimetro() definidos");
        System.out.println("✅ Clase Cuadrilatero hereda de Forma");
        System.out.println("✅ Métodos abstractos implementados en Cuadrilatero");
        System.out.println("✅ Atributos y métodos necesarios para cálculos agregados");
        System.out.println("✅ Objeto Cuadrilatero creado desde main()");
        System.out.println("✅ Métodos area() y perimetro() ejecutados y mostrados");
        System.out.println("✅ Funcionalidad adicional implementada (polimorfismo, validaciones)");
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
