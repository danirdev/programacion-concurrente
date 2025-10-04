package tp4.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase principal que simula el funcionamiento de un zoológico con control de acceso.
 * Coordina el flujo de visitantes a través de un pasillo único que actúa como entrada y salida.
 */
public class ZoologicoSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("           SIMULACIÓN DE ZOOLÓGICO CON CONTROL DE ACCESO");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando zoológico...");
        System.out.println();
        System.out.println("CONFIGURACIÓN:");
        System.out.println("• Pasillo único: 50ms por uso (entrada o salida)");
        System.out.println("• Llegada Visitantes: 100-200ms entre llegadas");
        System.out.println("• Tiempo en Zoo: 400-700ms por visitante");
        System.out.println("• Duración: 60 segundos de simulación");
        System.out.println("• Control: Solo una persona puede usar el pasillo a la vez");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Crear el pasillo compartido (recurso crítico)
        Pasillo pasillo = new Pasillo();
        
        // Crear el generador de visitantes
        GeneradorVisitantes generadorVisitantes = new GeneradorVisitantes(pasillo);
        
        // Crear hilo para mostrar estadísticas periódicas
        Thread monitorEstadisticas = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(20000); // Cada 20 segundos
                    pasillo.mostrarEstado();
                    generadorVisitantes.mostrarResumenVisitantes();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        monitorEstadisticas.setName("Monitor-Estadisticas");
        
        try {
            // Iniciar todos los hilos
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] === ZOOLÓGICO ABIERTO ===");
            
            generadorVisitantes.start();
            monitorEstadisticas.start();
            
            // Ejecutar simulación por 60 segundos
            Thread.sleep(60000); // 60 segundos
            
            // Detener generación de nuevos visitantes
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println();
            System.out.println("[" + tiempo + "] === CERRANDO ZOOLÓGICO (No más visitantes nuevos) ===");
            
            generadorVisitantes.interrupt();
            monitorEstadisticas.interrupt();
            
            // Esperar a que terminen los hilos principales
            monitorEstadisticas.join(1000);
            
            // Dar tiempo a que los visitantes actuales terminen su visita
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] Esperando que los visitantes actuales terminen...");
            Thread.sleep(5000); // 5 segundos adicionales
            
            // Esperar a que terminen los visitantes
            generadorVisitantes.esperarVisitantes();
            generadorVisitantes.join(3000);
            
            // Mostrar estadísticas finales
            mostrarEstadisticasFinales(pasillo, generadorVisitantes);
            
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(Pasillo pasillo, 
                                                  GeneradorVisitantes generadorVisitantes) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("                    ESTADÍSTICAS FINALES DEL ZOOLÓGICO");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del pasillo
        System.out.println("🚪 ESTADÍSTICAS DEL PASILLO:");
        System.out.println("   Entradas realizadas:    " + pasillo.getTotalEntradas());
        System.out.println("   Salidas realizadas:     " + pasillo.getTotalSalidas());
        System.out.println("   Visitantes en zoo:      " + pasillo.getVisitantesEnZoo());
        System.out.println("   Diferencia (E-S):       " + (pasillo.getTotalEntradas() - pasillo.getTotalSalidas()));
        
        double[] estadisticasUso = pasillo.getEstadisticasUso();
        System.out.println("   Total usos del pasillo: " + (int)estadisticasUso[0]);
        System.out.println("   % Usos para entrada:    " + String.format("%.1f%%", estadisticasUso[1]));
        System.out.println("   % Usos para salida:     " + String.format("%.1f%%", estadisticasUso[2]));
        System.out.println();
        
        // Estadísticas de visitantes
        int[] estadisticasVisitantes = generadorVisitantes.getEstadisticasVisitantes();
        int totalGenerados = generadorVisitantes.getVisitantesGenerados();
        int completaron = estadisticasVisitantes[0];
        int enZoo = estadisticasVisitantes[1];
        int esperando = estadisticasVisitantes[2];
        int noIniciados = estadisticasVisitantes[3];
        
        System.out.println("👥 ESTADÍSTICAS DE VISITANTES:");
        System.out.println("   Visitantes generados:   " + totalGenerados);
        System.out.println("   Completaron visita:     " + completaron);
        System.out.println("   Aún en el zoo:          " + enZoo);
        System.out.println("   Esperando entrada:      " + esperando);
        System.out.println("   No iniciados:           " + noIniciados);
        System.out.println("   Tasa de finalización:   " + 
                          String.format("%.1f%%", (double)completaron / totalGenerados * 100));
        System.out.println();
        
        // Estadísticas de tiempos
        double[] estadisticasTiempos = generadorVisitantes.getEstadisticasTiempos();
        if (estadisticasTiempos[0] > 0) {
            System.out.println("⏱️  ESTADÍSTICAS DE TIEMPOS:");
            System.out.println("   Tiempo promedio total:   " + String.format("%.0f", estadisticasTiempos[0]) + "ms");
            System.out.println("   Tiempo promedio espera:  " + String.format("%.0f", estadisticasTiempos[1]) + "ms");
            System.out.println("   Tiempo máximo total:     " + String.format("%.0f", estadisticasTiempos[2]) + "ms");
            System.out.println("   Tiempo máximo espera:    " + String.format("%.0f", estadisticasTiempos[3]) + "ms");
            System.out.println();
        }
        
        // Análisis de rendimiento
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double usosDelPasilloPorMinuto = estadisticasUso[0] / 1.0; // 60 segundos = 1 minuto
        double visitantesPorMinuto = totalGenerados / 1.0;
        double capacidadTeorica = 60.0 / 0.05; // 1200 usos por minuto (50ms por uso)
        
        System.out.println("   Usos del pasillo/min:    " + String.format("%.1f", usosDelPasilloPorMinuto));
        System.out.println("   Visitantes generados/min:" + String.format("%.1f", visitantesPorMinuto));
        System.out.println("   Capacidad teórica:       " + String.format("%.0f", capacidadTeorica) + " usos/min");
        System.out.println("   Utilización del pasillo: " + 
                          String.format("%.1f%%", (usosDelPasilloPorMinuto / capacidadTeorica) * 100));
        System.out.println();
        
        // Identificar cuellos de botella y patrones
        System.out.println("🔍 ANÁLISIS DE CUELLOS DE BOTELLA:");
        
        if (esperando > totalGenerados * 0.2) {
            System.out.println("   ⚠️  CUELLO DE BOTELLA: Muchos visitantes esperando entrada (" + esperando + ")");
            System.out.println("       Recomendación: Ampliar el pasillo o crear entradas adicionales");
        }
        
        if (enZoo > 15) {
            System.out.println("   📈 Zoo con alta ocupación (" + enZoo + " visitantes)");
            System.out.println("       El pasillo está limitando las salidas");
        }
        
        if (pasillo.getTotalEntradas() > pasillo.getTotalSalidas() * 2) {
            System.out.println("   ⚠️  Desequilibrio: Muchas más entradas que salidas");
            System.out.println("       Los visitantes permanecen mucho tiempo en el zoo");
        }
        
        double utilizacion = (usosDelPasilloPorMinuto / capacidadTeorica) * 100;
        if (utilizacion > 80) {
            System.out.println("   🔥 Alta utilización del pasillo (" + String.format("%.1f%%", utilizacion) + ")");
            System.out.println("       El sistema está cerca de su capacidad máxima");
        } else if (utilizacion < 30) {
            System.out.println("   ✅ Baja utilización del pasillo (" + String.format("%.1f%%", utilizacion) + ")");
            System.out.println("       El sistema puede manejar más visitantes");
        }
        
        System.out.println();
        
        // Conclusiones
        System.out.println("📋 CONCLUSIONES:");
        
        if (completaron == totalGenerados) {
            System.out.println("   • ✅ Todos los visitantes completaron exitosamente su visita");
        } else {
            System.out.println("   • ⚠️  " + (totalGenerados - completaron) + " visitantes no completaron su visita");
        }
        
        if (visitantesPorMinuto * 2 > capacidadTeorica / 60) { // Cada visitante usa el pasillo 2 veces
            System.out.println("   • La tasa de llegada está cerca del límite del sistema");
            System.out.println("   • Se recomienda optimizar el flujo o aumentar la capacidad");
        }
        
        if (estadisticasTiempos[1] > 1000) { // Más de 1 segundo de espera promedio
            System.out.println("   • Tiempos de espera elevados para entrar al zoo");
            System.out.println("   • Los visitantes experimentan demoras significativas");
        }
        
        if (pasillo.getVisitantesEnZoo() == 0) {
            System.out.println("   • ✅ Zoo completamente vacío al final de la simulación");
        } else {
            System.out.println("   • " + pasillo.getVisitantesEnZoo() + " visitantes aún en el zoo");
        }
        
        System.out.println();
        System.out.println("🎯 RECOMENDACIONES:");
        System.out.println("   • Para reducir esperas: Implementar múltiples pasillos");
        System.out.println("   • Para mejorar flujo: Separar entrada y salida");
        System.out.println("   • Para optimizar: Implementar sistema de reservas");
        System.out.println("   • Para monitoreo: Agregar sensores de ocupación en tiempo real");
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("Simulación finalizada. ¡Gracias por usar el simulador de zoológico!");
        System.out.println("=".repeat(80));
    }
}
