package tp8.actividad4;

import java.io.File;

/**
 * 📄 ArchivoInfo - Información de un archivo
 * 
 * Esta clase encapsula la información relevante de un archivo detectado
 * en el directorio monitoreado.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ArchivoInfo {
    
    // 🏷️ Información del archivo
    private final String nombre;
    private final long tamaño;
    private final long fechaModificacion;
    
    /**
     * 🏗️ Constructor de ArchivoInfo
     * 
     * @param archivo File del cual extraer información
     */
    public ArchivoInfo(File archivo) {
        this.nombre = archivo.getName();
        this.tamaño = archivo.length();
        this.fechaModificacion = archivo.lastModified();
    }
    
    /**
     * 📏 Formatear tamaño de archivo de forma legible
     * 
     * @return Tamaño formateado (bytes, KB, MB, GB)
     */
    public String getTamañoFormateado() {
        if (tamaño < 1024) {
            return tamaño + " bytes";
        } else if (tamaño < 1024 * 1024) {
            return String.format("%.2f KB", tamaño / 1024.0);
        } else if (tamaño < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", tamaño / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", tamaño / (1024.0 * 1024 * 1024));
        }
    }
    
    /**
     * 📝 Obtener mensaje de notificación
     * 
     * @return String con formato de notificación
     */
    public String getMensajeNotificacion() {
        return String.format("🆕 Nuevo archivo [%s], con tamaño [%s]", nombre, getTamañoFormateado());
    }
    
    // 🔧 Getters
    
    public String getNombre() { return nombre; }
    
    public long getTamaño() { return tamaño; }
    
    public long getFechaModificacion() { return fechaModificacion; }
    
    /**
     * 📝 Representación en string del archivo
     * 
     * @return Información del archivo
     */
    @Override
    public String toString() {
        return String.format("ArchivoInfo{nombre='%s', tamaño=%s}", nombre, getTamañoFormateado());
    }
    
    /**
     * ⚖️ Equals basado en nombre de archivo
     * 
     * @param obj Objeto a comparar
     * @return true si los nombres son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArchivoInfo that = (ArchivoInfo) obj;
        return nombre.equals(that.nombre);
    }
    
    /**
     * #️⃣ HashCode basado en nombre de archivo
     * 
     * @return Hash del nombre
     */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
