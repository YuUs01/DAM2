package Actividad4;

import java.io.*;

public class ConversorCodificacion {

    public static void main(String[] args) {

        // Ruta relativa del archivo a convertir (debe estar en la carpeta Actividad4)
        String archivoOriginal = "AccesoDatos/Ficheros/src/Actividad4/entrada.txt";

        // Rutas de salida (se crearán automáticamente)
        String archivoISO = "AccesoDatos/Ficheros/src/Actividad4/salida_ISO.txt";
        String archivoUTF16 = "AccesoDatos/Ficheros/src/Actividad4/salida_UTF16.txt";

        File original = new File(archivoOriginal);
        if (!original.exists()) {
            System.err.println("ERROR: El archivo original no existe en la ruta indicada.");
            System.err.println("Ruta probada: " + original.getAbsolutePath());
            return;
        }

        try (
                BufferedReader bfr = new BufferedReader(
                        new InputStreamReader(new FileInputStream(original), "UTF-8"));
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

            System.out.println("Conversión completada:");
            System.out.println("→ " + archivoISO);
            System.out.println("→ " + archivoUTF16);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
