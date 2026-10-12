package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceAgregar;
import dao.interfaceDAO.InterfaceBuscarPorId;
import dao.interfaceDAO.InterfaceListar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.DetalleVenta;
import modelo.entidades.Gama;
import modelo.entidades.Marca;
import modelo.entidades.SistemaOperativo;
import modelo.entidades.Celular;
import modelo.entidades.Venta;

public class DetallesVentaDao implements InterfaceAgregar<DetalleVenta>, InterfaceBuscarPorId<DetalleVenta>, InterfaceListar<DetalleVenta> {

    @Override
    public Boolean agregar(DetalleVenta detalle) {
        String sql = "insert into detalles_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) values (?, ?, ?, ?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, detalle.getVenta().getId());
            ps.setLong(2, detalle.getCelular().getId());
            ps.setLong(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar detalle de venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public DetalleVenta buscarPorId(Long id) {
        String sql = "select dv.id, dv.cantidad, dv.precio_unitario, dv.subtotal, " +
                     "c.id as celular_id, c.modelo, c.precio, c.stock, " +
                     "m.id as marca_id, m.nombre as marca_nombre, " +
                     "s.id as so_id, s.nombre as so_nombre, " +
                     "g.id as gama_id, g.nombre as gama_nombre, " +
                     "dv.venta_fk " +
                     "from detalles_venta dv " +
                     "join celular c on dv.celular_fk = c.id " +
                     "join marca m on c.marca_fk = m.id " +
                     "join sistema_operativo s on c.sistema_operativo_fk = s.id " +
                     "join gama g on c.gama_fk = g.id " +
                     "where dv.id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearDetalle(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar detalle de venta por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DetalleVenta> listarTodo() {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "select dv.id, dv.cantidad, dv.precio_unitario, dv.subtotal, " +
                     "c.id as celular_id, c.modelo, c.precio, c.stock, " +
                     "m.id as marca_id, m.nombre as marca_nombre, " +
                     "s.id as so_id, s.nombre as so_nombre, " +
                     "g.id as gama_id, g.nombre as gama_nombre, " +
                     "dv.venta_fk " +
                     "from detalles_venta dv " +
                     "join celular c on dv.celular_fk = c.id " +
                     "join marca m on c.marca_fk = m.id " +
                     "join sistema_operativo s on c.sistema_operativo_fk = s.id " +
                     "join gama g on c.gama_fk = g.id";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearDetalle(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar detalles de venta: " + e.getMessage());
        }
        return lista;
    }

    // lista los detalles de una venta especifica — util para mostrar el resumen de una venta
    public List<DetalleVenta> listarPorVenta(Long ventaId) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "select dv.id, dv.cantidad, dv.precio_unitario, dv.subtotal, " +
                     "c.id as celular_id, c.modelo, c.precio, c.stock, " +
                     "m.id as marca_id, m.nombre as marca_nombre, " +
                     "s.id as so_id, s.nombre as so_nombre, " +
                     "g.id as gama_id, g.nombre as gama_nombre, " +
                     "dv.venta_fk " +
                     "from detalles_venta dv " +
                     "join celular c on dv.celular_fk = c.id " +
                     "join marca m on c.marca_fk = m.id " +
                     "join sistema_operativo s on c.sistema_operativo_fk = s.id " +
                     "join gama g on c.gama_fk = g.id " +
                     "where dv.venta_fk = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, ventaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearDetalle(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar detalles por venta: " + e.getMessage());
        }
        return lista;
    }

    // metodo privado para no repetir la construccion del objeto
    // nota: la venta aqui es un objeto parcial con solo el id, suficiente para la referencia
    private DetalleVenta mapearDetalle(ResultSet rs) throws Exception {
        Marca marca = new Marca(rs.getLong("marca_id"), rs.getString("marca_nombre"));
        SistemaOperativo so = new SistemaOperativo(rs.getLong("so_id"), rs.getString("so_nombre"));
        Gama gama = new Gama(rs.getLong("gama_id"), rs.getString("gama_nombre"));
        Celular celular = new Celular(
                rs.getLong("celular_id"),
                marca,
                rs.getString("modelo"),
                rs.getDouble("precio"),
                rs.getLong("stock"),
                so,
                gama
        );
        // venta parcial solo con id para evitar un join circular
        Venta venta = new Venta(rs.getLong("venta_fk"));
        return new DetalleVenta(
                rs.getLong("id"),
                venta,
                celular,
                rs.getLong("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal")
        );
    }
}
