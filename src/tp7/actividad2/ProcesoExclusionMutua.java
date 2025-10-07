package tp7.actividad2;

import java.util.Random;

/**
 * 🔄 ProcesoExclusionMutua - Thread que accede a recurso compartido
 * 
 * Esta clase representa un proceso que necesita acceder a un recurso compartido
 * de forma exclusiva. Implementa el patrón de exclusión mutua usando semáforos
 * y demuestra cómo evitar race conditions y deadlocks.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ProcesoExclusionMutua extends Thread {
    
    // 🏷️ Identificación del proceso
    private final String procesoId;
    
    // 🔒 Referencia al recurso compartido
    private final RecursoCompartido recurso;
    
    // ⚙️ Configuración del proceso
    private final int numeroAccesos;
    private final int tiempoMinTrabajo;
    private final int tiempoMaxTrabajo;
    private final int tiempoMinDescanso;
    private final int tiempoMaxDescanso;
    
    // 🎲 Generador de números aleatorios
    private final Random random;
    
    // 📊 Estadísticas del proceso
    private int accesosExitosos;
    private int accesosFallidos;
    private long tiempoTotalEspera;
    private long tiempoTotalTrabajo;
    private boolean activo;
    
    /**
     * 🏗️ Constructor del ProcesoExclusionMutua
     * 
     * @param procesoId Identificador único del proceso
     * @param recurso Recurso compartido al que acceder
     * @param numeroAccesos Número de veces que intentará acceder al recurso
     * @param tiempoMinTrabajo Tiempo mínimo de trabajo en región crítica (ms)
     * @param tiempoMaxTrabajo Tiempo máximo de trabajo en región crítica (ms)
     */
    public ProcesoExclusionMutua(String procesoId, RecursoCompartido recurso, 
                                int numeroAccesos, int tiempoMinTrabajo, int tiempoMaxTrabajo) {
        this.procesoId = procesoId;
        this.recurso = recurso;
        this.numeroAccesos = numeroAccesos;
        this.tiempoMinTrabajo = tiempoMinTrabajo;
        this.tiempoMaxTrabajo = tiempoMaxTrabajo;
        this.tiempoMinDescanso = 100;
        this.tiempoMaxDescanso = 500;
        
        this.random = new Random();
        this.accesosExitosos = 0;
        this.accesosFallidos = 0;
        this.tiempoTotalEspera = 0;
        this.tiempoTotalTrabajo = 0;
        this.activo = true;
        
        // 🏷️ Configurar nombre del thread
        this.setName("Proceso-" + procesoId);
        
        System.out.printf("🔄 %s inicializado - Accesos: %d, Trabajo: %d-%dms%n", 
                         procesoId, numeroAccesos, tiempoMinTrabajo, tiempoMaxTrabajo);
    }
    
    /**
     * 🏃‍♂️ Método principal del thread
     * Ejecuta el ciclo de vida del proceso con accesos a región crítica
     */
    @Override
    public void run() {
        System.out.printf("▶️ %s iniciado - Thread: %s%n", procesoId, Thread.currentThread().getName());
        
        long tiempoInicioTotal = System.currentTimeMillis();
        
        try {
            for (int i = 1; i <= numeroAccesos && activo && !Thread.currentThread().isInterrupted(); i++) {
                
                System.out.printf("[%s] 🎯 Intento de acceso %d/%d%n", procesoId, i, numeroAccesos);
                
                // 🔒 Intentar acceder a la región crítica
                boolean exitoso = intentarAccesoRegionCritica(i);
                
                if (exitoso) {
                    accesosExitosos++;
                    System.out.printf("[%s] ✅ Acceso %d completado exitosamente%n", procesoId, i);
                } else {
                    accesosFallidos++;
                    System.out.printf("[%s] ❌ Acceso %d falló%n", procesoId, i);
                }
                
                // 😴 Descansar entre accesos (simular trabajo no crítico)
                if (i < numeroAccesos) {
                    descansar();
                }
            }
            
        } catch (InterruptedException e) {
            System.out.printf("⚠️ %s interrumpido%n", procesoId);
            Thread.currentThread().interrupt();
        } finally {
            activo = false;
            long tiempoTotalEjecucion = System.currentTimeMillis() - tiempoInicioTotal;
            System.out.printf("🛑 %s finalizado - Tiempo total: %dms%n", procesoId, tiempoTotalEjecucion);
            mostrarEstadisticasProceso();
        }
    }
    
    /**
     * 🔒 Intentar acceso a la región crítica
     * 
     * @param numeroIntento Número del intento actual
     * @return true si el acceso fue exitoso
     * @throws InterruptedException Si el thread es interrumpido
     */
    private boolean intentarAccesoRegionCritica(int numeroIntento) throws InterruptedException {
        long tiempoInicioAcceso = System.currentTimeMillis();
        
        // 🎲 Generar tiempo de trabajo aleatorio
        int tiempoTrabajo = tiempoMinTrabajo + random.nextInt(tiempoMaxTrabajo - tiempoMinTrabajo + 1);
        
        try {
            // 🚪 Entrar en región crítica
            boolean exitoso = recurso.entrarRegionCritica(procesoId, tiempoTrabajo);
            
            if (exitoso) {
                long tiempoEnRegion = System.currentTimeMillis() - tiempoInicioAcceso;
                tiempoTotalTrabajo += tiempoEnRegion;
                
                // 🚪 Salir de región crítica
                recurso.salirRegionCritica(procesoId);
                
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.err.printf("❌ [%s] Error en acceso %d: %s%n", procesoId, numeroIntento, e.getMessage());
            return false;
        }
    }
    
    /**
     * 😴 Descansar entre accesos (trabajo no crítico)
     * 
     * @throws InterruptedException Si el thread es interrumpido
     */
    private void descansar() throws InterruptedException {
        int tiempoDescanso = tiempoMinDescanso + random.nextInt(tiempoMaxDescanso - tiempoMinDescanso + 1);
        
        System.out.printf("[%s] 😴 Descansando por %dms (trabajo no crítico)%n", procesoId, tiempoDescanso);
        Thread.sleep(tiempoDescanso);
    }
    
    /**
     * 🛑 Detener el proceso de forma segura
     */
    public void detener() {
        activo = false;
        this.interrupt();
        System.out.printf("🔴 %s marcado para detener%n", procesoId);
    }
    
    /**
     * 📊 Mostrar estadísticas específicas del proceso
     */
    private void mostrarEstadisticasProceso() {
        System.out.printf("\n=== 📊 ESTADÍSTICAS %s ===\n", procesoId);
        System.out.printf("✅ Accesos exitosos: %d/%d (%.1f%%)%n", 
                         accesosExitosos, numeroAccesos, 
                         (accesosExitosos * 100.0) / numeroAccesos);
        System.out.printf("❌ Accesos fallidos: %d%n", accesosFallidos);
        System.out.printf("⏱️ Tiempo promedio por acceso: %.2fms%n", 
                         accesosExitosos > 0 ? (double) tiempoTotalTrabajo / accesosExitosos : 0);
        System.out.printf("🎯 Eficiencia: %.1f%%%n", calcularEficiencia());
    }
    
    /**
     * 📈 Calcular eficiencia del proceso
     * 
     * @return Porcentaje de eficiencia (0-100)
     */
    private double calcularEficiencia() {
        if (numeroAccesos == 0) return 0;
        
        double tasaExito = (double) accesosExitosos / numeroAccesos;
        double factorTiempo = tiempoTotalTrabajo > 0 ? 
            Math.min(1.0, 10000.0 / tiempoTotalTrabajo) : 0; // Normalizar tiempo
        
        return (tasaExito * 0.7 + factorTiempo * 0.3) * 100;
    }
    
    /**
     * 📊 Obtener estadísticas del proceso
     * 
     * @return Array con [exitosos, fallidos, tiempoTotal, eficiencia]
     */
    public double[] getEstadisticas() {
        return new double[]{
            accesosExitosos,
            accesosFallidos,
            tiempoTotalTrabajo,
            calcularEficiencia()
        };
    }
    
    /**
     * 🏷️ Obtener identificador del proceso
     * 
     * @return ID del proceso
     */
    public String getProcesoId() {
        return procesoId;
    }
    
    /**
     * ✅ Verificar si el proceso está activo
     * 
     * @return true si está activo, false si está detenido
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * 🎯 Obtener tasa de éxito del proceso
     * 
     * @return Porcentaje de accesos exitosos (0-100)
     */
    public double getTasaExito() {
        return numeroAccesos > 0 ? (accesosExitosos * 100.0) / numeroAccesos : 0;
    }
    
    /**
     * ⏱️ Obtener tiempo promedio por acceso
     * 
     * @return Tiempo promedio en milisegundos
     */
    public double getTiempoPromedioAcceso() {
        return accesosExitosos > 0 ? (double) tiempoTotalTrabajo / accesosExitosos : 0;
    }
    
    /**
     * 📝 Representación en string del proceso
     * 
     * @return Información básica del proceso
     */
    @Override
    public String toString() {
        return String.format("Proceso{id='%s', exitosos=%d/%d, activo=%s, eficiencia=%.1f%%}", 
                           procesoId, accesosExitosos, numeroAccesos, activo, calcularEficiencia());
    }
}
