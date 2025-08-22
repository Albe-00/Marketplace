package Controller;

import Model.*;
import DAO.*;

import java.util.List;

public class ControllerUtente {
    protected Utente utenteCorrente;
    public ControllerUtente() {
        // Inizializza il controller per l'utente
        int idUtenteCorrente = ControllerBase.getInstance().getIdUtenteCorrente();
        UtenteDAO utenteDAO= new UtenteDAO();
        this.utenteCorrente = (Utente) utenteDAO.select(idUtenteCorrente);
    }

    // visualizza le informazioni dell'utente corrente
    public void visualizzaProfilo() {
        utenteCorrente.stampa();
    }
    public String getNome() {
        return utenteCorrente.getNome();
    }

    // visualizza le informazioni di un utenteSpecifico
    public void visualizzaProfilo(int id_utente){
        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utenteDavisualizzare = (Utente) utenteDAO.select(id_utente);

        // Se utente scelto non esiste
        if(utenteDavisualizzare == null){
            System.out.println("Utente indicato non esiste");
            return;
        }

        //Se è un venditore
        if(utenteDavisualizzare.isVenditore()){

            utenteDavisualizzare.stampa();
            // Stampo i servizi che eroga
            List<Servizio> serviziVenditore = recuperaServiziVisibiliVenditore(utenteDavisualizzare.getId());
            if (serviziVenditore.isEmpty()) {
                System.out.println("NESSUN SERVIZIO DISPONIBILE");
            } else {
                System.out.println("SERVIZI :");
                for (Servizio servizio : serviziVenditore) {
                    servizio.stampa();
                }
            }

            // Stampa le recensioni dell'utente
            System.out.println("------------------------------------------");
            List<Recensione> recensioniVenditore = recuperaRecensioniVenditore(utenteDavisualizzare.getId());
            if (recensioniVenditore.isEmpty()) {
                System.out.println("NESSUNA RECENSIONE DISPONIBILE");
            } else {
                System.out.println("RECENSIONI :");
                for (Recensione recensione : recensioniVenditore) {
                    recensione.stampa();
                }
            }
            System.out.println("------------------------------------------");
        }else{
            System.out.println("L'utente indicato non è un venditore.");
        }

    }

    // Metodi per la modifica dellle informazioni dell'utente corrente
    public boolean modificaNome(String nuovoNome , String password) {
        if(utenteCorrente.isEqualPassword(password) ){
            utenteCorrente.setNome(nuovoNome);
            UtenteDAO utenteDAO= new UtenteDAO();
            utenteDAO.update(utenteCorrente);
            return true; // Nome modificato con successo
        }

        return false; // La password non corrisponde
    }
    public boolean modificaCognome(String nuovoCognome , String password) {
        if( utenteCorrente.isEqualPassword(password) ){
            utenteCorrente.setCognome(nuovoCognome);
            UtenteDAO utenteDAO= new UtenteDAO();
            utenteDAO.update(utenteCorrente);
            return true; // Cognome modificato con successo
        }

        return false; // La password non corrisponde
    }
    public boolean modificaEmail(String nuovaEmail , String password) {
        if( !utenteCorrente.isEqualPassword(password) )
            return false; // La password non corrisponde

        // Controlla se l'email è già in uso
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();

        for (Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getEmail().equals(nuovaEmail) && utente.getId() != utenteCorrente.getId())
                return false; // Email già in uso

        }

