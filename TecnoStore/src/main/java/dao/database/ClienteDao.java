package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceActualizar;
import dao.interfaceDAO.InterfaceAgregar;
import dao.interfaceDAO.InterfaceBuscarPorId;
import dao.interfaceDAO.InterfaceListar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Cliente;

public class ClienteDao implements InterfaceAgregar<Cliente>, InterfaceActualizar<Cliente>,
        InterfaceBuscarPorId<Cliente>, InterfaceListar<Cliente> {

    @Override
    public Boolean agregar(Cliente cliente) {
        //esto solo guarda el cliente con la fk
        String sql = "insert into cliente (persona_fk) values (?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, cliente.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Cliente cliente) {
        String sql = "update persona set nombre = ?, identificacion = ?, correo = ?, telefono = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getIdentificador());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setLong(5, cliente.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {
        String sql = "select p.id, p.nombre, p.identificacion, p.correo, p.telefono " +
                     "from cliente c " +
                     "join persona p on c.persona_fk = p.id " +
                     "where c.persona_fk = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodo() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "select p.id, p.nombre, p.identificacion, p.correo, p.telefono " +
                     "from cliente c " +
                     "join persona p on c.persona_fk = p.id";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }
}
