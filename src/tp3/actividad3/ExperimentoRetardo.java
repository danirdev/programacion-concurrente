package tp3.actividad3;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para experimentar con contadores con retardo aleatorio
 * Demuestra el efecto del retardo en race conditions y la importancia de join()
 * @author PC2025
 */
public class ExperimentoRetardo {
    
    private static final int VALOR_INICIAL = 100;
    private static final int NUM_EXPERIMENTOS = 5; // Menos experimentos debido al retardo
    
    /**
     * Ejecuta un experimento SIN join() - demuestra terminación prematura
     * @param numeroExperimento número del experimento actual
     * @return valor final del contador (puede ser incompleto)
     */
    public static int ejecutarExperimentoSinJoin(int numeroExperimento) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXPERIMENTO " + numeroExperimento + " - SIN JOIN() - TERMINACIÓN PREMATURA");
        System.out.println("=".repeat(80));
        
        // Crear contador con retardo
        ContadorConRetardo contador = new ContadorConRetardo(VALOR_INICIAL);
        System.out.println("Valor inicial: " + contador.getValor());
        System.out.println(contador.getInfoRetardo());
        
        // Crear hilos
        HiloIncrementadorConRetardo incrementador = 
            new HiloIncrementadorConRetardo(contador, "Inc-" + numeroExperimento, 10); // Menos operaciones para demo
        HiloDecrementadorConRetardo decrementador = 
            new HiloDecrementadorConRetardo(contador, "Dec-" + numeroExperimento, 10);
        
        // Ejecutar hilos SIN join()
        long inicio = System.currentTimeMillis();
        
        incrementador.start();
        decrementador.start();
        
        // NO usar join() - el programa principal continúa inmediatamente
        System.out.println("⚠️  Programa principal continúa SIN esperar hilos...");
        
        // Pausa mínima para ver algo de ejecución
        try {
            Thread.sleep(500); // Solo 500ms, insuficiente para completar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long fin = System.currentTimeMillis();
        int valorFinal = contador.getValor();
        
        System.out.println("⚠️  TERMINACIÓN PREMATURA después de " + (fin - inicio) + "ms");
        System.out.println("⚠️  Valor observado: " + valorFinal + " (probablemente incompleto)");
        System.out.println("⚠️  Los hilos pueden seguir ejecutándose en background...");
        
        return valorFinal;
    }
    
    /**
     * Ejecuta un experimento CON join() - demuestra terminación completa
     * @param numeroExperimento número del experimento actual
     * @return valor final del contador (completo)
     */
    public static int ejecutarExperimentoConJoin(int numeroExperimento) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXPERIMENTO " + numeroExperimento + " - CON JOIN() - TERMINACIÓN COMPLETA");
        System.out.println("=".repeat(80));
        
        // Crear contador con retardo
        ContadorConRetardo contador = new ContadorConRetardo(VALOR_INICIAL);
        System.out.println("Valor inicial: " + contador.getValor());
        System.out.println(contador.getInfoRetardo());
        
        // Crear hilos
        HiloIncrementadorConRetardo incrementador = 
            new HiloIncrementadorConRetardo(contador, "Inc-" + numeroExperimento, 10); // Menos operaciones para demo
        HiloDecrementadorConRetardo decrementador = 
            new HiloDecrementadorConRetardo(contador, "Dec-" + numeroExperimento, 10);
        
        // Ejecutar hilos CON join()
        long inicio = System.currentTimeMillis();
        
        incrementador.start();
        decrementador.start();
        
        System.out.println("✅ Programa principal ESPERA a que terminen los hilos...");
        
        try {
            // USAR join() - esperar que ambos hilos terminen
            incrementador.join();
            decrementador.join();
            System.out.println("✅ Ambos hilos han terminado completamente");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Experimento " + numeroExperimento + " interrumpido");
        }
        
        long fin = System.currentTimeMillis();
        int valorFinal = contador.getValor();
        
        System.out.println("✅ TERMINACIÓN COMPLETA después de " + (fin - inicio) + "ms");
        System.out.println("✅ Valor final: " + valorFinal + " (esperado: " + VALOR_INICIAL + ")");
        
        if (valorFinal != VALOR_INICIAL) {
            System.out.println("⚠️  RACE CONDITION DETECTADA! Diferencia: " + 
                              (valorFinal - VALOR_INICIAL));
        } else {
            System.out.println("✅ Resultado correcto (por casualidad)");
        }
        
