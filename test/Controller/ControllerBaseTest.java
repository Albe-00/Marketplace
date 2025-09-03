package Controller;

import DAO.DatabaseConnection;

import static org.junit.Assert.*;

import DAO.UtenteDAO;
import Model.Utente;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ControllerBaseTest {

    private static Connection connection;
    private ControllerBase controller;
    private UtenteDAO utenteDAO;

    @BeforeClass
    public static void setupDatabase() throws Exception {

        Class.forName("org.h2.Driver");
        // Ottieni la connection H2
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", "");

        //Modifica i campi privati di DatabaseConnection
        DatabaseConnection db = DatabaseConnection.getInstance();

        setPrivateField(db, "URL", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        setPrivateField(db, "USER", "sa");
        setPrivateField(db, "PASSWORD", "");

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Utente (" +
                    "id_utente INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(50), " +
                    "cognome VARCHAR(50), " +
                    "email VARCHAR(100) UNIQUE, " +
                    "telefono VARCHAR(10), " +
                    "password VARCHAR(100), " +
                    "venditore boolean)");

            stmt.execute("INSERT INTO utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Utente");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
        }
    }

    @Before
    public void init() {
        controller = ControllerBase.getInstance();
        utenteDAO = new UtenteDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testRegisterNuovoUtente() {
        Utente nuovo = new Utente(2,"Luigi", "Verdi", "luigiverdi@example.com", "9876543210", "pwd2", true);

        boolean result = controller.registerUtente(nuovo);
        assertTrue(result);

        assertEquals(2, utenteDAO.selectAll().size());

        nuovo = new Utente(3,"Mario", "Rossi", "mariorossi@example.com", "9876543211", "pwd3", false);

        result = controller.registerUtente(nuovo);
        assertFalse(result);

        assertEquals(2, utenteDAO.selectAll().size());
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}