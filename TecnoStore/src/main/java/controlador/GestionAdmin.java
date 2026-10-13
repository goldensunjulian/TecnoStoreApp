package controlador;

import dao.database.AuditoriaDao;
import dao.database.ClienteDao;
import dao.database.EmpleadoDao;
import dao.database.PersonaDao;
import dao.database.RolDao;
import dao.database.VentaDao;
import dao.database.CelularDao;
import dao.database.DetallesVentaDao;
import java.util.List;
import java.util.Map;
import modelo.entidades.Auditoria;
import modelo.entidades.Celular;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Empleado;
import modelo.entidades.Persona;
import modelo.entidades.Rol;
import modelo.entidades.Venta;
import util.ArchivoUtils;
import util.ReporteUtils;
import vista.Ingreso;
import vista.MostrarInformacion.MostrarEmpleado;
import vista.MostrarInformacion.MostrarVenta;

public class GestionAdmin {

    private final Ingreso ingreso = new Ingreso();
    private final MostrarEmpleado mostrarEmp = new MostrarEmpleado();
    private final MostrarVenta mostrarVenta = new MostrarVenta();
    private final EmpleadoDao empleadoDao = new EmpleadoDao();
    private final PersonaDao personaDao = new PersonaDao();
    private final ClienteDao clienteDao = new ClienteDao();
    private final RolDao rolDao = new RolDao();
    private final VentaDao ventaDao = new VentaDao();
    private final AuditoriaDao auditoriaDao = new AuditoriaDao();

    private final Empleado empleado;
    private final CelularDao celularDao = new CelularDao();
    private final DetallesVentaDao detalleDao = new DetallesVentaDao();

    public GestionAdmin(Empleado empleado) {
        this.empleado = empleado;
    }

    public void menu() {
        int op;
        do {
            op = ingreso.menuAdmin();
            switch (op) {
                case 1 -> menuEmpleados();
                case 2 -> verAuditoria();
                case 3 -> mostrarReportesConsola();
                case 4 -> generarReporte();
                case 5 -> System.out.println("Cerrando sesion...");
            }
        } while (op != 5);
    }

    // seccion para gestionar empleados 

    private void menuEmpleados() {
        int op;
        do {
            op = ingreso.menuEmpleados();
            switch (op) {
                case 1 -> agregarEmpleado();
                case 2 -> mostrarEmp.lista(empleadoDao.listarTodo());
                case 3 -> actualizarEmpleado();
                case 4 -> eliminarEmpleado();
                case 5 -> System.out.println("Volviendo...");
            }
        } while (op != 5);
    }

    private void agregarEmpleado() {
        String identificacion = ingreso.pedirIdentificacion();
        Persona persona = personaDao.buscarPorIdentificacion(identificacion);

        if (persona == null) {
            System.out.println("Persona no encontrada. Ingrese los datos:");
            persona = ingreso.ingresarPersona(identificacion);
            if (!personaDao.agregar(persona)) {
                System.out.println("No se pudo registrar la persona. El correo o identificacion ya existe.");
                return;
            }
        } else {
            System.out.println("Persona encontrada: " + persona.getNombre());
            // verificar que no sea ya un empleado
            if (empleadoDao.buscarPorId(persona.getId()) != null) {
                System.out.println("Esta persona ya es empleado.");
                return;
            }
        }

        // mostrar roles disponibles y escoger
        System.out.println("--- Roles ---");
        rolDao.listarTodo().forEach(System.out::println);
        Long idRol = ingreso.pedirId();
        Rol rol = rolDao.buscarPorId(idRol);
        if (rol == null) { System.out.println("Rol no encontrado."); return; }

        Empleado nuevoEmpleado = ingreso.ingresarEmpleado(persona, rol);
        empleadoDao.agregar(nuevoEmpleado);
        auditoriaDao.agregar(new Auditoria("Admin agrego empleado: " + persona.getNombre(), empleado));
        System.out.println("Empleado agregado.");
    }

