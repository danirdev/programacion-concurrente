# TP5 - Actividad 4: Multiplicación Concurrente de Matrices con Runnable

## Descripción del Problema

Implementación de un sistema de **multiplicación concurrente de matrices** que calcula A×B = C de forma paralela, donde cada hilo calcula elementos específicos de la matriz resultante. El sistema utiliza herencia y sincronización para coordinar el acceso a la matriz resultado.

## Enunciado Original

> Cree una rutina que realice la multiplicación de dos matrices (A×B = C) de forma concurrente. Para ello debe tener en cuenta lo siguiente:
> 
> a. La matriz A será de 20×15 y la matriz B será de 15×20, con lo cual la matriz C será de 20×20 previamente definida.
> 
> b. Ambas matrices deben inicializarse con números enteros aleatorios entre [5-15].
> 
> c. La matriz resultante (C) será el objeto a sincronizar.
> 
> d. Debe existir una clase (CalculoConcurrente) que implemente la interfaz Runnable que realice el cálculo de la multiplicación de los vectores (A[i][1-15] x B[1-15][j]), y cada resultado se irá ingresando en la matriz C según la conversión que se indica en el inciso "e", por ello la necesidad de sincronizar los accesos a este objeto.
> 
> e. Además la clase CalculoConcurrente debe heredar de la clase Operaciones que posee un método para cálculo de raíces que es el siguiente: [método de cálculo de raíces]

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                MULTIPLICACIÓN CONCURRENTE DE MATRICES           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │  MATRIZ A   │    │  MATRIZ B   │    │  MATRIZ C   │         │
│  │   20×15     │    │   15×20     │    │   20×20     │         │
│  │             │    │             │    │ (RESULTADO) │         │
│  │ [5-15] rand │    │ [5-15] rand │    │ SINCRONIZADA│         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│         │                   │                   ▲              │
│         │                   │                   │              │
│         ▼                   ▼                   │              │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │            HILOS CONCURRENTES                          │   │
│  │                                                         │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │   │
│  │  │ Hilo 1      │  │ Hilo 2      │  │ Hilo N      │    │   │
│  │  │ Calcula     │  │ Calcula     │  │ Calcula     │    │   │
│  │  │ C[0][0]     │  │ C[0][1]     │  │ C[i][j]     │    │   │
│  │  │ C[0][1]...  │  │ C[0][2]...  │  │ ...         │    │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘    │   │
│  │                                                         │   │
│  │  Cada hilo: A[i][1-15] × B[1-15][j] → C[i][j]        │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                │                                │
│                                ▼                                │
│                    ┌─────────────────────────────────────┐     │
│                    │        SINCRONIZACIÓN               │     │
│                    │                                     │     │
│                    │ • Acceso exclusivo a matriz C      │     │
│                    │ • Herencia de clase Operaciones    │     │
│                    │ • Cálculo de raíces                │     │
│                    └─────────────────────────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
```

### Características del Sistema

1. **Matrices Específicas**: A(20×15), B(15×20), C(20×20)
2. **Inicialización Aleatoria**: Valores entre 5-15
3. **Concurrencia**: Múltiples hilos calculando elementos de C
4. **Sincronización**: Acceso controlado a matriz resultado
5. **Herencia**: CalculoConcurrente hereda de Operaciones

## Estructura del Proyecto

```
tp5/actividad4/
├── Operaciones.java             # Clase base con método de raíces
├── CalculoConcurrente.java      # Runnable que hereda de Operaciones
├── MatrizConcurrente.java       # Matriz C sincronizada
├── GestorMatrices.java          # Manejo de matrices A y B
├── MultiplicacionSimulacion.java # Clase principal de simulación
└── README.md                    # Esta documentación
```

## Diagrama de Multiplicación de Matrices

```
┌─────────────────────────────────────────────────────────────┐
│                 MULTIPLICACIÓN A × B = C                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│     A[20×15]           B[15×20]           C[20×20]         │
│                                                             │
│  ┌─ ─ ─ ─ ─ ─ ─┐   ┌─ ─ ─ ─ ─ ─ ─ ─ ─┐   ┌─ ─ ─ ─ ─ ─ ─┐ │
│  │ a₁₁ ... a₁₁₅│   │ b₁₁ ... b₁₂₀   │   │ c₁₁ ... c₁₂₀│ │
│  │ a₂₁ ... a₂₁₅│   │ ... ... ...     │   │ ... ... ... │ │
│  │ ... ... ... │ × │ ... ... ...     │ = │ ... ... ... │ │
│  │ ... ... ... │   │ ... ... ...     │   │ ... ... ... │ │
│  │ a₂₀₁... a₂₀₁₅│   │ b₁₅₁... b₁₅₂₀   │   │ c₂₀₁... c₂₀₂₀│ │
│  └─ ─ ─ ─ ─ ─ ─┘   └─ ─ ─ ─ ─ ─ ─ ─ ─┘   └─ ─ ─ ─ ─ ─ ─┘ │
│                                                             │
│  Cada elemento: C[i][j] = Σ(k=0 to 14) A[i][k] × B[k][j] │
└─────────────────────────────────────────────────────────────┘
```

## Algoritmo de Multiplicación Concurrente

### Distribución de Trabajo por Hilos

```
┌─────────────────────────────────────────────────────────────┐
│              ESTRATEGIAS DE PARALELIZACIÓN                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ESTRATEGIA 1: Por Filas                                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ Hilo 1: Calcula fila 0 completa (C[0][0] a C[0][19])│   │
│  │ Hilo 2: Calcula fila 1 completa (C[1][0] a C[1][19])│   │
│  │ ...                                                  │   │
│  │ Hilo 20: Calcula fila 19 completa                   │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ESTRATEGIA 2: Por Elementos                                │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ Hilo 1: C[0][0], C[0][4], C[0][8], ...             │   │
│  │ Hilo 2: C[0][1], C[0][5], C[0][9], ...             │   │
│  │ Hilo 3: C[0][2], C[0][6], C[0][10], ...            │   │
│  │ Hilo 4: C[0][3], C[0][7], C[0][11], ...            │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ESTRATEGIA 3: Por Bloques (Implementada)                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ Divide matriz C en bloques de trabajo equilibrados  │   │
│  │ Cada hilo procesa un rango de elementos            │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## Implementación Técnica

