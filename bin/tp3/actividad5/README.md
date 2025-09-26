# TP3 - Actividad 5 - Cálculo Intensivo con Hilos

## Enunciado

**5)** Implemente la ejecución del siguiente procedimiento:

```java
public static double SumRootN(int root) {
    double result = 0;
    for (int i = 0; i < 10000000; i++) {
        result += Math.exp(Math.log(i) / root);
    }
    return result;
}
```

**a.** Se le solicita que lo ejecute 20 veces de forma secuencial (para root entre [1-20]) y calcule el tiempo de ejecución, y además revise el "Administrador de tareas" de Windows para comprobar la utilización de la/las CPU/s.

**b.** Adicionalmente, implemente el mismo procedimiento pero mediante Hilos, ejecutándolo 20 veces y revisando el "Administrador de tareas" de Windows para comprobar la utilización de la/las CPU/s.

**c.** Indique el tiempo de ejecución de ambas alternativas. Comente los resultados.

---

## Análisis del Problema

### Descripción del Algoritmo
- **Función SumRootN**: Calcula la suma de raíces n-ésimas usando logaritmos
- **Operación**: `Math.exp(Math.log(i) / root)` equivale a `i^(1/root)`
- **Iteraciones**: 10,000,000 por cada ejecución
- **Parámetro root**: Valores de 1 a 20
- **Carga computacional**: Muy intensiva (operaciones matemáticas complejas)

### Conceptos Demostrados
- **Cálculo intensivo** vs **I/O bound**
- **Paralelización** de tareas computacionales
- **Utilización de CPU** en sistemas multi-core
- **Speedup** y eficiencia de paralelización
- **Overhead** de creación y gestión de hilos

---

## Implementación

### Estructura de Clases

1. **`CalculadoraSecuencial.java`** - Implementación secuencial del algoritmo
2. **`CalculadoraConcurrente.java`** - Implementación con hilos
3. **`HiloCalculador.java`** - Hilo individual para un cálculo específico
4. **`MonitorRendimiento.java`** - Monitoreo de CPU y memoria
5. **`ComparadorRendimiento.java`** - Comparación y análisis de resultados
6. **`TestCalculoIntenso.java`** - Clase principal con experimentos

### Características de la Implementación
- **Ejecución secuencial**: Un cálculo tras otro
- **Ejecución concurrente**: 20 hilos simultáneos (uno por cada root)
- **Medición precisa**: Tiempo en nanosegundos
- **Monitoreo de recursos**: CPU y memoria durante ejecución
- **Análisis estadístico**: Speedup, eficiencia, overhead

---

## Cómo Ejecutar

### Opción 1: Comparación Completa
```bash
javac tp3/actividad5/*.java
java tp3.actividad5.ComparadorRendimiento
```

### Opción 2: Solo Secuencial
```bash
javac tp3/actividad5/*.java
java tp3.actividad5.CalculadoraSecuencial
```

### Opción 3: Solo Concurrente
```bash
javac tp3/actividad5/*.java
java tp3.actividad5.CalculadoraConcurrente
```

---

## Resultados Esperados

### Ejecución Secuencial
- **Tiempo**: ~X segundos (depende del hardware)
- **CPU**: Un solo núcleo al 100%, otros núcleos inactivos
- **Patrón**: Utilización secuencial de recursos

### Ejecución Concurrente
- **Tiempo**: ~X/N segundos (donde N es número de núcleos)
- **CPU**: Múltiples núcleos al 100%
- **Patrón**: Utilización paralela de recursos

### Análisis de Rendimiento
- **Speedup**: Tiempo_secuencial / Tiempo_concurrente
- **Eficiencia**: Speedup / Número_de_núcleos
- **Overhead**: Tiempo adicional por gestión de hilos

---

## Consideraciones Técnicas

### Algoritmo Matemático
```java
// Equivalencias matemáticas:
Math.exp(Math.log(i) / root) == Math.pow(i, 1.0/root) == i^(1/root)

// Para root=1: i^1 = i (suma de números naturales)
// Para root=2: i^(1/2) = √i (suma de raíces cuadradas)
// Para root=3: i^(1/3) = ∛i (suma de raíces cúbicas)
```

### Paralelización
- **Independencia**: Cada cálculo es independiente
- **Sin dependencias**: No hay variables compartidas
- **Ideal para paralelización**: CPU-bound sin sincronización

### Monitoreo de Sistema
- **Administrador de Tareas**: Observar utilización de CPU
- **Núcleos individuales**: Verificar distribución de carga
- **Memoria**: Monitorear uso durante ejecución

Este ejercicio demuestra claramente las ventajas de la paralelización en tareas computacionalmente intensivas.
