# 🔢 TP8 - ACTIVIDAD 5: PROCESAMIENTO PARALELO CON CALLABLE Y FUTURE

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Callable, Future y ExecutorService

---

## 🎯 **OBJETIVO**

Procesar un **arreglo de números** usando un **pool de ejecución de tamaño 2** para realizar cálculos intensivos con la interfaz **Callable** que retorna resultados, y recuperarlos usando **Future**.

---

## 📝 **DESCRIPCIÓN DEL EJERCICIO**

### 🔢 **Arreglo de Datos**

```java
long[] vector = new long[] { 100477L, 105477L, 112986L, 100078L, 165987L, 142578L };
```

### 🧮 **Función de Cálculo**

Cada número debe procesarse con esta función que demora varios segundos:

```java
static BigInteger M = new BigInteger("1999");

private static BigInteger compute(long n) {
    String s = "";
    for (long i = 0; i < n; i++) {
        s = s + n;
    }
    return new BigInteger(s.toString()).mod(M);
}
```

### ⚙️ **Requisitos**

- **Pool de threads:** Tamaño 2
- **Mecanismo:** Callable para retornar resultados
- **Recuperación:** Future para obtener resultados asincrónicos

---

## 🏗️ **ARQUITECTURA DE LA SOLUCIÓN**

### 📊 **Diagrama del Sistema**

```
┌─────────────────────────────────────────────────────────────────┐
│         PROCESAMIENTO PARALELO CON CALLABLE Y FUTURE            │
│                                                                 │
│  ARREGLO DE ENTRADA:                                           │
│  [100477, 105477, 112986, 100078, 165987, 142578]             │
│                         │                                       │
│                         ▼                                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         ExecutorService (Pool Fijo - 2 Threads)          │  │
│  │                                                          │  │
│  │    Thread 1         │         Thread 2                  │  │
│  │       ↓                         ↓                        │  │
│  └──────────────────────────────────────────────────────────┘  │
│           │                         │                           │
│           ▼                         ▼                           │
│  ┌─────────────────┐      ┌─────────────────┐                 │
│  │ Callable Task 1 │      │ Callable Task 2 │                 │
│  │ compute(100477) │      │ compute(105477) │                 │
│  └────────┬────────┘      └────────┬────────┘                 │
│           │                         │                           │
│           ▼                         ▼                           │
│  ┌─────────────────┐      ┌─────────────────┐                 │
│  │  Future<Result> │      │  Future<Result> │                 │
│  │   result.get()  │      │   result.get()  │                 │
│  └─────────────────┘      └─────────────────┘                 │
│           │                         │                           │
│           └──────────┬──────────────┘                           │
│                      ▼                                          │
│            RESULTADOS PROCESADOS                               │
│  [resultado1, resultado2, resultado3, ...]                    │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp8/actividad5/
├── README.md                           (Este archivo)
├── TareaCalculo.java                  (Callable que ejecuta compute)
└── ProcesamientoParaleloSimulacion.java (Simulación principal)
```

### 🔑 **Componentes Principales**

#### 1️⃣ **Callable vs Runnable**

| Característica | Runnable | Callable |
|---------------|----------|----------|
| Retorna valor | ❌ No (void) | ✅ Sí (genérico T) |
| Lanza excepciones | ❌ No checked | ✅ Sí (throws Exception) |
| Método | run() | call() |
| Uso con Future | ❌ No | ✅ Sí |

#### 2️⃣ **Future<T>**
- Representa resultado de operación asíncrona
- Métodos principales:
  - `get()` - Obtener resultado (bloquea hasta que termine)
  - `get(timeout, unit)` - Obtener con timeout
  - `isDone()` - Verificar si terminó
  - `cancel()` - Cancelar ejecución

