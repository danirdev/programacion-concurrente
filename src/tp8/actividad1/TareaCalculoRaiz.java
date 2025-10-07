package tp8.actividad1;

/**
 * 🔢 TareaCalculoRaiz - Tarea que calcula suma de raíces N-ésimas
 * 
 * Esta clase implementa Runnable y ejecuta el cálculo intensivo:
 * result = Σ(i=0 to 10,000,000) exp(log(i) / root)
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaCalculoRaiz implements Runnable {
    
    // 🏷️ Identificación de la tarea
    private final int numeroTarea;
    private final int root;
    
    // 🔢 Configuración del cálculo
    private static final int ITERACIONES = 10_000_000;
    
    // 📊 Resultados
    private double resultado;
    private long tiempoEjecucion;
    private String nombreThread;
    
    /**
     * 🏗️ Constructor de la TareaCalculoRaiz
     * 
     * @param numeroTarea Número identificador de la tarea
     * @param root Valor de la raíz (root > 0)
     */
    public TareaCalculoRaiz(int numeroTarea, int root) {
        if (root <= 0) {
            throw new IllegalArgumentException("root debe ser mayor a 0");
        }
        
        this.numeroTarea = numeroTarea;
        this.root = root;
        this.resultado = 0;
        this.tiempoEjecucion = 0;
    }
    
    /**
     * 🏃‍♂️ Método run - Ejecuta el cálculo
     */
    @Override
    public void run() {
        nombreThread = Thread.currentThread().getName();
        
        System.out.printf("[%s] 🚀 Tarea #%d (root=%d) iniciada%n", 
                         nombreThread, numeroTarea, root);
        
        long tiempoInicio = System.currentTimeMillis();
        
        // 🔢 Ejecutar cálculo SumRootN
        resultado = sumRootN(root);
        
        tiempoEjecucion = System.currentTimeMillis() - tiempoInicio;
        
        System.out.printf("[%s] ✅ Tarea #%d completada - Resultado: %.6e - Tiempo: %dms%n", 
                         nombreThread, numeroTarea, resultado, tiempoEjecucion);
    }
    
    /**
     * 🔢 Cálculo SumRootN - Suma de raíces N-ésimas
     * 
     * @param root Valor de la raíz
     * @return Resultado del cálculo
     */
    private double sumRootN(int root) {
        double result = 0;
        
        for (int i = 0; i < ITERACIONES; i++) {
            // Cálculo: exp(log(i) / root) = i^(1/root)
            result += Math.exp(Math.log(i) / root);
        }
        
        return result;
    }
    
    // 🔧 Getters
    
    public int getNumeroTarea() { return numeroTarea; }
    
    public int getRoot() { return root; }
    
    public double getResultado() { return resultado; }
    
    public long getTiempoEjecucion() { return tiempoEjecucion; }
    
    public String getNombreThread() { return nombreThread; }
    
    /**
     * 📊 Obtener información de la tarea
     * 
     * @return String con información de la tarea
     */
    public String getInfo() {
        return String.format("Tarea #%d (root=%d): resultado=%.6e, tiempo=%dms, thread=%s", 
                           numeroTarea, root, resultado, tiempoEjecucion, nombreThread);
    }
    
    /**
     * 📝 Representación en string de la tarea
     * 
     * @return Información básica de la tarea
     */
    @Override
    public String toString() {
        return String.format("TareaCalculoRaiz{#%d, root=%d, tiempo=%dms}", 
                           numeroTarea, root, tiempoEjecucion);
    }
}
