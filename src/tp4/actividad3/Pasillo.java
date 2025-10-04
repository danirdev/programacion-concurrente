package tp4.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Pasillo que representa el recurso crítico compartido del zoológico.
 * Solo permite el paso de una persona a la vez, tanto para entrada como para salida.
 * Tiempo fijo de uso: 50ms.
 */
public class Pasillo {
    private boolean ocupado;
    private int totalEntradas;
    private int totalSalidas;
    private int visitantesEnZoo;
    private final int TIEMPO_PASILLO = 50; // 50ms fijo
    private final DateTimeFormatter timeFormatter;
    private String direccionActual; // "ENTRADA" o "SALIDA"
    private int visitanteUsandoPasillo;
    
    /**
     * Constructor del Pasillo
     */
    public Pasillo() {
        this.ocupado = false;
        this.totalEntradas = 0;
        this.totalSalidas = 0;
        this.visitantesEnZoo = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.direccionActual = "";
        this.visitanteUsandoPasillo = -1;
    }
    
    /**
     * Método sincronizado para que un visitante entre al zoológico
     * @param visitanteId ID del visitante
     */
    public synchronized void entrarAlZoo(int visitanteId) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Esperar hasta que el pasillo esté libre
        while (ocupado) {
            try {
                System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                                 " esperando pasillo (ocupado por " + direccionActual + 
                                 " - Visitante-" + visitanteUsandoPasillo + ")");
                wait();
                tiempo = LocalTime.now().format(timeFormatter); // Actualizar tiempo después de esperar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        // Tomar control del pasillo
        ocupado = true;
        direccionActual = "ENTRADA";
        visitanteUsandoPasillo = visitanteId;
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                         " entrando por el pasillo (" + TIEMPO_PASILLO + "ms)");
        
        try {
            // Simular tiempo de paso por el pasillo
            Thread.sleep(TIEMPO_PASILLO);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Completar entrada
        totalEntradas++;
        visitantesEnZoo++;
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                         " entró al zoológico (Visitantes en zoo: " + visitantesEnZoo + ")");
        
        // Liberar el pasillo
        ocupado = false;
        direccionActual = "";
        visitanteUsandoPasillo = -1;
        notifyAll(); // Notificar a otros visitantes esperando
    }
    
    /**
     * Método sincronizado para que un visitante salga del zoológico
     * @param visitanteId ID del visitante
     */
    public synchronized void salirDelZoo(int visitanteId) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Esperar hasta que el pasillo esté libre
        while (ocupado) {
            try {
                System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                                 " esperando pasillo para salir (ocupado por " + direccionActual + 
                                 " - Visitante-" + visitanteUsandoPasillo + ")");
                wait();
                tiempo = LocalTime.now().format(timeFormatter); // Actualizar tiempo después de esperar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        // Tomar control del pasillo
        ocupado = true;
        direccionActual = "SALIDA";
        visitanteUsandoPasillo = visitanteId;
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                         " saliendo por el pasillo (" + TIEMPO_PASILLO + "ms)");
        
        try {
            // Simular tiempo de paso por el pasillo
            Thread.sleep(TIEMPO_PASILLO);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Completar salida
        totalSalidas++;
        visitantesEnZoo--;
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Visitante-" + visitanteId + 
                         " salió del zoológico (Visitantes en zoo: " + visitantesEnZoo + ")");
        
        // Liberar el pasillo
        ocupado = false;
        direccionActual = "";
        visitanteUsandoPasillo = -1;
        notifyAll(); // Notificar a otros visitantes esperando
    }
    
    /**
     * Verifica si el pasillo está ocupado
     * @return true si está ocupado, false si está libre
     */
    public synchronized boolean estaOcupado() {
        return ocupado;
    }
    
    /**
     * Obtiene el número total de entradas
     * @return Total de entradas realizadas
     */
    public synchronized int getTotalEntradas() {
        return totalEntradas;
    }
    
    /**
     * Obtiene el número total de salidas
     * @return Total de salidas realizadas
     */
    public synchronized int getTotalSalidas() {
        return totalSalidas;
    }
    
    /**
     * Obtiene el número de visitantes actualmente en el zoo
     * @return Visitantes en el zoo
     */
    public synchronized int getVisitantesEnZoo() {
        return visitantesEnZoo;
    }
    
    /**
     * Obtiene la dirección actual del pasillo
     * @return "ENTRADA", "SALIDA" o "" si está libre
     */
    public synchronized String getDireccionActual() {
        return direccionActual;
    }
    
    /**
     * Muestra el estado actual del pasillo
     */
    public synchronized void mostrarEstado() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] === ESTADO DEL PASILLO ===");
        
        if (ocupado) {
            System.out.println("Estado: OCUPADO (" + direccionActual + ")");
            System.out.println("Visitante usando: " + visitanteUsandoPasillo);
        } else {
            System.out.println("Estado: LIBRE");
        }
        
        System.out.println("Total entradas: " + totalEntradas);
        System.out.println("Total salidas: " + totalSalidas);
        System.out.println("Visitantes en zoo: " + visitantesEnZoo);
        System.out.println("Diferencia (entradas - salidas): " + (totalEntradas - totalSalidas));
        
        if (visitantesEnZoo == 0) {
            System.out.println("¡ZOO VACÍO!");
        } else if (visitantesEnZoo > 20) {
            System.out.println("¡ZOO MUY LLENO! (" + visitantesEnZoo + " visitantes)");
        }
        
        System.out.println("===========================");
    }
    
    /**
     * Obtiene estadísticas de uso del pasillo
     * @return Array con [totalUsos, porcentajeEntradas, porcentajeSalidas]
     */
    public synchronized double[] getEstadisticasUso() {
        int totalUsos = totalEntradas + totalSalidas;
        double porcentajeEntradas = totalUsos > 0 ? (double) totalEntradas / totalUsos * 100 : 0;
        double porcentajeSalidas = totalUsos > 0 ? (double) totalSalidas / totalUsos * 100 : 0;
        
        return new double[]{totalUsos, porcentajeEntradas, porcentajeSalidas};
    }
}
