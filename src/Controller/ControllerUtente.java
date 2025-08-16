package Controller;

import Model.*;
import DAO.*;

import java.util.List;

public class ControllerUtente {
    Utente utenteCorrente;
    public ControllerUtente(Utente utente) {
        // Inizializza il controller per l'utente
        this.utenteCorrente = (Utente) utente;
    }
    public void visualizzaProfilo() {
        utenteCorrente.stampa();
    }
    public void modificaProfilo(Utente utenteModificato) {
        // Modifica i dati dell'utente corrente
        utenteCorrente.setNome(utenteModificato.getNome());
        utenteCorrente.setCognome(utenteModificato.getCognome());
        utenteCorrente.setTelefono(utenteModificato.getTelefono());

        // Salva le modifiche nel database
        UtenteDAO utenteDAO = new UtenteDAO();
        utenteDAO.update(utenteCorrente);

    }
    public boolean modificaEmail(String nuovaEmail) {
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
    public List<Servizio> cercaServizi(String ricerca) {
        ServizioDAO servizioDAO = new ServizioDAO();
        return servizioDAO.cercaServizi(ricerca);
    }
    public List<Venditore> cercaVenditori(String ricerca) {
        VenditoreDAO venditoreDAO = new VenditoreDAO();
        return venditoreDAO.cercaVenditori(ricerca);
    }
    public List<Ordine> visualizzaOrdini() {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.selectByCliente(utenteCorrente.getId());
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
        return venditoreDAO.insert(nuovoVenditore);
    }


}
