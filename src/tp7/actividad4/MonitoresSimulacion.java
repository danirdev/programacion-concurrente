package tp7.actividad4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 🖥️ MonitoresSimulacion - Simulación Principal de Monitores con Semáforos
 * 
 * Esta clase implementa la simulación completa de monitores implementados
 * con semáforos, demostrando exclusión mutua implícita, variables de condición
 * y comparación con semáforos puros.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class MonitoresSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int NUMERO_PROCESOS = 4;
    private static final int OPERACIONES_POR_PROCESO = 10;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🖥️ SIMULACIÓN: MONITORES IMPLEMENTADOS CON SEMÁFOROS");
        System.out.println(SEPARADOR);
        
        MonitoresSimulacion simulacion = new MonitoresSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🎮 Menú interactivo
        simulacion.ejecutarMenuPrincipal();
    }
    
    /**
     * 📋 Mostrar información inicial
     */
    private void mostrarInformacionInicial() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Demostrar monitores con exclusión mutua implícita");
        System.out.println("🔬 Implementación: Monitores usando semáforos como base");
        System.out.println("📊 Comparación: Monitor vs Semáforos puros");
        System.out.println();
    }
    
    /**
     * 🎮 Ejecutar menú principal interactivo
     */
    private void ejecutarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenuPrincipal();
            
            try {
                int opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        demostrarMonitorBuffer();
                        break;
                    case 2:
                        demostrarMonitorContador();
                        break;
                    case 3:
                        compararMonitorVsSemaforos();
                        break;
                    case 4:
                        ejecutarPruebasIntegridad();
                        break;
                    case 5:
                        mostrarAnalisisEducativo();
                        break;
                    case 6:
                        continuar = false;
                        break;
                    default:
                        System.out.println("❌ Opción inválida. Intente nuevamente.");
                }
                
                if (continuar) {
                    System.out.println("\n⏸️ Presione Enter para continuar...");
                    scanner.nextLine(); // Limpiar buffer
                    scanner.nextLine(); // Esperar Enter
                }
                
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        System.out.println("\n👋 ¡Gracias por usar la simulación de monitores!");
        scanner.close();
    }
    
    /**
     * 📋 Mostrar menú principal
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n" + SEPARADOR);
        System.out.println("🎮 MENÚ PRINCIPAL - MONITORES CON SEMÁFOROS");
        System.out.println(SEPARADOR);
        System.out.println("1. 📦 Demostración Monitor Buffer (Productor-Consumidor)");
        System.out.println("2. 🔢 Demostración Monitor Contador");
        System.out.println("3. ⚖️ Comparación Monitor vs Semáforos");
        System.out.println("4. 🧪 Pruebas de Integridad");
        System.out.println("5. 🎓 Análisis Educativo");
        System.out.println("6. 🚪 Salir");
        System.out.println(SEPARADOR);
        System.out.print("Seleccione una opción (1-6): ");
    }
    
    /**
     * 📦 Demostrar Monitor Buffer
     */
    private void demostrarMonitorBuffer() {
        System.out.println("\n📦 === DEMOSTRACIÓN MONITOR BUFFER ===");
        System.out.println("🎯 Problema Productor-Consumidor con exclusión mutua implícita\n");
        
        // 🏗️ Crear monitor buffer
        MonitorBuffer buffer = new MonitorBuffer(5);
        
        // 👥 Crear threads productores y consumidores
        List<Thread> threads = new ArrayList<>();
        
        // 🏭 Crear productores
        for (int i = 1; i <= 2; i++) {
            final int productorid = i;
            Thread productor = new Thread(() -> {
                String id = "PROD" + productorid;
                for (int j = 1; j <= OPERACIONES_POR_PROCESO; j++) {
                    try {
                        String elemento = String.format("Elemento_%s_%d", id, j);
                        buffer.depositar(elemento, id);
                        Thread.sleep(100 + (int)(Math.random() * 100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            productor.setName("Productor-" + i);
            threads.add(productor);
        }
        
        // 👤 Crear consumidores
        for (int i = 1; i <= 2; i++) {
            final int consumidorId = i;
            Thread consumidor = new Thread(() -> {
                String id = "CONS" + consumidorId;
                for (int j = 1; j <= OPERACIONES_POR_PROCESO; j++) {
                    try {
                        Object elemento = buffer.extraer(id);
                        System.out.printf("[%s] 🔧 Procesando: %s%n", id, elemento);
                        Thread.sleep(150 + (int)(Math.random() * 100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            consumidor.setName("Consumidor-" + i);
            threads.add(consumidor);
        }
        
        // ▶️ Ejecutar simulación
        ejecutarThreads(threads, "Monitor Buffer");
        
        // 📊 Mostrar resultados
        System.out.println(buffer.getEstadisticasBuffer());
        System.out.println(buffer.getEstadisticas());
        
        // 🔍 Verificar integridad
        boolean integro = buffer.verificarIntegridadBuffer();
        System.out.printf("🔍 Integridad del Monitor Buffer: %s%n", 
                         integro ? "✅ VERIFICADA" : "❌ COMPROMETIDA");
    }
    
    /**
     * 🔢 Demostrar Monitor Contador
     */
    private void demostrarMonitorContador() {
        System.out.println("\n🔢 === DEMOSTRACIÓN MONITOR CONTADOR ===");
        System.out.println("🎯 Contador compartido con exclusión mutua implícita\n");
        
        // 🏗️ Crear monitor contador
        MonitorContador contador = new MonitorContador(0, -50, 50);
        
        // 👥 Crear threads que operan sobre el contador
        List<Thread> threads = new ArrayList<>();
        
        // ➕ Threads que incrementan
        for (int i = 1; i <= 2; i++) {
            final int threadId = i;
            Thread incrementador = new Thread(() -> {
                String id = "INC" + threadId;
                for (int j = 1; j <= OPERACIONES_POR_PROCESO; j++) {
                    try {
                        int nuevoValor = contador.incrementar(id);
                        System.out.printf("[%s] ➕ Incrementado a: %d%n", id, nuevoValor);
                        Thread.sleep(80 + (int)(Math.random() * 40));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            incrementador.setName("Incrementador-" + i);
            threads.add(incrementador);
        }
        
        // ➖ Threads que decrementan
        for (int i = 1; i <= 2; i++) {
            final int threadId = i;
            Thread decrementador = new Thread(() -> {
                String id = "DEC" + threadId;
                for (int j = 1; j <= OPERACIONES_POR_PROCESO; j++) {
                    try {
                        Thread.sleep(200); // Esperar un poco para que haya incrementos
                        int nuevoValor = contador.decrementar(id);
                        System.out.printf("[%s] ➖ Decrementado a: %d%n", id, nuevoValor);
                        Thread.sleep(80 + (int)(Math.random() * 40));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            decrementador.setName("Decrementador-" + i);
            threads.add(decrementador);
        }
        
        // ▶️ Ejecutar simulación
        ejecutarThreads(threads, "Monitor Contador");
        
        // 📊 Mostrar resultados
        System.out.println(contador.getEstadisticasContador());
        System.out.println(contador.getEstadisticas());
        
        // 🔍 Verificar integridad
        boolean integro = contador.verificarIntegridadContador();
        System.out.printf("🔍 Integridad del Monitor Contador: %s%n", 
                         integro ? "✅ VERIFICADA" : "❌ COMPROMETIDA");
    }
    
    /**
     * ⚖️ Comparar Monitor vs Semáforos
     */
    private void compararMonitorVsSemaforos() {
        System.out.println("\n⚖️ === COMPARACIÓN MONITOR vs SEMÁFOROS ===");
        System.out.println("🔬 Análisis comparativo de ambos enfoques\n");
        
        // 📊 Tabla comparativa
        System.out.println("📊 TABLA COMPARATIVA:");
        System.out.printf("%-20s %-15s %-15s %-15s%n", "CARACTERÍSTICA", "MONITOR", "SEMÁFOROS", "GANADOR");
        System.out.println("-".repeat(65));
        
        compararCaracteristica("Exclusión Mutua", "Implícita ✅", "Manual ⚠️", "🏆 MONITOR");
        compararCaracteristica("Facilidad de Uso", "Muy Alta ✅", "Media ⚠️", "🏆 MONITOR");
        compararCaracteristica("Encapsulación", "Completa ✅", "Parcial ⚠️", "🏆 MONITOR");
        compararCaracteristica("Rendimiento", "Bueno ⚠️", "Excelente ✅", "🏆 SEMÁFORO");
        compararCaracteristica("Flexibilidad", "Media ⚠️", "Alta ✅", "🏆 SEMÁFORO");
        compararCaracteristica("Propensión Errores", "Muy Baja ✅", "Alta ❌", "🏆 MONITOR");
        compararCaracteristica("Curva Aprendizaje", "Suave ✅", "Empinada ❌", "🏆 MONITOR");
        
        System.out.println("-".repeat(65));
        
        // 🎯 Análisis detallado
        System.out.println("\n🎯 ANÁLISIS DETALLADO:");
        System.out.println("✅ VENTAJAS DE MONITORES:");
        System.out.println("   • Exclusión mutua automática (imposible olvidar)");
        System.out.println("   • Encapsulación completa del estado");
        System.out.println("   • Código más limpio y legible");
        System.out.println("   • Variables de condición integradas");
        System.out.println("   • Menor propensión a errores");
        
        System.out.println("\n✅ VENTAJAS DE SEMÁFOROS:");
        System.out.println("   • Rendimiento superior (menor overhead)");
        System.out.println("   • Máxima flexibilidad de uso");
        System.out.println("   • Control granular del comportamiento");
        System.out.println("   • Adaptable a cualquier patrón");
        
        System.out.println("\n🎓 RECOMENDACIÓN:");
        System.out.println("   🖥️ Usar MONITORES para: Aplicaciones complejas, equipos grandes, mantenimiento");
        System.out.println("   🚦 Usar SEMÁFOROS para: Sistemas críticos, optimización extrema, patrones específicos");
    }
    
    /**
     * 📊 Comparar una característica específica
     */
    private void compararCaracteristica(String caracteristica, String monitor, String semaforo, String ganador) {
        System.out.printf("%-20s %-15s %-15s %-15s%n", caracteristica, monitor, semaforo, ganador);
    }
    
    /**
     * 🧪 Ejecutar pruebas de integridad
     */
    private void ejecutarPruebasIntegridad() {
        System.out.println("\n🧪 === PRUEBAS DE INTEGRIDAD ===");
        System.out.println("🔍 Verificando integridad de todos los monitores\n");
        
        // 📦 Prueba Monitor Buffer
        System.out.println("📦 Probando MonitorBuffer...");
        MonitorBuffer buffer = new MonitorBuffer(3);
        boolean pruebaBuffer = buffer.ejecutarPrueba("PRUEBA");
        System.out.printf("   Resultado: %s%n", pruebaBuffer ? "✅ EXITOSA" : "❌ FALLIDA");
        
        // 🔢 Prueba Monitor Contador
        System.out.println("\n🔢 Probando MonitorContador...");
        MonitorContador contador = new MonitorContador(10);
        boolean pruebaContador = contador.ejecutarPrueba("PRUEBA");
        System.out.printf("   Resultado: %s%n", pruebaContador ? "✅ EXITOSA" : "❌ FALLIDA");
        
        // 🔄 Prueba Variables de Condición
        System.out.println("\n🔄 Probando VariableCondicion...");
        VariableCondicion variable = new VariableCondicion("PruebaVariable");
        boolean pruebaVariable = probarVariableCondicion(variable);
        System.out.printf("   Resultado: %s%n", pruebaVariable ? "✅ EXITOSA" : "❌ FALLIDA");
        
        // 📊 Resumen
        System.out.println("\n📊 RESUMEN DE PRUEBAS:");
        System.out.printf("   📦 Monitor Buffer: %s%n", pruebaBuffer ? "✅" : "❌");
        System.out.printf("   🔢 Monitor Contador: %s%n", pruebaContador ? "✅" : "❌");
        System.out.printf("   🔄 Variable Condición: %s%n", pruebaVariable ? "✅" : "❌");
        
        boolean todasExitosas = pruebaBuffer && pruebaContador && pruebaVariable;
        System.out.printf("\n🎯 RESULTADO GENERAL: %s%n", 
                         todasExitosas ? "✅ TODAS LAS PRUEBAS EXITOSAS" : "❌ ALGUNAS PRUEBAS FALLARON");
    }
    
    /**
     * 🔄 Probar variable de condición
     */
    private boolean probarVariableCondicion(VariableCondicion variable) {
        try {
            // Verificar estado inicial
            boolean estadoInicial = !variable.hayProcesosEsperando();
            
            // Verificar integridad
            boolean integridad = variable.verificarIntegridad();
            
            // Señalar sin procesos esperando (debería ser ignorado)
            variable.señalar("PRUEBA");
            
            return estadoInicial && integridad;
            
        } catch (Exception e) {
            System.err.printf("❌ Error probando variable de condición: %s%n", e.getMessage());
            return false;
        }
    }
    
    /**
     * 🎓 Mostrar análisis educativo
     */
    private void mostrarAnalisisEducativo() {
        System.out.println("\n🎓 === ANÁLISIS EDUCATIVO ===");
        System.out.println("📚 Conceptos fundamentales de monitores\n");
        
        System.out.println("🖥️ ¿QUÉ ES UN MONITOR?");
        System.out.println("   Un monitor es una 'valla alrededor del recurso' que garantiza");
        System.out.println("   que solo un proceso pueda estar dentro a la vez.");
        System.out.println();
        
        System.out.println("🔑 CARACTERÍSTICAS CLAVE:");
        System.out.println("   ✅ Exclusión mutua IMPLÍCITA (automática)");
        System.out.println("   ✅ Encapsulación completa del estado");
        System.out.println("   ✅ Variables de condición para sincronización");
        System.out.println("   ✅ Imposible uso incorrecto (si está bien implementado)");
        System.out.println();
        
        System.out.println("🔄 VARIABLES DE CONDICIÓN:");
        System.out.println("   • wait(condicion) - Esperar hasta que se cumpla condición");
        System.out.println("   • signal(condicion) - Despertar UN proceso esperando");
        System.out.println("   • signalAll(condicion) - Despertar TODOS los procesos");
        System.out.println();
        
        System.out.println("🚦 IMPLEMENTACIÓN CON SEMÁFOROS:");
        System.out.println("   • Semáforo mutex para exclusión mutua");
        System.out.println("   • Semáforos adicionales para variables de condición");
        System.out.println("   • Liberación/re-adquisición automática en wait()");
        System.out.println();
        
        System.out.println("💡 CUÁNDO USAR MONITORES:");
        System.out.println("   ✅ Aplicaciones complejas con múltiples desarrolladores");
        System.out.println("   ✅ Cuando la simplicidad es más importante que el rendimiento");
        System.out.println("   ✅ Sistemas donde los errores de sincronización son críticos");
        System.out.println("   ✅ Código que debe ser mantenido por largo tiempo");
    }
    
    /**
     * ▶️ Ejecutar lista de threads y esperar finalización
     */
    private void ejecutarThreads(List<Thread> threads, String descripcion) {
        System.out.printf("🚀 Iniciando simulación: %s%n", descripcion);
        
        long tiempoInicio = System.currentTimeMillis();
        
        // Iniciar todos los threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Esperar finalización
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("✅ Simulación '%s' completada en %dms%n", descripcion, tiempoTotal);
    }
}
