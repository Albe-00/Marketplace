package DAO;

import org.junit.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import Model.Ordine;

public class OrdineDAOTest {

    private static Connection connection;
    private OrdineDAO ordineDAO;

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
            stmt.execute("CREATE TABLE ordine (" +
                    "id_ordine INT PRIMARY KEY AUTO_INCREMENT ," +
                    "id_cliente INT," +
                    "id_servizio INT," +
                    "prezzo FLOAT," +
                    "data_ordine DATE," +
                    "data_consegna DATE," +
                    "stato_ordine VARCHAR(50))");

            stmt.execute("CREATE TABLE Servizio (" +
                    "id_servizio INT PRIMARY KEY," +
                    "id_venditore INT," +
                    "titolo VARCHAR(50)," +
                    "descrizione VARCHAR(50)," +
                    "prezzo FLOAT," +
                    "categoria VARCHAR(50)," +
                    "dataPubblicazione DATE," +
                    "visibile BOOLEAN" +
                    ")");

            stmt.execute("INSERT INTO ordine VALUES " +
                    "(1, 100, 200, 50.5, '2023-01-01', '2023-01-10', 'IN_CORSO')");
            stmt.execute("INSERT INTO ordine VALUES " +
                    "(2, 10, 2, 10.4, '2020-01-01', '2020-01-10', 'IN_CORSO')");
            stmt.execute("INSERT INTO Servizio VALUES " +
                    "(200, 15, '', '', 50.5, '', '2015-03-01', TRUE)");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE ordine");
            stmt.execute("TRUNCATE TABLE Servizio");
            stmt.execute("INSERT INTO ordine VALUES (1, 100, 200, 50.5, '2023-01-01', '2023-01-10', 'IN_CORSO')");
            stmt.execute("INSERT INTO ordine VALUES (2, 10, 2, 10.4, '2020-01-01', '2020-01-10', 'IN_CORSO')");
            stmt.execute("INSERT INTO Servizio VALUES (200, 15, '', '', 50.5, '', '2015-03-01', TRUE)");
        }
    }

    @Before
    public void initDAO() {
        ordineDAO = new OrdineDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSelectOrdineEsistente() {
        Ordine ordine = (Ordine) ordineDAO.select(1);

        assertNotNull(ordine);
        assertEquals(1, ordine.getId_ordine());
        assertEquals(100, ordine.getId_cliente());
        assertEquals(200, ordine.getId_servizio());
        assertEquals(50.5f, ordine.getPrezzo(), 0.001);
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataOrdine()));
        assertEquals("2023-01-10", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataConsegna()));
        assertEquals("IN_CORSO", ordine.getStatoOrdine());
    }

    @Test
    public void testSelectOrdineNonEsistente() {
        Ordine ordine = (Ordine) ordineDAO.select(999);
        assertNull(ordine);
    }

    @Test
    public void testSelectAllData(){
        List<Object> ordini = ordineDAO.selectAll();

        assertNotNull(ordini);
        assertFalse(ordini.isEmpty());

        Ordine ordine = (Ordine) ordini.getFirst();
        assertEquals(1, ordine.getId_ordine());
        assertEquals(100, ordine.getId_cliente());
        assertEquals(200, ordine.getId_servizio());
        assertEquals(50.5f, ordine.getPrezzo(), 0.001);
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataOrdine()));
        assertEquals("2023-01-10", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataConsegna()));
        assertEquals("IN_CORSO", ordine.getStatoOrdine());

        ordine = (Ordine) ordini.getLast();
        assertEquals(2, ordine.getId_ordine());
        assertEquals(10, ordine.getId_cliente());
        assertEquals(2, ordine.getId_servizio());
        assertEquals(10.4f, ordine.getPrezzo(), 0.001);
        assertEquals("2020-01-01", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataOrdine()));
        assertEquals("2020-01-10", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataConsegna()));
        assertEquals("IN_CORSO", ordine.getStatoOrdine());
    }

    @Test
    public void testSelectAllNoData() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE ordine");
        }
        List<Object> ordini = ordineDAO.selectAll();
        assertNotNull(ordini);
        assertTrue(ordini.isEmpty());
    }

    @Test
    public void testInsertOrdine() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataOrdine = sdf.parse("2023-02-01");
        Date dataConsegna = sdf.parse("2023-02-10");

        Ordine ordine = new Ordine(3, 15, 5, 30.f, dataOrdine, dataConsegna, "IN_CORSO");
        int id = ordineDAO.insert(ordine);

        assertTrue(id > 0);

        Ordine ordineDB = (Ordine) ordineDAO.select(id);

        assertEquals(3, ordineDB.getId_ordine());
        assertEquals(15, ordineDB.getId_cliente());
        assertEquals(5, ordineDB.getId_servizio());
        assertEquals(30.f, ordineDB.getPrezzo(), 0.001);
        assertEquals("2023-02-01", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataOrdine()));
        assertEquals("2023-02-10", new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataConsegna()));
        assertEquals("IN_CORSO", ordine.getStatoOrdine());
    }

    @Test
    public void testUpdate(){
        Ordine ordineDB = (Ordine) ordineDAO.select(1);

        assertNotNull(ordineDB);

        boolean ok = ordineDAO.update(new Ordine(ordineDB.getId_ordine(), ordineDB.getId_cliente(), 7, 45.f, ordineDB.getDataOrdine(), ordineDB.getDataConsegna(), ordineDB.getStatoOrdine()));

        assertTrue(ok);

        ordineDB = (Ordine) ordineDAO.select(1);

        assertEquals(1, ordineDB.getId_ordine());
        assertEquals(100, ordineDB.getId_cliente());
        assertEquals(7, ordineDB.getId_servizio());
        assertEquals(45.f, ordineDB.getPrezzo(), 0.001);
        assertEquals("2023-01-01", new SimpleDateFormat("yyyy-MM-dd").format(ordineDB.getDataOrdine()));
        assertEquals("2023-01-10", new SimpleDateFormat("yyyy-MM-dd").format(ordineDB.getDataConsegna()));
        assertEquals("IN_CORSO", ordineDB.getStatoOrdine());
    }

    @Test
    public void testDeleteSomething(){
        assertNotNull(ordineDAO.select(1));
        ordineDAO.delete(1);

        assertNull(ordineDAO.select(1));
    }

    @Test
    public void testDeleteNothing(){
        assertNull(ordineDAO.select(3));
        ordineDAO.delete(3);

        assertNull(ordineDAO.select(3));
    }

    @Test
    public void testSelectByCliente(){
        assertFalse(ordineDAO.selectByCliente(10).isEmpty());
        assertFalse(ordineDAO.selectByCliente(100).isEmpty());
        assertTrue(ordineDAO.selectByCliente(1).isEmpty());
    }

    @Test
    public void testSelectByVenditore(){
        assertFalse(ordineDAO.selectByVenditore(15).isEmpty());
        assertTrue(ordineDAO.selectByVenditore(1).isEmpty());
    }

    @Test
    public void testSelectByVenditoreAndStato(){
        assertFalse(ordineDAO.selectByVenditoreAndStato(15, "IN_CORSO").isEmpty());
        assertTrue(ordineDAO.selectByVenditoreAndStato(1, "IN_CORSO").isEmpty());
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

