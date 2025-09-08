package Controller;

import static org.junit.Assert.*;

import DAO.DatabaseConnection;
import DAO.ServizioDAO;

import Model.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ControllerVenditoreTest {

    static DatabaseConnection db;
    private ControllerVenditore controllerVenditore;
    private ServizioDAO servizioDAO;

    @BeforeClass
    public static void setupDatabase() throws Exception {

        Class.forName("org.h2.Driver");
        db = DatabaseConnection.getInstance();
        setPrivateField(db, "URL", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
    }

    @Before
    public void resetTable() throws SQLException {
        db = DatabaseConnection.getInstance();
        try (Connection conn = db.getConnection(); Statement stmt = conn.createStatement()) {
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

            stmt.execute("INSERT INTO Ordine (id_cliente, id_servizio, prezzo, data_ordine, data_consegna, stato_ordine) VALUES (1, 1, 50.5, '2023-01-01', '2023-01-10', 'IN LAVORAZIONE')");
            stmt.execute("INSERT INTO Ordine (id_cliente, id_servizio, prezzo, data_ordine, data_consegna, stato_ordine) VALUES (1, 1, 50.5, '2023-01-01', '2023-01-10', 'IN ATTESA')");
            stmt.execute("INSERT INTO Ordine (id_cliente, id_servizio, prezzo, data_ordine, data_consegna, stato_ordine) VALUES (1, 1, 50.5, '2023-01-01', '2023-01-10', 'IN ATTESA')");

            stmt.execute("INSERT INTO Servizio (id_venditore, titolo, descrizione, prezzo, categoria, data_pubblicazione, visibile) VALUES (2, '', '', 50.5, '', '2023-01-01', true)");
        }
    }

    @Before
    public void init() {
        ControllerBase controllerBase = ControllerBase.getInstance();
        controllerBase.login("paolobianchi@example.com", "pwd2");
        controllerVenditore = new ControllerVenditore();
        servizioDAO = new ServizioDAO();
    }

    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = DatabaseConnection.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testVisualizzaModificaProfilo(){
        controllerVenditore.visualizzaProfilo();
        controllerVenditore.modificaDescrizione("modificata", "pwd2");
        controllerVenditore.visualizzaProfilo();
    }

    @Test
    public void testModificaServizio() {
        controllerVenditore.modificaNomeServizio(1, "modificato", "pwd2");
        controllerVenditore.modificaDescrizioneServizio(1, "modificata", "pwd2");
        controllerVenditore.modificaPrezzoServizio(1, 40.f, "pwd2");
        controllerVenditore.modificaCategoriaServizio(1, "modificato", "pwd2");
        controllerVenditore.modificaVisibilitaServizio(1, false, "pwd2");

        List<Servizio> servizi = servizioDAO.selectByVenditore(2);
        assertEquals(1, servizi.size());
        Servizio servizio = servizi.getFirst();
        assertEquals("modificato", servizio.getTitolo());
        assertEquals("modificata", servizio.getDescrizione());
        assertEquals(40.f, servizio.getPrezzo(), 0.01);
        assertEquals("modificato", servizio.getCategoria());
        assertFalse(servizio.isVisibile());
    }

    @Test
    public void testCreaEliminaServizio() {
        assertEquals(1, controllerVenditore.getNumeroServiziVenditore());

        boolean result = controllerVenditore.creaServizio("Nuovo", "Nuovo", 30.f, "Nuova", true);
        assertTrue(result);

        assertEquals(2, controllerVenditore.getNumeroServiziVenditore());

        result = controllerVenditore.eliminaServizio(1, "pwd2");
        assertTrue(result);

        assertEquals(1, controllerVenditore.getNumeroServiziVenditore());
    }

    @Test
    public void testGestioneOrdini() {
        assertEquals(3, controllerVenditore.recuperaOrdiniRicevuti().size());

        assertEquals(2, controllerVenditore.recuperaOrdiniInAttesa().size());

        assertEquals(1, controllerVenditore.recuperaOrdiniInLavorazione().size());

        assertFalse(controllerVenditore.iniziaOrdine(1));
        assertTrue(controllerVenditore.iniziaOrdine(2));

        assertFalse(controllerVenditore.rifiutaOrdine(2));
        assertTrue(controllerVenditore.rifiutaOrdine(3));

        assertFalse(controllerVenditore.completaOrdine(3));
        assertTrue(controllerVenditore.completaOrdine(1));

        assertEquals(0, controllerVenditore.recuperaOrdiniInAttesa().size());

        assertEquals(1, controllerVenditore.recuperaOrdiniInLavorazione().size());
    }

    @Test
    public void testRecuperaRecensioni() {
        List<Recensione> recensioni = controllerVenditore.recuperaRecensioniRicevute();
        assertEquals(1, recensioni.size());
        Recensione recensione = recensioni.getFirst();
        assertEquals(1, recensione.getId_autore());
        assertEquals(2, recensione.getId_venditore());
        assertEquals(3.4, recensione.getVoto(), 0.01);
        assertEquals("", recensione.getTesto());
    }
}
