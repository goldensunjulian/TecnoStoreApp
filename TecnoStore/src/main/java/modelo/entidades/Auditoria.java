package modelo.entidades;

import java.time.LocalDateTime;

public class Auditoria {
    Long id;
    String accion;
    LocalDateTime fecha;
    Empleado empleado;

    public Auditoria(Long id, String accion, LocalDateTime fecha, Empleado empleado) {
        this.id = id;
        this.accion = accion;
        this.fecha = fecha;
        this.empleado = empleado;
    }

    public Auditoria(String accion, Empleado empleado) {
        this.accion = accion;
        this.empleado = empleado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
}
