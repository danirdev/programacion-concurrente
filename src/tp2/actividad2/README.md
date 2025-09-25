# TP2 - Actividad 2 - Programa Concurrente con Semáforos

## Enunciado

**2)** Considerando el siguiente programa concurrente, calcular para él, el conjunto de los posibles valores finales para la variable x.

```
Vars x: Ent := 0;
     s1: sem := 1;
     s2: sem := 0

P1::                P2::                P3::
wait(s2)           wait(s1)            wait(s1)
wait(s1)           x := x*x            x := x+3
x := 2*x           send(s1)            send(s2)
send(s1)                               send(s1)
```

---

## Análisis del Programa

### Variables y Semáforos Iniciales
```pseudocode
x: Entero := 0          // Variable compartida
s1: Semáforo := 1       // Semáforo inicializado en 1 (disponible)
s2: Semáforo := 0       // Semáforo inicializado en 0 (no disponible)
```

### Procesos
```pseudocode
PROCESO P1:
    wait(s2)        // Espera que s2 esté disponible (inicialmente bloqueado)
    wait(s1)        // Espera que s1 esté disponible
    x := 2*x        // Multiplica x por 2
    send(s1)        // Libera s1

PROCESO P2:
    wait(s1)        // Espera que s1 esté disponible
    x := x*x        // Eleva x al cuadrado
    send(s1)        // Libera s1

PROCESO P3:
    wait(s1)        // Espera que s1 esté disponible
    x := x+3        // Suma 3 a x
    send(s2)        // Libera s2 (permite que P1 continúe)
    send(s1)        // Libera s1
```

---

## Análisis de Dependencias

### Estado Inicial de Semáforos:
- **s1 = 1** (disponible, un proceso puede adquirirlo)
- **s2 = 0** (no disponible, P1 está bloqueado)

### Dependencias Críticas:
1. **P1 está inicialmente bloqueado** en `wait(s2)` porque s2 = 0
2. **P1 solo puede ejecutarse** después de que P3 ejecute `send(s2)`
3. **P2 y P3 compiten** por s1 inicialmente (s1 = 1)
4. **Exclusión mutua** en las operaciones sobre x mediante s1

---

## Análisis de Posibles Ejecuciones

### Escenario 1: P2 ejecuta primero, luego P3, luego P1

#### Secuencia: P2 → P3 → P1
```pseudocode
Estado inicial: x = 0, s1 = 1, s2 = 0

P2 ejecuta:
    wait(s1)        // s1: 1→0, P2 adquiere s1
    x := x*x        // x = 0*0 = 0
    send(s1)        // s1: 0→1, P2 libera s1

Estado: x = 0, s1 = 1, s2 = 0

P3 ejecuta:
    wait(s1)        // s1: 1→0, P3 adquiere s1
    x := x+3        // x = 0+3 = 3
    send(s2)        // s2: 0→1, P3 libera s2 (desbloquea P1)
    send(s1)        // s1: 0→1, P3 libera s1

Estado: x = 3, s1 = 1, s2 = 1

P1 ejecuta:
    wait(s2)        // s2: 1→0, P1 adquiere s2
    wait(s1)        // s1: 1→0, P1 adquiere s1
    x := 2*x        // x = 2*3 = 6
    send(s1)        // s1: 0→1, P1 libera s1

Estado final: x = 6
```

**Resultado: x = 6**

### Escenario 2: P3 ejecuta primero, luego P2, luego P1

#### Secuencia: P3 → P2 → P1
```pseudocode
Estado inicial: x = 0, s1 = 1, s2 = 0

P3 ejecuta:
    wait(s1)        // s1: 1→0, P3 adquiere s1
    x := x+3        // x = 0+3 = 3
    send(s2)        // s2: 0→1, P3 libera s2 (desbloquea P1)
    send(s1)        // s1: 0→1, P3 libera s1

Estado: x = 3, s1 = 1, s2 = 1

P2 ejecuta:
    wait(s1)        // s1: 1→0, P2 adquiere s1
    x := x*x        // x = 3*3 = 9
    send(s1)        // s1: 0→1, P2 libera s1

Estado: x = 9, s1 = 1, s2 = 1

P1 ejecuta:
    wait(s2)        // s2: 1→0, P1 adquiere s2
    wait(s1)        // s1: 1→0, P1 adquiere s1
    x := 2*x        // x = 2*9 = 18
    send(s1)        // s1: 0→1, P1 libera s1

Estado final: x = 18
```

**Resultado: x = 18**

### Escenario 3: P3 ejecuta primero, P1 se desbloquea pero P2 ejecuta antes

