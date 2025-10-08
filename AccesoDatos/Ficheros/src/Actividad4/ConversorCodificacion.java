package Actividad4;

import java.io.*;

public class ConversorCodificacion {
    public static void main(String[] args) {
        // creación de la variable que contiene el archivo original, con la ruta relativa a su ubicación
        String archivoOriginal = "";//poner la ruta del archivo a covertir
        // creación de los archivos de salida codificados
        String archivoISO = ""; //poner la ruta del archivo a crear
        String archivoUTF16 = "";//poner la ruta del archivo a crear

        try (
                BufferedReader bfr = new BufferedReader(
                        new InputStreamReader(new FileInputStream(archivoOriginal), "UTF-8"));
                BufferedWriter bwISO = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(archivoISO), "ISO-8859-1"));
                BufferedWriter bwUTF16 = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(archivoUTF16), "UTF-16"))
        ) {
            String linea;
            while ((linea = bfr.readLine()) != null) {
                bwISO.write(linea);
                bwISO.newLine();
                bwUTF16.write(linea);
                bwUTF16.newLine();
            }
            System.out.println("Conversión del archivo completada");
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
