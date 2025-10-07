package tp9.actividad2;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que simula la planta de ensamblado
 */
public class AssemblyPlant {
    private static final int TOTAL_COMPONENTS = 100; // Total de componentes a ensamblar
    
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE PLANTA DE ENSAMBLADO ===");
        System.out.println("Recursos disponibles:");
        System.out.println("- 3 mesas de trabajo");
        System.out.println("- 4 pinzas");
        System.out.println("- 2 destornilladores");
        System.out.println("- 4 sargentos");
        System.out.println("- 100 componentes para ensamblar");
        System.out.println("\nTiempos de procesamiento:");
        System.out.println("- Fase 1: 400ms (destornillador + pinza)");
        System.out.println("- Fase 2: 200ms (2 sargentos)");
        System.out.println("- Fase 3: 800ms (2 pinzas)");
        System.out.println("\n=== INICIANDO PRODUCCIÓN ===\n");
        
        // Crea el manejador de recursos compartidos
        ResourceManager resourceManager = new ResourceManager();
        
        // Lista para almacenar todos los hilos de componentes
        List<Thread> componentThreads = new ArrayList<>();
        
        // Crea y lanza todos los hilos de componentes
        for (int i = 1; i <= TOTAL_COMPONENTS; i++) {
            // Crea un nuevo componente
            Component component = new Component(i, resourceManager);
            
            // Crea un hilo para el componente
            Thread thread = new Thread(component, "Component-" + i);
            componentThreads.add(thread);
            
            // Inicia el hilo
            thread.start();
            
            // Pequeña pausa entre el lanzamiento de hilos para evitar saturación
            try {
                Thread.sleep(10); // 10ms de pausa entre componentes
            } catch (InterruptedException e) {
                System.err.println("Error en la pausa entre componentes");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Espera a que todos los componentes terminen
        System.out.println("Esperando que todos los componentes terminen...\n");
        
        int completedComponents = 0;
        for (Thread thread : componentThreads) {
            try {
                thread.join(); // Espera a que el hilo termine
                completedComponents++;
                
                // Muestra progreso cada 10 componentes completados
                if (completedComponents % 10 == 0) {
                    System.out.println("\n>>> PROGRESO: " + completedComponents + "/" + TOTAL_COMPONENTS + 
                                     " componentes completados (" + 
                                     (completedComponents * 100 / TOTAL_COMPONENTS) + "%)\n");
                }
            } catch (InterruptedException e) {
                System.err.println("Error esperando que termine un componente");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("\n=== PRODUCCIÓN COMPLETADA ===");
        System.out.println("Todos los " + TOTAL_COMPONENTS + " componentes han sido ensamblados exitosamente!");
    }
}