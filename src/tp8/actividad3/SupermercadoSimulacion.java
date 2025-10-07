package tp8.actividad3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 🛒 SupermercadoSimulacion - Simulación de Supermercado con 3 Cajas
 * 
 * Esta clase simula un supermercado con 3 cajas atendiendo a 50 clientes
 * que llegan todos juntos. Cada cajero tarda entre 1 y 3 segundos en
 * atender a cada cliente.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class SupermercadoSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int NUMERO_CAJAS = 3;
    private static final int NUMERO_CLIENTES = 50;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🛒 SIMULACIÓN DE SUPERMERCADO");
        System.out.println(SEPARADOR);
        
        SupermercadoSimulacion simulacion = new SupermercadoSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar simulación
        List<Cliente> clientes = simulacion.ejecutarSimulacion();
        
        // 📊 Mostrar resultados
        simulacion.analizarResultados(clientes);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ SIMULACIÓN COMPLETADA");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información inicial
     */
    private void mostrarInformacionInicial() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Simular atención en supermercado con múltiples cajas");
        System.out.println("🔬 Framework: Java ExecutorService (Fixed Thread Pool)");
        System.out.println();
        
        System.out.println("🏪 CONFIGURACIÓN:");
        System.out.printf("   💳 Número de cajas: %d%n", NUMERO_CAJAS);
        System.out.printf("   👥 Número de clientes: %d%n", NUMERO_CLIENTES);
        System.out.printf("   ⏱️ Tiempo de atención: 1-3 segundos (aleatorio)%n");
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar la simulación del supermercado
     * 
     * @return Lista de clientes atendidos
     */
    private List<Cliente> ejecutarSimulacion() {
        System.out.println("🚀 INICIANDO ATENCIÓN DE CLIENTES...\n");
        
        long tiempoInicio = System.currentTimeMillis();
        
        // 👥 Crear lista de clientes
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= NUMERO_CLIENTES; i++) {
            clientes.add(new Cliente(i));
        }
        
        // 🏗️ Crear ExecutorService con pool de 3 threads (3 cajas)
        ExecutorService executor = Executors.newFixedThreadPool(NUMERO_CAJAS);
        
        try {
            // 📤 Enviar todos los clientes al pool
            for (Cliente cliente : clientes) {
                TareaAtencionCliente tarea = new TareaAtencionCliente(cliente);
                executor.submit(tarea);
            }
            
            // 🛑 Iniciar shutdown (no acepta más tareas)
            executor.shutdown();
            
            // ⏳ Esperar a que todos los clientes sean atendidos
            boolean terminado = executor.awaitTermination(10, TimeUnit.MINUTES);
            
            if (terminado) {
                System.out.println("\n✅ TODOS LOS CLIENTES ATENDIDOS\n");
            } else {
                System.out.println("\n⚠️ Timeout - Algunas atenciones no terminaron\n");
            }
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("⏱️ Tiempo total de simulación: %.2fs%n%n", tiempoTotal / 1000.0);
        
        return clientes;
    }
    
    /**
     * 📊 Analizar resultados de la simulación
     * 
     * @param clientes Lista de clientes atendidos
     */
    private void analizarResultados(List<Cliente> clientes) {
        System.out.println("📊 === ESTADÍSTICAS FINALES ===\n");
        
        // 📊 Calcular estadísticas por caja
        Map<Integer, List<Cliente>> clientesPorCaja = new HashMap<>();
        
        for (Cliente cliente : clientes) {
            int caja = cliente.getNumeroCaja();
            clientesPorCaja.computeIfAbsent(caja, k -> new ArrayList<>()).add(cliente);
        }
        
        // 📋 Mostrar tabla de estadísticas
        mostrarTablaEstadisticas(clientesPorCaja, clientes);
        
        // 📈 Métricas de rendimiento
        mostrarMetricasRendimiento(clientes);
        
        // 🎓 Análisis educativo
        mostrarAnalisisEducativo(clientesPorCaja);
    }
    
    /**
     * 📋 Mostrar tabla de estadísticas por caja
     * 
     * @param clientesPorCaja Mapa de clientes agrupados por caja
     * @param todosClientes Lista de todos los clientes
     */
    private void mostrarTablaEstadisticas(Map<Integer, List<Cliente>> clientesPorCaja, 
                                         List<Cliente> todosClientes) {
        System.out.printf("%-12s %-15s %-15s %-15s%n", "CAJA", "CLIENTES", "TIEMPO AVG", "TIEMPO TOTAL");
        System.out.println("-".repeat(60));
        
        long tiempoTotalGlobal = 0;
        
        for (int caja = 1; caja <= NUMERO_CAJAS; caja++) {
            List<Cliente> clientesCaja = clientesPorCaja.getOrDefault(caja, new ArrayList<>());
            
            if (!clientesCaja.isEmpty()) {
                // Calcular tiempos
                long tiempoTotal = clientesCaja.stream()
                    .mapToLong(Cliente::getTiempoAtencion)
                    .sum();
                
                double tiempoPromedio = (double) tiempoTotal / clientesCaja.size();
                
                tiempoTotalGlobal += tiempoTotal;
                
                System.out.printf("%-12s %-15d %-15.2fs %-15.2fs%n",
                                 "Caja " + caja,
                                 clientesCaja.size(),
                                 tiempoPromedio / 1000.0,
                                 tiempoTotal / 1000.0);
            }
        }
        
        System.out.println("-".repeat(60));
        
        // Total general
        double tiempoPromedioGlobal = (double) tiempoTotalGlobal / todosClientes.size();
        System.out.printf("%-12s %-15d %-15.2fs %-15.2fs%n",
                         "TOTAL",
                         todosClientes.size(),
                         tiempoPromedioGlobal / 1000.0,
                         tiempoTotalGlobal / 1000.0);
        
        System.out.println();
    }
    
    /**
     * 📈 Mostrar métricas de rendimiento
     * 
     * @param clientes Lista de clientes
     */
    private void mostrarMetricasRendimiento(List<Cliente> clientes) {
        System.out.println("📈 MÉTRICAS DE RENDIMIENTO:");
        
        // Tiempo total de simulación
        long tiempoInicio = clientes.stream()
            .mapToLong(Cliente::getTiempoInicioAtencion)
            .min()
            .orElse(0);
        
        long tiempoFin = clientes.stream()
            .mapToLong(Cliente::getTiempoFinAtencion)
            .max()
            .orElse(0);
        
        long tiempoSimulacion = tiempoFin - tiempoInicio;
        
        // Tiempo total trabajado por todas las cajas
        long tiempoTotalTrabajo = clientes.stream()
            .mapToLong(Cliente::getTiempoAtencion)
            .sum();
        
        // Eficiencia
        double eficiencia = (tiempoTotalTrabajo * 100.0) / (tiempoSimulacion * NUMERO_CAJAS);
        
        // Throughput
        double throughput = (clientes.size() * 1000.0) / tiempoSimulacion;
        
        System.out.printf("   ⏱️ Tiempo simulación: %.2fs%n", tiempoSimulacion / 1000.0);
        System.out.printf("   ⚙️ Tiempo total trabajo: %.2fs%n", tiempoTotalTrabajo / 1000.0);
        System.out.printf("   📊 Eficiencia: %.1f%%%n", eficiencia);
        System.out.printf("   🚀 Throughput: %.2f clientes/segundo%n", throughput);
        System.out.println();
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     * 
     * @param clientesPorCaja Mapa de clientes por caja
     */
    private void mostrarAnalisisEducativo(Map<Integer, List<Cliente>> clientesPorCaja) {
        System.out.println("🎓 === ANÁLISIS EDUCATIVO ===");
        
        System.out.println("\n💳 DISTRIBUCIÓN DE CLIENTES:");
        for (int caja = 1; caja <= NUMERO_CAJAS; caja++) {
            int cantidad = clientesPorCaja.getOrDefault(caja, new ArrayList<>()).size();
            System.out.printf("   Caja %d: %d clientes (%.1f%%)%n", 
                             caja, cantidad, (cantidad * 100.0) / NUMERO_CLIENTES);
        }
        
        System.out.println("\n🔄 EXECUTOR FRAMEWORK:");
        System.out.println("   ✅ Pool fijo de 3 threads representa 3 cajas físicas");
        System.out.println("   ✅ Cola automática gestiona clientes en espera");
        System.out.println("   ✅ Threads se reutilizan para múltiples clientes");
        System.out.println("   ✅ Máximo 3 clientes atendidos simultáneamente");
        
        System.out.println("\n📊 COMPORTAMIENTO OBSERVADO:");
        System.out.printf("   • Total clientes: %d%n", NUMERO_CLIENTES);
        System.out.printf("   • Clientes por caja: ~%d%n", NUMERO_CLIENTES / NUMERO_CAJAS);
        System.out.printf("   • Tiempo promedio atención: ~2 segundos%n");
        System.out.printf("   • Rondas de atención: ~%d%n", 
                         (NUMERO_CLIENTES + NUMERO_CAJAS - 1) / NUMERO_CAJAS);
        
        System.out.println("\n💡 VENTAJAS DEMOSTRADAS:");
        System.out.println("   ✅ Simulación realista de sistema de atención");
        System.out.println("   ✅ Gestión eficiente de recursos limitados (cajas)");
        System.out.println("   ✅ Cola de espera automática");
        System.out.println("   ✅ Balance de carga entre cajas");
        System.out.println("   ✅ Estadísticas detalladas por recurso");
    }
}
