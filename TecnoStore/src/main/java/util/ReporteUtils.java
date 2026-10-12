package util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.entidades.Celular;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Venta;

public class ReporteUtils {

    // celulares con stock menor a 5
    public static List<Celular> stockBajo(List<Celular> celulares) {
        return celulares.stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
    }

    // top 3 celulares mas vendidos por cantidad total vendida
    public static List<Map.Entry<String, Long>> top3MasVendidos(List<DetalleVenta> detalles) {
        return detalles.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getMarca().getNombre() + " " + d.getCelular().getModelo(),
                        Collectors.summingLong(DetalleVenta::getCantidad)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());
    }

    // ventas totales agrupadas por mes (formato: 2026-10)
    public static Map<String, Double> ventasPorMes(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().getYear() + "-" + String.format("%02d", v.getFecha().getMonthValue()),
                        Collectors.summingDouble(Venta::getTotal)
                ));
    }
}
