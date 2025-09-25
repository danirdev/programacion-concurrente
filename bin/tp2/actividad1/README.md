# TP2 - Actividad 1 - Análisis de Concurrencia

## Enunciado

**1)** Suponga que dos tareas deben realizar las siguientes acciones:

| Tarea1 | Tarea2 |
|--------|--------|
| **Begin** | **Begin** |
| A = A+100 (I1 T1) | A = 1.21 * A (I1 T2) |
| B = B-100 (I2 T1) | B = 0.5 * B (I2 T2) |
| **End** | **End** |

Si A inicia en 50 y B inicia en 150, indique mediante una tabla el resultado de la ejecución secuencial, primero Tarea1 y luego Tarea2, y viceversa. A continuación, suponga que ambas tareas se ejecutan de manera simultánea, indique el orden de ejecución de cada instrucción teniendo en cuenta todas las combinaciones posibles de las mismas, e indique el resultado final de cada variable (A y B) al finalizar cada combinación.

---

## Pseudocódigo de las Tareas

### Valores Iniciales
```
A := 50
B := 150
```

### Tarea1
```pseudocode
BEGIN Tarea1
    I1_T1: A := A + 100
    I2_T1: B := B - 100
END Tarea1
```

### Tarea2
```pseudocode
BEGIN Tarea2
    I1_T2: A := 1.21 * A
    I2_T2: B := 0.5 * B
END Tarea2
```

---

## 1. Análisis de Ejecución Secuencial

### Caso 1: Tarea1 → Tarea2

| Paso | Instrucción | Operación | A | B | Descripción |
|------|-------------|-----------|---|---|-------------|
| 0 | - | Inicial | 50 | 150 | Valores iniciales |
| 1 | I1_T1 | A := A + 100 | 150 | 150 | 50 + 100 = 150 |
| 2 | I2_T1 | B := B - 100 | 150 | 50 | 150 - 100 = 50 |
| 3 | I1_T2 | A := 1.21 * A | 181.5 | 50 | 1.21 × 150 = 181.5 |
| 4 | I2_T2 | B := 0.5 * B | 181.5 | 25 | 0.5 × 50 = 25 |

**Resultado Final: A = 181.5, B = 25**

### Caso 2: Tarea2 → Tarea1

| Paso | Instrucción | Operación | A | B | Descripción |
|------|-------------|-----------|---|---|-------------|
| 0 | - | Inicial | 50 | 150 | Valores iniciales |
| 1 | I1_T2 | A := 1.21 * A | 60.5 | 150 | 1.21 × 50 = 60.5 |
| 2 | I2_T2 | B := 0.5 * B | 60.5 | 75 | 0.5 × 150 = 75 |
| 3 | I1_T1 | A := A + 100 | 160.5 | 75 | 60.5 + 100 = 160.5 |
| 4 | I2_T1 | B := B - 100 | 160.5 | -25 | 75 - 100 = -25 |

**Resultado Final: A = 160.5, B = -25**

---

## 2. Análisis de Ejecución Concurrente

### Instrucciones Disponibles:
- **I1_T1**: A := A + 100
- **I2_T1**: B := B - 100  
- **I1_T2**: A := 1.21 * A
- **I2_T2**: B := 0.5 * B

### Todas las Combinaciones Posibles (6 combinaciones):

#### Combinación 1: I1_T1 → I1_T2 → I2_T1 → I2_T2

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T1 | A := A + 100 | 150 | 150 | 50 + 100 |
| 2 | I1_T2 | A := 1.21 * A | 181.5 | 150 | 1.21 × 150 |
| 3 | I2_T1 | B := B - 100 | 181.5 | 50 | 150 - 100 |
| 4 | I2_T2 | B := 0.5 * B | 181.5 | 25 | 0.5 × 50 |

**Resultado: A = 181.5, B = 25**

#### Combinación 2: I1_T1 → I2_T1 → I1_T2 → I2_T2

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T1 | A := A + 100 | 150 | 150 | 50 + 100 |
| 2 | I2_T1 | B := B - 100 | 150 | 50 | 150 - 100 |
| 3 | I1_T2 | A := 1.21 * A | 181.5 | 50 | 1.21 × 150 |
| 4 | I2_T2 | B := 0.5 * B | 181.5 | 25 | 0.5 × 50 |

**Resultado: A = 181.5, B = 25**

#### Combinación 3: I1_T1 → I2_T1 → I2_T2 → I1_T2

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T1 | A := A + 100 | 150 | 150 | 50 + 100 |
| 2 | I2_T1 | B := B - 100 | 150 | 50 | 150 - 100 |
| 3 | I2_T2 | B := 0.5 * B | 150 | 25 | 0.5 × 50 |
| 4 | I1_T2 | A := 1.21 * A | 181.5 | 25 | 1.21 × 150 |

**Resultado: A = 181.5, B = 25**

#### Combinación 4: I1_T2 → I1_T1 → I2_T1 → I2_T2

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T2 | A := 1.21 * A | 60.5 | 150 | 1.21 × 50 |
| 2 | I1_T1 | A := A + 100 | 160.5 | 150 | 60.5 + 100 |
| 3 | I2_T1 | B := B - 100 | 160.5 | 50 | 150 - 100 |
| 4 | I2_T2 | B := 0.5 * B | 160.5 | 25 | 0.5 × 50 |

**Resultado: A = 160.5, B = 25**

