package tp1.actividad3;

/**
 * Interfaz OperacionesComunes
 * Define operaciones básicas que pueden realizar las cuentas bancarias
 * Incluye 3 atributos (constantes) y 2 métodos
 * @author PC2025
 */
public interface OperacionesComunes {
    
    // 3 atributos de la interfaz (constantes públicas, estáticas y finales por defecto)
    String BANCO_NOMBRE = "Banco Nacional";
    double COMISION_SERVICIO = 50.0; // Comisión por pago de servicios
    int MAX_SERVICIOS_DIARIOS = 10; // Máximo de servicios que se pueden pagar por día
    
    /**
     * Permite pagar un servicio desde la cuenta
     * @return true si el pago fue exitoso, false en caso contrario
     */
    boolean pagarServicio();
    
    /**
     * Permite cambiar el alias de la cuenta
     * @return true si el cambio fue exitoso, false en caso contrario
     */
    boolean cambiarAlias();
}
