# 📚 CLASE 7 - EXECUTOR FRAMEWORK

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**📖 Tema:** Executor Framework

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [⚙️ Interface ExecutorService](#interface-executorservice)
2. [🔧 Implementaciones de Executors](#implementaciones-de-executors)
3. [📤 Métodos submit y Future](#métodos-submit-y-future)
4. [🔄 Interfaz Callable](#interfaz-callable)
5. [⏰ ScheduledExecutorService](#scheduledexecutorservice)
6. [💡 Ejemplos Prácticos](#ejemplos-prácticos)
7. [📚 Bibliografía](#bibliografía)

---

## ⚙️ **INTERFACE EXECUTORSERVICE**

### 📝 **Definición**

**ExecutorService** permite gestionar la programación concurrente de una forma **más sencilla y óptima**.

### 🎯 **Características**

- Representa un **mecanismo de ejecuciones asíncronas** para ejecutar tareas en segundo plano
- Se encuentra en el paquete `java.util.concurrent`
- Simplifica las tareas asíncronas proveyendo un **pool de hilos**
- Son **manejados por esta API** que abstrae del trabajo de crearlos y asignarles tareas

---

## 💻 **EJEMPLO BÁSICO DE EXECUTORSERVICE**

### 📋 **Código**

```java
ExecutorService executor = Executors.newCachedThreadPool();
// Inicia un pool de threads

// Ejecuta un thread del pool
executor.execute(new Runnable() {
    @Override
    public void run() {
        System.out.println("Tiempo: " + System.currentTimeMillis());
    }
});

System.out.println("Tiempo 2: " + System.currentTimeMillis());

// Finalizamos el pool
executor.shutdown();
```

### 🔍 **Componentes Clave**

#### 🏭 **Executors**
Clase proveedora de ExecutorServices que permite obtener una serie de implementaciones estándar.

#### 🔄 **newCachedThreadPool()**
Método estático que:
- Crea una implementación estándar para ExecutorServices
- Mantiene en caché los hilos de ejecución
- Si no existe un hilo disponible, crea uno nuevo
- Los hilos en caché que no se usan por **60 segundos** se destruyen
- **No tiene límite** de hilos a crear

#### ▶️ **execute()**
- Realiza la ejecución de un hilo
- Recibe un objeto que implementa la interfaz Runnable
- Define el proceso a ejecutar

#### 🛑 **shutdown()**
- Termina de forma segura todos los hilos de ejecución creados

---

## 💡 **EJEMPLO: CLASE TAREA**

### 📋 **Clase Tarea (Runnable)**

```java
package executors;

public class Tarea implements Runnable {
    private String nombre;
    
    public Tarea(String nombre) {
        super();
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(nombre);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

Esta clase será usada tanto con Runnable tradicional como con Executor.

---

## 📊 **COMPARACIÓN: RUNNABLE VS EXECUTOR**

### 1️⃣ **Forma Tradicional con RUNNABLE**

```java
package executors;

public class Principal {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Tarea("tarea1"));
        t1.start();
        
        Thread t2 = new Thread(new Tarea("tarea2"));
        t2.start();
        
        Thread t3 = new Thread(new Tarea("tarea3"));
        t3.start();
    }
}
```

### 2️⃣ **Forma Moderna con EXECUTOR**

```java
package executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class PrincipalExecutor {
    public static void main(String[] args) {
        Stream<String> flujo = Stream.of("tarea1", "tarea2", "tarea3");
        ExecutorService servicio = Executors.newCachedThreadPool();
        flujo.map(t -> new Tarea(t)).forEach(servicio::execute);
    }
}
```

### ✅ **Ventajas de Executor**

1. **Reducción de código**
2. **Flexibilidad**
3. **No inicializar los Threads manualmente**

---

## 🔧 **IMPLEMENTACIONES DE LA CLASE EXECUTORS**

### 1️⃣ **newFixedThreadPool(int n)**

```java
package executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        executor.execute(() -> doLongWork("hi 1"));
        executor.execute(() -> doLongWork("hi 2"));
        executor.execute(() -> doLongWork("hi 3"));
        
        executor.shutdown();
    }
    
    private static void doLongWork(String hola) {
        System.out.println("Running " + hola);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

**Características:**
- Crea un pool de hilos con un **tamaño fijo**
- Si se intenta ejecutar una tarea nueva cuando todos los hilos están trabajando, debe **esperar**
- Si algún hilo muere por una falla durante su ejecución, uno **nuevo será creado** en el pool cuando sea solicitado
- Ejemplo: creación de un pool con **10 threads**

---

### 2️⃣ **newScheduledThreadPool(int n)**

**Características:**
- Crea un pool de hilos que pueden ser **agendados**
- Pueden ejecutarse en **cierto momento** o **periódicamente**

### 3️⃣ **newSingleThreadExecutor()**

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
```

**Características:**
- Crea un pool de **1 solo hilo** de ejecución
- Obliga a que las tareas sean ejecutadas de manera **secuencial**
- Si el hilo muere por la falla de ejecución, crea uno **nuevo** en la siguiente petición
- Las tareas quedan en **cola** y se ejecutan de forma secuencial

### 4️⃣ **newSingleThreadScheduledExecutor()**

**Características:**
- Similar a las 2 anteriores
- Es un pool de **1 solo hilo**
- Puede ser **agendado** para ejecutarse en cierto momento o periódicamente

---

## 📤 **MÉTODO SUBMIT Y FUTURE**

### 📝 **Método submit(Runnable)**

Al igual que `execute()` recibe un Runnable, pero en este caso **retorna un objeto de tipo Future**.

**Objetivo:** Poder conocer en qué momento el hilo finalizó su ejecución.

### 💻 **Ejemplo con Future**

```java
// Inicia un pool de threads
ExecutorService executor = Executors.newCachedThreadPool();

// Ejecuta un thread del pool
Future future = executor.submit(new Runnable() {
    @Override
    public void run() {
        System.out.println("Tiempo: " + System.currentTimeMillis());
    }
});

// Retorna null cuando el hilo de ejecución finalizó
System.out.println(future.get());

executor.shutdown(); // Finaliza el pool
```

---

### 💻 **Ejemplo Completo con Future y Retorno de Valor**

```java
package executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceFutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        Future<String> future = executor.submit(() -> doLongWork("hi with future 1"));
        
        executor.shutdown();
        
        System.out.println("Valor de future: " + future.get());
    }
    
    private static String doLongWork(String msg) {
        System.out.println("Running " + msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "done " + msg;
    }
}
```

---

## 🔄 **INTERFAZ CALLABLE**

### 📝 **Diferencias entre Callable y Runnable**

La interfaz Callable es similar a Runnable, ambas están diseñadas para clases cuyas instancias se ejecutan potencialmente por otro hilo. Pero un Runnable **no devuelve un resultado** y **no puede arrojar una excepción marcada**.

### 📊 **Tabla Comparativa**

| Característica | Runnable | Callable |
|----------------|----------|----------|
| **Método a implementar** | `run()` | `call()` |
| **Retorna valor** | NO | SÍ |
| **Lanza excepciones** | NO (checked) | SÍ (checked) |
| **Uso con ExecutorService** | `execute()` | `submit()`, `invokeXXX()` |
| **Método de cola** | `execute()` | `submit()` |

### 🔑 **Puntos Clave**

- **Callable** necesita implementar el método `call()` y **Runnable** necesita implementar el método `run()`
- **Callable** puede devolver un valor, **Runnable NO PUEDE**
- **Callable** puede lanzar una excepción comprobada, **Runnable** no puede
- **Callable** se puede usar con `ExecutorService#invokeXXX(Collection<? extends Callable> tasks)` métodos pero **Runnable** no
- **Callable** usa el método `submit()` para poner en la cola de tareas, pero **Runnable** usa el método `execute()`

---

## 📋 **MÉTODOS PARA CALLABLE**

### 🔧 **Métodos Disponibles**

#### 1️⃣ **submit(Callable)**
Similar a `submit(Runnable)` pero recibe un objeto de tipo Callable.

#### 2️⃣ **invokeAny()**
- Ejecuta una lista de objetos Callable
- Como resultado obtiene el valor retornado por **uno de ellos**
- No se tiene certeza de cuál

#### 3️⃣ **invokeAll()**
- Similar al anterior pero recibe una lista de Callable
- Recibe una lista de objetos **Future**
- De los cuales se podrá monitorear el resultado final

---

## 💡 **EJEMPLO COMPARATIVO: RUNNABLE VS CALLABLE**

### 1️⃣ **Implementación con RUNNABLE**

#### **Clase Tarea1**

```java
package executors;

public class Tarea1 implements Runnable {
    @Override
    public void run() {
        int total = 0;
        for(int i = 0; i < 5; i++) {
            total += i;
            System.out.println("i: " + i + " total: " + total);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Nombre del Thread: " + Thread.currentThread().getName());
        System.out.println("Resultado devuelto: " + total);
    }
}
```

#### **Clase PrincipalHilo**

```java
package executors;

public class PrincipalHilo {
    public static void main(String[] args) {
        Tarea1 t = new Tarea1();
        Thread hilo = new Thread(t);
        hilo.start();
    }
}
```

**Problema:** No puede retornar el valor de `total` directamente.

---

### 2️⃣ **Implementación con CALLABLE**

#### **Clase MiCallable**

```java
package executors;
import java.util.concurrent.Callable;

public class MiCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int total = 0;
        for(int i = 0; i < 5; i++) {
            total += i;
            System.out.println("i: " + i + " total: " + total);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Nombre del Thread: " + Thread.currentThread().getName());
        return total;
    }
}
```

#### **Clase PrincipalCallable**

```java
package executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrincipalCallable {
    public static void main(String[] args) {
        try {
            ExecutorService servicio = Executors.newFixedThreadPool(1);
            Future<Integer> resultado = servicio.submit(new MiCallable());
            servicio.shutdown();
            
            System.out.println("Resultado devuelto: " + resultado.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```

**Ventaja:** Puede retornar el valor de `total` usando `Future<Integer>`.

---

## ⏰ **SCHEDULEDEXECUTORSERVICE**

### 📝 **Definición**

**Extiende de ExecutorService**, aparece porque ciertas tareas necesitan ejecutarse de una **manera programada**:
- Ejecución de una tarea dada a **intervalos determinados**
- O en un **momento específico**

Se encuentra en el paquete `java.util.concurrent`.

---

### 💻 **Ejemplo Básico**

```java
ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

ScheduledFuture future = executor.schedule(
    new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Ya pasaron 10 segundos.";
        }
    }, 10, TimeUnit.SECONDS);

System.out.println(future.get());
executor.shutdown();
```

### 🔍 **Componentes**

#### 📦 **ScheduledFuture**
Representación futura de la ejecución del thread.

#### ⏱️ **Método schedule()**
Recibe **3 parámetros:**
1. Un objeto **Callable** (o Runnable) con el proceso a ejecutar
2. Un **entero** que representa el tiempo a esperar para la ejecución
3. La **unidad de tiempo** (enumeración `TimeUnit`)

---

## 📋 **MÉTODOS DE SCHEDULEDEXECUTORSERVICE**

### 1️⃣ **schedule(Runnable, long, TimeUnit)**

```java
ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

ScheduledFuture future = executor.schedule(
    new Runnable() {
        @Override
        public void run() {
            System.out.println("Ya pasaron 10 segundos");
        }
    }, 10, TimeUnit.SECONDS);

future.get();
executor.shutdown();
```

Similar al ejemplo anterior pero el parámetro no es un objeto Callable sino un objeto Runnable.

---

### 2️⃣ **scheduleAtFixedRate()**

**Características:**
- Maneja **2 tiempos:**
  1. El tiempo de espera para la **primera ejecución**
  2. El tiempo de **ejecuciones sucesivas** luego de ocurrida la primera

### 3️⃣ **scheduleWithFixedDelay()**

**Características:**
- La forma de ejecución es similar a la anterior
- **Diferencia:** Las ejecuciones sucesivas esperan la **finalización de la ejecución anterior**

---

## 💡 **EJEMPLO 1: SCHEDULED CON RUNNABLE**

```java
package executors;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorRunnable {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        
        Runnable task2 = () -> System.out.println("Running task2...");
        
        task1();
        
        // run this task after 5 seconds, nonblock for task3
        ses.schedule(task2, 5, TimeUnit.SECONDS);
        
        task3();
        
        ses.shutdown();
    }
    
    public static void task1() {
        System.out.println("Running task1...");
    }
    
    public static void task3() {
        System.out.println("Running task3...");
    }
}
```

**Resultado:**
- `task1()` se ejecuta inmediatamente
- `task3()` se ejecuta inmediatamente
- `task2()` se ejecuta después de 5 segundos (no bloquea)

---

## 💡 **EJEMPLO 2: SCHEDULED CON REPETICIÓN**

```java
package executors;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorRepeat {
    private static int count = 0;
    
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        
        Runnable task1 = () -> {
            count++;
            System.out.println("Running...task1 - count : " + count);
        };
        
        // init Delay = 5, repeat the task every 1 second
        ScheduledFuture<?> scheduledFuture = 
            ses.scheduleAtFixedRate(task1, 5, 1, TimeUnit.SECONDS);
        
        while (true) {
            System.out.println("count :" + count);
            Thread.sleep(1000);
            
            if (count == 5) {
                System.out.println("Count is 5, cancel the scheduledFuture!");
                scheduledFuture.cancel(true);
                ses.shutdown();
                break;
            }
        }
    }
}
```

**Funcionamiento:**
- Espera **5 segundos** para la primera ejecución
- Repite la tarea cada **1 segundo**
- Se cancela cuando `count` llega a 5

---

## 📊 **TABLA RESUMEN: TIPOS DE EXECUTOR**

| Tipo | Características | Uso Recomendado |
|------|----------------|-----------------|
| **newCachedThreadPool()** | Pool dinámico, sin límite, cache 60s | Muchas tareas cortas |
| **newFixedThreadPool(n)** | Pool fijo de n hilos | Carga controlada |
| **newSingleThreadExecutor()** | 1 solo hilo, ejecución secuencial | Tareas en orden |
| **newScheduledThreadPool(n)** | Pool agendable, n hilos | Tareas programadas |
| **newSingleThreadScheduledExecutor()** | 1 hilo agendable | Tarea única programada |

---

## 📊 **MÉTODOS PRINCIPALES**

### ExecutorService

| Método | Descripción | Retorno |
|--------|-------------|---------|
| `execute(Runnable)` | Ejecuta tarea sin retorno | void |
| `submit(Runnable)` | Ejecuta tarea con Future | Future<?> |
| `submit(Callable<T>)` | Ejecuta tarea con resultado | Future<T> |
| `invokeAny(Collection)` | Ejecuta y retorna uno | T |
| `invokeAll(Collection)` | Ejecuta todos | List<Future<T>> |
| `shutdown()` | Finaliza ordenadamente | void |
| `shutdownNow()` | Finaliza inmediatamente | List<Runnable> |

### ScheduledExecutorService

| Método | Descripción | Parámetros |
|--------|-------------|-----------|
| `schedule()` | Ejecuta una vez con delay | task, delay, unit |
| `scheduleAtFixedRate()` | Ejecuta periódicamente | task, initialDelay, period, unit |
| `scheduleWithFixedDelay()` | Ejecuta con delay entre ejecuciones | task, initialDelay, delay, unit |

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **ExecutorService**

- Gestión simplificada de hilos
- Pool de hilos reutilizables
- Abstrae creación y asignación
- Paquete `java.util.concurrent`

### ✅ **Ventajas sobre Thread**

- Menor código
- Mayor flexibilidad
- Gestión automática de recursos
- Mejor rendimiento

### ✅ **Future**

- Representa resultado futuro
- Método `get()` espera resultado
- Permite cancelación
- Monitoreo de estado

### ✅ **Callable vs Runnable**

- Callable retorna valor
- Callable lanza excepciones checked
- Runnable más simple
- Callable más potente

### ✅ **ScheduledExecutorService**

- Tareas programadas
- Ejecución diferida
- Ejecución periódica
- Control de repeticiones

---

## 📚 **BIBLIOGRAFÍA CONSULTADA**

### 🌐 **Páginas Web**

Accedidas en Mayo de 2020:

1. **Java ExecutorService**  
   https://experto.dev/java-executorservice/

2. **Java Executor Service y Threading**  
   https://www.arquitecturajava.com/java-executor-service-y-threading/

3. **Java Callable Interface y su Uso**  
   https://www.arquitecturajava.com/java-callable-interface-y-su-uso/

4. **Java ScheduledExecutorService Examples**  
   https://mkyong.com/java/java-scheduledexecutorservice-examples/

5. **ExecutorService Documentation**  
   https://www.dokry.com/14805

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*