package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceAgregar;
import dao.interfaceDAO.InterfaceBuscarPorId;
import dao.interfaceDAO.InterfaceListar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Cliente;
import modelo.entidades.Empleado;
import modelo.entidades.Rol;
import modelo.entidades.Venta;

public class VentaDao implements InterfaceAgregar<Venta>, InterfaceBuscarPorId<Venta>, InterfaceListar<Venta> {

    @Override
    public Boolean agregar(Venta venta) {
        String sql = "insert into venta (cliente_fk, empleado_fk, total) values (?, ?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, venta.getCliente().getId());
            ps.setLong(2, venta.getEmpleado().getId());
            ps.setDouble(3, venta.getTotal());
            ps.executeUpdate();
            // capturamos el id generado y se lo seteamos a la venta
            // para usarlo en detalles_venta como venta_fk
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                venta.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Venta buscarPorId(Long id) {
        String sql = "select v.id, v.total, v.fecha, " +
                     "pc.id as cliente_id, pc.nombre as cliente_nombre, pc.identificacion as cliente_identificacion, " +
                     "pc.correo as cliente_correo, pc.telefono as cliente_telefono, " +
                     "pe.id as empleado_id, pe.nombre as empleado_nombre, pe.identificacion as empleado_identificacion, " +
                     "pe.correo as empleado_correo, pe.telefono as empleado_telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from venta v " +
                     "join cliente cl on v.cliente_fk = cl.persona_fk " +
                     "join persona pc on cl.persona_fk = pc.id " +
                     "join empleado e on v.empleado_fk = e.persona_fk " +
                     "join persona pe on e.persona_fk = pe.id " +
                     "join rol r on e.rol_fk = r.id " +
                     "where v.id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearVenta(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar venta por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Venta> listarTodo() {
        List<Venta> lista = new ArrayList<>();
        String sql = "select v.id, v.total, v.fecha, " +
                     "pc.id as cliente_id, pc.nombre as cliente_nombre, pc.identificacion as cliente_identificacion, " +
                     "pc.correo as cliente_correo, pc.telefono as cliente_telefono, " +
                     "pe.id as empleado_id, pe.nombre as empleado_nombre, pe.identificacion as empleado_identificacion, " +
                     "pe.correo as empleado_correo, pe.telefono as empleado_telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from venta v " +
                     "join cliente cl on v.cliente_fk = cl.persona_fk " +
                     "join persona pc on cl.persona_fk = pc.id " +
                     "join empleado e on v.empleado_fk = e.persona_fk " +
                     "join persona pe on e.persona_fk = pe.id " +
                     "join rol r on e.rol_fk = r.id";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearVenta(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    // metodo privado para no repetir la construccion del objeto en buscar y listar
    private Venta mapearVenta(ResultSet rs) throws Exception {
        Cliente cliente = new Cliente(
                rs.getLong("cliente_id"),
                rs.getString("cliente_nombre"),
                rs.getString("cliente_identificacion"),
                rs.getString("cliente_correo"),
                rs.getString("cliente_telefono")
        );
        Rol rol = new Rol(
                rs.getLong("rol_id"),
                rs.getString("rol_nombre"),
                rs.getLong("privilegios")
        );
        Empleado empleado = new Empleado(
                rol,
                rs.getString("username"),
                rs.getString("password"),
                rs.getLong("empleado_id"),
                rs.getString("empleado_nombre"),
                rs.getString("empleado_identificacion"),
                rs.getString("empleado_correo"),
                rs.getString("empleado_telefono")
        );
        return new Venta(
                rs.getLong("id"),
                cliente,
                empleado,
                rs.getDouble("total"),
                rs.getObject("fecha", LocalDateTime.class)
        );
    }
}
