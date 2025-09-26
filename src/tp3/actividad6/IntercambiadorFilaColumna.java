package tp3.actividad6;

/**
 * Hilo que realiza el intercambio entre una fila de matriz A y una columna de matriz B
 * Implementa el intercambio atómico sin pérdida de información
 * @author PC2025
 */
public class IntercambiadorFilaColumna extends Thread {
    
    private Matriz matrizA;
    private Matriz matrizB;
    private int indice; // índice de fila en A y columna en B
    private boolean completado;
    private Exception error;
    
    /**
     * Constructor del intercambiador
     * @param matrizA matriz A (100x200)
     * @param matrizB matriz B (200x100)
     * @param indice índice de la fila de A y columna de B a intercambiar
     */
    public IntercambiadorFilaColumna(Matriz matrizA, Matriz matrizB, int indice) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.indice = indice;
        this.completado = false;
        this.error = null;
        this.setName("Intercambiador-" + indice);
    }
    
    /**
     * Método run que ejecuta el intercambio
     * Intercambia fila[indice] de A con columna[indice] de B
     */
    @Override
    public void run() {
        try {
            System.out.println("[" + getName() + "] Iniciando intercambio fila " + indice + 
                              " de A ↔ columna " + indice + " de B");
            
            // Validar índices
            if (indice < 0 || indice >= matrizA.getFilas() || indice >= matrizB.getColumnas()) {
                throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
            }
            
            // Obtener fila de A y columna de B
            int[] filaA = matrizA.getFila(indice);
            int[] columnaB = matrizB.getColumna(indice);
            
            // Verificar compatibilidad de tamaños
            if (filaA.length != matrizB.getFilas() || columnaB.length != matrizA.getColumnas()) {
                throw new IllegalStateException("Tamaños incompatibles para intercambio en índice " + indice);
            }
            
            // Realizar intercambio atómico
            // Fila de A → Columna de B
            matrizB.setColumna(indice, filaA);
            
            // Columna de B → Fila de A  
            matrizA.setFila(indice, columnaB);
            
            completado = true;
            System.out.println("[" + getName() + "] Intercambio completado exitosamente");
            
        } catch (Exception e) {
            this.error = e;
            System.err.println("[" + getName() + "] Error durante intercambio: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si el intercambio ha completado exitosamente
     * @return true si completó sin errores
     */
    public boolean isCompletado() {
        return completado && error == null;
    }
    
    /**
     * Verifica si hubo error durante el intercambio
     * @return true si hubo error
     */
    public boolean tieneError() {
        return error != null;
    }
    
    /**
     * Obtiene el error ocurrido durante el intercambio
     * @return excepción ocurrida o null si no hubo error
     */
    public Exception getError() {
        return error;
    }
    
    /**
     * Obtiene el índice que maneja este intercambiador
     * @return índice de fila/columna
     */
    public int getIndice() {
        return indice;
    }
    
    /**
     * Obtiene información detallada del intercambiador
     * @return string con información del hilo
     */
    public String getInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Hilo: ").append(getName()).append("\n");
        info.append("Índice: ").append(indice).append("\n");
        info.append("Operación: Fila ").append(indice).append(" de A ↔ Columna ").append(indice).append(" de B\n");
        info.append("Estado: ");
        
        if (completado && error == null) {
            info.append("Completado exitosamente");
        } else if (error != null) {
            info.append("Error: ").append(error.getMessage());
        } else {
            info.append("En ejecución");
        }
        
        return info.toString();
    }
    
    /**
     * Espera a que el intercambio complete y verifica el resultado
     * @throws InterruptedException si el hilo es interrumpido
     * @throws RuntimeException si ocurrió un error durante el intercambio
     */
    public void esperarYVerificar() throws InterruptedException {
        this.join(); // Esperar a que termine
        
        if (tieneError()) {
            throw new RuntimeException("Error en intercambio " + indice + ": " + error.getMessage(), error);
        }
        
        if (!isCompletado()) {
            throw new RuntimeException("Intercambio " + indice + " no completó correctamente");
        }
    }
    
    /**
     * Método estático para crear y ejecutar un intercambiador
     * @param matrizA matriz A
     * @param matrizB matriz B
     * @param indice índice a intercambiar
     * @return hilo creado y iniciado
     */
    public static IntercambiadorFilaColumna crearYEjecutar(Matriz matrizA, Matriz matrizB, int indice) {
        IntercambiadorFilaColumna intercambiador = new IntercambiadorFilaColumna(matrizA, matrizB, indice);
        intercambiador.start();
        return intercambiador;
    }
    
    /**
     * Realiza un intercambio secuencial para comparación
     * @param matrizA matriz A
     * @param matrizB matriz B
     * @param indice índice a intercambiar
     */
    public static void intercambioSecuencial(Matriz matrizA, Matriz matrizB, int indice) {
        System.out.println("Intercambio secuencial en índice " + indice);
        
        // Obtener datos
        int[] filaA = matrizA.getFila(indice);
        int[] columnaB = matrizB.getColumna(indice);
        
        // Realizar intercambio
        matrizB.setColumna(indice, filaA);
        matrizA.setFila(indice, columnaB);
        
        System.out.println("Intercambio secuencial " + indice + " completado");
    }
    
    /**
     * Método para prueba individual del intercambiador
     */
    public static void main(String[] args) {
        System.out.println("PRUEBA INDIVIDUAL DE INTERCAMBIADOR");
        System.out.println("=".repeat(40));
        
        // Crear matrices pequeñas para prueba
        Matriz A = new Matriz(3, 4, "A_prueba");
        Matriz B = new Matriz(4, 3, "B_prueba");
        
        // Inicializar con valores conocidos
        A.inicializarAleatoria(1, 10);
        B.inicializarAleatoria(11, 20);
        
        System.out.println("Matrices antes del intercambio:");
        System.out.println(A);
        System.out.println(B);
        
        // Realizar intercambio en índice 1
        int indiceTest = 1;
        System.out.println("Intercambiando fila " + indiceTest + " de A con columna " + indiceTest + " de B");
        
        IntercambiadorFilaColumna intercambiador = new IntercambiadorFilaColumna(A, B, indiceTest);
        
        long tiempoInicio = System.nanoTime();
        intercambiador.start();
        
        try {
            intercambiador.join();
            long tiempoFin = System.nanoTime();
            
            System.out.println("\nMatrices después del intercambio:");
            System.out.println(A);
            System.out.println(B);
            
            System.out.println("Información del intercambiador:");
            System.out.println(intercambiador.getInformacion());
            
            System.out.println("Tiempo de intercambio: " + 
                              String.format("%.3f", (tiempoFin - tiempoInicio) / 1_000_000.0) + " ms");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Prueba interrumpida");
        }
    }
}
