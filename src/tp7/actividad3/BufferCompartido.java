package tp7.actividad3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🔄 BufferCompartido - Buffer circular sincronizado con semáforos
 * 
 * Esta clase implementa un buffer circular thread-safe utilizando semáforos
 * para resolver el problema clásico Productor-Consumidor. Garantiza exclusión
 * mutua y sincronización correcta entre múltiples productores y consumidores.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class BufferCompartido {
    
    // 📦 Buffer circular para almacenar productos
    private final Producto[] buffer;
    private final int capacidad;
    
    // 🔄 Índices para buffer circular
    private int indiceProduccion;  // Donde se coloca el próximo elemento
    private int indiceConsumo;     // Donde se toma el próximo elemento
    
    // 🚦 Semáforos para sincronización
    private final Semaphore mutex;              // Exclusión mutua (inicializado en 1)
    private final Semaphore espaciosLibres;     // Espacios disponibles (inicializado en N)
    private final Semaphore elementosDisponibles; // Elementos listos para consumir (inicializado en 0)
    
    // 📊 Estadísticas del buffer
    private final AtomicInteger totalProducidos;
    private final AtomicInteger totalConsumidos;
    private final AtomicInteger elementosActuales;
    private final AtomicInteger operacionesTotales;
    
    // 📈 Métricas de rendimiento
    private long tiempoEsperaProduccion;
    private long tiempoEsperaConsumo;
    private int maxElementosSimultaneos;
    private long tiempoInicioBuffer;
    
    /**
     * 🏗️ Constructor del BufferCompartido
     * 
     * @param capacidad Tamaño máximo del buffer
     */
    public BufferCompartido(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        
        this.capacidad = capacidad;
        this.buffer = new Producto[capacidad];
        this.indiceProduccion = 0;
        this.indiceConsumo = 0;
        
        // 🚦 Inicializar semáforos
        this.mutex = new Semaphore(1, true); // Fairness habilitado
        this.espaciosLibres = new Semaphore(capacidad, true);
        this.elementosDisponibles = new Semaphore(0, true);
        
        // 📊 Inicializar estadísticas
        this.totalProducidos = new AtomicInteger(0);
        this.totalConsumidos = new AtomicInteger(0);
        this.elementosActuales = new AtomicInteger(0);
        this.operacionesTotales = new AtomicInteger(0);
        
        this.tiempoEsperaProduccion = 0;
        this.tiempoEsperaConsumo = 0;
        this.maxElementosSimultaneos = 0;
        this.tiempoInicioBuffer = System.currentTimeMillis();
        
        System.out.printf("🔄 BufferCompartido inicializado - Capacidad: %d%n", capacidad);
        System.out.printf("🚦 Semáforos: mutex(1), espacios(%d), elementos(0)%n", capacidad);
    }
    
    /**
     * 🏭 Poner un producto en el buffer (operación del Productor)
     * 
     * @param producto Producto a almacenar
     * @param productorId ID del productor
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void poner(Producto producto, String productorId) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // 🚦 Paso 1: Esperar espacio libre en el buffer
            System.out.printf("[%s] 🔄 Esperando espacio libre para producto #%d%n", 
                             productorId, producto.getId());
            espaciosLibres.acquire();
            
            // 🚦 Paso 2: Adquirir exclusión mutua
            mutex.acquire();
            
            long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
            tiempoEsperaProduccion += tiempoEspera;
            
            // ⚡ REGIÓN CRÍTICA: Colocar producto en buffer
            buffer[indiceProduccion] = producto;
            producto.marcarComoProducido(productorId, totalProducidos.get() + 1);
            
            System.out.printf("[%s] 📦 Producto #%d colocado en posición %d - Espera: %dms%n", 
                             productorId, producto.getId(), indiceProduccion, tiempoEspera);
            
            // 🔄 Avanzar índice circular
            indiceProduccion = (indiceProduccion + 1) % capacidad;
            
            // 📊 Actualizar estadísticas
            int elementosActualesCount = elementosActuales.incrementAndGet();
            totalProducidos.incrementAndGet();
            operacionesTotales.incrementAndGet();
            
            if (elementosActualesCount > maxElementosSimultaneos) {
                maxElementosSimultaneos = elementosActualesCount;
            }
            
            System.out.printf("[%s] 📊 Buffer: %d/%d elementos%n", 
                             productorId, elementosActualesCount, capacidad);
            
        } finally {
            // 🚦 Paso 3: Liberar exclusión mutua
            mutex.release();
            
            // 🚦 Paso 4: Señalar que hay un elemento disponible
            elementosDisponibles.release();
        }
    }
    
    /**
     * 👤 Tomar un producto del buffer (operación del Consumidor)
     * 
     * @param consumidorId ID del consumidor
     * @return Producto extraído del buffer
     * @throws InterruptedException Si el thread es interrumpido
     */
    public Producto tomar(String consumidorId) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        Producto producto = null;
        
        try {
            // 🚦 Paso 1: Esperar elemento disponible
            System.out.printf("[%s] 🔄 Esperando elemento disponible%n", consumidorId);
            elementosDisponibles.acquire();
            
            // 🚦 Paso 2: Adquirir exclusión mutua
            mutex.acquire();
            
            long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
            tiempoEsperaConsumo += tiempoEspera;
            
            // ⚡ REGIÓN CRÍTICA: Extraer producto del buffer
            producto = buffer[indiceConsumo];
            buffer[indiceConsumo] = null; // Limpiar referencia
            
            System.out.printf("[%s] 📦 Producto #%d extraído de posición %d - Espera: %dms%n", 
                             consumidorId, producto.getId(), indiceConsumo, tiempoEspera);
            
            // 🔄 Avanzar índice circular
            indiceConsumo = (indiceConsumo + 1) % capacidad;
            
            // 📊 Actualizar estadísticas
            int elementosActualesCount = elementosActuales.decrementAndGet();
            totalConsumidos.incrementAndGet();
            operacionesTotales.incrementAndGet();
            
            producto.marcarComoConsumido(consumidorId);
            
            System.out.printf("[%s] 📊 Buffer: %d/%d elementos%n", 
                             consumidorId, elementosActualesCount, capacidad);
            
        } finally {
            // 🚦 Paso 3: Liberar exclusión mutua
            mutex.release();
            
            // 🚦 Paso 4: Señalar que hay un espacio libre
            espaciosLibres.release();
        }
        
        return producto;
    }
    
    /**
     * 📊 Obtener estadísticas completas del buffer
     * 
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        long tiempoTotal = System.currentTimeMillis() - tiempoInicioBuffer;
        double tiempoSegundos = tiempoTotal / 1000.0;
        
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📊 ESTADÍSTICAS DEL BUFFER ===\n");
        stats.append(String.format("📦 Capacidad: %d elementos%n", capacidad));
        stats.append(String.format("🏭 Total producidos: %d%n", totalProducidos.get()));
        stats.append(String.format("👤 Total consumidos: %d%n", totalConsumidos.get()));
        stats.append(String.format("📊 Elementos actuales: %d%n", elementosActuales.get()));
        stats.append(String.format("⚡ Operaciones totales: %d%n", operacionesTotales.get()));
        stats.append(String.format("📈 Máximo simultáneo: %d%n", maxElementosSimultaneos));
        stats.append(String.format("⏱️ Tiempo total: %.2f segundos%n", tiempoSegundos));
        
        if (totalProducidos.get() > 0) {
            stats.append(String.format("🏭 Tiempo promedio espera producción: %.2fms%n", 
                                     (double) tiempoEsperaProduccion / totalProducidos.get()));
        }
        
        if (totalConsumidos.get() > 0) {
            stats.append(String.format("👤 Tiempo promedio espera consumo: %.2fms%n", 
                                     (double) tiempoEsperaConsumo / totalConsumidos.get()));
        }
        
        if (tiempoSegundos > 0) {
            stats.append(String.format("📊 Throughput: %.2f operaciones/segundo%n", 
                                     operacionesTotales.get() / tiempoSegundos));
        }
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad del buffer
     * 
     * @return true si el buffer está en estado consistente
     */
    public boolean verificarIntegridad() {
        try {
            mutex.acquire();
            
            // Verificar que los índices estén en rango válido
            boolean indicesValidos = (indiceProduccion >= 0 && indiceProduccion < capacidad) &&
                                   (indiceConsumo >= 0 && indiceConsumo < capacidad);
            
            // Contar elementos no nulos en el buffer
            int elementosContados = 0;
            for (Producto producto : buffer) {
                if (producto != null) {
                    elementosContados++;
                }
            }
            
            // Verificar consistencia con contador atómico
            boolean contadorConsistente = elementosContados == elementosActuales.get();
            
            // Verificar que producidos >= consumidos
            boolean balanceValido = totalProducidos.get() >= totalConsumidos.get();
            
            // Verificar que la diferencia coincida con elementos actuales
            boolean diferenciaCorrecta = (totalProducidos.get() - totalConsumidos.get()) == elementosActuales.get();
            
            boolean integro = indicesValidos && contadorConsistente && balanceValido && diferenciaCorrecta;
            
            if (!integro) {
                System.err.printf("❌ INTEGRIDAD COMPROMETIDA:%n");
                System.err.printf("   Índices válidos: %s%n", indicesValidos);
                System.err.printf("   Contador consistente: %s (contados: %d, registrados: %d)%n", 
                                contadorConsistente, elementosContados, elementosActuales.get());
                System.err.printf("   Balance válido: %s%n", balanceValido);
                System.err.printf("   Diferencia correcta: %s%n", diferenciaCorrecta);
            }
            
            return integro;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            mutex.release();
        }
    }
    
    /**
     * 📈 Obtener métricas de rendimiento
     * 
     * @return Array con métricas [throughput, utilizacion, eficiencia]
     */
    public double[] getMetricasRendimiento() {
        long tiempoTotal = System.currentTimeMillis() - tiempoInicioBuffer;
        double tiempoSegundos = Math.max(tiempoTotal / 1000.0, 0.001); // Evitar división por 0
        
        double throughput = operacionesTotales.get() / tiempoSegundos;
        double utilizacionPromedio = maxElementosSimultaneos > 0 ? 
            (double) maxElementosSimultaneos / capacidad * 100 : 0;
        
        // Eficiencia basada en tiempo de espera vs tiempo total
        long tiempoEsperaTotal = tiempoEsperaProduccion + tiempoEsperaConsumo;
        double eficiencia = tiempoTotal > 0 ? 
            Math.max(0, 100 - (tiempoEsperaTotal * 100.0 / tiempoTotal)) : 0;
        
        return new double[]{throughput, utilizacionPromedio, eficiencia};
    }
    
    /**
     * 🚦 Información del estado de los semáforos
     * 
     * @return String con información de semáforos
     */
    public String getInfoSemaforos() {
        return String.format("🚦 Semáforos - Mutex: %d, Espacios: %d, Elementos: %d | Colas: %d, %d, %d", 
                           mutex.availablePermits(),
                           espaciosLibres.availablePermits(), 
                           elementosDisponibles.availablePermits(),
                           mutex.getQueueLength(),
                           espaciosLibres.getQueueLength(),
                           elementosDisponibles.getQueueLength());
    }
    
    /**
     * 🔄 Resetear estadísticas (para testing)
     */
    public void resetearEstadisticas() {
        try {
            mutex.acquire();
            
            totalProducidos.set(0);
            totalConsumidos.set(0);
            elementosActuales.set(0);
            operacionesTotales.set(0);
            tiempoEsperaProduccion = 0;
            tiempoEsperaConsumo = 0;
            maxElementosSimultaneos = 0;
            tiempoInicioBuffer = System.currentTimeMillis();
            
            // Limpiar buffer
            for (int i = 0; i < capacidad; i++) {
                buffer[i] = null;
            }
            indiceProduccion = 0;
            indiceConsumo = 0;
            
            System.out.println("🔄 Estadísticas del buffer reseteadas");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
    
    // 🔧 Getters
    
    public int getCapacidad() { return capacidad; }
    
    public int getElementosActuales() { return elementosActuales.get(); }
    
    public int getTotalProducidos() { return totalProducidos.get(); }
    
    public int getTotalConsumidos() { return totalConsumidos.get(); }
    
    public boolean estaVacio() { return elementosActuales.get() == 0; }
    
    public boolean estaLleno() { return elementosActuales.get() == capacidad; }
    
    /**
     * 📝 Representación en string del buffer
     * 
     * @return Información básica del buffer
     */
    @Override
    public String toString() {
        return String.format("BufferCompartido{capacidad=%d, elementos=%d/%d, producidos=%d, consumidos=%d}", 
                           capacidad, elementosActuales.get(), capacidad, 
                           totalProducidos.get(), totalConsumidos.get());
    }
}
