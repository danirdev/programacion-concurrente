package tp7.actividad6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 🚶 GeneradorClientes - Genera flujo de clientes hacia la barbería
 * 
 * Esta clase genera clientes con llegadas aleatorias para simular un
 * flujo realista de clientes a la barbería.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class GeneradorClientes {
    
    // 💈 Referencia a la barbería
    private final Barberia barberia;
    
    // ⚙️ Configuración de generación
    private final int numeroTotalClientes;
    private final int tiempoMinLlegada;
    private final int tiempoMaxLlegada;
    private final int tiempoCorte;
    
    // 🎲 Generador aleatorio
    private final Random random;
    
    // 📊 Clientes generados
    private final List<Cliente> clientes;
    
    /**
     * 🏗️ Constructor del GeneradorClientes
     * 
     * @param barberia Referencia a la barbería
     * @param numeroTotalClientes Número total de clientes a generar
     * @param tiempoMinLlegada Tiempo mínimo entre llegadas (ms)
     * @param tiempoMaxLlegada Tiempo máximo entre llegadas (ms)
     * @param tiempoCorte Tiempo de corte por cliente (ms)
     */
    public GeneradorClientes(Barberia barberia, int numeroTotalClientes, 
                           int tiempoMinLlegada, int tiempoMaxLlegada, int tiempoCorte) {
        this.barberia = barberia;
        this.numeroTotalClientes = numeroTotalClientes;
        this.tiempoMinLlegada = tiempoMinLlegada;
        this.tiempoMaxLlegada = tiempoMaxLlegada;
        this.tiempoCorte = tiempoCorte;
        
        this.random = new Random();
        this.clientes = new ArrayList<>();
        
        System.out.printf("🚶 GeneradorClientes configurado - Total: %d, Llegadas: %d-%dms%n", 
                         numeroTotalClientes, tiempoMinLlegada, tiempoMaxLlegada);
    }
    
    /**
     * 🚀 Generar y lanzar clientes
     * 
     * @return Lista de clientes generados
     */
    public List<Cliente> generarClientes() {
        System.out.printf("🚀 Generando %d clientes...%n", numeroTotalClientes);
        
        for (int i = 1; i <= numeroTotalClientes; i++) {
            try {
                // 🚶 Crear cliente
                Cliente cliente = new Cliente(i, barberia, tiempoCorte);
                clientes.add(cliente);
                
                // ▶️ Lanzar cliente
                cliente.start();
                
                // ⏱️ Esperar antes del siguiente cliente (simular llegadas espaciadas)
                if (i < numeroTotalClientes) {
                    int tiempoEspera = tiempoMinLlegada + 
                                      random.nextInt(tiempoMaxLlegada - tiempoMinLlegada + 1);
                    Thread.sleep(tiempoEspera);
                }
                
            } catch (InterruptedException e) {
                System.err.printf("⚠️ Generación de clientes interrumpida en cliente #%d%n", i);
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.printf("✅ %d clientes generados%n", clientes.size());
        return clientes;
    }
    
    /**
     * ⏳ Esperar que todos los clientes terminen
     */
    public void esperarFinalizacion() {
        System.out.println("⏳ Esperando que todos los clientes terminen...");
        
        for (Cliente cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                System.err.printf("⚠️ Interrumpido esperando %s%n", cliente.getClienteId());
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("✅ Todos los clientes han terminado");
    }
    
    /**
     * 📊 Obtener estadísticas de los clientes
     * 
     * @return String con estadísticas
     */
    public String getEstadisticasClientes() {
        int atendidos = 0;
        int rechazados = 0;
        long tiempoEsperaTotal = 0;
        int clientesConEspera = 0;
        
        for (Cliente cliente : clientes) {
            if (cliente.isAtendido()) {
                atendidos++;
                tiempoEsperaTotal += cliente.getTiempoEspera();
                clientesConEspera++;
            }
            if (cliente.isRechazado()) {
                rechazados++;
            }
        }
        
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📊 ESTADÍSTICAS CLIENTES ===\n");
        stats.append(String.format("🚶 Total clientes generados: %d%n", clientes.size()));
        stats.append(String.format("✅ Clientes atendidos: %d%n", atendidos));
        stats.append(String.format("❌ Clientes rechazados: %d%n", rechazados));
        
        if (clientes.size() > 0) {
            double tasaAtencion = (atendidos * 100.0) / clientes.size();
            double tasaRechazo = (rechazados * 100.0) / clientes.size();
            
            stats.append(String.format("📊 Tasa de atención: %.1f%%%n", tasaAtencion));
            stats.append(String.format("📊 Tasa de rechazo: %.1f%%%n", tasaRechazo));
        }
        
        if (clientesConEspera > 0) {
            double tiempoEsperaPromedio = (double) tiempoEsperaTotal / clientesConEspera;
            stats.append(String.format("⏱️ Tiempo espera promedio: %.2fms%n", tiempoEsperaPromedio));
        }
        
        return stats.toString();
    }
    
    /**
     * 🔢 Obtener número de clientes atendidos
     * 
     * @return Número de clientes atendidos
     */
    public int getClientesAtendidos() {
        return (int) clientes.stream().filter(Cliente::isAtendido).count();
    }
    
    /**
     * 🔢 Obtener número de clientes rechazados
     * 
     * @return Número de clientes rechazados
     */
    public int getClientesRechazados() {
        return (int) clientes.stream().filter(Cliente::isRechazado).count();
    }
    
    /**
     * 📋 Obtener lista de clientes
     * 
     * @return Lista de clientes generados
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
    
    /**
     * 📝 Representación en string del generador
     * 
     * @return Información del generador
     */
    @Override
    public String toString() {
        return String.format("GeneradorClientes{total=%d, atendidos=%d, rechazados=%d}", 
                           clientes.size(), getClientesAtendidos(), getClientesRechazados());
    }
}
