package tp4.actividad1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase Cola que implementa una estructura FIFO (First In, First Out)
 * para el problema del Productor-Consumidor.
 * Puede ser configurada como cola infinita o con tamaño limitado.
 */
public class Cola {
    private Queue<Integer> elementos;
    private int capacidadMaxima;
    private boolean esInfinita;
    
    /**
     * Constructor para cola infinita
     */
    public Cola() {
        this.elementos = new LinkedList<>();
        this.esInfinita = true;
        this.capacidadMaxima = -1;
    }
    
    /**
     * Constructor para cola con capacidad limitada
     * @param capacidad Capacidad máxima de la cola
     */
    public Cola(int capacidad) {
        this.elementos = new LinkedList<>();
        this.esInfinita = false;
        this.capacidadMaxima = capacidad;
    }
    
    /**
     * Método sincronizado para agregar un elemento a la cola
     * @param elemento Elemento a agregar
     */
    public synchronized void agregar(int elemento) {
        // Si la cola tiene capacidad limitada, esperar hasta que haya espacio
        while (!esInfinita && elementos.size() >= capacidadMaxima) {
            try {
                System.out.println("Cola llena. Productor esperando...");
                wait(); // Esperar hasta que haya espacio
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        elementos.offer(elemento);
        System.out.println("Elemento " + elemento + " agregado a la cola. Tamaño actual: " + elementos.size());
        notifyAll(); // Notificar a los consumidores que hay elementos disponibles
    }
    
    /**
     * Método sincronizado para consumir un elemento de la cola
     * @return El elemento consumido
     */
    public synchronized int consumir() {
        // Esperar hasta que haya elementos en la cola
        while (elementos.isEmpty()) {
            try {
                System.out.println("Cola vacía. Consumidor esperando...");
                wait(); // Esperar hasta que haya elementos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        
        int elemento = elementos.poll();
        System.out.println("Elemento " + elemento + " consumido de la cola. Tamaño actual: " + elementos.size());
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
}
