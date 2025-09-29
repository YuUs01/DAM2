package Excepciones;

public class ExcepcionesDivPorCero {
    //Este es un ejemplo de una excepcion no gestionada por el desarrollador. Se sabe que
    //el codigo va a fallar en la línea 7
    public static void main() {
        int a,b;
        a=5;
        b=2;
        System.out.println(a + "/" + b + "=" + a / b);
        b=0;
        System.out.println(a + "/" + b + "=" + a / b);
    }
}