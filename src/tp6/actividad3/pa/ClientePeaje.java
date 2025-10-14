
package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;
import java.util.Random;

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

        boolean adquirido = false; 

        try {
            semaforoCabinas.acquire();
            adquirido = true;       
            System.out.println("Cliente " + numeroCliente + " comienza a ser atendido");
            int tiempoAtencion = 1000 + random.nextInt(2001);
            Thread.sleep(tiempoAtencion);
                       
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } finally {
            if (adquirido) {
                System.out.println("Cliente " + numeroCliente + " finaliza su atención");
                semaforoCabinas.release();
            }
        }

    }

}


