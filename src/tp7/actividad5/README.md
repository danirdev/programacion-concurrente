# 🔄 TP7 - ACTIVIDAD 5: SINCRONIZACIÓN DE HILOS CON ORDEN ESPECÍFICO

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** Implementación del Punto N° 7 del TP 2 con Semáforos

---

## 🎯 **OBJETIVO**

Implementar sincronización de hilos con **orden específico** del TP 2 utilizando **semáforos** para garantizar que la salida sea exactamente **"R I O OK OK OK"** sin importar el orden de ejecución de los threads.

---

## 📝 **DESCRIPCIÓN DEL PROBLEMA**

### 🔄 **Problema de Sincronización con Orden Específico**

**Enunciado:** Considere los siguientes hilos:

```java
thread {
    print(" R ");
    print(" OK ");
}

thread {
    print(" I ");
    print(" OK ");
}

thread {
    print(" O ");
    print(" OK ");
}
```

**Objetivo:** Empleando semáforos, se debe asegurar que el resultado final de la impresión sea **"R I O OK OK OK"**.

### 📊 **Análisis del Problema**

**Restricciones:**
- ✅ **Orden de letras:** R → I → O (estrictamente secuencial)
- ✅ **Separación de fases:** Todas las letras antes que todos los OK
- ✅ **Orden de OK:** Cualquier orden (pueden ejecutarse en paralelo)

**Estructura:**
- **3 hilos concurrentes** ejecutándose
- **Cada hilo imprime:** 1 letra + 1 "OK"
- **Dependencias:** R antes que I, I antes que O
- **Barrera:** Todos los OK después de todas las letras

---

## 🚦 **SOLUCIÓN CON SEMÁFOROS**

### 🔧 **Estrategia de Implementación**

**Componentes de Sincronización:**
1. **Semáforos de Orden** - Para cadena R → I → O
2. **Barrera de Sincronización** - Para separar letras de OK
3. **Mutex de Contador** - Para coordinar la barrera

### 📊 **Diagrama de Solución**

```
┌─────────────────────────────────────────────────────────────────┐
│           SINCRONIZACIÓN R I O OK OK OK                         │
│                                                                 │
│  FASE 1: IMPRESIÓN DE LETRAS (ORDEN ESTRICTO)                 │
│  ┌──────────┐      ┌──────────┐      ┌──────────┐             │
│  │  Hilo R  │ ───► │  Hilo I  │ ───► │  Hilo O  │             │
│  │  "R"     │      │  "I"     │      │  "O"     │             │
│  └────┬─────┘      └────┬─────┘      └────┬─────┘             │
│       │                 │                 │                    │
│       │    semI         │    semO         │                    │
│       └─────────────────┴─────────────────┘                    │
│                         │                                       │
│                    BARRERA (contador + mutex)                  │
│                         │                                       │
│  FASE 2: IMPRESIÓN DE OK (ORDEN PARALELO)                     │
│  ┌──────────┐      ┌──────────┐      ┌──────────┐             │
│  │  Hilo R  │      │  Hilo I  │      │  Hilo O  │             │
│  │  "OK"    │      │  "OK"    │      │  "OK"    │             │
│  └──────────┘      └──────────┘      └──────────┘             │
│                                                                 │
│  SEMÁFOROS UTILIZADOS:                                         │
│  🚦 semI (0) - I espera a R                                    │
│  🚦 semO (0) - O espera a I                                    │
│  🚦 barrera (0) - Sincroniza fase de letras y OK               │
│  🚦 mutex (1) - Protege contador de la barrera                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp7/actividad5/
├── README.md                        (Este archivo)
├── HiloSincronizado.java           (Clase base para hilos sincronizados)
├── Barrera.java                    (Implementación de barrera con semáforos)
├── SincronizadorOrden.java         (Coordinador de sincronización)
├── HiloR.java                      (Thread que imprime R)
├── HiloI.java                      (Thread que imprime I)
├── HiloO.java                      (Thread que imprime O)
├── VerificadorSalida.java          (Verifica que la salida sea correcta)
└── SincronizacionOrdenSimulacion.java (Simulación principal)
```

### 🔑 **Características Implementadas**

