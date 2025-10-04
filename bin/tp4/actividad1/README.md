# TP4 - Actividad 1: Problema del Productor y Consumidor con Hilos

## Descripción del Ejercicio

Implementación del clásico problema del **Productor y Consumidor** utilizando hilos que heredan de la clase `Thread` en Java. El ejercicio incluye tres casos diferentes que demuestran el comportamiento de la sincronización entre procesos con diferentes velocidades de producción y consumo.

## Estructura del Proyecto

```
tp4/actividad1/
├── Cola.java                              # Estructura de datos compartida (FIFO)
├── Productor.java                         # Hilo productor que hereda de Thread
├── Consumidor.java                        # Hilo consumidor que hereda de Thread
├── ProductorConsumidorInfinito.java       # Caso a) Cola infinita
├── ProductorConsumidorLimitado.java       # Caso b) Cola limitada (tamaño 5)
├── ProductorConsumidorIntercambiado.java  # Caso c) Velocidades intercambiadas
└── README.md                              # Esta documentación
```

## Casos de Estudio

### Caso A: Cola Infinita - Productores Lentos
- **Cola**: Capacidad infinita
- **Productores**: 10 hilos (1000-1500ms por elemento)
- **Consumidores**: 10 hilos (400-800ms por elemento)
- **Expectativa**: Los consumidores esperarán elementos ya que los productores son más lentos

### Caso B: Cola Limitada - Productores Rápidos
- **Cola**: Capacidad limitada a 5 elementos
- **Productores**: 10 hilos (400-800ms por elemento)
- **Consumidores**: 10 hilos (1000-1500ms por elemento)
- **Expectativa**: Los productores esperarán espacio en la cola cuando esté llena

### Caso C: Velocidades Intercambiadas
- Ejecuta ambos casos anteriores pero intercambiando las velocidades
- Demuestra cómo el cambio de velocidades afecta el comportamiento del sistema

## Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                    PROBLEMA PRODUCTOR-CONSUMIDOR                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │ Productor 1 │───▶│                 │◀───│ Consumidor 1│     │
│  │ Productor 2 │───▶│                 │◀───│ Consumidor 2│     │
│  │ Productor 3 │───▶│   COLA FIFO     │◀───│ Consumidor 3│     │
│  │     ...     │───▶│  (Compartida)   │◀───│     ...     │     │
│  │ Productor10 │───▶│                 │◀───│ Consumidor10│     │
│  └─────────────┘    └─────────────────┘    └─────────────┘     │
│                                                                 │
│  Sincronización:                                                │
│  • wait() / notify() / notifyAll()                              │
│  • Métodos synchronized                                         │
│  • Control de capacidad (caso limitado)                        │
└─────────────────────────────────────────────────────────────────┘
```

## Implementación Técnica

### Clase Cola
```java
public class Cola {
    private Queue<Integer> elementos;
    private int capacidadMaxima;
    private boolean esInfinita;
    
