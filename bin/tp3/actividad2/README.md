# TP3 - Actividad 2 - Contador Compartido con Hilos

## Enunciado

**2)** Dado un contador V inicializado en 100. Defina 2 hilos, un hilo que incremente 100 veces en 1 dicha variable y otro hilo que decremente 100 veces en 1 dicha variable. Al finalizar imprima el valor final de V.

---

## Análisis del Problema

### Descripción
- **Variable compartida**: Contador V inicializado en 100
- **Hilo 1**: Incrementa V en 1, 100 veces (V = V + 1)
- **Hilo 2**: Decrementa V en 1, 100 veces (V = V - 1)
- **Resultado esperado**: V debería ser 100 (valor inicial)
- **Problema**: Race conditions pueden causar resultados incorrectos

### Conceptos Demostrados
- **Variables compartidas** entre hilos
- **Race conditions** (condiciones de carrera)
- **Operaciones no atómicas** (lectura-modificación-escritura)
- **Necesidad de sincronización**

---

## Implementación

### Estructura de Clases

1. **`Contador.java`** - Clase que encapsula la variable compartida
2. **`HiloIncrementador.java`** - Hilo que incrementa el contador
3. **`HiloDecrementador.java`** - Hilo que decrementa el contador
4. **`ContadorSincronizado.java`** - Versión sincronizada del contador
5. **`ExperimentoContador.java`** - Clase principal para experimentos
6. **`TestContador.java`** - Pruebas básicas

### Clase 1: Contador (Sin Sincronización)

**Propósito**: Demuestra el problema de race conditions

```java
public class Contador {
    private int valor;
    
    public Contador(int valorInicial) {
        this.valor = valorInicial;
    }
    
    public void incrementar() {
        valor++;  // Operación NO atómica
    }
    
    public void decrementar() {
        valor--;  // Operación NO atómica
    }
    
    public int getValor() {
        return valor;
    }
}
```

### Clase 2: HiloIncrementador

**Propósito**: Hilo que incrementa el contador 100 veces

```java
public class HiloIncrementador extends Thread {
    private Contador contador;
    
    public HiloIncrementador(Contador contador) {
        this.contador = contador;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            contador.incrementar();
        }
    }
}
```

### Clase 3: HiloDecrementador

**Propósito**: Hilo que decrementa el contador 100 veces

```java
public class HiloDecrementador extends Thread {
    private Contador contador;
    
    public HiloDecrementador(Contador contador) {
        this.contador = contador;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            contador.decrementar();
        }
    }
}
```

---

## Cómo Ejecutar

### Opción 1: Prueba Básica
```bash
javac tp3/actividad2/*.java
java tp3.actividad2.TestContador
```

### Opción 2: Experimento Completo
```bash
javac tp3/actividad2/*.java
java tp3.actividad2.ExperimentoContador
```

---

## Resultados Esperados

### Sin Sincronización
- **Resultado teórico**: 100 (100 + 100 - 100 = 100)
- **Resultado real**: Variable (95, 98, 102, 105, etc.)
- **Causa**: Race conditions en operaciones no atómicas

### Con Sincronización
- **Resultado**: Siempre 100
- **Método**: synchronized, AtomicInteger, o locks

---

## Análisis de Race Conditions

### Problema de la Operación No Atómica

La operación `valor++` se descompone en:
1. **Leer** valor actual
2. **Incrementar** en memoria
3. **Escribir** nuevo valor

Si dos hilos ejecutan simultáneamente:
```
Hilo1: lee valor=100
Hilo2: lee valor=100
Hilo1: incrementa a 101, escribe 101
Hilo2: decrementa a 99, escribe 99
Resultado: 99 (se perdió una operación)
```

### Soluciones de Sincronización

1. **synchronized**: Exclusión mutua
2. **AtomicInteger**: Operaciones atómicas
3. **ReentrantLock**: Control explícito de locks

Este ejercicio demuestra la importancia de la sincronización en programación concurrente.
