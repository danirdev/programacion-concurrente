package tp5.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase GeneradorAutomoviles que implementa Runnable.
 * Se encarga de generar los 100 automóviles que llegarán al estacionamiento
 * de forma continua con intervalos aleatorios.
 */
public class GeneradorAutomoviles implements Runnable {
    private final Estacionamiento estacionamiento;
    private final Random random;
    private int contadorAutomoviles;
    private final int TOTAL_AUTOMOVILES = 100;
    private final int TIEMPO_MIN_LLEGADA = 300;   // 300ms mínimo entre llegadas
    private final int TIEMPO_MAX_LLEGADA = 1000;  // 1000ms máximo entre llegadas
    private final DateTimeFormatter timeFormatter;
    private final List<Automovil> automovilesGenerados;
    private final List<Thread> hilosAutomoviles;
    private volatile boolean activo = true;
    
    /**
     * Constructor del GeneradorAutomoviles
     * @param estacionamiento Estacionamiento compartido
     */
    public GeneradorAutomoviles(Estacionamiento estacionamiento) {
        this.estacionamiento = estacionamiento;
        this.random = new Random();
        this.contadorAutomoviles = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.automovilesGenerados = new ArrayList<>();
        this.hilosAutomoviles = new ArrayList<>();
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🏭 Generador de Automóviles iniciado en hilo: " + nombreHilo);
        System.out.println("[" + tiempo + "] Objetivo: Generar " + TOTAL_AUTOMOVILES + 
                          " automóviles (Intervalo: " + TIEMPO_MIN_LLEGADA + "-" + TIEMPO_MAX_LLEGADA + "ms)");
        
        try {
            while (activo && contadorAutomoviles < TOTAL_AUTOMOVILES && 
                   !Thread.currentThread().isInterrupted()) {
                
                // Calcular tiempo hasta la llegada del próximo automóvil
                int tiempoLlegada = TIEMPO_MIN_LLEGADA + 
                                  random.nextInt(TIEMPO_MAX_LLEGADA - TIEMPO_MIN_LLEGADA + 1);
                
                // Esperar hasta que llegue el próximo automóvil
                Thread.sleep(tiempoLlegada);
                
                if (activo && contadorAutomoviles < TOTAL_AUTOMOVILES) {
                    // Generar nuevo automóvil
                    contadorAutomoviles++;
                    Automovil nuevoAutomovil = new Automovil(estacionamiento, contadorAutomoviles);
                    automovilesGenerados.add(nuevoAutomovil);
                    
                    // Crear e iniciar el hilo del automóvil usando Runnable
                    Thread hiloAutomovil = new Thread(nuevoAutomovil, 
                                                    "Hilo-Auto-" + String.format("%03d", contadorAutomoviles));
                    hilosAutomoviles.add(hiloAutomovil);
                    hiloAutomovil.start();
                    
                    tiempo = LocalTime.now().format(timeFormatter);
                    System.out.println("[" + tiempo + "] 🚗 NUEVO AUTOMÓVIL: Auto-" + 
                                     String.format("%03d", contadorAutomoviles) + 
                                     " generado (" + contadorAutomoviles + "/" + TOTAL_AUTOMOVILES + 
                                     ") en hilo " + hiloAutomovil.getName());
                    
                    // Mostrar progreso cada 10 automóviles
                    if (contadorAutomoviles % 10 == 0) {
                        tiempo = LocalTime.now().format(timeFormatter);
                        System.out.println("[" + tiempo + "] 📊 Progreso: " + contadorAutomoviles + 
                                         "/" + TOTAL_AUTOMOVILES + " automóviles generados (" + 
                                         String.format("%.1f%%", (double)contadorAutomoviles / TOTAL_AUTOMOVILES * 100) + ")");
                    }
                }
            }
            
            // Generación completada
            if (contadorAutomoviles >= TOTAL_AUTOMOVILES) {
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] ✅ GENERACIÓN COMPLETADA: " + 
                                 TOTAL_AUTOMOVILES + " automóviles generados");
            }
            
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ Generador de Automóviles interrumpido. " +
                             "Generados: " + contadorAutomoviles + "/" + TOTAL_AUTOMOVILES);
            Thread.currentThread().interrupt();
        } finally {
            detenerTodosLosAutomoviles();
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador de Automóviles finalizó su ejecución");
        }
    }
    
    /**
     * Detiene el generador de forma controlada
     */
    public void detener() {
        activo = false;
    }
    
    /**
     * Verifica si el generador está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean estaActivo() {
        return activo;
    }
    
    /**
     * Verifica si completó la generación de todos los automóviles
     * @return true si generó todos los automóviles, false en caso contrario
     */
    public boolean completoGeneracion() {
        return contadorAutomoviles >= TOTAL_AUTOMOVILES;
    }
    
    /**
     * Detiene todos los automóviles generados
     */
    private void detenerTodosLosAutomoviles() {
        System.out.println("🛑 Deteniendo todos los automóviles generados...");
        for (Automovil automovil : automovilesGenerados) {
            automovil.detener();
        }
        
        // Interrumpir hilos de automóviles que aún estén activos
        for (Thread hilo : hilosAutomoviles) {
            if (hilo.isAlive()) {
                hilo.interrupt();
            }
        }
    }
    
    /**
     * Obtiene el número total de automóviles generados
     * @return Número de automóviles generados
     */
    public int getAutomovilesGenerados() {
        return contadorAutomoviles;
    }
    
    /**
     * Obtiene el objetivo total de automóviles
     * @return Total de automóviles objetivo
     */
    public int getTotalAutomoviles() {
        return TOTAL_AUTOMOVILES;
    }
    
    /**
     * Obtiene la lista de todos los automóviles generados
     * @return Lista de automóviles
     */
    public List<Automovil> getListaAutomoviles() {
        return new ArrayList<>(automovilesGenerados);
    }
    
    /**
     * Obtiene la lista de hilos de automóviles
     * @return Lista de hilos
     */
    public List<Thread> getHilosAutomoviles() {
        return new ArrayList<>(hilosAutomoviles);
    }
    
    /**
     * Espera a que todos los automóviles terminen sus procesos
     */
    public void esperarAutomoviles() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] ⏳ Esperando que terminen todos los automóviles...");
        
        int automovilesEsperando = 0;
        for (Thread hilo : hilosAutomoviles) {
            try {
                if (hilo.isAlive()) {
                    automovilesEsperando++;
                    hilo.join(5000); // Esperar máximo 5 segundos por automóvil
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
     * Obtiene estadísticas de los automóviles
     * @return Array con [completaron, enProceso, noIniciados, detenidos]
     */
    public int[] getEstadisticasAutomoviles() {
        int completaron = 0;
        int enProceso = 0;
        int noIniciados = 0;
        int detenidos = 0;
        
        for (Automovil automovil : automovilesGenerados) {
            String estado = automovil.getEstadoActual();
            switch (estado) {
                case "COMPLETADO":
                    completaron++;
                    break;
                case "ESTACIONADO":
                case "ESPERANDO_ENTRADA":
                case "SALIENDO":
                    if (automovil.estaActivo()) {
                        enProceso++;
                    } else {
                        detenidos++;
                    }
                    break;
                case "NO_INICIADO":
                    noIniciados++;
                    break;
                default:
                    detenidos++;
                    break;
            }
        }
        
        return new int[]{completaron, enProceso, noIniciados, detenidos};
    }
    
    /**
     * Obtiene estadísticas de uso de entradas y salidas
     * @return Array con [entrada1, entrada2, salida1, salida2]
     */
    public int[] getEstadisticasAccesos() {
        int entrada1 = 0, entrada2 = 0, salida1 = 0, salida2 = 0;
        
        for (Automovil automovil : automovilesGenerados) {
            if (automovil.getEntradaUsada() == 1) entrada1++;
            else entrada2++;
            
            if (automovil.getSalidaUsada() == 1) salida1++;
            else salida2++;
        }
        
        return new int[]{entrada1, entrada2, salida1, salida2};
    }
    
    /**
     * Obtiene estadísticas de tiempos de los automóviles que completaron su ciclo
     * @return Array con [tiempoPromedioTotal, tiempoPromedioEspera, tiempoPromedioPermanencia]
     */
    public double[] getEstadisticasTiempos() {
        List<Long> tiemposTotales = new ArrayList<>();
        List<Long> tiemposEspera = new ArrayList<>();
        List<Long> tiemposPermanencia = new ArrayList<>();
        
        for (Automovil automovil : automovilesGenerados) {
            if (automovil.isCompletoCiclo()) {
                long tiempoTotal = automovil.getTiempoTotalEnSistema();
                long tiempoEspera = automovil.getTiempoEsperaEntrada();
                long tiempoPermanencia = automovil.getTiempoPermanencia();
                
                if (tiempoTotal > 0) tiemposTotales.add(tiempoTotal);
                if (tiempoEspera > 0) tiemposEspera.add(tiempoEspera);
                if (tiempoPermanencia > 0) tiemposPermanencia.add(tiempoPermanencia);
            }
        }
        
        double promedioTotal = tiemposTotales.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioEspera = tiemposEspera.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double promedioPermanencia = tiemposPermanencia.stream().mapToLong(Long::longValue).average().orElse(0.0);
        
        return new double[]{promedioTotal, promedioEspera, promedioPermanencia};
    }
    
    /**
     * Crea y inicia un hilo para este generador
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Generador-Automoviles");
        hilo.start();
        return hilo;
    }
    
    /**
     * Obtiene información del estado del generador
     * @return String con información del generador
     */
    public String getInfo() {
        return String.format("GeneradorAutomoviles[%d/%d generados, Activo:%s, HilosVivos:%d]", 
                           contadorAutomoviles, TOTAL_AUTOMOVILES, activo ? "Sí" : "No", 
                           (int) hilosAutomoviles.stream().filter(Thread::isAlive).count());
    }
}
