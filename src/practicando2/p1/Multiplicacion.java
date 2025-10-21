package practicando2.p1;

public class Multiplicacion extends Operacion {
    // Implementación polimórfica: 
    // Mismo nombre de método ('operar'), diferente comportamiento.
    public void operar() {
        // En lugar de '+' o '-', usamos '*'
        resultado = valor1 * valor2; 
    }
}
