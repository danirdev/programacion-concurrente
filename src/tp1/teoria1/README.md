# 📚 CLASE 1 - PARADIGMA ORIENTADO A OBJETOS

## 📋 **INFORMACIÓN DEL CURSO**

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

1. [🎯 Razones de Aparición del POO](#razones-de-aparición-del-poo)
2. [🏗️ Características Básicas](#características-básicas)
3. [📦 Estructura de un Objeto](#estructura-de-un-objeto)
4. [🔗 Tipos de Relaciones](#tipos-de-relaciones)
5. [🏛️ Clases](#clases)
6. [🧬 Herencia](#herencia)
7. [💡 Conceptos Avanzados](#conceptos-avanzados)
8. [📚 Bibliografía](#bibliografía)

---

## 🎯 **RAZONES DE APARICIÓN DEL POO**

### ❌ **Problemas del Paradigma Anterior**

El Paradigma Orientado a Objetos surge para resolver problemas fundamentales:

#### 1️⃣ **Falta de Portabilidad del Código y Reusabilidad**
- Código difícil de reutilizar en diferentes proyectos
- Dependencias específicas del sistema

#### 2️⃣ **Código Difícil de Modificar**
- Cambios requieren modificaciones extensas
- Alto acoplamiento entre componentes

#### 3️⃣ **Ciclos de Desarrollo Largos**
- Tiempo excesivo desde diseño hasta implementación
- Dificultad para iterar rápidamente

#### 4️⃣ **Técnicas de Codificación NO Intuitivas**
- Paradigmas alejados del pensamiento natural
- Código difícil de entender y mantener

---

## 🏗️ **CARACTERÍSTICAS BÁSICAS DEL POO**

### ✅ **Tres Pilares Fundamentales**

#### 1️⃣ **Basado en Objetos**
La unidad fundamental es el **objeto**.

#### 2️⃣ **Basado en Clases**
Las clases definen la estructura común de los objetos.

#### 3️⃣ **Capaz de tener HERENCIA de clases**
Permite reutilización de código existente.

---

## 📝 **DEFINICIONES FUNDAMENTALES**

### 🔷 **Definición de POO**

> **POO:** Metodología de desarrollo de aplicaciones en la cual éstas se organizan como **colecciones cooperativas de objetos**, cada uno de los cuales representan una **instancia** de alguna **clase**, y cuyas clases son miembros de **jerarquías de clases** unidas mediante **relaciones de herencia**.
> 
> *— Grady Booch*

### 🔶 **Definición de Objeto**

> **Objeto:** Conjunto complejo de datos y programas que poseen estructura y forman parte de una organización.
> 
> *— Cruz del Valle*

---

## 📦 **ESTRUCTURA DE UN OBJETO**

### 🔑 **Partes de un OBJETO**

Un objeto está compuesto por **tres componentes fundamentales**:

#### 1️⃣ **RELACIONES**
- Permiten que el objeto se **inserte en la organización**
- Están formadas por **punteros a otros objetos**

#### 2️⃣ **PROPIEDADES**
- Todo objeto puede tener cierto número de propiedades
- Cada una tendrá **uno o varios valores**:
  - Matrices
  - Vectores
  - Listas
  - Etc.
- Los valores pueden ser de cualquier tipo:
  - Numérico
  - Alfabético
  - Etc.

#### 3️⃣ **MÉTODOS**
- Son las **operaciones** que pueden realizarse sobre el objeto
- Están incorporados en forma de **programas (código)**
- El objeto es capaz de ejecutarlos
- Se ponen a disposición de sus **descendientes** a través de la **herencia**

---

## 🔗 **TIPOS DE RELACIONES**

### 🌳 **RELACIONES JERÁRQUICAS**

Son **esenciales** para la existencia misma de la aplicación. Son **bidireccionales**: un objeto es padre de otro cuando el primer objeto se encuentra situado inmediatamente encima del segundo en la organización.

#### 📊 **Organización Jerárquica Simple**
**Característica:** Un hijo tiene **un solo padre**

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
```

#### 🕸️ **Organización Jerárquica Compleja**
**Característica:** Un hijo tiene **varios padres**

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
        └─────────────┴──╲──────────┘
                         ╲
                          ╲ Múltiples padres
```

---

### 🧠 **RELACIONES SEMÁNTICAS**

**NO** tienen que ver con la organización de la que forman parte los objetos. Sus propiedades **solo dependen de los objetos en sí mismos** (de su significado) y **no de su posición** en la organización.

#### 📚 **Ejemplo: Diccionario Informatizado**

Permite al usuario obtener la definición de una palabra. La raíz se llama **TEMAS**, de la cual descienden **3 grandes ramas**:

```
                    ┌─────────┐
                    │  TEMAS  │
                    └─────────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
   ┌─────────┐      ┌─────────┐      ┌─────────┐
   │  VIDA   │      │  MUNDO  │      │ HOMBRE  │
   └─────────┘      └─────────┘      └─────────┘
```

**Descripción:**
- **VIDA:** Ciencias biológicas (Biología, Medicina)
- **MUNDO:** Ciencias de la naturaleza inerte (Matemática, Física, Química, Geología)
- **HOMBRE:** Ciencias humanas (Geografía, Historia, etc.)

#### 🔬 **Relación Semántica: NEWTON y ÓPTICA**

```
┌─────────┐    trabajó    ┌─────────┐
│ NEWTON  │ ──────────► │ ÓPTICA  │
└─────────┘              └─────────┘
```

Esta relación significa que **Newton trabajó en óptica**. Es **semántica** porque no hay connotación jerárquica entre NEWTON y ÓPTICA.

---

## 🎯 **RELACIÓN DE-LA-ESPECIE**

Se usa **al nivel de clase** para describir las relaciones entre **dos clases similares**.

### 💻 **Ejemplo: Programa de Dibujo**

#### 📐 **Clase Point**

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

#### ⭕ **Clase Circle**

```java
class Circle {
    // Atributos:
    int x, y, radius
    
    // Métodos:
    setX(int newX)
    getX()
    setY(int newY)
    getY()
    setRadius(int newRadius)
    getRadius()
}
```

### 🔍 **Análisis de la Relación**

**Comparando ambas definiciones:**
- Ambas clases tienen **2 elementos de datos x e y**
- En Point: describen la **posición del punto**
- En Circle: describen el **centro del círculo**
- Ambas ofrecen el **mismo conjunto de métodos** para x e y
- Circle **añade** un nuevo elemento `radius` con sus métodos

### 📊 **Representación UML**

```
┌─────────────────┐     de-la-especie     ┌─────────────────┐
│     Circle      │ ──────────────────► │     Point       │
└─────────────────┘                      └─────────────────┘
```

Se lee como: **"Circle es de-la-especie Point"**

---

## 🔄 **RELACIÓN ES-UN(A)**

Cuando se crean **objetos** de clases de la relación "De-La-Especie", la relación se llama **"es-un(a)"**.

### 📊 **Transformación**

```
Nivel de Clase:    Circle ──de-la-especie──► Point
                      ↓ instanciación        ↓
Nivel de Objeto:   círculo ──────es-un(a)────► point
```

### 💡 **Comportamiento**

- La clase Circle **es de la especie** de la clase Point
- Una instancia de Circle (acircle) **es un** point
- Cada círculo **se comporta como un punto**

```
┌─────────────────┐     es-un(a)     ┌─────────────────┐
│     circle      │ ──────────────► │     point       │
└─────────────────┘                  └─────────────────┘
```

---

## 🧩 **RELACIÓN PARTE-DE**

A veces se necesita construir objetos haciendo **una combinación de otros**.

### 🎨 **Ejemplo: Logo**

```java
class Logo {
    // Atributos:
    Circle circle
    Triangle triangle
    
    // Métodos:
    set(Point where)
}
```

### 📊 **Representación UML**

```
┌─────────────┐    parte-de    ┌─────────────┐    parte-de    ┌─────────────┐
│   Circle    │ ──────────────► │    Logo     │ ◄────────────── │  Triangle   │
└─────────────┘                 └─────────────┘                 └─────────────┘
```

El círculo y el triángulo son **parte-de** logotipo.

---

## 🔄 **RELACIÓN TIENE-UN(A)**

Esta relación es la **inversa de la relación parte-de**.

```
┌─────────────┐    parte-de    ┌─────────────┐    parte-de    ┌─────────────┐
│   Circle    │ ──────────────► │    Logo     │ ◄────────────── │  Triangle   │
│             │ ◄────────────── │             │ ──────────────► │             │
└─────────────┘   tiene-un(a)  └─────────────┘   tiene-un(a)  └─────────────┘
```

**Interpretación:**
- Logo **tiene-un** Circle
- Logo **tiene-un** Triangle

---

## 🏷️ **PROPIEDADES**

Se diferencian con las "variables" porque se pueden **heredar** de unos objetos a otros.

### 📋 **Tipos de Propiedades**

#### 🏗️ **PROPIEDADES PROPIAS**
Están formadas **dentro de la cápsula del objeto**.

#### 🧬 **PROPIEDADES HEREDADAS**
- Están definidas en **un objeto diferente, antepasado** de éste (padre, "abuelo", etc.)
- A veces se llaman **propiedad miembro** porque el objeto las posee por el solo hecho de ser miembro de una clase

Las propiedades **distinguen un objeto de los restantes** que forman parte de la misma organización.

---

## ⚙️ **MÉTODOS**

### 📝 **Definición**

Son las **operaciones que realizan acceso a los datos**. Se pueden definir como un programa procedimiento escrito en cualquier lenguaje, que está asociado a **un objeto determinado** y cuya ejecución **solo puede desencadenarse** a través de **un mensaje recibido** por éste o sus descendientes.

### 🔄 **Diferencia con Paradigma Procedural**

- **Paradigma Procedural:** programas, procedimientos, función, rutina, etc.
- **OOP:** se usa el término **método**

### 📋 **Clasificación de Métodos**

#### 🏗️ **Métodos Propios**
Están incluidos **dentro de la cápsula del objeto**.

#### 🧬 **Métodos Heredados**
- Están definidos en **un objeto diferente, antepasado** de éste
- A veces se llaman **métodos miembros** porque el objeto los posee por ser miembro de una clase

---

## 🎯 **OBJETOS - CARACTERÍSTICAS**

### 📋 **Seis Características Fundamentales**

1. **Se agrupan en grupos denominados clases**
2. **Contienen datos internos que definen su estado actual**
3. **Soportan ocultamiento de datos**
4. **Pueden heredar propiedades de otros objetos**
5. **Pueden comunicarse con otros objetos enviando o pasando mensajes**
6. **Tienen métodos que definen su comportamiento**

### 💻 **Ejemplo de Diseño**

```java
class nomina {
    // nomina empleado
    char nombre[30];
    float salario;
}; 
// (nomina es una clase)
// (empleado es un objeto)
```

---

## 🏛️ **CLASE - CONCEPTO**

### 📝 **Definición**

Cada objeto es **un ejemplar de una clase** a la que pertenece. Todos los ejemplares de la misma clase tienen el **mismo comportamiento** (invocan al mismo método) como respuesta a una solicitud.

### 💻 **Sintaxis de una Clase**

```java
class nombre_clase {
    miembro_1;        // lista de datos miembros
    miembro_2;
    miembro_3;
    
    funcion_miembro_1();    // funciones miembro
    funcion_miembro_2();    // conocidas como métodos
}
```

### 🔑 **Concepto Clave**

Una clase es un **tipo especial de datos** orientado a la creación de objetos que:
- Consta de **miembros**
- Contiene **uno o más elementos** llamados datos miembros
- Contiene **cero, una o más funciones** que manipulan esos datos (funciones miembro)
- Se puede definir con: `struct`, `union` o `class`

---

## 🔧 **CLASE - DECLARACIÓN**

### 💻 **Ejemplo de Declaración Simple**

```java
class counter {
    long count;  // Variable miembro de la clase
    public:
        void SetCount(long);
        long GetValue();
}
```

### 🔑 **Elementos Clave**

- **Palabra clave `class`:** Introduce una declaración de clase
- **Nombre de la clase:** Después aparece el identificador
- **Variables miembro:** Pertenecen a esa clase
- **Funciones miembro:** Pueden ser tan largas y complejas como uno desee
- **Identificadores únicos:** No coinciden con identificadores de otras clases

---

## 🏗️ **CLASE - CUERPO Y USO**

### 📋 **Cuerpo de una Clase**

```java
class counter {
    long count;
    public:
        // count = 3;  // ¡Error! count no está definido aquí
}
```

Cualquier variable definida en una clase tiene un **campo de acción**. Se produce un error al intentar acceder a una variable miembro **fuera del campo de acción de la clase**.

### 🎯 **Uso de una Clase**

Para usar una clase se debe **definir un objeto** con ella:

```java
Counter people;  // Definir variable de la clase people de tipo counter
```

**Concepto fundamental:**
> "Las variables instanciadas a partir de clases son los objetos"

### 💻 **Ejemplo de Uso**

```java
void main() {
    counter people;
    // Inicializar el objeto people
    SetValue(0);
    // Verificar que se borre
    long value = people.GetValue();
}
```

---

## 📦 **CLASE - COMPONENTES**

### 🔧 **Dos Partes Fundamentales**

Para definir una clase se debe tomar en cuenta que consta de:
1. **Una declaración**
2. **Una implementación**

### 💻 **Ejemplo Completo**

#### **Declaración de una clase**

```java
class nomina {
    nomina empleado;
    char nombre[30];
    float salario;
};  // (nomina es una clase)
    // (empleado es un objeto)
```

#### **Funciones miembro de la clase**

```java
class contador {
    long cuenta;
    public:
        void leervalor(long);
        long obtenervalor();
};
```

#### **Implementación de una clase**

```java
void contador::leerValor(long valor) {
    cuenta = valor;
}

long contador::obtenerValor() {
    return cuenta;
}
```

---

## 🔒 **ENCAPSULAMIENTO Y OCULTACIÓN**

### 🔐 **Encapsulamiento**

**Definición:** Propiedad por la que cada objeto es una **estructura compleja** en cuyo interior hay datos y programas relacionados entre sí, encerrados en una **cápsula**.

Es una **característica fundamental** en la OOP.

### 🕵️ **Ocultación de la Información**

**Definición:** Propiedad por la que los objetos son **inaccesibles**, e impiden que otros objetos, usuarios, o incluso los programadores conozcan cómo está distribuida la información o qué información hay disponible.

### 📨 **Comunicación por Mensajes**

Las peticiones de información a un objeto deben realizarse a través de **mensajes** dirigidos a él, con la orden de realizar la operación pertinente.

La respuesta será la información requerida, siempre que el objeto considere que quien envía el mensaje está **autorizado** para obtenerla.

### 🚀 **Beneficio: Reutilización**

El hecho de que cada objeto sea una cápsula facilita que un objeto determinado pueda ser **transportado** a otro punto de la organización, o incluso a otra organización totalmente diferente.

Si el objeto se construyó correctamente, sus métodos seguirán funcionando en el nuevo entorno sin problemas. Esta cualidad hace que la OOP sea muy apta para la **reutilización de programas**.

---

## 🌳 **ORGANIZACIÓN JERÁRQUICA DE OBJETOS**

### 📝 **Concepto**

Los objetos forman siempre una **organización jerárquica**, ya que ciertos objetos son superiores a otros.

### 🔄 **Tipos de Jerarquías**

- **SIMPLES:** Su estructura puede ser representada por medio de un "árbol"
- **COMPLEJOS:** Estructuras más complejas

### 📊 **Tres Niveles**

#### 🏔️ **1. Raíz de la jerarquía**
- Objeto **único y especial**
- Se caracteriza por estar en el nivel **más alto** de la estructura
- Se lo llama: objeto madre, Raíz o Entidad

#### 🌿 **2. Objetos intermedios**
- Descienden directamente de la raíz y tienen descendientes
- Representan **conjuntos o clases** de objetos
- Reciben nombres genéricos: VENTANA, CUENTA, FICHERO
- Se llaman **clases** o **tipos** si descienden de otra clase o **subclase**

#### 🍃 **3. Objetos terminales**
- Descienden de una clase o subclase
- **No tienen descendientes**
- Se llaman: casos particulares, **instancias** o **ítems**
- Representan los elementos del conjunto representado por la clase o subclase

---

## 🎭 **POLIMORFISMO**

### 📝 **Definición**

El polimorfismo es la capacidad de un método de **comportarse de manera diferente** según la clase del objeto que lo invoque.

Permite que un **mismo mensaje** pueda ser enviado a objetos de diferentes clases dentro de una jerarquía de herencia, y que estos objetos **respondan de maneras distintas** según su implementación.

### 💡 **Ejemplo**

- Un mensaje **+** enviado a un objeto de tipo **ENTERO** significaría **suma**
- Enviado a un objeto de tipo **STRING** significaría **concatenación** (unir dos cadenas de texto)

### ⚙️ **Sobrecarga de Operadores**

La **sobrecarga de un operador** es una característica de algunos lenguajes de OOP que permite **redefinir o extender** el comportamiento de un operador para trabajar con tipos de datos definidos por el usuario.

---

## 👹 **DEMONIOS**

### 📝 **Definición**

Es un tipo especial de métodos, poco frecuente en OOP, que se **activa automáticamente** cuando sucede algo especial.

Es un programa, como los métodos ordinarios, pero se diferencia porque su ejecución **no se activa con un mensaje**, sino que se desencadena automáticamente cuando ocurre un suceso determinado:
- La asignación de un valor a una propiedad de un objeto
- La lectura de un valor determinado
- Etc.

### 🔄 **Diferencias con Métodos Ordinarios**

Los demonios, cuando existen, se diferencian de otros métodos porque:
- **No son heredables**
- A veces están ligados a **una de las propiedades** de un objeto, más que al objeto entero

---

## 🧬 **HERENCIA**

### 📝 **Concepto Básico**

Hace uso de las relaciones **de-la-especie** y **es-un(a)**. Las clases que son de-la-especie de otra clase **comparten las propiedades** de esta última.

### 💻 **Ejemplo: Circle hereda de Point**

```java
class Circle inherits from Point {
    // Atributos:
    int radius  // Atributo propio de Circle
    
    // Métodos:
    setRadius(int newRadius)
    getRadius()
}
```

### ✅ **Beneficio**

La clase Circle **hereda todos los elementos de datos y métodos** de la clase Point. No hay necesidad de definirlos 2 veces.

### 💻 **Uso del Objeto**

```java
Circle acircle;
acircle.setX(1);        // Heredado de Point
acircle.setY(2);        // Heredado de Point
acircle.setRadius(3);   // Añadido por Circle
```

---

## 🔄 **HERENCIA - POLIMORFISMO EN ACCIÓN**

### 💻 **Ejemplo de Método Polimórfico**

```java
move(Point apoint, int deltax) {
    apoint.setX(apoint.getX() + deltax);
}

Circle acircle;
// ...
move(acircle, 10);  // Mover el círculo al mover su punto central
```

Ya que **círculo hereda de punto**, se puede usar esta función con un argumento círculo para mover su punto central y, a partir de ahí, todo el círculo.

---

## 📝 **HERENCIA - DEFINICIONES FORMALES**

### 🔷 **Definición de Herencia**

Mecanismo que permite que **una clase A herede propiedades de una clase B**. Se dice que "A hereda de B" cuando objetos de la clase A tienen acceso a atributos y métodos de la clase B sin necesidad de redefinirlos.

### 🔶 **Definición de Superclase/Subclase**

Si la clase A hereda de la clase B, entonces:
- **B es la superclase de A**
- **A es subclase de B**

Los objetos de una subclase pueden ser usados donde son usados los objetos de la superclase, porque los objetos de la subclase **comparten el mismo comportamiento** que objetos de la superclase.

### 📊 **Terminología**

- Las **superclases** también se llaman **clases padres**
- Las **subclases** se llaman **clases hijas** o **derivadas**

### 🌳 **Jerarquía de Herencia**

También se puede heredar de una subclase, haciendo que esta clase sea la superclase de la nueva subclase. Esto conduce a una **jerarquía de relaciones superclase/subclase**.

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

---

## 🔀 **HERENCIA MÚLTIPLE**

### 📝 **Definición**

"La herencia múltiple significa que **una subclase puede tener más de una superclase**. Esto permite que la subclase herede propiedades de más de una superclase y **mezclar** sus propiedades".

**NO significa:**
- Que múltiples subclases compartan la misma superclase
- Que una subclase herede de una clase que es a su vez subclase de otra clase

### 💻 **Ejemplo: DrawableString**

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
        └───────────────────────┘
```

```java
class DrawableString inherits from Point, String {
    attributes:        // Todos heredados de superclases
    methods:          // Todos heredados de superclases
}
```

### 💻 **Uso**

```java
DrawableString dstring;
// ...
move(dstring, 10);  // Porque es-un(a) point
// ...
dstring.append("Las flores color azul...");  // Porque es string
```

---

## ⚠️ **HERENCIA MÚLTIPLE - CONFLICTOS**

### 📝 **Definición de Conflicto**

Si la clase A hereda de más de una clase (A hereda de B1, B2, ..., Bn), puede presentar **conflictos de nomenclatura** en A si al menos dos de sus superclases definen propiedades (atributos o métodos) con el **mismo nombre**.

### ❓ **Ejemplo de Conflicto**

Si la clase String define un método `setX()` que pone el string en una secuencia de "X" caracteres:
- ¿Qué se hereda en DrawableString?
- ¿La versión de Point, de String o ninguna de las 2?

### ✅ **Soluciones**

#### 1️⃣ **Por Orden**
El orden en el cual las superclases son provistas define qué propiedad será accesible. Los otros quedarán "escondidos".

**Problema:** No es conveniente ya que presenta consecuencias implícitas dependiendo del orden.

#### 2️⃣ **Resolución Explícita**
Las subclases deben resolver el conflicto proveyendo una propiedad con el nombre y definiendo cómo usar los de sus superclases.

### 🔺 **Conflicto Especial: Herencia en Diamante**

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

Algunos lenguajes resuelven derivando D con:
- Las propiedades de A más
- Las propiedades de B y C sin las propiedades que han heredado de A

**O NO permiten la Herencia Múltiple**

---

## 🎆 **BENEFICIOS DEL DESARROLLO CON OOP**

### 🔄 **REUTILIZACIÓN DE CÓDIGO**

#### 💰 **Contexto**
- Los costos del **HW decrecen**
- Aparecen nuevas áreas de aplicación (procesamiento de imágenes, sonido, bases de datos)
- Los costos de producción de **SW siguen aumentando** (mantenimiento y modificación)

#### ✅ **Ventajas de OOP**

Los objetos son **portables** (teóricamente) y la herencia permite la **reusabilidad del código OO**:
- Es más **sencillo modificar** código existente
- Los objetos **no interactúan excepto a través de mensajes**
- Un cambio en la codificación de un objeto **no afectará la operación** con otro objeto siempre que los métodos respectivos permanezcan intactos

### 📊 **Herramienta Conceptual**

> "La introducción de tecnología de objetos como una herramienta conceptual para analizar, diseñar e implementar aplicaciones permite obtener aplicaciones más modificables, fácilmente extensibles y a partir de componentes reutilizables".

### 🚀 **Desarrollo Intuitivo**

Esta reusabilidad del código:
- **Disminuye el tiempo** que se utiliza en el desarrollo
- Hace que el desarrollo del software sea **más intuitivo**
- La gente piensa naturalmente en términos de **objetos** más que en términos de **algoritmos de software**

---

## ⚠️ **PROBLEMAS DERIVADOS DE LA UTILIZACIÓN DE OOP**

### 📋 **Cuatro Problemas Principales**

#### 📈 **a) Curvas de Aprendizaje Largas**
Un sistema orientado a objetos ve al mundo en una **forma única**. Involucra la conceptualización de todos los elementos de un programa, desde subsistemas a datos, en forma de objetos.

#### 🔗 **b) Dependencia del Lenguaje**
A pesar de la portabilidad conceptual de los objetos en un Sistema OO, en la práctica existen **muchas dependencias**.

**Ejemplo:** C++ soporta herencia múltiple mientras que Smalltalk no.

#### 🎨 **c) Determinación de las Clases**
Una clase es un molde que se utiliza para crear nuevos objetos. Es importante crear el **conjunto de clases adecuado** para un proyecto.

**Problema:** La definición de las clases es **más un arte que una ciencia**.

#### ⚡ **d) Performance**
En un sistema donde todo es un objeto y toda interacción es a través de mensajes, el **tráfico de mensajes afecta la performance**.

Un diseño de una aplicación OO que no tiene en cuenta la performance **no será viable comercialmente**.

---

## 💭 **UTOPÍA DEL OOP**

> "Debería existir una metodología fácil de aprender e independiente del lenguaje, y fácil de reestructurar que no drene la performance del sistema."

### 🎯 **Realidad vs Ideal**

| Aspecto | Ideal Deseado | Realidad Actual |
|---------|---------------|------------------|
| **Aprendizaje** | Fácil de aprender | Curvas largas |
| **Independencia** | Del lenguaje | Dependencias |
| **Definición** | Metodología clara | Más arte que ciencia |
| **Performance** | Sin impacto | Afecta rendimiento |

---

## 📊 **RESUMEN DE CONCEPTOS CLAVE**

### ✅ **Fundamentos del POO**

1. **Objetos:** Entidades con datos y métodos
2. **Clases:** Moldes para crear objetos
3. **Herencia:** Reutilización de código
4. **Encapsulamiento:** Ocultación de datos
5. **Polimorfismo:** Múltiples comportamientos
6. **Mensajes:** Comunicación entre objetos

### ✅ **Tipos de Relaciones**

- **Jerárquicas:** Padre-hijo (simple o compleja)
- **Semánticas:** Basadas en significado
- **De-la-especie:** Entre clases similares
- **Es-un(a):** Entre instancias
- **Parte-de / Tiene-un(a):** Composición

### ✅ **Beneficios**

- Reutilización de código
- Desarrollo más intuitivo
- Fácil mantenimiento
- Código más modificable
- Componentes extensibles

### ✅ **Desafíos**

- Curva de aprendizaje
- Dependencia del lenguaje
- Definición de clases (arte)
- Impacto en performance

---

## 📚 **BIBLIOGRAFÍA**

### 🌐 **Páginas Web Consultadas**

**Tutorial de Programación Orientada a Objetos:**  
https://www.desy.de/gna/html/cc/Tutorial/Spanish/tutorial.html

### 📄 **Documento Original**

**Material de referencia principal:**  
https://www.dropbox.com/s/5we40yhv45yv9s1/Programaci%C3%B3n%20Orientada%20a%20Objetos.doc?dl=0

---

## 🎓 **CONCLUSIÓN**

El Paradigma Orientado a Objetos surge como respuesta a las limitaciones de paradigmas anteriores, ofreciendo:

- **Mayor portabilidad** del código
- **Mejor reutilización** de componentes
- **Desarrollo más intuitivo** y natural
- **Facilidad de modificación** y mantenimiento

Aunque presenta desafíos como curvas de aprendizaje largas y dependencias del lenguaje, sus beneficios lo convierten en el paradigma dominante para el desarrollo de software moderno.

Los conceptos fundamentales de **objetos, clases, herencia, encapsulamiento y polimorfismo** forman la base sobre la cual se construyen sistemas complejos y escalables, permitiendo que los programadores modelen el mundo real de manera más efectiva en sus aplicaciones.

---

*Este material forma parte del curso de Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*  
*Equipo docente: Prof. Adj. Esp. Ing. José Farfán, JTP: Mg. Nélida Cáceres - Dr. Federico Medrano, Ay: Lic. Felipe Mullicundo*