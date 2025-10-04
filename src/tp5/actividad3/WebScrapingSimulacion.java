package tp5.actividad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que simula web scraping concurrente de noticias usando interfaz Runnable.
 * Crea 10 hilos para procesar simultáneamente 10 noticias de El Tribuno de Jujuy,
 * extrae el contenido específico y mide el tiempo de ejecución.
 */
public class WebScrapingSimulacion {
    
    public static void main(String[] args) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println("=".repeat(80));
        System.out.println("        WEB SCRAPING CONCURRENTE CON INTERFAZ RUNNABLE");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Iniciando simulación de web scraping...");
        System.out.println();
        System.out.println("CONFIGURACIÓN DEL SISTEMA:");
        System.out.println("• Implementación: Interfaz Runnable");
        System.out.println("• Fuente: El Tribuno de Jujuy - Sección Policiales");
        System.out.println("• Noticias a procesar: 10");
        System.out.println("• Hilos concurrentes: 10 (uno por noticia)");
        System.out.println("• Objetivo: Extraer contenido de <div amp-access=\"noticia\">");
        System.out.println("• Medición: Tiempo total de ejecución");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Registrar tiempo de inicio
        long tiempoInicioTotal = System.currentTimeMillis();
        
        // Obtener array de enlaces de noticias
        String[] enlaces = GestorEnlaces.obtenerEnlaces();
        
        // Mostrar información de los enlaces
        GestorEnlaces.mostrarInformacionEnlaces();
        
        // Validar enlaces antes de procesar
        if (!GestorEnlaces.validarEnlaces()) {
            System.err.println("❌ Error: Se encontraron enlaces inválidos. Abortando ejecución.");
            return;
        }
        
        System.out.println("✅ Validación de enlaces completada exitosamente");
        System.out.println();
        
        // Crear listas para gestionar las tareas y hilos
        List<ScrapingTask> tareas = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        
        // Crear 10 tareas Runnable, una para cada noticia
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🔧 Creando tareas de scraping...");
        
        for (int i = 0; i < enlaces.length; i++) {
            ScrapingTask tarea = new ScrapingTask(enlaces, i);
            tareas.add(tarea);
            
            // Crear hilo para cada tarea usando Runnable
            Thread hilo = new Thread(tarea, "Scraper-" + String.format("%02d", i + 1));
            hilos.add(hilo);
            
            System.out.println("   ✅ Tarea " + (i + 1) + "/10 creada para: " + 
                             enlaces[i].substring(0, Math.min(enlaces[i].length(), 50)) + "...");
        }
        
