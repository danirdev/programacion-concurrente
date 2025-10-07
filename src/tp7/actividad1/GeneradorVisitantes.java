package tp7.actividad1;

import java.util.Random;

/**
 * 👥 GeneradorVisitantes - Simulador de llegada de visitantes al jardín
 * 
 * Esta clase simula la llegada aleatoria de visitantes que quieren entrar
 * o salir del jardín. Proporciona métodos para generar patrones realistas
 * de tráfico de visitantes.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class GeneradorVisitantes {
    
    private final Random random;
    private final ContadorJardines contador;
    
    // 📊 Configuración de patrones de visitantes
    private static final double PROBABILIDAD_ENTRADA_ALTA = 0.7;  // Hora pico
    private static final double PROBABILIDAD_ENTRADA_NORMAL = 0.6; // Hora normal
    private static final double PROBABILIDAD_ENTRADA_BAJA = 0.4;   // Hora valle
    
    /**
     * 🏗️ Constructor del GeneradorVisitantes
     * 
     * @param contador Referencia al contador compartido
     */
    public GeneradorVisitantes(ContadorJardines contador) {
        this.contador = contador;
        this.random = new Random();
    }
    
    /**
     * 🎲 Generar operación aleatoria basada en el contexto actual
     * 
     * @param tipoHora Tipo de hora (PICO, NORMAL, VALLE)
     * @return true para entrada, false para salida
     */
    public boolean generarOperacion(TipoHora tipoHora) {
        int visitantesActuales = contador.getVisitantesActuales();
        
        // Si no hay visitantes, forzar entrada
        if (visitantesActuales == 0) {
            return true;
        }
        
        // Si hay muchos visitantes (>50), favorecer salidas
        if (visitantesActuales > 50) {
            return random.nextDouble() < 0.3; // 30% entrada, 70% salida
        }
        
        // Usar probabilidades según tipo de hora
        double probabilidadEntrada = switch (tipoHora) {
            case PICO -> PROBABILIDAD_ENTRADA_ALTA;
            case NORMAL -> PROBABILIDAD_ENTRADA_NORMAL;
            case VALLE -> PROBABILIDAD_ENTRADA_BAJA;
        };
        
        return random.nextDouble() < probabilidadEntrada;
    }
    
    /**
     * ⏱️ Generar tiempo de espera entre operaciones
     * 
     * @param tipoHora Tipo de hora que afecta la frecuencia
     * @return Tiempo en milisegundos
     */
    public int generarTiempoEspera(TipoHora tipoHora) {
        return switch (tipoHora) {
            case PICO -> 200 + random.nextInt(500);    // 200-700ms (alta frecuencia)
            case NORMAL -> 500 + random.nextInt(1000); // 500-1500ms (frecuencia normal)
            case VALLE -> 1000 + random.nextInt(2000); // 1000-3000ms (baja frecuencia)
        };
    }
    
    /**
     * 📅 Determinar tipo de hora basado en el tiempo transcurrido
     * 
     * @param tiempoTranscurrido Tiempo en segundos desde el inicio
     * @param duracionTotal Duración total de la simulación
     * @return Tipo de hora correspondiente
     */
    public TipoHora determinarTipoHora(long tiempoTranscurrido, int duracionTotal) {
        double porcentajeTranscurrido = (double) tiempoTranscurrido / duracionTotal;
        
        // Simular patrón realista: inicio normal, pico en el medio, valle al final
        if (porcentajeTranscurrido < 0.2) {
            return TipoHora.NORMAL;  // Primeros 20%
        } else if (porcentajeTranscurrido < 0.7) {
            return TipoHora.PICO;    // 20% - 70% (hora pico)
        } else {
            return TipoHora.VALLE;   // Últimos 30%
        }
    }
    
    /**
     * 🎯 Generar ráfaga de visitantes (evento especial)
     * 
     * @param cantidad Número de visitantes en la ráfaga
     * @param puntoAcceso Punto que procesará la ráfaga
     */
    public void generarRafagaVisitantes(int cantidad, String puntoAcceso) {
        System.out.println("🌊 Ráfaga de " + cantidad + " visitantes en " + puntoAcceso);
        
        for (int i = 0; i < cantidad; i++) {
            // Todas las operaciones de la ráfaga son entradas
            contador.entrarVisitante(puntoAcceso + "-RÁFAGA");
            
            try {
                // Pequeña pausa entre visitantes de la ráfaga
                Thread.sleep(50 + random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * 📊 Generar reporte de patrones de visitantes
     * 
     * @param duracionSimulacion Duración total en segundos
     * @return String con el reporte de patrones
     */
    public String generarReportePatrones(int duracionSimulacion) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n=== 📊 PATRONES DE VISITANTES ===\n");
        
        // Calcular distribución de tiempo por tipo de hora
        int tiempoNormal = (int) (duracionSimulacion * 0.2);
        int tiempoPico = (int) (duracionSimulacion * 0.5);
        int tiempoValle = (int) (duracionSimulacion * 0.3);
        
        reporte.append(String.format("⏰ Tiempo Normal: %ds (%.1f%% entrada)%n", 
                                   tiempoNormal, PROBABILIDAD_ENTRADA_NORMAL * 100));
        reporte.append(String.format("🔥 Tiempo Pico: %ds (%.1f%% entrada)%n", 
                                   tiempoPico, PROBABILIDAD_ENTRADA_ALTA * 100));
        reporte.append(String.format("🌙 Tiempo Valle: %ds (%.1f%% entrada)%n", 
                                   tiempoValle, PROBABILIDAD_ENTRADA_BAJA * 100));
        
        return reporte.toString();
    }
    
    /**
     * 🎲 Generar evento aleatorio especial
     * 
     * @return Descripción del evento o null si no hay evento
     */
    public String generarEventoEspecial() {
        double probabilidad = random.nextDouble();
        
        if (probabilidad < 0.05) { // 5% probabilidad
            return "🎪 Evento especial: Llegada de grupo turístico";
        } else if (probabilidad < 0.08) { // 3% probabilidad adicional
            return "🌧️ Evento especial: Lluvia ligera - menos entradas";
        } else if (probabilidad < 0.10) { // 2% probabilidad adicional
            return "☀️ Evento especial: Día soleado - más entradas";
        }
        
        return null; // Sin evento especial
    }
    
    /**
     * 📈 Calcular estadísticas de eficiencia del generador
     * 
     * @param operacionesGeneradas Total de operaciones generadas
     * @param tiempoTotal Tiempo total de simulación
     * @return String con estadísticas de eficiencia
     */
    public String calcularEstadisticasEficiencia(int operacionesGeneradas, long tiempoTotal) {
        double operacionesPorSegundo = (double) operacionesGeneradas / (tiempoTotal / 1000.0);
        
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== 📈 EFICIENCIA DEL GENERADOR ===\n");
        stats.append(String.format("⚡ Operaciones generadas: %d%n", operacionesGeneradas));
        stats.append(String.format("⏱️ Tiempo total: %.2f segundos%n", tiempoTotal / 1000.0));
        stats.append(String.format("📊 Operaciones/segundo: %.2f%n", operacionesPorSegundo));
        
        return stats.toString();
    }
    
    /**
     * 🔄 Enum para tipos de hora en el jardín
     */
    public enum TipoHora {
        PICO("🔥 Hora Pico"),
        NORMAL("⏰ Hora Normal"), 
        VALLE("🌙 Hora Valle");
        
        private final String descripcion;
        
        TipoHora(String descripcion) {
            this.descripcion = descripcion;
        }
        
        @Override
        public String toString() {
            return descripcion;
        }
    }
}
