package tp1.actividad1;

/**
 * Clase CuentaBancaria que representa una cuenta bancaria con operaciones básicas
 * @author PC2025
 */
public class CuentaBancaria {
    // Atributos de instancia
    private long numeroCuenta;
    private long dniCliente;
    private double saldoActual;
    private double interesAnual; // Porcentaje de interés anual
    
    // Variable estática para llevar el control del número de cuenta correlativo
    private static long ultimoNumeroCuenta = 100000;
    
    /**
     * Constructor por defecto
     * Inicializa la cuenta con valores por defecto
     */
    public CuentaBancaria() {
        this.numeroCuenta = ++ultimoNumeroCuenta;
        this.dniCliente = 0;
        this.saldoActual = 0.0;
        this.interesAnual = 0.0;
    }
    
    /**
     * Constructor con parámetros
     * @param dniCliente DNI del cliente
     * @param saldoActual Saldo inicial de la cuenta
     * @param interesAnual Interés anual aplicable (en porcentaje)
     */
    public CuentaBancaria(long dniCliente, double saldoActual, double interesAnual) {
        this.numeroCuenta = ++ultimoNumeroCuenta;
        this.dniCliente = dniCliente;
        this.saldoActual = saldoActual;
        this.interesAnual = interesAnual;
    }
    
    /**
     * Actualiza el saldo aplicando el interés diario
     * Interés diario = interés anual / 365
     */
    public void actualizarSaldo() {
        double interesDiario = interesAnual / 365.0 / 100.0; // Dividimos por 100 porque está en porcentaje
        this.saldoActual += this.saldoActual * interesDiario;
    }
    
    /**
     * Permite ingresar una cantidad a la cuenta
     * @param cantidad Cantidad a ingresar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean ingresar(double cantidad) {
        if (cantidad > 0) {
            this.saldoActual += cantidad;
            return true;
        }
        System.out.println("Error: La cantidad a ingresar debe ser positiva");
        return false;
    }
    
    /**
     * Permite retirar una cantidad de la cuenta si hay saldo suficiente
     * @param cantidad Cantidad a retirar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= this.saldoActual) {
            this.saldoActual -= cantidad;
            return true;
        } else if (cantidad > this.saldoActual) {
            System.out.println("Error: Saldo insuficiente. Saldo actual: $" + this.saldoActual);
            return false;
        } else {
            System.out.println("Error: La cantidad a retirar debe ser positiva");
            return false;
        }
    }
    
    /**
     * Muestra todos los datos de la cuenta
     */
    public void mostrarDatos() {
        System.out.println("=== DATOS DE LA CUENTA BANCARIA ===");
        System.out.println("Número de cuenta: " + this.numeroCuenta);
        System.out.println("DNI del cliente: " + this.dniCliente);
        System.out.println("Saldo actual: $" + String.format("%.2f", this.saldoActual));
        System.out.println("Interés anual: " + this.interesAnual + "%");
        System.out.println("===================================");
    }
    
    // Métodos getter y setter
    public long getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public long getDniCliente() {
        return dniCliente;
    }
    
    public void setDniCliente(long dniCliente) {
        this.dniCliente = dniCliente;
    }
    
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public double getInteresAnual() {
        return interesAnual;
    }
    
    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }
    
    /**
     * Método estático para obtener el último número de cuenta asignado
     * @return último número de cuenta asignado
     */
    public static long getUltimoNumeroCuenta() {
        return ultimoNumeroCuenta;
    }
}
