# CLASE 2: INTRODUCCIÓN A CONCURRENCIA

**MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE**  
**FAC. DE INGENIERÍA – UNJu – Ing. José Farfán – 2024**  
**Introducción a Concurrencia**

---

## 📋 ÍNDICE

1. [Conceptos Fundamentales](#conceptos-fundamentales)
2. [Programación Concurrente](#programación-concurrente)
3. [Variables Compartidas](#variables-compartidas)
4. [Regiones Críticas](#regiones-críticas)
5. [Bloqueo mediante Variables Compartidas](#bloqueo-mediante-variables-compartidas)
6. [Principios y Algoritmos de Concurrencia](#principios-y-algoritmos-de-concurrencia)

---

## 🎯 CONCEPTOS FUNDAMENTALES

### Definición de Concurrencia

**Concurrencia** es la capacidad del CPU para realizar más de un proceso al mismo tiempo.

```
┌─────────────────────────────────────────────────────────────┐
│                        CONCURRENCIA                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Search 1    Search 2    Search 3    Search 4              │
│     │           │           │           │                  │
│     ▼           ▼           ▼           ▼                  │
│   ┌───┐       ┌───┐       ┌───┐       ┌───┐                │
│   │   │       │   │       │   │       │   │                │
│   │   │       │   │       │   │       │   │                │
│   │   │       │   │       │   │       │   │                │
│   └───┘       └───┘       └───┘       └───┘                │
│     │           │           │           │                  │
│     └───────────┼───────────┼───────────┘                  │
│                 │           │                              │
│                 ▼           ▼                              │
│              ┌─────────────────┐                           │
│              │  Join results   │ ◄─── FIN                 │
│              └─────────────────┘                           │
│                       │                                    │
│                       ▼                                    │
│                  ┌─────────┐                               │
│                  │ Resultado│                               │
│                  └─────────┘                               │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 PROGRAMACIÓN CONCURRENTE

### Definición y Características

> **Notaciones y técnicas de programación se usan para expresar paralelismo potencial entre tareas para resolver los problemas de comunicación y sincronización entre procesos.**

### Conceptos Clave

#### 🔄 Concurrencia vs Paralelismo

**Concurrencia:** Es la capacidad del CPU para realizar más de un proceso al mismo tiempo.

**Paralelismo:** Toma 1 único problema, y con concurrencia llega a 1 solución más rápida. A partir del problema inicial, divide el problema en fracciones más pequeñas, y luego cada fracción es procesada de forma concurrente, aprovechando la capacidad del procesador para resolver el problema.

```
┌─────────────────────────────────────────────────────────────┐
│                    CONCURRENCIA                             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────┐    ┌─────────┐    ┌─────────┐                     │
│  │Tarea│───▶│  CPU 1  │    │Cola de  │                     │
│  │  1  │    │   ▲     │    │Procesos │                     │
│  └─────┘    │   │ ▼   │    │         │                     │
│              │   │ ▼   │    │  ┌───┐  │                     │
│  ┌─────┐    │   │ ▼   │    │  │ 2 │  │                     │
│  │Tarea│───▶│   │ ▼   │    │  │ 3 │  │                     │
│  │  2  │    │   │ ▼   │    │  │ 4 │  │                     │
│  └─────┘    │   │ ▼   │    │  └───┘  │                     │
│              └─────────┘    └─────────┘                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                     PARALELISMO                             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────┐    ┌─────────┐    ┌─────────┐                 │
│  │ Datos   │    │  CPU 1  │    │  CPU 2  │                 │
│  │ Entrada │───▶│    ▲    │    │    ▲    │                 │
│  └─────────┘    │    │    │    │    │    │                 │
│       │         │    ▼    │    │    ▼    │                 │
│       ▼         │ Proceso │    │ Proceso │                 │
│  ┌─────────┐    │    1    │    │    2    │                 │
│  │Dividir  │    │    │    │    │    │    │                 │
│  │Problema │    │    ▼    │    │    ▼    │                 │
│  └─────────┘    └─────────┘    └─────────┘                 │
│       │              │              │                      │
│       ▼              └──────┬───────┘                      │
│  ┌─────────┐                ▼                              │
│  │Fracciones│         ┌─────────────┐                      │
│  │Pequeñas │         │Join Results │                      │
│  └─────────┘         └─────────────┘                      │
│                             │                              │
│                             ▼                              │
│                      ┌─────────────┐                      │
│                      │  Resultado  │                      │
│                      │   Final     │                      │
│                      └─────────────┘                      │
└─────────────────────────────────────────────────────────────┘
```

#### 📊 Comparación de Arquitecturas

```
┌─────────────────────────────────────────────────────────────┐
│              DESCARGA 1   DESCARGA 2   DESCARGA 3   DESCARGA 4│
│                   │           │           │           │     │
│                   ▼           ▼           ▼           ▼     │
│                 ┌───┐       ┌───┐       ┌───┐       ┌───┐   │
│                 │   │       │   │       │   │       │   │   │
│                 │   │       │   │       │   │       │   │   │
│                 └───┘       └───┘       └───┘       └───┘   │
│                   │           │           │           │     │
│                   └───────────┼───────────┼───────────┘     │
│                               │           │                 │
│                               ▼           ▼                 │
│                         ┌─────────────────┐                 │
│                         │      FIN        │                 │
│                         └─────────────────┘                 │
│                               │                             │
│                               ▼                             │
│                         ┌─────────┐  ┌─────────┐            │
│                         │Resultado│  │Resultado│            │
│                         │    1    │  │    2    │            │
│                         └─────────┘  └─────────┘            │
│                         ┌─────────┐  ┌─────────┐            │
│                         │Resultado│  │Resultado│            │
│                         │    3    │  │    4    │            │
│                         └─────────┘  └─────────┘            │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 VARIABLES COMPARTIDAS

### Definición y Problemática

**Variables Compartidas** es el método más sencillo de comunicación entre los procesos de un programa. El acceso concurrente puede hacer que la acción de un proceso interfiera en las acciones de otro de forma no adecuada.

### 🌳 Problema de los Jardines

**Objetivo:** Controlar el número de visitantes a unos jardines.

#### Descripción del Sistema

- La entrada y la salida a los jardines se realiza por **2 puntos** que disponen de puertas giratorias
- Se asocia el **proceso P1** a un punto de **E/S** y el **proceso P2** al otro punto de **E/S**
- Los procesos se ejecutan concurrentemente y usan **1 única variable** para llevar el total de visitantes

#### Operaciones Básicas

```
┌─────────────────────────────────────────────────────────────┐
│                    OPERACIONES JARDÍN                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  • La entrada de un visitante por una de las puertas hace  │
│    que se ejecute:                           x := x + 1    │
│                                                             │
│  • La salida de un visitante hace que se ejecute:          │
│                                             x := x - 1     │
│                                                             │
│  • Si ambas instrucciones se realizan como 1 única         │
│    instrucción HW, no se plantea ningún problema           │
│                                                             │
│  • En un sistema multiprocesador si se arbitran            │
│    mecanismos que impiden que varios procesadores          │
│    accedan a la vez a una misma posición de memoria        │
│    NO hay problema                                          │
│                                                             │
│  • Si se produce la interferencia de 1 proceso en el       │
│    otro la actualización de la variable → se solucionará   │
│    con 1 REGIÓN CRÍTICA                                    │
└─────────────────────────────────────────────────────────────┘
```

#### Diagrama del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                         JARDÍN                             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐                           ┌─────────────┐ │
│  │   Entrada   │            P1             │   Entrada   │ │
│  │   x = x+1   │ ◄─────────────────────────▶│   x = x+1   │ │
│  └─────────────┘                           └─────────────┘ │
│        │                                         │         │
│        ▼                                         ▼         │
│  ┌─────────────┐     Concurrencia +            ┌─────────────┐ │
│  │   Salida    │     única variable x          │   Salida    │ │
│  │   x = x-1   │                               │   x = x-1   │ │
│  └─────────────┘            P2                 └─────────────┘ │
│                                                             │
│              Problema: actualización x → Región Crítica    │
└─────────────────────────────────────────────────────────────┘
```

#### Casos de Ejecución

**✅ No hay Problema:** 1 única instrucción (secuencial) o multiprocesador

**❌ Problema:** actualización x → **Región Crítica**

---

## 🔒 REGIONES CRÍTICAS

### Definición

**Regiones Críticas** son bloques de código que al ser declarados como regiones críticas respecto de 1 variable, el programador o el compilador introduce mecanismos de sincronización necesarios para que su ejecución se realice en un régimen de **exclusión mutua** respecto de otras regiones críticas declaradas respecto de la misma variable.

### Características Principales

#### 🔐 Exclusión Mutua

Garantizar la actualización segura a 1 variable compartida. Si se declara como variable compartida, su acceso solo se puede realizar en regiones críticas y todos los accesos se realizan con **Exclusión Mutua**.

#### 📊 Diagrama de Región de Aceptación

```
┌─────────────────────────────────────────────────────────────┐
│                    REGIONES CRÍTICAS                       │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                    ┌─────────────────┐                     │
│                    │                 │                     │
│               ┌────┤   Región de     ├────┐                │
│               │    │   Aceptación    │    │                │
│               │    │                 │    │                │
│               │    └─────────────────┘    │                │
│               │                           │                │
│          ┌────▼────┐                 ┌────▼────┐           │
│          │ Región  │                 │ Región  │           │
│          │ crítica │                 │ crítica │           │
│          └─────────┘                 └─────────┘           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

#### 🏗️ Estructura de Sección Crítica

```
┌─────────────────────────────────────────────────────────────┐
│                    SECCIÓN CRÍTICA                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                Sección Crítica                     │   │
│  ├─────────────────────────────────────────────────────┤   │
│  │                                                     │   │
│  │  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐ │   │
│  │  │   Proceso   │───▶│   Archivo De    │◄───│   Proceso   │ │   │
│  │  │      A      │    │     Datos       │    │      B      │ │   │
│  │  └─────────────┘    └─────────────────┘    └─────────────┘ │   │
│  │                                                     │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔧 BLOQUEO MEDIANTE VARIABLES COMPARTIDAS

### Implementación de Exclusión Mutua

Implementación del bloqueo a 1 región crítica mediante el uso de **1 variable compartida** indicador o **flag**.

#### 🔄 Algoritmo de Exclusión Mutua: Uso de 1 indicador

```pascal
(*Exclusión Mutua: Uso de 1indicador*)
module Exclusion_Mutua_1;
varilag: boolean;
process P1
begin
    loop
        while flag = true do
            (* Espera a que el dispositivo se libere *)
        end;
        flag := true;
        (* Uso del recurso Región Crítica *)
        flag := false;
        (* resto del proceso *)
    end
end P1;
```

#### 🔄 Proceso P2

```pascal
process P2
begin
    loop
        while flag = true do
            (* Espera a que el dispositivo se libere *)
        end;
        flag := true;
        (* Uso del recurso Región Crítica *)
        flag := false;
        (* resto del proceso *)
    end
end P2;

begin (* Exclusion_Mutua_1 *)
    flag := false;
    cobegin
        P1; P2;
    coend
end Exclusion_Mutua_1.
```

### 📋 Estructura del Algoritmo

```
┌─────────────────────────────────────────────────────────────┐
│            ALGORITMO DE EXCLUSIÓN MUTUA                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │    PROCESO P1   │              │    PROCESO P2   │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. Check flag   │              │ 1. Check flag   │       │
│  │ 2. Set flag     │◄────────────▶│ 2. Set flag     │       │
│  │ 3. Use resource │   Shared     │ 3. Use resource │       │
│  │ 4. Clear flag   │   Variable   │ 4. Clear flag   │       │
│  │ 5. Continue     │    (flag)    │ 5. Continue     │       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                VARIABLE COMPARTIDA                 │   │
│  │                flag: boolean                       │   │
│  │                                                     │   │
│  │  • true  = Recurso ocupado                         │   │
│  │  • false = Recurso libre                           │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 PRINCIPIOS Y ALGORITMOS DE CONCURRENCIA

### Objetivos de la Programación Concurrente

1. **🔒 Exclusión Mutua**: Garantizar que solo un proceso acceda a un recurso compartido a la vez
2. **⚡ Eficiencia**: Maximizar el uso de recursos del sistema
3. **🔄 Sincronización**: Coordinar la ejecución de procesos concurrentes
4. **🛡️ Prevención de Deadlocks**: Evitar bloqueos indefinidos
5. **⚖️ Fairness**: Garantizar acceso equitativo a los recursos

### Elementos Clave del Sistema

#### 🏗️ Componentes Fundamentales

```
┌─────────────────────────────────────────────────────────────┐
│                 COMPONENTES DEL SISTEMA                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │   PROCESOS  │    │  RECURSOS   │    │MECANISMOS DE│     │
│  │             │    │COMPARTIDOS  │    │SINCRONIZACIÓN│    │
│  │ • P1, P2... │◄──▶│             │◄──▶│             │     │
│  │ • Concurren │    │ • Variables │    │ • Semáforos │     │
│  │ • Independ. │    │ • Archivos  │    │ • Monitores │     │
│  │             │    │ • Disposi.  │    │ • Mensajes  │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 📚 Próximos Temas

En las siguientes clases profundizaremos en:

- **Semáforos**: Mecanismos de sincronización avanzados
- **Monitores**: Estructuras de alto nivel para concurrencia
- **Paso de Mensajes**: Comunicación entre procesos
- **Deadlocks**: Detección y prevención
- **Algoritmos de Sincronización**: Peterson, Dekker, etc.

---

## 📖 BIBLIOGRAFÍA

1. **Andrews, G.R.** - "Concurrent Programming: Principles and Practice"
2. **Ben-Ari, M.** - "Principles of Concurrent and Distributed Programming"
3. **Silberschatz, A., Galvin, P.B., Gagne, G.** - "Operating System Concepts"
4. **Tanenbaum, A.S.** - "Modern Operating Systems"
5. **Burns, A., Davies, G.** - "Concurrent Programming"

---

## 📝 RESUMEN EJECUTIVO

### Conceptos Clave Aprendidos

✅ **Concurrencia**: Capacidad de ejecutar múltiples procesos simultáneamente  
✅ **Paralelismo**: División de problemas para resolución concurrente  
✅ **Variables Compartidas**: Método básico de comunicación entre procesos  
✅ **Regiones Críticas**: Bloques de código con acceso exclusivo  
✅ **Exclusión Mutua**: Mecanismo para prevenir interferencias  
✅ **Algoritmos de Bloqueo**: Implementación práctica de sincronización  

### Próximos Pasos

🔜 Implementación de algoritmos más sofisticados  
🔜 Estudio de casos prácticos  
🔜 Análisis de rendimiento  
🔜 Herramientas de debugging concurrente  

---

## 🚫 PROBLEMAS CON EL ALGORITMO DE 1 INDICADOR

### ⚠️ Limitaciones del Método Básico

**RESULTADO DEL BLOQUEO:** NO RESUELVE EL PROBLEMA DE LA EXCLUSIÓN MUTUA, ya que al ser la comprobación y la puesta del indicador 2 accesos separados, puede ocurrir que se entrelace el uso del recurso de ambos procesos.

**SOLUCIÓN:** 2 INDICADORES para resolver el problema de la exclusión mutua

---

## 🔄 ALGORITMO DE EXCLUSIÓN MUTUA: USO DE 2 INDICADORES

### Implementación Mejorada

#### 📋 Estructura del Módulo

```pascal
(* Exclusión Mutua: Uso de dos indicadores *)
module Exclusion_Mutua_2;
var flag1, flag2: boolean;
procedure bloqueo(var mi_flag, su_flag: boolean);
begin
    mi_flag := true; (* intención de usar el recurso *)
    while su_flag do
    end; (* espera a que se libere el recurso *)
end bloqueo;

procedure desbloqueo(var mi_flag: boolean);
begin
    mi_flag := false;
end desbloqueo;
```

#### 🔄 Proceso P1

```pascal
process P1
begin
    loop
        bloqueo(flag1, flag2);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag1);
        (* resto del proceso *)
    end
end P1;
```

#### 🔄 Proceso P2

```pascal
process P2
begin
    loop
        bloqueo(flag2, flag1);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag2);
        (* resto del proceso *)
    end
end P2;

begin (* Exclusion_Mutua_2 *)
    flag1 := FALSE;
    flag2 := FALSE;
    cobegin
        P1;
        P2;
    coend
end Exclusion_Mutua_2.
```

### 📊 Diagrama del Algoritmo de 2 Indicadores

```
┌─────────────────────────────────────────────────────────────┐
│         ALGORITMO DE EXCLUSIÓN MUTUA: 2 INDICADORES        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │    PROCESO P1   │              │    PROCESO P2   │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. flag1 = true │              │ 1. flag2 = true │       │
│  │ 2. while flag2  │◄────────────▶│ 2. while flag1  │       │
│  │ 3. Use resource │   Variables  │ 3. Use resource │       │
│  │ 4. flag1 = false│   flag1,flag2│ 4. flag2 = false│       │
│  │ 5. Continue     │              │ 5. Continue     │       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              VARIABLES COMPARTIDAS                 │   │
│  │                                                     │   │
│  │  • flag1: boolean (intención de P1)                │   │
│  │  • flag2: boolean (intención de P2)                │   │
│  │                                                     │   │
│  │  • true  = Proceso quiere usar recurso             │   │
│  │  • false = Proceso no necesita recurso             │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## ⚠️ INCONVENIENTES DEL ALGORITMO DE 2 INDICADORES

### 🔄 Problemas de Concurrencia

#### 📋 Limitaciones Identificadas

• **Durante la espera de la liberación del recurso**, el proceso permanece ocupado (**busy wait**)

• **Si ambos procesos realizan la llamada al bloqueo de forma simultánea**, cada proceso puede poner su propio indicador y comprobar el estado del otro. Ambos ven los indicadores contrarios como ocupados y permanecerán a la espera de que el recurso quede liberado, pero esto no podrá suceder al no poder entrar ninguno en su sección crítica. Esta acción se llama:

#### 🚫 Interbloqueo o Deadlock

**Definición:** Ocurre cuando la desactivación del indicador asociado a un proceso se produce una vez que se ha completado el acceso a la región crítica.

#### 💡 Solución Propuesta

**Solución:** Que el proceso desactive su propio indicador durante la fase de bloqueo siempre que encuentre que el indicador del otro proceso está activado.

```pascal
procedure bloqueo(var mi_flag, su_flag: boolean);
begin
    mi_flag := true;
    while su_flag do
        mi_flag := false;
        mi_flag := true;
    end
end bloqueo;
```

### 🔄 Diagrama de Deadlock

```
┌─────────────────────────────────────────────────────────────┐
│                    PROBLEMA DE DEADLOCK                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐                           ┌─────────────┐ │
│  │ PROCESO P1  │                           │ PROCESO P2  │ │
│  ├─────────────┤                           ├─────────────┤ │
│  │             │                           │             │ │
│  │ flag1 = true│ ────────┐         ┌────── │ flag2 = true│ │
│  │             │         │         │       │             │ │
│  │ while flag2 │ ◄───────┼─────────┼─────► │ while flag1 │ │
│  │    do wait  │         │         │       │    do wait  │ │
│  │             │         ▼         ▼       │             │ │
│  └─────────────┘    ┌─────────────────┐    └─────────────┘ │
│                     │   DEADLOCK!     │                    │
│                     │                 │                    │
│                     │ Ambos procesos  │                    │
│                     │ esperan que el  │                    │
│                     │ otro libere el  │                    │
│                     │ recurso         │                    │
│                     └─────────────────┘                    │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 ALGORITMO DE PETERSON (1981)

### Descripción

**Introduce 1 variable adicional (turno)** para el problema de petición simultánea de acceso a la región crítica.

#### 📋 Estructura del Módulo Peterson

```pascal
(* Exclusión Mutua: Solución de Peterson *)
Module Exclusion_Mutua_P;
var flag1, flag2: boolean;    turno: integer;
procedure bloqueo(var mi_flag, su_flag: boolean; su_turno: integer);
begin
    mi_flag := true;
    turno := su_turno;
    while su_flag and (turno = su_turno) do ; end
end bloqueo;

procedure desbloqueo(var mi_flag: boolean);
begin
    mi_flag := false;
end desbloqueo;
```

#### 🔄 Proceso P1

```pascal
process P1
begin
    loop bloqueo(flag1, flag2, 2);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag1); (* resto del proceso *)
    end
end P1;
```

#### 🔄 Proceso P2

```pascal
process P2
begin
    loop bloqueo(flag2, flag1, 1);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag2);
        (* resto del proceso *)
    end
end P2;

begin (* Exclusion_Mutua_P *)
    flag1 := FALSE;
    flag2 := FALSE;
    cobegin
        P1; P2;
    coend
end Exclusion_Mutua_P;
```

### 📊 Diagrama del Algoritmo de Peterson

```
┌─────────────────────────────────────────────────────────────┐
│              ALGORITMO DE PETERSON (1981)                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │    PROCESO P1   │              │    PROCESO P2   │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. flag1 = true │              │ 1. flag2 = true │       │
│  │ 2. turno = 2    │◄────────────▶│ 2. turno = 1    │       │
│  │ 3. while flag2  │   Variables  │ 3. while flag1  │       │
│  │    and turno=2  │ flag1,flag2, │    and turno=1  │       │
│  │ 4. Use resource │    turno     │ 4. Use resource │       │
│  │ 5. flag1 = false│              │ 5. flag2 = false│       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                VARIABLES DE CONTROL                │   │
│  │                                                     │   │
│  │  • flag1, flag2: boolean (intenciones)             │   │
│  │  • turno: integer (prioridad de acceso)            │   │
│  │                                                     │   │
│  │  VENTAJA: Resuelve el problema de deadlock         │   │
│  │  mediante la variable de turno                     │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 ALGORITMO DE DEKKER (1965)

### Descripción

**Basado en Dijkstra 1968** - Implementa **1 variable turno** para establecer la prioridad relativa de 2 procesos y su actualización se realiza en la sección crítica lo que evita que pueda haber interferencias entre procesos.

#### 📋 Estructura del Módulo Dekker

```pascal
(* Exclusión Mutua: Solución de Dekker *)
moduleExclusion_Mutua_D;
var flag1, flag2: boolean;    turno: integer;
procedure bloqueo(var mi_flag, su_flag: boolean; su_turno: integer);
begin
    mi_flag := true;
    while su_flag do (* otro proceso en la sección crítica *)
        if turno = su_turno then
            mi_flag := false;
            while turno = su_turno do (* espera que el otro acabe *)
            end;
            mi_flag := true; end;
    end;
end bloqueo

procedure desbloqueo(var mi_flag: boolean; su_turno: integer);
begin
    turno := su_turno;    mi_flag := false
end desbloqueo;
```

#### 🔄 Proceso P1

```pascal
process P1
begin
    loop bloqueo(flag1, flag2, 2);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag1); (* resto del proceso *)
    end
end P1;
```

#### 🔄 Proceso P2

```pascal
process P2
begin
    loop
        bloqueo(flag2, flag1, 1);
        (* Uso del recurso Sección Crítica *)
        desbloqueo(flag2);
        (* resto del proceso *) end
end P2;

begin (* Exclusion_Mutua_D *)
    flag1 := FALSE; flag2 := FALSE;
    turno := 1;
    cobegin P1; P2; coend
end Exclusion_Mutua_D.0
```

### 📊 Diagrama del Algoritmo de Dekker

```
┌─────────────────────────────────────────────────────────────┐
│            ALGORITMO DE DEKKER (1965)                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │    PROCESO P1   │              │    PROCESO P2   │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. flag1 = true │              │ 1. flag2 = true │       │
│  │ 2. while flag2  │◄────────────▶│ 2. while flag1  │       │
│  │ 3. if turno=2   │   Variables  │ 3. if turno=1   │       │
│  │    flag1=false  │ flag1,flag2, │    flag2=false  │       │
│  │ 4. Use resource │    turno     │ 4. Use resource │       │
│  │ 5. turno=2      │              │ 5. turno=1      │       │
│  │ 6. flag1=false  │              │ 6. flag2=false  │       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                CARACTERÍSTICAS                      │   │
│  │                                                     │   │
│  │  • Primer algoritmo correcto de exclusión mutua    │   │
│  │  • Actualización de turno en sección crítica       │   │
│  │  • Evita interferencias entre procesos             │   │
│  │  • Base histórica para algoritmos posteriores      │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚦 SEMÁFOROS

### Introducción Histórica

**Edsger Dijkstra 1965** - usado por primera vez en SO Theos 1974

**Resuelve la mayoría de los problemas de sincronización** entre procesos y forma parte del diseño de muchos SO y de lenguajes de programación concurrente.

### Definición y Características

#### 🔢 Semáforo Binario

**Un semáforo binario es un indicador (S) de condición que registra si un recurso está disponible o no.**

- **Semáforo binario** solo puede tomar **2 valores: 0 y 1**
- Si el **semáforo binario S=1** el recurso está disponible y la tarea lo puede usar
- Si **S=0** el recurso no está disponible y el proceso debe esperar

#### 🏗️ Implementación con Cola de Tareas

**Los semáforos se implementan con una cola de tareas o de condición** a la cual se añaden los procesos que están en espera del recurso.

### 🔄 Operaciones Básicas

Solo se permiten **3 operaciones**:

#### 1. 🏁 **inicializa** (S: SemaforoBinario; v: integer)

Pone el valor del semáforo S al valor v (0 o 1)

#### 2. ⏸️ **espera (S)** 🛑

```
if S = 1 then S := 0
    else suspende la tarea que hace la
         llamada y pone en cola de tareas
```

#### 3. 📤 **señal (S)** ➡️

```
if la cola de tareas está vacía then S := 1
else reanudar la primera tarea de la cola de tareas
```

### 📊 Diagrama de Estados del Semáforo

```
┌─────────────────────────────────────────────────────────────┐
│              TRANSICIONES DE ESTADO - SEMÁFOROS            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                        Espera                              │
│                    ┌──────────┐                            │
│               ┌────│  Espera  │◄────┐                       │
│               │    └──────────┘     │                       │
│               │                     │                       │
│               ▼                     │                       │
│    Señal  ┌──────────┐         ┌──────────┐                │
│      ┌────│  Listo   │         │Ejecución │                │
│      │    └──────────┘         └──────────┘                │
│      │         ▲                     │                     │
│      │         │                     │                     │
│      ▼         │                     ▼                     │
│ ┌──────────┐   │                ┌──────────┐               │
│ │Suspendido│   │                │Durmiente │               │
│ └──────────┘   │                └──────────┘               │
│      │         │                     ▲                     │
│      │         │                     │                     │
│      └─────────┴─────────────────────┘                     │
│                                                             │
│  Estados principales:                                       │
│  • Espera: Proceso solicitando recurso                     │
│  • Listo: Recurso disponible, proceso puede ejecutar       │
│  • Ejecución: Proceso usando el recurso                    │
│  • Suspendido: Proceso en cola esperando                   │
│  • Durmiente: Proceso inactivo                             │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Ventajas de los Semáforos

✅ **Eliminan el busy wait**: Los procesos no consumen CPU mientras esperan  
✅ **Gestión automática de colas**: El SO maneja las colas de espera  
✅ **Operaciones atómicas**: Las operaciones espera() y señal() son indivisibles  
✅ **Flexibilidad**: Permiten sincronización compleja entre múltiples procesos  
✅ **Eficiencia**: Mejor uso de recursos del sistema  

### 🔄 Ejemplo de Uso

```pascal
(* Ejemplo básico con semáforos *)
var mutex: SemaforoBinario;

begin
    inicializa(mutex, 1);  (* Recurso inicialmente disponible *)
    
    (* Proceso que quiere acceder al recurso *)
    espera(mutex);         (* Solicitar acceso *)
    (* SECCIÓN CRÍTICA *)  (* Usar el recurso *)
    señal(mutex);          (* Liberar recurso *)
end;
```

---

## 📚 ALGORITMOS AVANZADOS DE CONCURRENCIA - RESUMEN

### 🔄 Evolución de los Algoritmos

```
┌─────────────────────────────────────────────────────────────┐
│                EVOLUCIÓN DE ALGORITMOS                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1960s  │  1970s  │  1980s  │  1990s+                      │
│         │         │         │                              │
│ ┌─────┐ │ ┌─────┐ │ ┌─────┐ │ ┌─────────┐                  │
│ │1 Flag│ │ │2Flag│ │ │Peter│ │ │Semáforos│                  │
│ │     │ │ │     │ │ │son  │ │ │ y       │                  │
│ │❌   │ │ │⚠️   │ │ │✅   │ │ │Monitores│                  │
│ └─────┘ │ └─────┘ │ └─────┘ │ │✅       │                  │
│         │         │         │ └─────────┘                  │
│         │ ┌─────┐ │         │                              │
│         │ │Dekker│ │         │                              │
│         │ │✅   │ │         │                              │
│         │ └─────┘ │         │                              │
│                                                             │
│  Problemas:       Soluciones:                              │
│  • Busy Wait      • Variables de turno                     │
│  • Deadlock       • Operaciones atómicas                  │
│  • Starvation     • Colas de procesos                     │
│                   • Gestión automática del SO             │
└─────────────────────────────────────────────────────────────┘
```

### 📊 Comparación de Algoritmos

| Algoritmo | Año | Variables | Deadlock | Starvation | Busy Wait |
|-----------|-----|-----------|----------|------------|-----------|
| 1 Flag    | ~1960 | 1 boolean | ❌ No previene | ❌ Posible | ✅ Sí |
| 2 Flags   | ~1965 | 2 boolean | ❌ Posible | ❌ Posible | ✅ Sí |
| Dekker    | 1965 | 2 bool + int | ✅ Previene | ✅ Previene | ✅ Sí |
| Peterson  | 1981 | 2 bool + int | ✅ Previene | ✅ Previene | ✅ Sí |
| Semáforos | 1965/1974 | Estructura + Cola | ✅ Previene | ✅ Previene | ❌ No |

---

## 🚦 SEMÁFOROS - EXCLUSIÓN MUTUA

### Implementación Práctica

**La operación `espera` se usa como procedimiento de bloqueo** antes de acceder a 1 región crítica

**La operación `señal` como procedimiento de desbloqueo**. Se usan tantos semáforos como clases de secciones críticas se establezcan. El proceso P1 se escribe:

#### 🔄 Estructura Básica del Proceso

```pascal
process P1
begin
    loop
        espera(S);
        (* Región Crítica *)
        señal(S);
        (* resto del proceso *)
    end
end P1;
```

### 📊 Diagrama de Exclusión Mutua con Semáforos

```
┌─────────────────────────────────────────────────────────────┐
│              EXCLUSIÓN MUTUA CON SEMÁFOROS                 │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │    PROCESO P1   │              │    PROCESO P2   │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. espera(S)    │              │ 1. espera(S)    │       │
│  │ 2. REGIÓN       │◄────────────▶│ 2. REGIÓN       │       │
│  │    CRÍTICA      │   Semáforo S │    CRÍTICA      │       │
│  │ 3. señal(S)     │              │ 3. señal(S)     │       │
│  │ 4. Resto del    │              │ 4. Resto del    │       │
│  │    proceso      │              │    proceso      │       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 SEMÁFORO S                         │   │
│  │                                                     │   │
│  │  • Valor inicial: 1 (recurso disponible)           │   │
│  │  • espera(S): Solicitar acceso exclusivo           │   │
│  │  • señal(S): Liberar recurso                       │   │
│  │  • Cola automática: Gestión del SO                 │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 SEMÁFOROS - SINCRONIZACIÓN

### Sincronización entre Procesos

**Espera y señal no se usan en un mismo proceso**, sino en 2 procesos separados; el que ejecuta la operación de espera queda bloqueado hasta que el otro proceso ejecuta la operación de señal.

**Ejemplo:** Cómo implementar una sincronización entre 2 procesos con semáforo.

#### 📋 Estructura del Módulo de Sincronización

```pascal
module Sincronización; (* Sincronización con semáforo *)
var sincro: semaforo;

process P1 (* Proceso que espera *)
begin
    ....
    espera(sincro);
    ....
end P1;

process P2 (* Proceso que señala *)
begin
    ....
    señal(sincro);
    ....
end P2;

begin (* Sincronización *)
    inicializa(sincro, 0);
    cobegin
        P1;
        P2;
    coend
end Sincronizacion.
```

### 📊 Diagrama de Sincronización

```
┌─────────────────────────────────────────────────────────────┐
│                SINCRONIZACIÓN CON SEMÁFOROS                │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │ PROCESO P1      │              │ PROCESO P2      │       │
│  │ (Espera)        │              │ (Señala)        │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ 1. Ejecuta      │              │ 1. Ejecuta      │       │
│  │    tareas       │              │    tareas       │       │
│  │ 2. espera(      │              │ 2. Completa     │       │
│  │    sincro) ──┐  │              │    trabajo      │       │
│  │              │  │              │ 3. señal(       │       │
│  │ 3. BLOQUEADO │  │◄─────────────┤    sincro)      │       │
│  │    hasta     │  │   Semáforo   │ 4. Continúa     │       │
│  │    señal     │  │   sincro=0   │    proceso      │       │
│  │              │  │              │                 │       │
│  │ 4. Continúa  ◄──┘              │                 │       │
│  │    ejecución    │              │                 │       │
│  │                 │              │                 │       │
│  └─────────────────┘              └─────────────────┘       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              CARACTERÍSTICAS                        │   │
│  │                                                     │   │
│  │  • Semáforo inicializado en 0                      │   │
│  │  • P1 se bloquea hasta que P2 ejecute señal        │   │
│  │  • Sincronización punto a punto                    │   │
│  │  • No hay busy wait                                │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏭 PROBLEMA PRODUCTOR-CONSUMIDOR

### Descripción del Problema

**Hay 2 procesos P1 y P2:** P1 produce datos y consume P2.

**P1 almacena datos en algún sitio hasta que P2 esté listo para usarlos**

**Ejemplo:** P1 genera información para 1 impresora y P2 es el proceso gestor de la impresora que imprime. Para almacenar los datos se dispone de **1 buffer (zona de memoria común)** al productor y al consumidor. Para almacenar y tomar datos se dispone de las **funciones Poner(x) y Tomar(x)**. Para saber el estado del buffer se usa la **funciones Lleno** (devuelve TRUE si el buffer está lleno) y **Vacío** (devuelve TRUE si el buffer está vacío).

#### 🚫 Problema Sin Semáforos

```pascal
(* Problema del Productor-Consumidor: Sin Semáforos *)
module Productor_Consumidor;
var BufferComun: buffer;

process Productor;
var x: dato;
begin
    loop
        produce(x);
        while Lleno do (* espera *) end;
        Poner(x);
    end
end Productor;

process Consumidor;
var x: dato;
begin
    loop
        while Vacío do (* espera *) end;
        Tomar(x);
        Consume(x)
    end
end Consumidor;

begin
    cobegin
        Productor; Consumidor;
    coend
end Productor_Consumidor;
```

### 📊 Diagrama del Buffer

```
┌─────────────────────────────────────────────────────────────┐
│                PROBLEMA PRODUCTOR-CONSUMIDOR               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  PRODUCTOR  │    │   BUFFER    │    │ CONSUMIDOR  │     │
│  │     P1      │    │   COMÚN     │    │     P2      │     │
│  ├─────────────┤    ├─────────────┤    ├─────────────┤     │
│  │             │    │             │    │             │     │
│  │ 1. produce  │    │ ┌─────────┐ │    │ 1. while    │     │
│  │    (x)      │───▶│ │ Dato 1  │ │───▶│    Vacío    │     │
│  │ 2. while    │    │ ├─────────┤ │    │ 2. Tomar(x) │     │
│  │    Lleno    │    │ │ Dato 2  │ │    │ 3. Consume  │     │
│  │ 3. Poner(x) │    │ ├─────────┤ │    │    (x)      │     │
│  │ 4. Repetir  │    │ │ Dato 3  │ │    │ 4. Repetir  │     │
│  │             │    │ └─────────┘ │    │             │     │
│  │             │    │             │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                  PROBLEMAS                          │   │
│  │                                                     │   │
│  │  • Busy wait en ambos procesos                     │   │
│  │  • Condiciones de carrera en buffer                │   │
│  │  • No hay exclusión mutua                          │   │
│  │  • Ineficiencia en uso de CPU                      │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔧 SOLUCIÓN CON SEMÁFOROS - PRODUCTOR-CONSUMIDOR

### Inconvenientes del Método Básico

**1.- Poner(x) y Tomar(x) usan el mismo buffer ==> EXCLUSIÓN MUTUA**

**2.- Ambos procesos usan una espera ocupada cuando no pueden acceder al buffer**

#### 💡 Solución con Semáforos

```pascal
(* Problema del Productor-Consumidor: Con semáforos *)
module Productor_Consumidor;
var BufferComun: buffer;
    AccesoBuffer, NoLleno, NoVacio: semaforo;

process Productor;
var x: dato;
begin
    loop
        produce(x);
        espera(AccesoBuffer);
        if Lleno then
            señal(AccesoBuffer);
            espera(NoLleno);
            espera(AccesoBuffer)
        end;
        Poner(x);
        señal(AccesoBuffer);
        señal(NoVacio)
    end
end Productor;

process Consumidor;
var x: dato;
begin
    loop
        espera(AccesoBuffer);
        if Vacío then
            señal(AccesoBuffer);
            espera(NoVacio);
            espera(AccesoBuffer)
        end;
        Tomar(x);
        señal(AccesoBuffer);
        señal(NoLleno);
        Consume(x)
    end
end Consumidor;

begin
    inicializa(AccesoBuffer, 1);
    inicializa(NoLleno, 1);
    inicializa(NoVacio, 0);
    cobegin
        Productor; Consumidor;
    coend
end Productor_Consumidor;
```

### 📊 Diagrama de Solución con Semáforos

```
┌─────────────────────────────────────────────────────────────┐
│           PRODUCTOR-CONSUMIDOR CON SEMÁFOROS               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  PRODUCTOR  │    │   BUFFER    │    │ CONSUMIDOR  │     │
│  ├─────────────┤    │   COMÚN     │    ├─────────────┤     │
│  │             │    │             │    │             │     │
│  │ espera(     │    │ ┌─────────┐ │    │ espera(     │     │
│  │ AccesoBuf)  │───▶│ │ DATOS   │ │◄───│ AccesoBuf)  │     │
│  │ if Lleno    │    │ │ SEGUROS │ │    │ if Vacío    │     │
│  │ Poner(x)    │    │ └─────────┘ │    │ Tomar(x)    │     │
│  │ señal(      │    │             │    │ señal(      │     │
│  │ AccesoBuf)  │    │             │    │ AccesoBuf)  │     │
│  │ señal(      │    │             │    │ señal(      │     │
│  │ NoVacio)    │    │             │    │ NoLleno)    │     │
│  │             │    │             │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                  SEMÁFOROS                          │   │
│  │                                                     │   │
│  │  • AccesoBuffer = 1 (exclusión mutua)              │   │
│  │  • NoLleno = 1 (buffer no lleno)                   │   │
│  │  • NoVacio = 0 (buffer inicialmente vacío)         │   │
│  │                                                     │   │
│  │  VENTAJAS:                                          │   │
│  │  • No hay busy wait                                 │   │
│  │  • Exclusión mutua garantizada                     │   │
│  │  • Sincronización automática                       │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔢 SEMÁFOROS PARA N RECURSOS DISPONIBLES

### Semáforos Generales (No Binarios)

**El semáforo se inicializa con el número total de recursos disponibles (N)**

**Espera y señal se diseñan para que se impida el acceso al recurso protegido por el semáforo** cuando el valor de éste es menor o igual que cero.

**Si se obtiene 1 recurso el semáforo se decrementa y se incrementa cuando 1 de ellos se libera.**

**Si la operación de espera se ejecuta cuando el semáforo tiene 1 valor menor que 1**, el proceso debe quedar en espera de que la ejecución de 1 operación señal libere alguno de los recursos.

### 🔄 Operaciones para Semáforos Generales

#### 🏁 **inicializa** (S: SemaforoBinario; v: integer)

```
S = N
numero_suspendidos := 0
```

#### ⏸️ **espera (S)** 🛑

```
if S > 0 then S := S-1
else    numero_suspendidos := numero_suspendidos+1;
        suspende la tarea que hace la llamada y poner en la cola de tareas
```

#### 📤 **señal (S)** ➡️

```
if numero_suspendidos > 0 then
    numero_suspendidos := numero_suspendidos - 1
    pasa al estado listo un proceso suspendido
else S := S + 1
```

### 📊 Diagrama de Semáforos Generales

```
┌─────────────────────────────────────────────────────────────┐
│              SEMÁFOROS PARA N RECURSOS                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 SEMÁFORO S = N                      │   │
│  │                                                     │   │
│  │  Valor inicial: N (recursos disponibles)           │   │
│  │  ┌─────────────────────────────────────────────┐   │   │
│  │  │  OPERACIÓN espera(S)                        │   │   │
│  │  │                                             │   │   │
│  │  │  if S > 0 then                              │   │   │
│  │  │      S := S - 1                            │   │   │
│  │  │      (* Recurso asignado *)                 │   │   │
│  │  │  else                                       │   │   │
│  │  │      numero_suspendidos++                   │   │   │
│  │  │      (* Proceso a cola de espera *)         │   │   │
│  │  └─────────────────────────────────────────────┘   │   │
│  │                                                     │   │
│  │  ┌─────────────────────────────────────────────┐   │   │
│  │  │  OPERACIÓN señal(S)                         │   │   │
│  │  │                                             │   │   │
│  │  │  if numero_suspendidos > 0 then             │   │   │
│  │  │      numero_suspendidos--                   │   │   │
│  │  │      (* Despertar proceso *)                │   │   │
│  │  │  else                                       │   │   │
│  │  │      S := S + 1                            │   │   │
│  │  │      (* Incrementar recursos *)             │   │   │
│  │  └─────────────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                    EJEMPLO                          │   │
│  │                                                     │   │
│  │  Impresoras disponibles: N = 3                     │   │
│  │                                                     │   │
│  │  Proceso 1: espera(S) → S = 2 (usa impresora 1)    │   │
│  │  Proceso 2: espera(S) → S = 1 (usa impresora 2)    │   │
│  │  Proceso 3: espera(S) → S = 0 (usa impresora 3)    │   │
│  │  Proceso 4: espera(S) → BLOQUEADO (espera)         │   │
│  │                                                     │   │
│  │  Proceso 1: señal(S) → Despierta Proceso 4         │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Ventajas de Semáforos Generales

✅ **Gestión múltiple**: Control de N recursos idénticos  
✅ **Eficiencia**: Asignación automática de recursos  
✅ **Escalabilidad**: Fácil ajuste del número de recursos  
✅ **Fairness**: Cola FIFO para procesos en espera  
✅ **Flexibilidad**: Aplicable a diversos tipos de recursos  

### 🔄 Casos de Uso Comunes

- **Impresoras**: Control de acceso a múltiples impresoras
- **Conexiones de red**: Límite de conexiones simultáneas
- **Memoria**: Gestión de bloques de memoria
- **Threads**: Control de pool de hilos de ejecución
- **Licencias**: Software con límite de usuarios concurrentes

---

## 📚 RESUMEN DE SEMÁFOROS

### 🔄 Tipos de Semáforos

```
┌─────────────────────────────────────────────────────────────┐
│                    TIPOS DE SEMÁFOROS                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │   BINARIOS      │              │   GENERALES     │       │
│  │   (0 o 1)       │              │   (0 a N)       │       │
│  ├─────────────────┤              ├─────────────────┤       │
│  │                 │              │                 │       │
│  │ • Exclusión     │              │ • N recursos    │       │
│  │   mutua         │              │   idénticos     │       │
│  │ • Sincronización│              │ • Pool de       │       │
│  │   punto a punto │              │   recursos      │       │
│  │ • Valor: 0 o 1  │              │ • Valor: 0 a N  │       │
│  │                 │              │                 │       │
│  │ Casos de uso:   │              │ Casos de uso:   │       │
│  │ • Mutex         │              │ • Impresoras    │       │
│  │ • Barreras      │              │ • Conexiones    │       │
│  │ • Señales       │              │ • Memoria       │       │
│  │                 │              │ • Licencias     │       │
│  └─────────────────┘              └─────────────────┘       │
└─────────────────────────────────────────────────────────────┘
```

### 📊 Comparación de Soluciones

| Método | Exclusión Mutua | Sincronización | Busy Wait | Complejidad |
|--------|-----------------|----------------|-----------|-------------|
| Variables compartidas | ⚠️ Limitada | ❌ No | ✅ Sí | 🔴 Alta |
| Algoritmo Peterson | ✅ Sí | ❌ No | ✅ Sí | 🟡 Media |
| Algoritmo Dekker | ✅ Sí | ❌ No | ✅ Sí | 🟡 Media |
| Semáforos binarios | ✅ Sí | ✅ Sí | ❌ No | 🟢 Baja |
| Semáforos generales | ✅ Sí | ✅ Sí | ❌ No | 🟢 Baja |

---

## 📺 MONITORES

### Definición y Características

**Procedimientos que proporcionan el acceso con EXCLUSIÓN MUTUA a un recurso o conjunto de recursos compartidos por 1 grupo de procesos.** Los procedimientos se encapsulan a 1 módulo que tiene la propiedad de que **sólo un proceso puede estar activo cada vez que se ejecuta un procedimiento del monitor.**

**El monitor se puede ver como una valla alrededor del recurso** de modo que los procesos que quieran utilizarlo deben entrar dentro de la valla, pero en la forma que impone el monitor.

**Muchos procesos pueden querer entrar en distintos instantes de tiempo, pero sólo se permite a entrar un proceso cada vez**, debiendo esperar a que salga el que está dentro.

### 🔐 Exclusión Mutua Implícita

**La exclusión mutua está implícita:** la única acción que debe realizar el programador del proceso que usa un recurso es invocar una entrada del monitor. Si el monitor se ha codificado correctamente **NO puede ser utilizado incorrectamente por un programa de aplicación que desee usar el recurso.**

### 🔄 Variables de Condición

**Los monitores NO proporcionan por sí mismos 1 mecanismo para sincronizar tareas** y por ello su construcción se completa usando **señales o variable de condición para sincronizar los procesos.**

### 📊 Diagrama Conceptual del Monitor

```
┌─────────────────────────────────────────────────────────────┐
│                         MONITOR                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 VALLA PROTECTORA                    │   │
│  │                                                     │   │
│  │  ┌─────────────────────────────────────────────┐  │   │
│  │  │              RECURSO COMPARTIDO              │  │   │
│  │  │                                             │  │   │
│  │  │  • Variables locales del monitor           │  │   │
│  │  │  • Variables de condición                   │  │   │
│  │  │  • Procedimientos de acceso                │  │   │
│  │  │                                             │  │   │
│  │  │  ┌───────────────────────────────────────┐  │  │   │
│  │  │  │         ZONA DE EXCLUSIÓN MUTUA        │  │  │   │
│  │  │  │                                       │  │  │   │
│  │  │  │  Sólo 1 proceso puede estar activo   │  │  │   │
│  │  │  │  ejecutando procedimientos del        │  │  │   │
│  │  │  │  monitor en cualquier momento         │  │  │   │
│  │  │  └───────────────────────────────────────┘  │  │   │
│  │  └─────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 PROCESOS EN ESPERA                  │   │
│  │                                                     │   │
│  │  ┌─────────┐  ┌─────────┐  ┌─────────┐      │   │
│  │  │Proceso P2│  │Proceso P3│  │Proceso P4│      │   │
│  │  │ (espera)  │  │ (espera)  │  │ (espera)  │      │   │
│  │  └─────────┘  └─────────┘  └─────────┘      │   │
│  │                     ▲                               │   │
│  │                     │                               │   │
│  │              Esperan turno                          │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 📝 MONITOR - SINTAXIS

### Estructura del Monitor

```pascal
monitor nombre_monitor;
    declaración de los tipos y procedimientos que se importan y exportan
    declaración de las variables locales del monitor y de las variables de condición
    
    procedure Prc1(..);
    begin
        ... var.de condición (siempre que sean necesarias) ...
    end;
    
    procedure Prc2(..);
    begin
        ...
    end; ....
    
    procedure Prcm(..);
    begin
        ...
    end;
    
    begin inicialización del monitor end.
    
    ....
    export Prc1, Prc2, ..., Prcn
```

### 📊 Diagrama de Estructura del Monitor

```
┌─────────────────────────────────────────────────────────────┐
│                    ESTRUCTURA DEL MONITOR                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                 DECLARACIONES                       │   │
│  │                                                     │   │
│  │  • Tipos y procedimientos (import/export)           │   │
│  │  • Variables locales del monitor                   │   │
│  │  • Variables de condición                           │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                PROCEDIMIENTOS                       │   │
│  │                                                     │   │
│  │  procedure Prc1(..)                                 │   │
│  │  begin                                              │   │
│  │      (* Código del procedimiento *)                 │   │
│  │      (* Variables de condición si necesario *)       │   │
│  │  end;                                               │   │
│  │                                                     │   │
│  │  procedure Prc2(..)                                 │   │
│  │  begin                                              │   │
│  │      (* Código del procedimiento *)                 │   │
│  │  end;                                               │   │
│  │                                                     │   │
│  │  ...                                                │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              INICIALIZACIÓN Y EXPORT               │   │
│  │                                                     │   │
│  │  begin                                              │   │
│  │      (* inicialización del monitor *)              │   │
│  │  end.                                               │   │
│  │                                                     │   │
│  │  export Prc1, Prc2, ..., Prcn                      │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Ventajas de los Monitores

✅ **Exclusión mutua implícita**: No requiere programación explícita  
✅ **Encapsulación**: Recursos y procedimientos en un módulo  
✅ **Seguridad**: Imposible usar incorrectamente el recurso  
✅ **Abstracción**: Oculta detalles de sincronización  
✅ **Mantenibilidad**: Código más organizado y legible  

---

## ✉️ MENSAJES

### Definición y Propósito

**Solución al problema de la concurrencia de procesos que integra la sincronización y la comunicación entre ellos y resulta adecuado tanto para sistemas centralizados como distribuidos.**

### 📬 Comunicación mediante Mensajes

**La comunicación mediante mensajes necesita siempre de un proceso emisor y de 1 receptor** así como de información que intercambiarse. Por ello, las **operaciones básicas** para la comunicación mediante mensajes que proporciona todo sistema operativo son: **enviar(mensaje) y recibir(mensaje)**.

**Las acciones de transmisión de información y de sincronización se ven como actividades inseparables.**

**Requiere un enlace entre el receptor y el emisor (puede variar de sistema a sistema.)**

### 🔄 Factores de Implementación

Su **implementación depende** de:

1. **El modo de nombrar los procesos**
2. **El modelo de sincronización**  
3. **Almacenamiento y estructura del mensaje**

---

## 📊 MODELOS PARA LA SINCRONIZACIÓN DE PROCESOS

### Tipos de Sincronización

**Varían según las formas que puede adoptar la operación de envío del mensaje:**

#### a) **Síncrona**
El proceso que envía **sólo prosigue su tarea cuando el mensaje ha sido recibido**. Ej. llamada a un procedimiento.

#### b) **Asíncrona** 
El **proceso que envía un mensaje sigue su ejecución sin preocuparse si el mensaje se recibe o no.**

#### c) **Invocación remota**
El proceso que envía el mensaje **sólo prosigue su ejecución cuando ha recibido una respuesta del receptor.** Emisor y receptor **ejecutan síncronamente un segmento de código.**

### 📊 Diagrama de Modelos de Sincronización

```
┌─────────────────────────────────────────────────────────────┐
│              MODELOS DE SINCRONIZACIÓN                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  a) SÍNCRONA: Emisor espera confirmación                     │
│  ┌─────────────┐    ┌─────────────┐                │
│  │   EMISOR    │    │   RECEPTOR  │                │
│  │ enviar() ────▶ recibir()  │                │
│  │ ESPERA   ◄───── ACK        │                │
│  └─────────────┘    └─────────────┘                │
│                                                             │
│  b) ASÍNCRONA: Emisor continúa inmediatamente               │
│  ┌─────────────┐    ┌─────────────┐                │
│  │   EMISOR    │    │   RECEPTOR  │                │
│  │ enviar() ────▶ recibir()  │                │
│  │ continúa     │    │             │                │
│  └─────────────┘    └─────────────┘                │
│                                                             │
│  c) INVOCACIÓN REMOTA: Cliente-Servidor                     │
│  ┌─────────────────────────────────────────────────┐    │
│  │        Computadora A        Computadora B       │    │
│  │  ┌─────────────┐    ┌─────────────┐     │    │
│  │  │ Programa     │    │ Programa     │     │    │
│  │  │ Cliente      │    │ Servidor     │     │    │
│  │  │ Mensaje STUB │───▶ Objeto Remoto│     │    │
│  │  └─────────────┘    └─────────────┘     │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚫 INTERBLOQUEO (DEADLOCK)

