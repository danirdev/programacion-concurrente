package tp7.actividad6;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 💈 BarberoDurmienteSimulacion - Simulación Principal del Barbero Durmiente
 * 
 * Esta clase implementa la simulación completa del problema del barbero durmiente
 * con semáforos, demostrando sincronización correcta entre el barbero y múltiples
 * clientes con recursos limitados (sillas de espera).
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class BarberoDurmienteSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int CAPACIDAD_SILLAS = 3;
    private static final int NUMERO_CLIENTES = 15;
    private static final int TIEMPO_MIN_LLEGADA = 100;
    private static final int TIEMPO_MAX_LLEGADA = 500;
    private static final int TIEMPO_MIN_CORTE = 200;
    private static final int TIEMPO_MAX_CORTE = 800;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("💈 SIMULACIÓN: PROBLEMA DEL BARBERO DURMIENTE");
        System.out.println(SEPARADOR);
        
        BarberoDurmienteSimulacion simulacion = new BarberoDurmienteSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar simulación
        simulacion.ejecutarSimulacion();
        
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
        System.out.println("🎯 Objetivo: Sincronizar barbero y clientes con semáforos");
        System.out.println("🔬 Problema: Barbero Durmiente con recursos limitados");
        System.out.println();
        
        System.out.println("⚙️ CONFIGURACIÓN DE LA SIMULACIÓN:");
        System.out.printf("   🪑 Capacidad de sillas: %d%n", CAPACIDAD_SILLAS);
        System.out.printf("   🚶 Número de clientes: %d%n", NUMERO_CLIENTES);
        System.out.printf("   ⏱️ Tiempo entre llegadas: %d-%d ms%n", TIEMPO_MIN_LLEGADA, TIEMPO_MAX_LLEGADA);
        System.out.printf("   ✂️ Tiempo de corte: %d-%d ms%n", TIEMPO_MIN_CORTE, TIEMPO_MAX_CORTE);
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar la simulación completa
     */
    private void ejecutarSimulacion() {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // 🏗️ Crear barbería
            Barberia barberia = new Barberia("La Barbería Concurrente", CAPACIDAD_SILLAS);
            
            // 💈 Crear barbero
            Barbero barbero = new Barbero("Barbero Principal", barberia, NUMERO_CLIENTES, 
                                         TIEMPO_MIN_CORTE, TIEMPO_MAX_CORTE);
            
            // 🚶 Crear generador de clientes
            GeneradorClientes generador = new GeneradorClientes(barberia, NUMERO_CLIENTES,
                                                               TIEMPO_MIN_LLEGADA, TIEMPO_MAX_LLEGADA,
                                                               (TIEMPO_MIN_CORTE + TIEMPO_MAX_CORTE) / 2);
            
            System.out.println("🚀 INICIANDO SIMULACIÓN...\n");
            
            // ▶️ Iniciar barbero
            barbero.start();
            
            // Pequeña pausa para que el barbero se duerma
            Thread.sleep(500);
            
            // 🚶 Generar clientes
            List<Cliente> clientes = generador.generarClientes();
            
            // ⏳ Esperar que todos los clientes terminen
            generador.esperarFinalizacion();
            
            // ⏳ Esperar que el barbero termine
            barbero.join();
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // 📊 Mostrar resultados
            mostrarResultados(barberia, barbero, generador, tiempoTotal);
            
            // 🔍 Verificar integridad
            verificarIntegridad(barberia);
            
            // 🚪 Cerrar barbería
            barberia.cerrar();
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Error en simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 📊 Mostrar resultados de la simulación
     * 
     * @param barberia Barbería utilizada
     * @param barbero Barbero que atendió
     * @param generador Generador de clientes
     * @param tiempoTotal Tiempo total de simulación
     */
    private void mostrarResultados(Barberia barberia, Barbero barbero, 
                                  GeneradorClientes generador, long tiempoTotal) {
        System.out.println("\n📊 === RESULTADOS DE LA SIMULACIÓN ===\n");
        
        // Estadísticas de la barbería
        System.out.println(barberia.getEstadisticas());
        
        // Estadísticas del barbero
        double[] statsBarbero = barbero.getEstadisticas();
        System.out.printf("💈 Clientes atendidos por barbero: %.0f%n", statsBarbero[0]);
        System.out.printf("⏱️ Tiempo cortando: %.2fs%n", statsBarbero[1] / 1000.0);
        System.out.printf("😴 Tiempo durmiendo: %.2fs%n", statsBarbero[2] / 1000.0);
        System.out.printf("📈 Utilización barbero: %.1f%%%n", statsBarbero[3]);
        
        // Estadísticas de clientes
        System.out.println(generador.getEstadisticasClientes());
        
        // Tiempo total
        System.out.printf("⏱️ Tiempo total simulación: %.2fs%n", tiempoTotal / 1000.0);
        
        // Tabla resumen
        mostrarTablaResumen(barberia, generador);
    }
    
    /**
     * 📋 Mostrar tabla resumen
     * 
     * @param barberia Barbería utilizada
     * @param generador Generador de clientes
     */
    private void mostrarTablaResumen(Barberia barberia, GeneradorClientes generador) {
        System.out.println("\n📊 TABLA RESUMEN:");
        System.out.printf("%-25s %10s%n", "MÉTRICA", "VALOR");
        System.out.println("-".repeat(40));
        
        System.out.printf("%-25s %10d%n", "Capacidad sillas", barberia.getCapacidadSillas());
        System.out.printf("%-25s %10d%n", "Total llegadas", barberia.getTotalLlegadas());
        System.out.printf("%-25s %10d%n", "Clientes atendidos", barberia.getClientesAtendidos());
        System.out.printf("%-25s %10d%n", "Clientes rechazados", barberia.getClientesRechazados());
        
        if (barberia.getTotalLlegadas() > 0) {
            double tasaAtencion = (barberia.getClientesAtendidos() * 100.0) / barberia.getTotalLlegadas();
            double tasaRechazo = (barberia.getClientesRechazados() * 100.0) / barberia.getTotalLlegadas();
            
            System.out.printf("%-25s %9.1f%%%n", "Tasa de atención", tasaAtencion);
            System.out.printf("%-25s %9.1f%%%n", "Tasa de rechazo", tasaRechazo);
        }
        
        System.out.println("-".repeat(40));
    }
    
    /**
     * 🔍 Verificar integridad del sistema
     * 
     * @param barberia Barbería a verificar
     */
    private void verificarIntegridad(Barberia barberia) {
        System.out.println("\n🔍 VERIFICACIÓN DE INTEGRIDAD:");
        
        boolean integra = barberia.verificarIntegridad();
        
        if (integra) {
            System.out.println("✅ INTEGRIDAD VERIFICADA: El sistema funcionó correctamente");
            System.out.println("   ✅ Semáforos sincronizaron correctamente");
            System.out.println("   ✅ Sin race conditions detectadas");
            System.out.println("   ✅ Estadísticas consistentes");
        } else {
            System.out.println("❌ PROBLEMAS DE INTEGRIDAD DETECTADOS");
        }
        
        // Información de semáforos
        System.out.println("\n" + barberia.getInfoSemaforos());
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     */
    @SuppressWarnings("unused")
    private void mostrarAnalisisEducativo() {
        System.out.println("\n🎓 === ANÁLISIS EDUCATIVO ===");
        System.out.println("📚 Conceptos demostrados en esta simulación:\n");
        
        System.out.println("🚦 SEMÁFOROS UTILIZADOS:");
        System.out.println("   1. clientes (0) - Barbero duerme esperando clientes");
        System.out.println("   2. barbero (0) - Cliente espera que barbero esté listo");
        System.out.println("   3. mutex (1) - Protege acceso a sillas libres");
        
        System.out.println("\n💡 PROBLEMAS RESUELTOS:");
        System.out.println("   ✅ Sincronización barbero-cliente (dormir/despertar)");
        System.out.println("   ✅ Gestión de recursos limitados (sillas)");
        System.out.println("   ✅ Política de admisión (rechazo cuando está lleno)");
        System.out.println("   ✅ Exclusión mutua en estado compartido");
        
        System.out.println("\n🎯 PROPIEDADES GARANTIZADAS:");
        System.out.println("   ✅ Un cliente a la vez siendo atendido");
        System.out.println("   ✅ Barbero solo atiende cuando hay clientes");
        System.out.println("   ✅ Clientes rechazados si no hay espacio");
        System.out.println("   ✅ Sin pérdida de señales entre threads");
    }
}
