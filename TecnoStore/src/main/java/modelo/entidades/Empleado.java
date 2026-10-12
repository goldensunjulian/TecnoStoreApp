package modelo.entidades;

public class Empleado extends Persona {


    private Rol rol;
    private String username;
    private String contraseña;

    // leer XD
    public Empleado(Rol rol, String username, String contraseña, Long id,
        String nombre, String identificador, String correo, String telefono) {
        super(id, nombre, identificador, correo, telefono);
        this.rol = rol;
        this.username = username;
        this.contraseña = contraseña;
    }

    // *aplausos*
    public Empleado(Rol rol, String username, String contraseña,
                    String nombre, String identificador, String correo, String telefono) {
        super(nombre, identificador, correo, telefono);
        this.rol = rol;
        this.username = username;
        this.contraseña = contraseña;
    }


    public Empleado(Rol rol, Long id, String nombre, String identificador, String correo, String telefono) {
        super(id, nombre, identificador, correo, telefono);
        this.rol = rol;
    }

    public Rol getRol(){
        return rol;
    }
    
    public void setRol(Rol rol){
        this.rol = rol;
    }

    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }

    public String getContraseña(){
        return contraseña;
    }
    
    public void setContraseña(String contraseña){
        this.contraseña = contraseña;
    }
}