package tp3.actividad44;

import java.util.Scanner;

/*

    Debe solicitar el ingreso de una frase por la entrada de teclado, a continuación (una vez 
    apretado Enter) deberá imprimir 10 veces dicha frase pero carácter por carácter 
    empleando hilos.

 */

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una frase: ");
        String frase = scanner.nextLine();

        Frase[] hilos = new Frase[10];

        for (int i = 0; i < 10; i++) {
            hilos[i] = new Frase(frase);
            hilos[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nImpresión finalizada.");
        scanner.close();
    }
    
}
