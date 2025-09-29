package accesosecuencial;
/*
El siguiente programa realiza diversos cambios en los contenidos de un fichero de texto tales como eliminar secuencias de espacios al principio de línea, sustituir secuencias de espacios en otros lugares por un solo espacio, y hacer que todas las líneas empiecen por mayúsculas. Se puede probar con el fichero generado por el programa anterior.
Para las transformaciones en el texto se utiliza funcionalidad de la clase Character. Lo más importante no es entender en detalle lo que hace dentro del bucle while para cada línea, sino la técnica que emplea para modificar los contenidos de un fichero, leyendo en un BufferedReader, escribiendo en un BufferedWriter, utilizando ficheros temporales, y renombrando ficheros. Antes que nada, y por si acaso, se hace una copia del fichero en uno nuevo en cuyo nombre aparece una marca de tiempo incluyendo la fecha y hora exactas. Los nuevos contenidos del fichero se escriben en un fichero temporal. Al terminar, se borra el fichero original y se renombra el fichero temporal con el nombre del fichero original. El renombrado de ficheros consiste no solo en un cambio de nombre, sino también de ubicación, si se especifica un directorio distinto a aquel en que está ubicado el fichero. Es recomendable, y se puede ver que es muy sencillo, hacer que los programas realicen copias de seguridad de todos aquellos ficheros que vayan a modificar, al menos hasta que se hayan probado lo suficiente, después de lo cual se puede eliminar esta parte del programa.
 */
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Clase que modifica un fichero de texto, corrigiendo su formato.
 * El proceso se realiza leyendo el fichero original, escribiendo el contenido modificado en un fichero temporal,
 * y luego reemplazando el original con el temporal.
 */
public class ArreglaFicheroTexto {

    /**
     * El método principal del programa.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        String nomFichero = "f_texto.txt";
        File f = new File(nomFichero);

        // Verificación inicial: Comprueba si el fichero de entrada existe.
        if (!f.exists()) {
            System.out.println("Fichero " + nomFichero + " no existe.");
            return;
        }

        // Bloque try-with-resources: Asegura que el BufferedReader se cierre automáticamente.
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {

            // Crea un fichero temporal para escribir el contenido corregido.
            File fTemp = File.createTempFile(nomFichero, "");
            System.out.println("Creado fich. temporal " + fTemp.getAbsolutePath());

            // Crea un BufferedWriter para escribir en el fichero temporal.
            BufferedWriter bfw = new BufferedWriter(new FileWriter(fTemp));
            String linea = bfr.readLine();

            // Bucle principal: Lee el fichero original línea por línea.
            while (linea != null) {

                // Variables de estado para el procesamiento de la línea.
                boolean principioLinea = true, espacios = false, primerAlfab = false;

                // Bucle interno: Itera sobre cada carácter de la línea.
                for (int i = 0; i < linea.length(); i++) {
                    char c = linea.charAt(i);

                    // Lógica de procesamiento de caracteres.
                    if (Character.isWhitespace(c)) {
                        // Si el carácter es un espacio en blanco...
                        if (!espacios && !principioLinea) {
                            // ... y no es el inicio de la línea o un espacio consecutivo, escribe un solo espacio.
                            bfw.write(c);
                        }
                        espacios = true;
                    } else if (Character.isAlphabetic(c)) {
                        // Si el carácter es alfabético...
                        if (!primerAlfab) {
                            // ... y es el primer carácter alfabético de la línea, lo convierte a mayúscula.
                            bfw.write(Character.toUpperCase(c));
                            primerAlfab = true;
                        } else {
                            // ... si no, lo escribe tal cual.
                            bfw.write(c);
                        }
                        espacios = false;
                        principioLinea = false;
                    } else {
                        // Si el carácter no es ni un espacio ni alfabético, lo escribe directamente.
                        bfw.write(c);
                        espacios = false;
                        principioLinea = false;
                    }
                }

                // Salto de línea después de procesar toda la línea.
                bfw.newLine();
                // Lee la siguiente línea para la siguiente iteración.
                linea = bfr.readLine();
            }

            // Cierra el BufferedWriter.
            bfw.close();

            // Renombra el fichero original añadiendo una extensión de copia de seguridad con la fecha y hora.
            f.renameTo(new File(nomFichero + "." + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".bak"));
            // Renombra el fichero temporal con el nombre del fichero original, reemplazándolo.
            fTemp.renameTo(new File(nomFichero));

        } catch (IOException e) {
            // Manejo de excepciones de entrada/salida.
            System.out.println(e.getMessage());
        } catch (Exception e) {
            // Manejo de cualquier otra excepción genérica.
            e.printStackTrace();
        }
    }
}