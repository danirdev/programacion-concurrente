package practicando2.p1;

import java.util.Scanner;

public class Operacion {
    // Los atributos 'protected' permiten que las clases hijas accedan directamente.
    protected int valor1;
    protected int valor2;
    protected int resultado;
    private Scanner teclado;

    // Constructor para inicializar el Scanner
    public Operacion() {
        this.teclado = new Scanner(System.in);
    }

    // Método común para cargar el primer valor
    public void cargar1() {
        System.out.print("Ingrese el 1er. valor: ");
        valor1 = teclado.nextInt();
    }

    // Método común para cargar el segundo valor
    public void cargar2() {
        System.out.print("Ingrese el 2do. valor: ");
        valor2 = teclado.nextInt();
    }

    // Método común para mostrar el resultado
    public void mostrarResultado() {
        System.out.println("Resultado: " + resultado);
    }
}
