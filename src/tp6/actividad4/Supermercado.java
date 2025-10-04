package tp6.actividad4;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase Supermercado que gestiona los recursos del supermercado:
 * - 15 carritos para que los clientes hagan sus compras
 * - 3 cajas de atención para procesar los pagos
 * 
 * Utiliza semáforos para controlar el acceso a estos recursos limitados.
 */
public class Supermercado {
    
    // Recursos del supermercado
    private final Semaphore carritos;
    private final Semaphore cajas;
    private final Random random;
    private final DateTimeFormatter timeFormatter;
    
    // Estadísticas del supermercado
    private int clientesTotalesAtendidos = 0;
    private int clientesActualmenteComprando = 0;
    private int clientesActualmentePagando = 0;
    private long tiempoInicioOperacion;
    
    // Configuración
    private static final int TOTAL_CARRITOS = 15;
    private static final int TOTAL_CAJAS = 3;
    private static final int TIEMPO_MIN_COMPRAS = 4000; // 4 segundos
    private static final int TIEMPO_MAX_COMPRAS = 7000; // 7 segundos
    private static final int TIEMPO_MIN_PAGO = 2000;    // 2 segundos
    private static final int TIEMPO_MAX_PAGO = 4000;    // 4 segundos
    
    /**
     * Constructor del Supermercado
     */
    public Supermercado() {
        // Inicializar semáforos con fairness para orden FIFO
        this.carritos = new Semaphore(TOTAL_CARRITOS, true);
        this.cajas = new Semaphore(TOTAL_CAJAS, true);
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.tiempoInicioOperacion = System.currentTimeMillis();
        
        System.out.println("🏪 Supermercado abierto al público");
        System.out.println("   Carritos disponibles: " + TOTAL_CARRITOS);
        System.out.println("   Cajas de atención: " + TOTAL_CAJAS);
        System.out.println("   Tiempo de compras: " + TIEMPO_MIN_COMPRAS/1000 + "-" + TIEMPO_MAX_COMPRAS/1000 + " segundos");
        System.out.println("   Tiempo de pago: " + TIEMPO_MIN_PAGO/1000 + "-" + TIEMPO_MAX_PAGO/1000 + " segundos");
    }
    
    /**
     * Método para que un cliente entre al supermercado y tome un carrito.
     * Si no hay carritos disponibles, el cliente espera afuera.
     * 
     * @param clienteId ID del cliente
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void entrarYTomarCarrito(int clienteId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Si no hay carritos disponibles, mostrar que está esperando
        if (carritos.availablePermits() == 0) {
            System.out.println("[" + tiempo + "] Cliente " + clienteId + 
                             " esperando afuera del supermercado (carritos ocupados: " + 
                             TOTAL_CARRITOS + "/" + TOTAL_CARRITOS + ")");
        }
        
        // Esperar por un carrito disponible
        carritos.acquire();
        
        synchronized(this) {
            clientesActualmenteComprando++;
        }
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] ✅ Cliente " + clienteId + 
                          " entró al Súper y tomó un carrito " +
                          "(Carritos disponibles: " + carritos.availablePermits() + 
                          "/" + TOTAL_CARRITOS + ")");
    }
    
    /**
     * Método para simular que un cliente está comprando.
     * 
     * @param clienteId ID del cliente
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void realizarCompras(int clienteId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Calcular tiempo aleatorio de compras (4-7 segundos)
        int tiempoCompras = TIEMPO_MIN_COMPRAS + 
                           random.nextInt(TIEMPO_MAX_COMPRAS - TIEMPO_MIN_COMPRAS + 1);
        
        System.out.println("[" + tiempo + "] 🛒 Cliente " + clienteId + 
                          " está comprando (" + String.format("%.1f", tiempoCompras/1000.0) + "s)");
        
        // Simular tiempo de compras
        Thread.sleep(tiempoCompras);
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🛍️ Cliente " + clienteId + 
                          " terminó de comprar, dirigiéndose a las cajas");
    }
    
    /**
     * Método para que un cliente pague en una caja.
     * Si no hay cajas disponibles, el cliente espera en cola.
     * 
     * @param clienteId ID del cliente
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void pagarEnCaja(int clienteId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Si no hay cajas disponibles, mostrar que está esperando
        if (cajas.availablePermits() == 0) {
            System.out.println("[" + tiempo + "] Cliente " + clienteId + 
                             " esperando en cola para pagar (cajas ocupadas: " + 
                             TOTAL_CAJAS + "/" + TOTAL_CAJAS + ")");
        }
        
        // Esperar por una caja disponible
        cajas.acquire();
        
        synchronized(this) {
            clientesActualmentePagando++;
            clientesActualmenteComprando--;
        }
        
        try {
            // Calcular tiempo aleatorio de pago (2-4 segundos)
            int tiempoPago = TIEMPO_MIN_PAGO + 
                           random.nextInt(TIEMPO_MAX_PAGO - TIEMPO_MIN_PAGO + 1);
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 💳 Cliente " + clienteId + 
                             " está pagando en la caja (" + 
                             String.format("%.1f", tiempoPago/1000.0) + "s)" +
                             " (Cajas disponibles: " + cajas.availablePermits() + 
                             "/" + TOTAL_CAJAS + ")");
            
            // Simular tiempo de pago
            Thread.sleep(tiempoPago);
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ Cliente " + clienteId + 
                             " terminó de pagar");
            
        } finally {
            // Liberar la caja
            cajas.release();
            
            synchronized(this) {
                clientesActualmentePagando--;
            }
        }
    }
    
    /**
     * Método para que un cliente salga del supermercado y devuelva el carrito.
     * 
     * @param clienteId ID del cliente
     */
    public void salirYDevolverCarrito(int clienteId) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Liberar el carrito
        carritos.release();
        
