package modelo.entidades;
public class Cliente extends Persona{
    String direccion;

    public Cliente(String direccion, Long id, String nombre, String identificador, String correo, String telefono) {
        super(id, nombre, identificador, correo, telefono);
        this.direccion = direccion;
    }

    public Cliente(String direccion, String nombre, String identificador, String correo, String telefono) {
        super(nombre, identificador, correo, telefono);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
