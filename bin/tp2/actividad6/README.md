# TP2 - Actividad 6 - Sincronización con Dependencias de Procesos

## Enunciado

**6)** Existen 2 procesos concurrentes donde **S no puede ejecutar S₂ hasta que R haya ejecutado R₁**. ¿Cuántos semáforos deberías emplear y en qué valores los deberías inicializar? ¿Dónde pondrías las operaciones `wait()` y `signal()`?

| R | S |
|---|---|
| R₁ | S₁ |
| R₂ | S₂ |

---

## Análisis del Problema

### Restricción de Sincronización
- **Dependencia**: S₂ no puede ejecutar hasta que R₁ haya terminado
- **Orden requerido**: R₁ debe completarse **antes** que S₂ comience
- **Procesos independientes**: R₂ y S₁ pueden ejecutar libremente

### Diagrama de Dependencias
```
R₁ -----> S₂
 |         |
 v         v  
R₂        S₁ (independientes)
```

---

## Solución con Semáforos

### Cantidad de Semáforos Necesarios
**Respuesta: 1 semáforo**

Solo necesitamos **un semáforo** para controlar la dependencia R₁ → S₂.

### Valor de Inicialización
**Respuesta: 0**

El semáforo debe inicializarse en **0** porque:
- S₂ debe **esperar** hasta que R₁ complete
- Inicialmente, R₁ **no ha ejecutado**, por lo que S₂ debe estar **bloqueado**

### Ubicación de Operaciones

#### **Semáforo de Sincronización**
```pseudocode
VAR sincronizacion: Semáforo := 0
```

#### **Proceso R**
```pseudocode
PROCESO R
BEGIN
    // R₁ puede ejecutar libremente
    EJECUTAR R₁
    
    // Después de completar R₁, señalar que S₂ puede proceder
    signal(sincronizacion)
    
    // R₂ puede ejecutar libremente (no tiene restricciones)
    EJECUTAR R₂
END
```

#### **Proceso S**
```pseudocode
PROCESO S
BEGIN
    // S₁ puede ejecutar libremente (no tiene restricciones)
    EJECUTAR S₁
    
    // S₂ debe esperar hasta que R₁ haya completado
    wait(sincronizacion)
    EJECUTAR S₂
END
```

---

## Implementación Completa

### Pseudocódigo Detallado

```pseudocode
PROGRAMA SincronizacionProcesos

// Declaración del semáforo
VAR sincronizacion: Semáforo := 0

PROCESO R
BEGIN
    ESCRIBIR("R: Iniciando R₁")
    // Simular ejecución de R₁
    DELAY(tiempo_R1)
    ESCRIBIR("R: R₁ completado")
    
    // Señalar que R₁ ha terminado, S₂ puede proceder
    signal(sincronizacion)
    ESCRIBIR("R: Señal enviada para S₂")
    
    ESCRIBIR("R: Iniciando R₂")
    // Simular ejecución de R₂
    DELAY(tiempo_R2)
    ESCRIBIR("R: R₂ completado")
END

PROCESO S
BEGIN
    ESCRIBIR("S: Iniciando S₁")
    // Simular ejecución de S₁
    DELAY(tiempo_S1)
    ESCRIBIR("S: S₁ completado")
    
    ESCRIBIR("S: Esperando que R₁ complete...")
    // Esperar hasta que R₁ haya completado
    wait(sincronizacion)
    ESCRIBIR("S: Recibida señal, iniciando S₂")
    
    // Simular ejecución de S₂
    DELAY(tiempo_S2)
    ESCRIBIR("S: S₂ completado")
END

// Programa principal
INICIO
    // Inicializar semáforo
    sincronizacion := CrearSemáforo(0)
    
    // Lanzar procesos concurrentes
    LANZAR PROCESO R
    LANZAR PROCESO S
    
    // Esperar que ambos procesos terminen
    ESPERAR_PROCESOS()
    
    ESCRIBIR("Programa terminado")
FIN
```

---

## Análisis de Ejecución

### Posibles Secuencias de Ejecución

#### **Secuencia 1: R₁ ejecuta primero**
```
Tiempo →
R: |--R₁--[signal]--R₂--|
S: |--S₁--[wait]--S₂--|

Resultado: ✅ Correcto - S₂ ejecuta después de R₁
```

#### **Secuencia 2: S₁ ejecuta primero, S₂ espera**
```
Tiempo →
R: |----R₁----[signal]--R₂--|
S: |--S₁--[wait bloqueado]--S₂--|

Resultado: ✅ Correcto - S₂ espera hasta que R₁ complete
```

#### **Secuencia 3: Ejecución intercalada**
```
Tiempo →
R: |--R₁--[signal]----R₂--|
S: |----S₁----[wait]--S₂--|

Resultado: ✅ Correcto - Orden preservado
```

### Estados del Semáforo

| Momento | Operación | Valor del Semáforo | Estado de S₂ |
|---------|-----------|-------------------|--------------|
| Inicial | - | 0 | Puede esperar |
| R₁ completa | `signal()` | 1 | Puede proceder |
| S₂ inicia | `wait()` | 0 | Ejecutando |

