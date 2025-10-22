package tp3.actividad33;

public class Incrementador extends Thread {
    
    private Contador contador;

    public Incrementador(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                contador.incrementar();
                Thread.sleep(50 + (int)(Math.random() * 100));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
