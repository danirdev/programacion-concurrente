package tp1.actividad2;

/**
 * Clase de prueba para demostrar el funcionamiento de CuentaSueldo
 * @author PC2025
 */
public class TestCuentaSueldo {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA CLASE CUENTASUELDO ===\n");
        
        // Crear cuenta sueldo con constructor por defecto
        System.out.println("1. Creando cuenta sueldo con constructor por defecto:");
        CuentaSueldo cuenta1 = new CuentaSueldo();
        cuenta1.mostrarDatos();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Crear cuenta sueldo con CBU
        System.out.println("2. Creando cuenta sueldo con CBU (tope fijo en $15.000):");
        CuentaSueldo cuenta2 = new CuentaSueldo(123456789012345678L);
        cuenta2.setDniCliente(12345678);
        cuenta2.setLegajo(1001);
        cuenta2.setInstitucion("Universidad Nacional");
        cuenta2.setBeneficios("Descuentos en comercios adheridos");
        cuenta2.ingresar(25000.0); // Ingresamos dinero para las pruebas
        cuenta2.mostrarDatos();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Probar método retirar sobrescrito (con validación de tope)
        System.out.println("3. Probando método retirar() sobrescrito:");
        
        System.out.println("\n--- Intentando retirar $10.000 (dentro del tope) ---");
        if (cuenta2.retirar(10000.0)) {
            System.out.println("Retiro exitoso!");
            System.out.println("Saldo actual: $" + String.format("%.2f", cuenta2.getSaldoActual()));
        }
        
        System.out.println("\n--- Intentando retirar $20.000 (supera el tope de $15.000) ---");
        cuenta2.retirar(20000.0);
        System.out.println("Saldo actual: $" + String.format("%.2f", cuenta2.getSaldoActual()));
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Probar transferencias por CBU
        System.out.println("4. Probando transferencias por CBU:");
        
        System.out.println("\n--- Transferencia de $5.000 por CBU ---");
        cuenta2.transferir(5000.0, 987654321098765432L);
        
        System.out.println("\n--- Intentando transferir $20.000 (supera el tope) ---");
        cuenta2.transferir(20000.0, 987654321098765432L);
        
        System.out.println("\n--- Intentando transferir con CBU inválido (menos de 18 dígitos) ---");
        cuenta2.transferir(1000.0, 12345L);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Probar transferencias por Alias (método sobrecargado)
        System.out.println("5. Probando transferencias por Alias:");
        
        System.out.println("\n--- Transferencia de $3.000 por Alias ---");
        cuenta2.transferir(3000.0, "JUAN.PEREZ.CUENTA");
        
        System.out.println("\n--- Transferencia de $2.000 por Alias ---");
        cuenta2.transferir(2000.0, "MARIA.GONZALEZ.SUELDO");
        
        System.out.println("\n--- Intentando transferir con alias vacío ---");
        cuenta2.transferir(1000.0, "");
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Crear otra cuenta para mostrar herencia completa
        System.out.println("6. Creando cuenta sueldo completa:");
        CuentaSueldo cuenta3 = new CuentaSueldo(
            87654321,           // DNI
            30000.0,           // Saldo inicial
            4.5,               // Interés anual
            2002,              // Legajo
            "Ministerio de Educación", // Institución
            "Obra social + Descuentos", // Beneficios
            111222333444555666L, // CBU
            20000.0            // Tope personalizado
        );
        cuenta3.mostrarDatos();
        
        System.out.println("\n--- Probando con tope personalizado de $20.000 ---");
        System.out.println("Intentando retirar $18.000:");
        if (cuenta3.retirar(18000.0)) {
            System.out.println("Retiro exitoso con tope personalizado!");
            System.out.println("Saldo actual: $" + String.format("%.2f", cuenta3.getSaldoActual()));
        }
        
        System.out.println("\n--- Probando actualización de saldo (herencia) ---");
        System.out.println("Saldo antes de aplicar interés: $" + String.format("%.2f", cuenta3.getSaldoActual()));
        cuenta3.actualizarSaldo();
        System.out.println("Saldo después de aplicar interés: $" + String.format("%.2f", cuenta3.getSaldoActual()));
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
