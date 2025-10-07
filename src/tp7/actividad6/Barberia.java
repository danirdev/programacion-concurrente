package tp7.actividad6;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 💈 Barberia - Estado compartido de la barbería
 * 
 * Esta clase representa el estado compartido de la barbería, incluyendo
 * las sillas de espera y los semáforos para sincronización entre el
 * barbero y los clientes.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Barberia {
    
    // 🏷️ Identificación de la barbería
    private final String nombre;
    
    // 🪑 Configuración de sillas
    private final int capacidadSillas;
    private int sillasLibres;
    
    // 🚦 Semáforos para sincronización
    private final Semaphore clientes;   // Barbero espera por clientes (duerme aquí)
    private final Semaphore barbero;    // Cliente espera que barbero esté listo
    private final Semaphore mutex;      // Protege acceso a sillasLibres
    
    // 📊 Estadísticas
    private final AtomicInteger clientesAtendidos;
    private final AtomicInteger clientesRechazados;
    private final AtomicInteger clientesEnEspera;
    private final AtomicInteger totalLlegadas;
    
    // ⏱️ Tiempos
    private long tiempoApertura;
    private long tiempoBarberoOcupado;
    private long tiempoBaberoDormido;
    
    // 🔍 Estado actual
    private volatile String estadoBarbero; // "DURMIENDO", "CORTANDO", "ESPERANDO"
    private volatile boolean abierta;
    
    /**
     * 🏗️ Constructor de la Barbería
     * 
     * @param nombre Nombre de la barbería
     * @param capacidadSillas Número de sillas en sala de espera
     */
    public Barberia(String nombre, int capacidadSillas) {
        if (capacidadSillas <= 0) {
            throw new IllegalArgumentException("Capacidad de sillas debe ser mayor a 0");
        }
        
        this.nombre = nombre;
        this.capacidadSillas = capacidadSillas;
        this.sillasLibres = capacidadSillas;
        
        // 🚦 Inicializar semáforos
        this.clientes = new Semaphore(0, true);  // Barbero espera clientes
        this.barbero = new Semaphore(0, true);   // Cliente espera barbero
        this.mutex = new Semaphore(1, true);     // Mutex para sillasLibres
        
        // 📊 Inicializar estadísticas
        this.clientesAtendidos = new AtomicInteger(0);
        this.clientesRechazados = new AtomicInteger(0);
        this.clientesEnEspera = new AtomicInteger(0);
        this.totalLlegadas = new AtomicInteger(0);
        
        this.tiempoApertura = System.currentTimeMillis();
        this.tiempoBarberoOcupado = 0;
        this.tiempoBaberoDormido = 0;
        
        this.estadoBarbero = "DURMIENDO";
        this.abierta = true;
        
        System.out.printf("💈 Barbería '%s' abierta - Sillas disponibles: %d%n", 
                         nombre, capacidadSillas);
    }
    
    /**
     * 🚶 Cliente intenta entrar a la barbería
     * 
     * @param clienteId ID del cliente
     * @return true si pudo entrar, false si fue rechazado
     * @throws InterruptedException Si el thread es interrumpido
     */
    public boolean clienteIntentaEntrar(String clienteId) throws InterruptedException {
        totalLlegadas.incrementAndGet();
        System.out.printf("[%s] 🚶 Llegó a la barbería%n", clienteId);
        
        // 🔒 Verificar si hay sillas disponibles
        mutex.acquire();
        
        if (sillasLibres > 0) {
            // ✅ Hay espacio - Cliente entra y se sienta
            sillasLibres--;
            clientesEnEspera.incrementAndGet();
            
            System.out.printf("[%s] 🪑 Hay sillas disponibles (%d/%d)%n", 
                             clienteId, sillasLibres, capacidadSillas);
            
            // 🔔 Notificar al barbero (despertarlo o incrementar cola)
            System.out.printf("[%s] 🔔 Notificando al barbero%n", clienteId);
            clientes.release();
            
            mutex.release();
            
            // ⏳ Esperar a que el barbero esté listo
            System.out.printf("[%s] ⏳ Esperando que barbero esté listo%n", clienteId);
            barbero.acquire();
            
            System.out.printf("[%s] ✅ Barbero disponible, entrando a la silla%n", clienteId);
            return true;
            
        } else {
            // ❌ No hay espacio - Cliente se retira
            clientesRechazados.incrementAndGet();
            System.out.printf("[%s] ❌ No hay sillas disponibles (0/%d)%n", 
                             clienteId, capacidadSillas);
            System.out.printf("[%s] 🚪 Retirándose de la barbería%n", clienteId);
            
            mutex.release();
            return false;
        }
    }
    
    /**
     * 😴 Barbero espera por un cliente
     * 
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void barberoEsperaCliente() throws InterruptedException {
        estadoBarbero = "DURMIENDO";
        long tiempoInicioDormido = System.currentTimeMillis();
        
        System.out.println("😴 Barbero: Durmiendo... (esperando clientes)");
        
        // 😴 Dormir hasta que llegue un cliente
        clientes.acquire();
        
        long tiempoDormido = System.currentTimeMillis() - tiempoInicioDormido;
        tiempoBaberoDormido += tiempoDormido;
        
        System.out.println("😴→⏳ Barbero: Despertando...");
        estadoBarbero = "ESPERANDO";
    }
    
    /**
     * ✂️ Barbero prepara para atender cliente
     * 
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void barberoPrepararAtencion() throws InterruptedException {
        // 🔒 Tomar cliente de la sala de espera
        mutex.acquire();
        
        sillasLibres++;
        clientesEnEspera.decrementAndGet();
        
        System.out.printf("⏳ Barbero: Cliente tomado de espera - Sillas libres: %d/%d%n", 
                         sillasLibres, capacidadSillas);
        
        // 🔔 Señalar que está listo para atender
        System.out.println("⏳ Barbero: Listo para atender");
        barbero.release();
        
        mutex.release();
        
        estadoBarbero = "CORTANDO";
    }
    
    /**
     * ✂️ Barbero corta el pelo (simular trabajo)
     * 
     * @param clienteId ID del cliente siendo atendido
     * @param tiempoCorte Tiempo de corte en milisegundos
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void barberoCortarPelo(String clienteId, int tiempoCorte) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        System.out.printf("✂️ Barbero: Cortando pelo al %s%n", clienteId);
        
        // ✂️ Simular corte de pelo
        Thread.sleep(tiempoCorte);
        
        long tiempoReal = System.currentTimeMillis() - tiempoInicio;
        tiempoBarberoOcupado += tiempoReal;
        
        clientesAtendidos.incrementAndGet();
        
        System.out.printf("✅ Barbero: Terminé de cortar pelo al %s (tiempo: %dms)%n", 
                         clienteId, tiempoReal);
    }
    
    /**
     * 👋 Cliente recibe corte de pelo
     * 
     * @param clienteId ID del cliente
     * @param tiempoCorte Tiempo de corte esperado
     * @throws InterruptedException Si el thread es interrumpido
     */
    public void clienteRecibeCorte(String clienteId, int tiempoCorte) throws InterruptedException {
        System.out.printf("[%s] ✂️ Recibiendo corte de pelo...%n", clienteId);
        
        // Esperar mientras el barbero corta
        Thread.sleep(tiempoCorte);
        
        System.out.printf("[%s] ✅ Corte terminado%n", clienteId);
        System.out.printf("[%s] 👋 Saliendo de la barbería%n", clienteId);
    }
    
    /**
     * 🚪 Cerrar la barbería
     */
    public void cerrar() {
        abierta = false;
        System.out.printf("🚪 Barbería '%s' cerrada%n", nombre);
    }
    
    /**
     * 📊 Obtener estadísticas de la barbería
     * 
     * @return String con estadísticas detalladas
     */
    public String getEstadisticas() {
        long tiempoTotal = System.currentTimeMillis() - tiempoApertura;
        double tiempoTotalSegundos = tiempoTotal / 1000.0;
        
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 📊 ESTADÍSTICAS BARBERÍA '%s' ===\n", nombre));
        stats.append(String.format("🪑 Capacidad sillas: %d%n", capacidadSillas));
        stats.append(String.format("🚶 Total llegadas: %d%n", totalLlegadas.get()));
        stats.append(String.format("✅ Clientes atendidos: %d%n", clientesAtendidos.get()));
        stats.append(String.format("❌ Clientes rechazados: %d%n", clientesRechazados.get()));
        stats.append(String.format("⏳ Clientes en espera: %d%n", clientesEnEspera.get()));
        
        if (totalLlegadas.get() > 0) {
            double tasaAtencion = (clientesAtendidos.get() * 100.0) / totalLlegadas.get();
            double tasaRechazo = (clientesRechazados.get() * 100.0) / totalLlegadas.get();
            
            stats.append(String.format("📊 Tasa de atención: %.1f%%%n", tasaAtencion));
            stats.append(String.format("📊 Tasa de rechazo: %.1f%%%n", tasaRechazo));
        }
        
        if (tiempoTotalSegundos > 0) {
            double porcentajeOcupado = (tiempoBarberoOcupado * 100.0) / tiempoTotal;
            double porcentajeDormido = (tiempoBaberoDormido * 100.0) / tiempoTotal;
            
            stats.append(String.format("⏱️ Tiempo total: %.2fs%n", tiempoTotalSegundos));
            stats.append(String.format("✂️ Barbero ocupado: %.1f%%%n", porcentajeOcupado));
            stats.append(String.format("😴 Barbero dormido: %.1f%%%n", porcentajeDormido));
        }
        
        stats.append(String.format("📊 Estado actual: %s%n", estadoBarbero));
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad de la barbería
     * 
     * @return true si está íntegra
     */
    public boolean verificarIntegridad() {
        boolean sillasValidas = sillasLibres >= 0 && sillasLibres <= capacidadSillas;
        boolean estadisticasValidas = clientesAtendidos.get() >= 0 && 
                                     clientesRechazados.get() >= 0 &&
                                     totalLlegadas.get() == (clientesAtendidos.get() + 
                                                            clientesRechazados.get() + 
                                                            clientesEnEspera.get());
        
        boolean integra = sillasValidas && estadisticasValidas;
        
        if (!integra) {
            System.err.println("❌ INTEGRIDAD BARBERÍA COMPROMETIDA:");
            System.err.printf("   Sillas válidas: %s (%d ∈ [0, %d])%n", 
                             sillasValidas, sillasLibres, capacidadSillas);
            System.err.printf("   Estadísticas válidas: %s%n", estadisticasValidas);
        }
        
        return integra;
    }
    
    /**
     * 🚦 Información de semáforos
     * 
     * @return String con información
     */
    public String getInfoSemaforos() {
        return String.format("🚦 Semáforos - clientes: %d, barbero: %d, mutex: %d | Colas: %d, %d, %d", 
                           clientes.availablePermits(),
                           barbero.availablePermits(),
                           mutex.availablePermits(),
                           clientes.getQueueLength(),
                           barbero.getQueueLength(),
                           mutex.getQueueLength());
    }
    
    // 🔧 Getters
    
    public String getNombre() { return nombre; }
    public int getCapacidadSillas() { return capacidadSillas; }
    public int getSillasLibres() { return sillasLibres; }
    public int getClientesAtendidos() { return clientesAtendidos.get(); }
    public int getClientesRechazados() { return clientesRechazados.get(); }
    public int getClientesEnEspera() { return clientesEnEspera.get(); }
    public int getTotalLlegadas() { return totalLlegadas.get(); }
    public String getEstadoBarbero() { return estadoBarbero; }
    public boolean isAbierta() { return abierta; }
    
    /**
     * 📝 Representación en string de la barbería
     * 
     * @return Información básica
     */
    @Override
    public String toString() {
        return String.format("Barberia{nombre='%s', sillas=%d/%d, atendidos=%d, rechazados=%d, estado=%s}", 
                           nombre, sillasLibres, capacidadSillas, clientesAtendidos.get(), 
                           clientesRechazados.get(), estadoBarbero);
    }
}
