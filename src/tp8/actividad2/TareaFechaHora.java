package tp8.actividad2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 📅 TareaFechaHora - Tarea1: Captura fecha/hora y almacena en lista
 * 
 * Esta tarea se ejecuta periódicamente cada 2 segundos, capturando la
 * fecha y hora actual del sistema y almacenándola en la lista compartida.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaFechaHora implements Runnable {
    
    // 📋 Referencia a la lista compartida
    private final ListaCompartida lista;
    
    // 📊 Contador de ejecuciones
    private int numeroEjecucion;
    
    // 📝 Formato de fecha/hora: HH:mm:ss:S
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
    
    /**
     * 🏗️ Constructor de TareaFechaHora
     * 
     * @param lista Lista compartida donde almacenar timestamps
     */
    public TareaFechaHora(ListaCompartida lista) {
        this.lista = lista;
        this.numeroEjecucion = 0;
        
        System.out.println("📅 Tarea1 (FechaHora) creada");
    }
    
    /**
     * 🏃‍♂️ Método run - Ejecuta la captura de fecha/hora
     */
    @Override
    public void run() {
        try {
            numeroEjecucion++;
            
            // 🕐 Obtener fecha/hora actual
            LocalDateTime ahora = LocalDateTime.now();
            String timestamp = ahora.format(FORMATTER);
            
            // 📋 Almacenar en lista compartida
            lista.agregar(timestamp);
            
            System.out.printf("[Tarea1 #%d] 📅 Timestamp capturado: %s%n", 
                             numeroEjecucion, timestamp);
            
        } catch (Exception e) {
            System.err.printf("❌ Error en Tarea1: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 📊 Obtener número de ejecuciones
     * 
     * @return Número de veces que se ha ejecutado la tarea
     */
    public int getNumeroEjecucion() {
        return numeroEjecucion;
    }
    
    /**
     * 📝 Representación en string de la tarea
     * 
     * @return Información de la tarea
     */
    @Override
    public String toString() {
        return String.format("TareaFechaHora{ejecuciones=%d}", numeroEjecucion);
    }
}
