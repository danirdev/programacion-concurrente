# TP4 - Actividad 3: Simulación de Zoológico con Control de Acceso

## Descripción del Problema

Simulación de un **zoológico** que tiene una única puerta de entrada y salida muy angosta, donde solo puede pasar una persona a la vez. El sistema debe controlar el flujo de visitantes que entran y salen del zoológico de manera continua.

## Enunciado Original

> Modele la entrada a un Zoológico. Dicho Zoo solo tiene una puerta que actúa de entrada y salida, mediante un pasillo muy angosto en el cual solo puede entrar o salir una persona a la vez. Las personas van entrando de forma indefinida en un tiempo variable entre 100-200ms, la entrada por el pasillo le demanda 50ms (lo mismo que la salida), luego permanece en el Zoo un tiempo variable de 400-700, y sale. Indique las acciones de cada persona (identificada por un ID numérico) desde que hace fila para entrar, entra por el pasillo, permanece en el zoo, hace fila para salir y finalmente sale del Zoo.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                        ZOOLÓGICO SISTEMA                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │    FILA     │───▶│     PASILLO     │◀───│    FILA     │     │
│  │  ENTRADA    │    │   ÚNICO 50ms    │    │   SALIDA    │     │
│  │             │    │                 │    │             │     │
│  │ Persona 1   │    │ ┌─────────────┐ │    │ Persona X   │     │
│  │ Persona 2   │    │ │ UNA PERSONA │ │    │ Persona Y   │     │
│  │ Persona 3   │    │ │  A LA VEZ   │ │    │ Persona Z   │     │
│  │    ...      │    │ └─────────────┘ │    │    ...      │     │
│  └─────────────┘    └─────────────────┘    └─────────────┘     │
│                              │                                 │
│                              ▼                                 │
│                    ┌─────────────────┐                         │
│                    │   INTERIOR ZOO  │                         │
│                    │   400-700ms     │                         │
│                    │   (visitando)   │                         │
│                    └─────────────────┘                         │
│                                                                 │
│  Llegada: 100-200ms    Pasillo: 50ms    Visita: 400-700ms     │
└─────────────────────────────────────────────────────────────────┘
```

### Características del Sistema

1. **Pasillo Único**: Recurso crítico compartido
   - Solo una persona puede usarlo a la vez
   - Tiempo fijo: 50ms para entrar o salir
   - Actúa como entrada y salida

2. **Visitantes**: Llegada continua
   - Intervalo de llegada: 100-200ms
   - Tiempo en el zoo: 400-700ms
   - Identificación única por ID numérico

3. **Control de Flujo**: Exclusión mutua
   - Fila de entrada
   - Fila de salida
   - Sincronización bidireccional

## Estructura del Proyecto

```
tp4/actividad3/
├── Pasillo.java                # Recurso crítico compartido (entrada/salida)
├── Visitante.java              # Hilo visitante del zoológico
├── GeneradorVisitantes.java    # Hilo que genera visitantes
├── ZoologicoSimulacion.java    # Clase principal de simulación
└── README.md                   # Esta documentación
```

## Diagrama de Estados del Visitante

```
┌─────────────────┐
│    LLEGADA      │
│   (generado)    │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ FILA ENTRADA    │
│ (esperando      │
│  pasillo)       │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ ENTRANDO        │
│ (usando pasillo │
│  50ms)          │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ VISITANDO ZOO   │
│ (400-700ms)     │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ FILA SALIDA     │
│ (esperando      │
│  pasillo)       │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ SALIENDO        │
│ (usando pasillo │
│  50ms)          │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ FUERA DEL ZOO   │
│ (completado)    │
└─────────────────┘
```

## Diagrama de Flujo del Sistema

```
┌─────────────────┐
│ Inicio Sistema  │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear Pasillo   │
│ (Recurso único) │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Iniciar         │
│ Generador       │
│ Visitantes      │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Simulación      │
│ Continua        │
│ (60 segundos)   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Mostrar         │
│ Estadísticas    │
└─────────────────┘
```

## Estados del Pasillo

```
┌─────────────────────────────────────────────────────────────┐
│                    ESTADOS DEL PASILLO                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  LIBRE           OCUPADO (ENTRADA)    OCUPADO (SALIDA)     │
│                                                             │
│  Disponible  →   Persona X entrando → Persona Y saliendo   │
│  para uso        (50ms)               (50ms)               │
│                                                             │
│  Próximo en      Fila entrada         Fila salida          │
│  fila puede      esperando            esperando             │
│  usarlo                                                     │
└─────────────────────────────────────────────────────────────┘
```

## Implementación Técnica

### Sincronización Requerida

1. **Acceso Exclusivo al Pasillo**: Solo una persona a la vez
2. **Control de Dirección**: Entrada vs salida
3. **Gestión de Filas**: Entrada y salida separadas
4. **Notificación**: Cuando el pasillo se libera

### Mecanismos de Concurrencia

```java
// Sincronización en el Pasillo
public synchronized void entrarAlZoo(int visitanteId)
public synchronized void salirDelZoo(int visitanteId)

