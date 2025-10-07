package tp7.actividad4;

/**
 * 📦 MonitorBuffer - Monitor para buffer compartido (Productor-Consumidor)
 * 
 * Esta clase implementa un monitor que encapsula un buffer compartido
 * para resolver el problema Productor-Consumidor con exclusión mutua
 * implícita y variables de condición para sincronización.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class MonitorBuffer extends Monitor {
    
    // 📦 Buffer interno
    private final Object[] buffer;
    private final int capacidad;
    
    // 🔄 Índices para buffer circular
    private int indiceEntrada;
    private int indiceSalida;
    private int elementosActuales;
    
    // 🔄 Variables de condición
    private final VariableCondicion noLleno;    // Para productores
    private final VariableCondicion noVacio;    // Para consumidores
    
    // 📊 Estadísticas específicas del buffer
    private int totalDepositados;
    private int totalExtraidos;
    
    /**
     * 🏗️ Constructor del MonitorBuffer
     * 
     * @param capacidad Tamaño máximo del buffer
     */
    public MonitorBuffer(int capacidad) {
        super("BufferCompartido");
        
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        
        this.capacidad = capacidad;
        this.buffer = new Object[capacidad];
        this.indiceEntrada = 0;
        this.indiceSalida = 0;
        this.elementosActuales = 0;
        
        // 🔄 Crear variables de condición
        this.noLleno = new VariableCondicion("NoLleno");
        this.noVacio = new VariableCondicion("NoVacio");
        
        this.totalDepositados = 0;
        this.totalExtraidos = 0;
        
        inicializarEstado();
        
        System.out.printf("📦 MonitorBuffer creado - Capacidad: %d elementos%n", capacidad);
    }
    
    /**
     * 🏭 Depositar elemento en el buffer (operación del Productor)
     * Exclusión mutua implícita garantizada por el monitor
     * 
     * @param elemento Elemento a depositar
     * @param productorId ID del productor
     */
    public void depositar(Object elemento, String productorId) {
        ejecutarProcedimiento(productorId, () -> {
            try {
                // ⏳ Esperar mientras el buffer esté lleno
                while (elementosActuales == capacidad) {
                    System.out.printf("[%s] ⏳ Buffer lleno, esperando espacio...%n", productorId);
                    noLleno.esperar(productorId, getMutexSemaforo());
                }
                
                // 📦 Depositar elemento
                buffer[indiceEntrada] = elemento;
                indiceEntrada = (indiceEntrada + 1) % capacidad;
                elementosActuales++;
                totalDepositados++;
                
                System.out.printf("[%s] ✅ Elemento depositado - Buffer: %d/%d%n", 
                                 productorId, elementosActuales, capacidad);
                
                // 🔔 Señalar que el buffer no está vacío
                noVacio.señalar(productorId);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Depósito interrumpido", e);
            }
        });
    }
    
    /**
     * 👤 Extraer elemento del buffer (operación del Consumidor)
     * Exclusión mutua implícita garantizada por el monitor
     * 
     * @param consumidorId ID del consumidor
     * @return Elemento extraído del buffer
     */
    public Object extraer(String consumidorId) {
        return ejecutarFuncion(consumidorId, () -> {
            try {
                // ⏳ Esperar mientras el buffer esté vacío
                while (elementosActuales == 0) {
                    System.out.printf("[%s] ⏳ Buffer vacío, esperando elementos...%n", consumidorId);
                    noVacio.esperar(consumidorId, getMutexSemaforo());
                }
                
                // 📦 Extraer elemento
                Object elemento = buffer[indiceSalida];
                buffer[indiceSalida] = null; // Limpiar referencia
                indiceSalida = (indiceSalida + 1) % capacidad;
                elementosActuales--;
                totalExtraidos++;
                
                System.out.printf("[%s] ✅ Elemento extraído - Buffer: %d/%d%n", 
                                 consumidorId, elementosActuales, capacidad);
                
                // 🔔 Señalar que el buffer no está lleno
                noLleno.señalar(consumidorId);
                
                return elemento;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Extracción interrumpida", e);
            }
        });
    }
    
    /**
     * 📊 Obtener número de elementos actuales (lectura sincronizada)
     * 
     * @param procesoId ID del proceso que consulta
     * @return Número de elementos en el buffer
     */
    public int getElementosActuales(String procesoId) {
        return ejecutarFuncion(procesoId, () -> elementosActuales);
    }
    
    /**
     * 📦 Verificar si el buffer está vacío (lectura sincronizada)
     * 
     * @param procesoId ID del proceso que consulta
     * @return true si el buffer está vacío
     */
    public boolean estaVacio(String procesoId) {
        return ejecutarFuncion(procesoId, () -> elementosActuales == 0);
    }
    
    /**
     * 📦 Verificar si el buffer está lleno (lectura sincronizada)
     * 
     * @param procesoId ID del proceso que consulta
     * @return true si el buffer está lleno
     */
    public boolean estaLleno(String procesoId) {
        return ejecutarFuncion(procesoId, () -> elementosActuales == capacidad);
    }
    
    /**
     * 📊 Obtener estadísticas específicas del buffer
     * 
     * @return String con estadísticas del buffer
     */
    public String getEstadisticasBuffer() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 📦 ESTADÍSTICAS MONITOR BUFFER ===\n"));
        stats.append(String.format("📦 Capacidad: %d elementos%n", capacidad));
        stats.append(String.format("📊 Elementos actuales: %d%n", elementosActuales));
        stats.append(String.format("🏭 Total depositados: %d%n", totalDepositados));
        stats.append(String.format("👤 Total extraídos: %d%n", totalExtraidos));
        stats.append(String.format("📈 Utilización: %.1f%%%n", 
                                  (double) elementosActuales / capacidad * 100));
        
        // Estadísticas de variables de condición
        stats.append(noLleno.getEstadisticas());
        stats.append(noVacio.getEstadisticas());
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad específica del buffer
     * 
     * @return true si el buffer está íntegro
     */
    public boolean verificarIntegridadBuffer() {
        // Verificar integridad base del monitor
        boolean integridadBase = verificarIntegridad();
        
        // Verificar índices válidos
        boolean indicesValidos = (indiceEntrada >= 0 && indiceEntrada < capacidad) &&
                               (indiceSalida >= 0 && indiceSalida < capacidad);
        
        // Verificar contador de elementos
        boolean contadorValido = elementosActuales >= 0 && elementosActuales <= capacidad;
        
        // Verificar consistencia de estadísticas
        boolean estadisticasConsistentes = (totalDepositados - totalExtraidos) == elementosActuales;
        
        // Verificar variables de condición
        boolean variablesIntegras = noLleno.verificarIntegridad() && noVacio.verificarIntegridad();
        
        boolean integro = integridadBase && indicesValidos && contadorValido && 
                         estadisticasConsistentes && variablesIntegras;
        
        if (!integro) {
            System.err.printf("❌ INTEGRIDAD BUFFER COMPROMETIDA:%n");
            System.err.printf("   Índices válidos: %s%n", indicesValidos);
            System.err.printf("   Contador válido: %s (%d)%n", contadorValido, elementosActuales);
            System.err.printf("   Estadísticas consistentes: %s%n", estadisticasConsistentes);
            System.err.printf("   Variables íntegras: %s%n", variablesIntegras);
        }
        
        return integro;
    }
    
    // 🎯 Implementación de métodos abstractos
    
    @Override
    protected void inicializarEstado() {
        System.out.printf("🔧 Inicializando estado del MonitorBuffer%n");
        // Estado ya inicializado en constructor
    }
    
    @Override
    public String getDescripcion() {
        return String.format("Monitor Buffer Compartido (Capacidad: %d, Actual: %d)", 
                           capacidad, elementosActuales);
    }
    
    @Override
    public boolean ejecutarPrueba(String procesoId) {
        System.out.printf("🧪 Ejecutando prueba del MonitorBuffer con proceso %s%n", procesoId);
        
        try {
            // Prueba básica: depositar y extraer
            String elementoPrueba = "ElementoPrueba_" + procesoId;
            
            depositar(elementoPrueba, procesoId);
            Object elementoExtraido = extraer(procesoId);
            
            boolean pruebaExitosa = elementoPrueba.equals(elementoExtraido);
            
            System.out.printf("🧪 Prueba MonitorBuffer: %s%n", 
                             pruebaExitosa ? "✅ EXITOSA" : "❌ FALLIDA");
            
            return pruebaExitosa;
            
        } catch (Exception e) {
            System.err.printf("❌ Error en prueba MonitorBuffer: %s%n", e.getMessage());
            return false;
        }
    }
    
    // 🔧 Getters específicos
    
    public int getCapacidad() { return capacidad; }
    
    public int getTotalDepositados() { return totalDepositados; }
    
    public int getTotalExtraidos() { return totalExtraidos; }
    
    public VariableCondicion getVariableNoLleno() { return noLleno; }
    
    public VariableCondicion getVariableNoVacio() { return noVacio; }
    
    /**
     * 📝 Representación en string del monitor buffer
     * 
     * @return Información del buffer
     */
    @Override
    public String toString() {
        return String.format("MonitorBuffer{capacidad=%d, elementos=%d/%d, depositados=%d, extraídos=%d}", 
                           capacidad, elementosActuales, capacidad, totalDepositados, totalExtraidos);
    }
}
