package practicando2.p3;

public class HiloContador extends Thread {
    private Contador contador;

    public HiloContador(String nNombre, Contador nContador) {
        super(nNombre);
        this.contador = nContador;
    }

    @Override
    public void run() {
        try {
            // ESTA ES LA REGIÓN CRÍTICA SIN CONTROL:
            for (int j = 0; j < 10; j++) {
                int i = contador.getContador(); // 1. Lee el valor
                
                // Hilo se duerme: ¡El otro hilo puede leer el mismo valor aquí! [cite: 2900]
                Thread.sleep((int) (Math.random() * 10)); 
                
                contador.setContador(i + 1); // 2. Escribe el nuevo valor
                System.out.println(getName() + " pone el contador a " + i);
            }
        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar el método sleep");
        }
    }
}
