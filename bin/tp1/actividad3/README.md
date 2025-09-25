# Actividad 3 - Interfaces para Operaciones Bancarias

## Enunciado

**3)** Cree una interfaz llamada **OperacionesComunes** con 3 atributos y 2 métodos (`pagarServicio()` y `cambiarAlias()`). Cree otra interfaz llamada **OperacionesImportantes** con un método llamado `transferenciaAltoMonto(double monto)`. La interfaz **OperacionesBancarias** hereda de **OperacionesComunes** y **OperacionesImportantes**. Luego la clase **CuentaSueldo** debe implementar la interfaz **OperacionesBancarias**.

---

## Implementación

### Archivos creados:
- `OperacionesComunes.java` - Interfaz base con 3 atributos y 2 métodos
- `OperacionesImportantes.java` - Interfaz para operaciones de alto monto
- `OperacionesBancarias.java` - Interfaz que hereda de las dos anteriores
- `CuentaSueldoMejorada.java` - Clase CuentaSueldo que implementa OperacionesBancarias
- `TestActividad3.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Interfaz OperacionesComunes con 3 atributos y métodos pagarServicio() y cambiarAlias()  
✅ Interfaz OperacionesImportantes con transferenciaAltoMonto(double monto)  
✅ Interfaz OperacionesBancarias que hereda de ambas interfaces  
✅ Clase CuentaSueldoMejorada que implementa OperacionesBancarias  
✅ Implementación de todos los métodos requeridos  
✅ Uso de atributos de interfaz (constantes)  
✅ Clase de prueba completa  

### Cómo ejecutar:
```bash
# Compilar
javac -d ../../../bin tp1/actividad1/*.java tp1/actividad2/*.java tp1/actividad3/*.java

# Ejecutar prueba
java -cp bin tp1.actividad3.TestActividad3
```

### Ejemplo de uso:
```java
// Crear cuenta que implementa OperacionesBancarias
CuentaSueldoMejorada cuenta = new CuentaSueldoMejorada(12345678, 50000.0, 3.5);

// Usar métodos de OperacionesComunes
cuenta.pagarServicio();
cuenta.cambiarAlias();

// Usar métodos de OperacionesImportantes
cuenta.transferenciaAltoMonto(25000.0);
```
