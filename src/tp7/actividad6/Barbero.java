package tp7.actividad6;

import java.util.Random;

/**
 * 💈 Barbero - Thread que representa al barbero
 * 
 * Esta clase implementa el comportamiento del barbero que duerme cuando
 * no hay clientes, se despierta cuando llegan, y los atiende uno por uno.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Barbero extends Thread {
    
    // 🏷️ Identificación del barbero
    private final String barberoId;
    
    // 💈 Referencia a la barbería
    private final Barberia barberia;
    
    // ⚙️ Configuración
    private final int tiempoMinCorte;
    private final int tiempoMaxCorte;
    private final int numeroClientesObjetivo;
    
    // 🎲 Generador aleatorio
    private final Random random;
    
    // 📊 Estadísticas del barbero
    private int clientesAtendidosPorBarbero;
    private long tiempoTotalCortando;
    private long tiempoTotalDurmiendo;
    private boolean activo;
    
    /**
     * 🏗️ Constructor del Barbero
     * 
     * @param barberoId ID del barbero
     * @param barberia Referencia a la barbería
     * @param numeroClientesObjetivo Número de clientes a atender antes de cerrar
     * @param tiempoMinCorte Tiempo mínimo de corte (ms)
     * @param tiempoMaxCorte Tiempo máximo de corte (ms)
     */
    public Barbero(String barberoId, Barberia barberia, int numeroClientesObjetivo,
                   int tiempoMinCorte, int tiempoMaxCorte) {
        this.barberoId = barberoId;
        this.barberia = barberia;
        this.numeroClientesObjetivo = numeroClientesObjetivo;
        this.tiempoMinCorte = tiempoMinCorte;
        this.tiempoMaxCorte = tiempoMaxCorte;
        
        this.random = new Random();
        this.clientesAtendidosPorBarbero = 0;
        this.tiempoTotalCortando = 0;
        this.tiempoTotalDurmiendo = 0;
        this.activo = true;
        
        this.setName("Barbero-" + barberoId);
        
        System.out.printf("💈 Barbero '%s' listo para trabajar%n", barberoId);
    }
    
    /**
     * 🏃‍♂️ Método principal del thread barbero
     */
    @Override
    public void run() {
        System.out.printf("▶️ Barbero '%s' comenzando jornada%n", barberoId);
        
        long tiempoInicioJornada = System.currentTimeMillis();
        
        try {
            while (activo && clientesAtendidosPorBarbero < numeroClientesObjetivo && 
                   !Thread.currentThread().isInterrupted()) {
                
                // 😴 Esperar por un cliente (dormir si no hay)
                long tiempoAntesDormir = System.currentTimeMillis();
                barberia.barberoEsperaCliente();
                tiempoTotalDurmiendo += System.currentTimeMillis() - tiempoAntesDormir;
                
                // ⏳ Preparar para atender
                barberia.barberoPrepararAtencion();
                
                // ✂️ Cortar el pelo
                int tiempoCorte = tiempoMinCorte + random.nextInt(tiempoMaxCorte - tiempoMinCorte + 1);
                
                long tiempoAntesCortar = System.currentTimeMillis();
                String clienteId = "Cliente #" + (clientesAtendidosPorBarbero + 1);
                barberia.barberoCortarPelo(clienteId, tiempoCorte);
                tiempoTotalCortando += System.currentTimeMillis() - tiempoAntesCortar;
                
                clientesAtendidosPorBarbero++;
                
                // 📊 Mostrar progreso cada 5 clientes
                if (clientesAtendidosPorBarbero % 5 == 0) {
                    System.out.printf("📊 Barbero: He atendido %d/%d clientes%n", 
                                     clientesAtendidosPorBarbero, numeroClientesObjetivo);
                }
            }
            
        } catch (InterruptedException e) {
            System.out.printf("⚠️ Barbero '%s' interrumpido%n", barberoId);
            Thread.currentThread().interrupt();
        } finally {
            activo = false;
            long tiempoTotalJornada = System.currentTimeMillis() - tiempoInicioJornada;
            System.out.printf("🛑 Barbero '%s' terminando jornada - Tiempo total: %dms%n", 
                             barberoId, tiempoTotalJornada);
            mostrarEstadisticasBarbero();
        }
    }
    
    /**
     * 📊 Mostrar estadísticas del barbero
     */
    private void mostrarEstadisticasBarbero() {
        System.out.printf("\n=== 📊 ESTADÍSTICAS BARBERO '%s' ===\n", barberoId);
        System.out.printf("✂️ Clientes atendidos: %d%n", clientesAtendidosPorBarbero);
        System.out.printf("⏱️ Tiempo total cortando: %dms (%.2fs)%n", 
                         tiempoTotalCortando, tiempoTotalCortando / 1000.0);
        System.out.printf("😴 Tiempo total durmiendo: %dms (%.2fs)%n", 
                         tiempoTotalDurmiendo, tiempoTotalDurmiendo / 1000.0);
        
        if (clientesAtendidosPorBarbero > 0) {
            System.out.printf("📊 Tiempo promedio por corte: %.2fms%n", 
                             (double) tiempoTotalCortando / clientesAtendidosPorBarbero);
        }
        
        long tiempoTotal = tiempoTotalCortando + tiempoTotalDurmiendo;
        if (tiempoTotal > 0) {
            double utilizacion = (tiempoTotalCortando * 100.0) / tiempoTotal;
            System.out.printf("📈 Utilización del barbero: %.1f%%%n", utilizacion);
        }
    }
    
    /**
     * 🛑 Detener barbero de forma segura
     */
    public void detener() {
        activo = false;
        this.interrupt();
        System.out.printf("🔴 Barbero '%s' marcado para detener%n", barberoId);
    }
    
    /**
     * 📊 Obtener estadísticas del barbero
     * 
     * @return Array con [atendidos, tiempoCorte, tiempoDormido, utilizacion]
     */
    public double[] getEstadisticas() {
        long tiempoTotal = tiempoTotalCortando + tiempoTotalDurmiendo;
        double utilizacion = tiempoTotal > 0 ? (tiempoTotalCortando * 100.0) / tiempoTotal : 0;
        
        return new double[]{
            clientesAtendidosPorBarbero,
            tiempoTotalCortando,
            tiempoTotalDurmiendo,
            utilizacion
        };
    }
    
    // 🔧 Getters
    
    public String getBarberoId() { return barberoId; }
    
    public int getClientesAtendidosPorBarbero() { return clientesAtendidosPorBarbero; }
    
    public boolean isActivo() { return activo; }
    
    /**
     * 📝 Representación en string del barbero
     * 
     * @return Información del barbero
     */
    @Override
    public String toString() {
        return String.format("Barbero{id='%s', atendidos=%d, activo=%s}", 
                           barberoId, clientesAtendidosPorBarbero, activo);
    }
}
