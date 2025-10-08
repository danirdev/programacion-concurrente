package tp6.actividad3.pb;

import java.util.concurrent.Semaphore;

// Versión 3b: Individualizando cada cabina
class Cabina {
    private int numeroCabina;
    private boolean disponible;
    private Semaphore mutex; // Para controlar acceso exclusivo a esta cabina
    
    public Cabina(int numeroCabina, boolean disponible) {
        this.numeroCabina = numeroCabina;
        this.disponible = disponible;
        this.mutex = new Semaphore(1);
    }
    
    public int getNumeroCabina() {
        return numeroCabina;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public Semaphore getMutex() {
        return mutex;
    }
}