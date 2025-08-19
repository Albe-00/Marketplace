package View;

import Controller.ControllerBase;
import Controller.ControllerVenditore;
import Model.*;

import java.util.List;
import java.util.Scanner;

public class MenuVenditore extends Menu {
    ControllerVenditore controllerVenditore;
    public MenuVenditore() {
        super();
        Venditore venditore = (Venditore) ControllerBase.getInstance().getUtenteCorrente();
        controllerVenditore = new ControllerVenditore(venditore);
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean uscita = false;
        int scelta;
        while(!uscita) {
            System.out.println("-- Menu Venditore --");
            System.out.println("1. Visualizza profilo");
            System.out.println("2. Modifica profilo");
            System.out.println("3. Cerca servizi");
            System.out.println("4. Cerca Venditori");
            System.out.println("5. Visualizza ordini");
            System.out.println("6. Effettua ordine");
            System.out.println("7. Effettua recensione");

            // Opzioni specifiche per il venditore
            System.out.println("8. Crea un nuovo servizio");
            System.out.println("9. Modifica un servizio esistente");
            System.out.println("10. Elimina un servizio");
            System.out.println("11. Visualizza gli ordini ricevuti");
            System.out.println("12. Inizia un ordine");
            System.out.println("13. Rifiuta un ordine");
            System.out.println("14. Concludi un ordine");
            System.out.println("15. Visualizza le recensioni ricevute");
            System.out.println("16. Logout");
            System.out.print("Seleziona un'opzione: ");

            // Legge l'input dell'utente
            scelta= inputScelta();

            switch (scelta) {
                case 1:
                    controllerVenditore.visualizzaProfilo();
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
                    creaServizio();
                    break;
                case 9:
                    modificaServizio();
                    break;
                case 10:
                    eliminaServizio();
                    break;
                case 11:
                    visualizzaOrdiniRicevuti();
                    break;
                case 12:
                    iniziaOrdine();
                    break;
                case 13:
                    rifiutaOrdine();
                    break;
                case 14:
                    completaOrdine();
                    break;
                case 15:
                    visualizzaRecensioniRicevute();
                    break;
                case 16:
                    ControllerBase controller = ControllerBase.getInstance();
                    controller.logout();
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

    private void modificaProfilo() {
        Scanner scanner = new Scanner(System.in);
        int scelta;
        boolean modificaEffettuata = false;
        System.out.println("Modifica Profilo");
        System.out.println("Cosa vuoi modificare?");
        System.out.println("1. Nome");
        System.out.println("2. Cognome");
        System.out.println("3. Email");
        System.out.println("4. Password");
        System.out.println("5. Telefono");
        System.out.println("6. Descrizione");
        System.out.println("7. Annulla");

        // Legge l'input dell'utente
        scelta = inputScelta();

        System.out.println("Inserisci la tua password per confermare:");
        String password = scanner.nextLine();

        switch (scelta) {
            case 1:
                System.out.println("Inserisci il nuovo nome:");
                String nuovoNome = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaNome(nuovoNome, password);
                break;
            case 2:
                System.out.println("Inserisci il nuovo cognome:");
                String nuovoCognome = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaCognome(nuovoCognome, password);
                break;
            case 3:
                System.out.println("Inserisci la nuova email:");
                String nuovaEmail = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaEmail(nuovaEmail, password);
                break;
            case 4:
                System.out.println("Inserisci la nuova password:");
                String nuovaPassword = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaPassword(nuovaPassword, password);
                break;
            case 5:
                System.out.println("Inserisci il nuovo numero di telefono:");
                String nuovoTelefono = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaTelefono(nuovoTelefono, password);
                break;
            case 6:
                System.out.println("Inserisci il nuovo numero di telefono:");
                String nuovaDescrizione = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaDescrizione(nuovaDescrizione, password);
                break;
            case 7:
                System.out.println("Modifica annullata.");
                return;
            default:
                System.out.println("Opzione non valida. Riprova.");
                return;

        }
        if (modificaEffettuata) {
            System.out.println("Profilo modificato con successo.");
            controllerVenditore.visualizzaProfilo();
        } else {
            System.out.println("Errore durante la modifica del profilo. Verifica la password e riprova.");
        }
    }
    private void cercaServizi(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Che servizio cerchi ?");
        String query = scanner.nextLine();
        List<Servizio> serviziTrovati = controllerVenditore.cercaServizi(query);
        if (serviziTrovati.isEmpty()) {
            System.out.println("Nessun servizio trovato.");
        } else {
            System.out.println("Servizi trovati:");
            for (Servizio servizio : serviziTrovati) {
                servizio.stampa();
            }
        }
    }
    private void cercaVenditori() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quale venditore cerchi ?");
        String query = scanner.nextLine();
        List<Venditore> venditoriTrovati = controllerVenditore.cercaVenditori(query);
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
        List<Ordine> ordiniTrovati = controllerVenditore.recuperaOrdiniEffettuati();
        if (ordiniTrovati.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
        } else {
            System.out.println("Ordini trovati:");
            for (Ordine ordine : ordiniTrovati) {
                ordine.stampa();
            }
        }
    }
    private void effettuaOrdine() {
        cercaServizi();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci l'ID del servizio da ordinare:");
        int idServizio = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline rimasto dopo nextInt()
        controllerVenditore.effettuaOrdine(idServizio);
    }
    private void effettuaRecensione() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci ID del venditore da recensire:");
        int idVenditore = scanner.nextInt();
        System.out.println("Inserisci il voto (1-5):");
        int voto = scanner.nextInt();
        System.out.println("Inserisci il testo della recensione:");
        String testo = scanner.nextLine();

        if (controllerVenditore.effettuaRecensione(idVenditore, voto, testo)) {
            System.out.println("Recensione effettuata con successo.");
        } else {
            System.out.println("Errore durante l'effettuazione della recensione.");
        }
    }

    // gestione dei servizi del venditore
    private void creaServizio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il titolo del servizio:");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci la descrizione del servizio:");
        String descrizione = scanner.nextLine();
        System.out.println("Inserisci il prezzo del servizio:");
        float prezzo = scanner.nextFloat();
        scanner.nextLine(); // Consuma il newline rimasto dopo nextDouble()
        System.out.println("Inserisci la categoria del servizio:");
        String categoria = scanner.nextLine();
        System.out.println("Vuoi che il nuovo servizio sia visibile ? (si/no)");
        boolean visibilita = inputBoolean();

