package practicando.p6;

// Definimos una tarea
class AutoTarea implements Runnable {
    private String nombre;

    public AutoTarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(nombre + " está avanzando al paso " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(nombre + " terminó la carrera!");
    }
}
