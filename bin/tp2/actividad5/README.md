# TP2 - Actividad 5 - Análisis de Variables Compartidas

## Enunciado

**5)** Cuáles son los posibles valores de x.

```
global int y = 0, z = 0;

thread {
    int x;
    x = y + z;
}

thread {
    y = 1;
    z = 2;
}
```

---

## Análisis del Programa

### Variables y Threads

#### **Variables Globales:**
```pseudocode
y: Entero := 0    // Variable compartida, inicializada en 0
z: Entero := 0    // Variable compartida, inicializada en 0
```

#### **Thread 1:**
```pseudocode
THREAD T1:
    x: Entero     // Variable local (no compartida)
    x := y + z    // Lee y y z, asigna la suma a x
```

#### **Thread 2:**
```pseudocode
THREAD T2:
    y := 1        // Modifica variable global y
    z := 2        // Modifica variable global z
```

---

## Análisis de Posibles Intercalaciones

### Identificación de Operaciones Atómicas

#### **Operaciones de T1:**
- **O1**: `x := y + z` (lectura de y, lectura de z, suma, asignación a x)

#### **Operaciones de T2:**
- **O2**: `y := 1` (asignación a y)
- **O3**: `z := 2` (asignación a z)

### Descomposición Detallada de O1

La operación `x = y + z` se puede descomponer en:
1. **Leer y** → temp1
2. **Leer z** → temp2  
3. **Sumar** temp1 + temp2 → temp3
4. **Asignar** temp3 → x

---

## Análisis de Todos los Escenarios Posibles

### Escenario 1: T1 ejecuta completamente antes que T2

#### Secuencia: O1 → O2 → O3
```pseudocode
Estado inicial: y = 0, z = 0

T1 ejecuta:
    Leer y = 0
    Leer z = 0  
    x = 0 + 0 = 0

T2 ejecuta:
    y = 1
    z = 2

Estado final: y = 1, z = 2, x = 0
```

**Resultado: x = 0**

### Escenario 2: T2 ejecuta completamente antes que T1

#### Secuencia: O2 → O3 → O1
```pseudocode
Estado inicial: y = 0, z = 0

T2 ejecuta:
    y = 1
    z = 2

Estado intermedio: y = 1, z = 2

T1 ejecuta:
    Leer y = 1
    Leer z = 2
    x = 1 + 2 = 3

Estado final: y = 1, z = 2, x = 3
```

**Resultado: x = 3**

### Escenario 3: Intercalación - T2 modifica y, luego T1 lee, luego T2 modifica z

#### Secuencia: O2 → O1 → O3
```pseudocode
Estado inicial: y = 0, z = 0

T2 ejecuta O2:
    y = 1

Estado intermedio: y = 1, z = 0

T1 ejecuta O1:
    Leer y = 1
    Leer z = 0
    x = 1 + 0 = 1

T2 ejecuta O3:
    z = 2

Estado final: y = 1, z = 2, x = 1
```

**Resultado: x = 1**

### Escenario 4: Intercalación - T2 modifica z, luego T1 lee, luego T2 modifica y

#### Secuencia: O3 → O1 → O2
```pseudocode
Estado inicial: y = 0, z = 0

T2 ejecuta O3:
    z = 2

Estado intermedio: y = 0, z = 2

T1 ejecuta O1:
    Leer y = 0
    Leer z = 2
    x = 0 + 2 = 2

T2 ejecuta O2:
    y = 1

Estado final: y = 1, z = 2, x = 2
```

**Resultado: x = 2**

### Escenario 5: Intercalación compleja - T1 lee y, T2 modifica y, T2 modifica z, T1 continúa

#### Considerando operaciones más granulares:

Si asumimos que `x = y + z` puede ser interrumpida entre la lectura de y y la lectura de z:

#### Secuencia: Leer_y → O2 → O3 → Leer_z_y_calcular
```pseudocode
Estado inicial: y = 0, z = 0

T1 ejecuta parcialmente:
    temp_y = y = 0    // T1 lee y

T2 ejecuta O2:
    y = 1             // T2 modifica y (pero T1 ya leyó el valor anterior)

T2 ejecuta O3:
    z = 2             // T2 modifica z

T1 continúa:
    temp_z = z = 2    // T1 lee z (valor ya modificado)
    x = temp_y + temp_z = 0 + 2 = 2

Estado final: y = 1, z = 2, x = 2
```

**Resultado: x = 2**

