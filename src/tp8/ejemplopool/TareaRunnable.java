package tp8.ejemplopool;
import java.util.concurrent.TimeUnit;

public class TareaRunnable implements Runnable {
	
	public void run()
	{
		System.out.println("Ejecutando Tarea dentro de: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
	}

}
