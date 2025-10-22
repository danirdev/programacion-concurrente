package practicando2.p3;

// Clase que contiene la variable compartida (Región Crítica)
public class Contador {
    // La variable 'contador' es la región crítica.
    private int contador = 1;

    public void setContador(int nContador) {
        // En el ejemplo original, aquí se simula el tiempo de espera,
        // permitiendo que otro hilo lea el valor ANTES de que este hilo lo escriba.
        contador = nContador;
    }

    public int getContador() {
        return contador;
    }
}
