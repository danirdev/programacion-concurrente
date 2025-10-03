# 📚 CLASE 5 - INTERFAZ RUNNABLE

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**📖 Tema:** Interfaz Runnable

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [🔧 Implementación de Hilos con Runnable](#implementación-de-hilos-con-runnable)
2. [📝 Implementación del método run()](#implementación-del-método-run)
3. [🚀 Creación y Ejecución de Tareas](#creación-y-ejecución-de-tareas)
4. [💡 Ejemplo 1: Mostrar 0 y 1](#ejemplo-1-mostrar-0-y-1)
5. [🛒 Ejemplo 2: Cajera de Supermercado](#ejemplo-2-cajera-de-supermercado)
6. [📚 Bibliografía](#bibliografía)

---

## 🔧 **IMPLEMENTACIÓN DE HILOS USANDO LA INTERFAZ RUNNABLE**

### 📝 **Características de una Interfaz**

Una interfaz en Java:
- Solo puede contener **métodos abstractos** y/o **variables estáticas y finales** (constantes)
- **NO puede implementar** cualquier método
- Puede **extenderse de otras interfaces**
- Al contrario que las clases, puede extenderse de **múltiples interfaces**
- **No puede ser instanciada** con el operador `new`

### 🎯 **Ventajas de Runnable**

La interfaz Runnable es la técnica **más adecuada** para la creación de aplicaciones multitarea en lugar de la extensión de la clase Thread.

### 📋 **Requisitos**

Una clase que implementa una interfaz debe **implementar todos los métodos** definidos en esa interfaz.

### ✅ **Acciones Necesarias**

1. Implementar el método `run()` de la interfaz Runnable
2. Creación y ejecución de tareas

---

## 📝 **CONCEPTO DE LA INTERFAZ RUNNABLE**

### 🔄 **Definición**

La interfaz Runnable se implementa por **cualquier clase cuyas instancias se ejecuten por un hilo**. Dicha clase debe implementar el método `run()`.

### 🎯 **Forma Más Habitual**

La implementación de la interfaz Runnable es la forma **más habitual** de crear tareas.

### 🏗️ **Funcionamiento**

- La **interfaz** establece el trabajo a realizar
- La **clase o clases** que la implementan indican **cómo realizar ese trabajo**
- Permite construir un hilo sobre **cualquier objeto** que implemente la interfaz Runnable

### 📋 **Implementación**

Para implementar esta interfaz, una clase solo tiene que implementar el método `run()`:
- Dentro del `run()` se define el **código que constituye el nuevo hilo**

### 🔧 **Después de Crear la Clase**

Después de crear una clase que implemente la interfaz Runnable:
- Se debe **crear un objeto del tipo Thread** dentro de esa clase
- Usando los **constructores de Thread**

---

## 💻 **ESQUEMA DE IMPLEMENTACIÓN**

### 📋 **Estructura de la Clase con Runnable**

```java
// Crear una clase con hilos
public class MiClase implements Runnable {
    Thread unHilo;
    
    MiClase() {
        unHilo = new Thread();
    }
    
    public void run() {
        if (unHilo != null) {
            // Cuerpo del hilo
        }
    }
}
```

### 🚀 **Crear e Iniciar el Hilo**

Se crea el hilo e inicia su ejecución en la clase que usa a MiClase:

```java
MiClase miHilo = new MiClase();
new Thread(miHilo).start();
```

---

## 📝 **IMPLEMENTACIÓN DEL MÉTODO RUN()**

### 💻 **Ejemplo: Clase TareaRb**

```java
public class TareaRb implements Runnable {
    
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("Nombre " + Thread.currentThread().getName());
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### 🔍 **Análisis del Código**

#### 📋 **Único Método**
La interfaz Runnable incluye un **único método run()** con las acciones a realizar por las tareas.

#### 🧬 **Ventaja de Herencia**
Al codificar este método mediante la implementación de la interfaz Runnable:
- Permite a la clase **heredar al mismo tiempo** la funcionalidad de alguna otra clase existente

#### 🔍 **Método getName()**
- Al **no heredar de Thread**, no dispone de un constructor al que pasarle el nombre de la tarea
- La llamada a `currentThread()` no devuelve un objeto `TareaRb`
- Devuelve un objeto de la clase **Thread** que representa la tarea en ejecución

---

## 🚀 **CREACIÓN Y EJECUCIÓN DE TAREAS**

### 💻 **Clase Principal**

```java
public class Principal {
    public static void main(String[] args) {
        // Se crea 1 único objeto tarearb que se comparte en cada thread
        TareaRb t = new TareaRb();
        
        // Las tareas son instancias de la clase Thread
        Thread t1 = new Thread(t, "pepe");
        Thread t2 = new Thread(t, "ana");
        Thread t3 = new Thread(t, "juan");
        
        // Los Threads se ponen en ejecución
        t1.start();
        t2.start();
        t3.start();
    }
}
```

### 🔑 **Conceptos Clave**

#### 🧵 **Toda tarea es un thread**
- Hay que crear **tantos objetos de la clase Thread** como tareas se quieran poner en ejecución

#### 📋 **Código de las tareas**
- El código de esas tareas debe definirse en el método `run()` de la clase que implementa `Runnable()`

### 🔧 **Constructores de Thread**

Para crear objetos Thread se debe usar alguno de los siguientes constructores:

```java
Thread(Runnable obj)
Thread(Runnable obj, String nombre)
```

---

## 💡 **EJEMPLO 1: MOSTRAR 0 Y 1**

### 🎯 **Objetivo**

Mostrar el número "0" mil veces y el número "1" mil veces.

---

### 1️⃣ **SIN HILOS**

```java
public class MostrarCeroUno {
    
    public void mostrar0() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("0-");
    }
    
    public void mostrar1() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("1-");
    }
    
    public static void main(String[] args) {
        MostrarCeroUno m = new MostrarCeroUno();
        m.mostrar0();
        m.mostrar1();
    }
}
```

**Resultado:** Primero se muestran todos los 0, luego todos los 1 (secuencial)

---

### 2️⃣ **CON THREAD (HERENCIA)**

```java
public class MostrarCeroUnoHilo {
    
    public static void main(String[] args) {
        HiloMostrarCero h1 = new HiloMostrarCero();
        h1.start();
        
        HiloMostrarUno h2 = new HiloMostrarUno();
        h2.start();
    }
}

class HiloMostrarCero extends Thread {
    @Override
    public void run() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("0-");
    }
}

class HiloMostrarUno extends Thread {
    @Override
    public void run() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("1-");
    }
}
```

**Resultado:** Los 0 y 1 se mezclan (ejecución concurrente)

---

### 3️⃣ **CON INTERFAZ RUNNABLE**

```java
public class MostrarCeroUnoHiloRunnable {
    
    public static void main(String[] args) {
        HiloMostrarCeroRunnable h1 = new HiloMostrarCeroRunnable();
        HiloMostrarUnoRunnable h2 = new HiloMostrarUnoRunnable();
    }
}

class HiloMostrarCeroRunnable implements Runnable {
    private Thread t;
    
    public HiloMostrarCeroRunnable() {
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("0-");
    }
}

class HiloMostrarUnoRunnable implements Runnable {
    private Thread t;
    
    public HiloMostrarUnoRunnable() {
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        for (int f = 1; f <= 1000; f++)
            System.out.print("1-");
    }
}
```

**Resultado:** Los 0 y 1 se mezclan (ejecución concurrente)

---

## 🛒 **EJEMPLO 2: CAJERA DE SUPERMERCADO**

### 🎯 **Descripción del Problema**

Se simula el proceso de cobro de un supermercado donde:
- Los **clientes** adquieren productos
- Una **cajera** cobra los productos, pasando uno a uno por el escáner
- La cajera debe procesar la compra **cliente a cliente**

### 📊 **Representación de Productos**

Se define un **array de enteros** que representa:
- Los productos comprados
- El tiempo que la cajera tarda en pasar cada producto por el escáner

**Ejemplo:** `[1, 3, 5]` significa:
- El cliente ha comprado **3 productos**
- Producto 1: tarda **1 segundo**
- Producto 2: tarda **3 segundos**
- Producto 3: tarda **5 segundos**
- **Total:** 9 segundos para toda la compra

---

## 📋 **CLASE "CLIENTE.JAVA"**

```java
public class Cliente {
    private String nombre;
    private int[] carroCompra;
    
    public Cliente(String nombre, int[] carroCompra) {
        super();
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int[] getCarroCompra() {
        return carroCompra;
    }
    
    public void setCarroCompra(int[] carroCompra) {
        this.carroCompra = carroCompra;
    }
}
```

---

## 📋 **CLASE "CAJERA.JAVA"**

```java
public class Cajera {
    private String nombre;
    
    public Cajera(String nombre) {
        super();
        this.nombre = nombre;
    }
    
    public void procesarCompra(Cliente cliente, long timeStamp) {
        System.out.println("La cajera " + this.nombre
            + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE " + cliente.getNombre()
            + " EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        
        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1)
                + " ->Tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        }
        
        System.out.println("La cajera " + this.nombre
            + " HA TERMINADO DE PROCESAR " + cliente.getNombre() + " EN EL TIEMPO: "
            + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
    }
    
    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
```

---

## 📋 **CLASE "MAIN.JAVA" (SIN HILOS)**

```java
public class Main {
    
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });
        
        Cajera cajera1 = new Cajera("Cajera 1");
        Cajera cajera2 = new Cajera("Cajera 2");
        
        long initialTime = System.currentTimeMillis(); // Tiempo inicial de referencia
        
        cajera1.procesarCompra(cliente1, initialTime);
        cajera2.procesarCompra(cliente2, initialTime);
    }
}
```

### ⚠️ **Comportamiento**

Si se ejecuta este programa con 2 clientes y 1 solo proceso:
- Se procesa **primero** la compra del Cliente 1
- **Después** la del Cliente 2
- Tiempo total: **Cliente 1 + Cliente 2 = 26 segundos**

### 🔴 **CUIDADO**

Aunque se hayan puesto 2 objetos de la clase Cajera (cajera1 y cajera2):
- **NO significa** tener 2 cajeras independientes
- Lo que se afirma es que dentro del **mismo hilo** se ejecutan primero los métodos de cajera1 y después los de cajera2
- A nivel de procesamiento es como tener **1 sola cajera**

---

## 📋 **CLASE "CAJERATHREAD.JAVA" (CON THREAD)**

### 🔧 **Modificación de la Clase**

Se pueden procesar 2 clientes a la vez si hubiese 2 cajeras y se asigna una a cada cliente. Para ello se modifica la clase "Cajera.java" haciendo que **herede de la clase Thread**.

```java
public class CajeraThread extends Thread {
    private String nombre;
    private Cliente cliente;
    private long initialTime;
    
    // Constructor, getter & setter
    public CajeraThread(String nombre, Cliente cliente, long initialTime) {
        super();
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }
```

---

### 📋 **Método run() Sobrescrito**

```java
    @Override
    public void run() {
        System.out.println("La cajera " + this.nombre 
            + " comienza a procesar la compra del cliente " + this.cliente.getNombre()
            + " en el tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        
        for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesado el producto " + (i + 1) + " del cliente " 
                + this.cliente.getNombre()
                + "->Tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        }
        
        System.out.println("La cajera " + this.nombre + " TERMINO DE PROCESAR " 
            + this.cliente.getNombre()
            + " EN EL TIEMPO: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
    }
    
    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### 🔍 **Por qué @Override**

Es necesario **sobre-escribir el método run()** (de ahí la etiqueta `@Override`) porque:
- Es un método que está en la clase **Runnable**
- La clase **Thread implementa** esa interfaz
- En él se codifica la **funcionalidad que se ejecuta en un hilo**
- Lo que se programe en el método `run()` se va a ejecutar de forma **secuencial en un hilo**

### ⚙️ **Métodos Adicionales**

En la clase "CajeraThread" se pueden sobre-escribir más métodos para que hagan acciones sobre el hilo o thread:
- Parar el thread
- Ponerlo en reposo
- Etc.

---

## 📋 **CLASE "MAINTHREAD.JAVA"**

```java
public class MainThread {
    
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });
        
        long initialTime = System.currentTimeMillis(); // Tiempo inicial de referencia
        
        CajeraThread cajera1 = new CajeraThread("Cajera 1", cliente1, initialTime);
        CajeraThread cajera2 = new CajeraThread("Cajera 2", cliente2, initialTime);
        
        cajera1.start();
        cajera2.start();
    }
}
```

### ✅ **Resultado**

El método Main de la clase "MainThread.java":
- Procesa las compras de los clientes de forma **paralela e independiente**
- Tarda solo **15 segundos** en terminar su ejecución
- En lugar de 26 segundos (versión secuencial)

---

## 📋 **CLASE "MAINRUNNABLE.JAVA" (CON RUNNABLE)**

```java
public class MainRunnable implements Runnable {
    private Cliente cliente;
    private Cajera cajera;
    private long initialTime;
    
    public MainRunnable(Cliente cliente, Cajera cajera, long initialTime) {
        this.cajera = cajera;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }
    
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });
        
        Cajera cajera1 = new Cajera("Cajera 1");
        Cajera cajera2 = new Cajera("Cajera 2");
        
        long initialTime = System.currentTimeMillis(); // Tiempo inicial de referencia
        
        Runnable proceso1 = new MainRunnable(cliente1, cajera1, initialTime);
        Runnable proceso2 = new MainRunnable(cliente2, cajera2, initialTime);
        
        new Thread(proceso1).start();
        new Thread(proceso2).start();
    }
    
    @Override
    public void run() {
        this.cajera.procesarCompra(this.cliente, this.initialTime);
    }
}
```

### 🔍 **Análisis de la Implementación**

#### 📋 **Ventajas de Runnable**

Otra forma es implementar la interfaz "Runnable":
- **No se dispone** ni se podrá sobre-escribir los métodos de la clase Thread
- Ya que no se van a usar
- Solo se va a tener que sobre-escribir el método `run()`

#### ⚙️ **Simplicidad**

Solo es necesario implementar el método `run()` para que:
- Los procesos implementados en ese método se ejecuten en **un hilo diferente**

#### 🔄 **Reutilización**

En este ejemplo se utilizan objetos de las clases "Cliente.java" y "Cajera.java" para implementar la multitarea.

---

## 📊 **COMPARACIÓN: THREAD VS RUNNABLE**

### 📋 **Tabla Comparativa**

| Aspecto | Thread (Herencia) | Runnable (Interfaz) |
|---------|------------------|---------------------|
| **Extensión** | Extiende Thread | Implementa Runnable |
| **Herencia adicional** | ❌ No permite | ✅ Sí permite |
| **Métodos Thread** | ✅ Acceso directo | ❌ No tiene acceso |
| **Flexibilidad** | Limitada | Alta |
| **Recomendación** | Menos común | **Más recomendada** |
| **Reutilización** | Limitada | Mayor |

### ✅ **Ventajas de Runnable**

1. **Herencia múltiple simulada**
   - Puede heredar de otra clase y aún implementar Runnable

2. **Separación de responsabilidades**
   - La tarea está separada del mecanismo de ejecución

3. **Reutilización de código**
   - El mismo objeto Runnable puede usarse en múltiples threads

4. **Flexibilidad**
   - No está atado a la jerarquía de Thread

### ⚠️ **Cuándo Usar Thread**

- Cuando necesitas sobre-escribir otros métodos de Thread
- Cuando no necesitas heredar de otra clase
- Para casos muy simples

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Interfaz Runnable**

- Solo tiene **un método**: `run()`
- Es la forma **más recomendada** de crear hilos
- Permite **herencia adicional**
- Separa la **tarea** de la **ejecución**

### ✅ **Implementación**

- Debe implementar el método `run()`
- Crear un objeto Thread con el Runnable
- Llamar a `start()` para iniciar la ejecución

### ✅ **Constructores Thread**

```java
Thread(Runnable obj)
Thread(Runnable obj, String nombre)
```

### ✅ **Ventajas Principales**

- Mayor flexibilidad
- Mejor diseño orientado a objetos
- Permite herencia de otras clases
- Reutilización de código

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 📖 **Libros**

**García de Jalón J, Rodríguez J, Mingo I, Imaz A, Brazalez A, Larzabal A, Calleja J y García J** (2000)  
*Aprenda Java como si estuviera en primero*

**Sánchez Jorge** (2004)  
*Java2 incluye Swing, Threads, programación en red, Javabeans, JDBC y JSP / Servlets*

### 🌐 **Páginas Web Consultadas**

Accedidas en Octubre de 2023:

1. **Multitarea e Hilos en Java con Ejemplos**  
   http://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/

2. **Tutoriales de Programación - Hilos**  
   https://www.tutorialesprogramacionya.com/javaya/detalleconcepto.php?codigo=180

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*