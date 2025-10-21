package practicando.p2;

public class EjemploConcurrencia {
    public static void main(String[] args) {
        // Tarea 1
        Thread hilo1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Cocinero: preparando plato " + i);
            }
        });

        // Tarea 2
        Thread hilo2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Mesero: sirviendo mesa " + i);
            }
        });

        hilo1.start(); // se ejecuta al mismo tiempo
        hilo2.start();
    }
}

