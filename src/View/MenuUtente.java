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
        while(!uscita) {
            System.out.println("-- Menu Utente --");
            System.out.println("1. Visualizza profilo");
            System.out.println("2. Modifica profilo");
            System.out.println("3. Cerca servizi");
            System.out.println("4. Cerca Venditori");
            System.out.println("5. Visualizza ordini");
            System.out.println("6. Effettua recensione");
            System.out.println("7. Diventa venditore");
            System.out.println("8. Logout");
            System.out.print("Seleziona un'opzione: ");
            int scelta = scanner.nextInt();
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
                    effettuaRecensione();
                    break;
                case 7:
                    diventaVenditore();
                case 8:
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
        Utente utenteModificato = ControllerBase.getInstance().getUtenteCorrente();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Modifica Profilo");
        System.out.println("Cosa vuoi modificare?");
        System.out.println("1. Nome");
        System.out.println("2. Cognome");
        System.out.println("3. Email");
        System.out.println("4. Telefono");
        System.out.println("5. Annulla");
        int scelta = scanner.nextInt();
        switch (scelta) {
            case 1:
                System.out.println("Inserisci il nuovo nome:");
                String nome = scanner.nextLine();
                utenteModificato.setNome(nome);
                break;
            case 2:
                System.out.println("Inserisci il nuovo cognome:");
                String cognome = scanner.nextLine();
                utenteModificato.setCognome(cognome);
                break;
            case 3:
                System.out.println("Inserisci la nuova email:");
                String email = scanner.nextLine();
                controllerUtente.modificaEmail(email);
                return;
            case 4:
                System.out.println("Inserisci il nuovo numero di telefono:");
                String telefono = scanner.nextLine();
                utenteModificato.setTelefono(telefono);
                break;
            case 5:
                System.out.println("Modifica annullata.");
                return;
            default:
                System.out.println("Opzione non valida. Riprova.");
                return;

        }
        controllerUtente.modificaProfilo(utenteModificato);
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
                System.out.println(servizio);
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
                System.out.println(ordine);
            }
        }
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

    private void diventaVenditore() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci una descrizione per il tuo profilo venditore:");
        String descrizione = scanner.nextLine();

        if(controllerUtente.diventaVenditore(descrizione)) {
            System.out.println("Sei diventato un venditore con successo!");
        } else {
            System.out.println("Errore durante la registrazione come venditore.");
        }
    }
}
