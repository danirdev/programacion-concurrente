# TP2 - Actividad 4 - Concurrencia en Lenguajes de Programación

## Enunciado

**4)** Investigue como y mediante qué mecanismos se implementa concurrencia en los siguientes lenguajes de programación: Java, C# y Python. Indique además los nombres de las librerías para cada lenguaje.

---

## Concurrencia en Java

### Mecanismos Principales

#### 1. **Threads (Hilos)**
```java
// Extendiendo Thread
class MiHilo extends Thread {
    public void run() {
        // Código del hilo
    }
}

// Implementando Runnable
class MiTarea implements Runnable {
    public void run() {
        // Código de la tarea
    }
}
```

#### 2. **Sincronización**
```java
// Métodos sincronizados
public synchronized void metodoSincronizado() {
    // Código sincronizado
}

// Bloques sincronizados
synchronized(objeto) {
    // Código sincronizado
}
```

#### 3. **Executor Framework**
```java
ExecutorService executor = Executors.newFixedThreadPool(4);
executor.submit(() -> {
    // Tarea a ejecutar
});
```

#### 4. **Fork/Join Framework**
```java
class MiTarea extends RecursiveTask<Integer> {
    protected Integer compute() {
        // Lógica divide y vencerás
    }
}
```

### Librerías y Paquetes de Java

#### **java.util.concurrent**
- **Clases principales:**
  - `ExecutorService`, `ThreadPoolExecutor`
  - `Future`, `CompletableFuture`
  - `CountDownLatch`, `CyclicBarrier`
  - `Semaphore`, `ReentrantLock`
  - `ConcurrentHashMap`, `BlockingQueue`

#### **java.util.concurrent.atomic**
- **Clases atómicas:**
  - `AtomicInteger`, `AtomicLong`
  - `AtomicReference`, `AtomicBoolean`

#### **java.util.concurrent.locks**
- **Locks avanzados:**
  - `ReentrantLock`, `ReadWriteLock`
  - `StampedLock`, `Condition`

### Ejemplo Completo en Java
```java
import java.util.concurrent.*;

public class EjemploConcurrencia {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Semaphore semaforo = new Semaphore(2);
    
    public void ejecutarTareas() {
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    semaforo.acquire();
                    System.out.println("Tarea " + taskId + " ejecutándose");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaforo.release();
                }
            });
        }
    }
}
```

---

## Concurrencia en C#

### Mecanismos Principales

#### 1. **Task Parallel Library (TPL)**
```csharp
// Tareas básicas
Task.Run(() => {
    // Código asíncrono
});

// Tareas con retorno
Task<int> tarea = Task.Run(() => {
    return 42;
});
```

#### 2. **async/await Pattern**
```csharp
public async Task<string> MetodoAsincrono() {
    await Task.Delay(1000);
    return "Completado";
}
```

#### 3. **Parallel LINQ (PLINQ)**
```csharp
var resultado = datos.AsParallel()
    .Where(x => x > 10)
    .Select(x => x * 2)
    .ToList();
```

#### 4. **Threads Tradicionales**
```csharp
Thread hilo = new Thread(() => {
    // Código del hilo
});
hilo.Start();
```

### Librerías y Namespaces de C#

#### **System.Threading**
- **Clases principales:**
  - `Thread`, `ThreadPool`
  - `Mutex`, `Semaphore`, `AutoResetEvent`
  - `ReaderWriterLock`, `Monitor`
  - `CancellationToken`, `CancellationTokenSource`

#### **System.Threading.Tasks**
- **Task Parallel Library:**
  - `Task`, `Task<T>`
  - `TaskFactory`, `TaskScheduler`
  - `Parallel` (ForEach, For, Invoke)

#### **System.Collections.Concurrent**
- **Colecciones thread-safe:**
  - `ConcurrentDictionary`, `ConcurrentQueue`
  - `ConcurrentStack`, `ConcurrentBag`
  - `BlockingCollection`

#### **System.Threading.Channels**
- **Comunicación asíncrona:**
  - `Channel<T>`, `ChannelReader<T>`, `ChannelWriter<T>`

