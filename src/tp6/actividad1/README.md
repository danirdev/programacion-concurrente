# TP6 - Actividad 1: Simulación de Estacionamiento con Semáforos

## Descripción del Problema

Implementación de un sistema de **estacionamiento con semáforos** que controla el acceso de automóviles usando `java.util.concurrent.Semaphore`. El sistema simula un estacionamiento con capacidad para 20 automóviles, 2 entradas, 2 salidas, y procesa 100 automóviles en total.

## Enunciado Original

> Realice un programa que simule un estacionamiento con capacidad para 20 automóviles. Dicho estacionamiento posee dos entradas y dos salidas, un automóvil permanece un tiempo y luego abandona el lugar, con lo cual tendrá que ser cuidadoso para controlar el acceso a dicho estacionamiento de manera de bloquear a los autos que deseen ingresar cuando la capacidad se ha completado. Simule la E/S de 100 automóviles. El estacionamiento inicia vacío.

## Análisis del Sistema con Semáforos

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│              ESTACIONAMIENTO CON SEMÁFOROS                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────┐  │
│  │   SEMÁFOROS     │    │ ESTACIONAMIENTO │    │  CONTROL    │  │
│  │                 │    │                 │    │             │  │
│  │ • Capacidad(20) │───▶│   20 ESPACIOS   │◀───│ • Entradas  │  │
│  │ • Entrada1(1)   │    │                 │    │ • Salidas   │  │
│  │ • Entrada2(1)   │    │ [🚗][🚗][⬜]   │    │ • Mutex     │  │
│  │ • Salida1(1)    │    │ [🚗][⬜][🚗]   │    │             │  │
│  │ • Salida2(1)    │    │ [⬜][🚗][⬜]   │    │             │  │
│  │ • Mutex(1)      │    │                 │    │             │  │
│  └─────────────────┘    └─────────────────┘    └─────────────┘  │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 FLUJO DE AUTOMÓVILES                    │   │
│  │                                                         │   │
│  │  Llegada → Esperar Entrada → Entrar → Estacionar →     │   │
│  │  Esperar Salida → Salir → Liberar Espacio             │   │
│  │                                                         │   │
│  │  100 Automóviles procesados concurrentemente           │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Diferencias con TP5 (Runnable vs Semáforos)

| Aspecto | TP5 (Runnable) | TP6 (Semáforos) |
|---------|----------------|------------------|
| **Control de Capacidad** | `synchronized` + `wait()/notify()` | `Semaphore(20)` |
| **Control de Entradas** | Lógica manual | `Semaphore(1)` por entrada |
| **Control de Salidas** | Lógica manual | `Semaphore(1)` por salida |
| **Exclusión Mutua** | `synchronized` | `Semaphore(1)` (mutex) |
| **Complejidad** | Alta (manejo manual) | Baja (semáforos automáticos) |
| **Legibilidad** | Media | Alta |

## Estructura del Proyecto

```
tp6/actividad1/
├── EstacionamientoSemaforos.java    # Recurso compartido con semáforos
├── AutomovilSemaforo.java           # Thread que usa semáforos
├── GeneradorAutomoviles.java        # Generador de automóviles
├── EstacionamientoSimulacion.java   # Clase principal de simulación
└── README.md                        # Esta documentación
```

## Diseño con Semáforos

### Semáforos Utilizados

```java
public class EstacionamientoSemaforos {
    // Control de capacidad total (20 espacios)
    private final Semaphore capacidad = new Semaphore(20);
    
    // Control de acceso a entradas (1 auto por vez por entrada)
    private final Semaphore entrada1 = new Semaphore(1);
    private final Semaphore entrada2 = new Semaphore(1);
    
    // Control de acceso a salidas (1 auto por vez por salida)
    private final Semaphore salida1 = new Semaphore(1);
    private final Semaphore salida2 = new Semaphore(1);
    
    // Mutex para operaciones críticas (estadísticas, logging)
    private final Semaphore mutex = new Semaphore(1);
}
```

### Algoritmo de Entrada

```java
public void entrar(int automovilId, int entradaId) throws InterruptedException {
    // 1. Esperar espacio disponible en el estacionamiento
    capacidad.acquire();
    
    // 2. Esperar acceso a la entrada específica
    Semaphore entradaSemaforo = (entradaId == 1) ? entrada1 : entrada2;
    entradaSemaforo.acquire();
    
    try {
        // 3. Proceso de entrada (crítico)
        mutex.acquire();
        try {
            // Registrar entrada, actualizar estadísticas
            registrarEntrada(automovilId, entradaId);
        } finally {
            mutex.release();
        }
        
        // 4. Simular tiempo de entrada
        Thread.sleep(200);
        
    } finally {
        // 5. Liberar acceso a la entrada
        entradaSemaforo.release();
    }
}
```

### Algoritmo de Salida

