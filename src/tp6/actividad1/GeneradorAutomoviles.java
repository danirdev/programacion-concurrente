package tp6.actividad1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase GeneradorAutomoviles que se encarga de generar y gestionar
 * los 100 automóviles que utilizarán el estacionamiento con semáforos.
 * Extiende Thread para generar automóviles de forma continua.
 */
public class GeneradorAutomoviles extends Thread {
    private final EstacionamientoSemaforos estacionamiento;
    private final Random random;
    private final DateTimeFormatter timeFormatter;
    
    // Configuración de generación
    private final int TOTAL_AUTOMOVILES = 100;
    private final int TIEMPO_MIN_LLEGADA = 200;   // 200ms mínimo entre llegadas
    private final int TIEMPO_MAX_LLEGADA = 800;   // 800ms máximo entre llegadas
    
    // Estado del generador
    private int automovilesGenerados;
    private final List<AutomovilSemaforo> automoviles;
    private volatile boolean activo = true;
    private boolean generacionCompleta = false;
    
    /**
     * Constructor del GeneradorAutomoviles
     * 
     * @param estacionamiento Estacionamiento con semáforos
     */
    public GeneradorAutomoviles(EstacionamientoSemaforos estacionamiento) {
        super("Generador-Automoviles");
        this.estacionamiento = estacionamiento;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.automovilesGenerados = 0;
        this.automoviles = new ArrayList<>();
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🏭 " + getName() + " iniciado");
        System.out.println("   Objetivo: Generar " + TOTAL_AUTOMOVILES + " automóviles");
        System.out.println("   Intervalo: " + TIEMPO_MIN_LLEGADA + "-" + TIEMPO_MAX_LLEGADA + "ms entre llegadas");
        
        try {
            while (activo && automovilesGenerados < TOTAL_AUTOMOVILES) {
                // Calcular tiempo hasta la llegada del próximo automóvil
                int tiempoLlegada = TIEMPO_MIN_LLEGADA + 
                                  random.nextInt(TIEMPO_MAX_LLEGADA - TIEMPO_MIN_LLEGADA + 1);
                
                // Esperar hasta que llegue el próximo automóvil
                Thread.sleep(tiempoLlegada);
                
                if (activo && automovilesGenerados < TOTAL_AUTOMOVILES) {
                    // Generar nuevo automóvil
                    automovilesGenerados++;
                    AutomovilSemaforo nuevoAutomovil = new AutomovilSemaforo(estacionamiento, automovilesGenerados);
                    automoviles.add(nuevoAutomovil);
                    
                    // Iniciar el hilo del automóvil
                    nuevoAutomovil.start();
                    
                    tiempo = LocalTime.now().format(timeFormatter);
                    System.out.println("[" + tiempo + "] 🚗 GENERADO: " + nuevoAutomovil.getName() + 
                                     " (" + automovilesGenerados + "/" + TOTAL_AUTOMOVILES + 
                                     ") - E" + nuevoAutomovil.getEntradaPreferida() + 
                                     "→S" + nuevoAutomovil.getSalidaPreferida());
                    
                    // Mostrar progreso cada 10 automóviles
                    if (automovilesGenerados % 10 == 0) {
                        double progreso = (automovilesGenerados * 100.0) / TOTAL_AUTOMOVILES;
                        System.out.println("[" + tiempo + "] 📊 Progreso: " + automovilesGenerados + 
                                         "/" + TOTAL_AUTOMOVILES + " (" + 
                                         String.format("%.1f%%", progreso) + ")");
                    }
                }
            }
            
            // Generación completada
            generacionCompleta = true;
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ GENERACIÓN COMPLETADA: " + 
                             TOTAL_AUTOMOVILES + " automóviles generados");
            
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ Generador interrumpido. Generados: " + 
                             automovilesGenerados + "/" + TOTAL_AUTOMOVILES);
            Thread.currentThread().interrupt();
        } finally {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador finalizó su ejecución");
        }
    }
    
    /**
     * Detiene el generador de forma controlada
     */
    public void detener() {
        activo = false;
        this.interrupt();
    }
    
    /**
     * Espera a que todos los automóviles terminen sus procesos
     */
    public void esperarAutomoviles() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] ⏳ Esperando que terminen todos los automóviles...");
        
        int automovilesEsperando = 0;
        for (AutomovilSemaforo automovil : automoviles) {
            try {
                if (automovil.isAlive()) {
                    automovilesEsperando++;
                    automovil.join(10000); // Esperar máximo 10 segundos por automóvil
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] ✅ Finalización de automóviles completada " +
                         "(se esperaron " + automovilesEsperando + " hilos)");
    }
    
    /**
     * Obtiene estadísticas de los automóviles generados
     * 
     * @return Array con [completaron, enProceso, abandonaron, interrumpidos]
     */
    public int[] getEstadisticasAutomoviles() {
        int completaron = 0;
        int enProceso = 0;
        int abandonaron = 0;
        int interrumpidos = 0;
        
        for (AutomovilSemaforo automovil : automoviles) {
            String estado = automovil.getEstadoActual();
            switch (estado) {
                case "COMPLETADO":
                    completaron++;
                    break;
                case "ESTACIONADO":
                case "ESPERANDO_ENTRADA":
                case "ESPERANDO_SALIDA":
                case "LLEGANDO":
                    enProceso++;
                    break;
                case "ABANDONADO":
                    abandonaron++;
                    break;
                case "INTERRUMPIDO":
                    interrumpidos++;
                    break;
            }
        }
        
        return new int[]{completaron, enProceso, abandonaron, interrumpidos};
    }
    
    /**
     * Obtiene estadísticas de uso de entradas y salidas
     * 
     * @return Array con [entrada1, entrada2, salida1, salida2]
     */
    public int[] getEstadisticasAccesos() {
        int entrada1 = 0, entrada2 = 0, salida1 = 0, salida2 = 0;
        
        for (AutomovilSemaforo automovil : automoviles) {
            if (automovil.getEntradaPreferida() == 1) entrada1++;
            else entrada2++;
            
            if (automovil.getSalidaPreferida() == 1) salida1++;
            else salida2++;
        }
        
        return new int[]{entrada1, entrada2, salida1, salida2};
    }
    
    /**
     * Obtiene estadísticas de tiempos de los automóviles que completaron su ciclo
     * 
     * @return Array con [tiempoPromedioTotal, tiempoPromedioEspera, tiempoPromedioPermanencia, eficienciaPromedio]
     */
    public double[] getEstadisticasTiempos() {
        List<Long> tiemposTotales = new ArrayList<>();
        List<Long> tiemposEspera = new ArrayList<>();
        List<Long> tiemposPermanencia = new ArrayList<>();
        List<Double> eficiencias = new ArrayList<>();
        
        for (AutomovilSemaforo automovil : automoviles) {
            if (automovil.isCompletoCiclo()) {
                long tiempoTotal = automovil.getTiempoTotalEnSistema();
                long tiempoEspera = automovil.getTiempoEsperaEntrada();
                long tiempoPermanencia = automovil.getTiempoRealPermanencia();
                double eficiencia = automovil.calcularEficiencia();
                
                if (tiempoTotal > 0) tiemposTotales.add(tiempoTotal);
                if (tiempoEspera > 0) tiemposEspera.add(tiempoEspera);
                if (tiempoPermanencia > 0) tiemposPermanencia.add(tiempoPermanencia);
                if (eficiencia > 0) eficiencias.add(eficiencia);
            }
        }
        
        double promedioTotal = tiemposTotales.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioEspera = tiemposEspera.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioPermanencia = tiemposPermanencia.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioEficiencia = eficiencias.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        
        return new double[]{promedioTotal, promedioEspera, promedioPermanencia, promedioEficiencia};
    }
    
    /**
     * Obtiene la lista de automóviles que tuvieron que esperar
     * 
     * @return Lista de automóviles que esperaron
     */
    public List<AutomovilSemaforo> getAutomovilesQueEsperaron() {
        List<AutomovilSemaforo> esperaron = new ArrayList<>();
        
        for (AutomovilSemaforo automovil : automoviles) {
            if (automovil.tuvoQueEsperar()) {
                esperaron.add(automovil);
            }
        }
        
        return esperaron;
    }
    
    /**
     * Obtiene estadísticas de distribución de tiempos de permanencia
     * 
     * @return Array con [corta(<3s), media(3-6s), larga(>6s)]
     */
    public int[] getDistribucionPermanencia() {
        int corta = 0, media = 0, larga = 0;
        
        for (AutomovilSemaforo automovil : automoviles) {
            int tiempoPlanificado = automovil.getTiempoPermanenciaPlanificado();
            
            if (tiempoPlanificado < 3000) {
                corta++;
            } else if (tiempoPlanificado <= 6000) {
                media++;
            } else {
                larga++;
            }
        }
        
        return new int[]{corta, media, larga};
    }
    
    /**
     * Muestra un reporte detallado de todos los automóviles
     */
    public void mostrarReporteDetallado() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("              REPORTE DETALLADO DE AUTOMÓVILES");
        System.out.println("=".repeat(80));
        
        for (int i = 0; i < automoviles.size(); i++) {
            AutomovilSemaforo automovil = automoviles.get(i);
            System.out.println(String.format("%3d. %s", (i + 1), automovil.getEstadisticasDetalladas()));
        }
        
        System.out.println("=".repeat(80));
    }
    
    /**
     * Verifica si la generación está completa
     * 
     * @return true si se generaron todos los automóviles
     */
    public boolean isGeneracionCompleta() {
        return generacionCompleta;
    }
    
    /**
     * Obtiene el número de automóviles generados
     * 
     * @return Número de automóviles generados
     */
    public int getAutomovilesGenerados() {
        return automovilesGenerados;
    }
    
    /**
     * Obtiene el objetivo total de automóviles
     * 
     * @return Total de automóviles objetivo
     */
    public int getTotalAutomoviles() {
        return TOTAL_AUTOMOVILES;
    }
    
    /**
     * Obtiene la lista completa de automóviles
     * 
     * @return Lista de automóviles generados
     */
    public List<AutomovilSemaforo> getAutomoviles() {
        return new ArrayList<>(automoviles);
    }
    
    /**
     * Obtiene información del estado del generador
     * 
     * @return String con información del generador
     */
    public String getInfo() {
        int hilosVivos = (int) automoviles.stream().filter(Thread::isAlive).count();
        return String.format("GeneradorAutomoviles[%d/%d generados, %d vivos, Completo:%s]", 
                           automovilesGenerados, TOTAL_AUTOMOVILES, hilosVivos, generacionCompleta);
    }
    
    /**
     * Método toString para representación del generador
     * 
     * @return Representación string del generador
     */
    @Override
    public String toString() {
        return String.format("GeneradorAutomoviles[Generados:%d/%d, Activo:%s, Completo:%s]", 
                           automovilesGenerados, TOTAL_AUTOMOVILES, activo, generacionCompleta);
    }
}
