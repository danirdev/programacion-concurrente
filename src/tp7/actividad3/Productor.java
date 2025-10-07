package tp7.actividad3;

import java.util.Random;

/**
 * 🏭 Productor - Thread que produce elementos para el buffer compartido
 * 
 * Esta clase implementa el lado productor del problema clásico Productor-Consumidor.
 * Genera productos de forma continua y los coloca en el buffer compartido
 * utilizando semáforos para sincronización segura.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Productor extends Thread {
    
    // 🏷️ Identificación del productor
    private final String productorId;
    
    // 🔄 Referencia al buffer compartido
    private final BufferCompartido buffer;
    
    // ⚙️ Configuración de producción
    private final int elementosAProducir;
    private final int tiempoMinProduccion;
    private final int tiempoMaxProduccion;
    
    // 🎲 Generador de números aleatorios
    private final Random random;
    
    // 📊 Estadísticas del productor
    private int elementosProducidos;
    private long tiempoTotalProduccion;
    private long tiempoTotalEspera;
    private boolean activo;
    
    // 🏭 Configuración de productos
    private final String[] tiposProductos = {
        "DOCUMENTO", "IMAGEN", "VIDEO", "AUDIO", "DATOS", "REPORTE", "MENSAJE", "ARCHIVO"
    };
    
    /**
     * 🏗️ Constructor del Productor
     * 
     * @param productorId Identificador único del productor
     * @param buffer Buffer compartido donde colocar productos
     * @param elementosAProducir Número total de elementos a producir
     * @param tiempoMinProduccion Tiempo mínimo de producción por elemento (ms)
     * @param tiempoMaxProduccion Tiempo máximo de producción por elemento (ms)
     */
    public Productor(String productorId, BufferCompartido buffer, int elementosAProducir, 
                    int tiempoMinProduccion, int tiempoMaxProduccion) {
        this.productorId = productorId;
        this.buffer = buffer;
        this.elementosAProducir = elementosAProducir;
        this.tiempoMinProduccion = tiempoMinProduccion;
        this.tiempoMaxProduccion = tiempoMaxProduccion;
        
        this.random = new Random();
        this.elementosProducidos = 0;
        this.tiempoTotalProduccion = 0;
        this.tiempoTotalEspera = 0;
        this.activo = true;
        
        // 🏷️ Configurar nombre del thread
        this.setName("Productor-" + productorId);
        
        System.out.printf("🏭 %s inicializado - Elementos: %d, Tiempo: %d-%dms%n", 
                         productorId, elementosAProducir, tiempoMinProduccion, tiempoMaxProduccion);
    }
    
    /**
     * 🏃‍♂️ Método principal del thread productor
     */
    @Override
    public void run() {
        System.out.printf("▶️ %s iniciado - Thread: %s%n", productorId, Thread.currentThread().getName());
        
        long tiempoInicioTotal = System.currentTimeMillis();
        
        try {
            for (int i = 1; i <= elementosAProducir && activo && !Thread.currentThread().isInterrupted(); i++) {
                
                // 🏭 Producir elemento
                Producto producto = producirElemento(i);
                
                // 📦 Colocar en buffer
                colocarEnBuffer(producto);
                
                elementosProducidos++;
                
                // 😴 Pausa entre producciones (simular tiempo de trabajo)
                if (i < elementosAProducir) {
                    pausarEntreProduccion();
                }
            }
            
        } catch (InterruptedException e) {
            System.out.printf("⚠️ %s interrumpido%n", productorId);
            Thread.currentThread().interrupt();
        } finally {
            activo = false;
            tiempoTotalProduccion = System.currentTimeMillis() - tiempoInicioTotal;
            System.out.printf("🛑 %s finalizado - Tiempo total: %dms%n", productorId, tiempoTotalProduccion);
            mostrarEstadisticasProductor();
        }
    }
    
    /**
     * 🏭 Producir un elemento (simular proceso de creación)
     * 
     * @param numeroElemento Número secuencial del elemento
     * @return Producto creado
     * @throws InterruptedException Si el thread es interrumpido
     */
    private Producto producirElemento(int numeroElemento) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        // 🎲 Generar tiempo de producción aleatorio
        int tiempoProduccion = tiempoMinProduccion + 
                              random.nextInt(tiempoMaxProduccion - tiempoMinProduccion + 1);
        
        System.out.printf("[%s] 🔧 Produciendo elemento #%d (tiempo estimado: %dms)%n", 
                         productorId, numeroElemento, tiempoProduccion);
        
        // ⏱️ Simular tiempo de producción
        Thread.sleep(tiempoProduccion);
        
        // 🎲 Generar producto con características aleatorias
        String tipoProducto = tiposProductos[random.nextInt(tiposProductos.length)];
        String nombreProducto = String.format("%s_%s_%03d", productorId, tipoProducto, numeroElemento);
        
        // 📦 Crear datos del producto (simulados)
        Object datosProducto = generarDatosProducto(tipoProducto, numeroElemento);
        
        Producto producto = new Producto(
            generarIdUnico(numeroElemento), 
            nombreProducto, 
            tipoProducto, 
            datosProducto
        );
        
        long tiempoReal = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("[%s] ✅ Elemento #%d producido - Tiempo real: %dms%n", 
                         productorId, numeroElemento, tiempoReal);
        
        return producto;
    }
    
    /**
     * 📦 Colocar producto en el buffer compartido
     * 
     * @param producto Producto a colocar
     * @throws InterruptedException Si el thread es interrumpido
     */
    private void colocarEnBuffer(Producto producto) throws InterruptedException {
        long tiempoInicio = System.currentTimeMillis();
        
        System.out.printf("[%s] 📤 Intentando colocar producto #%d en buffer%n", 
                         productorId, producto.getId());
        
        // 🔄 Usar el buffer compartido (con semáforos)
        buffer.poner(producto, productorId);
        
        long tiempoEspera = System.currentTimeMillis() - tiempoInicio;
        tiempoTotalEspera += tiempoEspera;
        
        System.out.printf("[%s] ✅ Producto #%d colocado exitosamente%n", 
                         productorId, producto.getId());
    }
    
    /**
     * 😴 Pausar entre producciones
     * 
     * @throws InterruptedException Si el thread es interrumpido
     */
    private void pausarEntreProduccion() throws InterruptedException {
        // Pausa corta entre producciones (10-50ms)
        int pausaCorta = 10 + random.nextInt(40);
        Thread.sleep(pausaCorta);
    }
    
    /**
     * 🎲 Generar datos simulados para el producto
     * 
     * @param tipo Tipo de producto
     * @param numero Número del elemento
     * @return Datos simulados
     */
    private Object generarDatosProducto(String tipo, int numero) {
        return switch (tipo) {
            case "DOCUMENTO" -> String.format("Contenido del documento %d generado por %s", numero, productorId);
            case "IMAGEN" -> new byte[1024 + random.nextInt(2048)]; // Simular imagen
            case "VIDEO" -> new byte[5120 + random.nextInt(10240)]; // Simular video
            case "AUDIO" -> new byte[512 + random.nextInt(1024)]; // Simular audio
            case "DATOS" -> random.nextDouble() * 1000; // Datos numéricos
            case "REPORTE" -> String.format("Reporte #%d - Estado: %s", numero, 
                                          random.nextBoolean() ? "COMPLETO" : "PARCIAL");
            case "MENSAJE" -> String.format("Mensaje %d desde %s - Prioridad: %d", 
                                          numero, productorId, random.nextInt(5) + 1);
            default -> String.format("Archivo genérico %d", numero);
        };
    }
    
    /**
     * 🔢 Generar ID único para el producto
     * 
     * @param numeroElemento Número secuencial
     * @return ID único
     */
    private int generarIdUnico(int numeroElemento) {
        // Combinar hash del productor con número de elemento
        return Math.abs(productorId.hashCode()) * 1000 + numeroElemento;
    }
    
    /**
     * 🛑 Detener el productor de forma segura
     */
    public void detener() {
        activo = false;
        this.interrupt();
        System.out.printf("🔴 %s marcado para detener%n", productorId);
    }
    
    /**
     * 📊 Mostrar estadísticas específicas del productor
     */
    private void mostrarEstadisticasProductor() {
        System.out.printf("\n=== 📊 ESTADÍSTICAS %s ===\n", productorId);
        System.out.printf("🏭 Elementos producidos: %d/%d%n", elementosProducidos, elementosAProducir);
        System.out.printf("⏱️ Tiempo total producción: %dms%n", tiempoTotalProduccion);
        System.out.printf("⏳ Tiempo total espera: %dms%n", tiempoTotalEspera);
        
        if (elementosProducidos > 0) {
            System.out.printf("📊 Tiempo promedio por elemento: %.2fms%n", 
                             (double) tiempoTotalProduccion / elementosProducidos);
            System.out.printf("⏱️ Tiempo promedio espera: %.2fms%n", 
                             (double) tiempoTotalEspera / elementosProducidos);
        }
        
        if (tiempoTotalProduccion > 0) {
            double throughput = (elementosProducidos * 1000.0) / tiempoTotalProduccion;
            System.out.printf("🚀 Throughput: %.2f elementos/segundo%n", throughput);
            
            double eficiencia = ((double) (tiempoTotalProduccion - tiempoTotalEspera) / tiempoTotalProduccion) * 100;
            System.out.printf("⚡ Eficiencia: %.1f%% (tiempo productivo vs total)%n", eficiencia);
        }
    }
    
    /**
     * 📊 Obtener estadísticas del productor
     * 
     * @return Array con [producidos, tiempoTotal, tiempoEspera, throughput, eficiencia]
     */
    public double[] getEstadisticas() {
        double throughput = tiempoTotalProduccion > 0 ? 
            (elementosProducidos * 1000.0) / tiempoTotalProduccion : 0;
        double eficiencia = tiempoTotalProduccion > 0 ? 
            ((double) (tiempoTotalProduccion - tiempoTotalEspera) / tiempoTotalProduccion) * 100 : 0;
        
        return new double[]{
            elementosProducidos,
            tiempoTotalProduccion,
            tiempoTotalEspera,
            throughput,
            eficiencia
        };
    }
    
    /**
     * 🏷️ Obtener identificador del productor
     * 
     * @return ID del productor
     */
    public String getProductorId() {
        return productorId;
    }
    
    /**
     * ✅ Verificar si el productor está activo
     * 
     * @return true si está activo, false si está detenido
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * 🎯 Obtener porcentaje de completitud
     * 
     * @return Porcentaje de elementos producidos (0-100)
     */
    public double getPorcentajeCompletitud() {
        return elementosAProducir > 0 ? (elementosProducidos * 100.0) / elementosAProducir : 0;
    }
    
    /**
     * 📝 Representación en string del productor
     * 
     * @return Información básica del productor
     */
    @Override
    public String toString() {
        return String.format("Productor{id='%s', producidos=%d/%d, activo=%s, eficiencia=%.1f%%}", 
                           productorId, elementosProducidos, elementosAProducir, activo, 
                           getEstadisticas()[4]);
    }
}
