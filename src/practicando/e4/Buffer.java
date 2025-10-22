package practicando.e4;

class Buffer {
    private int dato;
    private boolean disponible = false;

    // producir un número
    public synchronized void producir(int valor) {
        while (disponible) {
            try {
                wait(); // espera hasta que se consuma
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dato = valor;
        disponible = true;
        System.out.println("Produce: " + valor);
        notify(); // avisa al consumidor que hay dato
    }

    // consumir un número
    public synchronized int consumir() {
        while (!disponible) {
            try {
                wait(); // espera hasta que haya algo para consumir
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consume: " + dato);
        disponible = false;
        notify(); // avisa al productor que puede producir de nuevo
        return dato;
    }
}
