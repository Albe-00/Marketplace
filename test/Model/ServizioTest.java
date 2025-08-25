package Model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Date;

public class ServizioTest {
    @Test
    public void testServizioConstructor() {
        Servizio servizio = new Servizio(1, 1, "", "", -10.f, "", new Date(System.currentTimeMillis()), true);
        assertEquals(0.f, servizio.getPrezzo(), 0.0001);
    }

    @Test
    public void testServizioOtherConstructor() {
        Servizio servizio = new Servizio(1, 1, "", "", -10.f, "",true);
        assertEquals(0.f, servizio.getPrezzo(), 0.0001);
    }
}
