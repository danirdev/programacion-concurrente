package practicando2.p2;

// Esta clase define la TAREA a ejecutar
public class TareaContadora implements Runnable {
    private String nombre;
    
    // Constructor para darle un nombre a cada tarea
    public TareaContadora(String nombre) {
        this.nombre = nombre;
    }
    
    // El corazón de la tarea concurrente: el método run()
    @Override
    public void run() {
        System.out.println("-> Tarea " + nombre + " INICIADA por el Hilo: " + Thread.currentThread().getName());
        
        // El trabajo real que queremos que se ejecute concurrentemente
        for (int i = 1; i <= 5; i++) {
            System.out.println("  " + nombre + ": contando " + i);
            try {
                // Hacemos que el hilo duerma un poco para simular trabajo y ver la concurrencia
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                // La interrupción es una excepción que debe manejarse con sleep() [cite: 543]
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("<- Tarea " + nombre + " FINALIZADA.");
    }
}
