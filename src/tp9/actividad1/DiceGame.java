package tp9.actividad1;

import java.util.Arrays;

/**
 * Clase que maneja el juego de dados con 6 dados
 */
public class DiceGame {
    private Dice[] dices; // Array de 6 dados

    /**
     * Constructor: crea 6 dados nuevos
     */
    public DiceGame() {
        dices = new Dice[6];
        for (int i = 0; i < 6; i++) {
            dices[i] = new Dice();
        }
    }

    /**
     * Lanza todos los dados a la vez
     */
    public void rollAll() {
        for (Dice dice : dices) {
            dice.roll();
        }
    }

    /**
     * Obtiene los valores de todos los dados en un array
     * @return array con los 6 valores de los dados
     */
    public int[] getDiceValues() {
        int[] values = new int[6];
        for (int i = 0; i < 6; i++) {
            values[i] = dices[i].getValue();
        }
        return values;
    }

    /**
     * Verifica si la suma de todos los dados es par
     * @return true si la suma es par, false si es impar
     */
    public boolean isSumEven() {
        int sum = Arrays.stream(getDiceValues()).sum(); // Suma todos los valores
        return sum % 2 == 0; // Si el residuo de dividir por 2 es 0, es par
    }

    /**
     * Verifica si se formó una escalera (1, 2, 3, 4, 5, 6 en cualquier orden)
     * @return true si hay escalera, false si no
     */
    public boolean isStair() {
        int[] values = getDiceValues();
        Arrays.sort(values); // Ordena los valores de menor a mayor
        
        // Verifica que tengamos exactamente 1, 2, 3, 4, 5, 6
        for (int i = 0; i < 6; i++) {
            if (values[i] != i + 1) { // values[0] debe ser 1, values[1] debe ser 2, etc.
                return false;
            }
        }
        return true;
    }

    /**
     * Convierte los valores de los dados a String para mostrar
     * @return representación en texto de los valores de los dados
     */
    @Override
    public String toString() {
        return Arrays.toString(getDiceValues());
    }
}