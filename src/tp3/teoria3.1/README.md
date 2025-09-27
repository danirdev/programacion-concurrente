# 💻 CLASE 3 - JAVA Y ECLIPSE IDE

## 📊 ÍNDICE TIOBE - FEBRERO 2024

**Ranking de Popularidad de Lenguajes de Programación**

```
┌─────────────────────────────────────────────────────────────┐
│                    ÍNDICE TIOBE - FEB 2024                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  🥇 1. Python, con un rating de 15.33%                    │
│  🥈 2. C, con 14.06%                                      │
│  🥉 3. Java, con 12.13%                                     │
│  4️⃣ 4. C++, con 8.01%                                       │
│  5️⃣ 5. C#, con 5.37%                                        │
│  6️⃣ 6. Visual Basic, con 5.23%                             │
│  7️⃣ 7. JavaScript, con 1.83%                               │
│  8️⃣ 8. PHP, con 1.79%                                       │
│  9️⃣ 9. Assembly language, con 1.60%                        │
│  🔟 10. SQL, con 1.55                                       │
│  11. Go, con 1.23%                                           │
│  12. Swift, con 1.16%                                        │
│                                                             │
│  ┌─────────────────────────────────────────────────┐  │
│  │                   JAVA                            │  │
│  │                                                 │  │
│  │      ☕                    🌍                   │  │
│  │   ┌───────┐              ┌─────────────┐   │  │
│  │   │       │              │ Eclipse IDE │   │  │
│  │   │ JAVA  │              │             │   │  │
│  │   │       │              │             │   │  │
│  │   └───────┘              └─────────────┘   │  │
│  └─────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## 📝 INFORMACIÓN DEL CURSO

**MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE**  
**2024 - Profesor: Ing. José Farfán**  
**FAC.DE INGENIERÍA - UNJu**  
**JAVA**

---

## ☕ CARACTERÍSTICAS PRINCIPALES DE JAVA

### 🔄 Proceso de Compilación y Ejecución

```
┌─────────────────────────────────────────────────────────────┐
│                PROCESO DE COMPILACIÓN JAVA                │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────┐                           │
│  │ Código Java (archivos .java) │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────────────────┐                           │
│  │    JAVAC (compilador)     │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────────────────┐                           │
│  │ Byte Code (archivos .class) │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────┐   ┌─────────────┐               │
│  │     JVM     │   │     JVM     │               │
│  │   Windows   │   │    Linux    │               │
│  └─────────────┘   └─────────────┘               │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Características Fundamentales

• **🌍 Independiente del HW**  
  Funciona en cualquier plataforma con JVM

• **📦 Orientado a Objetos y de propósito general**  
  Paradigma OOP completo y versátil

• **📝 Sintaxis parecida a C++**  
  Fácil transición para programadores de C/C++

• **💪 Robusto**  
  Manejo de errores y excepciones integrado

• **🔗 Solo admite herencia simple**  
  Evita la complejidad de herencia múltiple

### 🔒 Seguridad

#### **Desde el punto de vista del programador:**
- **Comprobación estricta de tipos de datos**
- **Gestión de excepciones**
- **No existencia de punteros**
- **Manejo de errores sintácticos y lógicos**

#### **Desde el punto de vista del usuario de aplicaciones:**
- **Los programas se ejecutan sobre una máquina virtual (JVM)**
- **Espacio de nombre y Programación Concurrente Nativa**

### 📊 Tipos de datos estandarizados

### 📝 Estructuras Condicionales

- **Sentencias if-else**
- **Sentencias switch**
- **Operadores condicionales**

### 📝 Manejo de Errores

- **Tipos de errores: sintácticos, lógicos y de ejecución**
- **Manejo de excepciones con try-catch**
- **Uso de finally para liberar recursos**

---

## 🌍 ECLIPSE IDE

### 📝 Definición

**Entorno integrado de desarrollo o IDE (Integrated Development Environment)** que permite escribir código de un modo cómodo (la comodidad reside en que los entornos de desarrollo integrados son mucho más que 1 simple editor de textos).

### 🎯 Características comunes de las IDE

#### 🎨 Funcionalidades Básicas
- **Coloreado de sintaxis**
- **Herramientas de búsqueda**
- **Asistentes para la escritura de código**
- **Ejecución de aplicaciones** sin abandonar el entorno
- **Herramientas de depuración de código**

