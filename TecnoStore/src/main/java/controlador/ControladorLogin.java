package controlador;

import dao.database.EmpleadoDao;
import modelo.entidades.Empleado;
import vista.VistaLogin;

public class ControladorLogin {

    private final VistaLogin vista = new VistaLogin();
    private final EmpleadoDao empleadoDao = new EmpleadoDao();

    public Empleado iniciarSesion() {
        Empleado empleado = null;

        do {
            String username = vista.pedirUsername();
            String password = vista.pedirPassword();

            empleado = empleadoDao.buscarPorUsername(username);

            // si no existe el username o la password no coincide, error
            if (empleado == null || !empleado.getContraseña().equals(password)) {
                vista.mostrarErrorCredenciales();
                empleado = null; // forzamos otra vuelta
            }

        } while (empleado == null);

        vista.mostrarBienvenida(empleado);
        return empleado; // el main usa el rol para redirigir al menu correcto
    }
}