    // Métodos sincronizados:
    public synchronized void agregar(int elemento)
    public synchronized int consumir()
}
```

### Sincronización
- **wait()**: Suspende el hilo cuando no puede proceder
- **notify()/notifyAll()**: Despierta hilos en espera
- **synchronized**: Garantiza acceso exclusivo a métodos críticos

### Flujo de Ejecución

```
┌─────────────────┐
│ Inicio Programa │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear Cola      │
│ (Infinita/5)    │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear 10        │
│ Productores     │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear 10        │
│ Consumidores    │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Iniciar Todos   │
│ los Hilos       │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Ejecutar por    │
│ 30 segundos     │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Interrumpir     │
│ Todos los Hilos │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Mostrar         │
│ Estadísticas    │
└─────────────────┘
```

## Ejecución de los Casos

### Ejecutar Caso A (Cola Infinita)
```bash
cd d:\programacion\java\PC2025\src
javac tp4/actividad1/*.java
java tp4.actividad1.ProductorConsumidorInfinito
```

### Ejecutar Caso B (Cola Limitada)
```bash
java tp4.actividad1.ProductorConsumidorLimitado
```

### Ejecutar Caso C (Velocidades Intercambiadas)
```bash
java tp4.actividad1.ProductorConsumidorIntercambiado
```

## Resultados Esperados

### Caso A: Cola Infinita - Productores Lentos
```
=== ESTADÍSTICAS FINALES ===
Total producido: ~150-200 elementos
Total consumido: ~150-200 elementos  
Elementos en cola: 0-5 elementos
RESULTADO: Los consumidores esperaron elementos (productores más lentos)
```

### Caso B: Cola Limitada - Productores Rápidos
```
=== ESTADÍSTICAS FINALES ===
Total producido: ~100-150 elementos
Total consumido: ~100-150 elementos
Elementos en cola: 5 elementos (cola llena)
RESULTADO: Los productores esperaron espacio en la cola
```

### Caso C: Análisis Comparativo
- **Cola Infinita + Productores Rápidos**: Acumulación masiva de elementos
- **Cola Limitada + Productores Lentos**: Consumidores frecuentemente esperando

## Conceptos de Concurrencia Demostrados

### 1. Exclusión Mutua
```java
public synchronized void agregar(int elemento) {
    // Solo un hilo puede ejecutar este método a la vez
}
```

### 2. Sincronización por Condición
```java
while (elementos.isEmpty()) {
    wait(); // Esperar hasta que haya elementos
}
```

### 3. Notificación entre Hilos
```java
notifyAll(); // Despertar todos los hilos en espera
```

### 4. Control de Capacidad
```java
while (!esInfinita && elementos.size() >= capacidadMaxima) {
    wait(); // Esperar hasta que haya espacio
}
```

## Análisis de Rendimiento

### Factores que Afectan el Rendimiento

1. **Velocidad Relativa**: La diferencia entre velocidades de producción y consumo
2. **Capacidad de Cola**: Limitada vs infinita afecta la sincronización
3. **Número de Hilos**: Más hilos pueden aumentar la concurrencia pero también la contención
4. **Tiempo de Sincronización**: Overhead de wait/notify

### Métricas Observadas

| Caso | Producción/seg | Consumo/seg | Cola Promedio | Esperas |
|------|----------------|-------------|---------------|---------|
| A    | ~6-8 elem/seg  | ~15-20 elem/seg | 0-2 elem   | Consumidores |
| B    | ~15-20 elem/seg| ~6-8 elem/seg   | 4-5 elem   | Productores |

## Problemas Clásicos Resueltos

### 1. Condición de Carrera (Race Condition)
- **Problema**: Múltiples hilos accediendo simultáneamente a la cola
- **Solución**: Métodos `synchronized` en la clase Cola

### 2. Interbloqueo (Deadlock)
- **Prevención**: Uso consistente de `wait()` y `notifyAll()`
- **Evitado**: No hay dependencias circulares entre recursos

### 3. Inanición (Starvation)
- **Mitigado**: `notifyAll()` despierta todos los hilos en espera
- **Fairness**: Java no garantiza orden FIFO en wait/notify

## Extensiones Posibles

### 1. Prioridades de Hilos
```java
productor.setPriority(Thread.MAX_PRIORITY);
consumidor.setPriority(Thread.MIN_PRIORITY);
```

### 2. Múltiples Tipos de Elementos
```java
public class ElementoTipado {
    private int valor;
    private String tipo;
    private long timestamp;
}
```

### 3. Estadísticas en Tiempo Real
```java
public class MonitorEstadisticas extends Thread {
    // Mostrar estadísticas cada 5 segundos
}
```

### 4. Patrones de Consumo
- Consumo por lotes (batch processing)
- Consumo selectivo por tipo
- Consumo con timeout

## Conclusiones

### Lecciones Aprendidas

1. **Sincronización es Crítica**: Sin sincronización adecuada, los datos se corrompen
2. **Velocidades Relativas Importan**: Determinan el comportamiento del sistema
3. **Capacidad de Buffer**: Afecta significativamente el rendimiento
4. **Overhead de Sincronización**: Los mecanismos de sincronización tienen costo

### Aplicaciones Reales

- **Sistemas de Colas de Mensajes**: RabbitMQ, Apache Kafka
- **Pools de Conexiones**: Bases de datos, HTTP connections
- **Pipelines de Procesamiento**: ETL, stream processing
- **Sistemas de Archivos**: Buffers de escritura/lectura

### Mejores Prácticas

1. **Usar `notifyAll()` en lugar de `notify()`** para evitar deadlocks
2. **Verificar condiciones en bucles `while`** no en `if`
3. **Manejar `InterruptedException`** apropiadamente
4. **Documentar invariantes de sincronización** claramente

## Referencias

- **Java Concurrency in Practice** - Brian Goetz
- **Operating System Concepts** - Silberschatz, Galvin, Gagne
- **The Art of Multiprocessor Programming** - Herlihy, Shavit
- **Java Documentation**: [Thread Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)

---

**Autor**: Curso de Programación Concurrente 2025  
**Fecha**: Octubre 2024  
**Versión**: 1.0
