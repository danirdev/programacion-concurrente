package tp3.actividad1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase principal que ejecuta experimentos con hilos concurrentes
 * Implementa todos los requerimientos del ejercicio
 * @author PC2025
 */
public class ExperimentoHilos {
    
    private List<String> resultadosEjecuciones;
    
    /**
     * Constructor
     */
    public ExperimentoHilos() {
        this.resultadosEjecuciones = new ArrayList<>();
    }
    
    /**
     * Ejecuta una sola ejecución concurrente de los hilos X e Y
     * @param numeroEjecucion número de la ejecución actual
     * @param usarJoin si debe usar join() para esperar los hilos
     * @return salida capturada de la ejecución
     */
    public String ejecutarEjecucionConcurrente(int numeroEjecucion, boolean usarJoin) {
        // Capturar la salida en un String para análisis posterior
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream capturedOut = new PrintStream(baos);
        
        try {
            System.setOut(capturedOut);
            
            System.out.println("=== EJECUCIÓN " + numeroEjecucion + " ===");
            
            // Crear los hilos
            HiloX hiloX = new HiloX("HiloX-" + numeroEjecucion);
            HiloY hiloY = new HiloY("HiloY-" + numeroEjecucion);
            
            // Iniciar los hilos
            hiloX.start();
            hiloY.start();
            
            if (usarJoin) {
                // Esperar que ambos hilos terminen
                hiloX.join();
                hiloY.join();
            } else {
                // Sin join - puede que no terminen antes de continuar
                Thread.sleep(100); // Pausa mínima para ver algo de ejecución
            }
            
            System.out.println("=== FIN EJECUCIÓN " + numeroEjecucion + " ===\n");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ejecución " + numeroEjecucion + " interrumpida");
        } finally {
            System.setOut(originalOut);
        }
        
        String resultado = baos.toString();
        resultadosEjecuciones.add(resultado);
        return resultado;
    }
    
    /**
     * Ejecuta 10 repeticiones del experimento concurrente
     * @param usarJoin si debe usar join() para sincronización
     */
    public void ejecutar10Repeticiones(boolean usarJoin) {
        System.out.println("INICIANDO 10 EJECUCIONES CONCURRENTES " + 
                          (usarJoin ? "CON JOIN" : "SIN JOIN"));
        System.out.println("=".repeat(60));
        
        resultadosEjecuciones.clear();
        
        for (int i = 1; i <= 10; i++) {
            String resultado = ejecutarEjecucionConcurrente(i, usarJoin);
            System.out.println("Ejecución " + i + " completada");
            
            // Pausa entre ejecuciones para observar mejor
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("10 EJECUCIONES COMPLETADAS");
    }
    
    /**
     * Analiza los resultados para encontrar la sucesión más larga
     * de cualquier letra (X o Y)
     */
    public void analizarSucesionMasLarga() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ANÁLISIS DE SUCESIONES MÁS LARGAS");
        System.out.println("=".repeat(60));
        
        String sucesionMasLarga = "";
        int longitudMaxima = 0;
        int ejecucionEncontrada = 0;
        
        for (int i = 0; i < resultadosEjecuciones.size(); i++) {
            String resultado = resultadosEjecuciones.get(i);
            
            // Buscar sucesiones de X
            String sucesionX = encontrarSucesionMasLarga(resultado, 'X');
            if (sucesionX.length() > longitudMaxima) {
                longitudMaxima = sucesionX.length();
                sucesionMasLarga = sucesionX;
                ejecucionEncontrada = i + 1;
            }
            
            // Buscar sucesiones de Y
            String sucesionY = encontrarSucesionMasLarga(resultado, 'Y');
            if (sucesionY.length() > longitudMaxima) {
                longitudMaxima = sucesionY.length();
                sucesionMasLarga = sucesionY;
                ejecucionEncontrada = i + 1;
            }
        }
        
        System.out.println("Sucesión más larga encontrada:");
        System.out.println("Ejecución: " + ejecucionEncontrada);
        System.out.println("Longitud: " + longitudMaxima + " caracteres");
        System.out.println("Sucesión: " + sucesionMasLarga);
        
        if (sucesionMasLarga.contains("X")) {
            System.out.println("Tipo: Sucesión de patrones X");
        } else if (sucesionMasLarga.contains("Y")) {
            System.out.println("Tipo: Sucesión de patrones Y");
        }
    }
    
