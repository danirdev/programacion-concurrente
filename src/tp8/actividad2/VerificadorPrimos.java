package tp8.actividad2;

/**
 * 🔢 VerificadorPrimos - Utilidad para verificar números primos
 * 
 * Esta clase proporciona métodos estáticos para verificar si un número
 * es primo de forma eficiente.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class VerificadorPrimos {
    
    /**
     * ✅ Verificar si un número es primo
     * 
     * @param n Número a verificar
     * @return true si el número es primo, false en caso contrario
     */
    public static boolean esPrimo(int n) {
        // Casos especiales
        if (n <= 1) return false;
        if (n <= 3) return true;
        
        // Eliminar múltiplos de 2 y 3
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        // Verificar divisores hasta √n
        // Solo verificamos números de la forma 6k±1
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 📊 Obtener información sobre un número
     * 
     * @param n Número a analizar
     * @return String con información del número
     */
    public static String getInfoNumero(int n) {
        boolean primo = esPrimo(n);
        StringBuilder info = new StringBuilder();
        
        info.append(String.format("Número: %d → ", n));
        
        if (primo) {
            info.append("✨ ES PRIMO");
        } else {
            info.append("❌ NO es primo");
            
            // Encontrar algunos factores si no es primo
            if (n > 1) {
                info.append(" (Factores: ");
                boolean primero = true;
                for (int i = 2; i <= Math.min(n, 100); i++) {
                    if (n % i == 0) {
                        if (!primero) info.append(", ");
                        info.append(i);
                        primero = false;
                        if (!primero && i > 10) {
                            info.append("...");
                            break;
                        }
                    }
                }
                info.append(")");
            }
        }
        
        return info.toString();
    }
    
    /**
     * 🔢 Contar números primos en un rango
     * 
     * @param inicio Inicio del rango (inclusivo)
     * @param fin Fin del rango (inclusivo)
     * @return Cantidad de números primos en el rango
     */
    public static int contarPrimosEnRango(int inicio, int fin) {
        int contador = 0;
        for (int i = inicio; i <= fin; i++) {
            if (esPrimo(i)) {
                contador++;
            }
        }
        return contador;
    }
    
    /**
     * 📋 Obtener lista de primos en un rango
     * 
     * @param inicio Inicio del rango (inclusivo)
     * @param fin Fin del rango (inclusivo)
     * @return String con lista de primos
     */
    public static String listarPrimosEnRango(int inicio, int fin) {
        StringBuilder lista = new StringBuilder();
        lista.append(String.format("Primos entre %d y %d: ", inicio, fin));
        
        boolean primero = true;
        int contador = 0;
        
        for (int i = inicio; i <= fin && contador < 20; i++) {
            if (esPrimo(i)) {
                if (!primero) lista.append(", ");
                lista.append(i);
                primero = false;
                contador++;
            }
        }
        
        if (contador == 20) {
            lista.append("...");
        }
        
        return lista.toString();
    }
}
