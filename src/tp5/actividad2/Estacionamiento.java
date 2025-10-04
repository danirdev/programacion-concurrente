package tp5.actividad2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase Estacionamiento que representa el recurso compartido con capacidad limitada.
 * Controla el acceso de automóviles con un máximo de 20 espacios simultáneos.
 * Posee 2 entradas y 2 salidas para el flujo de vehículos.
 */
public class Estacionamiento {
    private final int CAPACIDAD_MAXIMA = 20;
    private int automovilesActuales;
    private int totalEntradas;
    private int totalSalidas;
    private int automovilesEsperandoEntrada;
    private final DateTimeFormatter timeFormatter;
    private final Map<Integer, Long> tiemposEntrada; // Para calcular permanencia
    private final boolean[] espacios; // Array para representar espacios ocupados
    
    /**
     * Constructor del Estacionamiento
     */
    public Estacionamiento() {
        this.automovilesActuales = 0;
        this.totalEntradas = 0;
        this.totalSalidas = 0;
        this.automovilesEsperandoEntrada = 0;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.tiemposEntrada = new HashMap<>();
        this.espacios = new boolean[CAPACIDAD_MAXIMA]; // false = libre, true = ocupado
    }
    
    /**
     * Método sincronizado para que un automóvil entre al estacionamiento
     * @param automovilId ID del automóvil
     * @param entradaId ID de la entrada (1 o 2)
     * @throws InterruptedException Si el hilo es interrumpido
     */
    public synchronized void entrar(int automovilId, int entradaId) throws InterruptedException {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        // Esperar hasta que haya espacio disponible
        while (automovilesActuales >= CAPACIDAD_MAXIMA) {
            automovilesEsperandoEntrada++;
            System.out.println("[" + tiempo + "] ¡ESTACIONAMIENTO LLENO! (" + automovilesActuales + "/" + 
                             CAPACIDAD_MAXIMA + ") - Automóvil-" + String.format("%03d", automovilId) + 
                             " esperando en Entrada-" + entradaId + 
                             " (Esperando: " + automovilesEsperandoEntrada + ")");
            
            wait(); // Esperar hasta que se libere un espacio
            automovilesEsperandoEntrada--;
            tiempo = LocalTime.now().format(timeFormatter); // Actualizar tiempo después de esperar
        }
        
        // Asignar espacio y registrar entrada
        int espacioAsignado = asignarEspacio();
        automovilesActuales++;
        totalEntradas++;
        tiemposEntrada.put(automovilId, System.currentTimeMillis());
        
        tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Automóvil-" + String.format("%03d", automovilId) + 
                         " entró por Entrada-" + entradaId + " → Espacio-" + espacioAsignado + 
                         " (Ocupación: " + automovilesActuales + "/" + CAPACIDAD_MAXIMA + ")");
        
