package tp8.actividad2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 📋 ListaCompartida - Lista thread-safe para almacenar timestamps
 * 
 * Esta clase encapsula una lista sincronizada para almacenar de forma segura
 * los timestamps capturados por Tarea1 y procesados por Tarea2.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ListaCompartida {
    
    // 📋 Lista sincronizada de timestamps
    private final List<String> timestamps;
    
    // 📊 Estadísticas
    private int totalAgregados;
    private int totalLeidos;
    
    /**
     * 🏗️ Constructor de ListaCompartida
     */
    public ListaCompartida() {
        // Crear lista sincronizada (thread-safe)
        this.timestamps = Collections.synchronizedList(new ArrayList<>());
        this.totalAgregados = 0;
        this.totalLeidos = 0;
        
        System.out.println("📋 ListaCompartida inicializada (thread-safe)");
    }
    
    /**
     * ➕ Agregar timestamp a la lista
     * 
     * @param timestamp Timestamp en formato HH:mm:ss:S
     */
    public void agregar(String timestamp) {
        synchronized (timestamps) {
            timestamps.add(timestamp);
            totalAgregados++;
            System.out.printf("📋 [Lista] Agregado: %s (Total: %d)%n", timestamp, timestamps.size());
        }
    }
    
    /**
     * 📖 Obtener el último timestamp de la lista
     * 
     * @return Último timestamp o null si la lista está vacía
     */
    public String obtenerUltimo() {
        synchronized (timestamps) {
            if (timestamps.isEmpty()) {
                System.out.println("⚠️ [Lista] Vacía - No hay timestamps");
                return null;
            }
            
            String ultimo = timestamps.get(timestamps.size() - 1);
            totalLeidos++;
            System.out.printf("📖 [Lista] Leído último: %s%n", ultimo);
            return ultimo;
        }
    }
    
    /**
     * 🔢 Obtener tamaño de la lista
     * 
     * @return Número de timestamps en la lista
     */
    public int size() {
        synchronized (timestamps) {
            return timestamps.size();
        }
    }
    
    /**
     * 🔍 Verificar si la lista está vacía
     * 
     * @return true si la lista está vacía
     */
    public boolean isEmpty() {
        synchronized (timestamps) {
            return timestamps.isEmpty();
        }
    }
    
    /**
     * 📊 Obtener estadísticas de la lista
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        synchronized (timestamps) {
            StringBuilder stats = new StringBuilder();
            stats.append("\n=== 📊 ESTADÍSTICAS LISTA COMPARTIDA ===\n");
            stats.append(String.format("📋 Tamaño actual: %d%n", timestamps.size()));
            stats.append(String.format("➕ Total agregados: %d%n", totalAgregados));
            stats.append(String.format("📖 Total leídos: %d%n", totalLeidos));
            
            if (timestamps.size() > 0) {
                stats.append(String.format("🕐 Primer timestamp: %s%n", timestamps.get(0)));
                stats.append(String.format("🕐 Último timestamp: %s%n", timestamps.get(timestamps.size() - 1)));
            }
            
            return stats.toString();
        }
    }
    
    /**
     * 📋 Obtener todos los timestamps
     * 
     * @return Lista de timestamps (copia segura)
     */
    public List<String> obtenerTodos() {
        synchronized (timestamps) {
            return new ArrayList<>(timestamps);
        }
    }
    
    /**
     * 🧹 Limpiar la lista
     */
    public void limpiar() {
        synchronized (timestamps) {
            timestamps.clear();
            System.out.println("🧹 [Lista] Lista limpiada");
        }
    }
    
    /**
     * 📝 Representación en string de la lista
     * 
     * @return Información de la lista
     */
    @Override
    public String toString() {
        synchronized (timestamps) {
            return String.format("ListaCompartida{size=%d, agregados=%d, leídos=%d}", 
                               timestamps.size(), totalAgregados, totalLeidos);
        }
    }
}
