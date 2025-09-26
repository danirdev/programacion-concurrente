package tp3.actividad6;

import java.util.Scanner;

/**
 * Clase principal para probar el intercambio concurrente de matrices
 * Implementa todos los requerimientos de la Actividad 6
 * @author PC2025
 */
public class TestIntercambioMatrices {
    
    /**
     * Ejecuta la demostración completa según el enunciado
     */
    public static void ejecutarDemostracionCompleta() {
        System.out.println("TP3 - ACTIVIDAD 6 - INTERCAMBIO CONCURRENTE DE MATRICES");
        System.out.println("=".repeat(70));
        System.out.println("Enunciado: Intercambiar filas de A con columnas de B concurrentemente");
        System.out.println("Matriz A: 100x200, Matriz B: 200x100");
        System.out.println("Valores aleatorios: 1-100");
        System.out.println("=".repeat(70));
        
        // Paso 1: Crear matrices según el enunciado
        System.out.println("\n📋 PASO 1: Creando matrices...");
        Matriz A = new Matriz(100, 200, "A");
        Matriz B = new Matriz(200, 100, "B");
        
        System.out.println("✅ Matriz A creada: " + A.getFilas() + "x" + A.getColumnas());
        System.out.println("✅ Matriz B creada: " + B.getFilas() + "x" + B.getColumnas());
        
        // Paso 2: Inicializar con números aleatorios 1-100
        System.out.println("\n📋 PASO 2: Inicializando con números aleatorios (1-100)...");
        A.inicializarAleatoria(1, 100);
        B.inicializarAleatoria(1, 100);
        
        System.out.println("✅ Matrices inicializadas con valores aleatorios");
        
        // Paso 3: Mostrar matrices antes del intercambio
        System.out.println("\n📋 PASO 3: Mostrando matrices ANTES del intercambio...");
        System.out.println("\nEstadísticas de las matrices ANTES:");
        System.out.println("  " + A.toResumen());
        System.out.println("  " + B.toResumen());
        
        // Mostrar esquinas para visualización
        System.out.println("\nEsquina superior izquierda de A (5x5):");
        mostrarEsquinaMatriz(A, 0, 0, 5, 5);
        
        System.out.println("\nEsquina superior izquierda de B (5x5):");
        mostrarEsquinaMatriz(B, 0, 0, 5, 5);
        
        // Crear copias para verificación posterior
        Matriz A_original = A.clonar();
        Matriz B_original = B.clonar();
        A_original.setNombre("A_original");
        B_original.setNombre("B_original");
        
        // Paso 4: Realizar intercambio concurrente
        System.out.println("\n📋 PASO 4: Realizando intercambio CONCURRENTE...");
        System.out.println("Creando 100 hilos (uno por cada par fila-columna)...");
        
        GestorIntercambio gestor = new GestorIntercambio(A, B);
        long tiempoEjecucion = gestor.ejecutarIntercambioConcurrente();
        
        // Paso 5: Mostrar matrices después del intercambio
        System.out.println("\n📋 PASO 5: Mostrando matrices DESPUÉS del intercambio...");
        System.out.println("\nEstadísticas de las matrices DESPUÉS:");
        System.out.println("  " + A.toResumen());
        System.out.println("  " + B.toResumen());
        
        // Mostrar esquinas después del intercambio
        System.out.println("\nEsquina superior izquierda de A (5x5) DESPUÉS:");
        mostrarEsquinaMatriz(A, 0, 0, 5, 5);
        
        System.out.println("\nEsquina superior izquierda de B (5x5) DESPUÉS:");
        mostrarEsquinaMatriz(B, 0, 0, 5, 5);
        
        // Paso 6: Verificar correctitud del intercambio
        System.out.println("\n📋 PASO 6: Verificando correctitud del intercambio...");
        boolean intercambioCorrect = gestor.verificarCorrectitud(A_original, B_original);
        
        // Paso 7: Mostrar comparación visual
        System.out.println("\n📋 PASO 7: Comparación visual detallada...");
        VisualizadorMatrices.mostrarComparacion(A_original, B_original, A, B);
        
        // Paso 8: Generar reporte final
        System.out.println("\n📋 PASO 8: Reporte final...");
        String reporte = VisualizadorMatrices.crearReporteIntercambio(
            A_original, B_original, A, B, tiempoEjecucion);
        System.out.println(reporte);
        
        // Resumen final
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RESUMEN DE LA EJECUCIÓN");
        System.out.println("=".repeat(70));
        System.out.printf("✅ Matrices creadas: A(%dx%d), B(%dx%d)%n", 
                         A_original.getFilas(), A_original.getColumnas(),
                         B_original.getFilas(), B_original.getColumnas());
        System.out.println("✅ Inicializadas con valores aleatorios 1-100");
        System.out.println("✅ Intercambio concurrente ejecutado con 100 hilos");
        System.out.printf("✅ Tiempo de ejecución: %d ms%n", tiempoEjecucion);
        System.out.println("✅ Verificación de correctitud: " + (intercambioCorrect ? "EXITOSA" : "FALLIDA"));
        System.out.println("✅ Matrices mostradas antes y después del intercambio");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Muestra una esquina específica de una matriz
     */
    private static void mostrarEsquinaMatriz(Matriz matriz, int filaInicio, int colInicio, 
                                           int numFilas, int numCols) {
        int filaFin = Math.min(filaInicio + numFilas, matriz.getFilas());
        int colFin = Math.min(colInicio + numCols, matriz.getColumnas());
        
        for (int i = filaInicio; i < filaFin; i++) {
            System.out.print("  ");
            for (int j = colInicio; j < colFin; j++) {
                System.out.printf("%4d ", matriz.get(i, j));
            }
            System.out.println();
        }
    }
    
    /**
     * Ejecuta una demostración con matrices más pequeñas para mejor visualización
     */
    public static void ejecutarDemostracionPequena() {
        System.out.println("DEMOSTRACIÓN CON MATRICES PEQUEÑAS");
        System.out.println("=".repeat(50));
        System.out.println("Para mejor visualización, usando matrices A(4x6) y B(6x4)");
        
        // Crear matrices pequeñas
        Matriz A = new Matriz(4, 6, "A_pequeña");
        Matriz B = new Matriz(6, 4, "B_pequeña");
        
        A.inicializarAleatoria(1, 20);
        B.inicializarAleatoria(21, 40);
        
        // Mostrar matrices completas antes
        System.out.println("\nMATRICES ANTES DEL INTERCAMBIO:");
        VisualizadorMatrices.mostrarMatrizCompleta(A);
        System.out.println();
        VisualizadorMatrices.mostrarMatrizCompleta(B);
        
        // Crear copias
        Matriz A_antes = A.clonar();
        Matriz B_antes = B.clonar();
        
        // Realizar intercambio
        System.out.println("\nRealizando intercambio concurrente...");
        GestorIntercambio gestor = new GestorIntercambio(A, B);
        long tiempo = gestor.ejecutarIntercambioConcurrente();
        
        // Mostrar matrices después
        System.out.println("\nMATRICES DESPUÉS DEL INTERCAMBIO:");
        VisualizadorMatrices.mostrarMatrizCompleta(A);
        System.out.println();
        VisualizadorMatrices.mostrarMatrizCompleta(B);
        
        // Verificar correctitud
        gestor.verificarCorrectitud(A_antes, B_antes);
        
        System.out.printf("\nIntercambio completado en %d ms%n", tiempo);
    }
    
    /**
     * Compara rendimiento entre intercambio secuencial y concurrente
     */
    public static void compararRendimiento() {
        System.out.println("COMPARACIÓN DE RENDIMIENTO");
        System.out.println("=".repeat(50));
        
        // Crear matrices según el enunciado
        Matriz A1 = new Matriz(100, 200, "A_secuencial");
        Matriz B1 = new Matriz(200, 100, "B_secuencial");
        A1.inicializarAleatoria(1, 100);
        B1.inicializarAleatoria(1, 100);
        
        Matriz A2 = new Matriz(100, 200, "A_concurrente");
        Matriz B2 = new Matriz(200, 100, "B_concurrente");
        A2.inicializarAleatoria(1, 100);
        B2.inicializarAleatoria(1, 100);
        
        // Comparar rendimiento
        GestorIntercambio gestor = new GestorIntercambio(A1, B1);
        gestor.compararRendimiento();
    }
    
    /**
     * Muestra el menú de opciones
     */
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TP3 - ACTIVIDAD 6 - INTERCAMBIO CONCURRENTE DE MATRICES");
        System.out.println("=".repeat(60));
        System.out.println("1. Demostración completa (según enunciado)");
        System.out.println("2. Demostración con matrices pequeñas");
        System.out.println("3. Comparación de rendimiento");
        System.out.println("4. Prueba individual de componentes");
        System.out.println("5. Salir");
        System.out.println("=".repeat(60));
        System.out.print("Seleccione una opción (1-5): ");
    }
    
