# TP3 - Actividad 1 - Implementación de Hilos en Java

## Enunciado

**1)** Implemente lo siguiente:

**a.** Una clase que imprima el siguiente patrón de sucesión de X: ".0X." (.iX. para i=0......99), a continuación debe imprimir un patrón de sucesión de Y: ".0Y." (.iY. para i=0......99).

**b.** Mediante Hilos heredando de Thread, emplee dos clases para realizar dicha impresión de forma concurrente. Repita el proceso 10 veces para observar cómo se imprimen los distintos lanzamientos.

**c.** Obtenga la porción de la fila que posea la sucesión más larga de cualquier letra. Imprima toda la sucesión.

**d.** Si en el inciso b) no se imprimen correctamente utilice una pausa o join.

**e.** Razone y comente los resultados obtenidos.

---

## Implementación Completa

### Estructura de Clases Creadas

1. **`ImpresorSecuencial.java`** - Implementación secuencial (inciso a)
2. **`HiloX.java`** - Hilo para patrón X (inciso b)
3. **`HiloY.java`** - Hilo para patrón Y (inciso b)
4. **`ExperimentoHilos.java`** - Clase principal con análisis completo (incisos b, c, d, e)
5. **`TestBasico.java`** - Pruebas básicas para demostración

### Clase 1: ImpresorSecuencial

**Propósito**: Implementa la impresión secuencial de patrones (inciso a)

```java
public class ImpresorSecuencial {
    public void imprimirPatronX() {
        // Imprime .0X. .1X. .2X. ... .99X.
    }
    
    public void imprimirPatronY() {
        // Imprime .0Y. .1Y. .2Y. ... .99Y.
    }
    
    public void ejecutarSecuencial() {
        // Ejecuta ambos patrones en secuencia
    }
}
```

### Clase 2: HiloX (extends Thread)

**Propósito**: Hilo concurrente para imprimir patrón X

```java
public class HiloX extends Thread {
    @Override
    public void run() {
        // Imprime .0X. .1X. .2X. ... .99X. de forma concurrente
        // Incluye Thread.sleep(1) para permitir intercalación
    }
}
```

### Clase 3: HiloY (extends Thread)

**Propósito**: Hilo concurrente para imprimir patrón Y

```java
public class HiloY extends Thread {
    @Override
    public void run() {
        // Imprime .0Y. .1Y. .2Y. ... .99Y. de forma concurrente
        // Incluye Thread.sleep(1) para permitir intercalación
    }
}
```

### Clase 4: ExperimentoHilos (Clase Principal)

**Propósito**: Implementa todos los requerimientos del ejercicio

**Características principales:**
- ✅ **10 repeticiones** del proceso concurrente (inciso b)
- ✅ **Análisis de sucesiones más largas** (inciso c)
- ✅ **Comparación con y sin join()** (inciso d)
- ✅ **Análisis y comentarios** de resultados (inciso e)
- ✅ **Captura de salida** para análisis posterior
- ✅ **Expresiones regulares** para encontrar patrones

**Métodos principales:**
```java
public class ExperimentoHilos {
    // Ejecuta una ejecución concurrente
    public String ejecutarEjecucionConcurrente(int numero, boolean usarJoin)
    
    // Ejecuta 10 repeticiones del experimento
    public void ejecutar10Repeticiones(boolean usarJoin)
    
    // Encuentra la sucesión más larga de X o Y
    public void analizarSucesionMasLarga()
    
    // Compara ejecución con y sin join()
    public void compararConYSinJoin()
    
    // Proporciona análisis detallado
    public void analizarYComentarResultados()
}
```

### Clase 5: TestBasico

**Propósito**: Pruebas simples para demostrar conceptos básicos

```java
public class TestBasico {
    // Demuestra diferencia entre con y sin join()
    public static void demostrarDiferenciaJoin()
    
    // Ejecuta múltiples pruebas para observar variabilidad
    public static void ejecutarMultiplesPruebas()
}
```

---

## Cómo Ejecutar

### Opción 1: Prueba Básica
```bash
javac tp3/actividad1/*.java
java tp3.actividad1.TestBasico
```

### Opción 2: Experimento Completo
```bash
javac tp3/actividad1/*.java
java tp3.actividad1.ExperimentoHilos
```

### Opción 3: Impresión Secuencial
```bash
javac tp3/actividad1/*.java
java tp3.actividad1.ImpresorSecuencial
```

---

## Análisis de Resultados

### Comportamiento Observado

#### **Sin join():**
- Los hilos pueden no completarse antes de que termine main()
- La salida puede estar incompleta
- El programa principal no espera a los hilos

#### **Con join():**
- Se garantiza que ambos hilos terminen completamente
- La salida está completa
- Sincronización apropiada entre hilos

### Patrones de Concurrencia

#### **Intercalación Típica:**
```
.0X..0Y..1X..1Y..2X..2Y..3X..3Y...
```

#### **Sucesiones Largas:**
```
.0X..1X..2X..3X..4X..5X..6X..7X..8X..9X.
```

#### **Variabilidad entre Ejecuciones:**
- Cada ejecución produce patrones diferentes
- El scheduler del SO determina el orden
- Thread.sleep(1) permite observar intercalación

### Conceptos Demostrados

1. **Herencia de Thread**: Clases HiloX y HiloY extienden Thread
2. **Método run()**: Implementación del comportamiento del hilo
3. **start()**: Inicia la ejecución concurrente
4. **join()**: Sincronización para esperar terminación
5. **InterruptedException**: Manejo de interrupciones
6. **Concurrencia**: Ejecución simultánea de múltiples hilos
7. **No determinismo**: Resultados variables entre ejecuciones
8. **Scheduling**: El SO decide cuándo ejecuta cada hilo

### Recomendaciones

1. **Siempre usar join()** cuando se necesite esperar hilos
2. **Manejar InterruptedException** apropiadamente
3. **Thread.sleep()** útil para observar concurrencia
4. **Considerar sincronización** para salida ordenada
5. **Capturar salida** para análisis posterior

---

## Conclusiones

### Logros del Ejercicio

✅ **Inciso a**: Clase ImpresorSecuencial implementada  
✅ **Inciso b**: Hilos HiloX y HiloY con 10 repeticiones  
✅ **Inciso c**: Análisis de sucesiones más largas  
✅ **Inciso d**: Implementación con join() y pausas  
✅ **Inciso e**: Análisis y comentarios detallados  

### Conceptos Aprendidos

- **Programación concurrente** con hilos en Java
- **Diferencia entre ejecución secuencial y concurrente**
- **Importancia de la sincronización** con join()
- **Variabilidad y no determinismo** en sistemas concurrentes
- **Análisis de patrones** en salida concurrente

Este ejercicio demuestra los fundamentos de la programación con hilos en Java y la importancia de la sincronización apropiada.
