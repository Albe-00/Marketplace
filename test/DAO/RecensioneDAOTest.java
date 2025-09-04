package DAO;

import Model.Recensione;

import org.junit.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RecensioneDAOTest {
    private static Connection connection;
    private RecensioneDAO recensioneDAO;

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
            stmt.execute("CREATE TABLE Recensione (" +
                    "id_recensione INT PRIMARY KEY AUTO_INCREMENT," +
                    "id_autore INT," +
                    "id_venditore INT," +
                    "voto FLOAT," +
                    "testo VARCHAR(50)," +
                    "data DATE DEFAULT CURRENT_DATE" +
                    ")");


            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) " +
                    "VALUES (1, 5, 3, 3.5, '')");
            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) " +
                    "VALUES (2, 1, 7, 2.1, '')");
            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) " +
                    "VALUES (3, 8, 3, 4, '')");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Recensione");
            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) VALUES (1, 5, 3, 3.5, '')");
            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) VALUES (2, 1, 7, 2.1, '')");
            stmt.execute("INSERT INTO Recensione (id_recensione, id_autore, id_venditore, voto, testo) VALUES (3, 8, 3, 4, '')");
        }
    }

    @Before
    public void initDAO() {
        recensioneDAO = new RecensioneDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testSelect() {
        Recensione recensione = (Recensione) recensioneDAO.select(1);

        assertNotNull(recensione);
        assertEquals(1, recensione. getId_recensione());
        assertEquals(5, recensione.getId_autore());
        assertEquals(3, recensione.getId_venditore());
        assertEquals(3.5, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());

        recensione = (Recensione) recensioneDAO.select(2);

        assertNotNull(recensione);
        assertEquals(2, recensione. getId_recensione());
        assertEquals(1, recensione.getId_autore());
        assertEquals(7, recensione.getId_venditore());
        assertEquals(2.1, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());

        assertNull(recensioneDAO.select(4));
    }

    @Test
    public void testSelectAll() throws Exception {
        List<Object> recensioni = recensioneDAO.selectAll();

        assertNotNull(recensioni);
        assertFalse(recensioni.isEmpty());

        Recensione recensione = (Recensione) recensioni.getFirst();
        assertEquals(1, recensione.getId_recensione());
        assertEquals(5, recensione.getId_autore());
        assertEquals(3, recensione.getId_venditore());
        assertEquals(3.5, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());

        recensione = (Recensione) recensioni.get(1);
        assertEquals(2, recensione.getId_recensione());
        assertEquals(1, recensione.getId_autore());
        assertEquals(7, recensione.getId_venditore());
        assertEquals(2.1, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Recensione");
        }
        recensioni = recensioneDAO.selectAll();
        assertNotNull(recensioni);
        assertTrue(recensioni.isEmpty());
    }

    @Test
    public void testInsert(){
        int id = recensioneDAO.insert(new Recensione(4, 10, 1.3f, ""));
        Recensione recensione = (Recensione) recensioneDAO.select(id);

        assertNotNull(recensione);
        assertEquals(4, recensione.getId_recensione());
        assertEquals(10, recensione.getId_venditore());
        assertEquals(1.3f, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());
    }

    @Test
    public void testUpdate() throws Exception {
        Recensione recensioneDB = (Recensione) recensioneDAO.select(1);

        assertNotNull(recensioneDB);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataRecensione = sdf.parse("2023-02-01");

        boolean ok = recensioneDAO.update(new Recensione(1, recensioneDB.getId_autore(), recensioneDB.getId_venditore(), 4.2f, "", dataRecensione));

        assertTrue(ok);

        recensioneDB = (Recensione) recensioneDAO.select(1);

        assertEquals(1, recensioneDB.getId_recensione());
        assertEquals(5, recensioneDB.getId_autore());
        assertEquals(3, recensioneDB.getId_venditore());
        assertEquals(4.2f, recensioneDB.getVoto(), 0.001);
        assertEquals("", recensioneDB.getTesto());
    }

    @Test
    public void testDeleteSomething(){
        assertNotNull(recensioneDAO.select(1));
        recensioneDAO.delete(1);

        assertNull(recensioneDAO.select(1));
    }

    @Test
    public void testDeleteNothing(){
        assertNull(recensioneDAO.select(4));
        recensioneDAO.delete(3);

        assertNull(recensioneDAO.select(3));
    }

    @Test
    public void testCountByVenditore(){
        assertEquals(2, recensioneDAO.countByVenditore(3));
        assertEquals(0, recensioneDAO.countByVenditore(4));
        assertEquals(1, recensioneDAO.countByVenditore(7));
    }

    @Test
    public void testSelectByVenditore(){
        List<Recensione> recensioni = recensioneDAO.selectByVenditore(3);

        assertNotNull(recensioni);
        assertFalse(recensioni.isEmpty());

        assertEquals(1, recensioni.getFirst().getId_recensione());
        assertEquals(5, recensioni.getFirst().getId_autore());
        assertEquals(3, recensioni.getFirst().getId_venditore());
        assertEquals(3.5, recensioni.getFirst().getVoto(), 0.01);
        assertEquals("", recensioni.getFirst().getTesto());

        assertEquals(3, recensioni.getLast().getId_recensione());
        assertEquals(8, recensioni.getLast().getId_autore());
        assertEquals(3, recensioni.getLast().getId_venditore());
        assertEquals(4, recensioni.getLast().getVoto(), 0.01);
        assertEquals("", recensioni.getLast().getTesto());

        recensioni = recensioneDAO.selectByVenditore(7);

        assertNotNull(recensioni);
        assertFalse(recensioni.isEmpty());

        assertEquals(2, recensioni.getFirst().getId_recensione());
        assertEquals(1, recensioni.getFirst().getId_autore());
        assertEquals(7, recensioni.getFirst().getId_venditore());
        assertEquals(2.1, recensioni.getFirst().getVoto(), 0.01);
        assertEquals("", recensioni.getFirst().getTesto());
    }
}
