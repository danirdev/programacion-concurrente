# 💈 TP7 - ACTIVIDAD 6: PROBLEMA DEL BARBERO DURMIENTE

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Implementación del Punto N° 8 del TP 2 con Semáforos

---

## 🎯 **OBJETIVO**

Implementar el **Problema del Barbero Durmiente** del TP 2 utilizando **semáforos** para coordinar la sincronización entre el barbero y los clientes de manera eficiente y sin race conditions.

---

## 📝 **DESCRIPCIÓN DEL PROBLEMA**

### 💈 **Problema del Barbero Durmiente**

**Enunciado:** Una barbería posee:
- Una **sala de espera** con **n sillas**
- Una **sala para el barbero** (donde corta el pelo)
- **1 barbero** que puede estar: durmiendo, cortando pelo, o esperando

**Reglas del Sistema:**
1. Si **no hay clientes** esperando → El barbero **duerme**
2. Si **todas las sillas están ocupadas** → El cliente **se retira**
3. Si el barbero está **ocupado** pero hay **sillas libres** → El cliente **se sienta a esperar**
4. Si el barbero está **dormido** → El cliente lo **despierta** y recibe servicio

### 🎭 **Actores del Sistema**

#### **El Barbero:**
- 😴 **Durmiendo** - Esperando por clientes
- ✂️ **Cortando pelo** - Atendiendo un cliente
- ⏳ **Esperando** - Listo para atender

#### **Los Clientes:**
- 🚶 **Llega** - Verifica disponibilidad
- 💺 **Se sienta** - Espera turno en sala de espera
- ✂️ **Recibe servicio** - Está siendo atendido
- 🚪 **Se retira** - Si no hay sillas disponibles

### 🔑 **Desafíos de Sincronización**

1. **Despertar al barbero** cuando llega un cliente
2. **Coordinar turno** entre barbero y cliente
3. **Gestionar sillas** de forma thread-safe
4. **Evitar pérdida de clientes** cuando no hay espacio
5. **Garantizar exclusión mutua** en sala de espera

---

## 🚦 **SOLUCIÓN CON SEMÁFOROS**

### 🔧 **Estrategia de Implementación**

**Semáforos Utilizados:**
1. **`clientes`** (0) - Barbero espera por clientes (duerme en este semáforo)
2. **`barbero`** (0) - Cliente espera que barbero esté listo
3. **`mutex`** (1) - Protege acceso a contador de sillas libres

**Variable Compartida:**
- **`sillasLibres`** (n) - Número de sillas disponibles en sala de espera

### 📊 **Diagrama de Solución**

```
┌─────────────────────────────────────────────────────────────────┐
│                    BARBERÍA CON SEMÁFOROS                       │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              SALA DE ESPERA (n sillas)                   │  │
│  │                                                          │  │
│  │  🪑 🪑 🪑 ... (sillasLibres)                             │  │
│  │                                                          │  │
│  │  Variable compartida protegida por mutex                 │  │
│  └──────────────────────────────────────────────────────────┘  │
│                           │                                     │
│                           ▼                                     │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              SILLA DEL BARBERO                           │  │
│  │                                                          │  │
│  │         💈 Barbero (1 único recurso)                     │  │
│  │                                                          │  │
│  │  Estados: 😴 Dormido → ✂️ Cortando → ⏳ Esperando       │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  FLUJO DE SINCRONIZACIÓN:                                      │
│                                                                 │
│  Cliente llega:                                                │
│    1. mutex.acquire() → Verificar sillasLibres                 │
│    2. SI sillasLibres > 0:                                     │
│         - sillasLibres--                                       │
│         - clientes.release() → Despertar/notificar barbero     │
│         - mutex.release()                                      │
│         - barbero.acquire() → Esperar que barbero esté listo   │
│    3. SINO:                                                    │
│         - Cliente se retira (no hay espacio)                   │
│                                                                 │
│  Barbero:                                                      │
│    1. clientes.acquire() → Dormir hasta que llegue cliente    │
│    2. mutex.acquire() → Tomar cliente de sala espera          │
│    3. sillasLibres++                                          │
│    4. barbero.release() → Señalar que está listo              │
│    5. mutex.release()                                         │
│    6. cortarPelo() → Atender cliente                          │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp7/actividad6/
├── README.md                        (Este archivo)
├── Barberia.java                   (Estado compartido de la barbería)
├── Barbero.java                    (Thread del barbero)
├── Cliente.java                    (Thread del cliente)
├── EstadisticasBarberia.java       (Métricas de la simulación)
├── GeneradorClientes.java          (Genera flujo de clientes)
└── BarberoDurmienteSimulacion.java (Simulación principal)
```

### 🔑 **Características Implementadas**

