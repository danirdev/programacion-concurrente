package tp6.actividad3.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase AutomovilPeaje que extiende Thread.
 * Representa un automóvil que llega a la estación de peaje para ser atendido.
 * Implementación A: No se identifica qué cabina específica atiende al cliente.
 */
public class AutomovilPeaje extends Thread {
    
    private final int numeroCliente;
    private final EstacionPeajeSimple estacionPeaje;
    private final DateTimeFormatter timeFormatter;
    private final Random random;
    
    // Estado del automóvil
    private long tiempoLlegada;
    private long tiempoInicioAtencion;
    private long tiempoFinAtencion;
    private boolean completoAtencion;
    private String estadoActual;
    
    /**
     * Constructor del AutomovilPeaje
     * 
     * @param numeroCliente Número del cliente (1-50)
     * @param estacionPeaje Estación de peaje donde será atendido
     */
    public AutomovilPeaje(int numeroCliente, EstacionPeajeSimple estacionPeaje) {
        super("Cliente-" + String.format("%02d", numeroCliente));
        this.numeroCliente = numeroCliente;
        this.estacionPeaje = estacionPeaje;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.random = new Random();
        this.completoAtencion = false;
        this.estadoActual = "CREADO";
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        try {
            // 1. LLEGADA A LA ESTACIÓN
            tiempoLlegada = System.currentTimeMillis();
            estadoActual = "LLEGANDO";
            
            // Simular tiempo de llegada variable (algunos llegan más tarde)
            if (numeroCliente > 1) {
                int tiempoLlegada = 100 + random.nextInt(300); // 100-400ms entre llegadas
                Thread.sleep(tiempoLlegada);
            }
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🚗 " + getName() + 
                             " llegó a la estación de peaje");
            
            // 2. ESPERAR Y SER ATENDIDO
            estadoActual = "ESPERANDO_ATENCION";
            tiempoInicioAtencion = System.currentTimeMillis();
            
            estacionPeaje.atenderCliente(numeroCliente);
            
            tiempoFinAtencion = System.currentTimeMillis();
            
            // 3. COMPLETAR PROCESO
            estadoActual = "COMPLETADO";
            completoAtencion = true;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🛣️ " + getName() + 
                             " salió de la estación y continuó su viaje " +
                             "(Tiempo total en estación: " + getTiempoTotalEnEstacion() + "ms)");
            
        } catch (InterruptedException e) {
            estadoActual = "INTERRUMPIDO";
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ " + getName() + 
                             " interrumpido en estado: " + estadoActual);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            estadoActual = "ERROR";
            tiempo = LocalTime.now().format(timeFormatter);
            System.err.println("[" + tiempo + "] 💥 Error en " + getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene el número del cliente
     * 
     * @return Número del cliente
     */
    public int getNumeroCliente() {
        return numeroCliente;
    }
    
    /**
     * Verifica si completó la atención
     * 
     * @return true si completó la atención, false en caso contrario
     */
    public boolean isCompletoAtencion() {
        return completoAtencion;
    }
    
    /**
     * Obtiene el estado actual del automóvil
     * 
     * @return Estado actual como String
     */
    public String getEstadoActual() {
        return estadoActual;
    }
    
    /**
     * Obtiene el tiempo total que estuvo en la estación
     * 
     * @return Tiempo en milisegundos desde llegada hasta salida
     */
    public long getTiempoTotalEnEstacion() {
        if (tiempoFinAtencion > 0 && tiempoLlegada > 0) {
            return tiempoFinAtencion - tiempoLlegada;
        }
        return -1; // No completó el proceso
    }
    
    /**
     * Obtiene el tiempo de espera antes de ser atendido
     * 
     * @return Tiempo en milisegundos desde llegada hasta inicio de atención
     */
    public long getTiempoEspera() {
        if (tiempoInicioAtencion > 0 && tiempoLlegada > 0) {
            return tiempoInicioAtencion - tiempoLlegada;
        }
        return -1; // No fue atendido aún
    }
    
    /**
     * Obtiene el tiempo de atención (tiempo que tardó en ser atendido)
     * 
     * @return Tiempo en milisegundos de duración de la atención
     */
    public long getTiempoAtencion() {
        if (tiempoFinAtencion > 0 && tiempoInicioAtencion > 0) {
            return tiempoFinAtencion - tiempoInicioAtencion;
        }
        return -1; // No completó la atención
    }
    
    /**
     * Verifica si tuvo que esperar para ser atendido
     * 
     * @return true si esperó más de 500ms, false en caso contrario
     */
    public boolean tuvoQueEsperar() {
        return getTiempoEspera() > 500; // Más de 500ms se considera espera significativa
    }
    
    /**
     * Calcula la eficiencia del proceso (tiempo de atención vs tiempo total)
     * 
     * @return Porcentaje de eficiencia (0-100)
     */
    public double calcularEficiencia() {
        if (!completoAtencion) {
            return 0.0;
        }
        
        long tiempoTotal = getTiempoTotalEnEstacion();
        long tiempoAtencion = getTiempoAtencion();
        
        if (tiempoTotal <= 0) {
            return 0.0;
        }
        
        return (tiempoAtencion * 100.0) / tiempoTotal;
    }
    
    /**
     * Obtiene el tiempo transcurrido desde la llegada
     * 
     * @return Tiempo en milisegundos desde la llegada
     */
    public long getTiempoTranscurrido() {
        if (tiempoLlegada > 0) {
            return System.currentTimeMillis() - tiempoLlegada;
        }
        return 0;
    }
    
    /**
     * Obtiene estadísticas detalladas del automóvil
     * 
     * @return String con estadísticas completas
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append(getName()).append(": ");
        
        if (completoAtencion) {
            stats.append("COMPLETADO");
            stats.append(" | Tiempo total: ").append(getTiempoTotalEnEstacion()).append("ms");
            stats.append(" | Tiempo espera: ").append(getTiempoEspera()).append("ms");
            stats.append(" | Tiempo atención: ").append(getTiempoAtencion()).append("ms");
            stats.append(" | Eficiencia: ").append(String.format("%.1f%%", calcularEficiencia()));
            
            if (tuvoQueEsperar()) {
                stats.append(" | ⏳ Esperó");
            } else {
                stats.append(" | ⚡ Sin espera");
            }
        } else {
            stats.append(estadoActual);
            if (tiempoLlegada > 0) {
                stats.append(" | Tiempo transcurrido: ").append(getTiempoTranscurrido()).append("ms");
            }
        }
        
        return stats.toString();
    }
    
    /**
     * Obtiene información resumida del automóvil
     * 
     * @return String con información básica
     */
    public String getInfoResumida() {
        String estado = completoAtencion ? "Completado" : estadoActual;
        String tiempoInfo = completoAtencion ? 
            String.format("(%dms)", getTiempoTotalEnEstacion()) :
            String.format("(%dms transcurridos)", getTiempoTranscurrido());
        
        return String.format("%s[%s %s]", getName(), estado, tiempoInfo);
    }
    
    /**
     * Obtiene información sobre la experiencia del cliente
     * 
     * @return String con evaluación de la experiencia
     */
    public String getExperienciaCliente() {
        if (!completoAtencion) {
            return "En proceso";
        }
        
        long tiempoEspera = getTiempoEspera();
        
        if (tiempoEspera < 1000) {
            return "✅ Excelente (sin espera)";
        } else if (tiempoEspera < 5000) {
            return "✅ Buena (espera corta)";
        } else if (tiempoEspera < 15000) {
            return "⚠️ Regular (espera media)";
        } else {
            return "❌ Mala (espera larga)";
        }
    }
    
    /**
     * Verifica si el cliente llegó antes de que se abriera la cabina 3
     * 
     * @return true si llegó en los primeros 15 segundos, false en caso contrario
     */
    public boolean llegoAntesCabina3() {
        return tiempoLlegada > 0 && (System.currentTimeMillis() - tiempoLlegada) < 15000;
    }
    
    /**
     * Método toString para representación del automóvil
     * 
     * @return Representación string del automóvil
     */
    @Override
    public String toString() {
        return String.format("AutomovilPeaje[Cliente:%d, Estado:%s, Completado:%s]", 
                           numeroCliente, estadoActual, completoAtencion);
    }
}