### Clase Base Operaciones

```java
public class Operaciones {
    /**
     * Método para cálculo de raíces (según enunciado)
     * @param numero Número del cual calcular la raíz
     * @return Raíz cuadrada del número
     */
    protected double calcularRaiz(double numero) {
        return Math.sqrt(numero);
    }
}
```

### Clase CalculoConcurrente

```java
public class CalculoConcurrente extends Operaciones implements Runnable {
    private int[][] matrizA;
    private int[][] matrizB;
    private MatrizConcurrente matrizC;
    private int filaInicio, filaFin;
    
    @Override
    public void run() {
        for (int i = filaInicio; i <= filaFin; i++) {
            for (int j = 0; j < 20; j++) {
                // Calcular A[i][1-15] × B[1-15][j]
                int resultado = calcularElemento(i, j);
                
                // Aplicar cálculo de raíces si es necesario
                double raiz = calcularRaiz(resultado);
                
                // Sincronizar acceso a matriz C
                matrizC.establecerElemento(i, j, resultado);
            }
        }
    }
}
```

## Casos de Uso Principales

### 1. Inicialización de Matrices
```
Matriz A[20×15] ← valores aleatorios [5-15]
Matriz B[15×20] ← valores aleatorios [5-15]
Matriz C[20×20] ← inicializada en ceros
```

### 2. Distribución de Trabajo
```
Total elementos: 400 (20×20)
Número de hilos: N (configurable)
Elementos por hilo: 400/N
```

### 3. Cálculo Concurrente
```
Hilo i → Calcula rango de elementos → Sincroniza escritura → Continúa
```

### 4. Verificación de Resultados
```
Comparar resultado concurrente vs secuencial → Validar corrección
```

## Ejemplo de Salida Esperada

```
=== MULTIPLICACIÓN CONCURRENTE DE MATRICES ===
Configuración:
  Matriz A: 20×15 (valores 5-15)
  Matriz B: 15×20 (valores 5-15)  
  Matriz C: 20×20 (resultado)
  Hilos: 4

[14:30:15.123] Inicializando matrices...
[14:30:15.145] Matriz A inicializada con valores aleatorios
[14:30:15.156] Matriz B inicializada con valores aleatorios
[14:30:15.167] Matriz C preparada para sincronización

[14:30:15.178] Iniciando cálculo concurrente...
[14:30:15.189] Hilo-1: Procesando filas 0-4
[14:30:15.190] Hilo-2: Procesando filas 5-9
[14:30:15.191] Hilo-3: Procesando filas 10-14
[14:30:15.192] Hilo-4: Procesando filas 15-19

[14:30:15.234] Hilo-1 completado (100 elementos calculados)
[14:30:15.245] Hilo-2 completado (100 elementos calculados)
[14:30:15.256] Hilo-3 completado (100 elementos calculados)
[14:30:15.267] Hilo-4 completado (100 elementos calculados)

=== RESULTADOS ===
Tiempo de ejecución: 89ms
Elementos calculados: 400
Verificación: ✅ CORRECTA
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Rendimiento**:
   - Tiempo de cálculo concurrente vs secuencial
   - Speedup alcanzado
   - Eficiencia de paralelización

2. **Corrección**:
   - Verificación elemento por elemento
   - Detección de condiciones de carrera
   - Validación de sincronización

3. **Recursos**:
   - Uso de memoria
   - Contención de sincronización
   - Distribución de carga entre hilos

## Desafíos de Implementación

### 1. Sincronización Eficiente
- Minimizar overhead de sincronización
- Evitar condiciones de carrera
- Balancear granularidad de locks

### 2. Distribución de Trabajo
- Equilibrar carga entre hilos
- Minimizar false sharing
- Optimizar acceso a memoria

### 3. Herencia y Composición
- Integrar herencia de Operaciones
- Implementar Runnable correctamente
- Mantener cohesión de clases

### 4. Verificación de Corrección
- Comparar con implementación secuencial
- Detectar errores de sincronización
- Validar integridad de datos

## Algoritmo de Multiplicación

### Cálculo de Elemento C[i][j]

```java
private int calcularElemento(int i, int j) {
    int suma = 0;
    
    // C[i][j] = Σ(k=0 to 14) A[i][k] × B[k][j]
    for (int k = 0; k < 15; k++) {
        suma += matrizA[i][k] * matrizB[k][j];
    }
    
    return suma;
}
```

### Estrategia de Sincronización

```java
public class MatrizConcurrente {
    private int[][] matriz;
    private final Object[][] locks; // Lock por elemento (opcional)
    
