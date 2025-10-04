package tp6.actividad4;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Cliente que extiende Thread.
 * Representa un cliente que llega al supermercado para hacer compras.
 * Sigue el flujo completo: entrar, tomar carrito, comprar, pagar y salir.
 */
public class Cliente extends Thread {
    
    private final int clienteId;
    private final Supermercado supermercado;
    private final DateTimeFormatter timeFormatter;
    
    // Estado del cliente
    private long tiempoLlegada;
    private long tiempoEntrada;
    private long tiempoInicioCompras;
    private long tiempoFinCompras;
    private long tiempoInicioPago;
    private long tiempoFinPago;
    private long tiempoSalida;
    private boolean completoCompras;
    private String estadoActual;
    
    /**
     * Constructor del Cliente
     * 
     * @param clienteId ID único del cliente
     * @param supermercado Supermercado donde hará las compras
     */
    public Cliente(int clienteId, Supermercado supermercado) {
        super("Cliente-" + clienteId);
        this.clienteId = clienteId;
        this.supermercado = supermercado;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.completoCompras = false;
        this.estadoActual = "CREADO";
    }
    
    @Override
    public void run() {
        String tiempo = LocalTime.now().format(timeFormatter);
        
        try {
            // 1. LLEGADA AL SUPERMERCADO
            tiempoLlegada = System.currentTimeMillis();
            estadoActual = "LLEGANDO";
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🚶 Cliente " + clienteId + 
                             " llegó al supermercado");
            
            // 2. ENTRAR Y TOMAR CARRITO
            estadoActual = "ESPERANDO_CARRITO";
            supermercado.entrarYTomarCarrito(clienteId);
            
            tiempoEntrada = System.currentTimeMillis();
            estadoActual = "COMPRANDO";
            
            // 3. REALIZAR COMPRAS
            tiempoInicioCompras = System.currentTimeMillis();
            supermercado.realizarCompras(clienteId);
            tiempoFinCompras = System.currentTimeMillis();
            
            // 4. PAGAR EN CAJA
            estadoActual = "ESPERANDO_CAJA";
            tiempoInicioPago = System.currentTimeMillis();
            supermercado.pagarEnCaja(clienteId);
            tiempoFinPago = System.currentTimeMillis();
            
            // 5. SALIR Y DEVOLVER CARRITO
            estadoActual = "SALIENDO";
            supermercado.salirYDevolverCarrito(clienteId);
            tiempoSalida = System.currentTimeMillis();
            
            // 6. COMPLETAR PROCESO
            estadoActual = "COMPLETADO";
            completoCompras = true;
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] 🎉 Cliente " + clienteId + 
                             " completó todo el proceso " +
                             "(Tiempo total en supermercado: " + getTiempoTotalEnSupermercado() + "ms)");
            
        } catch (InterruptedException e) {
            estadoActual = "INTERRUMPIDO";
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("[" + tiempo + "] ❌ Cliente " + clienteId + 
                             " interrumpido en estado: " + estadoActual);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            estadoActual = "ERROR";
            tiempo = LocalTime.now().format(timeFormatter);
            System.err.println("[" + tiempo + "] 💥 Error en Cliente " + clienteId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene el ID del cliente
     * 
     * @return ID del cliente
     */
    public int getClienteId() {
        return clienteId;
    }
    
    /**
     * Verifica si completó todo el proceso de compras
     * 
     * @return true si completó las compras, false en caso contrario
     */
    public boolean isCompletoCompras() {
        return completoCompras;
    }
    
    /**
     * Obtiene el estado actual del cliente
     * 
     * @return Estado actual como String
     */
    public String getEstadoActual() {
        return estadoActual;
    }
    
    /**
     * Obtiene el tiempo total que estuvo en el supermercado
     * 
     * @return Tiempo en milisegundos desde llegada hasta salida
     */
    public long getTiempoTotalEnSupermercado() {
        if (tiempoSalida > 0 && tiempoLlegada > 0) {
            return tiempoSalida - tiempoLlegada;
        }
        return -1; // No completó el proceso
    }
    
    /**
     * Obtiene el tiempo de espera para obtener carrito
     * 
     * @return Tiempo en milisegundos desde llegada hasta entrada
     */
    public long getTiempoEsperaCarrito() {
        if (tiempoEntrada > 0 && tiempoLlegada > 0) {
            return tiempoEntrada - tiempoLlegada;
        }
        return -1; // No obtuvo carrito aún
    }
    
    /**
     * Obtiene el tiempo que tardó en hacer las compras
     * 
     * @return Tiempo en milisegundos de duración de las compras
     */
    public long getTiempoCompras() {
        if (tiempoFinCompras > 0 && tiempoInicioCompras > 0) {
            return tiempoFinCompras - tiempoInicioCompras;
        }
        return -1; // No completó las compras
    }
    
    /**
     * Obtiene el tiempo de espera para pagar en caja
     * 
     * @return Tiempo en milisegundos desde fin de compras hasta inicio de pago
     */
    public long getTiempoEsperaCaja() {
        if (tiempoInicioPago > 0 && tiempoFinCompras > 0) {
            return tiempoInicioPago - tiempoFinCompras;
        }
        return -1; // No llegó a la caja aún
    }
    
    /**
     * Obtiene el tiempo que tardó en pagar
     * 
     * @return Tiempo en milisegundos de duración del pago
     */
    public long getTiempoPago() {
        if (tiempoFinPago > 0 && tiempoInicioPago > 0) {
            return tiempoFinPago - tiempoInicioPago;
        }
        return -1; // No completó el pago
    }
    
    /**
     * Verifica si tuvo que esperar para obtener carrito
     * 
     * @return true si esperó más de 500ms, false en caso contrario
     */
    public boolean tuvoQueEsperarCarrito() {
        return getTiempoEsperaCarrito() > 500; // Más de 500ms se considera espera significativa
    }
    
    /**
     * Verifica si tuvo que esperar para pagar en caja
     * 
     * @return true si esperó más de 500ms, false en caso contrario
     */
    public boolean tuvoQueEsperarCaja() {
        return getTiempoEsperaCaja() > 500; // Más de 500ms se considera espera significativa
    }
    
    /**
     * Calcula la eficiencia del proceso (tiempo útil vs tiempo total)
     * 
     * @return Porcentaje de eficiencia (0-100)
     */
    public double calcularEficiencia() {
        if (!completoCompras) {
            return 0.0;
        }
        
        long tiempoTotal = getTiempoTotalEnSupermercado();
        long tiempoUtil = getTiempoCompras() + getTiempoPago(); // Tiempo comprando + pagando
        
        if (tiempoTotal <= 0) {
            return 0.0;
        }
        
        return (tiempoUtil * 100.0) / tiempoTotal;
    }
    
    /**
     * Obtiene el tiempo transcurrido desde la llegada
     * 
     * @return Tiempo en milisegundos desde la llegada
     */
    public long getTiempoTranscurrido() {
        if (tiempoLlegada > 0) {
            return System.currentTimeMillis() - tiempoLlegada;
        }
        return 0;
    }
    
    /**
     * Obtiene estadísticas detalladas del cliente
     * 
     * @return String con estadísticas completas
     */
    public String getEstadisticasDetalladas() {
        StringBuilder stats = new StringBuilder();
        stats.append("Cliente ").append(clienteId).append(": ");
        
        if (completoCompras) {
            stats.append("COMPLETADO");
            stats.append(" | Tiempo total: ").append(getTiempoTotalEnSupermercado()).append("ms");
            stats.append(" | Espera carrito: ").append(getTiempoEsperaCarrito()).append("ms");
            stats.append(" | Tiempo compras: ").append(getTiempoCompras()).append("ms");
            stats.append(" | Espera caja: ").append(getTiempoEsperaCaja()).append("ms");
            stats.append(" | Tiempo pago: ").append(getTiempoPago()).append("ms");
            stats.append(" | Eficiencia: ").append(String.format("%.1f%%", calcularEficiencia()));
            
            if (tuvoQueEsperarCarrito()) {
                stats.append(" | ⏳ Esperó carrito");
            }
            if (tuvoQueEsperarCaja()) {
                stats.append(" | ⏳ Esperó caja");
            }
            if (!tuvoQueEsperarCarrito() && !tuvoQueEsperarCaja()) {
                stats.append(" | ⚡ Sin esperas");
            }
        } else {
            stats.append(estadoActual);
            if (tiempoLlegada > 0) {
                stats.append(" | Tiempo transcurrido: ").append(getTiempoTranscurrido()).append("ms");
            }
        }
        
        return stats.toString();
    }
    
    /**
     * Obtiene información resumida del cliente
     * 
     * @return String con información básica
     */
    public String getInfoResumida() {
        String estado = completoCompras ? "Completado" : estadoActual;
        String tiempoInfo = completoCompras ? 
            String.format("(%dms)", getTiempoTotalEnSupermercado()) :
            String.format("(%dms transcurridos)", getTiempoTranscurrido());
        
        return String.format("Cliente %d[%s %s]", clienteId, estado, tiempoInfo);
    }
    
    /**
     * Obtiene información sobre la experiencia del cliente
     * 
     * @return String con evaluación de la experiencia
     */
    public String getExperienciaCliente() {
        if (!completoCompras) {
            return "En proceso";
        }
        
        long tiempoEsperaTotal = getTiempoEsperaCarrito() + getTiempoEsperaCaja();
        
        if (tiempoEsperaTotal < 1000) {
            return "✅ Excelente (sin esperas significativas)";
        } else if (tiempoEsperaTotal < 5000) {
            return "✅ Buena (esperas cortas)";
        } else if (tiempoEsperaTotal < 15000) {
            return "⚠️ Regular (esperas moderadas)";
        } else {
            return "❌ Mala (esperas largas)";
        }
    }
    
    /**
     * Analiza en qué fase del proceso gastó más tiempo
     * 
     * @return String con análisis del tiempo
     */
    public String analizarTiempos() {
        if (!completoCompras) {
            return "Proceso incompleto";
        }
        
        long tiempoEsperaCarrito = getTiempoEsperaCarrito();
        long tiempoCompras = getTiempoCompras();
        long tiempoEsperaCaja = getTiempoEsperaCaja();
        long tiempoPago = getTiempoPago();
        
        long tiempoMaximo = Math.max(Math.max(tiempoEsperaCarrito, tiempoCompras), 
                                   Math.max(tiempoEsperaCaja, tiempoPago));
        
        if (tiempoMaximo == tiempoCompras) {
            return "Mayor tiempo en: 🛒 Compras (" + tiempoCompras + "ms)";
        } else if (tiempoMaximo == tiempoEsperaCarrito) {
            return "Mayor tiempo en: ⏳ Espera de carrito (" + tiempoEsperaCarrito + "ms)";
        } else if (tiempoMaximo == tiempoEsperaCaja) {
            return "Mayor tiempo en: ⏳ Espera de caja (" + tiempoEsperaCaja + "ms)";
        } else {
            return "Mayor tiempo en: 💳 Pago (" + tiempoPago + "ms)";
        }
    }
    
    /**
     * Compara su experiencia con otro cliente
     * 
     * @param otroCliente Otro cliente para comparar
     * @return String con comparación
     */
    public String compararCon(Cliente otroCliente) {
        if (!this.completoCompras || !otroCliente.completoCompras) {
            return "No se puede comparar: alguno no completó el proceso";
        }
        
        long miTiempo = this.getTiempoTotalEnSupermercado();
        long otroTiempo = otroCliente.getTiempoTotalEnSupermercado();
        
        String resultado = String.format("Cliente %d (%dms) vs Cliente %d (%dms): ", 
                                       this.clienteId, miTiempo,
                                       otroCliente.clienteId, otroTiempo);
        
        if (miTiempo < otroTiempo * 0.9) {
            resultado += "🏆 Cliente " + this.clienteId + " fue más rápido";
        } else if (otroTiempo < miTiempo * 0.9) {
            resultado += "🏆 Cliente " + otroCliente.clienteId + " fue más rápido";
        } else {
            resultado += "⚖️ Tiempos similares";
        }
        
        return resultado;
    }
    
    /**
     * Método toString para representación del cliente
     * 
     * @return Representación string del cliente
     */
    @Override
    public String toString() {
        return String.format("Cliente[ID:%d, Estado:%s, Completado:%s]", 
                           clienteId, estadoActual, completoCompras);
    }
}
