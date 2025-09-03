package AllTests;

import DAO.UtenteDAOTest;
import DAO.VenditoreDAOTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtenteDAOTest.class,
        VenditoreDAOTest.class
})
public class UtenteVenditoreTestOrder {
    //necessario per eseguire utentedaotest e venditoredaotest secondo un certo ordine altrimenti vanno in conflitto le tabelle dei due test
}