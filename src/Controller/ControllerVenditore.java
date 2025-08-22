package Controller;

import DAO.VenditoreDAO;
import DAO.ServizioDAO;
import DAO.OrdineDAO;
import DAO.RecensioneDAO;
import Model.Venditore;
import Model.Servizio;
import Model.Ordine;
import Model.Recensione;

import java.util.List;


public class ControllerVenditore extends ControllerUtente {
    public ControllerVenditore() {
        // Inizializza il controller per il venditore
        super();
        VenditoreDAO venditoreDAO= new VenditoreDAO();
        // Dato che l'utente corrente è un venditore devo aggiornare
        // l'attributo utenteCorrente con la variabile di tipo venditore relativa a quell'utente
        // cosi facendo utenteCorrente conterrà una varibile di tipo Venditore
        // e oltre alle informazioni di base di un utente , saranno presenti anche la descrizione
        // e il rating
        this.utenteCorrente = (Venditore)venditoreDAO.select(utenteCorrente.getId());
    }

    // metodi per la visualizzazione del profilo del venditore e dei suoi servizi
    @Override
    public void visualizzaProfilo() {
        visualizzaServiziVenditore();
    }
    public void visualizzaServiziVenditore() {
        ServizioDAO servizioDAO = new ServizioDAO();
        System.out.println("Servizi offerti da " + utenteCorrente.getNome() + ":");
        List<Servizio> serviziOfferti = servizioDAO.selectByVenditore(utenteCorrente.getId());

        if (serviziOfferti.isEmpty()) {
            System.out.println("Nessun servizio.");
            return; // Esce se non ci sono servizi
        }

        for (Servizio servizio : serviziOfferti) {
            servizio.stampa();
        }
    }

    // Modifica il profilo del venditore
    public boolean modificaDescrizione(String nuovaDescrizione, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ((Venditore) utenteCorrente).setDescrizione(nuovaDescrizione);
        return true; // Descrizione modificato con successo
    }

    //controllo servizi

    public int getNumeroServiziVenditore() {
        ServizioDAO servizioDAO = new ServizioDAO();
        return servizioDAO.countByVenditore(utenteCorrente.getId());
    }

    // crea un nuovo servizio
    public boolean creaServizio(String titolo, String descrizione, float prezzo, String categoria,boolean visibile) {
        if (titolo.isEmpty() || descrizione.isEmpty() || prezzo <= 0 || categoria.isEmpty()) {
            return false; // Dati non validi
        }
        Servizio nuovoServizio = new Servizio(0, utenteCorrente.getId(), titolo, descrizione, prezzo, categoria,visibile);
        ServizioDAO servizioDAO = new ServizioDAO();

        int idNuovoServizio = servizioDAO.insert(nuovoServizio);

        return idNuovoServizio > 0; // Restituisce true se il servizio è stato creato con successo, altrimenti false

    }

    // Modifica un servizio esistente
    public boolean modificaNomeServizio(int idServizio, String nuovoTitolo, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            servizio.setTitolo(nuovoTitolo);
            return servizioDAO.update(servizio); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }
    public boolean modificaDescrizioneServizio(int idServizio, String nuovaDescrizione, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            servizio.setDescrizione(nuovaDescrizione);
            return servizioDAO.update(servizio); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }
    public boolean modificaPrezzoServizio(int idServizio, float nuovoPrezzo, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            servizio.setPrezzo(nuovoPrezzo);
            return servizioDAO.update(servizio); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }
    public boolean modificaCategoriaServizio(int idServizio, String nuovaCategoria, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            servizio.setCategoria(nuovaCategoria);
            return servizioDAO.update(servizio); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }
    public boolean modificaVisibilitaServizio(int idServizio, boolean nuovaVisibilita, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            servizio.setVisibile(nuovaVisibilita);
            return servizioDAO.update(servizio); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }

    // Elimina un servizio esistente
    public boolean eliminaServizio(int idServizio, String password) {
        if (!utenteCorrente.isEqualPassword(password)) {
            return false; // La password non corrisponde
        }
        ServizioDAO servizioDAO = new ServizioDAO();
        Servizio servizio = (Servizio) servizioDAO.select(idServizio);
        if (servizio != null && servizio.getId_venditore() == utenteCorrente.getId()) {
            return servizioDAO.delete(idServizio); // Restituisce true se l'eliminazione è riuscita
        }
        return false; // Servizio non trovato o non appartiene al venditore
    }

    // metodi per la gestione degli ordini
    public List<Ordine> recuperaOrdiniRicevuti() {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.selectByVenditore(utenteCorrente.getId());
    }
    public List<Ordine> recuperaOrdiniInAttesa() {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.selectByVenditoreAndStato(utenteCorrente.getId(), "IN ATTESA");
    }
    public List<Ordine> recuperaOrdiniInLavorazione() {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.selectByVenditoreAndStato(utenteCorrente.getId(), "IN LAVORAZIONE");
    }
    public boolean iniziaOrdine(int idOrdine){
        OrdineDAO ordineDAO = new OrdineDAO();
        Ordine ordine = (Ordine) ordineDAO.select(idOrdine);
        int idVenditore = ordineDAO.getIdVenditore(idOrdine);
        if (ordine != null && idVenditore == utenteCorrente.getId() && ordine.getStatoOrdine().equals("IN ATTESA")) {
            ordine.setStatoOrdine("IN LAVORAZIONE");
            return ordineDAO.update(ordine); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Ordine non trovato o non appartiene al venditore o stato non valido
    }
    public boolean rifiutaOrdine(int idOrdine){
        OrdineDAO ordineDAO = new OrdineDAO();

        Ordine ordine = (Ordine) ordineDAO.select(idOrdine);
        int idVenditore = ordineDAO.getIdVenditore(idOrdine);

        if (ordine != null && idVenditore == utenteCorrente.getId() && ordine.getStatoOrdine().equals("IN ATTESA")) {
            ordine.setStatoOrdine("RIFIUTATO");
            return ordineDAO.update(ordine); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Ordine non trovato o non appartiene al venditore o stato non valido
    }
    public boolean completaOrdine(int idOrdine){
        OrdineDAO ordineDAO = new OrdineDAO();
        Ordine ordine = (Ordine) ordineDAO.select(idOrdine);
        int idVenditore = ordineDAO.getIdVenditore(idOrdine);
        if (ordine != null && idVenditore == utenteCorrente.getId() && ordine.getStatoOrdine().equals("IN LAVORAZIONE")) {
            ordine.setStatoOrdine("COMPLETATO");
            return ordineDAO.update(ordine); // Restituisce true se l'aggiornamento è riuscito
        }
        return false; // Ordine non trovato o non appartiene al venditore o stato non valido
    }

    // Metodi per la gestione delle recensioni
    public List<Recensione> recuperaRecensioniRicevute(){
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        return recensioneDAO.selectByVenditore(utenteCorrente.getId());
    }

}


