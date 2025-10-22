package practicando2.p3;

public class HiloContadorS extends Thread {
    private Contador contador;

    public HiloContadorS(String nNombre, Contador nContador) {
        super(nNombre);
        this.contador = nContador;
    }

    // ... dentro de la clase HiloContador ...
    @Override
    public void run() {
        try {
            // El bloque synchronized garantiza que solo UN hilo a la vez
            // puede ejecutar el código dentro de las llaves, usando el objeto 'contador' como llave.
            synchronized (contador) { //[cite: 2926]
                for (int j = 0; j < 10; j++) {
                    int i = contador.getContador();
                    Thread.sleep((int) (Math.random() * 10)); // El sleep es ahora seguro
                    contador.setContador(i + 1);
                    System.out.println(getName() + " pone el contador a " + i);
                }
            } // Fin del bloque synchronized
        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar el método sleep");
        }
    }
}

