package tp3.actividad11;

public class Main {

    public static void secuenciaNormal() {
        System.out.println("Secuencia de X");
        for (int i = 0; i < 100; i++) {
            System.out.print("." + i + "X" + ". ");
        }
        System.out.println();
        System.out.println("Secuencia de Y");
        for (int i = 0; i < 100; i++) {
            System.out.print("." + i + "Y" + ". ");
        }
    }

    public static void main(String[] args) {

        Hilo hilo1 = new Hilo("X");
        Hilo hilo2 = new Hilo("Y");

        System.out.println("Secuancia normal");
        secuenciaNormal();
        System.out.println();
  
        System.out.println("\nSecuencia con hilos");
        hilo1.start();
        hilo2.start();

    }
    
}