- ✅ **Barbero duerme** cuando no hay clientes
- ✅ **Clientes se retiran** si no hay sillas
- ✅ **Sincronización correcta** barbero-cliente
- ✅ **Estadísticas detalladas** (atendidos, rechazados, tiempos)
- ✅ **Generador de clientes** con llegadas aleatorias
- ✅ **Visualización en tiempo real** del estado
- ✅ **Múltiples escenarios** de prueba
- ✅ **Análisis de rendimiento**

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp7/actividad6/*.java

# Ejecutar simulación
java tp7.actividad6.BarberoDurmienteSimulacion
```

### 📊 **Salida Esperada**

```
=== SIMULACIÓN: PROBLEMA DEL BARBERO DURMIENTE ===

💈 Barbería abierta - Sillas disponibles: 3
😴 Barbero: Durmiendo... (esperando clientes)

🚶 Cliente #1 llegó
[Cliente #1] 🪑 Hay sillas disponibles (3/3)
[Cliente #1] 🔔 Despertando al barbero
😴→⏳ Barbero: Despertando...
⏳ Barbero: Listo para atender
[Cliente #1] ⏳ Esperando que barbero esté listo
[Cliente #1] ✅ Barbero disponible, entrando a la silla
✂️ Barbero: Cortando pelo al Cliente #1
[Cliente #1] ✂️ Recibiendo corte de pelo...
✅ Barbero: Terminé de cortar pelo al Cliente #1
[Cliente #1] 👋 Saliendo de la barbería

🚶 Cliente #2 llegó
[Cliente #2] 🪑 Hay sillas disponibles (3/3)
[Cliente #2] 🔔 Notificando al barbero
⏳ Barbero: Cliente en espera, atendiendo...
✂️ Barbero: Cortando pelo al Cliente #2
...

🚶 Cliente #5 llegó
[Cliente #5] ❌ No hay sillas disponibles (0/3)
[Cliente #5] 🚪 Retirándose de la barbería

📊 ESTADÍSTICAS FINALES:
┌────────────────────┬──────────┐
│     MÉTRICA        │  VALOR   │
├────────────────────┼──────────┤
│ Clientes atendidos │    12    │
│ Clientes rechazados│     3    │
│ Tasa de atención   │   80%    │
│ Tiempo promedio    │  2.3s    │
│ Barbero dormido    │   15%    │
│ Utilización sillas │   65%    │
└────────────────────┴──────────┘
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **Semáforos y su Función**

#### 1️⃣ **Semáforo `clientes` (inicializado en 0)**
- **Propósito:** Sincroniza llegada de clientes
- **Barbero hace:** `clientes.acquire()` → Se duerme hasta que llegue un cliente
- **Cliente hace:** `clientes.release()` → Despierta/notifica al barbero

#### 2️⃣ **Semáforo `barbero` (inicializado en 0)**
- **Propósito:** Sincroniza cuando barbero está listo
- **Cliente hace:** `barbero.acquire()` → Espera que barbero esté disponible
- **Barbero hace:** `barbero.release()` → Señala que está listo para atender

#### 3️⃣ **Semáforo `mutex` (inicializado en 1)**
- **Propósito:** Protege acceso a `sillasLibres`
- **Uso:** Exclusión mutua al verificar/modificar sillas disponibles

### 📊 **Escenarios de Ejecución**

#### **Escenario 1: Barbero dormido, cliente llega**
```
Estado inicial: clientes=0, barbero=0, sillasLibres=n

Cliente llega:
    mutex.acquire()
    sillasLibres-- (n-1)
    clientes.release() → clientes=1 (despierta barbero)
    mutex.release()
    barbero.acquire() → se bloquea esperando barbero

Barbero despierta:
    clientes.acquire() → clientes=0, barbero procede
    mutex.acquire()
    sillasLibres++ (n)
    barbero.release() → barbero=1 (libera cliente)
    mutex.release()
    cortarPelo()

Cliente procede:
    barbero.acquire() → barbero=0, cliente procede
    recibir servicio
```

#### **Escenario 2: Barbero ocupado, sillas disponibles**
```
Estado: barbero cortando pelo, sillasLibres < n

Cliente llega:
    mutex.acquire()
    sillasLibres-- 
    clientes.release() → incrementa cola de clientes
    mutex.release()
    barbero.acquire() → se bloquea hasta que barbero termine
```

#### **Escenario 3: Todas las sillas ocupadas**
```
Estado: sillasLibres = 0

Cliente llega:
    mutex.acquire()
    SI sillasLibres == 0:
        mutex.release()
        Cliente se retira (no espera)
```

---

## ✅ **VERIFICACIÓN DE CORRECTITUD**

### **Propiedades Garantizadas**

#### **1. Sincronización Barbero-Cliente**
- ✅ Barbero solo atiende cuando hay clientes
- ✅ Cliente solo entra cuando barbero está listo
- ✅ No se pierde ninguna señal

#### **2. Gestión de Sillas**
- ✅ `sillasLibres` nunca negativo
- ✅ `sillasLibres` nunca excede capacidad
- ✅ Acceso exclusivo con mutex

#### **3. Comportamientos Correctos**
- ✅ Barbero duerme cuando no hay clientes
- ✅ Clientes se retiran si no hay espacio
- ✅ Un cliente a la vez siendo atendido

#### **4. Ausencia de Problemas**
- ✅ Sin deadlock (no hay ciclos)
- ✅ Sin starvation (cola FIFO de semáforos)
- ✅ Sin race conditions (mutex protege estado)

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 💈 **Problema clásico** del barbero durmiente
- 🚦 **Sincronización productor-consumidor** (clientes-barbero)
- 🔄 **Semáforos para dormir/despertar** threads
- 📊 **Gestión de recursos limitados** (sillas)
- ⚡ **Exclusión mutua** en estado compartido
- 🎯 **Políticas de admisión** (rechazo de clientes)

---

## 📚 **REFERENCIAS**

- **Clase 2:** Introducción a Programación Concurrente
- **TP 2 - Punto 8:** Problema del Barbero Durmiente
- **Dijkstra (1965):** Problema clásico de sincronización
- **Java Concurrency:** java.util.concurrent.Semaphore

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación completa del Problema del Barbero Durmiente con semáforos, demostrando sincronización eficiente entre threads, gestión de recursos limitados y prevención de race conditions.*
