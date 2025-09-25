package tp1.actividad2;

import tp1.actividad1.CuentaBancaria;

/**
 * Clase CuentaSueldo que hereda de CuentaBancaria
 * Incluye funcionalidades específicas para cuentas sueldo con tope de retiro y transferencias
 * @author PC2025
 */
public class CuentaSueldo extends CuentaBancaria {
    // Atributos específicos de CuentaSueldo
    private int legajo;
    private String institucion;
    private String beneficios;
    private long cbu; // CBU de 18 dígitos
    private double tope;
    
    /**
     * Constructor por defecto
     * Inicializa la cuenta sueldo con valores por defecto
     */
    public CuentaSueldo() {
        super(); // Llama al constructor de CuentaBancaria
        this.legajo = 0;
        this.institucion = "";
        this.beneficios = "";
        this.cbu = 0L;
        this.tope = 15000.0; // Tope por defecto
    }
    
    /**
     * Constructor con CBU
     * @param cbu CBU de 18 dígitos
     * El tope se fija automáticamente en 15.000
     */
    public CuentaSueldo(long cbu) {
        super(); // Llama al constructor de CuentaBancaria
        this.legajo = 0;
        this.institucion = "";
        this.beneficios = "";
        this.tope = 15000.0; // Tope fijo en 15.000
        setCbu(cbu); // Usa el setter para validar
    }
    
    /**
     * Constructor completo
     * @param dniCliente DNI del cliente
     * @param saldoActual Saldo inicial
     * @param interesAnual Interés anual
     * @param legajo Legajo del empleado
     * @param institucion Institución empleadora
     * @param beneficios Beneficios de la cuenta
     * @param cbu CBU de 18 dígitos
     * @param tope Tope de retiro
     */
    public CuentaSueldo(long dniCliente, double saldoActual, double interesAnual, 
                       int legajo, String institucion, String beneficios, long cbu, double tope) {
        super(dniCliente, saldoActual, interesAnual);
        this.legajo = legajo;
        this.institucion = institucion;
        this.beneficios = beneficios;
        this.tope = tope;
        setCbu(cbu); // Usa el setter para validar
    }
    
    /**
     * Sobrescribe el método retirar para incluir validación de tope
     * @param cantidad Cantidad a retirar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    @Override
    public boolean retirar(double cantidad) {
        if (cantidad > tope) {
            System.out.println("Error: La cantidad a retirar ($" + cantidad + 
                             ") supera el tope permitido ($" + tope + ")");
            return false;
        }
        // Si pasa la validación del tope, usa el método padre
        return super.retirar(cantidad);
    }
    
    /**
     * Método para transferir dinero a otra cuenta usando CBU
     * @param monto Monto a transferir
     * @param cbuDestino CBU de la cuenta destino
     * @return true si la transferencia fue exitosa, false en caso contrario
     */
    public boolean transferir(double monto, long cbuDestino) {
        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser positivo");
            return false;
        }
        
        if (monto > getSaldoActual()) {
            System.out.println("Error: Saldo insuficiente para la transferencia. Saldo actual: $" + 
                             String.format("%.2f", getSaldoActual()));
            return false;
        }
        
        if (monto > tope) {
            System.out.println("Error: El monto a transferir ($" + monto + 
                             ") supera el tope permitido ($" + tope + ")");
            return false;
        }
        
        // Validar CBU destino (18 dígitos)
        if (!validarCbu(cbuDestino)) {
            System.out.println("Error: CBU destino inválido. Debe tener 18 dígitos");
            return false;
        }
        
        // Realizar la transferencia
        if (super.retirar(monto)) {
            System.out.println("=== TRANSFERENCIA REALIZADA ===");
            System.out.println("Monto transferido: $" + String.format("%.2f", monto));
            System.out.println("CBU destino: " + cbuDestino);
            System.out.println("Saldo final de la cuenta: $" + String.format("%.2f", getSaldoActual()));
            System.out.println("===============================");
            return true;
        }
        
        return false;
    }
    
    /**
     * Método sobrecargado para transferir dinero usando alias alfanumérico
     * @param monto Monto a transferir
     * @param alias Alias alfanumérico de la cuenta destino
     * @return true si la transferencia fue exitosa, false en caso contrario
     */
    public boolean transferir(double monto, String alias) {
        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser positivo");
            return false;
        }
        
        if (monto > getSaldoActual()) {
            System.out.println("Error: Saldo insuficiente para la transferencia. Saldo actual: $" + 
                             String.format("%.2f", getSaldoActual()));
            return false;
        }
        
        if (monto > tope) {
            System.out.println("Error: El monto a transferir ($" + monto + 
                             ") supera el tope permitido ($" + tope + ")");
            return false;
        }
        
        // Validar alias (no vacío y alfanumérico)
        if (alias == null || alias.trim().isEmpty()) {
            System.out.println("Error: El alias no puede estar vacío");
            return false;
        }
        
        // Realizar la transferencia
        if (super.retirar(monto)) {
            System.out.println("=== TRANSFERENCIA REALIZADA ===");
            System.out.println("Monto transferido: $" + String.format("%.2f", monto));
            System.out.println("Alias destino: " + alias);
            System.out.println("Saldo final de la cuenta: $" + String.format("%.2f", getSaldoActual()));
            System.out.println("===============================");
            return true;
        }
        
        return false;
    }
    
    /**
     * Valida que el CBU tenga exactamente 18 dígitos
     * @param cbu CBU a validar
     * @return true si es válido, false en caso contrario
     */
    private boolean validarCbu(long cbu) {
        String cbuStr = String.valueOf(Math.abs(cbu)); // Usar valor absoluto para evitar problemas con negativos
        return cbuStr.length() == 18;
    }
    
    /**
     * Sobrescribe el método mostrarDatos para incluir información específica de CuentaSueldo
     */
    @Override
    public void mostrarDatos() {
        System.out.println("=== DATOS DE LA CUENTA SUELDO ===");
        System.out.println("Número de cuenta: " + getNumeroCuenta());
        System.out.println("DNI del cliente: " + getDniCliente());
        System.out.println("Saldo actual: $" + String.format("%.2f", getSaldoActual()));
        System.out.println("Interés anual: " + getInteresAnual() + "%");
        System.out.println("Legajo: " + legajo);
        System.out.println("Institución: " + institucion);
        System.out.println("Beneficios: " + beneficios);
        System.out.println("CBU: " + cbu);
        System.out.println("Tope de retiro/transferencia: $" + String.format("%.2f", tope));
        System.out.println("=================================");
    }
    
    // Getters y Setters específicos
    public int getLegajo() {
        return legajo;
    }
    
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
    
    public String getInstitucion() {
        return institucion;
    }
    
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
    public String getBeneficios() {
        return beneficios;
    }
    
    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }
    
    public long getCbu() {
        return cbu;
    }
    
    public void setCbu(long cbu) {
        if (validarCbu(cbu)) {
            this.cbu = cbu;
        } else {
            System.out.println("Advertencia: CBU inválido. Debe tener 18 dígitos. CBU establecido en 0.");
            this.cbu = 0L;
        }
    }
    
    public double getTope() {
        return tope;
    }
    
    public void setTope(double tope) {
        this.tope = tope;
    }
}
