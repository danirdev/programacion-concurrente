package practicando.p4;

// 1️⃣ Definimos una clase que implementa Runnable
class AutoRunnable implements Runnable {
    private String nombre;

    public AutoRunnable(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " avanza a la posición " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nombre + " llegó a la meta!");
    }
}