        System.out.println();
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] 🚀 INICIANDO SCRAPING CONCURRENTE");
        System.out.println("=".repeat(50));
        
        // Registrar tiempo de inicio del scraping
        long tiempoInicioScraping = System.currentTimeMillis();
        
        // Iniciar todos los hilos simultáneamente
        for (int i = 0; i < hilos.size(); i++) {
            Thread hilo = hilos.get(i);
            hilo.start();
            System.out.println("🔄 " + hilo.getName() + " iniciado para noticia " + (i + 1));
            
            // Pequeño delay para evitar sobrecarga inicial
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println();
        System.out.println("⏳ Esperando que todos los hilos completen el scraping...");
        System.out.println("📰 Los resultados aparecerán conforme cada hilo termine:");
        System.out.println();
        
        // Esperar a que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join(); // Esperar a que termine cada hilo
            } catch (InterruptedException e) {
                System.err.println("❌ Interrupción durante la espera del hilo: " + hilo.getName());
                Thread.currentThread().interrupt();
            }
        }
        
        // Calcular tiempos de ejecución
        long tiempoFinScraping = System.currentTimeMillis();
        long tiempoFinTotal = System.currentTimeMillis();
        
        long tiempoScraping = tiempoFinScraping - tiempoInicioScraping;
        long tiempoTotal = tiempoFinTotal - tiempoInicioTotal;
        
        // Mostrar estadísticas finales
        mostrarEstadisticasFinales(tareas, tiempoScraping, tiempoTotal);
        
        // Demostrar ventajas de Runnable para web scraping
        demostrarVentajasRunnable(tareas, tiempoScraping);
    }
    
    /**
     * Muestra las estadísticas finales del web scraping
     */
    private static void mostrarEstadisticasFinales(List<ScrapingTask> tareas, 
                                                  long tiempoScraping, 
                                                  long tiempoTotal) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String tiempo = LocalTime.now().format(timeFormatter);
        
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("                 ESTADÍSTICAS FINALES DE WEB SCRAPING");
        System.out.println("=".repeat(80));
        System.out.println("[" + tiempo + "] Scraping completado");
        System.out.println();
        
        // Contar resultados
        int exitosos = 0;
        int errores = 0;
        long tiempoMinimo = Long.MAX_VALUE;
        long tiempoMaximo = 0;
        long tiempoTotalTareas = 0;
        
        for (ScrapingTask tarea : tareas) {
            if (tarea.isExitoso()) {
                exitosos++;
            } else {
                errores++;
            }
            
            long tiempoTarea = tarea.getTiempoEjecucion();
            tiempoTotalTareas += tiempoTarea;
            
            if (tiempoTarea < tiempoMinimo) {
                tiempoMinimo = tiempoTarea;
            }
            if (tiempoTarea > tiempoMaximo) {
                tiempoMaximo = tiempoTarea;
            }
        }
        
        // Estadísticas generales
        System.out.println("📊 ESTADÍSTICAS GENERALES:");
        System.out.println("   Total de noticias: " + tareas.size());
        System.out.println("   Procesadas exitosamente: " + exitosos);
        System.out.println("   Errores: " + errores);
        System.out.println("   Tasa de éxito: " + String.format("%.1f%%", (exitosos * 100.0 / tareas.size())));
        System.out.println();
        
        // Estadísticas de tiempo
        System.out.println("⏱️ ESTADÍSTICAS DE TIEMPO:");
        System.out.println("   Tiempo total del programa: " + tiempoTotal + "ms (" + 
                          String.format("%.3f", tiempoTotal / 1000.0) + "s)");
        System.out.println("   Tiempo de scraping concurrente: " + tiempoScraping + "ms (" + 
                          String.format("%.3f", tiempoScraping / 1000.0) + "s)");
        
        if (exitosos > 0) {
            double tiempoPromedio = tiempoTotalTareas / (double) tareas.size();
            System.out.println("   Tiempo promedio por noticia: " + String.format("%.0f", tiempoPromedio) + "ms");
            System.out.println("   Tiempo mínimo: " + tiempoMinimo + "ms");
            System.out.println("   Tiempo máximo: " + tiempoMaximo + "ms");
        }
        System.out.println();
        
        // Análisis de rendimiento
        System.out.println("⚡ ANÁLISIS DE RENDIMIENTO:");
        double tiempoSecuencial = tiempoTotalTareas; // Si se hubiera hecho secuencialmente
        double mejora = tiempoSecuencial / tiempoScraping;
        
        System.out.println("   Tiempo secuencial estimado: " + String.format("%.0f", tiempoSecuencial) + "ms");
        System.out.println("   Tiempo concurrente real: " + tiempoScraping + "ms");
        System.out.println("   Mejora de rendimiento: " + String.format("%.1fx", mejora) + " más rápido");
        System.out.println("   Eficiencia de paralelización: " + 
                          String.format("%.1f%%", (mejora / tareas.size()) * 100));
        System.out.println();
        
        // Detalles por tarea
        System.out.println("📋 DETALLES POR TAREA:");
        for (int i = 0; i < tareas.size(); i++) {
            ScrapingTask tarea = tareas.get(i);
            String estado = tarea.isExitoso() ? "✅ ÉXITO" : "❌ ERROR";
            String detalle = tarea.isExitoso() ? 
                           String.format("(%dms)", tarea.getTiempoEjecucion()) :
                           "(" + tarea.getMensajeError() + ")";
            
            System.out.println(String.format("   Noticia %2d: %s %s", 
                                            (i + 1), estado, detalle));
        }
        System.out.println();
        
        // Mostrar errores si los hay
        if (errores > 0) {
            System.out.println("🚨 ERRORES ENCONTRADOS:");
            for (int i = 0; i < tareas.size(); i++) {
                ScrapingTask tarea = tareas.get(i);
                if (!tarea.isExitoso()) {
                    System.out.println("   Noticia " + (i + 1) + ": " + tarea.getMensajeError());
                    System.out.println("   URL: " + tarea.getUrl());
                }
            }
            System.out.println();
        }
        
        // Conclusiones
        System.out.println("🎯 CONCLUSIONES:");
        if (exitosos == tareas.size()) {
            System.out.println("   ✅ Todas las noticias fueron procesadas exitosamente");
        } else if (exitosos >= tareas.size() * 0.8) {
            System.out.println("   ⚠️ La mayoría de noticias fueron procesadas exitosamente");
        } else {
            System.out.println("   ❌ Se encontraron múltiples errores en el procesamiento");
        }
        
        if (mejora >= 5) {
            System.out.println("   🚀 Excelente mejora de rendimiento con concurrencia");
        } else if (mejora >= 2) {
            System.out.println("   ✅ Buena mejora de rendimiento con concurrencia");
        } else {
            System.out.println("   ⚠️ Mejora limitada - posible overhead de concurrencia");
        }
    }
    
    /**
     * Demuestra las ventajas específicas de usar Runnable para web scraping
     */
    private static void demostrarVentajasRunnable(List<ScrapingTask> tareas, long tiempoScraping) {
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("         VENTAJAS DE RUNNABLE PARA WEB SCRAPING");
        System.out.println("=".repeat(80));
        
        System.out.println("\n🎯 1. PARALELIZACIÓN DE I/O BOUND TASKS:");
        System.out.println("   ✅ Web scraping es ideal para concurrencia (I/O intensivo)");
        System.out.println("   ✅ Mientras un hilo espera respuesta HTTP, otros procesan");
        System.out.println("   ✅ Aprovechamiento máximo de tiempo de espera de red");
        
        System.out.println("\n🔄 2. FLEXIBILIDAD DE IMPLEMENTACIÓN:");
        System.out.println("   ✅ Cada ScrapingTask es una unidad de trabajo independiente");
        System.out.println("   ✅ Fácil reutilización para diferentes sitios web");
        System.out.println("   ✅ Separación clara entre lógica de scraping y gestión de hilos");
        
        System.out.println("\n🎛️ 3. CONTROL GRANULAR:");
        System.out.println("   ✅ Cada tarea mantiene su propio estado y estadísticas");
        System.out.println("   ✅ Manejo independiente de errores por tarea");
        System.out.println("   ✅ Timeouts y reintentos configurables por tarea");
        
        // Demostrar estadísticas detalladas
        int tareasExitosas = (int) tareas.stream().filter(ScrapingTask::isExitoso).count();
        System.out.println("\n📊 4. MONITOREO DETALLADO:");
        System.out.println("   ✅ Seguimiento individual de " + tareas.size() + " tareas");
        System.out.println("   ✅ Tasa de éxito: " + tareasExitosas + "/" + tareas.size());
        System.out.println("   ✅ Tiempos de ejecución por tarea disponibles");
        
        System.out.println("\n🧪 5. TESTABILIDAD SUPERIOR:");
        System.out.println("   ✅ Lógica de scraping testeable sin hilos reales");
        System.out.println("   ✅ Mocking sencillo de respuestas HTTP");
        System.out.println("   ✅ Tests unitarios de parsing HTML independientes");
        
        System.out.println("\n⚡ 6. ESCALABILIDAD:");
        System.out.println("   ✅ Fácil integración con ExecutorService");
        System.out.println("   ✅ Control de número máximo de conexiones concurrentes");
        System.out.println("   ✅ Rate limiting implementable por pool de hilos");
        
        System.out.println("\n🏗️ 7. ARQUITECTURA LIMPIA:");
        System.out.println("   ✅ Responsabilidad única por clase");
        System.out.println("   ✅ Bajo acoplamiento entre componentes");
        System.out.println("   ✅ Extensible para diferentes tipos de scraping");
        
        System.out.println("\n📈 8. COMPARACIÓN CON HERENCIA DE THREAD:");
        System.out.println("   Flexibilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Reutilización:    Thread ⭐⭐   vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Testabilidad:     Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Escalabilidad:    Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        System.out.println("   Mantenibilidad:   Thread ⭐⭐⭐ vs Runnable ⭐⭐⭐⭐⭐");
        
        System.out.println("\n💡 9. CASOS DE USO REALES:");
        System.out.println("   • Agregadores de noticias (Google News, Feedly)");
        System.out.println("   • Monitoreo de precios (e-commerce)");
        System.out.println("   • Análisis de competencia (business intelligence)");
        System.out.println("   • Recolección de datos para investigación");
        
        System.out.println("\n⚠️ 10. CONSIDERACIONES ÉTICAS:");
        System.out.println("   • Respetar robots.txt y términos de servicio");
        System.out.println("   • Implementar rate limiting apropiado");
        System.out.println("   • No sobrecargar servidores objetivo");
        System.out.println("   • Uso responsable de datos extraídos");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🏆 CONCLUSIÓN: Runnable es la elección ideal para web scraping");
        System.out.println("   concurrente, ofreciendo flexibilidad, escalabilidad y");
        System.out.println("   mantenibilidad superior a la herencia de Thread.");
        System.out.println("=".repeat(80));
    }
}
