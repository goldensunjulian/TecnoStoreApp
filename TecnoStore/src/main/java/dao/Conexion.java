package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection c = null;
    public static Connection conexion() {
        try {
            if (c == null || c.isClosed()) {
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/TecnoStore", "root", "Tristan2105");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}