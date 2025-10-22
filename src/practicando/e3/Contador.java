package practicando.e3;

class Contador {
    int valor = 0;

    // Método sincronizado para evitar condiciones de carrera
    synchronized void incrementar() {
        valor++;
    }
}
