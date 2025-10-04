package tp6.actividad2.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase HiloSemaforoInterno que extiende Thread.
 * Utiliza el semáforo interno definido en la clase SemaforoInterno.
 * Cada hilo duerme por 5 segundos después de adquirir el semáforo.
 */
public class HiloSemaforoInterno extends Thread {
    
    private final String nombreHilo;
    private final DateTimeFormatter timeFormatter;
    private long tiempoInicio;
    private long tiempoFinSueno;
    private boolean completoEjecucion;
    private long tiempoEsperaTotal;
    
    /**
     * Constructor del HiloSemaforoInterno
     * 
     * @param nombre Nombre del hilo
     */
    public HiloSemaforoInterno(String nombre) {
        super(nombre);
        this.nombreHilo = nombre;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.completoEjecucion = false;
        this.tiempoEsperaTotal = 0;
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        tiempoInicio = System.currentTimeMillis();
        
        System.out.println("[" + tiempo + "] 🚀 " + nombreHilo + " iniciado");
        
        try {
            // Adquirir el semáforo
            long inicioEspera = System.currentTimeMillis();
            SemaforoInterno.acquire(nombreHilo);
            long finEspera = System.currentTimeMillis();
            tiempoEsperaTotal = finEspera - inicioEspera;
            
            // Mostrar inicio de sueño
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 😴 " + nombreHilo + 
                             " iniciando sueño de 5 segundos... (esperó " + 
                             tiempoEsperaTotal + "ms)");
            
            // Dormir 5 segundos
            Thread.sleep(5000);
            
            // Mostrar fin de sueño
            tiempoFinSueno = System.currentTimeMillis();
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ⏰ " + nombreHilo + 
                             " despertó después de 5 segundos");
            
            completoEjecucion = true;
            
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ " + nombreHilo + 
                             " interrumpido durante la ejecución");
            Thread.currentThread().interrupt();
        } finally {
            // Liberar el semáforo (siempre, incluso si hay excepción)
            SemaforoInterno.release(nombreHilo);
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🏁 " + nombreHilo + 
                             " finalizó su ejecución");
        }
    }
    
    /**
     * Obtiene el tiempo total de espera para adquirir el semáforo
     * 
     * @return Tiempo de espera en milisegundos
     */
    public long getTiempoEspera() {
        return tiempoEsperaTotal;
    }
    
    /**
     * Verifica si el hilo completó su ejecución exitosamente
     * 
     * @return true si completó la ejecución, false en caso contrario
     */
    public boolean isCompletoEjecucion() {
        return completoEjecucion;
    }
    
    /**
     * Obtiene el tiempo total de ejecución del hilo
     * 
     * @return Tiempo total en milisegundos
     */
    public long getTiempoTotalEjecucion() {
        if (tiempoFinSueno > 0 && tiempoInicio > 0) {
            return tiempoFinSueno - tiempoInicio;
        }
        return -1; // No completó la ejecución
    }
    
    /**
     * Obtiene el nombre del hilo
     * 
     * @return Nombre del hilo
     */
    public String getNombreHilo() {
        return nombreHilo;
    }
    
    /**
     * Obtiene estadísticas detalladas del hilo
     * 
     * @return String con estadísticas del hilo
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append(nombreHilo).append(": ");
        
        if (completoEjecucion) {
            stats.append("COMPLETADO");
            stats.append(" | Tiempo total: ").append(getTiempoTotalEjecucion()).append("ms");
            stats.append(" | Tiempo espera: ").append(tiempoEsperaTotal).append("ms");
            
            if (tiempoEsperaTotal > 0) {
                double porcentajeEspera = (tiempoEsperaTotal * 100.0) / getTiempoTotalEjecucion();
                stats.append(" | % Espera: ").append(String.format("%.1f%%", porcentajeEspera));
            }
        } else {
            stats.append("EN EJECUCIÓN o INTERRUMPIDO");
            if (tiempoInicio > 0) {
                long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
                stats.append(" | Tiempo transcurrido: ").append(tiempoTranscurrido).append("ms");
            }
        }
        
        return stats.toString();
    }
    
    /**
     * Verifica si el hilo tuvo que esperar para adquirir el semáforo
     * 
     * @return true si esperó más de 100ms, false en caso contrario
     */
    public boolean tuvoQueEsperar() {
        return tiempoEsperaTotal > 100; // Más de 100ms se considera espera significativa
    }
    
    /**
     * Obtiene información resumida del hilo
     * 
     * @return String con información básica del hilo
     */
    public String getInfoResumida() {
        String estado = completoEjecucion ? "Completado" : 
                       isAlive() ? "Ejecutando" : "Detenido";
        return String.format("%s[%s, Espera:%dms]", nombreHilo, estado, tiempoEsperaTotal);
    }
    
    /**
     * Método toString para representación del hilo
     * 
     * @return Representación string del hilo
     */
    @Override
    public String toString() {
        return String.format("HiloSemaforoInterno[%s, Completado:%s, Espera:%dms]", 
                           nombreHilo, completoEjecucion, tiempoEsperaTotal);
    }
}
