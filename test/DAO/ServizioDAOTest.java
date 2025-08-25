package DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.Ordine;
import Model.Servizio;

import org.junit.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class ServizioDAOTest {
    private static Connection connection;
    private ServizioDAO servizioDAO;

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
            stmt.execute("CREATE TABLE Servizio (" +
                    "id_servizio INT PRIMARY KEY AUTO_INCREMENT ," +
                    "id_venditore INT," +
                    "titolo VARCHAR(50)," +
                    "descrizione VARCHAR(50)," +
                    "prezzo FLOAT," +
                    "categoria VARCHAR(50)," +
                    "data_pubblicazione DATE," +
                    "visibile BOOLEAN)");

            stmt.execute("INSERT INTO Servizio VALUES " +
                    "(1, 4, '', '', 45.2, '', '2023-01-01', true)");
            stmt.execute("INSERT INTO Servizio VALUES " +
                    "(2, 2, '', '', 12, '', '2023-05-01', true)");
            stmt.execute("INSERT INTO Servizio VALUES " +
                    "(3, 2, '', '', 10, '', '2023-06-01', true)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Servizio");
            stmt.execute("INSERT INTO Servizio VALUES (1, 4, '', '', 45.2, '', '2023-01-01', true)");
            stmt.execute("INSERT INTO Servizio VALUES (2, 2, '', '', 12, '', '2023-05-01', true)");
            stmt.execute("INSERT INTO Servizio VALUES (3, 2, '', '', 10, '', '2023-06-01', true)");
        }
    }

    @Before
    public void initDAO() {
        servizioDAO = new ServizioDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSelect(){
        Servizio servizio = (Servizio) servizioDAO.select(1);

        assertNotNull(servizio);
        assertEquals(1, servizio.getId_servizio());
        assertEquals(4, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(45.2, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        servizio = (Servizio) servizioDAO.select(2);

        assertNotNull(servizio);
        assertEquals(2, servizio.getId_servizio());
        assertEquals(2, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(12, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-05-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        servizio = (Servizio) servizioDAO.select(3);

        assertNotNull(servizio);
        assertEquals(3, servizio.getId_servizio());
        assertEquals(2, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(10, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-06-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        servizio = (Servizio) servizioDAO.select(4);
        assertNull(servizio);
    }

    @Test
    public void testSelectAll() throws Exception {
        List<Object> servizi = servizioDAO.selectAll();

        assertNotNull(servizi);
        assertFalse(servizi.isEmpty());

        Servizio servizio = (Servizio) servizi.getFirst();

        assertEquals(1, servizio.getId_servizio());
        assertEquals(4, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(45.2, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        servizio = (Servizio) servizi.get(1);

        assertNotNull(servizio);
        assertEquals(2, servizio.getId_servizio());
        assertEquals(2, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(12, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-05-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        servizio = (Servizio) servizi.getLast();

        assertNotNull(servizio);
        assertEquals(3, servizio.getId_servizio());
        assertEquals(2, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(10, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-06-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Servizio");
        }
        servizi = servizioDAO.selectAll();
        assertNotNull(servizi);
        assertTrue(servizi.isEmpty());
    }

    @Test
    public void testInsert() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = sdf.parse("2023-07-01");

        int id = servizioDAO.insert(new Servizio(4, 10, "", "", 5.3f, "", data, true));
        Servizio servizio = (Servizio) servizioDAO.select(id);

        assertNotNull(servizio);
        assertEquals(4, servizio.getId_servizio());
        assertEquals(10, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(5.3f, servizio.getPrezzo(), 0.01);
        assertEquals("", servizio.getCategoria());
        assertEquals("2023-07-01", new SimpleDateFormat("yyyy-MM-dd").format(servizio.getDataPubblicazione()));
        assertTrue(servizio.isVisibile());
    }

    @Test
    public void testUpdate() {
        Servizio servizioDB = (Servizio) servizioDAO.select(1);

        assertNotNull(servizioDB);

        boolean ok = servizioDAO.update(new Servizio(servizioDB.getId_servizio(), servizioDB.getId_venditore(), "", "", 20.5f, "", servizioDB.getDataPubblicazione(), servizioDB.isVisibile()));

        assertTrue(ok);

        servizioDB = (Servizio) servizioDAO.select(1);

        assertEquals(1, servizioDB.getId_servizio());
        assertEquals(4, servizioDB.getId_venditore());
        assertEquals("", servizioDB.getTitolo());
        assertEquals("", servizioDB.getDescrizione());
        assertEquals(20.5f, servizioDB.getPrezzo(), 0.01);
        assertEquals("", servizioDB.getCategoria());
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(servizioDB.getDataPubblicazione()));
        assertTrue(servizioDB.isVisibile());
    }
}
