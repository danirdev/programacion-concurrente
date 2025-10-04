# TP6 - Actividad 2: Semáforo General con Hilos Thread

## Descripción del Problema

Implementación de un **semáforo general personalizado** usando hilos que heredan de Thread. El sistema simula un semáforo inicializado en 3, donde cada hilo duerme por 5 segundos, demostrando el control de acceso concurrente y el orden de ejecución.

## Enunciado Original

> Implemente, mediante Hilos heredando de Thread, un programa que emplee un semáforo general inicializado en 3 y simplemente cada hilo duerma por 5". Debe indicar el momento antes de empezar a dormir y cuando deja de dormir. Observar el orden de ejecución de los hilos pasando por parámetro el nombre del mismo. Debe lanzar 10 hilos.
> 
> a. En el primer intento genere una clase llamada "Semáforo" y defina e inicialice el semáforo de control dentro de dicha clase.
> 
> b. En el segundo intento defina el semáforo en el main y pase por parámetro a la clase "Semáforo" dicho objeto de control.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                    SEMÁFORO GENERAL PERSONALIZADO               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────┐  │
│  │   SEMÁFORO      │    │   10 HILOS      │    │  CONTROL    │  │
│  │   GENERAL       │    │   (Thread)      │    │             │  │
│  │                 │    │                 │    │             │  │
│  │ • Valor: 3      │───▶│ Hilo-1 ──┐      │    │ • Acquire   │  │
│  │ • Permits: 3    │    │ Hilo-2 ──┤      │    │ • Release   │  │
│  │ • Cola espera   │    │ Hilo-3 ──┼──────┼───▶│ • Wait/Notify│  │
│  │ • Mutex interno │    │ Hilo-4 ──┤      │    │ • Logging   │  │
│  │                 │    │ ...   ──┤      │    │             │  │
│  │                 │    │ Hilo-10──┘      │    │             │  │
│  └─────────────────┘    └─────────────────┘    └─────────────┘  │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 FLUJO DE EJECUCIÓN                      │   │
│  │                                                         │   │
│  │  Hilo → acquire() → [Esperar si valor=0] → Dormir 5s → │   │
│  │  release() → [Despertar hilos esperando]               │   │
│  │                                                         │   │
│  │  Máximo 3 hilos durmiendo simultáneamente              │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Diferencias entre Implementaciones

| Aspecto | Implementación A | Implementación B |
|---------|------------------|------------------|
| **Ubicación del Semáforo** | Dentro de clase Semaforo | En main() |
| **Acceso** | Estático/Singleton | Por parámetro |
| **Acoplamiento** | Alto | Bajo |
| **Flexibilidad** | Baja | Alta |
| **Reutilización** | Limitada | Excelente |

## Estructura del Proyecto

```
tp6/actividad2/
├── implementacion_a/
│   ├── SemaforoInterno.java         # Semáforo definido internamente
│   ├── HiloSemaforoInterno.java     # Thread que usa semáforo interno
│   └── SemaforoInternoSimulacion.java # Main de implementación A
├── implementacion_b/
│   ├── SemaforoExterno.java         # Semáforo pasado por parámetro
│   ├── HiloSemaforoExterno.java     # Thread que recibe semáforo
│   └── SemaforoExternoSimulacion.java # Main de implementación B
└── README.md                        # Esta documentación
```

## Diseño del Semáforo General

### Implementación A: Semáforo Interno

```java
public class SemaforoInterno {
    private static int valor = 3;           // Valor inicial del semáforo
    private static final Object lock = new Object();
    
    public static void acquire(String nombreHilo) throws InterruptedException {
        synchronized(lock) {
            while (valor <= 0) {
                System.out.println(nombreHilo + " esperando... (valor=" + valor + ")");
                lock.wait();
            }
            valor--;
            System.out.println(nombreHilo + " adquirió semáforo (valor=" + valor + ")");
        }
    }
    
    public static void release(String nombreHilo) {
        synchronized(lock) {
            valor++;
            System.out.println(nombreHilo + " liberó semáforo (valor=" + valor + ")");
            lock.notifyAll();
        }
    }
}
```

### Implementación B: Semáforo Externo

```java
public class SemaforoExterno {
    private int valor;
    private final Object lock = new Object();
    
    public SemaforoExterno(int valorInicial) {
        this.valor = valorInicial;
    }
    
    public void acquire(String nombreHilo) throws InterruptedException {
        synchronized(lock) {
            while (valor <= 0) {
                System.out.println(nombreHilo + " esperando... (valor=" + valor + ")");
                lock.wait();
            }
            valor--;
            System.out.println(nombreHilo + " adquirió semáforo (valor=" + valor + ")");
        }
    }
    
    public void release(String nombreHilo) {
        synchronized(lock) {
            valor++;
            System.out.println(nombreHilo + " liberó semáforo (valor=" + valor + ")");
            lock.notifyAll();
        }
    }
}
```

