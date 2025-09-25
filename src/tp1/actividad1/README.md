# Actividad 1 - Clase CuentaBancaria

## Enunciado

**1)** Empleando Java, cree una clase **CuentaBancaria** con atributos para el número de cuenta (un entero largo), el DNI del cliente (otro entero largo), el saldo actual (double) y el interés anual que se aplica a la cuenta (porcentaje). Defina en la clase los siguientes métodos:

### Métodos requeridos:
- **Constructor por defecto** y **constructor con DNI, saldo e interés**.
- **`actualizarSaldo()`**: actualizará el saldo de la cuenta aplicándole el interés diario (interés anual dividido entre 365 aplicado al saldo actual).
- **`ingresar(double)`**: permitirá ingresar una cantidad en la cuenta.
- **`retirar(double)`**: permitirá sacar una cantidad de la cuenta (si hay saldo).
- **Método que nos permita mostrar todos los datos de la cuenta**.

### Nota importante:
- El número de cuenta se asignará de forma correlativa a partir de **100001**, asignando el siguiente número al último asignado.

---

## Implementación

### Archivos creados:
- `CuentaBancaria.java` - Clase principal con toda la funcionalidad
- `TestCuentaBancaria.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Atributos: numeroCuenta (long), dniCliente (long), saldoActual (double), interesAnual (double)  
✅ Constructor por defecto  
✅ Constructor con parámetros (DNI, saldo, interés)  
✅ Numeración correlativa automática desde 100001  
✅ Método `actualizarSaldo()` con cálculo de interés diario  
✅ Método `ingresar(double)` con validaciones  
✅ Método `retirar(double)` con validación de saldo  
✅ Método `mostrarDatos()` para visualizar información completa  
✅ Getters y setters apropiados  
✅ Validaciones de entrada y manejo de errores  

### Cómo ejecutar:
```bash
# Compilar
javac -d ../../../bin tp1/actividad1/*.java

# Ejecutar prueba
java -cp bin tp1.actividad1.TestCuentaBancaria
```

### Ejemplo de uso:
```java
// Crear cuenta con constructor parametrizado
CuentaBancaria cuenta = new CuentaBancaria(12345678, 1000.0, 5.0);

// Realizar operaciones
cuenta.ingresar(500.0);
cuenta.retirar(200.0);
cuenta.actualizarSaldo(); // Aplica interés diario
cuenta.mostrarDatos();    // Muestra información completa
```
