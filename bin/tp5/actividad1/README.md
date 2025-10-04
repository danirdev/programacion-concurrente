# TP5 - Actividad 1: Implementación con Interfaz Runnable

## Descripción del Ejercicio

Reimplementación de ejercicios anteriores utilizando la **interfaz Runnable** en lugar de heredar de la clase Thread. Específicamente:

- **Punto 2 del TP N° 3**: Simulación de Panadería
- **Punto 1.a del TP N° 4**: Problema Productor-Consumidor (Cola Infinita)

## Diferencias entre Thread y Runnable

### Herencia de Thread (TP4)
```java
public class Productor extends Thread {
    @Override
    public void run() {
        // Lógica del hilo
    }
}

// Uso
Productor productor = new Productor();
productor.start();
```

### Implementación de Runnable (TP5)
```java
public class Productor implements Runnable {
    @Override
    public void run() {
        // Lógica del hilo
    }
}

// Uso
Thread hiloProductor = new Thread(new Productor());
hiloProductor.start();
```

## Ventajas de Runnable

```
┌─────────────────────────────────────────────────────────────────┐
│                    VENTAJAS DE RUNNABLE                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ✅ HERENCIA MÚLTIPLE                                          │
│     • Permite heredar de otra clase                            │
│     • Mayor flexibilidad en el diseño                          │
│                                                                 │
│  ✅ SEPARACIÓN DE RESPONSABILIDADES                            │
│     • Tarea separada de la ejecución                          │
│     • Mejor diseño orientado a objetos                        │
│                                                                 │
│  ✅ REUTILIZACIÓN                                              │
│     • Mismo Runnable en múltiples hilos                       │
│     • Pools de hilos más eficientes                           │
│                                                                 │
│  ✅ FLEXIBILIDAD                                               │
│     • Diferentes formas de ejecutar la tarea                  │
│     • Compatible con ExecutorService                           │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## Estructura del Proyecto

```
tp5/actividad1/
├── panaderia/
│   ├── MostradorRunnable.java           # Buffer compartido
│   ├── ProductorBizcochos.java          # Runnable productor bizcochos
│   ├── ProductorFacturas.java           # Runnable productor facturas
│   ├── Cliente.java                     # Runnable cliente
│   ├── GeneradorClientes.java           # Runnable generador
│   └── PanaderiaRunnableSimulacion.java # Simulación principal
├── productor_consumidor/
│   ├── ColaRunnable.java                # Cola FIFO sincronizada
│   ├── Productor.java                   # Runnable productor
│   ├── Consumidor.java                  # Runnable consumidor
│   └── ProductorConsumidorRunnable.java # Simulación principal
└── README.md                            # Esta documentación
```

## Patrón de Implementación

### 1. Clase Base Runnable
```java
public class TareaBase implements Runnable {
    protected boolean activo = true;
    protected String nombre;
    
    public TareaBase(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        while (activo && !Thread.currentThread().isInterrupted()) {
            // Lógica específica de la tarea
            ejecutarTarea();
        }
    }
    
    protected abstract void ejecutarTarea();
    
    public void detener() {
        activo = false;
    }
}
```

### 2. Gestión de Hilos
```java
public class GestorHilos {
    private List<Thread> hilos = new ArrayList<>();
    
    public void agregarTarea(Runnable tarea, String nombre) {
        Thread hilo = new Thread(tarea, nombre);
        hilos.add(hilo);
        hilo.start();
    }
    
    public void detenerTodos() {
        for (Thread hilo : hilos) {
            hilo.interrupt();
        }
    }
    
    public void esperarTodos() throws InterruptedException {
        for (Thread hilo : hilos) {
            hilo.join();
        }
    }
}
```

## Comparación de Implementaciones

### Problema Productor-Consumidor

#### Con Thread (TP4)
```java
public class Productor extends Thread {
    private Cola cola;
    
    public Productor(Cola cola) {
        this.cola = cola;
    }
    
    @Override
    public void run() {
        // Lógica del productor
    }
}
```

#### Con Runnable (TP5)
```java
public class Productor implements Runnable {
    private Cola cola;
    private Thread hiloActual;
    
    public Productor(Cola cola) {
        this.cola = cola;
    }
    
    @Override
    public void run() {
        hiloActual = Thread.currentThread();
        // Lógica del productor
    }
    
    public void iniciar(String nombre) {
        Thread hilo = new Thread(this, nombre);
        hilo.start();
    }
}
```

## Casos de Uso Implementados

### 1. Panadería con Runnable

```
┌─────────────────────────────────────────────────────────────────┐
│                    PANADERÍA CON RUNNABLE                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Thread(ProductorBizcochos) ───┐                               │
│                                │                               │
│  Thread(ProductorFacturas) ────┼──► MostradorRunnable ◄────┐   │
│                                │                           │   │
│  Thread(GeneradorClientes) ────┘                           │   │
│                                                            │   │
│                              Thread(Cliente1) ────────────┘   │
│                              Thread(Cliente2)                 │
│                              Thread(ClienteN)                 │
│                                                                 │
│  Ventaja: Cada Runnable puede heredar de otra clase base      │
└─────────────────────────────────────────────────────────────────┘
```

### 2. Productor-Consumidor con Runnable

```
┌─────────────────────────────────────────────────────────────────┐
│                PRODUCTOR-CONSUMIDOR CON RUNNABLE                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Thread(Productor1) ──┐                                        │
│  Thread(Productor2) ──┼──► ColaRunnable ◄──┐                  │
│  Thread(ProductorN) ──┘                     │                  │
│                                             │                  │
│                                Thread(Consumidor1)             │
│                                Thread(Consumidor2)             │
│                                Thread(ConsumidorN)             │
│                                                                 │
│  Ventaja: Reutilización del mismo Runnable en múltiples hilos │
└─────────────────────────────────────────────────────────────────┘
```

## Mejoras en el Diseño

### 1. Clase Base para Tareas
```java
public abstract class TareaConcurrente implements Runnable {
    protected final String nombre;
    protected volatile boolean activo = true;
    protected final DateTimeFormatter timeFormatter;
    
