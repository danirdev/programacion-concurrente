package tp7.actividad5;

/**
 * ✅ VerificadorSalida - Verifica que la salida sea correcta
 * 
 * Esta clase verifica que la salida generada por los hilos sea exactamente
 * " R  I  O  OK  OK  OK " en ese orden específico.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class VerificadorSalida {
    
    // 🎯 Salida esperada
    private static final String SALIDA_ESPERADA = " R  I  O  OK  OK  OK ";
    
    // 📊 Estadísticas de verificaciones
    private int totalVerificaciones;
    private int verificacionesExitosas;
    private int verificacionesFallidas;
    
    /**
     * 🏗️ Constructor del VerificadorSalida
     */
    public VerificadorSalida() {
        this.totalVerificaciones = 0;
        this.verificacionesExitosas = 0;
        this.verificacionesFallidas = 0;
        
        System.out.println("✅ VerificadorSalida inicializado");
    }
    
    /**
     * 🔍 Verificar si la salida es correcta
     * 
     * @param salida Salida a verificar
     * @return true si la salida es correcta
     */
    public boolean verificar(String salida) {
        totalVerificaciones++;
        
        boolean correcta = SALIDA_ESPERADA.equals(salida);
        
        if (correcta) {
            verificacionesExitosas++;
            System.out.printf("✅ Salida CORRECTA: \"%s\"%n", salida);
        } else {
            verificacionesFallidas++;
            System.err.printf("❌ Salida INCORRECTA:%n");
            System.err.printf("   Esperada: \"%s\"%n", SALIDA_ESPERADA);
            System.err.printf("   Obtenida: \"%s\"%n", salida);
            
            // Análisis detallado del error
            analizarError(salida);
        }
        
        return correcta;
    }
    
    /**
     * 🔍 Analizar error en la salida
     * 
     * @param salida Salida incorrecta
     */
    private void analizarError(String salida) {
        System.err.println("📊 ANÁLISIS DEL ERROR:");
        
        // Verificar longitud
        if (salida.length() != SALIDA_ESPERADA.length()) {
            System.err.printf("   ⚠️ Longitud incorrecta: %d (esperada: %d)%n", 
                             salida.length(), SALIDA_ESPERADA.length());
        }
        
        // Verificar orden de letras
        if (!salida.contains(" R ")) {
            System.err.println("   ❌ Falta 'R'");
        } else if (!salida.contains(" I ")) {
            System.err.println("   ❌ Falta 'I'");
        } else if (!salida.contains(" O ")) {
            System.err.println("   ❌ Falta 'O'");
        } else {
            // Verificar orden
            int posR = salida.indexOf(" R ");
            int posI = salida.indexOf(" I ");
            int posO = salida.indexOf(" O ");
            
            if (posI < posR) {
                System.err.println("   ❌ 'I' aparece antes que 'R'");
            }
            if (posO < posI) {
                System.err.println("   ❌ 'O' aparece antes que 'I'");
            }
        }
        
        // Contar OK
        int contadorOK = contarOcurrencias(salida, " OK ");
        if (contadorOK != 3) {
            System.err.printf("   ❌ Número de OK incorrecto: %d (esperado: 3)%n", contadorOK);
        }
        
        // Verificar posición de OK (deben estar después de las letras)
        int ultimaLetra = Math.max(salida.lastIndexOf(" O "), 
                                  Math.max(salida.lastIndexOf(" R "), salida.lastIndexOf(" I ")));
        int primerOK = salida.indexOf(" OK ");
        
        if (primerOK < ultimaLetra) {
            System.err.println("   ❌ Algún OK aparece antes que todas las letras");
        }
    }
    
    /**
     * 🔢 Contar ocurrencias de un substring
     * 
     * @param texto Texto donde buscar
     * @param patron Patrón a buscar
     * @return Número de ocurrencias
     */
    private int contarOcurrencias(String texto, String patron) {
        int contador = 0;
        int indice = 0;
        
        while ((indice = texto.indexOf(patron, indice)) != -1) {
            contador++;
            indice += patron.length();
        }
        
        return contador;
    }
    
    /**
     * ✅ Verificar formato específico
     * 
     * @param salida Salida a verificar
     * @return Resultado detallado de la verificación
     */
    public ResultadoVerificacion verificarDetallado(String salida) {
        ResultadoVerificacion resultado = new ResultadoVerificacion();
        resultado.salidaObtenida = salida;
        resultado.salidaEsperada = SALIDA_ESPERADA;
        
        // Verificar igualdad exacta
        resultado.correcta = SALIDA_ESPERADA.equals(salida);
        
        // Verificar componentes
        resultado.tieneR = salida.contains(" R ");
        resultado.tieneI = salida.contains(" I ");
        resultado.tieneO = salida.contains(" O ");
        resultado.numeroOK = contarOcurrencias(salida, " OK ");
        
        // Verificar orden
        if (resultado.tieneR && resultado.tieneI && resultado.tieneO) {
            int posR = salida.indexOf(" R ");
            int posI = salida.indexOf(" I ");
            int posO = salida.indexOf(" O ");
            
            resultado.ordenCorrecto = (posR < posI) && (posI < posO);
        }
        
        // Verificar separación de fases
        if (resultado.tieneR && resultado.tieneI && resultado.tieneO && resultado.numeroOK == 3) {
            int ultimaLetra = Math.max(salida.lastIndexOf(" O "), 
                                      Math.max(salida.lastIndexOf(" R "), salida.lastIndexOf(" I ")));
            int primerOK = salida.indexOf(" OK ");
            
            resultado.fasesSeparadas = primerOK > ultimaLetra;
        }
        
        // Actualizar estadísticas
        totalVerificaciones++;
        if (resultado.correcta) {
            verificacionesExitosas++;
        } else {
            verificacionesFallidas++;
        }
        
        return resultado;
    }
    
    /**
     * 📊 Obtener estadísticas del verificador
     * 
     * @return String con estadísticas
     */
    public String getEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== ✅ ESTADÍSTICAS VERIFICADOR ===\n");
        stats.append(String.format("🔢 Total verificaciones: %d%n", totalVerificaciones));
        stats.append(String.format("✅ Verificaciones exitosas: %d%n", verificacionesExitosas));
        stats.append(String.format("❌ Verificaciones fallidas: %d%n", verificacionesFallidas));
        
        if (totalVerificaciones > 0) {
            double tasaExito = (verificacionesExitosas * 100.0) / totalVerificaciones;
            stats.append(String.format("📊 Tasa de éxito: %.1f%%%n", tasaExito));
        }
        
        return stats.toString();
    }
    
    /**
     * 🔄 Resetear estadísticas
     */
    public void resetearEstadisticas() {
        totalVerificaciones = 0;
        verificacionesExitosas = 0;
        verificacionesFallidas = 0;
        System.out.println("🔄 Estadísticas del verificador reseteadas");
    }
    
    // 🔧 Getters
    
    public static String getSalidaEsperada() { return SALIDA_ESPERADA; }
    
    public int getTotalVerificaciones() { return totalVerificaciones; }
    
    public int getVerificacionesExitosas() { return verificacionesExitosas; }
    
    public int getVerificacionesFallidas() { return verificacionesFallidas; }
    
    public double getTasaExito() {
        return totalVerificaciones > 0 ? 
            (verificacionesExitosas * 100.0) / totalVerificaciones : 0;
    }
    
    /**
     * 📝 Representación en string del verificador
     * 
     * @return Información del verificador
     */
    @Override
    public String toString() {
        return String.format("VerificadorSalida{total=%d, exitosas=%d, fallidas=%d, tasa=%.1f%%}", 
                           totalVerificaciones, verificacionesExitosas, verificacionesFallidas, getTasaExito());
    }
    
    /**
     * 📊 Clase interna para resultado detallado
     */
    public static class ResultadoVerificacion {
        public String salidaEsperada;
        public String salidaObtenida;
        public boolean correcta;
        public boolean tieneR;
        public boolean tieneI;
        public boolean tieneO;
        public int numeroOK;
        public boolean ordenCorrecto;
        public boolean fasesSeparadas;
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ResultadoVerificacion{\n");
            sb.append(String.format("  correcta=%s%n", correcta));
            sb.append(String.format("  letras=[R:%s, I:%s, O:%s]%n", tieneR, tieneI, tieneO));
            sb.append(String.format("  numeroOK=%d%n", numeroOK));
            sb.append(String.format("  ordenCorrecto=%s%n", ordenCorrecto));
            sb.append(String.format("  fasesSeparadas=%s%n", fasesSeparadas));
            sb.append("}");
            return sb.toString();
        }
    }
}
