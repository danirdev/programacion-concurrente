package tp7.actividad1;

import java.util.concurrent.Semaphore;

/**
 * 🌳 ContadorJardines - Recurso Compartido para el Problema de los Jardines
 * 
 * Esta clase representa el contador compartido de visitantes en los jardines.
 * Utiliza un semáforo mutex para garantizar exclusión mutua en las operaciones
 * de entrada y salida de visitantes.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ContadorJardines {
    
    // 🔢 Variable compartida - contador de visitantes actuales
    private int contadorVisitantes;
    
    // 🚦 Semáforo mutex para exclusión mutua (inicializado en 1)
    private final Semaphore mutex;
    
    // 📊 Estadísticas de operaciones
    private int totalEntradas;
    private int totalSalidas;
    private int totalOperaciones;
    
    /**
     * 🏗️ Constructor - Inicializa el contador y el semáforo
     */
    public ContadorJardines() {
        this.contadorVisitantes = 0;
        this.mutex = new Semaphore(1, true); // Fairness habilitado
        this.totalEntradas = 0;
        this.totalSalidas = 0;
        this.totalOperaciones = 0;
        
        System.out.println("🌳 ContadorJardines inicializado - Visitantes: " + contadorVisitantes);
    }
    
    /**
     * 🚪 Operación: Entrada de visitante
     * Incrementa el contador de visitantes de forma sincronizada
     * 
     * @param puntoId Identificador del punto de acceso (P1 o P2)
     * @return Número actual de visitantes después de la entrada
     */
    public int entrarVisitante(String puntoId) {
        try {
            // 🔒 Adquirir el semáforo (entrar en región crítica)
            mutex.acquire();
            
            // ⚡ REGIÓN CRÍTICA: Incrementar contador
            contadorVisitantes++;
            totalEntradas++;
            totalOperaciones++;
            
            int visitantesActuales = contadorVisitantes;
            
            // 📝 Log de la operación
            String timestamp = java.time.LocalTime.now().toString();
            System.out.printf("[%s] %s - Visitante ENTRA - Total: %d%n", 
                            puntoId, timestamp, visitantesActuales);
            
            return visitantesActuales;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Error en entrarVisitante: " + e.getMessage());
            return contadorVisitantes;
        } finally {
            // 🔓 Liberar el semáforo (salir de región crítica)
            mutex.release();
        }
    }
    
    /**
     * 🚪 Operación: Salida de visitante
     * Decrementa el contador de visitantes de forma sincronizada
     * 
     * @param puntoId Identificador del punto de acceso (P1 o P2)
     * @return Número actual de visitantes después de la salida
     */
    public int salirVisitante(String puntoId) {
        try {
            // 🔒 Adquirir el semáforo (entrar en región crítica)
            mutex.acquire();
            
            // ⚡ REGIÓN CRÍTICA: Decrementar contador (solo si hay visitantes)
            if (contadorVisitantes > 0) {
                contadorVisitantes--;
                totalSalidas++;
                totalOperaciones++;
                
                int visitantesActuales = contadorVisitantes;
                
                // 📝 Log de la operación
                String timestamp = java.time.LocalTime.now().toString();
                System.out.printf("[%s] %s - Visitante SALE  - Total: %d%n", 
                                puntoId, timestamp, visitantesActuales);
                
                return visitantesActuales;
            } else {
                // 🚫 No hay visitantes para salir
                System.out.printf("[%s] ⚠️  No hay visitantes para salir%n", puntoId);
                return 0;
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Error en salirVisitante: " + e.getMessage());
            return contadorVisitantes;
        } finally {
            // 🔓 Liberar el semáforo (salir de región crítica)
            mutex.release();
        }
    }
    
    /**
     * 📊 Obtener número actual de visitantes (lectura sincronizada)
     * 
     * @return Número actual de visitantes en el jardín
     */
    public int getVisitantesActuales() {
        try {
            mutex.acquire();
            return contadorVisitantes;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return contadorVisitantes;
        } finally {
            mutex.release();
        }
    }
    
    /**
     * 📈 Obtener estadísticas completas del jardín
     * 
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        try {
            mutex.acquire();
            
            StringBuilder stats = new StringBuilder();
            stats.append("\n=== 📊 ESTADÍSTICAS DEL JARDÍN ===\n");
            stats.append(String.format("👥 Visitantes Actuales: %d%n", contadorVisitantes));
            stats.append(String.format("🚪 Total Entradas: %d%n", totalEntradas));
            stats.append(String.format("🚪 Total Salidas: %d%n", totalSalidas));
            stats.append(String.format("⚡ Total Operaciones: %d%n", totalOperaciones));
            stats.append(String.format("🔄 Diferencia (E-S): %d%n", totalEntradas - totalSalidas));
            
            return stats.toString();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "❌ Error obteniendo estadísticas";
        } finally {
            mutex.release();
        }
    }
    
    /**
     * 🔍 Información del estado del semáforo
     * 
     * @return String con información del semáforo
     */
    public String getInfoSemaforo() {
        return String.format("🚦 Semáforo - Permisos disponibles: %d, Cola: %d", 
                           mutex.availablePermits(), 
                           mutex.getQueueLength());
    }
    
    /**
     * 🧹 Resetear estadísticas (para testing)
     */
    public void resetear() {
        try {
            mutex.acquire();
            contadorVisitantes = 0;
            totalEntradas = 0;
            totalSalidas = 0;
            totalOperaciones = 0;
            System.out.println("🔄 ContadorJardines reseteado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
}
