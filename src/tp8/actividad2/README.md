# ⏰ TP8 - ACTIVIDAD 2: SCHEDULED EXECUTOR SERVICE - TAREAS PERIÓDICAS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** ScheduledExecutorService - Tareas Periódicas

---

## 🎯 **OBJETIVO**

Implementar un sistema de **tareas periódicas** usando **ScheduledExecutorService** que ejecute dos tareas cada 2 segundos:
- **Tarea1:** Captura fecha/hora actual y la almacena en lista
- **Tarea2:** Procesa milisegundos, verifica si es primo y lo guarda en archivos

---

## 📝 **DESCRIPCIÓN DEL EJERCICIO**

### 🔄 **Tareas a Implementar**

#### **Tarea 1: Captura de Fecha/Hora**
- Obtiene la **fecha y hora actual** del sistema en formato `HH:mm:ss:S`
- **Almacena** la timestamp en una **lista compartida**
- Se ejecuta **periódicamente** cada 2 segundos

#### **Tarea 2: Procesamiento de Números**
- Trabaja con el **último número** agregado a la lista
- Obtiene el valor de **milisegundos** de la fecha/hora
- **Verifica** si el número es **primo**:
  - Si es **primo** → Guarda en archivo `Primos.txt`
  - Si **no es primo** → Guarda en archivo `NoPrimos.txt`
- Se ejecuta **periódicamente** cada 2 segundos

### ⚙️ **Configuración de Ejecución**

- **Pausa inicial:** 2 segundos
- **Período de ejecución:** 2 segundos
- **Forma:** Ininterrumpida (hasta detener manualmente)

---

## 🏗️ **ARQUITECTURA DE LA SOLUCIÓN**

### 📊 **ScheduledExecutorService**

```
┌─────────────────────────────────────────────────────────────────┐
│           SCHEDULED EXECUTOR SERVICE                            │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │    ScheduledExecutorService (2 threads)                  │  │
│  │                                                          │  │
│  │    Thread 1: Tarea1 (cada 2s)                           │  │
│  │    Thread 2: Tarea2 (cada 2s)                           │  │
│  └──────────────────────────────────────────────────────────┘  │
│                         │                                       │
│           ┌─────────────┴──────────────┐                       │
│           ▼                            ▼                        │
│  ┌──────────────────┐        ┌──────────────────┐              │
│  │    TAREA 1       │        │    TAREA 2       │              │
│  │  Fecha/Hora      │        │  Procesamiento   │              │
│  ├──────────────────┤        ├──────────────────┤              │
│  │ 1. Obtener       │        │ 1. Leer último   │              │
│  │    timestamp     │        │    de lista      │              │
│  │ 2. Formato       │        │ 2. Extraer       │              │
│  │    HH:mm:ss:S    │        │    milisegundos  │              │
│  │ 3. Guardar en    │        │ 3. Verificar si  │              │
│  │    lista         │        │    es primo      │              │
│  └────────┬─────────┘        │ 4. Escribir en   │              │
│           │                  │    archivo       │              │
│           ▼                  └────────┬─────────┘              │
│  ┌──────────────────┐                │                         │
│  │ LISTA COMPARTIDA │◄───────────────┘                         │
│  │  (Thread-Safe)   │                                          │
│  └──────────────────┘                                          │
│           │                                                     │
│           ▼                                                     │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              ARCHIVOS DE SALIDA                          │  │
│  │                                                          │  │
│  │  📄 Primos.txt       📄 NoPrimos.txt                     │  │
│  │  (números primos)    (números no primos)                 │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp8/actividad2/
├── README.md                          (Este archivo)
├── ListaCompartida.java              (Lista thread-safe para timestamps)
├── TareaFechaHora.java               (Tarea1: Captura fecha/hora)
├── TareaProcesamiento.java           (Tarea2: Procesa y verifica primos)
├── VerificadorPrimos.java            (Utilidad para verificar números primos)
└── TareasPeriodicasSimulacion.java   (Main con ScheduledExecutorService)
```

### 🔑 **Componentes Principales**

#### 1️⃣ **TareaFechaHora** (Tarea1)
- Obtiene timestamp actual del sistema
- Formato: `HH:mm:ss:S` (incluye milisegundos)
- Guarda en lista compartida thread-safe

#### 2️⃣ **TareaProcesamiento** (Tarea2)
- Lee último timestamp de la lista
- Extrae valor de milisegundos
- Verifica si es número primo
- Escribe en archivo correspondiente

