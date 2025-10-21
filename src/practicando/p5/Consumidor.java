package practicando.p5;

// Hilo consumidor
class Consumidor extends Thread {
    private Buffer buffer;
    public Consumidor(Buffer b) { buffer = b; }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.consumir();
        }
    }
}
