# TP5 - Actividad 2: Simulación de Estacionamiento con Runnable

## Descripción del Problema

Simulación de un **estacionamiento** con capacidad limitada para 20 automóviles, que posee dos entradas y dos salidas. Los automóviles permanecen un tiempo en el estacionamiento y luego lo abandonan. El sistema debe controlar el acceso para bloquear nuevos ingresos cuando la capacidad esté completa.

## Enunciado Original

> Realice un programa que simule un estacionamiento con capacidad para 20 automóviles. Dicho estacionamiento posee dos entradas y dos salidas, un automóvil permanece un tiempo y luego abandona el lugar, con lo cual tendrá que ser cuidadoso para controlar el acceso a dicho estacionamiento de manera de bloquear a los autos que deseen ingresar cuando la capacidad se ha completado. Simule la E/S de 100 automóviles. El estacionamiento inicia vacío.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                    ESTACIONAMIENTO SISTEMA                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ENTRADA 1 ──┐                                   ┌── SALIDA 1   │
│              │    ┌─────────────────────────┐    │              │
│  ENTRADA 2 ──┼───▶│     ESTACIONAMIENTO     │◀───┼── SALIDA 2   │
│              │    │    CAPACIDAD: 20        │    │              │
│              │    │                         │    │              │
│              │    │  [Auto1] [Auto2] ...    │    │              │
│              │    │  [AutoN] [    ] ...     │    │              │
│              │    │  [    ] [    ] ...      │    │              │
│              │    └─────────────────────────┘    │              │
│              │                                   │              │
│         Control de Acceso                   Control de Salida   │
│         (Bloqueo cuando                     (Libera espacios)   │
│          capacidad = 20)                                        │
│                                                                 │
│  Simulación: 100 automóviles total                             │
│  Estado inicial: Estacionamiento vacío                         │
└─────────────────────────────────────────────────────────────────┘
```

### Características del Sistema

1. **Capacidad Limitada**: Máximo 20 automóviles simultáneos
2. **Múltiples Accesos**: 2 entradas y 2 salidas independientes
3. **Control de Flujo**: Bloqueo cuando está lleno
4. **Simulación**: 100 automóviles en total
5. **Estado Inicial**: Estacionamiento vacío

## Estructura del Proyecto

```
tp5/actividad2/
├── Estacionamiento.java          # Recurso compartido con control de capacidad
├── Automovil.java               # Runnable que representa un automóvil
├── GeneradorAutomoviles.java    # Runnable que genera automóviles
├── ControladorEntrada.java      # Runnable que gestiona entradas
├── ControladorSalida.java       # Runnable que gestiona salidas
├── EstacionamientoSimulacion.java # Clase principal de simulación
└── README.md                    # Esta documentación
```

## Diagrama de Estados del Automóvil

```
┌─────────────────┐
│    GENERADO     │
│   (creado)      │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ ESPERANDO       │
│ ENTRADA         │
│ (en cola)       │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ ENTRANDO        │
│ (usando entrada)│
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ ESTACIONADO     │
│ (tiempo variable│
│  permanencia)   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ ESPERANDO       │
│ SALIDA          │
│ (en cola)       │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ SALIENDO        │
│ (usando salida) │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ FUERA DEL       │
│ ESTACIONAMIENTO │
└─────────────────┘
```

## Diagrama de Control de Capacidad

```
┌─────────────────────────────────────────────────────────────┐
│                 CONTROL DE CAPACIDAD                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  VACÍO (0/20)     PARCIAL (N/20)     LLENO (20/20)        │
│                                                             │
│  Entradas: ✅     Entradas: ✅        Entradas: ❌         │
│  Salidas:  ❌     Salidas:  ✅        Salidas:  ✅         │
│                                                             │
│  Automóviles      Flujo normal        Automóviles          │
│  pueden entrar    de entrada/salida   esperan en cola      │
│  libremente                           de entrada           │
└─────────────────────────────────────────────────────────────┘
```

## Implementación Técnica

### Sincronización Requerida

1. **Control de Capacidad**: Máximo 20 automóviles
2. **Acceso a Entradas**: 2 entradas concurrentes
3. **Acceso a Salidas**: 2 salidas concurrentes
4. **Conteo Atómico**: Automóviles dentro del estacionamiento

### Mecanismos de Concurrencia

```java
// Control de capacidad en Estacionamiento
public synchronized void entrar(int automovilId) throws InterruptedException {
    while (automovilesActuales >= CAPACIDAD_MAXIMA) {
        wait(); // Esperar hasta que haya espacio
    }
    automovilesActuales++;
    // Registrar entrada
}

