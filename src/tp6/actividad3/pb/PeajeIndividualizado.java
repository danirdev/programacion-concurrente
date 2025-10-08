package tp6.actividad3.pb;

import java.util.concurrent.Semaphore;

public class PeajeIndividualizado {
    public static void main(String[] args) {
        // Crear las 3 cabinas
        Cabina[] cabinas = new Cabina[3];
        cabinas[0] = new Cabina(1, true);   // Disponible
        cabinas[1] = new Cabina(2, true);   // Disponible
        cabinas[2] = new Cabina(3, false);  // No disponible (empleado en el baño)
        
        // Semáforo general inicializado en 2 (solo 2 cabinas disponibles al inicio)
        Semaphore semaforoGeneral = new Semaphore(2);
        
        System.out.println("=== ESTACIÓN DE PEAJE - CABINAS INDIVIDUALIZADAS ===");
        System.out.println("Cabinas totales: 3");
        System.out.println("Cabina 1: Disponible");
        System.out.println("Cabina 2: Disponible");
        System.out.println("Cabina 3: NO Disponible (empleado en el baño)");
        System.out.println("Cola de espera: 50 autos\n");
        
        // Iniciar el hilo del empleado que vuelve
        EmpleadoVuelveIndividual empleado = new EmpleadoVuelveIndividual(cabinas[2], semaforoGeneral);
        empleado.start();
        
        // Crear y lanzar los 50 clientes
        ClientePeajeIndividual[] clientes = new ClientePeajeIndividual[50];
        
        for (int i = 0; i < 50; i++) {
            clientes[i] = new ClientePeajeIndividual(i + 1, cabinas, semaforoGeneral);
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
