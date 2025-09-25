package tp1.actividad3;

/**
 * Clase de prueba para demostrar el funcionamiento de las interfaces y CuentaSueldoMejorada
 * @author PC2025
 */
public class TestActividad3 {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LA ACTIVIDAD 3 - INTERFACES ===\n");
        
        // Mostrar información de las interfaces
        System.out.println("1. Información de las interfaces:");
        System.out.println("--- Constantes de OperacionesComunes ---");
        System.out.println("Banco: " + OperacionesComunes.BANCO_NOMBRE);
        System.out.println("Comisión por servicio: $" + OperacionesComunes.COMISION_SERVICIO);
        System.out.println("Máximo servicios diarios: " + OperacionesComunes.MAX_SERVICIOS_DIARIOS);
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Crear cuenta sueldo mejorada
        System.out.println("2. Creando CuentaSueldoMejorada que implementa OperacionesBancarias:");
        CuentaSueldoMejorada cuenta = new CuentaSueldoMejorada(12345678, 100000.0, 4.5);
        cuenta.setLegajo(1001);
        cuenta.setInstitucion("Universidad Nacional");
        cuenta.setBeneficios("Obra social + Descuentos comerciales");
        cuenta.setCbu(123456789012345678L);
        cuenta.mostrarDatos();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Probar método pagarServicio() de OperacionesComunes
        System.out.println("3. Probando método pagarServicio() (OperacionesComunes):");
        
        System.out.println("\n--- Primer pago de servicio ---");
        cuenta.pagarServicio();
        
        System.out.println("\n--- Segundo pago de servicio ---");
        cuenta.pagarServicio();
        
        System.out.println("\n--- Tercer pago de servicio ---");
        cuenta.pagarServicio();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Probar método cambiarAlias() de OperacionesComunes
        System.out.println("4. Probando método cambiarAlias() (OperacionesComunes):");
        cuenta.cambiarAlias();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Probar método transferenciaAltoMonto() de OperacionesImportantes
        System.out.println("5. Probando método transferenciaAltoMonto() (OperacionesImportantes):");
        
        System.out.println("\n--- Intentando transferencia de monto bajo ($25.000) ---");
        cuenta.transferenciaAltoMonto(25000.0);
        
        System.out.println("\n--- Transferencia de alto monto válida ($60.000) ---");
        cuenta.transferenciaAltoMonto(60000.0);
        
        System.out.println("\n--- Intentando otra transferencia de alto monto ($40.000) ---");
        cuenta.transferenciaAltoMonto(40000.0);
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demostrar que sigue funcionando la herencia
        System.out.println("6. Demostrando herencia (métodos de CuentaSueldo y CuentaBancaria):");
        
        System.out.println("\n--- Ingresando dinero (herencia de CuentaBancaria) ---");
        cuenta.ingresar(20000.0);
        System.out.println("Saldo actual: $" + String.format("%.2f", cuenta.getSaldoActual()));
        
        System.out.println("\n--- Aplicando interés diario (herencia de CuentaBancaria) ---");
        double saldoAntes = cuenta.getSaldoActual();
        cuenta.actualizarSaldo();
        double saldoDespues = cuenta.getSaldoActual();
        System.out.println("Saldo antes: $" + String.format("%.2f", saldoAntes));
        System.out.println("Saldo después: $" + String.format("%.2f", saldoDespues));
        System.out.println("Interés aplicado: $" + String.format("%.2f", saldoDespues - saldoAntes));
        
        System.out.println("\n--- Transferencia normal por CBU (herencia de CuentaSueldo) ---");
        cuenta.transferir(5000.0, 987654321098765432L);
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Crear otra cuenta para mostrar polimorfismo
        System.out.println("7. Demostrando polimorfismo con interfaces:");
        
        // Usar la cuenta como OperacionesBancarias
        OperacionesBancarias operaciones = cuenta;
        System.out.println("Usando la cuenta como OperacionesBancarias:");
        System.out.println("Banco: " + operaciones.BANCO_NOMBRE);
        operaciones.pagarServicio();
        operaciones.cambiarAlias();
        
        // Usar la cuenta como OperacionesComunes
        OperacionesComunes operacionesComunes = cuenta;
        System.out.println("\nUsando la cuenta como OperacionesComunes:");
        System.out.println("Máximo servicios: " + operacionesComunes.MAX_SERVICIOS_DIARIOS);
        
        // Usar la cuenta como OperacionesImportantes
        OperacionesImportantes operacionesImportantes = cuenta;
        System.out.println("\nUsando la cuenta como OperacionesImportantes:");
        operacionesImportantes.transferenciaAltoMonto(55000.0);
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Estado final de la cuenta
        System.out.println("8. Estado final de la cuenta:");
        cuenta.mostrarDatos();
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}
