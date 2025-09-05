package Controller;

import DAO.DatabaseConnection;

import static org.junit.Assert.*;

import DAO.UtenteDAO;
import DAO.VenditoreDAO;
import Model.Utente;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Model.Venditore;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ControllerBaseTest {

    private static Connection connection;
    private ControllerBase controller;
    private UtenteDAO utenteDAO;
    private VenditoreDAO venditoreDAO;

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

            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");

            stmt.execute("CREATE TABLE Venditore (" +
                    "id_venditore INT PRIMARY KEY , " +
                    "descrizione VARCHAR(50), " +
                    "rating FLOAT)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Utente");
            stmt.execute("ALTER TABLE Utente ALTER COLUMN id_utente RESTART WITH 1");
            stmt.execute("TRUNCATE TABLE Venditore");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
        }
    }

    @Before
    public void init() {
        controller = ControllerBase.getInstance();
        utenteDAO = new UtenteDAO();
        venditoreDAO = new VenditoreDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testRegisterUtente() {
        Utente nuovo = new Utente(2,"Luigi", "Verdi", "luigiverdi@example.com", "9876543210", "pwd2", true);

        boolean result = controller.registerUtente(nuovo);
        assertTrue(result);

        List<Object> utenti = utenteDAO.selectAll();

        assertEquals(2, utenti.size());

        Utente utente = (Utente) utenti.getFirst();
        assertEquals(1, utente.getId());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mariorossi@example.com", utente.getEmail());

        utente = (Utente) utenti.getLast();

        assertEquals(2, utente.getId());
        assertEquals("Luigi", utente.getNome());
        assertEquals("Verdi", utente.getCognome());
        assertEquals("luigiverdi@example.com", utente.getEmail());

        nuovo = new Utente(3,"Mario", "Rossi", "mariorossi@example.com", "9876543211", "pwd3", false);

        result = controller.registerUtente(nuovo);
        assertFalse(result);

        assertEquals(2, utenteDAO.selectAll().size());
    }

    @Test
    public void testRegisterVenditore(){
        Venditore nuovo = new Venditore(2,"Luigi", "Verdi", "luigiverdi@example.com", "pwd2", "9876543210", "");

        boolean result = controller.registerVenditore(nuovo);
        assertTrue(result);

        Venditore venditore = (Venditore) venditoreDAO.select(2);
        assertNotNull(venditore);
        assertEquals(2, venditore.getId());
        assertEquals("Luigi", venditore.getNome());
        assertEquals("Verdi", venditore.getCognome());
        assertEquals("luigiverdi@example.com", venditore.getEmail());
        assertEquals("pwd2", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());

        nuovo = new Venditore(3,"Mario", "Rossi", "mariorossi@example.com", "9876543211", "pwd3", "");

        result = controller.registerVenditore(nuovo);
        assertFalse(result);
    }

    @Test
    public void testLogin(){
        assertFalse(controller.login("luigiverdi@example.com", "pwd3"));
        assertTrue(controller.login("mariorossi@example.com", "pwd"));
        controller.logout();
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}