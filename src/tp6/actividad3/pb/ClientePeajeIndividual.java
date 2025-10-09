package tp6.actividad3.pb;

import java.util.Random;
import java.util.concurrent.Semaphore;

class ClientePeajeIndividual extends Thread {
    private int numeroCliente;
    private Cabina[] cabinas;
    private Semaphore semaforoGeneral;
    private Random random;
    
    public ClientePeajeIndividual(int numeroCliente, Cabina[] cabinas, Semaphore semaforoGeneral) {
        this.numeroCliente = numeroCliente;
        this.cabinas = cabinas;
        this.semaforoGeneral = semaforoGeneral;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        Cabina cabinaAsignada = null;
        boolean semaforoAdquirido = false;
        
        try {
            // Esperar a que haya alguna cabina disponible
            semaforoGeneral.acquire();
            semaforoAdquirido = true;
            
            // Buscar una cabina disponible
            while (cabinaAsignada == null) {
                for (Cabina cabina : cabinas) {
                    if (cabina.isDisponible() && cabina.getMutex().tryAcquire()) {
                        cabinaAsignada = cabina;
                        break;
                    }
                }
                if (cabinaAsignada == null) {
                    Thread.sleep(50); // Pequeña espera antes de reintentar
                }
            }
            
            // Comienza la atención
            System.out.println("Cliente " + numeroCliente + " comienza a ser atendido en CABINA " 
                             + cabinaAsignada.getNumeroCabina());
            
            // Tiempo de atención variable entre 1 y 3 segundos
            int tiempoAtencion = 1000 + random.nextInt(2001); // 1000-3000 ms
            Thread.sleep(tiempoAtencion);
            
            // Finaliza la atención
            System.out.println("Cliente " + numeroCliente + " finaliza su atención en CABINA " 
                             + cabinaAsignada.getNumeroCabina());
            
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            // Libera los recursos en orden inverso al que fueron adquiridos
            if (cabinaAsignada != null) {
                cabinaAsignada.getMutex().release();
            }
            if (semaforoAdquirido) {
                semaforoGeneral.release();
            }
        }
    }
}