## Algoritmo de Funcionamiento

### Flujo de Cada Hilo

```
┌─────────────────┐
│ Hilo iniciado   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ acquire()       │
│ • Verificar     │
│   valor > 0     │
│ • Si no, wait() │
│ • Si sí, valor--│
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Sección Crítica │
│ • Mostrar inicio│
│ • Dormir 5s     │
│ • Mostrar fin   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ release()       │
│ • valor++       │
│ • notifyAll()   │
└─────────────────┘
```

### Estados del Semáforo

```
Valor = 3: [Hilo1] [Hilo2] [Hilo3] | [Hilo4-wait] [Hilo5-wait] ...
Valor = 2: [Hilo1] [Hilo2] [----] | [Hilo3] [Hilo4-wait] [Hilo5-wait] ...
Valor = 1: [Hilo1] [----] [----] | [Hilo2] [Hilo3] [Hilo4-wait] ...
Valor = 0: [----] [----] [----] | [Hilo1] [Hilo2] [Hilo3] [Hilo4-wait] ...
```

## Casos de Uso Principales

### 1. Acceso Inmediato (valor > 0)
```
Hilo-1 → acquire() → valor=2 → Dormir 5s → release() → valor=3
```

### 2. Espera por Recurso (valor = 0)
```
Hilo-4 → acquire() → valor=0 → wait() → [otro hilo libera] → acquire() → Dormir 5s
```

### 3. Liberación y Notificación
```
Hilo-1 → release() → valor++ → notifyAll() → [hilos esperando despiertan]
```

### 4. Orden de Ejecución
```
Inicio: Hilo-1, Hilo-2, Hilo-3 (inmediato)
Espera: Hilo-4, Hilo-5, ..., Hilo-10
Liberación: Orden no determinista (depende del scheduler)
```

## Ejemplo de Salida Esperada

```
=== SEMÁFORO GENERAL - IMPLEMENTACIÓN A ===
Semáforo inicializado en 3

[14:30:15.123] Hilo-1 adquirió semáforo (valor=2)
[14:30:15.124] Hilo-1 iniciando sueño de 5 segundos...
[14:30:15.125] Hilo-2 adquirió semáforo (valor=1)
[14:30:15.126] Hilo-2 iniciando sueño de 5 segundos...
[14:30:15.127] Hilo-3 adquirió semáforo (valor=0)
[14:30:15.128] Hilo-3 iniciando sueño de 5 segundos...
[14:30:15.129] Hilo-4 esperando... (valor=0)
[14:30:15.130] Hilo-5 esperando... (valor=0)
[14:30:15.131] Hilo-6 esperando... (valor=0)
...

[14:30:20.123] Hilo-1 despertó después de 5 segundos
[14:30:20.124] Hilo-1 liberó semáforo (valor=1)
[14:30:20.125] Hilo-4 adquirió semáforo (valor=0)
[14:30:20.126] Hilo-4 iniciando sueño de 5 segundos...

[14:30:20.127] Hilo-2 despertó después de 5 segundos
[14:30:20.128] Hilo-2 liberó semáforo (valor=1)
[14:30:20.129] Hilo-5 adquirió semáforo (valor=0)
[14:30:20.130] Hilo-5 iniciando sueño de 5 segundos...

=== TODOS LOS HILOS COMPLETADOS ===
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Concurrencia**:
   - Número máximo de hilos durmiendo simultáneamente (3)
   - Número de hilos esperando en cola
   - Tiempo total de ejecución

2. **Orden de Ejecución**:
   - Orden de adquisición del semáforo
   - Orden de liberación del semáforo
   - Fairness en el acceso

3. **Rendimiento**:
   - Tiempo promedio de espera por hilo
   - Utilización del semáforo
   - Throughput del sistema

## Implementación Técnica

### Clase HiloSemaforo (Implementación A)

```java
public class HiloSemaforoInterno extends Thread {
    private final String nombreHilo;
    
    public HiloSemaforoInterno(String nombre) {
        super(nombre);
        this.nombreHilo = nombre;
    }
    
    @Override
    public void run() {
        try {
            // Adquirir semáforo
            SemaforoInterno.acquire(nombreHilo);
            
            // Mostrar inicio de sueño
            System.out.println(nombreHilo + " iniciando sueño de 5 segundos...");
            
            // Dormir 5 segundos
            Thread.sleep(5000);
            
            // Mostrar fin de sueño
            System.out.println(nombreHilo + " despertó después de 5 segundos");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Liberar semáforo
            SemaforoInterno.release(nombreHilo);
        }
    }
}
```

### Clase HiloSemaforo (Implementación B)

```java
public class HiloSemaforoExterno extends Thread {
    private final String nombreHilo;
    private final SemaforoExterno semaforo;
    
