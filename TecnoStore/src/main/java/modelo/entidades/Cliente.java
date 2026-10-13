package modelo.entidades;

public class Cliente extends Persona {


    // leer
    public Cliente(Long id, String nombre,
                   String identificador, String correo, String telefono) {
        super(id, nombre, identificador, correo, telefono);

    }

    // xd
    public Cliente(String nombre, String identificador, String correo, String telefono) {
        super(nombre, identificador, correo, telefono);
    }

    
        @Override
    public String toString() {
        return """
               Id:              %s
               Nombre:          %s
               Identificador:   %s
               Correo:          %s
               Telefono:        %s
               """.formatted(getId(), getNombre(), getIdentificador(), getCorreo(), getTelefono());
    }
}