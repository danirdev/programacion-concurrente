package practicando.e2;

public class Corredor extends Thread {
    private String nombre;

    public Corredor(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " corre paso " + i);
            try {
                Thread.sleep(1000); // pausa de 1 segundo entre pasos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nombre + " llegó a la meta!");
    }
}
