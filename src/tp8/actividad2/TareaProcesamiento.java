package tp8.actividad2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 🔢 TareaProcesamiento - Tarea2: Procesa milisegundos y verifica si es primo
 * 
 * Esta tarea se ejecuta periódicamente cada 2 segundos, tomando el último
 * timestamp de la lista, extrayendo los milisegundos y verificando si es
 * número primo para guardarlo en el archivo correspondiente.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaProcesamiento implements Runnable {
    
    // 📋 Referencia a la lista compartida
    private final ListaCompartida lista;
    
    // 📄 Nombres de archivos
    private static final String ARCHIVO_PRIMOS = "Primos.txt";
    private static final String ARCHIVO_NO_PRIMOS = "NoPrimos.txt";
    
    // 📊 Estadísticas
    private int numeroEjecucion;
    private int primosEncontrados;
    private int noPrimosEncontrados;
    
    /**
     * 🏗️ Constructor de TareaProcesamiento
     * 
     * @param lista Lista compartida de donde leer timestamps
     */
    public TareaProcesamiento(ListaCompartida lista) {
        this.lista = lista;
        this.numeroEjecucion = 0;
        this.primosEncontrados = 0;
        this.noPrimosEncontrados = 0;
        
        System.out.println("🔢 Tarea2 (Procesamiento) creada");
    }
    
    /**
     * 🏃‍♂️ Método run - Ejecuta el procesamiento de milisegundos
     */
    @Override
    public void run() {
        try {
            numeroEjecucion++;
            
            // 📖 Obtener último timestamp de la lista
            String timestamp = lista.obtenerUltimo();
            
            if (timestamp == null) {
                System.out.printf("[Tarea2 #%d] ⚠️ No hay timestamps para procesar%n", numeroEjecucion);
                return;
            }
            
            // 🔢 Extraer milisegundos del timestamp (formato: HH:mm:ss:SSS)
            int milisegundos = extraerMilisegundos(timestamp);
            
            System.out.printf("[Tarea2 #%d] 🔢 Procesando timestamp: %s → milisegundos: %d%n", 
                             numeroEjecucion, timestamp, milisegundos);
            
            // ✅ Verificar si es primo
            boolean esPrimo = VerificadorPrimos.esPrimo(milisegundos);
            
            // 📄 Guardar en archivo correspondiente
            if (esPrimo) {
                guardarEnArchivo(ARCHIVO_PRIMOS, milisegundos, timestamp);
                primosEncontrados++;
                System.out.printf("[Tarea2 #%d] ✨ %d ES PRIMO → Guardado en %s%n", 
                                 numeroEjecucion, milisegundos, ARCHIVO_PRIMOS);
            } else {
                guardarEnArchivo(ARCHIVO_NO_PRIMOS, milisegundos, timestamp);
                noPrimosEncontrados++;
                System.out.printf("[Tarea2 #%d] ❌ %d NO es primo → Guardado en %s%n", 
                                 numeroEjecucion, milisegundos, ARCHIVO_NO_PRIMOS);
            }
            
        } catch (Exception e) {
            System.err.printf("❌ Error en Tarea2: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 🔢 Extraer milisegundos del timestamp
     * 
     * @param timestamp Timestamp en formato HH:mm:ss:SSS
     * @return Valor de milisegundos
     */
    private int extraerMilisegundos(String timestamp) {
        try {
            // Formato esperado: HH:mm:ss:SSS
            // Extraer los últimos 3 dígitos (milisegundos)
            String[] partes = timestamp.split(":");
            if (partes.length >= 4) {
                return Integer.parseInt(partes[3]);
            } else {
                // Formato alternativo sin milisegundos
                return 0;
            }
        } catch (Exception e) {
            System.err.printf("⚠️ Error extrayendo milisegundos de '%s': %s%n", 
                             timestamp, e.getMessage());
            return 0;
        }
    }
    
    /**
     * 📄 Guardar número en archivo
     * 
     * @param nombreArchivo Nombre del archivo
     * @param numero Número a guardar
     * @param timestamp Timestamp original
     */
    private void guardarEnArchivo(String nombreArchivo, int numero, String timestamp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            String linea = String.format("[%s] Milisegundos: %d%n", timestamp, numero);
            writer.write(linea);
            writer.flush();
        } catch (IOException e) {
            System.err.printf("❌ Error escribiendo en archivo '%s': %s%n", 
                             nombreArchivo, e.getMessage());
        }
    }
    
    /**
     * 📊 Obtener estadísticas de procesamiento
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📊 ESTADÍSTICAS TAREA PROCESAMIENTO ===\n");
        stats.append(String.format("🔄 Ejecuciones: %d%n", numeroEjecucion));
        stats.append(String.format("✨ Primos encontrados: %d%n", primosEncontrados));
        stats.append(String.format("❌ No primos encontrados: %d%n", noPrimosEncontrados));
        
        if (numeroEjecucion > 0) {
            double porcentajePrimos = (primosEncontrados * 100.0) / numeroEjecucion;
            stats.append(String.format("📊 Porcentaje primos: %.1f%%%n", porcentajePrimos));
        }
        
        stats.append(String.format("📄 Archivo primos: %s%n", ARCHIVO_PRIMOS));
        stats.append(String.format("📄 Archivo no primos: %s%n", ARCHIVO_NO_PRIMOS));
        
        return stats.toString();
    }
    
    /**
     * 🔢 Obtener número de primos encontrados
     * 
     * @return Cantidad de números primos
     */
    public int getPrimosEncontrados() {
        return primosEncontrados;
    }
    
    /**
     * 🔢 Obtener número de no primos encontrados
     * 
     * @return Cantidad de números no primos
     */
    public int getNoPrimosEncontrados() {
        return noPrimosEncontrados;
    }
    
    /**
     * 📝 Representación en string de la tarea
     * 
     * @return Información de la tarea
     */
    @Override
    public String toString() {
        return String.format("TareaProcesamiento{ejecuciones=%d, primos=%d, noPrimos=%d}", 
                           numeroEjecucion, primosEncontrados, noPrimosEncontrados);
    }
}
