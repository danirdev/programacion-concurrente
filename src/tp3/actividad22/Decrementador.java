package tp3.actividad22;

public class Decrementador extends Thread {

    private Contador contador;

    public Decrementador(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            contador.decrementar();
        }
    }
    
}
