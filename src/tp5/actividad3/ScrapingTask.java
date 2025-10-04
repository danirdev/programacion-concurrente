package tp5.actividad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase ScrapingTask que implementa Runnable.
 * Cada instancia procesa una noticia específica del array de enlaces,
 * realiza una petición HTTP y extrae el contenido del div amp-access="noticia".
 */
public class ScrapingTask implements Runnable {
    private final String[] enlaces;
    private final int indice;
    private final DateTimeFormatter timeFormatter;
    private String contenidoExtraido;
    private long tiempoEjecucion;
    private boolean exitoso;
    private String mensajeError;
    
    /**
     * Constructor del ScrapingTask
     * @param enlaces Array de URLs de noticias
     * @param indice Índice del enlace a procesar (0-9)
     */
    public ScrapingTask(String[] enlaces, int indice) {
        this.enlaces = enlaces;
        this.indice = indice;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.contenidoExtraido = "";
        this.tiempoEjecucion = 0;
        this.exitoso = false;
        this.mensajeError = "";
    }
    
    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        String tiempo = LocalTime.now().format(timeFormatter);
        long inicioTiempo = System.currentTimeMillis();
        
        try {
            String url = enlaces[indice];
            System.out.println("[" + tiempo + "] " + nombreHilo + ": Procesando noticia " + 
                             (indice + 1) + "/10 - " + url);
            
            // Realizar petición HTTP y extraer contenido
            String html = realizarPeticionHTTP(url);
            contenidoExtraido = extraerContenidoNoticia(html);
            
            tiempoEjecucion = System.currentTimeMillis() - inicioTiempo;
            exitoso = true;
            
            // Imprimir resultado inmediatamente cuando termine
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("\n" + "=".repeat(80));
            System.out.println("📰 NOTICIA EXTRAÍDA POR " + nombreHilo.toUpperCase());
            System.out.println("=".repeat(80));
            System.out.println("🔗 URL: " + url);
            System.out.println("⏱️  Tiempo de procesamiento: " + tiempoEjecucion + "ms");
            System.out.println("📄 Contenido extraído:");
            System.out.println("-".repeat(40));
            System.out.println(contenidoExtraido);
            System.out.println("-".repeat(40));
            System.out.println("✅ Procesado exitosamente por " + nombreHilo + " a las " + tiempo);
            System.out.println("=".repeat(80) + "\n");
            
        } catch (Exception e) {
            tiempoEjecucion = System.currentTimeMillis() - inicioTiempo;
            exitoso = false;
            mensajeError = e.getMessage();
            
            tiempo = LocalTime.now().format(timeFormatter);
            System.out.println("\n" + "=".repeat(80));
            System.out.println("❌ ERROR EN " + nombreHilo.toUpperCase());
            System.out.println("=".repeat(80));
            System.out.println("🔗 URL: " + enlaces[indice]);
            System.out.println("⏱️  Tiempo antes del error: " + tiempoEjecucion + "ms");
            System.out.println("💥 Error: " + mensajeError);
            System.out.println("🕒 Ocurrió a las " + tiempo);
            System.out.println("=".repeat(80) + "\n");
        }
    }
    
    /**
     * Realiza una petición HTTP GET a la URL especificada
     * @param urlString URL de la noticia
     * @return Contenido HTML de la página
     * @throws IOException Si hay error en la conexión
     */
    private String realizarPeticionHTTP(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        
        // Configurar la conexión
        conexion.setRequestMethod("GET");
        conexion.setConnectTimeout(10000); // 10 segundos timeout
        conexion.setReadTimeout(15000);    // 15 segundos timeout
        conexion.setRequestProperty("User-Agent", 
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        conexion.setRequestProperty("Accept", 
            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conexion.setRequestProperty("Accept-Language", "es-ES,es;q=0.8,en;q=0.5");
        conexion.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conexion.setRequestProperty("Connection", "keep-alive");
        
        // Verificar código de respuesta
        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP " + codigoRespuesta + ": " + conexion.getResponseMessage());
        }
        
        // Leer el contenido HTML
        StringBuilder html = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), "UTF-8"))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                html.append(linea).append("\n");
            }
        }
        
        conexion.disconnect();
        return html.toString();
    }
    
    /**
     * Extrae el contenido del div amp-access="noticia" del HTML
     * @param html Contenido HTML completo
     * @return Texto extraído y limpio
     */
    private String extraerContenidoNoticia(String html) {
        // Buscar el div específico
        String patronInicio = "<div amp-access=\"noticia\">";
        String patronInicioAlt = "<div amp-access='noticia'>";
        
        int indiceInicio = html.indexOf(patronInicio);
        if (indiceInicio == -1) {
            indiceInicio = html.indexOf(patronInicioAlt);
        }
        
        if (indiceInicio == -1) {
            // Buscar patrones alternativos comunes en sitios de noticias
            String[] patronesAlternativos = {
                "<div class=\"noticia-contenido\">",
                "<div class=\"article-content\">",
                "<div class=\"post-content\">",
                "<div class=\"entry-content\">",
                "<article class=\"noticia\">"
            };
            
            for (String patron : patronesAlternativos) {
                indiceInicio = html.indexOf(patron);
                if (indiceInicio != -1) {
                    break;
                }
            }
            
            if (indiceInicio == -1) {
                return "⚠️ No se encontró el contenido de la noticia en la estructura HTML esperada. " +
                       "Posible cambio en el formato del sitio web.";
            }
        }
        
        // Encontrar el cierre del div
        int indiceFin = encontrarCierreDiv(html, indiceInicio);
        
        if (indiceFin == -1) {
            return "⚠️ No se pudo determinar el final del contenido de la noticia.";
        }
        
        // Extraer el contenido del div
        String contenidoDiv = html.substring(indiceInicio, indiceFin);
        
        // Limpiar HTML y extraer solo el texto
        String textoLimpio = limpiarHTML(contenidoDiv);
        
        // Validar que el contenido extraído no esté vacío
        if (textoLimpio.trim().isEmpty()) {
            return "⚠️ El contenido extraído está vacío. Posible problema en el parsing HTML.";
        }
        
        // Limitar longitud para mejor visualización
        if (textoLimpio.length() > 500) {
            textoLimpio = textoLimpio.substring(0, 500) + "... [contenido truncado]";
        }
        
        return textoLimpio.trim();
    }
    
    /**
     * Encuentra el índice de cierre del div correspondiente
     * @param html Contenido HTML
     * @param indiceInicio Índice donde inicia el div
     * @return Índice donde termina el div
     */
    private int encontrarCierreDiv(String html, int indiceInicio) {
        int contadorDivs = 1;
        int indiceActual = indiceInicio;
        
        // Buscar desde después del div de apertura
        indiceActual = html.indexOf('>', indiceActual) + 1;
        
        while (indiceActual < html.length() && contadorDivs > 0) {
            int siguienteDiv = html.indexOf("<div", indiceActual);
            int siguienteCierre = html.indexOf("</div>", indiceActual);
            
            if (siguienteCierre == -1) {
                break; // No hay más cierres
            }
            
            if (siguienteDiv != -1 && siguienteDiv < siguienteCierre) {
                // Encontramos otro div de apertura antes del cierre
                contadorDivs++;
                indiceActual = siguienteDiv + 4;
            } else {
                // Encontramos un cierre
                contadorDivs--;
                indiceActual = siguienteCierre + 6;
                
                if (contadorDivs == 0) {
                    return indiceActual;
                }
            }
        }
        
        return -1; // No se encontró el cierre
    }
    
    /**
     * Limpia las etiquetas HTML y extrae solo el texto
     * @param htmlContenido Contenido HTML con etiquetas
     * @return Texto limpio sin etiquetas HTML
     */
    private String limpiarHTML(String htmlContenido) {
        // Remover etiquetas HTML
        String textoLimpio = htmlContenido.replaceAll("<[^>]+>", " ");
        
        // Decodificar entidades HTML comunes
        textoLimpio = textoLimpio.replace("&nbsp;", " ");
        textoLimpio = textoLimpio.replace("&amp;", "&");
        textoLimpio = textoLimpio.replace("&lt;", "<");
        textoLimpio = textoLimpio.replace("&gt;", ">");
        textoLimpio = textoLimpio.replace("&quot;", "\"");
        textoLimpio = textoLimpio.replace("&#39;", "'");
        textoLimpio = textoLimpio.replace("&aacute;", "á");
        textoLimpio = textoLimpio.replace("&eacute;", "é");
        textoLimpio = textoLimpio.replace("&iacute;", "í");
        textoLimpio = textoLimpio.replace("&oacute;", "ó");
        textoLimpio = textoLimpio.replace("&uacute;", "ú");
        textoLimpio = textoLimpio.replace("&ntilde;", "ñ");
        
        // Normalizar espacios en blanco
        textoLimpio = textoLimpio.replaceAll("\\s+", " ");
        
        return textoLimpio.trim();
    }
    
    /**
     * Obtiene el contenido extraído
     * @return Contenido de la noticia extraído
     */
    public String getContenidoExtraido() {
        return contenidoExtraido;
    }
    
    /**
     * Obtiene el tiempo de ejecución en milisegundos
     * @return Tiempo de ejecución
     */
    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
    
    /**
     * Verifica si la extracción fue exitosa
     * @return true si fue exitosa, false en caso contrario
     */
    public boolean isExitoso() {
        return exitoso;
    }
    
    /**
     * Obtiene el mensaje de error si hubo alguno
     * @return Mensaje de error o cadena vacía si no hubo error
     */
    public String getMensajeError() {
        return mensajeError;
    }
    
    /**
     * Obtiene el índice del enlace procesado
     * @return Índice del enlace
     */
    public int getIndice() {
        return indice;
    }
    
    /**
     * Obtiene la URL procesada
     * @return URL de la noticia
     */
    public String getUrl() {
        return enlaces[indice];
    }
}
