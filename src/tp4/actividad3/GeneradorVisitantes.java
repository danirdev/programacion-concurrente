package tp4.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase GeneradorVisitantes que hereda de Thread.
 * Se encarga de generar visitantes que llegan al zoológico de forma continua.
 * Intervalo de llegada: 100-200ms entre visitantes.
 */
public class GeneradorVisitantes extends Thread {
    private Pasillo pasillo;
    private Random random;
    private int contadorVisitantes;
    private final int TIEMPO_MIN_LLEGADA = 100;  // 100ms mínimo
    private final int TIEMPO_MAX_LLEGADA = 200;  // 200ms máximo
    private final DateTimeFormatter timeFormatter;
    private List<Visitante> visitantesGenerados;
    
    /**
     * Constructor del GeneradorVisitantes
     * @param pasillo Pasillo compartido del zoológico
     */
    public GeneradorVisitantes(Pasillo pasillo) {
        this.pasillo = pasillo;
        this.random = new Random();
        this.contadorVisitantes = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.visitantesGenerados = new ArrayList<>();
        this.setName("Generador-Visitantes");
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Generador de Visitantes iniciado (Llegadas cada " + 
                          TIEMPO_MIN_LLEGADA + "-" + TIEMPO_MAX_LLEGADA + "ms)");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Calcular tiempo hasta la llegada del próximo visitante
                int tiempoLlegada = TIEMPO_MIN_LLEGADA + random.nextInt(TIEMPO_MAX_LLEGADA - TIEMPO_MIN_LLEGADA + 1);
                
                // Esperar hasta que llegue el próximo visitante
                Thread.sleep(tiempoLlegada);
                
                // Generar nuevo visitante
                contadorVisitantes++;
                Visitante nuevoVisitante = new Visitante(pasillo, contadorVisitantes);
                visitantesGenerados.add(nuevoVisitante);
                
                // Iniciar el hilo del visitante
                nuevoVisitante.start();
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] >>> NUEVO VISITANTE: Visitante-" + contadorVisitantes + 
                                 " generado (próximo en ~" + tiempoLlegada + "ms)");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador de Visitantes detenido. Total generados: " + 
                             contadorVisitantes);
            
            // Interrumpir todos los visitantes activos
            for (Visitante visitante : visitantesGenerados) {
                if (visitante.isAlive()) {
                    visitante.interrupt();
                }
            }
            
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el número total de visitantes generados
     * @return Número de visitantes generados
     */
    public int getVisitantesGenerados() {
        return contadorVisitantes;
    }
    
    /**
     * Obtiene la lista de todos los visitantes generados
     * @return Lista de visitantes
     */
    public List<Visitante> getListaVisitantes() {
        return new ArrayList<>(visitantesGenerados);
    }
    
    /**
     * Espera a que todos los visitantes terminen sus procesos
     */
    public void esperarVisitantes() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Esperando que terminen todos los visitantes...");
        
        for (Visitante visitante : visitantesGenerados) {
            try {
                visitante.join(5000); // Esperar máximo 5 segundos por visitante
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Todos los visitantes han terminado");
    }
    
    /**
     * Obtiene estadísticas de los visitantes
     * @return Array con [visitantesCompletaron, visitantesEnZoo, visitantesEsperando, visitantesNoIniciados]
     */
    public int[] getEstadisticasVisitantes() {
        int completaron = 0;
        int enZoo = 0;
        int esperando = 0;
        int noIniciados = 0;
        
        for (Visitante visitante : visitantesGenerados) {
            String estado = visitante.getEstadoActual();
            switch (estado) {
                case "COMPLETADO":
                    completaron++;
                    break;
                case "EN_ZOO":
                case "SALIENDO":
                    enZoo++;
                    break;
                case "ESPERANDO_ENTRADA":
                    esperando++;
                    break;
                case "NO_INICIADO":
                    noIniciados++;
                    break;
            }
        }
        
        return new int[]{completaron, enZoo, esperando, noIniciados};
    }
    
    /**
     * Obtiene estadísticas de tiempos de los visitantes que completaron su visita
     * @return Array con [tiempoPromedioTotal, tiempoPromedioEspera, tiempoMaximoTotal, tiempoMaximoEspera]
     */
    public double[] getEstadisticasTiempos() {
        List<Long> tiemposTotales = new ArrayList<>();
        List<Long> tiemposEspera = new ArrayList<>();
        
        for (Visitante visitante : visitantesGenerados) {
            if (visitante.isCompletoVisita()) {
                long tiempoTotal = visitante.getTiempoTotalEnSistema();
                long tiempoEspera = visitante.getTiempoEsperaEntrada();
                
                if (tiempoTotal > 0) tiemposTotales.add(tiempoTotal);
                if (tiempoEspera > 0) tiemposEspera.add(tiempoEspera);
            }
        }
        
        double promedioTotal = tiemposTotales.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioEspera = tiemposEspera.stream().mapToLong(Long::longValue).average().orElse(0.0);
        long maximoTotal = tiemposTotales.stream().mapToLong(Long::longValue).max().orElse(0);
        long maximoEspera = tiemposEspera.stream().mapToLong(Long::longValue).max().orElse(0);
        
        return new double[]{promedioTotal, promedioEspera, maximoTotal, maximoEspera};
    }
    
    /**
     * Muestra un resumen del estado de todos los visitantes
     */
    public void mostrarResumenVisitantes() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] === RESUMEN DE VISITANTES ===");
        
        int[] estadisticas = getEstadisticasVisitantes();
        System.out.println("Completaron visita: " + estadisticas[0]);
        System.out.println("En el zoo: " + estadisticas[1]);
        System.out.println("Esperando entrada: " + estadisticas[2]);
        System.out.println("No iniciados: " + estadisticas[3]);
        System.out.println("Total generados: " + contadorVisitantes);
        
        double[] tiempos = getEstadisticasTiempos();
        if (tiempos[0] > 0) {
            System.out.println("Tiempo promedio total: " + String.format("%.1f", tiempos[0]) + "ms");
            System.out.println("Tiempo promedio espera: " + String.format("%.1f", tiempos[1]) + "ms");
            System.out.println("Tiempo máximo total: " + String.format("%.0f", tiempos[2]) + "ms");
            System.out.println("Tiempo máximo espera: " + String.format("%.0f", tiempos[3]) + "ms");
        }
        
        System.out.println("===============================");
    }
}
