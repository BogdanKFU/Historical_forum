package ru.kpfu.itis.group501.popov.singletons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static Connection connection = null;
    private final static String url = "jdbc:postgresql://localhost:5432/semester_work";
    private final static String username = "postgres";
    private final static String password = "hugoqwerty";
    private final static String driver = "org.postgresql.Driver";
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
