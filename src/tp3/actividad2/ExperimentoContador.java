package tp3.actividad2;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para experimentar con contadores compartidos
 * Demuestra race conditions y soluciones de sincronización
 * @author PC2025
 */
public class ExperimentoContador {
    
    private static final int VALOR_INICIAL = 100;
    private static final int NUM_EXPERIMENTOS = 10;
    
    /**
     * Ejecuta un experimento con contador sin sincronización
     * @param numeroExperimento número del experimento actual
     * @return valor final del contador
     */
    public static int ejecutarExperimentoSinSincronizacion(int numeroExperimento) {
        System.out.println("\n=== EXPERIMENTO " + numeroExperimento + 
                          " - SIN SINCRONIZACIÓN ===");
        
        // Crear contador compartido
        Contador contador = new Contador(VALOR_INICIAL);
        System.out.println("Valor inicial: " + contador.getValor());
        
        // Crear hilos
        HiloIncrementador incrementador = new HiloIncrementador(contador, 
                                                               "Inc-" + numeroExperimento);
        HiloDecrementador decrementador = new HiloDecrementador(contador, 
                                                               "Dec-" + numeroExperimento);
        
        // Ejecutar hilos
        long inicio = System.currentTimeMillis();
        
        incrementador.start();
        decrementador.start();
        
        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Experimento " + numeroExperimento + " interrumpido");
        }
        
        long fin = System.currentTimeMillis();
        int valorFinal = contador.getValor();
        
        System.out.println("Valor final: " + valorFinal + 
                          " (esperado: " + VALOR_INICIAL + ")");
        System.out.println("Tiempo: " + (fin - inicio) + " ms");
        
        if (valorFinal != VALOR_INICIAL) {
            System.out.println("⚠️  RACE CONDITION DETECTADA! Diferencia: " + 
                              (valorFinal - VALOR_INICIAL));
        } else {
            System.out.println("✅ Resultado correcto (por casualidad)");
        }
        
