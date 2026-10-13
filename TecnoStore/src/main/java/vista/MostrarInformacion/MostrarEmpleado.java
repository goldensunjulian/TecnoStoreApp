package vista.MostrarInformacion;

import java.util.List;
import modelo.entidades.Empleado;

public class MostrarEmpleado {

    public void lista(List<Empleado> empleados) {
        for (Empleado e : empleados) {
            System.out.println(e);
        }
    }
}
