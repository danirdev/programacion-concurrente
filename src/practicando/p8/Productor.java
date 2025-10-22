package practicando.p8;

class Productor extends Thread {
    private MonitorBuffer buffer;
    public Productor(MonitorBuffer b) { buffer = b; }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.producir(i);
        }
    }
}
