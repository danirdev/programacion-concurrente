package practicando.e5;

import java.util.concurrent.Semaphore;

public class Estacionamiento {
    public static void main(String[] args) {
        Semaphore estacionamiento = new Semaphore(3); // 3 lugares disponibles

        for (int i = 1; i <= 5; i++) {
            new Auto("Auto " + i, estacionamiento).start();
        }
    }
}
