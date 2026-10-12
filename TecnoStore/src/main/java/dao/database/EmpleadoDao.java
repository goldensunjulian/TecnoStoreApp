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
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Empleado;
import modelo.entidades.Rol;

public class EmpleadoDao implements InterfaceAgregar<Empleado>, InterfaceActualizar<Empleado>,
        InterfaceEliminar, InterfaceBuscarPorId<Empleado>, InterfaceListar<Empleado> {

    @Override
    public Boolean agregar(Empleado empleado) {
        // la persona ya fue insertada antes con PersonaDao
        String sql = "insert into empleado (persona_fk, rol_fk, username, password) values (?, ?, ?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, empleado.getId());
            ps.setLong(2, empleado.getRol().getId());
            ps.setString(3, empleado.getUsername());
            ps.setString(4, empleado.getContraseña());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Empleado empleado) {
        // dos update: uno en persona y otro en empleado
        String sqlPersona  = "update persona set nombre = ?, identificacion = ?, correo = ?, telefono = ? where id = ?";
        String sqlEmpleado = "update empleado set rol_fk = ?, username = ?, password = ? where persona_fk = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement psPersona = c.prepareStatement(sqlPersona);
            psPersona.setString(1, empleado.getNombre());
            psPersona.setString(2, empleado.getIdentificador());
            psPersona.setString(3, empleado.getCorreo());
            psPersona.setString(4, empleado.getTelefono());
            psPersona.setLong(5, empleado.getId());
            psPersona.executeUpdate();

            PreparedStatement psEmpleado = c.prepareStatement(sqlEmpleado);
            psEmpleado.setLong(1, empleado.getRol().getId());
            psEmpleado.setString(2, empleado.getUsername());
            psEmpleado.setString(3, empleado.getContraseña());
            psEmpleado.setLong(4, empleado.getId());
            psEmpleado.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        // primero empleado por la FK, luego persona
        String sqlEmpleado = "delete from empleado where persona_fk = ?";
        String sqlPersona  = "delete from persona where id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement psEmp = c.prepareStatement(sqlEmpleado);
            psEmp.setLong(1, id);
            psEmp.executeUpdate();

            PreparedStatement psPer = c.prepareStatement(sqlPersona);
            psPer.setLong(1, id);
            psPer.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Empleado buscarPorId(Long id) {
        String sql = "select p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from empleado e " +
                     "join persona p on e.persona_fk = p.id " +
                     "join rol r on e.rol_fk = r.id " +
                     "where e.persona_fk = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rol rol = new Rol(
                        rs.getLong("rol_id"),
                        rs.getString("rol_nombre"),
                        rs.getLong("privilegios")
                );
                return new Empleado(
                        rol,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar empleado por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Empleado> listarTodo() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "select p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from empleado e " +
                     "join persona p on e.persona_fk = p.id " +
                     "join rol r on e.rol_fk = r.id";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol(
                        rs.getLong("rol_id"),
                        rs.getString("rol_nombre"),
                        rs.getLong("privilegios")
                );
                lista.add(new Empleado(
                        rol,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // usado en el login para autenticar
    public Empleado buscarPorUsername(String username) {
        String sql = "select p.id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from empleado e " +
                     "join persona p on e.persona_fk = p.id " +
                     "join rol r on e.rol_fk = r.id " +
                     "where e.username = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rol rol = new Rol(
                        rs.getLong("rol_id"),
                        rs.getString("rol_nombre"),
                        rs.getLong("privilegios")
                );
                return new Empleado(
                        rol,
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar empleado por username: " + e.getMessage());
        }
        return null;
    }
}
