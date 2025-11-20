package dao;

import pojos.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {
    Connection connection;
    public Dao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Comprueba si la tabla CLIENTES existe y, si no, la crea.
     * Este método es idempotente, lo que significa que se puede ejecutar
     * varias veces sin causar errores ni cambios después de la primera ejecución exitosa.
     *
     * @throws SQLException Si ocurre un error de acceso a la base de datos durante la operación.
     */
    public void crearTablaClientesSiNoExiste() throws SQLException {
        // Define el nombre de la tabla que queremos comprobar/crear.
        final String nombreTabla = "CLIENTES";

        // 1. Obtener los metadatos de la base de datos a través de la conexión.
        // DatabaseMetaData nos proporciona métodos para explorar la estructura de la BBDD.
        DatabaseMetaData dbm = connection.getMetaData();

        // 2. Comprobar si la tabla "CLIENTES" ya existe.
        // getTables() devuelve un ResultSet con la lista de tablas que coinciden con el patrón.
        // Pasamos null a catálogo y esquema para buscar en cualquier lugar, y el nombre exacto de la tabla.
        ResultSet tables = dbm.getTables(null, null, nombreTabla, null);

        // 3. Evaluar el resultado.
        if (tables.next()) {
            // Si tables.next() devuelve 'true', significa que el ResultSet tiene al menos una fila,
            // lo que confirma que la tabla ya existe.
            System.out.println("La tabla '" + nombreTabla + "' ya existe. No se requiere ninguna acción.");
        } else {
            // Si el ResultSet está vacío, la tabla no existe y procedemos a crearla.
            System.out.println("La tabla '" + nombreTabla + "' no existe. Creándola...");

            // Usamos un bloque 'try-with-resources' para asegurar que el Statement se cierre automáticamente.
            try (Statement stmt = connection.createStatement()) {
                // La sentencia DDL a ejecutar.
                String sql = "CREATE TABLE CLIENTES ("
                        + "DNI CHAR(9) NOT NULL, "
                        + "APELLIDOS VARCHAR(32) NOT NULL, "
                        + "CP CHAR(5), "
                        + "PRIMARY KEY(DNI))";

                // 4. Ejecutar la sentencia de creación.
                // Usamos executeUpdate() para sentencias DDL (CREATE, ALTER, DROP) y DML (INSERT, UPDATE, DELETE).
                stmt.executeUpdate(sql);
                System.out.println("Tabla '" + nombreTabla + "' creada con éxito.");
            }
        }
    }

    public void crearTablaCompaniesSiNoExiste() throws SQLException {
        // Define el nombre de la tabla que queremos comprobar/crear.
        final String nombreTabla = "COMPANIES";

        // 1. Obtener los metadatos de la base de datos a través de la conexión.
        // DatabaseMetaData nos proporciona métodos para explorar la estructura de la BBDD.
        DatabaseMetaData dbm = connection.getMetaData();

        // 2. Comprobar si la tabla "CLIENTES" ya existe.
        // getTables() devuelve un ResultSet con la lista de tablas que coinciden con el patrón.
        // Pasamos null a catálogo y esquema para buscar en cualquier lugar, y el nombre exacto de la tabla.
        ResultSet tables = dbm.getTables(null, null, nombreTabla, null);

        // 3. Evaluar el resultado.
        if (tables.next()) {
            // Si tables.next() devuelve 'true', significa que el ResultSet tiene al menos una fila,
            // lo que confirma que la tabla ya existe.
            System.out.println("La tabla '" + nombreTabla + "' ya existe. No se requiere ninguna acción.");
        } else {
            // Si el ResultSet está vacío, la tabla no existe y procedemos a crearla.
            System.out.println("La tabla '" + nombreTabla + "' no existe. Creándola...");

            // Usamos un bloque 'try-with-resources' para asegurar que el Statement se cierre automáticamente.
            try (Statement stmt = connection.createStatement()) {
                // La sentencia DDL a ejecutar.
                String sql = "CREATE TABLE COMPANIES ("
                        + "CIF VARCHAR(9) NOT NULL, "
                        + "NOMBRE VARCHAR(32) NOT NULL, "
                        + "SECTOR VARCHAR(32), "
                        + "PRIMARY KEY(CIF))";

                // 4. Ejecutar la sentencia de creación.
                // Usamos executeUpdate() para sentencias DDL (CREATE, ALTER, DROP) y DML (INSERT, UPDATE, DELETE).
                stmt.executeUpdate(sql);
                System.out.println("Tabla '" + nombreTabla + "' creada con éxito.");
            }
        }
    }

    /**
     * Ejecuta una sentencia SQL de inserción utilizando un Statement.
     *ADVERTENCIA: Este método es vulnerable a inyección SQL.
     * Es útil para fines educativos o para ejecutar sentencias completamente controladas,
     * pero no debe usarse con datos provenientes del usuario.
     *
     * @param conn La conexión activa a la base de datos.
     * @param sqlInsert La sentencia SQL completa para el INSERT (ej: "INSERT INTO CLIENTES...")
     * @throws SQLException Si ocurre un error al ejecutar la inserción.
     */
    public void insertarDatosConStatement(Connection conn, String sqlInsert) throws SQLException {
        // 1. Usar try-with-resources para asegurar que el Statement se cierre automáticamente.
        // Un Statement es un objeto que representa una sentencia SQL.
        try (Statement stmt = conn.createStatement()) {

            // 2. Ejecutar la sentencia de inserción.
            // executeUpdate() devuelve el número de filas afectadas (insertadas, actualizadas o borradas).
            int filasAfectadas = stmt.executeUpdate(sqlInsert);

            // 3. Informar del resultado.
            System.out.println("Inserción completada con éxito. Filas afectadas: " + filasAfectadas);
        }
    }

    /**
     * Inserta una lista de clientes en la tabla CLIENTES1 usando un PreparedStatement.
     * Este método recibe una conexión ya existente.
     * Recepción de Connection: El método insertarClientes(Connection conn, ...)
     * no crea la conexión, sino que la recibe como parámetro.
     * Esta es una excelente práctica conocida como inyección de dependencias,
     * que hace que el método sea más reutilizable y fácil de probar.
     * La responsabilidad de abrir y cerrar la conexión recae en quien llama al método.
     * PreparedStatement: Al usar ? como marcadores, evitamos la concatenación de strings.
     * Esto nos protege contra ataques de inyección SQL y permite que el motor de
     * la base de datos precompile la consulta, mejorando el rendimiento si se ejecuta varias veces.
     * try-with-resources: La línea try (PreparedStatement pstmt = ...)
     * asegura que el objeto pstmt se cierre automáticamente al finalizar el bloque,
     * previniendo fugas de recursos en la base de datos, incluso si ocurre un error.
     *
     * @param conn La conexión activa a la base de datos.
     * @param clientes La lista de clientes a insertar.
     */
    public void insertarClientes(Connection conn, List<Cliente> clientes) {
        //1. Define la sentencia SQL con placeholders (?) para seguridad y rendimiento.
        String sql = "INSERT INTO CLIENTES (DNI, APELLIDOS, CP) VALUES (?, ?, ?)";

        //2. Usa try-with-resources para asegurar que el PreparedStatement se cierre.
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Cliente cliente : clientes) {
                //3. Asigna los valores a los placeholders.
                pstmt.setString(1, cliente.getDni());      // Primer '?'
                pstmt.setString(2, cliente.getApellidos()); // Segundo '?'
                pstmt.setInt(3, cliente.getCodigoPostal()); // Tercer '?'

                // 4. Ejecuta la inserción para este cliente.
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Cliente insertado con éxito: " + cliente.getApellidos());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar los datos de los clientes.");
            // Imprime la traza del error para obtener más detalles.
            e.printStackTrace();
        }
    }

    /**
     * Inserta clientes de forma más eficiente usando procesamiento por lotes (batch).
     * Esto reduce drásticamente la comunicación con la base de datos.
     * Mejora: Inserción por Lotes (Batch Processing)
     *
     * Realizar una operación de base de datos por cada cliente en un bucle puede ser ineficiente.
     * Es mucho más rápido agrupar todas las inserciones
     * en un "lote" y enviarlas al servidor de base de datos de una sola vez.
     *
     * Esta versión es altamente recomendada para inserciones múltiples.
     *
     * @param conn La conexión activa a la base de datos.
     * @param clientes La lista de clientes a insertar.
     */
    public void insertarClientesBatch(Connection conn, List<Cliente> clientes) {
        String sql = "INSERT INTO CLIENTES (DNI, APELLIDOS, CP) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Cliente cliente : clientes) {
                pstmt.setString(1, cliente.getDni());
                pstmt.setString(2, cliente.getApellidos());
                pstmt.setInt(3, cliente.getCodigoPostal());

                //En lugar de ejecutar, añade la consulta al lote.
                pstmt.addBatch();
            }

            //Ejecuta todas las operaciones del lote en una sola llamada a la BD.
            int[] resultados = pstmt.executeBatch();

            System.out.println("Proceso por lotes finalizado. Total de clientes procesados: " + resultados.length);

        } catch (SQLException e) {
            System.err.println("Error durante la inserción por lotes.");
            e.printStackTrace();
        }
    }

    /**
     * Inserta clientes usando un lote dentro de una transacción controlada manualmente.
     * Si cualquier inserción falla, se revierten (rollback) todas las demás.
     * * Inserta clientes de forma más eficiente usando procesamiento por lotes (batch).
     *      * Esto reduce drásticamente la comunicación con la base de datos.
     *      * Mejora: Inserción por Lotes (Batch Processing)
     *      *
     *      * Realizar una operación de base de datos por cada cliente en un bucle puede ser ineficiente.
     *      * Es mucho más rápido agrupar todas las inserciones
     *      * en un "lote" y enviarlas al servidor de base de datos de una sola vez.
     *      *
     *      * Esta versión es altamente recomendada para inserciones múltiples.
     *
     * @param conn La conexión activa a la base de datos.
     * @param clientes La lista de clientes a insertar.
     */


    public void insertarClientesBatchConTransaccion(Connection conn, List<Cliente> clientes) {
        String sql = "INSERT INTO CLIENTES (DNI, APELLIDOS, CP) VALUES (?, ?, ?)";

        try {
            //1. INICIAR LA TRANSACCIÓN
            // Desactivamos el modo auto-commit para controlar la transacción manualmente.
            conn.setAutoCommit(false);

            // Usamos try-with-resources para el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Cliente cliente : clientes) {
                    pstmt.setString(1, cliente.getDni());
                    pstmt.setString(2, cliente.getApellidos());
                    pstmt.setInt(3, cliente.getCodigoPostal());
                    pstmt.addBatch();
                }

                //2.EJECUTAR EL LOTE
                System.out.println("Ejecutando el lote de inserciones...");
                pstmt.executeBatch();

                //3. CONFIRMAR LA TRANSACCIÓN
                // Si executeBatch() no lanzó una excepción, todo fue bien. Hacemos permanentes los cambios.
                conn.commit();
                System.out.println("¡Éxito! La transacción ha sido confirmada (commit).");
            }
        } catch (SQLException e) {
            //4. MANEJAR EL ERROR Y HACER ROLLBACK
            System.err.println("Error durante la inserción por lotes. Iniciando rollback...");
            try {
                if (conn != null) {
                    // Revertimos todos los cambios hechos desde el setAutoCommit(false)
                    conn.rollback();
                    System.out.println("El rollback se ha completado con éxito.");
                }
            } catch (SQLException ex) {
                System.err.println("Error crítico al intentar hacer rollback.");
                ex.printStackTrace();
            }
            // También es útil imprimir el error original que causó el fallo
            e.printStackTrace();
        } finally {
            //5. RESTAURAR EL MODO ORIGINAL
            //Es una buena práctica devolver la conexión a su estado original.
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Crea múltiples facturas, una por cada DNI de la lista, gestionando
     * cada una como una transacción independiente.
     * Si falla la creación de una factura (por ejemplo, por un DNI inválido),
     * solo se revierte esa factura específica, y el programa intenta continuar con las siguientes.
     * Esto evita que un solo error invalide todo el lote.
     * La gestión de la transacción (setAutoCommit, commit, rollback) se encuentra dentro del bucle.
     * Esto crea un límite transaccional para cada factura. Si la factura del DNI 11223344A falla,
     * su rollback no afecta a la factura del DNI 78901234X que ya se confirmó (commit) en la iteración anterior.
     * El procesamiento es por lotes.
     * El uso de un bloque try-with-resources para los PreparedStatement asegura que se cierren
     * automáticamente al finalizar, simplificando el código.
     * El bloque finally: restaurar el estado original del autoCommit de la conexión, asegurando
     * que el método no deje la conexión en un estado inesperado.
     *
     * @param conn La conexión activa a la base de datos.
     * @param dnisClientes La lista de DNI de los clientes para quienes crear facturas.
     * @param lineas La lista de objetos LineaFactura a insertar en CADA factura.
     * @return Un Map que asocia cada DNI a su número de factura generado con éxito.
     * @throws SQLException Si ocurre un error irrecuperable con la conexión.
     */
    public static Map<String, Integer> crearFacturas(Connection conn, List<String> dnisClientes, List<LineaFactura> lineas) throws SQLException {
        String sqlInsertFactura = "INSERT INTO FACTURAS(DNI_CLIENTE) VALUES (?)";
        String sqlInsertLinea = "INSERT INTO LINEAS_FACTURA(NUM_FACTURA, LINEA_FACTURA, CONCEPTO, CANTIDAD) VALUES (?, ?, ?, ?)";

        // Guardamos el estado original del auto-commit para restaurarlo al final.
        boolean autoCommitOriginal = conn.getAutoCommit();
        // El mapa almacenará los resultados exitosos: DNI -> numFactura
        Map<String, Integer> facturasCreadas = new HashMap<>();

        try (PreparedStatement psInsertFact = conn.prepareStatement(sqlInsertFactura, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psInsertLinea = conn.prepareStatement(sqlInsertLinea)) {

            // Iteramos sobre cada DNI para crear su factura correspondiente.
            for (String dni : dnisClientes) {
                try {
                    // == INICIO DE LA TRANSACCIÓN PARA UNA FACTURA ==
                    conn.setAutoCommit(false);

                    // 1. INSERTAR LA CABECERA DE LA FACTURA
                    psInsertFact.setString(1, dni);
                    psInsertFact.executeUpdate();

                    // 2. OBTENER LA CLAVE GENERADA
                    int numFacturaGenerado = -1;
                    try (ResultSet generatedKeys = psInsertFact.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            numFacturaGenerado = generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("Error al crear la factura para el DNI " + dni + ", no se obtuvo ID.");
                        }
                    }

                    // 3. INSERTAR LAS LÍNEAS DE LA FACTURA (usando batch para eficiencia)
                    int numeroDeLinea = 1;
                    for (LineaFactura linea : lineas) {
                        psInsertLinea.setInt(1, numFacturaGenerado);
                        psInsertLinea.setInt(2, numeroDeLinea++);
                        psInsertLinea.setString(3, linea.getConcepto());
                        psInsertLinea.setInt(4, linea.getCantidad());
                        psInsertLinea.addBatch(); // Agregamos la inserción al lote
                    }
                    psInsertLinea.executeBatch(); // Ejecutamos el lote de líneas

                    // == FIN DE LA TRANSACCIÓN (ÉXITO PARA ESTA FACTURA) ==
                    conn.commit();
                    facturasCreadas.put(dni, numFacturaGenerado); // Guardamos el resultado exitoso
                    System.out.println("ÉXITO: Factura creada para DNI " + dni + " con número " + numFacturaGenerado);

                } catch (SQLException e) {
                    // == ROLLBACK PARA LA FACTURA ACTUAL ==
                    // Si algo falló para este DNI, deshacemos sus cambios y continuamos con el siguiente.
                    System.err.println("FALLO: Ocurrió un error para el DNI " + dni + ". Realizando rollback...");
                    conn.rollback();
                    e.printStackTrace(System.err);
                }
            }
        } finally {
            // == LIMPIEZA FINAL ==
            // Al final de todo el proceso, restauramos el estado original del auto-commit.
            conn.setAutoCommit(autoCommitOriginal);
        }

        return facturasCreadas;
    }

    /**
     * Llama a un procedimiento almacenado para obtener un listado de clientes.
     *
     * @param conn La conexión activa a la base de datos.
     * @param dni El DNI del cliente para filtrar (parámetro de entrada).
     * @return Un objeto ResultadoListado que contiene la lista de clientes y el valor
     * del parámetro INOUT.
     * @throws SQLException Si ocurre un error durante la comunicación con la BBDD.
     */
    public static ResultadoListado llamarListadoClientes(Connection conn, String dni) throws SQLException {
        // La sintaxis "{call ...}" es el estándar de JDBC para llamar a procedimientos.
        String sql = "{call listado_parcial_clientes(?,?)}"; // Asumimos 2 parámetros

        List<Cliente> listaClientes = new ArrayList<>();
        int valorInOut;

        // Uso de try-with-resources para asegurar que el CallableStatement se cierre
        // automáticamente, incluso si ocurre una excepción. Esto previene fugas de recursos.
        try (CallableStatement stmt = conn.prepareCall(sql)) {

            // 1. CONFIGURAR PARÁMETROS
            // Parámetro 1: DNI (de entrada, IN)
            stmt.setString(1, dni);

            // Parámetro 2: Contador (de entrada/salida, INOUT)
            stmt.setInt(2, 0); // Valor inicial de entrada
            stmt.registerOutParameter(2, Types.INTEGER); // Se registra como parámetro de salida

            // 2. EJECUTAR EL PROCEDIMIENTO
            // Se usa execute() porque el procedimiento puede devolver un ResultSet y parámetros de salida.
            stmt.execute();

            // 3. RECUPERAR LOS RESULTADOS
            // Primero recuperamos el valor del parámetro de salida (OUT o INOUT).
            valorInOut = stmt.getInt(2);

            // Luego, procesamos el ResultSet si existe.
            try (ResultSet rs = stmt.getResultSet()) {
                while (rs != null && rs.next()) {
                    String dniCliente = rs.getString("DNI");
                    String apellidosCliente = rs.getString("APELLIDOS");
                    listaClientes.add(new Cliente(dniCliente, apellidosCliente, -1));
                }
            } // El try-with-resources interno cierra el ResultSet automáticamente.
        }

        // Devolvemos un objeto que encapsula todos los resultados.
        return new ResultadoListado(valorInOut, listaClientes);
    }

    public void obtenerYMostrarApellidosAlternativo(String dniCliente, Connection connection) {
        //Las funciones se pueden invocar directamente en una consulta SELECT.
        String sql = "SELECT obtener_apellidos_mejorado(?) AS apellidos";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            //Paso 1: Establecer el parámetro de entrada
            pstmt.setString(1, dniCliente);

            //Paso 2: Ejecutar la consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                // **Paso 3: Procesar el resultado**
                if (rs.next()) {
                    String apellidos = rs.getString("apellidos"); // O rs.getString(1)
                    System.out.println("Apellidos encontrados (método alternativo): " + apellidos);
                } else {
                    System.out.println("La consulta no devolvió resultados.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el método alternativo.");
            e.printStackTrace();
        }
    }
    /**
     * Realiza un conjunto de modificaciones en la tabla CLIENTES usando un ResultSet actualizable.
     * Modifica el CP del último cliente, borra el penúltimo e inserta uno nuevo.
     *
     * IMPORTANTE: Este método asume que se está ejecutando dentro de una transacción
     * gestionada por el código que lo llama (no hace commit ni rollback).
     *
     * @param conn La conexión activa a la base de datos.
     * @param nuevoCpUltimoCliente El nuevo código postal para el último cliente del resultset.
     * @param clienteAInsertar Un objeto ClienteNuevo con los datos del cliente a insertar.
     * @throws SQLException Si ocurre un error en la base de datos o el ResultSet está vacío.
     */
    public static void modificarClientesConResultSet(Connection conn, String nuevoCpUltimoCliente, ClienteNuevo clienteAInsertar) throws SQLException {

        String sql = "SELECT DNI, APELLIDOS, CP FROM CLIENTES WHERE CP IS NOT NULL";

        // Usamos try-with-resources para el Statement y el ResultSet.
        // Esto garantiza que ambos se cierren automáticamente, previniendo fugas de recursos.
        try (Statement sConsulta = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = sConsulta.executeQuery(sql)) {

            // Verificamos que el ResultSet tenga al menos dos filas para poder operar
            if (!rs.last()) { // Mueve el cursor al final y devuelve false si está vacío
                throw new SQLException("No hay clientes para modificar. El ResultSet está vacío.");
            }
            if (rs.getRow() < 2) {
                throw new SQLException("Se necesitan al menos dos clientes para realizar la operación completa.");
            }
            rs.first(); // Volvemos al principio para seguir la lógica original

            // 1. MODIFICAR EL ÚLTIMO CLIENTE
            rs.last(); // Nos posicionamos en el último registro
            rs.updateString("CP", nuevoCpUltimoCliente);
            rs.updateRow(); // Confirma la modificación en la BBDD
            System.out.println("-> Fila actualizada.");

            // 2. BORRAR EL PENÚLTIMO CLIENTE
            rs.previous(); // Nos movemos al penúltimo registro
            rs.deleteRow(); // Borra la fila actual de la BBDD
            System.out.println("-> Fila borrada.");

            // 3. INSERTAR UN NUEVO CLIENTE
            rs.moveToInsertRow(); // Movemos el cursor a una fila especial para inserción
            rs.updateString("DNI", clienteAInsertar.dni());
            rs.updateString("APELLIDOS", clienteAInsertar.apellidos());
            rs.updateString("CP", clienteAInsertar.cp());
            rs.insertRow(); // Confirma la inserción en la BBDD
            System.out.println("-> Nueva fila insertada.");

            // rs.moveToCurrentRow(); // Opcional: vuelve el cursor a la fila actual antes de la inserción
        }
    }

    //Actividad 1 tema 4
    public static void actualizarCliente(Connection conn, List<String[]> datosFinales) throws SQLException {
        String sqlDelete = "DELETE FROM CLIENTES";
        String sqlInsert = "INSERT INTO CLIENTES (DNI, APELLIDOS, CP) VALUES (?, ?, ?)";

        try (PreparedStatement pstmDelete = conn.prepareStatement(sqlDelete);
             PreparedStatement pstmInsert = conn.prepareStatement(sqlInsert)) {

            int filasEliminadas = pstmDelete.executeUpdate();
            System.out.println("Se eliminaron " + filasEliminadas + " registros obsoletos.");

            for (String[] cliente : datosFinales) {
                String dni = cliente[0];
                String apellidos = cliente[1];
                String cp = cliente[2];

                System.out.println("Procesando cliente: " + dni);

                pstmInsert.setString(1, dni);
                pstmInsert.setString(2, apellidos);

                if (cp == null || cp.isEmpty()) {
                    pstmInsert.setNull(3, Types.CHAR);
                } else {
                    pstmInsert.setString(3, cp);
                }

                pstmInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserta una lista de clientes en la base de datos utilizando un lote (batch).
     * Este método es mucho más eficiente que hacer un INSERT por cada cliente.
     *
     * @param conn La conexión activa a la base de datos. Se asume que la gestión
     * de la transacción (commit/rollback) se hace fuera de este método.
     * @param clientes La lista de objetos Cliente a insertar.
     * @return Un array de enteros con los resultados de la ejecución del lote.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public static int[] insertarClientesEnLote(Connection conn, List<Cliente> clientes) throws SQLException {
        String sql = "INSERT INTO CLIENTES(DNI, APELLIDOS, CP) VALUES (?, ?, ?)";

        // Usamos try-with-resources para asegurar que el PreparedStatement se cierre.
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Verificamos si la lista de clientes está vacía para no hacer trabajo innecesario.
            if (clientes == null || clientes.isEmpty()) {
                System.out.println("La lista de clientes está vacía, no se insertará nada.");
                return new int[0];
            }

            // Recorremos la lista de objetos Cliente. Este código es mucho más limpio y seguro.
            for (Cliente cliente : clientes) {
                // Usamos los getters del objeto, evitando errores por índice (ej: datosClientes[nCli][i]).
                stmt.setString(1, cliente.getDni());
                stmt.setString(2, cliente.getApellidos());
                stmt.setInt(3, cliente.getCodigoPostal());

                // Agregamos la sentencia configurada al lote.
                stmt.addBatch();
            }

            // Ejecutamos todas las sentencias del lote en una sola llamada a la base de datos.
            System.out.println("Ejecutando lote de inserción...");
            return stmt.executeBatch();
        }
    }

    //Actividad 6 Tema 4 método de inserción de compañías.
    public void insertarCompaniesBatchConTransaccion(Connection conn, List<Company> companies) {
        String sql = "INSERT INTO COMPANIES (CIF, NOMBRE, SECTOR) VALUES (?, ?, ?)";

        try {
            // 1. INICIAR LA TRANSACCIÓN
            // Desactivamos el modo auto-commit para controlar la transacción manualmente.
            conn.setAutoCommit(false);

            // Usamos try-with-resources para el PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Company company : companies) {
                    pstmt.setString(1, company.getCif());
                    pstmt.setString(2, company.getNombre());
                    pstmt.setString(3, company.getSector());
                    pstmt.addBatch();
                }

                // 2. EJECUTAR EL LOTE
                System.out.println("Ejecutando el lote de inserciones...");
                pstmt.executeBatch();

                // 3. CONFIRMAR LA TRANSACCIÓN
                // Si executeBatch() no lanzó una excepción, todo fue bien. Hacemos permanentes los cambios.
                conn.commit();
                System.out.println("¡Éxito! La transacción ha sido confirmada (commit).");
            }
        } catch (SQLException e) {
            // 4. MANEJAR EL ERROR Y HACER ROLLBACK
            System.err.println("Error durante la inserción por lotes. Iniciando rollback...");
            try {
                if (conn != null) {
                    // Revertimos todos los cambios hechos desde el setAutoCommit(false)
                    conn.rollback();
                    System.out.println("El rollback se ha completado con éxito.");
                }
            } catch (SQLException ex) {
                System.err.println("Error crítico al intentar hacer rollback.");
                ex.printStackTrace();
            }
            // También es útil imprimir el error original que causó el fallo
            e.printStackTrace();
        } finally {
            // 5. RESTAURAR EL MODO ORIGINAL
            // Es una buena práctica devolver la conexión a su estado original.
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void queryDatabase(Connection connection, String nombreTabla) throws SQLException{
        try(connection) {
            if (connection == null) {
                throw new Exception("Error al obtener la conexión a la base de datos.");
            }

            System.out.println("Hemos obtenido la conexión a la base de datos");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nombreTabla);

            System.out.println("Conexión establecida. Navegando por la tabla '" + nombreTabla + "'.");
            System.out.println("Comandos: 'k' (siguiente), 'd', <número> (ir a fila), '.' (salir)");
            System.out.println("---------------------");

            if (rs.first()) {
                NavegadorTabla nv = new NavegadorTabla();
                nv.mostrarFilaActual(rs);
                nv.navegacionInteractiva(rs);
            } else {
                System.out.println("La tabla '" + nombreTabla + "' está vacía");
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            System.err.println("Ha ocurrido un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Programa finalizado. Recursos liberados");
    }
}