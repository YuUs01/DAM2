// Volcado hexadecimal de un fichero con FileInputStream
package accesosecuencial;
/*
Ahora un ejemplo con flujos binarios.
El siguiente programa hace un volcado binario de un fichero indicado desde línea de comandos.
Los contenidos del fichero se leen en bloques de 32 bytes, y el contenido de cada bloque se escribe en una línea de texto.
Los bytes se escriben en hexadecimal (base 16) y, por tanto, cada byte se escribe utilizando dos caracteres1.
El programa muestra como máximo los primeros 2 kilobytes (MAX_BYTES=2048).
Por supuesto, este programa se puede utilizar tanto con ficheros binarios como con ficheros de texto.
Hacer notar que esta clase permite hacer el volcado binario de un InputStream, y un FileInputStream es un caso particular.
Siempre que sea posible, debemos hacer que las clases que desarrollemos funcionen con streams en general,
y no solo con ficheros en particular.
 */
import java.io.*;

/**
 * Clase que permite realizar un volcado binario (en formato hexadecimal) de un fichero.
 */
public class VolcadoBinario {
    // Define el número de bytes a leer y mostrar por fila en el volcado.
    static int TAM_FILA=32;
    // Define el número máximo de bytes a volcar.
    static int MAX_BYTES=2048;
    // Referencia al stream de entrada de datos, puede ser de cualquier tipo que herede de InputStream.
    InputStream is=null;

    /**
     * Constructor de la clase.
     * @param is El InputStream desde el que se leerán los datos.
     */
    public VolcadoBinario(InputStream is) {
        this.is=is;
    }

    /**
     * Realiza el volcado de los bytes del fichero.
     * Lee el fichero en bloques, convierte los bytes a hexadecimal y los imprime en la consola.
     * @throws IOException Si ocurre un error de entrada/salida durante la lectura del fichero.
     */
    public void volcar(PrintStream ps) throws IOException {
        // Un array de bytes que actúa como un buffer para almacenar los datos leídos.
        byte buffer[]=new byte[TAM_FILA];
        // Almacena el número de bytes leídos en cada operación de lectura.
        int bytesLeidos;
        // Mantiene el desplazamiento o 'offset' actual dentro del fichero.
        int offset=0;
        do {
            // Lee hasta 'TAM_FILA' bytes del stream y los almacena en el buffer.
            bytesLeidos=is.read(buffer);
            // Imprime el offset actual formateado a 5 dígitos.
            ps.format("[%5d] ", offset);
            // Itera sobre los bytes leídos en el buffer.
            for(int i=0; i<bytesLeidos; i++) {
                // Formatea y imprime cada byte en su representación hexadecimal de dos dígitos.
                ps.format("%2x", buffer[i]);
            }
            // Incrementa el offset con el número de bytes que se acaban de leer.
            offset+=bytesLeidos;
            // Salto de línea para la siguiente fila del volcado.
            ps.println();
        } while (bytesLeidos==TAM_FILA && offset<MAX_BYTES); // Continúa el bucle mientras se lean 'TAM_FILA' bytes y no se haya superado el límite 'MAX_BYTES'.
    }

    /**
     * Método principal (main) para la ejecución del programa.
     * @param args Argumentos de la línea de comandos, se espera que el primer argumento sea la ruta del fichero.
     */
    public static void main(String[] args) {
        // Comprueba si se ha pasado al menos un argumento al programa.
        if(args.length<1) {
            System.out.println("No se ha indicado ningún fichero");
            return;
        }
        // Asigna el primer argumento a la variable 'nomFich'.
        String nomFich=args[0];
        // Usa un 'try-with-resources' para asegurar que el 'FileInputStream' se cierra automáticamente.
        try (FileInputStream fis = new FileInputStream(nomFich);
             PrintStream ps = new PrintStream("volcado_salida.txt")) {

            VolcadoBinario vb = new VolcadoBinario(fis);
            vb.volcar(ps);
        }
        // Captura la excepción si el fichero no se encuentra.
        catch (FileNotFoundException e) {
            System.err.println("ERROR: no existe fichero "+nomFich);
        }
        // Captura la excepción si ocurre un error general de entrada/salida.
        catch (IOException e) {
            System.err.println("ERROR de E/S: "+e.getMessage());
        }
        // Captura cualquier otra excepción no prevista y muestra su traza de pila.
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}