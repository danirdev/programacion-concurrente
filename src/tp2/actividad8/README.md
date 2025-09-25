# TP2 - Actividad 8 - Problema de la Barbería (Sleeping Barber)

## Enunciado

**8)** Mediante semáforos y **pseudocódigo**, resuelva el siguiente problema: Una barbería posee una sala de espera con n sillas y una sala para el barbero. Si no hay clientes esperando, el barbero duerme. Si un cliente entra a la barbería y todas las sillas están ocupadas, el cliente se retira. Si el barbero está ocupado, pero hay sillas, el cliente se sienta en una de las sillas libres. Si el barbero está dormido, el cliente lo despierta. Escriba un programa para coordinar al barbero y a los clientes.

---

## Análisis del Problema

### Descripción del Sistema
- **1 barbero** que puede estar durmiendo, cortando pelo, o esperando
- **n sillas** en la sala de espera (capacidad limitada)
- **Múltiples clientes** que llegan de forma aleatoria

### Estados y Comportamientos

#### **Estados del Barbero:**
1. **Durmiendo**: No hay clientes esperando
2. **Cortando pelo**: Atendiendo a un cliente
3. **Esperando**: Listo para atender al siguiente cliente

#### **Comportamientos de los Clientes:**
1. **Llega y encuentra barbero dormido**: Lo despierta y se corta el pelo
2. **Llega y barbero ocupado con sillas libres**: Se sienta a esperar
3. **Llega y todas las sillas ocupadas**: Se retira sin cortarse el pelo

### Recursos y Sincronización
- **Sillas de espera**: Recurso limitado (n sillas)
- **Barbero**: Recurso único y exclusivo
- **Coordinación**: Cliente-barbero deben sincronizarse

---

## Solución con Semáforos

### Semáforos Necesarios

```pseudocode
// Semáforos para sincronización
VAR clientes: Semáforo := 0        // Cuenta clientes esperando
VAR barbero: Semáforo := 0         // Sincroniza barbero con cliente
VAR mutex: Semáforo := 1           // Protege variables compartidas

// Variables compartidas
VAR sillas_libres: Entero := n     // Número de sillas disponibles
VAR clientes_esperando: Entero := 0 // Clientes en sala de espera
```

### Explicación de Semáforos

#### **clientes (inicializado en 0)**
- **Propósito**: Cuenta el número de clientes esperando atención
- **Uso**: El barbero hace `wait(clientes)` para dormir hasta que llegue un cliente

#### **barbero (inicializado en 0)**  
- **Propósito**: Sincroniza el momento en que barbero está listo para atender
- **Uso**: El cliente hace `wait(barbero)` para esperar que el barbero esté disponible

#### **mutex (inicializado en 1)**
- **Propósito**: Protege el acceso a las variables compartidas
- **Uso**: Exclusión mutua para modificar `sillas_libres` y `clientes_esperando`

---

## Implementación en Pseudocódigo

### Proceso del Barbero

```pseudocode
PROCESO Barbero
BEGIN
    MIENTRAS verdadero HACER
        // Esperar hasta que llegue un cliente
        wait(clientes)
        
        // Proteger acceso a variables compartidas
        wait(mutex)
        
        // Un cliente menos esperando
        clientes_esperando := clientes_esperando - 1
        
        // Una silla más libre
        sillas_libres := sillas_libres + 1
        
        // Señalar que el barbero está listo
        signal(barbero)
        
        // Liberar mutex
        signal(mutex)
        
        // Cortar el pelo (sección crítica del barbero)
        ESCRIBIR("Barbero: Cortando pelo al cliente")
        DELAY(tiempo_corte_pelo)
        ESCRIBIR("Barbero: Terminé de cortar el pelo")
    FIN MIENTRAS
END
```

### Proceso del Cliente

```pseudocode
PROCESO Cliente(id: Entero)
BEGIN
    // Proteger acceso a variables compartidas
    wait(mutex)
    
    // Verificar si hay sillas disponibles
    SI sillas_libres > 0 ENTONCES
        // Hay sillas libres, el cliente se sienta
        sillas_libres := sillas_libres - 1
        clientes_esperando := clientes_esperando + 1
        
        ESCRIBIR("Cliente " + id + ": Me siento a esperar")
        
        // Despertar al barbero (si está dormido)
        signal(clientes)
        
        // Liberar mutex
        signal(mutex)
        
        // Esperar a que el barbero esté listo
        wait(barbero)
        
        ESCRIBIR("Cliente " + id + ": El barbero me está cortando el pelo")
        
    SINO
        // No hay sillas libres, el cliente se va
        ESCRIBIR("Cliente " + id + ": No hay sillas, me voy")
        
        // Liberar mutex
        signal(mutex)
    FIN SI
END
```

### Programa Principal

