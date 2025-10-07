package tp9.actividad1;

import java.util.Random;

/**
 * Clase que representa un dado individual
 */
public class Dice {
    private int value; // Valor actual del dado (1-6)
    private Random random; // Generador de números aleatorios

    /**
     * Constructor: inicializa el dado y lo lanza por primera vez
     */
    public Dice() {
        random = new Random();
        roll(); // Lanza el dado al crearlo
    }

    /**
     * Lanza el dado y genera un valor aleatorio entre 1 y 6
     */
    public void roll() {
        value = random.nextInt(6) + 1; // nextInt(6) da 0-5, sumamos 1 para obtener 1-6
    }

    /**
     * Obtiene el valor actual del dado
     * @return valor entre 1 y 6
     */
    public int getValue() {
        return value;
    }
}