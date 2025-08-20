package Controller;

import DAO.*;
import Model.Utente;
import Model.Venditore;

import java.util.List;

// SINGLETON
public class ControllerBase {
    private static ControllerBase instance = null;
    private Utente utenteCorrente;

    //SINGLETON
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

    //fixme vorrei togliere getterUtenteCorrente
    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }
    public boolean isUtenteCorrenteVenditore(){
        return utenteCorrente.isVenditore();
    }
    public boolean registerUtente(Utente nuovoUtente) {
        // Controlla se l'email è già in uso
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        for (Object obj : utenti) {
            Utente userIterator = (Utente) obj;
            if (userIterator.getEmail().equals(nuovoUtente.getEmail())) {
                return false;
            }
        }
        // Se l'email non è in uso, inserisce l'utente nel database
        int idNuovoUtente = utenteDAO.insert(nuovoUtente);           //insert restituisce l'id dell'utente inserito, se l'inserimento va a buon fine
        if (idNuovoUtente == -1) {
            return false; // Errore nell'inserimento
        }else{
            // Imposta l'ID dell'oggetto utenteCorrente con la chiave primaria generata in autonmia dal database
            nuovoUtente.setId(idNuovoUtente);
            this.utenteCorrente = nuovoUtente;
            return true;
        }
    }
    public boolean registerVenditore(Venditore nuovoVenditore) {
        Utente utenteDaRegistrare = new Utente(
                nuovoVenditore.getId(),
                nuovoVenditore.getNome(),
                nuovoVenditore.getCognome(),
                nuovoVenditore.getEmail(),
                nuovoVenditore.getPassword(),
                nuovoVenditore.getTelefono(),
                true // Imposta come venditore
        );
        // Prima registra il nuovo utente nella tebella Utente
        if( registerUtente(utenteDaRegistrare) ) {
            nuovoVenditore.setId(utenteCorrente.getId()); // Imposta l'ID del venditore con quello dell'utente appena registrato

            // Poi lo registra anche nella tabella Venditore e imposta il nuovo utenteCorrente con un oggetto Venditore
            VenditoreDAO venditoreDAO = new VenditoreDAO();

            if(venditoreDAO.insert(nuovoVenditore)!= -1) {
                this.utenteCorrente = nuovoVenditore;
                return true;
            }

        }
        return false; // Registrazione fallita
    }
    public boolean login(String email, String password) {
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        // Scorre la lista di utenti per verificare le credenziali
        for (Object obj : utenti) {
            Utente utente = (Utente) obj;
            // Controlla se l'email e la password corrispondono
            if (utente.getEmail().equals(email) && utente.getPassword().equals(password)) {
                // Credenziali valide, imposta l'utente corrente
                if(utente.isVenditore()) {
                    // Se l'utente è un venditore, recupera i dettagli del venditore e imposta l'utente corrente come Venditore
                    VenditoreDAO venditoreDAO = new VenditoreDAO();
                    this.utenteCorrente = (Venditore) venditoreDAO.select(utente.getId());
                } else {
                    // Altrimenti, imposta l'utente corrente come Utente
                    this.utenteCorrente = utente;
                }
                return true;
            }
        }
        return false; // Credenziali non valide
    }
    public void logout() {
        this.utenteCorrente = null;
    }
}
