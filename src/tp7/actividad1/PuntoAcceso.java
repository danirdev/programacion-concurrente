package tp7.actividad1;

import java.util.Random;

/**
 * 🚪 PuntoAcceso - Thread que representa un punto de entrada/salida del jardín
 * 
 * Esta clase implementa un Thread que simula un punto de acceso (P1 o P2) 
 * donde los visitantes pueden entrar y salir del jardín. Cada punto opera
 * de forma concurrente y utiliza el ContadorJardines compartido.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class PuntoAcceso extends Thread {
    
    // 🏷️ Identificación del punto
    private final String puntoId;
    
    // 🌳 Referencia al contador compartido
    private final ContadorJardines contador;
    
    // ⏱️ Configuración de tiempo
    private final int duracionSimulacion; // en segundos
    private final Random random;
    
    // 📊 Estadísticas del punto
    private int entradasRealizadas;
    private int salidasRealizadas;
    private boolean activo;
    
    /**
     * 🏗️ Constructor del PuntoAcceso
     * 
     * @param puntoId Identificador del punto (ej: "P1", "P2")
     * @param contador Referencia al contador compartido
     * @param duracionSimulacion Duración de la simulación en segundos
     */
    public PuntoAcceso(String puntoId, ContadorJardines contador, int duracionSimulacion) {
        this.puntoId = puntoId;
        this.contador = contador;
        this.duracionSimulacion = duracionSimulacion;
        this.random = new Random();
        this.entradasRealizadas = 0;
        this.salidasRealizadas = 0;
        this.activo = true;
        
        // 🏷️ Configurar nombre del thread
        this.setName("PuntoAcceso-" + puntoId);
        
        System.out.println("🚪 " + puntoId + " inicializado - Duración: " + duracionSimulacion + "s");
    }
    
    /**
     * 🏃‍♂️ Método principal del thread
     * Simula la operación continua del punto de acceso
     */
    @Override
    public void run() {
        System.out.println("▶️  " + puntoId + " iniciado - Thread: " + Thread.currentThread().getName());
        
        long tiempoInicio = System.currentTimeMillis();
        long tiempoLimite = tiempoInicio + (duracionSimulacion * 1000L);
        
        try {
            while (activo && System.currentTimeMillis() < tiempoLimite && !Thread.currentThread().isInterrupted()) {
                
                // 🎲 Decidir aleatoriamente si es entrada o salida
                boolean esEntrada = decidirOperacion();
                
                if (esEntrada) {
                    // 🚪 Procesar entrada de visitante
                    procesarEntrada();
                } else {
                    // 🚪 Procesar salida de visitante
                    procesarSalida();
                }
                
                // ⏱️ Pausa aleatoria entre operaciones (0.5 - 2 segundos)
                Thread.sleep(500 + random.nextInt(1500));
            }
            
        } catch (InterruptedException e) {
            System.out.println("⚠️  " + puntoId + " interrumpido");
            Thread.currentThread().interrupt();
        } finally {
            activo = false;
            System.out.println("🛑 " + puntoId + " finalizado");
            mostrarEstadisticasPunto();
        }
    }
    
    /**
     * 🎲 Decidir si la próxima operación es entrada o salida
     * Lógica: 60% probabilidad de entrada, 40% de salida
     * 
     * @return true si es entrada, false si es salida
     */
    private boolean decidirOperacion() {
        // Si no hay visitantes, forzar entrada
        if (contador.getVisitantesActuales() == 0) {
            return true;
        }
        
        // 60% entrada, 40% salida
        return random.nextDouble() < 0.6;
    }
    
    /**
     * 🚪 Procesar entrada de un visitante
     */
    private void procesarEntrada() {
        try {
            // ⏱️ Simular tiempo de procesamiento de entrada
            Thread.sleep(100 + random.nextInt(200));
            
            // 🌳 Registrar entrada en el contador compartido
            contador.entrarVisitante(puntoId);
            entradasRealizadas++;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🚪 Procesar salida de un visitante
     */
    private void procesarSalida() {
        try {
            // ⏱️ Simular tiempo de procesamiento de salida
            Thread.sleep(100 + random.nextInt(200));
            
            // 🌳 Registrar salida en el contador compartido
            int visitantesAntes = contador.getVisitantesActuales();
            contador.salirVisitante(puntoId);
            
            // Solo contar como salida exitosa si había visitantes
            if (visitantesAntes > 0) {
                salidasRealizadas++;
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🛑 Detener el punto de acceso de forma segura
     */
    public void detener() {
        activo = false;
        this.interrupt();
        System.out.println("🔴 " + puntoId + " marcado para detener");
    }
    
    /**
     * 📊 Mostrar estadísticas específicas del punto
     */
    private void mostrarEstadisticasPunto() {
        System.out.println("\n=== 📊 ESTADÍSTICAS " + puntoId + " ===");
        System.out.println("🚪 Entradas procesadas: " + entradasRealizadas);
        System.out.println("🚪 Salidas procesadas: " + salidasRealizadas);
        System.out.println("⚡ Total operaciones: " + (entradasRealizadas + salidasRealizadas));
        System.out.println("🔄 Diferencia (E-S): " + (entradasRealizadas - salidasRealizadas));
    }
    
    /**
     * 📈 Obtener estadísticas del punto
     * 
     * @return Array con [entradas, salidas, total]
     */
    public int[] getEstadisticas() {
        return new int[]{entradasRealizadas, salidasRealizadas, entradasRealizadas + salidasRealizadas};
    }
    
    /**
     * 🏷️ Obtener identificador del punto
     * 
     * @return ID del punto de acceso
     */
    public String getPuntoId() {
        return puntoId;
    }
    
    /**
     * ✅ Verificar si el punto está activo
     * 
     * @return true si está activo, false si está detenido
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * 📝 Representación en string del punto
     * 
     * @return Información básica del punto
     */
    @Override
    public String toString() {
        return String.format("PuntoAcceso{id='%s', entradas=%d, salidas=%d, activo=%s}", 
                           puntoId, entradasRealizadas, salidasRealizadas, activo);
    }
}
