package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class NavegadorTabla {

    public void navegacionInteractiva(ResultSet rs) throws SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String comando;

        System.out.println("Comandos: número → ir a fila; 'k' → siguiente; 'd' → anterior; '.' → salir");

        while (true) {
            System.out.print("> ");
            try {
                comando = br.readLine();
            } catch (IOException e) {
                System.out.println("Error leyendo la entrada. Saliendo...");
                break;
            }

            if (comando == null) break;      // Fin de entrada
            comando = comando.trim();         // Eliminamos espacios/saltos

            if (comando.equals(".")) break;   // Salir

            try {
                // Si es número → mover a esa fila
                int numeroFila = Integer.parseInt(comando);
                if (rs.absolute(numeroFila)) {
                    mostrarFilaActual(rs);
                } else {
                    System.out.println("Error: La fila " + numeroFila + " no existe.");
                }

            } catch (NumberFormatException e) {
                // No es un número → interpretar como comando
                switch (comando.toLowerCase()) {

                    case "k": // siguiente fila
                        if (rs.next()) {
                            mostrarFilaActual(rs);
                        } else {
                            System.out.println("Estás en la última fila.");
                            rs.last();
                        }
                        break;

                    case "d": // fila anterior
                        if (rs.previous()) {
                            mostrarFilaActual(rs);
                        } else {
                            System.out.println("Estás en la primera fila.");
                            rs.first();
                        }
                        break;

                    default:
                        System.out.println("Comando no reconocido. Usa 'k', 'd' o '.' para salir.");
                }
            }
        }
    }

    public void mostrarFilaActual(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumnas = rsmd.getColumnCount();

        System.out.println("\n-- Fila " + rs.getRow() + " --");

        for (int i = 1; i <= numColumnas; i++) {
            String nombreColumna = rsmd.getColumnName(i);
            String valorColumna = rs.getString(i);
            System.out.printf(" %-20s : %s%n", nombreColumna, valorColumna);
        }
    }
}
