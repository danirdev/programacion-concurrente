package tp6.actividad1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Clase EstacionamientoSemaforos que utiliza semáforos para controlar
 * el acceso a un estacionamiento con capacidad limitada.
 * Implementa el patrón Resource Pool usando java.util.concurrent.Semaphore.
 */
public class EstacionamientoSemaforos {
    
    // Semáforos para control de recursos
    private final Semaphore capacidad;           // Control de espacios disponibles (20)
    private final Semaphore entrada1;            // Control de acceso a entrada 1
    private final Semaphore entrada2;            // Control de acceso a entrada 2
    private final Semaphore salida1;             // Control de acceso a salida 1
    private final Semaphore salida2;             // Control de acceso a salida 2
    private final Semaphore mutex;               // Exclusión mutua para operaciones críticas
    
    // Estadísticas del estacionamiento
    private int automovilesActuales;
    private int totalEntradas;
    private int totalSalidas;
    private final DateTimeFormatter timeFormatter;
    
    // Configuración
    private static final int CAPACIDAD_MAXIMA = 20;
    private static final int TIEMPO_ENTRADA_MS = 200;
    private static final int TIEMPO_SALIDA_MS = 200;
    
    /**
     * Constructor del EstacionamientoSemaforos
     */
    public EstacionamientoSemaforos() {
        // Inicializar semáforos
        this.capacidad = new Semaphore(CAPACIDAD_MAXIMA, true); // Fair para FIFO
        this.entrada1 = new Semaphore(1, true);
        this.entrada2 = new Semaphore(1, true);
        this.salida1 = new Semaphore(1, true);
        this.salida2 = new Semaphore(1, true);
        this.mutex = new Semaphore(1, true);
        
        // Inicializar estadísticas
        this.automovilesActuales = 0;
        this.totalEntradas = 0;
        this.totalSalidas = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        
        System.out.println("✅ Estacionamiento con semáforos inicializado");
        System.out.println("   Capacidad: " + CAPACIDAD_MAXIMA + " espacios");
        System.out.println("   Entradas: 2 (con semáforos individuales)");
        System.out.println("   Salidas: 2 (con semáforos individuales)");
        System.out.println("   Fairness: Habilitado (FIFO)");
    }
    
