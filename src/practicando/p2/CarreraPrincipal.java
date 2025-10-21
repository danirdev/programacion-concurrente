package practicando.p2;

public class CarreraPrincipal {
    public static void main(String[] args) {
        AutoCarrera auto1 = new AutoCarrera("Auto rojo");
        AutoCarrera auto2 = new AutoCarrera("Auto azul");

        auto1.start();  // inicia hilo 1
        auto2.start();  // inicia hilo 2
    }
}

