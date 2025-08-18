package Controller;

import Model.*;
import DAO.*;

import java.util.List;

public class ControllerUtente {
    public static ControllerUtente instance = null;
    protected Utente utenteCorrente;
    public ControllerUtente(Utente utente) {
        // Inizializza il controller per l'utente
        this.utenteCorrente = utente;
    }

    public void visualizzaProfilo() {
        utenteCorrente.stampa();
    }

    // Modifica le informazioni dell'utente corrente
    public boolean modificaNome(String nuovoNome , String password) {
        if( !utenteCorrente.getPassword().equals(password) )
            return false; // La password non corrisponde
        utenteCorrente.setNome(nuovoNome);
        return true; // Nome modificato con successo
    }
    public boolean modificaCognome(String nuovoCognome , String password) {
        if( !utenteCorrente.getPassword().equals(password) )
            return false; // La password non corrisponde
        utenteCorrente.setCognome(nuovoCognome);
        return true; // Nome modificato con successo
    }
    public boolean modificaEmail(String nuovaEmail , String password) {
        if( !utenteCorrente.getPassword().equals(password) )
            return false; // La password non corrisponde

        // Controlla se l'email è già in uso
        UtenteDAO utenteDAO = new UtenteDAO();
        List<Object> utenti = utenteDAO.selectAll();

        for (Object obj : utenti) {
            Utente utente = (Utente) obj;
            if (utente.getEmail().equals(nuovaEmail) && utente.getId() != utenteCorrente.getId()) {
                System.out.println("L'email è già in uso da un altro utente.");
                return false; // Email già in uso
            }
        }

        // Se l'email non è in uso, aggiorna l'utente corrente
        utenteCorrente.setEmail(nuovaEmail);
        utenteDAO.update(utenteCorrente);
        return true; // Email modificata con successo
    }
    public boolean modificaPassword(String vecchiaPassword, String nuovaPassword) {
        if(utenteCorrente.getPassword().equals(vecchiaPassword)) {
            utenteCorrente.setPassword(nuovaPassword);
            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.update(utenteCorrente);
            return true; // Password modificata con successo
        }
        return false; // La vecchia password non corrisponde
    }
    public boolean modificaTelefono(String nuovoTelefono , String password) {
        if( !utenteCorrente.getPassword().equals(password) )
            return false; // La password non corrisponde
        utenteCorrente.setTelefono(nuovoTelefono);
        return true; // Nome modificato con successo
    }

    // Funzioni per la ricerca di servizi e venditori
    public List<Servizio> cercaServizi(String ricerca) {
        ServizioDAO servizioDAO = new ServizioDAO();
        return servizioDAO.cercaServizi(ricerca);
    }
    public List<Venditore> cercaVenditori(String ricerca) {
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        return venditoreDAO.cercaVenditori(ricerca);
    }

    // Funzioni per la gestione degli ordini e delle recensioni
    public List<Ordine> visualizzaOrdini() {
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
            Ordine nuovoOrdine = new Ordine(0, utenteCorrente.getId(), idServizio, new java.util.Date(), null);
            // Salvo l'ordine nel database
            return ordineDAO.insert(nuovoOrdine) != -1; // Ordine effettuato con successo il metodo insert restituisce un ID positivo
        }
        return false; // Servizio non trovato o non visibile
    }
    public boolean effettuaRecensione(int idVenditore, int voto, String testo) {
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        VenditoreDAO venditoreDAO = new VenditoreDAO();

        // Controllo che il voto sia compreso tra 1 e 5
        if (voto < 1 || voto > 5)
            return false;

        //Controllo la presenza del venditore
        Venditore venditore = (Venditore) venditoreDAO.select(idVenditore);
        if (venditore != null) {
            // Creo la recensione
            Recensione recensione = new Recensione(0, utenteCorrente.getId(), idVenditore, voto, testo);
            // Salvo la recensione nel database
            recensioneDAO.insert(recensione);
            // Aggiorno il rating del venditore
            venditore.setRating(venditore.getRating() + voto); // Aggiorno il rating
            venditoreDAO.update(venditore); // Salvo le modifiche al venditore
            return true; // Recensione effettuata con successo
        } else {
            return false; // Venditore non trovato
        }
    }

    // Funzione per diventare venditore
    public boolean diventaVenditore(String descrizione) {
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        // Controllo se l'utente corrente è già un venditore
        if (utenteCorrente.isVenditore()) {
            System.out.println("L'utente è già un venditore.");
            return false; // L'utente è già un venditore
        }
        utenteCorrente.setVenditore(true); // Imposto l'utente come venditore
        UtenteDAO utenteDAO = new UtenteDAO();
        // Aggiorno il campo booleano nella tabella utente del database
        utenteDAO.update(utenteCorrente);

        // Creo un nuovo oggetto Venditore con i dati dell'utente corrente e la descrizione
        Venditore nuovoVenditore = new Venditore(utenteCorrente.getId(), utenteCorrente.getNome(),
                utenteCorrente.getCognome(), utenteCorrente.getEmail(), utenteCorrente.getPassword(),
                utenteCorrente.getTelefono(), descrizione);

        // Salvo il nuovo venditore nel database
        return venditoreDAO.insert(nuovoVenditore) != -1; // Se l'inserimento ha successo, restituisce un ID positivo
    }


}
