package tp7.actividad5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🚧 Barrera - Implementación de barrera de sincronización con semáforos
 * 
 * Esta clase implementa una barrera de sincronización que permite que múltiples
 * threads esperen hasta que todos alcancen un punto de sincronización común,
 * y luego todos continúan simultáneamente.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Barrera {
    
    // 🔢 Número de threads que deben esperar en la barrera
    private final int numeroThreads;
    
    // 🚦 Semáforos para la barrera
    private final Semaphore mutex;      // Protege el contador
    private final Semaphore barrera;    // Bloquea threads hasta liberación
    
    // 📊 Contador de threads esperando
    private final AtomicInteger contador;
    
    // 📈 Estadísticas
    private int totalActivaciones;
    private long tiempoUltimaActivacion;
    
    /**
     * 🏗️ Constructor de la Barrera
     * 
     * @param numeroThreads Número de threads que deben sincronizarse
     */
    public Barrera(int numeroThreads) {
        if (numeroThreads <= 0) {
            throw new IllegalArgumentException("Número de threads debe ser mayor a 0");
        }
        
        this.numeroThreads = numeroThreads;
        this.mutex = new Semaphore(1, true); // Fairness habilitado
        this.barrera = new Semaphore(0, true);
        this.contador = new AtomicInteger(0);
        this.totalActivaciones = 0;
        this.tiempoUltimaActivacion = 0;
        
        System.out.printf("🚧 Barrera creada para %d threads%n", numeroThreads);
    }
    
    /**
     * ⏳ Esperar en la barrera
     * El thread se bloquea hasta que todos los threads lleguen a la barrera
     * 
     * @param threadId ID del thread que espera
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void esperar(String threadId) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        // 🔒 Entrar en sección crítica para incrementar contador
        mutex.acquire();
        
        int numeroActual = contador.incrementAndGet();
        System.out.printf("[%s] 🚧 Llegó a la barrera (%d/%d)%n", 
                         threadId, numeroActual, numeroThreads);
        
        if (numeroActual == numeroThreads) {
            // 🔓 Último thread: liberar a todos
            System.out.printf("[%s] 🔔 Última llegada - Liberando barrera%n", threadId);
            
            totalActivaciones++;
            tiempoUltimaActivacion = System.currentTimeMillis();
            
            // Resetear contador para siguiente uso
            contador.set(0);
            
            // Liberar todos los threads esperando
            barrera.release(numeroThreads);
            
            System.out.printf("[%s] ✅ Barrera liberada - Todos pueden continuar%n", threadId);
        }
        
        mutex.release();
        
        // ⏳ Esperar hasta que la barrera se libere
        barrera.acquire();
        
        long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("[%s] 🏃 Pasando barrera - Espera: %dms%n", threadId, tiempoEspera);
    }
    
    /**
     * 🔄 Resetear la barrera (para reutilización)
     */
    public void resetear() {
        try {
            mutex.acquire();
            contador.set(0);
            // Drenar cualquier permiso residual
            barrera.drainPermits();
            mutex.release();
            System.out.println("🔄 Barrera reseteada");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 📊 Obtener estadísticas de la barrera
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 🚧 ESTADÍSTICAS BARRERA ===\n"));
        stats.append(String.format("🔢 Threads requeridos: %d%n", numeroThreads));
        stats.append(String.format("📊 Threads esperando: %d%n", contador.get()));
        stats.append(String.format("🔄 Total activaciones: %d%n", totalActivaciones));
        
        if (tiempoUltimaActivacion > 0) {
            long tiempoDesdeUltima = System.currentTimeMillis() - tiempoUltimaActivacion;
            stats.append(String.format("⏱️ Tiempo desde última activación: %dms%n", tiempoDesdeUltima));
        }
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar si todos los threads han pasado la barrera
     * 
     * @return true si la barrera está completa
     */
    public boolean estaCompleta() {
        return contador.get() == 0 && barrera.availablePermits() == 0;
    }
    
    /**
     * 📈 Obtener número de threads esperando
     * 
     * @return Número de threads en la barrera
     */
    public int getThreadsEsperando() {
        return contador.get();
    }
    
    /**
     * 🔢 Obtener número total de threads requeridos
     * 
     * @return Número de threads configurado
     */
    public int getNumeroThreads() {
        return numeroThreads;
    }
    
    /**
     * 📊 Obtener total de activaciones
     * 
     * @return Número de veces que la barrera fue activada
     */
    public int getTotalActivaciones() {
        return totalActivaciones;
    }
    
    /**
     * 🔍 Verificar integridad de la barrera
     * 
     * @return true si la barrera está en estado consistente
     */
    public boolean verificarIntegridad() {
        int esperando = contador.get();
        boolean contadorValido = esperando >= 0 && esperando <= numeroThreads;
        boolean mutexValido = mutex.availablePermits() >= 0 && mutex.availablePermits() <= 1;
        boolean barreraValida = barrera.availablePermits() >= 0;
        
        boolean integra = contadorValido && mutexValido && barreraValida;
        
        if (!integra) {
            System.err.printf("❌ INTEGRIDAD BARRERA COMPROMETIDA:%n");
            System.err.printf("   Contador válido: %s (%d)%n", contadorValido, esperando);
            System.err.printf("   Mutex válido: %s%n", mutexValido);
            System.err.printf("   Barrera válida: %s%n", barreraValida);
        }
        
        return integra;
    }
    
    /**
     * 📝 Representación en string de la barrera
     * 
     * @return Información de la barrera
     */
    @Override
    public String toString() {
        return String.format("Barrera{threads=%d, esperando=%d/%d, activaciones=%d}", 
                           numeroThreads, contador.get(), numeroThreads, totalActivaciones);
    }
}
