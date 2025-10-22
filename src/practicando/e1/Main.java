package practicando.e1;

public class Main {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria(1000);
        cuenta.mostrarSaldo();
        cuenta.depositar(500);
        cuenta.retirar(200);
        cuenta.mostrarSaldo();
    }
}

