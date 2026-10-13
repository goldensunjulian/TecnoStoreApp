package controlador;

import dao.database.AuditoriaDao;
import dao.database.CelularDao;
import dao.database.ClienteDao;
import dao.database.DetallesVentaDao;
import dao.database.PersonaDao;
import dao.database.VentaDao;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Auditoria;
import modelo.entidades.Celular;
import modelo.entidades.Cliente;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Empleado;
import modelo.entidades.Persona;
import modelo.entidades.Venta;
import modelo.excepciones.StockInsuficienteException;
import vista.Ingreso;
import vista.MostrarCelular;

public class GestionVendedor {

    private final Ingreso ingreso           = new Ingreso();
    private final MostrarCelular mostrarCel = new MostrarCelular();
    private final PersonaDao personaDao     = new PersonaDao();
    private final ClienteDao clienteDao     = new ClienteDao();
    private final CelularDao celularDao     = new CelularDao();
    private final VentaDao ventaDao         = new VentaDao();
    private final DetallesVentaDao detalleDao = new DetallesVentaDao();
    private final AuditoriaDao auditoriaDao = new AuditoriaDao();

    private final Empleado empleado;

    public GestionVendedor(Empleado empleado) {
        this.empleado = empleado;
    }

    public void menu() {
        int op;
        do {
            op = ingreso.menuVendedor();
            switch (op) {
                case 1 -> nuevaVenta();
                case 2 -> System.out.println("Cerrando sesion...");
            }
        } while (op != 2);
    }

    private void nuevaVenta() {
        Cliente cliente = buscarORegistrarCliente();
        if (cliente == null) return;
    }

    private void iniciarCompra(Cliente cliente) {
        int opCliente = ingreso.menuOpcionesCliente();
        if (opCliente == 2) {
            actualizarDatosCliente(cliente);
        }
        procesarVenta(cliente);
    }

    private Cliente buscarORegistrarCliente() {
        String identificacion = ingreso.pedirIdentificacion();
        Persona persona = personaDao.buscarPorIdentificacion(identificacion);

        if (persona == null) {
            // persona nueva — pedir datos y crear persona + cliente
            System.out.println("Persona no encontrada. Ingrese los datos:");
            persona = ingreso.ingresarPersona(identificacion);
            personaDao.agregar(persona);
            clienteDao.agregar(new Cliente(persona.getId(), persona.getNombre(),
                    persona.getIdentificador(), persona.getCorreo(), persona.getTelefono()));
            auditoriaDao.agregar(new Auditoria("Vendedor registro nuevo cliente: " + persona.getNombre(), empleado));
            Cliente nuevoCliente = clienteDao.buscarPorId(persona.getId());
            iniciarCompra(nuevoCliente);
        } else {
            // persona existe — verificar si ya es cliente
            Cliente clienteExistente = clienteDao.buscarPorId(persona.getId());
            if (clienteExistente == null) {
                // es empleado comprando — registrar como cliente
                clienteDao.agregar(new Cliente(persona.getId(), persona.getNombre(),
                        persona.getIdentificador(), persona.getCorreo(), persona.getTelefono()));
            }
            iniciarCompra(clienteDao.buscarPorId(persona.getId()));
        }

        return null;
    }

    private void actualizarDatosCliente(Cliente cliente) {
        Persona actualizada = ingreso.actualizarPersona(cliente);
        personaDao.actualizar(actualizada);
        auditoriaDao.agregar(new Auditoria("Vendedor actualizo datos del cliente: " + cliente.getNombre(), empleado));
    }

    private void procesarVenta(Cliente cliente) {
        Venta venta = new Venta(cliente, empleado);
        List<DetalleVenta> carrito = new ArrayList<>();
        boolean continuar = true;

        // agregar el primer celular antes de entrar al loop del carrito
        agregarAlCarrito(carrito);

        while (continuar) {
            // mostrar carrito actual
            System.out.println("--- Carrito ---");
            for (int i = 0; i < carrito.size(); i++) {
                System.out.println((i + 1) + ". " + carrito.get(i));
            }

            int opCarrito = ingreso.menuCarrito();
            switch (opCarrito) {
                case 1 -> agregarAlCarrito(carrito);
                case 2 -> {
                    if (!carrito.isEmpty()) {
                        int item = ingreso.pedirItemAQuitar(carrito.size());
                        carrito.remove(item - 1);
                        System.out.println("Item eliminado.");
                    } else {
                        System.out.println("El carrito esta vacio.");
                    }
                }
                case 3 -> {
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito esta vacio, agregue al menos un producto.");
                    } else {
                        confirmarVenta(venta, carrito);
                        continuar = false;
                    }
                }
                case 4 -> {
                    System.out.println("Venta cancelada.");
                    continuar = false;
                }
            }
        }
    }

    private void agregarAlCarrito(List<DetalleVenta> carrito) {
        List<Celular> catalogo = celularDao.listarTodo();
        mostrarCel.lista(catalogo);

        Long idCelular = ingreso.pedirIdCelular();
        Celular celular = celularDao.buscarPorId(idCelular);

        if (celular == null) {
            System.out.println("Celular no encontrado.");
            return;
        }

        try {
            if (celular.getStock() == 0) {
                throw new StockInsuficienteException(
                        celular.getMarca().getNombre() + " " + celular.getModelo(),
                        celular.getStock(), 1L);
            }
            Long cantidad = ingreso.pedirCantidad();
            if (cantidad > celular.getStock()) {
                throw new StockInsuficienteException(
                        celular.getMarca().getNombre() + " " + celular.getModelo(),
                        celular.getStock(), cantidad);
            }
            carrito.add(new DetalleVenta(celular, cantidad, celular.getPrecio()));
            System.out.println("Agregado al carrito.");
        } catch (StockInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void confirmarVenta(Venta venta, List<DetalleVenta> carrito) {
        // calcular total con IVA 19%
        double subtotalGeneral = carrito.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
        double totalConIva = subtotalGeneral * 1.19;
        venta.setTotal(totalConIva);

        // insertar venta y capturar id generado
        ventaDao.agregar(venta);

        // insertar cada detalle y actualizar stock
        for (DetalleVenta detalle : carrito) {
            detalle.setVenta(venta);
            detalleDao.agregar(new DetalleVenta(venta, detalle.getCelular(),
                    detalle.getCantidad(), detalle.getPrecioUnitario()));
            long nuevoStock = detalle.getCelular().getStock() - detalle.getCantidad();
            celularDao.actualizarStock(detalle.getCelular().getId(), nuevoStock);
        }

        auditoriaDao.agregar(new Auditoria("Vendedor realizo venta id: " + venta.getId() +
                " cliente: " + venta.getCliente().getNombre(), empleado));

        System.out.println("Venta confirmada. Total con IVA: $" + String.format("%.2f", totalConIva));
    }
}