#### 3️⃣ **ScheduledExecutorService**
- `newScheduledThreadPool(2)` - 2 threads
- `scheduleAtFixedRate()` - Ejecución periódica
- Initial delay: 2 segundos
- Period: 2 segundos

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp8/actividad2/*.java

# Ejecutar simulación
java tp8.actividad2.TareasPeriodicasSimulacion
```

### 📊 **Salida Esperada**

```
=== TAREAS PERIÓDICAS CON SCHEDULED EXECUTOR SERVICE ===

⚙️ CONFIGURACIÓN:
   ⏰ Delay inicial: 2 segundos
   🔄 Período: 2 segundos
   📋 Tareas: 2 (Tarea1 y Tarea2)

🚀 INICIANDO TAREAS PERIÓDICAS...

[00:00:02] 📅 Tarea1: Capturada fecha/hora - 12:30:45:123
[00:00:02] 🔢 Tarea2: Procesando milisegundos: 123
[00:00:02] ✅ 123 NO es primo → Guardado en NoPrimos.txt

[00:00:04] 📅 Tarea1: Capturada fecha/hora - 12:30:47:456
[00:00:04] 🔢 Tarea2: Procesando milisegundos: 456
[00:00:04] ✅ 456 NO es primo → Guardado en NoPrimos.txt

[00:00:06] 📅 Tarea1: Capturada fecha/hora - 12:30:49:787
[00:00:06] 🔢 Tarea2: Procesando milisegundos: 787
[00:00:06] ✅ 787 ES PRIMO → Guardado en Primos.txt

...

📊 ESTADÍSTICAS (después de 30 segundos):
   📅 Timestamps capturados: 15
   🔢 Números procesados: 15
   ✨ Números primos encontrados: 4
   ❌ Números no primos: 11
   📄 Primos.txt: 4 entradas
   📄 NoPrimos.txt: 11 entradas
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **ScheduledExecutorService vs Timer**

#### ✅ **Ventajas de ScheduledExecutorService:**
1. **Thread pool** - Múltiples tareas concurrentes
2. **Más robusto** - Maneja excepciones sin cancelar tareas
3. **Flexibilidad** - Varias estrategias de scheduling
4. **Thread-safe** - API diseñada para concurrencia

### 🔄 **Métodos de Scheduling**

#### **scheduleAtFixedRate()**
```java
scheduler.scheduleAtFixedRate(tarea, delay, period, TimeUnit.SECONDS);
```
- Ejecuta tarea **cada X tiempo** desde el **inicio** de la anterior
- **No espera** a que termine la tarea anterior
- Útil para tareas de **duración fija**

#### **scheduleWithFixedDelay()**
```java
scheduler.scheduleWithFixedDelay(tarea, delay, period, TimeUnit.SECONDS);
```
- Ejecuta tarea **cada X tiempo** desde el **fin** de la anterior
- **Espera** a que termine la tarea anterior
- Útil para tareas de **duración variable**

### 🔢 **Verificación de Números Primos**

**Algoritmo eficiente:**
```java
boolean esPrimo(int n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    
    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0)
            return false;
    }
    return true;
}
```

### 📝 **Sincronización de Lista**

Opciones thread-safe:
1. **Collections.synchronizedList()** - Sincronización básica
2. **CopyOnWriteArrayList** - Óptimo para lecturas frecuentes
3. **ConcurrentLinkedQueue** - Cola concurrente sin bloqueo

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- ⏰ **ScheduledExecutorService** - Tareas periódicas
- 🔄 **scheduleAtFixedRate()** - Ejecución con período fijo
- 📋 **Lista thread-safe** - Sincronización de datos
- 🔢 **Verificación de primos** - Algoritmo eficiente
- 📄 **Escritura a archivos** - I/O desde múltiples threads
- ⏱️ **Timestamps** - Manejo de fecha/hora
- 🚦 **Concurrencia controlada** - Múltiples tareas periódicas

---

## 📚 **REFERENCIAS**

- **Clase 7:** Executor Framework
- **Java Concurrency:** ScheduledExecutorService
- **Scheduling:** scheduleAtFixedRate vs scheduleWithFixedDelay
- **Thread Safety:** Collections sincronizadas

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación de tareas periódicas con ScheduledExecutorService, demostrando scheduling automático, sincronización de datos compartidos y procesamiento concurrente de timestamps.*
