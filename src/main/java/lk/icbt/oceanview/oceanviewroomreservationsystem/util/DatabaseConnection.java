package lk.icbt.oceanview.oceanviewroomreservationsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/oceanview_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "fight2kill";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_PARAMS = "?useSSL=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true"
            + "&autoReconnect=true"
            + "&useUnicode=true"
            + "&characterEncoding=UTF-8"
            + "&connectionCollation=utf8mb4_unicode_ci";
    private static DatabaseConnection instance;
    private Connection connection;


    private DatabaseConnection() {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("[DatabaseConnection] MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("[DatabaseConnection] ERROR: MySQL JDBC Driver not found!");
            System.err.println("[DatabaseConnection] Make sure mysql-connector-j is in pom.xml");
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
            System.out.println("[DatabaseConnection] New Singleton instance created");
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                        DB_URL + CONNECTION_PARAMS,
                        DB_USER,
                        DB_PASSWORD
                );
                connection.createStatement().execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
                System.out.println("[DatabaseConnection] Database connection established successfully");
                System.out.println("[DatabaseConnection] Connected to: " + DB_URL);
            } catch (SQLException e) {
                System.err.println("[DatabaseConnection] ERROR: Failed to establish database connection!");
                System.err.println("[DatabaseConnection] URL: " + DB_URL);
                System.err.println("[DatabaseConnection] User: " + DB_USER);
                System.err.println("[DatabaseConnection] Error: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }


    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("[DatabaseConnection] Connection test SUCCESSFUL");
                System.out.println("[DatabaseConnection] Database: " + conn.getCatalog());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseConnection] Connection test FAILED: " + e.getMessage());
        }
        return false;
    }


    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[DatabaseConnection] Database connection closed successfully");
            } catch (SQLException e) {
                System.err.println("[DatabaseConnection] ERROR closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }


    public String getConnectionInfo() {
        return "Database URL: " + DB_URL + "\n" +
                "Database User: " + DB_USER + "\n" +
                "JDBC Driver: " + DB_DRIVER + "\n" +
                "Connection Status: " + (isConnected() ? "CONNECTED" : "DISCONNECTED");
    }


    public void printConnectionInfo() {
        System.out.println("\n========================================");
        System.out.println("DATABASE CONNECTION INFO");
        System.out.println("========================================");
        System.out.println(getConnectionInfo());
        System.out.println("========================================\n");
    }
}
