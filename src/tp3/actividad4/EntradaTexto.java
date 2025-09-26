package tp3.actividad4;

import java.util.Scanner;

/**
 * Clase para manejar la entrada de texto del usuario
 * Proporciona interfaz para capturar frases y validar entrada
 * @author PC2025
 */
public class EntradaTexto {
    
    private Scanner scanner;
    
    /**
     * Constructor que inicializa el scanner
     */
    public EntradaTexto() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Solicita al usuario que ingrese una frase
     * @return frase ingresada por el usuario
     */
    public String solicitarFrase() {
        System.out.println("=== ENTRADA DE TEXTO ===");
        System.out.println("Por favor, ingrese una frase para imprimir:");
        System.out.print("Frase: ");
        
        String frase = scanner.nextLine().trim();
        
        // Validar que no esté vacía
        while (frase.isEmpty()) {
            System.out.println("⚠️  La frase no puede estar vacía.");
            System.out.print("Ingrese una frase válida: ");
            frase = scanner.nextLine().trim();
        }
        
        return frase;
    }
    
    /**
     * Solicita una frase con mensaje personalizado
     * @param mensaje mensaje personalizado para mostrar
     * @return frase ingresada
     */
    public String solicitarFrase(String mensaje) {
        System.out.println(mensaje);
        System.out.print("Frase: ");
        
        String frase = scanner.nextLine().trim();
        
        while (frase.isEmpty()) {
            System.out.println("⚠️  La frase no puede estar vacía.");
            System.out.print("Ingrese una frase válida: ");
            frase = scanner.nextLine().trim();
        }
        
        return frase;
    }
    
    /**
     * Solicita confirmación del usuario
     * @param frase frase a confirmar
     * @return true si el usuario confirma, false en caso contrario
     */
    public boolean confirmarFrase(String frase) {
        System.out.println("\nFrase ingresada: \"" + frase + "\"");
        System.out.println("Longitud: " + frase.length() + " caracteres");
        System.out.print("¿Es correcta? (s/n): ");
        
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
    }
    
    /**
     * Solicita el modo de impresión al usuario
     * @return modo seleccionado (1, 2, o 3)
     */
    public int solicitarModoImpresion() {
        System.out.println("\n=== SELECCIÓN DE MODO ===");
        System.out.println("1. Impresión concurrente (intercalada)");
        System.out.println("2. Impresión con identificadores de hilo");
        System.out.println("3. Impresión sincronizada (ordenada)");
        System.out.println("4. Comparar todos los modos");
        System.out.print("Seleccione un modo (1-4): ");
        
        int modo = 0;
        try {
            modo = Integer.parseInt(scanner.nextLine().trim());
            while (modo < 1 || modo > 4) {
                System.out.print("⚠️  Opción inválida. Seleccione 1, 2, 3 o 4: ");
                modo = Integer.parseInt(scanner.nextLine().trim());
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Entrada inválida. Usando modo 1 por defecto.");
            modo = 1;
        }
        
        return modo;
    }
    
    /**
     * Pregunta si el usuario quiere ejecutar otra prueba
     * @return true si quiere continuar, false en caso contrario
     */
    public boolean preguntarContinuar() {
        System.out.print("\n¿Desea probar con otra frase? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
    }
    
    /**
     * Muestra información sobre el ejercicio
     */
    public void mostrarInformacion() {
        System.out.println("=".repeat(60));
        System.out.println("TP3 - ACTIVIDAD 4 - ENTRADA DE TEXTO CON HILOS");
        System.out.println("=".repeat(60));
        System.out.println("Este programa:");
        System.out.println("• Solicita una frase por teclado");
        System.out.println("• Crea 10 hilos que imprimen la frase");
        System.out.println("• Cada hilo imprime carácter por carácter");
        System.out.println("• Demuestra concurrencia en la salida de texto");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Muestra estadísticas de la frase
     * @param frase frase a analizar
     */
    public void mostrarEstadisticas(String frase) {
        System.out.println("\n=== ESTADÍSTICAS DE LA FRASE ===");
        System.out.println("Frase: \"" + frase + "\"");
        System.out.println("Longitud: " + frase.length() + " caracteres");
        System.out.println("Palabras: " + frase.split("\\s+").length);
        System.out.println("Hilos a crear: 10");
        System.out.println("Total de caracteres a imprimir: " + (frase.length() * 10));
        
        // Estimar tiempo aproximado
        int tiempoEstimado = frase.length() * 30; // 30ms por carácter aproximadamente
        System.out.println("Tiempo estimado (concurrente): ~" + tiempoEstimado + "ms");
        System.out.println("Tiempo estimado (sincronizado): ~" + (tiempoEstimado * 10) + "ms");
    }
    
    /**
     * Cierra el scanner
     */
    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
    }
    
    /**
     * Método para obtener el scanner (si se necesita externamente)
     * @return scanner actual
     */
    public Scanner getScanner() {
        return scanner;
    }
}
