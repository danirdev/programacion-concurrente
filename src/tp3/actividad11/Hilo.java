package tp3.actividad11;

public class Hilo extends Thread {

    private String nombre;

    public Hilo(String nombre) {
        this.nombre = nombre;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("." + i + nombre + ". ");
        }
    }
    
}
