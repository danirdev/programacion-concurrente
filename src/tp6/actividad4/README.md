# TP6 - Actividad 4: Simulación de Supermercado

## Descripción del Problema

Implementación de un **supermercado con 3 cajas de atención y 15 carritos** para que los clientes realicen sus compras. Cuando los 15 carritos están ocupados, los clientes esperan afuera del supermercado hasta que se desocupe uno. El sistema simula el flujo completo desde la entrada hasta el pago y salida.

## Enunciado Original

> Un supermercado posee 3 cajas de atención y 15 carritos para que los clientes realicen las compras. Cuando los 15 carritos están ocupados, los clientes esperan afuera del supermercado a que se desocupe uno de estos carritos y así poder entrar al supermercado a comprar. Cada cliente demora en realizar sus compras un tiempo aleatorio entre 4"-7" y cada cajero demora en atender a cada cliente un tiempo aleatorio entre 2"-4". Debe mostrar un mensaje indicando que el "Cliente X entró al Súper y tomó un carrito", otro mensaje cuando el "Cliente X está comprando", otro mensaje cuando "Cliente X está pagando en la caja" y un último mensaje cuando "Cliente X abandona el Súper". Los clientes llegan de forma indefinida al supermercado en un tiempo aleatorio entre 300ms y 500ms.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                        SUPERMERCADO                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────┐  │
│  │   ENTRADA       │    │   ÁREA COMPRAS  │    │   CAJAS     │  │
│  │                 │    │                 │    │             │  │
│  │ Clientes        │───▶│ 15 Carritos     │───▶│ 3 Cajas     │  │
│  │ Esperando       │    │                 │    │             │  │
│  │                 │    │ [Carrito-01]    │    │ [Caja-1] ✅ │  │
│  │ Llegada:        │    │ [Carrito-02]    │    │ [Caja-2] ✅ │  │
│  │ 300-500ms       │    │ ...             │    │ [Caja-3] ✅ │  │
│  │                 │    │ [Carrito-15]    │    │             │  │
│  └─────────────────┘    └─────────────────┘    └─────────────┘  │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 FLUJO DEL CLIENTE                       │   │
│  │                                                         │   │
│  │  Llegar → Esperar Carrito → Entrar y Tomar Carrito →   │   │
│  │  Comprar (4-7s) → Esperar Caja → Pagar (2-4s) →       │   │
│  │  Devolver Carrito → Salir del Supermercado             │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Recursos del Sistema

| Recurso | Cantidad | Función | Control |
|---------|----------|---------|---------|
| **Carritos** | 15 | Permitir compras | Semáforo |
| **Cajas** | 3 | Procesar pagos | Semáforo |
| **Clientes** | Infinito | Llegan continuamente | Thread |

## Estructura del Proyecto

```
tp6/actividad4/
├── Supermercado.java                    # Gestión de carritos y cajas
├── Cliente.java                         # Thread que representa un cliente
├── CajaRegistradora.java               # Representación de caja individual
├── SupermercadoSimulacion.java         # Main de la simulación
└── README.md                           # Esta documentación
```

## Flujo Detallado del Cliente

### Estados del Cliente

1. **LLEGANDO** - Cliente llega al supermercado
2. **ESPERANDO_CARRITO** - Espera que se libere un carrito
3. **COMPRANDO** - Tiene carrito y está comprando (4-7s)
4. **ESPERANDO_CAJA** - Espera turno para pagar
5. **PAGANDO** - Está en caja pagando (2-4s)
6. **SALIENDO** - Devuelve carrito y sale

### Mensajes Requeridos

```java
"Cliente X entró al Súper y tomó un carrito"
"Cliente X está comprando"
"Cliente X está pagando en la caja"
"Cliente X abandona el Súper"
```

## Implementación Técnica

### Clase Supermercado

```java
public class Supermercado {
    private final Semaphore carritos = new Semaphore(15, true);
    private final Semaphore cajas = new Semaphore(3, true);
    
    public void entrarYTomarCarrito(int clienteId) throws InterruptedException {
        carritos.acquire();
        System.out.println("Cliente " + clienteId + " entró al Súper y tomó un carrito");
    }
    
    public void pagarEnCaja(int clienteId) throws InterruptedException {
        cajas.acquire();
        System.out.println("Cliente " + clienteId + " está pagando en la caja");
        // Simular tiempo de pago (2-4s)
        cajas.release();
    }
    
    public void salirYDevolverCarrito(int clienteId) {
        carritos.release();
        System.out.println("Cliente " + clienteId + " abandona el Súper");
    }
}
```

### Clase Cliente

