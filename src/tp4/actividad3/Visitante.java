package tp4.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase Visitante que hereda de Thread.
 * Representa un visitante del zoológico que pasa por todo el ciclo:
 * llegada → fila entrada → pasillo → visita zoo → fila salida → pasillo → salida
 */
public class Visitante extends Thread {
    private Pasillo pasillo;
    private int visitanteId;
    private Random random;
    private final int TIEMPO_MIN_VISITA = 400;  // 400ms mínimo en el zoo
    private final int TIEMPO_MAX_VISITA = 700;  // 700ms máximo en el zoo
    private final DateTimeFormatter timeFormatter;
    private boolean completoVisita;
    private long tiempoLlegada;
    private long tiempoEntrada;
    private long tiempoSalida;
    
    /**
     * Constructor del Visitante
     * @param pasillo Pasillo compartido del zoológico
     * @param visitanteId Identificador único del visitante
     */
    public Visitante(Pasillo pasillo, int visitanteId) {
        this.pasillo = pasillo;
        this.visitanteId = visitanteId;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.completoVisita = false;
        this.setName("Visitante-" + visitanteId);
    }
    
    @Override
    public void run() {
        try {
            // 1. LLEGADA AL ZOOLÓGICO
            tiempoLlegada = System.currentTimeMillis();
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + " llegó al zoológico");
            
            // 2. HACER FILA PARA ENTRAR
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + " hace fila para entrar");
            
            // 3. ENTRAR POR EL PASILLO
            pasillo.entrarAlZoo(visitanteId);
            tiempoEntrada = System.currentTimeMillis();
            
            // 4. VISITAR EL ZOOLÓGICO
            int tiempoVisita = TIEMPO_MIN_VISITA + random.nextInt(TIEMPO_MAX_VISITA - TIEMPO_MIN_VISITA + 1);
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                             " visitando el zoo (" + tiempoVisita + "ms)");
            
            Thread.sleep(tiempoVisita);
            
            // 5. TERMINAR VISITA
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                             " terminó la visita (" + tiempoVisita + "ms)");
            
            // 6. HACER FILA PARA SALIR
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + " hace fila para salir");
            
            // 7. SALIR POR EL PASILLO
            pasillo.salirDelZoo(visitanteId);
            tiempoSalida = System.currentTimeMillis();
            
            // 8. COMPLETAR SALIDA
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                             " completó su visita al zoológico");
            
            completoVisita = true;
            
        } catch (InterruptedException e) {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                             " interrumpido durante la visita");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Verifica si el visitante completó su visita
     * @return true si completó la visita, false en caso contrario
     */
    public boolean isCompletoVisita() {
        return completoVisita;
    }
    
    /**
     * Obtiene el ID del visitante
     * @return ID del visitante
     */
    public int getVisitanteId() {
        return visitanteId;
    }
    
    /**
     * Obtiene el tiempo total de permanencia en el sistema
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
     * Obtiene estadísticas detalladas del visitante
     * @return String con información completa del visitante
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append("Visitante-").append(visitanteId).append(":");
        
        if (completoVisita) {
            stats.append(" COMPLETÓ VISITA");
            stats.append(" | Tiempo total: ").append(getTiempoTotalEnSistema()).append("ms");
            stats.append(" | Espera entrada: ").append(getTiempoEsperaEntrada()).append("ms");
        } else if (tiempoEntrada > 0) {
            stats.append(" EN EL ZOO");
        } else if (tiempoLlegada > 0) {
            stats.append(" ESPERANDO ENTRADA");
        } else {
            stats.append(" NO INICIADO");
        }
        
        return stats.toString();
    }
    
    /**
     * Obtiene el estado actual del visitante
     * @return Estado como String
     */
    public String getEstadoActual() {
        if (completoVisita) {
            return "COMPLETADO";
        } else if (tiempoSalida > 0) {
            return "SALIENDO";
        } else if (tiempoEntrada > 0) {
            return "EN_ZOO";
        } else if (tiempoLlegada > 0) {
            return "ESPERANDO_ENTRADA";
        } else {
            return "NO_INICIADO";
        }
    }
}