        // Se l'email non è in uso, aggiorna l'utente corrente
        utenteCorrente.setEmail(nuovaEmail);
        utenteDAO.update(utenteCorrente);
        return true; // Email modificata con successo
    }
    public boolean modificaPassword(String vecchiaPassword, String nuovaPassword) {
        if(utenteCorrente.isEqualPassword(vecchiaPassword)) {
            utenteCorrente.setPassword(nuovaPassword);
            UtenteDAO utenteDAO = new UtenteDAO();
            return utenteDAO.updatePassword(utenteCorrente.getId(), nuovaPassword);
        }
        return false; // La vecchia password non corrisponde
    }
    public boolean modificaTelefono(String nuovoTelefono , String password) {
        if( !utenteCorrente.isEqualPassword(password) ){
            utenteCorrente.setTelefono(nuovoTelefono);
            UtenteDAO utenteDAO= new UtenteDAO();
            utenteDAO.update(utenteCorrente);
            return true; // Telefono modificato con successo
        }

        return false; // La password non corrisponde
    }

    // Metodi per la gestione di servizi
    public List<Servizio> cercaServizi(String ricerca) {
        ServizioDAO servizioDAO = new ServizioDAO();
        return servizioDAO.cercaServizi(ricerca);
    }
    private List<Servizio> recuperaServiziVisibiliVenditore(int id_venditore){
        ServizioDAO servizioDAO = new ServizioDAO();
        return servizioDAO.selectServiziVisibiliByVenditore(id_venditore);
    }

    // Metodi per la gestione dei venditori
    public List<Venditore> cercaVenditori(String ricerca) {
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        return venditoreDAO.cercaVenditori(ricerca);
    }

    // Metodi per la gestione degli ordini
    public List<Ordine> recuperaOrdiniEffettuati() {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.selectByCliente(utenteCorrente.getId());
    }
    public boolean effettuaOrdine(int idServizio) {
        ServizioDAO servizioDAO = new ServizioDAO();
        OrdineDAO ordineDAO = new OrdineDAO();

        // Controllo la presenza del servizio
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.isVisibile()) {
            // Creo un nuovo ordine
            Ordine nuovoOrdine = new Ordine(utenteCorrente.getId(), idServizio, new java.util.Date());
            // Salvo l'ordine nel database
            return ordineDAO.insert(nuovoOrdine) != -1; // Ordine effettuato con successo il metodo insert restituisce un ID positivo
        }
        return false; // Servizio non trovato o non visibile
    }

    // Metodi per la gestione delle recensioni
    public boolean effettuaRecensione(int idVenditore, int voto, String testo) {
        // Controllo che il voto sia compreso tra 1 e 5
        if (voto < 1 || voto > 5)
            return false;

        RecensioneDAO recensioneDAO = new RecensioneDAO();
        VenditoreDAO venditoreDAO = new VenditoreDAO();

        //Controllo la presenza del venditore
        Venditore venditore = (Venditore) venditoreDAO.select(idVenditore);
        if (venditore != null) {
            // Creo la recensione
            Recensione nuovaRecensione = new Recensione(utenteCorrente.getId(), idVenditore, voto, testo);

            int numeroRecensioni = recensioneDAO.countByVenditore(idVenditore);

            // Salvo la recensione nel database
            if( recensioneDAO.insert(nuovaRecensione) > 0){
                // Aggiorno il rating del venditore
                float ratingAggiornato = (venditore.getRating() * numeroRecensioni + voto) / (numeroRecensioni + 1);
                venditore.setRating(ratingAggiornato); // Aggiorno il rating
                venditoreDAO.update(venditore); // Salvo le modifiche al venditore
                return true; // Recensione effettuata con successo
            }
        }
        return false; // Venditore non trovato o errore nell'inserimento della recensione
    }
    protected List<Recensione> recuperaRecensioniVenditore(int id_venditore){
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        return recensioneDAO.selectByVenditore(id_venditore);
    }

    // Funzione per diventare venditore
    public boolean diventaVenditore(String descrizione, String password) {
        // Controllo se l'utente corrente è già un venditore
        if ( utenteCorrente.isVenditore() || !utenteCorrente.isEqualPassword(password))
            return false; // L'utente è già un venditore o password errata


        VenditoreDAO venditoreDAO = new VenditoreDAO();
        UtenteDAO utenteDAO = new UtenteDAO();

        // Creo un nuovo oggetto Venditore con i dati dell'utente corrente e la descrizione
        Venditore nuovoVenditore = new Venditore(utenteCorrente, descrizione);

        // Inserisco il nuovo venditore nel database
        int idNuovoVenditore = venditoreDAO.insert(nuovoVenditore);


        if (idNuovoVenditore == -1)     // Errore nell'inserimento del venditore nel database
            return false;


        utenteCorrente.setVenditore(true);  // Imposto l'attributo booleano venditore di utenteCorrente a true
        utenteDAO.update(utenteCorrente);   // Aggiorno l'utente nel database

        // recupero la email dell'utente per fare il login
        String email = utenteCorrente.getEmail();

        ControllerBase controllerBase = ControllerBase.getInstance();
        //effettua logout e login per modificare utente corrente e farlo risultare venditore
        // di conseguenza si avvierà il menu venditore
        controllerBase.logout();
        controllerBase.login(email,password);
        return true;
    }


}
