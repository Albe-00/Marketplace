package Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class RecensioneTest {
    @Test
    public void testRecensioneConstructor() {
        Recensione recensione = new Recensione(1, 1, 1, 12, "");
        assertEquals(8, recensione.getVoto());
    }

    @Test
    public void testRecensioneOtherConstructor() {
        Recensione recensione = new Recensione(1, 1, 12.f, "");
        assertEquals(8, recensione.getVoto());
    }
}
