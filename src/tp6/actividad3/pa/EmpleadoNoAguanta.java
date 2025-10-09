package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;

// Hilo para simular el empleado que vuelve del baño
class EmpleadoNoAguanta extends Thread {

    // Atributos
    private Semaphore semaforoCabinas;
    
    // Constructor
    public EmpleadoNoAguanta(Semaphore semaforoCabinas) {
        this.semaforoCabinas = semaforoCabinas;
    }
    
    // Método que simula el tiempo que el empleado está en el baño
    @Override
    public void run() {

        try {
            // Indicamos que una cabina está cerrada
            System.out.println("!!! Una cabina está cerrada, el empleado fue al baño...");
            // Esperamos 15 segundos
            Thread.sleep(15000);          
          
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // El empleado vuelve del baño y abre la cabina
            System.out.println(">>> El empleado volvió del baño, la cabina está disponible nuevamente");
            semaforoCabinas.release();
        }
    }
}
