# Actividad 5 - Clase Circulo

## Enunciado

**5)** Al igual que en el punto anterior, cree una clase **Circulo** que herede de **Forma** e implemente los métodos `area()` y `perimetro()` de un círculo. Desde el `main()` cree un objeto de tipo **Circulo** y muestre la ejecución de los métodos `area()` y `perimetro()`.

---

## Implementación

### Archivos creados:
- `Circulo.java` - Clase que hereda de Forma e implementa los métodos para círculo
- `TestActividad5.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Clase Circulo que hereda de Forma (de la Actividad 4)  
✅ Implementación de métodos area() y perimetro() para círculo  
✅ Atributo radio necesario para los cálculos  
✅ Constructores apropiados  
✅ Fórmulas matemáticas correctas (π × r² para área, 2 × π × r para perímetro)  
✅ Clase de prueba con creación de objetos y ejecución de métodos  

### Fórmulas utilizadas:
- **Área del círculo**: π × r²
- **Perímetro del círculo**: 2 × π × r

### Cómo ejecutar:
```bash
# Compilar (incluye dependencia de actividad4)
javac -d ../../../bin tp1/actividad4/*.java tp1/actividad5/*.java

# Ejecutar prueba
java -cp bin tp1.actividad5.TestActividad5
```

### Ejemplo de uso:
```java
// Crear círculo
Circulo miCirculo = new Circulo("Mi Círculo", 5.0);

// Ejecutar métodos
double area = miCirculo.area();
double perimetro = miCirculo.perimetro();

System.out.println("Área: " + area);
System.out.println("Perímetro: " + perimetro);
```
