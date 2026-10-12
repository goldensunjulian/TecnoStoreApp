package util;

import dao.database.DetallesVentaDao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Venta;

public class ArchivoUtils {

    private static final String RUTA = "reporte_ventas.txt";
    private static final DetallesVentaDao detalleDao = new DetallesVentaDao();

    // false en FileWriter sobreescribe el archivo si existe, lo crea si no
    public static void generarReporte(List<Venta> ventas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, false))) {

            bw.write("REPORTE DE VENTAS - TECNOSTORE");
            bw.newLine();
            bw.newLine();

            for (Venta v : ventas) {
                bw.write("Venta:    " + v.getId());
                bw.newLine();
                bw.write("Cliente:  " + v.getCliente().getNombre());
                bw.newLine();
                bw.write("Empleado: " + v.getEmpleado().getNombre());
                bw.newLine();
                bw.write("Fecha:    " + v.getFecha());
                bw.newLine();
                bw.write("Total:    $" + String.format("%.2f", v.getTotal()));
                bw.newLine();

                List<DetalleVenta> detalles = detalleDao.listarPorVenta(v.getId());
                for (DetalleVenta d : detalles) {
                    bw.write("  - " + d.getCelular().getMarca().getNombre()
                            + " " + d.getCelular().getModelo()
                            + " x" + d.getCantidad()
                            + " $" + String.format("%.2f", d.getSubtotal()));
                    bw.newLine();
                }

                bw.newLine();
            }

            bw.write("Total ventas: " + ventas.size());
            bw.newLine();

        } catch (Exception e) {
            System.out.println("Error al generar reporte: " + e.getMessage());
        }
    }
}
