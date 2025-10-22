package practicando.p8;

public class Main {
    public static void main(String[] args) {
        MonitorBuffer buffer = new MonitorBuffer();
        new Productor(buffer).start();
        new Consumidor(buffer).start();
    }
}
