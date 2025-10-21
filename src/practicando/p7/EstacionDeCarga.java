package practicando.p7;

import java.util.concurrent.Semaphore;

public class EstacionDeCarga {
    public static void main(String[] args) {
        Semaphore estacion = new Semaphore(2); // 2 lugares disponibles

        for (int i = 1; i <= 5; i++) {
            new Auto("Auto " + i, estacion).start();
        }
    }
}