#### Secuencia: Leer_y → O3 → O2 → Leer_z_y_calcular
```pseudocode
Estado inicial: y = 0, z = 0

T1 ejecuta parcialmente:
    temp_y = y = 0    // T1 lee y

T2 ejecuta O3:
    z = 2             // T2 modifica z

T2 ejecuta O2:
    y = 1             // T2 modifica y (pero T1 ya leyó el valor anterior)

T1 continúa:
    temp_z = z = 2    // T1 lee z (valor ya modificado)
    x = temp_y + temp_z = 0 + 2 = 2

Estado final: y = 1, z = 2, x = 2
```

**Resultado: x = 2**

---

## Tabla Resumen de Todos los Escenarios

| Escenario | Orden de Ejecución | Valor de y cuando T1 lee | Valor de z cuando T1 lee | Resultado x |
|-----------|-------------------|-------------------------|-------------------------|-------------|
| 1 | T1 completo → T2 completo | 0 | 0 | **0** |
| 2 | T2 completo → T1 completo | 1 | 2 | **3** |
| 3 | y=1 → T1 → z=2 | 1 | 0 | **1** |
| 4 | z=2 → T1 → y=1 | 0 | 2 | **2** |
| 5a | T1 lee y → T2 completo → T1 continúa | 0 (leído antes) | 2 (leído después) | **2** |
| 5b | T1 lee y → z=2 → y=1 → T1 continúa | 0 (leído antes) | 2 (leído después) | **2** |

---

## Conjunto de Posibles Valores de x

### **Respuesta: {0, 1, 2, 3}**

La variable local `x` puede tomar **4 valores diferentes**:

- **x = 0**: Cuando T1 lee ambas variables antes de que T2 las modifique
- **x = 1**: Cuando T1 lee y=1 (modificada) y z=0 (original)
- **x = 2**: Cuando T1 lee y=0 (original) y z=2 (modificada)
- **x = 3**: Cuando T1 lee ambas variables después de que T2 las modifique

---

## Análisis Detallado de Condiciones de Carrera

### Factores Críticos

#### **1. Atomicidad de Operaciones**
- **`y = 1`** y **`z = 2`** son operaciones atómicas
- **`x = y + z`** puede no ser atómica (depende de la implementación)

#### **2. Orden de Lectura en T1**
- Si T1 lee `y` primero, luego `z`: diferentes intercalaciones posibles
- Si T1 lee `z` primero, luego `y`: diferentes intercalaciones posibles

#### **3. Timing de Ejecución**
- **Sin sincronización**: Cualquier intercalación es posible
- **Scheduler del SO**: Determina el orden real de ejecución

### Casos Límite

#### **Caso A: Operación Completamente Atómica**
Si `x = y + z` fuera completamente atómica, solo serían posibles:
- **x = 0** (T1 antes que T2)
- **x = 3** (T1 después que T2)

#### **Caso B: Operación Completamente No Atómica**
Si cada lectura puede ser interrumpida independientemente:
- Todos los valores **{0, 1, 2, 3}** son posibles

---

## Representación con Diagramas de Tiempo

### Escenario para x = 1
```
Tiempo →
T1: |----[lee y=0]----[y=1]----[lee z=0]----[x=1+0=1]----|
T2: |----------[y=1]----------[z=2]-------------------|
```

### Escenario para x = 2  
```
Tiempo →
T1: |----[lee y=0]----[z=2]----[lee z=2]----[x=0+2=2]----|
T2: |----------[z=2]----------[y=1]-------------------|
```

---

## Conclusiones

### Características del Sistema
1. **No Determinismo**: El resultado depende del timing de ejecución
2. **Condiciones de Carrera**: Las variables y, z son compartidas sin sincronización
3. **Variable Local**: x es local a T1, no hay conflicto de escritura
4. **4 Resultados Posibles**: Dependiendo de la intercalación específica

### Implicaciones
- **Sin Sincronización**: El comportamiento es impredecible
- **Dependencia del Scheduler**: El SO determina el orden real
- **Atomicidad Crítica**: La granularidad de `x = y + z` afecta los resultados
- **Necesidad de Coordinación**: Para resultados deterministas se requiere sincronización

### Solución para Determinismo
```pseudocode
// Con sincronización
mutex m;

thread T1 {
    lock(m);
    int x = y + z;
    unlock(m);
}

thread T2 {
    lock(m);
    y = 1;
    z = 2;
    unlock(m);
}
```

**Resultado con sincronización**: Solo x = 0 o x = 3 (dependiendo del orden de adquisición del mutex)

El conjunto de posibles valores para x es **{0, 1, 2, 3}**, demostrando la complejidad de los sistemas concurrentes sin sincronización.
