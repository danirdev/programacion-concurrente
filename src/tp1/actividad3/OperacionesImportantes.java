package tp1.actividad3;

/**
 * Interfaz OperacionesImportantes
 * Define operaciones de alto monto que requieren validaciones especiales
 * @author PC2025
 */
public interface OperacionesImportantes {
    
    /**
     * Permite realizar transferencias de alto monto con validaciones especiales
     * @param monto Monto a transferir (debe ser considerado "alto monto")
     * @return true si la transferencia fue exitosa, false en caso contrario
     */
    boolean transferenciaAltoMonto(double monto);
}
