# Actividad 4 - Clase Abstracta Forma

## Enunciado

**4)** Defina una clase **abstracta** llamada **Forma**, con un atributo **NombreForma** y dos métodos `area()` y `perimetro()`. Luego cree una clase llamada **Cuadrilatero** que herede de **Forma** e implemente los métodos definidos en **Forma**. Además agregue los atributos y métodos necesarios para poder realizar los cálculos del área y perímetro de un cuadrilátero. Desde el `main()` cree un objeto de tipo **Cuadrilatero** y muestre la ejecución de los métodos `area()` y `perimetro()`.

---

## Implementación

### Archivos creados:
- `Forma.java` - Clase abstracta base con atributo NombreForma y métodos abstractos
- `Cuadrilatero.java` - Clase que hereda de Forma e implementa los métodos
- `TestActividad4.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Clase abstracta Forma con atributo NombreForma  
✅ Métodos abstractos area() y perimetro() en Forma  
✅ Clase Cuadrilatero que hereda de Forma  
✅ Implementación de métodos area() y perimetro() en Cuadrilatero  
✅ Atributos necesarios para cálculos (lados del cuadrilátero)  
✅ Constructores apropiados  
✅ Clase de prueba con creación de objetos y ejecución de métodos  

### Cómo ejecutar:
```bash
# Compilar
javac -d ../../../bin tp1/actividad4/*.java

# Ejecutar prueba
java -cp bin tp1.actividad4.TestActividad4
```

### Ejemplo de uso:
```java
// Crear cuadrilátero (rectángulo)
Cuadrilatero rectangulo = new Cuadrilatero("Rectángulo", 5.0, 3.0, 5.0, 3.0);

// Ejecutar métodos
double area = rectangulo.area();
double perimetro = rectangulo.perimetro();

System.out.println("Área: " + area);
System.out.println("Perímetro: " + perimetro);
```
