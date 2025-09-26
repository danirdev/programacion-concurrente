package tp3.actividad5;

/**
 * Clase para comparar el rendimiento entre ejecución secuencial y concurrente
 * Implementa el análisis completo requerido por la Actividad 5
 * @author PC2025
 */
public class ComparadorRendimiento {
    
    /**
     * Ejecuta la comparación completa entre ambas implementaciones
     */
    public static void ejecutarComparacionCompleta() {
        System.out.println("TP3 - ACTIVIDAD 5 - COMPARACIÓN DE RENDIMIENTO");
        System.out.println("=".repeat(70));
        System.out.println("Algoritmo: SumRootN con 10,000,000 iteraciones por cálculo");
        System.out.println("Cálculos: 20 (root de 1 a 20)");
        System.out.println("=".repeat(70));
        
        // Información del sistema
        mostrarInformacionSistema();
        
        // Instrucciones para el usuario
        mostrarInstrucciones();
        
        // Esperar confirmación del usuario
        esperarConfirmacion("Presione Enter para comenzar la ejecución SECUENCIAL...");
        
        // Ejecutar versión secuencial
        System.out.println("\n" + "█".repeat(70));
        System.out.println("FASE 1: EJECUCIÓN SECUENCIAL");
        System.out.println("█".repeat(70));
        
        long tiempoInicioSecuencial = System.nanoTime();
        double[] resultadosSecuencial = CalculadoraSecuencial.ejecutarSecuencial();
        long tiempoFinSecuencial = System.nanoTime();
        
        double tiempoSecuencial = (tiempoFinSecuencial - tiempoInicioSecuencial) / 1_000_000_000.0;
        
        // Pausa entre ejecuciones
        esperarConfirmacion("\nPresione Enter para comenzar la ejecución CONCURRENTE...");
        
        // Ejecutar versión concurrente
        System.out.println("\n" + "█".repeat(70));
        System.out.println("FASE 2: EJECUCIÓN CONCURRENTE");
        System.out.println("█".repeat(70));
        
        long tiempoInicioConcurrente = System.nanoTime();
        double[] resultadosConcurrente = CalculadoraConcurrente.ejecutarConcurrente();
        long tiempoFinConcurrente = System.nanoTime();
        
        double tiempoConcurrente = (tiempoFinConcurrente - tiempoInicioConcurrente) / 1_000_000_000.0;
        
        // Análisis de resultados
        analizarResultados(tiempoSecuencial, tiempoConcurrente, resultadosSecuencial, resultadosConcurrente);
    }
    
    /**
     * Muestra información del sistema
     */
    private static void mostrarInformacionSistema() {
        System.out.println("\nINFORMACIÓN DEL SISTEMA:");
        System.out.println("-".repeat(30));
        
        int nucleos = Runtime.getRuntime().availableProcessors();
        long memoriaMax = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        String javaVersion = System.getProperty("java.version");
        String os = System.getProperty("os.name");
        
        System.out.println("Sistema Operativo: " + os);
        System.out.println("Versión de Java: " + javaVersion);
        System.out.println("Núcleos de CPU disponibles: " + nucleos);
        System.out.println("Memoria máxima JVM: " + memoriaMax + " MB");
        System.out.println("Speedup teórico máximo: " + nucleos + "x");
    }
    
    /**
     * Muestra instrucciones para el usuario
     */
    private static void mostrarInstrucciones() {
        System.out.println("\nINSTRUCCIONES IMPORTANTES:");
        System.out.println("-".repeat(30));
        System.out.println("1. Abra el Administrador de Tareas de Windows");
        System.out.println("2. Vaya a la pestaña 'Rendimiento'");
        System.out.println("3. Seleccione 'CPU'");
        System.out.println("4. Observe los gráficos de utilización por núcleo");
        System.out.println("5. Durante la ejecución SECUENCIAL:");
        System.out.println("   • Solo UN núcleo debería estar al 100%");
        System.out.println("   • Los demás núcleos deberían estar inactivos");
        System.out.println("6. Durante la ejecución CONCURRENTE:");
        System.out.println("   • MÚLTIPLES núcleos deberían estar al 100%");
        System.out.println("   • La carga debería distribuirse entre núcleos");
    }
    