#### 🔗 Funcionalidades Avanzadas
- **Conexión con sistema de seguimiento de errores**
- **Facilidades para la creación de tareas**
- **Herramientas avanzadas para el análisis de código**
- **Herramientas de análisis de rendimiento**
- **Conexión a gestores de bases de datos**
- **Conexión con sistemas de control de versiones**

### 📊 Diagrama de Funcionalidades de Eclipse

```
┌─────────────────────────────────────────────────────────────┐
│                      ECLIPSE IDE                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                FUNCIONALIDADES                     │   │
│  │                                                     │   │
│  │  🎨 EDITOR:                                     │   │
│  │  • Coloreado de sintaxis                           │   │
│  │  • Herramientas de búsqueda                        │   │
│  │  • Asistentes para escritura                       │   │
│  │                                                     │   │
│  │  🚀 EJECUCIÓN:                                  │   │
│  │  • Ejecución sin abandonar entorno                 │   │
│  │  • Herramientas de depuración                       │   │
│  │                                                     │   │
│  │  🔗 INTEGRACIÓN:                                │   │
│  │  • Sistema de seguimiento de errores              │   │
│  │  • Creación de tareas                             │   │
│  │  • Análisis de código y rendimiento                │   │
│  │  • Gestores de bases de datos                     │   │
│  │  • Control de versiones                           │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏷️ PROGRAMACIÓN ORIENTADA A OBJETOS

### 📋 DECLARACIÓN DE UNA CLASE Y DEFINICIÓN DE OBJETOS

#### 🔧 Sintaxis de una Clase

```java
class [nombre de la clase] {
    [atributos o variables de la clase]
    [métodos o funciones de la clase]
    [main]
}
```

### 👤 Ejemplo Práctico: Clase Persona

```java
import java.util.Scanner;

public class Persona {
    private Scanner teclado;
    private String nombre;
    private int edad;
    
    public void inicializar() {
        teclado = new Scanner(System.in);
        System.out.print("Ingrese nombre:");
        nombre = teclado.next();
        System.out.print("Ingrese edad:");
        edad = teclado.nextInt();
    }
    
    public void imprimir() {
        System.out.println("Nombre:" + nombre);
        System.out.println("Edad:" + edad);
    }
    
    public void esMayorEdad() {
        if (edad >= 18) {
            System.out.print(nombre + " es > de edad.");
        } else {
            System.out.print(nombre + " no es < de edad.");
        }
    }
    
    public static void main(String[] ar) {
        Persona persona1;
        persona1 = new Persona();
        persona1.inicializar();
        persona1.imprimir();
        persona1.esMayorEdad();
    }
}
```

**🎯 Objetivo**: Confeccionar 1 clase que permita cargar el nombre y la edad de 1 persona, mostrar los datos cargados e imprimir un mensaje si es mayor de edad (edad>=18)

---

## 🔧 DECLARACIÓN DE MÉTODOS

### 📝 Tipos de Métodos

#### 1️⃣ **Métodos sin parámetros**
```java
public void [nombre del método]() {
    [algoritmo]
}
```

#### 2️⃣ **Métodos con parámetros**
```java
public void [nombre del método]([parámetros]) {
    [algoritmo]
}
```

#### 3️⃣ **Métodos que retornan un dato**
```java
public [tipo de dato] [nombre del método]([parámetros]) {
    [algoritmo]
    return [tipo de dato];
}
```

### 🧮 Ejemplo: Tabla de Multiplicar

```java
import java.util.Scanner;

public class TablaMultiplicar {
    public void cargarValor() {
        Scanner teclado = new Scanner(System.in);
        int valor;
        do {
            System.out.print("Ingrese valor:");
            valor = teclado.nextInt();
            if (valor != -1) {
                calcular(valor);
            }
        } while (valor != -1);
    }
    
    public void calcular(int v) {
        for (int f = v; f <= v * 10; f += v) {
            System.out.print(f + "-");
        }
    }
    
