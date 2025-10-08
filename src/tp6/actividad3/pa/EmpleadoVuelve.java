package tp6.actividad3.pa;

import java.util.concurrent.Semaphore;

// Hilo para simular el empleado que vuelve del baño
class EmpleadoVuelve extends Thread {
    private Semaphore semaforoCabinas;
    
    public EmpleadoVuelve(Semaphore semaforoCabinas) {
        this.semaforoCabinas = semaforoCabinas;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(">>> Una cabina está cerrada, el empleado fue al baño...");
            Thread.sleep(15000); // Espera 15 segundos
            semaforoCabinas.release(); // Libera un permiso adicional
            System.out.println(">>> El empleado volvió del baño, la cabina está disponible nuevamente");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
