package tp6.actividad2.implementacion_a;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase SemaforoInterno - Implementación A
 * Define e inicializa el semáforo de control dentro de la clase.
 * Utiliza métodos estáticos para el control del semáforo general.
 */
public class SemaforoInterno {
    
    // Semáforo general inicializado en 3
    private static int valor = 3;
    private static final Object lock = new Object();
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    // Estadísticas del semáforo
    private static int totalAdquisiciones = 0;
    private static int totalLiberaciones = 0;
    private static int hilosEsperando = 0;
    
    /**
     * Método para adquirir el semáforo.
     * Si el valor es 0, el hilo espera hasta que se libere un recurso.
     * 
     * @param nombreHilo Nombre del hilo que intenta adquirir
     * @throws InterruptedException Si el hilo es interrumpido mientras espera
     */
    public static void acquire(String nombreHilo) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        synchronized(lock) {
            // Si no hay recursos disponibles, esperar
            while (valor <= 0) {
                hilosEsperando++;
                System.out.println("[" + tiempo + "] " + nombreHilo + 
                                 " esperando... (valor=" + valor + 
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
                             " adquirió semáforo (valor=" + valor + 
                             ", total adquisiciones=" + totalAdquisiciones + ")");
        }
    }
    
    /**
     * Método para liberar el semáforo.
     * Incrementa el valor y notifica a todos los hilos esperando.
     * 
     * @param nombreHilo Nombre del hilo que libera el recurso
     */
    public static void release(String nombreHilo) {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        synchronized(lock) {
            valor++;
            totalLiberaciones++;
            
            System.out.println("[" + tiempo + "] 🔓 " + nombreHilo + 
                             " liberó semáforo (valor=" + valor + 
                             ", total liberaciones=" + totalLiberaciones + ")");
            
            // Notificar a todos los hilos esperando
            lock.notifyAll();
            
            if (hilosEsperando > 0) {
                System.out.println("[" + tiempo + "] 📢 Notificando a " + 
                                 hilosEsperando + " hilos esperando");
            }
        }
    }
    
    /**
     * Obtiene el valor actual del semáforo
     * 
     * @return Valor actual del semáforo
     */
    public static int getValor() {
        synchronized(lock) {
            return valor;
        }
    }
    
    /**
     * Obtiene el número de hilos esperando
     * 
     * @return Número de hilos esperando
     */
    public static int getHilosEsperando() {
        synchronized(lock) {
            return hilosEsperando;
        }
    }
    
    /**
     * Obtiene estadísticas del semáforo
     * 
     * @return Array con [valor, totalAdquisiciones, totalLiberaciones, hilosEsperando]
     */
    public static int[] getEstadisticas() {
        synchronized(lock) {
            return new int[]{valor, totalAdquisiciones, totalLiberaciones, hilosEsperando};
        }
    }
    
    /**
     * Muestra el estado actual del semáforo
     */
    public static void mostrarEstado() {
        synchronized(lock) {
            String tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("\n[" + tiempo + "] === ESTADO DEL SEMÁFORO INTERNO ===");
            System.out.println("Valor actual: " + valor);
            System.out.println("Total adquisiciones: " + totalAdquisiciones);
            System.out.println("Total liberaciones: " + totalLiberaciones);
            System.out.println("Hilos esperando: " + hilosEsperando);
            System.out.println("Balance (A-L): " + (totalAdquisiciones - totalLiberaciones));
            
            if (valor > 0) {
                System.out.println("Estado: 🟢 RECURSOS DISPONIBLES");
            } else {
                System.out.println("Estado: 🔴 SIN RECURSOS - HILOS ESPERANDO");
            }
            System.out.println("=====================================\n");
        }
    }
    
    /**
     * Reinicia el semáforo a su estado inicial
     */
    public static void reiniciar() {
        synchronized(lock) {
            valor = 3;
            totalAdquisiciones = 0;
            totalLiberaciones = 0;
            hilosEsperando = 0;
            System.out.println("🔄 Semáforo interno reiniciado a valor inicial (3)");
        }
    }
    
    /**
     * Verifica si el semáforo tiene recursos disponibles
     * 
     * @return true si hay recursos disponibles, false en caso contrario
     */
    public static boolean tieneRecursosDisponibles() {
        synchronized(lock) {
            return valor > 0;
        }
    }
    
    /**
     * Obtiene información resumida del semáforo
     * 
     * @return String con información resumida
     */
    public static String getInfoResumida() {
        synchronized(lock) {
            return String.format("SemaforoInterno[valor=%d, esperando=%d, A=%d, L=%d]", 
                               valor, hilosEsperando, totalAdquisiciones, totalLiberaciones);
        }
    }
    
    /**
     * Método para obtener información de configuración
     * 
     * @return String con información de configuración
     */
    public static String getConfiguracion() {
        return "Semáforo Interno - Implementación A:\n" +
               "• Valor inicial: 3\n" +
               "• Tipo: Estático (Singleton implícito)\n" +
               "• Acceso: Métodos estáticos\n" +
               "• Ubicación: Dentro de la clase\n" +
               "• Acoplamiento: Alto\n" +
               "• Reutilización: Limitada";
    }
}
