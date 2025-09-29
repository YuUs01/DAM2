package accesosecuencial;
/*
Como ejemplo, el siguiente programa muestra los contenidos de un fichero de texto línea a línea, numerando
las líneas.Para leer líneas de texto se usa el método readLine() de la clase BufferedReader.
En este programa, y en todos a partir de ahora, se utilizarán bloques try con recursos para crear distintos
tipos de flujos (stream), con lo que close() se ejecutará automáticamente al final.
 */
// Uso de readLine() de BufferedReader
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Esta clase lee un archivo de texto, numera cada línea y la imprime en la consola.
public class EscribeConNumerosDeLineas {

    public static void main(String[] args) {
        // Verifica que se haya pasado el nombre del archivo como argumento de línea de comandos.
        if (args.length < 1) {
            System.out.println("Indicar por favor nombre de fichero.");
            return;
        }

        // Almacena el nombre del archivo pasado como argumento.
        String nomFich = args[0];

        // Se usa un bloque try-with-resources para asegurar que el BufferedReader se cierre automáticamente.
        try (BufferedReader fbr = new BufferedReader(new FileReader(nomFich))) {
            int i = 0;
            String linea = fbr.readLine(); // Lee la primera línea del archivo.

            // Bucle que se ejecuta mientras haya líneas para leer (hasta que readLine() devuelva null).
            while (linea != null) {
                // Formatea e imprime el número de línea (incrementado) y el contenido de la línea.
                System.out.format("[%5d] %s", ++i, linea);
                System.out.println();
                linea = fbr.readLine(); // Lee la siguiente línea.
            }
        }
        // Captura la excepción si el archivo no se encuentra.
        catch (FileNotFoundException e) {
            System.out.println("No existe fichero " + nomFich);
        }
        // Captura otras excepciones de E/S.
        catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
        // Captura cualquier otra excepción no especificada.
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
