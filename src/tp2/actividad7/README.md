# TP2 - Actividad 7 - Sincronización de Hilos con Orden Específico

## Enunciado

**7)** Considere los siguientes hilos:

```c
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

Empleando semáforos, se debe asegurar que el resultado final de la impresión sea **"R I O OK OK OK"**.

---

## Análisis del Problema

### Objetivo de Sincronización
- **Resultado deseado**: "R I O OK OK OK"
- **Orden requerido**: 
  1. Primero: R, I, O (en ese orden)
  2. Después: OK, OK, OK (en cualquier orden)

### Estructura del Problema
- **3 hilos concurrentes** que imprimen caracteres
- **Cada hilo** imprime 2 elementos: una letra y "OK"
- **Restricción**: Las letras deben imprimirse en orden R → I → O
- **Flexibilidad**: Los "OK" pueden imprimirse después en cualquier orden

### Descomposición de Operaciones
```
Hilo 1: print("R") → print("OK")
Hilo 2: print("I") → print("OK")  
Hilo 3: print("O") → print("OK")
```

**Dependencias requeridas:**
- R debe imprimir antes que I
- I debe imprimir antes que O
- Todos los "OK" deben imprimir después de todas las letras

---

## Solución con Semáforos

### Estrategia de Sincronización

#### **Enfoque 1: Cadena de Dependencias**
Crear una cadena R → I → O usando semáforos secuenciales.

#### **Enfoque 2: Barrera de Sincronización**
Separar la fase de letras de la fase de "OK" usando una barrera.

### Implementación con Cadena de Dependencias

#### **Semáforos Necesarios**
```pseudocode
VAR sem_I: Semáforo := 0    // Controla cuándo I puede imprimir
VAR sem_O: Semáforo := 0    // Controla cuándo O puede imprimir
VAR sem_OK: Semáforo := 0   // Controla cuándo los OK pueden imprimir
VAR contador_letras: Entero := 0  // Cuenta letras impresas
VAR mutex_contador: Semáforo := 1 // Protege el contador
```

#### **Pseudocódigo Completo**
```pseudocode
PROGRAMA SincronizacionHilos

// Declaración de semáforos
VAR sem_I: Semáforo := 0
VAR sem_O: Semáforo := 0
VAR sem_OK: Semáforo := 0
VAR contador_letras: Entero := 0
VAR mutex_contador: Semáforo := 1

HILO HiloR
BEGIN
    // R puede imprimir inmediatamente
    PRINT(" R ")
    
    // Incrementar contador y verificar si todos terminaron
    wait(mutex_contador)
    contador_letras := contador_letras + 1
    SI contador_letras = 3 ENTONCES
        // Todas las letras impresas, liberar los OK
        signal(sem_OK)
        signal(sem_OK)
        signal(sem_OK)
    FIN SI
    send(mutex_contador)
    
    // Permitir que I imprima
    signal(sem_I)
    
    // Esperar para imprimir OK
    wait(sem_OK)
    PRINT(" OK ")
END

HILO HiloI
BEGIN
    // Esperar hasta que R haya impreso
    wait(sem_I)
    PRINT(" I ")
    
    // Incrementar contador y verificar si todos terminaron
    wait(mutex_contador)
    contador_letras := contador_letras + 1
    SI contador_letras = 3 ENTONCES
        // Todas las letras impresas, liberar los OK
        signal(sem_OK)
        signal(sem_OK)
        signal(sem_OK)
    FIN SI
    send(mutex_contador)
    
    // Permitir que O imprima
    signal(sem_O)
    
    // Esperar para imprimir OK
    wait(sem_OK)
    PRINT(" OK ")
END

HILO HiloO
BEGIN
    // Esperar hasta que I haya impreso
    wait(sem_O)
    PRINT(" O ")
    
    // Incrementar contador y verificar si todos terminaron
    wait(mutex_contador)
    contador_letras := contador_letras + 1
    SI contador_letras = 3 ENTONCES
        // Todas las letras impresas, liberar los OK
        signal(sem_OK)
        signal(sem_OK)
        signal(sem_OK)
    FIN SI
    send(mutex_contador)
    
    // Esperar para imprimir OK
    wait(sem_OK)
    PRINT(" OK ")
END

INICIO
    // Lanzar los tres hilos concurrentemente
    LANZAR HiloR
    LANZAR HiloI
    LANZAR HiloO
    
    // Esperar que todos terminen
    ESPERAR_HILOS()
