package tp6.Ej3B;

public class PeajeMain {
    private static final int TOTAL_AUTOS = 50;

    public static void main(String[] args) {

        Cabina[] cabinas = new Cabina[3];
        for (int i = 0; i < 3; i++) {
            cabinas[i] = new Cabina(i + 1);
        }


        try {
            cabinas[2].getSemaforo().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Iniciando simulación de peaje con 3 cabinas (una inactiva por 15 segundos)...");
        System.out.println("----------------------------------------------------");

        // Habilita la cabina 3
        new Thread(() -> {
            try {
                Thread.sleep(15000);
                System.out.println(">> Cabina 3 vuelve del baño y ya está disponible!");
                cabinas[2].getSemaforo().release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Crea los autos
        for (int i = 1; i <= TOTAL_AUTOS; i++) {
            Thread auto = new Thread(new Auto(i, cabinas));
            auto.start();
            try {
                Thread.sleep(200); // pausa entre autos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------------------------------");
        System.out.println("Simulación finalizada.");
    }
}