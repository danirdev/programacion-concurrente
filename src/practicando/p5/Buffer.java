package practicando.p5;

// Clase que representa el depósito compartido
class Buffer {
    private int dato;
    private boolean disponible = false;

    public synchronized void producir(int valor) {
        while (disponible) {  // si ya hay un dato, esperar
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dato = valor;
        disponible = true;
        System.out.println("Productor produce: " + valor);
        notify();  // avisar al consumidor
    }

    public synchronized int consumir() {
        while (!disponible) {  // si no hay dato, esperar
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumidor consume: " + dato);
        disponible = false;
        notify();  // avisar al productor
        return dato;
    }
}