### Ejemplo Completo en C#
```csharp
using System;
using System.Threading;
using System.Threading.Tasks;

public class EjemploConcurrencia {
    private readonly SemaphoreSlim semaforo = new SemaphoreSlim(2, 2);
    
    public async Task EjecutarTareasAsync() {
        var tareas = new Task[10];
        
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            tareas[i] = Task.Run(async () => {
                await semaforo.WaitAsync();
                try {
                    Console.WriteLine($"Tarea {taskId} ejecutándose");
                    await Task.Delay(1000);
                } finally {
                    semaforo.Release();
                }
            });
        }
        
        await Task.WhenAll(tareas);
    }
}
```

---

## Concurrencia en Python

### Mecanismos Principales

#### 1. **Threading Module**
```python
import threading

def worker():
    # Código del hilo
    pass

thread = threading.Thread(target=worker)
thread.start()
```

#### 2. **Multiprocessing Module**
```python
import multiprocessing

def worker(name):
    # Código del proceso
    pass

process = multiprocessing.Process(target=worker, args=('Worker1',))
process.start()
```

#### 3. **asyncio (Programación Asíncrona)**
```python
import asyncio

async def tarea_asincrona():
    await asyncio.sleep(1)
    return "Completado"

async def main():
    resultado = await tarea_asincrona()
    print(resultado)

asyncio.run(main())
```

#### 4. **Concurrent.futures**
```python
from concurrent.futures import ThreadPoolExecutor, ProcessPoolExecutor

with ThreadPoolExecutor(max_workers=4) as executor:
    future = executor.submit(funcion, argumentos)
    resultado = future.result()
```

### Librerías y Módulos de Python

#### **threading**
- **Clases principales:**
  - `Thread`, `Lock`, `RLock`
  - `Semaphore`, `BoundedSemaphore`
  - `Event`, `Condition`
  - `Timer`, `Barrier`

#### **multiprocessing**
- **Procesamiento multiproceso:**
  - `Process`, `Pool`
  - `Queue`, `Pipe`
  - `Manager`, `Value`, `Array`
  - `Lock`, `Semaphore` (para procesos)

#### **asyncio**
- **Programación asíncrona:**
  - `async`/`await` keywords
  - `asyncio.create_task()`, `asyncio.gather()`
  - `asyncio.Queue`, `asyncio.Semaphore`
  - `asyncio.Lock`, `asyncio.Event`

#### **concurrent.futures**
- **Abstracción de alto nivel:**
  - `ThreadPoolExecutor`, `ProcessPoolExecutor`
  - `Future`, `as_completed()`

#### **queue**
- **Colas thread-safe:**
  - `Queue`, `LifoQueue`, `PriorityQueue`

### Ejemplo Completo en Python
```python
import asyncio
import threading
from concurrent.futures import ThreadPoolExecutor

class EjemploConcurrencia:
    def __init__(self):
        self.semaforo = threading.Semaphore(2)
    
    def tarea_con_hilo(self, task_id):
        with self.semaforo:
            print(f"Tarea {task_id} ejecutándose")
            threading.Event().wait(1)  # Simular trabajo
    
    async def tarea_asincrona(self, task_id):
        semaforo_async = asyncio.Semaphore(2)
        async with semaforo_async:
            print(f"Tarea async {task_id} ejecutándose")
            await asyncio.sleep(1)
    
    def ejecutar_con_threads(self):
        with ThreadPoolExecutor(max_workers=4) as executor:
            futures = [executor.submit(self.tarea_con_hilo, i) for i in range(10)]
            for future in futures:
                future.result()
    
    async def ejecutar_asincrono(self):
        tareas = [self.tarea_asincrona(i) for i in range(10)]
        await asyncio.gather(*tareas)
```

---

## Comparación de Características

### Tabla Comparativa

