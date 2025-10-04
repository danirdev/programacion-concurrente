package tp6.actividad3.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal para la simulación de Estación de Peaje - Implementación A.
 * Simula 50 automóviles siendo atendidos en 3 cabinas (una cerrada inicialmente).
 * NO individualiza qué cabina atiende a cada cliente.
 */
public class PeajeSimpleSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("      ESTACIÓN DE PEAJE - IMPLEMENTACIÓN A (SIN INDIVIDUALIZAR)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación de estación de peaje");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: A - Sin individualizar cabinas");
        System.out.println("• Total de automóviles: 50");
        System.out.println("• Cabinas iniciales: 2 (Cabina 3 cerrada)");
        System.out.println("• Tiempo de atención: 1-3 segundos por cliente");
        System.out.println("• Cabina 3: Disponible después de 15 segundos");
        System.out.println("• Información mostrada: Cliente + tiempo, SIN cabina específica");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Registrar tiempo de inicio
        long tiempoInicioTotal = System.currentTimeMillis();
        
        // Crear la estación de peaje
        EstacionPeajeSimple estacionPeaje = new EstacionPeajeSimple();
        System.out.println("Estado inicial: " + estacionPeaje.getInfoResumida());
        System.out.println();
        
        // Crear lista de automóviles (50 clientes)
        List<AutomovilPeaje> automoviles = new ArrayList<>();
        
        // Crear 50 automóviles
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🔧 Creando cola de 50 automóviles...");
        
        for (int i = 1; i <= 50; i++) {
            AutomovilPeaje automovil = new AutomovilPeaje(i, estacionPeaje);
            automoviles.add(automovil);
        }
        
        System.out.println("   ✅ 50 automóviles creados y listos para llegar al peaje");
        System.out.println();
        
        // Crear hilo monitor para mostrar estadísticas periódicas
        Thread monitor = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(10000); // Cada 10 segundos
                    estacionPeaje.mostrarEstado();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor-Peaje-Simple");
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🚀 INICIANDO SIMULACIÓN DE PEAJE");
        System.out.println("=".repeat(50));
        System.out.println("📊 Los automóviles llegarán gradualmente a la estación");
        System.out.println("⏱️ Observar el impacto cuando se abra la Cabina 3 (15s)");
        System.out.println("🔍 Notar que NO se identifica qué cabina atiende a cada cliente");
        System.out.println("=".repeat(50));
        System.out.println();
        
        // Iniciar monitor de estadísticas
        monitor.start();
        
        // Iniciar todos los automóviles (llegarán con intervalos aleatorios)
        for (AutomovilPeaje automovil : automoviles) {
            automovil.start();
        }
        
        System.out.println("⏳ Esperando que todos los automóviles completen el proceso...");
        System.out.println();
        
        // Esperar a que todos los automóviles terminen
        for (AutomovilPeaje automovil : automoviles) {
            try {
                automovil.join(); // Esperar a que termine cada automóvil
            } catch (InterruptedException e) {
                System.err.println("❌ Interrupción durante la espera del automóvil: " + automovil.getName());
                Thread.currentThread().interrupt();
            }
        }
        
        // Detener el monitor
        monitor.interrupt();
        try {
            monitor.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Calcular tiempo total
        long tiempoFinTotal = System.currentTimeMillis();
        long tiempoTotal = tiempoFinTotal - tiempoInicioTotal;
        
        // Mostrar estadísticas finales
        mostrarEstadisticasFinales(estacionPeaje, automoviles, tiempoTotal);
        
        // Demostrar características de la Implementación A
        demostrarCaracteristicasImplementacionA(estacionPeaje, automoviles);
    }
    
    /**
     * Muestra las estadísticas finales de la simulación
     */
    private static void mostrarEstadisticasFinales(EstacionPeajeSimple estacionPeaje, 
                                                  List<AutomovilPeaje> automoviles, 
                                                  long tiempoTotal) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         ESTADÍSTICAS FINALES - IMPLEMENTACIÓN A (SIN INDIVIDUALIZAR)");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Simulación completada");
        System.out.println();
        
        // Estadísticas del peaje
        int[] statsEstacion = estacionPeaje.getEstadisticas();
        double[] rendimiento = estacionPeaje.calcularRendimiento();
        
        System.out.println("🏢 ESTADÍSTICAS DE LA ESTACIÓN:");
        System.out.println("   Clientes atendidos: " + statsEstacion[0] + "/50");
        System.out.println("   Clientes esperando al final: " + statsEstacion[1]);
        System.out.println("   Cabinas disponibles al final: " + statsEstacion[2]);
        System.out.println("   Cabina 3 estado final: " + (statsEstacion[3] == 1 ? "✅ DISPONIBLE" : "❌ CERRADA"));
        System.out.println("   Throughput: " + String.format("%.1f", rendimiento[0]) + " clientes/minuto");
        System.out.println("   Tiempo promedio por cliente: " + String.format("%.0f", rendimiento[1]) + "ms");
        System.out.println("   Eficiencia del sistema: " + String.format("%.1f%%", rendimiento[2]));
        System.out.println();
        
        // Estadísticas de automóviles
        int completados = 0;
        int esperaron = 0;
        long tiempoEsperaTotal = 0;
        long tiempoEsperaMaximo = 0;
        long tiempoEsperaMinimo = Long.MAX_VALUE;
        long tiempoAtencionTotal = 0;
        int clientesAntesCabina3 = 0;
        
        System.out.println("🚗 ESTADÍSTICAS DE AUTOMÓVILES:");
        for (AutomovilPeaje automovil : automoviles) {
            if (automovil.isCompletoAtencion()) {
                completados++;
                
                long tiempoEspera = automovil.getTiempoEspera();
                long tiempoAtencion = automovil.getTiempoAtencion();
                
                if (automovil.tuvoQueEsperar()) {
                    esperaron++;
                    tiempoEsperaTotal += tiempoEspera;
                    tiempoEsperaMaximo = Math.max(tiempoEsperaMaximo, tiempoEspera);
                    tiempoEsperaMinimo = Math.min(tiempoEsperaMinimo, tiempoEspera);
                }
                
                if (tiempoAtencion > 0) {
                    tiempoAtencionTotal += tiempoAtencion;
                }
                
                if (automovil.llegoAntesCabina3()) {
                    clientesAntesCabina3++;
                }
            }
        }
        
        System.out.println("   Total automóviles: " + automoviles.size());
        System.out.println("   Completaron proceso: " + completados);
        System.out.println("   Automóviles que esperaron: " + esperaron);
        System.out.println("   Tasa de finalización: " + 
                          String.format("%.1f%%", (completados * 100.0 / automoviles.size())));
        
        if (esperaron > 0) {
            double tiempoPromedioEspera = tiempoEsperaTotal / (double) esperaron;
            System.out.println("   Tiempo promedio de espera: " + String.format("%.0f", tiempoPromedioEspera) + "ms");
            System.out.println("   Tiempo máximo de espera: " + tiempoEsperaMaximo + "ms");
            System.out.println("   Tiempo mínimo de espera: " + (tiempoEsperaMinimo == Long.MAX_VALUE ? 0 : tiempoEsperaMinimo) + "ms");
        }
        
        if (completados > 0) {
            double tiempoPromedioAtencion = tiempoAtencionTotal / (double) completados;
            System.out.println("   Tiempo promedio de atención: " + String.format("%.0f", tiempoPromedioAtencion) + "ms");
        }
        
        System.out.println("   Clientes antes de Cabina 3: " + clientesAntesCabina3);
        System.out.println("   Clientes después de Cabina 3: " + (completados - clientesAntesCabina3));
        System.out.println();
        
        // Análisis temporal
        System.out.println("⏱️ ANÁLISIS TEMPORAL:");
        double tiempoTotalSegundos = tiempoTotal / 1000.0;
        System.out.println("   Tiempo total de simulación: " + String.format("%.1f", tiempoTotalSegundos) + "s");
        
        // Estimaciones teóricas
        double tiempoTeoricoSolo2Cabinas = (50 * 2.0) / 2; // 50 segundos con 2 cabinas
        double tiempoTeoricoSolo3Cabinas = (50 * 2.0) / 3; // 33.3 segundos con 3 cabinas
        
        System.out.println("   Tiempo teórico (solo 2 cabinas): " + String.format("%.1f", tiempoTeoricoSolo2Cabinas) + "s");
        System.out.println("   Tiempo teórico (solo 3 cabinas): " + String.format("%.1f", tiempoTeoricoSolo3Cabinas) + "s");
        
        if (tiempoTotalSegundos <= tiempoTeoricoSolo3Cabinas * 1.2) {
            System.out.println("   Rendimiento: ✅ EXCELENTE (cerca del óptimo)");
        } else if (tiempoTotalSegundos <= tiempoTeoricoSolo2Cabinas * 1.2) {
            System.out.println("   Rendimiento: ✅ BUENO (eficiencia aceptable)");
        } else {
            System.out.println("   Rendimiento: ⚠️ MEJORABLE (por debajo del esperado)");
        }
        System.out.println();
        
        // Análisis del impacto de la Cabina 3
        System.out.println("📊 IMPACTO DE LA CABINA 3:");
        if (estacionPeaje.isCabina3Disponible()) {
            System.out.println("   ✅ Cabina 3 se abrió correctamente después de 15s");
            System.out.println("   📈 Mejora teórica de throughput: +50% (2→3 cabinas)");
            
            double porcentajeAntesCabina3 = (clientesAntesCabina3 * 100.0) / completados;
            System.out.println("   📊 Clientes atendidos antes de Cabina 3: " + 
                             String.format("%.1f%%", porcentajeAntesCabina3));
            
            if (porcentajeAntesCabina3 < 40) {
                System.out.println("   🚀 Impacto significativo: La mayoría se benefició de 3 cabinas");
            } else {
                System.out.println("   ⚠️ Impacto limitado: Muchos clientes ya fueron atendidos");
            }
        } else {
            System.out.println("   ❌ Cabina 3 no se abrió durante la simulación");
        }
        System.out.println();
        
        // Experiencia del cliente
        System.out.println("😊 EXPERIENCIA DEL CLIENTE:");
        int excelente = 0, buena = 0, regular = 0, mala = 0;
        
        for (AutomovilPeaje automovil : automoviles) {
            if (automovil.isCompletoAtencion()) {
                String experiencia = automovil.getExperienciaCliente();
                if (experiencia.contains("Excelente")) excelente++;
                else if (experiencia.contains("Buena")) buena++;
                else if (experiencia.contains("Regular")) regular++;
                else if (experiencia.contains("Mala")) mala++;
            }
        }
        
        System.out.println("   ✅ Excelente (sin espera): " + excelente + " clientes");
        System.out.println("   ✅ Buena (espera corta): " + buena + " clientes");
        System.out.println("   ⚠️ Regular (espera media): " + regular + " clientes");
        System.out.println("   ❌ Mala (espera larga): " + mala + " clientes");
        
        double satisfaccion = ((excelente + buena) * 100.0) / completados;
        System.out.println("   📊 Índice de satisfacción: " + String.format("%.1f%%", satisfaccion));
    }
    
    /**
     * Demuestra las características específicas de la Implementación A
     */
    private static void demostrarCaracteristicasImplementacionA(EstacionPeajeSimple estacionPeaje, 
                                                               List<AutomovilPeaje> automoviles) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         CARACTERÍSTICAS DE LA IMPLEMENTACIÓN A");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🏗️ ARQUITECTURA SIN INDIVIDUALIZACIÓN:");
        System.out.println("   ✅ Semáforo simple para control de cabinas disponibles");
        System.out.println("   ✅ No se identifica qué cabina específica atiende");
        System.out.println("   ✅ Información básica: cliente + tiempo de atención");
        System.out.println("   ✅ Implementación minimalista y directa");
        
        System.out.println("\n👍 VENTAJAS DEMOSTRADAS:");
        System.out.println("   ✅ Simplicidad máxima - Código fácil de entender");
        System.out.println("   ✅ Menos overhead - No tracking de cabinas individuales");
        System.out.println("   ✅ Implementación rápida - Menos componentes");
        System.out.println("   ✅ Funcionalidad básica completa - Cumple objetivo principal");
        
        System.out.println("\n👎 LIMITACIONES EVIDENTES:");
        System.out.println("   ❌ Sin identificación de cabina - No se sabe cuál atiende");
        System.out.println("   ❌ Análisis limitado - No se puede optimizar por cabina");
        System.out.println("   ❌ Debugging difícil - Información insuficiente para troubleshooting");
        System.out.println("   ❌ Sin estadísticas granulares - No se puede medir rendimiento individual");
        
        System.out.println("\n🔍 INFORMACIÓN DISPONIBLE:");
        System.out.println("   • Número de cliente atendido: ✅");
        System.out.println("   • Tiempo de inicio de atención: ✅");
        System.out.println("   • Tiempo de fin de atención: ✅");
        System.out.println("   • Cabina específica que atiende: ❌ NO DISPONIBLE");
        System.out.println("   • Rendimiento por cabina: ❌ NO DISPONIBLE");
        System.out.println("   • Distribución de carga: ❌ NO DISPONIBLE");
        
        System.out.println("\n📊 MÉTRICAS DISPONIBLES:");
        System.out.println("   ✅ Total de clientes atendidos");
        System.out.println("   ✅ Tiempo promedio de atención");
        System.out.println("   ✅ Throughput general del sistema");
        System.out.println("   ✅ Tiempo total de operación");
        System.out.println("   ❌ Utilización individual por cabina");
        System.out.println("   ❌ Balanceamiento de carga");
        System.out.println("   ❌ Identificación de cuellos de botella específicos");
        
        System.out.println("\n🎯 CASOS DE USO APROPIADOS:");
        System.out.println("   ✅ Prototipos y demos rápidas");
        System.out.println("   ✅ Sistemas simples sin necesidad de análisis detallado");
        System.out.println("   ✅ Aplicaciones donde solo importa el throughput general");
        System.out.println("   ✅ Casos donde la simplicidad es más importante que la información");
        
        System.out.println("\n❌ CASOS DONDE NO ES SUFICIENTE:");
        System.out.println("   ❌ Sistemas de producción que requieren monitoreo detallado");
        System.out.println("   ❌ Aplicaciones que necesitan optimización por recurso");
        System.out.println("   ❌ Sistemas donde se debe identificar cuellos de botella");
        System.out.println("   ❌ Casos que requieren balanceamiento de carga");
        
        System.out.println("\n💡 PREGUNTA CLAVE DEL ENUNCIADO:");
        System.out.println("   ❓ '¿Será posible individualizar cada cabina?'");
        System.out.println("   ✅ RESPUESTA: SÍ, es posible y necesario para análisis detallado");
        System.out.println("   🔧 SOLUCIÓN: Implementación B con tracking de cabinas individuales");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🎯 CONCLUSIÓN: La Implementación A es funcional pero limitada.");
        System.out.println("   Para casos reales se necesita la Implementación B con");
        System.out.println("   individualización de cabinas para análisis y optimización.");
        System.out.println("=".repeat(80));
    }
}
