package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceActualizar;
import dao.interfaceDAO.InterfaceAgregar;
import dao.interfaceDAO.InterfaceBuscarPorId;
import dao.interfaceDAO.InterfaceEliminar;
import dao.interfaceDAO.InterfaceListar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Celular;
import modelo.entidades.Gama;
import modelo.entidades.Marca;
import modelo.entidades.SistemaOperativo;

public class CelularDao implements InterfaceAgregar<Celular>, InterfaceActualizar<Celular>,
        InterfaceEliminar, InterfaceBuscarPorId<Celular>, InterfaceListar<Celular> {

    @Override
    public Boolean agregar(Celular celular) {
        String sql = "insert into celular (marca_fk, modelo, precio, stock, sistema_operativo_fk, gama_fk) values (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, celular.getMarca().getId());
            ps.setString(2, celular.getModelo());
            ps.setDouble(3, celular.getPrecio());
            ps.setLong(4, celular.getStock());
            ps.setLong(5, celular.getSo().getId());
            ps.setLong(6, celular.getGama().getId());
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                celular.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar celular: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Celular celular) {
        String sql = "update celular set marca_fk = ?, modelo = ?, precio = ?, stock = ?, sistema_operativo_fk = ?, gama_fk = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, celular.getMarca().getId());
            ps.setString(2, celular.getModelo());
            ps.setDouble(3, celular.getPrecio());
            ps.setLong(4, celular.getStock());
            ps.setLong(5, celular.getSo().getId());
            ps.setLong(6, celular.getGama().getId());
            ps.setLong(7, celular.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar celular: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        String sql = "delete from celular where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar celular: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Celular buscarPorId(Long id) {
        String sql = "select c.id, c.modelo, c.precio, c.stock, " +
                     "m.id as marca_id, m.nombre as marca_nombre, " +
                     "s.id as so_id, s.nombre as so_nombre, " +
                     "g.id as gama_id, g.nombre as gama_nombre " +
                     "from celular c " +
                     "join marca m on c.marca_fk = m.id " +
                     "join sistema_operativo s on c.sistema_operativo_fk = s.id " +
                     "join gama g on c.gama_fk = g.id " +
                     "where c.id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearCelular(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar celular por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Celular> listarTodo() {
        List<Celular> lista = new ArrayList<>();
        String sql = "select c.id, c.modelo, c.precio, c.stock, " +
                     "m.id as marca_id, m.nombre as marca_nombre, " +
                     "s.id as so_id, s.nombre as so_nombre, " +
                     "g.id as gama_id, g.nombre as gama_nombre " +
                     "from celular c " +
                     "join marca m on c.marca_fk = m.id " +
                     "join sistema_operativo s on c.sistema_operativo_fk = s.id " +
                     "join gama g on c.gama_fk = g.id";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearCelular(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar celulares: " + e.getMessage());
        }
        return lista;
    }

    // actualiza solo el stock para confirmar venta
    public Boolean actualizarStock(Long id, Long nuevoStock) {
        String sql = "update celular set stock = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, nuevoStock);
            ps.setLong(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }

    // me evita repetir la armada en las otras consultas
    private Celular mapearCelular(ResultSet rs) throws Exception {
        Marca marca = new Marca(rs.getLong("marca_id"), rs.getString("marca_nombre"));
        SistemaOperativo so = new SistemaOperativo(rs.getLong("so_id"), rs.getString("so_nombre"));
        Gama gama = new Gama(rs.getLong("gama_id"), rs.getString("gama_nombre"));
        return new Celular(
                rs.getLong("id"),
                marca,
                rs.getString("modelo"),
                rs.getDouble("precio"),
                rs.getLong("stock"),
                so,
                gama
        );
    }
}