        return valorFinal;
    }
    
    /**
     * Ejecuta experimento con contador sincronizado
     * @param numeroExperimento número del experimento actual
     * @param usarAtomico si usar AtomicInteger o synchronized
     * @return valor final del contador
     */
    public static int ejecutarExperimentoSincronizado(int numeroExperimento, boolean usarAtomico) {
        String tipo = usarAtomico ? "ATÓMICO" : "SYNCHRONIZED";
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXPERIMENTO " + numeroExperimento + " - SINCRONIZADO (" + tipo + ")");
        System.out.println("=".repeat(80));
        
        // Crear contador sincronizado con retardo
        ContadorSincronizadoConRetardo contador = new ContadorSincronizadoConRetardo(VALOR_INICIAL);
        System.out.println("Valor inicial: " + VALOR_INICIAL);
        System.out.println(contador.getInfoRetardo());
        
        // Crear hilos que usan métodos sincronizados
        Thread incrementador = new Thread(() -> {
            Thread.currentThread().setName("Inc-Sync-" + numeroExperimento);
            System.out.println("[Inc-Sync-" + numeroExperimento + "] Iniciando incrementos sincronizados...");
            
            for (int i = 0; i < 10; i++) { // Menos operaciones para demo
                if (usarAtomico) {
                    contador.incrementarAtomico();
                } else {
                    contador.incrementarSincronizado();
                }
            }
            System.out.println("[Inc-Sync-" + numeroExperimento + "] Completado");
        });
        
        Thread decrementador = new Thread(() -> {
            Thread.currentThread().setName("Dec-Sync-" + numeroExperimento);
            System.out.println("[Dec-Sync-" + numeroExperimento + "] Iniciando decrementos sincronizados...");
            
            for (int i = 0; i < 10; i++) { // Menos operaciones para demo
                if (usarAtomico) {
                    contador.decrementarAtomico();
                } else {
                    contador.decrementarSincronizado();
                }
            }
            System.out.println("[Dec-Sync-" + numeroExperimento + "] Completado");
        });
        
        // Ejecutar hilos CON join()
        long inicio = System.currentTimeMillis();
        
        incrementador.start();
        decrementador.start();
        
        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Experimento sincronizado " + numeroExperimento + " interrumpido");
        }
        
        long fin = System.currentTimeMillis();
        int valorFinal = usarAtomico ? contador.getValorAtomico() : contador.getValorSincronizado();
        
        System.out.println("✅ Experimento sincronizado completado en " + (fin - inicio) + "ms");
        System.out.println("✅ Valor final: " + valorFinal + " (esperado: " + VALOR_INICIAL + ")");
        
        if (valorFinal == VALOR_INICIAL) {
            System.out.println("✅ Resultado correcto - Sincronización exitosa");
        } else {
            System.out.println("❌ Error inesperado en sincronización");
        }
        
        return valorFinal;
    }
    
    /**
     * Ejecuta análisis completo comparando con y sin join()
     */
    public static void ejecutarAnalisisCompleto() {
        System.out.println("ANÁLISIS COMPLETO - EFECTO DEL RETARDO Y JOIN()");
        System.out.println("=".repeat(80));
        System.out.println("Valor inicial: " + VALOR_INICIAL);
        System.out.println("Retardo por operación: 50-150ms");
        System.out.println("Operaciones por hilo: 10 (reducido para demostración)");
        
        // Fase 1: Experimentos SIN join()
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FASE 1: EXPERIMENTOS SIN JOIN() - DEMOSTRAR TERMINACIÓN PREMATURA");
        System.out.println("=".repeat(80));
        
        List<Integer> resultadosSinJoin = new ArrayList<>();
        for (int i = 1; i <= 3; i++) { // Solo 3 experimentos
            int resultado = ejecutarExperimentoSinJoin(i);
            resultadosSinJoin.add(resultado);
            
            // Pausa entre experimentos para separar salidas
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Fase 2: Experimentos CON join()
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FASE 2: EXPERIMENTOS CON JOIN() - DEMOSTRAR TERMINACIÓN COMPLETA");
        System.out.println("=".repeat(80));
        
        List<Integer> resultadosConJoin = new ArrayList<>();
        for (int i = 1; i <= 3; i++) { // Solo 3 experimentos
            int resultado = ejecutarExperimentoConJoin(i);
            resultadosConJoin.add(resultado);
            
            // Pausa entre experimentos
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Fase 3: Experimentos sincronizados
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FASE 3: EXPERIMENTOS SINCRONIZADOS");
        System.out.println("=".repeat(80));
        
        List<Integer> resultadosSync = new ArrayList<>();
        for (int i = 1; i <= 2; i++) { // Solo 2 experimentos
            int resultado = ejecutarExperimentoSincronizado(i, false); // synchronized
            resultadosSync.add(resultado);
        }
        
        // Análisis de resultados
        analizarResultados(resultadosSinJoin, resultadosConJoin, resultadosSync);
    }
    
    /**
     * Analiza y presenta los resultados de todos los experimentos
     */
    private static void analizarResultados(List<Integer> sinJoin, 
                                         List<Integer> conJoin, 
                                         List<Integer> sincronizados) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ANÁLISIS DE RESULTADOS CON RETARDO");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. SIN JOIN() (terminación prematura):");
        System.out.println("   Resultados observados: " + sinJoin);
        System.out.println("   ⚠️  Estos valores son INCOMPLETOS debido a terminación prematura");
        System.out.println("   ⚠️  Los hilos pueden seguir ejecutándose después del main()");
        
        System.out.println("\n2. CON JOIN() (terminación completa):");
        System.out.println("   Resultados: " + conJoin);
        long correctosConJoin = conJoin.stream().filter(v -> v == VALOR_INICIAL).count();
        System.out.println("   Resultados correctos: " + correctosConJoin + "/" + conJoin.size());
        System.out.println("   ✅ Estos valores son COMPLETOS gracias a join()");
        
        System.out.println("\n3. SINCRONIZADOS:");
        System.out.println("   Resultados: " + sincronizados);
        long correctosSync = sincronizados.stream().filter(v -> v == VALOR_INICIAL).count();
        System.out.println("   Resultados correctos: " + correctosSync + "/" + sincronizados.size());
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CONCLUSIONES CLAVE:");
        System.out.println("• SIN join(): Terminación prematura, resultados incompletos");
        System.out.println("• CON join(): Terminación completa, pero race conditions visibles");
        System.out.println("• Retardo 50-150ms: Amplifica las race conditions");
        System.out.println("• Sincronización: Previene race conditions incluso con retardo");
        System.out.println("• join() es CRÍTICO para obtener resultados completos");
        System.out.println("=".repeat(80));
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 3 - CONTADOR CON RETARDO ALEATORIO");
        System.out.println("=".repeat(80));
        System.out.println("Demostración del efecto del retardo y la importancia de join()");
        
        ejecutarAnalisisCompleto();
        
        System.out.println("\nEXPERIMENTO COMPLETADO");
        System.out.println("Nota: Los retardos hacen que la ejecución sea más lenta pero más observable");
    }
}
