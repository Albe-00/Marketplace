package Controller;

import static org.junit.Assert.*;

import DAO.DatabaseConnection;
import DAO.UtenteDAO;
import DAO.VenditoreDAO;

import Model.*;

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

public class ControllerUtenteTest {

    private static Connection connection;
    private ControllerBase controllerBase;
    private ControllerUtente controllerUtente;
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
            stmt.execute("CREATE TABLE Servizio (" +
                    "id_servizio INT PRIMARY KEY AUTO_INCREMENT ," +
                    "id_venditore INT," +
                    "titolo VARCHAR(50)," +
                    "descrizione VARCHAR(50)," +
                    "prezzo FLOAT," +
                    "categoria VARCHAR(50)," +
                    "data_pubblicazione DATE," +
                    "visibile BOOLEAN)");

            stmt.execute("CREATE TABLE Ordine (" +
                    "id_ordine INT PRIMARY KEY AUTO_INCREMENT ," +
                    "id_cliente INT," +
                    "id_servizio INT," +
                    "prezzo FLOAT," +
                    "data_ordine DATE," +
                    "data_consegna DATE," +
                    "stato_ordine VARCHAR(50))");

            stmt.execute("CREATE TABLE Recensione (" +
                    "id_recensione INT PRIMARY KEY AUTO_INCREMENT," +
                    "id_autore INT," +
                    "id_venditore INT," +
                    "voto FLOAT," +
                    "testo VARCHAR(50)," +
                    "data DATE DEFAULT CURRENT_DATE" +
                    ")");
        }
    }

    @Before
    public void resetTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Utente");
            stmt.execute("ALTER TABLE Utente ALTER COLUMN id_utente RESTART WITH 1");
            stmt.execute("TRUNCATE TABLE Venditore");
            stmt.execute("TRUNCATE TABLE Servizio");
            stmt.execute("ALTER TABLE Servizio ALTER COLUMN id_servizio RESTART WITH 1");
            stmt.execute("TRUNCATE TABLE Recensione");
            stmt.execute("ALTER TABLE Recensione ALTER COLUMN id_recensione RESTART WITH 1");
            stmt.execute("TRUNCATE TABLE Ordine");
            stmt.execute("ALTER TABLE Ordine ALTER COLUMN id_ordine RESTART WITH 1");

            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Mario', 'Rossi', 'mariorossi@example.com', '0123456789', 'pwd', false)");
            stmt.execute("INSERT INTO Utente (nome, cognome, email, telefono, password, venditore) VALUES ('Paolo', 'Bianchi', 'paolobianchi@example.com', '0123456789', 'pwd2', true)");

            stmt.execute("INSERT INTO Venditore VALUES (2, '', 3.4)");

            stmt.execute("INSERT INTO Recensione (id_autore, id_venditore, voto, testo) VALUES (1, 2, 3.4, '')");

            stmt.execute("INSERT INTO Ordine (id_cliente, id_servizio, prezzo, data_ordine, data_consegna, stato_ordine) VALUES (1, 1, 50.5, '2023-01-01', '2023-01-10', 'IN_CORSO')");

            stmt.execute("INSERT INTO Servizio (id_venditore, titolo, descrizione, prezzo, categoria, data_pubblicazione, visibile) VALUES (2, '', '', 50.5, '', '2023-01-01', true)");
        }
    }

    @Before
    public void init() {
        controllerBase = ControllerBase.getInstance();
        controllerBase.login("mariorossi@example.com", "pwd");
        controllerUtente = new ControllerUtente();
        utenteDAO = new UtenteDAO();
        venditoreDAO = new VenditoreDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testModificaProfilo() {
        controllerUtente.modificaNome("Luigi", "pwd");
        controllerUtente.modificaCognome("Verdi", "pwd");
        controllerUtente.modificaEmail("mariorossi@example.com", "pwd");
        controllerUtente.modificaEmail("luigiverdi@example.com", "pwd");
        controllerUtente.modificaPassword("pwd", "pwd3");
        controllerUtente.modificaTelefono("1021651", "pwd"); //test sul controllo della password
        controllerUtente.modificaTelefono("1021651", "pwd3");

        Utente utente = (Utente) utenteDAO.select(1);   //lo estraggo dal database per testare i DAO

        assertEquals(1, utente.getId());
        assertEquals("Luigi", utente.getNome());
        assertEquals("Verdi", utente.getCognome());
        assertEquals("pwd3", utente.getPassword());
        assertEquals("luigiverdi@example.com", utente.getEmail());
        assertEquals("1021651", utente.getTelefono());
    }

    @Test
    public void testDiventaVenditore() {
        boolean result = controllerUtente.diventaVenditore("", "pwd");
        assertTrue(result);

        Venditore venditore = (Venditore) venditoreDAO.select(1);
        assertEquals(1, venditore.getId());
        assertEquals("Mario", venditore.getNome());
        assertEquals("Rossi", venditore.getCognome());
        assertEquals("pwd", venditore.getPassword());
        assertEquals("mariorossi@example.com", venditore.getEmail());
    }

    @Test
    public void testVisualizzaProfilo(){
        controllerUtente.visualizzaProfilo(1);
        controllerUtente.visualizzaProfilo(2);
    }

    @Test
    public void testCerca(){
        controllerUtente.diventaVenditore("", "pwd");
        List<Venditore> venditori = controllerUtente.cercaVenditori("Mario");
        assertEquals(1, venditori.size());
        Venditore venditore = venditori.getFirst();
        assertEquals("Mario", venditore.getNome());
        assertEquals("Rossi", venditore.getCognome());
        assertEquals("pwd", venditore.getPassword());
        assertEquals("mariorossi@example.com", venditore.getEmail());

        venditori = controllerUtente.cercaVenditori("Paolo");
        assertEquals(1, venditori.size());
        venditore = venditori.getFirst();
        assertEquals("Paolo", venditore.getNome());
        assertEquals("Bianchi", venditore.getCognome());
        assertEquals("pwd2", venditore.getPassword());
        assertEquals("paolobianchi@example.com", venditore.getEmail());

        List<Servizio> servizi = controllerUtente.cercaServizi("");
        assertEquals(1, servizi.size());
        Servizio servizio = servizi.getFirst();
        assertEquals(1, servizio.getId_servizio());
        assertEquals(2, servizio.getId_venditore());
        assertEquals("", servizio.getTitolo());
        assertEquals("", servizio.getDescrizione());
        assertEquals(50.5, servizio.getPrezzo(), 0.01);
    }

    @Test
    public void testGestioneOrdini(){
        controllerUtente.effettuaOrdine(1);

        List<Ordine> cronologia = controllerUtente.recuperaOrdiniEffettuati();
        assertEquals(2, cronologia.size());

        Ordine ordine = cronologia.getFirst();
        assertEquals(1, ordine.getId_ordine());
        assertEquals(1, ordine.getId_cliente());
        assertEquals(1, ordine.getId_servizio());
        assertEquals(50.5, ordine.getPrezzo(), 0.01);

        ordine = cronologia.getLast();
        assertEquals(2, ordine.getId_ordine());
        assertEquals(1, ordine.getId_cliente());
        assertEquals(1, ordine.getId_servizio());
        assertEquals(50.5, ordine.getPrezzo(), 0.01);

        cronologia = controllerUtente.recuperaOrdiniInAttesa();
        assertEquals(1, cronologia.size());

        controllerUtente.annullaOrdine(2);

        cronologia = controllerUtente.recuperaOrdiniInAttesa();
        assertEquals(0, cronologia.size());
    }

    @Test
    public void testGestioneRecensioni(){
        controllerUtente.effettuaRecensione(2, 3.4f, "");

        List<Recensione> recensioni = controllerUtente.recuperaRecensioniVenditore(2);
        assertEquals(2, recensioni.size());
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
