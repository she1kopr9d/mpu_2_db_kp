package ru.kafedra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager _dbInstance;
    private Connection _dbConnection;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/kafedra_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rootpassword";

    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this._dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (_dbInstance == null || isConnectionClosed()) {
            _dbInstance = new DatabaseManager();
        }
        return _dbInstance;
    }

    public Connection getConnection() {
        return _dbConnection;
    }

    private static boolean isConnectionClosed() {
        try {
            return _dbInstance._dbConnection == null || _dbInstance._dbConnection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }
}