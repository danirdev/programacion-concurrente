package tp3.actividad2;

/**
 * Clase de prueba básica para demostrar el problema del contador compartido
 * Versión simplificada para entender los conceptos fundamentales
 * @author PC2025
 */
public class TestContador {
    
    /**
     * Ejecuta una prueba simple con contador sin sincronización
     */
    public static void pruebaBasicaSinSincronizacion() {
        System.out.println("=== PRUEBA BÁSICA SIN SINCRONIZACIÓN ===");
        
        // Crear contador inicializado en 100
        Contador contador = new Contador(100);
        System.out.println("Valor inicial: " + contador.getValor());
        
        // Crear hilos
        HiloIncrementador incrementador = new HiloIncrementador(contador);
        HiloDecrementador decrementador = new HiloDecrementador(contador);
        
        // Ejecutar hilos
        System.out.println("Iniciando hilos...");
        incrementador.start();
        decrementador.start();
        
        try {
            // Esperar que ambos hilos terminen
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Prueba interrumpida");
        }
        
        // Mostrar resultado
        int valorFinal = contador.getValor();
        System.out.println("Valor final: " + valorFinal);
        System.out.println("Valor esperado: 100");
        
        if (valorFinal == 100) {
            System.out.println("✅ Resultado correcto (¡por casualidad!)");
        } else {
            System.out.println("⚠️  Race condition detectada - Diferencia: " + 
                              (valorFinal - 100));
        }
        
        System.out.println();
    }
    
    /**
     * Ejecuta una prueba con contador sincronizado
     */
    public static void pruebaBasicaConSincronizacion() {
        System.out.println("=== PRUEBA BÁSICA CON SINCRONIZACIÓN ===");
        
        // Crear contador sincronizado inicializado en 100
        ContadorSincronizado contador = new ContadorSincronizado(100);
        System.out.println("Valor inicial: 100");
        
        // Crear hilos usando métodos sincronizados
        Thread incrementador = new Thread(() -> {
            System.out.println("[Incrementador] Iniciando...");
            for (int i = 0; i < 100; i++) {
                contador.incrementarSincronizado();
            }
            System.out.println("[Incrementador] Completado");
        });
        
        Thread decrementador = new Thread(() -> {
            System.out.println("[Decrementador] Iniciando...");
            for (int i = 0; i < 100; i++) {
                contador.decrementarSincronizado();
            }
            System.out.println("[Decrementador] Completado");
        });
        
        // Ejecutar hilos
        System.out.println("Iniciando hilos sincronizados...");
        incrementador.start();
        decrementador.start();
        
        try {
            // Esperar que ambos hilos terminen
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Prueba interrumpida");
        }
        
        // Mostrar resultado
        int valorFinal = contador.getValorSincronizado();
        System.out.println("Valor final: " + valorFinal);
        System.out.println("Valor esperado: 100");
        
        if (valorFinal == 100) {
            System.out.println("✅ Resultado correcto - Sincronización exitosa");
        } else {
            System.out.println("❌ Error inesperado");
        }
        
        System.out.println();
    }
    
    /**
     * Demuestra la diferencia entre versiones sincronizadas y no sincronizadas
     */
    public static void compararVersiones() {
        System.out.println("=== COMPARACIÓN DE VERSIONES ===");
        
        System.out.println("Ejecutando 5 pruebas de cada tipo...\n");
        
        // Múltiples pruebas sin sincronización
        System.out.println("SIN SINCRONIZACIÓN:");
        for (int i = 1; i <= 5; i++) {
            Contador contador = new Contador(100);
            
            HiloIncrementador inc = new HiloIncrementador(contador);
            HiloDecrementador dec = new HiloDecrementador(contador);
            
            inc.start();
            dec.start();
            
            try {
                inc.join();
                dec.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            System.out.println("Prueba " + i + ": " + contador.getValor());
        }
        
        System.out.println("\nCON SINCRONIZACIÓN:");
        for (int i = 1; i <= 5; i++) {
            ContadorSincronizado contador = new ContadorSincronizado(100);
            
            Thread inc = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    contador.incrementarSincronizado();
                }
            });
            
            Thread dec = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    contador.decrementarSincronizado();
                }
            });
            
            inc.start();
            dec.start();
            
            try {
                inc.join();
                dec.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            System.out.println("Prueba " + i + ": " + contador.getValorSincronizado());
        }
    }
    
    /**
     * Método principal para ejecutar las pruebas básicas
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 2 - PRUEBAS BÁSICAS");
        System.out.println("=".repeat(50));
        
        // Ejecutar pruebas básicas
        pruebaBasicaSinSincronizacion();
        pruebaBasicaConSincronizacion();
        compararVersiones();
        
        System.out.println("=".repeat(50));
        System.out.println("PRUEBAS BÁSICAS COMPLETADAS");
        System.out.println("Para análisis completo, ejecutar ExperimentoContador.main()");
        System.out.println("=".repeat(50));
    }
}
