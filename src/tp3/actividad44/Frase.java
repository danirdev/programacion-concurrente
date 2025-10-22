package tp3.actividad44;

public class Frase extends Thread {
    
    private String frase;

    public Frase(String frase) {
        this.frase = frase;
    }

    public String getFrase() {
        return frase;
    }

    @Override
    public void run() {
        for (char c : frase.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
}
