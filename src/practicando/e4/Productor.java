package practicando.e4;

class Productor extends Thread {
    private Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.producir(i);
        }
    }
}
