
package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;

public class Peaje {
    public static void main(String[] args) {

        Semaphore semaforoCabinas = new Semaphore(2);
        
        System.out.println("ESTACIÓN DE PEAJE");
        System.out.println("Cabinas disponibles: 3 (pero 1 empleado está en el baño)");
        System.out.println("Cola de espera: 50 autos\n");
        
        EmpleadoNoAguanta empleado = new EmpleadoNoAguanta(semaforoCabinas);
        empleado.start();
        
        ClientePeaje[] clientes = new ClientePeaje[50];
        
        for (int i = 0; i < 50; i++) {
            clientes[i] = new ClientePeaje(i + 1, semaforoCabinas);
            clientes[i].start();
        }
        
        for (int i = 0; i < 50; i++) {
            try {
                clientes[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); 
                break; 
            }
        }
        
        System.out.println("\nTodos los clientes han sido atendidos");
    }
}

