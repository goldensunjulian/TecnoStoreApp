package dao.database;

import dao.Conexion;
import dao.interfaceDAO.InterfaceActualizar;
import dao.interfaceDAO.InterfaceAgregar;
import dao.interfaceDAO.InterfaceBuscarPorId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.entidades.Persona;

public class PersonaDao implements InterfaceAgregar<Persona>, InterfaceActualizar<Persona>, InterfaceBuscarPorId<Persona> {

    @Override
    public Boolean agregar(Persona persona) {
        String sql = "insert into persona (nombre, identificacion, correo, telefono) values (?, ?, ?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getIdentificador());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());
            ps.executeUpdate();
            // tomamos el id generado
            // para usarlo como persona_fk en cliente o empleado
            ResultSet generado = ps.getGeneratedKeys();
            if (generado.next()) {
                persona.setId(generado.getLong(1));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar persona: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Persona persona) {
        String sql = "update persona set nombre = ?, identificacion = ?, correo = ?, telefono = ? where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getIdentificador());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());
            ps.setLong(5, persona.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar persona: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Persona buscarPorId(Long id) {
        String sql = "select * from persona where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Persona(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar persona por id: " + e.getMessage());
        }
        return null;
    }

    // busca por identi para saber si la persona ya existe
    public Persona buscarPorIdentificacion(String identificacion) {
        String sql = "select * from persona where identificacion = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Persona(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar persona por identificacion: " + e.getMessage());
        }
        return null;
    }
}
