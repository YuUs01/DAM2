package Actividad3T3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainJackson {
    public static void main(String[] args) {
        List <Producto> productos = new ArrayList<>();
        productos.add(new Producto("P001", "Libro", 10.99, 5));
        productos.add(new Producto("P002", "Comic", 8.99, 10));
        productos.add(new Producto("P003", "Figura", 50.00, 4));

        Inventario inventario = new Inventario("Tienda de Comics", productos);

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);

        File fich = new File("inventario.json");

        //Serializacion del inventario de la tienda
        try {
            om.writeValue(fich, inventario);
            System.out.println("Inventario Guardado de manera correcta");
        }catch (Exception e){
            e.printStackTrace();
        }

        //Deserializar el archivo json a un objeto de java

        try {
            Inventario inventarioGuardado = om.readValue(fich, Inventario.class);
            for (Producto p : inventarioGuardado.getProductos()){
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}
