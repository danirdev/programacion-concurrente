package tp9.actividad2;

import java.util.concurrent.Semaphore;

/**
 * Clase que maneja los recursos compartidos (herramientas) usando semáforos
 */
public class ResourceManager {
    // Semáforos para controlar el acceso a cada tipo de herramienta
    private final Semaphore pinzas;          // 4 pinzas disponibles
    private final Semaphore destornilladores; // 2 destornilladores disponibles
    private final Semaphore sargentos;       // 4 sargentos disponibles
    private final Semaphore mesas;          // 3 mesas disponibles
    
    /**
     * Constructor: inicializa los semáforos con la cantidad de recursos disponibles
     */
    public ResourceManager() {
        pinzas = new Semaphore(4);          // 4 pinzas
        destornilladores = new Semaphore(2); // 2 destornilladores
        sargentos = new Semaphore(4);       // 4 sargentos
        mesas = new Semaphore(3);           // 3 mesas
    }
    
    // Métodos para adquirir recursos
    
    /**
     * Intenta adquirir una mesa para trabajar
     */
    public void acquireMesa() throws InterruptedException {
        mesas.acquire();
    }
    
    /**
     * Libera una mesa
     */
    public void releaseMesa() {
        mesas.release();
    }
    
    /**
     * Adquiere recursos para la Fase 1: 1 destornillador + 1 pinza
     */
    public void acquirePhase1Resources() throws InterruptedException {
        destornilladores.acquire(); // Toma 1 destornillador
        pinzas.acquire();           // Toma 1 pinza
    }
    
    /**
     * Libera recursos de la Fase 1
     */
    public void releasePhase1Resources() {
        destornilladores.release(); // Libera el destornillador
        pinzas.release();           // Libera la pinza
    }
    
    /**
     * Adquiere recursos para la Fase 2: 2 sargentos
     */
    public void acquirePhase2Resources() throws InterruptedException {
        sargentos.acquire(2); // Toma 2 sargentos
    }
    
    /**
     * Libera recursos de la Fase 2
     */
    public void releasePhase2Resources() {
        sargentos.release(2); // Libera los 2 sargentos
    }
    
    /**
     * Adquiere recursos para la Fase 3: 2 pinzas
     */
    public void acquirePhase3Resources() throws InterruptedException {
        pinzas.acquire(2); // Toma 2 pinzas
    }
    
    /**
     * Libera recursos de la Fase 3
     */
    public void releasePhase3Resources() {
        pinzas.release(2); // Libera las 2 pinzas
    }
}