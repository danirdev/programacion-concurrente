package tp7.punto2;

public class Main extends VariableX {
	public static void main(String[] args) {

		// Creamos los tres hilos que simulan los procesos P1, P2 y P3
        Thread p1 = new Thread(new P1());
        Thread p2 = new Thread(new P2());
        Thread p3 = new Thread(new P3());

        // Iniciamos los hilos
        p1.start();
        p2.start();
        p3.start();

        try {
            // Esperamos que los hilos terminen
            p1.join();
            p2.join();
            p3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Valor final de x: " + getX());
    }
	
}
