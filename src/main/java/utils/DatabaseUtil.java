package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    static {
        try {
            Class.forName("org.postgresql.Driver");
//            Logger.info("PostgreSQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
//            Logger.log(Level.SEVERE, "Failed to load PostgreSQL JDBC Driver.", e);
            throw new RuntimeException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "nEw_pass12";

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
            conn.setSchema("airline_db");

            return conn;
        } catch (SQLException e) {
            System.out.println(e);
//            LOGGER.log(Level.SEVERE, "Failed to establish database connection.", e);
            throw e;
        }
    }
}