package tp6.actividad3.pb;

import java.util.concurrent.Semaphore;

class EmpleadoVuelveIndividual extends Thread {
    private Cabina cabinaCerrada;
    private Semaphore semaforoGeneral;
    
    public EmpleadoVuelveIndividual(Cabina cabinaCerrada, Semaphore semaforoGeneral) {
        this.cabinaCerrada = cabinaCerrada;
        this.semaforoGeneral = semaforoGeneral;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(">>> CABINA " + cabinaCerrada.getNumeroCabina() 
                             + " está cerrada, el empleado fue al baño...");
            Thread.sleep(15000); // Espera 15 segundos
            
            // Habilitar la cabina
            cabinaCerrada.setDisponible(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">>> El empleado volvió del baño, CABINA " 
                             + cabinaCerrada.getNumeroCabina() 
                             + " está disponible nuevamente");
            semaforoGeneral.release(); // Libera un permiso adicional
        }
    }
}
