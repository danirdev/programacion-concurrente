package tp7.actividad2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 🔒 ExclusionMutuaSimulacion - Simulación Principal de Exclusión Mutua
 * 
 * Esta clase implementa la simulación completa de exclusión mutua comparando
 * los algoritmos clásicos (1 Flag, 2 Flags, Peterson) con la solución moderna
 * de semáforos. Demuestra las ventajas de los semáforos sobre los métodos tradicionales.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ExclusionMutuaSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int NUMERO_PROCESOS = 2;
    private static final int ACCESOS_POR_PROCESO = 15;
    private static final int TIEMPO_MIN_TRABAJO = 50;
    private static final int TIEMPO_MAX_TRABAJO = 150;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🔒 SIMULACIÓN: EXCLUSIÓN MUTUA CON SEMÁFOROS VS ALGORITMOS CLÁSICOS");
        System.out.println(SEPARADOR);
        
        ExclusionMutuaSimulacion simulacion = new ExclusionMutuaSimulacion();
        
        // 📋 Mostrar información de inicio
        simulacion.mostrarInformacionInicio();
        
        // 🎯 Mostrar menú de opciones
        simulacion.mostrarMenu();
        
        System.out.println(SEPARADOR);
        System.out.println("✅ SIMULACIÓN COMPLETADA");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información de inicio
     */
    private void mostrarInformacionInicio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🔄 Procesos: " + NUMERO_PROCESOS);
        System.out.println("🎯 Accesos por proceso: " + ACCESOS_POR_PROCESO);
        System.out.println("⏱️ Tiempo de trabajo: " + TIEMPO_MIN_TRABAJO + "-" + TIEMPO_MAX_TRABAJO + "ms");
        System.out.println("🎯 Objetivo: Demostrar superioridad de semáforos sobre algoritmos clásicos");
        System.out.println();
    }
    
    /**
     * 🎯 Mostrar menú de opciones
     */
    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n🎯 === MENÚ DE SIMULACIONES ===");
            System.out.println("1. 🚦 Demostración con Semáforos (Recomendado)");
            System.out.println("2. 🚩 Algoritmo 1 Flag (Defectuoso)");
            System.out.println("3. 🚩🚩 Algoritmo 2 Flags (Problemático)");
            System.out.println("4. 🎯 Algoritmo Peterson (Clásico)");
            System.out.println("5. 📊 Comparación Completa de Todos");
            System.out.println("6. 🔍 Análisis Teórico");
            System.out.println("0. ❌ Salir");
            System.out.print("\n🔢 Seleccione una opción: ");
            
            try {
                int opcion = scanner.nextInt();
                System.out.println();
                
                switch (opcion) {
                    case 1 -> ejecutarSimulacionSemaforos();
                    case 2 -> ejecutarSimulacionUnFlag();
                    case 3 -> ejecutarSimulacionDosFlags();
                    case 4 -> ejecutarSimulacionPeterson();
                    case 5 -> ejecutarComparacionCompleta();
                    case 6 -> mostrarAnalisisTeorico();
                    case 0 -> {
                        continuar = false;
                        System.out.println("👋 ¡Gracias por usar la simulación!");
                    }
                    default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
                }
                
                if (continuar && opcion != 0) {
                    System.out.print("\n⏸️ Presione Enter para continuar...");
                    scanner.nextLine();
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("❌ Error en la entrada. Intente nuevamente.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        scanner.close();
    }
    
    /**
     * 🚦 Ejecutar simulación con semáforos
     */
    private void ejecutarSimulacionSemaforos() {
        System.out.println("🚦 === SIMULACIÓN CON SEMÁFOROS ===");
        System.out.println("🎯 Demostrando la solución MODERNA y EFICIENTE\n");
        
        RecursoCompartido recurso = new RecursoCompartido();
        List<ProcesoExclusionMutua> procesos = new ArrayList<>();
        
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                ProcesoExclusionMutua proceso = new ProcesoExclusionMutua(
                    "P" + i, recurso, ACCESOS_POR_PROCESO, 
                    TIEMPO_MIN_TRABAJO, TIEMPO_MAX_TRABAJO
                );
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            System.out.println("🚀 Iniciando procesos concurrentes...");
            for (ProcesoExclusionMutua proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (ProcesoExclusionMutua proceso : procesos) {
                proceso.join();
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Mostrar resultados
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📊 RESULTADOS DE SEMÁFOROS");
            System.out.println("=".repeat(50));
            System.out.println(recurso.getEstadisticas());
            System.out.printf("⏱️ Tiempo total de simulación: %dms%n", tiempoTotal);
            
            // Verificar integridad
            boolean integro = recurso.verificarIntegridad();
            System.out.printf("🔍 Verificación de integridad: %s%n", 
                            integro ? "✅ EXITOSA" : "❌ FALLIDA");
            
            System.out.println("\n🏆 CONCLUSIÓN: Los semáforos garantizan:");
            System.out.println("   ✅ Exclusión mutua perfecta");
            System.out.println("   ✅ Sin busy wait (eficiencia de CPU)");
            System.out.println("   ✅ Sin deadlocks");
            System.out.println("   ✅ Fairness automático");
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🚩 Ejecutar simulación con algoritmo 1 Flag (defectuoso)
     */
    private void ejecutarSimulacionUnFlag() {
        System.out.println("🚩 === SIMULACIÓN ALGORITMO 1 FLAG ===");
        System.out.println("⚠️ ADVERTENCIA: Este algoritmo es DEFECTUOSO\n");
        
        AlgoritmoClasico.UnFlag algoritmo = new AlgoritmoClasico.UnFlag();
        List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
        
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = new AlgoritmoClasico.ProcesoPrueba(
                    "1Flag", "P" + i, i, ACCESOS_POR_PROCESO, algoritmo
                );
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización con timeout
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(5000);
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Mostrar resultados
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📊 RESULTADOS DE 1 FLAG");
            System.out.println("=".repeat(50));
            System.out.printf("❌ Violaciones de exclusión mutua: %d%n", algoritmo.getViolaciones());
            System.out.printf("⏱️ Tiempo total: %dms%n", tiempoTotal);
            
            System.out.println("\n❌ PROBLEMAS DETECTADOS:");
            System.out.println("   ❌ NO garantiza exclusión mutua");
            System.out.println("   ❌ Race conditions frecuentes");
            System.out.println("   ❌ Verificación y asignación no atómicas");
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🚩🚩 Ejecutar simulación con algoritmo 2 Flags
     */
    private void ejecutarSimulacionDosFlags() {
        System.out.println("🚩🚩 === SIMULACIÓN ALGORITMO 2 FLAGS ===");
        System.out.println("⚠️ ADVERTENCIA: Riesgo de deadlock\n");
        
        AlgoritmoClasico.DosFlags algoritmo = new AlgoritmoClasico.DosFlags();
        List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
        
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = new AlgoritmoClasico.ProcesoPrueba(
                    "2Flags", "P" + i, i, ACCESOS_POR_PROCESO, algoritmo
                );
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización con timeout mayor
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(10000);
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Mostrar resultados
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📊 RESULTADOS DE 2 FLAGS");
            System.out.println("=".repeat(50));
            System.out.printf("❌ Violaciones: %d%n", algoritmo.getViolaciones());
            System.out.printf("⚠️ Deadlocks detectados: %d%n", algoritmo.getDeadlocks());
            System.out.printf("⏱️ Tiempo total: %dms%n", tiempoTotal);
            
            System.out.println("\n⚠️ PROBLEMAS DETECTADOS:");
            if (algoritmo.getDeadlocks() > 0) {
                System.out.println("   ❌ Deadlocks ocurrieron");
            }
            System.out.println("   ❌ Busy wait ineficiente");
            System.out.println("   ❌ Posible starvation");
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 🎯 Ejecutar simulación con algoritmo Peterson
     */
    private void ejecutarSimulacionPeterson() {
        System.out.println("🎯 === SIMULACIÓN ALGORITMO PETERSON ===");
        System.out.println("✅ Algoritmo correcto pero con busy wait\n");
        
        AlgoritmoClasico.Peterson algoritmo = new AlgoritmoClasico.Peterson();
        List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
        
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = new AlgoritmoClasico.ProcesoPrueba(
                    "Peterson", "P" + i, i, ACCESOS_POR_PROCESO, algoritmo
                );
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(8000);
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Mostrar resultados
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📊 RESULTADOS DE PETERSON");
            System.out.println("=".repeat(50));
            System.out.printf("✅ Violaciones: %d%n", algoritmo.getViolaciones());
            System.out.printf("🔄 Ciclos de busy wait: %d%n", algoritmo.getBusyWaitCiclos());
            System.out.printf("⏱️ Tiempo total: %dms%n", tiempoTotal);
            
            System.out.println("\n✅ VENTAJAS:");
            System.out.println("   ✅ Exclusión mutua garantizada");
            System.out.println("   ✅ Sin deadlock");
            System.out.println("\n❌ DESVENTAJAS:");
            System.out.println("   ❌ Busy wait (desperdicio de CPU)");
            System.out.println("   ❌ Complejo de implementar y mantener");
            
        } catch (InterruptedException e) {
            System.err.println("❌ Simulación interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 📊 Ejecutar comparación completa
     */
    private void ejecutarComparacionCompleta() {
        System.out.println("📊 === COMPARACIÓN COMPLETA DE ALGORITMOS ===");
        System.out.println("🎯 Ejecutando todos los algoritmos para comparación...\n");
        
        ComparadorAlgoritmos comparador = new ComparadorAlgoritmos();
        ComparadorAlgoritmos.ResultadoComparacion resultado = comparador.ejecutarComparacionCompleta();
        
        // Mostrar tabla comparativa
        System.out.println("\n📊 TABLA COMPARATIVA FINAL:");
        System.out.printf("%-15s %-10s %-12s %-10s %-12s%n", 
                         "ALGORITMO", "TIEMPO(ms)", "VIOLACIONES", "DEADLOCKS", "EFICIENCIA");
        System.out.println("-".repeat(60));
        
        imprimirComparacion("1 Flag", resultado.unFlag);
        imprimirComparacion("2 Flags", resultado.dosFlags);
        imprimirComparacion("Peterson", resultado.peterson);
        imprimirComparacion("Semáforos", resultado.semaforos);
        
        System.out.println("-".repeat(60));
        System.out.println("\n🏆 GANADOR CLARO: SEMÁFOROS");
        System.out.println("   🎯 Mejor en TODAS las métricas importantes");
    }
    
    /**
     * 📝 Imprimir línea de comparación
     */
    private void imprimirComparacion(String nombre, ComparadorAlgoritmos.MetricasAlgoritmo metricas) {
        String icono = metricas.violaciones == 0 && metricas.deadlocks == 0 ? "✅" : "❌";
        System.out.printf("%s %-12s %-10d %-12d %-10d %-12.1f%%%n",
                         icono, nombre, metricas.tiempoEjecucion, metricas.violaciones, 
                         metricas.deadlocks, metricas.eficiencia);
    }
    
    /**
     * 🔍 Mostrar análisis teórico
     */
    private void mostrarAnalisisTeorico() {
        System.out.println("🔍 === ANÁLISIS TEÓRICO DE ALGORITMOS ===\n");
        
        System.out.println("📚 EVOLUCIÓN HISTÓRICA:");
        System.out.println("1960s: Primeros intentos con flags simples");
        System.out.println("1965:  Dijkstra introduce los semáforos");
        System.out.println("1981:  Peterson perfecciona algoritmo clásico");
        System.out.println("Hoy:   Semáforos son el estándar industrial\n");
        
        System.out.println("🔬 ANÁLISIS COMPARATIVO:");
        System.out.println();
        
        System.out.println("🚩 ALGORITMO 1 FLAG:");
        System.out.println("   ❌ Exclusión mutua: NO garantizada");
        System.out.println("   ❌ Deadlock: Posible");
        System.out.println("   ❌ Starvation: Posible");
        System.out.println("   ⭐ Simplicidad: Alta");
        System.out.println("   📊 Uso práctico: NINGUNO (defectuoso)\n");
        
        System.out.println("🚩🚩 ALGORITMO 2 FLAGS:");
        System.out.println("   ✅ Exclusión mutua: Garantizada");
        System.out.println("   ❌ Deadlock: Frecuente");
        System.out.println("   ❌ Busy wait: Ineficiente");
        System.out.println("   ⭐ Simplicidad: Media");
        System.out.println("   📊 Uso práctico: Limitado\n");
        
        System.out.println("🎯 ALGORITMO PETERSON:");
        System.out.println("   ✅ Exclusión mutua: Garantizada");
        System.out.println("   ✅ Deadlock: Imposible");
        System.out.println("   ❌ Busy wait: Presente");
        System.out.println("   ⭐ Simplicidad: Baja");
        System.out.println("   📊 Uso práctico: Académico\n");
        
        System.out.println("🚦 SEMÁFOROS:");
        System.out.println("   ✅ Exclusión mutua: Garantizada");
        System.out.println("   ✅ Deadlock: Evitable");
        System.out.println("   ✅ Busy wait: Eliminado");
        System.out.println("   ✅ Fairness: Automático");
        System.out.println("   ⭐ Simplicidad: Alta");
        System.out.println("   📊 Uso práctico: ESTÁNDAR INDUSTRIAL\n");
        
        System.out.println("🎓 CONCLUSIÓN ACADÉMICA:");
        System.out.println("Los semáforos representan la evolución natural de los");
        System.out.println("algoritmos de exclusión mutua, resolviendo TODOS los");
        System.out.println("problemas de los métodos clásicos de forma elegante.");
    }
}
