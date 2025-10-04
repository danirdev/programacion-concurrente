package tp6.actividad3.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase EstacionPeajeSimple - Implementación A
 * Estación de peaje que NO individualiza cada cabina.
 * Utiliza un semáforo simple para controlar el acceso a las cabinas disponibles.
 */
public class EstacionPeajeSimple {
    
    // Semáforo para controlar cabinas disponibles (inicialmente 2, luego 3)
    private final Semaphore cabinasDisponibles;
    private final Random random;
    private final DateTimeFormatter timeFormatter;
    
    // Estadísticas del peaje
    private int clientesAtendidos = 0;
    private int clientesEsperando = 0;
    private long tiempoInicioOperacion;
    private boolean cabina3Disponible = false;
    
    // Configuración
    private static final int TIEMPO_MIN_ATENCION = 1000; // 1 segundo
    private static final int TIEMPO_MAX_ATENCION = 3000; // 3 segundos
    private static final int TIEMPO_CABINA3_CERRADA = 15000; // 15 segundos
    
    /**
     * Constructor de EstacionPeajeSimple
     */
    public EstacionPeajeSimple() {
        // Inicialmente solo 2 cabinas disponibles (cabina 3 cerrada)
        this.cabinasDisponibles = new Semaphore(2, true); // Fair para FIFO
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.tiempoInicioOperacion = System.currentTimeMillis();
        
        // Programar apertura de cabina 3 después de 15 segundos
        programarAperturaCabina3();
        
        System.out.println("✅ Estación de Peaje Simple inicializada");
        System.out.println("   Cabinas inicialmente disponibles: 2");
        System.out.println("   Cabina 3: Cerrada (empleado en el baño)");
        System.out.println("   Tiempo de atención: " + TIEMPO_MIN_ATENCION/1000 + "-" + TIEMPO_MAX_ATENCION/1000 + " segundos");
        System.out.println("   Cabina 3 disponible en: " + TIEMPO_CABINA3_CERRADA/1000 + " segundos");
    }
    
