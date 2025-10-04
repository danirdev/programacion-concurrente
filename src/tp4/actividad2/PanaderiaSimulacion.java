package tp4.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase principal que simula el funcionamiento de una panadería 24 horas.
 * Coordina la producción de bizcochos y facturas, y la llegada de clientes.
 */
public class PanaderiaSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("           SIMULACIÓN DE PANADERÍA 24 HORAS");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando panadería...");
        System.out.println();
        System.out.println("CONFIGURACIÓN:");
        System.out.println("• Horno Bizcochos: 400-600ms por unidad");
        System.out.println("• Horno Facturas: 1000-1300ms por unidad");
        System.out.println("• Llegada Clientes: 800-1500ms entre llegadas");
        System.out.println("• Compra Cliente: 200-400ms (1 Bizcocho + 1 Factura)");
        System.out.println("• Duración: 60 segundos de simulación");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Crear el mostrador compartido
        Mostrador mostrador = new Mostrador();
        
        // Crear los hornos (productores)
        ProductorBizcochos hornoBizcochos = new ProductorBizcochos(mostrador);
        ProductorFacturas hornoFacturas = new ProductorFacturas(mostrador);
        
        // Crear el generador de clientes
        GeneradorClientes generadorClientes = new GeneradorClientes(mostrador);
        
        // Crear hilo para mostrar estadísticas periódicas
        Thread monitorEstadisticas = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(15000); // Cada 15 segundos
                    mostrador.mostrarEstado();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        monitorEstadisticas.setName("Monitor-Estadisticas");
        
        try {
            // Iniciar todos los hilos
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] === PANADERÍA EN FUNCIONAMIENTO ===");
            
            hornoBizcochos.start();
            hornoFacturas.start();
            generadorClientes.start();
            monitorEstadisticas.start();
            
            // Ejecutar simulación por 60 segundos
            Thread.sleep(60000); // 60 segundos
            
            // Detener todos los hilos
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println();
            System.out.println("[" + tiempo + "] === CERRANDO PANADERÍA ===");
            
            hornoBizcochos.interrupt();
            hornoFacturas.interrupt();
            generadorClientes.interrupt();
            monitorEstadisticas.interrupt();
            
            // Esperar a que terminen los hilos principales
            hornoBizcochos.join(2000);
            hornoFacturas.join(2000);
            monitorEstadisticas.join(1000);
            
            // Esperar a que terminen los clientes
            generadorClientes.esperarClientes();
            generadorClientes.join(3000);
            
            // Mostrar estadísticas finales
            mostrarEstadisticasFinales(mostrador, hornoBizcochos, hornoFacturas, generadorClientes);
            
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(Mostrador mostrador, 
                                                  ProductorBizcochos hornoBizcochos,
                                                  ProductorFacturas hornoFacturas,
                                                  GeneradorClientes generadorClientes) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("                    ESTADÍSTICAS FINALES");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas de producción
        System.out.println("📊 PRODUCCIÓN:");
        System.out.println("   Bizcochos producidos: " + hornoBizcochos.getBizcochosProcidos());
        System.out.println("   Facturas producidas:  " + hornoFacturas.getFacturasProducidas());
        System.out.println("   Ratio B/F:            " + 
                          String.format("%.2f", (double)hornoBizcochos.getBizcochosProcidos() / 
                                               hornoFacturas.getFacturasProducidas()));
        System.out.println();
        
        // Estadísticas de inventario
        System.out.println("📦 INVENTARIO FINAL:");
        System.out.println("   Bizcochos en mostrador: " + mostrador.getBizcochos());
        System.out.println("   Facturas en mostrador:  " + mostrador.getFacturas());
        System.out.println("   Total en mostrador:     " + (mostrador.getBizcochos() + mostrador.getFacturas()));
        System.out.println();
        
        // Estadísticas de clientes
        int[] estadisticasClientes = generadorClientes.getEstadisticasClientes();
        int clientesAtendidos = estadisticasClientes[0];
        int clientesNoAtendidos = estadisticasClientes[1];
        int totalClientes = generadorClientes.getClientesGenerados();
        
        System.out.println("👥 CLIENTES:");
        System.out.println("   Clientes generados:    " + totalClientes);
        System.out.println("   Clientes atendidos:    " + clientesAtendidos);
        System.out.println("   Clientes no atendidos: " + clientesNoAtendidos);
        System.out.println("   Tasa de atención:      " + 
                          String.format("%.1f%%", (double)clientesAtendidos / totalClientes * 100));
        System.out.println("   Ventas totales:        " + mostrador.getTotalVentas());
        System.out.println();
        
        // Análisis de rendimiento
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double bizcochosPorMinuto = hornoBizcochos.getBizcochosProcidos() / 1.0; // 60 segundos = 1 minuto
        double facturasPorMinuto = hornoFacturas.getFacturasProducidas() / 1.0;
        double clientesPorMinuto = totalClientes / 1.0;
        
        System.out.println("   Bizcochos/minuto:      " + String.format("%.1f", bizcochosPorMinuto));
        System.out.println("   Facturas/minuto:       " + String.format("%.1f", facturasPorMinuto));
        System.out.println("   Clientes/minuto:       " + String.format("%.1f", clientesPorMinuto));
        System.out.println("   Capacidad teórica:     " + String.format("%.1f ventas/min", 
                                                                        Math.min(bizcochosPorMinuto, facturasPorMinuto)));
        System.out.println();
        
        // Identificar cuellos de botella
        System.out.println("🔍 ANÁLISIS DE CUELLOS DE BOTELLA:");
        if (hornoBizcochos.getBizcochosProcidos() > hornoFacturas.getFacturasProducidas() * 1.5) {
            System.out.println("   ⚠️  CUELLO DE BOTELLA: Producción de Facturas es muy lenta");
            System.out.println("       Recomendación: Agregar más hornos de facturas");
        } else if (hornoFacturas.getFacturasProducidas() > hornoBizcochos.getBizcochosProcidos() * 1.5) {
            System.out.println("   ⚠️  CUELLO DE BOTELLA: Producción de Bizcochos es muy lenta");
            System.out.println("       Recomendación: Agregar más hornos de bizcochos");
        } else {
            System.out.println("   ✅ Producción balanceada entre ambos productos");
        }
        
        if (mostrador.getBizcochos() > 10) {
            System.out.println("   📈 Exceso de bizcochos en inventario");
        }
        if (mostrador.getFacturas() > 10) {
            System.out.println("   📈 Exceso de facturas en inventario");
        }
        if (clientesNoAtendidos > totalClientes * 0.1) {
            System.out.println("   ⚠️  Alta tasa de clientes no atendidos (" + 
                              String.format("%.1f%%", (double)clientesNoAtendidos / totalClientes * 100) + ")");
        }
        
        System.out.println();
        
        // Conclusiones
        System.out.println("📋 CONCLUSIONES:");
        if (facturasPorMinuto < bizcochosPorMinuto * 0.7) {
            System.out.println("   • Las facturas son el factor limitante del sistema");
            System.out.println("   • Los clientes esperan principalmente por facturas");
        }
        if (clientesPorMinuto > Math.min(bizcochosPorMinuto, facturasPorMinuto)) {
            System.out.println("   • La demanda supera la capacidad de producción");
            System.out.println("   • Se recomienda aumentar la capacidad productiva");
        }
        if (mostrador.getTotalVentas() == totalClientes) {
            System.out.println("   • ✅ Todos los clientes fueron atendidos exitosamente");
        }
        
        System.out.println("=".repeat(80));
        System.out.println("Simulación finalizada. ¡Gracias por usar el simulador de panadería!");
        System.out.println("=".repeat(80));
    }
}
