package practicando.p8;

class Consumidor extends Thread {
    private MonitorBuffer buffer;
    public Consumidor(MonitorBuffer b) { buffer = b; }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.consumir();
        }
    }
}
