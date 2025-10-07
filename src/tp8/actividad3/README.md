# 🛒 TP8 - ACTIVIDAD 3: SIMULACIÓN DE SUPERMERCADO CON CAJAS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Executor Framework - Simulación de Supermercado

---

## 🎯 **OBJETIVO**

Simular la atención en un **supermercado** con **3 cajas** donde cada cajero demora entre **1 y 3 segundos** en atender a un cliente. La cola de espera es de **50 clientes** que llegan todos juntos. Debe mostrar por pantalla el número de cliente atendido, cuándo comienza y cuándo finaliza la atención.

---

## 📝 **DESCRIPCIÓN DEL EJERCICIO**

### 🏪 **Escenario del Supermercado**

- **Número de cajas:** 3
- **Número de clientes:** 50 (llegan todos juntos)
- **Tiempo de atención:** Variable entre 1" y 3" por cliente
- **Sistema de cola:** Los clientes esperan su turno para ser atendidos

### 📊 **Información a Mostrar**

Para cada cliente:
1. 🔢 **Número de cliente** que está siendo atendido
2. ⏰ **Inicio de atención** - Cuando comienza a ser atendido
3. ✅ **Fin de atención** - Cuando termina de ser atendido
4. 💳 **Caja** que lo atendió

---

## 🏗️ **ARQUITECTURA DE LA SOLUCIÓN**

### 📊 **Diagrama del Sistema**

```
┌─────────────────────────────────────────────────────────────────┐
│              SUPERMERCADO - SISTEMA DE CAJAS                    │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         ExecutorService (Pool Fijo - 3 Cajas)            │  │
│  │                                                          │  │
│  │    Caja 1     │    Caja 2     │    Caja 3              │  │
│  │    Thread 1   │    Thread 2   │    Thread 3             │  │
│  │       ↓             ↓              ↓                     │  │
│  └──────────────────────────────────────────────────────────┘  │
│                         │                                       │
│                         ▼                                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                   COLA DE CLIENTES                       │  │
│  │                                                          │  │
│  │  👤 👤 👤 👤 👤 ... (50 clientes en espera)              │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  FLUJO DE ATENCIÓN:                                            │
│  1. Cliente entra en cola (submit al ExecutorService)         │
│  2. Caja libre toma cliente de la cola                        │
│  3. Cajero atiende (1-3 segundos aleatorios)                  │
│  4. Cliente termina y se retira                                │
│  5. Caja queda libre para siguiente cliente                    │
│                                                                 │
│  REPORTES EN TIEMPO REAL:                                      │
│  📍 [Caja X] Cliente #Y: Iniciando atención...                │
│  ✅ [Caja X] Cliente #Y: Atención completada (Zt)             │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp8/actividad3/
├── README.md                        (Este archivo)
├── Cliente.java                    (Representa un cliente)
├── TareaAtencionCliente.java      (Runnable: atención de un cliente)
├── Caja.java                      (Información de la caja)
└── SupermercadoSimulacion.java    (Simulación principal)
```

### 🔑 **Componentes Principales**

#### 1️⃣ **Cliente**
- Número de cliente (1-50)
- Timestamp de llegada
- Tiempos de inicio y fin de atención

#### 2️⃣ **TareaAtencionCliente** (Runnable)
- Simula atención de 1-3 segundos
- Registra inicio y fin de atención
- Muestra información por pantalla

