package practicando.p8;

class MonitorBuffer {
    private int dato;
    private boolean disponible = false;

    // método sincronizado: producir
    public synchronized void producir(int valor) {
        while (disponible) {  // si hay dato, esperar
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dato = valor;
        disponible = true;
        System.out.println("Produce: " + valor);
        notify(); // despierta al consumidor
    }

    // método sincronizado: consumir
    public synchronized int consumir() {
        while (!disponible) { // si no hay dato, esperar
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consume: " + dato);
        disponible = false;
        notify(); // despierta al productor
        return dato;
    }
}
