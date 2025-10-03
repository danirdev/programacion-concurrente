# 📚 CLASE 3 - CONCURRENCIA EN JAVA USANDO HILOS

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** ING. JOSÉ FARFÁN  
**📖 Tema:** Concurrencia en Java usando Hilos

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [🎯 Razones para la Concurrencia](#razones-para-la-concurrencia)
2. [⚙️ Programas y Procesos](#programas-y-procesos)
3. [🧵 Hilos (Threads)](#hilos-threads)
4. [🔧 Formas de Implementar Hilos](#formas-de-implementar-hilos)
5. [📊 Estados de un Hilo](#estados-de-un-hilo)
6. [🛠️ Herramientas de Planificación](#herramientas-de-planificación)
7. [⚠️ Región Crítica](#región-crítica)
8. [🔄 Multiprocesamiento en Java](#multiprocesamiento)
9. [📚 Bibliografía](#bibliografía)

---

## 🎯 **RAZONES PARA LA CONCURRENCIA EN JAVA USANDO HILOS**

### 📌 **Contexto Técnico**

Incluso en un **esquema tradicional** con:
- 1 computadora
- 1 único procesador
- 1 sola instrucción por vez

Pueden existir **varios procesos concurrentes**.

### 🔄 **Simulación de Multitarea**

**Técnicas para simular multiejecución:**
- Los **Sistemas Operativos multitarea** permiten que los programas parezcan ejecutarse simultáneamente
- Aunque físicamente esto no es posible, **simulan la multitarea**
- El SO gestiona el tiempo de CPU entre diferentes procesos

### 💻 **Procesadores Multinúcleo**

**Realidad actual:**
- Existen procesadores con múltiples núcleos: **2, 4, 6, 8, 10, 12, ..., 64 (XEON)**
- Sin embargo, estos núcleos pueden **no ser suficientes** para la cantidad de programas que desean ejecutarse
- **Solución:** Implementar hilos (threads) a nivel de software

### ⚠️ **Problemas de la Concurrencia**

La multitarea y multihilos implica desafíos:
- **Acceso a regiones críticas**
- **Sincronización de hilos**
- **Coordinación de recursos compartidos**

---

## ⚙️ **PROGRAMAS Y PROCESOS**

### 📝 **Definiciones Fundamentales**

#### 🔷 **PROGRAMA**
Un **programa en Java** es:
- Un archivo `.class`
- Es **ESTÁTICO**
- **No requiere:**
  - Espacio de memoria (hasta que se ejecute)
  - Tiempo de CPU

#### 🔶 **PROCESO**
Un **proceso** es:
- Un **programa en ejecución**
- El archivo `.class` después de pulsar el botón "Ejecutar" en Eclipse
- Es **DINÁMICO**
- **Requiere:**
  - Tiempo de CPU
  - Almacenar su código en memoria

### 📊 **Comparación**

| Aspecto | Programa | Proceso |
|---------|----------|---------|
| **Estado** | Estático | Dinámico |
| **Archivo** | .class | .class en ejecución |
| **CPU** | No requiere | Requiere tiempo |
| **Memoria** | No ocupa | Ocupa espacio |
| **Actividad** | Inactivo | Activo |

---

## 🧵 **HILOS (THREADS)**

### 📝 **Definición**

Un **hilo** es:
- **1 parte de 1 proceso**
- Tiene **variables locales propias**
- **Comparte la memoria** con el resto de hilos del mismo proceso
- Permite a una aplicación realizar **varias tareas a la vez** (concurrentemente)

### 🎯 **Características de los Hilos**

#### ✅ **Ventajas**
- **Mayor rapidez** de ejecución comparado con multiprocesos
- **Menor overhead** que crear procesos separados
- **Comunicación más eficiente** entre hilos del mismo proceso

#### ⚠️ **Complejidad**
La programación con hilos puede ser compleja porque:
- Se debe controlar que el trabajo de un hilo **no interfiera** con otro
- Es necesario **coordinar los hilos** entre ellos
- Requiere manejo de **sincronización**

---

## 🔧 **FORMAS DE IMPLEMENTAR HILOS EN JAVA**

En Java existen **3 formas** de implementar concurrencia:

### 1️⃣ **Herencia de la clase Thread**

```java
public class hiloHerencia extends Thread {
    
    public static void main(String[] args) {
        hiloHerencia hilo = new hiloHerencia();
        hilo.setName("un hilo que hereda de Thread.");
        hilo.start();
    }
    
    public void run() {
        System.out.println("Soy " + getName() + " Hola mundo!!!");
    }
}
```

### 2️⃣ **Implementando interfaz Runnable**

**Cuándo usar Runnable:**
- Se necesita **lanzar hilos sueltos**
- Se quiere dotar de concurrencia a una clase que **ya es base de una jerarquía**
- No se puede heredar de Thread (Java **no tiene herencia múltiple**)

```java
public class hiloRunnable implements Runnable {
    
    public static void main(String[] args) {
        hiloRunnable run = new hiloRunnable();
        Thread hilo = new Thread(run);
        hilo.start();
    }
    
    @Override
    public void run() {
        System.out.println("Soy " + this.toString() + ". Hola mundo!!!");
    }
}
```

### 3️⃣ **Utilizar objetos de la clase ThreadPoolExecutor**

Esta forma avanzada permite gestionar un pool de hilos para optimizar recursos.

---

## 📊 **EJEMPLO USANDO HERENCIA DE LA CLASE THREAD**

### 🔷 **Clase UnHilo**

Contiene el código que ejecuta un hilo.

```java
public class UnHilo extends Thread {
    
    public UnHilo(String nombreHilo) {
        super(nombreHilo);
    }
    
    public void run() {
        System.out.println(getName());
    }
}
```

### 🔶 **Clase TestUnHilo**

Crea una instancia de UnHilo y la ejecuta.

```java
public class TestUnHilo {
    
    public static void main(String[] args) {
        UnHilo hiloUno = new UnHilo("HiloUno");
        hiloUno.start();
    }
}
```

### 📋 **Análisis del Código**

#### 🔧 **Constructor**
- Recibe un parámetro `String` con el nombre del hilo
- Llama al constructor de la clase base `Thread`

#### ⚙️ **Método run()**
- **Método más importante** del hilo
- Contiene el código con el trabajo que debe realizar el thread
- En este ejemplo: imprime por pantalla el nombre del hilo

#### 🚀 **Método start()**
- No está definido en la clase `UnHilo`, sino en la clase base `Thread`
- Realiza procesos transparentes para el programador
- **Invoca automáticamente** al método `run()` del hilo

#### 🏁 **Terminación**
Un hilo finaliza cuando culmina la ejecución de su método `run()` → **Estado MUERTO**

---

## 📊 **ESTADOS DE UN HILO**

### 🔄 **Diagrama de Estados**

```
    ┌─────────┐
    │  NUEVO  │
    └─────────┘
         │ start()
         ▼
    ┌──────────┐
    │PREPARADO │ ◄──────────────┐
    └──────────┘                │
         │                      │
         │ Planificador         │
         ▼                      │
    ┌────────────┐              │
    │ EJECUCIÓN  │──────────────┘
    └────────────┘    yield()
         │
         │ Evento
         ▼
    ┌───────────┐
    │ BLOQUEADO │
    └───────────┘
         │
         │ run() termina
         ▼
    ┌─────────┐
    │  MUERTO │
    └─────────┘
```

### 📝 **Descripción de Estados**

#### 🆕 **Estado NUEVO**
- Se ha creado un objeto hilo
- Todavía no se le ha asignado ninguna tarea
- Para ejecutarse, se debe llamar a `start()`
- Pasa al estado **PREPARADO**

#### ⏸️ **Estado PREPARADO**
- El hilo está preparado para ejecutarse
- El **PLANIFICADOR DE HILOS** decide:
  - Si puede ejecutarse
  - O debe esperar (por ejemplo, a que termine otro hilo)

#### ▶️ **Estado EN EJECUCIÓN**
- El hilo accede a tiempo de CPU y se ejecuta
- **Transiciones posibles:**
  - Si finaliza completamente → **MUERTO**
  - Si el planificador decide que cumplió su periodo → **PREPARADO**
  - Si espera un evento → **BLOQUEADO**

#### 🔒 **Estado BLOQUEADO**
- El hilo no puede ejecutarse
- Espera que ocurra algo específico
- Cuando ocurre el evento esperado → **PREPARADO**

#### ⚰️ **Estado MUERTO**
- El hilo ha finalizado su tarea
- Deja de existir
- No puede volver a ejecutarse

---

## 🛠️ **HERRAMIENTAS DE PLANIFICACIÓN DE HILOS**

Java proporciona un conjunto de métodos para controlar el cambio de estado de los hilos.

### 1️⃣ **Método yield()**

```java
Thread.yield();
```

**Funcionalidad:**
- Un hilo **cede voluntariamente** su tiempo de CPU
- Pasa del estado **EN EJECUCIÓN** → **PREPARADO**
- Permite que otros hilos se ejecuten

**Comportamiento:**
- Si **no hay otro hilo esperando**: el planificador vuelve a cambiar el hilo a **EN EJECUCIÓN**
- Si **hay otros hilos**: les da oportunidad de ejecutarse

### 2️⃣ **Método sleep()**

```java
Thread.sleep(long milisegundos);
```

**Funcionalidad:**
- El hilo pasa al estado **BLOQUEADO**
- Se mantiene bloqueado por el tiempo especificado
- Una vez cumplido el tiempo → **PREPARADO**

**Nota:** Es obligatorio controlar con `InterruptedException`

### 3️⃣ **Método join()**

```java
hilo.join();
```

**Funcionalidad:**
- Permite a un hilo **quedar a la espera** de que termine otro hilo
- Útil para mantener un **orden en la secuencia** de hilos
- Se puede arrancar una secuencia de hilos llamando a `join()` para que cada uno finalice en orden

**Nota:** Es obligatorio controlar con `InterruptedException`

---

## 📋 **EJEMPLO CON join()**

```java
public class TestDosHilos {
    
    public static void main(String[] args) {
        UnHilo hiloUno = new UnHilo("HiloUno");
        UnHilo hiloDos = new UnHilo("HiloDos");
        
        hiloUno.start();
        hiloDos.start();
        
        try {
            hiloUno.join();
            hiloDos.join();
        } catch (InterruptedException ie) {
            // Manejo de excepción
        }
        
        System.out.println("El programa ha finalizado");
    }
}
```

**Análisis:**
- Se crean dos hilos
- Ambos se inician con `start()`
- El método `main()` espera a que ambos terminen con `join()`
- Solo después de que ambos hilos finalicen, se imprime el mensaje final

---

## ⚠️ **REGIÓN CRÍTICA EN UN PROGRAMA**

### 📝 **Definición**

Una **región crítica** es una sección de código donde múltiples hilos acceden a **recursos compartidos**. Sin control adecuado, puede producir **resultados incorrectos e impredecibles**.

### 🔷 **Clase Contador (Región Crítica)**

```java
public class Contador {
    private int contador = 1;
    
    public void setContador(int nContador) {
        contador = nContador;
    }
    
    public int getContador() {
        return contador;
    }
}
```

### 🔶 **Clase HiloContador**

```java
public class HiloContador extends Thread {
    private Contador contador;
    
    public HiloContador(String nNombre, Contador nContador) {
        super(nNombre);
        contador = nContador;
    }
    
    public void run() {
        try {
            for (int j = 0; j < 10; j++) {
                int i = contador.getContador();
                sleep((int) (Math.random() * 10));
                contador.setContador(i + 1);
                System.out.println(getName() + " pone el contador a " + i);
            }
        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar el método sleep");
        }
    }
}
```

### 🔴 **Clase TestHiloContador**

```java
public class TestHiloContador {
    
    public static void main(String[] args) {
        Contador cont = new Contador();
        HiloContador hc1 = new HiloContador("HiloUno", cont);
        HiloContador hc2 = new HiloContador("HiloDos", cont);
        
        hc1.start();
        hc2.start();
        
        try {
            hc1.join();
            hc2.join();
        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar el método join");
        }
        
        System.out.println("El último valor que debe mostrarse es 10*2=20");
    }
}
```

### ⚠️ **Problema**

Al ejecutar este código:
- El **resultado NO es el esperado**
- **Varía cada vez** que se ejecuta el programa
- Esto se debe a que existe una **región crítica no controlada** (la variable `contador`)

---

## ✅ **SOLUCIÓN: synchronized**

### 🔒 **Modificación del método run()**

```java
public void run() {
    try {
        synchronized (contador) {  // Bloque sincronizado
            for (int j = 0; j < 10; j++) {
                int i = contador.getContador();
                sleep((int) (Math.random() * 10));
                contador.setContador(i + 1);
                System.out.println(getName() + " pone el contador a " + i);
            }
        }
    } catch (InterruptedException e) {
        System.out.println("Error al ejecutar el método sleep");
    }
}
```

### 🎯 **Funcionamiento de synchronized**

**Sintaxis:**
```java
synchronized(nombreDeVariableSeccionCritica) {
    // Código de la región crítica
}
```

**Efecto:**
- Solo **un hilo a la vez** puede ejecutar el código dentro del bloque
- Los demás hilos **esperan** hasta que el bloque esté libre
- Garantiza **acceso exclusivo** a la región crítica

---

## 🔄 **MULTIPROCESAMIENTO EN JAVA**

### 📝 **Definición**

El **multiprocesamiento** es la ejecución de varios procesos de manera **concurrente** o al mismo tiempo.

### ⚙️ **Gestión Automática**

**La máquina virtual de Java (JVM) gestiona el multiprocesamiento para:**
- Aprovechar los núcleos de uno o varios procesadores
- Distribuir la carga de trabajo

**Responsabilidad del programador:**
1. El programa se ejecute por "bloques"
2. Poner cada tarea en un hilo de ejecución

**La JVM se encarga de:**
- Gestionar qué se ejecuta en cada momento
- Similar a como el Sistema Operativo gestiona procesos
- Decidir qué se ejecuta o qué espera

---

## 💻 **EJEMPLO DE MULTIPROCESAMIENTO**

### 🔷 **Clase unaTareaEnUnHiloa**

```java
// Objeto que representa una tarea que se ejecuta en un hilo
class unaTareaEnUnHiloa extends Thread {
    
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print(i + ", ");
        }
    }
}
```

### 🔶 **Clase manejadorDeHilos**

```java
public class manejadorDeHilos {
    
    public static void main(String[] args) {
        unaTareaEnUnHiloa a, b;
        a = new unaTareaEnUnHiloa();
        b = new unaTareaEnUnHiloa();
        a.start();
        b.start();
    }
}
```

### 📊 **Resultado de la Ejecución**

**Observaciones:**
- **NO** se ejecuta primero la tarea `a` y luego `b`
- Los números están **desordenados**
- Las 2 tareas se han ejecutado **a la vez**
- Cada hilo escribe cuando puede
- **Cada ejecución produce resultados diferentes**

---

## 🚀 **EJEMPLO CON CARGA INTENSIVA**

### 🔷 **Clase unaTareaEnUnHilob (Versión intensiva)**

```java
class unaTareaEnUnHilob extends Thread {
    
    public void run() {
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 100000000; j++) {
                for (int k = 0; k < 100000000; k++) {
                    for (int l = 0; l < 100000000; l++) {
                        // Trabajo intensivo
                    }
                }
            }
        }
    }
}
```

### 📊 **Observaciones con el Administrador de Tareas**

**Con 2 hilos (a y b):**
- Procesador trabaja al **~50%**
- Se usan 2 de los 4 núcleos disponibles
- Núcleos 2 y 3 ejecutando las tareas

**Con 4 hilos (a, b, c, d):**
```java
public class manejadorDeHilosb {
    
    public static void main(String[] args) {
        unaTareaEnUnHilob a, b, c, d;
        a = new unaTareaEnUnHilob();
        b = new unaTareaEnUnHilob();
        c = new unaTareaEnUnHilob();
        d = new unaTareaEnUnHilob();
        a.start();
        b.start();
        c.start();
        d.start();
    }
}
```

- Procesador trabaja al **~100%**
- Se aprovecha completamente el procesador de 4 núcleos

### 📈 **Comparación**

**Sin hilos (ejecución secuencial):**
- Procesador al **~25%**
- Tardado mucho más tiempo
- Tareas ejecutadas una tras otra
- Números ordenados en pantalla

**Con hilos (ejecución concurrente):**
- Procesador al **100%** (con 4 hilos en 4 núcleos)
- Menor tiempo total
- Mejor aprovechamiento del hardware

---

## 🎯 **CONCLUSIONES SOBRE MULTIPROCESAMIENTO**

### ✅ **Ventajas**

1. **Mejor aprovechamiento del hardware**
   - Uso de múltiples núcleos
   - Mayor eficiencia del procesador

2. **Reducción de tiempos de ejecución**
   - Tareas paralelas en lugar de secuenciales
   - Mejora significativa en rendimiento

3. **Escalabilidad**
   - Más tareas = mejor uso de recursos
   - Adaptación automática al hardware disponible

### 💡 **Recomendaciones**

**Cuando usar hilos:**
- Muchas tareas independientes
- Operaciones que pueden ejecutarse en paralelo
- Necesidad de aprovechar procesadores multinúcleo

**Gestión eficiente:**
- Usar vectores, colas o listas de tareas
- Un hilo por tarea independiente
- Mejor rendimiento con más tareas programadas en hilos

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 📖 **Fuentes Principales**

1. **DNB – Ingeniería del Software** (2012)  
   *Empezando con Concurrencia en Java*  
   Disponible en: goo.gl/5M5yni  
   Accedido en Septiembre del 2018

2. **Multiprocesamiento en Java**  
   Disponible en: goo.gl/aGTEfT  
   Accedido en Septiembre del 2018

3. **García de Jalón J, Rodríguez J, Mingo I, Imaz A, Brazalez A, Larzabal A, Calleja J y García J** (2000)  
   *Aprenda Java como si estuviera en primero*

4. **García Marcos, Cristian** (2010)  
   *Concurrencia en Java*  
   Disponible en: https://goo.gl/rb1HDg  
   Accedido en Septiembre del 2018

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Fundamentos**

- **Hilo:** Parte de un proceso con variables propias que comparte memoria
- **Proceso:** Programa en ejecución (dinámico)
- **Programa:** Archivo .class (estático)

### ✅ **Implementación**

- **3 formas:** Herencia de Thread, Interfaz Runnable, ThreadPoolExecutor
- **Método run():** Contiene el código del hilo
- **Método start():** Inicia la ejecución del hilo

### ✅ **Estados**

- **Nuevo → Preparado → Ejecución → Bloqueado/Muerto**
- **Planificador:** Gestiona la transición entre estados

### ✅ **Sincronización**

- **Región crítica:** Código que accede a recursos compartidos
- **synchronized:** Garantiza acceso exclusivo
- **Métodos:** yield(), sleep(), join()

### ✅ **Multiprocesamiento**

- **JVM:** Gestiona la distribución en núcleos
- **Hilos:** Aprovechan procesadores multinúcleo
- **Rendimiento:** Mejor uso de recursos del sistema

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*  
*Prof. Ing. José Farfán*