// Control de exclusión mutua
while (pasilloOcupado) {
    wait(); // Esperar que se libere el pasillo
}

// Liberación del recurso
pasilloOcupado = false;
notifyAll(); // Despertar visitantes esperando
```

## Casos de Uso Principales

### 1. Entrada Normal
```
Visitante llega → Fila entrada → Usa pasillo (50ms) → Entra al zoo
```

### 2. Salida Normal
```
Termina visita → Fila salida → Usa pasillo (50ms) → Sale del zoo
```

### 3. Conflicto de Acceso
```
Visitante A quiere entrar + Visitante B quiere salir → Uno espera
```

### 4. Pasillo Ocupado
```
Múltiples visitantes → Forman filas → Acceso secuencial al pasillo
```

## Ejemplo de Salida Esperada

```
=== ZOOLÓGICO EN FUNCIONAMIENTO ===
[14:30:15] Visitante-1 llegó al zoológico
[14:30:15] Visitante-1 hace fila para entrar
[14:30:15] Visitante-1 entrando por el pasillo (50ms)
[14:30:15] Visitante-1 entró al zoológico
[14:30:16] Visitante-2 llegó al zoológico
[14:30:16] Visitante-2 hace fila para entrar
[14:30:16] Visitante-1 visitando el zoo (520ms)
[14:30:16] Visitante-2 esperando pasillo (ocupado por entrada)
[14:30:17] Visitante-2 entrando por el pasillo (50ms)
[14:30:17] Visitante-2 entró al zoológico
[14:30:17] Visitante-1 terminó la visita
[14:30:17] Visitante-1 hace fila para salir
[14:30:17] Visitante-1 saliendo por el pasillo (50ms)
[14:30:18] Visitante-1 salió del zoológico
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Flujo de Visitantes**:
   - Visitantes que entraron
   - Visitantes que salieron
   - Visitantes actualmente en el zoo

2. **Uso del Pasillo**:
   - Tiempo total ocupado
   - Número de usos para entrada
   - Número de usos para salida

3. **Tiempos de Espera**:
   - Tiempo promedio en fila de entrada
   - Tiempo promedio en fila de salida
   - Tiempo máximo de espera

4. **Capacidad**:
   - Máximo de visitantes simultáneos en el zoo
   - Tasa de llegada vs tasa de salida

## Desafíos de Implementación

### 1. Recurso Compartido Bidireccional
- El mismo pasillo para entrada y salida
- Prevenir interbloqueos entre direcciones
- Gestión justa de prioridades

### 2. Sincronización Compleja
- Múltiples visitantes esperando entrada
- Múltiples visitantes esperando salida
- Coordinación entre ambas filas

### 3. Estados Concurrentes
- Visitantes en diferentes etapas simultáneamente
- Seguimiento individual de cada visitante
- Estadísticas en tiempo real

## Algoritmo de Control del Pasillo

### Estrategia de Acceso

