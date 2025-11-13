package print;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImprimirResultados {


    /**
     * Obtiene e imprime los nombres de todas las tablas de usuario en la base de datos
     * asociada a la conexión proporcionada.
     *
     * @param conn La conexión activa y no nula a la base de datos.
     * @throws SQLException Si ocurre un error al acceder a los metadatos.
     */
    public void imprimirTablas(Connection conn, String catalogo) throws SQLException {
        // 1. Obtener el objeto DatabaseMetaData de la conexión.
        // Este objeto contiene toda la información estructural de la base de datos.
        DatabaseMetaData metaData = conn.getMetaData();

        // 2. Especificar que solo queremos buscar objetos de tipo "TABLE".
        // Esto excluye Vistas (VIEW), Tablas del Sistema (SYSTEM TABLE), etc.
        String[] types = {"TABLE"};

        // 3. Usar try-with-resources para asegurar que el ResultSet se cierre automáticamente.
        // getTables() busca las tablas. Con 'null' en los primeros 3 parámetros,
        // le indicamos que busque en cualquier catálogo y esquema.
        try (ResultSet rs = metaData.getTables(catalogo, null, null, types)) {
            System.out.println("📋 Tablas encontradas en la base de datos:");

            // 4. Iterar sobre los resultados.
            while (rs.next()) {
                // La columna "TABLE_NAME" contiene el nombre de la tabla.
                String nombreTabla = rs.getString("TABLE_NAME");
                System.out.println("- " + nombreTabla);
            }
        }
    }


    /**
     * Imprime en la consola todos los registros de la tabla especificada.
     * El método primero verifica si la tabla existe para evitar errores de SQL.
     * Luego, muestra los nombres de las columnas y después cada fila de datos.
     *
     * @param conn        La conexión activa a la base de datos.
     * @param nombreTabla El nombre de la tabla de la que se quieren leer los registros.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public void imprimirRegistros(Connection conn, String catalogo, String nombreTabla) throws SQLException {
        // 1. Verificar primero si la tabla existe para evitar errores y SQL Injection básico.
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(catalogo, null, nombreTabla, new String[]{"TABLE"})) {
            if (!tables.next()) {
                System.err.println("Error: La tabla '" + nombreTabla + "' no existe en la base de datos.");
                return; // Salir del método si la tabla no se encuentra.
            }
        }

        // 2. Construir la consulta SQL. Usar un PreparedStatement aunque el nombre de la tabla no se pueda parametrizar,
        // es una buena práctica y nos protege si añadiéramos un WHERE en el futuro.
        // OJO: El nombre de la tabla no puede ser reemplazado por '?', por eso lo concatenamos.
        // La validación anterior nos da una capa de seguridad.
        String sql = "SELECT * FROM " + nombreTabla;

        System.out.println("📄 Registros de la tabla: " + nombreTabla);
        System.out.println("----------------------------------------");

        // 3. Usar try-with-resources para garantizar el cierre automático de PreparedStatement y ResultSet.
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 4. Obtener los metadatos del ResultSet.
            // Esto nos permite descubrir dinámicamente las columnas del resultado.
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int numeroColumnas = rsMetaData.getColumnCount();

            // 5. Imprimir las cabeceras (nombres de las columnas).
            for (int i = 1; i <= numeroColumnas; i++) {
                // getColumnName() obtiene el nombre de la columna en la posición 'i'.
                System.out.print(rsMetaData.getColumnName(i) + "\t\t");
            }
            System.out.println("\n----------------------------------------");

            // 6. Iterar sobre cada fila (registro) del ResultSet.
            while (rs.next()) {
                // 7. Por cada fila, iterar sobre cada una de sus columnas.
                for (int i = 1; i <= numeroColumnas; i++) {
                    // getString(i) obtiene el valor de la columna 'i' de la fila actual como un String.
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println(); // Salto de línea para pasar al siguiente registro.
            }
        }
    }


    //Actividad 2 Tema 4
    public void imprimirRegistros2(Connection conn, String catalogo, String nombreTabla) throws SQLException {
        // 1. Verificar primero si la tabla existe
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(catalogo, null, nombreTabla, new String[]{"TABLE"})) {
            if (!tables.next()) {
                System.err.println("Error: La tabla '" + nombreTabla + "' no existe en la base de datos.");
                return;
            }
        }

        String sql = "SELECT * FROM " + nombreTabla;

        System.out.println("📄 Registros de la tabla: " + nombreTabla);
        System.out.println("----------------------------------------");

        // 2. Ejecutar la consulta y obtener metadatos
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumnas = rsmd.getColumnCount();

            // Imprimir cabeceras de columnas
            for (int i = 1; i <= numColumnas; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t\t");
            }
            System.out.println("\n----------------------------------------");

            // Imprimir registros
            while (rs.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    // Asume que la tercera columna (CP) es numérica
                    if (i % 3 == 0) {
                        System.out.print(rs.getInt(i) + "\t\t");
                    } else {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                }
                System.out.println(); // salto de línea al final de cada registro
            }
        }
    }


    //Actividad 3 Tema 4
//    public void mostrarNombresInversoConLista (Connection conn) throws SQLException{
//
//        List<String> nombres = new ArrayList<>();
//        System.out.println("leyendo y guradando en la lista");
//        try (Statement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
//    }

    //Actividad 5 Tema 4
    public void mostrarClientesPorDNI(Connection conn, String[] dnis) throws SQLException {
        //1. La plantilla SQL. El ? es un marcador de posición para el DNI
        String sql = "SELECT * FROM CLIENTES WHERE DNI = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            // iteramos sobre la lista de DNIs
            for (String dni : dnis) {
                // Asignar el valor del DNI al actual primer marcador de posición
                pstm.setString(1, dni);


                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        //cliente encontrado
                        System.out.println("-----CLIENTE ENCONTRADO-----");
                        String apellidos = rs.getString("APELLIDOS");
                        String cp = rs.getString("CP");

                        System.out.println("DNI: " + dni);
                        System.out.println("APELLIDOS: " + apellidos);
                        System.out.println("CP: " + cp);
                    } else {
                        //si rs.next es false
                        System.out.println("NO SE ENCONTRÓ NINGÚN CLIENTE CON DNI: " + dni);
                    }
                }
            }
        }
    }
}
