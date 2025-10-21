package practicando.p1;

public class Main2 {
    public static void main(String[] args) {
        Auto miAuto = new Auto();     // creo un objeto
        miAuto.marca = "Toyota";
        miAuto.velocidad = 0;

        // Llamamos varias veces al método acelerar
        miAuto.acelerar();
        miAuto.acelerar();
        miAuto.acelerar();
    }
}