    public static void main(String[] ar) {
        TablaMultiplicar tabla;
        tabla = new TablaMultiplicar();
        tabla.cargarValor();
    }
}
```

**🎯 Objetivo**: Confeccionar 1 clase que permita ingresar valores enteros por teclado y que muestre la tabla de multiplicar de dicho valor. Finalizar el programa al ingresar -1

---

## 📊 VECTORES (ARRAYS)

### 📋 Definición

**Un vector es 1 estructura de datos que permite almacenar un CONJUNTO de datos del MISMO tipo. Con 1 único nombre se define un vector y por medio de 1 subíndice se hace referencia a cada elemento del mismo (componente)**

### 🔧 Sintaxis de Vectores

```java
// Declaración
[tipo de dato][] [nombre del vector];

// Inicialización
[nombre del vector] = new [tipo de dato][tamaño];

// Acceso a elementos
[nombre del vector][índice]
```

### 💰 Ejemplo: Sueldos de Operarios

```java
import java.util.Scanner;

public class PruebaVector1 {
    private Scanner teclado;
    private int[] sueldos;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        sueldos = new int[5];
        for (int f = 0; f < 5; f++) {
            System.out.print("Ingrese valor del componente:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for (int f = 0; f < 5; f++) {
            System.out.println(sueldos[f]);
        }
    }
    
    public static void main(String[] ar) {
        PruebaVector1 pv = new PruebaVector1();
        pv.cargar();
        pv.imprimir();
    }
}
```

**🎯 Objetivo**: Guardar los sueldos de 5 operarios

### 📊 Representación Visual del Vector

```
┌─────────────────────────────────────────────────────────────┐
│                        VECTOR SUELDOS                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌───────┬───────┬───────┬───────┬───────┐                 │
│  │ 1200  │  750  │  820  │  550  │  490  │                 │
│  └───────┴───────┴───────┴───────┴───────┘                 │
│     [0]     [1]     [2]     [3]     [4]                    │
│                                                             │
│  sueldos[0] sueldos[1] sueldos[2] sueldos[3] sueldos[4]    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 VECTORES DINÁMICOS

### 🔧 Ejemplo: Vector con Tamaño Variable

```java
import java.util.Scanner;

public class PruebaVector8 {
    private Scanner teclado;
    private int[] sueldos;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        System.out.print("Cuantos sueldos cargará:");
        int cant;
        cant = teclado.nextInt();
        sueldos = new int[cant];
        for (int f = 0; f < sueldos.length; f++) {
            System.out.print("Ingrese sueldo:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for (int f = 0; f < sueldos.length; f++) {
            System.out.println(sueldos[f]);
        }
    }
    
    public static void main(String[] ar) {
        PruebaVector8 pv = new PruebaVector8();
        pv.cargar();
        pv.imprimir();
    }
}
```

**🎯 Objetivo**: Almacenar los sueldos de operarios, se debe pedir la cantidad de sueldos a ingresar y luego crear un vector con dicho tamaño.

---

## 📊 VECTORES PARALELOS

### 📋 Definición

**Se da cuando hay una relación entre las componentes de igual subíndice (misma posición) de un vector y otro.**

### 👥 Ejemplo: Nombres y Edades

```java
import java.util.Scanner;

public class PruebaVector10 {
    private Scanner teclado;
    private String[] nombres;
    private int[] edades;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        nombres = new String[5];
        edades = new int[5];
        for (int f = 0; f < nombres.length; f++) {
            System.out.print("Ingrese nombre:");
            nombres[f] = teclado.next();
            System.out.print("Ingrese edad:");
            edades[f] = teclado.nextInt();
        }
    }
    
    public void mayoresEdad() {
        System.out.println("Personas mayores de edad:");
        for (int f = 0; f < nombres.length; f++) {
            if (edades[f] >= 18) {
                System.out.println(nombres[f]);
            }
        }
    }
    
    public static void main(String[] ar) {
        PruebaVector10 pv = new PruebaVector10();
        pv.cargar();
        pv.mayoresEdad();
    }
}
```

### 📊 Representación Visual de Vectores Paralelos