    /**
     * Espera confirmación del usuario
     * @param mensaje mensaje a mostrar
     */
    private static void esperarConfirmacion(String mensaje) {
        System.out.println("\n" + mensaje);
        try {
            System.in.read();
            // Limpiar buffer
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignorar
        }
    }
    
    /**
     * Analiza y presenta los resultados de ambas ejecuciones
     */
    private static void analizarResultados(double tiempoSecuencial, double tiempoConcurrente,
                                         double[] resultadosSecuencial, double[] resultadosConcurrente) {
        System.out.println("\n" + "█".repeat(70));
        System.out.println("ANÁLISIS DE RESULTADOS");
        System.out.println("█".repeat(70));
        
        // Tiempos de ejecución
        System.out.println("\nTIEMPOS DE EJECUCIÓN:");
        System.out.println("-".repeat(30));
        System.out.printf("Ejecución secuencial:  %8.2f segundos%n", tiempoSecuencial);
        System.out.printf("Ejecución concurrente: %8.2f segundos%n", tiempoConcurrente);
        
        // Cálculo de speedup
        double speedup = tiempoSecuencial / tiempoConcurrente;
        System.out.printf("Speedup obtenido:      %8.2fx%n", speedup);
        
        // Eficiencia
        int nucleos = Runtime.getRuntime().availableProcessors();
        double eficiencia = speedup / nucleos;
        System.out.printf("Eficiencia:            %8.1f%% (%.2f/%d núcleos)%n", 
                         eficiencia * 100, speedup, nucleos);
        
        // Mejora de rendimiento
        double mejoraPorcentaje = ((tiempoSecuencial - tiempoConcurrente) / tiempoSecuencial) * 100;
        System.out.printf("Mejora de rendimiento: %8.1f%%(%n", mejoraPorcentaje);
        
        // Verificar correctitud de resultados
        verificarCorrectitud(resultadosSecuencial, resultadosConcurrente);
        
        // Análisis detallado
        analizarSpeedup(speedup, nucleos);
        
        // Conclusiones
        mostrarConclusiones(speedup, eficiencia, nucleos);
    }
    
    /**
     * Verifica que ambas implementaciones produzcan los mismos resultados
     */
    private static void verificarCorrectitud(double[] secuencial, double[] concurrente) {
        System.out.println("\nVERIFICACIÓN DE CORRECTITUD:");
        System.out.println("-".repeat(30));
        
        boolean todosCorrectos = true;
        double tolerancia = 1e-10; // Tolerancia para comparación de doubles
        
        for (int i = 0; i < secuencial.length; i++) {
            double diferencia = Math.abs(secuencial[i] - concurrente[i]);
            if (diferencia > tolerancia) {
                System.out.printf("⚠️  Diferencia en root %d: %.2e vs %.2e (diff: %.2e)%n", 
                                 i + 1, secuencial[i], concurrente[i], diferencia);
                todosCorrectos = false;
            }
        }
        
        if (todosCorrectos) {
            System.out.println("✅ Todos los resultados son idénticos");
            System.out.println("✅ La implementación concurrente es correcta");
        } else {
            System.out.println("⚠️  Se encontraron diferencias en los resultados");
        }
    }
    
