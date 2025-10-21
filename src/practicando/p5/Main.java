package practicando.p5;

// Programa principal
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        new Productor(buffer).start();
        new Consumidor(buffer).start();
    }
}