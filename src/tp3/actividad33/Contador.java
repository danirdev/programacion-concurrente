package tp3.actividad33;

public class Contador {
    private int valor;

    public Contador(int valorInicial) {
        this.valor = valorInicial;
    }

    public int getValor() {
        return valor;
    }

    public void incrementar() {
        valor++;
    }

    public void decrementar() {
        valor--;
    }
    
}
