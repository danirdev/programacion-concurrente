package tp5.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase Automovil que implementa Runnable.
 * Representa un automóvil que entra al estacionamiento, permanece un tiempo
 * y luego sale. Utiliza entradas y salidas aleatorias.
 */
public class Automovil implements Runnable {
    private final Estacionamiento estacionamiento;
    private final int automovilId;
    private final Random random;
    private final int TIEMPO_MIN_PERMANENCIA = 2000;  // 2 segundos mínimo
    private final int TIEMPO_MAX_PERMANENCIA = 8000;  // 8 segundos máximo
    private final DateTimeFormatter timeFormatter;
    private boolean completoCiclo;
    private long tiempoLlegada;
    private long tiempoEntrada;
    private long tiempoSalida;
    private int entradaUsada;
    private int salidaUsada;
    private volatile boolean activo = true;
    
    /**
     * Constructor del Automóvil
     * @param estacionamiento Estacionamiento compartido
     * @param automovilId Identificador único del automóvil
     */
    public Automovil(Estacionamiento estacionamiento, int automovilId) {
        this.estacionamiento = estacionamiento;
        this.automovilId = automovilId;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.completoCiclo = false;
        this.entradaUsada = random.nextInt(2) + 1; // Entrada 1 o 2
        this.salidaUsada = random.nextInt(2) + 1;  // Salida 1 o 2
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        
        try {
            // 1. LLEGADA AL ESTACIONAMIENTO
            tiempoLlegada = System.currentTimeMillis();
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🚗 Automóvil-" + String.format("%03d", automovilId) + 
                             " llegó al estacionamiento (Hilo: " + nombreHilo + 
                             ", Entrada objetivo: " + entradaUsada + ")");
            
            if (!activo) return;
            
            // 2. INTENTAR ENTRAR AL ESTACIONAMIENTO
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Automóvil-" + String.format("%03d", automovilId) + 
                             " esperando entrada por Entrada-" + entradaUsada);
            
            estacionamiento.entrar(automovilId, entradaUsada);
            tiempoEntrada = System.currentTimeMillis();
            
            if (!activo) return;
            
            // 3. PERMANECER EN EL ESTACIONAMIENTO
            int tiempoPermanencia = TIEMPO_MIN_PERMANENCIA + 
                                  random.nextInt(TIEMPO_MAX_PERMANENCIA - TIEMPO_MIN_PERMANENCIA + 1);
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🅿️ Automóvil-" + String.format("%03d", automovilId) + 
                             " estacionado (permanecerá " + tiempoPermanencia + "ms, " +
                             "saldrá por Salida-" + salidaUsada + ")");
            
            Thread.sleep(tiempoPermanencia);
            
            if (!activo) return;
            
            // 4. SALIR DEL ESTACIONAMIENTO
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Automóvil-" + String.format("%03d", automovilId) + 
                             " terminó permanencia, dirigiéndose a Salida-" + salidaUsada);
            
            estacionamiento.salir(automovilId, salidaUsada);
            tiempoSalida = System.currentTimeMillis();
            
            // 5. COMPLETAR CICLO
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ Automóvil-" + String.format("%03d", automovilId) + 
                             " completó su ciclo (E" + entradaUsada + "→S" + salidaUsada + 
                             ", Total: " + getTiempoTotalEnSistema() + "ms)");
            
            completoCiclo = true;
            
        } catch (InterruptedException e) {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ Automóvil-" + String.format("%03d", automovilId) + 
                             " interrumpido durante su ciclo");
            Thread.currentThread().interrupt();
        } finally {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Automóvil-" + String.format("%03d", automovilId) + 
                             " finalizó su ejecución");
        }
    }
    
    /**
     * Detiene el automóvil de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el automóvil está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Verifica si el automóvil completó su ciclo
     * @return true si completó el ciclo, false en caso contrario
     */
    public boolean isCompletoCiclo() {
        return completoCiclo;
    }
    
    /**
     * Obtiene el ID del automóvil
     * @return ID del automóvil
     */
    public int getAutomovilId() {
        return automovilId;
    }
    
    /**
     * Obtiene la entrada utilizada
     * @return Número de entrada (1 o 2)
     */
    public int getEntradaUsada() {
        return entradaUsada;
    }
    
    /**
     * Obtiene la salida utilizada
     * @return Número de salida (1 o 2)
     */
    public int getSalidaUsada() {
        return salidaUsada;
    }
    
    /**
     * Obtiene el tiempo total en el sistema
     * @return Tiempo en milisegundos desde llegada hasta salida
     */
    public long getTiempoTotalEnSistema() {
        if (tiempoSalida > 0 && tiempoLlegada > 0) {
            return tiempoSalida - tiempoLlegada;
        }
        return -1; // No completó el ciclo
    }
    
    /**
     * Obtiene el tiempo de espera para entrar
     * @return Tiempo en milisegundos desde llegada hasta entrada
     */
    public long getTiempoEsperaEntrada() {
        if (tiempoEntrada > 0 && tiempoLlegada > 0) {
            return tiempoEntrada - tiempoLlegada;
        }
        return -1; // No entró aún
    }
    
    /**
     * Obtiene el tiempo de permanencia en el estacionamiento
     * @return Tiempo en milisegundos desde entrada hasta salida
     */
    public long getTiempoPermanencia() {
        if (tiempoSalida > 0 && tiempoEntrada > 0) {
            return tiempoSalida - tiempoEntrada;
        }
        return -1; // No salió aún
    }
    
    /**
     * Obtiene el estado actual del automóvil
     * @return Estado como String
     */
    public String getEstadoActual() {
        if (completoCiclo) {
            return "COMPLETADO";
        } else if (tiempoSalida > 0) {
            return "SALIENDO";
        } else if (tiempoEntrada > 0) {
            return "ESTACIONADO";
        } else if (tiempoLlegada > 0) {
            return "ESPERANDO_ENTRADA";
        } else {
            return "NO_INICIADO";
        }
    }
    
    /**
     * Crea y inicia un hilo para este automóvil
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Auto-" + String.format("%03d", automovilId));
        hilo.start();
        return hilo;
    }
    
    /**
     * Crea un hilo para este automóvil sin iniciarlo
     * @return El hilo creado (sin iniciar)
     */
    public Thread crearHilo() {
        return new Thread(this, "Auto-" + String.format("%03d", automovilId));
    }
    
    /**
     * Obtiene estadísticas detalladas del automóvil
     * @return String con información completa del automóvil
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append("Auto-").append(String.format("%03d", automovilId)).append(": ");
        
        if (completoCiclo) {
            stats.append("COMPLETADO");
            stats.append(" | E").append(entradaUsada).append("→S").append(salidaUsada);
            stats.append(" | Total: ").append(getTiempoTotalEnSistema()).append("ms");
            stats.append(" | Espera: ").append(getTiempoEsperaEntrada()).append("ms");
            stats.append(" | Permanencia: ").append(getTiempoPermanencia()).append("ms");
        } else {
            stats.append(getEstadoActual());
            if (tiempoLlegada > 0) {
                stats.append(" | Entrada objetivo: ").append(entradaUsada);
                stats.append(" | Salida objetivo: ").append(salidaUsada);
            }
        }
        
        return stats.toString();
    }
    
    /**
     * Obtiene información resumida del automóvil
     * @return String con información básica del automóvil
     */
    public String getInfo() {
        String estado = completoCiclo ? "Completado" : 
                       activo ? getEstadoActual() : "Detenido";
        return String.format("Auto-%03d[%s, E%d→S%d]", 
                           automovilId, estado, entradaUsada, salidaUsada);
    }
}
