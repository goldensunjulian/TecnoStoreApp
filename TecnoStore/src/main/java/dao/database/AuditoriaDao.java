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
import modelo.entidades.Auditoria;
import modelo.entidades.Empleado;
import modelo.entidades.Rol;

public class AuditoriaDao implements InterfaceAgregar<Auditoria>, InterfaceBuscarPorId<Auditoria>, InterfaceListar<Auditoria> {

    @Override
    public Boolean agregar(Auditoria auditoria) {
        // la fecha la pone la DB con DEFAULT CURRENT_TIMESTAMP, no se envia
        String sql = "insert into auditoria (accion, empleado_fk) values (?, ?)";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, auditoria.getAccion());
            ps.setLong(2, auditoria.getEmpleado().getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar auditoria: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Auditoria buscarPorId(Long id) {
        String sql = "select a.id, a.accion, a.fecha, " +
                     "p.id as empleado_id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "e.username, e.password, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from auditoria a " +
                     "join empleado e on a.empleado_fk = e.persona_fk " +
                     "join persona p on e.persona_fk = p.id " +
                     "join rol r on e.rol_fk = r.id " +
                     "where a.id = ?";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearAuditoria(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar auditoria por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Auditoria> listarTodo() {
        List<Auditoria> lista = new ArrayList<>();
        String sql = "select a.id, a.accion, a.fecha, " +
                     "p.id as empleado_id, p.nombre, p.identificacion, p.correo, p.telefono, " +
                     "r.id as rol_id, r.rol as rol_nombre, r.privilegios " +
                     "from auditoria a " +
                     "join empleado e on a.empleado_fk = e.persona_fk " +
                     "join persona p on e.persona_fk = p.id " +
                     "join rol r on e.rol_fk = r.id " +
                     "order by a.fecha desc";
        try (Connection c = Conexion.conexion()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearAuditoria(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar auditorias: " + e.getMessage());
        }
        return lista;
    }

    private Auditoria mapearAuditoria(ResultSet rs) throws Exception {
        Rol rol = new Rol(
                rs.getLong("rol_id"),
                rs.getString("rol_nombre"),
                rs.getLong("privilegios")
        );
        // constructor sin credenciales — username y password no se exponen en auditoria
        Empleado empleado = new Empleado(
                rol,
                rs.getLong("empleado_id"),
                rs.getString("nombre"),
                rs.getString("identificacion"),
                rs.getString("correo"),
                rs.getString("telefono")
        );
        return new Auditoria(
                rs.getLong("id"),
                rs.getString("accion"),
                rs.getObject("fecha", java.time.LocalDateTime.class),
                empleado
        );
    }
}
