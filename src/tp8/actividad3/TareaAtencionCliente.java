package tp8.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 💳 TareaAtencionCliente - Tarea de atención de un cliente en una caja
 * 
 * Esta clase implementa Runnable y simula la atención de un cliente
 * por parte de un cajero, con tiempo variable entre 1 y 3 segundos.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaAtencionCliente implements Runnable {
    
    // 👤 Cliente a atender
    private final Cliente cliente;
    
    // ⚙️ Configuración de tiempos
    private static final int TIEMPO_MIN_ATENCION = 1000; // 1 segundo
    private static final int TIEMPO_MAX_ATENCION = 3000; // 3 segundos
    
    // 🎲 Generador aleatorio
    private static final Random random = new Random();
    
    // 💳 Contador de cajas (para asignar número de caja)
    private static final AtomicInteger contadorCajas = new AtomicInteger(0);
    private static final ThreadLocal<Integer> numeroCaja = ThreadLocal.withInitial(() -> 
        contadorCajas.incrementAndGet()
    );
    
    // 📝 Formato de tiempo
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * 🏗️ Constructor de TareaAtencionCliente
     * 
     * @param cliente Cliente a atender
     */
    public TareaAtencionCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    /**
     * 🏃‍♂️ Método run - Ejecuta la atención del cliente
     */
    @Override
    public void run() {
        try {
            // 💳 Obtener número de caja
            int caja = numeroCaja.get();
            
            // 📍 Iniciar atención
            cliente.iniciarAtencion(caja);
            String horaInicio = LocalTime.now().format(FORMATTER);
            
            System.out.printf("[%s] 📍 [Caja %d] Cliente #%d: Iniciando atención...%n", 
                             horaInicio, caja, cliente.getNumeroCliente());
            
            // ⏱️ Simular tiempo de atención (1-3 segundos)
            int tiempoAtencion = TIEMPO_MIN_ATENCION + 
                                random.nextInt(TIEMPO_MAX_ATENCION - TIEMPO_MIN_ATENCION + 1);
            Thread.sleep(tiempoAtencion);
            
            // ✅ Finalizar atención
            cliente.finalizarAtencion();
            String horaFin = LocalTime.now().format(FORMATTER);
            
            System.out.printf("[%s] ✅ [Caja %d] Cliente #%d: Atención completada (%.2fs)%n", 
                             horaFin, caja, cliente.getNumeroCliente(), 
                             cliente.getTiempoAtencion() / 1000.0);
            
        } catch (InterruptedException e) {
            System.err.printf("❌ Atención de Cliente #%d interrumpida: %s%n", 
                             cliente.getNumeroCliente(), e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🔄 Resetear contador de cajas (para múltiples simulaciones)
     */
    public static void resetearContador() {
        contadorCajas.set(0);
    }
}