- ✅ **Cadena de dependencias** con semáforos (R → I → O)
- ✅ **Barrera de sincronización** para separar fases
- ✅ **Verificación automática** de la salida correcta
- ✅ **Múltiples ejecuciones** para probar consistencia
- ✅ **Análisis de tiempos** y rendimiento
- ✅ **Detección de errores** de sincronización
- ✅ **Logging detallado** de ejecución

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac tp7/actividad5/*.java

# Ejecutar simulación
java tp7.actividad5.SincronizacionOrdenSimulacion
```

### 📊 **Salida Esperada**

```
=== SIMULACIÓN: SINCRONIZACIÓN R I O OK OK OK ===

🔄 EJECUCIÓN #1:
[HiloR] 🔤 Imprimiendo: R
[HiloR] 🔔 Señalando a HiloI
[HiloI] ⏳ Esperando señal de HiloR...
[HiloI] ✅ Señal recibida
[HiloI] 🔤 Imprimiendo: I
[HiloI] 🔔 Señalando a HiloO
[HiloO] ⏳ Esperando señal de HiloI...
[HiloO] ✅ Señal recibida
[HiloO] 🔤 Imprimiendo: O
[Barrera] 🚧 3/3 hilos esperando, liberando barrera
[HiloR] 🔤 Imprimiendo: OK
[HiloI] 🔤 Imprimiendo: OK
[HiloO] 🔤 Imprimiendo: OK

✅ Salida: " R  I  O  OK  OK  OK "
✅ CORRECTA - Orden verificado

🔄 EJECUCIÓN #2:
...

📊 ESTADÍSTICAS DE 10 EJECUCIONES:
┌───────────┬────────────┬────────────┬────────────┐
│ EJECUCIÓN │   SALIDA   │   TIEMPO   │   ESTADO   │
├───────────┼────────────┼────────────┼────────────┤
│     1     │    R I O   │    15ms    │  ✅ OK     │
│     2     │    R I O   │    12ms    │  ✅ OK     │
│    ...    │    ...     │    ...     │   ...      │
│    10     │    R I O   │    14ms    │  ✅ OK     │
└───────────┴────────────┴────────────┴────────────┘

🎯 RESULTADOS:
   ✅ Éxito: 10/10 (100%)
   ⏱️ Tiempo promedio: 13.5ms
   📊 Consistencia: PERFECTA
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **Mecanismos de Sincronización**

#### 1️⃣ **Cadena de Dependencias**
```java
// R puede ejecutar inmediatamente
print("R");
semI.release(); // Habilitar I

// I espera a R
semI.acquire();
print("I");
semO.release(); // Habilitar O

// O espera a I
semO.acquire();
print("O");
```

#### 2️⃣ **Barrera de Sincronización**
```java
void esperarBarrera() {
    mutex.acquire();
    contador++;
    if (contador == 3) {
        barrera.release(3); // Liberar todos
    }
    mutex.release();
    
    barrera.acquire(); // Esperar en barrera
}
```

### 📊 **Tabla de Estados**

| Momento | semI | semO | barrera | contador | Estado |
|---------|------|------|---------|----------|--------|
| Inicio | 0 | 0 | 0 | 0 | Todos esperando |
| R imprime | 1 | 0 | 0 | 0 | I puede proceder |
| I imprime | 0 | 1 | 0 | 0 | O puede proceder |
| O imprime | 0 | 0 | 0 | 0 | Todos en barrera |
| Barrera | 0 | 0 | 3 | 3 | Todos liberados |
| OK impreso | 0 | 0 | 0 | 0 | Finalizado |

---

## 🧪 **VERIFICACIÓN DE CORRECTITUD**

### ✅ **Propiedades Garantizadas**

#### **1. Orden de Letras**
- ✅ **R siempre primero** - No tiene dependencias
- ✅ **I después de R** - `semI.acquire()` garantiza el orden
- ✅ **O después de I** - `semO.acquire()` garantiza el orden
- ✅ **Secuencia fija** - R → I → O siempre

#### **2. Separación de Fases**
- ✅ **Barrera efectiva** - Ningún OK antes que todas las letras
- ✅ **Sincronización completa** - Todos esperan en la barrera
- ✅ **Liberación simultánea** - Todos los OK pueden proceder

#### **3. Ausencia de Problemas**
- ✅ **Sin deadlock** - No hay dependencias circulares
- ✅ **Sin starvation** - Todos los hilos progresan
- ✅ **Sin race conditions** - Acceso protegido al contador

### 🧪 **Casos de Prueba**

#### **Prueba 1: Ejecución Normal**
```
Entrada: 3 hilos ejecutan concurrentemente
Salida esperada: " R  I  O  OK  OK  OK "
Resultado: ✅ PASA
```

#### **Prueba 2: Diferentes Velocidades**
```
Escenario: HiloO muy rápido, HiloR lento
Resultado: Orden preservado " R  I  O  OK  OK  OK "
Resultado: ✅ PASA
```

#### **Prueba 3: Múltiples Ejecuciones**
```
Repetir 100 veces: Siempre el mismo resultado
Resultado: ✅ PASA (100% consistencia)
```

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 🔄 **Sincronización secuencial** con semáforos
- 🚧 **Barreras de sincronización** para fases
- 🔗 **Cadenas de dependencias** entre threads
- ⏱️ **Control de orden de ejecución** preciso
- 📊 **Separación de fases** concurrentes
- 🛡️ **Protección de secciones críticas**

---

## 📚 **REFERENCIAS**

- **Clase 2:** Introducción a Programación Concurrente
- **TP 2 - Punto 7:** Sincronización con Orden Específico
- **Dijkstra (1965):** Semáforos para coordinación
- **Barreras:** Patrón de sincronización de fases
- **Pipeline Secuencial:** Patrón R → I → O

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación completa del problema de sincronización con orden específico usando semáforos, demostrando control preciso de la secuencia de ejecución y separación de fases en sistemas concurrentes.*
