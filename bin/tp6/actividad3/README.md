# TP6 - Actividad 3: Simulación de Estación de Peaje

## Descripción del Problema

Implementación de una **estación de peaje con 3 cabinas** que atienden a una cola de 50 automóviles. Cada cabina demora entre 1 y 3 segundos en atender a un cliente. El sistema incluye dos implementaciones: una sin individualizar cabinas y otra con cabinas individualizadas para identificar cuál atiende a cada cliente.

## Enunciado Original

> Simule la atención en una estación de peaje donde existen 3 cabinas. Cada cabina demora en atender a un cliente un tiempo variable entre 1" y 3". Suponga que la cola de espera es de 50 autos. Deberá indicar el Número de cliente que es atendido, cuando comienza la atención y cuando finaliza la misma. Y para complicarnos la vida, supongamos además que una de las 3 cabinas inicia NO Disponible, es decir, el empleado de una de las cabinas fue al baño y vuelve 15" después de haber iniciado la atención sus dos compañeros.
> 
> a. En el primer intento la ejecución no individualiza cada cabina.
> 
> b. En el segundo intento se le pide que individualice cada cabina, es decir, necesitamos saber que cabina atiende a cada cliente, ¿será esto posible? ¿De qué modo cree que podría resolverlo?

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                    ESTACIÓN DE PEAJE                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────┐  │
│  │   COLA DE       │    │   3 CABINAS     │    │  CONTROL    │  │
│  │   ESPERA        │    │                 │    │             │  │
│  │                 │    │ ┌─────────────┐ │    │ • Semáforos │  │
│  │ 50 Automóviles  │───▶│ │ Cabina 1 ✅ │ │    │ • Logging   │  │
│  │                 │    │ │ Cabina 2 ✅ │ │    │ • Tiempos   │  │
│  │ [Auto-01]       │    │ │ Cabina 3 ❌ │ │    │ • Stats     │  │
│  │ [Auto-02]       │    │ └─────────────┘ │    │             │  │
│  │ [Auto-03]       │    │                 │    │             │  │
│  │ ...             │    │ Tiempo: 1-3s    │    │             │  │
│  │ [Auto-50]       │    │ Cabina 3: +15s  │    │             │  │
│  └─────────────────┘    └─────────────────┘    └─────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### Diferencias entre Implementaciones

| Aspecto | Implementación A | Implementación B |
|---------|------------------|------------------|
| **Identificación de Cabinas** | No individualizada | Cabinas identificadas |
| **Información mostrada** | Solo tiempo de atención | Cabina específica + tiempo |
| **Complejidad** | Baja | Media |
| **Utilidad** | Básica | Análisis detallado |
| **Implementación** | Semáforo simple | Semáforos + identificación |

## Estructura del Proyecto

```
tp6/actividad3/
├── implementacion_a/
│   ├── EstacionPeajeSimple.java         # Peaje sin individualizar cabinas
│   ├── AutomovilPeaje.java              # Automóvil que paga peaje
│   └── PeajeSimpleSimulacion.java       # Main de implementación A
├── implementacion_b/
│   ├── EstacionPeajeDetallada.java      # Peaje con cabinas individualizadas
│   ├── CabinaPeaje.java                 # Representación de cabina individual
│   ├── AutomovilPeajeDetallado.java     # Automóvil con info de cabina
│   └── PeajeDetalladoSimulacion.java    # Main de implementación B
└── README.md                            # Esta documentación
```

## Respuesta a la Pregunta Clave

### ¿Será posible individualizar cada cabina?

**✅ SÍ, es posible y altamente recomendable**

### ¿De qué modo se puede resolver?

**Implementación B** demuestra varias estrategias:

1. **Array de Cabinas Individuales**
2. **Asignación Específica de Recursos**
3. **Tracking de Estado por Cabina**
4. **Logging Detallado con Identificación**

## Conceptos de Concurrencia Aplicados

- **Semáforos**: Control de acceso a recursos limitados
- **Sincronización**: Coordinación entre hilos
- **Exclusión Mutua**: Acceso seguro a recursos compartidos
- **Fairness**: Orden justo de atención (FIFO)
- **Resource Pool**: Gestión de pool de cabinas

## Ejemplo de Salida Esperada

### Implementación A (Sin Individualizar)
```
[14:30:15.123] Cliente 1 esperando cabina...
[14:30:15.124] Cliente 1 siendo atendido (2.1s)
[14:30:15.125] Cliente 2 esperando cabina...
[14:30:15.126] Cliente 2 siendo atendido (1.8s)
[14:30:30.123] ¡Cabina 3 ahora disponible!
```

### Implementación B (Con Individualización)
```
[14:30:15.123] Cliente 1 asignado a Cabina-1
[14:30:15.124] Cabina-1: Atendiendo Cliente 1 (2.1s)
[14:30:15.125] Cliente 2 asignado a Cabina-2
[14:30:30.123] ¡Cabina-3 regresó del baño!
```

## Métricas del Sistema

1. **Rendimiento**: Throughput, tiempo total, eficiencia
2. **Utilización**: Por cabina individual
3. **Balanceamiento**: Distribución de carga
4. **Impacto Cabina 3**: Mejora después de 15s

---

**Implementaciones**: A (Simple) y B (Detallada)  
**Tiempo Estimado**: ~60-90 segundos para 50 autos  
**Autor**: Curso de Programación Concurrente 2025
