package Excepciones;

// Liberación de recursos en bloque finally

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ExcepcionesConFinally2 {

    public static void main(String[] args) {
        // Declaración de variables para los recursos que se van a gestionar.
        // Se inicializan a 'null' para poder comprobar su estado más adelante.
        File fm1 = null;
        File fm2 = null;
        FileInputStream ifs1 = null;
        FileInputStream ifs2 = null;

        try {
            // Genera un número aleatorio entre 1 y 3 para simular un fallo en diferentes
            // puntos del programa.
            Random aleat = new Random();
            int falloTras = aleat.nextInt(3) + 1;

            System.out.println("SE SALE AL LLEGAR A " + falloTras);

            // Simula un fallo prematuro. Si el número aleatorio es 1, el método termina
            // aquí y se ejecuta el bloque 'finally'.
            if (falloTras <= 1) {
                return;
            }

            // --- Inicio de la gestión de recursos f1 ---
            ifs1 = new FileInputStream("f1.dat");
            System.out.println("Abierto f1.dat");
            fm1 = new File("f1.info.tmp");
            fm1.createNewFile();
            System.out.println("Creado " + fm1.getAbsolutePath());
            // --- Fin de la gestión de recursos f1 ---

            // Simula un segundo fallo. Si el número aleatorio es 2, el método termina
            // después de gestionar f1 y se ejecuta el bloque 'finally'.
            if (falloTras <= 2) {
                return;
            }
            // --- Inicio de la gestión de recursos f2 ---
            ifs2 = new FileInputStream("f2.dat");
            System.out.println("Abierto f2.dat");
            fm2 = new File("f2.info.tmp");
            fm2.createNewFile();
            System.out.println("Creado " + fm2.getAbsolutePath());
            // --- Fin de la gestión de recursos f2 ---

            System.out.println("Ejecutado hasta el final");

        } catch (FileNotFoundException e) {
            // Captura una excepción si no se encuentra el fichero (por ejemplo, f1.dat o f2.dat).
            System.err.println("Fichero no encontrado: " + e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otra excepción genérica, lo cual es útil para manejar
            // errores inesperados.
            e.printStackTrace();
        } finally {
            // El bloque 'finally' se ejecuta siempre, tanto si hay una excepción como si no.
            // Su propósito principal es liberar los recursos de manera segura.
            System.out.println("Liberando recursos: INICIO.");

            // Comprobación y cierre del primer flujo de entrada.
            // Es crucial verificar si 'ifs1' no es 'null' antes de intentar cerrarlo,
            // ya que si ocurre un fallo al inicio, 'ifs1' podría no haber sido inicializado.
            if (ifs1 != null) {
                try {
                    ifs1.close();
                    System.out.println("Cerrado f1.dat");

                } catch (IOException e) {
                    // Si ocurre un error al cerrar el flujo, se captura la excepción.
                    System.err.println("Error al cerrar fichero: " + e.getMessage());
                }
            }
            // Comprobación y cierre del segundo flujo de entrada, siguiendo la misma lógica.
            if (ifs2 != null) {
                try {
                    ifs2.close();
                    System.out.println("Cerrado f2.dat");
                } catch (IOException e) {
                    System.err.println("Error al cerrar fichero: " + e.getMessage());
                }
            }
            // Eliminación del primer archivo temporal.
            // Se verifica que no sea 'null' para evitar un 'NullPointerException'.
            if (fm1 != null) {
                fm1.delete();
                System.out.println("Borrado " + fm1.getName());
            }
            // Eliminación del segundo archivo temporal.
            if (fm2 != null) {
                fm2.delete();
                System.out.println("Borrado " + fm2.getName());
            }
            System.out.println("Liberando recursos: FIN.");
        }
    }
}