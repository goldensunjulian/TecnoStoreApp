package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection c;

    private Conexion() {
        try {
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tecnostore", "root", "Tristan2105"
            );
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public static Connection conexion() {
        try {
            Conexion c = getInstance();
            if (c.c == null || c.c.isClosed()) {
                c.c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tecnostore", "root", "Tristan2105"
                );
            }
            return c.c;
        } catch (SQLException e) {
            System.out.println("Error al obtener conexion: " + e.getMessage());
            return null;
        }
    }
}