```pseudocode
PROGRAMA Barberia

// Constantes
CONST n: Entero := 5  // Número de sillas en sala de espera

// Declaración de semáforos
VAR clientes: Semáforo := 0
VAR barbero: Semáforo := 0  
VAR mutex: Semáforo := 1

// Variables compartidas
VAR sillas_libres: Entero := n
VAR clientes_esperando: Entero := 0

INICIO
    ESCRIBIR("Barbería abierta con " + n + " sillas de espera")
    
    // Lanzar proceso del barbero
    LANZAR Barbero
    
    // Simular llegada de clientes
    PARA i := 1 HASTA 20 HACER
        DELAY(tiempo_llegada_aleatoria)
        LANZAR Cliente(i)
    FIN PARA
    
    ESCRIBIR("Simulación terminada")
FIN
```

---

## Análisis de Funcionamiento

### Escenarios de Ejecución

#### **Escenario 1: Barbero dormido, cliente llega**
```pseudocode
Estado inicial: clientes=0, barbero=0, sillas_libres=n

Cliente llega:
    wait(mutex) → adquiere mutex
    sillas_libres > 0 → TRUE
    sillas_libres := n-1
    clientes_esperando := 1
    signal(clientes) → clientes=1 (despierta barbero)
    signal(mutex) → libera mutex
    wait(barbero) → se bloquea esperando barbero

Barbero despierta:
    wait(clientes) → clientes=0, barbero procede
    wait(mutex) → adquiere mutex
    clientes_esperando := 0
    sillas_libres := n
    signal(barbero) → barbero=1 (libera cliente)
    signal(mutex) → libera mutex
    [corta pelo]

Cliente continúa:
    wait(barbero) → barbero=0, cliente procede
    [recibe corte de pelo]
```

#### **Escenario 2: Barbero ocupado, sillas disponibles**
```pseudocode
Estado: barbero cortando pelo, sillas_libres < n

Cliente llega:
    wait(mutex) → adquiere mutex
    sillas_libres > 0 → TRUE
    sillas_libres := sillas_libres - 1
    clientes_esperando := clientes_esperando + 1
    signal(clientes) → incrementa cola de clientes
    signal(mutex) → libera mutex
    wait(barbero) → se bloquea hasta que barbero termine

Resultado: Cliente espera en cola
```

#### **Escenario 3: Todas las sillas ocupadas**
```pseudocode
Estado: sillas_libres = 0

Cliente llega:
    wait(mutex) → adquiere mutex
    sillas_libres > 0 → FALSE
    ESCRIBIR("No hay sillas, me voy")
    signal(mutex) → libera mutex
    [cliente se retira]

Resultado: Cliente se va sin cortarse el pelo
```

---

## Implementación en Java

### Código Completo en Java

```java
import java.util.concurrent.Semaphore;
import java.util.Random;

public class Barberia {
    private static final int NUM_SILLAS = 5;
    private static Semaphore clientes = new Semaphore(0);
    private static Semaphore barbero = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);
    
    private static int sillasLibres = NUM_SILLAS;
    private static int clientesEsperando = 0;
    private static Random random = new Random();
    
    static class Barbero extends Thread {
        public void run() {
            try {
                while (true) {
                    // Esperar hasta que llegue un cliente
                    clientes.acquire();
                    
                    // Proteger variables compartidas
                    mutex.acquire();
                    
                    // Un cliente menos esperando
                    clientesEsperando--;
                    sillasLibres++;
                    
                    // Señalar que barbero está listo
                    barbero.release();
                    
                    // Liberar mutex
                    mutex.release();
                    
                    // Cortar pelo
                    System.out.println("Barbero: Cortando pelo...");
                    Thread.sleep(2000 + random.nextInt(1000));
                    System.out.println("Barbero: Terminé de cortar el pelo");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Cliente extends Thread {
        private int id;
        
        public Cliente(int id) {
            this.id = id;
        }
        
        public void run() {
            try {
                // Proteger acceso a variables compartidas
                mutex.acquire();
                
                if (sillasLibres > 0) {
                    // Hay sillas libres
                    sillasLibres--;
                    clientesEsperando++;
                    
                    System.out.println("Cliente " + id + ": Me siento a esperar (" + 
                                     clientesEsperando + " esperando)");
                    
                    // Despertar barbero
                    clientes.release();
                    
                    // Liberar mutex
                    mutex.release();
                    
                    // Esperar que barbero esté listo
                    barbero.acquire();
                    
                    System.out.println("Cliente " + id + ": Me están cortando el pelo");
                    
                } else {
                    // No hay sillas libres
                    System.out.println("Cliente " + id + ": No hay sillas, me voy");
                    mutex.release();
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Barbería abierta con " + NUM_SILLAS + " sillas de espera");
        
        // Lanzar barbero
        new Barbero().start();
        
        // Simular llegada de clientes
        for (int i = 1; i <= 15; i++) {
            Thread.sleep(random.nextInt(1000));
            new Cliente(i).start();
        }
    }
}
```

