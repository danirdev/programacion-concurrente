package tp3.actividad1;

/**
 * Clase de prueba básica para demostrar el comportamiento de los hilos
 * Versión simplificada para observar claramente la concurrencia
 * @author PC2025
 */
public class TestBasico {
    
    /**
     * Ejecuta una prueba simple con dos hilos
     * @param usarJoin si debe usar join() para esperar los hilos
     */
    public static void ejecutarPruebaSimple(boolean usarJoin) {
        System.out.println("=== PRUEBA " + (usarJoin ? "CON" : "SIN") + " JOIN ===");
        
        // Crear los hilos
        HiloX hiloX = new HiloX("HiloX");
        HiloY hiloY = new HiloY("HiloY");
        
        // Registrar tiempo de inicio
        long inicio = System.currentTimeMillis();
        
        // Iniciar los hilos
        System.out.println("Iniciando hilos...");
        hiloX.start();
        hiloY.start();
        
        if (usarJoin) {
            try {
                // Esperar que ambos hilos terminen
                hiloX.join();
                hiloY.join();
                System.out.println("Ambos hilos han terminado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Hilo principal interrumpido");
            }
        } else {
            // Sin join - continuar inmediatamente
            System.out.println("Continuando sin esperar hilos...");
        }
        
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo transcurrido: " + (fin - inicio) + " ms");
        System.out.println("=== FIN PRUEBA ===\n");
    }
    
    /**
     * Demuestra la diferencia entre usar y no usar join()
     */
    public static void demostrarDiferenciaJoin() {
        System.out.println("DEMOSTRACIÓN: DIFERENCIA ENTRE CON Y SIN JOIN()");
        System.out.println("=".repeat(60));
        
        // Prueba sin join
        ejecutarPruebaSimple(false);
        
        // Pausa para separar las pruebas
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Prueba con join
        ejecutarPruebaSimple(true);
    }
    
    /**
     * Ejecuta múltiples pruebas para observar variabilidad
     */
    public static void ejecutarMultiplesPruebas() {
        System.out.println("MÚLTIPLES EJECUCIONES PARA OBSERVAR VARIABILIDAD");
        System.out.println("=".repeat(60));
        
        for (int i = 1; i <= 5; i++) {
            System.out.println("--- Ejecución " + i + " ---");
            
            HiloX hiloX = new HiloX("X" + i);
            HiloY hiloY = new HiloY("Y" + i);
            
            hiloX.start();
            hiloY.start();
            
            try {
                hiloX.join();
                hiloY.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            System.out.println("Ejecución " + i + " completada\n");
            
            // Pausa entre ejecuciones
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * Método principal para ejecutar las pruebas básicas
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 1 - PRUEBAS BÁSICAS DE HILOS");
        System.out.println("=".repeat(80));
        
        // Demostrar diferencia con join
        demostrarDiferenciaJoin();
        
        // Pausa entre secciones
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Ejecutar múltiples pruebas
        ejecutarMultiplesPruebas();
        
        System.out.println("=".repeat(80));
        System.out.println("PRUEBAS BÁSICAS COMPLETADAS");
        System.out.println("Para análisis completo, ejecutar ExperimentoHilos.main()");
        System.out.println("=".repeat(80));
    }
}
