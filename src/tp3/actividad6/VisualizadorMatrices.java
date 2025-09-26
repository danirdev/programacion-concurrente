package tp3.actividad6;

/**
 * Clase para visualizar matrices antes y después del intercambio
 * Proporciona diferentes formatos de visualización según el tamaño
 * @author PC2025
 */
public class VisualizadorMatrices {
    
    /**
     * Muestra las matrices antes y después del intercambio
     * @param AAntes matriz A antes del intercambio
     * @param BAntes matriz B antes del intercambio
     * @param ADespues matriz A después del intercambio
     * @param BDespues matriz B después del intercambio
     */
    public static void mostrarComparacion(Matriz AAntes, Matriz BAntes, 
                                        Matriz ADespues, Matriz BDespues) {
        System.out.println("=".repeat(80));
        System.out.println("COMPARACIÓN: ANTES Y DESPUÉS DEL INTERCAMBIO");
        System.out.println("=".repeat(80));
        
        // Mostrar estadísticas generales
        mostrarEstadisticasComparativas(AAntes, BAntes, ADespues, BDespues);
        
        // Mostrar esquinas de las matrices
        System.out.println("\nVISUALIZACIÓN DE ESQUINAS (5x5):");
        System.out.println("-".repeat(60));
        
        mostrarEsquinasComparativas(AAntes, BAntes, ADespues, BDespues);
        
        // Verificar algunos intercambios específicos
        verificarIntercambiosEspecificos(AAntes, BAntes, ADespues, BDespues);
    }
    
    /**
     * Muestra estadísticas comparativas de las matrices
     */
    private static void mostrarEstadisticasComparativas(Matriz AAntes, Matriz BAntes, 
                                                      Matriz ADespues, Matriz BDespues) {
        System.out.println("ESTADÍSTICAS COMPARATIVAS:");
        System.out.println("-".repeat(40));
        
        System.out.println("ANTES DEL INTERCAMBIO:");
        System.out.println("  " + AAntes.toResumen());
        System.out.println("  " + BAntes.toResumen());
        
        System.out.println("\nDESPUÉS DEL INTERCAMBIO:");
        System.out.println("  " + ADespues.toResumen());
        System.out.println("  " + BDespues.toResumen());
        
        // Verificar conservación de datos
        double[] statsAAntes = AAntes.calcularEstadisticas();
        double[] statsBAntes = BAntes.calcularEstadisticas();
        double[] statsADespues = ADespues.calcularEstadisticas();
        double[] statsBDespues = BDespues.calcularEstadisticas();
        
        double sumaAntesTotal = statsAAntes[2] + statsBAntes[2];
        double sumaDespuesTotal = statsADespues[2] + statsBDespues[2];
        
        System.out.println("\nCONSERVACIÓN DE DATOS:");
        System.out.printf("Suma total antes:   %.0f%n", sumaAntesTotal);
        System.out.printf("Suma total después: %.0f%n", sumaDespuesTotal);
        
        if (Math.abs(sumaAntesTotal - sumaDespuesTotal) < 0.001) {
            System.out.println("✅ Los datos se conservaron correctamente");
        } else {
            System.out.println("❌ Se perdieron datos durante el intercambio");
        }
    }
    
    /**
     * Muestra las esquinas de las matrices para comparación visual
     */
    private static void mostrarEsquinasComparativas(Matriz AAntes, Matriz BAntes, 
                                                  Matriz ADespues, Matriz BDespues) {
        
        // Matriz A - Esquina superior izquierda
        System.out.println("MATRIZ A - Esquina superior izquierda (5x5):");
        System.out.println("ANTES:");
        mostrarEsquina(AAntes, 0, 0, 5, 5);
        System.out.println("DESPUÉS:");
        mostrarEsquina(ADespues, 0, 0, 5, 5);
        
        System.out.println();
        
        // Matriz B - Esquina superior izquierda
        System.out.println("MATRIZ B - Esquina superior izquierda (5x5):");
        System.out.println("ANTES:");
        mostrarEsquina(BAntes, 0, 0, 5, 5);
        System.out.println("DESPUÉS:");
        mostrarEsquina(BDespues, 0, 0, 5, 5);
    }
    
