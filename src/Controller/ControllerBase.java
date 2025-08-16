package Controller;

import DAO.*;
import Model.Utente;

import java.util.List;

// SINGLETON
public class ControllerBase {
    private static ControllerBase instance;
    private Utente utenteCorrente;
    private ControllerBase() {
        // Inizializza la connessione al database
        utenteCorrente = null;
    }
    public static ControllerBase getInstance() {
        if (instance == null) {
            instance = new ControllerBase();
        }
        return instance;
    }
    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }
    public void setUtenteCorrente(Utente utente) {
        this.utenteCorrente = utente;
    }

    public boolean isUtenteVenditore(){
        return utenteCorrente.isVenditore();
    }
    public Utente register(Utente utente) {
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        for (Object obj : utenti) {
            Utente userIterator = (Utente) obj;
            if (userIterator.getEmail().equals(utente.getEmail())) {
                return null;
            }
        }
        if(utenteDAO.insert(utente)) {
            this.utenteCorrente = utente;
            return utente;
        }
        return null;
    }
    public Utente login(String email, String password) {
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        for (Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getEmail().equals(email) && utente.getPassword().equals(password)) {
                this.utenteCorrente = utente;
                return utente;
            }
        }
        return null;
    }
    public void logout() {
        this.utenteCorrente = null;
    }
}
