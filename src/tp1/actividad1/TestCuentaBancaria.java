package tp1.actividad1;

/**
 * Clase de prueba para demostrar el funcionamiento de CuentaBancaria
 * @author PC2025
 */
public class TestCuentaBancaria {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE CUENTABANCARIA ===\n");
        
        // Crear cuenta con constructor por defecto
        System.out.println("1. Creando cuenta con constructor por defecto:");
        CuentaBancaria cuenta1 = new CuentaBancaria();
        cuenta1.mostrarDatos();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Crear cuenta con constructor con parámetros
        System.out.println("2. Creando cuenta con constructor parametrizado:");
        CuentaBancaria cuenta2 = new CuentaBancaria(12345678, 1000.0, 5.0);
        cuenta2.mostrarDatos();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Probar operaciones con la cuenta2
        System.out.println("3. Probando operaciones con la cuenta:");
        
        // Ingresar dinero
        System.out.println("\n--- Ingresando $500 ---");
        if (cuenta2.ingresar(500.0)) {
            System.out.println("Ingreso exitoso!");
        }
        cuenta2.mostrarDatos();
        
        // Retirar dinero
        System.out.println("\n--- Retirando $200 ---");
        if (cuenta2.retirar(200.0)) {
            System.out.println("Retiro exitoso!");
        }
        cuenta2.mostrarDatos();
        
        // Intentar retirar más dinero del disponible
        System.out.println("\n--- Intentando retirar $2000 (más del saldo disponible) ---");
        cuenta2.retirar(2000.0);
        
        // Actualizar saldo con interés diario
        System.out.println("\n--- Aplicando interés diario ---");
        System.out.println("Saldo antes de aplicar interés: $" + String.format("%.2f", cuenta2.getSaldoActual()));
        cuenta2.actualizarSaldo();
        System.out.println("Saldo después de aplicar interés diario: $" + String.format("%.2f", cuenta2.getSaldoActual()));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Crear una tercera cuenta para mostrar la numeración correlativa
        System.out.println("4. Creando tercera cuenta para mostrar numeración correlativa:");
        CuentaBancaria cuenta3 = new CuentaBancaria(87654321, 2500.0, 3.5);
        cuenta3.mostrarDatos();
        
        System.out.println("\n--- Resumen de números de cuenta asignados ---");
        System.out.println("Cuenta 1: " + cuenta1.getNumeroCuenta());
        System.out.println("Cuenta 2: " + cuenta2.getNumeroCuenta());
        System.out.println("Cuenta 3: " + cuenta3.getNumeroCuenta());
        System.out.println("Último número asignado: " + CuentaBancaria.getUltimoNumeroCuenta());
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
