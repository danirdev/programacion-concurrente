package tp1.actividad3;

/**
 * Interfaz OperacionesBancarias
 * Hereda de OperacionesComunes y OperacionesImportantes
 * Combina todas las operaciones bancarias en una sola interfaz
 * @author PC2025
 */
public interface OperacionesBancarias extends OperacionesComunes, OperacionesImportantes {
    
    // Esta interfaz hereda todos los métodos y atributos de:
    // - OperacionesComunes: BANCO_NOMBRE, COMISION_SERVICIO, MAX_SERVICIOS_DIARIOS, pagarServicio(), cambiarAlias()
    // - OperacionesImportantes: transferenciaAltoMonto(double monto)
    
    // Se pueden agregar métodos adicionales específicos si es necesario
    // Por ahora, la interfaz actúa como un contenedor que agrupa las otras dos interfaces
}
