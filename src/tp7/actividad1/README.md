# 🌳 TP7 - ACTIVIDAD 1: PROBLEMA DE LOS JARDINES CON SEMÁFOROS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Implementación del Punto N° 2 del TP 2 con Semáforos

---

## 🎯 **OBJETIVO**

Implementar el **Problema de los Jardines** del TP 2 utilizando **semáforos** para garantizar la sincronización correcta entre procesos concurrentes.

---

## 📝 **DESCRIPCIÓN DEL PROBLEMA**

### 🌳 **Problema de los Jardines**

**Objetivo:** Controlar el número de visitantes a unos jardines.

**Descripción del Sistema:**
- La entrada y la salida a los jardines se realiza por **2 puntos** que disponen de puertas giratorias
- Se asocia el proceso **P1** a un punto de E/S
- El proceso **P2** al otro punto de E/S  
- Los procesos se ejecutan **concurrentemente**
- Usan **una única variable** para llevar la cuenta del número de visitantes

**Operaciones:**
```
Entrada de visitante: x := x + 1
Salida de visitante:  x := x - 1
```

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

## 🚦 **SOLUCIÓN CON SEMÁFOROS**

### 🔧 **Estrategia de Implementación**

1. **Semáforo Mutex:** Para proteger la variable compartida `contadorVisitantes`
2. **Clase ContadorJardines:** Recurso compartido con operaciones sincronizadas
3. **Clase PuntoAcceso:** Thread que representa cada punto de entrada/salida
4. **Simulación Principal:** Coordina la ejecución de ambos puntos

### 📊 **Diagrama de Solución**

```
┌─────────────────────────────────────────────────────────┐
│                    JARDINES                             │
│                                                         │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐ │
│  │   Punto 1   │    │ ContadorJardines│    │   Punto 2   │ │
│  │             │    │                 │    │             │ │
│  │ Thread P1   │◄──►│  Semáforo Mutex │◄──►│ Thread P2   │ │
│  │             │    │                 │    │             │ │
│  │ Entrada/    │    │ contadorVisit.  │    │ Entrada/    │ │
│  │ Salida      │    │ = 0             │    │ Salida      │ │
│  └─────────────┘    └─────────────────┘    └─────────────┘ │
│                                                         │
│  Operaciones Sincronizadas:                            │
│  • entrarVisitante() → acquire() → x++ → release()     │
│  • salirVisitante()  → acquire() → x-- → release()     │
└─────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp7/actividad1/
├── README.md                    (Este archivo)
├── ContadorJardines.java        (Recurso compartido)
├── PuntoAcceso.java            (Thread para cada punto)
├── JardinesSimulacion.java     (Simulación principal)
└── GeneradorVisitantes.java    (Generador de visitantes)
```

### 🔑 **Características Implementadas**

- ✅ **Semáforo Mutex** para exclusión mutua
- ✅ **Dos puntos de acceso** concurrentes (P1 y P2)
- ✅ **Operaciones sincronizadas** (entrada/salida)
- ✅ **Contador compartido** protegido
- ✅ **Simulación realista** con tiempos aleatorios
- ✅ **Estadísticas detalladas** de rendimiento
- ✅ **Logging completo** con timestamps

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp7/actividad1/*.java

# Ejecutar simulación
java tp7.actividad1.JardinesSimulacion
```

### 📊 **Salida Esperada**

```
=== SIMULACIÓN JARDINES CON SEMÁFOROS ===
Iniciando simulación con 2 puntos de acceso...

[P1] 09:15:23.123 - Visitante ENTRA - Total: 1
[P2] 09:15:23.456 - Visitante ENTRA - Total: 2
[P1] 09:15:24.789 - Visitante SALE  - Total: 1
[P2] 09:15:25.012 - Visitante ENTRA - Total: 2
...

=== ESTADÍSTICAS FINALES ===
Punto 1 - Entradas: 25, Salidas: 23
Punto 2 - Entradas: 28, Salidas: 26
Total Visitantes Actuales: 4
Operaciones Totales: 102
Tiempo Total: 30.5 segundos
```

---

## 🔍 **ANÁLISIS DE CONCURRENCIA**

### ✅ **Ventajas de la Solución con Semáforos**

1. **Exclusión Mutua Garantizada:** El semáforo protege la región crítica
2. **Simplicidad:** Código más limpio que algoritmos manuales
3. **Fairness:** Los semáforos de Java garantizan orden FIFO
4. **Robustez:** Manejo automático de interrupciones
5. **Escalabilidad:** Fácil extensión a más puntos de acceso

### 🔄 **Flujo de Sincronización**

```
Thread P1/P2 quiere modificar contador:
1. acquire() en semáforo mutex
2. Entra en región crítica
3. Modifica contadorVisitantes
4. Sale de región crítica  
5. release() en semáforo mutex
```

### 📈 **Comparación con Otras Soluciones**

| Método | Complejidad | Fairness | Robustez | Rendimiento |
|--------|-------------|----------|----------|-------------|
| Algoritmos Manuales | Alta | No garantizado | Baja | Variable |
| Synchronized | Media | No garantizado | Media | Buena |
| **Semáforos** | **Baja** | **Garantizado** | **Alta** | **Excelente** |

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🚦 **Semáforos para exclusión mutua**
- 🔄 **Sincronización de threads concurrentes**
- 🛡️ **Protección de regiones críticas**
- 📊 **Variables compartidas seguras**
- ⚡ **Concurrencia sin interferencia**
- 📈 **Análisis de rendimiento concurrente**

---

## 📚 **REFERENCIAS**

- **Clase 2:** Introducción a Programación Concurrente
- **TP 2 - Punto 2:** Problema de los Jardines
- **Java Concurrency:** java.util.concurrent.Semaphore
- **Algoritmos de Sincronización:** Exclusión Mutua

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación completa del Problema de los Jardines usando semáforos para garantizar sincronización correcta entre procesos concurrentes.*
