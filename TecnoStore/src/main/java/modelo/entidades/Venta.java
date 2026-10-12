package modelo.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venta {
    private Long id;
    private Cliente cliente;
    private Empleado empleado;
    private Double total;
    private LocalDateTime fecha;
    private final List<DetalleVenta> detalles = new ArrayList<>();   // para enviar varios detalles venta al confirmar 

    // leer weee
    public Venta(Long id, Cliente cliente, Empleado empleado, Double total, LocalDateTime fecha) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
        this.fecha = fecha;
    }

    // ya savemos XD
    public Venta(Cliente cliente, Empleado empleado) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = 0.0;
    }

    public Venta(Long id) {
        this.id = id;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalle.setVenta(this);
        detalles.add(detalle);
    }

    public List<DetalleVenta> getDetalles(){
        return Collections.unmodifiableList(detalles);
    }

    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    public Cliente getCliente(){
        return cliente;
    }
    
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Empleado getEmpleado(){
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado){
        this.empleado = empleado;
    }

    public Double getTotal(){
        return total;
    }
    
    public void setTotal(Double total){
        this.total = total;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }
}