        // Mostrar estado si está cerca de la capacidad máxima
        if (automovilesActuales >= CAPACIDAD_MAXIMA * 0.8) {
            System.out.println("[" + tiempo + "] ⚠️  Estacionamiento casi lleno: " + 
                             automovilesActuales + "/" + CAPACIDAD_MAXIMA);
        }
    }
    
    /**
     * Método sincronizado para que un automóvil salga del estacionamiento
     * @param automovilId ID del automóvil
     * @param salidaId ID de la salida (1 o 2)
     */
    public synchronized void salir(int automovilId, int salidaId) {
        // Liberar espacio
        liberarEspacio(automovilId);
        automovilesActuales--;
        totalSalidas++;
        
        // Calcular tiempo de permanencia
        Long tiempoEntrada = tiemposEntrada.remove(automovilId);
        long permanencia = tiempoEntrada != null ? 
            System.currentTimeMillis() - tiempoEntrada : 0;
        
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] Automóvil-" + String.format("%03d", automovilId) + 
                         " salió por Salida-" + salidaId + 
                         " (Permanencia: " + permanencia + "ms, Ocupación: " + 
                         automovilesActuales + "/" + CAPACIDAD_MAXIMA + ")");
        
        // Notificar a automóviles esperando que hay espacio disponible
        notifyAll();
        
        // Mostrar liberación de espacio si había cola
        if (automovilesEsperandoEntrada > 0) {
            System.out.println("[" + tiempo + "] ✅ Espacio liberado - " + 
                             automovilesEsperandoEntrada + " automóviles pueden intentar entrar");
        }
    }
    
    /**
     * Asigna un espacio libre en el estacionamiento
     * @return Número del espacio asignado (1-20)
     */
    private int asignarEspacio() {
        for (int i = 0; i < espacios.length; i++) {
            if (!espacios[i]) {
                espacios[i] = true;
                return i + 1; // Espacios numerados del 1 al 20
            }
        }
        return -1; // No debería ocurrir si el control de capacidad funciona
    }
    
    /**
     * Libera el espacio ocupado por un automóvil
     * @param automovilId ID del automóvil
     */
    private void liberarEspacio(int automovilId) {
        // En una implementación más compleja, mantendríamos un mapeo automóvil->espacio
        // Por simplicidad, liberamos el primer espacio ocupado
        for (int i = 0; i < espacios.length; i++) {
            if (espacios[i]) {
                espacios[i] = false;
                break;
            }
        }
    }
    
    /**
     * Obtiene el número actual de automóviles en el estacionamiento
     * @return Número de automóviles actuales
     */
    public synchronized int getAutomovilesActuales() {
        return automovilesActuales;
    }
    
    /**
     * Obtiene el total de entradas registradas
     * @return Total de entradas
     */
    public synchronized int getTotalEntradas() {
        return totalEntradas;
    }
    
    /**
     * Obtiene el total de salidas registradas
     * @return Total de salidas
     */
    public synchronized int getTotalSalidas() {
        return totalSalidas;
    }
    
    /**
     * Obtiene el número de automóviles esperando entrada
     * @return Automóviles esperando
     */
    public synchronized int getAutomovilesEsperandoEntrada() {
        return automovilesEsperandoEntrada;
    }
    
    /**
     * Obtiene la capacidad máxima del estacionamiento
     * @return Capacidad máxima
     */
    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }
    
    /**
     * Verifica si el estacionamiento está lleno
     * @return true si está lleno, false en caso contrario
     */
    public synchronized boolean estaLleno() {
        return automovilesActuales >= CAPACIDAD_MAXIMA;
    }
    
    /**
     * Verifica si el estacionamiento está vacío
     * @return true si está vacío, false en caso contrario
     */
    public synchronized boolean estaVacio() {
        return automovilesActuales == 0;
    }
    
    /**
     * Obtiene el porcentaje de ocupación
     * @return Porcentaje de ocupación (0-100)
     */
    public synchronized double getPorcentajeOcupacion() {
        return (double) automovilesActuales / CAPACIDAD_MAXIMA * 100;
    }
    
    /**
     * Muestra el estado actual del estacionamiento
     */
    public synchronized void mostrarEstado() {
        String tiempo = LocalTime.now().format(timeFormatter);
        System.out.println("[" + tiempo + "] === ESTADO DEL ESTACIONAMIENTO ===");
        
        System.out.println("Ocupación actual: " + automovilesActuales + "/" + CAPACIDAD_MAXIMA + 
                         " (" + String.format("%.1f%%", getPorcentajeOcupacion()) + ")");
        System.out.println("Total entradas: " + totalEntradas);
        System.out.println("Total salidas: " + totalSalidas);
        System.out.println("Automóviles esperando: " + automovilesEsperandoEntrada);
        System.out.println("Diferencia (E-S): " + (totalEntradas - totalSalidas));
        
        // Mostrar representación visual del estacionamiento
        System.out.print("Espacios: ");
        for (int i = 0; i < espacios.length; i++) {
            if (i % 10 == 0 && i > 0) System.out.print(" | ");
            System.out.print(espacios[i] ? "🚗" : "⬜");
        }
        System.out.println();
        
        // Estado del estacionamiento
        if (estaVacio()) {
            System.out.println("🟢 ESTACIONAMIENTO VACÍO");
        } else if (estaLleno()) {
            System.out.println("🔴 ESTACIONAMIENTO LLENO");
        } else if (getPorcentajeOcupacion() >= 80) {
            System.out.println("🟡 ESTACIONAMIENTO CASI LLENO");
        } else {
            System.out.println("🟢 ESTACIONAMIENTO CON ESPACIOS DISPONIBLES");
        }
        
        System.out.println("=====================================");
    }
    
    /**
     * Obtiene información resumida del estacionamiento
     * @return String con información del estado actual
     */
    public synchronized String getInfoResumida() {
        return String.format("Estacionamiento[%d/%d, E:%d, S:%d, Esperando:%d]", 
                           automovilesActuales, CAPACIDAD_MAXIMA, 
                           totalEntradas, totalSalidas, automovilesEsperandoEntrada);
    }
    
    /**
     * Obtiene estadísticas detalladas del estacionamiento
     * @return Array con [ocupacionActual, totalEntradas, totalSalidas, esperando, porcentajeOcupacion]
     */
    public synchronized double[] getEstadisticas() {
        return new double[]{
            automovilesActuales,
            totalEntradas, 
            totalSalidas,
            automovilesEsperandoEntrada,
            getPorcentajeOcupacion()
        };
    }
}
