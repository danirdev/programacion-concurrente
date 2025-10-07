package tp8.actividad5;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * 🔢 TareaCalculo - Callable que ejecuta cálculo intensivo
 * 
 * Esta clase implementa Callable<BigInteger> y ejecuta la función compute()
 * que realiza un cálculo intensivo que demora varios segundos.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class TareaCalculo implements Callable<BigInteger> {
    
    // 🔢 Número a procesar
    private final long numero;
    private final int indice;
    
    // 🧮 Módulo para el cálculo
    private static final BigInteger M = new BigInteger("1999");
    
    // ⏱️ Tiempos
    private long tiempoInicio;
    private long tiempoFin;
    
    /**
     * 🏗️ Constructor de TareaCalculo
     * 
     * @param numero Número a procesar
     * @param indice Índice en el arreglo
     */
    public TareaCalculo(long numero, int indice) {
        this.numero = numero;
        this.indice = indice;
    }
    
    /**
     * 🏃‍♂️ Método call - Ejecuta el cálculo y retorna resultado
     * 
     * @return Resultado del cálculo como BigInteger
     * @throws Exception Si ocurre algún error
     */
    @Override
    public BigInteger call() throws Exception {
        tiempoInicio = System.currentTimeMillis();
        String nombreThread = Thread.currentThread().getName();
        
        System.out.printf("[%s] 🔄 Procesando elemento %d: %d%n", 
                         nombreThread, indice, numero);
        
        // 🧮 Ejecutar cálculo intensivo
        BigInteger resultado = compute(numero);
        
        tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        
        System.out.printf("[%s] ✅ Elemento %d completado: resultado = %s (tiempo: %.2fs)%n", 
                         nombreThread, indice, resultado, tiempoTotal / 1000.0);
        
        return resultado;
    }
    
    /**
     * 🧮 Función compute - Cálculo intensivo
     * 
     * @param n Número a procesar
     * @return Resultado del cálculo mod M
     */
    private static BigInteger compute(long n) {
        String s = "";
        for (long i = 0; i < n; i++) {
            s = s + n;
        }
        return new BigInteger(s.toString()).mod(M);
    }
    
    /**
     * ⏱️ Obtener tiempo de ejecución
     * 
     * @return Tiempo en milisegundos
     */
    public long getTiempoEjecucion() {
        return tiempoFin - tiempoInicio;
    }
    
    // 🔧 Getters
    
    public long getNumero() { return numero; }
    
    public int getIndice() { return indice; }
    
    /**
     * 📝 Representación en string de la tarea
     * 
     * @return Información de la tarea
     */
    @Override
    public String toString() {
        return String.format("TareaCalculo{indice=%d, numero=%d}", indice, numero);
    }
}
