# TP5 - Actividad 3: Web Scraping Concurrente con Runnable

## Descripción del Problema

Implementación de un sistema de **web scraping concurrente** que extrae noticias de la sección policiales del diario El Tribuno de Jujuy. El sistema utiliza 10 hilos concurrentes para procesar enlaces de noticias, realizar peticiones HTTP y extraer contenido específico.

## Enunciado Original

> Dentro de la sección policiales del diario El Tribuno de Jujuy (https://eltribunodejujuy.com/seccion/policiales), seleccione los enlaces de 10 noticias y cárguelos en un array. Cree una tarea que tenga como atributo dicho array y desde el main() lance 10 hilos para ejecutar dicha tarea. Esta tarea lo que hará es tomar el ID del array de noticias pasado desde el main(), realizará una petición HTTP Request y obtendrá la nota. Dentro de cada nota existe una sección (<DIV></DIV>) llamado <div amp-access="noticia"> dentro de la cual, dentro de ese DIV está el cuerpo de la noticia, que nos lo que nos interesa. Luego, a medida que vaya terminando cada hilo de ejecutarse, debe imprimir por pantalla el cuerpo de la noticia encontrado. Muestre el tiempo de ejecución.

## Análisis del Sistema

### Componentes Principales

```
┌─────────────────────────────────────────────────────────────────┐
│                    WEB SCRAPING CONCURRENTE                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────────┐    ┌─────────────┐     │
│  │   MAIN()    │───▶│  ARRAY ENLACES  │───▶│ 10 HILOS    │     │
│  │             │    │   (10 noticias) │    │ CONCURRENTES│     │
│  │ • Cargar    │    │                 │    │             │     │
│  │   enlaces   │    │ [URL1] [URL2]   │    │ Hilo 1 ──┐  │     │
│  │ • Crear     │    │ [URL3] [URL4]   │    │ Hilo 2 ──┤  │     │
│  │   hilos     │    │ [URL5] [URL6]   │    │ Hilo 3 ──┼──┐    │
│  │ • Medir     │    │ [URL7] [URL8]   │    │    ...   ──┤  │    │
│  │   tiempo    │    │ [URL9] [URL10]  │    │ Hilo 10 ──┘  │    │
│  └─────────────┘    └─────────────────┘    └─────────────┘    │
│                              │                       │        │
│                              ▼                       ▼        │
│                    ┌─────────────────┐    ┌─────────────────┐ │
│                    │ HTTP REQUESTS   │    │ EXTRACCIÓN HTML │ │
│                    │                 │    │                 │ │
│                    │ • GET noticia   │    │ • Buscar <div   │ │
│                    │ • Obtener HTML  │    │   amp-access=   │ │
│                    │ • Manejar       │    │   "noticia">    │ │
│                    │   errores       │    │ • Extraer texto │ │
│                    └─────────────────┘    └─────────────────┘ │
│                              │                       │        │
│                              ▼                       ▼        │
│                    ┌─────────────────────────────────────────┐ │
│                    │           SALIDA CONCURRENTE            │ │
│                    │                                         │ │
│                    │ • Imprimir cuerpo de noticia           │ │
│                    │ • Mostrar tiempo de ejecución          │ │
│                    │ • Orden no determinista               │ │
│                    └─────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Características del Sistema

1. **Web Scraping**: Extracción de contenido de páginas web
2. **Concurrencia**: 10 hilos procesando simultáneamente
3. **HTTP Requests**: Peticiones GET a URLs específicas
4. **Parsing HTML**: Extracción de contenido específico
5. **Medición de Tiempo**: Análisis de rendimiento

## Estructura del Proyecto

```
tp5/actividad3/
├── ScrapingTask.java            # Runnable que procesa una noticia
├── NoticiaScraper.java          # Clase para extraer contenido HTML
├── GestorEnlaces.java           # Gestor de URLs de noticias
├── WebScrapingSimulacion.java   # Clase principal de simulación
├── utils/
│   ├── HttpClient.java          # Cliente HTTP personalizado
│   └── HtmlParser.java          # Parser HTML para extracción
└── README.md                    # Esta documentación
```

## Diagrama de Flujo del Proceso

```
┌─────────────────┐
│ Inicio Main()   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Cargar 10       │
│ Enlaces         │
│ (Array URLs)    │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Crear 10        │
│ ScrapingTask    │
│ (Runnable)      │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Iniciar 10      │
│ Hilos           │
│ Concurrentes    │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Cada Hilo:      │
│ • Tomar ID      │
│ • HTTP Request  │
│ • Parse HTML    │
│ • Extraer texto │
│ • Imprimir      │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Medir Tiempo    │
│ Total           │
└─────────────────┘
```

## Implementación Técnica

### Estructura HTML Objetivo

```html
<!-- Estructura típica de noticia en El Tribuno -->
<html>
  <body>
    <div class="contenido">
      <div amp-access="noticia">
        <!-- ESTE ES EL CONTENIDO QUE NECESITAMOS -->
        <p>Cuerpo de la noticia...</p>
        <p>Más contenido...</p>
      </div>
    </div>
  </body>
</html>
```

### Extracción con Runnable

```java
public class ScrapingTask implements Runnable {
    private String[] enlaces;
    private int indice;
    
    @Override
    public void run() {
        String url = enlaces[indice];
        
        // 1. Realizar HTTP Request
        String html = httpClient.get(url);
        
        // 2. Extraer contenido específico
        String contenido = extraerContenidoNoticia(html);
        
        // 3. Imprimir resultado
        System.out.println("Hilo " + Thread.currentThread().getName() + 
                          ": " + contenido);
    }
}
```

## Casos de Uso Principales

### 1. Scraping Exitoso
```
Hilo-1 → URL válida → HTML obtenido → Contenido extraído → Impresión
```

### 2. Error de Conexión
```
Hilo-2 → URL inválida → Timeout/404 → Manejo de error → Log de error
```

### 3. Contenido No Encontrado
```
Hilo-3 → HTML obtenido → Div no encontrado → Mensaje de advertencia
```

### 4. Procesamiento Concurrente
```
10 Hilos ejecutándose simultáneamente → Resultados en orden aleatorio
```

## Ejemplo de Salida Esperada

```
=== WEB SCRAPING CONCURRENTE INICIADO ===
Tiempo de inicio: 14:30:15.123
Enlaces cargados: 10 noticias de El Tribuno de Jujuy

[14:30:15.456] Hilo-3: Procesando https://eltribunodejujuy.com/noticia/123
[14:30:15.789] Hilo-1: Procesando https://eltribunodejujuy.com/noticia/124
[14:30:16.012] Hilo-7: Procesando https://eltribunodejujuy.com/noticia/125

--- CONTENIDO EXTRAÍDO ---
Hilo-3: Un hombre de 35 años fue detenido en la madrugada de ayer por...
Hilo-1: La Policía de Jujuy informó que se realizó un operativo en...
Hilo-7: Efectivos policiales detuvieron a dos personas por...

=== SCRAPING COMPLETADO ===
Tiempo total de ejecución: 2.345 segundos
Noticias procesadas: 10/10
Errores: 0
```

## Métricas del Sistema

### Estadísticas a Monitorear

1. **Rendimiento**:
   - Tiempo total de ejecución
   - Tiempo promedio por noticia
   - Noticias procesadas por segundo

2. **Éxito/Errores**:
   - Noticias extraídas exitosamente
   - Errores de conexión HTTP
   - Errores de parsing HTML

3. **Concurrencia**:
   - Hilos activos simultáneamente
   - Orden de finalización de hilos
   - Utilización de recursos

## Desafíos de Implementación

### 1. Gestión de HTTP Requests
- Timeouts y reintentos
- Manejo de códigos de error HTTP
- Headers apropiados para evitar bloqueos

### 2. Parsing HTML Robusto
- Variaciones en la estructura HTML
- Contenido dinámico (JavaScript)
- Caracteres especiales y encoding

### 3. Concurrencia Controlada
- Límite de conexiones simultáneas
- Rate limiting para evitar sobrecarga del servidor
- Sincronización de salida

### 4. Manejo de Errores
- URLs inválidas o caídas
- Contenido no encontrado
- Timeouts de red

## Algoritmo de Extracción

### Proceso de Scraping por Hilo

```java
public String procesarNoticia(String url, int hiloId) {
    try {
        // 1. Realizar petición HTTP
        long inicioRequest = System.currentTimeMillis();
        String html = realizarPeticionHTTP(url);
        long tiempoRequest = System.currentTimeMillis() - inicioRequest;
        
        // 2. Buscar div específico
        String patron = "<div amp-access=\"noticia\">";
        int inicio = html.indexOf(patron);
        
        if (inicio == -1) {
            return "Contenido no encontrado en " + url;
        }
        
        // 3. Extraer contenido hasta cierre del div
        String contenido = extraerContenidoDiv(html, inicio);
        
        // 4. Limpiar HTML tags
        String textoLimpio = limpiarHTML(contenido);
        
        return "Hilo-" + hiloId + " (" + tiempoRequest + "ms): " + textoLimpio;
        
    } catch (IOException e) {
        return "Error en Hilo-" + hiloId + ": " + e.getMessage();
    }
}
```

## Extensiones Posibles

### 1. Pool de Conexiones
```java
public class ConnectionPool {
    private final int MAX_CONNECTIONS = 5;
    private Semaphore semaforo = new Semaphore(MAX_CONNECTIONS);
}
```

### 2. Cache de Resultados
```java
public class CacheNoticias {
    private Map<String, String> cache = new ConcurrentHashMap<>();
    
    public String obtenerNoticia(String url) {
        return cache.computeIfAbsent(url, this::scrapearNoticia);
    }
}
```

### 3. Persistencia de Datos
```java
public class AlmacenNoticias {
    public void guardarNoticia(String url, String contenido) {
        // Guardar en base de datos o archivo
    }
}
```

### 4. Análisis de Contenido
```java
public class AnalizadorTexto {
    public EstadisticasTexto analizar(String contenido) {
        // Contar palabras, sentiment analysis, etc.
    }
}
```

## Consideraciones Éticas y Legales

### 1. Robots.txt
- Verificar permisos de scraping
- Respetar directivas del sitio web
- Rate limiting apropiado

### 2. Términos de Servicio
- Revisar términos de uso del sitio
- No sobrecargar el servidor
- Uso responsable de los datos

### 3. Derechos de Autor
- Respetar propiedad intelectual
- Uso educativo/investigación
- Atribución apropiada

## Análisis de Rendimiento

### Ventajas de Concurrencia

1. **Paralelización**: 10 requests simultáneos vs secuenciales
2. **Mejor Utilización**: CPU y red trabajando simultáneamente
3. **Tiempo Reducido**: ~10x más rápido que procesamiento secuencial

### Métricas Esperadas

```
Procesamiento Secuencial:
- 10 noticias × 500ms promedio = 5 segundos

Procesamiento Concurrente:
- 10 noticias en paralelo ≈ 800ms total
- Mejora: ~6x más rápido
```

## Aplicaciones Reales

### Sistemas Similares

- **Agregadores de Noticias**: Google News, Feedly
- **Monitoreo de Medios**: Análisis de menciones
- **Investigación Académica**: Recolección de datos
- **Business Intelligence**: Análisis de competencia

### Patrones de Diseño Aplicados

- **Worker Pool**: Hilos procesando tareas de una cola
- **Producer-Consumer**: URLs como productos, hilos como consumidores
- **Template Method**: Proceso estándar de scraping
- **Strategy**: Diferentes estrategias de parsing por sitio

## Casos Extremos a Considerar

### 1. Sitio Web Caído
```
Escenario: Servidor no responde
Resultado: Timeouts en todos los hilos
Solución: Reintentos y URLs alternativas
```

### 2. Rate Limiting
```
Escenario: Demasiadas requests simultáneas
Resultado: Bloqueo temporal del IP
Solución: Delays entre requests y proxies
```

### 3. Contenido Dinámico
```
Escenario: Contenido generado por JavaScript
Resultado: HTML incompleto
Solución: Headless browser (Selenium)
```

## Conclusiones Esperadas

### Beneficios de Runnable para Web Scraping

1. **Escalabilidad**: Fácil ajuste del número de hilos
2. **Flexibilidad**: Diferentes estrategias de scraping por hilo
3. **Mantenibilidad**: Lógica de scraping separada de gestión de hilos
4. **Testabilidad**: Scraping testeable sin concurrencia real

### Lecciones de Concurrencia

1. **I/O Bound Tasks**: Ideales para paralelización
2. **Resource Management**: Control de conexiones simultáneas
3. **Error Handling**: Manejo robusto en entornos concurrentes
4. **Performance Monitoring**: Medición de mejoras de rendimiento

---

**Próximos Pasos**: Implementación de las clases Java con interfaz Runnable  
**Tiempo Estimado**: Scraping de 10 noticias en ~1-2 segundos  
**Autor**: Curso de Programación Concurrente 2025

**Nota Importante**: Este ejercicio es con fines educativos. En implementaciones reales, siempre verificar robots.txt y términos de servicio del sitio web objetivo.