---

## Verificación de Correctitud

### Propiedades Garantizadas

#### **1. Exclusión Mutua**
- ✅ **Variables protegidas**: `mutex` protege `sillas_libres` y `clientes_esperando`
- ✅ **Un barbero**: Solo un cliente es atendido a la vez
- ✅ **Acceso seguro**: No hay race conditions en variables compartidas

#### **2. Sincronización Correcta**
- ✅ **Barbero duerme**: Cuando no hay clientes (`wait(clientes)`)
- ✅ **Cliente espera**: Hasta que barbero esté listo (`wait(barbero)`)
- ✅ **Despertar apropiado**: `signal(clientes)` despierta al barbero

#### **3. Capacidad Respetada**
- ✅ **Límite de sillas**: Máximo `n` clientes esperando
- ✅ **Cliente se retira**: Cuando no hay sillas disponibles
- ✅ **Conteo correcto**: `sillas_libres` siempre ≥ 0

#### **4. Progreso**
- ✅ **Sin deadlock**: No hay dependencias circulares
- ✅ **Sin starvation**: Clientes son atendidos en orden FIFO
- ✅ **Barbero activo**: Siempre atiende cuando hay clientes

### Estados Invariantes

#### **Invariantes del Sistema**
1. `0 ≤ sillas_libres ≤ n`
2. `0 ≤ clientes_esperando ≤ n`
3. `sillas_libres + clientes_esperando = n`
4. `clientes.valor() = clientes_esperando`

---

## Extensiones y Variaciones

### Barbería con Múltiples Barberos

```pseudocode
// Para k barberos
VAR barberos_libres: Semáforo := k
VAR mutex_barberos: Semáforo := 1

PROCESO Cliente_Multi(id)
BEGIN
    wait(mutex)
    SI sillas_libres > 0 ENTONCES
        sillas_libres := sillas_libres - 1
        signal(clientes)
        signal(mutex)
        
        // Esperar barbero disponible
        wait(barberos_libres)
        ESCRIBIR("Cliente " + id + ": Siendo atendido")
        
    SINO
        ESCRIBIR("Cliente " + id + ": Me voy")
        signal(mutex)
    FIN SI
END

PROCESO Barbero_Multi(id)
BEGIN
    MIENTRAS verdadero HACER
        wait(clientes)
        wait(mutex)
        sillas_libres := sillas_libres + 1
        signal(mutex)
        
        ESCRIBIR("Barbero " + id + ": Cortando pelo")
        DELAY(tiempo_corte)
        
        signal(barberos_libres)
    FIN MIENTRAS
END
```

### Barbería con Prioridades

```pseudocode
// Clientes VIP tienen prioridad
VAR clientes_vip: Semáforo := 0
VAR clientes_normales: Semáforo := 0

PROCESO Barbero_Prioridad
BEGIN
    MIENTRAS verdadero HACER
        // Primero atender VIP si hay
        SI clientes_vip.valor() > 0 ENTONCES
            wait(clientes_vip)
        SINO
            wait(clientes_normales)
        FIN SI
        
        // Proceder con corte...
    FIN MIENTRAS
END
```

---

## Conclusiones

### Características de la Solución

#### **Elegancia del Diseño**
- **3 semáforos**: Cantidad mínima necesaria
- **Sincronización bidireccional**: Cliente ↔ Barbero
- **Capacidad controlada**: Límite de sillas respetado
- **Estados claros**: Dormido, ocupado, esperando

#### **Robustez**
- **Manejo de capacidad**: Clientes se retiran cuando está lleno
- **Sincronización segura**: Sin race conditions
- **Progreso garantizado**: Sin bloqueos permanentes

#### **Escalabilidad**
- **Parámetro configurable**: Número de sillas `n`
- **Extensible**: Múltiples barberos, prioridades
- **Modular**: Procesos independientes y reutilizables

### Patrón de Diseño: Producer-Consumer con Capacidad Limitada

#### **Elementos del Patrón**
- **Productores**: Clientes que llegan
- **Consumidor**: Barbero que atiende
- **Buffer limitado**: Sillas de espera (capacidad n)
- **Sincronización**: Semáforos para coordinación

#### **Aplicaciones Similares**
- **Servidor web**: Requests y thread pool
- **Impresora compartida**: Jobs y cola de impresión
- **Restaurant**: Clientes y mesas disponibles

### Lecciones Aprendidas

1. **Sincronización compleja**: Requiere múltiples semáforos coordinados
2. **Capacidad limitada**: Manejo explícito de recursos finitos
3. **Estados múltiples**: Barbero puede estar en diferentes estados
4. **Exclusión mutua crítica**: Variables compartidas deben protegerse

El problema de la barbería demuestra cómo usar semáforos para resolver problemas complejos de sincronización con **múltiples estados**, **capacidad limitada** y **coordinación bidireccional** entre procesos.
