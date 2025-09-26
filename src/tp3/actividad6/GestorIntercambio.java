package tp3.actividad6;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor que coordina el intercambio concurrente entre matrices
 * Maneja múltiples hilos intercambiadores y verifica la correctitud
 * @author PC2025
 */
public class GestorIntercambio {
    
    private Matriz matrizA;
    private Matriz matrizB;
    private List<IntercambiadorFilaColumna> intercambiadores;
    private boolean intercambioCompletado;
    
    /**
     * Constructor del gestor de intercambio
     * @param matrizA matriz A (100x200)
     * @param matrizB matriz B (200x100)
     */
    public GestorIntercambio(Matriz matrizA, Matriz matrizB) {
        if (!matrizA.esCompatibleParaIntercambio(matrizB)) {
            throw new IllegalArgumentException("Las matrices no son compatibles para intercambio");
        }
        
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.intercambiadores = new ArrayList<>();
        this.intercambioCompletado = false;
    }
    
    /**
     * Ejecuta el intercambio concurrente usando múltiples hilos
     * @return tiempo de ejecución en milisegundos
     */
    public long ejecutarIntercambioConcurrente() {
        System.out.println("=== INTERCAMBIO CONCURRENTE DE MATRICES ===");
        System.out.println("Matriz A: " + matrizA.getFilas() + "x" + matrizA.getColumnas());
        System.out.println("Matriz B: " + matrizB.getFilas() + "x" + matrizB.getColumnas());
        System.out.println("Hilos a crear: " + matrizA.getFilas());
        System.out.println("-".repeat(50));
        
        intercambiadores.clear();
        
        long tiempoInicio = System.nanoTime();
        
        // Crear e iniciar todos los hilos intercambiadores
        System.out.println("Creando " + matrizA.getFilas() + " hilos intercambiadores...");
        for (int i = 0; i < matrizA.getFilas(); i++) {
            IntercambiadorFilaColumna intercambiador = new IntercambiadorFilaColumna(matrizA, matrizB, i);
            intercambiadores.add(intercambiador);
            intercambiador.start();
        }
        
        // Esperar que todos los hilos terminen
        System.out.println("Esperando que todos los intercambiadores terminen...");
        int completados = 0;
        int errores = 0;
        
        try {
            for (IntercambiadorFilaColumna intercambiador : intercambiadores) {
                intercambiador.join();
                
                if (intercambiador.isCompletado()) {
                    completados++;
                } else if (intercambiador.tieneError()) {
                    errores++;
                    System.err.println("Error en " + intercambiador.getName() + ": " + 
                                     intercambiador.getError().getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Intercambio interrumpido");
            return -1;
        }
        
        long tiempoFin = System.nanoTime();
        long tiempoEjecucion = (tiempoFin - tiempoInicio) / 1_000_000; // convertir a ms
        
        // Mostrar resultados
        System.out.println("-".repeat(50));
        System.out.println("RESULTADOS DEL INTERCAMBIO CONCURRENTE:");
        System.out.printf("Intercambiadores completados: %d/%d%n", completados, intercambiadores.size());
        System.out.printf("Errores: %d%n", errores);
        System.out.printf("Tiempo de ejecución: %d ms%n", tiempoEjecucion);
        
        intercambioCompletado = (errores == 0 && completados == intercambiadores.size());
        
        if (intercambioCompletado) {
            System.out.println("✅ Intercambio concurrente completado exitosamente");
        } else {
            System.out.println("⚠️ Intercambio completado con errores");
        }
        
        return tiempoEjecucion;
    }
    
    /**
     * Ejecuta el intercambio de forma secuencial para comparación
     * @return tiempo de ejecución en milisegundos
     */
    public long ejecutarIntercambioSecuencial() {
        System.out.println("=== INTERCAMBIO SECUENCIAL DE MATRICES ===");
        System.out.println("Intercambiando " + matrizA.getFilas() + " pares fila-columna...");
        System.out.println("-".repeat(50));
        
        long tiempoInicio = System.nanoTime();
        
        // Realizar intercambios uno por uno
        for (int i = 0; i < matrizA.getFilas(); i++) {
            System.out.println("Intercambiando fila " + i + " de A ↔ columna " + i + " de B");
            IntercambiadorFilaColumna.intercambioSecuencial(matrizA, matrizB, i);
        }
        
        long tiempoFin = System.nanoTime();
        long tiempoEjecucion = (tiempoFin - tiempoInicio) / 1_000_000;
        
        System.out.println("-".repeat(50));
        System.out.printf("✅ Intercambio secuencial completado en %d ms%n", tiempoEjecucion);
        
        return tiempoEjecucion;
    }
    
    /**
     * Compara el rendimiento entre intercambio concurrente y secuencial
     */
    public void compararRendimiento() {
        System.out.println("COMPARACIÓN DE RENDIMIENTO");
        System.out.println("=".repeat(60));
        
        // Crear copias de las matrices para comparación justa
        Matriz A_copia1 = matrizA.clonar();
        Matriz B_copia1 = matrizB.clonar();
        A_copia1.setNombre("A_secuencial");
        B_copia1.setNombre("B_secuencial");
        
        Matriz A_copia2 = matrizA.clonar();
        Matriz B_copia2 = matrizB.clonar();
        A_copia2.setNombre("A_concurrente");
        B_copia2.setNombre("B_concurrente");
        
        // Ejecutar versión secuencial
        GestorIntercambio gestorSecuencial = new GestorIntercambio(A_copia1, B_copia1);
        long tiempoSecuencial = gestorSecuencial.ejecutarIntercambioSecuencial();
        
        System.out.println("\n" + "=".repeat(60));
        
        // Ejecutar versión concurrente
        GestorIntercambio gestorConcurrente = new GestorIntercambio(A_copia2, B_copia2);
        long tiempoConcurrente = gestorConcurrente.ejecutarIntercambioConcurrente();
        
        // Análisis de resultados
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ANÁLISIS DE RENDIMIENTO:");
        System.out.printf("Tiempo secuencial:  %6d ms%n", tiempoSecuencial);
        System.out.printf("Tiempo concurrente: %6d ms%n", tiempoConcurrente);
        
        if (tiempoConcurrente > 0) {
            double speedup = (double) tiempoSecuencial / tiempoConcurrente;
            double mejora = ((double) (tiempoSecuencial - tiempoConcurrente) / tiempoSecuencial) * 100;
            
            System.out.printf("Speedup: %.2fx%n", speedup);
            System.out.printf("Mejora: %.1f%%%n", mejora);
            
            if (speedup > 1.2) {
                System.out.println("✅ La concurrencia mejora significativamente el rendimiento");
            } else if (speedup > 0.8) {
                System.out.println("⚠️ Rendimiento similar entre ambas versiones");
            } else {
                System.out.println("⚠️ La versión secuencial es más rápida (overhead de hilos)");
            }
        }
    }
    
    /**
     * Verifica que el intercambio se haya realizado correctamente
     * @param matrizAOriginal matriz A antes del intercambio
     * @param matrizBOriginal matriz B antes del intercambio
     * @return true si el intercambio es correcto
     */
    public boolean verificarCorrectitud(Matriz matrizAOriginal, Matriz matrizBOriginal) {
        System.out.println("=== VERIFICACIÓN DE CORRECTITUD ===");
        
        boolean correcto = true;
        int erroresEncontrados = 0;
        
        // Verificar que cada fila de A original esté ahora en la columna correspondiente de B
        for (int i = 0; i < matrizA.getFilas(); i++) {
            int[] filaAOriginal = matrizAOriginal.getFila(i);
            int[] columnaBActual = matrizB.getColumna(i);
            
            if (!sonIguales(filaAOriginal, columnaBActual)) {
                System.err.printf("Error: Fila %d de A original no coincide con columna %d de B actual%n", i, i);
                erroresEncontrados++;
                correcto = false;
            }
        }
        
        // Verificar que cada columna de B original esté ahora en la fila correspondiente de A
        for (int i = 0; i < matrizB.getColumnas(); i++) {
            int[] columnaBOriginal = matrizBOriginal.getColumna(i);
            int[] filaAActual = matrizA.getFila(i);
            
            if (!sonIguales(columnaBOriginal, filaAActual)) {
                System.err.printf("Error: Columna %d de B original no coincide con fila %d de A actual%n", i, i);
                erroresEncontrados++;
                correcto = false;
            }
        }
        
        System.out.println("-".repeat(40));
        if (correcto) {
            System.out.println("✅ Verificación exitosa: El intercambio es correcto");
            System.out.println("✅ Todos los datos se intercambiaron sin pérdida");
        } else {
            System.out.printf("❌ Verificación fallida: %d errores encontrados%n", erroresEncontrados);
        }
        
        return correcto;
    }
    
    /**
     * Verifica si dos arrays son iguales
     * @param array1 primer array
     * @param array2 segundo array
     * @return true si son iguales
     */
    private boolean sonIguales(int[] array1, int[] array2) {
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
     * Obtiene estadísticas de los intercambiadores
     * @return string con estadísticas detalladas
     */
    public String getEstadisticasIntercambiadores() {
        if (intercambiadores.isEmpty()) {
            return "No se han ejecutado intercambiadores";
        }
        
        StringBuilder stats = new StringBuilder();
        stats.append("ESTADÍSTICAS DE INTERCAMBIADORES:\n");
        stats.append("-".repeat(40)).append("\n");
        
        int completados = 0;
        int errores = 0;
        
        for (IntercambiadorFilaColumna intercambiador : intercambiadores) {
            if (intercambiador.isCompletado()) {
                completados++;
            } else if (intercambiador.tieneError()) {
                errores++;
            }
        }
        
        stats.append("Total de intercambiadores: ").append(intercambiadores.size()).append("\n");
        stats.append("Completados exitosamente: ").append(completados).append("\n");
        stats.append("Con errores: ").append(errores).append("\n");
        stats.append("Tasa de éxito: ").append(String.format("%.1f%%", 
                    (completados * 100.0) / intercambiadores.size())).append("\n");
        
        return stats.toString();
    }
    
    /**
     * Método principal para prueba independiente
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 6 - GESTOR DE INTERCAMBIO");
        System.out.println("=".repeat(60));
        
        // Crear matrices según el enunciado
        System.out.println("Creando matrices A (100x200) y B (200x100)...");
        Matriz A = new Matriz(100, 200, "A");
        Matriz B = new Matriz(200, 100, "B");
        
        // Inicializar con números aleatorios 1-100
        System.out.println("Inicializando con números aleatorios (1-100)...");
        A.inicializarAleatoria(1, 100);
        B.inicializarAleatoria(1, 100);
        
        // Crear copias para verificación
        Matriz A_original = A.clonar();
        Matriz B_original = B.clonar();
        A_original.setNombre("A_original");
        B_original.setNombre("B_original");
        
        // Mostrar estadísticas antes del intercambio
        System.out.println("\nEstadísticas antes del intercambio:");
        System.out.println(A.toResumen());
        System.out.println(B.toResumen());
        
        // Ejecutar intercambio concurrente
        GestorIntercambio gestor = new GestorIntercambio(A, B);
        long tiempo = gestor.ejecutarIntercambioConcurrente();
        
        // Mostrar estadísticas después del intercambio
        System.out.println("\nEstadísticas después del intercambio:");
        System.out.println(A.toResumen());
        System.out.println(B.toResumen());
        
        // Verificar correctitud
        gestor.verificarCorrectitud(A_original, B_original);
        
        // Mostrar estadísticas de intercambiadores
        System.out.println("\n" + gestor.getEstadisticasIntercambiadores());
        
        System.out.println("\n=".repeat(60));
        System.out.println("INTERCAMBIO COMPLETADO");
    }
}
