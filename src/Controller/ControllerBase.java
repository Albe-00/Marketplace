package Controller;

import DAO.*;
import Model.Utente;
import Model.Venditore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// SINGLETON
public class ControllerBase {
    private static ControllerBase instance = null;
    private Utente utenteCorrente;

    //SINGLETON
    private ControllerBase() {
        // Inizializza la connessione al database
        utenteCorrente = null;
        if(!isConnectionValid())
            System.exit(1);     //Status 1 = Connessione col server non valida
    }
    public static ControllerBase getInstance() {
        if (instance == null) {
            instance = new ControllerBase();
        }
        return instance;
    }
    public boolean isConnectionValid(){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                System.out.println("Errore durante la chiusura della connessione");
                return false;
            }
            return true;
        }
        return false;
    }

    public int getIdUtenteCorrente(){
        return utenteCorrente.getId();
    }
    public boolean isUtenteLoggato(){return utenteCorrente != null;}
    public boolean isUtenteCorrenteVenditore(){
        return utenteCorrente.isVenditore();
    }
    public boolean registerUtente(Utente nuovoUtente) {
        // Controlla se l'email è già in uso
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();
        //Controllo se email è già utilizzata
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
        // Prima registra il nuovo utente nella tebella Utente
        if( registerUtente(nuovoVenditore) ) {
            nuovoVenditore.setId(utenteCorrente.getId()); // Imposta l'ID del venditore con quello dell'utente appena registrato nel database

            // Poi lo registra anche nella tabella Venditore e imposta il nuovo utenteCorrente con un oggetto Venditore
            VenditoreDAO venditoreDAO = new VenditoreDAO();

            return venditoreDAO.insert(nuovoVenditore)!= -1;    //Return true se nuovo venditore aggiunto nella tabella Venditore del database , false altrimenti
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
            if (utente.getEmail().equals(email) && utente.isEqualPassword(password)) {
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
