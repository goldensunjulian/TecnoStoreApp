package vista;

import modelo.entidades.Empleado;
import modelo.validaciones.validarClave;
import modelo.validaciones.validarUsuario;

public class VistaLogin {

    private final validarUsuario valUsuario = new validarUsuario();
    private final validarClave valClave = new validarClave();

    public String pedirUsername() {
        return valUsuario.validacion("Usuario: ");
    }

    public String pedirPassword() {
        return valClave.validacion("Contraseña: ");
    }

    public void mostrarErrorCredenciales() {
        System.out.println("Usuario o contraseña incorrectos. Intente de nuevo.");
    }

    public void mostrarBienvenida(Empleado empleado) {
        System.out.println("Bienvenido, " + empleado.getNombre() + " (" + empleado.getRol().getRol() + ")");
    }
}
