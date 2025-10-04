package tp5.actividad1.panaderia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase MostradorRunnable que representa el buffer compartido donde se almacenan
 * los productos de la panadería (Bizcochos y Facturas).
 * Versión mejorada del TP4 usando mejor encapsulación para trabajar con Runnable.
 */
public class MostradorRunnable {
    private int bizcochos;
    private int facturas;
    private int totalBizcochosProcidos;
    private int totalFacturasProducidas;
    private int totalVentas;
    private int clientesEsperando;
    private final DateTimeFormatter timeFormatter;
    
    /**
     * Constructor del MostradorRunnable
     */
    public MostradorRunnable() {
        this.bizcochos = 0;
        this.facturas = 0;
        this.totalBizcochosProcidos = 0;
        this.totalFacturasProducidas = 0;
        this.totalVentas = 0;
        this.clientesEsperando = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    /**
     * Método sincronizado para agregar un bizcocho al mostrador
     * @param productorId ID del productor para logging
     */
    public synchronized void agregarBizcocho(int productorId) {
        bizcochos++;
        totalBizcochosProcidos++;
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno-Bizcochos-" + productorId + 
                         ": +1 Bizcocho (B:" + bizcochos + ", F:" + facturas + ")");
        
        // Notificar a los clientes que hay nuevos productos
        notifyAll();
    }
    
    /**
     * Método sincronizado para agregar una factura al mostrador
     * @param productorId ID del productor para logging
     */
    public synchronized void agregarFactura(int productorId) {
        facturas++;
        totalFacturasProducidas++;
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Horno-Facturas-" + productorId + 
                         ": +1 Factura (B:" + bizcochos + ", F:" + facturas + ")");
        
        // Notificar a los clientes que hay nuevos productos
        notifyAll();
    }
    
    /**
     * Método sincronizado para que un cliente compre productos
     * Cada cliente necesita 1 bizcocho y 1 factura
     * @param clienteId ID del cliente
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public synchronized void comprarProductos(int clienteId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Verificar si hay productos suficientes
        while (bizcochos == 0 || facturas == 0) {
            clientesEsperando++;
            System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                             " esperando productos (B:" + bizcochos + ", F:" + facturas + 
                             ") - Clientes esperando: " + clientesEsperando);
            
            if (bizcochos == 0 && facturas == 0) {
                System.out.println("[" + tiempo + "] ¡MOSTRADOR VACÍO! Cliente-" + clienteId + " debe esperar");
            }
            
            wait(); // Esperar hasta que lleguen productos
            clientesEsperando--;
            tiempo = LocalTime.now().format(timeFormatter); // Actualizar tiempo después de esperar
        }
        
        // Realizar la compra
        bizcochos--;
        facturas--;
        totalVentas++;
        
        System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                         " comprando (B:" + (bizcochos + 1) + ", F:" + (facturas + 1) + " disponibles)");
        System.out.println("[" + tiempo + "] Cliente-" + clienteId + 
                         " compró 1 Bizcocho + 1 Factura");
        System.out.println("[" + tiempo + "] Mostrador actualizado (B:" + bizcochos + ", F:" + facturas + ")");
    }
    
    /**
     * Obtiene el stock actual de bizcochos
     * @return Número de bizcochos en el mostrador
     */
    public synchronized int getBizcochos() {
        return bizcochos;
    }
    
    /**
     * Obtiene el stock actual de facturas
     * @return Número de facturas en el mostrador
     */
    public synchronized int getFacturas() {
        return facturas;
    }
    
    /**
     * Obtiene el total de bizcochos producidos
     * @return Total de bizcochos producidos desde el inicio
     */
    public synchronized int getTotalBizcochosProcidos() {
        return totalBizcochosProcidos;
    }
    
    /**
     * Obtiene el total de facturas producidas
     * @return Total de facturas producidas desde el inicio
     */
    public synchronized int getTotalFacturasProducidas() {
        return totalFacturasProducidas;
    }
    
    /**
     * Obtiene el total de ventas realizadas
     * @return Número total de ventas (clientes atendidos)
     */
    public synchronized int getTotalVentas() {
        return totalVentas;
    }
    
    /**
     * Obtiene el número de clientes esperando
     * @return Número de clientes actualmente esperando
     */
    public synchronized int getClientesEsperando() {
        return clientesEsperando;
    }
    
    /**
     * Muestra el estado actual del mostrador
     */
    public synchronized void mostrarEstado() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] === ESTADO MOSTRADOR (Runnable) ===");
        System.out.println("Stock actual: B:" + bizcochos + ", F:" + facturas);
        System.out.println("Producción total: B:" + totalBizcochosProcidos + ", F:" + totalFacturasProducidas);
        System.out.println("Ventas totales: " + totalVentas);
        System.out.println("Clientes esperando: " + clientesEsperando);
        
        if (bizcochos == 0 && facturas == 0) {
            System.out.println("¡MOSTRADOR VACÍO!");
        } else if (bizcochos == 0) {
            System.out.println("¡SIN BIZCOCHOS!");
        } else if (facturas == 0) {
            System.out.println("¡SIN FACTURAS!");
        }
        System.out.println("================================");
    }
    
    /**
     * Obtiene información resumida del mostrador
     * @return String con información del estado actual
     */
    public synchronized String getInfoResumida() {
        return String.format("Mostrador[B:%d, F:%d, Ventas:%d, Esperando:%d]", 
                           bizcochos, facturas, totalVentas, clientesEsperando);
    }
}
