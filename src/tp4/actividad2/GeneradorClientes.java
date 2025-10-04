package tp4.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase GeneradorClientes que hereda de Thread.
 * Se encarga de generar clientes que llegan a la panadería de forma continua.
 * Intervalo de llegada: 800-1500ms entre clientes.
 */
public class GeneradorClientes extends Thread {
    private Mostrador mostrador;
    private Random random;
    private int contadorClientes;
    private final int TIEMPO_MIN_LLEGADA = 800;   // 800ms mínimo
    private final int TIEMPO_MAX_LLEGADA = 1500;  // 1500ms máximo
    private final DateTimeFormatter timeFormatter;
    private List<Cliente> clientesGenerados;
    
    /**
     * Constructor del GeneradorClientes
     * @param mostrador Mostrador compartido de la panadería
     */
    public GeneradorClientes(Mostrador mostrador) {
        this.mostrador = mostrador;
        this.random = new Random();
        this.contadorClientes = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.clientesGenerados = new ArrayList<>();
        this.setName("Generador-Clientes");
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Generador de Clientes iniciado (Llegadas cada " + 
                          TIEMPO_MIN_LLEGADA + "-" + TIEMPO_MAX_LLEGADA + "ms)");
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Calcular tiempo hasta la llegada del próximo cliente
                int tiempoLlegada = TIEMPO_MIN_LLEGADA + random.nextInt(TIEMPO_MAX_LLEGADA - TIEMPO_MIN_LLEGADA + 1);
                
                // Esperar hasta que llegue el próximo cliente
                Thread.sleep(tiempoLlegada);
                
                // Generar nuevo cliente
                contadorClientes++;
                Cliente nuevoCliente = new Cliente(mostrador, contadorClientes);
                clientesGenerados.add(nuevoCliente);
                
                // Iniciar el hilo del cliente
                nuevoCliente.start();
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] >>> NUEVO CLIENTE: Cliente-" + contadorClientes + 
                                 " generado (próximo en " + tiempoLlegada + "ms)");
            }
        } catch (InterruptedException e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Generador de Clientes detenido. Total generados: " + 
                             contadorClientes);
            
            // Interrumpir todos los clientes activos
            for (Cliente cliente : clientesGenerados) {
                if (cliente.isAlive()) {
                    cliente.interrupt();
                }
            }
            
            Thread.currentThread().interrupt();
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
     * Espera a que todos los clientes terminen sus procesos
     */
    public void esperarClientes() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Esperando que terminen todos los clientes...");
        
        for (Cliente cliente : clientesGenerados) {
            try {
                cliente.join(5000); // Esperar máximo 5 segundos por cliente
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
     * @return Array con [clientesAtendidos, clientesNoAtendidos]
     */
    public int[] getEstadisticasClientes() {
        int atendidos = 0;
        int noAtendidos = 0;
        
        for (Cliente cliente : clientesGenerados) {
            if (cliente.isCompraExitosa()) {
                atendidos++;
            } else {
                noAtendidos++;
            }
        }
        
        return new int[]{atendidos, noAtendidos};
    }
}