```java
public void salir(int automovilId, int salidaId) throws InterruptedException {
    // 1. Esperar acceso a la salida específica
    Semaphore salidaSemaforo = (salidaId == 1) ? salida1 : salida2;
    salidaSemaforo.acquire();
    
    try {
        // 2. Proceso de salida (crítico)
        mutex.acquire();
        try {
            // Registrar salida, actualizar estadísticas
            registrarSalida(automovilId, salidaId);
        } finally {
            mutex.release();
        }
        
        // 3. Simular tiempo de salida
        Thread.sleep(200);
        
    } finally {
        // 4. Liberar acceso a la salida
        salidaSemaforo.release();
        
        // 5. Liberar espacio en el estacionamiento
        capacidad.release();
    }
}
```

## Ventajas de los Semáforos

### 1. Simplicidad de Código
```java
// Con semáforos (TP6)
capacidad.acquire();  // Esperar espacio
// ... usar recurso ...
capacidad.release();  // Liberar espacio

// Sin semáforos (TP5)
synchronized(this) {
    while (espaciosOcupados >= CAPACIDAD_MAXIMA) {
        wait();
    }
    espaciosOcupados++;
}
// ... usar recurso ...
synchronized(this) {
    espaciosOcupados--;
    notifyAll();
}
```

### 2. Control Granular
```java
// Diferentes semáforos para diferentes recursos
Semaphore entrada1 = new Semaphore(1);    // Control de entrada 1
Semaphore entrada2 = new Semaphore(1);    // Control de entrada 2
Semaphore capacidad = new Semaphore(20);  // Control de capacidad total
```

### 3. Prevención de Deadlocks
```java
// Orden consistente de adquisición de semáforos
// 1. Capacidad primero
// 2. Entrada/Salida segundo
// 3. Mutex último
```

## Casos de Uso Principales

### 1. Entrada Normal
```
Automóvil → capacidad.acquire() → entrada.acquire() → Entrar → entrada.release()
```

### 2. Estacionamiento Lleno
```
Automóvil → capacidad.acquire() [BLOQUEA] → Espera hasta que otro salga
```

### 3. Entrada Ocupada
```
Automóvil → capacidad.acquire() → entrada.acquire() [BLOQUEA] → Espera entrada libre
```

### 4. Salida Normal
```
Automóvil → salida.acquire() → Salir → salida.release() → capacidad.release()
```

## Ejemplo de Salida Esperada

```
=== ESTACIONAMIENTO CON SEMÁFOROS INICIADO ===
Configuración:
  Capacidad: 20 automóviles (Semaphore(20))
  Entradas: 2 (Semaphore(1) cada una)
  Salidas: 2 (Semaphore(1) cada una)
  Automóviles: 100 total

[14:30:15.123] Estacionamiento abierto
[14:30:15.145] Auto-001 esperando espacio... (19 disponibles)
[14:30:15.156] Auto-001 esperando Entrada-1...
[14:30:15.167] Auto-001 entrando por Entrada-1
[14:30:15.178] Auto-001 estacionado (Ocupación: 1/20)

[14:30:16.234] Auto-002 esperando espacio... (18 disponibles)
[14:30:16.245] Auto-002 esperando Entrada-2...
[14:30:16.256] Auto-002 entrando por Entrada-2
[14:30:16.267] Auto-002 estacionado (Ocupación: 2/20)

...

[14:30:45.123] ¡ESTACIONAMIENTO LLENO! (20/20)
[14:30:45.134] Auto-025 esperando espacio... (0 disponibles)
[14:30:46.145] Auto-003 saliendo por Salida-1
[14:30:46.156] Auto-003 liberó espacio (Ocupación: 19/20)
[14:30:46.167] Auto-025 obtuvo espacio disponible

=== SIMULACIÓN COMPLETADA ===
Tiempo total: 45.234 segundos
Automóviles procesados: 100/100
Eficiencia: ✅ EXCELENTE
```

## Métricas del Sistema

### Estadísticas con Semáforos

1. **Utilización de Recursos**:
   - Permits disponibles en cada semáforo
   - Hilos esperando por cada recurso
   - Tiempo promedio de espera

2. **Rendimiento**:
   - Throughput de entradas/salidas
   - Tiempo de ocupación promedio
   - Eficiencia de utilización del estacionamiento

3. **Concurrencia**:
   - Número de hilos bloqueados simultáneamente
   - Contención por recurso
   - Fairness de acceso

## Implementación Técnica

### Clase EstacionamientoSemaforos

```java
public class EstacionamientoSemaforos {
    // Semáforos para control de recursos
    private final Semaphore capacidad;
    private final Semaphore entrada1, entrada2;
    private final Semaphore salida1, salida2;
    private final Semaphore mutex;
    
    // Estadísticas
    private int automovilesActuales = 0;
    private int totalEntradas = 0;
    private int totalSalidas = 0;
    
    public EstacionamientoSemaforos() {
        this.capacidad = new Semaphore(20);      // 20 espacios
        this.entrada1 = new Semaphore(1);        // 1 entrada a la vez
        this.entrada2 = new Semaphore(1);        // 1 entrada a la vez
        this.salida1 = new Semaphore(1);         // 1 salida a la vez
        this.salida2 = new Semaphore(1);         // 1 salida a la vez
        this.mutex = new Semaphore(1);           // Exclusión mutua
    }
}
```

