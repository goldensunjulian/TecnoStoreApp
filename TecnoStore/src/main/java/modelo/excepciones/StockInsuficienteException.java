package modelo.excepciones;

/**
 * Excepcion de dominio lanzada cuando se intenta vender una cantidad
 * mayor al stock disponible de un celular.
 */
public class StockInsuficienteException extends Exception {

    private final long stockDisponible;
    private final long cantidadSolicitada;

    public StockInsuficienteException(String modelo, long stockDisponible, long cantidadSolicitada) {
        super("Stock insuficiente para '" + modelo + "'. "
                + "Disponible: " + stockDisponible
                + ", Solicitado: " + cantidadSolicitada);
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public long getStockDisponible() {
        return stockDisponible;
    }

    public long getCantidadSolicitada() {
        return cantidadSolicitada;
    }
}