        synchronized(this) {
            clientesTotalesAtendidos++;
        }
        
        System.out.println("[" + tiempo + "] 🚪 Cliente " + clienteId + 
                          " abandona el Súper " +
                          "(Carritos disponibles: " + carritos.availablePermits() + 
                          "/" + TOTAL_CARRITOS + 
                          ", Total atendidos: " + clientesTotalesAtendidos + ")");
        
        // Si hay clientes esperando, notificar que se liberó un carrito
        if (carritos.getQueueLength() > 0) {
            System.out.println("[" + tiempo + "] 📢 Carrito disponible - " + 
                             carritos.getQueueLength() + " clientes esperando afuera");
        }
    }
    
    /**
     * Obtiene estadísticas actuales del supermercado
     * 
     * @return Array con [clientesTotales, comprando, pagando, carritosDisponibles, cajasDisponibles]
     */
    public int[] getEstadisticas() {
        synchronized(this) {
            return new int[]{
                clientesTotalesAtendidos,
                clientesActualmenteComprando,
                clientesActualmentePagando,
                carritos.availablePermits(),
                cajas.availablePermits()
            };
        }
    }
    
    /**
     * Muestra el estado actual del supermercado
     */
    public void mostrarEstado() {
        synchronized(this) {
            String tiempo = LocalTime.now().format(timeFormatter);
            long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
            
            System.out.println("\n[" + tiempo + "] === ESTADO DEL SUPERMERCADO ===");
            System.out.println("Tiempo de operación: " + String.format("%.1f", tiempoOperacion/1000.0) + "s");
            System.out.println("Clientes totales atendidos: " + clientesTotalesAtendidos);
            System.out.println("Clientes comprando actualmente: " + clientesActualmenteComprando);
            System.out.println("Clientes pagando actualmente: " + clientesActualmentePagando);
            System.out.println("Carritos disponibles: " + carritos.availablePermits() + "/" + TOTAL_CARRITOS);
            System.out.println("Cajas disponibles: " + cajas.availablePermits() + "/" + TOTAL_CAJAS);
            System.out.println("Clientes esperando carrito: " + carritos.getQueueLength());
            System.out.println("Clientes esperando caja: " + cajas.getQueueLength());
            
            // Calcular utilización
            double utilizacionCarritos = ((TOTAL_CARRITOS - carritos.availablePermits()) * 100.0) / TOTAL_CARRITOS;
            double utilizacionCajas = ((TOTAL_CAJAS - cajas.availablePermits()) * 100.0) / TOTAL_CAJAS;
            
            System.out.println("Utilización carritos: " + String.format("%.1f%%", utilizacionCarritos));
            System.out.println("Utilización cajas: " + String.format("%.1f%%", utilizacionCajas));
            
            if (clientesTotalesAtendidos > 0) {
                double throughput = (clientesTotalesAtendidos * 60.0) / (tiempoOperacion / 1000.0);
                System.out.println("Throughput: " + String.format("%.1f", throughput) + " clientes/minuto");
            }
            
            // Estado general
            if (carritos.availablePermits() == 0) {
                System.out.println("Estado: 🔴 CARRITOS AGOTADOS - Clientes esperando afuera");
            } else if (cajas.availablePermits() == 0) {
                System.out.println("Estado: 🟡 CAJAS OCUPADAS - Cola en cajas");
            } else {
                System.out.println("Estado: 🟢 OPERACIÓN NORMAL");
            }
            
            System.out.println("==========================================\n");
        }
    }
    
    /**
     * Verifica si todos los carritos están ocupados
     * 
     * @return true si no hay carritos disponibles, false en caso contrario
     */
    public boolean carritosAgotados() {
        return carritos.availablePermits() == 0;
    }
    
    /**
     * Verifica si todas las cajas están ocupadas
     * 
     * @return true si no hay cajas disponibles, false en caso contrario
     */
    public boolean cajasOcupadas() {
        return cajas.availablePermits() == 0;
    }
    
    /**
     * Obtiene el número de clientes esperando por carritos
     * 
     * @return Número de clientes en cola esperando carrito
     */
    public int getClientesEsperandoCarrito() {
        return carritos.getQueueLength();
    }
    
    /**
     * Obtiene el número de clientes esperando por cajas
     * 
     * @return Número de clientes en cola esperando caja
     */
    public int getClientesEsperandoCaja() {
        return cajas.getQueueLength();
    }
    
    /**
     * Calcula estadísticas de rendimiento
     * 
     * @return Array con [throughput, utilizacionCarritos, utilizacionCajas, eficiencia]
     */
    public double[] calcularRendimiento() {
        synchronized(this) {
            long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
            
            if (tiempoOperacion == 0) {
                return new double[]{0.0, 0.0, 0.0, 0.0};
            }
            
            double throughput = (clientesTotalesAtendidos * 60000.0) / tiempoOperacion; // clientes/minuto
            double utilizacionCarritos = ((TOTAL_CARRITOS - carritos.availablePermits()) * 100.0) / TOTAL_CARRITOS;
            double utilizacionCajas = ((TOTAL_CAJAS - cajas.availablePermits()) * 100.0) / TOTAL_CAJAS;
            
            // Eficiencia basada en balance entre utilización y throughput
            double eficiencia = (utilizacionCarritos + utilizacionCajas) / 2.0;
            
            return new double[]{throughput, utilizacionCarritos, utilizacionCajas, eficiencia};
        }
    }
    
    /**
     * Obtiene información resumida del supermercado
     * 
     * @return String con información resumida
     */
    public String getInfoResumida() {
        synchronized(this) {
            return String.format("Supermercado[Atendidos:%d, Comprando:%d, Pagando:%d, Carritos:%d/%d, Cajas:%d/%d]", 
                               clientesTotalesAtendidos, clientesActualmenteComprando, clientesActualmentePagando,
                               carritos.availablePermits(), TOTAL_CARRITOS,
                               cajas.availablePermits(), TOTAL_CAJAS);
        }
    }
    
    /**
     * Método toString para representación del supermercado
     * 
     * @return Representación string del supermercado
     */
    @Override
    public String toString() {
        return String.format("Supermercado[Carritos:%d/%d, Cajas:%d/%d, Atendidos:%d]", 
                           carritos.availablePermits(), TOTAL_CARRITOS,
                           cajas.availablePermits(), TOTAL_CAJAS,
                           clientesTotalesAtendidos);
    }
}
