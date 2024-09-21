package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    // URL de conexão JDBC ao MySQL
    public static final String DB_URL = "jdbc:mysql://localhost:3306/sys";
    public static final String USER = "root";
    public static final String PASSWORD = "020722";

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        // Retorna a conexão
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
