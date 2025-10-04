package tp6.actividad2.implementacion_b;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase HiloSemaforoExterno que extiende Thread.
 * Recibe un semáforo externo por parámetro (inyección de dependencias).
 * Cada hilo duerme por 5 segundos después de adquirir el semáforo.
 */
public class HiloSemaforoExterno extends Thread {
    
    private final String nombreHilo;
    private final SemaforoExterno semaforo;
    private final DateTimeFormatter timeFormatter;
    private long tiempoInicio;
    private long tiempoFinSueno;
    private boolean completoEjecucion;
    private long tiempoEsperaTotal;
    private boolean usoTryAcquire;
    
    /**
     * Constructor del HiloSemaforoExterno
     * 
     * @param nombre Nombre del hilo
     * @param semaforo Semáforo externo a utilizar
     */
    public HiloSemaforoExterno(String nombre, SemaforoExterno semaforo) {
        this(nombre, semaforo, false);
    }
    
    /**
     * Constructor del HiloSemaforoExterno con opción de tryAcquire
     * 
     * @param nombre Nombre del hilo
     * @param semaforo Semáforo externo a utilizar
     * @param usarTryAcquire Si debe usar tryAcquire en lugar de acquire
     */
    public HiloSemaforoExterno(String nombre, SemaforoExterno semaforo, boolean usarTryAcquire) {
        super(nombre);
        this.nombreHilo = nombre;
        this.semaforo = semaforo;
        this.usoTryAcquire = usarTryAcquire;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.completoEjecucion = false;
        this.tiempoEsperaTotal = 0;
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        tiempoInicio = System.currentTimeMillis();
        
        System.out.println("[" + tiempo + "] 🚀 " + nombreHilo + " iniciado " +
                          "(usando " + semaforo.getIdentificador() + ")");
        
        try {
            boolean adquirido = false;
            long inicioEspera = System.currentTimeMillis();
            
            if (usoTryAcquire) {
                // Intentar adquirir sin bloquear
                adquirido = semaforo.tryAcquire(nombreHilo);
                if (!adquirido) {
                    // Si no pudo adquirir, usar acquire normal
                    semaforo.acquire(nombreHilo);
                    adquirido = true;
                }
            } else {
                // Adquirir el semáforo (método normal)
                semaforo.acquire(nombreHilo);
                adquirido = true;
            }
            
            long finEspera = System.currentTimeMillis();
            tiempoEsperaTotal = finEspera - inicioEspera;
            
            if (adquirido) {
                // Mostrar inicio de sueño
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] 😴 " + nombreHilo + 
                                 " iniciando sueño de 5 segundos... (esperó " + 
                                 tiempoEsperaTotal + "ms en " + semaforo.getIdentificador() + ")");
                
                // Dormir 5 segundos
                Thread.sleep(5000);
                
                // Mostrar fin de sueño
                tiempoFinSueno = System.currentTimeMillis();
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] ⏰ " + nombreHilo + 
                                 " despertó después de 5 segundos");
                
                completoEjecucion = true;
            }
            
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ " + nombreHilo + 
                             " interrumpido durante la ejecución");
            Thread.currentThread().interrupt();
        } finally {
            // Liberar el semáforo (siempre, incluso si hay excepción)
            if (completoEjecucion || tiempoEsperaTotal > 0) {
                semaforo.release(nombreHilo);
            }
            
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
     * Obtiene el semáforo utilizado por este hilo
     * 
     * @return Semáforo externo utilizado
     */
    public SemaforoExterno getSemaforo() {
        return semaforo;
    }
    
    /**
     * Verifica si usó tryAcquire
     * 
     * @return true si usó tryAcquire, false si usó acquire normal
     */
    public boolean isUsoTryAcquire() {
        return usoTryAcquire;
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
            stats.append(" | Semáforo: ").append(semaforo.getIdentificador());
            stats.append(" | Tiempo total: ").append(getTiempoTotalEjecucion()).append("ms");
            stats.append(" | Tiempo espera: ").append(tiempoEsperaTotal).append("ms");
            
            if (tiempoEsperaTotal > 0) {
                double porcentajeEspera = (tiempoEsperaTotal * 100.0) / getTiempoTotalEjecucion();
                stats.append(" | % Espera: ").append(String.format("%.1f%%", porcentajeEspera));
            }
            
            if (usoTryAcquire) {
                stats.append(" | Método: tryAcquire");
            }
        } else {
            stats.append("EN EJECUCIÓN o INTERRUMPIDO");
            stats.append(" | Semáforo: ").append(semaforo.getIdentificador());
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
        return String.format("%s[%s, %s, Espera:%dms]", 
                           nombreHilo, estado, semaforo.getIdentificador(), tiempoEsperaTotal);
    }
    
    /**
     * Calcula la eficiencia del hilo (tiempo útil vs tiempo total)
     * 
     * @return Porcentaje de eficiencia (0-100)
     */
    public double calcularEficiencia() {
        if (!completoEjecucion) {
            return 0.0;
        }
        
        long tiempoTotal = getTiempoTotalEjecucion();
        long tiempoUtil = 5000; // 5 segundos de sueño
        
        if (tiempoTotal <= 0) {
            return 0.0;
        }
        
        return (tiempoUtil * 100.0) / tiempoTotal;
    }
    
    /**
     * Obtiene el tiempo transcurrido desde el inicio
     * 
     * @return Tiempo en milisegundos desde el inicio
     */
    public long getTiempoTranscurrido() {
        if (tiempoInicio > 0) {
            return System.currentTimeMillis() - tiempoInicio;
        }
        return 0;
    }
    
    /**
     * Método toString para representación del hilo
     * 
     * @return Representación string del hilo
     */
    @Override
    public String toString() {
        return String.format("HiloSemaforoExterno[%s, Semáforo:%s, Completado:%s, Espera:%dms]", 
                           nombreHilo, semaforo.getIdentificador(), completoEjecucion, tiempoEsperaTotal);
    }
}
