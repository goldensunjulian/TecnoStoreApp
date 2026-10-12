package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceBuscarPorId;
import dao.interfaceDAO.InterfaceListar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Rol;

public class RolDao implements InterfaceBuscarPorId<Rol>, InterfaceListar<Rol> {

    @Override
    public Rol buscarPorId(Long id) {
        String sql = "select * from rol where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Rol(rs.getLong("id"), rs.getString("rol"), rs.getLong("privilegios"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar rol: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Rol> listarTodo() {
        List<Rol> lista = new ArrayList<>();
        String sql = "select * from rol";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Rol(
                        rs.getLong("id"),
                        rs.getString("rol"),
                        rs.getLong("privilegios")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }
}
