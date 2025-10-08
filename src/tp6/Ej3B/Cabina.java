package tp6.Ej3B;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Cabina {
    private int id;
    private Semaphore semaforo;
    private Random random;

    public Cabina(int id) {
        this.id = id;
        this.semaforo = new Semaphore(1);
        this.random = new Random();
    }

    public int getId() {
        return id;
    }

    public Semaphore getSemaforo() {
        return semaforo;
    }

    public void atenderAuto(int idAuto) {
        try {
            System.out.println("Auto " + idAuto + " está siendo atendido en la cabina " + id);
            Thread.sleep((1 + random.nextInt(3)) * 1000);
            System.out.println("Auto " + idAuto + " terminó en la cabina " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }
}