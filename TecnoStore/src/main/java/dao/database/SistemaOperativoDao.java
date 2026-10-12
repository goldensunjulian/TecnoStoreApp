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
import modelo.entidades.SistemaOperativo;

public class SistemaOperativoDao implements InterfaceAgregar<SistemaOperativo>, InterfaceActualizar<SistemaOperativo>,
        InterfaceEliminar, InterfaceBuscarPorId<SistemaOperativo>, InterfaceListar<SistemaOperativo> {

    @Override
    public Boolean agregar(SistemaOperativo so) {
        String sql = "insert into sistema_operativo (nombre) values (?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, so.getNombre());
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                so.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar sistema operativo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(SistemaOperativo so) {
        String sql = "update sistema_operativo set nombre = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, so.getNombre());
            ps.setLong(2, so.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar sistema operativo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        String sql = "delete from sistema_operativo where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar sistema operativo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public SistemaOperativo buscarPorId(Long id) {
        String sql = "select * from sistema_operativo where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SistemaOperativo(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar sistema operativo por id: " + e.getMessage());
        }
        return null;
    }

    public SistemaOperativo buscarPorNombre(String nombre) {
        String sql = "select * from sistema_operativo where lower(nombre) = lower(?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SistemaOperativo(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar sistema operativo por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SistemaOperativo> listarTodo() {
        List<SistemaOperativo> lista = new ArrayList<>();
        String sql = "select * from sistema_operativo";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new SistemaOperativo(rs.getLong("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar sistemas operativos: " + e.getMessage());
        }
        return lista;
    }
}