public synchronized void salir(int automovilId) {
    automovilesActuales--;
    notifyAll(); // Notificar que hay espacio disponible
    // Registrar salida
}
```

## Casos de Uso Principales

### 1. Entrada Normal
```
Automóvil generado → Cola entrada → Verificar capacidad → Entrar → Estacionado
```

### 2. Entrada Bloqueada
```
Automóvil generado → Cola entrada → Capacidad llena → Esperar → Entrar cuando hay espacio
```

### 3. Salida Normal
```
Tiempo cumplido → Cola salida → Salir → Liberar espacio → Notificar entradas
```

### 4. Flujo Completo
```
Generación → Entrada → Permanencia → Salida → Finalización
```

## Ejemplo de Salida Esperada

```
=== ESTACIONAMIENTO EN FUNCIONAMIENTO ===
[14:30:15] Estacionamiento iniciado (Capacidad: 0/20)
[14:30:15] Automóvil-001 generado
[14:30:15] Automóvil-001 esperando entrada (Entrada-1)
[14:30:15] Automóvil-001 entrando al estacionamiento
[14:30:15] Estacionamiento: +1 automóvil (1/20)
[14:30:16] Automóvil-002 generado
[14:30:16] Automóvil-001 estacionado (permanencia: 5000ms)
[14:30:17] Automóvil-002 entrando al estacionamiento
[14:30:17] Estacionamiento: +1 automóvil (2/20)
...
[14:30:45] ¡ESTACIONAMIENTO LLENO! (20/20)
[14:30:45] Automóvil-025 esperando espacio disponible
[14:30:46] Automóvil-003 saliendo del estacionamiento
[14:30:46] Estacionamiento: -1 automóvil (19/20)
[14:30:46] Automóvil-025 puede entrar ahora
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Ocupación**:
   - Automóviles actuales en estacionamiento
   - Capacidad utilizada (%)
   - Tiempo con estacionamiento lleno

2. **Flujo de Vehículos**:
   - Total de entradas realizadas
   - Total de salidas realizadas
   - Automóviles esperando entrada

3. **Rendimiento**:
   - Tiempo promedio de permanencia
   - Tiempo promedio de espera para entrar
   - Utilización de entradas y salidas

4. **Colas**:
   - Longitud de cola de entrada
   - Longitud de cola de salida
   - Tiempo máximo de espera

## Desafíos de Implementación

### 1. Control de Capacidad
- Sincronización entre múltiples entradas
- Prevención de condiciones de carrera
- Gestión de esperas cuando está lleno

### 2. Múltiples Puntos de Acceso
- 2 entradas independientes pero coordinadas
- 2 salidas independientes
- Balanceamiento de carga entre accesos

### 3. Gestión de Estados
- Seguimiento individual de cada automóvil
- Estados del estacionamiento en tiempo real
- Coordinación entre generación y procesamiento

## Algoritmo de Control

### Entrada al Estacionamiento
```java
public synchronized void intentarEntrar(int automovilId, int entradaId) 
    throws InterruptedException {
    
    // 1. Verificar capacidad disponible
    while (automovilesActuales >= CAPACIDAD_MAXIMA) {
        System.out.println("Estacionamiento lleno. Auto-" + automovilId + 
                          " esperando en Entrada-" + entradaId);
        wait();
    }
    
    // 2. Registrar entrada
    automovilesActuales++;
    totalEntradas++;
    
    // 3. Asignar espacio
    int espacio = asignarEspacio();
    
    System.out.println("Auto-" + automovilId + " entró por Entrada-" + 
                      entradaId + " (Espacio: " + espacio + 
                      ", Ocupación: " + automovilesActuales + "/20)");
}
```

