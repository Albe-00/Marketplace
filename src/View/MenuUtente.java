package View;

import Controller.ControllerBase;

import java.util.List;
import java.util.Scanner;
import Controller.ControllerUtente;
import DAO.RecensioneDAO;
import Model.*;

public class MenuUtente extends Menu {

    ControllerBase controllerBase;
    private ControllerUtente controllerUtente;

    public MenuUtente(Scanner scanner) {
        super();
        this.scanner = scanner;
        controllerBase = ControllerBase.getInstance();
        this.controllerUtente = new ControllerUtente( ControllerBase.getInstance().getUtenteCorrente() );
    }

    public void display() {
        boolean uscita = false;
        int scelta;
        while(!uscita) {
            System.out.println("Ciao " + controllerUtente.getNome() + "!");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1. Visualizza profilo");
            System.out.println("2. Modifica profilo");
            System.out.println("3. Cerca servizi e venditori");
            System.out.println("4. Visualizza ordini");
            System.out.println("5. Effettua ordine");
            System.out.println("6. Effettua recensione");
            System.out.println("7. Diventa venditore");
            System.out.println("8. Logout");
            System.out.print("Seleziona un'opzione: ");

            // Legge l'input dell'utente
            scelta = inputInt();

            switch (scelta) {
                case 1:
                    controllerUtente.visualizzaProfilo();
                    break;
                case 2:
                    modificaProfilo();
                    break;
                case 3:
                    cerca();
                    break;
                case 4:
                    visualizzaOrdiniEffettuati();
                    break;
                case 5:
                    effettuaOrdine();
                    break;
                case 6:
                    effettuaRecensione();
                    break;
                case 7:
                    if( diventaVenditore() ) {
                        uscita = true; // Esce dal menu se l'utente diventa venditore
                        controllerBase.logout();
                        //fixme ad ora fa logout , e richiede il login per accedere al menu venditore
                    }
                    break;
                case 8:
                    controllerBase.logout();
                    uscita = true;
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
            if (!uscita) {
                System.out.println("Premi invio per continuare...");
                scanner.nextLine(); // Attende l'input dell'utente
            }
        }
    }
    //versione display con 2 ricerche
    /*@Override
    public void display() {
        boolean uscita = false;
        int scelta;
        while(!uscita) {
            System.out.println("Ciao " + controllerUtente.getNome() + "!");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1. Visualizza profilo");
            System.out.println("2. Modifica profilo");
            System.out.println("3. Cerca servizi");
            System.out.println("4. Cerca Venditori");
            System.out.println("5. Visualizza ordini");
            System.out.println("6. Effettua ordine");
            System.out.println("7. Effettua recensione");
            System.out.println("8. Diventa venditore");
            System.out.println("9. Logout");
            System.out.print("Seleziona un'opzione: ");

            // Legge l'input dell'utente
            scelta = inputInt();

            switch (scelta) {
                case 1:
                    controllerUtente.visualizzaProfilo();
                    break;
                case 2:
                    modificaProfilo();
                    break;
                case 3:
                    cercaServizi();
                    break;
                case 4:
                    cercaVenditori();
                    break;
                case 5:
                    visualizzaOrdiniEffettuati();
                    break;
                case 6:
                    effettuaOrdine();
                    break;
                case 7:
                    effettuaRecensione();
                    break;
                case 8:
                    if( diventaVenditore() ) {
                        uscita = true; // Esce dal menu se l'utente diventa venditore
                        controllerBase.logout();
                        //fixme ad ora fa logout , e richiede il login per accedere al menu venditore
                    }
                    break;
                case 9:
                    controllerBase.logout();
                    uscita = true;
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
            if (!uscita) {
                System.out.println("Premi invio per continuare...");
                scanner.nextLine(); // Attende l'input dell'utente
            }
        }
    }*/
    private void modificaProfilo() {
        int scelta;
        boolean modificaEffettuata = false;
        System.out.println("Modifica Profilo");
        System.out.println("Cosa vuoi modificare?");
        System.out.println("1. Nome");
        System.out.println("2. Cognome");
        System.out.println("3. Email");
        System.out.println("4. Password");
        System.out.println("5. Telefono");
        System.out.println("6. Annulla");

        // Legge l'input dell'utente
        scelta = inputInt();

        System.out.println("Inserisci la tua password per confermare:");
        String password = scanner.nextLine();

        switch (scelta) {
            case 1:
                System.out.println("Inserisci il nuovo nome:");
                String nuovoNome = scanner.nextLine();
                modificaEffettuata = controllerUtente.modificaNome(nuovoNome, password);
                break;
            case 2:
                System.out.println("Inserisci il nuovo cognome:");
                String nuovoCognome = scanner.nextLine();
                modificaEffettuata = controllerUtente.modificaCognome(nuovoCognome, password);
                break;
            case 3:
                System.out.println("Inserisci la nuova email:");
                String nuovaEmail = scanner.nextLine();
                modificaEffettuata = controllerUtente.modificaEmail(nuovaEmail, password);
                break;
            case 4:
                System.out.println("Inserisci la nuova password:");
                String nuovaPassword = scanner.nextLine();
                modificaEffettuata = controllerUtente.modificaPassword(nuovaPassword, password);
                break;
            case 5:
                System.out.println("Inserisci il nuovo numero di telefono:");
                String nuovoTelefono = scanner.nextLine();
                modificaEffettuata = controllerUtente.modificaTelefono(nuovoTelefono, password);
                break;
            case 6:
                System.out.println("Modifica annullata.");
                return;
            default:
                System.out.println("Opzione non valida. Riprova.");
                return;

        }
        if (modificaEffettuata) {
            System.out.println("Profilo modificato con successo.");
            controllerUtente.visualizzaProfilo();
        } else {
            System.out.println("Errore durante la modifica del profilo. Verifica la password e riprova.");
        }
    }
    private void cerca(){
        System.out.println("Cosa stai cercando ?");
        String query = scanner.nextLine();
        List<Servizio> serviziTrovati = controllerUtente.cercaServizi(query);
        List<Venditore> venditoriTrovati = controllerUtente.cercaVenditori(query);

        // Stampa i servizi trovati
        if (serviziTrovati.isEmpty()) {
            System.out.println("Nessun servizio trovato.");
        } else {
            System.out.println("SERVIZI TROVATI:");
            for (Servizio servizio : serviziTrovati) {
                servizio.stampa();
            }
        }
        System.out.println(); // Aggiunge una riga vuota per separare i risultati

        // Stampa i venditori trovati
        if (venditoriTrovati.isEmpty()) {
            System.out.println("Nessun venditore trovato.");
        } else {
            System.out.println("VENDITORI TROVATI:");
            for (Venditore venditore : venditoriTrovati) {
                venditore.stampa();
            }
        }
    }
    private void cercaServizi(){
        System.out.println("Che servizio cerchi ?");
        String query = scanner.nextLine();
        List<Servizio> serviziTrovati = controllerUtente.cercaServizi(query);
        if (serviziTrovati.isEmpty()) {
            System.out.println("Nessun servizio trovato.");
        } else {
            System.out.println("SERVIZI TROVATI:");
            for (Servizio servizio : serviziTrovati) {
                servizio.stampa();
            }
        }
    }
    private void cercaVenditori() {
        System.out.println("Quale venditore cerchi ?");
        String query = scanner.nextLine();
        List<Venditore> venditoriTrovati = controllerUtente.cercaVenditori(query);
        if (venditoriTrovati.isEmpty()) {
            System.out.println("Nessun venditore trovato.");
        } else {
            System.out.println("Venditori trovati:");
            for (Venditore venditore : venditoriTrovati) {
                venditore.stampa();
            }
        }
    }
    private void visualizzaOrdiniEffettuati() {
        List<Ordine> ordiniTrovati = controllerUtente.recuperaOrdiniEffettuati();
        if (ordiniTrovati.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
        } else {
            System.out.println("I tuoi Ordini :");
            for (Ordine ordine : ordiniTrovati) {
                ordine.stampa();
            }
        }
    }
    private void effettuaOrdine() {
        cercaServizi();
        System.out.println("------------------------------------------");
        System.out.println("Inserisci l'ID del servizio da ordinare:");
        int idServizio = inputInt();
        controllerUtente.effettuaOrdine(idServizio);
    }
    private void effettuaRecensione() {
        cercaVenditori();
        System.out.println("Inserisci ID del venditore da recensire:");
        int idVenditore = inputInt();
        System.out.println("Inserisci il voto (1-5):");
        int voto = inputInt();
        System.out.println("Inserisci il testo della recensione:");
        String testo = scanner.nextLine();

        if (controllerUtente.effettuaRecensione(idVenditore, voto, testo)) {
            System.out.println("Recensione effettuata con successo.");
        } else {
            System.out.println("Errore durante l'effettuazione della recensione.");
        }
    }
    private boolean diventaVenditore() {
        System.out.println("Inserisci una descrizione per il tuo profilo venditore:");
        String descrizione = scanner.nextLine();

        if(controllerUtente.diventaVenditore(descrizione)) {
            System.out.println("Sei diventato un venditore con successo!");
            return true;
        } else {
            System.out.println("Errore durante la registrazione come venditore.");
            return false;
        }
    }
    private int inputInt() {
        String numeroStringa;
        int numero;
        numeroStringa = scanner.nextLine();
        try {
            numero = Integer.parseInt(numeroStringa); // caso numerico
        } catch (NumberFormatException e) {
            numero = -1;
        }
        return numero;
    }
}
