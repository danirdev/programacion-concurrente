package tp3.actividad6;

import java.util.Random;

/**
 * Clase para representar y manipular matrices de enteros
 * Proporciona operaciones básicas y utilidades para visualización
 * @author PC2025
 */
public class Matriz {
    
    private int[][] datos;
    private int filas;
    private int columnas;
    private String nombre;
    
    /**
     * Constructor que crea una matriz con dimensiones especificadas
     * @param filas número de filas
     * @param columnas número de columnas
     * @param nombre nombre identificador de la matriz
     */
    public Matriz(int filas, int columnas, String nombre) {
        this.filas = filas;
        this.columnas = columnas;
        this.nombre = nombre;
        this.datos = new int[filas][columnas];
    }
    
    /**
     * Constructor de copia
     * @param otra matriz a copiar
     * @param nuevoNombre nuevo nombre para la copia
     */
    public Matriz(Matriz otra, String nuevoNombre) {
        this.filas = otra.filas;
        this.columnas = otra.columnas;
        this.nombre = nuevoNombre;
        this.datos = new int[filas][columnas];
        
        // Copiar datos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.datos[i][j] = otra.datos[i][j];
            }
        }
    }
    
    /**
     * Inicializa la matriz con números aleatorios en el rango especificado
     * @param min valor mínimo (inclusivo)
     * @param max valor máximo (inclusivo)
     */
    public void inicializarAleatoria(int min, int max) {
        Random random = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = min + random.nextInt(max - min + 1);
            }
        }
    }
    
    /**
     * Inicializa la matriz con números aleatorios entre 1 y 100
     */
    public void inicializarAleatoria() {
        inicializarAleatoria(1, 100);
    }
    
    /**
     * Obtiene el valor en la posición especificada
     * @param fila índice de fila
     * @param columna índice de columna
     * @return valor en la posición
     */
    public int get(int fila, int columna) {
        validarIndices(fila, columna);
        return datos[fila][columna];
    }
    
    /**
     * Establece el valor en la posición especificada
     * @param fila índice de fila
     * @param columna índice de columna
     * @param valor valor a establecer
     */
    public void set(int fila, int columna, int valor) {
        validarIndices(fila, columna);
        datos[fila][columna] = valor;
    }
    
    /**
     * Obtiene una fila completa como array
     * @param fila índice de fila
     * @return array con los valores de la fila
     */
    public int[] getFila(int fila) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
        }
        
        int[] resultado = new int[columnas];
        System.arraycopy(datos[fila], 0, resultado, 0, columnas);
        return resultado;
    }
    
    /**
     * Obtiene una columna completa como array
     * @param columna índice de columna
     * @return array con los valores de la columna
     */
    public int[] getColumna(int columna) {
        if (columna < 0 || columna >= columnas) {
            throw new IndexOutOfBoundsException("Columna fuera de rango: " + columna);
        }
        
        int[] resultado = new int[filas];
        for (int i = 0; i < filas; i++) {
            resultado[i] = datos[i][columna];
        }
        return resultado;
    }
    
    /**
     * Establece una fila completa
     * @param fila índice de fila
     * @param valores array con los nuevos valores
     */
    public void setFila(int fila, int[] valores) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
        }
        if (valores.length != columnas) {
            throw new IllegalArgumentException("El array debe tener " + columnas + " elementos");
        }
        
        System.arraycopy(valores, 0, datos[fila], 0, columnas);
    }
    
    /**
     * Establece una columna completa
     * @param columna índice de columna
     * @param valores array con los nuevos valores
     */
    public void setColumna(int columna, int[] valores) {
        if (columna < 0 || columna >= columnas) {
            throw new IndexOutOfBoundsException("Columna fuera de rango: " + columna);
        }
        if (valores.length != filas) {
            throw new IllegalArgumentException("El array debe tener " + filas + " elementos");
        }
        
        for (int i = 0; i < filas; i++) {
            datos[i][columna] = valores[i];
        }
    }
    
    /**
     * Valida que los índices estén dentro del rango válido
     * @param fila índice de fila
     * @param columna índice de columna
     */
    private void validarIndices(int fila, int columna) {
        if (fila < 0 || fila >= filas) {
            throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
        }
        if (columna < 0 || columna >= columnas) {
            throw new IndexOutOfBoundsException("Columna fuera de rango: " + columna);
        }
    }
    
    /**
     * Obtiene el número de filas
     * @return número de filas
     */
    public int getFilas() {
        return filas;
    }
    
    /**
     * Obtiene el número de columnas
     * @return número de columnas
     */
    public int getColumnas() {
        return columnas;
    }
    
    /**
     * Obtiene el nombre de la matriz
     * @return nombre de la matriz
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece un nuevo nombre para la matriz
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Calcula estadísticas básicas de la matriz
     * @return array con [min, max, suma, promedio]
     */
    public double[] calcularEstadisticas() {
        int min = datos[0][0];
        int max = datos[0][0];
        long suma = 0;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int valor = datos[i][j];
                if (valor < min) min = valor;
                if (valor > max) max = valor;
                suma += valor;
            }
        }
        
        double promedio = (double) suma / (filas * columnas);
        return new double[]{min, max, suma, promedio};
    }
    
    /**
     * Genera una representación en string de la matriz
     * Para matrices grandes, muestra solo las esquinas
     * @return representación en string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matriz ").append(nombre).append(" (").append(filas).append("x").append(columnas).append("):\n");
        
        if (filas <= 10 && columnas <= 10) {
            // Mostrar matriz completa si es pequeña
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    sb.append(String.format("%4d ", datos[i][j]));
                }
                sb.append("\n");
            }
        } else {
            // Mostrar solo esquinas para matrices grandes
            sb.append("Esquina superior izquierda (5x5):\n");
            for (int i = 0; i < Math.min(5, filas); i++) {
                for (int j = 0; j < Math.min(5, columnas); j++) {
                    sb.append(String.format("%4d ", datos[i][j]));
                }
                sb.append("\n");
            }
            
            if (filas > 5 && columnas > 5) {
                sb.append("...\n");
                sb.append("Esquina inferior derecha (5x5):\n");
                for (int i = Math.max(0, filas - 5); i < filas; i++) {
                    for (int j = Math.max(0, columnas - 5); j < columnas; j++) {
                        sb.append(String.format("%4d ", datos[i][j]));
                    }
                    sb.append("\n");
                }
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Genera un resumen compacto de la matriz
     * @return resumen con dimensiones y estadísticas
     */
    public String toResumen() {
        double[] stats = calcularEstadisticas();
        return String.format("%s (%dx%d): min=%d, max=%d, promedio=%.2f", 
                           nombre, filas, columnas, (int)stats[0], (int)stats[1], stats[3]);
    }
    
    /**
     * Verifica si esta matriz es compatible para intercambio con otra
     * @param otra matriz a verificar
     * @return true si son compatibles para intercambio
     */
    public boolean esCompatibleParaIntercambio(Matriz otra) {
        return this.filas == otra.columnas && this.columnas == otra.filas;
    }
    
    /**
     * Crea una copia profunda de la matriz
     * @return nueva matriz con los mismos datos
     */
    public Matriz clonar() {
        return new Matriz(this, this.nombre + "_copia");
    }
}
