Perfecto, aquí está el documento convertido a Markdown limpio y organizado:

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
8. [🔄 Multiprocesamiento](#multiprocesamiento)
9. [📚 Bibliografía](#bibliografía)

---

## 🎯 **RAZONES PARA LA CONCURRENCIA EN JAVA USANDO HILOS**

### 📌 **Contexto Tradicional**

Incluso en un **esquema tradicional** con:
- 1 computadora
- 1 único procesador
- 1 sola instrucción

Pueden haber **varios procesos concurrentes**.

### 🔄 **Simulación de Multitarea**

**Técnicas para simular multiejecución:**
- Los **Sistemas Operativos multitarea** hacen que los programas parezcan ejecutarse simultáneamente
- Aunque esto no es físicamente posible, **simulan la multitarea**

### 💻 **Procesadores Multinúcleo**

Existen procesadores con múltiples núcleos:
- 2, 4, 6, 8, 10, 12, ..., 64 núcleos (XEON)

**Problema:** Estos núcleos pueden **no ser suficientes** para la cantidad de programas que desean hacer uso de los mismos.

**Solución de Software:** Un programa requiera la ejecución de varias tareas → **HILOS O THREADS**

### ⚠️ **Desafío**

La multitarea y multihilos implica una serie de problemas como el acceso a ciertas instrucciones o **"región crítica"**.

---

## ⚙️ **PROGRAMAS Y PROCESOS**

### 📋 **Definiciones**

#### 🔷 **PROGRAMA en JAVA**
- Es un archivo `.class`
- Es **ESTÁTICO**
- **No requiere:**
  - Espacio de memoria
  - Tiempo de CPU

#### 🔶 **PROCESO**
- Es un **programa en ejecución**
- Es el archivo `.class` después de haber pulsado el botón "Ejecutar" de Eclipse
- Es **DINÁMICO**
- **Requiere:**
  - Tiempo de CPU
  - Almacenar su código en memoria

### 📊 **Comparación**

| Aspecto | Programa | Proceso |
|---------|----------|---------|
| **Estado** | Estático | Dinámico |
| **Archivo** | .class | .class ejecutándose |
| **Memoria** | No requiere | Requiere espacio |
| **CPU** | No requiere | Requiere tiempo |
| **Actividad** | Inactivo | Activo |

---

## 🧵 **HILOS (THREADS)**

### 📝 **Definición**

Un **hilo** es:
- **1 parte de 1 proceso**
- Tiene **variables locales propias**
- **Comparte la memoria** con el resto de hilos del mismo proceso
- Permite a una aplicación realizar **varias tareas a la vez** (concurrentemente)

### 🎯 **Características**

#### ✅ **Ventajas**
- **Mayor rapidez** de ejecución comparado con multiprocesos
- Los multihilos se ejecutan en lugar de multiprocesos por la **rapidez de ejecución**

#### ⚠️ **Complejidad**
La programación de aplicaciones puede ser compleja porque:
- Se debe controlar que el trabajo de un hilo **no interfiera** con otro hilo en el mismo proceso
- Es necesario que los hilos se **coordinen entre ellos**

---

## 🔧 **FORMAS DE IMPLEMENTAR UN HILO EN JAVA**

En el lenguaje Java hay **3 formas** de implementar la concurrencia:

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

---

### 2️⃣ **Implementando interfaz Runnable**

**Es conveniente implementar la interfaz Runnable cuando:**
- Se necesita **lanzar hilos sueltos**
- Se quiere dotar de concurrencia a una clase que **ya es base de una jerarquía** y que no podría heredar de Thread (en Java **no existe la herencia múltiple**)

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

---

### 3️⃣ **Utilizar objetos de la clase ThreadPoolExecutor**

Esta forma avanzada permite gestionar un pool de hilos para optimizar recursos (se verá en detalle en clases posteriores).

---

## 💻 **EJEMPLO USANDO HERENCIA DE LA CLASE THREAD**

### 📋 **Clase UnHilo**

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

### 🔍 **Componentes de la Clase**

#### 🔧 **Constructor**
- Recibe un parámetro de entrada tipo `String` (contiene el nombre del hilo)
- Llama al constructor de la clase base enviando el parámetro de entrada con el nombre

#### ⚙️ **Método run()**
- **Método más importante** del hilo
- Contiene el código con el trabajo que debe realizar el thread
- En este ejemplo: únicamente imprime por pantalla el nombre del hilo

---

### 📋 **Clase TestUnHilo**

Crea una instancia de la clase UnHilo y la ejecuta.

```java
public class TestUnHilo {
    
    public static void main(String[] args) {
        UnHilo hiloUno = new UnHilo("HiloUno");
        hiloUno.start();
    }
}
```

---

## 🔍 **ANÁLISIS DEL EJEMPLO**

### 🚀 **Proceso de Ejecución**

Para que el hilo se ejecute:
1. Se incluye una clase `TestUnHilo` que tiene el método `main()`
2. Se crea una instancia del objeto `UnHilo`
3. Se llama a su método `start()`

### 🔑 **Método start()**

- **No está definido** en la clase `UnHilo`, sino en la clase base `Thread`
- Al utilizar este método, el objeto desde el que se lo llama se pone en **ejecución**
- Hace una serie de procesos (**transparentes para el programador**)
- **Invoca automáticamente** al método `run()` del hilo

### 🏁 **Terminación**

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
         │ Planificador         │ yield()
         ▼                      │
    ┌────────────┐              │
    │ EJECUCIÓN  │──────────────┘
    └────────────┘
         │
         │ Evento/sleep()
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

---

### 📝 **Descripción de Estados**

#### 🆕 **Estado NUEVO**
- Se ha creado un objeto hilo
- Todavía **no se le ha asignado ninguna tarea**
- Para ejecutarse, se debe llamar a `start()`
- El hilo pasará al estado **PREPARADO**

#### ⏸️ **Estado PREPARADO**
- El hilo está **preparado para ejecutarse**
- El **PLANIFICADOR DE HILOS** decide:
  - Si puede ejecutarse
  - O debe esperar (por ejemplo, a que acabe la ejecución de otro hilo)

#### ▶️ **Estado EN EJECUCIÓN**
- Una vez que el hilo puede acceder a tiempo de CPU, **se ejecuta**
- **Transiciones posibles:**
  - Si el hilo **finaliza completamente** → estado **MUERTO**
  - Si el planificador decide que cumplió su periodo y el hilo **no ha finalizado** → estado **PREPARADO**
  - Espera a que el planificador vuelva a darle permiso para ejecutarse

#### 🔒 **Estado BLOQUEADO**
- El hilo **no puede ejecutarse** porque espera que ocurra algo
- En cuanto ocurra lo que lo está dejando bloqueado → estado **PREPARADO**

#### ⚰️ **Estado MUERTO**
- El hilo ha **finalizado su tarea** y deja de existir

---

## 🛠️ **HERRAMIENTAS DE PLANIFICACIÓN DE HILOS**

Java proporciona un conjunto de métodos que permiten controlar el cambio de un hilo de un estado a otro.

### 1️⃣ **Método yield()**

```java
Thread.yield();
```

**Funcionalidad:**
- Un hilo puede **ceder tiempo de CPU** asignado para que otros hilos se ejecuten
- Con la llamada a este método, el hilo pasa del estado **EN EJECUCIÓN** → **PREPARADO**

**Comportamiento especial:**
- Si **no hay otro hilo esperando CPU**, el planificador volverá a cambiar el estado del hilo de **PREPARADO** a **EN EJECUCIÓN**
- Para que vuelva a ocupar tiempo de CPU

---

### 2️⃣ **Método sleep()**

```java
Thread.sleep(long parametroTiempo);
```

**Funcionalidad:**
- El hilo pasa al estado **BLOQUEADO**
- Por tantos **milisegundos** como se le indiquen en el parámetro de entrada
- Una vez cumplido ese tiempo → se pone en estado **PREPARADO**

**Nota:** Es obligatorio controlar con `InterruptedException`

---

### 3️⃣ **Método join()**

```java
hilo.join();
```

**Funcionalidad:**
- Permite a un hilo **quedar a la espera** de que termine un segundo hilo
- Suele utilizarse para mantener un **orden en la secuencia** de los hilos

**Uso típico:**
- Se puede arrancar una secuencia de hilos llamando a `join()` para que cada uno finalice en el orden que se ha marcado
- Es obligatorio controlar con `InterruptedException`

---

## 💻 **EJEMPLO CON join()**

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
- El método `main()` espera a que ambos hilos terminen antes de imprimir "El programa ha finalizado"

---

## ⚠️ **REGIÓN CRÍTICA EN UN PROGRAMA**

### 📋 **Clase Contador (Región Crítica)**

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

**Características:**
- Define la **región crítica**
- Tiene un atributo de tipo entero inicializado a 1
- Su correspondiente setter y getter

---

### 📋 **Clase HiloContador**

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

**Características:**
- Es el hilo que **ejecuta la región crítica**
- Tiene un bucle de 10 iteraciones
- En cada iteración se incrementa en 1 el valor de la variable `contador`
- Finalmente, cada hilo habrá aumentado en 10 unidades el valor inicial del contador

**Nota:** Se introdujo un tiempo de espera con `sleep()` entre `getContador()` y `setContador()` para que la CPU no realice de una sola vez todo un hilo y después otro.

---

### 📋 **Clase TestHiloContador**

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

**Funcionalidad:**
- Contiene el método `main()` que crea:
  - Un objeto `Contador`
  - 2 hilos `HiloContador`
- Permite ver el funcionamiento del programa con 2 hilos ejecutándose simultáneamente

---

## ❌ **PROBLEMA: Región Crítica Sin Control**

Al ejecutar el programa:
- El **resultado NO es el esperado**
- **Varía cada vez** que se ejecuta el programa
- Esto se debe a que existe una **región crítica** (la variable `contador`) que **no se controla**

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

### 🎯 **Funcionamiento**

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

Es la ejecución de **varios procesos de manera concurrente** o al mismo tiempo.

### ⚙️ **Gestión Automática por la JVM**

En JAVA la máquina virtual de Java gestiona el multiprocesamiento para **aprovechar los núcleos** de uno o varios procesadores.

**El programador solo debe preocuparse porque:**
- El programa se ejecute por **"bloques"**
- Poner cada tarea en **un hilo de ejecución**

### 🔄 **Gestión Similar al Sistema Operativo**

La máquina virtual se encarga de gestionar todo, similar a lo que ocurre con los Sistemas Operativos:
- Se tienen muchos procesos que se ejecutan desde que inicia la computadora
- El SO se encarga de gestionar qué se ejecuta en cada momento

De igual manera en Java:
- Se puede gestionar qué se ejecuta o qué se queda esperando
- Se le puede decir a todas las tareas que se ejecuten a la vez
- Sin esperar a otros procesos

---

## 💻 **EJEMPLO DE MULTIPROCESAMIENTO**

### 📋 **Programa Principal**

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

### 📋 **Clase de Tarea**

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

**Análisis:**
- La clase `manejadorDeHilos` es un objeto sencillo que crea 2 variables `a` y `b`
- Ambas del mismo tipo
- La clase `unaTareaEnUnHilo` hereda de la clase `Thread`

---

### 🔍 **Funcionamiento**

**Proceso:**
1. Se deben hacer las tareas en una clase que herede de `Thread`
2. Se ejecuten en un hilo
3. Programar el método `run()`, que es el que se ejecuta cuando el hilo arranca

**Método start():**
- Las variables del tipo `unaTareaEnUnHilo` tienen un método propio de la clase `Thread` que es `start()`
- Con el cual se le dice que comience su ejecución
- Lo que hace es simplemente ejecutar la función `run()`

---

### 📊 **Resultado de la Ejecución**

Al ejecutarse se advierte que:
- **No** se ha ejecutado la tarea `a` y luego la `b`
- Están **desordenados los números**
- Las **2 tareas se han ejecutado a la vez**
- Cuando han podido han ido escribiendo el número por el que iban contando
- Si se ejecuta varias veces el programa se verá que **da resultados distintos**

---

## 🚀 **EJEMPLO CON CARGA INTENSIVA**

### 📋 **Clase con Bucles Anidados**

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

**Objetivo:**
- Ahora va a **tardar mucho** cada hilo
- Esta vez se va a **notar** si se han usado los hilos o no en el tiempo total que va a tardar
- Aunque tal vez tarde demasiado y haya que pararlo antes

---

### 📊 **Observación con Administrador de Tareas**

Se puede observar la ejecución con el **administrador de tareas**:

**Con 2 hilos (a y b):**
- Ver los núcleos desde donde se corrió
- Si son 4 núcleos, el segundo y tercero están ejecutando las tareas `a` y `b`
- El procesador trabaja al **~50%** aproximadamente

**Con 4 hilos (a, b, c, d):**
- Si se pusieran 2 tareas más con hilos subiría al **100%**
- Se aprovecharía aún más el procesador

---

## 💻 **EJEMPLO: Aprovechamiento al 100%**

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

### 📊 **Comparación**

**Sin hilos (ejecución secuencial):**
- El procesador habría trabajado al **~25%** aproximadamente
- Tardado mucho más en terminar
- Se hubiera ejecutado primero una, luego la otra
- Los números hubieran salido todos **ordenados** por la pantalla

**Con hilos (ejecución concurrente):**
- Procesador al **100%** (con 4 hilos en 4 núcleos)
- Menor tiempo total
- Números **desordenados** (ejecución paralela)

---

## 🎯 **RECOMENDACIONES**

### ❓ **¿Qué pasa con muchas tareas?**

Cuando se tienen **muchas tareas**:
- Se debe hacer un **vector de tareas**
- Una **cola de tareas**
- O una **lista**

**Regla general:**
> Mientras más tareas existan, es mejor que estén programadas cada una en un hilo.

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 📖 **Fuentes Principales**

**DNB – Ingeniería del Software** (2012)  
*Empezando con Concurrencia en Java*  
Disponible en: goo.gl/5M5yni  
Accedido en Septiembre del 2018

**Multiprocesamiento en Java**  
Disponible en: goo.gl/aGTEfT  
Accedido en Septiembre del 2018

**García de Jalón J, Rodríguez J, Mingo I, Imaz A, Brazalez A, Larzabal A, Calleja J y García J** (2000)  
*Aprenda Java como si estuviera en primero*

**García Marcos, Cristian** (2010)  
*Concurrencia en Java*  
Disponible en: https://goo.gl/rb1HDg  
Accedido en Septiembre del 2018

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### Fundamentos

- **Hilo:** Parte de un proceso con variables propias que comparte memoria
- **Proceso:** Programa en ejecución (dinámico)
- **Programa:** Archivo .class (estático)

### Implementación

- **3 formas:** Herencia de Thread, Interfaz Runnable, ThreadPoolExecutor
- **Método run():** Contiene el código del hilo
- **Método start():** Inicia la ejecución del hilo

### Estados

- **Nuevo → Preparado → Ejecución → Bloqueado/Muerto**
- **Planificador:** Gestiona la transición entre estados

### Sincronización

- **Región crítica:** Código que accede a recursos compartidos
- **synchronized:** Garantiza acceso exclusivo
- **Métodos:** yield(), sleep(), join()

### Multiprocesamiento

- **JVM:** Gestiona la distribución en núcleos
- **Hilos:** Aprovechan procesadores multinúcleo
- **Rendimiento:** Mejor uso de recursos del sistema

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*  
*Prof. Ing. José Farfán*