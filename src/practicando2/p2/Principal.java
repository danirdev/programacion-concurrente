package practicando2.p2;

public class Principal {
    public static void main(String[] args) {
        
        // 1. Crear las instancias de la tarea (Runnable)
        TareaContadora tarea1 = new TareaContadora("T-A");
        TareaContadora tarea2 = new TareaContadora("T-B");
        
        // 2. Crear los objetos Thread, asociando cada uno a una tarea
        // Utilizamos el constructor Thread(Runnable obj) [cite: 76]
        Thread hiloA = new Thread(tarea1);
        Thread hiloB = new Thread(tarea2);
        
        // 3. Poner los Threads en ejecución llamando al método start() [cite: 488]
        // NOTA: start() invoca a run() en un nuevo hilo. NO se llama directamente a run().
        hiloA.start();
        hiloB.start();
        
        // El hilo principal (main) continúa su ejecución sin esperar a los hilos A y B
        System.out.println("... El hilo principal (main) terminó de lanzar las tareas.");
    }
}