#### Combinación 5: I1_T2 → I1_T1 → I2_T2 → I2_T1

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T2 | A := 1.21 * A | 60.5 | 150 | 1.21 × 50 |
| 2 | I1_T1 | A := A + 100 | 160.5 | 150 | 60.5 + 100 |
| 3 | I2_T2 | B := 0.5 * B | 160.5 | 75 | 0.5 × 150 |
| 4 | I2_T1 | B := B - 100 | 160.5 | -25 | 75 - 100 |

**Resultado: A = 160.5, B = -25**

#### Combinación 6: I1_T2 → I2_T2 → I1_T1 → I2_T1

| Paso | Instrucción | Operación | A | B | Cálculo |
|------|-------------|-----------|---|---|---------|
| 0 | - | Inicial | 50 | 150 | - |
| 1 | I1_T2 | A := 1.21 * A | 60.5 | 150 | 1.21 × 50 |
| 2 | I2_T2 | B := 0.5 * B | 60.5 | 75 | 0.5 × 150 |
| 3 | I1_T1 | A := A + 100 | 160.5 | 75 | 60.5 + 100 |
| 4 | I2_T1 | B := B - 100 | 160.5 | -25 | 75 - 100 |

**Resultado: A = 160.5, B = -25**

---

## 3. Tabla Resumen de Resultados

| Tipo de Ejecución | Orden de Ejecución | A Final | B Final |
|-------------------|-------------------|---------|---------|
| **Secuencial** | T1 → T2 | 181.5 | 25 |
| **Secuencial** | T2 → T1 | 160.5 | -25 |
| **Concurrente** | I1T1→I1T2→I2T1→I2T2 | 181.5 | 25 |
| **Concurrente** | I1T1→I2T1→I1T2→I2T2 | 181.5 | 25 |
| **Concurrente** | I1T1→I2T1→I2T2→I1T2 | 181.5 | 25 |
| **Concurrente** | I1T2→I1T1→I2T1→I2T2 | 160.5 | 25 |
| **Concurrente** | I1T2→I1T1→I2T2→I2T1 | 160.5 | -25 |
| **Concurrente** | I1T2→I2T2→I1T1→I2T1 | 160.5 | -25 |

---

## 4. Análisis de Resultados

### Resultados Únicos Posibles:
1. **A = 181.5, B = 25** (4 combinaciones)
2. **A = 160.5, B = 25** (1 combinación)
3. **A = 160.5, B = -25** (3 combinaciones)

### Agrupación por Resultados:

#### Grupo 1: A = 181.5, B = 25
- Ejecución secuencial: T1 → T2
- Combinaciones concurrentes: 1, 2, 3

**Característica común**: Todas las operaciones de T1 se ejecutan antes que las de T2

#### Grupo 2: A = 160.5, B = 25  
- Combinación concurrente: 4

**Característica**: I1_T2 se ejecuta primero, pero I2_T1 se ejecuta antes que I2_T2

#### Grupo 3: A = 160.5, B = -25
- Ejecución secuencial: T2 → T1
- Combinaciones concurrentes: 5, 6

**Característica común**: Todas las operaciones de T2 se ejecutan antes que las de T1

---

## 5. Conclusiones

### 5.1 Determinismo vs No Determinismo
- **Ejecución Secuencial**: Completamente determinista
  - Cada orden produce siempre el mismo resultado
  - 2 posibles resultados según el orden de las tareas

- **Ejecución Concurrente**: No determinista
  - 6 posibles órdenes de intercalación
  - 3 posibles resultados finales
  - El resultado depende del orden específico de ejecución

### 5.2 Condiciones de Carrera (Race Conditions)
- Las variables **A** y **B** son **recursos compartidos**
- Ambas tareas **leen y modifican** las mismas variables
- El resultado final depende del **timing** de las operaciones
- Sin sincronización, el comportamiento es **impredecible**

### 5.3 Factores Críticos
- **Orden de I1_T1 vs I1_T2**: Determina si A será 181.5 o 160.5
- **Orden de I2_T1 vs I2_T2**: Determina si B será 25 o -25
- **Intercalación de operaciones**: Crea diferentes combinaciones de resultados

### 5.4 Implicaciones para Sistemas Concurrentes
1. **Necesidad de Sincronización**: Para obtener resultados predecibles
2. **Diseño Cuidadoso**: Los sistemas concurrentes requieren análisis exhaustivo
3. **Testing Complejo**: Deben probarse todas las posibles intercalaciones
4. **Atomicidad**: Las operaciones críticas deben ser atómicas

---

## 6. Algoritmo de Análisis General

```pseudocode
ALGORITMO AnalisisConcurrencia
ENTRADA: 
    - Conjunto de tareas T = {T1, T2, ..., Tn}
    - Variables compartidas V = {v1, v2, ..., vm}
    - Valores iniciales I = {i1, i2, ..., im}

PROCESO:
    1. Identificar todas las instrucciones de cada tarea
    2. Generar todas las permutaciones válidas de instrucciones
    3. Para cada permutación:
        a. Inicializar variables con valores iniciales
        b. Ejecutar instrucciones en orden
        c. Registrar resultado final
    4. Agrupar resultados idénticos
    5. Analizar patrones y condiciones de carrera

SALIDA:
    - Tabla de todos los resultados posibles
    - Análisis de condiciones de carrera
    - Recomendaciones de sincronización
FIN ALGORITMO
```

Este análisis demuestra la complejidad inherente de los sistemas concurrentes y la importancia de la sincronización para obtener comportamientos predecibles.
