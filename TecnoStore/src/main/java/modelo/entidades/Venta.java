package modelo.entidades;

import java.time.LocalDateTime;

public class Venta {
    Long id;
    Cliente cliente;
    Empleado empleado;
    Double total;
    LocalDateTime fecha;

    public Venta(Long id, Cliente cliente, Empleado empleado, Double total, LocalDateTime fecha) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
        this.fecha = fecha;
    }

    public Venta(Cliente cliente, Empleado empleado, Double total) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}
