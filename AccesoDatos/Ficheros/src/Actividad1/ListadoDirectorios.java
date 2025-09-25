package Actividad1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListadoDirectorios {
    public static void main(String[] args) {
        String ruta = "."; // directorio actual por defecto
        if (args.length >= 1) ruta = args[0];

        File fich = new File(ruta);

        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio " + ruta);
        } else {
            if (fich.isFile()) {
                System.out.println("Es un fichero: " + ruta);
            } else {
                System.out.println(ruta + " es un directorio. Contenidos: ");
                File[] ficheros = fich.listFiles();

                if (ficheros != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for (File f : ficheros) {
                        String textoDescr = f.isDirectory() ? "/" : f.isFile() ? "_" : "?";
                        String permisos = (f.canRead() ? "r" : "-") +
                                (f.canWrite() ? "w" : "-") +
                                (f.canExecute() ? "x" : "-");

                        System.out.println("(" + textoDescr + ") " + f.getName() +
                                " " + f.length() + " bytes " +
                                "Permisos: " + permisos +
                                " Última mod: " + sdf.format(new Date(f.lastModified())));
                    }
                }
            }
        }
    }
}

