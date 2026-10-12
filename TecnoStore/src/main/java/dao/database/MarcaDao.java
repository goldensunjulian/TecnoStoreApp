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
import modelo.entidades.Marca;

public class MarcaDao implements InterfaceAgregar<Marca>, InterfaceActualizar<Marca>,
        InterfaceEliminar, InterfaceBuscarPorId<Marca>, InterfaceListar<Marca> {

    @Override
    public Boolean agregar(Marca marca) {
        String sql = "insert into marca (nombre) values (?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, marca.getNombre());
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                marca.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar marca: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Marca marca) {
        String sql = "update marca set nombre = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, marca.getNombre());
            ps.setLong(2, marca.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar marca: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        String sql = "delete from marca where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar marca: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Marca buscarPorId(Long id) {
        String sql = "select * from marca where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Marca(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar marca por id: " + e.getMessage());
        }
        return null;
    }

    public Marca buscarPorNombre(String nombre) {
        String sql = "select * from marca where lower(nombre) = lower(?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Marca(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar marca por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Marca> listarTodo() {
        List<Marca> lista = new ArrayList<>();
        String sql = "select * from marca";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Marca(rs.getLong("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar marcas: " + e.getMessage());
        }
        return lista;
    }
}
