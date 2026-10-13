package controlador;

import modelo.entidades.Empleado;

public class GestionGeneral {

    public void menuGeneral() {
        // para ver y confirmar quien logea
        Empleado empleado = new ControladorLogin().iniciarSesion();

        // redirigir segun privilegios del rol
        switch (empleado.getRol().getPrivilegios().intValue()) {
            case 1 -> new GestionVendedor(empleado).menu();
            case 2 -> new GestionBodega(empleado).menu();
            case 3 -> new GestionAdmin(empleado).menu();
            default -> System.out.println("Rol no reconocido.");
        }
    }
}
