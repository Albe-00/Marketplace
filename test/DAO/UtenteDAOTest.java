package DAO;

import Model.Utente;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class UtenteDAOTest {
    private static Connection connection;
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
            stmt.execute("INSERT INTO utente (nome, cognome, email, telefono, password, venditore) VALUES ('Luigi', 'Verdi', 'luigiverdi@example.com', '9876543210', 'pwd2', true)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Utente");
            stmt.execute("INSERT INTO Utente VALUES (1, 'Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
            stmt.execute("INSERT INTO Utente VALUES (2, 'Luigi', 'Verdi', 'luigiverdi@example.com', '9876543210', 'pwd2', true)");
        }
    }

    @Before
    public void initDAO() {
        utenteDAO = new UtenteDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSelect(){
        Utente utente = (Utente) utenteDAO.select(1);

        assertNotNull(utente);
        assertEquals(1, utente.getId());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mariorossi@example.com", utente.getEmail());
        assertEquals("0123456789", utente.getTelefono());
        assertEquals("pwd", utente.getPassword());
        assertFalse(utente.isVenditore());

        utente = (Utente) utenteDAO.select(2);

        assertNull(utente);
    }

    @Test
    public void testSelectAll(){
        List<Object> utenti = utenteDAO.selectAll();

        assertEquals(utenti.size(), 2);

        assertNotNull(utenti);
        assertFalse(utenti.isEmpty());

        Utente utente = (Utente) utenti.getFirst();

        assertEquals(1, utente.getId());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mariorossi@example.com", utente.getEmail());
        assertEquals("0123456789", utente.getTelefono());
        assertEquals("pwd", utente.getPassword());
        assertFalse(utente.isVenditore());

        utente = (Utente) utenti.getLast();

        assertEquals(2, utente.getId());
        assertEquals("Luigi", utente.getNome());
        assertEquals("Verdi", utente.getCognome());
        assertEquals("luigiverdi@example.com", utente.getEmail());
        assertEquals("9876543210", utente.getTelefono());
        assertEquals("pwd2", utente.getPassword());
        assertTrue(utente.isVenditore());
    }

    @Test
    public void testDelete(){
        assertTrue(utenteDAO.delete(1));
        assertNull(utenteDAO.select(1));
        assertFalse(utenteDAO.delete(3));
    }

    @Test
    public void testInsert(){
        int id = utenteDAO.insert(new Utente(3, "Pietro", "Bianchi", "pietrobianchi@example.com", "pwd3", "0123456789", false));
        assertTrue(id > 0);

        Utente utente = (Utente) utenteDAO.select(id);
        assertEquals(3, utente.getId());
        assertEquals("Pietro", utente.getNome());
        assertEquals("Bianchi", utente.getCognome());
        assertEquals("pietrobianchi@example.com", utente.getEmail());
        assertEquals("0123456789", utente.getTelefono());
        assertEquals("pwd3", utente.getPassword());
        assertFalse(utente.isVenditore());
    }

    @Test
    public void testUpdate(){
        boolean result = utenteDAO.update(new Utente(2, "Pietro", "Bianchi", "pietrobianchi@example.com", "pwd3", "0123456789", false));
        assertTrue(result);
        Utente utente = (Utente) utenteDAO.select(2);
        assertNotNull(utente);
        assertEquals(utente.getId(), 2);
        assertEquals("Pietro", utente.getNome());
        assertEquals("Bianchi", utente.getCognome());
        assertEquals("pietrobianchi@example.com", utente.getEmail());
        assertEquals("0123456789", utente.getTelefono());
        assertEquals("pwd3", utente.getPassword());
        assertFalse(utente.isVenditore());
    }

    @Test
    public void testUpdatePassword(){
        boolean result = utenteDAO.updatePassword(2, "pwd3");
        assertTrue(result);

        Utente utente = (Utente) utenteDAO.select(2);
        assertNotNull(utente);
        assertEquals("pwd3", utente.getPassword());
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}