#### Análisis de intercalación: P3 → P1 (parcial) → P2 → P1 (continúa)

**Subcaso 3a: P3 completo → P1 hasta wait(s1) → P2 → P1 continúa**
```pseudocode
Estado inicial: x = 0, s1 = 1, s2 = 0

P3 ejecuta completo:
    wait(s1)        // s1: 1→0
    x := x+3        // x = 3
    send(s2)        // s2: 0→1 (desbloquea P1)
    send(s1)        // s1: 0→1

Estado: x = 3, s1 = 1, s2 = 1

P1 ejecuta parcialmente:
    wait(s2)        // s2: 1→0, P1 pasa el primer wait
    wait(s1)        // s1: 1→0, P1 adquiere s1 y queda listo para ejecutar

P2 intenta ejecutar:
    wait(s1)        // s1 = 0, P2 se bloquea esperando s1

P1 continúa:
    x := 2*x        // x = 2*3 = 6
    send(s1)        // s1: 0→1, libera s1

P2 ahora puede ejecutar:
    wait(s1)        // s1: 1→0, P2 adquiere s1
    x := x*x        // x = 6*6 = 36
    send(s1)        // s1: 0→1

Estado final: x = 36
```

**Resultado: x = 36**

---

## Análisis Completo de Todas las Intercalaciones

### Restricciones del Sistema:
1. **P1 no puede ejecutar** hasta que P3 ejecute `send(s2)`
2. **Solo un proceso a la vez** puede modificar x (protegido por s1)
3. **P3 debe ejecutar al menos `send(s2)`** antes de que P1 pueda comenzar

### Posibles Órdenes de Ejecución:

#### Orden 1: P2 → P3 → P1
- P2: x = 0*0 = 0
- P3: x = 0+3 = 3, libera s2
- P1: x = 2*3 = 6
- **Resultado: x = 6**

#### Orden 2: P3 → P2 → P1  
- P3: x = 0+3 = 3, libera s2
- P2: x = 3*3 = 9
- P1: x = 2*9 = 18
- **Resultado: x = 18**

#### Orden 3: P3 → P1 → P2
- P3: x = 0+3 = 3, libera s2
- P1: x = 2*3 = 6
- P2: x = 6*6 = 36
- **Resultado: x = 36**

---

## Tabla Resumen de Resultados

| Orden de Ejecución | Operaciones en Secuencia | Valor Final de x |
|-------------------|---------------------------|------------------|
| P2 → P3 → P1 | x=0*0=0 → x=0+3=3 → x=2*3=6 | **6** |
| P3 → P2 → P1 | x=0+3=3 → x=3*3=9 → x=2*9=18 | **18** |
| P3 → P1 → P2 | x=0+3=3 → x=2*3=6 → x=6*6=36 | **36** |

---

## Conjunto de Posibles Valores Finales

### **Respuesta: {6, 18, 36}**

La variable x puede tomar **3 valores finales diferentes**:
- **x = 6** (cuando P2 ejecuta primero)
- **x = 18** (cuando P3 ejecuta primero y P2 antes que P1)  
- **x = 36** (cuando P3 ejecuta primero y P1 antes que P2)

---

## Análisis de Sincronización

### Mecanismos de Control:
1. **Semáforo s1**: Proporciona exclusión mutua para el acceso a x
2. **Semáforo s2**: Controla la dependencia entre P3 y P1
3. **Sincronización obligatoria**: P1 depende de P3

### Propiedades del Sistema:
- **Exclusión mutua**: Garantizada por s1
- **Dependencia de orden**: P1 requiere que P3 ejecute primero
- **No determinismo controlado**: 3 posibles resultados válidos
- **Ausencia de deadlock**: No hay ciclos de dependencia

### Diagrama de Dependencias:
```
P3 ----send(s2)----> P1
 |                    |
 |                    |
 v                    v
s1 <----------------> s1
 ^                    ^
 |                    |
 |                    |
P2 -----------------> s1
```

---

## Conclusiones

1. **Determinismo Parcial**: Aunque hay no determinismo, está controlado por los semáforos
2. **Sincronización Efectiva**: Los semáforos garantizan exclusión mutua y orden parcial
3. **Tres Resultados Posibles**: El sistema puede producir exactamente 3 valores finales
4. **Dependencia Crítica**: P1 siempre ejecuta después de que P3 libere s2
5. **Competencia Controlada**: P2 y P3 compiten por s1, pero de forma segura

El conjunto de posibles valores finales para x es **{6, 18, 36}**.
