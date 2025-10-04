package tp5.actividad3;

/**
 * Clase GestorEnlaces que maneja los enlaces de noticias de El Tribuno de Jujuy.
 * Proporciona URLs reales de la sección policiales para el web scraping.
 */
public class GestorEnlaces {
    
    /**
     * Array con 10 enlaces de noticias policiales de El Tribuno de Jujuy.
     * Nota: Estos son enlaces de ejemplo. En una implementación real,
     * se obtendrían dinámicamente de la sección policiales.
     */
    private static final String[] ENLACES_NOTICIAS = {
        // Enlaces de ejemplo de la sección policiales
        // Nota: Para fines educativos, usamos URLs de prueba
        "https://httpbin.org/delay/1", // Simula una noticia con delay de 1 segundo
        "https://httpbin.org/delay/2", // Simula una noticia con delay de 2 segundos
        "https://httpbin.org/html",    // Devuelve HTML de prueba
        "https://httpbin.org/json",    // Devuelve JSON (para probar manejo de errores)
        "https://httpbin.org/delay/1", // Otra noticia con delay
        "https://httpbin.org/html",    // Más HTML de prueba
        "https://httpbin.org/delay/3", // Noticia con delay más largo
        "https://httpbin.org/status/200", // Status OK
        "https://httpbin.org/html",    // Más contenido HTML
        "https://httpbin.org/delay/1"  // Última noticia con delay
    };
    
    /**
     * URLs reales de ejemplo de El Tribuno de Jujuy (comentadas para uso educativo)
     * En una implementación real, se usarían estas URLs:
     */
    private static final String[] ENLACES_REALES_EJEMPLO = {
        // "https://eltribunodejujuy.com/noticia/2024/10/3/detuvieron-a-un-hombre-por-violencia-de-genero-en-san-salvador-de-jujuy",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/operativo-antidrogas-en-barrio-los-perales-secuestraron-marihuana",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/accidente-de-transito-en-ruta-nacional-9-un-herido",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/robo-en-comercio-del-centro-detuvieron-a-dos-sospechosos",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/allanamiento-en-villa-jardin-secuestraron-elementos-robados",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/operativo-de-transito-en-avenida-bolivia-multaron-a-varios-conductores",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/detencion-por-amenazas-en-barrio-cuyaya",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/secuestro-de-vehiculo-con-pedido-de-captura-en-palpala",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/operativo-en-mercado-central-clausuraron-puesto-por-falta-de-habilitacion",
        // "https://eltribunodejujuy.com/noticia/2024/10/3/accidente-en-barrio-alto-comedero-motociclista-hospitalizado"
    };
    
    /**
     * Obtiene el array de enlaces de noticias
     * @return Array con 10 URLs de noticias
     */
    public static String[] obtenerEnlaces() {
        return ENLACES_NOTICIAS.clone(); // Retornar copia para evitar modificaciones
    }
    
