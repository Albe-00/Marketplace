package View;

import Controller.ControllerBase;

import java.awt.desktop.SystemEventListener;
import java.util.List;
import java.util.Scanner;
import Controller.ControllerUtente;
import DAO.RecensioneDAO;
import Model.*;

public class MenuUtente extends Menu {
    private ControllerUtente controllerUtente;

    public MenuUtente() {
        super();
        this.controllerUtente = new ControllerUtente(ControllerBase.getInstance().getUtenteCorrente());
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean uscita = false;
        int scelta;
        while(!uscita) {
            System.out.println("-- Menu Utente --");
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
            scelta = inputScelta();

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
                    visualizzaOrdini();
                    break;
                case 6:
                    effettuaOrdine();
                    break;
                case 7:
                    effettuaRecensione();
                    break;
                case 8:
                    if( diventaVenditore() )
                        uscita = true; // Esce dal menu se l'utente diventa venditore
                    break;
                case 9:
                    scanner.close();
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
        System.out.println("6. Annulla");

        // Legge l'input dell'utente
        scelta = inputScelta();

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
    private void cercaServizi(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Che servizio cerchi ?");
        String query = scanner.nextLine();
        List<Servizio> serviziTrovati = controllerUtente.cercaServizi(query);
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
    private void visualizzaOrdini() {
        List<Ordine> ordiniTrovati = controllerUtente.visualizzaOrdini();
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
        controllerUtente.effettuaOrdine(idServizio);
    }
    private void effettuaRecensione() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci ID del venditore da recensire:");
        int idVenditore = scanner.nextInt();
        System.out.println("Inserisci il voto (1-5):");
        int voto = scanner.nextInt();
        System.out.println("Inserisci il testo della recensione:");
        String testo = scanner.nextLine();

        if (controllerUtente.effettuaRecensione(idVenditore, voto, testo)) {
            System.out.println("Recensione effettuata con successo.");
        } else {
            System.out.println("Errore durante l'effettuazione della recensione.");
        }
    }
    private boolean diventaVenditore() {
        Scanner scanner = new Scanner(System.in);
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
}
