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
import modelo.entidades.Gama;

public class GamaDao implements InterfaceAgregar<Gama>, InterfaceActualizar<Gama>,
        InterfaceEliminar, InterfaceBuscarPorId<Gama>, InterfaceListar<Gama> {

    @Override
    public Boolean agregar(Gama gama) {
        String sql = "insert into gama (nombre) values (?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, gama.getNombre());
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                gama.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar gama: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Gama gama) {
        String sql = "update gama set nombre = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, gama.getNombre());
            ps.setLong(2, gama.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar gama: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        String sql = "delete from gama where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar gama: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Gama buscarPorId(Long id) {
        String sql = "select * from gama where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Gama(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar gama por id: " + e.getMessage());
        }
        return null;
    }

    public Gama buscarPorNombre(String nombre) {
        String sql = "select * from gama where lower(nombre) = lower(?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Gama(rs.getLong("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar gama por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Gama> listarTodo() {
        List<Gama> lista = new ArrayList<>();
        String sql = "select * from gama";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Gama(rs.getLong("id"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al listar gamas: " + e.getMessage());
        }
        return lista;
    }
}
