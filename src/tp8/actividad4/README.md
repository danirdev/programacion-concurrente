# 📁 TP8 - ACTIVIDAD 4: MONITOR DE DIRECTORIO CON TAREAS PERIÓDICAS

## 📋 **INFORMACIÓN DEL TRABAJO PRÁCTICO**

**📅 Año:** 2025  
**🏫 Materia:** PROGRAMACIÓN CONCURRENTE  
**🏛️ Facultad:** FAC. DE INGENIERÍA - UNJu  
**👨‍🏫 Profesor:** Ing. José Farfán  
**📖 Tema:** ScheduledExecutorService - Monitor de Archivos

---

## 🎯 **OBJETIVO**

Programar una **tarea repetitiva** que cada **5 segundos** chequee si en un directorio se ha creado un **nuevo archivo**. Debe mostrar por pantalla el nombre y tamaño del archivo nuevo detectado.

---

## 📝 **DESCRIPCIÓN DEL EJERCICIO**

### 🔍 **Funcionamiento del Monitor**

1. **Lectura inicial:** Cargar lista de archivos existentes en el directorio
2. **Verificación periódica:** Cada 5 segundos, verificar si hay archivos nuevos
3. **Detección de cambios:** Comparar lista actual con lista anterior
4. **Notificación:** Mostrar información de archivos nuevos detectados

### 📊 **Información a Mostrar**

Para cada archivo nuevo detectado:
```
🆕 Nuevo archivo [nombre_archivo], con tamaño [tamaño_archivo]
```

---

## 🏗️ **ARQUITECTURA DE LA SOLUCIÓN**

### 📊 **Diagrama del Sistema**

```
┌─────────────────────────────────────────────────────────────────┐
│              MONITOR DE DIRECTORIO                              │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │    ScheduledExecutorService (1 thread)                   │  │
│  │                                                          │  │
│  │         TareaMonitoreo (cada 5 segundos)                │  │
│  └──────────────────────────────────────────────────────────┘  │
│                         │                                       │
│                         ▼                                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                DIRECTORIO MONITOREADO                    │  │
│  │                                                          │  │
│  │  📄 archivo1.txt                                         │  │
│  │  📄 archivo2.txt                                         │  │
│  │  📄 ...                                                  │  │
│  └──────────────────────────────────────────────────────────┘  │
│                         │                                       │
│                         ▼                                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │          PROCESO DE DETECCIÓN                            │  │
│  │                                                          │  │
│  │  1. Leer archivos actuales                              │  │
│  │  2. Comparar con lista anterior                          │  │
│  │  3. Identificar archivos nuevos                          │  │
│  │  4. Obtener información (nombre, tamaño)                 │  │
│  │  5. Notificar por pantalla                               │  │
│  │  6. Actualizar lista de archivos conocidos               │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  SALIDA:                                                       │
│  🆕 Nuevo archivo documento.txt, con tamaño 2.5 KB            │
│  🆕 Nuevo archivo imagen.jpg, con tamaño 1.2 MB               │
└─────────────────────────────────────────────────────────────────┘
```

---

## 💻 **IMPLEMENTACIÓN**

### 📁 **Estructura de Archivos**

```
tp8/actividad4/
├── README.md                         (Este archivo)
├── ArchivoInfo.java                 (Información de archivo)
├── TareaMonitoreoDirectorio.java   (Tarea periódica de monitoreo)
└── MonitorDirectorioSimulacion.java (Simulación principal)
```

### 🔑 **Componentes Principales**

#### 1️⃣ **ArchivoInfo**
- Nombre del archivo
- Tamaño del archivo
- Fecha de creación/modificación
- Formateo de tamaño (bytes, KB, MB)

#### 2️⃣ **TareaMonitoreoDirectorio** (Runnable)
- Mantiene lista de archivos conocidos
- Escanea directorio cada 5 segundos
- Detecta archivos nuevos
- Muestra notificaciones

#### 3️⃣ **MonitorDirectorioSimulacion**
- Crea directorio de prueba si no existe
- Configura ScheduledExecutorService
- Ejecuta monitor indefinidamente
- Permite cancelación manual

---

## 🚀 **EJECUCIÓN**

### ▶️ **Compilar y Ejecutar**

```bash
# Compilar
javac -d bin src/tp8/actividad4/*.java

# Ejecutar monitor (ctrl+C para detener)
java -cp bin tp8.actividad4.MonitorDirectorioSimulacion
```

### 📊 **Salida Esperada**

