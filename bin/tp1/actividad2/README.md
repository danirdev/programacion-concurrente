# Actividad 2 - Clase CuentaSueldo

## Enunciado

**2)** A partir de la clase definida en el ejemplo anterior, cree una nueva clase **CuentaSueldo**, que herede de **Cuenta** y posea los siguientes atributos propios: legajo (entero), institución (string), beneficios (string), CBU (entero largo de 18 dígitos), tope (double). Además debe poseer los siguientes métodos:

### Métodos requeridos:
- **Constructor por defecto** y **constructor con CBU** (el cual es un número de 18 dígitos) y tope fijado en 15.000
- **Sobrescribir el método `retirar(double)`**, para que además de permitir sacar una cantidad de la cuenta (si hay saldo), no permita extracciones superiores al tope.
- **`transferir(monto, CBU)`**: este método simulará una transferencia a otra cuenta por el monto ingresado (siempre y cuando haya saldo) y decrementará el saldo de la cuenta. Para la "transferencia", muestre el saldo final de la cuenta.
- **Sobrecargue el método `transferir(monto, Alias)`** de la clase CuentaSueldo para que acepte un alias alfanumérico.

---

## Implementación

### Archivos creados:
- `CuentaSueldo.java` - Clase que hereda de CuentaBancaria
- `TestCuentaSueldo.java` - Clase de prueba para demostrar el funcionamiento
- `README.md` - Este archivo con el enunciado

### Características implementadas:
✅ Herencia de CuentaBancaria  
✅ Atributos adicionales: legajo, institución, beneficios, CBU, tope  
✅ Constructor por defecto  
✅ Constructor con CBU y tope fijo de 15.000  
✅ Método `retirar()` sobrescrito con validación de tope  
✅ Método `transferir(monto, CBU)` con validación de saldo  
✅ Método `transferir(monto, Alias)` sobrecargado  
✅ Validaciones de CBU (18 dígitos)  
✅ Mostrar saldo final después de transferencias  

### Cómo ejecutar:
```bash
# Compilar
javac -d ../../../bin tp1/actividad2/*.java

# Ejecutar prueba
java -cp bin tp1.actividad2.TestCuentaSueldo
```

### Ejemplo de uso:
```java
// Crear cuenta sueldo
CuentaSueldo cuenta = new CuentaSueldo(123456789012345678L);

// Realizar operaciones
cuenta.ingresar(20000.0);
cuenta.retirar(10000.0);  // Respeta el tope de 15.000
cuenta.transferir(5000.0, 987654321098765432L);  // Por CBU
cuenta.transferir(2000.0, "ALIAS.BANCO.CUENTA");  // Por alias
```
