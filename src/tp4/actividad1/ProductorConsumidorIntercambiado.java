package tp4.actividad1;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del problema Productor-Consumidor intercambiando velocidades.
 * Caso c) del ejercicio: Intercambiar las velocidades de ambos casos anteriores.
 */
public class ProductorConsumidorIntercambiado {
    
    public static void main(String[] args) {
        System.out.println("=== PROBLEMA PRODUCTOR-CONSUMIDOR: VELOCIDADES INTERCAMBIADAS ===");
        System.out.println("Se ejecutarán ambos casos con velocidades intercambiadas:");
        System.out.println("1. Cola infinita con productores rápidos (400-800ms) y consumidores lentos (1000-1500ms)");
        System.out.println("2. Cola limitada (5) con productores lentos (1000-1500ms) y consumidores rápidos (400-800ms)");
        System.out.println("================================================================\n");
        
        // Ejecutar caso 1: Cola infinita con velocidades intercambiadas
        ejecutarColaInfinitaIntercambiada();
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Ejecutar caso 2: Cola limitada con velocidades intercambiadas
        ejecutarColaLimitadaIntercambiada();
    }
    
    /**
     * Ejecuta el caso de cola infinita con velocidades intercambiadas
     */
    private static void ejecutarColaInfinitaIntercambiada() {
        System.out.println("=== CASO 1: COLA INFINITA - VELOCIDADES INTERCAMBIADAS ===");
        System.out.println("Productores rápidos (400-800ms) vs Consumidores lentos (1000-1500ms)");
        System.out.println("Expectativa: Se acumularán elementos en la cola\n");
        
        Cola cola = new Cola(); // Cola infinita
        List<Productor> productores = new ArrayList<>();
        List<Consumidor> consumidores = new ArrayList<>();
        
        // Crear productores rápidos
        for (int i = 1; i <= 10; i++) {
            Productor productor = new Productor(cola, i, 400, 800);
            productores.add(productor);
        }
        
        // Crear consumidores lentos
        for (int i = 1; i <= 10; i++) {
            Consumidor consumidor = new Consumidor(cola, i, 1000, 1500);
            consumidores.add(consumidor);
        }
        
        // Iniciar hilos
        for (Productor productor : productores) {
            productor.start();
        }
        for (Consumidor consumidor : consumidores) {
            consumidor.start();
        }
        
        // Ejecutar por 20 segundos
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Finalizar hilos
        for (Productor productor : productores) {
            productor.interrupt();
        }
        for (Consumidor consumidor : consumidores) {
            consumidor.interrupt();
        }
        
        // Esperar terminación
        try {
            for (Productor productor : productores) {
                productor.join();
            }
            for (Consumidor consumidor : consumidores) {
                consumidor.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        mostrarEstadisticasCaso1(productores, consumidores, cola);
    }
    
    /**
     * Ejecuta el caso de cola limitada con velocidades intercambiadas
     */
    private static void ejecutarColaLimitadaIntercambiada() {
        System.out.println("=== CASO 2: COLA LIMITADA (5) - VELOCIDADES INTERCAMBIADAS ===");
        System.out.println("Productores lentos (1000-1500ms) vs Consumidores rápidos (400-800ms)");
        System.out.println("Expectativa: Los consumidores esperarán elementos\n");
        
        Cola cola = new Cola(5); // Cola limitada
        List<Productor> productores = new ArrayList<>();
        List<Consumidor> consumidores = new ArrayList<>();
        
        // Crear productores lentos
        for (int i = 1; i <= 10; i++) {
            Productor productor = new Productor(cola, i, 1000, 1500);
            productores.add(productor);
        }
        
        // Crear consumidores rápidos
        for (int i = 1; i <= 10; i++) {
            Consumidor consumidor = new Consumidor(cola, i, 400, 800);
            consumidores.add(consumidor);
        }
        
        // Iniciar hilos
        for (Productor productor : productores) {
            productor.start();
        }
        for (Consumidor consumidor : consumidores) {
            consumidor.start();
        }
        
        // Ejecutar por 20 segundos
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Finalizar hilos
        for (Productor productor : productores) {
            productor.interrupt();
        }
        for (Consumidor consumidor : consumidores) {
            consumidor.interrupt();
        }
        
        // Esperar terminación
        try {
            for (Productor productor : productores) {
                productor.join();
            }
            for (Consumidor consumidor : consumidores) {
                consumidor.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        mostrarEstadisticasCaso2(productores, consumidores, cola);
    }
    
    private static void mostrarEstadisticasCaso1(List<Productor> productores, 
                                               List<Consumidor> consumidores, 
                                               Cola cola) {
        System.out.println("\n=== ESTADÍSTICAS CASO 1 (Cola Infinita Intercambiada) ===");
        
        int totalProducido = 0;
        int totalConsumido = 0;
        
        for (Productor productor : productores) {
            totalProducido += productor.getElementosProducidos();
        }
        
        for (Consumidor consumidor : consumidores) {
            totalConsumido += consumidor.getElementosConsumidos();
        }
        
        System.out.println("Total producido: " + totalProducido);
        System.out.println("Total consumido: " + totalConsumido);
        System.out.println("Elementos acumulados en cola: " + cola.tamaño());
        System.out.println("Diferencia: " + (totalProducido - totalConsumido));
        System.out.println("ANÁLISIS: Productores rápidos → Acumulación en cola infinita");
    }
    
    private static void mostrarEstadisticasCaso2(List<Productor> productores, 
                                               List<Consumidor> consumidores, 
                                               Cola cola) {
        System.out.println("\n=== ESTADÍSTICAS CASO 2 (Cola Limitada Intercambiada) ===");
        
        int totalProducido = 0;
        int totalConsumido = 0;
        
        for (Productor productor : productores) {
            totalProducido += productor.getElementosProducidos();
        }
        
        for (Consumidor consumidor : consumidores) {
            totalConsumido += consumidor.getElementosConsumidos();
        }
        
        System.out.println("Total producido: " + totalProducido);
        System.out.println("Total consumido: " + totalConsumido);
        System.out.println("Elementos en cola: " + cola.tamaño());
        System.out.println("Diferencia: " + (totalProducido - totalConsumido));
        System.out.println("ANÁLISIS: Productores lentos → Consumidores esperan elementos");
    }
}