    /**
     * Ejecuta pruebas individuales de componentes
     */
    private static void ejecutarPruebasIndividuales() {
        System.out.println("PRUEBAS INDIVIDUALES DE COMPONENTES");
        System.out.println("=".repeat(50));
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("1. Probar clase Matriz");
        System.out.println("2. Probar IntercambiadorFilaColumna");
        System.out.println("3. Probar VisualizadorMatrices");
        System.out.print("Seleccione componente a probar (1-3): ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    System.out.println("\nEjecutando prueba de Matriz...");
                    // Crear matriz de prueba
                    Matriz test = new Matriz(5, 5, "Test");
                    test.inicializarAleatoria(1, 50);
                    System.out.println(test);
                    System.out.println("Estadísticas: " + test.toResumen());
                    break;
                    
                case 2:
                    System.out.println("\nEjecutando prueba de IntercambiadorFilaColumna...");
                    IntercambiadorFilaColumna.main(new String[0]);
                    break;
                    
                case 3:
                    System.out.println("\nEjecutando prueba de VisualizadorMatrices...");
                    VisualizadorMatrices.main(new String[0]);
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida");
        }
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Verificar argumentos de línea de comandos
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "completo":
                    ejecutarDemostracionCompleta();
                    return;
                case "pequeño":
                case "pequeno":
                    ejecutarDemostracionPequena();
                    return;
                case "rendimiento":
                    compararRendimiento();
                    return;
                case "auto":
                    System.out.println("Modo automático - Ejecutando demostración completa...");
                    ejecutarDemostracionCompleta();
                    return;
            }
        }
        
        // Modo interactivo
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1:
                        ejecutarDemostracionCompleta();
                        break;
                        
                    case 2:
                        ejecutarDemostracionPequena();
                        break;
                        
                    case 3:
                        compararRendimiento();
                        break;
                        
                    case 4:
                        ejecutarPruebasIndividuales();
                        break;
                        
                    case 5:
                        continuar = false;
                        System.out.println("\n¡Gracias por usar el programa!");
                        break;
                        
                    default:
                        System.out.println("\nOpción no válida. Intente nuevamente.");
                }
                
                if (continuar && opcion != 5) {
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Por favor ingrese un número del 1 al 5.");
            }
        }
        
        scanner.close();
    }
}
