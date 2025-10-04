package tp5.actividad4;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase CalculoConcurrente que hereda de Operaciones e implementa Runnable.
 * Según el enunciado, realiza el cálculo de multiplicación de vectores
 * A[i][1-15] x B[1-15][j] y cada resultado se ingresa en la matriz C
 * de forma sincronizada.
 */
public class CalculoConcurrente extends Operaciones implements Runnable {
    private final int[][] matrizA;
    private final int[][] matrizB;
    private final MatrizConcurrente matrizC;
    private final int filaInicio;
    private final int filaFin;
    private final int hiloId;
    private final DateTimeFormatter timeFormatter;
    private int elementosCalculados;
    private long tiempoEjecucion;
    
    /**
     * Constructor de CalculoConcurrente
     * 
     * @param matrizA Matriz A de 20×15
     * @param matrizB Matriz B de 15×20
     * @param matrizC Matriz C de 20×20 (sincronizada)
     * @param filaInicio Primera fila a procesar (inclusive)
     * @param filaFin Última fila a procesar (inclusive)
     * @param hiloId Identificador del hilo para logging
     */
    public CalculoConcurrente(int[][] matrizA, int[][] matrizB, MatrizConcurrente matrizC,
                             int filaInicio, int filaFin, int hiloId) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.matrizC = matrizC;
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        this.hiloId = hiloId;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.elementosCalculados = 0;
        this.tiempoEjecucion = 0;
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        long inicioTiempo = System.currentTimeMillis();
        
        System.out.println("[" + tiempo + "] " + nombreHilo + " (ID:" + hiloId + 
                          "): Iniciando cálculo de filas " + filaInicio + "-" + filaFin);
        
