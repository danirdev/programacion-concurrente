package practicando.e6;

class DescargaArchivo implements Runnable {
    private String nombre;

    public DescargaArchivo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println(nombre + " → iniciando descarga...");
        try {
            Thread.sleep(2000); // simula el tiempo de descarga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(nombre + " → descarga completa ✅");
    }
}