    /**
     * Analiza el speedup obtenido
     */
    private static void analizarSpeedup(double speedup, int nucleos) {
        System.out.println("\nANÁLISIS DEL SPEEDUP:");
        System.out.println("-".repeat(30));
        
        double speedupTeorico = nucleos;
        double porcentajeSpeedup = (speedup / speedupTeorico) * 100;
        
        System.out.printf("Speedup teórico máximo: %.0fx (número de núcleos)%n", speedupTeorico);
        System.out.printf("Speedup obtenido:       %.2fx%n", speedup);
        System.out.printf("Porcentaje del teórico: %.1f%%%n", porcentajeSpeedup);
        
        if (speedup >= speedupTeorico * 0.8) {
            System.out.println("✅ Excelente paralelización (>80% del teórico)");
        } else if (speedup >= speedupTeorico * 0.6) {
            System.out.println("✅ Buena paralelización (60-80% del teórico)");
        } else if (speedup >= speedupTeorico * 0.4) {
            System.out.println("⚠️  Paralelización moderada (40-60% del teórico)");
        } else {
            System.out.println("⚠️  Paralelización limitada (<40% del teórico)");
        }
        
        // Factores que afectan el speedup
        System.out.println("\nFACTORES QUE AFECTAN EL SPEEDUP:");
        System.out.println("• Overhead de creación y gestión de hilos");
        System.out.println("• Competencia por recursos del sistema");
        System.out.println("• Limitaciones de memoria y cache");
        System.out.println("• Otros procesos ejecutándose en el sistema");
    }
    
    /**
     * Muestra conclusiones del análisis
     */
    private static void mostrarConclusiones(double speedup, double eficiencia, int nucleos) {
        System.out.println("\n" + "█".repeat(70));
        System.out.println("CONCLUSIONES");
        System.out.println("█".repeat(70));
        
        System.out.println("\n1. RENDIMIENTO:");
        if (speedup > 1.5) {
            System.out.println("   ✅ La paralelización mejora significativamente el rendimiento");
        } else {
            System.out.println("   ⚠️  La paralelización tiene beneficios limitados");
        }
        
        System.out.println("\n2. UTILIZACIÓN DE RECURSOS:");
        System.out.println("   • Secuencial: Utiliza solo 1 núcleo (~" + String.format("%.0f", 100.0/nucleos) + "% de CPU total)");
        System.out.println("   • Concurrente: Utiliza múltiples núcleos (hasta 100% de CPU total)");
        
        System.out.println("\n3. TIPO DE PROBLEMA:");
        System.out.println("   ✅ Este es un problema CPU-intensivo ideal para paralelización");
        System.out.println("   ✅ No hay dependencias entre cálculos");
        System.out.println("   ✅ No requiere sincronización compleja");
        
        System.out.println("\n4. RECOMENDACIONES:");
        if (eficiencia > 0.7) {
            System.out.println("   ✅ La paralelización es muy efectiva para este problema");
        } else {
            System.out.println("   ⚠️  Considerar optimizaciones adicionales o ajustar número de hilos");
        }
        
        System.out.println("\n5. OBSERVACIONES EN ADMINISTRADOR DE TAREAS:");
        System.out.println("   • Secuencial: Un núcleo al 100%, otros inactivos");
        System.out.println("   • Concurrente: Múltiples núcleos activos simultáneamente");
        System.out.println("   • La diferencia debería ser claramente visible");
    }
    
    /**
     * Ejecuta análisis estadístico con múltiples iteraciones
     */
    public static void ejecutarAnalisisEstadistico() {
        System.out.println("ANÁLISIS ESTADÍSTICO CON MÚLTIPLES ITERACIONES");
        System.out.println("=".repeat(60));
        
        int iteraciones = 3;
        System.out.println("Ejecutando " + iteraciones + " iteraciones de cada versión...\n");
        
        double tiempoSecuencial = CalculadoraSecuencial.ejecutarConEstadisticas(iteraciones);
        System.out.println();
        double tiempoConcurrente = CalculadoraConcurrente.ejecutarConEstadisticas(iteraciones);
        
        double speedupPromedio = tiempoSecuencial / tiempoConcurrente;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RESULTADOS ESTADÍSTICOS:");
        System.out.printf("Speedup promedio: %.2fx%n", speedupPromedio);
        System.out.printf("Mejora promedio: %.1f%%%n", ((tiempoSecuencial - tiempoConcurrente) / tiempoSecuencial) * 100);
    }
    
    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Verificar argumentos
        if (args.length > 0 && args[0].equals("estadistico")) {
            ejecutarAnalisisEstadistico();
        } else {
            ejecutarComparacionCompleta();
        }
    }
}