    /**
     * Muestra una esquina específica de una matriz
     * @param matriz matriz a mostrar
     * @param filaInicio fila de inicio
     * @param colInicio columna de inicio
     * @param numFilas número de filas a mostrar
     * @param numCols número de columnas a mostrar
     */
    private static void mostrarEsquina(Matriz matriz, int filaInicio, int colInicio, 
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
     * Verifica intercambios específicos para demostrar que funcionó
     */
    private static void verificarIntercambiosEspecificos(Matriz AAntes, Matriz BAntes, 
                                                       Matriz ADespues, Matriz BDespues) {
        System.out.println("\nVERIFICACIÓN DE INTERCAMBIOS ESPECÍFICOS:");
        System.out.println("-".repeat(50));
        
        // Verificar algunos intercambios específicos
        int[] indicesVerificar = {0, 1, 2, 49, 99}; // Algunos índices para verificar
        
        for (int idx : indicesVerificar) {
            if (idx < AAntes.getFilas() && idx < BAntes.getColumnas()) {
                System.out.println("Verificando intercambio en índice " + idx + ":");
                
                // Mostrar algunos valores de la fila/columna intercambiada
                System.out.print("  Fila " + idx + " de A (antes): ");
                mostrarPrimerosValores(AAntes.getFila(idx), 5);
                
                System.out.print("  Col " + idx + " de B (después): ");
                mostrarPrimerosValores(BDespues.getColumna(idx), 5);
                
                System.out.print("  Col " + idx + " de B (antes): ");
                mostrarPrimerosValores(BAntes.getColumna(idx), 5);
                
                System.out.print("  Fila " + idx + " de A (después): ");
                mostrarPrimerosValores(ADespues.getFila(idx), 5);
                
                // Verificar que el intercambio es correcto
                boolean intercambioCorrect = 
                    sonIguales(AAntes.getFila(idx), BDespues.getColumna(idx)) &&
                    sonIguales(BAntes.getColumna(idx), ADespues.getFila(idx));
                
                System.out.println("  Estado: " + (intercambioCorrect ? "✅ Correcto" : "❌ Error"));
                System.out.println();
            }
        }
    }
    
    /**
     * Muestra los primeros valores de un array
     * @param array array a mostrar
     * @param cantidad cantidad de valores a mostrar
     */
    private static void mostrarPrimerosValores(int[] array, int cantidad) {
        int limite = Math.min(cantidad, array.length);
        for (int i = 0; i < limite; i++) {
            System.out.printf("%3d ", array[i]);
        }
        if (array.length > cantidad) {
            System.out.print("...");
        }
        System.out.println();
    }
    
    /**
     * Verifica si dos arrays son iguales
     * @param array1 primer array
     * @param array2 segundo array
     * @return true si son iguales
     */
    private static boolean sonIguales(int[] array1, int[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Muestra una matriz completa (solo para matrices pequeñas)
     * @param matriz matriz a mostrar
     */
    public static void mostrarMatrizCompleta(Matriz matriz) {
        System.out.println("MATRIZ " + matriz.getNombre() + " (" + 
                          matriz.getFilas() + "x" + matriz.getColumnas() + "):");
        
        if (matriz.getFilas() <= 20 && matriz.getColumnas() <= 20) {
            // Mostrar matriz completa
            for (int i = 0; i < matriz.getFilas(); i++) {
                System.out.printf("Fila %2d: ", i);
                for (int j = 0; j < matriz.getColumnas(); j++) {
                    System.out.printf("%4d ", matriz.get(i, j));
                }
                System.out.println();
            }
        } else {
            // Mostrar solo esquinas
            System.out.println("Matriz demasiado grande, mostrando esquinas:");
            System.out.println(matriz.toString());
        }
    }
    
    /**
     * Crea un reporte detallado del intercambio
     * @param AAntes matriz A antes
     * @param BAntes matriz B antes
     * @param ADespues matriz A después
     * @param BDespues matriz B después
     * @param tiempoEjecucion tiempo de ejecución en ms
     * @return reporte como string
     */
    public static String crearReporteIntercambio(Matriz AAntes, Matriz BAntes, 
                                               Matriz ADespues, Matriz BDespues, 
                                               long tiempoEjecucion) {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("REPORTE DE INTERCAMBIO DE MATRICES\n");
        reporte.append("=".repeat(50)).append("\n");
        reporte.append("Fecha: ").append(java.time.LocalDateTime.now()).append("\n");
        reporte.append("Tiempo de ejecución: ").append(tiempoEjecucion).append(" ms\n\n");
        
        reporte.append("DIMENSIONES:\n");
        reporte.append("Matriz A: ").append(AAntes.getFilas()).append("x").append(AAntes.getColumnas()).append("\n");
        reporte.append("Matriz B: ").append(BAntes.getFilas()).append("x").append(BAntes.getColumnas()).append("\n\n");
        
        reporte.append("ESTADÍSTICAS ANTES:\n");
        double[] statsAAntes = AAntes.calcularEstadisticas();
        double[] statsBAntes = BAntes.calcularEstadisticas();
        reporte.append("A: min=").append((int)statsAAntes[0]).append(", max=").append((int)statsAAntes[1])
               .append(", promedio=").append(String.format("%.2f", statsAAntes[3])).append("\n");
        reporte.append("B: min=").append((int)statsBAntes[0]).append(", max=").append((int)statsBAntes[1])
               .append(", promedio=").append(String.format("%.2f", statsBAntes[3])).append("\n\n");
        
        reporte.append("ESTADÍSTICAS DESPUÉS:\n");
        double[] statsADespues = ADespues.calcularEstadisticas();
        double[] statsBDespues = BDespues.calcularEstadisticas();
        reporte.append("A: min=").append((int)statsADespues[0]).append(", max=").append((int)statsADespues[1])
               .append(", promedio=").append(String.format("%.2f", statsADespues[3])).append("\n");
        reporte.append("B: min=").append((int)statsBDespues[0]).append(", max=").append((int)statsBDespues[1])
               .append(", promedio=").append(String.format("%.2f", statsBDespues[3])).append("\n\n");
        
        // Verificar conservación
        double sumaAntes = statsAAntes[2] + statsBAntes[2];
        double sumaDespues = statsADespues[2] + statsBDespues[2];
        reporte.append("CONSERVACIÓN DE DATOS:\n");
        reporte.append("Suma total antes: ").append(String.format("%.0f", sumaAntes)).append("\n");
        reporte.append("Suma total después: ").append(String.format("%.0f", sumaDespues)).append("\n");
        reporte.append("Diferencia: ").append(String.format("%.0f", Math.abs(sumaAntes - sumaDespues))).append("\n");
        
        if (Math.abs(sumaAntes - sumaDespues) < 0.001) {
            reporte.append("Estado: ✅ Datos conservados correctamente\n");
        } else {
            reporte.append("Estado: ❌ Se perdieron datos\n");
        }
        
        return reporte.toString();
    }
    
    /**
     * Método principal para prueba independiente
     */
    public static void main(String[] args) {
        System.out.println("PRUEBA DEL VISUALIZADOR DE MATRICES");
        System.out.println("=".repeat(50));
        
        // Crear matrices pequeñas para prueba
        Matriz A = new Matriz(5, 8, "A_test");
        Matriz B = new Matriz(8, 5, "B_test");
        
        A.inicializarAleatoria(1, 20);
        B.inicializarAleatoria(21, 40);
        
        // Crear copias
        Matriz A_antes = A.clonar();
        Matriz B_antes = B.clonar();
        A_antes.setNombre("A_antes");
        B_antes.setNombre("B_antes");
        
        System.out.println("Matrices antes del intercambio:");
        mostrarMatrizCompleta(A_antes);
        System.out.println();
        mostrarMatrizCompleta(B_antes);
        
        // Simular intercambio (para prueba, intercambio manual)
        for (int i = 0; i < A.getFilas(); i++) {
            int[] filaA = A.getFila(i);
            int[] colB = B.getColumna(i);
            A.setFila(i, colB);
            B.setColumna(i, filaA);
        }
        
        System.out.println("\nMatrices después del intercambio:");
        mostrarMatrizCompleta(A);
        System.out.println();
        mostrarMatrizCompleta(B);
        
        // Mostrar comparación
        mostrarComparacion(A_antes, B_antes, A, B);
    }
}
