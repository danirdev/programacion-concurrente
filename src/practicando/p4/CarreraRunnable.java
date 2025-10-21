package practicando.p4;

// 2️⃣ Creamos los hilos en el programa principal
public class CarreraRunnable {
    public static void main(String[] args) {
        AutoRunnable auto1 = new AutoRunnable("Auto rojo");
        AutoRunnable auto2 = new AutoRunnable("Auto azul");

        Thread hilo1 = new Thread(auto1);
        Thread hilo2 = new Thread(auto2);

        hilo1.start();
        hilo2.start();
    }
}
