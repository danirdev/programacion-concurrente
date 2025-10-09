package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;
import java.util.Random;

// Hilo para simular un cliente que llega al peaje
class ClientePeaje extends Thread {

    // Atributos
    private int numeroCliente;
    private Semaphore semaforoCabinas;
    private Random random;
    
    // Constructor
    public ClientePeaje(int numeroCliente, Semaphore semaforoCabinas) {
        this.numeroCliente = numeroCliente;
        this.semaforoCabinas = semaforoCabinas;
        this.random = new Random();
    }
    
    // Método que simula la atención del cliente en la cabina
    @Override
    public void run() {

        // Variable para rastrear si adquirió el semáforo
        boolean adquirido = false; 

        try {
            // El cliente espera una cabina disponible
            semaforoCabinas.acquire();
            // Solo marca como true si adquiere exitosamente
            adquirido = true;       
            // Comienza la atención
            System.out.println("Cliente " + numeroCliente + " comienza a ser atendido");
            // Tiempo de atención variable entre 1 y 3 segundos
            int tiempoAtencion = 1000 + random.nextInt(2001);
            // Simula el tiempo de atención
            Thread.sleep(tiempoAtencion);
                       
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Restaura el estado de interrupción
            Thread.currentThread().interrupt();

        } finally {
            // Solo libera el semáforo si lo adquirió
            if (adquirido) {
                // Finaliza la atención
                System.out.println("Cliente " + numeroCliente + " finaliza su atención");
                // Libera el semáforo para que otro cliente pueda ser atendido
                semaforoCabinas.release();
            }

        }

    }

}
