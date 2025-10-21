package practicando2.p1;

public class Prueba {
    public static void main(String[] ar) {
        // Crear objeto de la clase Suma
        Suma suma1 = new Suma();
        
        // Usar métodos heredados de Operacion
        suma1.cargar1();
        suma1.cargar2();
        
        // Usar método propio de Suma
        suma1.operar();
        System.out.print("El resultado de la suma es: ");
        suma1.mostrarResultado(); // Usar método heredado

        // Crear objeto de la clase Resta
        Resta resta1 = new Resta();
        
        // Usar métodos heredados de Operacion
        resta1.cargar1();
        resta1.cargar2();
        
        // Usar método propio de Resta
        resta1.operar();
        System.out.print("El resultado de la resta es: ");
        resta1.mostrarResultado(); // Usar método heredado
    }
}
