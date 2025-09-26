# 📚 CLASE 1 - PROGRAMACIÓN ORIENTADA A OBJETOS (POO)

## 🎯 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**📖 Tema:** Paradigma Orientado a Objetos  

### 👨‍🏫 **EQUIPO DOCENTE**
- **Prof. Adj. Esp. Ing. José Farfán**
- **JTP: Mg. Nélida Cáceres - Dr. Federico Medrano**  
- **Ay: Lic. Felipe Mullicundo**

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [🏗️ Paradigma Orientado a Objetos](#paradigma-orientado-a-objetos)
2. [🔧 Estructura de un Objeto](#estructura-de-un-objeto)
3. [📋 Definiciones Fundamentales](#definiciones-fundamentales)
4. [🎯 Razones de Aparición del POO](#razones-de-aparición-del-poo)
5. [⭐ Características Básicas](#características-básicas)
6. [🔗 Tipos de Relaciones](#tipos-de-relaciones)
7. [🌳 Relaciones Jerárquicas](#relaciones-jerárquicas)
8. [💡 Ejemplos Prácticos](#ejemplos-prácticos)

---

## 🏗️ **PARADIGMA ORIENTADO A OBJETOS**

### 📝 **Definición Fundamental**

> **POO:** Metodología de desarrollo de aplicaciones en la cual éstas se organizan como **colecciones cooperativas de objetos**, cada uno de los cuales representan una **instancia** de alguna **clase**, y cuyas clases son miembros de **jerarquías de clases** unidas mediante **relaciones de herencia**.
> 
> *— Grady Booch*

### 🎯 **Conceptos Clave del Diagrama POO**

```
                    🔴 Origen y
                      Conceptos
                         |
    🟠 Características ←  🔵 POO  → 🟢 Objeto
                         |
                    🟣 Clase ← 🔵 UML
```

**Elementos Fundamentales:**
- **🔴 Origen y Conceptos:** Fundamentos teóricos
- **🟠 Características:** Propiedades del paradigma
- **🔵 POO:** Paradigma central
- **🟢 Objeto:** Instancias concretas
- **🟣 Clase:** Moldes o plantillas
- **🔵 UML:** Lenguaje de modelado

---

## 🎯 **RAZONES DE APARICIÓN DEL POO**

### ❌ **Problemas del Paradigma Anterior**

#### 🚫 **1. Falta de Portabilidad del Código y Reusabilidad**
- Código difícil de reutilizar en diferentes proyectos
- Dependencias específicas del sistema
- Falta de modularidad

#### 🚫 **2. Código difícil de modificar**
- Cambios requieren modificaciones extensas
- Alto acoplamiento entre componentes
- Efectos secundarios impredecibles

#### 🚫 **3. Ciclos de Desarrollo largos**
- Tiempo excesivo desde diseño hasta implementación
- Dificultad para iterar rápidamente
- Procesos de testing complejos

#### 🚫 **4. Técnicas de Codificación NO Intuitivas**
- Paradigmas alejados del pensamiento natural
- Curva de aprendizaje pronunciada
- Código difícil de entender y mantener

### ✅ **CARACTERÍSTICAS BÁSICAS DEL POO**

#### 🏗️ **1. Basado en Objetos**
- La unidad fundamental es el **objeto**
- Encapsulación de datos y comportamiento
- Abstracción del mundo real

#### 🏛️ **2. Basado en Clases**
- Las clases definen la estructura común
- Los objetos son instancias de clases
- Plantillas reutilizables

#### 🧬 **3. Capaz de tener HERENCIA de clases**
- Reutilización de código existente
- Jerarquías de especialización
- Extensión de funcionalidad

---

## 📋 **DEFINICIONES FUNDAMENTALES**

### 🏛️ **Clase**
> **Definición:** Conjunto complejo de datos y programas que poseen estructura y forman parte de una organización.
> 
> *— Cruz del Valle*

### 🎯 **Objeto**
> **Definición:** Conjunto complejo de datos y programas que poseen estructura y forman parte de una organización.
> 
> *— Cruz del Valle*

### 📊 **Relación Clase-Objeto**

```
┌─────────────┐
│    CLASE    │ ──────► Molde/Plantilla
└─────────────┘
       │
       │ instancia
       ▼
┌─────────────┐
│   OBJETO    │ ──────► Instancia específica
│   OBJETO    │
│   OBJETO    │
└─────────────┘
```

**Ejemplo Visual con Los Simpson:**
- **Clase:** Persona (molde general)
- **Objetos:** Homer, Bart, Lisa, Marge (instancias específicas)

---

## 🔧 **ESTRUCTURA DE UN OBJETO**

### 📋 **Partes de un OBJETO**

Un objeto está compuesto por **tres componentes fundamentales**:

#### 🔗 **1. Relaciones**
- **Función:** Permiten que el objeto se inserte en la organización
- **Implementación:** Están formadas por punteros a otros objetos
- **Propósito:** Establecer conexiones y dependencias

#### 🏷️ **2. Propiedades**
- **Definición:** Todo objeto puede tener cierto número de propiedades
- **Estructura:** Cada una de las cuales tendrá, a su vez, 1 o varios valores
- **Tipos de valores:**
  - **Matrices**
  - **Vectores** 
  - **Listas**
  - **Etc.**
- **Tipos de datos:**
  - **Numérico**
  - **Alfabético**
  - **Etc.**

#### ⚙️ **3. Métodos**
- **Definición:** Son las operaciones que pueden realizarse sobre el objeto
- **Implementación:** Normalmente están incorporados en forma de **programas (código)**
- **Capacidades:** El objeto es capaz de ejecutar
- **Herencia:** También pone a disposición de sus descendientes a través de la **herencia**

### 📐 **Diagrama de Estructura**

```
        ┌─────────────────────────┐
        │        OBJETO           │
        └─────────────────────────┘
                    │
        ┌───────────┼───────────┐
        │           │           │
        ▼           ▼           ▼
┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│ RELACIONES  │ │PROPIEDADES  │ │   MÉTODOS   │
│             │ │             │ │             │
│ • Punteros  │ │ • Matrices  │ │ • Programas │
│ • Enlaces   │ │ • Vectores  │ │ • Código    │
│ • Conexiones│ │ • Listas    │ │ • Funciones │
└─────────────┘ └─────────────┘ └─────────────┘
```

---

## 🔗 **TIPOS DE RELACIONES**

### 🌳 **RELACIONES JERÁRQUICAS**

#### 📝 **Definición**
Son **esenciales** para la existencia misma de la aplicación que la construyen. Son **bidireccionales**, es decir, 1 objeto es padre de otro cuando el 1er objeto se encuentra situado inmediatamente encima del 2do en la organización en la que ambos forman parte. Si un objeto es padre de otro, el 2do es **hijo** del 1ro.

#### 🏗️ **Tipos de Organización Jerárquica**

##### 📊 **1. Organización Jerárquica Simple**
**Característica:** 1 hijo tiene **1 solo padre**

```
                    ┌───┐
                    │ \ │  ← Raíz
                    └───┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
        ▼             ▼             ▼
      ┌───┐         ┌───┐         ┌───┐
      │ A │         │ B │         │ C │
      └───┘         └───┘         └───┘
        │             │
    ┌───┼───┐         │
    │   │   │         │
    ▼   ▼   ▼         ▼
  ┌───┐┌───┐┌───┐   ┌───┐
  │ D ││ E ││ F │   │   │
  └───┘└───┘└───┘   └───┘
```

**Características:**
- Estructura de árbol clásica
- Cada nodo tiene exactamente un padre
- Jerarquía clara y simple
- Fácil de navegar y entender

##### 🕸️ **2. Organización Jerárquica Compleja**
**Característica:** 1 hijo tiene **varios padres**

```
                    ┌───┐
                    │ \ │  ← Raíz
                    └───┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
        ▼             ▼             ▼
      ┌───┐         ┌───┐         ┌───┐
      │ A │         │ B │         │ C │
      └───┘         └───┘         └───┘
        │             │ ╲           │
        │             │  ╲          │
        │             │   ╲         │
        ▼             ▼    ╲        ▼
      ┌───┐         ┌───┐   ╲     ┌───┐
      │ D │         │ E │    ╲    │ F │
      └───┘         └───┘     ╲   └───┘
                              ╲    │
                               ╲   │
                                ╲  │
                                 ╲ ▼
                                  ╲───► Múltiples padres
```

**Características:**
- Estructura de grafo dirigido
- Un nodo puede tener múltiples padres
- Mayor flexibilidad en las relaciones
- Más complejo de gestionar
- Permite herencia múltiple

#### 🔄 **Comparación de Organizaciones**

| Aspecto | Jerárquica Simple | Jerárquica Compleja |
|---------|-------------------|---------------------|
| **Padres por hijo** | 1 solo padre | Varios padres |
| **Estructura** | Árbol | Grafo dirigido |
| **Complejidad** | Baja | Alta |
| **Navegación** | Fácil | Compleja |
| **Herencia** | Simple | Múltiple |
| **Mantenimiento** | Sencillo | Complejo |

---

## 💡 **EJEMPLOS PRÁCTICOS**

### 🏦 **Ejemplo 1: Sistema Bancario (Jerárquica Simple)**

```
┌─────────────────┐
│ CuentaBancaria  │ ← Clase padre
└─────────────────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌─────────┐ ┌─────────────┐
│ Cuenta  │ │ Cuenta      │
│ Corriente│ │ Sueldo      │
└─────────┘ └─────────────┘
```

**Implementación:**
```java
// Clase padre
public class CuentaBancaria {
    protected String numeroCuenta;
    protected double saldo;
    
    public void depositar(double monto) { /* ... */ }
    public boolean retirar(double monto) { /* ... */ }
}

// Clase hija - Herencia simple
public class CuentaSueldo extends CuentaBancaria {
    private double topeTransferencia;
    
    public boolean transferir(double monto, String cbu) { /* ... */ }
}
```

### 🎓 **Ejemplo 2: Sistema Educativo (Jerárquica Compleja)**

```
┌─────────────┐    ┌─────────────┐
│  Empleado   │    │  Estudiante │
└─────────────┘    └─────────────┘
       │                  │
       └────────┬─────────┘
                │
                ▼
        ┌─────────────┐
        │ Estudiante  │ ← Herencia múltiple
        │ Trabajador  │
        └─────────────┘
```

**Implementación:**
```java
// Interfaces para herencia múltiple
interface Empleado {
    void trabajar();
    double getSalario();
}

interface Estudiante {
    void estudiar();
    double getPromedio();
}

// Clase con herencia múltiple
public class EstudianteTrabajador implements Empleado, Estudiante {
    // Implementa ambas interfaces
    public void trabajar() { /* ... */ }
    public void estudiar() { /* ... */ }
}
```

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Puntos Fundamentales**

1. **🏗️ POO = Objetos + Clases + Herencia + Relaciones**
2. **🎯 Objeto = Relaciones + Propiedades + Métodos**
3. **🏛️ Clase = Molde para crear objetos**
4. **🧬 Herencia = Reutilización y extensión**
5. **🔗 Relaciones = Conexiones entre objetos**

### 🌟 **Beneficios del POO**

- ✅ **Reutilización** de código
- ✅ **Mantenibilidad** mejorada  
- ✅ **Modularidad** y organización
- ✅ **Abstracción** de complejidad
- ✅ **Polimorfismo** y flexibilidad
- ✅ **Encapsulación** de datos

### 🎯 **Tipos de Relaciones**

- **🌳 Jerárquicas Simples:** Un padre por hijo
- **🕸️ Jerárquicas Complejas:** Múltiples padres por hijo
- **🔗 Bidireccionales:** Padre ↔ Hijo
- **📊 Esenciales:** Para la existencia de la aplicación

---

## 📚 **PRÓXIMOS TEMAS**

En las siguientes clases profundizaremos en:
- **🔒 Encapsulación** y modificadores de acceso
- **🎭 Polimorfismo** y sobrecarga de métodos
- **📋 Interfaces** y clases abstractas
- **🏗️ Composición** vs Herencia
- **🎨 Patrones de diseño** orientados a objetos
- **📐 UML** y diagramas de clases

---

## 📝 **NOTAS ADICIONALES**

> **💡 Tip:** La comprensión de las relaciones jerárquicas es fundamental para diseñar sistemas POO efectivos. La elección entre jerarquías simples o complejas depende de los requisitos específicos del sistema.

> **⚠️ Importante:** En Java, la herencia múltiple de clases no está permitida, pero se puede simular usando interfaces, como se muestra en los ejemplos.

---

---

## 🔗 **CLASIFICACIÓN DE LAS RELACIONES (CONTINUACIÓN)**

### 🧠 **RELACIONES SEMÁNTICAS**

#### 📝 **Definición**
**NO** tienen que ver con la organización de la que forman parte los objetos que las establecen. Sus propiedades **solo dependen de los objetos en sí mismos** (de su significado) y **no de su posición** en la organización.

#### 🌍 **Ejemplo: Diccionario Informatizado**
Permite al usuario obtener la definición de 1 palabra. Las palabras son objetos y la organización jerárquica es la que proviene de forma natural de la estructura del conocimiento sobre el mundo.

#### 🌳 **Jerarquía de Conocimiento - TEMAS**

La raíz se llama **TEMAS**. De este término genérico descienden **3 grandes ramas** de objetos llamadas:

```
                    ┌─────────┐
                    │  TEMAS  │ ← Raíz del conocimiento
                    └─────────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
   ┌─────────┐      ┌─────────┐      ┌─────────┐
   │  VIDA   │      │  MUNDO  │      │ HOMBRE  │
   └─────────┘      └─────────┘      └─────────┘
        │                │                │
   ┌────┴────┐      ┌────┴────┐      ┌────┴────┐
   │         │      │         │      │         │
   ▼         ▼      ▼         ▼      ▼         ▼
┌─────┐  ┌─────┐ ┌─────┐  ┌─────┐ ┌─────┐  ┌─────┐
│ BIO │  │ MED │ │ MAT │  │ FIS │ │ GEOG │  │ HIST │
└─────┘  └─────┘ └─────┘  └─────┘ └─────┘  └─────┘
```

**Descripción de las Ramas:**

- **🧬 VIDA:** Comprende las Ciencias biológicas
  - **BIO:** Biología
  - **MED:** Medicina

- **🌍 MUNDO:** Comprende las Ciencias de la naturaleza inerte
  - **MAT:** Matemática
  - **FIS:** Física
  - **QUIM:** Química (implícita)
  - **GEOL:** Geología (implícita)

- **👥 HOMBRE:** Comprende las Ciencias humanas
  - **GEOG:** Geografía
  - **HIST:** Historia
  - **etc.**

#### 🔬 **Ejemplo de Relación Semántica: NEWTON y ÓPTICA**

Se establece la **relación trabajo** entre los objetos **NEWTON** y **ÓPTICA**, la cual significa que **Newton trabajó en óptica**. Esta relación es **semántica**, pues **no hay connotación jerárquica** entre NEWTON y ÓPTICA y su interpretación **depende del significado** de ambos objetos.

```
┌─────────┐    trabajo    ┌─────────┐
│ NEWTON  │ ──────────► │ ÓPTICA  │
└─────────┘              └─────────┘
```

---

## 🎯 **RELACIÓN DE-LA-ESPECIE**

### 📋 **Definición y Uso**
Se usa **al nivel de clase** para describir las relaciones entre **dos clases similares**.

### 💻 **Ejemplo: Programa de Dibujo**
Permite el dibujo de variados objetos tales como **puntos**, **rectángulos**, **triángulos** y muchos más. Por cada objeto, se provee una **definición de clase**.

#### 📐 **Clase Point (Punto)**
```java
class Point {
    // Atributos:
    int x, y
    
    // Métodos:
    setX(int newX)
    getX()
    setY(int newY)
    getY()
}
```

#### ⭕ **Clase Circle (Círculo)**
```java
class Circle {
    // Atributos:
    int x, y,
    radius
    
    // Métodos:
    setX(int newX)
    getX()
    setY(int newY)
    getY()
    setRadius(newRadius)
    getRadius()
}
```

### 🔍 **Análisis de la Relación**

#### 🎯 **Elementos Comunes**
- **Ambas clases tienen 2 elementos de datos x e y**
  - En la clase **Point**: estos elementos describen la **posición del punto**
  - En el caso de la clase **Circle**: describen el **centro del círculo**
  - Así, **x e y tienen el mismo significado** en ambas clases: **Describen la posición de su objeto asociado** por medio de la definición de 1 punto

#### ⚙️ **Métodos Compartidos**
- **Ambas clases ofrecen el mismo conjunto de métodos** para **obtener y definir** el valor de los 2 elementos x e y

#### ➕ **Extensión**
- **La clase Circle "añade" un nuevo elemento radius** y sus correspondientes **métodos de acceso**

#### 🎯 **Resultado**
Con las propiedades de la clase **Point** se describe **1 círculo** como:
**1 punto + 1 radio + métodos para accederlo**

### 📊 **Representación UML**

```
┌─────────────────┐     de-la-especie     ┌─────────────────┐
│     Circle      │ ──────────────────► │     Point       │
│   (círculo)     │                      │    (punto)      │
└─────────────────┘                      └─────────────────┘
```

**Interpretación:** Las clases se dibujan usando **rectángulos**. Su nombre empieza con **letra mayúscula**. Las flechas indican la **dirección de la relación**, de ahí que se deba leer como **"Circle es de-la-especie Point"**.

---

## 🎯 **RELACIÓN ES-UN(A)**

### 📝 **Definición**
Cuando se crean objetos de clases de la relación **"De-La-Especie"**, la relación se llama **"es-un(a)"**.

### 🔄 **Transformación de Relación**
```
Nivel de Clase:    Circle ──de-la-especie──► Point
                      ↓ instanciación        ↓
Nivel de Objeto:   círculo ──────es-un(a)────► point
```

### 💡 **Ejemplo Práctico**

#### 🎯 **Comportamiento de Objetos**
- **La clase Circle es de la especie de la clase Point**
- **1 instancia de Circle como círculo, es 1 point**
- **Cada círculo se comporta como 1 punto**, se puede mover puntos en la dirección **x** al alterar el valor de **x**
- **Similarmente, se mueven círculos en ésta dirección** al alterar su valor de **x**

### 📐 **Representación UML de Objetos**

```
┌─────────────────┐     es-un(a)     ┌─────────────────┐
│     circle      │ ──────────────► │     point       │
└─────────────────┘                  └─────────────────┘
```

**Nota:** Los objetos se usan **rectángulos con esquinas redondeadas**. Su nombre consta de **letras minúsculas**.

---

## 🧩 **RELACIÓN PARTE-DE**

### 📝 **Definición**
A veces se necesita construir objetos haciendo **1 combinación de otros**. Esto se hace por la **programación procedimiento**, donde se tiene la **estructura o registro** para juntar **variados tipos de datos**.

### 🎨 **Ejemplo: Programa de Dibujo - Logotipo**

En el programa de dibujo se quiere tener **1 figura especial** que represente **1 logotipo propio** que consiste en **1 círculo y 1 triángulo**. Se asume que se tiene definida una clase **Triangle**. El logo consta en **2 partes** o el **círculo** y el **triángulo** son **parte-de** logotipo:

#### 🏗️ **Clase Logo**
```java
class Logo {
    // Atributos:
    Circle circle
    Triangle triangle
    
    // Métodos:
    set(Point where)
}
```

### 📊 **Representación UML - Relación Parte-De**

```
┌─────────────┐    parte-de    ┌─────────────┐    parte-de    ┌─────────────┐
│   Circle    │ ──────────────► │    Logo     │ ◄────────────── │  Triangle   │
│  (círculo)  │                 │ (logotipo)  │                 │ (triángulo) │
└─────────────┘                 └─────────────┘                 └─────────────┘
```

### 🔍 **Características de la Relación Parte-De**

#### 🎯 **Composición**
- **El objeto Logo está compuesto** por un Circle y un Triangle
- **Ambos objetos forman parte** del Logo
- **La relación es bidireccional** en términos de dependencia

#### 🏗️ **Agregación vs Composición**
- **Agregación:** "tiene-un" - relación más débil
- **Composición:** "parte-de" - relación más fuerte
- **En este caso:** Circle y Triangle son **partes esenciales** del Logo

#### 💻 **Implementación en Java**
```java
public class Logo {
    private Circle circle;      // Parte del logo
    private Triangle triangle;  // Parte del logo
    
    public Logo() {
        this.circle = new Circle();
        this.triangle = new Triangle();
    }
    
    public void set(Point where) {
        // Posicionar ambas partes del logo
        circle.setPosition(where);
        triangle.setPosition(where);
    }
    
    public void draw() {
        circle.draw();
        triangle.draw();
    }
}
```

---

## 📊 **RESUMEN DE TIPOS DE RELACIONES**

### 🔗 **Clasificación Completa**

| Tipo de Relación | Nivel | Descripción | Ejemplo | Notación UML |
|------------------|-------|-------------|---------|---------------|
| **Jerárquica Simple** | Organización | 1 padre por hijo | Árbol de clases | Líneas con jerarquía |
| **Jerárquica Compleja** | Organización | Múltiples padres | Herencia múltiple | Grafo dirigido |
| **Semántica** | Significado | Basada en el significado | Newton-Óptica | Línea etiquetada |
| **De-La-Especie** | Clase | Entre clases similares | Circle-Point | Flecha con etiqueta |
| **Es-Un(a)** | Objeto | Instancias de de-la-especie | círculo-point | Flecha entre objetos |
| **Parte-De** | Composición | Objetos compuestos | Logo(Circle+Triangle) | Línea con diamante |

### 🎯 **Conceptos Clave**

#### ✅ **Relaciones Fundamentales**
1. **🌳 Jerárquicas:** Estructura organizacional
2. **🧠 Semánticas:** Basadas en significado
3. **🎯 De-la-especie:** Herencia entre clases
4. **🔄 Es-un(a):** Instanciación de herencia
5. **🧩 Parte-de:** Composición de objetos

#### 🔍 **Diferencias Importantes**
- **Herencia (es-un):** "Circle **es un** Point"
- **Composición (tiene-un):** "Logo **tiene un** Circle"
- **Agregación (parte-de):** "Circle **es parte de** Logo"

---

## 🎓 **CONCEPTOS AVANZADOS PARA RECORDAR**

### 💡 **Principios de Diseño**

1. **🎯 Favor composición sobre herencia**
   - La relación **parte-de** es más flexible
   - Evita jerarquías complejas
   - Facilita el mantenimiento

2. **🔍 Analizar el dominio del problema**
   - **Relaciones semánticas** reflejan el mundo real
   - **Jerarquías** organizan la estructura
   - **Composición** modela objetos complejos

3. **📐 UML como herramienta**
   - **Rectángulos** para clases
   - **Rectángulos redondeados** para objetos
   - **Flechas** indican dirección de relaciones

### 🚀 **Aplicación Práctica**

#### 🏦 **Sistema Bancario Extendido**
```java
// Relación de-la-especie / es-un(a)
public class CuentaSueldo extends CuentaBancaria {
    // CuentaSueldo es-una CuentaBancaria
}

// Relación parte-de
public class Cliente {
    private List<CuentaBancaria> cuentas; // Cliente tiene cuentas
    private Persona datosPersonales;      // Cliente tiene datos
}

// Relación semántica
public class Transaccion {
    private CuentaBancaria origen;
    private CuentaBancaria destino;
    // Relación "transferencia" entre cuentas
}
```

---

## 🔄 **RELACIÓN TIENE-UN(A)**

### 📝 **Definición**
Esta relación es la **inversa de la relación parte-de**. Por lo tanto, se puede añadir esta relación a la ilustración parte-de **añadiendo flechas en la otra dirección**.

### 📊 **Representación UML - Relación Bidireccional**

```
┌─────────────┐    parte-de    ┌─────────────┐    parte-de    ┌─────────────┐
│   Circle    │ ──────────────► │    Logo     │ ◄────────────── │  Triangle   │
│             │ ◄────────────── │             │ ──────────────► │             │
└─────────────┘   tiene-un(a)  └─────────────┘   tiene-un(a)  └─────────────┘
```

### 🔍 **Interpretación de la Relación**

#### 📊 **Desde la perspectiva del Logo:**
- **Logo tiene-un Circle** (Logo contiene un círculo)
- **Logo tiene-un Triangle** (Logo contiene un triángulo)

#### 📊 **Desde la perspectiva de las partes:**
- **Circle es parte-de Logo** (El círculo forma parte del logo)
- **Triangle es parte-de Logo** (El triángulo forma parte del logo)

### 💻 **Implementación en Java**
```java
public class Logo {
    // Logo TIENE-UN Circle
    private Circle circle;
    
    // Logo TIENE-UN Triangle  
    private Triangle triangle;
    
    public Logo() {
        this.circle = new Circle();    // Logo crea y posee un círculo
        this.triangle = new Triangle(); // Logo crea y posee un triángulo
    }
    
    // Métodos que demuestran la relación "tiene-un"
    public Circle getCircle() { return circle; }
    public Triangle getTriangle() { return triangle; }
}
```

---

## 🏷️ **PROPIEDADES**

### 📝 **Definición**
Se diferencian con las **"variables"** que se pueden **heredar de unos objetos a otros**. Pueden ser:

### 🔄 **Clasificación de Propiedades**

#### 🏗️ **PROPIEDADES PROPIAS**
- **Definición:** Están formadas **dentro de la cápsula del objeto**
- **Características:**
  - Pertenecen exclusivamente al objeto
  - Se definen en la clase del objeto
  - No dependen de otros objetos
  - Encapsuladas dentro del objeto

#### 🧬 **PROPIEDADES HEREDADAS**
- **Definición:** Están definidas en **1 objeto diferente, antepasado de éste** (padre, "abuelo", etc.)
- **Características:**
  - Provienen de clases padre o ancestros
  - Se obtienen por herencia
  - A veces se las llaman **propiedad miembro** que el objeto las posee por el solo hecho de ser miembro de 1 clase

### 🔍 **Distinción y Organización**

Las propiedades **distinguen 1 objeto de los restantes** que forman parte de la misma organización y tiene valores que **dependen de la propiedad** que se trate. Las propiedades de 1 objeto pueden ser **heredadas a sus descendientes** en la organización.

---

## ⚙️ **MÉTODOS**

### 📝 **Definición**
Son las **operaciones que realizan acceso a los datos**. Se pueden definir como **1 programa procedimiento** escrito en cualquier lenguaje, que está asociado a **1 objeto determinado** y cuya ejecución **sólo puede desencadenarse** a través de **1 mensaje recibido** por éste o sus descendientes.

### 🔄 **Diferencia con Paradigma Procedural**
En el **Paradigma Procedural** se los conoce como **programas, procedimientos, función, rutina, etc.**, en **OOP** se usa el término **método**. La forma de invocarlo, a través de **1 mensaje y a su campo de acción**, limitado a **un objeto y a sus descendientes**.

### 🔄 **Clasificación de Métodos**

Pueden **heredarse**, por lo tanto se clasifican en:

#### 🏗️ **Métodos Propios**
- **Definición:** Están incluidos **dentro de la cápsula del objeto**
- **Características:**
  - Definidos en la clase del objeto
  - Acceso directo a propiedades propias
  - Comportamiento específico del objeto

#### 🧬 **Métodos Heredados**
- **Definición:** Están definidos en **1 objeto diferente, antepasado de éste** (padre, abuelo, etc.)
- **Características:**
  - Provienen de clases ancestros
  - A veces estos métodos se los llaman **métodos miembros** que el objeto los posee por el solo hecho de ser miembro de 1 clase

---

## 🎯 **OBJETOS - CARACTERÍSTICAS**

### 📋 **Características Fundamentales**

#### 🏗️ **1. Se agrupan en grupos denominados clases**
- Los objetos con características similares forman una clase
- La clase define la estructura común
- Todos los objetos de una clase comparten el mismo "molde"

#### 📊 **2. Contienen datos internos que definen su estado actual**
- Cada objeto mantiene su propio estado
- Los datos internos representan las propiedades del objeto
- El estado puede cambiar durante la ejecución

#### 🔒 **3. Soportan ocultamiento de datos**
- **Encapsulación:** Los datos internos están protegidos
- **Acceso controlado:** Solo a través de métodos públicos
- **Abstracción:** Se ocultan los detalles de implementación

#### 🧬 **4. Pueden heredar propiedades de otros objetos**
- **Herencia:** Obtienen características de clases padre
- **Reutilización:** No necesitan redefinir propiedades existentes
- **Especialización:** Pueden añadir nuevas características

#### 📨 **5. Pueden comunicarse con otros objetos enviando o pasando mensajes**
- **Interacción:** Los objetos colaboran entre sí
- **Mensajes:** Forma de invocar métodos en otros objetos
- **Colaboración:** Trabajo conjunto para resolver problemas

#### ⚙️ **6. Tienen métodos que definen su comportamiento**
- **Comportamiento:** Definido por los métodos del objeto
- **Operaciones:** Acciones que el objeto puede realizar
- **Responsabilidades:** Lo que el objeto "sabe hacer"

### 📝 **Definición Completa**

> **Es 1 entidad lógica que contiene datos y un código especial que indica manipular los datos.**

### 🔄 **Proceso de Ejecución**

> **En el momento de la ejecución se puede degradar el diseño del programa.**

### 🏗️ **Construcción de Programas**

> **Son construcciones de programas que se obtienen a partir de entidades llamadas clases.** El programador tiene la responsabilidad de crear **clases propias**, pero **también puede tener acceso a clases desarrolladas por otros**.

---

## 🏛️ **CLASE - CONCEPTO DE UNA CLASE**

### 📝 **Definición Fundamental**

**Cada objeto es 1 ejemplar de 1 clase a la que pertenece.** Todos los ejemplares de la misma clase **tienen el mismo comportamiento** (invocan al mismo método) como respuesta a una solicitud.

### 📊 **Estructura de una Clase**

**1 clase es 1 tipo especial de datos** orientado a la creación de objetos que consta de **miembros**. **1 clase es 1 tipo de dato que contiene 1 o + elementos** llamados **datos miembros**, y **cero, uno o más funciones** que manipulan esos datos llamados **función miembro**. **1 clase se puede definir** con **1 estructura (struct), 1 unión (union) o 1 clase (class)**.

### 💻 **Sintaxis de una Clase**

```java
class nombre_clase {
    miembro_1;        //lista de datos miembros
    miembro_2
    miembro_3
    funcion_miembro_1( );    // funciones
    miembro conocidas
    funcion_miembro_2 ( );   // funciones
    como métodos
}
```

### 📊 **Diagrama Clase-Objeto**

```
                    ┌─────────────┐
                    │    Clase A    │ ← Molde/Plantilla
                    └─────────────┘
                           │
        ┌────────────────┼────────────────┐
        │               │               │
        ▼               ▼               ▼
   ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
   │   Objeto 1   │ │   Objeto 2   │ │   Objeto n   │
   └─────────────┘ └─────────────┘ └─────────────┘
```

### 📊 **Ejemplo: Clase Nómina de Personal**

```
┌───────────────────────────────────────────────────┐
│                 Clase Nómina de Personal                 │
├───────────────────────────────────────────────────┤
│ Nombre                    │ Salario                   │
├───────────────────────────┼─────────────────────────┤
│ Empleado 1                │                           │
├───────────────────────────┼─────────────────────────┤
│ Empleado 2                │                           │
├───────────────────────────┼─────────────────────────┤
│ .........                 │                           │
├───────────────────────────┼─────────────────────────┤
│ Empleado n                │                           │
└───────────────────────────┴─────────────────────────┘
                            │
                            ▼
                        Objetos
```

### 💻 **Ejemplo en Java**
```java
// Ejemplo: diseño de un Objeto.
class nomina { // (nomina empleado)
    char nombre[30];
    float salario;
}; // (nomina es una clase)
   // (empleado es un objeto)
```

---

## 🏷️ **CLASE - IDENTIFICADORES DE DISEÑO DE UNA CLASE**

### 📝 **Declaración de una Clase**

**Para usar una clase, 1ro hay que declararla.** La **declaración de una clase puede aparecer sólo 1 vez en un programa**.

#### 💻 **Ejemplo: Declaración de una clase simple**
```java
class counter {
    long count;  // Variable miembro de la clase
    public:
        void SetCount(long);
        long GetValue( ); 
}
```

### 🔑 **Elementos Clave de la Declaración**

#### 🏷️ **1. Palabra Clave `class`**
- La palabra clave **`class` introduce 1 declaración de clase**
- Es obligatoria para definir una nueva clase

#### 📝 **2. Nombre de la Clase**
- **Después aparece el nombre de la clase**
- Debe ser un identificador válido
- Sigue las convenciones de nomenclatura

#### 📊 **3. Contenido de la Clase**
- **Las clases contienen no sólo declaraciones de variables, sino también definiciones de funciones**
- **Las funciones contenidas en clases pueden ser tan largas y complejas como uno desee**

#### 🎯 **4. Variables de Clase**
- **Las variables declaradas en 1 clase pertenecen a esa clase**
- **Las variables pueden compartirse entre las diferentes instancias de una clase**

#### 🔒 **5. Identificadores Únicos**
- **Los identificadores de variables y funciones contenidos en 1 clase no coinciden con los identificadores que se usan en otras**
- **1 clase es un mundo con identificadores propios únicos**

---

## 🏗️ **CLASE - CUERPO DE UNA CLASE**

### 📝 **Definición del Cuerpo**

En el siguiente ejemplo, **la variable `count` se define dentro del cuerpo de la clase**. Por lo tanto, **`count` recibe el nombre de variable miembro de la clase**.

#### 💻 **Ejemplo: Cuerpo de Clase**
```java
class counter {
    long count;  // Variable miembro definida en el cuerpo
    public:
        count = 3; // ¡se genera un error ya que count no se encuentra definido!
}
```

### ⚠️ **Campo de Acción de Variables**

**Cualquier variable definida en 1 clase tiene 1 campo de acción.** Se produce **1 error al intentar el acceso a 1 variable miembro después de la declaración de la clase o fuera del campo de acción de la clase**.

#### 🔴 **Problema de Acceso**
- **Variables definidas dentro de la clase** tienen un ámbito específico
- **No se puede acceder** a variables miembro fuera del contexto apropiado
- **Requiere métodos públicos** para acceso controlado

---

## 🚀 **CLASE - USO DE UNA CLASE**

### 📝 **Definición de Objetos**

**Para usar 1 clase se debe definir 1 objeto con ella.** Las **variables de 1 clase se definen como variables de tipo estructura o escalares**.

#### 💻 **Ejemplo: Definir variable de clase**
```java
Counter people  // Definir la variable de la clase people de tipo counter
```

### 🎯 **Concepto Fundamental**

> **"Las variables instanciadas a partir de clases son los objetos".** Es casi imposible usar 1 clase directamente.

### 💻 **Ejemplo de Uso Completo**

#### 📑 **Para el objeto `people`, esta es la forma en que se podría usar:**
```java
void main ( )
{
    counter people;
    // Inicializar el objeto people.
    SetValue(0);
    // Verificar que se borre
    long value = people.GetValue( );
}
```

### 🔑 **Características Importantes**

#### 🏗️ **Tipo Especial de Datos**
- **Una clase es un tipo especial de datos**
- **Está orientada a la creación de objetos**
- **Consta de miembros** que pueden ser datos o funciones

#### 🔒 **Modificadores de Acceso**
- **Pueden ser datos o funciones privadas o públicas**
- **Controlan la visibilidad** de los miembros
- **Implementan encapsulación** de datos

---

## 🏗️ **CLASE - COMPONENTES DE UNA CLASE**

### 📝 **Estructura de Definición**

**Para definir 1 clase se debe tomar en cuenta que consta de 2 partes:**
1. **1 declaración**
2. **1 implementación**

### 📋 **1. Declaración de una Clase**
- **La declaración lista los miembros de la clase**
- **Define la interfaz pública**
- **Especifica la estructura de datos**

### ⚙️ **2. Implementación o Cuerpo**
- **La implementación o cuerpo define las funciones de la clase**
- **Contiene el código ejecutable**
- **Implementa el comportamiento**

### 📊 **Ejemplo Visual de Componentes**

#### 📋 **Declaración de una clase**
```java
class nomina { // (nomina empleado)
    char nombre[30];
    float salario;
};  // (nomina es una clase)
    // (empleado es un objeto)
```

#### 🔄 **Separación Visual**
```
................................................
```

#### ⚙️ **Funciones miembro de la clase**
```java
class contador {
    long cuenta;
    public:
        void leervalor(long);
        long obtenervalor( );
};
```

#### 📊 **Implementación de una clase**
```java
void contador::leerValor(long valor)
{
    cuenta = valor;
}

long contador::obtenerValor( )
{
    return cuenta;
}
```

### 🔑 **Puntos Clave**

#### 🏗️ **Separación de Responsabilidades**
- **Declaración:** Define QUÉ hace la clase
- **Implementación:** Define CÓMO lo hace

#### 📊 **Organización del Código**
- **Facilita el mantenimiento**
- **Permite compilación separada**
- **Mejora la legibilidad**

---

## 🔒 **ENCAPSULAMIENTO Y OCULTACIÓN**

### 📝 **Encapsulamiento**

#### 🔑 **Definición**
**Propiedad por la que cada objeto es 1 estructura compleja en cuyo interior hay datos y programas relacionados entre sí, encerrados en 1 cápsula** (Característica Fundamental en la OOP).

### 🕵️ **Ocultación de la Información**

#### 📝 **Definición**
**Propiedad por la que los objetos son inaccesibles, e impiden que otros objetos, usuarios, o incluso los programadores conozcan cómo está distribuida la información o qué información hay disponible.** No significa que sea imposible conocer lo necesario a 1 objeto y a lo que contiene.

### 📨 **Comunicación por Mensajes**

#### 🔄 **Proceso de Comunicación**
**Las peticiones de información a 1 objeto, deben realizarse a través de mensajes dirigidos a él, con la orden de realizar la operación pertinente.** La respuesta a estas órdenes será la **información requerida, siempre que el objeto considere que quien envía el mensaje está autorizado para solicitarla**.

### 🎯 **Beneficios del Encapsulamiento**

#### 🚀 **Reutilización de Programas**
**El hecho de que cada objeto sea 1 cápsula facilita que 1 objeto determinado pueda ser transportado a otro punto de la organización, o incluso a otra organización totalmente diferente.** Si el objeto se **construyó correctamente, sus métodos seguirán funcionando en el nuevo entorno sin problemas**. Esta cualidad hace que **la OOP sea muy apta para la reutilización de programas**.

### 📊 **Diagrama Conceptual**

```
┌────────────────────────────────────────────────────────────┐
│                        OBJETO ENCAPSULADO                        │
├────────────────────────────────────────────────────────────┤
│                                                              │
│  🔒 DATOS PRIVADOS (Ocultos)                                │
│  • Variables internas                                         │
│  • Estado del objeto                                          │
│  • Información sensible                                      │
│                                                              │
│  ⚙️ MÉTODOS PRIVADOS (Ocultos)                               │
│  • Operaciones internas                                       │
│  • Lógica de implementación                                   │
│                                                              │
├────────────────────────────────────────────────────────────┤
│                     INTERFAZ PÚBLICA                          │
│                                                              │
│  📨 MÉTODOS PÚBLICOS (Accesibles)                           │
│  • Operaciones permitidas                                    │
│  • Comunicación por mensajes                                 │
│  • Servicios del objeto                                      │
│                                                              │
└────────────────────────────────────────────────────────────┘
                                │
                                ▼
                        📨 MENSAJES
                     (Comunicación Externa)
```

### 🌟 **Ventajas del Encapsulamiento y Ocultación**

#### ✅ **Seguridad**
- **Protección de datos** internos
- **Control de acceso** autorizado
- **Prevención de modificaciones** no deseadas

#### ✅ **Mantenibilidad**
- **Cambios internos** sin afectar el exterior
- **Evolución** de la implementación
- **Reducción de dependencias**

#### ✅ **Reutilización**
- **Objetos transportables** entre sistemas
- **Funcionamiento independiente** del entorno
- **Compatibilidad** con diferentes contextos

#### ✅ **Abstracción**
- **Interfaz simple** para operaciones complejas
- **Ocultación de complejidad** interna
- **Facilidad de uso** para otros programadores

---

## 🌳 **ORGANIZACIÓN JERÁRQUICA DE OBJETOS**

### 📝 **Definición**

**Los objetos forman siempre 1 organización jerárquica, ya que ciertos objetos son superiores a otros.**

### 🔄 **Tipos de Jerarquías**

Existen varios **tipos de jerarquías:**

#### 🌿 **SIMPLES**
- **Cuando su estructura pueda ser representada por medio de un "árbol"**
- Estructura lineal y clara
- Cada objeto tiene un solo padre

#### 🕸️ **COMPLEJOS** 
- **En otros casos puede ser más complejos**
- Estructuras de red o grafo
- Relaciones múltiples entre objetos

### 📊 **Niveles de la Jerarquía**

**En cualquier caso existen 3 niveles:**

#### 🏔️ **1. Raíz de la jerarquía**
- **Objeto único y especial**
- **Se caracteriza por estar en el nivel más alto de la estructura**
- **Se lo llama objeto madre, Raíz o Entidad**

#### 🌿 **2. Objetos intermedios**
- **Descienden directamente de la raíz y tienen descendientes**
- **Representan conjuntos o clases de objetos**
- **Pueden ser muy grales o muy especializados**
- **Reciben nombres genéricos que denotan al conjunto de objetos que representan**
- **Ejemplos:** VENTANA, CUENTA, FICHERO
- **En 1 conjunto reciben el nombre de clases o tipos si descienden de otra clase o subclase**

#### 🍃 **3. Objetos terminales**
- **Son aquellos que descienden de 1 clase o subclase y no tienen descendientes**
- **Suelen llamarse casos particulares, instancias o ítems**
- **Representan los elementos del conjunto representado por la clase o subclase a la que pertenecen**

### 📊 **Diagrama de Organización Jerárquica**

```
                    ┌─────────────────┐
                    │   RAÍZ/ENTIDAD  │ ← Nivel más alto
                    │ (Objeto Madre)  │
                    └─────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
   ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
   │   VENTANA   │    │   CUENTA    │    │  FICHERO    │ ← Objetos
   │  (Clase)    │    │  (Clase)    │    │  (Clase)    │   Intermedios
   └─────────────┘    └─────────────┘    └─────────────┘
        │                  │                  │
   ┌────┼────┐        ┌────┼────┐        ┌────┼────┐
   │    │    │        │    │    │        │    │    │
   ▼    ▼    ▼        ▼    ▼    ▼        ▼    ▼    ▼
 ┌───┐┌───┐┌───┐   ┌───┐┌───┐┌───┐   ┌───┐┌───┐┌───┐
 │V1 ││V2 ││V3 │   │C1 ││C2 ││C3 │   │F1 ││F2 ││F3 │ ← Objetos
 └───┘└───┘└───┘   └───┘└───┘└───┘   └───┘└───┘└───┘   Terminales
                                                      (Instancias)
```

---

## 🎭 **POLIMORFISMO**

### 📝 **Definición**

**El polimorfismo es la capacidad de un método de comportarse de manera diferente según la clase del objeto que lo invoque.** Esto permite que **un mismo mensaje pueda ser enviado a objetos de diferentes clases dentro de una jerarquía de herencia, y que estos objetos respondan de maneras distintas según su implementación**.

### 🎯 **Ejemplo Práctico**

#### 🔢 **Operador + en diferentes tipos**
- **Por ejemplo, un mensaje + enviado a un objeto de tipo ENTERO significaría suma**
- **Mientras que enviado a un objeto de tipo STRING significaría concatenación (unir dos cadenas de texto)**

### ⚙️ **Sobrecarga de Operadores**

**La sobrecarga de un operador** es una característica de algunos lenguajes de OOP que permite **redefinir o extender el comportamiento de un operador para trabajar con tipos de datos definidos por el usuario**.

### 💻 **Ejemplo de Código**

```python
class Vector:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    # Sobrecarga del operador +
    def __add__(self, other):
        return Vector(self.x + other.x, self.y + other.y)
    
    def __str__(self):
        return f"({self.x}, {self.y})"

# Creación de dos vectores
v1 = Vector(2, 3)
v2 = Vector(4, 5)

# Suma de los dos vectores usando el operador +
v3 = v1 + v2
print(v3)  # Output: (7, 10)
```

### 🔑 **Características del Polimorfismo**

#### ✅ **Flexibilidad**
- **Mismo mensaje, diferentes comportamientos**
- **Adaptación automática** según el tipo de objeto

#### ✅ **Reutilización**
- **Código más genérico** y reutilizable
- **Menos duplicación** de código

#### ✅ **Mantenibilidad**
- **Fácil extensión** con nuevos tipos
- **Modificaciones localizadas**

---

## 👹 **DEMONIOS**

### 📝 **Definición**

**Es un tipo especial de métodos, poco frecuente en OOP, que se activa automáticamente cuando sucede algo especial.** Es decir, **es un programa, como los métodos ordinarios, pero se diferencia de estos que su ejecución no se activa con un mensaje, sino que se desencadena automáticamente cuando ocurre un suceso determinado:**

- **La asignación de 1 valor a 1 propiedad de 1 objeto**
- **La lectura de 1 valor determinado**
- **Etc.**

### 🔄 **Diferencias con Métodos Ordinarios**

**Los demonios, cuando existen, se diferencian de otros métodos que:**
- **No son heredables**
- **A veces están ligados a 1 de las propiedades de 1 objeto, más que al objeto entero**

### ⚡ **Características de los Demonios**

#### 🔄 **Activación Automática**
- **No requieren invocación explícita**
- **Se ejecutan en respuesta a eventos específicos**
- **Actúan como "observadores" del estado del objeto**

#### 🎯 **Casos de Uso**
- **Validación automática** de datos
- **Actualización de propiedades dependientes**
- **Logging automático** de cambios
- **Notificaciones** de estado

### 💻 **Ejemplo Conceptual**

```java
public class CuentaBancaria {
    private double saldo;
    
    // Demonio que se activa al cambiar el saldo
    public void setSaldo(double nuevoSaldo) {
        // Validación automática (demonio)
        if (nuevoSaldo < 0) {
            System.out.println("¡Alerta! Saldo negativo detectado");
            enviarNotificacion("Saldo en números rojos");
        }
        
        // Logging automático (demonio)
        registrarCambio(this.saldo, nuevoSaldo);
        
        this.saldo = nuevoSaldo;
    }
}
```

---

## 🧬 **HERENCIA**

### 📝 **Definición**

**Hace uso de las relaciones de-la-especie y es-un(a).** Las **clases que son de-la-especie de otra clase comparten las propiedades de esta última**.

### 🎯 **Ejemplo: Circle hereda de Point**

#### 📋 **Declaración de Herencia**
```java
class Circle inherits from Point {
    // Atributos:
    int radius  // Atributo propio de Circle
    
    // Métodos:
    setRadius(int newRadius)  // Método propio
    getRadius()               // Método propio
}
```

#### ✅ **Beneficios de la Herencia**

**La clase Circle hereda todos los elementos de datos y métodos de la clase Point.** 
- **No hay necesidad de definirlos 2 veces**
- **Solo se usa los ya existentes, datos y definiciones de métodos**

### 🔄 **Relación es-un(a)**

**A nivel de objeto se define 1 círculo como 1 punto, debido a que un círculo es-un(a) punto.**

#### 💻 **Ejemplo de Uso**
```java
Circle acircle;
acircle.setX(1);        /* Heredado de Point */
acircle.setY(2);        /* Heredado de Point */
acircle.setRadius(3);   /* Añadido por Circle */
```

### 🔄 **Herencia Continuada**

#### 📝 **Concepto**
**"Es-un(a)" también implica que se puede usar 1 círculo en cualquier circunstancia donde se pueda usar 1 punto.**

#### 💻 **Ejemplo: Método move()**
```java
move(Point apoint, int deltax) {
    apoint.setX(apoint.getX() + deltax)
}
```

**Ya que círculo hereda de punto, se puede usar esta función con 1 argumento círculo para mover su punto central y, a partir de ahí, todo el círculo:**

```java
Circle acircle
...
move(acircle, 10)  /* Mover el círculo al mover */
                   /* su punto central */
```

### 📊 **Diagrama de Herencia**

```
┌─────────────────┐
│     Point       │ ← Clase base (padre)
│                 │
│ + x: int        │
│ + y: int        │
│ + setX()        │
│ + getX()        │
│ + setY()        │
│ + getY()        │
└─────────────────┘
         ▲
         │ inherits from
         │
┌─────────────────┐
│     Circle      │ ← Clase derivada (hija)
│                 │
│ + radius: int   │ ← Atributo añadido
│ + setRadius()   │ ← Método añadido
│ + getRadius()   │ ← Método añadido
└─────────────────┘
```

### 🎯 **Ventajas de la Herencia**

#### ✅ **Reutilización de Código**
- **Evita duplicación** de código
- **Aprovecha implementaciones** existentes
- **Reduce tiempo de desarrollo**

#### ✅ **Mantenibilidad**
- **Cambios en la clase base** se propagan automáticamente
- **Consistencia** en el comportamiento
- **Facilita actualizaciones**

#### ✅ **Polimorfismo**
- **Objetos derivados** pueden usarse donde se espera la clase base
- **Flexibilidad** en el diseño
- **Extensibilidad** del sistema

---

## 🧬 **HERENCIA (CONTINUACIÓN)**

### 📝 **Definiciones Fundamentales**

#### 🔄 **Mecanismo de Herencia**
**Definición:** Mecanismo que permite que **1 clase A herede propiedades de 1 clase B**. Se dice que **"A hereda de B"** cuando **objetos de la clase A tienen acceso a atributos y métodos de la clase B sin necesidad de redefinirlos**.

#### 🏗️ **Superclase/Subclase**
**Definición:** Si la **clase A hereda de la clase B**, entonces **B es la superclase de A**. **A es subclase de B**. Los **objetos de 1 subclase pueden ser usados donde son usados los objetos de la superclase**. Esto es porque **los objetos de la subclase comparten el mismo comportamiento que objetos de la superclase**.

### 📊 **Terminología de Herencia**

- **Las superclases también se llaman clases padres**
- **Las subclases clases hijas o derivadas**
- **También se puede heredar de 1 subclase**, haciendo que esta clase sea la superclase de la nueva subclase
- **Esto conduce a jerarquía de relaciones superclase/subclase**
- **Si se dibuja esta jerarquía, se obtiene 1 gráfica de herencia**

### 📊 **Esquema de Herencia**

**1 esquema consiste en usar flechas para indicar la relación de herencia entre clases u objetos.**

#### 📊 **Gráfica de herencia sencilla**

```
┌─────────────┐
│    Point     │ ← Superclase
└─────────────┘
         ▲
         │ hereda-de
         │
┌─────────────┐
│   Circle     │ ← Subclase
└─────────────┘
```

**Según el autor puede variar el sentido de la flecha**

---

## 🔀 **HERENCIA MÚTIPLE**

### 📝 **Definición**

**"La herencia múltiple significa que 1 subclase puede tener más de 1 superclase. Esto permite a la subclase herede propiedades de más de una superclase y –mezclar– sus propiedades".**

### ⚠️ **Aclaraciones Importantes**

**NO significa que múltiples subclases compartan la misma superclase. Tampoco que 1 subclase herede de 1 clase que es a su vez subclase de otra clase.**

### 💻 **Ejemplo Práctico: DrawableString**

**Para el ejemplo previo, si se tiene 1 clase String que permite el manejo adecuado de texto. Podría haber 1 método append para añadir otro texto. Se quiere usar esta clase para añadir texto a objetos que se dibujen; y usar rutinas ya existentes tales como move() para mover el texto donde se necesite.**

**Es lógico permitir que 1 texto para dibujarse tenga 1 punto que defina su localización dentro del área de dibujo. Por lo que se deriva 1 nueva clase DrawableString que hereda propiedades de Point y de String.**

### 📊 **Diagrama de Herencia Múltiple**

```
┌─────────────┐    ┌─────────────┐
│    Point     │    │   String    │
└─────────────┘    └─────────────┘
         ╲              ╱
          ╲            ╱
           ╲          ╱
            ╲        ╱
             ▼      ▼
        ┌───────────────────────┐
        │   DrawableString     │
        │ (String desplegable) │
        └───────────────────────┘
```

### 💻 **Implementación de DrawableString**

```java
class DrawableString inherits from Point, String {
    attributes:        /* Todos heredados de superclases */
    methods:          /* Todos heredados de superclases */
}
```

**Se puede usar objetos de la clase DrawableString como ambos: puntos y strings.**

### 🔄 **Uso Polimórfico**

#### 💻 **Debido a que drawablestring es-un(a) point se pueden mover dichos objetos:**
```java
DrawableString dstring
...
move(dstring, 10)
...
```

#### 💻 **Desde el momento que son string, se puede añadir otro texto:**
```java
dstring.append("La flores color azul ...")
```

---

## ⚠️ **HERENCIA MÚTIPLE - CONFLICTOS DE NOMENCLATURA**

### 📝 **Definición del Problema**

**Definición Herencia Múltiple:** Si la **clase A hereda de más de 1 clase**, por ejemplo **A hereda de B1, B2, ..., Bn**, se habla de **herencia múltiple**. Esto puede presentar **conflictos de nomenclatura en A** si al menos dos de sus superclases definen propiedades (atributos o métodos) con el mismo nombre.

### 🔍 **Ejemplo de Conflicto**

**Por ejemplo, si la clase String define un método setX() que pone el string en 1 secuencia de "X" caracteres. ¿Qué se hereda en DrawableString? ¿La versión de Point, de String o ninguna de las 2?**

### 🔧 **Estrategias de Resolución**

Estos conflictos se pueden resolver por:

#### 🎯 **1. Orden de Provisión**
- **El orden en el cuál las superclases son provistas**, definen qué propiedad será accesible por el nombre causante del conflicto
- **Los otros quedarán "escondidos"**

#### 🔄 **2. Resolución Explícita**
- **Las subclases deben resolver el conflicto proveyendo 1 propiedad con el nombre y definiendo cómo usar los de sus superclases**

### ⚠️ **Consideraciones de Implementación**

**La 1ra solución no es conveniente ya que presentan consecuencias implícitas dependiendo del orden. En el 2do caso, las subclases deben redefinir explícitamente las propiedades involucradas en conflictos.**

### 📊 **Conflicto Especial: Herencia en Diamante**

```
        ┌───────────┐
        │     A     │
        └───────────┘
             ╱   ╲
            ╱     ╲
           ╱       ╲
          ╱         ╲
         ▼           ▼
    ┌────────┐   ┌────────┐
    │   B    │   │   C    │
    └────────┘   └────────┘
         ╲           ╱
          ╲         ╱
           ╲       ╱
            ╲     ╱
             ▼   ▼
        ┌───────────┐
        │     D     │
        └───────────┘
```

**Un tipo especial de conflicto de nomenclatura se presenta si 1 clase D hereda en forma múltiple de las superclases B y C que a su vez derivan de 1 superclase A.**

Algunos lenguajes de programación resuelven esta gráfica de herencia especial derivando D con:
- **Las propiedades de A más**
- **Las propiedades de B y C sin las propiedades que han heredado de A**

**O NO permiten la Herencia Múltiple**

---

## 🎆 **BENEFICIOS QUE SE OBTIENEN DEL DESARROLLO CON OOP**

### 🔄 **REUTILIZACIÓN DE CÓDIGO**

#### 💰 **Reducción de Costos**
**Los costos del HW decrecen, apareciendo nuevas áreas de aplicación** (procesamiento de imágenes y sonido, bases de datos, etc), **pero los costos de producción de SW siguen aumentando** (mantenimiento y modificación de sistemas complejos).

#### 🔧 **Solución Parcial**
**Estos problemas no han sido solucionados en forma completa, pero como los objetos son portables (teóricamente) mientras que la herencia permite la reusabilidad del código OO, es más sencillo modificar código existente** ya que **los objetos no interactúan excepto a través de mensajes; en consecuencia un cambio en la codificación de un objeto no afectará la operación con otro objeto siempre que los métodos respectivos permanezcan intactos**.

### 📊 **Herramienta Conceptual**

> **"La introducción de tecnología de objetos como una herramienta conceptual para analizar, diseñar e implementar aplicaciones permite obtener aplicaciones más modificables, fácilmente extensibles y a partir de componentes reutilizables".**

### 🚀 **Desarrollo Intuitivo**

**Esta reutilizabilidad del código disminuye el tiempo que se utiliza en el desarrollo y hace que el desarrollo del software sea más intuitivo** porque **la gente piensa naturalmente en términos de objetos más que en términos de algoritmos de software**.

### 📊 **Diagrama de Beneficios**

```
┌────────────────────────────────────────────────────────────┐
│                      BENEFICIOS DEL DESARROLLO OOP                      │
├────────────────────────────────────────────────────────────┤
│                                                              │
│  💰 REDUCCIÓN DE COSTOS                                        │
│  • Costos de HW decrecen                                       │
│  • Costos de SW siguen aumentando                              │
│  • Mantenimiento y modificación complejos                      │
│                                                              │
│  🔄 REUTILIZACIÓN DE CÓDIGO                                   │
│  • Objetos portables (teóricamente)                           │
│  • Herencia permite reusabilidad                              │
│  • Modificación más sencilla                                   │
│  • Interacción solo por mensajes                               │
│                                                              │
│  📊 HERRAMIENTA CONCEPTUAL                                   │
│  • Aplicaciones más modificables                               │
│  • Fácilmente extensibles                                     │
│  • Componentes reutilizables                                  │
│                                                              │
│  🚀 DESARROLLO INTUITIVO                                     │
│  • Disminuye tiempo de desarrollo                             │
│  • Pensamiento natural en objetos                            │
│  • Más que algoritmos de software                             │
│                                                              │
└────────────────────────────────────────────────────────────┘
```

### 🎯 **Ventajas Clave del OOP**

#### ✅ **Portabilidad**
- **Objetos teóricamente portables** entre sistemas
- **Independencia de plataforma** (con las herramientas adecuadas)

#### ✅ **Mantenibilidad**
- **Cambios localizados** no afectan otros objetos
- **Métodos permanecen intactos** durante modificaciones
- **Interacción controlada** solo por mensajes

#### ✅ **Extensibilidad**
- **Fácil adición** de nuevas funcionalidades
- **Herencia** permite especialización
- **Composición** permite combinación de objetos

#### ✅ **Intuitividad**
- **Pensamiento natural** en términos de objetos
- **Modelado directo** del mundo real
- **Abstracción** de complejidad técnica

---

## ⚠️ **PROBLEMAS DERIVADOS DE LA UTILIZACIÓN DE OOP**

### 📝 **Problemas Identificados**

A pesar de los beneficios del paradigma orientado a objetos, existen ciertos problemas y limitaciones que deben considerarse:

#### 📈 **a) Curvas de aprendizaje largas**
**Un sistema orientado a objetos ve al mundo en 1 forma única.** Involucra la **conceptualización de todos los elementos de un programa, desde subsistemas a datos, en forma de objetos**.

**Características del problema:**
- **Cambio de paradigma mental** significativo
- **Requiere tiempo** para dominar los conceptos
- **Abstracción compleja** para principiantes
- **Pensamiento diferente** al paradigma procedural

#### 🔗 **b) Dependencia del lenguaje**
**A pesar de la portabilidad conceptual de los objetos en un sistema OO, en la práctica existen muchas dependencias** (C++ soporta herencia múltiple mientras que Smalltalk no).

**Limitaciones prácticas:**
- **Diferencias entre lenguajes** OOP
- **Características específicas** no universales
- **Portabilidad limitada** en la realidad
- **Incompatibilidades** entre sistemas

#### 🎨 **c) Determinación de las clases**
**1 clase es un molde que se utiliza para crear nuevos objetos.** Es importante **crear el conjunto de clases adecuado para un proyecto**. Pero **la definición de las clases es más un arte que 1 ciencia**.

**Desafíos del diseño:**
- **No hay metodología única** para definir clases
- **Experiencia y criterio** son fundamentales
- **Diseño subjetivo** y dependiente del contexto
- **Refactorización constante** necesaria

#### ⚡ **d) Performance**
**En un sistema donde todo es 1 objeto y toda interacción es a través de mensajes, el tráfico de mensajes afecta la performance.** **1 diseño de 1 aplicación OO que no tiene en cuenta la performance no será viable comercialmente**.

**Impacto en rendimiento:**
- **Overhead de mensajes** entre objetos
- **Abstracción** puede reducir eficiencia
- **Memoria adicional** para estructuras de objetos
- **Procesamiento extra** para polimorfismo

### 📊 **Diagrama de Problemas**

```
┌────────────────────────────────────────────────────────────┐
│                    PROBLEMAS DEL PARADIGMA OOP                    │
├────────────────────────────────────────────────────────────┤
│                                                              │
│  📈 CURVAS DE APRENDIZAJE LARGAS                             │
│  • Cambio de paradigma mental                                 │
│  • Conceptualización compleja                                │
│  • Tiempo para dominar conceptos                             │
│                                                              │
│  🔗 DEPENDENCIA DEL LENGUAJE                                │
│  • Diferencias entre lenguajes OOP                          │
│  • Portabilidad limitada en práctica                         │
│  • Incompatibilidades entre sistemas                        │
│                                                              │
│  🎨 DETERMINACIÓN DE CLASES                                  │
│  • Más arte que ciencia                                      │
│  • No hay metodología única                                  │
│  • Diseño subjetivo                                          │
│                                                              │
│  ⚡ PERFORMANCE                                              │
│  • Tráfico de mensajes                                       │
│  • Overhead de abstracción                                   │
│  • Viabilidad comercial                                      │
│                                                              │
└────────────────────────────────────────────────────────────┘
```

### 🎆 **UTOPÍA DEL OOP**

> 💭 **"Debería existir una metodología fácil de aprender e independiente del lenguaje, y fácil de reestructurar que no drene la performance del sistema"**

**Esta declaración representa el ideal teórico del paradigma orientado a objetos, pero en la práctica:**

#### 🎯 **Realidad vs Utopía**

| Aspecto | Utopía Deseada | Realidad Actual |
|---------|----------------|------------------|
| **Metodología** | Fácil de aprender | Curva de aprendizaje larga |
| **Independencia** | Del lenguaje | Dependiente del lenguaje |
| **Reestructuración** | Fácil | Compleja, más arte que ciencia |
| **Performance** | Sin impacto | Overhead significativo |

#### 🔍 **Conclusión**

A pesar de estos problemas, **el paradigma OOP sigue siendo valioso** porque:
- **Los beneficios superan** las limitaciones en muchos casos
- **La tecnología continúa evolucionando** para mitigar problemas
- **Las herramientas y metodologías** mejoran constantemente
- **La experiencia de la comunidad** reduce las curvas de aprendizaje

---

## 📚 **BIBLIOGRAFÍA**

### 🌐 **Páginas web consultadas:**

**Tutorial de Programación Orientada a Objetos:**
- [https://www.desy.de/gna/html/cc/Tutorial/Spanish/tutorial.html](https://www.desy.de/gna/html/cc/Tutorial/Spanish/tutorial.html)

### 📄 **Documento original:**

**Material de referencia principal:**
- [https://www.dropbox.com/s/5we40yhv45yv9s1/Programaci%C3%B3n%20Orientada%20a%20Objetos.doc?dl=0](https://www.dropbox.com/s/5we40yhv45yv9s1/Programaci%C3%B3n%20Orientada%20a%20Objetos.doc?dl=0)

### 📚 **Referencias Adicionales**

**Textos fundamentales en POO:**
- **Grady Booch** - "Object-Oriented Analysis and Design"
- **Cruz del Valle** - Definiciones fundamentales de Clase y Objeto
- **Material didáctico** del curso de Programación Concurrente UNJu

### 📝 **Créditos Académicos**

**Este material ha sido elaborado basado en:**
- **Clases teóricas** del Prof. Adj. Esp. Ing. José Farfán
- **Aportes del equipo docente** de Programación Concurrente
- **Material de cátedra** de la Facultad de Ingeniería UNJu
- **Fuentes bibliográficas** especializadas en POO

---

## 🎉 **CONCLUSIÓN DEL CURSO**

### 🎯 **Objetivos Alcanzados**

En esta clase hemos cubierto **todos los aspectos fundamentales** del Paradigma Orientado a Objetos:

#### ✅ **Conceptos Teóricos**
- **Definiciones fundamentales** de clase y objeto
- **Estructura de objetos** (relaciones, propiedades, métodos)
- **Tipos de relaciones** (jerárquicas, semánticas, de-la-especie, etc.)
- **Principios fundamentales** (encapsulación, herencia, polimorfismo)

#### ✅ **Conceptos Avanzados**
- **Herencia simple y múltiple**
- **Conflictos de nomenclatura** y sus soluciones
- **Organización jerárquica** de objetos
- **Demonios** y métodos especiales

#### ✅ **Aspectos Prácticos**
- **Beneficios del desarrollo** con OOP
- **Problemas y limitaciones** del paradigma
- **Ejemplos de implementación** en Java
- **Aplicaciones reales** en sistemas bancarios y educativos

### 🚀 **Próximos Pasos**

Con esta base sólida en POO, estás preparado para:
- **Implementar sistemas** orientados a objetos
- **Diseñar arquitecturas** robustas y mantenibles
- **Aplicar patrones de diseño** avanzados
- **Desarrollar aplicaciones** concurrentes y distribuidas

---

*Esta documentación forma parte del curso de Programación Concurrente 2024 - Facultad de Ingeniería UNJu*

*Basado en las clases del Prof. Adj. Esp. Ing. José Farfán y equipo docente*

*Material compilado y organizado para el estudio completo del Paradigma Orientado a Objetos*