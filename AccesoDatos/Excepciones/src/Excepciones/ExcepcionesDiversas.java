package Excepciones;
// Gestión diferenciada de distintos tipos de excepciones
public class ExcepcionesDiversas {
    public static void main(String[] args) {
        // Rellenar array con números variados
        int nums[][] = new int[2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                nums[i][j] = i + j;
            }
        }
        // Realizar cálculo para cada posición del array.
        // Se producen excepciones de diversos tipos.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    System.out.print("Segunda cifra de 5*nums[" + i + "][" + j + "]/" + j + ": ");
                    System.out.println(String.valueOf(5 * nums[i][j] / j).charAt(1));
                } catch (ArithmeticException e) {
                    System.out.println("ERROR: aritmético 5*"+nums[i][j]+"/"+j);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERROR: No existe nums[" + i + "][" + j + "]");
                } catch (Exception e) {
                    System.out.println("ERROR: de otro tipo al calcular segunda cifra de: 5*"+nums[i][j]+"/"+j);
                    System.out.println();
                    e.printStackTrace();
                }
            }
        }
    }
}