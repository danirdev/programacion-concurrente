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

*Esta documentación forma parte del curso de Programación Concurrente 2024 - Facultad de Ingeniería UNJu*

*Basado en las clases del Prof. Adj. Esp. Ing. José Farfán y equipo docente*