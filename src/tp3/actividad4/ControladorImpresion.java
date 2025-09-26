package tp3.actividad4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador que coordina múltiples hilos de impresión de caracteres
 * Implementa el requerimiento de imprimir 10 veces la frase
 * @author PC2025
 */
public class ControladorImpresion {
    
    private String frase;
    private List<ImpresorCaracteres> hilos;
    private static final int NUM_HILOS = 10;
    
    /**
     * Constructor del controlador
     * @param frase frase a imprimir por todos los hilos
     */
    public ControladorImpresion(String frase) {
        this.frase = frase;
        this.hilos = new ArrayList<>();
    }
    
    /**
     * Ejecuta la impresión concurrente sin sincronización
     * Los caracteres de diferentes hilos se intercalan
     */
    public void ejecutarImpresionConcurrente() {
        System.out.println("=== IMPRESIÓN CONCURRENTE (INTERCALADA) ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("Hilos: " + NUM_HILOS);
        System.out.println("Modo: Caracteres intercalados");
        System.out.println("-".repeat(50));
        
        // Crear hilos
        hilos.clear();
        for (int i = 1; i <= NUM_HILOS; i++) {
            ImpresorCaracteres hilo = new ImpresorCaracteres(frase, i, 30, false);
            hilos.add(hilo);
        }
        
        // Iniciar todos los hilos
        long tiempoInicio = System.currentTimeMillis();
        
        for (ImpresorCaracteres hilo : hilos) {
            hilo.start();
        }
        
        // Esperar que todos terminen
        try {
            for (ImpresorCaracteres hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ejecución interrumpida");
        }
        
        long tiempoFin = System.currentTimeMillis();
        
        System.out.println("-".repeat(50));
        System.out.println("✅ Impresión concurrente completada en " + 
                          (tiempoFin - tiempoInicio) + "ms");
        System.out.println("Nota: Los caracteres aparecen intercalados debido a la concurrencia");
    }
    
    /**
     * Ejecuta la impresión con identificadores de hilo
     * Útil para observar qué hilo imprime cada carácter
     */
    public void ejecutarImpresionConIdentificadores() {
        System.out.println("\n=== IMPRESIÓN CON IDENTIFICADORES DE HILO ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("Formato: [H#:carácter] donde # es el número de hilo");
        System.out.println("-".repeat(50));
        
        // Crear hilos especiales que muestran identificadores
        List<Thread> hilosEspeciales = new ArrayList<>();
        
        for (int i = 1; i <= NUM_HILOS; i++) {
            final int numeroHilo = i;
            Thread hilo = new Thread(() -> {
                try {
                    for (int j = 0; j < frase.length(); j++) {
                        char caracter = frase.charAt(j);
                        System.out.print("[H" + numeroHilo + ":" + caracter + "]");
                        Thread.sleep(40);
                    }
                    System.out.print("[H" + numeroHilo + ":FIN] ");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            hilo.setName("HiloEspecial-" + i);
            hilosEspeciales.add(hilo);
        }
        
        // Ejecutar hilos especiales
        long tiempoInicio = System.currentTimeMillis();
        
        for (Thread hilo : hilosEspeciales) {
            hilo.start();
        }
        
        try {
            for (Thread hilo : hilosEspeciales) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long tiempoFin = System.currentTimeMillis();
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("✅ Impresión con identificadores completada en " + 
                          (tiempoFin - tiempoInicio) + "ms");
    }
    
    /**
     * Ejecuta la impresión sincronizada
     * Cada hilo imprime su frase completa antes que el siguiente
     */
    public void ejecutarImpresionSincronizada() {
        System.out.println("\n=== IMPRESIÓN SINCRONIZADA (ORDENADA) ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("Modo: Una frase completa por vez");
        System.out.println("-".repeat(50));
        
        // Objeto para sincronización
        Object lock = new Object();
        List<Thread> hilosSincronizados = new ArrayList<>();
        
        for (int i = 1; i <= NUM_HILOS; i++) {
            final int numeroHilo = i;
            Thread hilo = new Thread(() -> {
                synchronized (lock) {
                    System.out.print("[Hilo-" + numeroHilo + "] ");
                    try {
                        for (int j = 0; j < frase.length(); j++) {
                            char caracter = frase.charAt(j);
                            System.out.print(caracter);
                            Thread.sleep(20); // Pausa más corta para sincronizado
                        }
                        System.out.println(); // Nueva línea al final
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            
            hilo.setName("HiloSync-" + i);
            hilosSincronizados.add(hilo);
        }
        
        // Ejecutar hilos sincronizados
        long tiempoInicio = System.currentTimeMillis();
        
        for (Thread hilo : hilosSincronizados) {
            hilo.start();
        }
        
        try {
            for (Thread hilo : hilosSincronizados) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long tiempoFin = System.currentTimeMillis();
        
        System.out.println("-".repeat(50));
        System.out.println("✅ Impresión sincronizada completada en " + 
                          (tiempoFin - tiempoInicio) + "ms");
        System.out.println("Nota: Cada hilo imprimió su frase completa de forma ordenada");
    }
    
    /**
     * Ejecuta todos los modos de impresión para comparar
     */
    public void ejecutarComparacionCompleta() {
        System.out.println("COMPARACIÓN DE MODOS DE IMPRESIÓN");
        System.out.println("=".repeat(60));
        
        // Modo 1: Concurrente
        ejecutarImpresionConcurrente();
        
        // Pausa entre modos
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Modo 2: Con identificadores
        ejecutarImpresionConIdentificadores();
        
        // Pausa entre modos
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Modo 3: Sincronizado
        ejecutarImpresionSincronizada();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARACIÓN COMPLETADA");
        System.out.println("• Concurrente: Máxima intercalación, difícil de leer");
        System.out.println("• Con identificadores: Intercalación visible y trazeable");
        System.out.println("• Sincronizado: Ordenado y legible, menos concurrente");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Método principal para prueba independiente
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("TP3 - ACTIVIDAD 4 - CONTROLADOR DE IMPRESIÓN");
        System.out.println("=".repeat(50));
        System.out.print("Ingrese una frase: ");
        String frase = scanner.nextLine();
        
        if (frase.trim().isEmpty()) {
            frase = "Hola Mundo Java";
            System.out.println("Usando frase por defecto: \"" + frase + "\"");
        }
        
        ControladorImpresion controlador = new ControladorImpresion(frase);
        controlador.ejecutarComparacionCompleta();
        
        scanner.close();
    }
}
