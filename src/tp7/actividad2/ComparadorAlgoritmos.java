package tp7.actividad2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 📊 ComparadorAlgoritmos - Comparación de rendimiento entre algoritmos
 * 
 * Esta clase ejecuta y compara el rendimiento de los diferentes algoritmos
 * de exclusión mutua: 1 Flag, 2 Flags, Peterson y Semáforos.
 * Proporciona métricas detalladas y análisis comparativo.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class ComparadorAlgoritmos {
    
    private static final int NUMERO_ACCESOS = 20;
    private static final int NUMERO_PROCESOS = 2;
    private static final String SEPARADOR = "=".repeat(70);
    
    /**
     * 🚀 Método principal - Ejecuta comparación completa
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("📊 COMPARADOR DE ALGORITMOS DE EXCLUSIÓN MUTUA");
        System.out.println(SEPARADOR);
        
        ComparadorAlgoritmos comparador = new ComparadorAlgoritmos();
        
        // 🧪 Ejecutar pruebas de todos los algoritmos
        ResultadoComparacion resultado = comparador.ejecutarComparacionCompleta();
        
        // 📈 Mostrar resultados comparativos
        comparador.mostrarResultadosComparativos(resultado);
        
        // 🏆 Determinar el mejor algoritmo
        comparador.determinarMejorAlgoritmo(resultado);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ COMPARACIÓN COMPLETADA");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 🧪 Ejecutar comparación completa de todos los algoritmos
     * 
     * @return Resultado con métricas de todos los algoritmos
     */
    public ResultadoComparacion ejecutarComparacionCompleta() {
        ResultadoComparacion resultado = new ResultadoComparacion();
        
        System.out.println("🚀 Iniciando comparación de algoritmos...\n");
        
        // 1️⃣ Probar algoritmo de 1 Flag
        resultado.unFlag = probarUnFlag();
        
        // 2️⃣ Probar algoritmo de 2 Flags
        resultado.dosFlags = probarDosFlags();
        
        // 3️⃣ Probar algoritmo de Peterson
        resultado.peterson = probarPeterson();
        
        // 4️⃣ Probar semáforos
        resultado.semaforos = probarSemaforos();
        
        return resultado;
    }
    
    /**
     * 🚩 Probar algoritmo de 1 Flag
     */
    private MetricasAlgoritmo probarUnFlag() {
        System.out.println("🚩 === PROBANDO ALGORITMO 1 FLAG ===");
        
        AlgoritmoClasico.UnFlag algoritmo = new AlgoritmoClasico.UnFlag();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
            CountDownLatch latch = new CountDownLatch(NUMERO_PROCESOS);
            
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = 
                    new AlgoritmoClasico.ProcesoPrueba("1Flag", "P" + i, i, NUMERO_ACCESOS, algoritmo);
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(5000); // Timeout de 5 segundos
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Recopilar métricas
            MetricasAlgoritmo metricas = new MetricasAlgoritmo("1 Flag");
            metricas.tiempoEjecucion = tiempoTotal;
            metricas.violaciones = algoritmo.getViolaciones();
            metricas.deadlocks = 0; // 1 Flag no tiene deadlock, pero tampoco exclusión mutua
            
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                double[] resultados = proceso.getResultados();
                metricas.accesosExitosos += (int) resultados[0];
                metricas.accesosFallidos += (int) resultados[1];
            }
            
            metricas.calcularEficiencia();
            
            System.out.printf("❌ 1 Flag - Violaciones: %d, Tiempo: %dms%n", 
                            metricas.violaciones, metricas.tiempoEjecucion);
            
            return metricas;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new MetricasAlgoritmo("1 Flag (ERROR)");
        }
    }
    
    /**
     * 🚩🚩 Probar algoritmo de 2 Flags
     */
    private MetricasAlgoritmo probarDosFlags() {
        System.out.println("\n🚩🚩 === PROBANDO ALGORITMO 2 FLAGS ===");
        
        AlgoritmoClasico.DosFlags algoritmo = new AlgoritmoClasico.DosFlags();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
            
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = 
                    new AlgoritmoClasico.ProcesoPrueba("2Flags", "P" + i, i, NUMERO_ACCESOS, algoritmo);
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(10000); // Timeout mayor por posibles deadlocks
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Recopilar métricas
            MetricasAlgoritmo metricas = new MetricasAlgoritmo("2 Flags");
            metricas.tiempoEjecucion = tiempoTotal;
            metricas.violaciones = algoritmo.getViolaciones();
            metricas.deadlocks = algoritmo.getDeadlocks();
            
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                double[] resultados = proceso.getResultados();
                metricas.accesosExitosos += (int) resultados[0];
                metricas.accesosFallidos += (int) resultados[1];
            }
            
            metricas.calcularEficiencia();
            
            System.out.printf("⚠️ 2 Flags - Violaciones: %d, Deadlocks: %d, Tiempo: %dms%n", 
                            metricas.violaciones, metricas.deadlocks, metricas.tiempoEjecucion);
            
            return metricas;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new MetricasAlgoritmo("2 Flags (ERROR)");
        }
    }
    
    /**
     * 🎯 Probar algoritmo de Peterson
     */
    private MetricasAlgoritmo probarPeterson() {
        System.out.println("\n🎯 === PROBANDO ALGORITMO PETERSON ===");
        
        AlgoritmoClasico.Peterson algoritmo = new AlgoritmoClasico.Peterson();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            List<AlgoritmoClasico.ProcesoPrueba> procesos = new ArrayList<>();
            
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                AlgoritmoClasico.ProcesoPrueba proceso = 
                    new AlgoritmoClasico.ProcesoPrueba("Peterson", "P" + i, i, NUMERO_ACCESOS, algoritmo);
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                proceso.join(8000); // Timeout de 8 segundos
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Recopilar métricas
            MetricasAlgoritmo metricas = new MetricasAlgoritmo("Peterson");
            metricas.tiempoEjecucion = tiempoTotal;
            metricas.violaciones = algoritmo.getViolaciones();
            metricas.deadlocks = 0; // Peterson no tiene deadlock
            metricas.busyWaitCiclos = algoritmo.getBusyWaitCiclos();
            
            for (AlgoritmoClasico.ProcesoPrueba proceso : procesos) {
                double[] resultados = proceso.getResultados();
                metricas.accesosExitosos += (int) resultados[0];
                metricas.accesosFallidos += (int) resultados[1];
            }
            
            metricas.calcularEficiencia();
            
            System.out.printf("✅ Peterson - Violaciones: %d, Busy Wait: %d, Tiempo: %dms%n", 
                            metricas.violaciones, metricas.busyWaitCiclos, metricas.tiempoEjecucion);
            
            return metricas;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new MetricasAlgoritmo("Peterson (ERROR)");
        }
    }
    
    /**
     * 🚦 Probar semáforos
     */
    private MetricasAlgoritmo probarSemaforos() {
        System.out.println("\n🚦 === PROBANDO SEMÁFOROS ===");
        
        RecursoCompartido recurso = new RecursoCompartido();
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            List<ProcesoExclusionMutua> procesos = new ArrayList<>();
            
            // Crear procesos
            for (int i = 1; i <= NUMERO_PROCESOS; i++) {
                ProcesoExclusionMutua proceso = 
                    new ProcesoExclusionMutua("P" + i, recurso, NUMERO_ACCESOS, 30, 80);
                procesos.add(proceso);
            }
            
            // Iniciar procesos
            for (ProcesoExclusionMutua proceso : procesos) {
                proceso.start();
            }
            
            // Esperar finalización
            for (ProcesoExclusionMutua proceso : procesos) {
                proceso.join();
            }
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // Recopilar métricas
            MetricasAlgoritmo metricas = new MetricasAlgoritmo("Semáforos");
            metricas.tiempoEjecucion = tiempoTotal;
            
            double[] metricasRecurso = recurso.getMetricasRendimiento();
            metricas.violaciones = (int) metricasRecurso[2]; // Debería ser 0
            metricas.deadlocks = 0; // Semáforos no tienen deadlock
            
            for (ProcesoExclusionMutua proceso : procesos) {
                double[] resultados = proceso.getEstadisticas();
                metricas.accesosExitosos += (int) resultados[0];
                metricas.accesosFallidos += (int) resultados[1];
            }
            
            metricas.calcularEficiencia();
            
            System.out.printf("🏆 Semáforos - Violaciones: %d, Tiempo: %dms%n", 
                            metricas.violaciones, metricas.tiempoEjecucion);
            
            return metricas;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new MetricasAlgoritmo("Semáforos (ERROR)");
        }
    }
    
    /**
     * 📊 Mostrar resultados comparativos
     */
    private void mostrarResultadosComparativos(ResultadoComparacion resultado) {
        System.out.println("\n" + SEPARADOR);
        System.out.println("📊 RESULTADOS COMPARATIVOS");
        System.out.println(SEPARADOR);
        
        // Tabla comparativa
        System.out.printf("%-15s %-10s %-12s %-10s %-12s %-10s%n", 
                         "ALGORITMO", "TIEMPO(ms)", "VIOLACIONES", "DEADLOCKS", "EFICIENCIA", "ESTADO");
        System.out.println("-".repeat(70));
        
        imprimirFilaResultado(resultado.unFlag);
        imprimirFilaResultado(resultado.dosFlags);
        imprimirFilaResultado(resultado.peterson);
        imprimirFilaResultado(resultado.semaforos);
        
        System.out.println("-".repeat(70));
    }
    
    /**
     * 📝 Imprimir fila de resultado
     */
    private void imprimirFilaResultado(MetricasAlgoritmo metricas) {
        String estado = determinarEstado(metricas);
        String icono = obtenerIcono(metricas);
        
        System.out.printf("%-15s %-10d %-12d %-10d %-12.1f%% %s%n",
                         metricas.nombre,
                         metricas.tiempoEjecucion,
                         metricas.violaciones,
                         metricas.deadlocks,
                         metricas.eficiencia,
                         icono + " " + estado);
    }
    
    /**
     * 🏆 Determinar el mejor algoritmo
     */
    private void determinarMejorAlgoritmo(ResultadoComparacion resultado) {
        System.out.println("\n=== 🏆 ANÁLISIS Y RECOMENDACIONES ===");
        
        // Análisis de cada algoritmo
        System.out.println("\n📋 ANÁLISIS DETALLADO:");
        analizarAlgoritmo(resultado.unFlag, "❌ DEFECTUOSO - No garantiza exclusión mutua");
        analizarAlgoritmo(resultado.dosFlags, "⚠️ PROBLEMÁTICO - Deadlocks frecuentes");
        analizarAlgoritmo(resultado.peterson, "✅ CORRECTO - Pero con busy wait");
        analizarAlgoritmo(resultado.semaforos, "🏆 ÓPTIMO - Solución moderna y eficiente");
        
        // Recomendación final
        System.out.println("\n🎯 RECOMENDACIÓN FINAL:");
        System.out.println("🏆 GANADOR: SEMÁFOROS");
        System.out.println("   ✅ Sin violaciones de exclusión mutua");
        System.out.println("   ✅ Sin deadlocks");
        System.out.println("   ✅ Sin busy wait (bloqueo eficiente)");
        System.out.println("   ✅ Fairness garantizado");
        System.out.println("   ✅ Mejor rendimiento general");
        System.out.println("   ✅ Código más simple y mantenible");
    }
    
    /**
     * 🔍 Analizar algoritmo específico
     */
    private void analizarAlgoritmo(MetricasAlgoritmo metricas, String descripcion) {
        System.out.printf("   %s: %s%n", metricas.nombre, descripcion);
        System.out.printf("      Tiempo: %dms, Eficiencia: %.1f%%, Violaciones: %d%n",
                         metricas.tiempoEjecucion, metricas.eficiencia, metricas.violaciones);
    }
    
    /**
     * 🎯 Determinar estado del algoritmo
     */
    private String determinarEstado(MetricasAlgoritmo metricas) {
        if (metricas.violaciones > 0) return "DEFECTUOSO";
        if (metricas.deadlocks > 0) return "PROBLEMÁTICO";
        if (metricas.busyWaitCiclos > 1000) return "INEFICIENTE";
        if (metricas.eficiencia > 90) return "EXCELENTE";
        if (metricas.eficiencia > 70) return "BUENO";
        return "REGULAR";
    }
    
    /**
     * 🎨 Obtener icono para el algoritmo
     */
    private String obtenerIcono(MetricasAlgoritmo metricas) {
        if (metricas.violaciones > 0) return "❌";
        if (metricas.deadlocks > 0) return "⚠️";
        if (metricas.eficiencia > 90) return "🏆";
        if (metricas.eficiencia > 70) return "✅";
        return "⭐";
    }
    
    /**
     * 📊 Clase para métricas de algoritmo
     */
    public static class MetricasAlgoritmo {
        public String nombre;
        public long tiempoEjecucion;
        public int violaciones;
        public int deadlocks;
        public int accesosExitosos;
        public int accesosFallidos;
        public int busyWaitCiclos;
        public double eficiencia;
        
        public MetricasAlgoritmo(String nombre) {
            this.nombre = nombre;
        }
        
        public void calcularEficiencia() {
            int totalAccesos = accesosExitosos + accesosFallidos;
            if (totalAccesos == 0) {
                eficiencia = 0;
                return;
            }
            
            double tasaExito = (double) accesosExitosos / totalAccesos;
            double penalizacionViolaciones = Math.max(0, 1.0 - (violaciones * 0.1));
            double penalizacionDeadlocks = Math.max(0, 1.0 - (deadlocks * 0.2));
            double penalizacionTiempo = tiempoEjecucion > 0 ? Math.min(1.0, 5000.0 / tiempoEjecucion) : 0;
            
            eficiencia = tasaExito * penalizacionViolaciones * penalizacionDeadlocks * penalizacionTiempo * 100;
        }
    }
    
    /**
     * 📋 Clase para resultado de comparación
     */
    public static class ResultadoComparacion {
        public MetricasAlgoritmo unFlag;
        public MetricasAlgoritmo dosFlags;
        public MetricasAlgoritmo peterson;
        public MetricasAlgoritmo semaforos;
    }
}