| Característica | Java | C# | Python |
|----------------|------|----|---------| 
| **Threads Nativos** | ✅ Thread, Runnable | ✅ Thread | ✅ threading.Thread |
| **Pool de Threads** | ✅ ExecutorService | ✅ ThreadPool | ✅ ThreadPoolExecutor |
| **Async/Await** | ❌ (CompletableFuture) | ✅ async/await | ✅ async/await |
| **Paralelismo de Datos** | ✅ Parallel Streams | ✅ PLINQ | ✅ multiprocessing |
| **Semáforos** | ✅ Semaphore | ✅ Semaphore/Slim | ✅ threading.Semaphore |
| **Locks** | ✅ ReentrantLock | ✅ lock, Monitor | ✅ threading.Lock |
| **Colecciones Concurrentes** | ✅ java.util.concurrent | ✅ Collections.Concurrent | ✅ queue, multiprocessing |
| **Futures/Promises** | ✅ Future, CompletableFuture | ✅ Task<T> | ✅ concurrent.futures |

### Fortalezas por Lenguaje

#### **Java**
- ✅ **Ecosystem maduro** con muchas librerías
- ✅ **Fork/Join Framework** para divide y vencerás
- ✅ **JVM optimizada** para concurrencia
- ✅ **Amplia gama de primitivas** de sincronización

#### **C#**
- ✅ **async/await nativo** muy elegante
- ✅ **Task Parallel Library** muy potente
- ✅ **PLINQ** para paralelización automática
- ✅ **Integración perfecta** con .NET ecosystem

#### **Python**
- ✅ **asyncio moderno** y potente
- ✅ **Multiprocessing** evita el GIL
- ✅ **Sintaxis simple** y clara
- ✅ **Flexibilidad** en enfoques de concurrencia

### Limitaciones por Lenguaje

#### **Java**
- ❌ **Verbosidad** en comparación con async/await
- ❌ **Complejidad** en manejo de excepciones asíncronas

#### **C#**
- ❌ **Dependencia** del ecosistema .NET
- ❌ **Menos portable** que Java o Python

#### **Python**
- ❌ **GIL (Global Interpreter Lock)** limita threading
- ❌ **Performance** inferior en CPU-intensive tasks
- ❌ **Multiprocessing** tiene overhead de comunicación

---

## Casos de Uso Recomendados

### **Java**
- **Aplicaciones empresariales** de alta concurrencia
- **Servicios web** con muchas conexiones simultáneas
- **Sistemas distribuidos** complejos
- **Aplicaciones que requieren máximo rendimiento**

### **C#**
- **Aplicaciones web modernas** (ASP.NET Core)
- **Aplicaciones de escritorio** con UI responsiva
- **Servicios en la nube** (Azure)
- **APIs REST** con alta concurrencia

### **Python**
- **Aplicaciones I/O intensive** (web scraping, APIs)
- **Microservicios** con FastAPI/Django
- **Scripts de automatización** concurrentes
- **Análisis de datos** paralelo

---

## Conclusiones

### Evolución de la Concurrencia
1. **Java**: Pionero con threads, evolucionó hacia ExecutorService y CompletableFuture
2. **C#**: Revolucionó con async/await, estableciendo el estándar moderno
3. **Python**: Adoptó async/await y mejoró multiprocessing para superar limitaciones del GIL

### Tendencias Actuales
- **Programación asíncrona** se está convirtiendo en estándar
- **Abstracciones de alto nivel** (Tasks, Futures) son preferidas sobre threads directos
- **Paralelización automática** (PLINQ, Parallel Streams) simplifica el desarrollo
- **Colecciones concurrentes** eliminan la necesidad de sincronización manual

### Recomendaciones
- **Para principiantes**: Comenzar con C# async/await o Python asyncio
- **Para rendimiento**: Java con ExecutorService y concurrent collections
- **Para prototipado rápido**: Python con threading o multiprocessing
- **Para aplicaciones web**: C# ASP.NET Core o Python FastAPI con asyncio

Cada lenguaje ofrece herramientas poderosas para concurrencia, la elección depende del contexto específico, requisitos de rendimiento y experiencia del equipo de desarrollo.
