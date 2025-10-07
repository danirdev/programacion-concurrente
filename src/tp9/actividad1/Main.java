package tp9.actividad1;

/**
 * Clase principal que ejecuta la simulación de la máquina de dados
 */
public class Main {
    public static void main(String[] args) {
        // Crea una instancia del juego de dados
        DiceGame game = new DiceGame();
        
        // Bucle infinito para testear la máquina cada 2 segundos
        while (true) {
            try {
                // 1. Lanza los 6 dados
                game.rollAll();
                
                // 2. Muestra la sucesión de dados
                System.out.println("\nDados: " + game);
                
                // 3. Indica si la suma es par o impar
                System.out.println("La suma es " + (game.isSumEven() ? "PAR" : "IMPAR"));
                
                // 4. Verifica si se produjo una escalera
                if (game.isStair()) {
                    System.out.println("SE HA PRODUCIDO UNA ESCALERA!!!");
                }
                
                // 5. Espera 2 segundos antes de la siguiente tirada
                Thread.sleep(2000); // 2000 milisegundos = 2 segundos
                
            } catch (InterruptedException e) {
                // Si se interrumpe el hilo, imprime el error y termina el programa
                e.printStackTrace();
                break;
            }
        }
    }
}