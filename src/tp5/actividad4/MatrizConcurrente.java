package tp5.actividad4;

/**
 * Clase MatrizConcurrente que encapsula la matriz resultado C y proporciona
 * acceso sincronizado para múltiples hilos. Esta es la matriz que será
 * sincronizada según el enunciado del ejercicio.
 */
public class MatrizConcurrente {
    private final int[][] matriz;
    private final int filas;
    private final int columnas;
    private int elementosCalculados;
    private final Object lock = new Object();
    
    /**
     * Constructor de MatrizConcurrente
     * @param filas Número de filas de la matriz (20)
     * @param columnas Número de columnas de la matriz (20)
     */
    public MatrizConcurrente(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new int[filas][columnas];
        this.elementosCalculados = 0;
        
        // Inicializar matriz en ceros
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = 0;
            }
        }
    }
    
    /**
     * Método sincronizado para establecer un elemento de la matriz.
     * Este es el punto de sincronización crítico donde múltiples hilos
     * pueden intentar escribir simultáneamente.
     * 
     * @param fila Índice de fila
     * @param columna Índice de columna
     * @param valor Valor a establecer
     * @throws IndexOutOfBoundsException Si los índices están fuera de rango
     */
    public synchronized void establecerElemento(int fila, int columna, int valor) {
        validarIndices(fila, columna);
        
        matriz[fila][columna] = valor;
        elementosCalculados++;
        
        // Opcional: Mostrar progreso cada cierto número de elementos
        if (elementosCalculados % 50 == 0) {
            double progreso = (elementosCalculados * 100.0) / (filas * columnas);
            System.out.println("Progreso: " + elementosCalculados + "/" + (filas * columnas) + 
                             " elementos (" + String.format("%.1f%%", progreso) + ")");
        }
    }
    
    /**
     * Método sincronizado para obtener un elemento de la matriz.
     * 
     * @param fila Índice de fila
     * @param columna Índice de columna
     * @return Valor del elemento en la posición especificada
     * @throws IndexOutOfBoundsException Si los índices están fuera de rango
     */
    public synchronized int obtenerElemento(int fila, int columna) {
        validarIndices(fila, columna);
        return matriz[fila][columna];
    }
    
    /**
     * Método sincronizado para establecer múltiples elementos de una fila.
     * Optimización para reducir overhead de sincronización cuando un hilo
     * calcula una fila completa.
     * 
     * @param fila Índice de fila
     * @param valores Array con los valores de la fila
     * @throws IndexOutOfBoundsException Si los índices están fuera de rango
     * @throws IllegalArgumentException Si el array de valores no tiene el tamaño correcto
     */
    public synchronized void establecerFila(int fila, int[] valores) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
        }
        if (valores.length != columnas) {
            throw new IllegalArgumentException("El array de valores debe tener " + columnas + " elementos");
        }
        
        for (int j = 0; j < columnas; j++) {
            matriz[fila][j] = valores[j];
        }
        elementosCalculados += columnas;
        
        System.out.println("Fila " + fila + " completada por " + Thread.currentThread().getName());
    }
    
    /**
     * Método sincronizado para obtener una fila completa.
     * 
     * @param fila Índice de fila
     * @return Array con los valores de la fila
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango
     */
    public synchronized int[] obtenerFila(int fila) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
        }
        
        int[] filaArray = new int[columnas];
        System.arraycopy(matriz[fila], 0, filaArray, 0, columnas);
        return filaArray;
    }
    
    /**
     * Método sincronizado para obtener una copia completa de la matriz.
     * Útil para verificación y presentación de resultados.
     * 
     * @return Copia de la matriz completa
     */
    public synchronized int[][] obtenerMatrizCompleta() {
        int[][] copia = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(matriz[i], 0, copia[i], 0, columnas);
        }
        return copia;
    }
    
    /**
     * Método para verificar si la matriz está completamente calculada.
     * 
     * @return true si todos los elementos han sido calculados
     */
    public synchronized boolean estaCompleta() {
        return elementosCalculados == (filas * columnas);
    }
    
    /**
     * Método para obtener el progreso del cálculo.
     * 
     * @return Porcentaje de elementos calculados (0-100)
     */
    public synchronized double obtenerProgreso() {
        return (elementosCalculados * 100.0) / (filas * columnas);
    }
    
    /**
     * Método para obtener el número de elementos calculados.
     * 
     * @return Número de elementos que han sido establecidos
     */
    public synchronized int getElementosCalculados() {
        return elementosCalculados;
    }
    
    /**
     * Método para obtener las dimensiones de la matriz.
     * 
     * @return Array con [filas, columnas]
     */
    public int[] getDimensiones() {
        return new int[]{filas, columnas};
    }
    
    /**
     * Método para reiniciar la matriz (útil para múltiples ejecuciones).
     */
    public synchronized void reiniciar() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = 0;
            }
        }
        elementosCalculados = 0;
        System.out.println("Matriz reiniciada a estado inicial");
    }
    
    /**
     * Método privado para validar índices.
     * 
     * @param fila Índice de fila
     * @param columna Índice de columna
     * @throws IndexOutOfBoundsException Si algún índice está fuera de rango
     */
    private void validarIndices(int fila, int columna) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila + " (debe estar entre 0 y " + (filas - 1) + ")");
        }
        if (columna < 0 || columna >= columnas) {
            throw new IndexOutOfBoundsException("Columna fuera de rango: " + columna + " (debe estar entre 0 y " + (columnas - 1) + ")");
        }
    }
    
    /**
     * Método para mostrar estadísticas de la matriz.
     */
    public synchronized void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS DE MATRIZ CONCURRENTE ===");
        System.out.println("Dimensiones: " + filas + "×" + columnas);
        System.out.println("Elementos totales: " + (filas * columnas));
        System.out.println("Elementos calculados: " + elementosCalculados);
        System.out.println("Progreso: " + String.format("%.2f%%", obtenerProgreso()));
        System.out.println("Estado: " + (estaCompleta() ? "COMPLETA" : "EN PROGRESO"));
        System.out.println("==========================================");
    }
    
    /**
     * Método para mostrar una representación parcial de la matriz.
     * Muestra solo las primeras filas y columnas para evitar output excesivo.
     * 
     * @param maxFilas Máximo número de filas a mostrar
     * @param maxColumnas Máximo número de columnas a mostrar
     */
    public synchronized void mostrarMatrizParcial(int maxFilas, int maxColumnas) {
        System.out.println("Matriz C (primeras " + Math.min(maxFilas, filas) + "×" + 
                          Math.min(maxColumnas, columnas) + " elementos):");
        
        for (int i = 0; i < Math.min(maxFilas, filas); i++) {
            for (int j = 0; j < Math.min(maxColumnas, columnas); j++) {
                System.out.printf("%6d ", matriz[i][j]);
            }
            if (columnas > maxColumnas) {
                System.out.print("...");
            }
            System.out.println();
        }
        
        if (filas > maxFilas) {
            System.out.println("...");
        }
    }
    
    /**
     * Método toString para representación de la clase.
     * 
     * @return Representación string de la matriz concurrente
     */
    @Override
    public String toString() {
        return String.format("MatrizConcurrente[%d×%d, %d/%d elementos calculados (%.1f%%)]", 
                           filas, columnas, elementosCalculados, filas * columnas, obtenerProgreso());
    }
}
