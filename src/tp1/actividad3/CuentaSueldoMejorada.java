package tp1.actividad3;

import tp1.actividad2.CuentaSueldo;

/**
 * Clase CuentaSueldoMejorada que hereda de CuentaSueldo e implementa OperacionesBancarias
 * Combina todas las funcionalidades de cuenta sueldo con las operaciones bancarias avanzadas
 * @author PC2025
 */
public class CuentaSueldoMejorada extends CuentaSueldo implements OperacionesBancarias {
    
    // Atributos adicionales para las nuevas funcionalidades
    private String alias;
    private int serviciosPagadosHoy;
    private static final double LIMITE_ALTO_MONTO = 50000.0; // Límite para considerar "alto monto"
    
    /**
     * Constructor por defecto
     */
    public CuentaSueldoMejorada() {
        super();
        this.alias = "CUENTA.NUEVA.ALIAS";
        this.serviciosPagadosHoy = 0;
    }
    
    /**
     * Constructor con CBU
     * @param cbu CBU de 18 dígitos
     */
    public CuentaSueldoMejorada(long cbu) {
        super(cbu);
        this.alias = "CUENTA.CBU." + String.valueOf(cbu).substring(0, 6);
        this.serviciosPagadosHoy = 0;
    }
    
    /**
     * Constructor completo
     * @param dniCliente DNI del cliente
     * @param saldoActual Saldo inicial
     * @param interesAnual Interés anual
     */
    public CuentaSueldoMejorada(long dniCliente, double saldoActual, double interesAnual) {
        super(dniCliente, saldoActual, interesAnual, 0, "", "", 0L, 15000.0);
        this.alias = "CUENTA.CLIENTE." + dniCliente;
        this.serviciosPagadosHoy = 0;
    }
    
    /**
     * Constructor completo con todos los parámetros
     * @param dniCliente DNI del cliente
     * @param saldoActual Saldo inicial
     * @param interesAnual Interés anual
     * @param legajo Legajo del empleado
     * @param institucion Institución empleadora
     * @param beneficios Beneficios de la cuenta
     * @param cbu CBU de 18 dígitos
     * @param tope Tope de retiro
     * @param alias Alias de la cuenta
     */
    public CuentaSueldoMejorada(long dniCliente, double saldoActual, double interesAnual, 
                               int legajo, String institucion, String beneficios, 
                               long cbu, double tope, String alias) {
        super(dniCliente, saldoActual, interesAnual, legajo, institucion, beneficios, cbu, tope);
        this.alias = alias != null ? alias : "CUENTA.COMPLETA.ALIAS";
        this.serviciosPagadosHoy = 0;
    }
    
    /**
     * Implementación del método pagarServicio de la interfaz OperacionesComunes
     * @return true si el pago fue exitoso, false en caso contrario
     */
    @Override
    public boolean pagarServicio() {
        // Verificar límite diario de servicios
        if (serviciosPagadosHoy >= MAX_SERVICIOS_DIARIOS) {
            System.out.println("Error: Ha alcanzado el límite diario de " + MAX_SERVICIOS_DIARIOS + " servicios pagados");
            return false;
        }
        
        // Monto fijo del servicio más comisión
        double montoServicio = 1500.0; // Monto ejemplo del servicio
        double montoTotal = montoServicio + COMISION_SERVICIO;
        
        if (montoTotal > getSaldoActual()) {
            System.out.println("Error: Saldo insuficiente para pagar el servicio. Saldo actual: $" + 
                             String.format("%.2f", getSaldoActual()));
            System.out.println("Monto requerido: $" + String.format("%.2f", montoTotal) + 
                             " (Servicio: $" + montoServicio + " + Comisión: $" + COMISION_SERVICIO + ")");
            return false;
        }
        
        // Realizar el pago usando el método retirar de la clase padre
        if (super.retirar(montoTotal)) {
            serviciosPagadosHoy++;
            System.out.println("=== PAGO DE SERVICIO REALIZADO ===");
            System.out.println("Banco: " + BANCO_NOMBRE);
            System.out.println("Monto del servicio: $" + String.format("%.2f", montoServicio));
            System.out.println("Comisión: $" + String.format("%.2f", COMISION_SERVICIO));
            System.out.println("Total debitado: $" + String.format("%.2f", montoTotal));
            System.out.println("Servicios pagados hoy: " + serviciosPagadosHoy + "/" + MAX_SERVICIOS_DIARIOS);
            System.out.println("Saldo restante: $" + String.format("%.2f", getSaldoActual()));
            System.out.println("==================================");
            return true;
        }
        
        return false;
    }
    
