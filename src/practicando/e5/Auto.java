package practicando.e5;

import java.util.concurrent.Semaphore;

class Auto extends Thread {
    private String nombre;
    private Semaphore estacionamiento;

    public Auto(String nombre, Semaphore estacionamiento) {
        this.nombre = nombre;
        this.estacionamiento = estacionamiento;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " quiere estacionar...");
            estacionamiento.acquire(); // pide un permiso para entrar

            System.out.println(nombre + " está estacionado 🚗");
            Thread.sleep(2000); // simula tiempo estacionado

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(nombre + " sale del estacionamiento.");
            estacionamiento.release(); // libera el permiso al salir
        }
    }
}

