package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private String URL = "jdbc:mysql://localhost:3306/marketplace_dev"; // Nome database
    private String USER = "root"; // Utente di default di XAMPP
    private String PASSWORD = ""; // Password vuota in XAMPP


    private DatabaseConnection() {

        // Carica il driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC non trovato!");
            e.printStackTrace();
        }
        // Crea la connessione al database
        connection = getConnection();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) instance = new DatabaseConnection();
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Riconnetti in caso di chiusura
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //System.out.println("✅ Connessione avvenuta con successo!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Errore di connessione al database!");
            e.printStackTrace();
        }
        return connection;
    }
}

