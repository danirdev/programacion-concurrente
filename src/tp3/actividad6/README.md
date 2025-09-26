# TP3 - Actividad 6 - Intercambio Concurrente de Matrices

## Enunciado

**6)** Defina 2 matrices. Matriz A de 100 x 200 y matriz B de 200 x 100. Inicialice ambas con números aleatorios (1-100). Luego realice el intercambio de forma concurrente de cada fila de A a su correspondiente columna en B y viceversa (cada columna de B a su correspondiente fila en A) sin perder la información en el pasaje. Muestre las matrices antes y después del intercambio.

---

## Análisis del Problema

### Descripción
- **Matriz A**: 100 filas × 200 columnas
- **Matriz B**: 200 filas × 100 columnas
- **Inicialización**: Números aleatorios entre 1-100
- **Operación**: Intercambio concurrente sin pérdida de datos
- **Intercambio**: Fila i de A ↔ Columna i de B

### Conceptos Demostrados
- **Matrices transpuestas** y operaciones matriciales
- **Intercambio atómico** de datos
- **Concurrencia** en operaciones matriciales
- **Sincronización** para evitar pérdida de datos
- **Paralelización** de operaciones independientes

---

## Implementación

### Estructura de Clases

1. **`Matriz.java`** - Clase para representar y manipular matrices
2. **`IntercambiadorFilaColumna.java`** - Hilo para intercambiar fila↔columna
3. **`GestorIntercambio.java`** - Coordina el intercambio concurrente
4. **`VisualizadorMatrices.java`** - Muestra matrices antes y después
5. **`TestIntercambioMatrices.java`** - Clase principal con demostración

### Características de la Implementación
- **Intercambio atómico**: Sin pérdida de información
- **100 hilos concurrentes**: Uno por cada par fila-columna
- **Matrices inmutables**: Durante el intercambio
- **Visualización**: Antes y después del intercambio
- **Verificación**: Correctitud del intercambio

---

## Estrategia de Intercambio

### Problema de Sincronización
```
Intercambio simultáneo:
A[i][j] ↔ B[j][i] para todo i,j válido

Sin sincronización:
1. Leer A[i][j] → temp1
2. Leer B[j][i] → temp2  
3. Escribir temp2 → A[i][j]
4. Escribir temp1 → B[j][i]
```

### Solución Implementada
- **Buffer temporal**: Copia completa de matrices
- **Intercambio atómico**: Por pares fila-columna
- **Hilos independientes**: Sin dependencias entre ellos

---

## Cómo Ejecutar

### Opción 1: Demostración Completa
```bash
javac tp3/actividad6/*.java
java tp3.actividad6.TestIntercambioMatrices
```

### Opción 2: Solo Intercambio
```bash
javac tp3/actividad6/*.java
java tp3.actividad6.GestorIntercambio
```

---

## Resultados Esperados

### Verificación del Intercambio
- **Antes**: A[i][j] = valor_original_A, B[j][i] = valor_original_B
- **Después**: A[i][j] = valor_original_B, B[j][i] = valor_original_A
- **Conservación**: Ningún dato se pierde durante el intercambio

### Visualización
- **Matrices pequeñas**: Mostrar valores completos
- **Matrices grandes**: Mostrar esquinas y estadísticas
- **Comparación**: Antes vs después del intercambio

### Rendimiento
- **Concurrencia**: 100 hilos trabajando simultáneamente
- **Speedup**: Mejora respecto a versión secuencial
- **Verificación**: Correctitud del intercambio paralelo

Este ejercicio demuestra operaciones matriciales concurrentes y la importancia de la sincronización en el intercambio de datos.
