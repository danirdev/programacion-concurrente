package tp3.actividad22;

public class Main {

    public static void main(String[] args) {

        Contador contador = new Contador(1000000);

        System.out.println("Valor inicial del contador: " + contador.getValor());

        Thread hiloInc = new Incrementador(contador);
        Thread hiloDec = new Decrementador(contador);

        hiloInc.start();
        hiloDec.start();

        try {
            hiloInc.join();
            hiloDec.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Valor final del contador: " + contador.getValor());
    
    }
    
}