FIN
```

---

## Solución Simplificada con Barrera

### Estrategia Más Elegante

#### **Semáforos Necesarios**
```pseudocode
VAR sem_I: Semáforo := 0      // I espera a R
VAR sem_O: Semáforo := 0      // O espera a I  
VAR barrera: Semáforo := 0    // Barrera para los OK
VAR mutex: Semáforo := 1      // Protección para contador
VAR contador: Entero := 0     // Cuenta hilos que terminaron letras
```

#### **Implementación Simplificada**
```pseudocode
PROGRAMA SincronizacionSimplificada

VAR sem_I: Semáforo := 0
VAR sem_O: Semáforo := 0
VAR barrera: Semáforo := 0
VAR mutex: Semáforo := 1
VAR contador: Entero := 0

PROCEDIMIENTO EsperarBarrera()
BEGIN
    wait(mutex)
    contador := contador + 1
    SI contador = 3 ENTONCES
        // Último hilo libera la barrera para todos
        signal(barrera)
        signal(barrera)
        signal(barrera)
    FIN SI
    signal(mutex)
    
    // Esperar en la barrera
    wait(barrera)
END

HILO HiloR
BEGIN
    PRINT(" R ")
    signal(sem_I)        // Permitir que I proceda
    EsperarBarrera()     // Esperar que todos terminen letras
    PRINT(" OK ")
END

HILO HiloI  
BEGIN
    wait(sem_I)          // Esperar a R
    PRINT(" I ")
    signal(sem_O)        // Permitir que O proceda
    EsperarBarrera()     // Esperar que todos terminen letras
    PRINT(" OK ")
END

HILO HiloO
BEGIN
    wait(sem_O)          // Esperar a I
    PRINT(" O ")
    EsperarBarrera()     // Esperar que todos terminen letras
    PRINT(" OK ")
END
```

---

## Implementación en Java

### Código Completo en Java
```java
import java.util.concurrent.Semaphore;

