package tp7.actividad3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 📊 EstadisticasCorrida - Métricas y análisis de una corrida individual
 * 
 * Esta clase encapsula todas las métricas y estadísticas de una corrida
 * individual del problema Productor-Consumidor, permitiendo análisis
 * detallado y comparación entre múltiples ejecuciones.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class EstadisticasCorrida {
    
    // 🏷️ Identificación de la corrida
    private final int numeroCorrida;
    private final LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFin;
    
    // ⏱️ Métricas de tiempo
    private long duracionTotal; // en milisegundos
    private long tiempoProduccion;
    private long tiempoConsumo;
    private long tiempoEsperaTotal;
    
    // 📊 Métricas de rendimiento
    private int elementosProcesados;
    private double throughputGeneral;
    private double throughputProductor;
    private double throughputConsumidor;
    
    // 🎯 Métricas de eficiencia
    private double eficienciaProductor;
    private double eficienciaConsumidor;
    private double eficienciaGeneral;
    private double utilizacionBuffer;
    
    // 🔄 Métricas del buffer
    private int capacidadBuffer;
    private int maxElementosSimultaneos;
    private double ocupacionPromedio;
    
    // ✅ Estado de la corrida
    private boolean exitosa;
    private String estadoFinal;
    private String observaciones;
    
    /**
     * 🏗️ Constructor de EstadisticasCorrida
     * 
     * @param numeroCorrida Número de la corrida (1-10)
     */
    public EstadisticasCorrida(int numeroCorrida) {
        this.numeroCorrida = numeroCorrida;
        this.tiempoInicio = LocalDateTime.now();
        this.exitosa = false;
        this.estadoFinal = "EN_PROGRESO";
        this.observaciones = "";
        
        System.out.printf("📊 Iniciando análisis de corrida #%d%n", numeroCorrida);
    }
    
    /**
     * 🏁 Finalizar corrida y calcular métricas
     * 
     * @param buffer Buffer compartido usado en la corrida
     * @param productor Productor de la corrida
     * @param consumidor Consumidor de la corrida
     */
    public void finalizarCorrida(BufferCompartido buffer, Productor productor, Consumidor consumidor) {
        this.tiempoFin = LocalDateTime.now();
        this.duracionTotal = java.time.Duration.between(tiempoInicio, tiempoFin).toMillis();
        
        // 📊 Recopilar métricas del buffer
        recopilarMetricasBuffer(buffer);
        
        // 🏭 Recopilar métricas del productor
        recopilarMetricasProductor(productor);
        
        // 👤 Recopilar métricas del consumidor
        recopilarMetricasConsumidor(consumidor);
        
        // 🎯 Calcular métricas generales
        calcularMetricasGenerales();
        
        // ✅ Determinar estado final
        determinarEstadoFinal(buffer, productor, consumidor);
        
        System.out.printf("🏁 Corrida #%d finalizada - Duración: %dms, Estado: %s%n", 
                         numeroCorrida, duracionTotal, estadoFinal);
    }
    
    /**
     * 📊 Recopilar métricas del buffer
     */
    private void recopilarMetricasBuffer(BufferCompartido buffer) {
        this.capacidadBuffer = buffer.getCapacidad();
        this.elementosProcesados = buffer.getTotalConsumidos();
        
        double[] metricasBuffer = buffer.getMetricasRendimiento();
        this.throughputGeneral = metricasBuffer[0]; // operaciones/segundo
        this.utilizacionBuffer = metricasBuffer[1]; // porcentaje utilización
        this.eficienciaGeneral = metricasBuffer[2]; // eficiencia general
        
        // Calcular ocupación promedio (estimada)
        this.ocupacionPromedio = utilizacionBuffer;
        this.maxElementosSimultaneos = (int) (capacidadBuffer * utilizacionBuffer / 100);
    }
    
    /**
     * 🏭 Recopilar métricas del productor
     */
    private void recopilarMetricasProductor(Productor productor) {
        double[] estadisticasProductor = productor.getEstadisticas();
        // [producidos, tiempoTotal, tiempoEspera, throughput, eficiencia]
        
        this.tiempoProduccion = (long) estadisticasProductor[1];
        this.throughputProductor = estadisticasProductor[3];
        this.eficienciaProductor = estadisticasProductor[4];
    }
    
    /**
     * 👤 Recopilar métricas del consumidor
     */
    private void recopilarMetricasConsumidor(Consumidor consumidor) {
        double[] estadisticasConsumidor = consumidor.getEstadisticas();
        // [consumidos, tiempoTotal, tiempoEspera, tiempoProcesamiento, throughput, eficiencia]
        
        this.tiempoConsumo = (long) estadisticasConsumidor[1];
        this.tiempoEsperaTotal = (long) estadisticasConsumidor[2];
        this.throughputConsumidor = estadisticasConsumidor[4];
        this.eficienciaConsumidor = estadisticasConsumidor[5];
    }
    
    /**
     * 🎯 Calcular métricas generales
     */
    private void calcularMetricasGenerales() {
        // Throughput general como promedio ponderado
        if (tiempoProduccion > 0 && tiempoConsumo > 0) {
            double tiempoTotal = tiempoProduccion + tiempoConsumo;
            this.throughputGeneral = (throughputProductor * tiempoProduccion + 
                                    throughputConsumidor * tiempoConsumo) / tiempoTotal;
        }
        
        // Eficiencia general como promedio de productor y consumidor
        this.eficienciaGeneral = (eficienciaProductor + eficienciaConsumidor) / 2;
        
        // Ajustar por utilización del buffer
        this.eficienciaGeneral *= (utilizacionBuffer / 100.0);
    }
    
    /**
     * ✅ Determinar estado final de la corrida
     */
    private void determinarEstadoFinal(BufferCompartido buffer, Productor productor, Consumidor consumidor) {
        // Verificar integridad del buffer
        boolean integridadBuffer = buffer.verificarIntegridad();
        
        // Verificar que todos los elementos fueron procesados
        boolean procesadoCompleto = buffer.getTotalProducidos() == buffer.getTotalConsumidos();
        
        // Verificar que no hay elementos perdidos
        boolean sinElementosPerdidos = buffer.getElementosActuales() == 0;
        
        if (integridadBuffer && procesadoCompleto && sinElementosPerdidos) {
            this.exitosa = true;
            this.estadoFinal = "EXITOSA";
            this.observaciones = "Corrida completada sin errores";
        } else {
            this.exitosa = false;
            this.estadoFinal = "CON_ERRORES";
            
            StringBuilder obs = new StringBuilder("Problemas detectados: ");
            if (!integridadBuffer) obs.append("Integridad comprometida; ");
            if (!procesadoCompleto) obs.append("Procesamiento incompleto; ");
            if (!sinElementosPerdidos) obs.append("Elementos perdidos; ");
            
            this.observaciones = obs.toString();
        }
        
        // Agregar observaciones de rendimiento
        if (eficienciaGeneral < 70) {
            this.observaciones += " Baja eficiencia (" + String.format("%.1f%%", eficienciaGeneral) + ")";
        }
        if (utilizacionBuffer < 30) {
            this.observaciones += " Baja utilización buffer (" + String.format("%.1f%%", utilizacionBuffer) + ")";
        }
    }
    
    /**
     * 📋 Generar reporte detallado de la corrida
     * 
     * @return String con reporte completo
     */
    public String generarReporteDetallado() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        
        StringBuilder reporte = new StringBuilder();
        reporte.append(String.format("\n=== 📊 REPORTE CORRIDA #%d ===\n", numeroCorrida));
        reporte.append(String.format("⏱️ Inicio: %s\n", tiempoInicio.format(formatter)));
        reporte.append(String.format("🏁 Fin: %s\n", tiempoFin != null ? tiempoFin.format(formatter) : "N/A"));
        reporte.append(String.format("⏰ Duración: %dms (%.2fs)\n", duracionTotal, duracionTotal / 1000.0));
        reporte.append(String.format("✅ Estado: %s\n", estadoFinal));
        
        reporte.append("\n📊 MÉTRICAS DE RENDIMIENTO:\n");
        reporte.append(String.format("   📦 Elementos procesados: %d\n", elementosProcesados));
        reporte.append(String.format("   🚀 Throughput general: %.2f elem/s\n", throughputGeneral));
        reporte.append(String.format("   🏭 Throughput productor: %.2f elem/s\n", throughputProductor));
        reporte.append(String.format("   👤 Throughput consumidor: %.2f elem/s\n", throughputConsumidor));
        
        reporte.append("\n🎯 MÉTRICAS DE EFICIENCIA:\n");
        reporte.append(String.format("   ⚡ Eficiencia general: %.1f%%\n", eficienciaGeneral));
        reporte.append(String.format("   🏭 Eficiencia productor: %.1f%%\n", eficienciaProductor));
        reporte.append(String.format("   👤 Eficiencia consumidor: %.1f%%\n", eficienciaConsumidor));
        
        reporte.append("\n🔄 MÉTRICAS DEL BUFFER:\n");
        reporte.append(String.format("   📦 Capacidad: %d elementos\n", capacidadBuffer));
        reporte.append(String.format("   📈 Utilización: %.1f%%\n", utilizacionBuffer));
        reporte.append(String.format("   📊 Ocupación promedio: %.1f%%\n", ocupacionPromedio));
        reporte.append(String.format("   🔝 Máximo simultáneo: %d elementos\n", maxElementosSimultaneos));
        
        reporte.append("\n📝 OBSERVACIONES:\n");
        reporte.append(String.format("   %s\n", observaciones));
        
        return reporte.toString();
    }
    
    /**
     * 📊 Obtener resumen de métricas para análisis estadístico
     * 
     * @return Array con métricas principales
     */
    public double[] getMetricasPrincipales() {
        return new double[]{
            duracionTotal,           // 0: Duración en ms
            throughputGeneral,       // 1: Throughput general
            eficienciaGeneral,       // 2: Eficiencia general
            utilizacionBuffer,       // 3: Utilización buffer
            elementosProcesados,     // 4: Elementos procesados
            exitosa ? 1.0 : 0.0     // 5: Éxito (1) o fallo (0)
        };
    }
    
    /**
     * 📈 Comparar con otra corrida
     * 
     * @param otra Otra corrida para comparar
     * @return String con comparación
     */
    public String compararCon(EstadisticasCorrida otra) {
        StringBuilder comparacion = new StringBuilder();
        comparacion.append(String.format("📊 COMPARACIÓN CORRIDA #%d vs #%d:\n", 
                                        numeroCorrida, otra.numeroCorrida));
        
        double difTiempo = ((double) duracionTotal - otra.duracionTotal) / otra.duracionTotal * 100;
        double difThroughput = (throughputGeneral - otra.throughputGeneral) / otra.throughputGeneral * 100;
        double difEficiencia = eficienciaGeneral - otra.eficienciaGeneral;
        
        comparacion.append(String.format("   ⏰ Tiempo: %+.1f%% (%dms vs %dms)\n", 
                                       difTiempo, duracionTotal, otra.duracionTotal));
        comparacion.append(String.format("   🚀 Throughput: %+.1f%% (%.2f vs %.2f elem/s)\n", 
                                       difThroughput, throughputGeneral, otra.throughputGeneral));
        comparacion.append(String.format("   ⚡ Eficiencia: %+.1f%% (%.1f%% vs %.1f%%)\n", 
                                       difEficiencia, eficienciaGeneral, otra.eficienciaGeneral));
        
        return comparacion.toString();
    }
    
    // 🔧 Getters
    
    public int getNumeroCorrida() { return numeroCorrida; }
    public LocalDateTime getTiempoInicio() { return tiempoInicio; }
    public LocalDateTime getTiempoFin() { return tiempoFin; }
    public long getDuracionTotal() { return duracionTotal; }
    public int getElementosProcesados() { return elementosProcesados; }
    public double getThroughputGeneral() { return throughputGeneral; }
    public double getEficienciaGeneral() { return eficienciaGeneral; }
    public double getUtilizacionBuffer() { return utilizacionBuffer; }
    public boolean isExitosa() { return exitosa; }
    public String getEstadoFinal() { return estadoFinal; }
    public String getObservaciones() { return observaciones; }
    
    /**
     * 📝 Representación en string de la corrida
     * 
     * @return Resumen de la corrida
     */
    @Override
    public String toString() {
        return String.format("Corrida{#%d, duración=%dms, throughput=%.2f, eficiencia=%.1f%%, estado=%s}", 
                           numeroCorrida, duracionTotal, throughputGeneral, eficienciaGeneral, estadoFinal);
    }
}
