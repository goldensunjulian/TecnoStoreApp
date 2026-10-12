package controlador;

import dao.database.EmpleadoDao;
import modelo.entidades.Empleado;
import vista.VistaLogin;

public class GestionGeneral {

    private final VistaLogin vistaLogin     = new VistaLogin();
    private final EmpleadoDao empleadoDao   = new EmpleadoDao();

    public void menuGeneral() {
        Empleado empleado = iniciarSesion();

        // redirigir segun privilegios del rol
        switch (empleado.getRol().getPrivilegios().intValue()) {
            case 1 -> new GestionVendedor(empleado).menu();
            case 2 -> new GestionBodega(empleado).menu();
            case 3 -> new GestionAdmin(empleado).menu();
            default -> System.out.println("Rol no reconocido.");
        }
    }

    private Empleado iniciarSesion() {
        Empleado empleado = null;

        do {
            String username = vistaLogin.pedirUsername();
            String password = vistaLogin.pedirPassword();

            empleado = empleadoDao.buscarPorUsername(username);

            if (empleado == null || !empleado.getContraseña().equals(password)) {
                vistaLogin.mostrarErrorCredenciales();
                empleado = null;
            }

        } while (empleado == null);

        vistaLogin.mostrarBienvenida(empleado);
        return empleado;
    }
}
