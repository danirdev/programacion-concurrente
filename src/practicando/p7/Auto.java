package practicando.p7;

import java.util.concurrent.Semaphore;

class Auto extends Thread {
    private String nombre;
    private Semaphore estacion;

    public Auto(String nombre, Semaphore estacion) {
        this.nombre = nombre;
        this.estacion = estacion;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " espera para cargar...");
            estacion.acquire(); // intenta entrar a la estación

            System.out.println(nombre + " está cargando 🚗⚡");
            Thread.sleep(2000); // simula el tiempo de carga

            System.out.println(nombre + " terminó de cargar y se va.");
            estacion.release(); // libera su lugar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

