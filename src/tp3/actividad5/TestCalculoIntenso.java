package tp3.actividad5;

import java.util.Scanner;

/**
 * Clase principal para probar el cálculo intensivo con diferentes enfoques
 * Implementa la interfaz completa para la Actividad 5
 * @author PC2025
 */
public class TestCalculoIntenso {
    
    /**
     * Muestra el menú principal de opciones
     */
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TP3 - ACTIVIDAD 5 - CÁLCULO INTENSIVO CON HILOS");
        System.out.println("=".repeat(60));
        System.out.println("1. Ejecución secuencial (un núcleo)");
        System.out.println("2. Ejecución concurrente (múltiples núcleos)");
        System.out.println("3. Comparación completa (recomendado)");
        System.out.println("4. Análisis estadístico (múltiples iteraciones)");
        System.out.println("5. Prueba rápida (un solo cálculo)");
        System.out.println("6. Información del sistema");
        System.out.println("7. Salir");
        System.out.println("=".repeat(60));
        System.out.print("Seleccione una opción (1-7): ");
    }
    
    /**
     * Ejecuta una prueba rápida con un solo cálculo
     */
    private static void ejecutarPruebaRapida() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== PRUEBA RÁPIDA ===");
        System.out.print("Ingrese el valor de root (1-20): ");
        
        try {
            int root = Integer.parseInt(scanner.nextLine());
            if (root < 1 || root > 20) {
                System.out.println("Valor fuera de rango. Usando root = 5");
                root = 5;
            }
            
            System.out.println("\nEjecutando SumRootN(" + root + ") de forma secuencial...");
            CalculadoraSecuencial.pruebaRapida(root);
            
            System.out.println("\nEjecutando SumRootN(" + root + ") con un hilo...");
            HiloCalculador hilo = new HiloCalculador(root);
            
            long tiempoInicio = System.nanoTime();
            hilo.start();
            
            try {
                hilo.join();
                long tiempoFin = System.nanoTime();
                
                double tiempoTotal = (tiempoFin - tiempoInicio) / 1_000_000_000.0;
                System.out.printf("Tiempo total con hilo: %.3f segundos%n", tiempoTotal);
                System.out.printf("Overhead del hilo: %.3f segundos%n", 
                                 tiempoTotal - hilo.getTiempoEjecucionSegundos());
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Prueba interrumpida");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Ejecutando con root = 5");
            CalculadoraSecuencial.pruebaRapida(5);
        }
    }
    
    /**
     * Muestra información detallada del sistema
     */
    private static void mostrarInformacionSistema() {
        System.out.println("\n=== INFORMACIÓN DEL SISTEMA ===");
        
        // Información básica
        System.out.println("\nINFORMACIÓN BÁSICA:");
        System.out.println("-".repeat(30));
        System.out.println("Sistema Operativo: " + System.getProperty("os.name"));
        System.out.println("Arquitectura: " + System.getProperty("os.arch"));
        System.out.println("Versión Java: " + System.getProperty("java.version"));
        System.out.println("Vendor Java: " + System.getProperty("java.vendor"));
        
        // Información de CPU
        System.out.println("\nINFORMACIÓN DE CPU:");
        System.out.println("-".repeat(30));
        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("Núcleos disponibles: " + nucleos);
        System.out.println("Speedup teórico máximo: " + nucleos + "x");
        
        // Información de memoria
        System.out.println("\nINFORMACIÓN DE MEMORIA:");
        System.out.println("-".repeat(30));
        Runtime runtime = Runtime.getRuntime();
        long memoriaMax = runtime.maxMemory();
        long memoriaTotal = runtime.totalMemory();
        long memoriaLibre = runtime.freeMemory();
        long memoriaUsada = memoriaTotal - memoriaLibre;
        
        System.out.printf("Memoria máxima JVM: %,d MB%n", memoriaMax / (1024 * 1024));
        System.out.printf("Memoria total JVM: %,d MB%n", memoriaTotal / (1024 * 1024));
        System.out.printf("Memoria usada: %,d MB%n", memoriaUsada / (1024 * 1024));
        System.out.printf("Memoria libre: %,d MB%n", memoriaLibre / (1024 * 1024));
        
        // Información del algoritmo
        System.out.println("\nINFORMACIÓN DEL ALGORITMO:");
        System.out.println("-".repeat(30));
        System.out.println("Función: SumRootN(root)");
        System.out.println("Operación: Math.exp(Math.log(i) / root)");
        System.out.println("Equivalente: i^(1/root)");
        System.out.println("Iteraciones por cálculo: 10,000,000");
        System.out.println("Número de cálculos: 20 (root 1-20)");
        System.out.println("Total de operaciones: 200,000,000");
        
        // Estimaciones de tiempo
        System.out.println("\nESTIMACIONES DE TIEMPO:");
        System.out.println("-".repeat(30));
        System.out.println("Tiempo estimado secuencial: 10-30 segundos");
        System.out.println("Tiempo estimado concurrente: " + 
                          String.format("%.1f", 15.0 / nucleos) + "-" + 
                          String.format("%.1f", 30.0 / nucleos) + " segundos");
        
        // Recomendaciones
        System.out.println("\nRECOMENDACIONES:");
        System.out.println("-".repeat(30));
        System.out.println("• Cierre otras aplicaciones para obtener mejores resultados");
        System.out.println("• Use el Administrador de Tareas para monitorear CPU");
        System.out.println("• La primera ejecución puede ser más lenta (JIT compilation)");
        System.out.println("• Los resultados pueden variar entre ejecuciones");
    }
    
    /**
     * Ejecuta el programa con interfaz interactiva
     */
    private static void ejecutarInteractivo() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1:
                        System.out.println("\nEjecutando versión secuencial...");
                        CalculadoraSecuencial.main(new String[0]);
                        break;
                        
                    case 2:
                        System.out.println("\nEjecutando versión concurrente...");
                        CalculadoraConcurrente.main(new String[0]);
                        break;
                        
                    case 3:
                        System.out.println("\nEjecutando comparación completa...");
                        ComparadorRendimiento.ejecutarComparacionCompleta();
                        break;
                        
                    case 4:
                        System.out.println("\nEjecutando análisis estadístico...");
                        ComparadorRendimiento.ejecutarAnalisisEstadistico();
                        break;
                        
                    case 5:
                        ejecutarPruebaRapida();
                        break;
                        
                    case 6:
                        mostrarInformacionSistema();
                        break;
                        
                    case 7:
                        continuar = false;
                        System.out.println("\n¡Gracias por usar el programa!");
                        break;
                        
                    default:
                        System.out.println("\nOpción no válida. Intente nuevamente.");
                }
                
                if (continuar && opcion != 6) {
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor ingrese un número del 1 al 7.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Ejecuta modo automático para demostración
     */
    private static void ejecutarModoAutomatico() {
        System.out.println("MODO AUTOMÁTICO - DEMOSTRACIÓN COMPLETA");
        System.out.println("=".repeat(60));
        
        // Información del sistema
        mostrarInformacionSistema();
        
        // Pausa
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Ejecutar comparación
        ComparadorRendimiento.ejecutarComparacionCompleta();
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Verificar argumentos de línea de comandos
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "secuencial":
                    CalculadoraSecuencial.main(new String[0]);
                    break;
                case "concurrente":
                    CalculadoraConcurrente.main(new String[0]);
                    break;
                case "comparar":
                    ComparadorRendimiento.ejecutarComparacionCompleta();
                    break;
                case "estadistico":
                    ComparadorRendimiento.ejecutarAnalisisEstadistico();
                    break;
                case "auto":
                    ejecutarModoAutomatico();
                    break;
                case "info":
                    mostrarInformacionSistema();
                    break;
                default:
                    System.out.println("Argumentos válidos: secuencial, concurrente, comparar, estadistico, auto, info");
                    System.out.println("Sin argumentos: modo interactivo");
            }
        } else {
            // Modo interactivo por defecto
            ejecutarInteractivo();
        }
    }
}