### Definición del Problema

**2 o más procesos entran en 1 estado que imposibilita a cualquiera de ellos salir del estado en que se encuentra.** A dicha situación se llega cuando cada proceso adquiere algún recurso necesario para su operación a la vez que espera a que se liberen otros recursos que retienen otros procesos, **llegándose a 1 situación que hace imposible que ninguno de ellos pueda continuar.**

### 📊 Diagrama de Interbloqueo

```
┌─────────────────────────────────────────────────────────────┐
│                    INTERBLOQUEO (DEADLOCK)                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                       solicita                             │
│                  ┌─────────────────┐                    │
│                  │                 │                    │
│                  ▼                 │                    │
│              ┌─────┐                ┌─────┐               │
│              │ P1  │                │ R2  │               │
│              └─────┘                └─────┘               │
│                 ▲                     │                    │
│                 │                     │                    │
│          tiene asignado              tiene asignado         │
│                 │                     │                    │
│                 │                     ▼                    │
│              ┌─────┐                ┌─────┐               │
│              │ R1  │                │ P2  │               │
│              └─────┘                └─────┘               │
│                 ▲                     │                    │
│                 │                     │                    │
│                 └─────────────────────┘                    │
│                              solicita                       │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                    SITUACIÓN                        │   │
│  │                                                     │   │
│  │  • Proceso P1: Tiene recurso R1, solicita R2        │   │
│  │  • Proceso P2: Tiene recurso R2, solicita R1        │   │
│  │                                                     │   │
│  │  RESULTADO: Ninguno puede continuar                 │   │
│  │                                                     │   │
│  │  • P1 espera que P2 libere R2                      │   │
│  │  • P2 espera que P1 libere R1                      │   │
│  │  • Ambos procesos quedan bloqueados indefinidamente │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Condiciones para el Interbloqueo

Para que ocurra un interbloqueo deben cumplirse **4 condiciones simultáneamente**:

1. **🔒 Exclusión Mutua**: Los recursos no pueden ser compartidos
2. **🔄 Retención y Espera**: Los procesos retienen recursos mientras esperan otros
3. **🚫 No Apropiación**: Los recursos no pueden ser quitados forzosamente
4. **🔄 Espera Circular**: Existe un ciclo de procesos esperando recursos

### 🛡️ Estrategias de Prevención

✅ **Prevención**: Eliminar una de las 4 condiciones necesarias  
✅ **Evitación**: Algoritmos como el del banquero  
✅ **Detección y Recuperación**: Detectar deadlocks y resolverlos  
✅ **Ignorar**: Asumir que los deadlocks son raros (política del avestruz)  

---

## 📚 RESUMEN FINAL - CONCURRENCIA

### 🔄 Evolución de Conceptos

```
┌─────────────────────────────────────────────────────────────┐
│                 RESUMEN DE CONCURRENCIA                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  CONCEPTOS BÁSICOS:                                         │
│  • Variables Compartidas → Regiones Críticas                │
│  • Algoritmos Clásicos → Peterson, Dekker                   │
│  • Semáforos → Exclusión Mutua y Sincronización             │
│  • Monitores → Encapsulación de Recursos                    │
│  • Mensajes → Comunicación entre Procesos                   │
│  • Interbloqueo → Prevención y Detección                    │
│                                                             │
│  PROBLEMAS RESUELTOS:                                       │
│  ✅ Exclusión mutua                                         │
│  ✅ Sincronización de procesos                              │
│  ✅ Comunicación segura                                   │
│  ✅ Prevención de deadlocks                               │
│  ✅ Gestión eficiente de recursos                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 📚 BIBLIOGRAFÍA RECOMENDADA

