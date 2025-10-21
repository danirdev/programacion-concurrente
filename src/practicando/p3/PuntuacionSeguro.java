package practicando.p3;

public class PuntuacionSeguro {
    static int puntos = 0;

    public static synchronized void sumar() {
        puntos++;
    }

    public static void main(String[] args) {
        Thread auto1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sumar();
            }
        });

        Thread auto2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sumar();
            }
        });

        auto1.start();
        auto2.start();

        try {
            auto1.join();
            auto2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Puntos finales: " + puntos);
    }
}