```java
public class Cliente extends Thread {
    private final int clienteId;
    private final Supermercado supermercado;
    
    @Override
    public void run() {
        try {
            // 1. Entrar y tomar carrito
            supermercado.entrarYTomarCarrito(clienteId);
            
            // 2. Comprar (4-7 segundos)
            System.out.println("Cliente " + clienteId + " está comprando");
            Thread.sleep(4000 + random.nextInt(3001)); // 4-7s
            
            // 3. Pagar en caja (2-4 segundos)
            supermercado.pagarEnCaja(clienteId);
            
            // 4. Salir y devolver carrito
            supermercado.salirYDevolverCarrito(clienteId);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

## Características del Sistema

### Concurrencia y Sincronización

- **Semáforo de Carritos**: Controla acceso a 15 carritos
- **Semáforo de Cajas**: Controla acceso a 3 cajas
- **Fairness**: FIFO para orden justo de atención
- **Thread Safety**: Acceso seguro a recursos compartidos

### Tiempos del Sistema

- **Llegada de clientes**: 300-500ms entre arribos
- **Tiempo de compras**: 4-7 segundos por cliente
- **Tiempo de pago**: 2-4 segundos por cliente
- **Tiempo total por cliente**: ~6-11 segundos

### Métricas a Monitorear

1. **Utilización de Carritos**: Porcentaje de carritos en uso
2. **Utilización de Cajas**: Porcentaje de cajas ocupadas
3. **Tiempo de Espera**: Tiempo promedio esperando carrito/caja
4. **Throughput**: Clientes atendidos por minuto
5. **Cola de Espera**: Clientes esperando fuera del supermercado

## Ejemplo de Salida Esperada

```
[14:30:15.123] Cliente 1 entró al Súper y tomó un carrito
[14:30:15.124] Cliente 1 está comprando
[14:30:15.425] Cliente 2 entró al Súper y tomó un carrito
[14:30:15.426] Cliente 2 está comprando
[14:30:20.130] Cliente 1 está pagando en la caja
[14:30:22.135] Cliente 1 abandona el Súper
[14:30:22.136] Cliente 16 entró al Súper y tomó un carrito (esperaba carrito)
[14:30:22.137] Cliente 16 está comprando
```

## Casos de Análisis

### Escenario 1: Baja Demanda
- Pocos clientes simultáneos
- Carritos y cajas disponibles
- Sin esperas significativas

### Escenario 2: Demanda Media
- 10-15 clientes simultáneos
- Algunos carritos ocupados
- Esperas ocasionales en cajas

### Escenario 3: Alta Demanda
- Más de 15 clientes
- Todos los carritos ocupados
- Clientes esperando afuera
- Colas en las cajas

### Escenario 4: Pico de Demanda
- Llegada masiva de clientes
- Cola larga esperando carritos
- Todas las cajas ocupadas
- Sistema al límite de capacidad

## Optimizaciones Posibles

### 1. Gestión Dinámica de Recursos
```java
// Abrir caja adicional en horas pico
if (clientesEsperando > 10) {
    abrirCajaAdicional();
}
```

### 2. Priorización de Clientes
```java
// Clientes premium o con pocos productos
Semaphore cajaRapida = new Semaphore(1, true);
```

### 3. Monitoreo en Tiempo Real
```java
// Dashboard con métricas actualizadas
public void mostrarEstadisticas() {
    System.out.println("Carritos disponibles: " + carritos.availablePermits());
    System.out.println("Cajas disponibles: " + cajas.availablePermits());
}
```

## Patrones de Diseño Aplicados

### 1. Resource Pool Pattern
```java
// Pool de carritos y cajas
Semaphore carritos = new Semaphore(15);
Semaphore cajas = new Semaphore(3);
```

### 2. Producer-Consumer Pattern
```java
// Generador de clientes (Producer)
// Supermercado procesa clientes (Consumer)
```

### 3. State Pattern
```java
// Estados del cliente: ESPERANDO, COMPRANDO, PAGANDO, SALIENDO
```

## Extensiones Posibles

### 1. Diferentes Tipos de Clientes
```java
public enum TipoCliente {
    NORMAL(4000, 7000),    // 4-7s comprando
    RAPIDO(2000, 4000),    // 2-4s comprando
    LENTO(6000, 10000);    // 6-10s comprando
}
```

### 2. Cajas Especializadas
```java
public class CajaRapida extends CajaRegistradora {
    // Solo para clientes con pocos productos
    private static final int MAX_PRODUCTOS = 10;
}
```

### 3. Sistema de Reservas
```java
public class SistemaReservas {
    public void reservarCarrito(int clienteId, LocalTime hora) {
        // Reservar carrito para hora específica
    }
}
```

### 4. Análisis de Patrones
```java
public class AnalizadorPatrones {
    public void analizarHorasPico() {
        // Identificar patrones de demanda
    }
}
```

## Métricas de Rendimiento

### KPIs Principales

1. **Tiempo Promedio de Espera por Carrito**
2. **Tiempo Promedio de Espera por Caja**
3. **Utilización de Recursos** (Carritos y Cajas)
4. **Throughput de Clientes** (clientes/hora)
5. **Satisfacción del Cliente** (basada en tiempos de espera)

### Alertas del Sistema

- ⚠️ **Cola larga**: Más de 10 clientes esperando carrito
- 🔴 **Saturación**: Todos los recursos ocupados por >5 minutos
- 📈 **Pico de demanda**: Llegada >20 clientes en 1 minuto
- 🕐 **Tiempo excesivo**: Cliente esperando >10 minutos

## Conclusiones Esperadas

### Comportamiento del Sistema

1. **Cuellos de Botella**: Identificar si son los carritos o las cajas
2. **Patrones de Uso**: Horarios de mayor demanda
3. **Eficiencia**: Utilización óptima de recursos
4. **Escalabilidad**: Capacidad de manejar demanda variable

### Lecciones de Concurrencia

1. **Semáforos**: Efectivos para control de recursos limitados
2. **Fairness**: Importante para experiencia del cliente
3. **Monitoreo**: Esencial para optimización operativa
4. **Balanceamiento**: Equilibrio entre recursos diferentes

---

**Objetivo**: Simular operación realista de supermercado  
**Tiempo Estimado**: Simulación continua con llegadas cada 300-500ms  
**Autor**: Curso de Programación Concurrente 2025
