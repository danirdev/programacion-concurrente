package tp3.actividad5;

/**
 * Hilo individual que ejecuta el cálculo SumRootN para un valor específico de root
 * Permite paralelización del algoritmo computacionalmente intensivo
 * @author PC2025
 */
public class HiloCalculador extends Thread {
    
    private int root;
    private double resultado;
    private long tiempoEjecucion; // en nanosegundos
    private boolean completado;
    private Exception error;
    
    private static final int ITERACIONES = 10000000;
    
    /**
     * Constructor del hilo calculador
     * @param root valor de root para el cálculo SumRootN
     */
    public HiloCalculador(int root) {
        this.root = root;
        this.resultado = 0.0;
        this.tiempoEjecucion = 0;
        this.completado = false;
        this.error = null;
        this.setName("HiloCalculador-" + root);
    }
    
    /**
     * Implementación del algoritmo SumRootN
     * Idéntico al de la versión secuencial
     * @param root valor de la raíz
     * @return suma calculada
     */
    private double SumRootN(int root) {
        double result = 0;
        for (int i = 0; i < ITERACIONES; i++) {
            result += Math.exp(Math.log(i) / root);
        }
        return result;
    }
    
    /**
     * Método run del hilo - ejecuta el cálculo
     */
    @Override
    public void run() {
        try {
            System.out.println("[" + getName() + "] Iniciando cálculo SumRootN(" + root + ")");
            
            long tiempoInicio = System.nanoTime();
            resultado = SumRootN(root);
            long tiempoFin = System.nanoTime();
            
            tiempoEjecucion = tiempoFin - tiempoInicio;
            completado = true;
            
            double tiempoSegundos = tiempoEjecucion / 1_000_000_000.0;
            System.out.printf("[%s] Completado en %.2f segundos (resultado: %.2e)%n", 
                             getName(), tiempoSegundos, resultado);
            
        } catch (Exception e) {
            this.error = e;
            System.err.println("[" + getName() + "] Error durante el cálculo: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene el resultado del cálculo
     * @return resultado calculado
     * @throws IllegalStateException si el cálculo no ha completado
     */
    public double getResultado() {
        if (!completado) {
            throw new IllegalStateException("El cálculo no ha completado aún");
        }
        return resultado;
    }
    
    /**
     * Obtiene el tiempo de ejecución en nanosegundos
     * @return tiempo de ejecución
     * @throws IllegalStateException si el cálculo no ha completado
     */
    public long getTiempoEjecucionNanos() {
        if (!completado) {
            throw new IllegalStateException("El cálculo no ha completado aún");
        }
        return tiempoEjecucion;
    }
    
    /**
     * Obtiene el tiempo de ejecución en segundos
     * @return tiempo de ejecución en segundos
     */
    public double getTiempoEjecucionSegundos() {
        return getTiempoEjecucionNanos() / 1_000_000_000.0;
    }
    
    /**
     * Verifica si el cálculo ha completado
     * @return true si completó, false en caso contrario
     */
    public boolean isCompletado() {
        return completado;
    }
    
    /**
     * Obtiene el valor de root asignado a este hilo
     * @return valor de root
     */
    public int getRoot() {
        return root;
    }
    
    /**
     * Verifica si hubo error durante la ejecución
     * @return true si hubo error, false en caso contrario
     */
    public boolean tieneError() {
        return error != null;
    }
    
    /**
     * Obtiene el error ocurrido durante la ejecución
     * @return excepción ocurrida o null si no hubo error
     */
    public Exception getError() {
        return error;
    }
    
    /**
     * Obtiene información detallada del hilo
     * @return string con información del hilo
     */
    public String getInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Hilo: ").append(getName()).append("\n");
        info.append("Root: ").append(root).append("\n");
        info.append("Estado: ").append(completado ? "Completado" : "En ejecución").append("\n");
        
        if (completado) {
            info.append("Resultado: ").append(String.format("%.6e", resultado)).append("\n");
            info.append("Tiempo: ").append(String.format("%.3f", getTiempoEjecucionSegundos())).append(" segundos\n");
            info.append("Operaciones/seg: ").append(String.format("%.0f", ITERACIONES / getTiempoEjecucionSegundos())).append("\n");
        }
        
        if (tieneError()) {
            info.append("Error: ").append(error.getMessage()).append("\n");
        }
        
        return info.toString();
    }
    
    /**
     * Espera a que el hilo complete y retorna el resultado
     * @return resultado del cálculo
     * @throws InterruptedException si el hilo es interrumpido
     * @throws RuntimeException si ocurrió un error durante el cálculo
     */
    public double esperarYObtenerResultado() throws InterruptedException {
        this.join(); // Esperar a que termine
        
        if (tieneError()) {
            throw new RuntimeException("Error en el cálculo: " + error.getMessage(), error);
        }
        
        return getResultado();
    }
    
    /**
     * Método estático para crear y ejecutar un hilo calculador
     * @param root valor de root
     * @return hilo creado y iniciado
     */
    public static HiloCalculador crearYEjecutar(int root) {
        HiloCalculador hilo = new HiloCalculador(root);
        hilo.start();
        return hilo;
    }
    
    /**
     * Método para prueba individual del hilo
     */
    public static void main(String[] args) {
        System.out.println("PRUEBA INDIVIDUAL DE HILO CALCULADOR");
        System.out.println("=".repeat(40));
        
        int rootPrueba = 5;
        System.out.println("Creando hilo para SumRootN(" + rootPrueba + ")");
        
        HiloCalculador hilo = new HiloCalculador(rootPrueba);
        
        long tiempoInicio = System.nanoTime();
        hilo.start();
        
        try {
            hilo.join();
            long tiempoFin = System.nanoTime();
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("RESULTADO DE LA PRUEBA:");
            System.out.println(hilo.getInformacion());
            System.out.println("Tiempo total (incluyendo overhead): " + 
                              String.format("%.3f", (tiempoFin - tiempoInicio) / 1_000_000_000.0) + " segundos");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Prueba interrumpida");
        }
    }
}
