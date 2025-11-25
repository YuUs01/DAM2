package UD1;

import java.io.IOException;

public class EjecutarProcesoProcessBuilder {
    public static void main(String[] args) {

        try{
            ProcessBuilder pb = new ProcessBuilder("notepad");
            Process proceso = pb.start();

            int exitCode = proceso.waitFor();
            System.out.println("Código de salida " + exitCode);

        } catch (IOException ioe) {
            System.err.println("No se pudo ejecutar el proceso. " +
                    "verifica que tienes permisos o que el comando existe: " + ioe.getMessage());
        }catch (Exception e){
            System.err.println("Se produjo un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
