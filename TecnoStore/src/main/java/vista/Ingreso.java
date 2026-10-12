package vista;

import modelo.entidades.Celular;
import modelo.entidades.Cliente;
import modelo.entidades.Empleado;
import modelo.entidades.Gama;
import modelo.entidades.Marca;
import modelo.entidades.Persona;
import modelo.entidades.Rol;
import modelo.entidades.SistemaOperativo;
import modelo.validaciones.validarCorreo;
import modelo.validaciones.validarClave;
import modelo.validaciones.validarDecimal;
import modelo.validaciones.validarRango;
import modelo.validaciones.validarTexto;
import modelo.validaciones.validarEntero;
import modelo.validaciones.validarUsuario;

public class Ingreso {

    private final validarTexto valTexto       = new validarTexto();
    private final validarEntero valEntero     = new validarEntero();
    private final validarDecimal valDecimal   = new validarDecimal();
    private final validarCorreo valCorreo     = new validarCorreo();
    private final validarClave valClave       = new validarClave();
    private final validarUsuario valUsuario   = new validarUsuario();
    private final validarRango valRango       = new validarRango();

    public int menuGeneral() {
        return valRango.validacion("""
                1. Vendedor
                2. Administrador Bodega
                3. Administrador
                4. Salir
                Opcion: """, 1, 4);
    }

    public int menuVendedor() {
        return valRango.validacion("""
                1. Nueva venta
                2. Salir
                Opcion: """, 1, 2);
    }

    public int menuOpcionesCliente() {
        return valRango.validacion("""
                1. Iniciar compra
                2. Actualizar datos
                Opcion: """, 1, 2);
    }

    public int menuCarrito() {
        return valRango.validacion("""
                1. Agregar celular
                2. Quitar item
                3. Confirmar venta
                4. Cancelar venta
                Opcion: """, 1, 4);
    }

    public int menuBodega() {
        return valRango.validacion("""
                1. Celulares
                2. Marcas
                3. Gamas
                4. Sistemas Operativos
                5. Salir
                Opcion: """, 1, 5);
    }

    public int menuCrud() {
        return valRango.validacion("""
                1. Agregar
                2. Listar
                3. Actualizar
                4. Eliminar
                5. Salir
                Opcion: """, 1, 5);
    }

    public int menuAdmin() {
        return valRango.validacion("""
                1. Empleados
                2. Ver auditoria
                3. Generar reporte de ventas
                4. Salir
                Opcion: """, 1, 4);
    }

    public int menuEmpleados() {
        return valRango.validacion("""
                1. Agregar
                2. Listar
                3. Actualizar
                4. Eliminar
                5. Salir
                Opcion: """, 1, 5);
    }

    // --- persona ---

    public String pedirIdentificacion() {
        return valTexto.validacion("Identificacion: ");
    }

    public Persona ingresarPersona(String identificacion) {
        String nombre   = valTexto.validacion("Nombre: ");
        String correo   = valCorreo.validacion("Correo: ");
        String telefono = valTexto.validacion("Telefono: ");
        return new Persona(nombre, identificacion, correo, telefono);
    }

    public Persona actualizarPersona(Persona persona) {
        System.out.println("Dejar vacio y Enter para no cambiar el campo");
        System.out.print("Nuevo nombre (" + persona.getNombre() + "): ");
        String nombre = new java.util.Scanner(System.in).nextLine().trim();
        System.out.print("Nuevo correo (" + persona.getCorreo() + "): ");
        String correo = new java.util.Scanner(System.in).nextLine().trim();
        System.out.print("Nuevo telefono (" + persona.getTelefono() + "): ");
        String telefono = new java.util.Scanner(System.in).nextLine().trim();

        if (!nombre.isEmpty())   persona.setNombre(nombre);
        if (!correo.isEmpty())   persona.setCorreo(correo);
        if (!telefono.isEmpty()) persona.setTelefono(telefono);
        return persona;
    }

    // --- empleado ---

    public Empleado ingresarEmpleado(Persona persona, Rol rol) {
        String username = valUsuario.validacion("Username: ");
        String clave    = valClave.validacion("Contrasena: ");
        return new Empleado(rol, username, clave,
                persona.getId(),
                persona.getNombre(), persona.getIdentificador(),
                persona.getCorreo(), persona.getTelefono());
    }

    public Long pedirIdEmpleado() {
        return valEntero.validacion("ID del empleado: ");
    }

    // --- celular ---

    public Celular ingresarCelular(Marca marca, SistemaOperativo so, Gama gama) {
        String modelo   = valTexto.validacion("Modelo: ");
        Double precio   = valDecimal.validacion("Precio: ");
        Long stock      = valEntero.validacion("Stock: ");
        return new Celular(marca, modelo, precio, stock, so, gama);
    }

    public Long pedirIdCelular() {
        return valEntero.validacion("ID del celular: ");
    }

    public Long pedirCantidad() {
        return valEntero.validacion("Cantidad: ");
    }

    public int pedirItemAQuitar(int total) {
        return valRango.validacion("Numero del item a quitar: ", 1, total);
    }

    // --- catalogos (marca, gama, so) ---

    public Marca ingresarMarca() {
        return new Marca(valTexto.validacion("Nombre de la marca: "));
    }

    public Gama ingresarGama() {
        return new Gama(valTexto.validacion("Nombre de la gama: "));
    }

    public SistemaOperativo ingresarSO() {
        return new SistemaOperativo(valTexto.validacion("Nombre del sistema operativo: "));
    }

    public Long pedirId() {
        return valEntero.validacion("ID: ");
    }
}