    /**
     * Implementación del método cambiarAlias de la interfaz OperacionesComunes
     * @return true si el cambio fue exitoso, false en caso contrario
     */
    @Override
    public boolean cambiarAlias() {
        // Generar un nuevo alias automáticamente basado en el DNI y número de cuenta
        String nuevoAlias = "CUENTA." + getDniCliente() + "." + getNumeroCuenta();
        
        String aliasAnterior = this.alias;
        this.alias = nuevoAlias;
        
        System.out.println("=== CAMBIO DE ALIAS REALIZADO ===");
        System.out.println("Banco: " + BANCO_NOMBRE);
        System.out.println("Alias anterior: " + aliasAnterior);
        System.out.println("Nuevo alias: " + this.alias);
        System.out.println("=================================");
        
        return true;
    }
    
    /**
     * Implementación del método transferenciaAltoMonto de la interfaz OperacionesImportantes
     * @param monto Monto a transferir
     * @return true si la transferencia fue exitosa, false en caso contrario
     */
    @Override
    public boolean transferenciaAltoMonto(double monto) {
        if (monto < LIMITE_ALTO_MONTO) {
            System.out.println("Error: El monto ($" + String.format("%.2f", monto) + 
                             ") no califica como alto monto. Mínimo requerido: $" + 
                             String.format("%.2f", LIMITE_ALTO_MONTO));
            return false;
        }
        
        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser positivo");
            return false;
        }
        
        if (monto > getSaldoActual()) {
            System.out.println("Error: Saldo insuficiente para la transferencia de alto monto. Saldo actual: $" + 
                             String.format("%.2f", getSaldoActual()));
            return false;
        }
        
        // Para transferencias de alto monto, se permite superar el tope normal
        // Simular la transferencia directamente
        double saldoAnterior = getSaldoActual();
        
        // Usar el método ingresar con valor negativo para simular el débito
        // Como no podemos modificar directamente el saldo, usamos una lógica especial
        
        // Crear una transferencia temporal usando el método transferir por alias
        // pero mostrando que es de alto monto
        System.out.println("=== PROCESANDO TRANSFERENCIA DE ALTO MONTO ===");
        System.out.println("Banco: " + BANCO_NOMBRE);
        System.out.println("Validando operación de alto monto...");
        System.out.println("Monto: $" + String.format("%.2f", monto));
        System.out.println("Límite alto monto: $" + String.format("%.2f", LIMITE_ALTO_MONTO));
        
        // Realizar la transferencia usando el método de la clase padre
        // Generar un CBU ficticio para la demostración
        long cbuDestino = 999888777666555444L;
        
        if (super.transferir(monto, "TRANSFERENCIA.ALTO.MONTO")) {
            System.out.println("*** TRANSFERENCIA DE ALTO MONTO APROBADA ***");
            System.out.println("CBU destino simulado: " + cbuDestino);
            System.out.println("===========================================");
            return true;
        }
        
        return false;
    }
    
    /**
     * Sobrescribe el método mostrarDatos para incluir información de las interfaces
     */
    @Override
    public void mostrarDatos() {
        System.out.println("=== DATOS DE LA CUENTA SUELDO MEJORADA ===");
        System.out.println("Banco: " + BANCO_NOMBRE);
        System.out.println("Número de cuenta: " + getNumeroCuenta());
        System.out.println("DNI del cliente: " + getDniCliente());
        System.out.println("Saldo actual: $" + String.format("%.2f", getSaldoActual()));
        System.out.println("Interés anual: " + getInteresAnual() + "%");
        System.out.println("Legajo: " + getLegajo());
        System.out.println("Institución: " + getInstitucion());
        System.out.println("Beneficios: " + getBeneficios());
        System.out.println("CBU: " + getCbu());
        System.out.println("Tope de retiro/transferencia: $" + String.format("%.2f", getTope()));
        System.out.println("Alias: " + alias);
        System.out.println("Servicios pagados hoy: " + serviciosPagadosHoy + "/" + MAX_SERVICIOS_DIARIOS);
        System.out.println("Comisión por servicio: $" + String.format("%.2f", COMISION_SERVICIO));
        System.out.println("Límite alto monto: $" + String.format("%.2f", LIMITE_ALTO_MONTO));
        System.out.println("==========================================");
    }
    
    // Getters y Setters adicionales
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public int getServiciosPagadosHoy() {
        return serviciosPagadosHoy;
    }
    
    public void resetearServiciosDiarios() {
        this.serviciosPagadosHoy = 0;
        System.out.println("Contador de servicios diarios reseteado");
    }
    
    public static double getLimiteAltoMonto() {
        return LIMITE_ALTO_MONTO;
    }
}
