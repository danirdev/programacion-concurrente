package practicando.p5;

// Hilo productor
class Productor extends Thread {
    private Buffer buffer;
    public Productor(Buffer b) { buffer = b; }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.producir(i);
        }
    }
}
