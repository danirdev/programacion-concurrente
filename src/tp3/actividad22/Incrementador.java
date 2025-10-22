package tp3.actividad22;

public class Incrementador extends Thread {
    
    private Contador contador;

    public Incrementador(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            contador.incrementar();
        }
    }
}
