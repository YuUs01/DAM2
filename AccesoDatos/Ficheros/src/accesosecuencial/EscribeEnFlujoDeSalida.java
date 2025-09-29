package accesosecuencial;

/*
El siguiente programa escribe un texto en un fichero. Después lo cierra y lo vuelve a abrir en modo append para añadir nuevos contenidos al final. A menos que el fichero ya exista, en cuyo caso no hace nada. Si se hicieran estas mismas operaciones sobre un fichero existente, se perderían los contenidos del fichero. Se añaden saltos de línea con newLine().
 */
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class EscribeEnFlujoDeSalida {

    public static void main(String[] args) {
        // Define el nombre del archivo de texto a manipular.
        String nomFichero = "f_texto.txt";

        // Crea un objeto File para representar el archivo.
        File f = new File(nomFichero);

        // Comprueba si el archivo ya existe. Si es así, imprime un mensaje
        // y finaliza la ejecución para evitar sobrescribir el contenido inicial.
        if (f.exists()) {
            System.out.println("Fichero " + nomFichero + " ya existe. No se hace nada");
            return;
        }

        // Se usa un bloque 'try-catch' para manejar posibles excepciones de I/O.
        try {
            // Crea un objeto FileWriter que se encarga de escribir en el archivo.
            // Se envuelve con un BufferedWriter para un manejo más eficiente de la escritura.
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f));

            // Escribe una línea de texto en el archivo.
            bfw.write("Este es un fichero de texto. ");

            // Inserta un salto de línea.
            bfw.newLine();

            // Escribe otra línea de texto.
            bfw.write("quizá no está del todo bien.");

            // Inserta otro salto de línea.
            bfw.newLine();

            // Cierra el flujo de escritura para liberar los recursos del sistema.
            // Esto también asegura que el contenido en el buffer se vuelque al archivo.
            bfw.close();

            // Se crea un nuevo BufferedWriter para añadir más texto al final del archivo.
            // El segundo argumento del constructor de FileWriter, 'true', activa el modo de 'append' (añadir).
            bfw = new BufferedWriter(new FileWriter(f, true));

            // Escribe texto adicional.
            bfw.write("Pero se puede arreglar.");

            // Inserta un salto de línea después del texto añadido.
            bfw.newLine();

            // Cierra el flujo de escritura nuevamente.
            bfw.close();

        } catch (IOException e) {
            // Captura y muestra un mensaje en caso de una excepción de I/O.
            System.out.println(e.getMessage());
        } catch (Exception e) {
            // Captura y muestra la traza completa de cualquier otra excepción no esperada.
            e.printStackTrace();
        }
    }
}