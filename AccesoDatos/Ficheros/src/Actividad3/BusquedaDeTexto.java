package Actividad3;

import java.io.*;

public class BusquedaDeTexto {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Indicar por favor nombre de fichero y la palabra que desea buscar");
            return;
        }

        String fich = args [0];

        String palabra = args [1];

        try (BufferedReader fbr = new BufferedReader(new FileReader(fich))){
            int i = 0;
            String linea = fbr.readLine();

            while (linea != null){
                i++;
                if (linea.contains(palabra)){
                    System.out.println("En la línea" + i + " se encuentra la palabra " + palabra);
                }
                linea = fbr.readLine();
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("No existe fichero " + fich);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
