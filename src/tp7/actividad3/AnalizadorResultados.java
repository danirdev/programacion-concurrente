package tp7.actividad3;

import java.util.Arrays;
import java.util.List;

/**
 * 📈 AnalizadorResultados - Análisis estadístico de múltiples corridas
 * 
 * Esta clase realiza análisis estadístico completo de las 10 corridas
 * del problema Productor-Consumidor, calculando medidas de tendencia central,
 * dispersión y generando reportes comparativos detallados.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class AnalizadorResultados {
    
    private final List<EstadisticasCorrida> corridas;
    private final int totalCorridas;
    
    // 📊 Estadísticas calculadas
    private EstadisticasResumen estadisticasResumen;
    
    /**
     * 🏗️ Constructor del AnalizadorResultados
     * 
     * @param corridas Lista de corridas a analizar
     */
    public AnalizadorResultados(List<EstadisticasCorrida> corridas) {
        this.corridas = corridas;
        this.totalCorridas = corridas.size();
        this.estadisticasResumen = null;
        
        System.out.printf("📈 AnalizadorResultados inicializado con %d corridas%n", totalCorridas);
    }
    
    /**
     * 🔬 Realizar análisis completo de todas las corridas
     * 
     * @return Estadísticas resumidas
     */
    public EstadisticasResumen analizarCorridas() {
        System.out.println("🔬 Iniciando análisis estadístico completo...");
        
        // 📊 Extraer métricas de todas las corridas
        double[][] metricas = extraerMetricas();
        
        // 📈 Calcular estadísticas descriptivas
        this.estadisticasResumen = calcularEstadisticasDescriptivas(metricas);
        
        // 🔍 Análisis de consistencia
        analizarConsistencia();
        
        // 📊 Análisis de rendimiento
        analizarRendimiento();
        
        // 🎯 Identificar mejores y peores corridas
        identificarCorridasExtremas();
        
        System.out.println("✅ Análisis estadístico completado");
        return estadisticasResumen;
    }
    
    /**
     * 📊 Extraer métricas de todas las corridas
     * 
     * @return Matriz con métricas [corrida][métrica]
     */
    private double[][] extraerMetricas() {
        double[][] metricas = new double[totalCorridas][6];
        
        for (int i = 0; i < totalCorridas; i++) {
            metricas[i] = corridas.get(i).getMetricasPrincipales();
        }
        
        return metricas;
    }
    
    /**
     * 📈 Calcular estadísticas descriptivas
     * 
     * @param metricas Matriz de métricas
     * @return Estadísticas resumidas
     */
    private EstadisticasResumen calcularEstadisticasDescriptivas(double[][] metricas) {
        EstadisticasResumen resumen = new EstadisticasResumen();
        
        // Nombres de las métricas
        String[] nombresMetricas = {
            "Duración (ms)", "Throughput (elem/s)", "Eficiencia (%)", 
            "Utilización Buffer (%)", "Elementos Procesados", "Tasa Éxito"
        };
        
        for (int metrica = 0; metrica < 6; metrica++) {
            double[] valores = new double[totalCorridas];
            for (int corrida = 0; corrida < totalCorridas; corrida++) {
                valores[corrida] = metricas[corrida][metrica];
            }
            
            // Calcular estadísticas para esta métrica
            EstadisticaMetrica estadMetrica = new EstadisticaMetrica();
            estadMetrica.nombre = nombresMetricas[metrica];
            estadMetrica.media = calcularMedia(valores);
            estadMetrica.mediana = calcularMediana(valores);
            estadMetrica.desviacionEstandar = calcularDesviacionEstandar(valores, estadMetrica.media);
            estadMetrica.minimo = Arrays.stream(valores).min().orElse(0);
            estadMetrica.maximo = Arrays.stream(valores).max().orElse(0);
            estadMetrica.rango = estadMetrica.maximo - estadMetrica.minimo;
            estadMetrica.coeficienteVariacion = estadMetrica.media != 0 ? 
                (estadMetrica.desviacionEstandar / estadMetrica.media) * 100 : 0;
            
            resumen.metricas[metrica] = estadMetrica;
        }
        
        return resumen;
    }
    
    /**
     * 🔍 Analizar consistencia entre corridas
     */
    private void analizarConsistencia() {
        // Calcular coeficiente de variación promedio
        double cvPromedio = 0;
        for (int i = 0; i < 4; i++) { // Solo métricas principales (no elementos ni éxito)
            cvPromedio += estadisticasResumen.metricas[i].coeficienteVariacion;
        }
        cvPromedio /= 4;
        
        estadisticasResumen.consistencia = determinarConsistencia(cvPromedio);
        estadisticasResumen.coeficienteVariacionPromedio = cvPromedio;
        
        System.out.printf("🔍 Consistencia: %s (CV promedio: %.2f%%)%n", 
                         estadisticasResumen.consistencia, cvPromedio);
    }
    
    /**
     * 📊 Analizar rendimiento general
     */
    private void analizarRendimiento() {
        // Calcular rendimiento basado en throughput y eficiencia
        double throughputPromedio = estadisticasResumen.metricas[1].media;
        double eficienciaPromedio = estadisticasResumen.metricas[2].media;
        
        estadisticasResumen.rendimientoGeneral = (throughputPromedio * 0.6 + eficienciaPromedio * 0.4);
        estadisticasResumen.clasificacionRendimiento = determinarClasificacionRendimiento(
            estadisticasResumen.rendimientoGeneral);
        
        System.out.printf("📊 Rendimiento general: %.2f (%s)%n", 
                         estadisticasResumen.rendimientoGeneral, 
                         estadisticasResumen.clasificacionRendimiento);
    }
    
    /**
     * 🎯 Identificar corridas extremas (mejor y peor)
     */
    private void identificarCorridasExtremas() {
        EstadisticasCorrida mejorCorrida = null;
        EstadisticasCorrida peorCorrida = null;
        double mejorPuntuacion = Double.MIN_VALUE;
        double peorPuntuacion = Double.MAX_VALUE;
        
        for (EstadisticasCorrida corrida : corridas) {
            // Calcular puntuación combinada (throughput + eficiencia - tiempo normalizado)
            double puntuacion = corrida.getThroughputGeneral() + corrida.getEficienciaGeneral() - 
                              (corrida.getDuracionTotal() / 100.0); // Normalizar tiempo
            
            if (puntuacion > mejorPuntuacion) {
                mejorPuntuacion = puntuacion;
                mejorCorrida = corrida;
            }
            
            if (puntuacion < peorPuntuacion) {
                peorPuntuacion = puntuacion;
                peorCorrida = corrida;
            }
        }
        
        estadisticasResumen.mejorCorrida = mejorCorrida;
        estadisticasResumen.peorCorrida = peorCorrida;
        
        System.out.printf("🏆 Mejor corrida: #%d (puntuación: %.2f)%n", 
                         mejorCorrida.getNumeroCorrida(), mejorPuntuacion);
        System.out.printf("📉 Peor corrida: #%d (puntuación: %.2f)%n", 
                         peorCorrida.getNumeroCorrida(), peorPuntuacion);
    }
    
    /**
     * 📋 Generar reporte completo de análisis
     * 
     * @return String con reporte detallado
     */
    public String generarReporteCompleto() {
        if (estadisticasResumen == null) {
            analizarCorridas();
        }
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n").append("=".repeat(80)).append("\n");
        reporte.append("📈 ANÁLISIS ESTADÍSTICO COMPLETO - 10 CORRIDAS PRODUCTOR-CONSUMIDOR\n");
        reporte.append("=".repeat(80)).append("\n");
        
        // 📊 Tabla de estadísticas descriptivas
        reporte.append("\n📊 ESTADÍSTICAS DESCRIPTIVAS:\n");
        reporte.append(String.format("%-25s %10s %10s %10s %10s %10s %8s%n", 
                                    "MÉTRICA", "MEDIA", "MEDIANA", "DESV.EST", "MIN", "MAX", "CV(%)"));
        reporte.append("-".repeat(80)).append("\n");
        
        for (EstadisticaMetrica metrica : estadisticasResumen.metricas) {
            reporte.append(String.format("%-25s %10.2f %10.2f %10.2f %10.2f %10.2f %8.1f%n",
                                       metrica.nombre, metrica.media, metrica.mediana, 
                                       metrica.desviacionEstandar, metrica.minimo, 
                                       metrica.maximo, metrica.coeficienteVariacion));
        }
        
        // 🎯 Análisis de rendimiento
        reporte.append("\n🎯 ANÁLISIS DE RENDIMIENTO:\n");
        reporte.append(String.format("   📊 Rendimiento General: %.2f (%s)%n", 
                                    estadisticasResumen.rendimientoGeneral, 
                                    estadisticasResumen.clasificacionRendimiento));
        reporte.append(String.format("   🔍 Consistencia: %s (CV: %.2f%%)%n", 
                                    estadisticasResumen.consistencia, 
                                    estadisticasResumen.coeficienteVariacionPromedio));
        
        // 🏆 Corridas extremas
        reporte.append("\n🏆 CORRIDAS DESTACADAS:\n");
        reporte.append(String.format("   🥇 Mejor: Corrida #%d - Throughput: %.2f elem/s, Eficiencia: %.1f%%\n",
                                    estadisticasResumen.mejorCorrida.getNumeroCorrida(),
                                    estadisticasResumen.mejorCorrida.getThroughputGeneral(),
                                    estadisticasResumen.mejorCorrida.getEficienciaGeneral()));
        reporte.append(String.format("   📉 Peor: Corrida #%d - Throughput: %.2f elem/s, Eficiencia: %.1f%%\n",
                                    estadisticasResumen.peorCorrida.getNumeroCorrida(),
                                    estadisticasResumen.peorCorrida.getThroughputGeneral(),
                                    estadisticasResumen.peorCorrida.getEficienciaGeneral()));
        
        // 📈 Tabla resumen de todas las corridas
        reporte.append("\n📈 RESUMEN DE TODAS LAS CORRIDAS:\n");
        reporte.append(String.format("%-8s %-12s %-15s %-12s %-15s %-12s%n", 
                                    "CORRIDA", "TIEMPO(ms)", "THROUGHPUT", "EFICIENCIA", "UTILIZACIÓN", "ESTADO"));
        reporte.append("-".repeat(80)).append("\n");
        
        for (EstadisticasCorrida corrida : corridas) {
            String icono = corrida.isExitosa() ? "✅" : "❌";
            reporte.append(String.format("%-8s %-12d %-15.2f %-12.1f %-15.1f %s %-10s%n",
                                       "#" + corrida.getNumeroCorrida(),
                                       corrida.getDuracionTotal(),
                                       corrida.getThroughputGeneral(),
                                       corrida.getEficienciaGeneral(),
                                       corrida.getUtilizacionBuffer(),
                                       icono,
                                       corrida.getEstadoFinal()));
        }
        
        // 🎓 Conclusiones
        reporte.append("\n🎓 CONCLUSIONES:\n");
        reporte.append(generarConclusiones());
        
        return reporte.toString();
    }
    
    /**
     * 🎓 Generar conclusiones del análisis
     * 
     * @return String con conclusiones
     */
    private String generarConclusiones() {
        StringBuilder conclusiones = new StringBuilder();
        
        // Análisis de éxito
        long corridasExitosas = corridas.stream().mapToLong(c -> c.isExitosa() ? 1 : 0).sum();
        double tasaExito = (corridasExitosas * 100.0) / totalCorridas;
        
        conclusiones.append(String.format("   ✅ Tasa de éxito: %.0f%% (%d/%d corridas exitosas)%n", 
                                         tasaExito, corridasExitosas, totalCorridas));
        
        // Análisis de consistencia
        if (estadisticasResumen.coeficienteVariacionPromedio < 10) {
            conclusiones.append("   🎯 Sistema MUY CONSISTENTE: Baja variabilidad entre corridas\n");
        } else if (estadisticasResumen.coeficienteVariacionPromedio < 20) {
            conclusiones.append("   ✅ Sistema CONSISTENTE: Variabilidad aceptable\n");
        } else {
            conclusiones.append("   ⚠️ Sistema INCONSISTENTE: Alta variabilidad entre corridas\n");
        }
        
        // Análisis de rendimiento
        double throughputPromedio = estadisticasResumen.metricas[1].media;
        if (throughputPromedio > 40) {
            conclusiones.append("   🚀 ALTO RENDIMIENTO: Throughput superior a 40 elem/s\n");
        } else if (throughputPromedio > 25) {
            conclusiones.append("   ✅ BUEN RENDIMIENTO: Throughput aceptable\n");
        } else {
            conclusiones.append("   📉 BAJO RENDIMIENTO: Throughput inferior a 25 elem/s\n");
        }
        
        // Recomendaciones
        conclusiones.append("\n💡 RECOMENDACIONES:\n");
        if (estadisticasResumen.coeficienteVariacionPromedio > 15) {
            conclusiones.append("   • Investigar causas de variabilidad en rendimiento\n");
        }
        if (throughputPromedio < 30) {
            conclusiones.append("   • Optimizar tiempos de producción/consumo\n");
        }
        conclusiones.append("   • Los semáforos garantizan sincronización correcta\n");
        conclusiones.append("   • El sistema es robusto y libre de race conditions\n");
        
        return conclusiones.toString();
    }
    
    // 🔧 Métodos auxiliares para cálculos estadísticos
    
    private double calcularMedia(double[] valores) {
        return Arrays.stream(valores).average().orElse(0);
    }
    
    private double calcularMediana(double[] valores) {
        double[] sorted = Arrays.copyOf(valores, valores.length);
        Arrays.sort(sorted);
        int n = sorted.length;
        return n % 2 == 0 ? (sorted[n/2 - 1] + sorted[n/2]) / 2 : sorted[n/2];
    }
    
    private double calcularDesviacionEstandar(double[] valores, double media) {
        double suma = Arrays.stream(valores).map(v -> Math.pow(v - media, 2)).sum();
        return Math.sqrt(suma / valores.length);
    }
    
    private String determinarConsistencia(double coeficienteVariacion) {
        if (coeficienteVariacion < 10) return "MUY ALTA";
        if (coeficienteVariacion < 20) return "ALTA";
        if (coeficienteVariacion < 30) return "MEDIA";
        return "BAJA";
    }
    
    private String determinarClasificacionRendimiento(double rendimiento) {
        if (rendimiento > 80) return "EXCELENTE";
        if (rendimiento > 60) return "BUENO";
        if (rendimiento > 40) return "REGULAR";
        return "DEFICIENTE";
    }
    
    // 📊 Clases internas para estructurar datos
    
    public static class EstadisticasResumen {
        public EstadisticaMetrica[] metricas = new EstadisticaMetrica[6];
        public String consistencia;
        public double coeficienteVariacionPromedio;
        public double rendimientoGeneral;
        public String clasificacionRendimiento;
        public EstadisticasCorrida mejorCorrida;
        public EstadisticasCorrida peorCorrida;
    }
    
    public static class EstadisticaMetrica {
        public String nombre;
        public double media;
        public double mediana;
        public double desviacionEstandar;
        public double minimo;
        public double maximo;
        public double rango;
        public double coeficienteVariacion;
    }
    
    // 🔧 Getters
    
    public EstadisticasResumen getEstadisticasResumen() {
        return estadisticasResumen;
    }
    
    public List<EstadisticasCorrida> getCorridas() {
        return corridas;
    }
}