    public HiloSemaforoExterno(String nombre, SemaforoExterno semaforo) {
        super(nombre);
        this.nombreHilo = nombre;
        this.semaforo = semaforo;
    }
    
    @Override
    public void run() {
        try {
            // Adquirir semáforo
            semaforo.acquire(nombreHilo);
            
            // Mostrar inicio de sueño
            System.out.println(nombreHilo + " iniciando sueño de 5 segundos...");
            
            // Dormir 5 segundos
            Thread.sleep(5000);
            
            // Mostrar fin de sueño
            System.out.println(nombreHilo + " despertó después de 5 segundos");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Liberar semáforo
            semaforo.release(nombreHilo);
        }
    }
}
```

## Análisis Comparativo

### Implementación A vs Implementación B

| Característica | Implementación A | Implementación B |
|----------------|------------------|------------------|
| **Acoplamiento** | Alto (estático) | Bajo (inyección) |
| **Testabilidad** | Difícil | Fácil |
| **Reutilización** | Limitada | Excelente |
| **Flexibilidad** | Baja | Alta |
| **Singleton** | Implícito | Explícito |
| **Configuración** | Fija | Dinámica |

### Ventajas y Desventajas

#### Implementación A (Semáforo Interno)
**Ventajas:**
- ✅ Simplicidad de uso
- ✅ No requiere pasar parámetros
- ✅ Acceso global automático

**Desventajas:**
- ❌ Alto acoplamiento
- ❌ Difícil de testear
- ❌ No reutilizable
- ❌ Configuración fija

#### Implementación B (Semáforo Externo)
**Ventajas:**
- ✅ Bajo acoplamiento
- ✅ Fácil de testear
- ✅ Altamente reutilizable
- ✅ Configuración flexible

**Desventajas:**
- ❌ Más código de configuración
- ❌ Requiere gestión de instancias

## Extensiones Posibles

### 1. Semáforo con Prioridades
```java
public class SemaforoPrioridad {
    private PriorityQueue<HiloConPrioridad> colaEspera;
    
    public void acquire(String nombre, int prioridad) {
        // Implementar cola de prioridad
    }
}
```

### 2. Semáforo con Timeout
```java
public boolean tryAcquire(String nombre, long timeout) {
    // Implementar timeout en wait()
    return lock.wait(timeout);
}
```

### 3. Semáforo con Estadísticas
```java
public class SemaforoConEstadisticas {
    private int totalAdquisiciones;
    private int totalLiberaciones;
    private long tiempoPromedioEspera;
    
    public void mostrarEstadisticas() {
        // Mostrar métricas del semáforo
    }
}
```

### 4. Semáforo Configurable
```java
public class SemaforoConfigurable {
    public SemaforoConfigurable(int valor, boolean fair, boolean logging) {
        // Configuración flexible
    }
}
```

## Patrones de Diseño Aplicados

### 1. Singleton Pattern (Implementación A)
```java
// Semáforo único global
private static SemaforoInterno instance;
```

### 2. Dependency Injection (Implementación B)
```java
// Inyección del semáforo por constructor
public HiloSemaforo(SemaforoExterno semaforo) {
    this.semaforo = semaforo;
}
```

### 3. Template Method Pattern
```java
// Algoritmo común con variaciones
public abstract class HiloSemaforoBase extends Thread {
    protected abstract void adquirirSemaforo();
    protected abstract void liberarSemaforo();
}
```

## Casos Extremos a Considerar

### 1. Interrupciones
```java
try {
    semaforo.acquire(nombre);
    // ... trabajo ...
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return; // Salir sin liberar
}
```

### 2. Excepciones en Sección Crítica
```java
try {
    semaforo.acquire(nombre);
    // ... trabajo que puede fallar ...
} finally {
    semaforo.release(nombre); // Siempre liberar
}
```

### 3. Deadlocks
```java
// Evitar múltiples semáforos en orden diferente
semaforo1.acquire();
try {
    semaforo2.acquire();
    try {
        // ... trabajo ...
    } finally {
        semaforo2.release();
    }
} finally {
    semaforo1.release();
}
```

## Conclusiones Esperadas

### Comportamiento del Sistema

1. **Máximo 3 hilos simultáneos**: El semáforo limita correctamente
2. **Cola de espera**: Los hilos 4-10 esperan su turno
3. **Orden no determinista**: Depende del scheduler del SO
4. **Fairness**: No garantizada sin implementación específica

### Lecciones de Concurrencia

1. **Semáforos personalizados**: Útiles para casos específicos
2. **wait/notify**: Mecanismo fundamental de sincronización
3. **Acoplamiento**: Impacta testabilidad y mantenibilidad
4. **Inyección de dependencias**: Mejora flexibilidad

---

**Próximos Pasos**: Implementación de ambas versiones (A y B)  
**Tiempo Estimado**: Ejecución completa en ~25-30 segundos  
**Autor**: Curso de Programación Concurrente 2025
