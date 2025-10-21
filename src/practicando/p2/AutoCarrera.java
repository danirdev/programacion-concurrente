package practicando.p2;

public class AutoCarrera extends Thread {
    private String nombre;

    public AutoCarrera(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " avanza a la posición " + i);
            try {
                Thread.sleep(500); // pausa medio segundo entre pasos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nombre + " ha llegado a la meta!");
    }
}