---

## Verificación de Correctitud

### Propiedades Garantizadas

#### **1. Dependencia Respetada**
- ✅ **S₂ nunca ejecuta antes que R₁**: El semáforo inicializado en 0 lo impide
- ✅ **R₁ siempre precede a S₂**: `signal()` después de R₁ permite que S₂ proceda

#### **2. No Bloqueo Innecesario**
- ✅ **R₁ y R₂ ejecutan libremente**: No tienen restricciones
- ✅ **S₁ ejecuta libremente**: No depende de ningún proceso
- ✅ **Solo S₂ espera**: Únicamente cuando es necesario

#### **3. Progreso Garantizado**
- ✅ **Sin deadlock**: No hay dependencias circulares
- ✅ **Sin starvation**: S₂ eventualmente ejecuta cuando R₁ complete

### Casos de Prueba

#### **Caso 1: R₁ muy rápido**
```pseudocode
R₁ (rápido) → signal() → S₂ puede proceder inmediatamente
```

#### **Caso 2: S₂ intenta ejecutar primero**
```pseudocode
S₂ intenta → wait() bloquea → espera hasta signal() de R₁
```

#### **Caso 3: Ejecución simultánea**
```pseudocode
R₁ y S₁ ejecutan en paralelo → R₁ termina → S₂ puede proceder
```

---

## Alternativas de Implementación

### Implementación en Java
```java
import java.util.concurrent.Semaphore;

public class SincronizacionProcesos {
    private static Semaphore sincronizacion = new Semaphore(0);
    
    static class ProcesoR extends Thread {
        public void run() {
            try {
                System.out.println("R: Ejecutando R₁");
                Thread.sleep(1000); // Simular R₁
                System.out.println("R: R₁ completado");
                
                sincronizacion.release(); // signal()
                System.out.println("R: Señal enviada");
                
                System.out.println("R: Ejecutando R₂");
                Thread.sleep(500); // Simular R₂
                System.out.println("R: R₂ completado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class ProcesoS extends Thread {
        public void run() {
            try {
                System.out.println("S: Ejecutando S₁");
                Thread.sleep(800); // Simular S₁
                System.out.println("S: S₁ completado");
                
                System.out.println("S: Esperando señal...");
                sincronizacion.acquire(); // wait()
                System.out.println("S: Ejecutando S₂");
                Thread.sleep(600); // Simular S₂
                System.out.println("S: S₂ completado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

### Implementación en Python
```python
import threading
import time

# Semáforo inicializado en 0
sincronizacion = threading.Semaphore(0)

def proceso_R():
    print("R: Ejecutando R₁")
    time.sleep(1)  # Simular R₁
    print("R: R₁ completado")
    
    sincronizacion.release()  # signal()
    print("R: Señal enviada")
    
    print("R: Ejecutando R₂")
    time.sleep(0.5)  # Simular R₂
    print("R: R₂ completado")

def proceso_S():
    print("S: Ejecutando S₁")
    time.sleep(0.8)  # Simular S₁
    print("S: S₁ completado")
    
    print("S: Esperando señal...")
    sincronizacion.acquire()  # wait()
    print("S: Ejecutando S₂")
    time.sleep(0.6)  # Simular S₂
    print("S: S₂ completado")
```

---

## Respuestas Directas a las Preguntas

### **¿Cuántos semáforos deberías emplear?**
**Respuesta: 1 semáforo**

Solo necesitamos un semáforo para controlar la dependencia R₁ → S₂.

### **¿En qué valores los deberías inicializar?**
**Respuesta: 0**

El semáforo debe inicializarse en 0 para que S₂ esté inicialmente bloqueado.

### **¿Dónde pondrías las operaciones wait() y signal()?**

**signal()**: Después de que R₁ complete en el proceso R
```pseudocode
EJECUTAR R₁
signal(sincronizacion)  // ← Aquí
EJECUTAR R₂
```

**wait()**: Antes de que S₂ ejecute en el proceso S
```pseudocode
EJECUTAR S₁
wait(sincronizacion)    // ← Aquí
EJECUTAR S₂
```

---

## Conclusiones

### Características de la Solución
1. **Minimalista**: Solo 1 semáforo necesario
2. **Eficiente**: No bloquea operaciones innecesarias
3. **Correcta**: Garantiza la dependencia R₁ → S₂
4. **Escalable**: Fácil de extender para más dependencias

### Principios Aplicados
- **Sincronización por señales**: Uso de semáforos para coordinación
- **Inicialización apropiada**: Valor 0 para dependencias
- **Ubicación estratégica**: `signal()` después del prerequisito, `wait()` antes del dependiente
- **Mínima restricción**: Solo se bloquea lo necesario

Esta solución demuestra cómo usar semáforos de forma elegante para establecer dependencias entre procesos concurrentes, garantizando el orden requerido sin introducir bloqueos innecesarios.