    /**
     * Método para que un automóvil entre al estacionamiento.
     * Utiliza semáforos para controlar capacidad y acceso a entradas.
     * 
     * @param automovilId ID del automóvil
     * @param entradaId ID de la entrada (1 o 2)
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void entrar(int automovilId, int entradaId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        String autoId = String.format("Auto-%03d", automovilId);
        
        // 1. Esperar espacio disponible en el estacionamiento
        System.out.println("[" + tiempo + "] " + autoId + 
                          " esperando espacio... (" + capacidad.availablePermits() + " disponibles)");
        
        capacidad.acquire(); // Bloquea si no hay espacios disponibles
        
        // 2. Seleccionar y esperar acceso a la entrada específica
        Semaphore entradaSemaforo = (entradaId == 1) ? entrada1 : entrada2;
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] " + autoId + 
                          " obtuvo espacio, esperando Entrada-" + entradaId + "...");
        
        entradaSemaforo.acquire(); // Bloquea si la entrada está ocupada
        
        try {
            // 3. Proceso de entrada (sección crítica)
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] " + autoId + 
                              " entrando por Entrada-" + entradaId);
            
            // Simular tiempo de entrada
            Thread.sleep(TIEMPO_ENTRADA_MS);
            
            // 4. Actualizar estadísticas (requiere mutex)
            mutex.acquire();
            try {
                automovilesActuales++;
                totalEntradas++;
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] ✅ " + autoId + 
                                  " estacionado (Ocupación: " + automovilesActuales + 
                                  "/" + CAPACIDAD_MAXIMA + ")");
                
                // Mostrar alerta si está cerca de la capacidad máxima
                if (automovilesActuales >= CAPACIDAD_MAXIMA * 0.9) {
                    System.out.println("[" + tiempo + "] ⚠️  Estacionamiento casi lleno: " + 
                                     automovilesActuales + "/" + CAPACIDAD_MAXIMA);
                }
                
            } finally {
                mutex.release();
            }
            
        } finally {
            // 5. Liberar acceso a la entrada
            entradaSemaforo.release();
        }
    }
    
    /**
     * Método para que un automóvil salga del estacionamiento.
     * Utiliza semáforos para controlar acceso a salidas y liberar capacidad.
     * 
     * @param automovilId ID del automóvil
     * @param salidaId ID de la salida (1 o 2)
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public void salir(int automovilId, int salidaId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        String autoId = String.format("Auto-%03d", automovilId);
        
        // 1. Esperar acceso a la salida específica
        Semaphore salidaSemaforo = (salidaId == 1) ? salida1 : salida2;
        
        System.out.println("[" + tiempo + "] " + autoId + 
                          " dirigiéndose a Salida-" + salidaId + "...");
        
        salidaSemaforo.acquire(); // Bloquea si la salida está ocupada
        
        try {
            // 2. Proceso de salida (sección crítica)
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] " + autoId + 
                              " saliendo por Salida-" + salidaId);
            
            // Simular tiempo de salida
            Thread.sleep(TIEMPO_SALIDA_MS);
            
            // 3. Actualizar estadísticas (requiere mutex)
            mutex.acquire();
            try {
                automovilesActuales--;
                totalSalidas++;
                
                tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("[" + tiempo + "] ✅ " + autoId + 
                                  " salió del estacionamiento (Ocupación: " + 
                                  automovilesActuales + "/" + CAPACIDAD_MAXIMA + ")");
                
                // Mostrar información si hay automóviles esperando
                int esperando = capacidad.getQueueLength();
                if (esperando > 0) {
                    System.out.println("[" + tiempo + "] 🚗 Espacio liberado - " + 
                                     esperando + " automóviles esperando");
                }
                
            } finally {
                mutex.release();
            }
            
        } finally {
            // 4. Liberar acceso a la salida
            salidaSemaforo.release();
            
            // 5. Liberar espacio en el estacionamiento (IMPORTANTE: al final)
            capacidad.release();
        }
    }
    
    /**
     * Método con timeout para entrada (evita esperas indefinidas)
     * 
     * @param automovilId ID del automóvil
     * @param entradaId ID de la entrada
     * @param timeoutSeconds Timeout en segundos
     * @return true si logró entrar, false si timeout
     */
    public boolean intentarEntrar(int automovilId, int entradaId, int timeoutSeconds) {
        String tiempo = LocalTime.now().format(timeFormatter);
        String autoId = String.format("Auto-%03d", automovilId);
        
        try {
            // Intentar adquirir espacio con timeout
            if (!capacidad.tryAcquire(timeoutSeconds, TimeUnit.SECONDS)) {
                System.out.println("[" + tiempo + "] ⏰ " + autoId + 
                                  " abandonó: timeout esperando espacio (" + timeoutSeconds + "s)");
                return false;
            }
            
            // Intentar adquirir entrada con timeout
            Semaphore entradaSemaforo = (entradaId == 1) ? entrada1 : entrada2;
            if (!entradaSemaforo.tryAcquire(2, TimeUnit.SECONDS)) {
                capacidad.release(); // Liberar espacio adquirido
                System.out.println("[" + tiempo + "] ⏰ " + autoId + 
                                  " abandonó: timeout esperando Entrada-" + entradaId);
                return false;
            }
            
            // Continuar con proceso normal de entrada
            try {
                Thread.sleep(TIEMPO_ENTRADA_MS);
                
                mutex.acquire();
                try {
                    automovilesActuales++;
                    totalEntradas++;
                    System.out.println("[" + tiempo + "] ✅ " + autoId + 
                                      " entró con timeout (Ocupación: " + automovilesActuales + 
                                      "/" + CAPACIDAD_MAXIMA + ")");
                } finally {
                    mutex.release();
                }
                
            } finally {
                entradaSemaforo.release();
            }
            
            return true;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Obtiene el estado actual de los semáforos
     * 
     * @return String con información de los semáforos
     */
    public String obtenerEstadoSemaforos() {
        try {
            mutex.acquire();
            try {
                StringBuilder estado = new StringBuilder();
                estado.append("=== ESTADO DE SEMÁFOROS ===\n");
                estado.append("Capacidad: ").append(capacidad.availablePermits()).append("/").append(CAPACIDAD_MAXIMA);
                estado.append(" (").append(capacidad.getQueueLength()).append(" esperando)\n");
                estado.append("Entrada 1: ").append(entrada1.availablePermits() > 0 ? "LIBRE" : "OCUPADA");
                estado.append(" (").append(entrada1.getQueueLength()).append(" esperando)\n");
                estado.append("Entrada 2: ").append(entrada2.availablePermits() > 0 ? "LIBRE" : "OCUPADA");
                estado.append(" (").append(entrada2.getQueueLength()).append(" esperando)\n");
                estado.append("Salida 1: ").append(salida1.availablePermits() > 0 ? "LIBRE" : "OCUPADA");
                estado.append(" (").append(salida1.getQueueLength()).append(" esperando)\n");
                estado.append("Salida 2: ").append(salida2.availablePermits() > 0 ? "LIBRE" : "OCUPADA");
                estado.append(" (").append(salida2.getQueueLength()).append(" esperando)\n");
                estado.append("===========================");
                return estado.toString();
            } finally {
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error obteniendo estado de semáforos";
        }
    }
    
    /**
     * Obtiene estadísticas del estacionamiento
     * 
     * @return Array con [ocupacionActual, totalEntradas, totalSalidas, capacidadMaxima]
     */
    public int[] obtenerEstadisticas() {
        try {
            mutex.acquire();
            try {
                return new int[]{automovilesActuales, totalEntradas, totalSalidas, CAPACIDAD_MAXIMA};
            } finally {
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new int[]{0, 0, 0, CAPACIDAD_MAXIMA};
        }
    }
    
    /**
     * Verifica si el estacionamiento está lleno
     * 
     * @return true si está lleno, false en caso contrario
     */
    public boolean estaLleno() {
        return capacidad.availablePermits() == 0;
    }
    
    /**
     * Verifica si el estacionamiento está vacío
     * 
     * @return true si está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        try {
            mutex.acquire();
            try {
                return automovilesActuales == 0;
            } finally {
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Muestra estadísticas detalladas del estacionamiento
     */
    public void mostrarEstadisticas() {
        try {
            mutex.acquire();
            try {
                String tiempo = LocalTime.now().format(timeFormatter);
                System.out.println("\n[" + tiempo + "] " + "=".repeat(50));
                System.out.println("    ESTADÍSTICAS DEL ESTACIONAMIENTO (SEMÁFOROS)");
                System.out.println("=".repeat(50));
                System.out.println("Ocupación actual: " + automovilesActuales + "/" + CAPACIDAD_MAXIMA + 
                                 " (" + String.format("%.1f%%", (automovilesActuales * 100.0 / CAPACIDAD_MAXIMA)) + ")");
                System.out.println("Total entradas: " + totalEntradas);
                System.out.println("Total salidas: " + totalSalidas);
                System.out.println("Balance (E-S): " + (totalEntradas - totalSalidas));
                System.out.println("Espacios disponibles: " + capacidad.availablePermits());
                System.out.println("Automóviles esperando espacio: " + capacidad.getQueueLength());
                
                // Estado de entradas y salidas
                System.out.println("\nEstado de accesos:");
                System.out.println("  Entrada 1: " + (entrada1.availablePermits() > 0 ? "LIBRE" : "OCUPADA") + 
                                 " (" + entrada1.getQueueLength() + " esperando)");
                System.out.println("  Entrada 2: " + (entrada2.availablePermits() > 0 ? "LIBRE" : "OCUPADA") + 
                                 " (" + entrada2.getQueueLength() + " esperando)");
                System.out.println("  Salida 1: " + (salida1.availablePermits() > 0 ? "LIBRE" : "OCUPADA") + 
                                 " (" + salida1.getQueueLength() + " esperando)");
                System.out.println("  Salida 2: " + (salida2.availablePermits() > 0 ? "LIBRE" : "OCUPADA") + 
                                 " (" + salida2.getQueueLength() + " esperando)");
                
                // Estado general
                if (estaVacio()) {
                    System.out.println("\n🟢 ESTACIONAMIENTO VACÍO");
                } else if (estaLleno()) {
                    System.out.println("\n🔴 ESTACIONAMIENTO LLENO");
                } else if (automovilesActuales >= CAPACIDAD_MAXIMA * 0.8) {
                    System.out.println("\n🟡 ESTACIONAMIENTO CASI LLENO");
                } else {
                    System.out.println("\n🟢 ESTACIONAMIENTO CON ESPACIOS DISPONIBLES");
                }
                
                System.out.println("=".repeat(50) + "\n");
                
            } finally {
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error mostrando estadísticas: hilo interrumpido");
        }
    }
    
    /**
     * Obtiene información resumida del estacionamiento
     * 
     * @return String con información resumida
     */
    public String getInfoResumida() {
        try {
            mutex.acquire();
            try {
                return String.format("Estacionamiento[%d/%d, E:%d, S:%d, Esperando:%d]", 
                                   automovilesActuales, CAPACIDAD_MAXIMA, 
                                   totalEntradas, totalSalidas, capacidad.getQueueLength());
            } finally {
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Estacionamiento[Error: interrumpido]";
        }
    }
}
