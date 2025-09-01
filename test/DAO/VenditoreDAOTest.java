package DAO;

import Model.Utente;
import Model.Venditore;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class VenditoreDAOTest {
    private static Connection connection;
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
                    "email VARCHAR(100), " +
                    "telefono VARCHAR(10), " +
                    "password VARCHAR(100), " +
                    "venditore boolean)");

            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Luigi', 'Verdi', 'luigiverdi@example.com', '9876543210', 'pwd2', true)");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Roberto', 'Gialli', 'robertogialli@example.com', '9876543210', 'pwd3', true)");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Pietro', 'Bianchi', 'pietrobianchi@example.com', '0123456789', 'pwd4', true)");

            stmt.execute("CREATE TABLE Venditore (" +
                    "id_venditore INT PRIMARY KEY , " +
                    "descrizione VARCHAR(50), " +
                    "rating FLOAT)");

            stmt.execute("INSERT INTO Venditore VALUES (2, '', 8.2)");
            stmt.execute("INSERT INTO Venditore VALUES (3, '', 7.8)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Utente");
            stmt.execute("TRUNCATE TABLE Venditore");
            stmt.execute("INSERT INTO Utente VALUES (1, 'Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
            stmt.execute("INSERT INTO Utente VALUES (2, 'Luigi', 'Verdi', 'luigiverdi@example.com', '9876543210', 'pwd2', true)");
            stmt.execute("INSERT INTO Utente VALUES (3, 'Roberto', 'Gialli', 'robertogialli@example.com', '9876543210', 'pwd3', true)");
            stmt.execute("INSERT INTO Utente VALUES (4, 'Pietro', 'Bianchi', 'pietrobianchi@example.com', '0123456789', 'pwd4', true)");
            stmt.execute("INSERT INTO Venditore VALUES (2, '', 8.2)");
            stmt.execute("INSERT INTO Venditore VALUES (3, '', 7.8)");
        }
    }

    @Before
    public void initDAO() {
        venditoreDAO = new VenditoreDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSelect(){
        Venditore venditore = (Venditore) venditoreDAO.select(2);

        assertNotNull(venditore);
        assertEquals(2, venditore.getId());
        assertEquals("Luigi", venditore.getNome());
        assertEquals("Verdi", venditore.getCognome());
        assertEquals("luigiverdi@example.com", venditore.getEmail());
        assertEquals("pwd2", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(8.2, venditore.getRating(), 0.01);

        venditore = (Venditore) venditoreDAO.select(1);
        assertNull(venditore);
    }

    @Test
    public void testSelectAll(){
        List<Object> venditori = venditoreDAO.selectAll();

        assertNotNull(venditori);
        assertEquals(2, venditori.size());
        Venditore venditore = (Venditore) venditori.getFirst();
        assertNotNull(venditore);
        assertEquals(2, venditore.getId());
        assertEquals("Luigi", venditore.getNome());
        assertEquals("Verdi", venditore.getCognome());
        assertEquals("luigiverdi@example.com", venditore.getEmail());
        assertEquals("pwd2", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(8.2, venditore.getRating(), 0.01);

        venditore = (Venditore) venditori.getLast();
        assertNotNull(venditore);
        assertEquals(3, venditore.getId());
        assertEquals("Roberto", venditore.getNome());
        assertEquals("Gialli", venditore.getCognome());
        assertEquals("robertogialli@example.com", venditore.getEmail());
        assertEquals("pwd3", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(7.8, venditore.getRating(), 0.01);
    }

    @Test
    public void testDelete(){
        assertTrue(venditoreDAO.delete(2));
        assertNull(venditoreDAO.select(2));
        assertFalse(venditoreDAO.delete(1));
    }

    @Test
    public void testInsert(){
        int id = venditoreDAO.insert(new Venditore(4, "Pietro", "Bianchi", "pietrobianchi@example.com", "0123456789", "pwd4", "", 6.1f));
        assertTrue(id > 0);

        Venditore venditore = (Venditore) venditoreDAO.select(4);
        assertNotNull(venditore);
        assertEquals(4, venditore.getId());
        assertEquals("Pietro", venditore.getNome());
        assertEquals("Bianchi", venditore.getCognome());
        assertEquals("pietrobianchi@example.com", venditore.getEmail());
        assertEquals("pwd4", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(6.1, venditore.getRating(), 0.01);
    }

    @Test
    public void testUpdate(){
        venditoreDAO.update(new Venditore(2, "Luigi", "Verdi", "luigiverdi@example.com", "9876543210", "pwd2", "12", 6.4f));
        Venditore venditore = (Venditore) venditoreDAO.select(2);

        assertNotNull(venditore);
        assertEquals("12", venditore.getDescrizione());
        assertEquals(6.4, venditore.getRating(), 0.01);
    }

    @Test
    public void testCercaVenditori(){
        List<Venditore> venditori = venditoreDAO.cercaVenditori("");

        assertFalse(venditori.isEmpty());
        assertNotNull(venditori);
        assertEquals(2, venditori.size());

        Venditore venditore = venditori.getFirst();

        assertNotNull(venditore);
        assertEquals("Luigi", venditore.getNome());
        assertEquals("Verdi", venditore.getCognome());
        assertEquals("luigiverdi@example.com", venditore.getEmail());
        assertEquals("pwd2", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(8.2, venditore.getRating(), 0.01);

        venditore = venditori.getLast();

        assertNotNull(venditore);
        assertEquals(3, venditore.getId());
        assertEquals("Roberto", venditore.getNome());
        assertEquals("Gialli", venditore.getCognome());
        assertEquals("robertogialli@example.com", venditore.getEmail());
        assertEquals("pwd3", venditore.getPassword());
        assertEquals("", venditore.getDescrizione());
        assertEquals(7.8, venditore.getRating(), 0.01);
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}