    private void actualizarEmpleado() {
        mostrarEmp.lista(empleadoDao.listarTodo());
        Long id = ingreso.pedirIdEmpleado();
        Empleado emp = empleadoDao.buscarPorId(id);
        if (emp == null) { System.out.println("Empleado no encontrado."); return; }

        // actualizar datos de persona
        Persona actualizada = ingreso.actualizarPersona(emp);
        personaDao.actualizar(actualizada);

        // cambiar rol
        System.out.println("Rol actual: " + emp.getRol().getRol());
        System.out.println("--- Roles disponibles ---");
        rolDao.listarTodo().forEach(System.out::println);
        Long idRol = ingreso.pedirId();
        Rol rol = rolDao.buscarPorId(idRol);
        if (rol != null) emp.setRol(rol);

        empleadoDao.actualizar(emp);
        auditoriaDao.agregar(new Auditoria("Admin actualizo empleado id: " + id, empleado));
        System.out.println("Empleado actualizado.");
    }

    private void eliminarEmpleado() {
        mostrarEmp.lista(empleadoDao.listarTodo());
        Long id = ingreso.pedirIdEmpleado();

        // para no deletearte a ti mismo XDXD
        if (id.equals(empleado.getId())) {
            System.out.println("No puede eliminarse a si mismo.");
            return;
        }

        empleadoDao.eliminar(id);
        auditoriaDao.agregar(new Auditoria("Admin elimino empleado id: " + id, empleado));
        System.out.println("Empleado eliminado.");
    }

    // para ver auditoria nada mas

    private void verAuditoria() {
        List<Auditoria> auditoria = auditoriaDao.listarTodo();
        if (auditoria.isEmpty()) {
            System.out.println("No hay registros de auditoria.");
        } else {
            auditoria.forEach(System.out::println);
        }
        auditoriaDao.agregar(new Auditoria("Admin consulto el registro de auditoria", empleado));
    }

    // reportes en consola usando Stream API

    private void mostrarReportesConsola() {
        // stock bajo
        System.out.println("=== Celulares con stock bajo (menos de 5 unidades) ===");
        List<Celular> bajoStock = ReporteUtils.stockBajo(celularDao.listarTodo());
        if (bajoStock.isEmpty()) {
            System.out.println("Todos los celulares tienen stock suficiente.");
        } else {
            bajoStock.forEach(System.out::println);
        }

        // top mas comprados vendidos XDXD
        System.out.println("=== Top 3 celulares mas vendidos ===");
        List<DetalleVenta> detalles = detalleDao.listarTodo();
        if (detalles.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            List<Map.Entry<String, Long>> top3 = ReporteUtils.top3MasVendidos(detalles);
            int posicion = 1;
            for (Map.Entry<String, Long> entry : top3) {
                System.out.println(posicion++ + ". " + entry.getKey() + " - " + entry.getValue() + " unidades vendidas");
            }
        }

        //venta del mes
        System.out.println("=== Ventas totales por mes ===");
        List<Venta> ventas = ventaDao.listarTodo();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            Map<String, Double> porMes = ReporteUtils.ventasPorMes(ventas);
            porMes.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.println(e.getKey() + ": $" + String.format("%.2f", e.getValue())));
        }

        auditoriaDao.agregar(new Auditoria("Admin consulto reportes de analisis", empleado));
    }

    // cosa rancia del reporte

    private void generarReporte() {
        List<Venta> ventas = ventaDao.listarTodo();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        mostrarVenta.lista(ventas);
        // ArchivoUtils genera el .txt porque ya es muy tarde para hacer un .dat y quemar mi vida en algo que realmente el proyecto no pidio ya que claramente decia .txt XDXD
        util.ArchivoUtils.generarReporte(ventas);
        auditoriaDao.agregar(new Auditoria("Admin genero reporte de ventas", empleado));
        System.out.println("Reporte generado en reporte_ventas.txt");
    }
}