    /**
     * Método principal para atender a un cliente.
     * No individualiza la cabina que atiende.
     * 
     * @param numeroCliente Número del cliente a atender
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void atenderCliente(int numeroCliente) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Incrementar contador de clientes esperando
        synchronized(this) {
            clientesEsperando++;
        }
        
        System.out.println("[" + tiempo + "] Cliente " + numeroCliente + 
                          " llegó a la estación (esperando: " + clientesEsperando + ")");
        
        // Esperar por una cabina disponible
        cabinasDisponibles.acquire();
        
        synchronized(this) {
            clientesEsperando--;
        }
        
        try {
            // Calcular tiempo de atención aleatorio (1-3 segundos)
            int tiempoAtencion = TIEMPO_MIN_ATENCION + 
                               random.nextInt(TIEMPO_MAX_ATENCION - TIEMPO_MIN_ATENCION + 1);
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ Cliente " + numeroCliente + 
                             " siendo atendido (" + String.format("%.1f", tiempoAtencion/1000.0) + "s)" +
                             " - Cabinas ocupadas: " + (getTotalCabinas() - cabinasDisponibles.availablePermits()) + 
                             "/" + getTotalCabinas());
            
            // Simular tiempo de atención
            Thread.sleep(tiempoAtencion);
            
            // Cliente termina atención
            synchronized(this) {
                clientesAtendidos++;
            }
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🏁 Cliente " + numeroCliente + 
                             " terminó atención y pagó peaje (Total atendidos: " + 
                             clientesAtendidos + ")");
            
        } finally {
            // Liberar cabina
            cabinasDisponibles.release();
            
            // Mostrar estado si hay clientes esperando
            if (clientesEsperando > 0) {
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] 📢 Cabina liberada - " + 
                                 clientesEsperando + " clientes esperando");
            }
        }
    }
    
    /**
     * Programa la apertura de la cabina 3 después de 15 segundos
     */
    private void programarAperturaCabina3() {
        Thread hiloAperturaCabina3 = new Thread(() -> {
            try {
                Thread.sleep(TIEMPO_CABINA3_CERRADA);
                
                synchronized(this) {
                    if (!cabina3Disponible) {
                        cabina3Disponible = true;
                        cabinasDisponibles.release(); // Agregar un permit más
                        
                        String tiempo = LocalTime.now().format(timeFormatter);
                        System.out.println("\n" + "=".repeat(60));
                        System.out.println("[" + tiempo + "] 🚽➡️🏢 ¡CABINA 3 REGRESÓ DEL BAÑO!");
                        System.out.println("   Empleado regresó después de 15 segundos");
                        System.out.println("   Cabinas ahora disponibles: 3");
                        System.out.println("   Throughput mejorado: +50%");
                        
                        if (clientesEsperando > 0) {
                            System.out.println("   ⚡ Procesando cola de " + clientesEsperando + " clientes esperando");
                        }
                        System.out.println("=".repeat(60) + "\n");
                    }
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Apertura-Cabina3");
        
        hiloAperturaCabina3.setDaemon(true);
        hiloAperturaCabina3.start();
    }
    
    /**
     * Obtiene el número total de cabinas actualmente disponibles
     * 
     * @return Número total de cabinas
     */
    public int getTotalCabinas() {
        return cabina3Disponible ? 3 : 2;
    }
    
    /**
     * Obtiene estadísticas actuales del peaje
     * 
     * @return Array con [clientesAtendidos, clientesEsperando, cabinasDisponibles, cabina3Disponible]
     */
    public int[] getEstadisticas() {
        synchronized(this) {
            return new int[]{
                clientesAtendidos, 
                clientesEsperando, 
                cabinasDisponibles.availablePermits(),
                cabina3Disponible ? 1 : 0
            };
        }
    }
    
    /**
     * Muestra el estado actual del peaje
     */
    public void mostrarEstado() {
        synchronized(this) {
            String tiempo = LocalTime.now().format(timeFormatter);
            long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
            
            System.out.println("\n[" + tiempo + "] === ESTADO ESTACIÓN DE PEAJE SIMPLE ===");
            System.out.println("Tiempo de operación: " + String.format("%.1f", tiempoOperacion/1000.0) + "s");
            System.out.println("Clientes atendidos: " + clientesAtendidos);
            System.out.println("Clientes esperando: " + clientesEsperando);
            System.out.println("Cabinas disponibles: " + cabinasDisponibles.availablePermits() + 
                             "/" + getTotalCabinas());
            System.out.println("Cabina 3 estado: " + (cabina3Disponible ? "✅ DISPONIBLE" : "❌ CERRADA"));
            
            if (clientesAtendidos > 0) {
                double throughput = (clientesAtendidos * 60.0) / (tiempoOperacion / 1000.0);
                System.out.println("Throughput: " + String.format("%.1f", throughput) + " clientes/minuto");
            }
            
            System.out.println("Hilos esperando cabina: " + cabinasDisponibles.getQueueLength());
            System.out.println("===============================================\n");
        }
    }
    
    /**
     * Verifica si la cabina 3 está disponible
     * 
     * @return true si la cabina 3 está disponible, false en caso contrario
     */
    public boolean isCabina3Disponible() {
        synchronized(this) {
            return cabina3Disponible;
        }
    }
    
    /**
     * Obtiene el tiempo transcurrido desde el inicio de operación
     * 
     * @return Tiempo en milisegundos
     */
    public long getTiempoOperacion() {
        return System.currentTimeMillis() - tiempoInicioOperacion;
    }
    
    /**
     * Calcula estadísticas de rendimiento
     * 
     * @return Array con [throughput, tiempoPromedioPorCliente, eficiencia]
     */
    public double[] calcularRendimiento() {
        synchronized(this) {
            long tiempoOperacion = getTiempoOperacion();
            
            if (tiempoOperacion == 0 || clientesAtendidos == 0) {
                return new double[]{0.0, 0.0, 0.0};
            }
            
            double throughput = (clientesAtendidos * 60000.0) / tiempoOperacion; // clientes/minuto
            double tiempoPromedio = tiempoOperacion / (double) clientesAtendidos; // ms/cliente
            
            // Eficiencia basada en utilización de cabinas
            int cabinasActuales = getTotalCabinas();
            double tiempoTeoricoMinimo = (clientesAtendidos * TIEMPO_MIN_ATENCION) / (double) cabinasActuales;
            double eficiencia = (tiempoTeoricoMinimo / tiempoOperacion) * 100.0;
            
            return new double[]{throughput, tiempoPromedio, Math.min(eficiencia, 100.0)};
        }
    }
    
    /**
     * Obtiene información resumida del peaje
     * 
     * @return String con información resumida
     */
    public String getInfoResumida() {
        synchronized(this) {
            return String.format("PeajeSimple[Atendidos:%d, Esperando:%d, Cabinas:%d/%d, C3:%s]", 
                               clientesAtendidos, clientesEsperando, 
                               cabinasDisponibles.availablePermits(), getTotalCabinas(),
                               cabina3Disponible ? "✅" : "❌");
        }
    }
}
