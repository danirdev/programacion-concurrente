# Actividad 6 - Refactorización usando Interfaz

## Enunciado

**6)** Realice lo mismo que en el punto 4) pero en lugar de utilizar una clase abstracta **Forma**, hágalo mediante una interfaz.

---

## Implementación

### Archivos creados:
- `IForma.java` - Interfaz que reemplaza la clase abstracta Forma
- `CuadrilateroInterfaz.java` - Clase que implementa IForma (equivalente a Cuadrilatero)
- `CirculoInterfaz.java` - Clase que implementa IForma (equivalente a Circulo)
- `TestActividad6.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Interfaz IForma con métodos area() y perimetro()  
✅ Atributos de interfaz (constantes) para información común  
✅ Clase CuadrilateroInterfaz que implementa IForma  
✅ Clase CirculoInterfaz que implementa IForma  
✅ Implementación de todos los métodos de la interfaz  
✅ Funcionalidad equivalente a las clases abstractas  
✅ Demostración de polimorfismo con interfaces  

### Diferencias con la implementación de clase abstracta:
- **Interfaz vs Clase Abstracta**: IForma es una interfaz, no una clase abstracta
- **Implementa vs Extiende**: Las clases `implements IForma` en lugar de `extends Forma`
- **Atributos**: Solo constantes públicas, estáticas y finales en la interfaz
- **Métodos**: Todos los métodos son abstractos por defecto en la interfaz
- **Herencia múltiple**: Las clases pueden implementar múltiples interfaces

### Cómo ejecutar:
```bash
# Compilar
javac -d ../../../bin tp1/actividad6/*.java

# Ejecutar prueba
java -cp bin tp1.actividad6.TestActividad6
```

### Ejemplo de uso:
```java
// Crear formas usando la interfaz
IForma rectangulo = new CuadrilateroInterfaz("Rectángulo", 6.0, 4.0);
IForma circulo = new CirculoInterfaz("Círculo", 5.0);

// Usar polimorfismo
double areaRectangulo = rectangulo.area();
double perimetroCirculo = circulo.perimetro();
```
