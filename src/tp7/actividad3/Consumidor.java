package tp7.actividad3;

import java.util.Random;

/**
 * 👤 Consumidor - Thread que consume elementos del buffer compartido
 * 
 * Esta clase implementa el lado consumidor del problema clásico Productor-Consumidor.
 * Extrae productos del buffer compartido y los procesa utilizando semáforos
 * para sincronización segura con los productores.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Consumidor extends Thread {
    
    // 🏷️ Identificación del consumidor
    private final String consumidorId;
    
    // 🔄 Referencia al buffer compartido
    private final BufferCompartido buffer;
    
    // ⚙️ Configuración de consumo
    private final int elementosAConsumir;
    private final int tiempoMinConsumo;
    private final int tiempoMaxConsumo;
    
    // 🎲 Generador de números aleatorios
    private final Random random;
    
    // 📊 Estadísticas del consumidor
    private int elementosConsumidos;
    private long tiempoTotalConsumo;
    private long tiempoTotalEspera;
    private long tiempoTotalProcesamiento;
    private boolean activo;
    
    /**
     * 🏗️ Constructor del Consumidor
     * 
     * @param consumidorId Identificador único del consumidor
     * @param buffer Buffer compartido de donde extraer productos
     * @param elementosAConsumir Número total de elementos a consumir
     * @param tiempoMinConsumo Tiempo mínimo de procesamiento por elemento (ms)
     * @param tiempoMaxConsumo Tiempo máximo de procesamiento por elemento (ms)
     */
    public Consumidor(String consumidorId, BufferCompartido buffer, int elementosAConsumir, 
                     int tiempoMinConsumo, int tiempoMaxConsumo) {
        this.consumidorId = consumidorId;
        this.buffer = buffer;
        this.elementosAConsumir = elementosAConsumir;
        this.tiempoMinConsumo = tiempoMinConsumo;
        this.tiempoMaxConsumo = tiempoMaxConsumo;
        
        this.random = new Random();
        this.elementosConsumidos = 0;
        this.tiempoTotalConsumo = 0;
        this.tiempoTotalEspera = 0;
        this.tiempoTotalProcesamiento = 0;
        this.activo = true;
        
        // 🏷️ Configurar nombre del thread
        this.setName("Consumidor-" + consumidorId);
        
        System.out.printf("👤 %s inicializado - Elementos: %d, Tiempo: %d-%dms%n", 
                         consumidorId, elementosAConsumir, tiempoMinConsumo, tiempoMaxConsumo);
    }
    
    /**
     * 🏃‍♂️ Método principal del thread consumidor
     */
    @Override
    public void run() {
        System.out.printf("▶️ %s iniciado - Thread: %s%n", consumidorId, Thread.currentThread().getName());
        
        long tiempoInicioTotal = System.currentTimeMillis();
        
        try {
            for (int i = 1; i <= elementosAConsumir && activo && !Thread.currentThread().isInterrupted(); i++) {
                
                // 📦 Extraer elemento del buffer
                Producto producto = extraerDelBuffer();
                
                if (producto != null) {
                    // 🔧 Procesar elemento
                    procesarElemento(producto, i);
                    elementosConsumidos++;
                } else {
                    System.out.printf("[%s] ⚠️ No se pudo extraer elemento #%d%n", consumidorId, i);
                }
                
                // 😴 Pausa entre consumos (simular tiempo entre procesamiento)
                if (i < elementosAConsumir) {
                    pausarEntreConsumo();
                }
            }
            
        } catch (InterruptedException e) {
            System.out.printf("⚠️ %s interrumpido%n", consumidorId);
            Thread.currentThread().interrupt();
        } finally {
            activo = false;
            tiempoTotalConsumo = System.currentTimeMillis() - tiempoInicioTotal;
            System.out.printf("🛑 %s finalizado - Tiempo total: %dms%n", consumidorId, tiempoTotalConsumo);
            mostrarEstadisticasConsumidor();
        }
    }
    
    /**
     * 📦 Extraer producto del buffer compartido
     * 
     * @return Producto extraído o null si hay error
     * @throws InterruptedException Si el thread es interrumpido
     */
    private Producto extraerDelBuffer() throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        System.out.printf("[%s] 📥 Intentando extraer elemento del buffer%n", consumidorId);
        
        // 🔄 Usar el buffer compartido (con semáforos)
        Producto producto = buffer.tomar(consumidorId);
        
        long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
        tiempoTotalEspera += tiempoEspera;
        
        if (producto != null) {
            System.out.printf("[%s] ✅ Producto #%d extraído exitosamente%n", 
                             consumidorId, producto.getId());
        }
        
        return producto;
    }
    
    /**
     * 🔧 Procesar elemento extraído del buffer
     * 
     * @param producto Producto a procesar
     * @param numeroElemento Número secuencial del elemento procesado
     * @throws InterruptedException Si el thread es interrumpido
     */
    private void procesarElemento(Producto producto, int numeroElemento) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        // 🎲 Generar tiempo de procesamiento aleatorio
        int tiempoProcesamiento = tiempoMinConsumo + 
                                 random.nextInt(tiempoMaxConsumo - tiempoMinConsumo + 1);
        
        System.out.printf("[%s] 🔧 Procesando %s (tiempo estimado: %dms)%n", 
                         consumidorId, producto.getNombre(), tiempoProcesamiento);
        
        // ⏱️ Simular tiempo de procesamiento
        Thread.sleep(tiempoProcesamiento);
        
        // 🔍 Procesar según el tipo de producto
        String resultadoProcesamiento = procesarSegunTipo(producto);
        
        long tiempoReal = System.currentTimeMillis() - tiempoInicio;
        tiempoTotalProcesamiento += tiempoReal;
        
        System.out.printf("[%s] ✅ Elemento #%d procesado - %s - Tiempo: %dms%n", 
                         consumidorId, numeroElemento, resultadoProcesamiento, tiempoReal);
        
        // 📊 Mostrar información del producto procesado
        if (numeroElemento % 10 == 0) { // Cada 10 elementos
            System.out.printf("[%s] 📊 Progreso: %d/%d elementos procesados%n", 
                             consumidorId, numeroElemento, elementosAConsumir);
        }
    }
    
    /**
     * 🔍 Procesar producto según su tipo
     * 
     * @param producto Producto a procesar
     * @return Resultado del procesamiento
     */
    private String procesarSegunTipo(Producto producto) {
        return switch (producto.getTipo()) {
            case "DOCUMENTO" -> "Documento indexado y archivado";
            case "IMAGEN" -> String.format("Imagen procesada (%d bytes)", producto.getTamanio());
            case "VIDEO" -> String.format("Video codificado (%d bytes)", producto.getTamanio());
            case "AUDIO" -> String.format("Audio normalizado (%d bytes)", producto.getTamanio());
            case "DATOS" -> "Datos analizados y validados";
            case "REPORTE" -> "Reporte generado y distribuido";
            case "MENSAJE" -> "Mensaje entregado al destinatario";
            case "ARCHIVO" -> "Archivo comprimido y almacenado";
            default -> "Procesamiento genérico completado";
        };
    }
    
    /**
     * 😴 Pausar entre consumos
     * 
     * @throws InterruptedException Si el thread es interrumpido
     */
    private void pausarEntreConsumo() throws InterruptedException {
        // Pausa corta entre consumos (5-30ms)
        int pausaCorta = 5 + random.nextInt(25);
        Thread.sleep(pausaCorta);
    }
    
    /**
     * 🛑 Detener el consumidor de forma segura
     */
    public void detener() {
        activo = false;
        this.interrupt();
        System.out.printf("🔴 %s marcado para detener%n", consumidorId);
    }
    
    /**
     * 📊 Mostrar estadísticas específicas del consumidor
     */
    private void mostrarEstadisticasConsumidor() {
        System.out.printf("\n=== 📊 ESTADÍSTICAS %s ===\n", consumidorId);
        System.out.printf("👤 Elementos consumidos: %d/%d%n", elementosConsumidos, elementosAConsumir);
        System.out.printf("⏱️ Tiempo total consumo: %dms%n", tiempoTotalConsumo);
        System.out.printf("⏳ Tiempo total espera: %dms%n", tiempoTotalEspera);
        System.out.printf("🔧 Tiempo total procesamiento: %dms%n", tiempoTotalProcesamiento);
        
        if (elementosConsumidos > 0) {
            System.out.printf("📊 Tiempo promedio por elemento: %.2fms%n", 
                             (double) tiempoTotalConsumo / elementosConsumidos);
            System.out.printf("⏱️ Tiempo promedio espera: %.2fms%n", 
                             (double) tiempoTotalEspera / elementosConsumidos);
            System.out.printf("🔧 Tiempo promedio procesamiento: %.2fms%n", 
                             (double) tiempoTotalProcesamiento / elementosConsumidos);
        }
        
        if (tiempoTotalConsumo > 0) {
            double throughput = (elementosConsumidos * 1000.0) / tiempoTotalConsumo;
            System.out.printf("🚀 Throughput: %.2f elementos/segundo%n", throughput);
            
            double eficiencia = ((double) tiempoTotalProcesamiento / tiempoTotalConsumo) * 100;
            System.out.printf("⚡ Eficiencia: %.1f%% (tiempo procesamiento vs total)%n", eficiencia);
            
            double utilizacion = ((double) (tiempoTotalConsumo - tiempoTotalEspera) / tiempoTotalConsumo) * 100;
            System.out.printf("📈 Utilización: %.1f%% (tiempo activo vs total)%n", utilizacion);
        }
    }
    
    /**
     * 📊 Obtener estadísticas del consumidor
     * 
     * @return Array con [consumidos, tiempoTotal, tiempoEspera, tiempoProcesamiento, throughput, eficiencia]
     */
    public double[] getEstadisticas() {
        double throughput = tiempoTotalConsumo > 0 ? 
            (elementosConsumidos * 1000.0) / tiempoTotalConsumo : 0;
        double eficiencia = tiempoTotalConsumo > 0 ? 
            ((double) tiempoTotalProcesamiento / tiempoTotalConsumo) * 100 : 0;
        
        return new double[]{
            elementosConsumidos,
            tiempoTotalConsumo,
            tiempoTotalEspera,
            tiempoTotalProcesamiento,
            throughput,
            eficiencia
        };
    }
    
    /**
     * 🏷️ Obtener identificador del consumidor
     * 
     * @return ID del consumidor
     */
    public String getConsumidorId() {
        return consumidorId;
    }
    
    /**
     * ✅ Verificar si el consumidor está activo
     * 
     * @return true si está activo, false si está detenido
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * 🎯 Obtener porcentaje de completitud
     * 
     * @return Porcentaje de elementos consumidos (0-100)
     */
    public double getPorcentajeCompletitud() {
        return elementosAConsumir > 0 ? (elementosConsumidos * 100.0) / elementosAConsumir : 0;
    }
    
    /**
     * ⏱️ Obtener tiempo promedio de procesamiento
     * 
     * @return Tiempo promedio en milisegundos
     */
    public double getTiempoPromedioProcesamiento() {
        return elementosConsumidos > 0 ? (double) tiempoTotalProcesamiento / elementosConsumidos : 0;
    }
    
    /**
     * 📈 Obtener utilización del consumidor
     * 
     * @return Porcentaje de utilización (tiempo activo vs total)
     */
    public double getUtilizacion() {
        return tiempoTotalConsumo > 0 ? 
            ((double) (tiempoTotalConsumo - tiempoTotalEspera) / tiempoTotalConsumo) * 100 : 0;
    }
    
    /**
     * 📝 Representación en string del consumidor
     * 
     * @return Información básica del consumidor
     */
    @Override
    public String toString() {
        return String.format("Consumidor{id='%s', consumidos=%d/%d, activo=%s, eficiencia=%.1f%%}", 
                           consumidorId, elementosConsumidos, elementosAConsumir, activo, 
                           getEstadisticas()[5]);
    }
}
