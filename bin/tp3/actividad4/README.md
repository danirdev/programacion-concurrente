# TP3 - Actividad 4 - Entrada de Texto con Hilos

## Enunciado

**4)** Debe solicitar el ingreso de una frase por la entrada de teclado, a continuación (una vez apretado Enter) deberá imprimir 10 veces dicha frase pero carácter por carácter empleando hilos.

---

## Análisis del Problema

### Descripción
- **Entrada de usuario**: Solicitar una frase por teclado
- **Trigger**: Después de presionar Enter
- **Salida**: Imprimir la frase 10 veces
- **Método**: Carácter por carácter usando hilos
- **Concurrencia**: Múltiples hilos imprimiendo simultáneamente

### Conceptos Demostrados
- **Entrada de usuario** con Scanner
- **Múltiples hilos** trabajando con la misma cadena
- **Impresión carácter por carácter**
- **Sincronización de salida** (opcional)
- **Intercalación de caracteres** entre hilos

---

## Implementación

### Estructura de Clases

1. **`ImpresorCaracteres.java`** - Hilo que imprime una frase carácter por carácter
2. **`ControladorImpresion.java`** - Coordina múltiples hilos de impresión
3. **`ImpresorSincronizado.java`** - Versión sincronizada para salida ordenada
4. **`EntradaTexto.java`** - Maneja la entrada del usuario
5. **`TestEntradaTexto.java`** - Clase principal con interfaz de usuario

### Características de la Implementación
- **10 hilos simultáneos**: Cada uno imprime la frase completa
- **Carácter por carácter**: Cada carácter se imprime individualmente
- **Intercalación visible**: Los caracteres de diferentes hilos se mezclan
- **Pausa entre caracteres**: Para hacer visible la concurrencia

---

## Cómo Ejecutar

### Opción 1: Versión Básica
```bash
javac tp3/actividad4/*.java
java tp3.actividad4.TestEntradaTexto
```

### Opción 2: Versión Sincronizada
```bash
javac tp3/actividad4/*.java
java tp3.actividad4.ControladorImpresion
```

---

## Resultados Esperados

### Sin Sincronización
- **Intercalación de caracteres**: Los caracteres de diferentes hilos se mezclan
- **Salida caótica**: Difícil de leer pero demuestra concurrencia
- **Ejemplo**: "HHoollaa  MMuunnddoo" (caracteres intercalados)

### Con Sincronización
- **Frases completas**: Cada hilo imprime su frase completa antes que otro
- **Salida ordenada**: Más legible pero menos concurrente
- **Ejemplo**: "Hola Mundo\nHola Mundo\n..." (frases separadas)

### Comportamiento Observable
- **Velocidad variable**: Diferentes hilos pueden ir a diferentes velocidades
- **No determinismo**: El orden de caracteres varía entre ejecuciones
- **Concurrencia visible**: Se puede observar la ejecución simultánea

---

## Variaciones Implementadas

### Versión 1: Intercalación Completa
- Cada carácter se imprime inmediatamente
- Máxima intercalación entre hilos
- Salida más caótica pero demuestra mejor la concurrencia

### Versión 2: Con Pausas
- Pequeñas pausas entre caracteres
- Intercalación más visible y controlada
- Mejor para observar el comportamiento

### Versión 3: Sincronizada
- Un hilo a la vez imprime su frase completa
- Salida ordenada y legible
- Demuestra control de concurrencia

Este ejercicio ilustra cómo múltiples hilos pueden trabajar con los mismos datos y cómo la sincronización afecta la salida.
