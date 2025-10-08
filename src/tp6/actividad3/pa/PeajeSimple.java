package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;

public class PeajeSimple {
    public static void main(String[] args) {
        // Semáforo inicializado en 2 (solo 2 cabinas disponibles al inicio)
        Semaphore semaforoCabinas = new Semaphore(2);
        
        System.out.println("=== ESTACIÓN DE PEAJE ===");
        System.out.println("Cabinas disponibles: 3 (pero 1 empleado está en el baño)");
        System.out.println("Cola de espera: 50 autos\n");
        
        // Iniciar el hilo del empleado que vuelve
        EmpleadoVuelve empleado = new EmpleadoVuelve(semaforoCabinas);
        empleado.start();
        
        // Crear y lanzar los 50 clientes
        ClientePeaje[] clientes = new ClientePeaje[50];
        
        for (int i = 0; i < 50; i++) {
            clientes[i] = new ClientePeaje(i + 1, semaforoCabinas);
            clientes[i].start();
        }
        
        // Esperar a que todos los clientes terminen
        for (int i = 0; i < 50; i++) {
            try {
                clientes[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n=== Todos los clientes han sido atendidos ===");
    }
}
