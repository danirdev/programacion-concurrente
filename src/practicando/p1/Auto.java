package practicando.p1;

public class Auto {

    String marca;
    int velocidad;

    void acelerar() {
        velocidad += 10;
        System.out.println("La velocidad actual es: " + velocidad + " km/h");
    }
}
