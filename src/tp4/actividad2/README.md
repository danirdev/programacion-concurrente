# TP4 - Actividad 2: Simulación de Panadería con Hilos

## Descripción del Problema

Simulación de una **panadería** que produce Bizcochos y Facturas, donde los clientes llegan para comprar productos. El sistema debe manejar la producción continua, la llegada de clientes, y la gestión del mostrador con productos disponibles.

## Enunciado Original

> Imagine el escenario de una panadería que produce Bizcochos y Facturas que son colocados en un mostrador, **cada cliente se lleva un Bizcocho y una Factura**, si los productos aún no están producidos, los clientes esperan. La producción de elementos y la compra son indefinidas (una panadería que trabaja las 24hs sin descansar recibiendo en todo momento clientes compradores). Además hay que destacar que los Bizcochos y Facturas se producen de a uno por vez, puesto que existe dos hornos muy pequeños y solo permiten la producción de un elemento de cada tipo en cualquier momento pero simultáneamente. Simule la situación indicando en cada momento todo lo que sucede en la Panadería: producción de un Bizcocho, producción de una Factura, cliente comprando, cliente esperando, cliente retirándose del local y mostrador vacío. Tenga en cuenta que el tiempo de producción de un Bizcocho es entre 400ms y 600ms, el de una Factura es entre 1000ms y 1300ms, y la llegada de los clientes se produce entre 800ms y 1500ms, la compra y la retirada de los clientes del local demanda un tiempo entre 200ms y 400ms. Debe identificar a cada cliente.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                        PANADERÍA SISTEMA                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │   HORNO     │───▶│                 │◀───│   CLIENTE   │     │
│  │ BIZCOCHOS   │    │   MOSTRADOR     │    │      1      │     │
│  │ 400-600ms   │    │                 │    │ 800-1500ms  │     │
│  └─────────────┘    │ • Bizcochos     │    │ llegada     │     │
│                     │ • Facturas      │    └─────────────┘     │
│  ┌─────────────┐    │                 │    ┌─────────────┐     │
│  │   HORNO     │───▶│ Cada cliente    │◀───│   CLIENTE   │     │
│  │  FACTURAS   │    │ lleva 1+1       │    │      2      │     │
│  │1000-1300ms  │    │                 │    │ 200-400ms   │     │
│  └─────────────┘    └─────────────────┘    │ compra      │     │
│                                            └─────────────┘     │
│  Producción Simultánea    Sincronización    Llegada Aleatoria  │
└─────────────────────────────────────────────────────────────────┘
```

### Características del Sistema

1. **Producción Simultánea**: Dos hornos independientes
   - Horno Bizcochos: 400-600ms por unidad
   - Horno Facturas: 1000-1300ms por unidad

2. **Clientes**: Llegada continua
   - Intervalo de llegada: 800-1500ms
   - Cada cliente necesita: 1 Bizcocho + 1 Factura
   - Tiempo de compra: 200-400ms

3. **Mostrador**: Buffer compartido
   - Almacena productos disponibles
   - Los clientes esperan si no hay productos suficientes

## Estructura del Proyecto

```
tp4/actividad2/
├── Mostrador.java              # Buffer compartido para productos
├── ProductorBizcochos.java     # Hilo productor de bizcochos
├── ProductorFacturas.java      # Hilo productor de facturas
├── Cliente.java                # Hilo cliente que compra productos
├── GeneradorClientes.java      # Hilo que genera clientes
├── PanaderiaSimulacion.java    # Clase principal de simulación
└── README.md                   # Esta documentación
```

## Diagrama de Flujo del Sistema

```
┌─────────────────┐
│ Inicio Sistema  │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear Mostrador │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Iniciar Hornos  │
│ (2 productores) │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Iniciar         │
│ Generador       │
│ Clientes        │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Simulación      │
│ Continua 24hs   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Mostrar         │
│ Estadísticas    │
└─────────────────┘
```

## Estados del Sistema

### Estados del Cliente

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   LLEGADA   │───▶│  ESPERANDO  │───▶│  COMPRANDO  │
│ (generado)  │    │ (productos) │    │ (200-400ms) │
└─────────────┘    └─────────────┘    └─────────┬───┘
                          │                    │
                          ▼                    ▼
                   ┌─────────────┐    ┌─────────────┐
                   │   SALIDA    │◀───│ RETIRÁNDOSE │
                   │ (del local) │    │ (satisfecho)│
                   └─────────────┘    └─────────────┘
```

### Estados del Mostrador

```
┌─────────────────────────────────────────────────────────────┐
│                    ESTADOS DEL MOSTRADOR                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  VACÍO           PARCIAL          COMPLETO                  │
│  B:0 F:0    →    B:X F:Y     →    B:N F:M                  │
│                                                             │
│  Clientes        Algunos          Todos los                │
│  esperan         esperan           clientes                │
│                                   pueden comprar           │
└─────────────────────────────────────────────────────────────┘
```

## Implementación Técnica

### Sincronización Requerida

1. **Acceso al Mostrador**: Múltiples productores y consumidores
2. **Control de Stock**: Verificar disponibilidad antes de vender
3. **Espera de Clientes**: Cuando no hay productos suficientes
4. **Notificación**: Cuando llegan nuevos productos

### Mecanismos de Concurrencia