```
┌─────────────────────────────────────────────────────────────┐
│                    VECTORES PARALELOS                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  NOMBRES:                                                   │
│  ┌───────┬───────┬───────┬───────┬───────┐                 │
│  │ Juan  │  Ana  │ Marcos│ Pablo │ Laura │                 │
│  └───────┴───────┴───────┴───────┴───────┘                 │
│     [0]     [1]     [2]     [3]     [4]                    │
│                                                             │
│  EDADES:                                                    │
│  ┌───────┬───────┬───────┬───────┬───────┐                 │
│  │  12   │  21   │  27   │  14   │  21   │                 │
│  └───────┴───────┴───────┴───────┴───────┘                 │
│     [0]     [1]     [2]     [3]     [4]                    │
│                                                             │
│  Relación: nombres[0] ↔ edades[0]                          │
│           nombres[1] ↔ edades[1]                          │
│           etc...                                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**🎯 Objetivo**: Almacenar en 1 vector los nombres de personas y en el otro sus edades

---

## 🔢 MATRICES (ARRAYS BIDIMENSIONALES)

### 📋 Definición

**Es 1 estructura de datos que permite almacenar 1 CONJUNTO de datos del MISMO tipo. Con 1 único nombre se define la matriz y por medio de DOS subíndices se hace referencia a cada elemento de la misma (componente)**

### 🎯 Ejemplo: Matriz de 3 filas x 5 columnas

**Crear 1 matriz de 3 filas x 5 columnas con elementos de tipo int, cargar sus componentes y luego imprimirlas.**

### 📊 Representación Visual de la Matriz

```
┌─────────────────────────────────────────────────────────────┐
│                        MATRIZ 3x5                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                    Columnas                                 │
│         ┌─────┬─────┬─────┬─────┬─────┐                     │
│         │  50 │   5 │  27 │ 400 │   7 │  Fila 0             │
│  Filas  ├─────┼─────┼─────┼─────┼─────┤                     │
│         │   0 │  67 │  90 │   6 │  97 │  Fila 1             │
│         ├─────┼─────┼─────┼─────┼─────┤                     │
│         │  30 │  14 │  23 │ 251 │ 490 │  Fila 2             │
│         └─────┴─────┴─────┴─────┴─────┘                     │
│           [0]   [1]   [2]   [3]   [4]                      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 💻 Código de Implementación

```java
import java.util.Scanner;

public class Matriz1 {
    private Scanner teclado;
    private int[][] mat;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        mat = new int[3][5];
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 5; c++) {
                System.out.print("Ingrese componente:");
                mat[f][c] = teclado.nextInt();
            }
        }
    }
    
    public void imprimir() {
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 5; c++) {
                System.out.print(mat[f][c] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] ar) {
        Matriz1 ma = new Matriz1();
        ma.cargar();
        ma.imprimir();
    }
}
```

---

## 🏗️ CONSTRUCTOR DE LA CLASE

### 📝 Definición

**Es 1 método que se ejecuta inicialmente y en forma automática.**

### 🎯 Características del Constructor

- **Tiene el mismo nombre de la clase**
- **Es el primer método que se ejecuta**
- **Se ejecuta en forma automática**
- **No puede retornar datos**
- **Se ejecuta una única vez**
- **Tiene por objetivo inicializar atributos**

### 💼 Ejemplo: Clase Operarios con Constructor

```java
import java.util.Scanner;

public class Operarios {
    private Scanner teclado;
    private int[] sueldos;
    
    public Operarios() {
        teclado = new Scanner(System.in);
        sueldos = new int[5];
        for (int f = 0; f < 5; f++) {
            System.out.print("Ingrese el valor:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for (int f = 0; f < 5; f++) {
            System.out.println(sueldos[f]);
        }
    }
    
    public static void main(String[] ar) {
        Operarios op = new Operarios();
        op.imprimir();
    }
}
```

**🎯 Objetivo**: Guardar los sueldos de 5 operarios en un vector. Realizar la creación y carga del vector en el constructor.

---

## 🧬 HERENCIA

### 📝 Definición

**Crear nuevas clases partiendo de clases existentes, que tiene todos los atributos y métodos de su 'superclase' o 'clase padre' y se le podrán añadir otros atributos y métodos propios.**

### 🔗 Sintaxis de Herencia

```java
public class [ClaseHija] extends [ClasePadre] {
    // Atributos y métodos adicionales
}
```

### 🧮 Ejemplo: Sistema de Operaciones Matemáticas

#### 👨‍👦 Clase Padre: Operacion

