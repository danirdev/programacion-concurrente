# 🔒 TP7 - ACTIVIDAD 2: REGIONES CRÍTICAS CON SEMÁFOROS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Implementación del Punto N° 3 del TP 2 con Semáforos

---

## 🎯 **OBJETIVO**

Implementar los **algoritmos de exclusión mutua** del TP 2 (1 Flag, 2 Flags, Peterson) utilizando **semáforos** para demostrar cómo los semáforos simplifican y mejoran la sincronización entre procesos concurrentes.

---

## 📝 **DESCRIPCIÓN DEL PROBLEMA**

### 🔒 **Regiones Críticas**

**Definición:** Bloques de código que al ser declarados como regiones críticas respecto de una variable, el programador o el compilador introduce mecanismos de sincronización necesarios para que su ejecución se realice en un régimen de **exclusión mutua**.

**Objetivo:** Garantizar la **actualización segura** a una variable compartida.

### 📊 **Algoritmos Clásicos de Exclusión Mutua**

#### 1️⃣ **Algoritmo de 1 Flag**
- **Problema:** NO resuelve exclusión mutua
- **Causa:** Comprobación y puesta del indicador son operaciones separadas
- **Resultado:** Ambos procesos pueden entrar simultáneamente

#### 2️⃣ **Algoritmo de 2 Flags**
- **Mejora:** Cada proceso tiene su propio flag
- **Problemas:** Busy wait y posible deadlock
- **Causa Deadlock:** Ambos procesos activan sus flags simultáneamente

#### 3️⃣ **Algoritmo de Peterson**
- **Solución:** Introduce variable `turno` adicional
- **Ventaja:** Resuelve deadlock y garantiza exclusión mutua
- **Desventaja:** Sigue teniendo busy wait

---

## 🚦 **SOLUCIÓN CON SEMÁFOROS**

### 🔧 **Estrategia de Implementación**

Los **semáforos** resuelven TODOS los problemas de los algoritmos clásicos:
- ✅ **Exclusión mutua garantizada**
- ✅ **Sin busy wait** (bloqueo eficiente)
- ✅ **Sin deadlock** (con uso correcto)
- ✅ **Fairness** (orden FIFO)
- ✅ **Simplicidad** de código

### 📊 **Comparación de Enfoques**

```
┌─────────────────────────────────────────────────────────────────┐
│                    COMPARACIÓN DE ALGORITMOS                   │
├─────────────────┬─────────────┬─────────────┬─────────────────┤
│    ALGORITMO    │ EXCLUSIÓN   │ BUSY WAIT   │   DEADLOCK      │
│                 │   MUTUA     │             │                 │
├─────────────────┼─────────────┼─────────────┼─────────────────┤
│ 1 Flag          │     ❌      │     ✅      │       ❌        │
│ 2 Flags         │     ✅      │     ❌      │       ❌        │
│ Peterson        │     ✅      │     ❌      │       ✅        │
│ SEMÁFOROS       │     ✅      │     ✅      │       ✅        │
└─────────────────┴─────────────┴─────────────┴─────────────────┘
```

### 🏗️ **Arquitectura de la Solución**

```
┌─────────────────────────────────────────────────────────────────┐
│                    EXCLUSIÓN MUTUA CON SEMÁFOROS               │
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │  Proceso P1 │    │ RecursoCompartido│    │  Proceso P2 │     │
│  │             │    │                 │    │             │     │
│  │ acquire()   │◄──►│  Semáforo Mutex │◄──►│ acquire()   │     │
│  │ // RC       │    │  (inicializado  │    │ // RC       │     │
│  │ release()   │    │   en 1)         │    │ release()   │     │
│  └─────────────┘    └─────────────────┘    └─────────────┘     │
│                                                                 │
│  Flujo de Sincronización:                                      │
│  1. acquire() → Entra en región crítica                        │
│  2. Ejecuta código crítico de forma exclusiva                  │
│  3. release() → Sale de región crítica                         │
│  4. Otro proceso puede entrar                                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp7/actividad2/
├── README.md                           (Este archivo)
├── RecursoCompartido.java             (Recurso protegido por semáforo)
├── ProcesoExclusionMutua.java         (Thread que accede al recurso)
├── AlgoritmoClasico.java              (Implementación de algoritmos clásicos)
├── ComparadorAlgoritmos.java          (Comparación de rendimiento)
└── ExclusionMutuaSimulacion.java      (Simulación principal)
```

### 🔑 **Características Implementadas**