```java
// Pseudocódigo del control de acceso
public synchronized void usarPasillo(int visitanteId, String direccion) {
    // 1. Esperar hasta que el pasillo esté libre
    while (pasilloOcupado) {
        wait();
    }
    
    // 2. Tomar control del pasillo
    pasilloOcupado = true;
    
    // 3. Usar el pasillo (50ms)
    Thread.sleep(50);
    
    // 4. Liberar el pasillo
    pasilloOcupado = false;
    notifyAll();
}
```

### Prevención de Inanición

1. **FIFO por Dirección**: Primer llegado, primer servido en cada fila
2. **Alternancia**: Evitar que una dirección monopolice el pasillo
3. **Timeout**: Límite de tiempo de espera máximo

## Extensiones Posibles

### 1. Múltiples Pasillo
```java
public class PasilloMultiple {
    private int pasillosDisponibles;
    private final int TOTAL_PASILLOS;
}
```

### 2. Prioridades de Visitantes
```java
enum TipoVisitante {
    REGULAR,
    VIP,
    DISCAPACITADO,
    GRUPO_ESCOLAR
}
```

### 3. Horarios de Funcionamiento
```java
public class HorarioZoo {
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private boolean estaAbierto();
}
```

### 4. Capacidad Máxima del Zoo
```java
public class ControlCapacidad {
    private final int CAPACIDAD_MAXIMA = 100;
    private int visitantesActuales;
}
```

## Análisis de Rendimiento

### Cuellos de Botella Identificados

1. **Pasillo Único**: Principal limitante del sistema
2. **Tiempo Fijo**: 50ms por uso no es optimizable
3. **Llegadas Rápidas**: Pueden superar capacidad de procesamiento

### Métricas de Eficiencia

```
Capacidad Teórica del Pasillo:
- 1 uso cada 50ms = 20 usos por segundo
- 10 usos entrada + 10 usos salida = equilibrio
- Máximo: 1200 usos por minuto
```

### Optimizaciones Posibles

1. **Múltiples Pasillos**: Aumentar throughput
2. **Pasillo Bidireccional**: Carriles separados
3. **Gestión Inteligente**: Priorizar dirección con más cola
4. **Predicción**: Anticipar patrones de flujo

## Aplicaciones Reales

### Sistemas Similares

- **Puentes de Un Carril**: Control de tráfico bidireccional
- **Ascensores**: Recurso compartido con capacidad limitada
- **Túneles**: Acceso controlado en ambas direcciones
- **Torniquetes**: Control de acceso en transporte público

### Patrones de Diseño Aplicados

- **Monitor**: Sincronización del pasillo
- **Producer-Consumer**: Generador y procesamiento de visitantes
- **State Machine**: Estados del visitante
- **Observer**: Monitoreo de estadísticas

## Casos Extremos a Considerar

### 1. Alta Concurrencia
```
Escenario: 50 visitantes llegando simultáneamente
Resultado: Formación de cola larga de entrada
Solución: Control de flujo y timeouts
```

### 2. Desequilibrio de Flujo
```
Escenario: Muchas entradas, pocas salidas
Resultado: Zoo se llena, pasillo monopolizado por salidas
Solución: Algoritmo de balanceamento
```

### 3. Visitantes de Larga Duración
```
Escenario: Visitantes que permanecen mucho tiempo
Resultado: Acumulación en el interior del zoo
Solución: Límite de tiempo de visita
```

## Conclusiones Esperadas

### Comportamiento del Sistema

1. **Pasillo como Cuello de Botella**: Limita el flujo total del sistema
2. **Formación de Colas**: Inevitable con alta demanda
3. **Equilibrio Dinámico**: Sistema se autorregula con el tiempo
4. **Efecto de Lotes**: Visitantes tienden a agruparse

### Lecciones de Concurrencia

1. **Recursos Críticos**: Su gestión determina el rendimiento total
2. **Exclusión Mutua**: Esencial pero costosa en términos de throughput
3. **Fairness**: Importante para evitar inanición de hilos
4. **Monitoreo**: Crucial para detectar cuellos de botella

---

**Próximos Pasos**: Implementación de las clases Java con herencia de Thread  
**Tiempo Estimado de Simulación**: 60 segundos para observar patrones  
**Autor**: Curso de Programación Concurrente 2025
