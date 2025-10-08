package tp6.Ej3B;

public class Auto implements Runnable {
    private int id;
    private Cabina[] cabinas;

    public Auto(int id, Cabina[] cabinas) {
        this.id = id;
        this.cabinas = cabinas;
    }

    @Override
    public void run() {
        boolean atendido = false;

        while (!atendido) {
            for (Cabina cabina : cabinas) {
                if (cabina.getSemaforo().tryAcquire()) {
                    cabina.atenderAuto(id);
                    atendido = true;
                    break;
                }
            }

            if (!atendido) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}