package tp4.actividad1;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del problema Productor-Consumidor con cola de tamaño limitado.
 * Caso b) del ejercicio: Cola de tamaño 5 con productores más rápidos que consumidores.
 */
public class ProductorConsumidorLimitado {
    
    public static void main(String[] args) {
        System.out.println("=== PROBLEMA PRODUCTOR-CONSUMIDOR: COLA LIMITADA ===");
        System.out.println("Capacidad de cola: 5 elementos");
        System.out.println("Productores: 10 (400-800ms por elemento)");
        System.out.println("Consumidores: 10 (1000-1500ms por elemento)");
        System.out.println("Los productores son más rápidos que los consumidores");
        System.out.println("======================================================\n");
        
        // Crear cola con capacidad limitada de 5 elementos
        Cola cola = new Cola(5);
        
        // Listas para almacenar los hilos
        List<Productor> productores = new ArrayList<>();
        List<Consumidor> consumidores = new ArrayList<>();
        
        // Crear 10 productores (más rápidos: 400-800ms)
        for (int i = 1; i <= 10; i++) {
            Productor productor = new Productor(cola, i, 400, 800);
            productores.add(productor);
        }
        
        // Crear 10 consumidores (más lentos: 1000-1500ms)
        for (int i = 1; i <= 10; i++) {
            Consumidor consumidor = new Consumidor(cola, i, 1000, 1500);
            consumidores.add(consumidor);
        }
        
        // Iniciar todos los productores
        System.out.println("Iniciando productores...");
        for (Productor productor : productores) {
            productor.start();
        }
        
        // Iniciar todos los consumidores
        System.out.println("Iniciando consumidores...");
        for (Consumidor consumidor : consumidores) {
            consumidor.start();
        }
        
        // Ejecutar por 30 segundos
        try {
            Thread.sleep(30000); // 30 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrumpir todos los hilos
        System.out.println("\n=== FINALIZANDO SIMULACIÓN ===");
        
        for (Productor productor : productores) {
            productor.interrupt();
        }
        
        for (Consumidor consumidor : consumidores) {
            consumidor.interrupt();
        }
        
        // Esperar a que todos los hilos terminen
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
        
        // Mostrar estadísticas finales
        mostrarEstadisticas(productores, consumidores, cola);
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticas(List<Productor> productores, 
                                          List<Consumidor> consumidores, 
                                          Cola cola) {
        System.out.println("\n=== ESTADÍSTICAS FINALES ===");
        
        int totalProducido = 0;
        int totalConsumido = 0;
        
        System.out.println("\nProductores:");
        for (Productor productor : productores) {
            int producidos = productor.getElementosProducidos();
            totalProducido += producidos;
            System.out.println("  Productor " + productor.getName() + ": " + producidos + " elementos");
        }
        
        System.out.println("\nConsumidores:");
        for (Consumidor consumidor : consumidores) {
            int consumidos = consumidor.getElementosConsumidos();
            totalConsumido += consumidos;
            System.out.println("  Consumidor " + consumidor.getName() + ": " + consumidos + " elementos");
        }
        
        System.out.println("\n--- RESUMEN ---");
        System.out.println("Total producido: " + totalProducido);
        System.out.println("Total consumido: " + totalConsumido);
        System.out.println("Elementos en cola: " + cola.tamaño());
        System.out.println("Capacidad máxima de cola: 5");
        System.out.println("Diferencia (producido - consumido): " + (totalProducido - totalConsumido));
        
        if (totalProducido < totalConsumido) {
            System.out.println("RESULTADO: Los consumidores esperaron elementos");
        } else if (totalProducido > totalConsumido) {
            System.out.println("RESULTADO: Los productores esperaron espacio en la cola");
        } else {
            System.out.println("RESULTADO: Producción y consumo equilibrados");
        }
        
        System.out.println("\nNOTA: Con cola limitada, los productores rápidos deben esperar");
        System.out.println("      cuando la cola se llena, sincronizando la velocidad.");
    }
}
