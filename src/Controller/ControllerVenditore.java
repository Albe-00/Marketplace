package Controller;

import DAO.UtenteDAO;
import DAO.VenditoreDAO;
import Model.Utente;
import Model.Venditore;
import java.util.List;


public class ControllerVenditore extends ControllerUtente {
    private Venditore venditore;

    public ControllerVenditore(Venditore venditore) {
        super(venditore);
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        this.venditore = venditore;
    }
    @Override
    public void visualizzaProfilo() {
        venditore.stampa();
    }



}


