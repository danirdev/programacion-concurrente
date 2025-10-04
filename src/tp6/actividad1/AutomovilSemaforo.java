package tp6.actividad1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase AutomovilSemaforo que extiende Thread.
 * Representa un automóvil que utiliza semáforos para acceder al estacionamiento.
 * Cada automóvil tiene un ciclo de vida completo: llegada, entrada, permanencia y salida.
 */
public class AutomovilSemaforo extends Thread {
    private final EstacionamientoSemaforos estacionamiento;
    private final int automovilId;
    private final Random random;
    private final DateTimeFormatter timeFormatter;
    
    // Configuración del automóvil
    private final int entradaPreferida;
    private final int salidaPreferida;
    private final int tiempoMinimoPermanencia = 2000;  // 2 segundos mínimo
    private final int tiempoMaximoPermanencia = 8000;  // 8 segundos máximo
    
    // Estado del automóvil
    private boolean completoCiclo;
    private long tiempoLlegada;
    private long tiempoEntrada;
    private long tiempoSalida;
    private int tiempoPermanencia;
    private String estadoActual;
    
    /**
     * Constructor del AutomovilSemaforo
     * 
     * @param estacionamiento Estacionamiento con semáforos
     * @param automovilId Identificador único del automóvil
     */
    public AutomovilSemaforo(EstacionamientoSemaforos estacionamiento, int automovilId) {
        super("Auto-" + String.format("%03d", automovilId));
        this.estacionamiento = estacionamiento;
        this.automovilId = automovilId;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        
        // Seleccionar entrada y salida aleatoriamente
        this.entradaPreferida = random.nextInt(2) + 1; // 1 o 2
        this.salidaPreferida = random.nextInt(2) + 1;  // 1 o 2
        
        // Calcular tiempo de permanencia aleatorio
        this.tiempoPermanencia = tiempoMinimoPermanencia + 
                               random.nextInt(tiempoMaximoPermanencia - tiempoMinimoPermanencia + 1);
        
        // Estado inicial
        this.completoCiclo = false;
        this.estadoActual = "CREADO";
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        try {
            // 1. LLEGADA AL ESTACIONAMIENTO
            tiempoLlegada = System.currentTimeMillis();
            estadoActual = "LLEGANDO";
            
            System.out.println("[" + tiempo + "] 🚗 " + getName() + 
                             " llegó al estacionamiento (Entrada preferida: " + entradaPreferida + 
                             ", Salida preferida: " + salidaPreferida + 
                             ", Permanencia: " + tiempoPermanencia + "ms)");
            
            // 2. INTENTAR ENTRAR AL ESTACIONAMIENTO
            estadoActual = "ESPERANDO_ENTRADA";
            estacionamiento.entrar(automovilId, entradaPreferida);
            tiempoEntrada = System.currentTimeMillis();
            
            // 3. PERMANECER EN EL ESTACIONAMIENTO
            estadoActual = "ESTACIONADO";
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🅿️ " + getName() + 
                             " estacionado, permanecerá " + tiempoPermanencia + "ms");
            
            Thread.sleep(tiempoPermanencia);
            
            // 4. SALIR DEL ESTACIONAMIENTO
            estadoActual = "ESPERANDO_SALIDA";
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] " + getName() + 
                             " terminó permanencia, dirigiéndose a salida");
            
            estacionamiento.salir(automovilId, salidaPreferida);
            tiempoSalida = System.currentTimeMillis();
            
            // 5. COMPLETAR CICLO
            estadoActual = "COMPLETADO";
            completoCiclo = true;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ " + getName() + 
                             " completó su ciclo (E" + entradaPreferida + "→S" + salidaPreferida + 
                             ", Tiempo total: " + getTiempoTotalEnSistema() + "ms)");
            
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
     * Método alternativo que usa timeout para evitar esperas indefinidas
     * 
     * @param timeoutSegundos Timeout en segundos para entrada
     */
    public void ejecutarConTimeout(int timeoutSegundos) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        try {
            // Llegada
            tiempoLlegada = System.currentTimeMillis();
            estadoActual = "LLEGANDO";
            
            System.out.println("[" + tiempo + "] 🚗 " + getName() + 
                             " llegó (con timeout " + timeoutSegundos + "s)");
            
            // Intentar entrar con timeout
            estadoActual = "ESPERANDO_ENTRADA";
            if (!estacionamiento.intentarEntrar(automovilId, entradaPreferida, timeoutSegundos)) {
                estadoActual = "ABANDONADO";
                System.out.println("[" + tiempo + "] 🚫 " + getName() + " abandonó por timeout");
                return;
            }
            
            tiempoEntrada = System.currentTimeMillis();
            
            // Continuar con ciclo normal
            estadoActual = "ESTACIONADO";
            Thread.sleep(tiempoPermanencia);
            
            estadoActual = "ESPERANDO_SALIDA";
            estacionamiento.salir(automovilId, salidaPreferida);
            tiempoSalida = System.currentTimeMillis();
            
            estadoActual = "COMPLETADO";
            completoCiclo = true;
            
        } catch (InterruptedException e) {
            estadoActual = "INTERRUMPIDO";
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el tiempo total que el automóvil estuvo en el sistema
     * 
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
     * 
     * @return Tiempo en milisegundos desde llegada hasta entrada
     */
    public long getTiempoEsperaEntrada() {
        if (tiempoEntrada > 0 && tiempoLlegada > 0) {
            return tiempoEntrada - tiempoLlegada;
        }
        return -1; // No entró aún
    }
    
    /**
     * Obtiene el tiempo real de permanencia en el estacionamiento
     * 
     * @return Tiempo en milisegundos desde entrada hasta salida
     */
    public long getTiempoRealPermanencia() {
        if (tiempoSalida > 0 && tiempoEntrada > 0) {
            return tiempoSalida - tiempoEntrada;
        }
        return -1; // No salió aún
    }
    
    /**
     * Verifica si el automóvil completó su ciclo
     * 
     * @return true si completó el ciclo, false en caso contrario
     */
    public boolean isCompletoCiclo() {
        return completoCiclo;
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
     * Obtiene el ID del automóvil
     * 
     * @return ID del automóvil
     */
    public int getAutomovilId() {
        return automovilId;
    }
    
    /**
     * Obtiene la entrada preferida
     * 
     * @return Número de entrada (1 o 2)
     */
    public int getEntradaPreferida() {
        return entradaPreferida;
    }
    
    /**
     * Obtiene la salida preferida
     * 
     * @return Número de salida (1 o 2)
     */
    public int getSalidaPreferida() {
        return salidaPreferida;
    }
    
    /**
     * Obtiene el tiempo planificado de permanencia
     * 
     * @return Tiempo de permanencia en milisegundos
     */
    public int getTiempoPermanenciaPlanificado() {
        return tiempoPermanencia;
    }
    
    /**
     * Obtiene estadísticas detalladas del automóvil
     * 
     * @return String con estadísticas completas
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append(getName()).append(": ");
        
        if (completoCiclo) {
            stats.append("COMPLETADO");
            stats.append(" | E").append(entradaPreferida).append("→S").append(salidaPreferida);
            stats.append(" | Total: ").append(getTiempoTotalEnSistema()).append("ms");
            stats.append(" | Espera: ").append(getTiempoEsperaEntrada()).append("ms");
            stats.append(" | Permanencia real: ").append(getTiempoRealPermanencia()).append("ms");
            stats.append(" | Permanencia planificada: ").append(tiempoPermanencia).append("ms");
        } else {
            stats.append(estadoActual);
            if (tiempoLlegada > 0) {
                stats.append(" | E").append(entradaPreferida).append("→S").append(salidaPreferida);
                stats.append(" | Permanencia planificada: ").append(tiempoPermanencia).append("ms");
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
        String estado = completoCiclo ? "Completado" : estadoActual;
        return String.format("%s[%s, E%d→S%d, %dms]", 
                           getName(), estado, entradaPreferida, salidaPreferida, tiempoPermanencia);
    }
    
    /**
     * Calcula la eficiencia del automóvil (tiempo útil vs tiempo total)
     * 
     * @return Porcentaje de eficiencia (0-100)
     */
    public double calcularEficiencia() {
        if (!completoCiclo) {
            return 0.0;
        }
        
        long tiempoTotal = getTiempoTotalEnSistema();
        long tiempoUtil = getTiempoRealPermanencia();
        
        if (tiempoTotal <= 0) {
            return 0.0;
        }
        
        return (tiempoUtil * 100.0) / tiempoTotal;
    }
    
    /**
     * Verifica si el automóvil tuvo que esperar para entrar
     * 
     * @return true si esperó, false si entró inmediatamente
     */
    public boolean tuvoQueEsperar() {
        return getTiempoEsperaEntrada() > 500; // Más de 500ms se considera espera
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
     * Método toString para representación del automóvil
     * 
     * @return Representación string del automóvil
     */
    @Override
    public String toString() {
        return String.format("AutomovilSemaforo[ID:%d, Estado:%s, E%d→S%d, Completado:%s]", 
                           automovilId, estadoActual, entradaPreferida, salidaPreferida, completoCiclo);
    }
}
