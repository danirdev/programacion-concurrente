package tp8.ejemplopool;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class EjemploExecutor {
	 public static void main(String[] args) {
	        System.out.println("Dentro de : " + Thread.currentThread().getName());

	        System.out.println("Creando el 'Executor Service' con un Pool de hilos de tama�o 2");
	        ExecutorService executorService = Executors.newFixedThreadPool(2);

	        Runnable task1 = new TareaRunnable();
	        Runnable task2 = new TareaRunnable();
	        Runnable task3 = new TareaRunnable();
	        Runnable task4 = new TareaRunnable();
	        Runnable task5 = new TareaRunnable();
	        
	        System.out.println("Enviando las tareas a ejecuci�n...");
	        executorService.submit(task1);
	        executorService.submit(task2);
	        executorService.submit(task3);
	        executorService.submit(task4);
	        executorService.submit(task5);
	        
	        executorService.shutdown();
	 }
}
