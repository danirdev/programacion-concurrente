package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase GeneradorClientes que implementa Runnable.
 * Se encarga de generar clientes que llegan a la panadería de forma continua.
 * Intervalo de llegada: 800-1500ms entre clientes.
 * Versión mejorada del TP4 usando interfaz Runnable.
 */
public class GeneradorClientes implements Runnable {
    private final MostradorRunnable mostrador;
    private final Random random;
    private int contadorClientes;
    private final int TIEMPO_MIN_LLEGADA = 800;   // 800ms mínimo
    private final int TIEMPO_MAX_LLEGADA = 1500;  // 1500ms máximo
    private final DateTimeFormatter timeFormatter;
    private final List<Cliente> clientesGenerados;
    private final List<Thread> hilosClientes;
    private volatile boolean activo = true;
    
    /**
     * Constructor del GeneradorClientes
     * @param mostrador Mostrador compartido de la panadería
     */
    public GeneradorClientes(MostradorRunnable mostrador) {
        this.mostrador = mostrador;
        this.random = new Random();
        this.contadorClientes = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.clientesGenerados = new ArrayList<>();
        this.hilosClientes = new ArrayList<>();
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Generador de Clientes iniciado en hilo: " + nombreHilo + 
                          " (Llegadas cada " + TIEMPO_MIN_LLEGADA + "-" + TIEMPO_MAX_LLEGADA + "ms)");
        
        try {
            while (activo && !Thread.currentThread().isInterrupted()) {
                // Calcular tiempo hasta la llegada del próximo cliente
                int tiempoLlegada = TIEMPO_MIN_LLEGADA + random.nextInt(TIEMPO_MAX_LLEGADA - TIEMPO_MIN_LLEGADA + 1);
                
                // Esperar hasta que llegue el próximo cliente
                Thread.sleep(tiempoLlegada);
                
                if (activo) {
                    // Generar nuevo cliente
                    contadorClientes++;
                    Cliente nuevoCliente = new Cliente(mostrador, contadorClientes);
                    clientesGenerados.add(nuevoCliente);
                    
                    // Crear e iniciar el hilo del cliente usando Runnable
                    Thread hiloCliente = new Thread(nuevoCliente, "Hilo-Cliente-" + contadorClientes);
                    hilosClientes.add(hiloCliente);
                    hiloCliente.start();
                    
                    tiempo = LocalTime.now().format(timeFormatter);
                    System.out.println("[" + tiempo + "] >>> NUEVO CLIENTE: Cliente-" + contadorClientes + 
                                     " generado en hilo " + hiloCliente.getName());
                }
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador de Clientes interrumpido. Total generados: " + 
                             contadorClientes);
            Thread.currentThread().interrupt();
        } finally {
            detenerTodosLosClientes();
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador de Clientes finalizó su ejecución");
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
     * Detiene todos los clientes generados
     */
    private void detenerTodosLosClientes() {
        System.out.println("Deteniendo todos los clientes generados...");
        for (Cliente cliente : clientesGenerados) {
            cliente.detener();
        }
        
        // Interrumpir hilos de clientes que aún estén activos
        for (Thread hilo : hilosClientes) {
            if (hilo.isAlive()) {
                hilo.interrupt();
            }
        }
    }
    
    /**
     * Obtiene el número total de clientes generados
     * @return Número de clientes generados
     */
    public int getClientesGenerados() {
        return contadorClientes;
    }
    
    /**
     * Obtiene la lista de todos los clientes generados
     * @return Lista de clientes
     */
    public List<Cliente> getListaClientes() {
        return new ArrayList<>(clientesGenerados);
    }
    
    /**
     * Obtiene la lista de hilos de clientes
     * @return Lista de hilos
     */
    public List<Thread> getHilosClientes() {
        return new ArrayList<>(hilosClientes);
    }
    
    /**
     * Espera a que todos los clientes terminen sus procesos
     */
    public void esperarClientes() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Esperando que terminen todos los clientes...");
        
        for (Thread hilo : hilosClientes) {
            try {
                hilo.join(3000); // Esperar máximo 3 segundos por cliente
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Todos los clientes han terminado");
    }
    
    /**
     * Obtiene estadísticas de los clientes
     * @return Array con [clientesAtendidos, clientesNoAtendidos, clientesActivos]
     */
    public int[] getEstadisticasClientes() {
        int atendidos = 0;
        int noAtendidos = 0;
        int activos = 0;
        
        for (Cliente cliente : clientesGenerados) {
            if (cliente.isCompraExitosa()) {
                atendidos++;
            } else if (cliente.estaActivo()) {
                activos++;
            } else {
                noAtendidos++;
            }
        }
        
        return new int[]{atendidos, noAtendidos, activos};
    }
    
    /**
     * Crea y inicia un hilo para este generador
     * @return El hilo creado
     */
    public Thread iniciarEnHilo() {
        Thread hilo = new Thread(this, "Generador-Clientes");
        hilo.start();
        return hilo;
    }
    
    /**
     * Obtiene información del estado del generador
     * @return String con información del generador
     */
    public String getInfo() {
        return String.format("GeneradorClientes[Generados:%d, Activo:%s, HilosActivos:%d]", 
                           contadorClientes, activo ? "Sí" : "No", 
                           (int) hilosClientes.stream().filter(Thread::isAlive).count());
    }
}
