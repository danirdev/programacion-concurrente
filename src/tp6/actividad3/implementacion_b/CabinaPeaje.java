package tp6.actividad3.implementacion_b;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Clase CabinaPeaje que representa una cabina individual de peaje.
 * Permite identificar específicamente qué cabina atiende a cada cliente.
 * Implementación B: Con individualización de cabinas.
 */
public class CabinaPeaje {
    
    private final int numeroCabina;
    private final Random random;
    private final DateTimeFormatter timeFormatter;
    
    // Estado de la cabina
    private boolean disponible;
    private boolean operativa; // Para manejar la cabina que está "en el baño"
    private int clientesAtendidos;
    private long tiempoTotalAtencion;
    private long tiempoInicioOperacion;
    
    // Configuración
    private static final int TIEMPO_MIN_ATENCION = 1000; // 1 segundo
    private static final int TIEMPO_MAX_ATENCION = 3000; // 3 segundos
    
    /**
     * Constructor de CabinaPeaje
     * 
     * @param numeroCabina Número de la cabina (1, 2, o 3)
     * @param operativaInicial Si la cabina está operativa desde el inicio
     */
    public CabinaPeaje(int numeroCabina, boolean operativaInicial) {
        this.numeroCabina = numeroCabina;
        this.random = new Random();
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.disponible = operativaInicial;
        this.operativa = operativaInicial;
        this.clientesAtendidos = 0;
        this.tiempoTotalAtencion = 0;
        this.tiempoInicioOperacion = System.currentTimeMillis();
        
        String tiempo = LocalTime.now().format(timeFormatter);
        String estado = operativaInicial ? "DISPONIBLE" : "CERRADA (empleado en el baño)";
        System.out.println("[" + tiempo + "] 🏢 Cabina-" + numeroCabina + " inicializada: " + estado);
    }
    
