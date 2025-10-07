package tp7.actividad5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 🔄 SincronizacionOrdenSimulacion - Simulación Principal R I O OK OK OK
 * 
 * Esta clase implementa la simulación completa del problema de sincronización
 * con orden específico, ejecutando múltiples corridas y verificando que la
 * salida sea siempre " R  I  O  OK  OK  OK ".
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class SincronizacionOrdenSimulacion {
    
    // ⚙️ Configuración de la simulación
    private static final int NUMERO_EJECUCIONES = 10;
    private static final String SEPARADOR = "=".repeat(80);
    
    /**
     * 🚀 Método principal - Punto de entrada de la simulación
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(SEPARADOR);
        System.out.println("🔄 SIMULACIÓN: SINCRONIZACIÓN R I O OK OK OK");
        System.out.println(SEPARADOR);
        
        SincronizacionOrdenSimulacion simulacion = new SincronizacionOrdenSimulacion();
        
        // 📋 Mostrar información inicial
        simulacion.mostrarInformacionInicial();
        
        // 🏃‍♂️ Ejecutar múltiples corridas
        List<ResultadoEjecucion> resultados = simulacion.ejecutarMultiplesEjecuciones(NUMERO_EJECUCIONES);
        
        // 📊 Analizar resultados
        simulacion.analizarResultados(resultados);
        
        System.out.println(SEPARADOR);
        System.out.println("✅ SIMULACIÓN COMPLETADA");
        System.out.println(SEPARADOR);
    }
    
    /**
     * 📋 Mostrar información inicial
     */
    private void mostrarInformacionInicial() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        
        System.out.println("📅 Fecha y Hora: " + fechaHora);
        System.out.println("🎯 Objetivo: Garantizar salida \"R I O OK OK OK\"");
        System.out.println("🔬 Mecanismo: Semáforos + Barrera de sincronización");
        System.out.println("📊 Número de ejecuciones: " + NUMERO_EJECUCIONES);
        System.out.println();
    }
    
    /**
     * 🏃‍♂️ Ejecutar múltiples ejecuciones
     * 
     * @param numeroEjecuciones Número de ejecuciones a realizar
     * @return Lista de resultados
     */
    private List<ResultadoEjecucion> ejecutarMultiplesEjecuciones(int numeroEjecuciones) {
        List<ResultadoEjecucion> resultados = new ArrayList<>();
        VerificadorSalida verificador = new VerificadorSalida();
        
        System.out.println("🚀 INICIANDO EJECUCIONES...\n");
        
        for (int i = 1; i <= numeroEjecuciones; i++) {
            System.out.printf("🔄 === EJECUCIÓN #%d/%d ===%n", i, numeroEjecuciones);
            
            ResultadoEjecucion resultado = ejecutarEjecucionIndividual(i, verificador);
            resultados.add(resultado);
            
            System.out.println();
            
            // Pausa breve entre ejecuciones
            if (i < numeroEjecuciones) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        return resultados;
    }
    
    /**
     * 🏃‍♂️ Ejecutar una ejecución individual
     * 
     * @param numeroEjecucion Número de la ejecución
     * @param verificador Verificador de salida
     * @return Resultado de la ejecución
     */
    private ResultadoEjecucion ejecutarEjecucionIndividual(int numeroEjecucion, VerificadorSalida verificador) {
        ResultadoEjecucion resultado = new ResultadoEjecucion(numeroEjecucion);
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // 🔄 Crear sincronizador
            SincronizadorOrden sincronizador = new SincronizadorOrden();
            
            // 👥 Crear hilos
            Thread hiloR = new Thread(() -> sincronizador.ejecutarHiloR("HiloR"), "HiloR");
            Thread hiloI = new Thread(() -> sincronizador.ejecutarHiloI("HiloI"), "HiloI");
            Thread hiloO = new Thread(() -> sincronizador.ejecutarHiloO("HiloO"), "HiloO");
            
            // ▶️ Iniciar hilos
            hiloR.start();
            hiloI.start();
            hiloO.start();
            
            // ⏳ Esperar finalización
            hiloR.join();
            hiloI.join();
            hiloO.join();
            
            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            
            // 📊 Recopilar resultados
            String salida = sincronizador.getSalida();
            boolean correcta = verificador.verificar(salida);
            
            resultado.salida = salida;
            resultado.tiempoEjecucion = tiempoTotal;
            resultado.exitosa = correcta;
            
            if (correcta) {
                sincronizador.registrarEjecucionExitosa();
            } else {
                sincronizador.registrarEjecucionFallida();
            }
            
            // 📋 Mostrar resumen
            mostrarResumenEjecucion(numeroEjecucion, resultado);
            
        } catch (Exception e) {
            System.err.printf("❌ Error en ejecución #%d: %s%n", numeroEjecucion, e.getMessage());
            resultado.exitosa = false;
            resultado.error = e.getMessage();
        }
        
        return resultado;
    }
    
    /**
     * 📋 Mostrar resumen de una ejecución
     * 
     * @param numeroEjecucion Número de la ejecución
     * @param resultado Resultado de la ejecución
     */
    private void mostrarResumenEjecucion(int numeroEjecucion, ResultadoEjecucion resultado) {
        String icono = resultado.exitosa ? "✅" : "❌";
        System.out.printf("\n[EJECUCIÓN #%d] %s RESULTADO:%n", numeroEjecucion, icono);
        System.out.printf("   📝 Salida: \"%s\"%n", resultado.salida);
        System.out.printf("   ⏱️ Tiempo: %dms%n", resultado.tiempoEjecucion);
        System.out.printf("   📊 Estado: %s%n", resultado.exitosa ? "CORRECTA" : "INCORRECTA");
    }
    
    /**
     * 📊 Analizar resultados de todas las ejecuciones
     * 
     * @param resultados Lista de resultados
     */
    private void analizarResultados(List<ResultadoEjecucion> resultados) {
        System.out.println("\n📊 === ANÁLISIS DE RESULTADOS ===\n");
        
        // 📋 Tabla de resultados
        mostrarTablaResultados(resultados);
        
        // 📈 Estadísticas generales
        mostrarEstadisticasGenerales(resultados);
        
        // 🔍 Análisis de consistencia
        analizarConsistencia(resultados);
        
        // 🎓 Conclusiones
        mostrarConclusiones(resultados);
    }
    
    /**
     * 📋 Mostrar tabla de resultados
     * 
     * @param resultados Lista de resultados
     */
    private void mostrarTablaResultados(List<ResultadoEjecucion> resultados) {
        System.out.println("📊 TABLA DE RESULTADOS:");
        System.out.printf("%-12s %-25s %-12s %-12s%n", "EJECUCIÓN", "SALIDA", "TIEMPO(ms)", "ESTADO");
        System.out.println("-".repeat(65));
        
        for (ResultadoEjecucion resultado : resultados) {
            String icono = resultado.exitosa ? "✅" : "❌";
            String salida = resultado.salida.length() > 20 ? 
                resultado.salida.substring(0, 20) + "..." : resultado.salida;
            
            System.out.printf("%-12s %-25s %-12d %s %-10s%n",
                             "#" + resultado.numeroEjecucion,
                             "\"" + salida + "\"",
                             resultado.tiempoEjecucion,
                             icono,
                             resultado.exitosa ? "OK" : "ERROR");
        }
        
        System.out.println("-".repeat(65));
    }
    
    /**
     * 📈 Mostrar estadísticas generales
     * 
     * @param resultados Lista de resultados
     */
    private void mostrarEstadisticasGenerales(List<ResultadoEjecucion> resultados) {
        System.out.println("\n📈 ESTADÍSTICAS GENERALES:");
        
        // Contar exitosas
        long exitosas = resultados.stream().filter(r -> r.exitosa).count();
        double tasaExito = (exitosas * 100.0) / resultados.size();
        
        // Calcular tiempos
        long tiempoTotal = resultados.stream().mapToLong(r -> r.tiempoEjecucion).sum();
        double tiempoPromedio = tiempoTotal / (double) resultados.size();
        long tiempoMin = resultados.stream().mapToLong(r -> r.tiempoEjecucion).min().orElse(0);
        long tiempoMax = resultados.stream().mapToLong(r -> r.tiempoEjecucion).max().orElse(0);
        
        System.out.printf("   🔢 Total ejecuciones: %d%n", resultados.size());
        System.out.printf("   ✅ Ejecuciones exitosas: %d%n", exitosas);
        System.out.printf("   ❌ Ejecuciones fallidas: %d%n", resultados.size() - exitosas);
        System.out.printf("   📊 Tasa de éxito: %.1f%%%n", tasaExito);
        System.out.printf("   ⏱️ Tiempo promedio: %.2fms%n", tiempoPromedio);
        System.out.printf("   ⚡ Tiempo mínimo: %dms%n", tiempoMin);
        System.out.printf("   🐌 Tiempo máximo: %dms%n", tiempoMax);
    }
    
    /**
     * 🔍 Analizar consistencia de resultados
     * 
     * @param resultados Lista de resultados
     */
    private void analizarConsistencia(List<ResultadoEjecucion> resultados) {
        System.out.println("\n🔍 ANÁLISIS DE CONSISTENCIA:");
        
        // Verificar si todas las salidas son iguales
        String salidaEsperada = VerificadorSalida.getSalidaEsperada();
        boolean todasIguales = resultados.stream()
            .allMatch(r -> salidaEsperada.equals(r.salida));
        
        if (todasIguales) {
            System.out.println("   ✅ PERFECTA CONSISTENCIA: Todas las salidas son idénticas");
        } else {
            System.out.println("   ⚠️ INCONSISTENCIA DETECTADA: Hay salidas diferentes");
            
            // Mostrar salidas únicas
            resultados.stream()
                .map(r -> r.salida)
                .distinct()
                .forEach(salida -> System.out.printf("      - \"%s\"%n", salida));
        }
        
        // Verificar variabilidad de tiempos
        double[] tiempos = resultados.stream().mapToDouble(r -> r.tiempoEjecucion).toArray();
        double desviacion = calcularDesviacionEstandar(tiempos);
        double coeficienteVariacion = (desviacion / calcularMedia(tiempos)) * 100;
        
        System.out.printf("   📊 Desviación estándar tiempos: %.2fms%n", desviacion);
        System.out.printf("   📊 Coeficiente de variación: %.1f%%%n", coeficienteVariacion);
        
        if (coeficienteVariacion < 20) {
            System.out.println("   ✅ ALTA CONSISTENCIA en tiempos de ejecución");
        } else if (coeficienteVariacion < 40) {
            System.out.println("   ⚠️ CONSISTENCIA MEDIA en tiempos de ejecución");
        } else {
            System.out.println("   ❌ BAJA CONSISTENCIA en tiempos de ejecución");
        }
    }
    
    /**
     * 🎓 Mostrar conclusiones
     * 
     * @param resultados Lista de resultados
     */
    private void mostrarConclusiones(List<ResultadoEjecucion> resultados) {
        System.out.println("\n🎓 CONCLUSIONES:");
        
        long exitosas = resultados.stream().filter(r -> r.exitosa).count();
        double tasaExito = (exitosas * 100.0) / resultados.size();
        
        if (tasaExito == 100.0) {
            System.out.println("   🏆 ÉXITO TOTAL: Todas las ejecuciones fueron correctas");
            System.out.println("   ✅ Los semáforos garantizan el orden R → I → O");
            System.out.println("   ✅ La barrera sincroniza correctamente las fases");
            System.out.println("   ✅ Sin race conditions detectadas");
            System.out.println("   ✅ Sin deadlocks observados");
            System.out.println("   ✅ Sincronización ROBUSTA y CONFIABLE");
        } else if (tasaExito >= 80.0) {
            System.out.printf("   ⚠️ ÉXITO PARCIAL: %.1f%% de ejecuciones correctas%n", tasaExito);
            System.out.println("   ⚠️ Se detectaron algunas inconsistencias");
            System.out.println("   🔍 Revisar implementación de la barrera");
        } else {
            System.out.printf("   ❌ PROBLEMAS GRAVES: Solo %.1f%% de éxito%n", tasaExito);
            System.out.println("   ❌ La sincronización NO es confiable");
            System.out.println("   🔍 Revisar implementación completa");
        }
        
        System.out.println("\n💡 LECCIONES APRENDIDAS:");
        System.out.println("   📚 Cadena de dependencias: semI y semO crean orden R→I→O");
        System.out.println("   📚 Barrera de sincronización: Separa fase de letras y OK");
        System.out.println("   📚 Semáforos permiten control PRECISO del orden de ejecución");
        System.out.println("   📚 La sincronización explícita es PREDECIBLE y VERIFICABLE");
    }
    
    /**
     * 📊 Calcular media de un array
     * 
     * @param valores Array de valores
     * @return Media
     */
    private double calcularMedia(double[] valores) {
        double suma = 0;
        for (double valor : valores) {
            suma += valor;
        }
        return suma / valores.length;
    }
    
    /**
     * 📊 Calcular desviación estándar
     * 
     * @param valores Array de valores
     * @return Desviación estándar
     */
    private double calcularDesviacionEstandar(double[] valores) {
        double media = calcularMedia(valores);
        double sumaCuadrados = 0;
        
        for (double valor : valores) {
            sumaCuadrados += Math.pow(valor - media, 2);
        }
        
        return Math.sqrt(sumaCuadrados / valores.length);
    }
    
    /**
     * 📊 Clase interna para resultado de ejecución
     */
    private static class ResultadoEjecucion {
        int numeroEjecucion;
        String salida;
        long tiempoEjecucion;
        boolean exitosa;
        String error;
        
        ResultadoEjecucion(int numeroEjecucion) {
            this.numeroEjecucion = numeroEjecucion;
            this.salida = "";
            this.tiempoEjecucion = 0;
            this.exitosa = false;
            this.error = null;
        }
        
        @Override
        public String toString() {
            return String.format("Resultado{#%d, salida='%s', tiempo=%dms, exitosa=%s}", 
                               numeroEjecucion, salida, tiempoEjecucion, exitosa);
        }
    }
}
