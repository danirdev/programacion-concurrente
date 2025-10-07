# 📚 CLASE 3 - INTRODUCCIÓN A JAVA

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Java

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [☕ Características de Java](#características-de-java)
2. [💻 Eclipse IDE](#eclipse-ide)
3. [🔧 Instalación y Configuración](#instalación-y-configuración)
4. [📝 Estructuras Básicas](#estructuras-básicas)
5. [🎯 Programación Orientada a Objetos](#programación-orientada-a-objetos)
6. [📚 Bibliografía](#bibliografía)

---

## ☕ **CARACTERÍSTICAS PRINCIPALES DE JAVA**

### 📊 **Índice TIOBE**

Java aparece consistentemente en los primeros lugares del Índice TIOBE (Febrero 2022), demostrando su relevancia y popularidad en la industria.

### 🔑 **Características Clave**

#### 1️⃣ **Independiente del Hardware**
El código Java se ejecuta en cualquier plataforma con JVM (Java Virtual Machine).

#### 2️⃣ **Orientado a Objetos y de Propósito General**
Paradigma OOP completo para diversos tipos de aplicaciones.

#### 3️⃣ **Sintaxis Parecida a C++**
Facilita la transición para programadores de C++.

#### 4️⃣ **Robusto**
Manejo de errores, gestión de memoria automática.

#### 5️⃣ **Solo Admite Herencia Simple**
A diferencia de C++, evita complejidades de herencia múltiple.

#### 6️⃣ **Seguridad desde el Punto de Vista del Programador**
- Comprobación estricta de tipos de datos
- Gestión de excepciones
- No existencia de punteros

#### 7️⃣ **Seguridad desde el Punto de Vista del Usuario**
- Los programas se ejecutan sobre una máquina virtual (JVM)
- Espacio de nombres
- Programación Concurrente Nativa

#### 8️⃣ **Tipos de Datos Estandarizados**
Los tipos de datos tienen el mismo tamaño en todas las plataformas.

---

## 🔄 **PROCESO DE COMPILACIÓN Y EJECUCIÓN**

```
┌─────────────────────────────────────────────────┐
│  Código Java (archivos .java)                  │
└─────────────────┬───────────────────────────────┘
                  │
                  ▼
         ┌─────────────────┐
         │ JAVAC (compilador) │
         └─────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────┐
│  Byte Code (archivos .class)                   │
└─────────────────┬───────────────────────────────┘
                  │
         ┌────────┴────────┐
         │                 │
         ▼                 ▼
    ┌────────┐        ┌────────┐
    │  JVM   │        │  JVM   │
    │Windows │        │ Linux  │
    └────────┘        └────────┘
```

**Ventaja:** "Write Once, Run Anywhere" (Escribe una vez, ejecuta en cualquier lugar)

---

## 💻 **ECLIPSE**

### 📝 **Definición**

**Eclipse** es un Entorno Integrado de Desarrollo o **IDE** (Integrated Development Environment) que permite escribir código de un modo cómodo.

La comodidad reside en que los entornos de desarrollo integrados son **mucho más que un simple editor de textos**.

### ✅ **Características Comunes de los IDE**

#### 🎨 **Asistencia de Código**
- Coloreado de sintaxis
- Asistentes para la escritura de código
- Auto-completado

#### 🔍 **Herramientas de Desarrollo**
- Herramientas de búsqueda
- Herramientas de depuración de código
- Herramientas avanzadas para el análisis de código
- Herramientas de análisis de rendimiento

#### ▶️ **Ejecución**
- Ejecución de aplicaciones sin abandonar el entorno

#### 🔗 **Integración**
- Conexión con sistema de control de versiones
- Conexión con sistema de seguimiento de errores
- Conexión a gestores de bases de datos

#### 📋 **Gestión de Proyectos**
- Facilidades para la creación de tareas

---

## 🔧 **INSTALACIÓN Y CONFIGURACIÓN**

### 📦 **Consideraciones para la Instalación de Java y Eclipse**

#### 1️⃣ **Instalar Java SE Development Kit**
- Descargar la versión x64 o de 32 bits según tu sistema

#### 2️⃣ **Configurar Variables de Entorno**

Eclipse necesita que Java esté instalado y correctamente configurado. Las siguientes variables de entorno deben estar correctamente seteadas:

```bash
set JAVAPATH=<directorio donde hayas instalado java>
PATH=.;%JAVAPATH%\bin;%PATH%
set CLASSPATH=.\;%JAVAPATH%\lib\classes.zip;%CLASSPATH%
```

---

## 🚀 **PASOS PARA CREAR UN PROGRAMA JAVA EN ECLIPSE**

### 📋 **Proceso en 4 Pasos**

#### 1️⃣ **Crear un Proyecto**
- File → New → Java Project
- Asignar nombre al proyecto

#### 2️⃣ **Crear una Clase**
- Click derecho en src → New → Class
- Incluir `main` si es el único programa

#### 3️⃣ **Escribir el Programa**
- Codificar en el editor

#### 4️⃣ **Compilar y Ejecutar**
- Run → Run As → Java Application

---

## ⚠️ **ERRORES EN PROGRAMACIÓN**

### 🔴 **Error Sintáctico**

Error en la escritura del código que impide la compilación.

```java
import java.util.Scanner;

public class SuperficieCuadrado {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int lado;
        int superficie;
        
        System.out.print("Ingrese el valor del lado del cuadrado:");
        lado = teclado.nextInt();
        superficie = lado * lado;
        
        System.out.print("La superficie del cuadrado es:");
        System.out.print(Superficie);  // ❌ Error: 'Superficie' con mayúscula
    }
}
```

**Error:** La variable se declaró como `superficie` (minúscula) pero se usa como `Superficie` (mayúscula). Java es case-sensitive.

---

### 🟡 **Error Lógico**

El programa compila y ejecuta, pero produce resultados incorrectos.

```java
import java.util.Scanner;

public class SuperficieCuadrado {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int lado;
        int superficie;
        
        System.out.print("Ingrese el valor del lado del cuadrado:");
        lado = teclado.nextInt();
        superficie = lado * lado * lado;  // ❌ Error lógico: calcula volumen, no superficie
        
        System.out.print("La superficie del cuadrado es:");
        System.out.print(superficie);
    }
}
```

**Error:** La fórmula calcula el volumen de un cubo (`lado³`) en lugar de la superficie de un cuadrado (`lado²`).

---

## 🔀 **ESTRUCTURAS CONDICIONALES**

### 1️⃣ **Estructura Condicional Simple**

```java
import java.util.Scanner;

public class EstructuraCondicionalSimple1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        float sueldo;
        
        System.out.print("Ingrese el sueldo:");
        sueldo = teclado.nextFloat();
        
        if (sueldo > 29855) {
            System.out.println("Esta persona debe abonar impuesto a las ganancias");
        }
    }
}
```

**Característica:** Solo ejecuta código si la condición es verdadera.

---

### 2️⃣ **Estructura Condicional Compuesta**

```java
import java.util.Scanner;

public class EstructuraCondicionalCompuesta1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int num1, num2;
        
        System.out.print("Ingrese primer valor:");
        num1 = teclado.nextInt();
        System.out.print("Ingrese segundo valor:");
        num2 = teclado.nextInt();
        
        if (num1 > num2) {
            System.out.print(num1);
        } else {
            System.out.print(num2);
        }
    }
}
```

**Característica:** Tiene dos caminos: uno para cuando la condición es verdadera y otro para cuando es falsa.

---

### 3️⃣ **Estructura Condicional Anidada**

```java
import java.util.Scanner;

public class EstructuraCondicionalAnidada1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int nota1, nota2, nota3;
        
        System.out.print("Ingrese primer nota:");
        nota1 = teclado.nextInt();
        System.out.print("Ingrese segunda nota:");
        nota2 = teclado.nextInt();
        System.out.print("Ingrese tercer nota:");
        nota3 = teclado.nextInt();
        
        int promedio = (nota1 + nota2 + nota3) / 3;
        
        if (promedio >= 7) {
            System.out.print("Promocionado");
        } else {
            if (promedio >= 4) {
                System.out.print("Regular");
            } else {
                System.out.print("Reprobado");
            }
        }
    }
}
```

**Característica:** Estructuras `if` dentro de otras estructuras `if`.

---

## ⚙️ **OPERADORES**

### 📋 **Tipos de Operadores**

#### 🔢 **Operadores Relacionales**
- `>` Mayor que
- `<` Menor que
- `>=` Mayor o igual que
- `<=` Menor o igual que
- `==` Igual a
- `!=` Diferente de

#### ➕ **Operadores Matemáticos**
- `+` Suma
- `-` Resta
- `*` Multiplicación
- `/` División
- `%` Módulo (resto)

#### 🔗 **Operadores Lógicos**
- `&&` AND (y lógico)
- `||` OR (o lógico)

---

### 💻 **Ejemplo con Operadores Lógicos**

```java
import java.util.Scanner;

public class CondicionesCompuestas1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int num1, num2, num3;
        
        System.out.print("Ingrese primer valor:");
        num1 = teclado.nextInt();
        System.out.print("Ingrese segundo valor:");
        num2 = teclado.nextInt();
        System.out.print("Ingrese tercer valor:");
        num3 = teclado.nextInt();
        
        if (num1 > num2 && num1 > num3) {
            System.out.print(num1);
        } else {
            if (num2 > num3) {
                System.out.print(num2);
            } else {
                System.out.print(num3);
            }
        }
    }
}
```

---

## 🔁 **ESTRUCTURAS REPETITIVAS**

### 1️⃣ **Estructura Repetitiva WHILE**

**Característica:** Se repite mientras la condición sea verdadera. La condición se evalúa **antes** de ejecutar el bloque.

#### 📋 **Ejemplo 1: Imprimir números del 1 al 100**

```java
public class EstructuraRepetitivaWhile1 {
    public static void main(String[] ar) {
        int x;
        x = 1;
        
        while (x <= 100) {
            System.out.print(x);
            System.out.print(" - ");
            x = x + 1;
        }
    }
}
```

#### 📋 **Ejemplo 2: Calcular suma y promedio**

```java
import java.util.Scanner;

public class EstructuraRepetitivaWhile3 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int x, suma, valor, promedio;
        
        x = 1;
        suma = 0;
        
        while (x <= 10) {
            System.out.print("Ingrese un valor:");
            valor = teclado.nextInt();
            suma = suma + valor;
            x = x + 1;
        }
        
        promedio = suma / 10;
        System.out.print("La suma de los 10 valores es:");
        System.out.println(suma);
        System.out.print("El promedio es:");
        System.out.print(promedio);
    }
}
```

---

### 2️⃣ **Estructura Repetitiva DO WHILE**

**Característica:** Se repite mientras la condición sea verdadera. La condición se evalúa **después** de ejecutar el bloque (se ejecuta al menos una vez).

```java
import java.util.Scanner;

public class EstructuraRepetitivaDoWhile1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int valor;
        
        do {
            System.out.print("Ingrese 1 valor entre 0 y 999 (0=fin):");
            valor = teclado.nextInt();
            
            if (valor >= 100) {
                System.out.println("Tiene 3 dígitos.");
            } else {
                if (valor >= 10) {
                    System.out.println("Tiene 2 dígitos.");
                } else {
                    System.out.println("Tiene 1 dígito.");
                }
            }
        } while (valor != 0);
    }
}
```

**Explicación:** El bloque se repite hasta que se ingresa el valor 0, momento en el cual la condición retorna `false` y finaliza el programa.

---

### 3️⃣ **Estructura Repetitiva FOR**

**Característica:** Estructura ideal cuando se conoce de antemano la cantidad de repeticiones.

#### 📋 **Ejemplo 1: Imprimir números del 1 al 100**

```java
public class EstructuraRepetitivaFor1 {
    public static void main(String[] ar) {
        int f;
        
        for(f = 1; f <= 100; f++) {
            System.out.print(f);
            System.out.print("-");
        }
    }
}
```

#### 📋 **Ejemplo 2: Calcular suma y promedio**

```java
import java.util.Scanner;

public class EstructuraRepetitivaFor2 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        int suma, f, valor, promedio;
        
        suma = 0;
        
        for(f = 1; f <= 10; f++) {
            System.out.print("Ingrese valor:");
            valor = teclado.nextInt();
            suma = suma + valor;
        }
        
        System.out.print("La suma es:");
        System.out.println(suma);
        promedio = suma / 10;
        System.out.print("El promedio es:");
        System.out.print(promedio);
    }
}
```

---

## 📝 **CADENA DE CARACTERES**

### 💻 **Ejemplo: Comparar edades**

```java
import java.util.Scanner;

public class CadenaDeCaracteres1 {
    public static void main(String[] ar) {
        Scanner teclado = new Scanner(System.in);
        String nombre1, nombre2;
        int edad1, edad2;
        
        System.out.print("Ingrese el nombre:");
        nombre1 = teclado.next();
        System.out.print("Ingrese edad:");
        edad1 = teclado.nextInt();
        
        System.out.print("Ingrese el nombre:");
        nombre2 = teclado.next();
        System.out.print("Ingrese edad:");
        edad2 = teclado.nextInt();
        
        System.out.print("La persona de mayor edad es:");
        if (edad1 > edad2) {
            System.out.print(nombre1);
        } else {
            System.out.print(nombre2);
        }
    }
}
```

**Objetivo:** Solicitar el ingreso del nombre y edad de dos personas y mostrar el nombre de la persona de mayor edad.

---

## 🎯 **DECLARACIÓN DE UNA CLASE Y DEFINICIÓN DE OBJETOS**

### 📋 **Sintaxis General**

```java
class [nombre de la clase] {
    [atributos o variables de la clase]
    [métodos o funciones de la clase]
    [main]
}
```

### 💻 **Ejemplo Completo: Clase Persona**

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
            System.out.print(nombre + " es mayor de edad.");
        } else {
            System.out.print(nombre + " no es menor de edad.");
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

**Objetivo:** Confeccionar una clase que permita cargar el nombre y la edad de una persona, mostrar los datos cargados e imprimir un mensaje si es mayor de edad (edad >= 18).

---

## ⚙️ **DECLARACIÓN DE MÉTODOS**

### 📋 **Tipos de Métodos**

#### 1️⃣ **Métodos sin Parámetros**
```java
public void [nombre del método]() {
    [algoritmo]
}
```

#### 2️⃣ **Métodos con Parámetros**
```java
public void [nombre del método]([parámetros]) {
    [algoritmo]
}
```

#### 3️⃣ **Métodos que Retornan un Dato**
```java
public [tipo de dato] [nombre del método]([parámetros]) {
    [algoritmo]
    return [valor del tipo de dato];
}
```

---

### 💻 **Ejemplo: Tabla de Multiplicar**

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
        for(int f = v; f <= v * 10; f = f + v) {
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

**Objetivo:** Confeccionar una clase que permita ingresar valores enteros por teclado y que muestre la tabla de multiplicar de dicho valor. Finalizar el programa al ingresar -1.

---

## 📊 **VECTOR**

### 📝 **Definición**

Un **vector** es una estructura de datos que permite almacenar un **CONJUNTO de datos del MISMO tipo**.

Con un **único nombre** se define un vector y por medio de un **subíndice** se hace referencia a cada elemento del mismo (componente).

### 💻 **Ejemplo 1: Vector de Tamaño Fijo**

```java
import java.util.Scanner;

public class PruebaVector1 {
    private Scanner teclado;
    private int[] sueldos;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        sueldos = new int[5];
        
        for(int f = 0; f < 5; f++) {
            System.out.print("Ingrese valor del componente:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for(int f = 0; f < 5; f++) {
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

**Objetivo:** Guardar los sueldos de 5 operarios.

---

### 💻 **Ejemplo 2: Vector de Tamaño Dinámico**

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
        
        for(int f = 0; f < sueldos.length; f++) {
            System.out.print("Ingrese sueldo:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for(int f = 0; f < sueldos.length; f++) {
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

**Objetivo:** Almacenar los sueldos de operarios. Se debe pedir la cantidad de sueldos a ingresar y luego crear un vector con dicho tamaño.

---

## 🔗 **VECTORES PARALELOS**

### 📝 **Definición**

Se da cuando hay una **relación entre las componentes de igual subíndice** (misma posición) de un vector y otro.

### 💻 **Ejemplo: Nombres y Edades**

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
        
        for(int f = 0; f < nombres.length; f++) {
            System.out.print("Ingrese nombre:");
            nombres[f] = teclado.next();
            System.out.print("Ingrese edad:");
            edades[f] = teclado.nextInt();
        }
    }
    
    public void mayoresEdad() {
        System.out.println("Personas mayores de edad.");
        for(int f = 0; f < nombres.length; f++) {
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

**Objetivo:** Almacenar en un vector los nombres de personas y en el otro sus edades.

---

## 📐 **MATRIZ**

### 📝 **Definición**

Es una **estructura de datos** que permite almacenar un **CONJUNTO de datos del MISMO tipo**.

Con un **único nombre** se define la matriz y por medio de **DOS subíndices** se hace referencia a cada elemento de la misma (componente).

### 💻 **Ejemplo: Matriz 3x5**

```java
import java.util.Scanner;

public class Matriz1 {
    private Scanner teclado;
    private int[][] mat;
    
    public void cargar() {
        teclado = new Scanner(System.in);
        mat = new int[3][5];
        
        for(int f = 0; f < 3; f++) {
            for(int c = 0; c < 5; c++) {
                System.out.print("Ingrese componente:");
                mat[f][c] = teclado.nextInt();
            }
        }
    }
    
    public void imprimir() {
        for(int f = 0; f < 3; f++) {
            for(int c = 0; c < 5; c++) {
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

**Objetivo:** Crear una matriz de 3 filas por 5 columnas con elementos de tipo `int`, cargar sus componentes y luego imprimirlas.

---

## 🏗️ **CONSTRUCTOR DE LA CLASE**

### 📝 **Definición**

Es un **método que se ejecuta inicialmente y en forma automática**.

### ✅ **Características del Constructor**

1. **Tiene el mismo nombre de la clase**
2. **Es el primer método que se ejecuta**
3. **Se ejecuta en forma automática**
4. **No puede retornar datos**
5. **Se ejecuta una única vez**
6. **Tiene por objetivo inicializar atributos**

### 💻 **Ejemplo: Constructor en Clase Operarios**

```java
import java.util.Scanner;

public class Operarios {
    private Scanner teclado;
    private int[] sueldos;
    
    public Operarios() {
        teclado = new Scanner(System.in);
        sueldos = new int[5];
        
        for(int f = 0; f < 5; f++) {
            System.out.print("Ingrese el valor:");
            sueldos[f] = teclado.nextInt();
        }
    }
    
    public void imprimir() {
        for(int f = 0; f < 5; f++) {
            System.out.println(sueldos[f]);
        }
    }
    
    public static void main(String[] ar) {
        Operarios op = new Operarios();
        op.imprimir();
    }
}
```

**Objetivo:** Guardar los sueldos de 5 operarios en un vector. Realizar la creación y carga del vector en el constructor.

---

## 🧬 **HERENCIA**

### 📝 **Definición**

Permite crear **nuevas clases partiendo de clases existentes**, que tiene todos los **atributos y métodos** de su **'superclase' o 'clase padre'** y se le podrán añadir otros atributos y métodos propios.

### 💻 **Ejemplo Completo**

#### 📋 **Clase Base: Operacion**

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
        System.out.print("Ingrese el 1er. valor:");
        valor1 = teclado.nextInt();
    }
    
    public void cargar2() {
        System.out.print("Ingrese el 2do. valor:");
        valor2 = teclado.nextInt();
    }
    
    public void mostrarResultado() {
        System.out.println(resultado);
    }
}
```

---

#### 📋 **Clase Derivada: Suma**

```java
public class Suma extends Operacion {
    void operar() {
        resultado = valor1 + valor2;
    }
}
```

#### 📋 **Clase Derivada: Resta**

```java
public class Resta extends Operacion {
    public void operar() {
        resultado = valor1 - valor2;
    }
}
```

---

#### 📋 **Clase Prueba (Main)**

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

**Nota:** El modificador `protected` permite que las variables sean accesibles desde las clases hijas.

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

🌐 **Tutoriales y Recursos Web**

**Tutorial de Programación Java:**  
http://www.tutorialesprogramacionya.com/javaya/

**Tutorial de Desarrollo Web:**  
https://desarrolloweb.com/manuales/57/

**Cursos Online Gratis de Java:**  
https://elestudiantedigital.com/cursos-online-gratis-java/

### 📖 **Libros Recomendados**

**Sánchez, Jorge** (2004)  
*Java2 incluye Swing, Threads, programación en red, JavaBeans, JDBC y JSP / Servlets*

**García de Jalón, Mingo, Imaz, Brazalez, Larzabal, Calleja y García** (2000)  
*Aprenda Java como si estuviera en primero*

**Fernandez, Canut y Navarro** (2000)  
*Desarrollo de Proyectos Informáticos con Tecnología Java*

---

## 📊 **RESUMEN DE CONCEPTOS CLAVE**

### ✅ **Fundamentos de Java**

- **Multiplataforma:** Write Once, Run Anywhere
- **Orientado a Objetos:** Paradigma OOP completo
- **Robusto:** Gestión automática de memoria
- **Seguro:** Sin punteros, con gestión de excepciones
- **Herencia Simple:** Solo herencia de una clase

### ✅ **Estructuras de Control**

- **Condicionales:** if, if-else, if anidado
- **Repetitivas:** while, do-while, for
- **Operadores:** Relacionales, matemáticos, lógicos

### ✅ **Estructuras de Datos**

- **Variables simples:** int, float, String
- **Vectores:** Arrays unidimensionales
- **Matrices:** Arrays bidimensionales
- **Vectores paralelos:** Relación por índice

### ✅ **POO en Java**

- **Clases:** Moldes para objetos
- **Objetos:** Instancias de clases
- **Métodos:** Sin parámetros, con parámetros, con retorno
- **Constructor:** Inicialización automática
- **Herencia:** Extends para reutilización

---

## 🎓 **TABLA COMPARATIVA: ESTRUCTURAS REPETITIVAS**

| Característica | while | do-while | for |
|----------------|-------|----------|-----|
| **Evaluación condición** | Al inicio | Al final | Al inicio |
| **Ejecuciones mínimas** | 0 | 1 | 0 |
| **Uso típico** | Repeticiones indefinidas | Al menos 1 vez | Repeticiones conocidas |
| **Ejemplo** | Validación entrada | Menús | Recorrer arrays |

---

## 🔍 **BUENAS PRÁCTICAS EN JAVA**

### ✅ **Nomenclatura**

- **Clases:** PascalCase (ej: `MiClase`)
- **Métodos:** camelCase (ej: `calcularPromedio`)
- **Variables:** camelCase (ej: `miVariable`)
- **Constantes:** MAYUSCULAS_SNAKE_CASE (ej: `MAX_VALOR`)

### ✅ **Modificadores de Acceso**

- **private:** Solo accesible dentro de la clase
- **protected:** Accesible en la clase y subclases
- **public:** Accesible desde cualquier lugar

### ✅ **Organización del Código**

1. Atributos de la clase
2. Constructor
3. Métodos públicos
4. Métodos privados
5. Método main (si es la clase principal)

---

## 💡 **CONSEJOS PARA PRINCIPIANTES**

### 🎯 **Errores Comunes a Evitar**

1. **No cerrar Scanner:**
   ```java
   Scanner teclado = new Scanner(System.in);
   // ... uso del scanner
   teclado.close();  // Buena práctica
   ```

2. **Confundir = con ==:**
   ```java
   if (x = 5)   // ❌ Asignación
   if (x == 5)  // ✅ Comparación
   ```

3. **Array fuera de límites:**
   ```java
   int[] array = new int[5];
   array[5] = 10;  // ❌ Error: índice 5 no existe (0-4)
   array[4] = 10;  // ✅ Correcto
   ```

4. **No inicializar variables:**
   ```java
   int suma;
   suma = suma + 10;  // ❌ Error: suma no inicializada
   
   int suma = 0;
   suma = suma + 10;  // ✅ Correcto
   ```

### 🚀 **Tips para Aprender**

1. **Practica constantemente:** Código todos los días
2. **Lee código de otros:** Aprende de ejemplos
3. **Comenta tu código:** Explica qué hace cada parte
4. **Usa el debugger:** Aprende a depurar en Eclipse
5. **Divide problemas:** Resuelve por partes
6. **Prueba con casos extremos:** Valores límite, negativos, cero

---

## 🔧 **ATAJOS ÚTILES EN ECLIPSE**

| Atajo | Función |
|-------|---------|
| **Ctrl + Space** | Auto-completar código |
| **Ctrl + Shift + F** | Formatear código |
| **Ctrl + /** | Comentar/descomentar línea |
| **Ctrl + S** | Guardar |
| **Ctrl + F11** | Ejecutar programa |
| **F3** | Ir a definición |
| **Ctrl + Shift + O** | Organizar imports |
| **Alt + Shift + R** | Renombrar variable |

---

## 🎯 **CONCEPTOS CLAVE PARA RECORDAR**

### Java Básico

- **Tipado estático:** Declarar tipo de variable
- **Case-sensitive:** Diferencia mayúsculas/minúsculas
- **Punto y coma:** Obligatorio al final de sentencias
- **Bloques:** Delimitados por llaves { }

### POO en Java

- **Encapsulación:** Atributos privados, métodos públicos
- **Abstracción:** Ocultar complejidad interna
- **Herencia:** Reutilizar código de clase padre
- **Polimorfismo:** Mismo método, diferentes comportamientos

### Estructuras de Datos

- **Arrays:** Tamaño fijo, índice base 0
- **Strings:** Inmutables, tipo referencia
- **Scanner:** Para entrada de datos
- **System.out:** Para salida de datos

---

## 📝 **EJERCICIOS PROPUESTOS**

### Nivel Básico

1. Calcular el área y perímetro de un rectángulo
2. Determinar si un número es par o impar
3. Calcular el factorial de un número
4. Encontrar el mayor de 3 números

### Nivel Intermedio

5. Invertir un array de enteros
6. Calcular promedio de notas y determinar aprobados
7. Buscar un elemento en un array
8. Ordenar un array (método burbuja)

### Nivel Avanzado

9. Crear clase Cuenta Bancaria con depósito y retiro
10. Sistema de registro de estudiantes con herencia
11. Calculadora con múltiples operaciones
12. Matriz transpuesta

---

## 🎓 **CONCLUSIÓN**

Java es un lenguaje robusto, multiplataforma y orientado a objetos que:

- **Facilita el desarrollo:** Con su amplia API y herramientas
- **Promueve buenas prácticas:** Con POO y manejo de excepciones
- **Es ampliamente usado:** En aplicaciones empresariales, Android, web
- **Tiene gran comunidad:** Abundante documentación y recursos

### Próximos Pasos

1. **Dominar lo básico:** Estructuras de control y POO
2. **Practicar constantemente:** Resolver ejercicios
3. **Explorar APIs:** Collections, IO, Networking
4. **Aprender concurrencia:** Threads y sincronización
5. **Desarrollar proyectos:** Aplicar conocimientos

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*  
*Prof. Ing. José Farfán*

---