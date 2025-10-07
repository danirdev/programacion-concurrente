# 🔄 TP8 - ACTIVIDAD 1: EXECUTOR FRAMEWORK - POOL DE HILOS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Executor Framework - Pool de Ejecución de Hilos

---

## 🎯 **OBJETIVO**

Implementar un **Pool de ejecución de hilos** usando **Executor Framework** con tamaño fijo de **3 threads** para ejecutar **10 tareas** que realizan cálculos matemáticos intensivos, midiendo el tiempo de ejecución.

---

## 📝 **DESCRIPCIÓN DEL EJERCICIO**

### 🔢 **Cálculo a Realizar**

Cada tarea debe ejecutar el siguiente cálculo, recibiendo como parámetro `root > 0` (número de tarea):

```java
public static void SumRootN(int root) {
    double result = 0;
    for (int i = 0; i < 10000000; i++) {
        result += Math.exp(Math.log(i) / root);
    }
    System.out.println("Resultado" + result);
}
```

### ⚙️ **Configuración**

- **Pool size:** 3 threads
- **Número de tareas:** 10
- **Iteraciones por tarea:** 10,000,000
- **Parámetro root:** Número de tarea (1-10)

---

## 🏗️ **ARQUITECTURA DE LA SOLUCIÓN**

### 📊 **Executor Framework**

```
┌─────────────────────────────────────────────────────────────────┐
│                  EXECUTOR FRAMEWORK                             │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         ExecutorService (Pool Fijo - 3 Threads)          │  │
│  │                                                          │  │
│  │    Thread 1  │  Thread 2  │  Thread 3                   │  │
│  │       ↓             ↓             ↓                      │  │
│  └──────────────────────────────────────────────────────────┘  │
│                         │                                       │
│                         ▼                                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │               COLA DE TAREAS                             │  │
│  │                                                          │  │
│  │  Tarea1  Tarea2  Tarea3  ...  Tarea10                   │  │
│  │  (root=1)(root=2)(root=3)     (root=10)                 │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  FLUJO:                                                        │
│  1. Se crean 10 tareas con diferentes valores de root         │
│  2. Pool de 3 threads las ejecuta concurrentemente            │
│  3. Threads toman tareas de la cola cuando están libres       │
│  4. Se mide tiempo total de ejecución                         │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp8/actividad1/
├── README.md                      (Este archivo)
├── TareaCalculoRaiz.java         (Runnable que ejecuta el cálculo)
├── PoolEjecucionSimulacion.java  (Simulación principal con ExecutorService)
└── AnalizadorRendimiento.java    (Análisis de tiempos y rendimiento)
```

### 🔑 **Componentes Principales**

#### 1️⃣ **TareaCalculoRaiz** (Runnable)
- Implementa el cálculo `SumRootN(int root)`
- Registra tiempo de ejecución individual
- Muestra resultado de cada tarea

#### 2️⃣ **PoolEjecucionSimulacion** (Main)
- Crea `ExecutorService` con pool fijo de 3 threads
- Genera y submite 10 tareas
- Controla shutdown del pool
- Mide tiempo total de ejecución

#### 3️⃣ **AnalizadorRendimiento**
- Analiza tiempos de ejecución
- Compara con ejecución secuencial
- Calcula speedup y eficiencia

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp8/actividad1/*.java

# Ejecutar simulación
java tp8.actividad1.PoolEjecucionSimulacion
```

### 📊 **Salida Esperada**

```
=== POOL DE EJECUCIÓN CON EXECUTOR FRAMEWORK ===

⚙️ CONFIGURACIÓN:
   🔢 Pool size: 3 threads
   📋 Número de tareas: 10
   🔄 Iteraciones por tarea: 10,000,000

🚀 INICIANDO EJECUCIÓN CON POOL...

[Thread-1] Tarea #1 (root=1) iniciada
[Thread-2] Tarea #2 (root=2) iniciada
[Thread-3] Tarea #3 (root=3) iniciada
[Thread-1] Tarea #1 completada - Resultado: 1.23456789E8 - Tiempo: 1523ms
[Thread-1] Tarea #4 (root=4) iniciada
[Thread-2] Tarea #2 completada - Resultado: 9.87654321E7 - Tiempo: 1487ms
[Thread-2] Tarea #5 (root=5) iniciada
...

✅ TODAS LAS TAREAS COMPLETADAS

⏱️ TIEMPO TOTAL: 5247ms

📊 ANÁLISIS DE RENDIMIENTO:
   ⏱️ Tiempo con pool (3 threads): 5.25s
   ⏱️ Tiempo secuencial estimado: 15.12s
   🚀 Speedup: 2.88x
   📈 Eficiencia: 96%
   💡 Mejora: Pool 65% más rápido
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **Executor Framework vs Threads Manuales**

#### ✅ **Ventajas de ExecutorService:**
1. **Gestión automática** de threads (creación, reutilización, destrucción)
2. **Pool limitado** - Evita crear threads ilimitados
3. **Cola de tareas** - Manejo eficiente de trabajo pendiente
4. **Reutilización** - Threads se reutilizan para múltiples tareas
5. **API simple** - Métodos `submit()`, `shutdown()`, `awaitTermination()`

#### 🔧 **Método `newFixedThreadPool(3)`:**
```java
ExecutorService executor = Executors.newFixedThreadPool(3);
```
- Crea pool con **3 threads activos**
- Tareas adicionales esperan en **cola**
- Threads se **reutilizan** automáticamente

### 📊 **Análisis de Rendimiento**

#### **¿Por qué pool de 3 threads?**
- **Balance óptimo** entre concurrencia y recursos
- Evita **overhead** de demasiados threads
- Aprovecha **múltiples núcleos** del procesador

#### **Comportamiento Esperado:**
Con 10 tareas y 3 threads:
- **Primera ronda:** Tareas 1, 2, 3 ejecutan en paralelo
- **Segunda ronda:** Tareas 4, 5, 6 ejecutan en paralelo
- **Tercera ronda:** Tareas 7, 8, 9 ejecutan en paralelo
- **Cuarta ronda:** Tarea 10 ejecuta sola

**Tiempo teórico:** ~4 rondas × tiempo_tarea ≈ Tiempo total

### 🔢 **Cálculo Matemático**

El cálculo realizado:
```
result = Σ(i=0 to 10,000,000) exp(log(i) / root)
       = Σ(i=0 to 10,000,000) i^(1/root)
```

- Para `root=1`: Suma de i^1 = suma simple
- Para `root=2`: Suma de raíces cuadradas
- Para `root=10`: Suma de raíces décimas

**Intensidad computacional:** ~10M operaciones por tarea

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🔄 **Executor Framework** - Pool de threads gestionado
- 📋 **Fixed Thread Pool** - Pool de tamaño fijo
- 🚀 **Concurrencia controlada** - Límite de threads
- ⏱️ **Medición de tiempos** - Análisis de rendimiento
- 📊 **Speedup y eficiencia** - Métricas de paralelización
- 🔧 **Reutilización de threads** - Eficiencia de recursos

---

## 📚 **REFERENCIAS**

- **Clase 7:** Executor Framework
- **Java Concurrency:** java.util.concurrent.ExecutorService
- **Executors:** Factory methods para crear pools
- **Thread Pools:** Gestión eficiente de threads

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación del Executor Framework con pool fijo de threads, demostrando gestión eficiente de tareas concurrentes y análisis de rendimiento.*
