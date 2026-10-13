package modelo.entidades;
public class Marca {
    private Long id;
    private String nombre;

    public Marca(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
        @Override
    public String toString() {
        return """
               Id:      %s
               Nombre:  %s
               """.formatted(id, nombre);
    }
    
}
