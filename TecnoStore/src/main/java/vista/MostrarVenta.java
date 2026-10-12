package vista;

import java.util.List;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Venta;

public class MostrarVenta {

    public void lista(List<Venta> ventas) {
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }

    // muestra una venta con su lista de detalles
    public void detalle(Venta venta, List<DetalleVenta> detalles) {
        System.out.println(venta);
        System.out.println("--- Detalle ---");
        for (DetalleVenta d : detalles) {
            System.out.println(d);
        }
    }
}