    public synchronized void establecerElemento(int i, int j, int valor) {
        matriz[i][j] = valor;
        // Opcional: usar locks más granulares
    }
}
```

## Análisis de Complejidad

### Complejidad Temporal

- **Secuencial**: O(n³) donde n = 20
- **Concurrente**: O(n³/p) donde p = número de hilos
- **Overhead**: Sincronización y creación de hilos

### Complejidad Espacial

- **Matrices**: O(n²) para A, B y C
- **Hilos**: O(p) donde p = número de hilos
- **Sincronización**: O(1) o O(n²) según estrategia

## Extensiones Posibles

### 1. Algoritmos Optimizados
```java
// Algoritmo de Strassen para matrices grandes
public class MultiplicacionStrassen extends Operaciones implements Runnable {
    // Implementación divide y vencerás
}
```

### 2. Matrices Dispersas
```java
public class MatrizDispersa {
    private Map<Pair<Integer, Integer>, Integer> elementos;
    // Optimización para matrices con muchos ceros
}
```

### 3. Paralelización por Bloques
```java
public class MultiplicacionBloques extends Operaciones implements Runnable {
    private int bloqueI, bloqueJ, bloqueK;
    // División en bloques para mejor cache locality
}
```

### 4. GPU Computing
```java
// Integración con CUDA o OpenCL para aceleración GPU
public interface CalculadoraGPU {
    int[][] multiplicar(int[][] A, int[][] B);
}
```

## Aplicaciones Reales

### Sistemas que Usan Multiplicación de Matrices

- **Gráficos por Computadora**: Transformaciones 3D
- **Machine Learning**: Redes neuronales, álgebra lineal
- **Procesamiento de Imágenes**: Filtros, convoluciones
- **Simulaciones Científicas**: Elementos finitos, CFD
- **Criptografía**: Algoritmos basados en matrices

### Patrones de Diseño Aplicados

- **Template Method**: Algoritmo base en Operaciones
- **Strategy**: Diferentes estrategias de paralelización
- **Observer**: Monitoreo de progreso de cálculo
- **Factory**: Creación de diferentes tipos de calculadoras

## Optimizaciones de Rendimiento

### 1. Cache Locality
```java
// Acceso por bloques para mejor uso de cache
for (int ii = 0; ii < n; ii += BLOCK_SIZE) {
    for (int jj = 0; jj < n; jj += BLOCK_SIZE) {
        for (int kk = 0; kk < n; kk += BLOCK_SIZE) {
            // Multiplicación de bloques
        }
    }
}
```

### 2. Vectorización
```java
// Uso de operaciones SIMD cuando sea posible
// Procesamiento de múltiples elementos simultáneamente
```

### 3. Memory Layout
```java
// Organización de datos para acceso secuencial
// Minimizar cache misses y false sharing
```

## Conclusiones Esperadas

### Beneficios de Concurrencia

1. **Speedup Lineal**: Teóricamente hasta N× con N hilos
2. **Utilización de CPU**: Aprovechamiento de múltiples cores
3. **Escalabilidad**: Adaptable a diferentes tamaños de problema

### Limitaciones

1. **Overhead de Sincronización**: Puede reducir beneficios
2. **Memory Bandwidth**: Cuello de botella en acceso a memoria
3. **False Sharing**: Contención de cache entre hilos

### Lecciones de Runnable

1. **Flexibilidad**: Herencia + interfaz en la misma clase
2. **Reutilización**: Misma lógica para diferentes configuraciones
3. **Testabilidad**: Fácil testing de algoritmo sin concurrencia

---

**Próximos Pasos**: Implementación de las clases Java con herencia e interfaz Runnable  
**Tiempo Estimado**: Multiplicación de 400 elementos en ~50-100ms  
**Autor**: Curso de Programación Concurrente 2025
