package tp7.actividad4;

/**
 * 🔢 MonitorContador - Monitor para contador compartido con exclusión mutua implícita
 * 
 * Esta clase implementa un monitor que encapsula un contador compartido
 * garantizando que todas las operaciones (incremento, decremento, lectura)
 * se realicen con exclusión mutua implícita.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class MonitorContador extends Monitor {
    
    // 🔢 Estado interno del contador
    private int valor;
    private final int valorMinimo;
    private final int valorMaximo;
    
    // 🔄 Variables de condición
    private final VariableCondicion noMinimo;    // Para esperar cuando valor > mínimo
    private final VariableCondicion noMaximo;    // Para esperar cuando valor < máximo
    
    // 📊 Estadísticas específicas del contador
    private int totalIncrementos;
    private int totalDecrementos;
    private int totalLecturas;
    private int valorMaximoAlcanzado;
    private int valorMinimoAlcanzado;
    
    /**
     * 🏗️ Constructor del MonitorContador
     * 
     * @param valorInicial Valor inicial del contador
     * @param valorMinimo Valor mínimo permitido
     * @param valorMaximo Valor máximo permitido
     */
    public MonitorContador(int valorInicial, int valorMinimo, int valorMaximo) {
        super("ContadorCompartido");
        
        if (valorMinimo > valorMaximo) {
            throw new IllegalArgumentException("Valor mínimo no puede ser mayor al máximo");
        }
        if (valorInicial < valorMinimo || valorInicial > valorMaximo) {
            throw new IllegalArgumentException("Valor inicial fuera del rango permitido");
        }
        
        this.valor = valorInicial;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        
        // 🔄 Crear variables de condición
        this.noMinimo = new VariableCondicion("NoMinimo");
        this.noMaximo = new VariableCondicion("NoMaximo");
        
        this.totalIncrementos = 0;
        this.totalDecrementos = 0;
        this.totalLecturas = 0;
        this.valorMaximoAlcanzado = valorInicial;
        this.valorMinimoAlcanzado = valorInicial;
        
        inicializarEstado();
        
        System.out.printf("🔢 MonitorContador creado - Valor: %d, Rango: [%d, %d]%n", 
                         valorInicial, valorMinimo, valorMaximo);
    }
    
    /**
     * 🏗️ Constructor simplificado (sin límites)
     * 
     * @param valorInicial Valor inicial del contador
     */
    public MonitorContador(int valorInicial) {
        this(valorInicial, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * ➕ Incrementar contador
     * Exclusión mutua implícita garantizada por el monitor
     * 
     * @param procesoId ID del proceso que incrementa
     * @return Nuevo valor del contador
     */
    public int incrementar(String procesoId) {
        return ejecutarFuncion(procesoId, () -> {
            try {
                // ⏳ Esperar mientras el contador esté en el máximo
                while (valor >= valorMaximo) {
                    System.out.printf("[%s] ⏳ Contador en máximo (%d), esperando...%n", procesoId, valorMaximo);
                    noMaximo.esperar(procesoId, getMutexSemaforo());
                }
                
                // ➕ Incrementar valor
                valor++;
                totalIncrementos++;
                
                // 📈 Actualizar estadísticas
                if (valor > valorMaximoAlcanzado) {
                    valorMaximoAlcanzado = valor;
                }
                
                System.out.printf("[%s] ➕ Contador incrementado: %d%n", procesoId, valor);
                
                // 🔔 Señalar que ya no estamos en el mínimo
                if (valor > valorMinimo) {
                    noMinimo.señalar(procesoId);
                }
                
                return valor;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Incremento interrumpido", e);
            }
        });
    }
    
    /**
     * ➖ Decrementar contador
     * Exclusión mutua implícita garantizada por el monitor
     * 
     * @param procesoId ID del proceso que decrementa
     * @return Nuevo valor del contador
     */
    public int decrementar(String procesoId) {
        return ejecutarFuncion(procesoId, () -> {
            try {
                // ⏳ Esperar mientras el contador esté en el mínimo
                while (valor <= valorMinimo) {
                    System.out.printf("[%s] ⏳ Contador en mínimo (%d), esperando...%n", procesoId, valorMinimo);
                    noMinimo.esperar(procesoId, getMutexSemaforo());
                }
                
                // ➖ Decrementar valor
                valor--;
                totalDecrementos++;
                
                // 📉 Actualizar estadísticas
                if (valor < valorMinimoAlcanzado) {
                    valorMinimoAlcanzado = valor;
                }
                
                System.out.printf("[%s] ➖ Contador decrementado: %d%n", procesoId, valor);
                
                // 🔔 Señalar que ya no estamos en el máximo
                if (valor < valorMaximo) {
                    noMaximo.señalar(procesoId);
                }
                
                return valor;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Decremento interrumpido", e);
            }
        });
    }
    
    /**
     * 📖 Obtener valor actual del contador (lectura sincronizada)
     * 
     * @param procesoId ID del proceso que lee
     * @return Valor actual del contador
     */
    public int obtenerValor(String procesoId) {
        return ejecutarFuncion(procesoId, () -> {
            totalLecturas++;
            System.out.printf("[%s] 📖 Leyendo contador: %d%n", procesoId, valor);
            return valor;
        });
    }
    
    /**
     * 🔄 Establecer nuevo valor (con validación)
     * 
     * @param nuevoValor Nuevo valor a establecer
     * @param procesoId ID del proceso que establece
     * @return Valor anterior
     */
    public int establecerValor(int nuevoValor, String procesoId) {
        return ejecutarFuncion(procesoId, () -> {
            if (nuevoValor < valorMinimo || nuevoValor > valorMaximo) {
                throw new IllegalArgumentException(
                    String.format("Valor %d fuera del rango [%d, %d]", nuevoValor, valorMinimo, valorMaximo));
            }
            
            int valorAnterior = valor;
            valor = nuevoValor;
            
            // 📊 Actualizar estadísticas
            if (valor > valorMaximoAlcanzado) {
                valorMaximoAlcanzado = valor;
            }
            if (valor < valorMinimoAlcanzado) {
                valorMinimoAlcanzado = valor;
            }
            
            System.out.printf("[%s] 🔄 Contador establecido: %d → %d%n", procesoId, valorAnterior, valor);
            
            // 🔔 Señalar cambios de estado
            if (valor > valorMinimo) {
                noMinimo.señalarTodos(procesoId);
            }
            if (valor < valorMaximo) {
                noMaximo.señalarTodos(procesoId);
            }
            
            return valorAnterior;
        });
    }
    
    /**
     * 🔍 Verificar si el contador está en el mínimo
     * 
     * @param procesoId ID del proceso que consulta
     * @return true si está en el valor mínimo
     */
    public boolean estaEnMinimo(String procesoId) {
        return ejecutarFuncion(procesoId, () -> valor <= valorMinimo);
    }
    
    /**
     * 🔍 Verificar si el contador está en el máximo
     * 
     * @param procesoId ID del proceso que consulta
     * @return true si está en el valor máximo
     */
    public boolean estaEnMaximo(String procesoId) {
        return ejecutarFuncion(procesoId, () -> valor >= valorMaximo);
    }
    
    /**
     * 📊 Obtener estadísticas específicas del contador
     * 
     * @return String con estadísticas del contador
     */
    public String getEstadisticasContador() {
        StringBuilder stats = new StringBuilder();
        stats.append(String.format("\n=== 🔢 ESTADÍSTICAS MONITOR CONTADOR ===\n"));
        stats.append(String.format("🔢 Valor actual: %d%n", valor));
        stats.append(String.format("📏 Rango: [%d, %d]%n", valorMinimo, valorMaximo));
        stats.append(String.format("➕ Total incrementos: %d%n", totalIncrementos));
        stats.append(String.format("➖ Total decrementos: %d%n", totalDecrementos));
        stats.append(String.format("📖 Total lecturas: %d%n", totalLecturas));
        stats.append(String.format("📈 Máximo alcanzado: %d%n", valorMaximoAlcanzado));
        stats.append(String.format("📉 Mínimo alcanzado: %d%n", valorMinimoAlcanzado));
        stats.append(String.format("🔄 Operaciones totales: %d%n", 
                                  totalIncrementos + totalDecrementos + totalLecturas));
        
        // Estadísticas de variables de condición
        stats.append(noMinimo.getEstadisticas());
        stats.append(noMaximo.getEstadisticas());
        
        return stats.toString();
    }
    
    /**
     * 🔍 Verificar integridad específica del contador
     * 
     * @return true si el contador está íntegro
     */
    public boolean verificarIntegridadContador() {
        // Verificar integridad base del monitor
        boolean integridadBase = verificarIntegridad();
        
        // Verificar que el valor esté en rango
        boolean valorEnRango = valor >= valorMinimo && valor <= valorMaximo;
        
        // Verificar consistencia de estadísticas
        boolean estadisticasValidas = totalIncrementos >= 0 && totalDecrementos >= 0 && totalLecturas >= 0;
        
        // Verificar rangos de valores alcanzados
        boolean rangosValidos = valorMinimoAlcanzado <= valorMaximoAlcanzado;
        
        // Verificar variables de condición
        boolean variablesIntegras = noMinimo.verificarIntegridad() && noMaximo.verificarIntegridad();
        
        boolean integro = integridadBase && valorEnRango && estadisticasValidas && 
                         rangosValidos && variablesIntegras;
        
        if (!integro) {
            System.err.printf("❌ INTEGRIDAD CONTADOR COMPROMETIDA:%n");
            System.err.printf("   Valor en rango: %s (%d ∈ [%d, %d])%n", 
                             valorEnRango, valor, valorMinimo, valorMaximo);
            System.err.printf("   Estadísticas válidas: %s%n", estadisticasValidas);
            System.err.printf("   Rangos válidos: %s%n", rangosValidos);
            System.err.printf("   Variables íntegras: %s%n", variablesIntegras);
        }
        
        return integro;
    }
    
    // 🎯 Implementación de métodos abstractos
    
    @Override
    protected void inicializarEstado() {
        System.out.printf("🔧 Inicializando estado del MonitorContador%n");
        // Estado ya inicializado en constructor
    }
    
    @Override
    public String getDescripcion() {
        return String.format("Monitor Contador (Valor: %d, Rango: [%d, %d])", 
                           valor, valorMinimo, valorMaximo);
    }
    
    @Override
    public boolean ejecutarPrueba(String procesoId) {
        System.out.printf("🧪 Ejecutando prueba del MonitorContador con proceso %s%n", procesoId);
        
        try {
            // Prueba básica: incrementar, leer, decrementar
            int valorInicial = obtenerValor(procesoId);
            int valorIncrementado = incrementar(procesoId);
            int valorLeido = obtenerValor(procesoId);
            int valorDecrementado = decrementar(procesoId);
            int valorFinal = obtenerValor(procesoId);
            
            boolean pruebaExitosa = (valorIncrementado == valorInicial + 1) &&
                                  (valorLeido == valorIncrementado) &&
                                  (valorDecrementado == valorInicial) &&
                                  (valorFinal == valorInicial);
            
            System.out.printf("🧪 Prueba MonitorContador: %s%n", 
                             pruebaExitosa ? "✅ EXITOSA" : "❌ FALLIDA");
            
            return pruebaExitosa;
            
        } catch (Exception e) {
            System.err.printf("❌ Error en prueba MonitorContador: %s%n", e.getMessage());
            return false;
        }
    }
    
    // 🔧 Getters específicos
    
    public int getValorMinimo() { return valorMinimo; }
    
    public int getValorMaximo() { return valorMaximo; }
    
    public int getTotalIncrementos() { return totalIncrementos; }
    
    public int getTotalDecrementos() { return totalDecrementos; }
    
    public int getTotalLecturas() { return totalLecturas; }
    
    public int getValorMaximoAlcanzado() { return valorMaximoAlcanzado; }
    
    public int getValorMinimoAlcanzado() { return valorMinimoAlcanzado; }
    
    public VariableCondicion getVariableNoMinimo() { return noMinimo; }
    
    public VariableCondicion getVariableNoMaximo() { return noMaximo; }
    
    /**
     * 📝 Representación en string del monitor contador
     * 
     * @return Información del contador
     */
    @Override
    public String toString() {
        return String.format("MonitorContador{valor=%d, rango=[%d,%d], inc=%d, dec=%d, lec=%d}", 
                           valor, valorMinimo, valorMaximo, totalIncrementos, totalDecrementos, totalLecturas);
    }
}