### Clase AutomovilSemaforo

```java
public class AutomovilSemaforo extends Thread {
    private final EstacionamientoSemaforos estacionamiento;
    private final int automovilId;
    private final int entradaPreferida;
    private final int salidaPreferida;
    
    @Override
    public void run() {
        try {
            // Proceso completo del automóvil
            estacionamiento.entrar(automovilId, entradaPreferida);
            
            // Simular tiempo de permanencia
            Thread.sleep(2000 + random.nextInt(6000));
            
            estacionamiento.salir(automovilId, salidaPreferida);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

## Análisis Comparativo

### TP5 (Runnable) vs TP6 (Semáforos)

| Característica | TP5 | TP6 |
|----------------|-----|-----|
| **Líneas de código** | ~150 | ~80 |
| **Complejidad** | Alta | Baja |
| **Manejo de errores** | Manual | Automático |
| **Deadlock prevention** | Manual | Incorporado |
| **Fairness** | No garantizada | Configurable |
| **Performance** | Buena | Excelente |
| **Mantenibilidad** | Media | Alta |

### Ventajas de Semáforos

1. **Código más limpio**: Menos líneas, más legible
2. **Menos propenso a errores**: Manejo automático de recursos
3. **Mejor rendimiento**: Optimizaciones internas del JVM
4. **Fairness configurable**: `Semaphore(permits, fair)`
5. **Interruptibilidad**: `acquireUninterruptibly()`, `tryAcquire()`

## Extensiones Posibles

### 1. Semáforos con Fairness
```java
// Garantizar orden FIFO en acceso a recursos
private final Semaphore capacidad = new Semaphore(20, true);
```

### 2. Timeouts en Adquisición
```java
// Timeout para evitar esperas indefinidas
if (capacidad.tryAcquire(5, TimeUnit.SECONDS)) {
    // Entrar al estacionamiento
} else {
    // Abandonar intento
}
```

### 3. Monitoreo de Semáforos
```java
public void mostrarEstadoSemaforos() {
    System.out.println("Capacidad disponible: " + capacidad.availablePermits());
    System.out.println("Hilos esperando capacidad: " + capacidad.getQueueLength());
    System.out.println("Entrada 1 disponible: " + entrada1.availablePermits());
}
```

### 4. Prioridades de Acceso
```java
// Semáforos separados para diferentes tipos de vehículos
private final Semaphore capacidadVIP = new Semaphore(5);
private final Semaphore capacidadRegular = new Semaphore(15);
```

## Patrones de Diseño con Semáforos

### 1. Resource Pool Pattern
```java
// Pool de recursos limitados
Semaphore pool = new Semaphore(N);
```

### 2. Mutex Pattern
```java
// Exclusión mutua
Semaphore mutex = new Semaphore(1);
```

### 3. Producer-Consumer Pattern
```java
// Control de buffer
Semaphore empty = new Semaphore(BUFFER_SIZE);
Semaphore full = new Semaphore(0);
```

### 4. Barrier Pattern
```java
// Sincronización de fases
Semaphore barrier = new Semaphore(0);
```

## Casos Extremos y Manejo

### 1. Estacionamiento Siempre Lleno
```java
// Timeout para evitar esperas indefinidas
if (!capacidad.tryAcquire(10, TimeUnit.SECONDS)) {
    System.out.println("Auto abandona: estacionamiento saturado");
    return;
}
```

### 2. Interrupciones
```java
try {
    capacidad.acquire();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return; // Salir limpiamente
}
```

### 3. Liberación de Recursos
```java
// Usar try-finally para garantizar liberación
capacidad.acquire();
try {
    // Usar recurso
} finally {
    capacidad.release(); // Siempre liberar
}
```

## Conclusiones Esperadas

### Beneficios de Semáforos

1. **Simplicidad**: Código más limpio y mantenible
2. **Robustez**: Menos propenso a errores de sincronización
3. **Performance**: Optimizaciones internas del JVM
4. **Flexibilidad**: Múltiples opciones de adquisición
5. **Interoperabilidad**: Integración con otros mecanismos de concurrencia

### Cuándo Usar Semáforos

- ✅ Control de recursos limitados
- ✅ Pools de conexiones/objetos
- ✅ Rate limiting
- ✅ Coordinación de fases
- ❌ Sincronización compleja con múltiples condiciones

---

**Próximos Pasos**: Implementación de las clases Java con Semaphore  
**Tiempo Estimado**: Procesamiento de 100 automóviles en ~30-45 segundos  
**Autor**: Curso de Programación Concurrente 2025
