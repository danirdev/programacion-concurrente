package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;
import java.util.Random;

// Versión 3a: Sin individualizar cabinas
class ClientePeaje extends Thread {
    private int numeroCliente;
    private Semaphore semaforoCabinas;
    private Random random;
    
    public ClientePeaje(int numeroCliente, Semaphore semaforoCabinas) {
        this.numeroCliente = numeroCliente;
        this.semaforoCabinas = semaforoCabinas;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            // El cliente espera una cabina disponible
            semaforoCabinas.acquire();
            
            // Comienza la atención
            System.out.println("Cliente " + numeroCliente + " comienza a ser atendido");
            
            // Tiempo de atención variable entre 1 y 3 segundos
            int tiempoAtencion = 1000 + random.nextInt(2001); // 1000-3000 ms
            Thread.sleep(tiempoAtencion);
            
            // Finaliza la atención
            System.out.println("Cliente " + numeroCliente + " finaliza su atención");
            
            // Libera la cabina
            semaforoCabinas.release();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
