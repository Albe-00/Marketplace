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
        if (utenteCorrente.isVenditore())
            System.out.println("Utente corrente è un venditore." + utenteCorrente.isVenditore());
        else
            System.out.println("Utente corrente non è un venditore."+ utenteCorrente.isVenditore());

        return utenteCorrente.isVenditore();
    }
    public Utente register() {
        // fixme implementare la logica di registrazione
        return null;
    }
    public Utente login(String email, String password) {
        // fixme implementare la logica di autenticazione
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        for (Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getEmail().equals(email) && utente.getPassword().equals(password)) {
                this.utenteCorrente = utente;
                System.out.println("🔑 Login effettuato con successo!");
                return utente;
            }
        }
        System.out.println("❌ Credenziali non valide. Riprova.");
        return null;
    }
    public void logout() {
        this.utenteCorrente = null;
        System.out.println("🔒 Logout effettuato con successo!");
    }
}
