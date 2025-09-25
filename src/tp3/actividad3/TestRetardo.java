package tp3.actividad3;

/**
 * Clase de prueba básica para demostrar el efecto del retardo aleatorio
 * Versión simplificada para entender los conceptos fundamentales
 * @author PC2025
 */
public class TestRetardo {
    
    /**
     * Demuestra la diferencia crítica entre usar y no usar join() con retardo
     */
    public static void demostrarEfectoJoin() {
        System.out.println("=== DEMOSTRACIÓN: EFECTO DE JOIN() CON RETARDO ===");
        
        // Prueba SIN join()
        System.out.println("\n1. PRUEBA SIN JOIN() - Terminación Prematura:");
        System.out.println("-".repeat(50));
        
        ContadorConRetardo contador1 = new ContadorConRetardo(100);
        System.out.println("Valor inicial: " + contador1.getValor());
        
        HiloIncrementadorConRetardo inc1 = new HiloIncrementadorConRetardo(contador1, "Inc-NoJoin", 5);
        HiloDecrementadorConRetardo dec1 = new HiloDecrementadorConRetardo(contador1, "Dec-NoJoin", 5);
        
        long inicio1 = System.currentTimeMillis();
        inc1.start();
        dec1.start();
        
        // NO usar join() - terminar inmediatamente
        System.out.println("⚠️  Main termina SIN esperar hilos...");
        
        // Pausa mínima para ver algo
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long fin1 = System.currentTimeMillis();
        System.out.println("⚠️  Valor observado tras " + (fin1 - inicio1) + "ms: " + contador1.getValor());
        System.out.println("⚠️  (Probablemente incompleto - hilos siguen ejecutándose)");
        
        // Pausa para separar pruebas
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Prueba CON join()
        System.out.println("\n2. PRUEBA CON JOIN() - Terminación Completa:");
        System.out.println("-".repeat(50));
        
        ContadorConRetardo contador2 = new ContadorConRetardo(100);
        System.out.println("Valor inicial: " + contador2.getValor());
        
        HiloIncrementadorConRetardo inc2 = new HiloIncrementadorConRetardo(contador2, "Inc-ConJoin", 5);
        HiloDecrementadorConRetardo dec2 = new HiloDecrementadorConRetardo(contador2, "Dec-ConJoin", 5);
        
        long inicio2 = System.currentTimeMillis();
        inc2.start();
        dec2.start();
        
        System.out.println("✅ Main ESPERA a que terminen los hilos...");
        
        try {
            // USAR join() - esperar terminación completa
            inc2.join();
            dec2.join();
            System.out.println("✅ Ambos hilos terminaron completamente");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long fin2 = System.currentTimeMillis();
        System.out.println("✅ Valor final tras " + (fin2 - inicio2) + "ms: " + contador2.getValor());
        System.out.println("✅ (Resultado completo y confiable)");
    }
    
    /**
     * Compara el comportamiento con y sin retardo
     */
    public static void compararConYSinRetardo() {
        System.out.println("\n=== COMPARACIÓN: CON Y SIN RETARDO ===");
        
        // Simulación rápida (sin retardo real, solo para comparar)
        System.out.println("\n1. Simulación SIN retardo significativo:");
        System.out.println("-".repeat(40));
        
        ContadorConRetardo contadorRapido = new ContadorConRetardo(100);
        
        Thread incRapido = new Thread(() -> {
            Thread.currentThread().setName("Inc-Rapido");
            System.out.println("[Inc-Rapido] Iniciando...");
            for (int i = 0; i < 3; i++) {
                int valorAntes = contadorRapido.getValor();
                // Simular operación rápida sin el retardo interno
                try {
                    Thread.sleep(10); // Retardo mínimo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                System.out.println("[Inc-Rapido] Operación " + (i+1) + " - Valor: " + contadorRapido.getValor());
            }
            System.out.println("[Inc-Rapido] Completado");
        });
        
        Thread decRapido = new Thread(() -> {
            Thread.currentThread().setName("Dec-Rapido");
            System.out.println("[Dec-Rapido] Iniciando...");
            for (int i = 0; i < 3; i++) {
                int valorAntes = contadorRapido.getValor();
                try {
                    Thread.sleep(10); // Retardo mínimo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                System.out.println("[Dec-Rapido] Operación " + (i+1) + " - Valor: " + contadorRapido.getValor());
            }
            System.out.println("[Dec-Rapido] Completado");
        });
        
        long inicioRapido = System.currentTimeMillis();
        incRapido.start();
        decRapido.start();
        
        try {
            incRapido.join();
            decRapido.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long finRapido = System.currentTimeMillis();
        System.out.println("Tiempo total (rápido): " + (finRapido - inicioRapido) + "ms");
        
        // Con retardo significativo
        System.out.println("\n2. CON retardo aleatorio 50-150ms:");
        System.out.println("-".repeat(40));
        
        ContadorConRetardo contadorLento = new ContadorConRetardo(100);
        
        HiloIncrementadorConRetardo incLento = new HiloIncrementadorConRetardo(contadorLento, "Inc-Lento", 3);
        HiloDecrementadorConRetardo decLento = new HiloDecrementadorConRetardo(contadorLento, "Dec-Lento", 3);
        
        long inicioLento = System.currentTimeMillis();
        incLento.start();
        decLento.start();
        
        try {
            incLento.join();
            decLento.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long finLento = System.currentTimeMillis();
        System.out.println("Tiempo total (con retardo): " + (finLento - inicioLento) + "ms");
        
        System.out.println("\nObservación: El retardo hace más visible la intercalación de hilos");
    }
    
    /**
     * Prueba básica con contador sincronizado
     */
    public static void pruebaContadorSincronizado() {
        System.out.println("\n=== PRUEBA: CONTADOR SINCRONIZADO CON RETARDO ===");
        
        ContadorSincronizadoConRetardo contador = new ContadorSincronizadoConRetardo(100);
        System.out.println("Valor inicial: 100");
        System.out.println(contador.getInfoRetardo());
        
        Thread inc = new Thread(() -> {
            Thread.currentThread().setName("Inc-Sync");
            System.out.println("[Inc-Sync] Iniciando incrementos sincronizados...");
            for (int i = 0; i < 3; i++) {
                contador.incrementarSincronizado();
            }
            System.out.println("[Inc-Sync] Completado");
        });
        
        Thread dec = new Thread(() -> {
            Thread.currentThread().setName("Dec-Sync");
            System.out.println("[Dec-Sync] Iniciando decrementos sincronizados...");
            for (int i = 0; i < 3; i++) {
                contador.decrementarSincronizado();
            }
            System.out.println("[Dec-Sync] Completado");
        });
        
        long inicio = System.currentTimeMillis();
        inc.start();
        dec.start();
        
        try {
            inc.join();
            dec.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long fin = System.currentTimeMillis();
        System.out.println("Valor final: " + contador.getValorSincronizado());
        System.out.println("Tiempo total: " + (fin - inicio) + "ms");
        System.out.println("✅ Resultado correcto gracias a sincronización");
    }
    
    /**
     * Método principal para ejecutar las pruebas básicas
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 3 - PRUEBAS BÁSICAS CON RETARDO");
        System.out.println("=".repeat(60));
        
        // Ejecutar demostraciones
        demostrarEfectoJoin();
        compararConYSinRetardo();
        pruebaContadorSincronizado();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CONCLUSIONES DE LAS PRUEBAS BÁSICAS:");
        System.out.println("• join() es ESENCIAL para obtener resultados completos");
        System.out.println("• El retardo amplifica las race conditions");
        System.out.println("• La sincronización previene problemas incluso con retardo");
        System.out.println("• El timing es crítico en programación concurrente");
        System.out.println("=".repeat(60));
        System.out.println("Para análisis completo, ejecutar ExperimentoRetardo.main()");
    }
}