### 📚 Recursos de Estudio

```
┌─────────────────────────────────────────────────────────────┐
│                 BIBLIOGRAFÍA RECOMENDADA                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│                        📚                                │
│                    ┌─────────────┐                       │
│                    │   LIBROS   │                       │
│                    │ RECOMENDA- │                       │
│                    │    DOS     │                       │
│                    └─────────────┘                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 🔗 Enlaces y Recursos Digitales

#### 🌐 Recursos Web Recomendados

• **Concurrencia VS Paralelismo**  
  🔗 [goo.gl/1M9jsX](https://goo.gl/1M9jsX)

• **Sincronización basada en memoria compartida: Regiones críticas**  
  🔗 [goo.gl/F3WRGk](https://goo.gl/F3WRGk)

• **Programación Concurrente - Documento Completo**  
  🔗 [Dropbox - Concurrencia.pdf](https://www.dropbox.com/scl/fi/bu9ou83n5vv3m1tsez4k5/concurrencia.pdf?rlkey=4icbj9l7ulsgq99y9wjub2wo5&dl=0)

### 📚 Bibliografía Académica Complementaria

1. **Andrews, G.R.** - "Concurrent Programming: Principles and Practice"  
   🏷️ *Fundamentos teóricos y prácticos*

2. **Ben-Ari, M.** - "Principles of Concurrent and Distributed Programming"  
   🏷️ *Principios de programación concurrente*

3. **Silberschatz, A., Galvin, P.B., Gagne, G.** - "Operating System Concepts"  
   🏷️ *Conceptos de sistemas operativos*

4. **Tanenbaum, A.S.** - "Modern Operating Systems"  
   🏷️ *Sistemas operativos modernos*

5. **Burns, A., Davies, G.** - "Concurrent Programming"  
   🏷️ *Programación concurrente avanzada*

6. **Herlihy, M., Shavit, N.** - "The Art of Multiprocessor Programming"  
   🏷️ *Arte de la programación multiprocesador*

### 📊 Recursos Adicionales

#### 🎥 Videos y Tutoriales
- Conferencias sobre concurrencia y paralelismo
- Tutoriales prácticos de implementación
- Casos de estudio reales

#### 💻 Herramientas de Desarrollo
- Simuladores de concurrencia
- Debuggers especializados
- Analizadores de deadlock

#### 📄 Papers y Artículos
- Investigaciones recientes en concurrencia
- Algoritmos optimizados
- Casos de uso industriales

---

### 🎆 ¡FELICITACIONES!

**Has completado exitosamente el estudio de la Clase 2: Introducción a Concurrencia**

✅ **Conceptos dominados**: Variables compartidas, regiones críticas, algoritmos clásicos  
✅ **Herramientas aprendidas**: Semáforos, monitores, mensajes  
✅ **Problemas resueltos**: Exclusión mutua, sincronización, deadlocks  
✅ **Base sólida**: Para programación concurrente avanzada  

**¡Continúa con el siguiente tema del curso!**

---

*Documento generado para el curso de Programación Concurrente 2024*  
*Facultad de Ingeniería - Universidad Nacional de Jujuy*  
*Profesor: Ing. José Farfán*

---