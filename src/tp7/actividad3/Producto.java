package tp7.actividad3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 📦 Producto - Clase que representa un elemento del buffer
 * 
 * Esta clase encapsula la información de cada elemento que es producido
 * y consumido en el sistema Productor-Consumidor. Incluye metadatos
 * para análisis y seguimiento del flujo de datos.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class Producto {
    
    // 🏷️ Identificación del producto
    private final int id;
    private final String nombre;
    private final String tipo;
    
    // ⏱️ Timestamps para análisis de rendimiento
    private final LocalDateTime tiempoCreacion;
    private LocalDateTime tiempoProduccion;
    private LocalDateTime tiempoConsumo;
    
    // 🏭 Información del productor
    private String productorId;
    private int numeroSecuencia;
    
    // 👤 Información del consumidor
    private String consumidorId;
    
    // 📊 Datos del producto
    private final Object datos;
    private int tamanio;
    private String estado;
    
    /**
     * 🏗️ Constructor básico del Producto
     * 
     * @param id Identificador único del producto
     * @param nombre Nombre descriptivo del producto
     * @param datos Datos contenidos en el producto
     */
    public Producto(int id, String nombre, Object datos) {
        this.id = id;
        this.nombre = nombre;
        this.datos = datos;
        this.tipo = determinarTipo(datos);
        this.tiempoCreacion = LocalDateTime.now();
        this.tamanio = calcularTamanio(datos);
        this.estado = "CREADO";
        
        System.out.printf("📦 Producto #%d '%s' creado - Tipo: %s, Tamaño: %d bytes%n", 
                         id, nombre, tipo, tamanio);
    }
    
    /**
     * 🏗️ Constructor completo del Producto
     * 
     * @param id Identificador único
     * @param nombre Nombre del producto
     * @param tipo Tipo de producto
     * @param datos Datos del producto
     */
    public Producto(int id, String nombre, String tipo, Object datos) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.datos = datos;
        this.tiempoCreacion = LocalDateTime.now();
        this.tamanio = calcularTamanio(datos);
        this.estado = "CREADO";
    }
    
    /**
     * 🏭 Marcar producto como producido
     * 
     * @param productorId ID del productor que lo creó
     * @param numeroSecuencia Número de secuencia en la producción
     */
    public void marcarComoProducido(String productorId, int numeroSecuencia) {
        this.productorId = productorId;
        this.numeroSecuencia = numeroSecuencia;
        this.tiempoProduccion = LocalDateTime.now();
        this.estado = "PRODUCIDO";
        
        System.out.printf("[%s] 🏭 Producto #%d producido - Secuencia: %d%n", 
                         productorId, id, numeroSecuencia);
    }
    
    /**
     * 👤 Marcar producto como consumido
     * 
     * @param consumidorId ID del consumidor que lo procesó
     */
    public void marcarComoConsumido(String consumidorId) {
        this.consumidorId = consumidorId;
        this.tiempoConsumo = LocalDateTime.now();
        this.estado = "CONSUMIDO";
        
        long tiempoVida = calcularTiempoVida();
        System.out.printf("[%s] 👤 Producto #%d consumido - Tiempo de vida: %dms%n", 
                         consumidorId, id, tiempoVida);
    }
    
    /**
     * ⏱️ Calcular tiempo de vida del producto
     * 
     * @return Tiempo en milisegundos desde creación hasta consumo
     */
    public long calcularTiempoVida() {
        if (tiempoConsumo == null) {
            return java.time.Duration.between(tiempoCreacion, LocalDateTime.now()).toMillis();
        }
        return java.time.Duration.between(tiempoCreacion, tiempoConsumo).toMillis();
    }
    
    /**
     * ⏱️ Calcular tiempo en buffer
     * 
     * @return Tiempo en milisegundos desde producción hasta consumo
     */
    public long calcularTiempoEnBuffer() {
        if (tiempoProduccion == null || tiempoConsumo == null) {
            return 0;
        }
        return java.time.Duration.between(tiempoProduccion, tiempoConsumo).toMillis();
    }
    
    /**
     * 🔍 Determinar tipo de datos automáticamente
     * 
     * @param datos Objeto de datos
     * @return Tipo como string
     */
    private String determinarTipo(Object datos) {
        if (datos == null) return "NULL";
        if (datos instanceof String) return "TEXTO";
        if (datos instanceof Number) return "NUMERICO";
        if (datos instanceof byte[]) return "BINARIO";
        return datos.getClass().getSimpleName().toUpperCase();
    }
    
    /**
     * 📏 Calcular tamaño aproximado del producto
     * 
     * @param datos Objeto de datos
     * @return Tamaño estimado en bytes
     */
    private int calcularTamanio(Object datos) {
        if (datos == null) return 0;
        if (datos instanceof String) return ((String) datos).length() * 2; // UTF-16
        if (datos instanceof Integer) return 4;
        if (datos instanceof Long) return 8;
        if (datos instanceof Double) return 8;
        if (datos instanceof byte[]) return ((byte[]) datos).length;
        return datos.toString().length() * 2; // Estimación
    }
    
    /**
     * 📊 Obtener información completa del producto
     * 
     * @return String con toda la información del producto
     */
    public String getInformacionCompleta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        
        StringBuilder info = new StringBuilder();
        info.append(String.format("📦 PRODUCTO #%d - %s%n", id, nombre));
        info.append(String.format("   🏷️ Tipo: %s, Tamaño: %d bytes%n", tipo, tamanio));
        info.append(String.format("   ⏱️ Creado: %s%n", tiempoCreacion.format(formatter)));
        
        if (tiempoProduccion != null) {
            info.append(String.format("   🏭 Producido: %s por %s (seq: %d)%n", 
                                    tiempoProduccion.format(formatter), productorId, numeroSecuencia));
        }
        
        if (tiempoConsumo != null) {
            info.append(String.format("   👤 Consumido: %s por %s%n", 
                                    tiempoConsumo.format(formatter), consumidorId));
            info.append(String.format("   ⏱️ Tiempo total: %dms, En buffer: %dms%n", 
                                    calcularTiempoVida(), calcularTiempoEnBuffer()));
        }
        
        info.append(String.format("   📊 Estado: %s%n", estado));
        
        return info.toString();
    }
    
    /**
     * 🔍 Verificar si el producto está completo (producido y consumido)
     * 
     * @return true si ha pasado por todo el ciclo
     */
    public boolean estaCompleto() {
        return tiempoProduccion != null && tiempoConsumo != null;
    }
    
    /**
     * ⚡ Obtener métricas de rendimiento
     * 
     * @return Array con [tiempoVida, tiempoEnBuffer, tamaño]
     */
    public double[] getMetricasRendimiento() {
        return new double[]{
            calcularTiempoVida(),
            calcularTiempoEnBuffer(),
            tamanio
        };
    }
    
    // 🔧 Getters y Setters
    
    public int getId() { return id; }
    
    public String getNombre() { return nombre; }
    
    public String getTipo() { return tipo; }
    
    public Object getDatos() { return datos; }
    
    public int getTamanio() { return tamanio; }
    
    public String getEstado() { return estado; }
    
    public LocalDateTime getTiempoCreacion() { return tiempoCreacion; }
    
    public LocalDateTime getTiempoProduccion() { return tiempoProduccion; }
    
    public LocalDateTime getTiempoConsumo() { return tiempoConsumo; }
    
    public String getProductorId() { return productorId; }
    
    public int getNumeroSecuencia() { return numeroSecuencia; }
    
    public String getConsumidorId() { return consumidorId; }
    
    /**
     * 📝 Representación en string del producto
     * 
     * @return Información básica del producto
     */
    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', tipo='%s', estado='%s', tamaño=%d}", 
                           id, nombre, tipo, estado, tamanio);
    }
    
    /**
     * ⚖️ Comparación de productos por ID
     * 
     * @param obj Objeto a comparar
     * @return true si son el mismo producto
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return id == producto.id;
    }
    
    /**
     * 🔢 Hash code basado en ID
     * 
     * @return Hash code del producto
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
