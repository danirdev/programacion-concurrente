package tp5.actividad1.productor_consumidor;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase ColaRunnable que implementa una estructura FIFO (First In, First Out)
 * para el problema del Productor-Consumidor usando interfaz Runnable.
 * Versión mejorada de la Cola del TP4 con mejor encapsulación.
 */
public class ColaRunnable {
    private Queue<Integer> elementos;
    private int capacidadMaxima;
    private boolean esInfinita;
    
    /**
     * Constructor para cola infinita
     */
    public ColaRunnable() {
        this.elementos = new LinkedList<>();
        this.esInfinita = true;
        this.capacidadMaxima = -1;
    }
    
    /**
     * Constructor para cola con capacidad limitada
     * @param capacidad Capacidad máxima de la cola
     */
    public ColaRunnable(int capacidad) {
        this.elementos = new LinkedList<>();
        this.esInfinita = false;
        this.capacidadMaxima = capacidad;
    }
    
    /**
     * Método sincronizado para agregar un elemento a la cola
     * @param elemento Elemento a agregar
     * @param productorId ID del productor para logging
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public synchronized void agregar(int elemento, int productorId) throws InterruptedException {
        // Si la cola tiene capacidad limitada, esperar hasta que haya espacio
        while (!esInfinita && elementos.size() >= capacidadMaxima) {
            System.out.println("Cola llena. Productor-" + productorId + " esperando...");
            wait(); // Esperar hasta que haya espacio
        }
        
        elementos.offer(elemento);
        System.out.println("Productor-" + productorId + " agregó elemento " + elemento + 
                         " a la cola. Tamaño actual: " + elementos.size());
        notifyAll(); // Notificar a los consumidores que hay elementos disponibles
    }
    
    /**
     * Método sincronizado para consumir un elemento de la cola
     * @param consumidorId ID del consumidor para logging
     * @return El elemento consumido
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public synchronized int consumir(int consumidorId) throws InterruptedException {
        // Esperar hasta que haya elementos en la cola
        while (elementos.isEmpty()) {
            System.out.println("Cola vacía. Consumidor-" + consumidorId + " esperando...");
            wait(); // Esperar hasta que haya elementos
        }
        
        int elemento = elementos.poll();
        System.out.println("Consumidor-" + consumidorId + " consumió elemento " + elemento + 
                         " de la cola. Tamaño actual: " + elementos.size());
        notifyAll(); // Notificar a los productores que hay espacio disponible
        return elemento;
    }
    
    /**
     * Obtiene el tamaño actual de la cola
     * @return Número de elementos en la cola
     */
    public synchronized int tamaño() {
        return elementos.size();
    }
    
    /**
     * Verifica si la cola está vacía
     * @return true si la cola está vacía, false en caso contrario
     */
    public synchronized boolean estaVacia() {
        return elementos.isEmpty();
    }
    
    /**
     * Verifica si la cola está llena (solo para colas con capacidad limitada)
     * @return true si la cola está llena, false en caso contrario
     */
    public synchronized boolean estaLlena() {
        return !esInfinita && elementos.size() >= capacidadMaxima;
    }
    
    /**
     * Obtiene información sobre la configuración de la cola
     * @return String con información de la cola
     */
    public String getInfo() {
        return esInfinita ? "Cola infinita" : "Cola limitada (capacidad: " + capacidadMaxima + ")";
    }
}