        return valorFinal;
    }
    
    /**
     * Ejecuta un experimento con contador sincronizado
     * @param numeroExperimento número del experimento actual
     * @param usarAtomico si usar AtomicInteger o synchronized
     * @return valor final del contador
     */
    public static int ejecutarExperimentoConSincronizacion(int numeroExperimento, 
                                                          boolean usarAtomico) {
        String tipo = usarAtomico ? "ATÓMICO" : "SYNCHRONIZED";
        System.out.println("\n=== EXPERIMENTO " + numeroExperimento + 
                          " - CON SINCRONIZACIÓN (" + tipo + ") ===");
        
        // Crear contador sincronizado
        ContadorSincronizado contador = new ContadorSincronizado(VALOR_INICIAL);
        System.out.println("Valor inicial: " + VALOR_INICIAL);
        
        // Crear hilos que usan métodos sincronizados
        Thread incrementador = new Thread(() -> {
            System.out.println("[Inc-" + numeroExperimento + "] Iniciando incrementos...");
            for (int i = 0; i < 100; i++) {
                if (usarAtomico) {
                    contador.incrementarAtomico();
                } else {
                    contador.incrementarSincronizado();
                }
                
                if ((i + 1) % 25 == 0) {
                    int valor = usarAtomico ? contador.getValorAtomico() : 
                                            contador.getValorSincronizado();
                    System.out.println("[Inc-" + numeroExperimento + "] Incremento " + 
                                     (i + 1) + "/100 - Valor: " + valor);
                }
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[Inc-" + numeroExperimento + "] Completado");
        });
        
        Thread decrementador = new Thread(() -> {
            System.out.println("[Dec-" + numeroExperimento + "] Iniciando decrementos...");
            for (int i = 0; i < 100; i++) {
                if (usarAtomico) {
                    contador.decrementarAtomico();
                } else {
                    contador.decrementarSincronizado();
                }
                
                if ((i + 1) % 25 == 0) {
                    int valor = usarAtomico ? contador.getValorAtomico() : 
                                            contador.getValorSincronizado();
                    System.out.println("[Dec-" + numeroExperimento + "] Decremento " + 
                                     (i + 1) + "/100 - Valor: " + valor);
                }
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[Dec-" + numeroExperimento + "] Completado");
        });
        
        // Ejecutar hilos
        long inicio = System.currentTimeMillis();
        
        incrementador.start();
        decrementador.start();
        
        try {
            incrementador.join();
            decrementador.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Experimento " + numeroExperimento + " interrumpido");
        }
        
        long fin = System.currentTimeMillis();
        int valorFinal = usarAtomico ? contador.getValorAtomico() : 
                                     contador.getValorSincronizado();
        
        System.out.println("Valor final: " + valorFinal + 
                          " (esperado: " + VALOR_INICIAL + ")");
        System.out.println("Tiempo: " + (fin - inicio) + " ms");
        
        if (valorFinal == VALOR_INICIAL) {
            System.out.println("✅ Resultado correcto - Sincronización exitosa");
        } else {
            System.out.println("❌ Error inesperado en sincronización");
        }
        
        return valorFinal;
    }
    
    /**
     * Ejecuta múltiples experimentos y analiza resultados
     */
    public static void ejecutarAnalisisCompleto() {
        System.out.println("ANÁLISIS COMPLETO DE CONTADORES COMPARTIDOS");
        System.out.println("=".repeat(60));
        System.out.println("Valor inicial: " + VALOR_INICIAL);
        System.out.println("Operaciones por hilo: 100");
        System.out.println("Resultado esperado: " + VALOR_INICIAL);
        
        // Experimentos sin sincronización
        List<Integer> resultadosSinSync = new ArrayList<>();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 1: EXPERIMENTOS SIN SINCRONIZACIÓN");
        System.out.println("=".repeat(60));
        
        for (int i = 1; i <= NUM_EXPERIMENTOS; i++) {
            int resultado = ejecutarExperimentoSinSincronizacion(i);
            resultadosSinSync.add(resultado);
        }
        
        // Experimentos con synchronized
        List<Integer> resultadosSync = new ArrayList<>();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 2: EXPERIMENTOS CON SYNCHRONIZED");
        System.out.println("=".repeat(60));
        
        for (int i = 1; i <= NUM_EXPERIMENTOS; i++) {
            int resultado = ejecutarExperimentoConSincronizacion(i, false);
            resultadosSync.add(resultado);
        }
        
        // Experimentos con AtomicInteger
        List<Integer> resultadosAtomico = new ArrayList<>();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 3: EXPERIMENTOS CON ATOMICINTEGER");
        System.out.println("=".repeat(60));
        
        for (int i = 1; i <= NUM_EXPERIMENTOS; i++) {
            int resultado = ejecutarExperimentoConSincronizacion(i, true);
            resultadosAtomico.add(resultado);
        }
        
        // Análisis de resultados
        analizarResultados(resultadosSinSync, resultadosSync, resultadosAtomico);
    }
    
    /**
     * Analiza y presenta los resultados de todos los experimentos
     */
    private static void analizarResultados(List<Integer> sinSync, 
                                         List<Integer> conSync, 
                                         List<Integer> atomico) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ANÁLISIS DE RESULTADOS");
        System.out.println("=".repeat(80));
        
        // Estadísticas sin sincronización
        System.out.println("\n1. SIN SINCRONIZACIÓN:");
        System.out.println("   Resultados: " + sinSync);
        long correctosSinSync = sinSync.stream().filter(v -> v == VALOR_INICIAL).count();
        System.out.println("   Resultados correctos: " + correctosSinSync + "/" + NUM_EXPERIMENTOS);
        System.out.println("   Porcentaje de éxito: " + (correctosSinSync * 100.0 / NUM_EXPERIMENTOS) + "%");
        
        int minSinSync = sinSync.stream().min(Integer::compare).orElse(0);
        int maxSinSync = sinSync.stream().max(Integer::compare).orElse(0);
        double promedioSinSync = sinSync.stream().mapToInt(Integer::intValue).average().orElse(0);
        
        System.out.println("   Valor mínimo: " + minSinSync);
        System.out.println("   Valor máximo: " + maxSinSync);
        System.out.println("   Valor promedio: " + String.format("%.2f", promedioSinSync));
        
        // Estadísticas con synchronized
        System.out.println("\n2. CON SYNCHRONIZED:");
        System.out.println("   Resultados: " + conSync);
        long correctosSync = conSync.stream().filter(v -> v == VALOR_INICIAL).count();
        System.out.println("   Resultados correctos: " + correctosSync + "/" + NUM_EXPERIMENTOS);
        System.out.println("   Porcentaje de éxito: " + (correctosSync * 100.0 / NUM_EXPERIMENTOS) + "%");
        
        // Estadísticas con AtomicInteger
        System.out.println("\n3. CON ATOMICINTEGER:");
        System.out.println("   Resultados: " + atomico);
        long correctosAtomico = atomico.stream().filter(v -> v == VALOR_INICIAL).count();
        System.out.println("   Resultados correctos: " + correctosAtomico + "/" + NUM_EXPERIMENTOS);
        System.out.println("   Porcentaje de éxito: " + (correctosAtomico * 100.0 / NUM_EXPERIMENTOS) + "%");
        
        // Conclusiones
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CONCLUSIONES:");
        System.out.println("• Sin sincronización: Resultados impredecibles debido a race conditions");
        System.out.println("• Con synchronized: Resultados correctos pero con overhead de sincronización");
        System.out.println("• Con AtomicInteger: Resultados correctos con mejor rendimiento");
        System.out.println("• La sincronización es ESENCIAL para operaciones sobre variables compartidas");
        System.out.println("=".repeat(80));
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        System.out.println("TP3 - ACTIVIDAD 2 - CONTADOR COMPARTIDO CON HILOS");
        System.out.println("=".repeat(80));
        
        ejecutarAnalisisCompleto();
        
        System.out.println("\nEXPERIMENTO COMPLETADO");
    }
}
