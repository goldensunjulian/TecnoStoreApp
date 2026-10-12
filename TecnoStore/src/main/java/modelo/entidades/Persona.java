package modelo.entidades;
public class Persona {
    Long id;
    String nombre;
    String identificador;
    String correo;
    String telefono;

    public Persona(Long id, String nombre, String identificador, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.identificador = identificador;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Persona(String nombre, String identificador, String correo, String telefono) {
        this.nombre = nombre;
        this.identificador = identificador;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
        @Override
    public String toString() {
        return """
               Id:              %s
               Nombre:          %s
               Identificador:   %s
               Correo:          %s
               Telefono:        %s
               """.formatted(id, nombre, identificador, correo, telefono);
    }
    
}
