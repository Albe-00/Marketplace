package Controller;

import Model.Utente;

public class ControllerUtente {
    Utente utenteCorrente;
    public ControllerUtente(Utente utente) {
        // Inizializza il controller per l'utente
        this.utenteCorrente = utente;
    }
    public void visualizzaProfilo() {
        // Logica per visualizzare il profilo dell'utente
        System.out.println("Visualizza profilo non implementato.");
    }
    public void modificaProfilo() {
        // Logica per modificare il profilo dell'utente
        System.out.println("Modifica profilo non implementato.");
    }
    public void cercaServizi() {
        // Logica per cercare servizi
        System.out.println("Cerca servizi non implementato.");
    }
    public void cercaVenditori() {
        // Logica per cercare venditori
        System.out.println("Cerca venditori non implementato.");
    }
    public void visualizzaOrdini() {
        // Logica per visualizzare gli ordini dell'utente
        System.out.println("Visualizza ordini non implementato.");
    }
    public void effettuaRecensione() {
        // Logica per effettuare una recensione
        System.out.println("Effettua recensione non implementato.");
    }
}
