package tp8.actividad4;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * 🔍 TareaMonitoreoDirectorio - Tarea periódica de monitoreo de directorio
 * 
 * Esta clase implementa Runnable y se ejecuta periódicamente para detectar
 * nuevos archivos en un directorio monitoreado.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaMonitoreoDirectorio implements Runnable {
    
    // 📁 Directorio a monitorear
    private final File directorio;
    
    // 📋 Conjunto de archivos conocidos
    private Set<String> archivosConocidos;
    
    // 📊 Estadísticas
    private int totalEscaneos;
    private int archivosNuevosDetectados;
    
    // 📝 Formato de tiempo
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * 🏗️ Constructor de TareaMonitoreoDirectorio
     * 
     * @param rutaDirectorio Ruta del directorio a monitorear
     */
    public TareaMonitoreoDirectorio(String rutaDirectorio) {
        this.directorio = new File(rutaDirectorio);
        this.archivosConocidos = new HashSet<>();
        this.totalEscaneos = 0;
        this.archivosNuevosDetectados = 0;
        
        // Validar que el directorio existe y es accesible
        if (!directorio.exists()) {
            System.err.printf("⚠️ El directorio '%s' no existe. Creándolo...%n", rutaDirectorio);
            if (directorio.mkdirs()) {
                System.out.printf("✅ Directorio '%s' creado exitosamente%n", rutaDirectorio);
            }
        }
        
        if (!directorio.isDirectory()) {
            throw new IllegalArgumentException("La ruta especificada no es un directorio: " + rutaDirectorio);
        }
        
        // Cargar archivos iniciales
        cargarArchivosIniciales();
    }
    
    /**
     * 📋 Cargar archivos iniciales del directorio
     */
    private void cargarArchivosIniciales() {
        File[] archivos = directorio.listFiles();
        
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    archivosConocidos.add(archivo.getName());
                }
            }
        }
        
        System.out.printf("📋 Archivos iniciales encontrados: %d%n", archivosConocidos.size());
    }
    
    /**
     * 🏃‍♂️ Método run - Ejecuta el monitoreo del directorio
     */
    @Override
    public void run() {
        try {
            totalEscaneos++;
            String horaEscaneo = LocalTime.now().format(FORMATTER);
            
            System.out.printf("[%s] 🔍 Escaneando directorio...%n", horaEscaneo);
            
            // 📂 Listar archivos actuales en el directorio
            File[] archivosActuales = directorio.listFiles();
            
            if (archivosActuales == null) {
                System.err.printf("[%s] ❌ Error al leer el directorio%n", horaEscaneo);
                return;
            }
            
            // 🔍 Detectar archivos nuevos
            Set<String> nombresActuales = new HashSet<>();
            int nuevosEncontrados = 0;
            
            for (File archivo : archivosActuales) {
                if (archivo.isFile()) {
                    String nombreArchivo = archivo.getName();
                    nombresActuales.add(nombreArchivo);
                    
                    // ✨ Si es un archivo nuevo, notificar
                    if (!archivosConocidos.contains(nombreArchivo)) {
                        ArchivoInfo info = new ArchivoInfo(archivo);
                        System.out.printf("[%s] %s%n", horaEscaneo, info.getMensajeNotificacion());
                        nuevosEncontrados++;
                        archivosNuevosDetectados++;
                    }
                }
            }
            
            // 📊 Actualizar lista de archivos conocidos
            archivosConocidos = nombresActuales;
            
            // 📋 Resumen del escaneo
            if (nuevosEncontrados > 0) {
                System.out.printf("[%s] ✅ Escaneo completado - %d archivo(s) nuevo(s) detectado(s)%n", 
                                 horaEscaneo, nuevosEncontrados);
            } else {
                System.out.printf("[%s] ✅ Escaneo completado - %d archivo(s) (sin cambios)%n", 
                                 horaEscaneo, archivosConocidos.size());
            }
            
        } catch (Exception e) {
            System.err.printf("❌ Error en monitoreo: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 📊 Obtener estadísticas del monitoreo
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📊 ESTADÍSTICAS DEL MONITOR ===\n");
        stats.append(String.format("📁 Directorio: %s%n", directorio.getAbsolutePath()));
        stats.append(String.format("🔢 Total escaneos: %d%n", totalEscaneos));
        stats.append(String.format("🆕 Archivos nuevos detectados: %d%n", archivosNuevosDetectados));
        stats.append(String.format("📁 Archivos actuales: %d%n", archivosConocidos.size()));
        
        if (totalEscaneos > 0) {
            double promedioNuevosPorEscaneo = (double) archivosNuevosDetectados / totalEscaneos;
            stats.append(String.format("📊 Promedio archivos nuevos/escaneo: %.2f%n", promedioNuevosPorEscaneo));
        }
        
        return stats.toString();
    }
    
    /**
     * 📋 Listar archivos actuales
     * 
     * @return String con lista de archivos
     */
    public String listarArchivosActuales() {
        StringBuilder lista = new StringBuilder();
        lista.append("\n📋 ARCHIVOS ACTUALES:\n");
        
        if (archivosConocidos.isEmpty()) {
            lista.append("   (Directorio vacío)\n");
        } else {
            int contador = 1;
            for (String nombreArchivo : archivosConocidos) {
                File archivo = new File(directorio, nombreArchivo);
                if (archivo.exists()) {
                    ArchivoInfo info = new ArchivoInfo(archivo);
                    lista.append(String.format("   %d. %s - %s%n", contador++, nombreArchivo, info.getTamañoFormateado()));
                }
            }
        }
        
        return lista.toString();
    }
    
    // 🔧 Getters
    
    public int getTotalEscaneos() { return totalEscaneos; }
    
    public int getArchivosNuevosDetectados() { return archivosNuevosDetectados; }
    
    public int getArchivosActuales() { return archivosConocidos.size(); }
    
    public String getRutaDirectorio() { return directorio.getAbsolutePath(); }
    
    /**
     * 📝 Representación en string de la tarea
     * 
     * @return Información de la tarea
     */
    @Override
    public String toString() {
        return String.format("TareaMonitoreoDirectorio{directorio='%s', escaneos=%d, nuevos=%d}", 
                           directorio.getName(), totalEscaneos, archivosNuevosDetectados);
    }
}
