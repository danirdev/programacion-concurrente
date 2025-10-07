package tp9.actividad2;

/**
 * Clase que representa un componente que debe ser ensamblado
 * Implementa Runnable para poder ejecutarse en un hilo separado
 */
public class Component implements Runnable {
    private final int id;                    // Identificador único del componente
    private final ResourceManager resources; // Referencia al manejador de recursos
    
    // Constantes de tiempo (en milisegundos)
    private static final int T = 400;        // Tiempo base T = 400ms
    private static final int PHASE1_TIME = T;        // Fase 1: T = 400ms
    private static final int PHASE2_TIME = T / 2;    // Fase 2: T/2 = 200ms
    private static final int PHASE3_TIME = T * 2;    // Fase 3: 2T = 800ms
    
    /**
     * Constructor del componente
     * @param id identificador único del componente
     * @param resources manejador de recursos compartidos
     */
    public Component(int id, ResourceManager resources) {
        this.id = id;
        this.resources = resources;
    }
    
    /**
     * Método principal que ejecuta el proceso de ensamblado
     */
    @Override
    public void run() {
        try {
            // Primero debe conseguir una mesa para trabajar
            System.out.println("Componente " + id + ": Esperando mesa...");
            resources.acquireMesa();
            System.out.println("Componente " + id + ": Mesa asignada");
            
            // Ejecuta las 3 fases en orden
            executePhase1();
            executePhase2();
            executePhase3();
            
            // Libera la mesa al terminar
            resources.releaseMesa();
            System.out.println("Componente " + id + ": ¡COMPLETADO! Mesa liberada");
            
        } catch (InterruptedException e) {
            System.out.println("Componente " + id + ": Proceso interrumpido");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Ejecuta la Fase 1: requiere 1 destornillador + 1 pinza por 400ms
     */
    private void executePhase1() throws InterruptedException {
        System.out.println("Componente " + id + ": Iniciando Fase 1 - Esperando destornillador y pinza...");
        
        // Adquiere los recursos necesarios
        resources.acquirePhase1Resources();
        System.out.println("Componente " + id + ": Fase 1 EN PROGRESO (destornillador + pinza)");
        
        // Simula el trabajo de la fase 1
        Thread.sleep(PHASE1_TIME);
        
        // Libera los recursos
        resources.releasePhase1Resources();
        System.out.println("Componente " + id + ": Fase 1 COMPLETADA");
    }
    
    /**
     * Ejecuta la Fase 2: requiere 2 sargentos por 200ms
     */
    private void executePhase2() throws InterruptedException {
        System.out.println("Componente " + id + ": Iniciando Fase 2 - Esperando 2 sargentos...");
        
        // Adquiere los recursos necesarios
        resources.acquirePhase2Resources();
        System.out.println("Componente " + id + ": Fase 2 EN PROGRESO (2 sargentos)");
        
        // Simula el trabajo de la fase 2
        Thread.sleep(PHASE2_TIME);
        
        // Libera los recursos
        resources.releasePhase2Resources();
        System.out.println("Componente " + id + ": Fase 2 COMPLETADA");
    }
    
    /**
     * Ejecuta la Fase 3: requiere 2 pinzas por 800ms
     */
    private void executePhase3() throws InterruptedException {
        System.out.println("Componente " + id + ": Iniciando Fase 3 - Esperando 2 pinzas...");
        
        // Adquiere los recursos necesarios
        resources.acquirePhase3Resources();
        System.out.println("Componente " + id + ": Fase 3 EN PROGRESO (2 pinzas)");
        
        // Simula el trabajo de la fase 3
        Thread.sleep(PHASE3_TIME);
        
        // Libera los recursos
        resources.releasePhase3Resources();
        System.out.println("Componente " + id + ": Fase 3 COMPLETADA");
    }
}