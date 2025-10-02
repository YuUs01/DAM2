package Actividad2;
// Excepción gestionada durante la ejecución de un programa.
public class ExcepcionesDivPorCeroGestEdit {
    public int divide(int a, int b) {
        return a / b;
    }
    public static void main(String[] args) {
        int a, b;
        a = 5; b = 2;
        try {
            System.out.println(a + " / " + b + " = " + a / b);

            b = 0;
            System.out.println(a + " / " + b + " = " + a / b);

            b = 3;
            System.out.println(a + " / " + b + " = " + a / b);
        } catch (ArithmeticException e) {
            System.err.println("Error al dividir: " + a + " / " + b);
        }
    }
}

