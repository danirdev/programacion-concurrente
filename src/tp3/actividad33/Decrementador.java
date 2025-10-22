package tp3.actividad33;

public class Decrementador extends Thread {

    private Contador contador;

    public Decrementador(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                contador.decrementar();
                Thread.sleep(50 + (int)(Math.random() * 100));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
