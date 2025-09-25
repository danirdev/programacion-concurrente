# TP3 - Actividad 3 - Contador con Retardo Aleatorio

## Enunciado

**3)** Sobre el inciso anterior agregue un retardo aleatorio entre 50-150ms y observe los resultados. **NOTA**: para los puntos 2 y 3 pruebe realizando una pausa o empleando el método join.

---

## Análisis del Problema

### Modificaciones Respecto a la Actividad 2
- **Retardo aleatorio**: Entre 50-150ms en cada operación
- **Mayor probabilidad de race conditions**: El retardo aumenta la ventana de tiempo para intercalación
- **Observación de efectos**: Comparar resultados con y sin join()
- **Timing crítico**: El retardo hace más visible el comportamiento concurrente

### Conceptos Demostrados
- **Efecto del timing** en race conditions
- **Importancia de join()** para sincronización
- **Variabilidad aumentada** en resultados
- **Scheduling del SO** más evidente con retardos

---

## Implementación

### Estructura de Clases

1. **`ContadorConRetardo.java`** - Contador con retardo aleatorio (sin sincronización)
2. **`ContadorSincronizadoConRetardo.java`** - Versión sincronizada con retardo
3. **`HiloIncrementadorConRetardo.java`** - Hilo incrementador con retardo
4. **`HiloDecrementadorConRetardo.java`** - Hilo decrementador con retardo
5. **`ExperimentoRetardo.java`** - Experimentos con y sin join()
6. **`TestRetardo.java`** - Pruebas básicas con retardo

### Características del Retardo
- **Rango**: 50-150 milisegundos
- **Distribución**: Uniforme aleatoria
- **Ubicación**: Después de cada operación de incremento/decremento
- **Propósito**: Simular operaciones más lentas y aumentar race conditions

---

## Cómo Ejecutar

### Opción 1: Prueba Básica
```bash
javac tp3/actividad3/*.java
java tp3.actividad3.TestRetardo
```

### Opción 2: Experimento Completo
```bash
javac tp3/actividad3/*.java
java tp3.actividad3.ExperimentoRetardo
```

---

## Resultados Esperados

### Sin join()
- **Terminación prematura**: El programa principal puede terminar antes que los hilos
- **Salida incompleta**: No se ven todos los resultados
- **Valores intermedios**: Se pueden observar estados parciales

### Con join()
- **Terminación completa**: Todos los hilos terminan antes de mostrar resultado final
- **Salida completa**: Se observan todas las operaciones
- **Resultado final correcto**: Con sincronización adecuada

### Efecto del Retardo
- **Mayor variabilidad**: Más race conditions visibles
- **Timing más evidente**: Se puede observar la intercalación de hilos
- **Resultados más dispersos**: Mayor rango de valores finales sin sincronización

---

## Análisis Comparativo

### Actividad 2 vs Actividad 3
- **Sin retardo**: Race conditions rápidas, menos visibles
- **Con retardo**: Race conditions más evidentes y frecuentes
- **join() más crítico**: Con retardo, join() es aún más importante
- **Observabilidad**: El retardo hace el comportamiento más observable

Este ejercicio demuestra cómo el timing afecta la concurrencia y la importancia crítica de la sincronización apropiada.
