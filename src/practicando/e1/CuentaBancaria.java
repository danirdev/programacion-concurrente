package practicando.e1;

public class CuentaBancaria {
    private double saldo;

    // Constructor
    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Método para depositar dinero
    public void depositar(double monto) {
        this.saldo += monto;
    }

    // Método para retirar dinero (verificar saldo)
    public void retirar(double monto) {
        if (monto <= this.saldo) {
            this.saldo -= monto;
        } else {
            System.out.println("Fondos insuficientes para retirar " + monto);
        }
    }

    // Método para mostrar el saldo
    public void mostrarSaldo() {
        System.out.println("Saldo actual: " + this.saldo);
    }
}