```
=== MONITOR DE DIRECTORIO CON TAREAS PERIÓDICAS ===

📁 CONFIGURACIÓN:
   📂 Directorio monitoreado: ./monitor_test/
   🔄 Frecuencia de escaneo: 5 segundos
   👁️ Modo: Detección de archivos nuevos

🚀 INICIANDO MONITOR...
📋 Archivos iniciales encontrados: 0

[00:00:00] 🔍 Escaneando directorio...
[00:00:00] ✅ Escaneo completado - 0 archivos

[00:00:05] 🔍 Escaneando directorio...
[00:00:05] 🆕 Nuevo archivo [documento.txt], con tamaño [2.5 KB]
[00:00:05] ✅ Escaneo completado - 1 archivo

[00:00:10] 🔍 Escaneando directorio...
[00:00:10] 🆕 Nuevo archivo [imagen.jpg], con tamaño [1.2 MB]
[00:00:10] 🆕 Nuevo archivo [datos.csv], con tamaño [45.8 KB]
[00:00:10] ✅ Escaneo completado - 3 archivos

[00:00:15] 🔍 Escaneando directorio...
[00:00:15] ✅ Escaneo completado - 3 archivos (sin cambios)

📊 ESTADÍSTICAS:
   🔢 Total escaneos: 4
   🆕 Archivos nuevos detectados: 3
   📁 Archivos actuales: 3
```

---

## 🔍 **ANÁLISIS DE LA SOLUCIÓN**

### ⚙️ **ScheduledExecutorService para Monitoreo**

#### **scheduleWithFixedDelay() vs scheduleAtFixedRate()**

```java
// Opción 1: scheduleAtFixedRate - periodo fijo
scheduler.scheduleAtFixedRate(tarea, 0, 5, TimeUnit.SECONDS);
// Ejecuta cada 5s desde el INICIO de la tarea anterior

// Opción 2: scheduleWithFixedDelay - delay fijo
scheduler.scheduleWithFixedDelay(tarea, 0, 5, TimeUnit.SECONDS);
// Ejecuta cada 5s desde el FIN de la tarea anterior
```

**Para este caso:** `scheduleWithFixedDelay` es mejor porque:
- Evita solapamiento si el escaneo demora más de 5s
- Garantiza 5s de pausa entre escaneos
- Más predecible con I/O que puede variar

### 📊 **Algoritmo de Detección**

```
Estado Inicial:
  archivosConocidos = {}

Cada 5 segundos:
  archivosActuales = listarDirectorio()
  archivosNuevos = archivosActuales - archivosConocidos
  
  Para cada archivoNuevo:
    obtenerInfo(nombre, tamaño)
    mostrarNotificacion()
  
  archivosConocidos = archivosActuales
```

### 🔢 **Formateo de Tamaños**

```
bytes < 1024         → X bytes
bytes < 1024²        → X.Y KB
bytes < 1024³        → X.Y MB
bytes >= 1024³       → X.Y GB
```

---

## 🎓 **CONCEPTOS DEMOSTRADOS**

- 📁 **Monitoreo de sistema de archivos** - File I/O
- ⏰ **Tareas periódicas** - ScheduledExecutorService
- 🔄 **scheduleWithFixedDelay()** - Delay fijo después de cada ejecución
- 🔍 **Detección de cambios** - Comparación de sets
- 📊 **Formateo de información** - Tamaños legibles
- 🚦 **Cancelación controlada** - Shutdown limpio

---

## 🧪 **PRUEBA DEL SISTEMA**

### 📝 **Pasos para Probar**

1. **Ejecutar el monitor:**
   ```bash
   java -cp bin tp8.actividad4.MonitorDirectorioSimulacion
   ```

2. **Abrir otra terminal/explorador de archivos**

3. **Copiar/crear archivos en el directorio monitoreado:**
   ```bash
   # En Windows
   copy archivo.txt monitor_test\
   
   # En Linux/Mac
   cp archivo.txt monitor_test/
   ```

4. **Observar la detección en tiempo real**

5. **Detener con Ctrl+C**

---

## 📚 **REFERENCIAS**

- **Clase 7:** Executor Framework
- **Java I/O:** File, Path, Files
- **Scheduling:** scheduleWithFixedDelay
- **File Monitoring:** Directory scanning

---

## 👨‍💻 **AUTOR**

**Estudiante:** [Tu Nombre]  
**Materia:** Programación Concurrente 2025  
**Facultad:** Ingeniería - UNJu

---

*Implementación de monitor de directorio con detección automática de archivos nuevos usando ScheduledExecutorService y scanning periódico.*