    /**
     * Encuentra la sucesión más larga de una letra específica
     * @param texto texto donde buscar
     * @param letra letra a buscar (X o Y)
     * @return la sucesión más larga encontrada
     */
    private String encontrarSucesionMasLarga(String texto, char letra) {
        // Patrón para encontrar sucesiones consecutivas de la letra
        Pattern patron = Pattern.compile("(\\.\\d+" + letra + "\\.)+" );
        Matcher matcher = patron.matcher(texto);
        
        String sucesionMasLarga = "";
        
        while (matcher.find()) {
            String sucesionActual = matcher.group();
            if (sucesionActual.length() > sucesionMasLarga.length()) {
                sucesionMasLarga = sucesionActual;
            }
        }
        
        return sucesionMasLarga;
    }
    
    /**
     * Compara ejecución con y sin join()
     */
    public void compararConYSinJoin() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPARACIÓN: EJECUCIÓN CON Y SIN JOIN()");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. EJECUTANDO SIN JOIN():");
        System.out.println("-".repeat(40));
        ejecutar10Repeticiones(false);
        
        System.out.println("\n2. EJECUTANDO CON JOIN():");
        System.out.println("-".repeat(40));
        ejecutar10Repeticiones(true);
    }
    
    /**
     * Proporciona análisis y comentarios sobre los resultados
     */
    public void analizarYComentarResultados() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ANÁLISIS Y COMENTARIOS DE RESULTADOS");
        System.out.println("=".repeat(80));
        
        System.out.println("\n1. COMPORTAMIENTO OBSERVADO:");
        System.out.println("   • Los hilos X e Y se ejecutan concurrentemente");
        System.out.println("   • La salida muestra intercalación de patrones .iX. y .iY.");
        System.out.println("   • Sin join(): Los hilos pueden no completarse antes de terminar main");
        System.out.println("   • Con join(): Se garantiza que ambos hilos terminen completamente");
        
        System.out.println("\n2. CONCURRENCIA Y SCHEDULING:");
        System.out.println("   • El scheduler del SO determina cuándo ejecuta cada hilo");
        System.out.println("   • Los resultados varían entre ejecuciones (no determinismo)");
        System.out.println("   • Thread.sleep(1) permite intercalación más visible");
        
        System.out.println("\n3. SINCRONIZACIÓN:");
        System.out.println("   • join() es crucial para esperar terminación de hilos");
        System.out.println("   • Sin join(), el programa principal puede terminar prematuramente");
        System.out.println("   • Los hilos comparten System.out (recurso compartido)");
        
        System.out.println("\n4. PATRONES OBSERVADOS:");
        System.out.println("   • Sucesiones largas indican que un hilo ejecutó sin interrupción");
        System.out.println("   • Intercalación frecuente muestra competencia por CPU");
        System.out.println("   • Cada ejecución produce patrones diferentes");
        
        System.out.println("\n5. RECOMENDACIONES:");
        System.out.println("   • Siempre usar join() cuando se necesite esperar hilos");
        System.out.println("   • Considerar sincronización para salida ordenada");
        System.out.println("   • Thread.sleep() es útil para observar concurrencia");
    }
    
    /**
     * Método principal que ejecuta todos los experimentos
     */
    public static void main(String[] args) {
        ExperimentoHilos experimento = new ExperimentoHilos();
        
        // Ejecutar experimento completo
        experimento.compararConYSinJoin();
        
        // Analizar sucesiones más largas
        experimento.analizarSucesionMasLarga();
        
        // Proporcionar análisis y comentarios
        experimento.analizarYComentarResultados();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("EXPERIMENTO COMPLETADO");
        System.out.println("=".repeat(80));
    }
}
