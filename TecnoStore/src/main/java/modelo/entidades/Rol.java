package modelo.entidades;
public class Rol {
    private Long id;
    private String rol;
    private Long privilegios;

    public Rol(Long id, String rol, Long privilegios) {
        this.id = id;
        this.rol = rol;
        this.privilegios = privilegios;
    }

    public Rol(String rol, Long privilegios) {
        this.rol = rol;
        this.privilegios = privilegios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(Long privilegios) {
        this.privilegios = privilegios;
    }
    
        @Override
    public String toString() {
        return """
               Id:          %s
               Rol:         %s
               Privilegios: %s
               """.formatted(id, rol, privilegios);
    }
}