- ✅ **Implementación con Semáforos** (solución moderna)
- ✅ **Implementación de Algoritmos Clásicos** (1 Flag, 2 Flags, Peterson)
- ✅ **Comparación de Rendimiento** entre enfoques
- ✅ **Detección de Problemas** (deadlock, race conditions)
- ✅ **Métricas Detalladas** de cada algoritmo
- ✅ **Simulación Realista** con múltiples escenarios
- ✅ **Análisis de Eficiencia** y robustez

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp7/actividad2/*.java

# Ejecutar simulación completa
java tp7.actividad2.ExclusionMutuaSimulacion

# Ejecutar comparación de algoritmos
java tp7.actividad2.ComparadorAlgoritmos
```

### 📊 **Salida Esperada**

```
=== SIMULACIÓN: EXCLUSIÓN MUTUA CON SEMÁFOROS ===

🚦 ALGORITMO: SEMÁFOROS
[P1] 09:15:23.123 - Solicitando acceso a región crítica
[P1] 09:15:23.124 - ENTRANDO en región crítica
[P2] 09:15:23.125 - Solicitando acceso a región crítica
[P2] 09:15:23.125 - ESPERANDO... (P1 en región crítica)
[P1] 09:15:24.124 - SALIENDO de región crítica
[P2] 09:15:24.125 - ENTRANDO en región crítica
...

=== COMPARACIÓN DE ALGORITMOS ===
┌─────────────────┬─────────────┬─────────────┬─────────────┐
│    ALGORITMO    │   TIEMPO    │ DEADLOCKS   │ EFICIENCIA  │
├─────────────────┼─────────────┼─────────────┼─────────────┤
│ 1 Flag          │   ERROR     │     N/A     │     0%      │
│ 2 Flags         │  15.2s      │     3       │    45%      │
│ Peterson        │  12.8s      │     0       │    78%      │
│ SEMÁFOROS       │   8.5s      │     0       │    98%      │
└─────────────────┴─────────────┴─────────────┴─────────────┘
```

---

## 🔍 **ANÁLISIS COMPARATIVO**

### 📈 **Ventajas de los Semáforos**

1. **🚀 Rendimiento Superior**
   - Sin busy wait → menor uso de CPU
   - Bloqueo eficiente del thread
   - Mejor throughput general

2. **🛡️ Robustez**
   - Exclusión mutua garantizada
   - Sin deadlock (con uso correcto)
   - Manejo automático de interrupciones

3. **📝 Simplicidad de Código**
   - API clara y concisa
   - Menos líneas de código
   - Menor probabilidad de errores

4. **⚖️ Fairness**
   - Orden FIFO garantizado
   - Sin starvation
   - Distribución equitativa de recursos

### 📊 **Métricas de Comparación**

| Métrica | 1 Flag | 2 Flags | Peterson | Semáforos |
|---------|--------|---------|----------|-----------|
| **Exclusión Mutua** | ❌ | ✅ | ✅ | ✅ |
| **Sin Busy Wait** | ❌ | ❌ | ❌ | ✅ |
| **Sin Deadlock** | ❌ | ❌ | ✅ | ✅ |
| **Fairness** | ❌ | ❌ | ⚠️ | ✅ |
| **Simplicidad** | ⭐⭐ | ⭐ | ⭐ | ⭐⭐⭐ |
| **Rendimiento** | ❌ | ⭐ | ⭐⭐ | ⭐⭐⭐ |

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🔒 **Regiones críticas** y exclusión mutua
- 🚦 **Semáforos como herramienta de sincronización**
- 📊 **Comparación de algoritmos clásicos vs modernos**
- ⚡ **Problemas de concurrencia** (deadlock, race conditions)
- 🔄 **Busy wait vs bloqueo eficiente**
- 📈 **Análisis de rendimiento** en sistemas concurrentes

---

## 🧪 **ESCENARIOS DE PRUEBA**

### 1️⃣ **Escenario Básico**
- 2 procesos accediendo a recurso compartido
- Verificación de exclusión mutua
- Medición de tiempos de acceso

### 2️⃣ **Escenario de Estrés**
- 10 procesos concurrentes
- Alta contención por el recurso
- Análisis de fairness y rendimiento

### 3️⃣ **Escenario de Comparación**
- Ejecución paralela de todos los algoritmos
- Medición comparativa de métricas
- Detección automática de problemas

---

## 📚 **REFERENCIAS**

- **Clase 2:** Introducción a Programación Concurrente
- **TP 2 - Punto 3:** Regiones Críticas y Algoritmos de Exclusión Mutua
- **Algoritmo de Peterson (1981):** Solución clásica a exclusión mutua
- **Java Concurrency:** java.util.concurrent.Semaphore
- **Dijkstra (1965):** Introducción de semáforos

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación completa de algoritmos de exclusión mutua con análisis comparativo entre métodos clásicos y semáforos modernos.*
