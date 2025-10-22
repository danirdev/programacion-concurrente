package practicando.e6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GestorDescargas {
    public static void main(String[] args) {
        // Crea un pool con 2 hilos
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Asigna 4 descargas
        pool.execute(new DescargaArchivo("Archivo 1"));
        pool.execute(new DescargaArchivo("Archivo 2"));
        pool.execute(new DescargaArchivo("Archivo 3"));
        pool.execute(new DescargaArchivo("Archivo 4"));

        // Cierra el pool (no acepta más tareas)
        pool.shutdown();
    }
}
