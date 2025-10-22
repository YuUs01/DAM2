package Actividad3T3;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private String nombreTienda;
    private List<Producto> productos;

    public Inventario() {
        // Jackson necesita constructor vacío
        this.productos = new ArrayList<>();
    }

    public Inventario(String nombreTienda, List<Producto> productos) {
        this.nombreTienda = nombreTienda;
        this.productos = productos;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventario: " + nombreTienda + "\n");
        for (Producto p : productos) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
