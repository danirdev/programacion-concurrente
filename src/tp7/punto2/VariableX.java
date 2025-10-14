package tp7.punto2;

import java.util.concurrent.Semaphore;

public class VariableX {

    private static int x = 0;   // Variable compartida
    
    // Definimos los semáforos
    private static Semaphore s1 = new Semaphore(1);     // Iniciado en 1
    private static Semaphore s2 = new Semaphore(0);     // Iniciado en 0
    
    // Proceso P1
    static class P1 implements Runnable {
        @Override
        public void run() {
            try {
                s2.acquire();       // wait(s2)
                s1.acquire();       // wait(s1)
                setX(2 * getX());   // x := 2 * x
                s1.release();       // send(s1)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Proceso P2
    static class P2 implements Runnable {
        @Override
        public void run() {
            try {
                s1.acquire();           // wait(s1)
                setX(getX() * getX());  // x := x * x
                s1.release();           // send(s1)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Proceso P3
    static class P3 implements Runnable {
        @Override
        public void run() {
            try {
                s1.acquire();       // wait(s1)
                setX(getX() + 3);   // x := x + 3
                s2.release();       // send(s2)
                s1.release();       // send(s1)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		VariableX.x = x;
	}
	 
}