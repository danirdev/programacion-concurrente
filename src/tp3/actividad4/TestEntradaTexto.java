package tp3.actividad4;

/**
 * Clase principal para probar la entrada de texto con hilos
 * Implementa el requerimiento completo de la Actividad 4
 * @author PC2025
 */
public class TestEntradaTexto {
    
    /**
     * Ejecuta el programa principal según el enunciado
     */
    public static void ejecutarProgramaPrincipal() {
        EntradaTexto entrada = new EntradaTexto();
        
        try {
            // Mostrar información del programa
            entrada.mostrarInformacion();
            
            boolean continuar = true;
            
            while (continuar) {
                // Solicitar frase al usuario
                String frase = entrada.solicitarFrase();
                
                // Confirmar la frase
                if (!entrada.confirmarFrase(frase)) {
                    System.out.println("Reintentando...\n");
                    continue;
                }
                
                // Mostrar estadísticas
                entrada.mostrarEstadisticas(frase);
                
                // Solicitar modo de impresión
                int modo = entrada.solicitarModoImpresion();
                
                // Crear controlador y ejecutar según el modo
                ControladorImpresion controlador = new ControladorImpresion(frase);
                
                System.out.println("\n🚀 Iniciando impresión...");
                System.out.println("Presione Ctrl+C si desea interrumpir\n");
                
                // Pequeña pausa antes de comenzar
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Ejecutar según el modo seleccionado
                switch (modo) {
                    case 1:
                        controlador.ejecutarImpresionConcurrente();
                        break;
                    case 2:
                        controlador.ejecutarImpresionConIdentificadores();
                        break;
                    case 3:
                        controlador.ejecutarImpresionSincronizada();
                        break;
                    case 4:
                        controlador.ejecutarComparacionCompleta();
                        break;
                    default:
                        System.out.println("Modo no válido, usando modo 1");
                        controlador.ejecutarImpresionConcurrente();
                }
                
                // Preguntar si desea continuar
                continuar = entrada.preguntarContinuar();
            }
            
            System.out.println("\n¡Gracias por usar el programa!");
            
        } finally {
            entrada.cerrar();
        }
    }
    
    /**
     * Ejecuta una demostración rápida con frase predefinida
     */
    public static void ejecutarDemostracionRapida() {
        System.out.println("=== DEMOSTRACIÓN RÁPIDA ===");
        
        String fraseDemo = "Java Concurrente";
        System.out.println("Usando frase de demostración: \"" + fraseDemo + "\"");
        
        ControladorImpresion controlador = new ControladorImpresion(fraseDemo);
        
        System.out.println("\n1. Impresión Concurrente:");
        controlador.ejecutarImpresionConcurrente();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n2. Impresión Sincronizada:");
        controlador.ejecutarImpresionSincronizada();
    }
    
    /**
     * Ejecuta pruebas de rendimiento con diferentes longitudes de frase
     */
    public static void ejecutarPruebasRendimiento() {
        System.out.println("=== PRUEBAS DE RENDIMIENTO ===");
        
        String[] frasesPrueba = {
            "Hola",
            "Hola Mundo",
            "Programación Concurrente en Java",
            "Esta es una frase más larga para probar el rendimiento de los hilos concurrentes"
        };
        
        for (String frase : frasesPrueba) {
            System.out.println("\n--- Probando: \"" + frase + "\" (" + frase.length() + " caracteres) ---");
            
            ControladorImpresion controlador = new ControladorImpresion(frase);
            
            // Medir tiempo concurrente
            long inicioConcurrente = System.currentTimeMillis();
            controlador.ejecutarImpresionConcurrente();
            long finConcurrente = System.currentTimeMillis();
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Medir tiempo sincronizado
            long inicioSincronizado = System.currentTimeMillis();
            controlador.ejecutarImpresionSincronizada();
            long finSincronizado = System.currentTimeMillis();
            
            // Mostrar comparación
            System.out.println("Tiempo concurrente: " + (finConcurrente - inicioConcurrente) + "ms");
            System.out.println("Tiempo sincronizado: " + (finSincronizado - inicioSincronizado) + "ms");
            
            double mejora = (double)(finSincronizado - inicioSincronizado) / (finConcurrente - inicioConcurrente);
            System.out.println("Mejora de velocidad: " + String.format("%.2f", mejora) + "x");
        }
    }
    
    /**
     * Muestra el menú de opciones
     */
    public static void mostrarMenu() {
        System.out.println("=== MENÚ DE OPCIONES ===");
        System.out.println("1. Programa principal (entrada de usuario)");
        System.out.println("2. Demostración rápida");
        System.out.println("3. Pruebas de rendimiento");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción (1-4): ");
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Verificar si se pasaron argumentos para modo automático
        if (args.length > 0) {
            if (args[0].equals("demo")) {
                ejecutarDemostracionRapida();
                return;
            } else if (args[0].equals("performance")) {
                ejecutarPruebasRendimiento();
                return;
            } else if (args[0].equals("auto")) {
                // Modo automático con frase predefinida
                String frase = args.length > 1 ? args[1] : "Hola Mundo Concurrente";
                System.out.println("Modo automático con frase: \"" + frase + "\"");
                ControladorImpresion controlador = new ControladorImpresion(frase);
                controlador.ejecutarComparacionCompleta();
                return;
            }
        }
        
        // Modo interactivo por defecto
        EntradaTexto entrada = new EntradaTexto();
        
        try {
            boolean continuar = true;
            
            while (continuar) {
                mostrarMenu();
                
                try {
                    int opcion = Integer.parseInt(entrada.getScanner().nextLine().trim());
                    
                    switch (opcion) {
                        case 1:
                            ejecutarProgramaPrincipal();
                            break;
                        case 2:
                            ejecutarDemostracionRapida();
                            break;
                        case 3:
                            ejecutarPruebasRendimiento();
                            break;
                        case 4:
                            continuar = false;
                            System.out.println("¡Hasta luego!");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                    
                    if (continuar && opcion != 4) {
                        System.out.println("\nPresione Enter para continuar...");
                        entrada.getScanner().nextLine();
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Intente nuevamente.");
                }
            }
            
        } finally {
            entrada.cerrar();
        }
    }
}