### Salida del Estacionamiento
```java
public synchronized void salir(int automovilId, int salidaId) {
    // 1. Liberar espacio
    automovilesActuales--;
    totalSalidas++;
    
    // 2. Notificar disponibilidad
    notifyAll();
    
    System.out.println("Auto-" + automovilId + " salió por Salida-" + 
                      salidaId + " (Ocupación: " + automovilesActuales + "/20)");
}
```

## Extensiones Posibles

### 1. Tipos de Vehículos
```java
enum TipoVehiculo {
    AUTO(1),        // Ocupa 1 espacio
    CAMIONETA(2),   // Ocupa 2 espacios
    MOTOCICLETA(0.5); // Ocupa medio espacio
}
```

### 2. Reservas y Prioridades
```java
public class SistemaReservas {
    private Set<Integer> espaciosReservados;
    private Queue<Integer> colaVIP;
    private Queue<Integer> colaRegular;
}
```

### 3. Tarifas y Facturación
```java
public class SistemaFacturacion {
    private double calcularTarifa(long tiempoPermanencia);
    private void generarTicket(int automovilId, double monto);
}
```

### 4. Sensores y Monitoreo
```java
public class SensorOcupacion implements Runnable {
    private void detectarMovimiento();
    private void actualizarEstado();
}
```

## Análisis de Rendimiento

### Cuellos de Botella Identificados

1. **Capacidad Total**: Limitante principal (20 automóviles)
2. **Tiempo de Permanencia**: Afecta rotación de espacios
3. **Velocidad de Generación**: Puede superar capacidad de procesamiento

### Métricas de Eficiencia

```
Capacidad Teórica:
- 20 espacios simultáneos
- Rotación depende del tiempo de permanencia
- 2 entradas + 2 salidas = 4 puntos de acceso concurrentes
```

### Optimizaciones Posibles

1. **Más Puntos de Acceso**: Aumentar entradas/salidas
2. **Gestión Inteligente**: Direccionar a entrada menos congestionada
3. **Reservas**: Sistema de pre-asignación de espacios
4. **Prioridades**: Carriles rápidos para diferentes tipos

## Aplicaciones Reales

### Sistemas Similares

- **Estacionamientos Comerciales**: Centros comerciales, aeropuertos
- **Garajes Automatizados**: Sistemas robotizados
- **Peajes**: Control de flujo vehicular
- **Recursos Limitados**: Pools de conexiones, licencias de software

### Patrones de Diseño Aplicados

- **Semaphore Pattern**: Control de recursos limitados
- **Producer-Consumer**: Generación y procesamiento de vehículos
- **Observer**: Monitoreo de estado del estacionamiento
- **State Machine**: Estados de los automóviles

## Casos Extremos a Considerar

### 1. Alta Demanda
```
Escenario: 50 automóviles llegando simultáneamente
Resultado: Formación de colas largas en entradas
Solución: Gestión de colas y timeouts
```

### 2. Permanencia Prolongada
```
Escenario: Automóviles que permanecen mucho tiempo
Resultado: Baja rotación, estacionamiento siempre lleno
Solución: Límites de tiempo y tarifas progresivas
```

### 3. Salidas Lentas
```
Escenario: Problemas en las salidas
Resultado: Acumulación interna, bloqueo de entradas
Solución: Monitoreo de salidas y rutas alternativas
```

## Conclusiones Esperadas

### Comportamiento del Sistema

1. **Capacidad como Limitante**: Los 20 espacios determinan el flujo máximo
2. **Formación de Colas**: Inevitable con alta demanda
3. **Equilibrio Dinámico**: Entradas y salidas se balancean naturalmente
4. **Efecto de Saturación**: Rendimiento se degrada cerca de la capacidad máxima

### Lecciones de Concurrencia

1. **Recursos Limitados**: Su gestión es crítica para el rendimiento
2. **Múltiples Accesos**: Aumentan throughput pero complican sincronización
3. **Notificación Eficiente**: `notifyAll()` esencial para despertar esperantes
4. **Monitoreo Continuo**: Estadísticas en tiempo real para optimización

---

**Próximos Pasos**: Implementación de las clases Java con interfaz Runnable  
**Tiempo Estimado de Simulación**: 60 segundos para procesar 100 automóviles  
**Autor**: Curso de Programación Concurrente 2025
