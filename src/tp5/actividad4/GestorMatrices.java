package tp5.actividad4;

import java.util.Random;

/**
 * Clase GestorMatrices que maneja la inicialización y operaciones
 * de las matrices A y B según las especificaciones del enunciado.
 */
public class GestorMatrices {
    private static final int VALOR_MINIMO = 5;
    private static final int VALOR_MAXIMO = 15;
    private static final Random random = new Random();
    
    /**
     * Crea e inicializa la matriz A de 20×15 con valores aleatorios entre 5-15.
     * 
     * @return Matriz A inicializada
     */
    public static int[][] crearMatrizA() {
        int[][] matrizA = new int[20][15];
        
        System.out.println("Inicializando Matriz A (20×15) con valores aleatorios [5-15]...");
        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                matrizA[i][j] = VALOR_MINIMO + random.nextInt(VALOR_MAXIMO - VALOR_MINIMO + 1);
            }
        }
        
        System.out.println("✅ Matriz A inicializada correctamente");
        return matrizA;
    }
    
    /**
     * Crea e inicializa la matriz B de 15×20 con valores aleatorios entre 5-15.
     * 
     * @return Matriz B inicializada
     */
    public static int[][] crearMatrizB() {
        int[][] matrizB = new int[15][20];
        
        System.out.println("Inicializando Matriz B (15×20) con valores aleatorios [5-15]...");
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                matrizB[i][j] = VALOR_MINIMO + random.nextInt(VALOR_MAXIMO - VALOR_MINIMO + 1);
            }
        }
        
        System.out.println("✅ Matriz B inicializada correctamente");
        return matrizB;
    }
    
    /**
     * Crea matrices A y B con valores específicos para testing.
     * Útil para verificar la corrección del algoritmo.
     * 
     * @return Array con [matrizA, matrizB]
     */
    public static int[][][] crearMatricesPrueba() {
        System.out.println("Creando matrices de prueba con valores conocidos...");
        
        int[][] matrizA = new int[20][15];
        int[][] matrizB = new int[15][20];
        
        // Inicializar con valores simples para facilitar verificación
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                matrizA[i][j] = (i + j) % 10 + 1; // Valores 1-10
            }
        }
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                matrizB[i][j] = (i * j) % 8 + 1; // Valores 1-8
            }
        }
        
        System.out.println("✅ Matrices de prueba creadas");
        return new int[][][]{matrizA, matrizB};
    }
    
    /**
     * Valida que las matrices tengan las dimensiones correctas para multiplicación.
     * 
     * @param matrizA Matriz A a validar
     * @param matrizB Matriz B a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     */
    public static boolean validarDimensiones(int[][] matrizA, int[][] matrizB) {
        // Verificar que A es 20×15
        if (matrizA.length != 20) {
            System.err.println("Error: Matriz A debe tener 20 filas, tiene " + matrizA.length);
            return false;
        }
        
        for (int i = 0; i < matrizA.length; i++) {
            if (matrizA[i].length != 15) {
                System.err.println("Error: Matriz A fila " + i + " debe tener 15 columnas, tiene " + matrizA[i].length);
                return false;
            }
        }
        
        // Verificar que B es 15×20
        if (matrizB.length != 15) {
            System.err.println("Error: Matriz B debe tener 15 filas, tiene " + matrizB.length);
            return false;
        }
        
        for (int i = 0; i < matrizB.length; i++) {
            if (matrizB[i].length != 20) {
                System.err.println("Error: Matriz B fila " + i + " debe tener 20 columnas, tiene " + matrizB[i].length);
                return false;
            }
        }
        
        System.out.println("✅ Dimensiones de matrices validadas correctamente");
        return true;
    }
    
    /**
     * Valida que los valores de las matrices están en el rango especificado [5-15].
     * 
     * @param matrizA Matriz A a validar
     * @param matrizB Matriz B a validar
     * @return true si todos los valores están en rango, false en caso contrario
     */
    public static boolean validarRangos(int[][] matrizA, int[][] matrizB) {
        // Validar matriz A
        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                if (matrizA[i][j] < VALOR_MINIMO || matrizA[i][j] > VALOR_MAXIMO) {
                    System.err.println("Error: Matriz A[" + i + "][" + j + "] = " + matrizA[i][j] + 
                                     " está fuera del rango [" + VALOR_MINIMO + "-" + VALOR_MAXIMO + "]");
                    return false;
                }
            }
        }
        
        // Validar matriz B
        for (int i = 0; i < matrizB.length; i++) {
            for (int j = 0; j < matrizB[i].length; j++) {
                if (matrizB[i][j] < VALOR_MINIMO || matrizB[i][j] > VALOR_MAXIMO) {
                    System.err.println("Error: Matriz B[" + i + "][" + j + "] = " + matrizB[i][j] + 
                                     " está fuera del rango [" + VALOR_MINIMO + "-" + VALOR_MAXIMO + "]");
                    return false;
                }
            }
        }
        
        System.out.println("✅ Rangos de valores validados correctamente");
        return true;
    }
    
    /**
     * Calcula la multiplicación de matrices de forma secuencial para verificación.
     * 
     * @param matrizA Matriz A (20×15)
     * @param matrizB Matriz B (15×20)
     * @return Matriz resultado C (20×20)
     */
    public static int[][] multiplicarSecuencial(int[][] matrizA, int[][] matrizB) {
        System.out.println("Calculando multiplicación secuencial para verificación...");
        
        int[][] matrizC = new int[20][20];
        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int suma = 0;
                for (int k = 0; k < 15; k++) {
                    suma += matrizA[i][k] * matrizB[k][j];
                }
                matrizC[i][j] = suma;
            }
        }
        
        System.out.println("✅ Multiplicación secuencial completada");
        return matrizC;
    }
    
    /**
     * Compara dos matrices para verificar si son iguales.
     * 
     * @param matriz1 Primera matriz
     * @param matriz2 Segunda matriz
     * @return true si las matrices son iguales, false en caso contrario
     */
    public static boolean compararMatrices(int[][] matriz1, int[][] matriz2) {
        if (matriz1.length != matriz2.length) {
            return false;
        }
        
        for (int i = 0; i < matriz1.length; i++) {
            if (matriz1[i].length != matriz2[i].length) {
                return false;
            }
            
            for (int j = 0; j < matriz1[i].length; j++) {
                if (matriz1[i][j] != matriz2[i][j]) {
                    System.err.println("Diferencia encontrada en posición [" + i + "][" + j + "]: " +
                                     matriz1[i][j] + " vs " + matriz2[i][j]);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Muestra estadísticas de una matriz.
     * 
     * @param matriz Matriz a analizar
     * @param nombre Nombre de la matriz para el reporte
     */
    public static void mostrarEstadisticas(int[][] matriz, String nombre) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        int suma = 0;
        int minimo = Integer.MAX_VALUE;
        int maximo = Integer.MIN_VALUE;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int valor = matriz[i][j];
                suma += valor;
                minimo = Math.min(minimo, valor);
                maximo = Math.max(maximo, valor);
            }
        }
        
        double promedio = (double) suma / (filas * columnas);
        
        System.out.println("=== ESTADÍSTICAS DE MATRIZ " + nombre + " ===");
        System.out.println("Dimensiones: " + filas + "×" + columnas);
        System.out.println("Elementos totales: " + (filas * columnas));
        System.out.println("Suma total: " + suma);
        System.out.println("Valor mínimo: " + minimo);
        System.out.println("Valor máximo: " + maximo);
        System.out.println("Valor promedio: " + String.format("%.2f", promedio));
        System.out.println("=======================================");
    }
    
    /**
     * Muestra una representación parcial de una matriz.
     * 
     * @param matriz Matriz a mostrar
     * @param nombre Nombre de la matriz
     * @param maxFilas Máximo número de filas a mostrar
     * @param maxColumnas Máximo número de columnas a mostrar
     */
    public static void mostrarMatrizParcial(int[][] matriz, String nombre, int maxFilas, int maxColumnas) {
        System.out.println("Matriz " + nombre + " (primeras " + 
                          Math.min(maxFilas, matriz.length) + "×" + 
                          Math.min(maxColumnas, matriz[0].length) + " elementos):");
        
        for (int i = 0; i < Math.min(maxFilas, matriz.length); i++) {
            for (int j = 0; j < Math.min(maxColumnas, matriz[i].length); j++) {
                System.out.printf("%4d ", matriz[i][j]);
            }
            if (matriz[i].length > maxColumnas) {
                System.out.print("...");
            }
            System.out.println();
        }
        
        if (matriz.length > maxFilas) {
            System.out.println("...");
        }
        System.out.println();
    }
    
    /**
     * Crea una copia profunda de una matriz.
     * 
     * @param matriz Matriz original
     * @return Copia de la matriz
     */
    public static int[][] copiarMatriz(int[][] matriz) {
        int[][] copia = new int[matriz.length][];
        for (int i = 0; i < matriz.length; i++) {
            copia[i] = matriz[i].clone();
        }
        return copia;
    }
    
    /**
     * Obtiene información sobre los rangos de valores utilizados.
     * 
     * @return String con información de los rangos
     */
    public static String obtenerInformacionRangos() {
        return String.format("Rango de valores: [%d-%d]\n" +
                           "Valores posibles: %d\n" +
                           "Distribución: Uniforme aleatoria",
                           VALOR_MINIMO, VALOR_MAXIMO, 
                           VALOR_MAXIMO - VALOR_MINIMO + 1);
    }
}