#### 3️⃣ **SupermercadoSimulacion**
- ExecutorService con pool de 3 threads (3 cajas)
- 50 clientes enviados al pool
- Estadísticas finales de atención

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac -d bin src/tp8/actividad3/*.java

# Ejecutar simulación
java -cp bin tp8.actividad3.SupermercadoSimulacion
```

### 📊 **Salida Esperada**

```
=== SIMULACIÓN DE SUPERMERCADO ===

🏪 CONFIGURACIÓN:
   💳 Número de cajas: 3
   👥 Número de clientes: 50
   ⏱️ Tiempo de atención: 1-3 segundos

🚀 INICIANDO ATENCIÓN DE CLIENTES...

[00:00:00] 📍 [Caja 1] Cliente #1: Iniciando atención...
[00:00:00] 📍 [Caja 2] Cliente #2: Iniciando atención...
[00:00:00] 📍 [Caja 3] Cliente #3: Iniciando atención...
[00:00:02] ✅ [Caja 1] Cliente #1: Atención completada (2.1s)
[00:00:02] 📍 [Caja 1] Cliente #4: Iniciando atención...
[00:00:02] ✅ [Caja 2] Cliente #2: Atención completada (2.3s)
[00:00:02] 📍 [Caja 2] Cliente #5: Iniciando atención...
[00:00:03] ✅ [Caja 3] Cliente #3: Atención completada (2.8s)
[00:00:03] 📍 [Caja 3] Cliente #6: Iniciando atención...
...

✅ TODOS LOS CLIENTES ATENDIDOS

📊 ESTADÍSTICAS FINALES:
┌───────────┬──────────────┬──────────────┬──────────────┐
│   CAJA    │   CLIENTES   │  TIEMPO AVG  │  TIEMPO TOTAL│
├───────────┼──────────────┼──────────────┼──────────────┤
│  Caja 1   │      17      │    2.1s      │    35.7s     │
│  Caja 2   │      16      │    2.0s      │    32.0s     │
│  Caja 3   │      17      │    2.2s      │    37.4s     │
├───────────┼──────────────┼──────────────┼──────────────┤
│  TOTAL    │      50      │    2.1s      │   105.1s     │
└───────────┴──────────────┴──────────────┴──────────────┘

⏱️ Tiempo total simulación: 35.8s
📊 Eficiencia: 97.8%
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **Por qué ExecutorService con Pool de 3?**

1. **Representa las 3 cajas físicas** del supermercado
2. **Limita concurrencia** a 3 atenciones simultáneas
3. **Cola automática** para los clientes esperando
4. **Reutilización de threads** (cajas) cuando terminan

### 📊 **Comportamiento del Sistema**

#### **Estado Inicial:**
- 3 cajas libres, 50 clientes en cola
- Primeros 3 clientes son atendidos inmediatamente

#### **Durante Ejecución:**
- Cuando una caja termina → toma siguiente cliente de la cola
- Máximo 3 clientes siendo atendidos simultáneamente
- Los demás 47 clientes esperan su turno

#### **Tiempo Total Esperado:**
- Tiempo promedio: 2 segundos por cliente
- Con 3 cajas: 50 clientes / 3 cajas ≈ 17 clientes por caja
- Tiempo total: ~17 × 2s ≈ 34-35 segundos

### 🎯 **Métricas de Rendimiento**

#### **Eficiencia:**
```
Eficiencia = (Tiempo trabajando / Tiempo total) × 100
           = (Σ tiempos atención / (Tiempo simulación × 3 cajas)) × 100
```

#### **Throughput:**
```
Throughput = Clientes atendidos / Tiempo total
           ≈ 50 clientes / 35s ≈ 1.43 clientes/segundo
```

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🏪 **Simulación realista** de sistema de atención
- 💳 **Pool limitado** representa recursos físicos (cajas)
- 📋 **Cola de espera** gestionada automáticamente
- ⏱️ **Tiempos aleatorios** de servicio
- 📊 **Estadísticas por recurso** (caja)
- 🔄 **Reutilización eficiente** de threads
- 📈 **Métricas de rendimiento** (eficiencia, throughput)

---

## 📚 **REFERENCIAS**

- **Clase 7:** Executor Framework
- **Java Concurrency:** Fixed Thread Pool
- **Simulación:** Sistemas de colas (Queue Theory)

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Simulación de sistema de atención en supermercado usando Executor Framework, demostrando gestión eficiente de recursos limitados y cola de espera con estadísticas en tiempo real.*
