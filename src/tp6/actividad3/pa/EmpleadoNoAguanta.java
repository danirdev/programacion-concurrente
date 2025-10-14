
package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;

class EmpleadoNoAguanta extends Thread {

    private Semaphore semaforoCabinas;
    
    public EmpleadoNoAguanta(Semaphore semaforoCabinas) {
        this.semaforoCabinas = semaforoCabinas;
    }
    
    @Override
    public void run() {

        try {
            System.out.println("!!! Una cabina está cerrada, el empleado fue al baño...");
            Thread.sleep(15000);          
          
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("!!! El empleado volvió del baño, la cabina está disponible nuevamente");
            semaforoCabinas.release();
        }
    }
}

