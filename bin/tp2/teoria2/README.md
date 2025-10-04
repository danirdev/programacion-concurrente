# 📚 CLASE 2 - INTRODUCCIÓN A PROGRAMACIÓN CONCURRENTE

## 📋 **INFORMACIÓN DEL CURSO**

**📅 Año:** 2024  
**🏫 Materia:** MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Introducción a Concurrencia

---

## 📖 **ÍNDICE DE CONTENIDOS**

1. [🔄 Programación Concurrente](#programación-concurrente)
2. [📦 Variables Compartidas](#variables-compartidas)
3. [🔒 Regiones Críticas](#regiones-críticas)
4. [🚦 Semáforos](#semáforos)
5. [🖥️ Monitores](#monitores)
6. [📨 Mensajes](#mensajes)
7. [⚠️ Interbloqueo](#interbloqueo)
8. [📚 Bibliografía](#bibliografía)

---

## 🔄 **PROGRAMACIÓN CONCURRENTE**

### 📝 **Definición**

Notaciones y técnicas de programación que se usan para expresar **paralelismo potencial** entre tareas para resolver los problemas de **comunicación y sincronización** entre procesos.

### 🔑 **Conceptos Clave**

#### 🔵 **Concurrencia**
Es la capacidad del CPU para realizar **más de un proceso al mismo tiempo**.

#### 🔵 **Paralelismo**
- Toma **un único problema**
- Con concurrencia llega a **una solución más rápida**
- A partir del problema inicial, **divide el problema en fracciones más pequeñas**
- Cada fracción es procesada de forma **concurrente**
- Aprovecha la capacidad del procesador para resolver el problema

---

## 📦 **VARIABLES COMPARTIDAS**

### 📝 **Definición**

Es el método **más sencillo** de comunicación entre los procesos de un programa. El acceso concurrente puede hacer que la acción de un proceso **interfiera** en las acciones de otro de una forma no adecuada.

---

## 🌳 **PROBLEMA DE LOS JARDINES**

### 🎯 **Objetivo**

Controlar el número de visitantes a unos jardines.

### 📋 **Descripción del Sistema**

- La entrada y la salida a los jardines se realiza por **2 puntos** que disponen de puertas giratorias
- Se asocia el proceso **P1** a un punto de E/S
- El proceso **P2** al otro punto de E/S
- Los procesos se ejecutan **concurrentemente**
- Usan **una única variable** para llevar la cuenta del número de visitantes

### 💻 **Operaciones**

```
Entrada de visitante: x := x + 1
Salida de visitante:  x := x - 1
```

### ✅ **Casos SIN Problema**

1. **Una única instrucción HW:** Si ambas instrucciones se realizan como una única instrucción hardware, no se plantea ningún problema

2. **Sistema multiprocesador:** Si se arbitran mecanismos que impiden que varios procesadores accedan a la vez a una misma posición de memoria, NO hay problema

### ❌ **Problema: Interferencia**

Si se produce la interferencia de un proceso en el otro, la actualización de la variable requiere una **REGIÓN CRÍTICA**.

```
┌─────────────────────────────────────┐
│           Jardín                    │
│                                     │
│  P1 ──► Entrada: x = x + 1         │
│         Salida:  x = x - 1         │
│                                     │
│  P2 ──► Entrada: x = x + 1         │
│         Salida:  x = x - 1         │
│                                     │
│  Concurrencia + única variable x   │
│  Problema: actualización x          │
│  Solución: REGIÓN CRÍTICA          │
└─────────────────────────────────────┘
```

---

## 🔒 **REGIONES CRÍTICAS**

### 📝 **Definición**

Bloques de código que al ser declarados como regiones críticas respecto de una variable, el programador o el compilador introduce mecanismos de sincronización necesarios para que su ejecución se realice en un régimen de **exclusión mutua** respecto de otras regiones críticas declaradas respecto de la misma variable.

### 🎯 **Objetivo**

Garantizar la **actualización segura** a una variable compartida. Si se declara como variable compartida, su acceso solo se puede realizar en regiones críticas y todos los accesos se realizan con **Exclusión Mutua**.

---

## 🔐 **BLOQUEO MEDIANTE VARIABLES COMPARTIDAS**

### 1️⃣ **Exclusión Mutua con 1 Indicador**

Implementación del bloqueo a una región crítica mediante el uso de una **variable compartida indicador o flag**.

```pascal
(* Exclusión Mutua: Uso de 1 indicador *)
module Exclusion_Mutua_1;
var flag: boolean;

process P1
begin
    loop
        while flag = true do
            (* Espera a que el dispositivo se libere *)
        end;
        flag := true;
        (* Uso del recurso - Región Crítica *)
        flag := false;
        (* resto del proceso *)
    end
end P1;

process P2
begin
    loop
        while flag = true do
            (* Espera a que el dispositivo se libere *)
        end;
        flag := true;
        (* Uso del recurso - Región Crítica *)
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

### ❌ **Problema**

**NO RESUELVE EL PROBLEMA** de la exclusión mutua, ya que al ser la **comprobación y la puesta del indicador operaciones separadas**, puede ocurrir que se entrelace el uso del recurso por ambos procesos.

---

### 2️⃣ **Exclusión Mutua con 2 Indicadores**

**Solución:** Usar **2 indicadores** para resolver el problema de la exclusión mutua.

```pascal
(* Exclusión Mutua: Uso de dos indicadores *)
module Exclusion_Mutua_2;
var flag1, flag2: boolean;

procedure bloqueo(var mi_flag, su_flag: boolean);
begin
    mi_flag := true;  (* intención de usar el recurso *)
    while su_flag do ;  (* espera a que se libere el recurso *)
end bloqueo;

procedure desbloqueo(var mi_flag: boolean);
begin
    mi_flag := false;
end desbloqueo;

process P1
begin
    loop
        bloqueo(flag1, flag2);
        (* Uso del recurso - Sección Crítica *)
        desbloqueo(flag1);
        (* resto del proceso *)
    end
end P1;

process P2
begin
    loop
        bloqueo(flag2, flag1);
        (* Uso del recurso - Sección Crítica *)
        desbloqueo(flag2);
        (* resto del proceso *)
    end
end P2;

begin (* Exclusion_Mutua_2 *)
    flag1 := FALSE;
    flag2 := FALSE;
    cobegin
        P1; P2;
    coend
end Exclusion_Mutua_2.
```

---

### ⚠️ **Inconvenientes**

#### 1️⃣ **Busy Wait (Espera Ocupada)**
Durante la espera de la liberación del recurso, el proceso permanece ocupado.

#### 2️⃣ **Interbloqueo (Deadlock)**
Si ambos procesos realizan la llamada al bloqueo de forma simultánea:
- Cada proceso puede poner su propio indicador
- Comprobar el estado del otro
- Ambos ven los indicadores contrarios como ocupados
- Permanecerán a la espera de que el recurso quede liberado
- Esto **no podrá suceder** al no poder entrar ninguno en su sección crítica

**Causa:** La desactivación del indicador asociado a un proceso se produce una vez que se ha completado el acceso a la región crítica.

### ✅ **Solución al Deadlock**

Que el proceso **desactive su propio indicador** durante la fase de bloqueo siempre que encuentre que el indicador del otro proceso está activado.

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

---

## 🎯 **ALGORITMO DE PETERSON (1981)**

Introduce una **variable adicional turno** para el problema de petición simultánea de acceso a la región crítica.

```pascal
(* Exclusión Mutua: Solución de Peterson *)
Module Exclusion_Mutua_P;
var flag1, flag2: boolean; turno: integer;

procedure bloqueo(var mi_flag, su_flag: boolean; su_turno: integer);
begin
    mi_flag := true;
    turno := su_turno;
    while su_flag and (turno = su_turno) do ;
end bloqueo;

procedure desbloqueo(var mi_flag: boolean);
begin
    mi_flag := false;
end desbloqueo;

process P1
begin
    loop
        bloqueo(flag1, flag2, 2);
        (* Uso del recurso - Sección Crítica *)
        desbloqueo(flag1);
        (* resto del proceso *)
    end
end P1;

process P2
begin
    loop
        bloqueo(flag2, flag1, 1);
        (* Uso del recurso - Sección Crítica *)
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
end Exclusion_Mutua_P.
```

---

## 🎯 **ALGORITMO DE DEKKER (BASADO EN DIJKSTRA 1968)**

Implementa una **variable turno** para establecer la prioridad relativa de 2 procesos y su actualización se realiza en la sección crítica, lo que evita que pueda haber interferencias entre procesos.

```pascal
(* Exclusión Mutua: Solución de Dekker *)
module Exclusion_Mutua_D;
var flag1, flag2: boolean; turno: integer;

procedure bloqueo(var mi_flag, su_flag: boolean; su_turno: integer);
begin
    mi_flag := true;
    while su_flag do  (* otro proceso en la sección crítica *)
        if turno = su_turno then
            mi_flag := false;
            while turno = su_turno do ;  (* espera que el otro acabe *)
        end;
        mi_flag := true;
    end;
end bloqueo;

procedure desbloqueo(var mi_flag: boolean; su_turno: integer);
begin
    turno := su_turno;
    mi_flag := false;
end desbloqueo;

process P1
begin
    loop
        bloqueo(flag1, flag2, 2);
        (* Uso del recurso - Sección Crítica *)
        desbloqueo(flag1, 2);
        (* resto proceso *)
    end
end P1;

process P2
begin
    loop
        bloqueo(flag2, flag1, 1);
        (* Uso del recurso - Sección Crítica *)
        desbloqueo(flag2, 1);
        (* resto del proceso *)
    end
end P2;

begin (* Exclusion_Mutua_D *)
    flag1 := FALSE;
    flag2 := FALSE;
    turno := 1;
    cobegin
        P1; P2;
    coend
end Exclusion_Mutua_D.
```

---

## 🚦 **SEMÁFOROS**

### 📝 **Definición**

Resuelve la mayoría de los problemas de sincronización entre procesos y forma parte del diseño de muchos Sistemas Operativos y de lenguajes de programación concurrentes.

### 🔑 **Semáforo Binario**

Un **semáforo binario** es un indicador (S) de condición que registra si un recurso está disponible o no.

- Solo puede tomar **2 valores: 0 y 1**
- Si **S = 1:** el recurso está **disponible** y la tarea lo puede usar
- Si **S = 0:** el recurso **no está disponible** y el proceso debe esperar

### 📊 **Implementación**

Los semáforos se implementan con una **cola de tareas o de condición** a la cual se añaden los procesos que están en espera del recurso.

### ⚙️ **Tres Operaciones Permitidas**

#### 1️⃣ **inicializa(S: SemaforoBinario; v: integer)**
Pone el valor del semáforo S al valor v (0 o 1)

#### 2️⃣ **espera(S)**
```
if S = 1 then
    S := 0
else
    suspende la tarea que hace la llamada
    y pone en cola de tareas
```

#### 3️⃣ **señal(S)**
```
if la cola de tareas está vacía then
    S := 1
else
    reanudar la primer tarea de la cola de tareas
```

### 📜 **Historia**

**Edsger Dijkstra** 1965: usado por primera vez en SO Theos 1974

---

## 🔒 **SEMÁFOROS - EXCLUSIÓN MUTUA**

La operación **espera** se usa como procedimiento de **bloqueo** antes de acceder a una región crítica.

La operación **señal** se usa como procedimiento de **desbloqueo**.

Se usan **tantos semáforos** como clases de secciones críticas se establezcan.

### 💻 **Ejemplo de Proceso**

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

---

## 🔄 **SEMÁFOROS - SINCRONIZACIÓN**

**Espera** y **señal** no se usan en un mismo proceso, sino en **2 procesos separados**:
- El que ejecuta la operación de **espera** queda **bloqueado**
- Hasta que el otro proceso ejecuta la operación de **señal**

### 💻 **Ejemplo de Sincronización**

```pascal
module Sincronización;  (* Sincronización con semáforo *)
var sincro: semaforo;

process P1  (* Proceso que espera *)
begin
    ....
    espera(sincro);
    ....
end P1;

process P2  (* Proceso que señala *)
begin
    ....
    señal(sincro);
    ....
end P2;

begin (* Sincronización *)
    inicializa(sincro, 0);
    cobegin
        P1; P2;
    coend
end Sincronizacion.
```

---

## 🏭 **PROBLEMA PRODUCTOR-CONSUMIDOR**

### 📝 **Descripción**

- Hay **2 procesos** P1 y P2
- **P1 produce datos** que consume P2
- P1 almacena datos en algún sitio hasta que P2 está listo para usarlos

### 💡 **Ejemplo**

- P1 genera información para una impresora
- P2 es el proceso gestor de la impresora que imprime

### 🔧 **Componentes**

- **Buffer:** Zona de memoria común al productor y al consumidor
- **Funciones:**
  - `Poner(x)`: Almacenar datos
  - `Tomar(x)`: Tomar datos
  - `Lleno`: Devuelve TRUE si el buffer está lleno
  - `Vacio`: Devuelve TRUE si el buffer está vacío

---

### 1️⃣ **Sin Semáforos**

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
        while Vacio do (* espera *) end;
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

---

### ❌ **Inconvenientes**

1. **Poner(x) y Tomar(x) usan el mismo buffer** → Necesita **EXCLUSIÓN MUTUA**
2. **Ambos procesos usan espera ocupada** cuando no pueden acceder al buffer

---

### 2️⃣ **Con Semáforos**

```pascal
(* Problema del Productor-Consumidor: Con Semáforos *)
module Productor_Consumidor;
var BufferComun: buffer;
    AccesoBuffer, Nolleno, Novacio: semaforo;

process Productor;
var x: dato;
begin
    loop
        produce(x);
        espera(AccesoBuffer);
        if Lleno then
            señal(AccesoBuffer);
            espera(Nolleno);
            espera(AccesoBuffer)
        end;
        Poner(x);
        señal(AccesoBuffer);
        señal(Novacio)
    end
end Productor;

process Consumidor;
var x: dato;
begin
    loop
        espera(AccesoBuffer);
        if Vacio then
            señal(AccesoBuffer);
            espera(Novacio);
            espera(AccesoBuffer)
        end;
        Tomar(x);
        señal(AccesoBuffer);
        señal(Nolleno);
        Consume(x)
    end
end Consumidor;

begin
    inicializa(AccesoBuffer, 1);
    inicializa(Nolleno, 1);
    inicializa(Novacio, 0);
    cobegin
        Productor; Consumidor;
    coend
end Productor_Consumidor;
```

---

## 🔢 **SEMÁFOROS PARA N RECURSOS DISPONIBLES**

### 📋 **Características**

El semáforo se inicializa con el **número total de recursos disponibles (N)**.

**Espera** y **señal** se diseñan para que se impida el acceso al recurso protegido por el semáforo cuando el valor de éste es **menor o igual que cero**.

### 🔄 **Funcionamiento**

- Si se obtiene **un recurso:** el semáforo se **decrementa**
- Se **incrementa** cuando uno de ellos se **libera**
- Si la operación de espera se ejecuta cuando el semáforo tiene un valor menor que 1, el proceso debe quedar en espera de que la ejecución de una operación señal libere alguno de los recursos

### ⚙️ **Operaciones**

#### **inicializa(S: SemaforoBinario; v: integer)**
```
S = N
numero_suspendidos := 0
```

#### **espera(S)**
```
if S > 0 then
    S := S - 1
else
    numero_suspendidos := numero_suspendidos + 1;
    suspende la tarea que hace la llamada
    y poner en la cola de tareas
```

#### **señal(S)**
```
if numero_suspendidos > 0 then
    numero_suspendidos := numero_suspendidos - 1
    pasa al estado listo un proceso suspendido
else
    S := S + 1
```

---

## 🖥️ **MONITOR**

### 📝 **Definición**

El monitor se puede ver como una **valla alrededor del recurso** de modo que los procesos que quieran utilizarlo deben entrar dentro de la valla, pero en la forma que impone el monitor.

### 🔑 **Características**

- Muchos procesos pueden querer entrar en distintos instantes de tiempo
- Pero solo se permite que **entre un proceso cada vez**
- Debiendo esperar a que salga el que está dentro

### ✅ **Exclusión Mutua Implícita**

La exclusión mutua está **implícita**: la única acción que debe realizar el programador del proceso que usa un recurso es **invocar una entrada del monitor**.

Si el monitor se ha codificado correctamente, **NO puede ser utilizado incorrectamente** por un programa de aplicación que desee usar el recurso.

### 🔄 **Sincronización**

Los monitores **NO proporcionan por sí mismos** un mecanismo para sincronizar tareas, por ello su construcción se completa usando **señales o variables de condición** para sincronizar los procesos.

### 📋 **Definición Formal**

Procedimientos que proporcionan el acceso con **EXCLUSIÓN MUTUA** a un recurso o conjunto de recursos compartidos por un grupo de procesos.

Los procedimientos se **encapsulan** a un módulo que tiene la propiedad de que **solo un proceso puede estar activo cada vez** que se ejecuta un procedimiento del monitor.

---

## 💻 **MONITOR - SINTAXIS**

```pascal
monitor: nombre_monitor;
    declaración de los tipos y procedimientos que se importan y exportan
    declaración de las variables locales del monitor y de las variables de condición
    
    procedure Prc1(..);
    begin
        ... var. de condición (siempre que sean necesarias) ...
    end;
    
    procedure Prc2(..);
    begin
        ...
    end;
    
    ....
    
    procedure Prcm(..);
    begin
        ...
    end;
    
begin
    inicialización del monitor
end.

export Prc1, Prc2, ...., Prcn
```

---

## 📨 **MENSAJES**

### 📝 **Definición**

La comunicación mediante mensajes necesita siempre de:
- Un **proceso emisor**
- Un **receptor**
- **Información** que intercambiarse

### ⚙️ **Operaciones Básicas**

Todo sistema operativo proporciona:
- **enviar(mensaje)**
- **recibir(mensaje)**

### 🔗 **Características**

Las acciones de **transmisión de información** y de **sincronización** se ven como actividades **inseparables**.

Requiere un **enlace entre el receptor y el emisor** (puede variar de sistema a sistema).

### 📋 **Implementación Depende De:**

1. El modo de **nombrar los procesos**
2. El **modelo de sincronización**
3. **Almacenamiento y estructura** del mensaje

### 🎯 **Ventaja**

Solución al problema de la concurrencia de procesos que **integra la sincronización y la comunicación** entre ellos y resulta adecuado tanto para sistemas **centralizados como distribuidos**.

---

## 🔄 **MODELOS PARA LA SINCRONIZACIÓN DE PROCESOS**

Varían según las formas que puede adoptar la operación de **envío del mensaje**:

### 1️⃣ **Síncrona**
El proceso que envía solo prosigue su tarea cuando el mensaje **ha sido recibido**.

**Ejemplo:** Llamada a un procedimiento.

### 2️⃣ **Asíncrona**
El proceso que envía un mensaje **sigue su ejecución** sin preocuparse si el mensaje se recibe o no.

### 3️⃣ **Invocación Remota**
El proceso que envía el mensaje solo prosigue su ejecución cuando **ha recibido una respuesta** del receptor.

**Característica:** Emisor y receptor ejecutan **síncronamente** un segmento de código.

---

## ⚠️ **INTERBLOQUEO (DEADLOCK)**

### 📝 **Definición**

**2 o más procesos** entran en un estado que imposibilita a cualquiera de ellos salir del estado en que se encuentra.

### 🔄 **Causa**

A dicha situación se llega porque:
- Cada proceso **adquiere algún recurso** necesario para su operación
- A la vez que **espera** a que se liberen otros recursos que retienen otros procesos
- Llegándose a una situación que hace **imposible** que ninguno de ellos pueda continuar

### 📊 **Diagrama Conceptual**

```
Proceso A ──► Tiene Recurso 1 ──► Espera Recurso 2
                                         ▲
                                         │
                                         │
Proceso B ──► Tiene Recurso 2 ──► Espera Recurso 1
                                         │
                                         └──► INTERBLOQUEO
```

---

## 📊 **TABLA COMPARATIVA: MECANISMOS DE SINCRONIZACIÓN**

| Mecanismo | Complejidad | Exclusión Mutua | Sincronización | Uso |
|-----------|-------------|-----------------|----------------|-----|
| **Variables Compartidas** | Baja | Manual | Manual | Básico |
| **Algoritmo Peterson** | Media | Automática | No | 2 procesos |
| **Algoritmo Dekker** | Media | Automática | No | 2 procesos |
| **Semáforos** | Media | Con código | Sí | General |
| **Monitores** | Alta | Implícita | Con variables | Avanzado |
| **Mensajes** | Alta | Implícita | Integrada | Distribuido |

---

## 🎓 **CONCEPTOS CLAVE PARA RECORDAR**

### ✅ **Fundamentos**

- **Concurrencia:** Múltiples procesos al mismo tiempo
- **Paralelismo:** Dividir problema en fracciones concurrentes
- **Variables Compartidas:** Método simple pero problemático
- **Región Crítica:** Código con acceso exclusivo

### ✅ **Problemas**

- **Busy Wait:** Espera ocupada ineficiente
- **Deadlock:** Interbloqueo entre procesos
- **Interferencia:** Procesos afectándose mutuamente

### ✅ **Soluciones**

- **Semáforos:** Mecanismo clásico de sincronización
- **Monitores:** Exclusión mutua implícita
- **Mensajes:** Comunicación y sincronización integradas

### ✅ **Algoritmos Importantes**

- **Peterson (1981):** Variable turno adicional
- **Dekker (1968):** Prioridad relativa
- **Dijkstra (1965):** Creador de semáforos

---

## 📚 **BIBLIOGRAFÍA RECOMENDADA**

### 🌐 **Enlaces Web**

**Concurrencia VS Paralelismo:**  
goo.gl/1M9jsX

**Sincronización basada en memoria compartida: Regiones críticas:**  
goo.gl/F3WRGk

**Programación Concurrente:**  
https://www.dropbox.com/scl/fi/bu9ou83n5vv3ml1sez4k5/concurrencia.pdf?rlkey=4icbj9l7ulsgq99y9wjub2wo5&dl=0

---

*Este material forma parte del curso Modelo de Desarrollo de Programas y Programación Concurrente 2024*  
*Facultad de Ingeniería - UNJu*  
*Prof. Ing. José Farfán*