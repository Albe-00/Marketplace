package Model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class OrdineTest {

    @Test
    public void testNuovoStatoNull() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine(null);
        assertEquals("IN ATTESA", ordine.getStatoOrdine());
    }

    @Test
    public void testNuovoStatoVuoto() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine("");
        assertEquals("IN ATTESA", ordine.getStatoOrdine());
    }

    @Test
    public void testNuovoStatoNonValido() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine("SPEDITO");
        assertEquals("IN ATTESA", ordine.getStatoOrdine());
    }

    @Test
    public void testOrdineGiaCompletato() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine("IN LAVORAZIONE");
        ordine.setStatoOrdine("COMPLETATO");
        ordine.setStatoOrdine("RIFIUTATO");
        assertEquals("COMPLETATO", ordine.getStatoOrdine());
    }

    @Test
    public void testDaInAttesaANonConsentito() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine("COMPLETATO");
        assertEquals("IN ATTESA", ordine.getStatoOrdine());
    }

    @Test
    public void testDaInLavorazioneANonConsentito() {
        Ordine ordine = new Ordine(1, 2, 100.0f, new Date());
        ordine.setStatoOrdine("IN LAVORAZIONE");
        ordine.setStatoOrdine("RIFIUTATO");
        assertEquals("IN LAVORAZIONE", ordine.getStatoOrdine());
    }
}

