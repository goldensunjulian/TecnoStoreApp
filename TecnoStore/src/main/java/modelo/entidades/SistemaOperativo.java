package modelo.entidades;
public class SistemaOperativo {
    private Long id;
    private String nombre;

    public SistemaOperativo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public SistemaOperativo(String nombre) {
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
