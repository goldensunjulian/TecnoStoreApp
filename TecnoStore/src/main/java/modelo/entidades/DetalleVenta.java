package modelo.entidades;
public class DetalleVenta {
    Long id;
    Venta venta;
    Celular celular;
    Long cantidad;
    Double precioUnitario;
    Double subtotal;

    //este es para recivir
    public DetalleVenta(Long id, Venta venta, Celular celular, Long cantidad, Double precioUnitario, Double subtotal) {
        this.id = id;
        this.venta = venta;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    //este es para enviar final a db
    public DetalleVenta(Venta venta, Celular celular, Long cantidad, Double precioUnitario) {
        this.venta = venta;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }
    //este es temporal antes de confirmar
    public DetalleVenta(Celular celular, Long cantidad, Double precioUnitario) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
}