```java
// Sincronización en el Mostrador
public synchronized void agregarBizcocho()
public synchronized void agregarFactura()
public synchronized boolean comprarProductos(int clienteId)

// Control de espera
while (bizcochos == 0 || facturas == 0) {
    wait(); // Cliente espera productos
}

// Notificación de disponibilidad
notifyAll(); // Despertar clientes esperando
```

## Casos de Uso Principales

### 1. Producción Normal
```
Horno Bizcochos → Produce cada 400-600ms → Mostrador
Horno Facturas  → Produce cada 1000-1300ms → Mostrador
```

### 2. Cliente Satisfecho
```
Cliente llega → Verifica stock → Compra 1B+1F → Se retira
```

### 3. Cliente Esperando
```
Cliente llega → Stock insuficiente → Espera → Compra → Se retira
```

### 4. Mostrador Vacío
```
Sin productos → Múltiples clientes esperando → Producción → Ventas
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Producción**:
   - Bizcochos producidos por minuto
   - Facturas producidas por minuto
   - Tiempo promedio de producción

2. **Ventas**:
   - Clientes atendidos
   - Tiempo promedio de espera
   - Clientes simultáneos esperando

3. **Inventario**:
   - Stock actual en mostrador
   - Stock máximo alcanzado
   - Tiempo con mostrador vacío

### Ejemplo de Salida Esperada

```
=== PANADERÍA EN FUNCIONAMIENTO ===
[10:30:15] Horno Bizcochos: Produciendo bizcocho #45 (520ms)
[10:30:16] Cliente-12 llegó a la panadería
[10:30:16] Cliente-12 esperando productos (B:0, F:2)
[10:30:17] Horno Facturas: Factura #23 lista (1150ms)
[10:30:17] Mostrador: +1 Factura (B:0, F:3)
[10:30:18] Horno Bizcochos: Bizcocho #45 listo (530ms)
[10:30:18] Mostrador: +1 Bizcocho (B:1, F:3)
[10:30:18] Cliente-12 comprando (B:1, F:1 disponibles)
[10:30:18] Cliente-12 compró 1 Bizcocho + 1 Factura (320ms)
[10:30:19] Cliente-12 se retiró satisfecho
[10:30:19] Mostrador actualizado (B:0, F:2)
```

## Desafíos de Implementación

### 1. Sincronización Compleja
- Múltiples productores (2 hornos)
- Múltiples consumidores (clientes)
- Recurso compartido (mostrador)

### 2. Diferentes Velocidades
- Bizcochos más rápidos que facturas
- Llegada irregular de clientes
- Posible acumulación desbalanceada

### 3. Identificación de Estados
- Mostrador vacío vs parcialmente lleno
- Clientes esperando vs comprando
- Producción activa vs inactiva

## Extensiones Posibles

### 1. Tipos de Clientes
```java
enum TipoCliente {
    REGULAR,    // 1 Bizcocho + 1 Factura
    MAYORISTA,  // Múltiples productos
    ESPECIAL    // Solo un tipo de producto
}
```

### 2. Horarios de Funcionamiento
```java
public class HorariosPanaderia {
    private boolean estaAbierta(LocalTime hora);
    private void cerrarPorMantenimiento();
}
```

### 3. Precios y Facturación
```java
public class SistemaFacturacion {
    private double calcularTotal(int bizcochos, int facturas);
    private void generarRecibo(Cliente cliente);
}
```

### 4. Capacidad del Mostrador
```java
public class Mostrador {
    private final int CAPACIDAD_MAXIMA = 50;
    // Control de overflow
}
```

## Análisis de Rendimiento

### Cuellos de Botella Identificados

1. **Producción de Facturas**: Más lenta (1000-1300ms vs 400-600ms)
2. **Sincronización**: Overhead de wait/notify
3. **Llegada de Clientes**: Puede superar capacidad de producción

### Optimizaciones Posibles

1. **Más Hornos de Facturas**: Balancear producción
2. **Buffer Más Grande**: Reducir esperas
3. **Prioridades**: Clientes VIP vs regulares
4. **Batch Processing**: Producción por lotes

## Aplicaciones Reales

### Sistemas Similares

- **Restaurantes**: Cocina → Mostrador → Clientes
- **Fábricas**: Producción → Almacén → Distribución
- **Call Centers**: Agentes → Cola → Clientes
- **Servidores Web**: Workers → Pool → Requests

### Patrones de Diseño Aplicados

- **Producer-Consumer**: Hornos y clientes
- **Observer**: Notificación de productos disponibles
- **Factory**: Creación de productos
- **Singleton**: Mostrador único

## Conclusiones Esperadas

### Comportamiento del Sistema

1. **Facturas como Cuello de Botella**: Su producción más lenta limitará las ventas
2. **Acumulación de Bizcochos**: Se producen más rápido que las facturas
3. **Esperas de Clientes**: Cuando no hay facturas disponibles
4. **Equilibrio Dinámico**: El sistema se autorregula con el tiempo

### Lecciones de Concurrencia

1. **Importancia del Balanceamiento**: Velocidades de producción deben estar equilibradas
2. **Gestión de Recursos**: El mostrador como recurso crítico compartido
3. **Sincronización Eficiente**: Minimizar tiempo en secciones críticas
4. **Monitoreo en Tiempo Real**: Estadísticas para optimización

---

**Próximos Pasos**: Implementación de las clases Java con herencia de Thread  
**Tiempo Estimado de Simulación**: 60 segundos para observar patrones  
**Autor**: Curso de Programación Concurrente 2025