public class SincronizacionHilos {
    private static Semaphore semI = new Semaphore(0);
    private static Semaphore semO = new Semaphore(0);
    private static Semaphore barrera = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);
    private static int contador = 0;
    
    private static void esperarBarrera() throws InterruptedException {
        mutex.acquire();
        contador++;
        if (contador == 3) {
            // Último hilo libera la barrera
            barrera.release(3);
        }
        mutex.release();
        
        // Esperar en la barrera
        barrera.acquire();
    }
    
    static class HiloR extends Thread {
        public void run() {
            try {
                System.out.print(" R ");
                semI.release(); // Permitir que I proceda
                esperarBarrera();
                System.out.print(" OK ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class HiloI extends Thread {
        public void run() {
            try {
                semI.acquire(); // Esperar a R
                System.out.print(" I ");
                semO.release(); // Permitir que O proceda
                esperarBarrera();
                System.out.print(" OK ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class HiloO extends Thread {
        public void run() {
            try {
                semO.acquire(); // Esperar a I
                System.out.print(" O ");
                esperarBarrera();
                System.out.print(" OK ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Thread hiloR = new HiloR();
        Thread hiloI = new HiloI();
        Thread hiloO = new HiloO();
        
        hiloR.start();
        hiloI.start();
        hiloO.start();
        
        hiloR.join();
        hiloI.join();
        hiloO.join();
        
        System.out.println(); // Nueva línea al final
    }
}
```

---

## Análisis de Ejecución

### Secuencia Garantizada

#### **Fase 1: Impresión de Letras (Orden Fijo)**
```
Tiempo →
HiloR: |--"R"--[signal(semI)]--[barrera]--|
HiloI: |--[wait(semI)]--"I"--[signal(semO)]--[barrera]--|
HiloO: |--[wait(semO)]--"O"--[barrera]--|

Resultado: " R  I  O "
```

#### **Fase 2: Impresión de OK (Orden Variable)**
```
Tiempo →
Todos los hilos: |--[barrera liberada]--"OK"--|

Resultado posible: " OK  OK  OK "
```

#### **Resultado Final Garantizado**
```
" R  I  O  OK  OK  OK "
```

### Estados de los Semáforos

| Momento | semI | semO | barrera | contador | Estado |
|---------|------|------|---------|----------|--------|
| Inicial | 0 | 0 | 0 | 0 | I y O bloqueados |
| R imprime | 1 | 0 | 0 | 0 | I puede proceder |
| I imprime | 0 | 1 | 0 | 0 | O puede proceder |
| O imprime | 0 | 0 | 0 | 0 | Todos en barrera |
| Barrera | 0 | 0 | 3 | 3 | Todos pueden imprimir OK |

---

## Verificación de Correctitud

### Propiedades Garantizadas

#### **1. Orden de Letras**
- ✅ **R siempre primero**: No tiene dependencias
- ✅ **I después de R**: `wait(semI)` garantiza el orden
- ✅ **O después de I**: `wait(semO)` garantiza el orden
- ✅ **Secuencia fija**: R → I → O siempre

#### **2. Separación de Fases**
- ✅ **Barrera efectiva**: Ningún OK se imprime antes que todas las letras
- ✅ **Sincronización completa**: Todos esperan en la barrera
- ✅ **Liberación simultánea**: Todos los OK pueden proceder juntos

#### **3. Ausencia de Problemas**
- ✅ **Sin deadlock**: No hay dependencias circulares
- ✅ **Sin starvation**: Todos los hilos eventualmente progresan
- ✅ **Sin race conditions**: Acceso protegido al contador

### Casos de Prueba

#### **Prueba 1: Ejecución Normal**
```
Entrada: 3 hilos ejecutan concurrentemente
Salida esperada: " R  I  O  OK  OK  OK "
```

#### **Prueba 2: Diferentes Velocidades**
```
Escenario: HiloO muy rápido, HiloR lento
Resultado: Orden preservado " R  I  O  OK  OK  OK "
```

#### **Prueba 3: Múltiples Ejecuciones**
```
Repetir 100 veces: Siempre el mismo resultado
```

---

## Alternativas de Implementación

### Implementación en Python
```python
import threading
import time

sem_I = threading.Semaphore(0)
sem_O = threading.Semaphore(0)
barrera = threading.Semaphore(0)
mutex = threading.Semaphore(1)
contador = 0

def esperar_barrera():
    global contador
    with mutex:
        contador += 1
        if contador == 3:
            barrera.release(3)
    
    barrera.acquire()

def hilo_R():
    print(" R ", end="")
    sem_I.release()
    esperar_barrera()
    print(" OK ", end="")

def hilo_I():
    sem_I.acquire()
    print(" I ", end="")
    sem_O.release()
    esperar_barrera()
    print(" OK ", end="")

def hilo_O():
    sem_O.acquire()
    print(" O ", end="")
    esperar_barrera()
    print(" OK ", end="")
```

### Implementación en C#
```csharp
using System;
using System.Threading;

public class SincronizacionHilos {
    private static SemaphoreSlim semI = new SemaphoreSlim(0, 1);
    private static SemaphoreSlim semO = new SemaphoreSlim(0, 1);
    private static SemaphoreSlim barrera = new SemaphoreSlim(0, 3);
    private static SemaphoreSlim mutex = new SemaphoreSlim(1, 1);
    private static int contador = 0;
    
    private static async Task EsperarBarrera() {
        await mutex.WaitAsync();
        contador++;
        if (contador == 3) {
            barrera.Release(3);
        }
        mutex.Release();
        
        await barrera.WaitAsync();
    }
    
    // Métodos para hilos R, I, O similares...
}
```

---

## Conclusiones

### Características de la Solución

#### **Elegancia del Diseño**
- **2 semáforos para orden**: `semI` y `semO` crean la cadena R → I → O
- **1 barrera para sincronización**: Separa fases de letras y OK
- **1 mutex para protección**: Acceso seguro al contador compartido

#### **Eficiencia**
- **Mínimo bloqueo**: Solo se bloquea lo necesario
- **Máxima concurrencia**: Los OK se pueden imprimir en paralelo
- **Sincronización precisa**: Barrera libera todos simultáneamente

#### **Robustez**
- **Orden garantizado**: R I O siempre en secuencia
- **Separación de fases**: OK nunca antes que las letras
- **Libre de problemas**: Sin deadlock, starvation o race conditions

### Patrón de Diseño Aplicado

#### **Pipeline con Barrera**
1. **Fase 1**: Pipeline secuencial (R → I → O)
2. **Barrera**: Punto de sincronización
3. **Fase 2**: Ejecución paralela (OK OK OK)

Este patrón es útil para problemas donde se requiere:
- **Orden específico** en una fase
- **Sincronización global** entre fases
- **Paralelismo** en la fase final

La solución demuestra cómo combinar **sincronización secuencial** y **barreras** para lograr un control preciso del orden de ejecución en sistemas concurrentes.