#### 3️⃣ **ExecutorService.submit()**
```java
// Submit retorna Future (a diferencia de execute)
Future<BigInteger> future = executor.submit(callable);
BigInteger resultado = future.get(); // Recuperar resultado
```

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac -d bin src/tp8/actividad5/*.java

# Ejecutar simulación
java -cp bin tp8.actividad5.ProcesamientoParaleloSimulacion
```

### 📊 **Salida Esperada**

```
=== PROCESAMIENTO PARALELO CON CALLABLE Y FUTURE ===

🔢 CONFIGURACIÓN:
   📊 Elementos a procesar: 6
   🔧 Pool de threads: 2
   🧮 Función: compute(n) con BigInteger

📋 ARREGLO DE ENTRADA:
   [100477, 105477, 112986, 100078, 165987, 142578]

🚀 INICIANDO PROCESAMIENTO PARALELO...

[Thread-1] 🔄 Procesando elemento 0: 100477
[Thread-2] 🔄 Procesando elemento 1: 105477
[Thread-1] ✅ Elemento 0 completado: resultado = 825 (tiempo: 12.3s)
[Thread-1] 🔄 Procesando elemento 2: 112986
[Thread-2] ✅ Elemento 1 completado: resultado = 1456 (tiempo: 13.1s)
[Thread-2] 🔄 Procesando elemento 3: 100078
...

📊 TABLA DE RESULTADOS:
┌──────────┬──────────────┬────────────┬──────────┐
│  ÍNDICE  │    VALOR     │ RESULTADO  │  TIEMPO  │
├──────────┼──────────────┼────────────┼──────────┤
│    0     │   100477     │    825     │  12.3s   │
│    1     │   105477     │   1456     │  13.1s   │
│    2     │   112986     │    742     │  14.2s   │
│    3     │   100078     │   1123     │  12.5s   │
│    4     │   165987     │    589     │  18.7s   │
│    5     │   142578     │    934     │  15.6s   │
└──────────┴──────────────┴────────────┴──────────┘

⏱️ Tiempo total con pool (2 threads): 43.2s
⏱️ Tiempo secuencial estimado: 86.4s
🚀 Speedup: 2.0x
📈 Eficiencia: 100%
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **Callable<T> - Tareas que Retornan Resultados**

```java
class TareaCalculo implements Callable<BigInteger> {
    private final long numero;
    
    @Override
    public BigInteger call() throws Exception {
        return compute(numero);
    }
}
```

**Ventajas de Callable:**
- ✅ Puede retornar un resultado
- ✅ Puede lanzar excepciones checked
- ✅ Se integra con Future para recuperación asíncrona

### 📊 **Future<T> - Recuperación de Resultados**

```java
// Enviar tareas y obtener Futures
List<Future<BigInteger>> futures = new ArrayList<>();
for (long numero : vector) {
    Future<BigInteger> future = executor.submit(new TareaCalculo(numero));
    futures.add(future);
}

// Recuperar resultados
for (Future<BigInteger> future : futures) {
    BigInteger resultado = future.get(); // Bloquea hasta que termine
}
```

### 🚀 **Speedup con 2 Threads**

**Cálculo esperado:**
- 6 tareas con 2 threads
- Primera ronda: tareas 0, 1 en paralelo
- Segunda ronda: tareas 2, 3 en paralelo  
- Tercera ronda: tareas 4, 5 en paralelo

**Speedup ideal:** 2x (porque usamos 2 threads)

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🔄 **Callable<T>** - Tareas con retorno de valor
- 📥 **Future<T>** - Recuperación asíncrona de resultados
- 🔧 **submit()** vs **execute()** - Diferencias
- ⏱️ **Procesamiento paralelo** de colecciones
- 📊 **Medición de speedup** y eficiencia
- 🚀 **Paralelización de cálculos** intensivos

---

## 📚 **DIFERENCIAS CLAVE**

### **execute() vs submit()**

| Método | Retorna | Para | Uso |
|--------|---------|------|-----|
| execute(Runnable) | void | Runnable | Fire-and-forget |
| submit(Runnable) | Future<?> | Runnable | Control de ejecución |
| submit(Callable) | Future<T> | Callable | Recuperar resultado |

### **Runnable vs Callable**

```java
// Runnable - sin retorno
Runnable runnable = () -> {
    System.out.println("Ejecutando");
    // No puede retornar valor
};

// Callable - con retorno
Callable<Integer> callable = () -> {
    System.out.println("Ejecutando");
    return 42; // Retorna valor
};
```

---

## 📚 **REFERENCIAS**

- **Clase 7:** Executor Framework
- **Java Concurrency:** Callable, Future
- **ExecutorService:** submit() method
- **Parallel Processing:** Task parallelism

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación de procesamiento paralelo con Callable y Future, demostrando recuperación de resultados de tareas asíncronas y paralelización eficiente de cálculos intensivos.*