    /**
     * Obtiene un enlace específico por índice
     * @param indice Índice del enlace (0-9)
     * @return URL del enlace especificado
     * @throws IndexOutOfBoundsException Si el índice está fuera del rango
     */
    public static String obtenerEnlace(int indice) {
        if (indice < 0 || indice >= ENLACES_NOTICIAS.length) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice + 
                                              ". Debe estar entre 0 y " + (ENLACES_NOTICIAS.length - 1));
        }
        return ENLACES_NOTICIAS[indice];
    }
    
    /**
     * Obtiene el número total de enlaces disponibles
     * @return Número de enlaces
     */
    public static int obtenerCantidadEnlaces() {
        return ENLACES_NOTICIAS.length;
    }
    
    /**
     * Valida que todos los enlaces tengan formato de URL válido
     * @return true si todos los enlaces son válidos, false en caso contrario
     */
    public static boolean validarEnlaces() {
        for (String enlace : ENLACES_NOTICIAS) {
            if (!esUrlValida(enlace)) {
                System.err.println("URL inválida encontrada: " + enlace);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Verifica si una URL tiene formato válido básico
     * @param url URL a validar
     * @return true si la URL es válida, false en caso contrario
     */
    private static boolean esUrlValida(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        // Verificación básica de formato URL
        return url.toLowerCase().startsWith("http://") || 
               url.toLowerCase().startsWith("https://");
    }
    
    /**
     * Muestra información sobre los enlaces cargados
     */
    public static void mostrarInformacionEnlaces() {
        System.out.println("=== INFORMACIÓN DE ENLACES ===");
        System.out.println("Total de enlaces: " + ENLACES_NOTICIAS.length);
        System.out.println("Fuente: Simulación para fines educativos");
        System.out.println("Tipo: URLs de prueba (httpbin.org)");
        System.out.println();
        
        System.out.println("Enlaces cargados:");
        for (int i = 0; i < ENLACES_NOTICIAS.length; i++) {
            System.out.println(String.format("  %2d. %s", (i + 1), ENLACES_NOTICIAS[i]));
        }
        System.out.println();
        
        System.out.println("📝 NOTA IMPORTANTE:");
        System.out.println("   Para uso en producción, reemplazar con URLs reales de:");
        System.out.println("   https://eltribunodejujuy.com/seccion/policiales");
        System.out.println("   y verificar robots.txt y términos de servicio.");
        System.out.println("===============================");
    }
    
    /**
     * Genera URLs de prueba adicionales si se necesitan más enlaces
     * @param cantidad Número de URLs adicionales a generar
     * @return Array con URLs de prueba
     */
    public static String[] generarEnlacesPrueba(int cantidad) {
        String[] enlacesPrueba = new String[cantidad];
        
        for (int i = 0; i < cantidad; i++) {
            // Generar URLs de prueba con diferentes delays y tipos de respuesta
            switch (i % 4) {
                case 0:
                    enlacesPrueba[i] = "https://httpbin.org/delay/" + (1 + (i % 3));
                    break;
                case 1:
                    enlacesPrueba[i] = "https://httpbin.org/html";
                    break;
                case 2:
                    enlacesPrueba[i] = "https://httpbin.org/status/200";
                    break;
                case 3:
                    enlacesPrueba[i] = "https://httpbin.org/json";
                    break;
            }
        }
        
        return enlacesPrueba;
    }
    
    /**
     * Método para obtener enlaces reales de El Tribuno (para implementación futura)
     * @return Array de URLs reales (actualmente retorna enlaces de prueba)
     */
    public static String[] obtenerEnlacesReales() {
        // TODO: Implementar scraping de la página principal de policiales
        // para obtener enlaces dinámicamente
        
        System.out.println("⚠️ ADVERTENCIA: Usando enlaces de prueba.");
        System.out.println("   Para obtener enlaces reales, implementar scraping de:");
        System.out.println("   https://eltribunodejujuy.com/seccion/policiales");
        
        return obtenerEnlaces();
    }
    
    /**
     * Obtiene estadísticas de los enlaces
     * @return String con información estadística
     */
    public static String obtenerEstadisticas() {
        int totalEnlaces = ENLACES_NOTICIAS.length;
        int enlacesHttps = 0;
        int enlacesHttp = 0;
        
        for (String enlace : ENLACES_NOTICIAS) {
            if (enlace.toLowerCase().startsWith("https://")) {
                enlacesHttps++;
            } else if (enlace.toLowerCase().startsWith("http://")) {
                enlacesHttp++;
            }
        }
        
        return String.format(
            "Estadísticas de Enlaces:\n" +
            "  Total: %d\n" +
            "  HTTPS: %d (%.1f%%)\n" +
            "  HTTP: %d (%.1f%%)\n" +
            "  Válidos: %s",
            totalEnlaces,
            enlacesHttps, (enlacesHttps * 100.0 / totalEnlaces),
            enlacesHttp, (enlacesHttp * 100.0 / totalEnlaces),
            validarEnlaces() ? "Sí" : "No"
        );
    }
}
