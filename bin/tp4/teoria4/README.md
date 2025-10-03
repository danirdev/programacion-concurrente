# 📚 CLASE 4 - EXCEPCIONES, WAIT Y NOTIFY

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**📖 Tema:** Excepciones - Wait - Notify

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [⚠️ Excepciones con Try-Catch](#excepciones-con-try-catch)
2. [🔄 Concurrencia con wait() y notify()](#concurrencia-con-wait-y-notify)
3. [📊 Wait y Notify como Cola de Espera](#wait-y-notify-como-cola-de-espera)
4. [🏭 Modelo Productor/Consumidor](#modelo-productor-consumidor)
5. [🛑 Interrumpir un Hilo](#interrumpir-un-hilo)
6. [💡 Ejemplos Prácticos](#ejemplos-prácticos)
7. [📚 Bibliografía](#bibliografía)

---

## ⚠️ **EXCEPCIONES CON TRY-CATCH**

### 📝 **Introducción**

En programación **siempre se producen errores** que es necesario **gestionar y tratar correctamente**.

Java dispone de un mecanismo consistente en el uso de bloques **try/catch/finally**.

### 🎯 **Técnica de Manejo de Excepciones**

La técnica consiste en:
1. Colocar instrucciones que **podrían provocar problemas** dentro de un bloque **try**
2. Colocar a continuación **uno o más bloques catch**
3. Si se provoca un error de un determinado tipo, se salta al bloque **catch** capaz de gestionar ese tipo de error específico

**Importante:** El bloque **catch** contiene el código necesario para gestionar el tipo específico de error.

**SI NO HAY ERRORES** en el bloque try, **nunca se ejecutan los bloques catch**.

---

## 🔧 **SINTAXIS DE TRY-CATCH**

### 📋 **Estructura General**

```java
try {
    // Código que puede provocar errores
}
catch(Tipo1 var1) {
    // Gestión del error var1, de tipo Tipo1
}
// ...
catch(TipoN varN) {
    // Gestión del error varN, de tipo TipoN
}
finally {
    // Código de finally (opcional)
}
```

### 🔑 **Componentes**

#### 1️⃣ **Bloque try (OBLIGATORIO)**
- Contiene las instrucciones con posibles problemas
- Es obligatorio que exista

#### 2️⃣ **Bloques catch (uno o más)**
- Cada uno especializado en un tipo de error o excepción
- Se puede tener múltiples catch para diferentes tipos de errores

#### 3️⃣ **Bloque finally (OPCIONAL)**
- Contiene código que **se ejecuta siempre**
- Independiente de si se produjeron o no errores

### 🎯 **Funcionamiento de catch**

Cada **catch** se parece a una función en la cual:
- Solo se recibe **un objeto de un determinado tipo** (el tipo del error)
- Solo se llama al catch cuyo **argumento coincida en tipo** con el tipo del error generado

---

## 💻 **EJEMPLO 1: ArrayIndexOutOfBoundsException**

### ❌ **Código SIN Manejo de Excepciones**

```java
public class Try1 {
    public static void main(String arg[]) {
        int[] array = new int[20];
        array[-3] = 24;
    }
}
```

### 🔴 **Error Generado**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException 
at Try1.main(Try1.java:6)
```

**Indica que:**
- Se ha generado una excepción del tipo `java.lang.ArrayIndexOutOfBoundsException`
- En la función `Try1.main`
- Dentro del fichero `Try1.java`
- En la línea 6 del código

**Esta excepción se lanza cuando:**
- Se intenta acceder a una posición de un array que no existe

---

### ✅ **Código CON Manejo de Excepciones (Try2.java)**

```java
public class Try2 {
    public static void main(String arg[]) {
        int[] array = new int[20];
        
        try {
            array[-3] = 24;
        }
        catch(ArrayIndexOutOfBoundsException excepcion) {
            System.out.println("Error de índice en un array");
        }
    }
}
```

**Resultado:**
- El programa **no se detiene abruptamente**
- Se imprime el mensaje de error personalizado
- El programa continúa su ejecución

---

## 💻 **EJEMPLO 2: Múltiples Excepciones (Try3.java)**

### 📋 **Código con Múltiples catch**

```java
public class Try3 {
    public static void main(String arg[]) {
        int[] array = new int[20];
        
        try {
            int b = 0;
            int a = 23 / b;  // División por cero
        }
        catch(ArrayIndexOutOfBoundsException excepcion) {
            System.out.println("Error de índice en un array");
        }
        catch(ArithmeticException excepcion) {
            System.out.println("Error Aritmético");
        }
    }
}
```

### 🎯 **Análisis**

- Se provoca un error de tipo **división por cero**
- Se pone un **catch específico** para dicho error
- Java ejecuta el catch de `ArithmeticException`
- El otro catch no se ejecuta

---

## 🔄 **CONCURRENCIA EN JAVA CON WAIT() Y NOTIFY()**

### 📝 **Introducción**

A veces conviene que **un hilo se bloquee** a la espera de que ocurra algún evento, como:
- La llegada de un dato para tratar
- Que el usuario termine de escribir algo en una interfaz de usuario

### 🔑 **Métodos Fundamentales**

**Todos los objetos Java tienen:**

#### 1️⃣ **Método wait()**
- Deja **bloqueado** al hilo que lo llama
- El hilo entra en estado de espera

#### 2️⃣ **Método notify()**
- **Desbloquea** a los hilos bloqueados por `wait()`
- Despierta al primer hilo en la cola de espera

**Patrón de uso:** Modelo productor/consumidor

---

## 🔒 **BLOQUEAR UN HILO**

### ⚠️ **Consideraciones Importantes**

**Manejo de Excepciones:**
- Las llamadas a `wait()` **lanzan excepciones** que hay que capturar
- Todas las llamadas deben estar en un **bloque try-catch**
- (Por simplicidad, en algunos ejemplos esto no se hará)

### 🔐 **Requisito: synchronized**

Para que un hilo se bloquee:
1. Debe llamar al método `wait()` de cualquier objeto
2. Es **necesario** que dicho hilo haya marcado ese objeto como **ocupado** con `synchronized`
3. Si no se hace así, salta una excepción: **"el hilo no es propietario del monitor"**

---

## 💻 **EJEMPLO: Leer Datos de una Lista**

### 📋 **Código de Consumidor**

```java
synchronized(lista) {
    if (lista.size() == 0)
        lista.wait();
    
    dato = lista.get(0);
    lista.remove(0);
}
```

### 🔍 **Análisis del Código**

#### 🔐 **synchronized(lista)**
- Se usa para **"apropiarse"** del objeto lista
- Marca el objeto como ocupado

#### ⏸️ **Si no hay datos**
- Se ejecuta `lista.wait()`
- El objeto lista se marca como **"desocupado"**
- Otros hilos pueden usarlo

#### ⏯️ **Cuando se despierta**
- Sale del `wait()`
- Se marca como **"ocupado"** nuevamente

#### 🔔 **Desbloqueo**
- El hilo se desbloquea cuando alguien llama a `lista.notify()`
- Si el hilo que mete datos llama luego a `lista.notify()`
- Cuando sale del `wait()` se tienen datos disponibles
- Solo hay que leerlos y borrarlos para no volver a tratarlos

---

## 📤 **EJEMPLO: Agregar Datos a una Lista**

### 📋 **Código de Productor**

```java
synchronized(lista) {
    lista.add(dato);
    lista.notify();
}
```

### 🔍 **Análisis del Código**

#### 📥 **Agregar dato**
- El hilo mete datos en la lista

#### 🔔 **Llamar a notify()**
- Debe llamar a `lista.notify()`
- Es necesario **apropiarse del objeto** lista con `synchronized`

#### ⏯️ **Resultado**
- El hilo que estaba bloqueado en `wait()` despierta
- Sale del `wait()`
- Seguirá su código leyendo el primer dato de la lista

---

## 📊 **WAIT() Y NOTIFY() COMO COLA DE ESPERA**

### 🔄 **Funcionamiento General**

`wait()` y `notify()` funcionan como una **lista de espera**:

#### 📋 **Cola de Hilos**
- Si varios hilos llaman a `wait()` se bloquean en una lista de espera
- El **primero** que llamó a `wait()` es el **primero** de la lista
- El **último** es el **último**

#### 🔔 **Despertando Hilos**
- Cada llamada a `notify()` despierta al **primer hilo** en la lista de espera
- **No despierta al resto**, que siguen dormidos
- Se necesita hacer **tantos notify() como hilos** hayan hecho `wait()` para despertarlos a todos

#### 📈 **notify() Acumulativo**
- Si se hacen varios `notify()` **antes** de que haya hilos en espera
- Quedan **marcados** todos esos `notify()`
- Los siguientes hilos que hagan `wait()` **no quedan bloqueados**

---

## 🔢 **WAIT() Y NOTIFY() COMO CONTADOR**

### 📊 **Mecanismo de Contador**

`wait()` y `notify()` funcionan como un **contador**:

#### ⏸️ **wait()**
- Mira el contador
- Si es **cero o menos** → se bloquea
- Cuando se desbloquea → **decrementa** el contador

#### 🔔 **notify()**
- **Incrementa** el contador
- Si se hace **0 o positivo** → despierta al primer hilo de la cola

---

## 🍬 **ANALOGÍA: Mesa con Caramelos**

### 📝 **Comparación Visual**

En una mesa hay personas, unos ponen caramelos y otros los recogen:

#### 👥 **Personas = Hilos**

#### 🍬 **Los que recogen caramelos (hacen wait())**
- Se ponen en una **cola** delante de la mesa
- Recogen **1 caramelo** y se van
- Si **no hay caramelos** → esperan que los haya y forman una cola

#### 📥 **Otras personas ponen caramelos (hacen notify())**
- Ponen **1 caramelo** en la mesa

#### 🔢 **El número de caramelos en la mesa**
- Es el **contador** que se mencionó

---

## 🏭 **MODELO PRODUCTOR/CONSUMIDOR**

### 📝 **Buenas Prácticas de Orientación a Objetos**

Es buena costumbre **"ocultar" la sincronización** a los hilos, de forma que:
- No se dependa de que el programador se acuerde de implementar su hilo correctamente
- No necesite recordar llamar a `synchronized`, `wait()` y `notify()`

### 💡 **Solución Recomendada**

Poner la lista de datos dentro de una clase y crear **2 métodos synchronized**:
- Uno para **añadir datos**
- Otro para **recoger datos**
- Con el `wait()` y el `notify()` dentro

---

## 💻 **IMPLEMENTACIÓN: MiListaSincronizada**

### 📋 **Código Completo**

```java
public class MiListaSincronizada {
    private LinkedList lista = new LinkedList();
    
    // Método para agregar datos
    public synchronized void addDato(Object dato) {
        lista.add(dato);
        notify();
    }
    
    // Método para obtener datos
    public synchronized Object getDato() {
        if (lista.size() == 0)
            wait();  // Se bloquea hasta que haya algún dato disponible
        
        Object dato = lista.get(0);
        lista.remove(0);
        return dato;
    }
}
```

### 🎯 **Uso Simplificado**

**El hilo que guarda datos solo debe hacer:**
```java
listaSincronizada.addDato(dato);
```

**El hilo que lee datos solo debe hacer:**
```java
Object dato = listaSincronizada.getDato();
```

**Ventaja:** La sincronización está **encapsulada** dentro de la clase

---

## 🛑 **INTERRUMPIR UN HILO**

### 📝 **Interrupción sin notify()**

Un hilo puede salir del `wait()` **sin necesidad de un notify()**. Esto es por una **interrupción**.

### ⚡ **Método interrupt()**

En Java se realiza con el método `interrupt()` del hilo:

```java
hiloLector.interrupt();
```

**Efecto:**
- El `hiloLector` saldrá del `wait()`
- Se encuentra con que **no hay datos** en la lista
- Sabrá que **alguien le ha interrumpido**
- Hará lo que tenga que hacer en ese caso

---

## 💻 **EJEMPLO: Lector de Socket**

### 📝 **Escenario**

Un hilo `lectorSocket` está pendiente de un socket (conexión con otro programa en otra computadora a través de red):

#### 📥 **Hilo lectorSocket**
1. Lee datos que llegan del otro programa
2. Los mete en `listaSincronizada`

#### 📤 **Hilo lectorDatos**
- Lee esos datos de la `listaSincronizada`
- Los trata

### ❓ **Problema**

¿Qué ocurre si **el socket se cierra**?

### ✅ **Solución**

1. El programa cierra la conexión (socket) con el otro programa
2. Una vez cerrada la conexión, el hilo `lectorSocket` puede **interrumpir** al hilo `lectorDatos`
3. Al ver que ha salido del `wait()` y que **no hay datos disponibles**
4. Puede suponer que **se ha cerrado la conexión** y terminar

---

## 💻 **CÓDIGO: Interrumpir un Hilo**

### 📋 **Hilo lectorDatos**

```java
while (true) {
    if (listaSincronizada.size() == 0)
        wait();
    
    if (listaSincronizada.size() > 0) {  // Se debe comprobar que hay datos
        Object dato = listaSincronizada.get(0);  // Si hay datos, se tratan
        listaSincronizada.remove(0);
        // tratar el dato
    }
    else {  // Si no hay datos es porque se cerró la conexión
        return;  // Se sale del bucle
    }
}
```

### 📋 **Cuando el hilo lectorSocket cierre la conexión**

```java
socket.close();
lectorDatos.interrupt();
```

---

## 💡 **EJEMPLO 1: SALUDO JEFE-EMPLEADOS**

### 🎯 **Descripción del Problema**

Sistema donde:
- **Empleados** esperan a que llegue el **jefe** para saludar
- El **jefe** saluda y despierta a todos los empleados
- Los **empleados** saludan después del jefe

---

### 📋 **Clase Main**

```java
public class Main {
    public static void main(String[] args) {
        Saludo s = new Saludo(); // Objeto en común, se encarga del wait y notify
        
        Personal Empleado1 = new Personal("Pepe", s, false);
        Personal Empleado2 = new Personal("José", s, false);
        Personal Empleado3 = new Personal("Pedro", s, false);
        Personal Jefe1 = new Personal("JEFE", s, true);
        
        /* Instancio los hilos y paso como parámetros:
         * - Nombre del Hilo
         * - Objeto en común (Saludo)
         * - Booleano para verificar si es jefe o empleado
         */
        
        Empleado1.start();  // Lanzo los hilos
        Empleado2.start();
        Empleado3.start();
        Jefe1.start();
    }
}
```

---

### 📋 **Clase Personal**

```java
import java.util.logging.Level;
import java.util.logging.Logger;

public class Personal extends Thread {
    String nombre;
    Saludo saludo;
    boolean esJefe;
    
    public Personal(String nombre, Saludo salu, boolean esJefe) {
        this.nombre = nombre;
        this.saludo = salu;
        this.esJefe = esJefe;
    }
    
    public void run() {
        System.out.println(nombre + " llegó.");
        
        try {
            Thread.sleep(1000);
            
            if (esJefe) {  // Verifico si el personal es jefe o no
                saludo.saludoJefe(nombre);
            } else {
                saludo.saludoEmpleado(nombre);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
```

---

### 📋 **Clase Saludo**

```java
import java.util.logging.Level;
import java.util.logging.Logger;

public class Saludo {
    
    public Saludo() { }
    
    /* Si no es jefe, el empleado va a quedar esperando a que llegue el jefe.
     * Se hace wait del hilo que está corriendo y se bloquea,
     * hasta que se le avise que ya puede saludar
     */
    public synchronized void saludoEmpleado(String nombre) {
        try {
            wait();
            System.out.println("\n" + nombre.toUpperCase() + 
                             "-: Buenos días jefe.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Saludo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Si es jefe, saluda y luego avisa a los empleados para que saluden.
     * El notifyAll despierta a todos los hilos que estén bloqueados
     */
    public synchronized void saludoJefe(String nombre) {
        System.out.println("\n****** " + nombre + 
                         "-: Buenos días empleados. ******");
        notifyAll();
    }
}
```

---

### ⚠️ **Problema Detectado**

**Si el jefe llega antes que un empleado:**
- Ese empleado **no despertará** de su `wait()`
- Ya que el `notifyAll()` ya ha sido llamado por el jefe

### 💡 **Ejercicio para el Alumno**

Se pide plantear una **solución** usando:
- Un **booleano** que indique si el jefe ya ha llegado o no
- En función de él, saludar al jefe o disculpar el retraso

---

## 💡 **EJEMPLO 2: BOLSA Y PRODUCTOS**

### 🎯 **Descripción del Problema**

Tenemos:
- Una **bolsa** que puede contener hasta 5 productos
- Un hilo que **llena la bolsa** con productos
- Otro hilo que **envía la bolsa** cuando está llena

---

### 📋 **Clase Bolsa**

```java
package com.arquitecturajava;
import java.util.ArrayList;

public class Bolsa {
    private ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    
    public void addProducto(Producto producto) {
        if (!estaLlena())
            listaProductos.add(producto);
    }
    
    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }
    
    public int getSize() {
        return listaProductos.size();
    }
    
    public boolean estaLlena() {
        return listaProductos.size() >= 5;
    }
}
```

---

### 📋 **Clase Producto**

```java
package com.arquitecturajava;

public class Producto {
    private String nombre;
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

---

### 📋 **Clase HiloEnvio**

Este Thread **cuando la bolsa esté llena la envía**.

```java
package com.arquitecturajava;

public class HiloEnvio extends Thread {
    private Bolsa bolsa;
    
    public HiloEnvio(Bolsa bolsa) {
        super();
        this.bolsa = bolsa;
    }
    
    @Override
    public void run() {
        if (bolsa.estaLlena() != true) {
            try {
                synchronized (bolsa) {
                    bolsa.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Enviando la bolsa con " + 
                         bolsa.getSize() + " elementos");
    }
    
    public Bolsa getBolsa() { 
        return bolsa; 
    }
    
    public void setBolsa(Bolsa bolsa) { 
        this.bolsa = bolsa; 
    }
}
```

**Funcionalidad:**
- Se encarga de sacar por pantalla: "Enviando la bolsa con X elementos"
- Recibe la Bolsa como parámetro y chequea que está llena
- Usa un bloque `synchronized` que coordina el trabajo entre los dos Threads

---

### 📋 **Clase Principal**

Para rellenar la bolsa usamos el Thread del programa principal que **cada segundo añade un nuevo elemento** a la lista.

```java
package com.arquitecturajava;

public class Principal {
    public static void main(String[] args) {
        Bolsa bolsa = new Bolsa();
        HiloEnvio hilo = new HiloEnvio(bolsa);
        hilo.start();
        
        for (int i = 0; i <= 10; i++) {
            Producto p = new Producto();
            
            try {
                synchronized (bolsa) {
                    Thread.sleep(1000);
                    
                    if (bolsa.estaLlena()) {
                        bolsa.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            bolsa.addProducto(p);
            System.out.println(bolsa.getSize());
        }
    }
}
```

**Sincronización:**
- Ambos Threads deben estar **sincronizados**
- Hasta que la bolsa no esté llena no se puede enviar
- Para sincronizar usamos `wait` y `notify`
- `HiloEnvio` se pone a **esperar (wait)** hasta que la Bolsa esté llena antes de enviarla

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 📖 **Libros**

**García de Jalón J, Rodríguez J, Mingo I, Imaz A, Brazalez A, Larzabal A, Calleja J y García J** (2000)  
*Aprenda Java como si estuviera en primero*

### 🌐 **Páginas Web Consultadas**

Accedidas en Septiembre de 2023:

1. **Wait y Notify en Java**  
   http://www.chuidiang.com/java/hilos/wait_y_notify.php

2. **Excepciones en Java**  
   http://www.mundojava.net/excepciones.html?Pg=java_inicial_4_6.html

3. **Ejemplo de Señalización Wait y Notify**  
   http://labojava.blogspot.com.ar/2012/10/ejemplo-de-senalizacion-wait-y-notify.html

4. **Java Wait Notify y Threads**  
   https://www.arquitecturajava.com/java-wait-notify-y-threads/

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Excepciones**

- **try:** Contiene código que puede generar errores
- **catch:** Maneja tipos específicos de excepciones
- **finally:** Se ejecuta siempre, haya o no errores
- **Múltiples catch:** Permite manejar diferentes tipos de errores

### ✅ **wait() y notify()**

- **wait():** Bloquea el hilo hasta que sea notificado
- **notify():** Despierta a un hilo bloqueado
- **notifyAll():** Despierta a todos los hilos bloqueados
- **synchronized:** Requisito obligatorio para usar wait/notify

### ✅ **Sincronización**

- **synchronized:** Marca un objeto como ocupado
- **Cola de espera:** Los hilos esperan en orden FIFO
- **Contador:** Mecanismo interno de wait/notify
- **Encapsulación:** Ocultar sincronización en clases

### ✅ **Patrones**

- **Productor/Consumidor:** Patrón clásico con wait/notify
- **Interrupción:** Método interrupt() para despertar hilos
- **MiListaSincronizada:** Encapsula la lógica de sincronización

---

## 📊 **TABLA COMPARATIVA: MÉTODOS DE SINCRONIZACIÓN**

| Método | Propósito | Requiere synchronized | Efecto |
|--------|-----------|----------------------|--------|
| **wait()** | Bloquear hilo | ✅ Sí | Hilo en espera |
| **notify()** | Despertar 1 hilo | ✅ Sí | Despierta al primero |
| **notifyAll()** | Despertar todos | ✅ Sí | Despierta a todos |
| **interrupt()** | Interrumpir hilo | ❌ No | Sale de wait() sin notify |
| **sleep()** | Pausa temporal | ❌ No | Bloqueo por tiempo |

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*