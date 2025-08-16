package Controller;

import DAO.VenditoreDAO;
import Model.Utente;
import Model.Venditore;
import java.util.List;


public class ControllerVenditore extends ControllerUtente {
    private Venditore venditore;

    public ControllerVenditore(Utente utente) {
        super(utente);
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        this.venditore = (Venditore) venditoreDAO.select(utente.getId());
    }

    @Override
    public void visualizzaProfilo() {
        venditore.stampa();
    }



}


