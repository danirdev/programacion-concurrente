package practicando.e2;

public class Carrera {
    public static void main(String[] args) {
        Corredor c1 = new Corredor("Daniel");
        Corredor c2 = new Corredor("Ana");
        Corredor c3 = new Corredor("Luis");

        c1.start(); // cada start() ejecuta el run() en un hilo distinto
        c2.start();
        c3.start();
    }
}
