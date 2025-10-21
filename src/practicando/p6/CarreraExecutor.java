package practicando.p6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarreraExecutor {
    public static void main(String[] args) {
        // Creamos un pool de 2 hilos
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Asignamos tareas (se ejecutan con los hilos del pool)
        pool.execute(new AutoTarea("Auto rojo"));
        pool.execute(new AutoTarea("Auto azul"));
        pool.execute(new AutoTarea("Auto verde"));

        // Cerramos el pool cuando termina
        pool.shutdown();
    }
}