    /**
     * Método para atender a un cliente en esta cabina específica.
     * 
     * @param numeroCliente Número del cliente a atender
     * @throws InterruptedException Si el hilo es interrumpido
     * @throws IllegalStateException Si la cabina no está disponible
     */
    public synchronized void atenderCliente(int numeroCliente) throws InterruptedException {
        if (!disponible || !operativa) {
            throw new IllegalStateException("Cabina-" + numeroCabina + " no está disponible para atender");
        }
        
        // Marcar cabina como ocupada
        disponible = false;
        
        try {
            // Calcular tiempo de atención aleatorio (1-3 segundos)
            int tiempoAtencion = TIEMPO_MIN_ATENCION + 
                               random.nextInt(TIEMPO_MAX_ATENCION - TIEMPO_MIN_ATENCION + 1);
            
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🏪 Cabina-" + numeroCabina + 
                             ": Atendiendo Cliente " + numeroCliente + 
                             " (" + String.format("%.1f", tiempoAtencion/1000.0) + "s)");
            
            // Simular tiempo de atención
            Thread.sleep(tiempoAtencion);
            
            // Actualizar estadísticas
            clientesAtendidos++;
            tiempoTotalAtencion += tiempoAtencion;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ Cabina-" + numeroCabina + 
                             ": Cliente " + numeroCliente + " terminó atención " +
                             "(Total atendidos: " + clientesAtendidos + ")");
            
        } finally {
            // Liberar cabina
            disponible = true;
        }
    }
    
    /**
     * Habilita la cabina (cuando el empleado regresa del baño)
     */
    public synchronized void habilitar() {
        if (!operativa) {
            operativa = true;
            disponible = true;
            
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🚽➡️🏢 Cabina-" + numeroCabina + 
                             ": ¡Empleado regresó del baño! Cabina ahora DISPONIBLE");
        }
    }
    
    /**
     * Deshabilita la cabina (empleado va al baño)
     */
    public synchronized void deshabilitar() {
        operativa = false;
        disponible = false;
        
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🏢➡️🚽 Cabina-" + numeroCabina + 
                         ": Empleado fue al baño. Cabina CERRADA temporalmente");
    }
    
    /**
     * Verifica si la cabina está disponible para atender
     * 
     * @return true si está disponible y operativa, false en caso contrario
     */
    public synchronized boolean estaDisponible() {
        return disponible && operativa;
    }
    
    /**
     * Verifica si la cabina está operativa (no en el baño)
     * 
     * @return true si está operativa, false en caso contrario
     */
    public synchronized boolean estaOperativa() {
        return operativa;
    }
    
    /**
     * Verifica si la cabina está ocupada atendiendo
     * 
     * @return true si está ocupada, false en caso contrario
     */
    public synchronized boolean estaOcupada() {
        return operativa && !disponible;
    }
    
    /**
     * Obtiene el número de la cabina
     * 
     * @return Número de la cabina
     */
    public int getNumeroCabina() {
        return numeroCabina;
    }
    
    /**
     * Obtiene el número de clientes atendidos
     * 
     * @return Número de clientes atendidos
     */
    public synchronized int getClientesAtendidos() {
        return clientesAtendidos;
    }
    
    /**
     * Obtiene el tiempo total de atención acumulado
     * 
     * @return Tiempo total en milisegundos
     */
    public synchronized long getTiempoTotalAtencion() {
        return tiempoTotalAtencion;
    }
    
    /**
     * Calcula el tiempo promedio de atención por cliente
     * 
     * @return Tiempo promedio en milisegundos
     */
    public synchronized double getTiempoPromedioAtencion() {
        if (clientesAtendidos == 0) {
            return 0.0;
        }
        return tiempoTotalAtencion / (double) clientesAtendidos;
    }
    
    /**
     * Calcula la utilización de la cabina (tiempo ocupada vs tiempo total)
     * 
     * @return Porcentaje de utilización (0-100)
     */
    public synchronized double calcularUtilizacion() {
        long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
        
        if (tiempoOperacion == 0 || !operativa) {
            return 0.0;
        }
        
        return (tiempoTotalAtencion * 100.0) / tiempoOperacion;
    }
    
    /**
     * Calcula el throughput de la cabina (clientes por minuto)
     * 
     * @return Throughput en clientes/minuto
     */
    public synchronized double calcularThroughput() {
        long tiempoOperacion = System.currentTimeMillis() - tiempoInicioOperacion;
        
        if (tiempoOperacion == 0) {
            return 0.0;
        }
        
        return (clientesAtendidos * 60000.0) / tiempoOperacion;
    }
    
    /**
     * Obtiene estadísticas detalladas de la cabina
     * 
     * @return Array con [clientesAtendidos, tiempoTotalAtencion, tiempoPromedioAtencion, utilizacion, throughput]
     */
    public synchronized double[] getEstadisticasDetalladas() {
        return new double[]{
            clientesAtendidos,
            tiempoTotalAtencion,
            getTiempoPromedioAtencion(),
            calcularUtilizacion(),
            calcularThroughput()
        };
    }
    
    /**
     * Muestra el estado actual de la cabina
     */
    public synchronized void mostrarEstado() {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("\n[" + tiempo + "] === ESTADO CABINA-" + numeroCabina + " ===");
        System.out.println("Operativa: " + (operativa ? "✅ SÍ" : "❌ NO (en el baño)"));
        System.out.println("Disponible: " + (disponible ? "✅ SÍ" : "🔴 OCUPADA"));
        System.out.println("Clientes atendidos: " + clientesAtendidos);
        System.out.println("Tiempo total atención: " + String.format("%.1f", tiempoTotalAtencion/1000.0) + "s");
        
        if (clientesAtendidos > 0) {
            System.out.println("Tiempo promedio: " + String.format("%.1f", getTiempoPromedioAtencion()) + "ms");
            System.out.println("Utilización: " + String.format("%.1f%%", calcularUtilizacion()));
            System.out.println("Throughput: " + String.format("%.1f", calcularThroughput()) + " clientes/min");
        }
        
        System.out.println("Estado actual: " + getEstadoTexto());
        System.out.println("=".repeat(25 + String.valueOf(numeroCabina).length()));
    }
    
    /**
     * Obtiene el estado actual como texto
     * 
     * @return Estado actual de la cabina
     */
    public synchronized String getEstadoTexto() {
        if (!operativa) {
            return "🚽 EN EL BAÑO";
        } else if (!disponible) {
            return "🔴 OCUPADA";
        } else {
            return "🟢 DISPONIBLE";
        }
    }
    
    /**
     * Obtiene información resumida de la cabina
     * 
     * @return String con información resumida
     */
    public synchronized String getInfoResumida() {
        return String.format("Cabina-%d[%s, Atendidos:%d, Util:%.1f%%]", 
                           numeroCabina, getEstadoTexto(), clientesAtendidos, calcularUtilizacion());
    }
    
    /**
     * Compara el rendimiento con otra cabina
     * 
     * @param otraCabina Cabina a comparar
     * @return String con comparación de rendimiento
     */
    public synchronized String compararCon(CabinaPeaje otraCabina) {
        double miThroughput = calcularThroughput();
        double otroThroughput = otraCabina.calcularThroughput();
        
        if (miThroughput > otroThroughput * 1.1) {
            return "🏆 Cabina-" + numeroCabina + " supera a Cabina-" + otraCabina.getNumeroCabina();
        } else if (otroThroughput > miThroughput * 1.1) {
            return "📉 Cabina-" + numeroCabina + " por debajo de Cabina-" + otraCabina.getNumeroCabina();
        } else {
            return "⚖️ Cabina-" + numeroCabina + " similar a Cabina-" + otraCabina.getNumeroCabina();
        }
    }
    
    /**
     * Método toString para representación de la cabina
     * 
     * @return Representación string de la cabina
     */
    @Override
    public String toString() {
        return String.format("CabinaPeaje[Num:%d, Operativa:%s, Disponible:%s, Atendidos:%d]", 
                           numeroCabina, operativa, disponible, clientesAtendidos);
    }
}
