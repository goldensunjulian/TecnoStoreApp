package modelo.entidades;
public class Precio {
    Long id;
    Double precio;

    public Precio(Long id, Double precio) {
        this.id = id;
        this.precio = precio;
    }

    public Precio(Double precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
}