    public TareaConcurrente(String nombre) {
        this.nombre = nombre;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }
    
    @Override
    public final void run() {
        inicializar();
        while (activo && !Thread.currentThread().isInterrupted()) {
            try {
                ejecutarCiclo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        finalizar();
    }
    
    protected abstract void inicializar();
    protected abstract void ejecutarCiclo() throws InterruptedException;
    protected abstract void finalizar();
    
    public void detener() {
        activo = false;
    }
}
```

### 2. Factory para Hilos
```java
public class FabricaHilos {
    public static Thread crearHilo(Runnable tarea, String nombre) {
        Thread hilo = new Thread(tarea, nombre);
        hilo.setDaemon(false);
        return hilo;
    }
    
    public static Thread crearHiloDemonio(Runnable tarea, String nombre) {
        Thread hilo = new Thread(tarea, nombre);
        hilo.setDaemon(true);
        return hilo;
    }
}
```

## Patrones de Concurrencia Aplicados

### 1. Producer-Consumer Pattern
```java
public class ProductorRunnable implements Runnable {
    private final BlockingQueue<Item> cola;
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Item item = producir();
            cola.put(item); // Bloquea si la cola está llena
        }
    }
}
```

### 2. Worker Pool Pattern
```java
public class PoolTrabajadores {
    private final ExecutorService executor;
    
    public PoolTrabajadores(int numHilos) {
        executor = Executors.newFixedThreadPool(numHilos);
    }
    
    public void ejecutarTarea(Runnable tarea) {
        executor.submit(tarea);
    }
}
```

## Gestión Avanzada de Hilos

### 1. Coordinador de Tareas
```java
public class CoordinadorTareas {
    private final List<Thread> hilos = new ArrayList<>();
    private final List<Runnable> tareas = new ArrayList<>();
    
    public void agregarTarea(Runnable tarea, String nombre) {
        tareas.add(tarea);
        Thread hilo = new Thread(tarea, nombre);
        hilos.add(hilo);
    }
    
    public void iniciarTodas() {
        hilos.forEach(Thread::start);
    }
    
    public void detenerTodas() {
        // Detener tareas que implementen interfaz Detenible
        tareas.stream()
              .filter(t -> t instanceof Detenible)
              .forEach(t -> ((Detenible) t).detener());
        
        // Interrumpir hilos
        hilos.forEach(Thread::interrupt);
    }
}
```

### 2. Interfaz para Control
```java
public interface Detenible {
    void detener();
    boolean estaActivo();
}

public interface Estadisticas {
    Map<String, Object> obtenerEstadisticas();
}
```

## Beneficios Observados

### 1. Flexibilidad de Herencia
```java
// Ahora podemos heredar de una clase base
public class ProductorEspecializado extends ClaseBase implements Runnable {
    // Combina funcionalidad de ClaseBase con capacidad de hilo
}
```

### 2. Reutilización de Código
```java
// El mismo Runnable puede usarse en múltiples contextos
Runnable tarea = new MiTarea();
Thread hilo1 = new Thread(tarea, "Hilo-1");
Thread hilo2 = new Thread(tarea, "Hilo-2");
executor.submit(tarea); // En un pool de hilos
```

### 3. Mejor Testabilidad
```java
// Más fácil de testear sin crear hilos reales
@Test
public void testTarea() {
    MiTarea tarea = new MiTarea();
    tarea.run(); // Ejecutar directamente sin hilo
    // Verificar resultados
}
```

## Métricas de Comparación

| Aspecto | Thread | Runnable | Ganador |
|---------|--------|----------|---------|
| Flexibilidad | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Runnable |
| Simplicidad | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | Thread |
| Reutilización | ⭐⭐ | ⭐⭐⭐⭐⭐ | Runnable |
| Testabilidad | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Runnable |
| Rendimiento | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | Empate |

## Casos de Uso Recomendados

### Usar Thread cuando:
- Necesitas funcionalidad específica de Thread
- El código es muy simple
- No necesitas herencia múltiple

### Usar Runnable cuando:
- Necesitas heredar de otra clase
- Quieres reutilizar la tarea
- Planeas usar pools de hilos
- Buscas mejor testabilidad
- Sigues principios de diseño OOP

## Conclusiones

La implementación con **Runnable** ofrece:

1. **Mayor Flexibilidad**: Permite herencia de otras clases
2. **Mejor Diseño**: Separación clara entre tarea y ejecución
3. **Reutilización**: Mismo código en diferentes contextos
4. **Escalabilidad**: Compatible con frameworks modernos
5. **Mantenibilidad**: Código más limpio y testeable

---

**Próximos Pasos**: Implementación de las clases Java con interfaz Runnable  
**Tiempo Estimado**: Comparación directa con implementaciones Thread del TP4  
**Autor**: Curso de Programación Concurrente 2025
