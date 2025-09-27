# 💻 CLASE 3 - JAVA Y ECLIPSE IDE

## 📊 ÍNDICE TIOBE - FEBRERO 2024

**Ranking de Popularidad de Lenguajes de Programación**

```
┌─────────────────────────────────────────────────────────────┐
│                    ÍNDICE TIOBE - FEB 2024                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  🥇 1. Python, con un rating de 15.33%                    │
│  🥈 2. C, con 14.06%                                      │
│  🥉 3. Java, con 12.13%                                     │
│  4️⃣ 4. C++, con 8.01%                                       │
│  5️⃣ 5. C#, con 5.37%                                        │
│  6️⃣ 6. Visual Basic, con 5.23%                             │
│  7️⃣ 7. JavaScript, con 1.83%                               │
│  8️⃣ 8. PHP, con 1.79%                                       │
│  9️⃣ 9. Assembly language, con 1.60%                        │
│  🔟 10. SQL, con 1.55                                       │
│  11. Go, con 1.23%                                           │
│  12. Swift, con 1.16%                                        │
│                                                             │
│  ┌─────────────────────────────────────────────────┐  │
│  │                   JAVA                            │  │
│  │                                                 │  │
│  │      ☕                    🌍                   │  │
│  │   ┌───────┐              ┌─────────────┐   │  │
│  │   │       │              │ Eclipse IDE │   │  │
│  │   │ JAVA  │              │             │   │  │
│  │   │       │              │             │   │  │
│  │   └───────┘              └─────────────┘   │  │
│  └─────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## 📝 INFORMACIÓN DEL CURSO

**MODELO DE DESARROLLO DE PROGRAMAS Y PROGRAMACIÓN CONCURRENTE**  
**2024 - Profesor: Ing. José Farfán**  
**FAC.DE INGENIERÍA - UNJu**  
**JAVA**

---

## ☕ CARACTERÍSTICAS PRINCIPALES DE JAVA

### 🔄 Proceso de Compilación y Ejecución

```
┌─────────────────────────────────────────────────────────────┐
│                PROCESO DE COMPILACIÓN JAVA                │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────┐                           │
│  │ Código Java (archivos .java) │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────────────────┐                           │
│  │    JAVAC (compilador)     │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────────────────┐                           │
│  │ Byte Code (archivos .class) │                           │
│  └─────────────────────────┘                           │
│                        │                               │
│                        ▼                               │
│  ┌─────────────┐   ┌─────────────┐               │
│  │     JVM     │   │     JVM     │               │
│  │   Windows   │   │    Linux    │               │
│  └─────────────┘   └─────────────┘               │
└─────────────────────────────────────────────────────────────┘
```

### 🎯 Características Fundamentales

• **🌍 Independiente del HW**  
  Funciona en cualquier plataforma con JVM

• **📦 Orientado a Objetos y de propósito general**  
  Paradigma OOP completo y versátil

• **📝 Sintaxis parecida a C++**  
  Fácil transición para programadores de C/C++

• **💪 Robusto**  
  Manejo de errores y excepciones integrado

• **🔗 Solo admite herencia simple**  
  Evita la complejidad de herencia múltiple

### 🔒 Seguridad

#### **Desde el punto de vista del programador:**
- **Comprobación estricta de tipos de datos**
- **Gestión de excepciones**
- **No existencia de punteros**

#### **Desde el punto de vista del usuario de aplicaciones:**
- **Los programas se ejecutan sobre una máquina virtual (JVM)**
- **Espacio de nombre y Programación Concurrente Nativa**

### 📊 Tipos de datos estandarizados

---

## 🌍 ECLIPSE IDE

### 📝 Definición

**Entorno integrado de desarrollo o IDE (Integrated Development Environment)** que permite escribir código de un modo cómodo (la comodidad reside en que los entornos de desarrollo integrados son mucho más que 1 simple editor de textos).

### 🎯 Características comunes de las IDE

#### 🎨 Funcionalidades Básicas
- **Coloreado de sintaxis**
- **Herramientas de búsqueda**
- **Asistentes para la escritura de código**
- **Ejecución de aplicaciones** sin abandonar el entorno
- **Herramientas de depuración de código**

#### 🔗 Funcionalidades Avanzadas
- **Conexión con sistema de seguimiento de errores**
- **Facilidades para la creación de tareas**
- **Herramientas avanzadas para el análisis de código**
- **Herramientas de análisis de rendimiento**
- **Conexión a gestores de bases de datos**
- **Conexión con sistemas de control de versiones**

### 📊 Diagrama de Funcionalidades de Eclipse

```
┌─────────────────────────────────────────────────────────────┐
│                      ECLIPSE IDE                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                FUNCIONALIDADES                     │   │
│  │                                                     │   │
│  │  🎨 EDITOR:                                     │   │
│  │  • Coloreado de sintaxis                           │   │
│  │  • Herramientas de búsqueda                        │   │
│  │  • Asistentes para escritura                       │   │
│  │                                                     │   │
│  │  🚀 EJECUCIÓN:                                  │   │
│  │  • Ejecución sin abandonar entorno                 │   │
│  │  • Herramientas de depuración                       │   │
│  │                                                     │   │
│  │  🔗 INTEGRACIÓN:                                │   │
│  │  • Sistema de seguimiento de errores              │   │
│  │  • Creación de tareas                             │   │
│  │  • Análisis de código y rendimiento                │   │
│  │  • Gestores de bases de datos                     │   │
│  │  • Control de versiones                           │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---