        try {
            // Procesar el rango de filas asignado
            for (int i = filaInicio; i <= filaFin; i++) {
                procesarFila(i, nombreHilo);
            }
            
            tiempoEjecucion = System.currentTimeMillis() - inicioTiempo;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ " + nombreHilo + " completado: " + 
                             elementosCalculados + " elementos calculados en " + 
                             tiempoEjecucion + "ms");
            
        } catch (Exception e) {
            tiempo = LocalTime.now().format(timeFormatter);
            System.err.println("[" + tiempo + "] ❌ Error en " + nombreHilo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Procesa una fila completa de la matriz resultado C.
     * Para cada elemento C[i][j], calcula A[i][1-15] × B[1-15][j]
     * 
     * @param fila Índice de la fila a procesar
     * @param nombreHilo Nombre del hilo para logging
     */
    private void procesarFila(int fila, String nombreHilo) {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] " + nombreHilo + ": Procesando fila " + fila);
        
        // Calcular todos los elementos de la fila
        int[] filaResultado = new int[20]; // Matriz C es 20×20
        
        for (int j = 0; j < 20; j++) {
            // Calcular C[i][j] = A[i][1-15] × B[1-15][j]
            int resultado = calcularElementoMatriz(fila, j);
            
            // Aplicar operación de raíces heredada de la clase Operaciones
            // (según el enunciado, se debe usar el método de cálculo de raíces)
            double raizResultado = calcularRaiz(resultado);
            
            // Para este ejercicio, usaremos el resultado original de la multiplicación
            // pero demostramos el uso del método heredado
            filaResultado[j] = resultado;
            
            elementosCalculados++;
            
            // Log detallado cada ciertos elementos (opcional)
            if (elementosCalculados % 20 == 0) {
                System.out.println("  " + nombreHilo + ": Elemento C[" + fila + "][" + j + 
                                 "] = " + resultado + " (√" + resultado + " = " + 
                                 String.format("%.2f", raizResultado) + ")");
            }
        }
        
        // Establecer toda la fila de una vez (más eficiente que elemento por elemento)
        matrizC.establecerFila(fila, filaResultado);
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] " + nombreHilo + ": Fila " + fila + " completada");
    }
    
    /**
     * Calcula un elemento específico de la matriz resultado C[i][j].
     * Implementa la multiplicación de vectores: A[i][1-15] × B[1-15][j]
     * 
     * @param i Índice de fila en matriz A y matriz C
     * @param j Índice de columna en matriz B y matriz C
     * @return Resultado de la multiplicación de vectores
     */
    private int calcularElementoMatriz(int i, int j) {
        int suma = 0;
        
        // C[i][j] = Σ(k=0 to 14) A[i][k] × B[k][j]
        for (int k = 0; k < 15; k++) {
            suma += matrizA[i][k] * matrizB[k][j];
        }
        
        return suma;
    }
    
    /**
     * Método que demuestra el uso de operaciones matemáticas heredadas.
     * Aplica diferentes operaciones de la clase Operaciones a un resultado.
     * 
     * @param valor Valor sobre el cual aplicar operaciones
     * @return Información sobre las operaciones aplicadas
     */
    private String aplicarOperacionesMatematicas(int valor) {
        StringBuilder resultado = new StringBuilder();
        
        try {
            // Usar métodos heredados de la clase Operaciones
            double raizCuadrada = calcularRaiz(valor);
            double raizCubica = calcularRaiz(valor, 3);
            double valorAbs = valorAbsoluto(valor);
            double logaritmo = valor > 0 ? calcularLogaritmo(valor) : 0;
            
            resultado.append(String.format("Valor: %d, √: %.2f, ∛: %.2f, |x|: %.0f", 
                           valor, raizCuadrada, raizCubica, valorAbs));
            
            if (valor > 0) {
                resultado.append(String.format(", ln: %.2f", logaritmo));
            }
            
        } catch (Exception e) {
            resultado.append("Error en operaciones matemáticas: ").append(e.getMessage());
        }
        
        return resultado.toString();
    }
    
    /**
     * Método para validar que las matrices tienen las dimensiones correctas.
     * 
     * @return true si las dimensiones son correctas, false en caso contrario
     */
    private boolean validarDimensiones() {
        // Verificar dimensiones según el enunciado:
        // A: 20×15, B: 15×20, C: 20×20
        
        if (matrizA.length != 20 || matrizA[0].length != 15) {
            System.err.println("Error: Matriz A debe ser 20×15, actual: " + 
                             matrizA.length + "×" + matrizA[0].length);
            return false;
        }
        
        if (matrizB.length != 15 || matrizB[0].length != 20) {
            System.err.println("Error: Matriz B debe ser 15×20, actual: " + 
                             matrizB.length + "×" + matrizB[0].length);
            return false;
        }
        
        int[] dimensionesC = matrizC.getDimensiones();
        if (dimensionesC[0] != 20 || dimensionesC[1] != 20) {
            System.err.println("Error: Matriz C debe ser 20×20, actual: " + 
                             dimensionesC[0] + "×" + dimensionesC[1]);
            return false;
        }
        
        return true;
    }
    
    /**
     * Obtiene el número de elementos calculados por este hilo.
     * 
     * @return Número de elementos calculados
     */
    public int getElementosCalculados() {
        return elementosCalculados;
    }
    
    /**
     * Obtiene el tiempo de ejecución del hilo.
     * 
     * @return Tiempo de ejecución en milisegundos
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
    
    /**
     * Obtiene el ID del hilo.
     * 
     * @return ID del hilo
     */
    public int getHiloId() {
        return hiloId;
    }
    
    /**
     * Obtiene el rango de filas procesadas por este hilo.
     * 
     * @return Array con [filaInicio, filaFin]
     */
    public int[] getRangoFilas() {
        return new int[]{filaInicio, filaFin};
    }
    
    /**
     * Calcula el número de elementos que debe procesar este hilo.
     * 
     * @return Número total de elementos a procesar
     */
    public int calcularElementosAProcesar() {
        int filas = filaFin - filaInicio + 1;
        return filas * 20; // 20 columnas por fila
    }
    
    /**
     * Método para obtener estadísticas del hilo.
     * 
     * @return String con estadísticas del hilo
     */
    public String obtenerEstadisticas() {
        int elementosEsperados = calcularElementosAProcesar();
        double eficiencia = tiempoEjecucion > 0 ? 
            (elementosCalculados * 1000.0) / tiempoEjecucion : 0;
        
        return String.format(
            "Hilo %d: Filas %d-%d, Elementos %d/%d, Tiempo %dms, Eficiencia %.1f elem/seg",
            hiloId, filaInicio, filaFin, elementosCalculados, elementosEsperados, 
            tiempoEjecucion, eficiencia
        );
    }
    
    /**
     * Método toString para representación de la clase.
     * 
     * @return Representación string del cálculo concurrente
     */
    @Override
    public String toString() {
        return String.format("CalculoConcurrente[Hilo %d, Filas %d-%d, Elementos calculados: %d]", 
                           hiloId, filaInicio, filaFin, elementosCalculados);
    }
}
