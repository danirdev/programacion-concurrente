package tp6.actividad2.implementacion_b;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase SemaforoExterno - Implementación B
 * El semáforo se define en el main y se pasa por parámetro a los hilos.
 * Utiliza métodos de instancia para el control del semáforo general.
 */
public class SemaforoExterno {
    
    // Semáforo general con valor configurable
    private int valor;
    private final int valorInicial;
    private final Object lock = new Object();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    // Estadísticas del semáforo
    private int totalAdquisiciones = 0;
    private int totalLiberaciones = 0;
    private int hilosEsperando = 0;
    private final String identificador;
    
    /**
     * Constructor del SemaforoExterno
     * 
     * @param valorInicial Valor inicial del semáforo
     */
    public SemaforoExterno(int valorInicial) {
        this(valorInicial, "SemaforoExterno");
    }
    
    /**
     * Constructor del SemaforoExterno con identificador
     * 
     * @param valorInicial Valor inicial del semáforo
     * @param identificador Identificador único del semáforo
     */
    public SemaforoExterno(int valorInicial, String identificador) {
        if (valorInicial < 0) {
            throw new IllegalArgumentException("El valor inicial no puede ser negativo: " + valorInicial);
        }
        
        this.valor = valorInicial;
        this.valorInicial = valorInicial;
        this.identificador = identificador;
        
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] ✅ " + identificador + 
                          " creado con valor inicial: " + valorInicial);
    }
    
    /**
     * Método para adquirir el semáforo.
     * Si el valor es 0, el hilo espera hasta que se libere un recurso.
     * 
     * @param nombreHilo Nombre del hilo que intenta adquirir
     * @throws InterruptedException Si el hilo es interrumpido mientras espera
     */
    public void acquire(String nombreHilo) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        synchronized(lock) {
            // Si no hay recursos disponibles, esperar
            while (valor <= 0) {
                hilosEsperando++;
                System.out.println("[" + tiempo + "] " + nombreHilo + 
                                 " esperando en " + identificador + "... (valor=" + valor + 
                                 ", esperando=" + hilosEsperando + ")");
                
                lock.wait(); // Esperar hasta que se libere un recurso
                hilosEsperando--;
                tiempo = LocalTime.now().format(timeFormatter); // Actualizar tiempo después de esperar
            }
            
            // Adquirir recurso
            valor--;
            totalAdquisiciones++;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ✅ " + nombreHilo + 
                             " adquirió " + identificador + " (valor=" + valor + 
                             ", total adquisiciones=" + totalAdquisiciones + ")");
        }
    }
    
    /**
     * Método para liberar el semáforo.
     * Incrementa el valor y notifica a todos los hilos esperando.
     * 
     * @param nombreHilo Nombre del hilo que libera el recurso
     */
    public void release(String nombreHilo) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        synchronized(lock) {
            valor++;
            totalLiberaciones++;
            
            System.out.println("[" + tiempo + "] 🔓 " + nombreHilo + 
                             " liberó " + identificador + " (valor=" + valor + 
                             ", total liberaciones=" + totalLiberaciones + ")");
            
            // Notificar a todos los hilos esperando
            lock.notifyAll();
            
            if (hilosEsperando > 0) {
                System.out.println("[" + tiempo + "] 📢 " + identificador + 
                                 " notificando a " + hilosEsperando + " hilos esperando");
            }
        }
    }
    
    /**
     * Método para intentar adquirir el semáforo sin bloquear
     * 
     * @param nombreHilo Nombre del hilo que intenta adquirir
     * @return true si pudo adquirir, false si no hay recursos disponibles
     */
    public boolean tryAcquire(String nombreHilo) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        synchronized(lock) {
            if (valor > 0) {
                valor--;
                totalAdquisiciones++;
                System.out.println("[" + tiempo + "] ⚡ " + nombreHilo + 
                                 " adquirió " + identificador + " inmediatamente (valor=" + valor + ")");
                return true;
            } else {
                System.out.println("[" + tiempo + "] ❌ " + nombreHilo + 
                                 " no pudo adquirir " + identificador + " (valor=" + valor + ")");
                return false;
            }
        }
    }
    
    /**
     * Obtiene el valor actual del semáforo
     * 
     * @return Valor actual del semáforo
     */
    public int getValor() {
        synchronized(lock) {
            return valor;
        }
    }
    
    /**
     * Obtiene el valor inicial del semáforo
     * 
     * @return Valor inicial del semáforo
     */
    public int getValorInicial() {
        return valorInicial;
    }
    
    /**
     * Obtiene el número de hilos esperando
     * 
     * @return Número de hilos esperando
     */
    public int getHilosEsperando() {
        synchronized(lock) {
            return hilosEsperando;
        }
    }
    
    /**
     * Obtiene el identificador del semáforo
     * 
     * @return Identificador del semáforo
     */
    public String getIdentificador() {
        return identificador;
    }
    
    /**
     * Obtiene estadísticas del semáforo
     * 
     * @return Array con [valor, totalAdquisiciones, totalLiberaciones, hilosEsperando]
     */
    public int[] getEstadisticas() {
        synchronized(lock) {
            return new int[]{valor, totalAdquisiciones, totalLiberaciones, hilosEsperando};
        }
    }
    
    /**
     * Muestra el estado actual del semáforo
     */
    public void mostrarEstado() {
        synchronized(lock) {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("\n[" + tiempo + "] === ESTADO DE " + identificador.toUpperCase() + " ===");
            System.out.println("Identificador: " + identificador);
            System.out.println("Valor inicial: " + valorInicial);
            System.out.println("Valor actual: " + valor);
            System.out.println("Total adquisiciones: " + totalAdquisiciones);
            System.out.println("Total liberaciones: " + totalLiberaciones);
            System.out.println("Hilos esperando: " + hilosEsperando);
            System.out.println("Balance (A-L): " + (totalAdquisiciones - totalLiberaciones));
            
            if (valor > 0) {
                System.out.println("Estado: 🟢 RECURSOS DISPONIBLES (" + valor + " disponibles)");
            } else {
                System.out.println("Estado: 🔴 SIN RECURSOS - HILOS ESPERANDO");
            }
            System.out.println("=" + "=".repeat(identificador.length() + 16) + "\n");
        }
    }
    
    /**
     * Reinicia el semáforo a su estado inicial
     */
    public void reiniciar() {
        synchronized(lock) {
            valor = valorInicial;
            totalAdquisiciones = 0;
            totalLiberaciones = 0;
            hilosEsperando = 0;
            
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🔄 " + identificador + 
                             " reiniciado a valor inicial (" + valorInicial + ")");
        }
    }
    
    /**
     * Verifica si el semáforo tiene recursos disponibles
     * 
     * @return true si hay recursos disponibles, false en caso contrario
     */
    public boolean tieneRecursosDisponibles() {
        synchronized(lock) {
            return valor > 0;
        }
    }
    
    /**
     * Verifica si el semáforo está en su estado inicial
     * 
     * @return true si está en estado inicial, false en caso contrario
     */
    public boolean estaEnEstadoInicial() {
        synchronized(lock) {
            return valor == valorInicial && totalAdquisiciones == totalLiberaciones;
        }
    }
    
    /**
     * Obtiene información resumida del semáforo
     * 
     * @return String con información resumida
     */
    public String getInfoResumida() {
        synchronized(lock) {
            return String.format("%s[valor=%d/%d, esperando=%d, A=%d, L=%d]", 
                               identificador, valor, valorInicial, hilosEsperando, 
                               totalAdquisiciones, totalLiberaciones);
        }
    }
    
    /**
     * Obtiene información de configuración del semáforo
     * 
     * @return String con información de configuración
     */
    public String getConfiguracion() {
        return String.format(
            "Semáforo Externo - Implementación B:\n" +
            "• Identificador: %s\n" +
            "• Valor inicial: %d\n" +
            "• Tipo: Instancia (Inyección de dependencias)\n" +
            "• Acceso: Métodos de instancia\n" +
            "• Ubicación: Definido en main, pasado por parámetro\n" +
            "• Acoplamiento: Bajo\n" +
            "• Reutilización: Alta\n" +
            "• Configuración: Flexible",
            identificador, valorInicial
        );
    }
    
    /**
     * Método toString para representación del semáforo
     * 
     * @return Representación string del semáforo
     */
    @Override
    public String toString() {
        synchronized(lock) {
            return String.format("SemaforoExterno[id=%s, valor=%d/%d, A=%d, L=%d, esperando=%d]", 
                               identificador, valor, valorInicial, 
                               totalAdquisiciones, totalLiberaciones, hilosEsperando);
        }
    }
}