        boolean nuovoServizioCreato = false;
        nuovoServizioCreato = controllerVenditore.creaServizio(titolo, descrizione, prezzo, categoria, visibilita);

        if (nuovoServizioCreato) {
            System.out.println("Servizio creato con successo.");
        } else {
            System.out.println("Errore durante la creazione del servizio.");
        }
    }
    private void modificaServizio() {
        controllerVenditore.visualizzaServiziVenditore();
        System.out.println("Inserisci l'ID del servizio da modificare:");
        int idServizio = inputScelta();
        if (idServizio < 0) {
            System.out.println("ID non valido. Riprova.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int scelta;
        boolean modificaEffettuata = false;
        System.out.println("Modifica Servizio");
        System.out.println("Cosa vuoi modificare?");
        System.out.println("1. titolo");
        System.out.println("2. descrizione");
        System.out.println("3. prezzo");
        System.out.println("4. categoria");
        System.out.println("5. visibilita'");
        System.out.println("6. Annulla");

        // Legge l'input dell'utente
        scelta = inputScelta();

        System.out.println("Inserisci la tua password per confermare:");
        String password = scanner.nextLine();

        switch (scelta) {
            case 1:
                System.out.println("Inserisci il nuovo titolo:");
                String nuovoTitolo = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaNomeServizio(idServizio, nuovoTitolo, password);
                break;
            case 2:
                System.out.println("Inserisci la nuova descrizione:");
                String nuovoDescrizione = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaDescrizioneServizio(idServizio, nuovoDescrizione, password);
                break;
            case 3:
                System.out.println("Inserisci il nuovo prezzo:");
                String nuovoPrezzoStringa = scanner.nextLine();
                float nuovoPrezzo;
                try {
                    nuovoPrezzo = Float.parseFloat(nuovoPrezzoStringa);
                } catch (NumberFormatException e) {
                    System.out.println("Prezzo non valido. Riprova.");
                    return;
                }
                modificaEffettuata = controllerVenditore.modificaPrezzoServizio(idServizio, nuovoPrezzo, password);
                break;
            case 4:
                System.out.println("Inserisci la nuova categoria:");
                String nuovaCategoria = scanner.nextLine();
                modificaEffettuata = controllerVenditore.modificaCategoriaServizio(idServizio, nuovaCategoria, password);
                break;
            case 5:
                System.out.println("Inserisci la nuova visibilità (si/no):");
                boolean nuovoVisibilita = inputBoolean();
                modificaEffettuata = controllerVenditore.modificaVisibilitaServizio(idServizio, nuovoVisibilita, password);
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
            controllerVenditore.visualizzaProfilo();
        } else {
            System.out.println("Errore durante la modifica del profilo. Verifica la password e riprova.");
        }
    }
    private void eliminaServizio() {
        controllerVenditore.visualizzaServiziVenditore();
        System.out.println("Inserisci l'ID del servizio da eliminare:");
        int idServizio = inputScelta();
        if (idServizio < 0) {
            System.out.println("ID non valido. Riprova.");
            return;
        }

        System.out.println("Inserisci la tua password per confermare:");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();

        if (controllerVenditore.eliminaServizio(idServizio, password)) {
            System.out.println("Servizio eliminato con successo.");
        } else {
            System.out.println("Errore durante l'eliminazione del servizio. Verifica la password e riprova.");
        }
    }

    // Gestione Ordini del venditore
    private void visualizzaOrdiniRicevuti() {
        List<Ordine> ordiniTrovati = controllerVenditore.recuperaOrdiniRicevuti();
        if (ordiniTrovati.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
        } else {
            System.out.println("Ordini trovati:");
            for (Ordine ordine : ordiniTrovati) {
                ordine.stampa();
            }
        }
    }
    private void iniziaOrdine() {
        controllerVenditore.recuperaOrdiniInAttesa();
        System.out.println("Inserisci l'ID del ordine da iniziare:");
        int idOrdine = inputScelta();
        if (idOrdine < 0) {
            System.out.println("ID non valido. Riprova.");
        }else{
            if (controllerVenditore.iniziaOrdine(idOrdine)) {
                System.out.println("Ordine iniziato con successo.");
            } else {
                System.out.println("Errore durante l'inizio dell'ordine. Verifica la password e riprova.");
            }
        }
    }
    private void rifiutaOrdine() {
        controllerVenditore.recuperaOrdiniInAttesa();
        System.out.println("Inserisci l'ID del ordine da iniziare:");
        int idOrdine = inputScelta();
        if (idOrdine < 0) {
            System.out.println("ID non valido. Riprova.");
        }else{
            if (controllerVenditore.rifiutaOrdine(idOrdine)) {
                System.out.println("Ordine rifiutato con successo.");
            } else {
                System.out.println("Errore durante il rifiuto dell'ordine. Verifica la password e riprova.");
            }
        }


    }
    private void completaOrdine() {
        controllerVenditore.recuperaOrdiniInLavorazione();
        System.out.println("Inserisci l'ID del ordine da iniziare:");
        int idOrdine = inputScelta();
        if (idOrdine < 0) {
            System.out.println("ID non valido. Riprova.");
        }else{
            if (controllerVenditore.completaOrdine(idOrdine)) {
                System.out.println("Ordine iniziato con successo.");
            } else {
                System.out.println("Errore durante l'inizio dell'ordine. Verifica la password e riprova.");
            }
        }


    }

    // // Visualizza le recensioni ricevute dal venditore
    private void visualizzaRecensioniRicevute() {
        List<Recensione> recensioniTrovate = controllerVenditore.recuperaRecensioniRicevute();
        if (recensioniTrovate.isEmpty()) {
            System.out.println("Nessuna recensione trovata.");
        } else {
            System.out.println("Recensioni trovate:");
            for (Recensione recensione : recensioniTrovate) {
                recensione.stampa();
            }
        }
    }

    // funzione per leggere l'input dell'utente e convertirlo in un intero , restituisce -1 se l'input non è un numero
    private int inputScelta() {
        Scanner scanner = new Scanner(System.in);
        String sceltaStringa;
        int scelta;
        sceltaStringa = scanner.nextLine();
        try {
            scelta = Integer.parseInt(sceltaStringa); // caso numerico
        } catch (NumberFormatException e) {
            scelta = -1; // così non corrispondera mai ad un id_venditore
        }

        return scelta;
    }
    // funzione per leggere l'input dell'utente come stringa e convertirlo in un booleano
    private boolean inputBoolean() {
        Scanner scanner = new Scanner(System.in);
        // trim (metodo della classe String) rimuove gli spazi all'inizio e alla fine della stringa
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("si") || input.equals("s") || input.equals("yes") || input.equals("y");
    }
}
