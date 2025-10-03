# 📚 CLASE 6 - SEMÁFOROS CON JAVA

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**📖 Tema:** Semáforos con Java

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [🔒 Semáforo - Definición](#semáforo-definición)
2. [🔑 Semáforo Binario](#semáforo-binario)
3. [✅ Forma Correcta de Implementar](#forma-correcta-de-implementar)
4. [📋 Métodos de la Clase Semaphore](#métodos-de-la-clase-semaphore)
5. [💡 Ejemplos Prácticos](#ejemplos-prácticos)
6. [📚 Bibliografía](#bibliografía)

---

## 🔒 **SEMÁFORO - DEFINICIÓN**

### 📝 **Concepto**

Un **semáforo** es una variable especial (o tipo abstracto de datos) que constituye el **método clásico** para:
- **Restringir** o **permitir** el acceso a recursos compartidos
- En un entorno **multiproceso**

### 💻 **Sintaxis Básica**

```java
Semaphore semaphore = new Semaphore(1);

semaphore.acquire();
// zona crítica
semaphore.release();
```

### 🎯 **Ventajas sobre wait() y notify()**

- El uso de los métodos `notify()` y `wait()` **no son lo suficientemente exhaustivos** para controlar la ejecución de hilos que acceden a objetos compartidos

- Los semáforos se pueden usar de la **misma manera** que el método `wait()` al restringir un bloque de código (`synchronized`)

- Los semáforos son **más flexibles** y permiten que los hilos puedan **conocer su estado antes de pedir el bloqueo** de alguna propiedad

---

## 🔑 **SEMÁFORO BINARIO**

### 📝 **Definición**

Son los **semáforos más sencillos**.

### 🔄 **Funcionamiento**

Para entrar en una zona crítica:
1. Un thread debe **adquirir** el derecho de acceso
2. Al salir, debe **liberarlo**

### ⚠️ **Regla Fundamental**

**Nunca hay que olvidar liberar un semáforo** al salir de la zona crítica.

### 🔴 **Problema con el Código Básico**

```java
semaphore.acquire();
// zona crítica
semaphore.release();
```

Este código se hace si se sale **normalmente**, pero:
- Si se sale de forma **abrupta** (`return` o excepción)
- Entonces el semáforo **no se libera**

---

## ✅ **FORMA CORRECTA DE IMPLEMENTAR UN SEMÁFORO**

### 💻 **Patrón Recomendado**

```java
semaphore.acquire();
try {
    // zona crítica
} finally {
    semaphore.release();
}
```

### 🎯 **Por qué este Patrón**

El bloque `finally` garantiza que:
- El semáforo **siempre se libera**
- Incluso si ocurre una excepción
- Incluso si hay un `return` prematuro

---

## 🔢 **SEMÁFOROS CON N PERMISOS**

### 📝 **Funcionamiento**

Un semáforo puede tener **N permisos**:

1. Los threads **solicitan** algunos permisos
2. Si los hay:
   - Los **retiran** del contador
   - **Siguen** ejecutándose
3. Si no los hay:
   - Quedan **esperando** a que los haya
4. Cuando se termina:
   - El thread **devuelve** los permisos

---

## 📋 **MÉTODOS DE LA CLASE SEMAPHORE**

### 📦 **Paquete**

```java
java.util.concurrent.Semaphore
```

### 🔧 **Constructor**

#### **Semaphore(int permits)**
```java
Semaphore semaphore = new Semaphore(10);
```

Crea un semáforo con un **cierto número de permisos**.

---

### 🔒 **Métodos de Adquisición**

#### 1️⃣ **acquire(int permits)**

```java
public void acquire(int permits) throws InterruptedException
```

**Funcionalidad:**
- **Solicita permisos**
- Si **no hay suficientes** → queda esperando
- Si **los hay** → se descuentan del semáforo y se sigue
- Si llega una **interrupción** → se aborta la espera

**Ejemplo:**
```java
semaphore.acquire(1);  // Adquirir 1 permiso
```

#### 2️⃣ **acquireUninterruptibly(int permits)**

```java
public void acquireUninterruptibly(int permits)
```

**Funcionalidad:**
- **Solicita permisos**
- Si **no hay suficientes** → queda esperando
- Si **los hay** → se descuentan del semáforo y se sigue
- Si llega una **interrupción** → **se ignora** y se sigue esperando

**Ejemplo:**
```java
semaphore.acquireUninterruptibly(1);  // Adquirir 1 permiso
```

---

### 🔓 **Método de Liberación**

#### **release(int permits)**

```java
public void release(int permits)
```

**Funcionalidad:**
- **Libera** unos permisos que retornan a la cuenta del semáforo
- Si hay threads esperando → se reactivan

**Ejemplo:**
```java
semaphore.release(1);  // Liberar 1 permiso
```

---

## 💡 **EJEMPLO 1: SEMÁFORO BÁSICO**

### 💻 **Código Completo**

```java
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    Semaphore semaphore = new Semaphore(10);
    
    public void printLock() {
        try {
            semaphore.acquire();
            System.out.println("Locks acquired");
            System.out.println("Locks remaining >> " + semaphore.availablePermits());
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println("Locks Released");
            System.out.println("Locks remaining >> " + semaphore.availablePermits());
        }
    }
    
    public static void main(String[] args) {
        final SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                semaphoreDemo.printLock();
            }
        };
        
        thread.start();
    }
}
```

### 🔍 **Análisis del Código**

- **Semáforo con 10 permisos** iniciales
- **acquire():** Adquiere un permiso
- **availablePermits():** Muestra permisos disponibles
- **finally:** Garantiza que el permiso se libera
- **release():** Devuelve el permiso al semáforo

---

## 💡 **EJEMPLO 2: TUBERÍA CON LÍMITE**

### 💻 **Código Completo**

```java
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Tuberia {
    
    class Hilo extends Thread {
        int id;
        
        public Hilo(int id) {
            this.id = id;
        }
        
        @Override
        public void run() {
            hil(id);
        }
    }
    
    Semaphore guardia;
    AtomicInteger count = new AtomicInteger(0);
    
    public Tuberia(int limite) {
        guardia = new Semaphore(limite, true);
    }
    
    public void hil(int id) {
        try {
            guardia.acquire();
        } catch (InterruptedException e) {}
        
        synchronized(count) {
            System.out.println(String.format("Hilo %d entró (%d)", 
                id, count.incrementAndGet()));
        }
        
        synchronized(count) {
            System.out.println(String.format("Hilo %d salió (%d)", 
                id, count.decrementAndGet()));
        }
        
        guardia.release();
    }
    
    public void creaHilos(int num) {
        for (int i = 1; i <= num; i++) {
            new Hilo(i).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
    
    public static void main(String[] args) {
        Tuberia tub = new Tuberia(5);
        tub.creaHilos(10);
    }
}
```

### 🔍 **Análisis del Código**

- **Semáforo con límite de 5** hilos simultáneos
- **AtomicInteger** para contador thread-safe
- Solo **5 hilos pueden ejecutarse** a la vez
- Los demás **esperan** hasta que se libere un permiso

---

## 💡 **EJEMPLO 3: PRODUCTOR-CONSUMIDOR**

### 📋 **Clase Productor**

```java
public class Productor extends Thread {
    private Almacen almacen;
    
    public Productor(String name, Almacen almacen) {
        super(name);
        this.almacen = almacen;
    }
    
    @Override
    public void run() {
        while (true) {
            almacen.producir(this.getName());
        }
    }
}
```

### 📋 **Clase Consumidor**

```java
public class Consumidor extends Thread {
    private Almacen almacen;
    
    public Consumidor(String name, Almacen almacen) {
        super(name);
        this.almacen = almacen;
    }
    
    @Override
    public void run() {
        while(true) {
            almacen.consumir(this.getName());
        }
    }
}
```

---

### 📋 **Clase Almacen**

```java
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Almacen {
    private final int MAX_LIMITE = 20;
    private int producto = 0;
    
    private Semaphore productor = new Semaphore(MAX_LIMITE);
    private Semaphore consumidor = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);
    
    public void producir(String nombreProductor) {
        System.out.println(nombreProductor + " intentando almacenar un producto");
        
        try {
            productor.acquire();
            mutex.acquire();
            
            producto++;
            System.out.println(nombreProductor + " almacena un producto. "
                + "Almacén con " + producto 
                + (producto > 1 ? " productos." : " producto."));
            
            mutex.release();
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consumidor.release();
        }
    }
```

---

```java
    public void consumir(String nombreConsumidor) {
        System.out.println(nombreConsumidor + " intentando retirar un producto");
        
        try {
            consumidor.acquire();
            mutex.acquire();
            
            producto--;
            System.out.println(nombreConsumidor + " retira un producto. "
                + "Almacén con " + producto 
                + (producto > 1 ? " productos." : " producto."));
            
            mutex.release();
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            productor.release();
        }
    }
}
```

---

### 📋 **Clase ProductorConsumidor (Main)**

```java
public class ProductorConsumidor {
    
    public static void main(String[] args) {
        final int PRODUCTOR = 3;
        final int CONSUMIDOR = 10;
        
        Almacen almacen = new Almacen();
        
        for (int i = 0; i < PRODUCTOR; i++) {
            new Productor("Productor " + i, almacen).start();
        }
        
        for (int i = 0; i < CONSUMIDOR; i++) {
            new Consumidor("Consumidor " + i, almacen).start();
        }
    }
}
```

### 🔍 **Análisis del Sistema**

#### 🔒 **Tres Semáforos**

1. **productor (MAX_LIMITE permisos)**
   - Controla espacios disponibles en el almacén
   - Inicia con 20 permisos

2. **consumidor (0 permisos)**
   - Controla productos disponibles para consumir
   - Inicia con 0 permisos

3. **mutex (1 permiso)**
   - Garantiza exclusión mutua al acceder a `producto`
   - Solo un hilo modifica la variable a la vez

#### 🔄 **Flujo de Producción**

1. Productor intenta `acquire()` del semáforo productor
2. Si hay espacio, adquiere `mutex`
3. Incrementa `producto`
4. Libera `mutex`
5. Libera un permiso del semáforo `consumidor`

#### 🔄 **Flujo de Consumo**

1. Consumidor intenta `acquire()` del semáforo consumidor
2. Si hay productos, adquiere `mutex`
3. Decrementa `producto`
4. Libera `mutex`
5. Libera un permiso del semáforo `productor`

---

## 💡 **EJEMPLO 4: SEMÁFORO CON RUNNABLE**

### 💻 **Código Completo**

```java
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    int i = 0;
    
    public static void main(String[] args) {
        final SemaphoreExample example = new SemaphoreExample();
        final Semaphore semaphore = new Semaphore(1);
        
        final Runnable r = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        semaphore.acquire();
                        
                        // Sección crítica a proteger
                        example.printSomething();
                        Thread.sleep(1000);
                        
                        semaphore.release();
                    } catch (Exception ex) {
                        System.out.println("— Interrupted…");
                        ex.printStackTrace();
                    }
                }
            }
        };
        
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
    }
    
    public void printSomething() {
        i++;
        System.out.println("— current value of the i :" + i);
    }
}
```

### 🔍 **Análisis del Código**

- **3 threads** ejecutan el mismo Runnable
- **Semáforo binario** (1 permiso) protege la sección crítica
- Solo **un thread a la vez** puede ejecutar `printSomething()`
- Garantiza que `i` se incremente de forma **ordenada**

---

## 💡 **EJEMPLO 5: LÍMITE DE EJECUCIONES CONCURRENTES**

### 💻 **Código Completo**

```java
import java.util.*;
import java.util.concurrent.*;

public class SemApp {
    
    public static void main(String[] args) {
        
        Runnable limitedCall = new Runnable() {
            final Random rand = new Random();
            final Semaphore available = new Semaphore(3);
            int count = 0;
            
            public void run() {
                int time = rand.nextInt(15);
                int num = count++;
                
                try {
                    available.acquire();
                    
                    System.out.println("Executing " +
                        "long-running action for " +
                        time + " seconds... #" + num);
                    
                    Thread.sleep(time * 1000);
                    
                    System.out.println("Done with #" + num + "!");
                    
                    available.release();
                } catch (InterruptedException intEx) {
                    intEx.printStackTrace();
                }
            }
        };
        
        for (int i = 0; i < 10; i++)
            new Thread(limitedCall).start();
    }
}
```

### 🔍 **Análisis del Código**

- **10 threads** se crean
- Semáforo con **3 permisos** limita la concurrencia
- Solo **3 threads pueden ejecutarse simultáneamente**
- Los demás **esperan** hasta que se libere un permiso
- Simula ejecución de **tareas de larga duración**

---

## 📊 **COMPARACIÓN: SYNCHRONIZED VS SEMAPHORE**

### 📋 **Tabla Comparativa**

| Aspecto | synchronized | Semaphore |
|---------|--------------|-----------|
| **Flexibilidad** | Limitada | Alta |
| **Permisos** | Solo 1 (binario) | N permisos configurables |
| **Liberación** | Automática al salir del bloque | Manual con release() |
| **Fairness** | No garantizado | Puede ser fair (FIFO) |
| **Estado** | No se puede consultar | Se puede consultar con availablePermits() |
| **Adquisición** | Bloqueo inmediato | Puede intentar sin bloquear (tryAcquire) |
| **Uso** | Más simple | Más flexible y potente |

### ✅ **Cuándo Usar Semaphore**

1. Necesitas **limitar el número de accesos concurrentes** (no solo a 1)
2. Quieres **conocer el estado** del semáforo antes de bloquearte
3. Necesitas **fairness** (orden FIFO de atención)
4. Quieres **intentar adquirir** sin bloquearte necesariamente
5. Implementas patrones como **productor-consumidor**

### ✅ **Cuándo Usar synchronized**

1. Necesitas **exclusión mutua simple** (solo 1 hilo)
2. Prefieres **sintaxis más simple**
3. No necesitas **consultar el estado**
4. Quieres **liberación automática** al salir del bloque

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Semáforos**

- **Variable especial** para controlar acceso a recursos compartidos
- Tiene **N permisos** configurables
- Los threads **adquieren** y **liberan** permisos
- **Más flexible** que synchronized

### ✅ **Tipos**

- **Binario:** 1 solo permiso (similar a mutex)
- **Contador:** N permisos (controla concurrencia)
- **Fair:** Garantiza orden FIFO

### ✅ **Métodos Principales**

```java
acquire()              // Adquiere 1 permiso
acquire(n)            // Adquiere n permisos
release()             // Libera 1 permiso
release(n)            // Libera n permisos
availablePermits()    // Consulta permisos disponibles
tryAcquire()          // Intenta adquirir sin bloquear
```

### ✅ **Patrón Correcto**

```java
semaphore.acquire();
try {
    // sección crítica
} finally {
    semaphore.release();
}
```

### ✅ **Aplicaciones**

- **Productor-Consumidor:** Control de buffer limitado
- **Pool de Recursos:** Limitar conexiones, hilos, etc.
- **Control de Concurrencia:** Máximo N accesos simultáneos
- **Sincronización:** Coordinar hilos productores y consumidores

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 📖 **Libros**

**José A. Mañas**  
*Java Vademécum /concurrencia*  
Disponible en: https://www.dit.upm.es/~pepe/libros/concurrency.pdf

### 🌐 **Páginas Web Consultadas**

Accedidas en Octubre de 2023:

1. **Java Util Concurrent Semaphore Example**  
   https://examples.javacodegeeks.com/core-java/util/concurrent/semaphore/java-util-concurrent-semaphore-example/

2. **Java Concurrente: Uso de Semáforos**  
   https://unpocodejava.com/2010/10/08/java-concurrente-uso-de-semaforos/

3. **Semáforos en Java**  
   https://trasteandocodigo.home.blog/2020/02/17/semaforos-en-java/

4. **Stack Overflow - Semáforos en Java**  
   https://es.stackoverflow.com/questions/59304/sem%C3%A1foros-en-java

5. **Programación Concurrente - Comunicación entre Hilos - Semáforos**  
   http://redimprogramacion.com/programacion-concurrente-comunicacion-entre-hilos-semaforos/

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*