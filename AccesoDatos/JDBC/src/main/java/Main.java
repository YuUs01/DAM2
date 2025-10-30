import dao.Dao;
import dao.DatabaseConnector;
import pojos.Cliente;
import print.ImprimirResultados;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static String CATALOGO = "hr_database";
    public static String WORLD = "world";
    public static String NOMBRE_TABLA = "CLIENTES";
    public static String T_FACTURAS = "FACTURAS";
    public static String T_LINEAS_FACTURA = "LINEAS_FACTURA";
    public static String INSERT_CLIENTES = "INSERT INTO CLIENTES(DNI,APELLIDOS,CP) VALUES "
            + "('78901234X','NADALES','44126'),"
            + "('89012345E','HOJAS', null),"
            + "('56789012B','SAMPER','29730'),"
            + "('09876543K','LAMIQUIZ', null);";


    public static void main(String[] args) {
        // Ejemplo de uso
        try {
            DatabaseConnector connector = new DatabaseConnector();
            Connection connection = connector.connection;

            if (connection == null) {
                throw new Exception("Error al obtener la conexión a la base de datos.");
            }

            System.out.println("Hemos obtenido la conexión a la base de datos");
            Dao dao = new Dao(connection);
            ImprimirResultados print = new ImprimirResultados();
            /**
             * Ejecutamos una sentencia DDL para crear una tabla
             */
            //dao.crearTablaClientesSiNoExiste();
             //Imprimimos los resultados
            //print.imprimirTablas(connection, CATALOGO);
//
//            // Insertamos registros en la tabla clientes
//            dao.insertarDatosConStatement(connection, INSERT_CLIENTES);
//            // Sacamos por consola los registros insertados
//            print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
//

            List<String[]> datosFinales = Arrays.asList(
                    new String[]{"78901234X", "Youssef", "44126"},
                    new String[]{"89012345E", "Youssef", null},
                    new String[]{"56789012B", "Youssef", "29730"}
            );

            dao.actualizarCliente(connection, datosFinales);
            print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
            // --- Datos de los 5 nuevos clientes a insertar ---
//            List<Cliente> nuevosClientes = Arrays.asList(
//                    new Cliente("12345678A", "Pérez Gómez", 28001),
//                    new Cliente("23456789B", "López Martín", 41002),
//                    new Cliente("34567890C", "Sánchez Ruiz", 46003),
//                    new Cliente("45678901D", "Fernández Díaz", 98004),
//                    new Cliente("56789012E", "Moreno Jiménez", 50005)
//            );

           //dao.insertarClientes(connection, nuevosClientes);
////            print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
//            List<Cliente> nuevosClientes2 = Arrays.asList(
//                    new Cliente("15345678A", "Ana Gómez", 28001),
//                    new Cliente("26456789B", "Jose Martín", 41002),
//                    new Cliente("37567890C", "Ramon Ruiz", 46003),
//                    new Cliente("48678901D", "Lucia Díaz", 98004),
//                    new Cliente("59789012E", "Amalia Jiménez", 50005)
//            );
////            dao.insertarClientesBatchConTransaccion(connection, nuevosClientes2);
                print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
//
//            // Preparamos los datos para las nuevas facturas
//            List<String> dnis = Arrays.asList(
//                    "78901234X",
//                    "09876543K",
//                    "15345678A",
//                    "INVALIDO", // DNI que podría causar un error para probar el rollback
//                    "59789012E"
//            );
//
//            List<LineaFactura> lineas = Arrays.asList(
//                    new LineaFactura("TORNILLOS", 10),
//                    new LineaFactura("TUERCAS", 50),
//                    new LineaFactura("ARANDELAS", 100),
//                    new LineaFactura("TACOS", 150)
//            );
//
//            // Llamamos a nuestro método para procesar el lote de facturas
////            Map<String, Integer> resultados = dao.crearFacturas(connection, dnis, lineas);
////
////            System.out.println("\n--- RESUMEN DEL PROCESO ---");
////            System.out.println("Facturas creadas exitosamente: " + resultados.size() + " de " + dnis.size());
////            resultados.forEach((dni, numFactura) ->
////                    System.out.println("  - DNI: " + dni + " -> Factura Nº: " + numFactura)
////            );
////            print.imprimirRegistros(connection, CATALOGO, T_FACTURAS);
////            print.imprimirRegistros(connection, CATALOGO, T_LINEAS_FACTURA);
//
//            // La lógica de negocio ahora es una simple llamada a un método.
////            String dniBusqueda = "78901234X";
////            ResultadoListado resultado = dao.llamarListadoClientes(connection, dniBusqueda);
////
////            // La responsabilidad de mostrar los datos se queda en el main.
////            System.out.println("=> Valor del parámetro INOUT devuelto: " + resultado.getContadorInOut());
////            System.out.println("Clientes encontrados:");
////
////            int nCli = 0;
////            for (Cliente cliente : resultado.getClientes()) {
////                System.out.println(" [" + (++nCli) + "] " + cliente.toString());
////            }
//
////            dao.obtenerYMostrarApellidosAlternativo("78901234X", connection);
//
////// == INICIO DE LA TRANSACCIÓN ==
////            // La responsabilidad de la transacción se queda en el método principal.
////            connection.setAutoCommit(false);
////
////            // Preparamos los datos para la operación
////            String nuevoCp = "02568";
////            ClienteNuevo nuevoCliente = new ClienteNuevo("24862486S", "ZURITA", "33983");
////
////            System.out.println("Iniciando operación de modificación de clientes...");
////            // Llamamos a nuestro método de lógica de negocio
////            dao.modificarClientesConResultSet(connection, nuevoCp, nuevoCliente);
////
////            // Si el método termina sin lanzar una excepción, confirmamos la transacción.
////            connection.commit();
////            System.out.println("\nTransacción confirmada (COMMIT) con éxito. ✅");
////
////            print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
//
//            // Los datos ahora son una lista de objetos, mucho más legible y segura.
//            List<Cliente> clientesNuevos = Arrays.asList(
//                    new Cliente("13579135G", "Maria Torres", 32564),
//                    new Cliente("24680246G", "Pedro Marin", 25865),
//                    new Cliente("96307418R", "Blanca Fernandez", 19273)
//            );
//            // == INICIO DE LA TRANSACCIÓN ==
//            // La gestión de la transacción (commit/rollback) se queda en el método 'main'.
//            connection.setAutoCommit(false);
//
//            try {
//                // Llamamos a nuestro método reutilizable.
//                int[] resultados = dao.insertarClientesEnLote(connection, clientesNuevos);
//
//                // == FIN DE LA TRANSACCIÓN (ÉXITO) ==
//                connection.commit();
//
//                System.out.println("Transacción confirmada (COMMIT) con éxito. ✅");
//                System.out.println("Resultados del lote: " + Arrays.toString(resultados));
//                // Un resultado de 1 (o Statement.SUCCESS_NO_INFO) por cada inserción indica éxito.
//                Arrays.stream(resultados).sequential().forEach(r -> System.out.println("Resultado: " + r));
//
//            } catch (SQLException e) {
//                System.err.println("Error de SQL, se desharán los cambios (ROLLBACK).");
//                e.printStackTrace(System.err);
//                // Si algo falla, hacemos rollback
//                connection.rollback();
//                System.err.println("Rollback realizado.");
//            }
//            connection.setAutoCommit(true);
//
//            print.imprimirRegistros(connection, CATALOGO, NOMBRE_TABLA);
//
//            // Cerramos la conexion
            connection.close();
        } catch (Exception e) {
            System.err.println("Fallo al intentar obtener la conexion a la base de datos.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