```java
import java.util.Scanner;

public class Operacion {
    protected Scanner teclado;
    protected int valor1;
    protected int valor2;
    protected int resultado;
    
    public Operacion() {
        teclado = new Scanner(System.in);
    }
    
    public void cargar1() {
        System.out.print("Ingrese el 1er.valor:");
        valor1 = teclado.nextInt();
    }
    
    public void cargar2() {
        System.out.print("Ingrese el 2do.valor:");
        valor2 = teclado.nextInt();
    }
    
    public void mostrarResultado() {
        System.out.println(resultado);
    }
}
```

#### ➕ Clase Hija: Suma

```java
public class Suma extends Operacion {
    public void operar() {
        resultado = valor1 + valor2;
    }
}
```

#### ➖ Clase Hija: Resta

```java
public class Resta extends Operacion {
    public void operar() {
        resultado = valor1 - valor2;
    }
}
```

#### 🧪 Clase de Prueba

```java
public class Prueba {
    public static void main(String[] ar) {
        Suma suma1 = new Suma();
        suma1.cargar1();
        suma1.cargar2();
        suma1.operar();
        System.out.print("El resultado de la suma es:");
        suma1.mostrarResultado();
        
        Resta resta1 = new Resta();
        resta1.cargar1();
        resta1.cargar2();
        resta1.operar();
        System.out.print("El resultado de la resta es:");
        resta1.mostrarResultado();
    }
}
```

### 🎯 Ventajas de la Herencia

- **Reutilización de código**: No duplicar funcionalidad
- **Jerarquía lógica**: Organización natural de clases
- **Mantenimiento**: Cambios en clase padre afectan a todas las hijas
- **Extensibilidad**: Fácil agregar nuevas funcionalidades

---

## 📚 BIBLIOGRAFÍA RECOMENDADA

### 🌐 Recursos Web

• **Tutorial Java Completo**  
  🔗 [http://www.tutorialesprogramacionya.com/javaya/](http://www.tutorialesprogramacionya.com/javaya/)

• **Manual de Desarrollo Web**  
  🔗 [https://desarrolloweb.com/manuales/57/](https://desarrolloweb.com/manuales/57/)

• **Cursos Online Gratuitos**  
  🔗 [https://elestudiantedigital.com/cursos-online-gratis-java/](https://elestudiantedigital.com/cursos-online-gratis-java/)

### 📖 Libros Especializados

• **Java2 incluye Swing, Threads, programación en red, JavaBeans, JDBC y JSP / Servlets. 2004. De Jorge Sánchez**

• **Aprenda Java como si estuviera en primero. 2000. De García de Jalón, Mingo, Imaz, Brazalez, Larzabal, Calleja y García.**

• **Desarrollo de Proyectos Informáticos con Tecnología Java. 2000. De Fernandez, Canut y Navarro.**

### 📊 Diagrama de Recursos

```
┌─────────────────────────────────────────────────────────────┐
│                 RECURSOS DE APRENDIZAJE                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                        📚                                │
│                    ┌─────────────┐                       │
│                    │   LIBROS    │                       │
│                    │ RECOMENDA-  │                       │
│                    │    DOS      │                       │
│                    └─────────────┘                       │
│                                                             │
│  🌐 TUTORIALES WEB    📖 LIBROS    💻 PRÁCTICA           │
│  • Interactivos       • Teóricos   • Proyectos            │
│  • Actualizados       • Completos  • Ejercicios           │
│  • Gratuitos          • Detallados • Ejemplos             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎉 ¡FELICITACIONES!

**Has completado exitosamente el estudio de la Clase 3: Java y Eclipse IDE**

✅ **Conceptos dominados**: POO, clases, objetos, métodos, vectores, matrices  
✅ **Herramientas aprendidas**: Eclipse IDE, debugging, compilación  
✅ **Estructuras de datos**: Arrays, vectores paralelos, matrices bidimensionales  
✅ **Programación avanzada**: Herencia, constructores, encapsulación  
✅ **Base sólida**: Para desarrollo de aplicaciones Java profesionales  

**¡Continúa con el siguiente tema del curso de Programación Concurrente!**

---

*Documento generado para el curso de Programación Concurrente 2024*  
*Facultad de Ingeniería - Universidad Nacional de Jujuy*  
*Profesor: Ing. José Farfán*

---
