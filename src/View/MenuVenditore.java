package View;

import Controller.ControllerBase;
import Controller.ControllerVenditore;
import Model.Utente;
import Model.Venditore;

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
            //System.out.println("6. Visualizza carrello");//todo si può aggiungere il carrello
            System.out.println("6. Effettua recensione");

            // Aggiungi opzioni specifiche per il venditore
            System.out.println("7. Crea un nuovo servizio");
            System.out.println("8. Modifica un servizio esistente");
            System.out.println("9. Elimina un servizio");
            System.out.println("10. Visualizza gli ordini ricevuti");
            System.out.println("11. Inizia un ordine");
            System.out.println("12. Concludi un ordine");
            System.out.println("13. Visualizza le recensioni ricevute");
            System.out.println("14. Logout");
            System.out.print("Seleziona un'opzione: ");

            // Legge l'input dell'utente
            scelta= inputScelta();

            switch (scelta) {
                case 1:
                    controllerVenditore.visualizzaProfilo();
                    break;
                case 2:
                    modificaProfilo();
                    System.out.println("Modifica profilo non implementato.");
                    break;
                case 3:
                    //this.controller.cercaServizi();
                    System.out.println("Cerca servizi non implementato.");
                    break;
                case 4:
                    //this.controller.cercaVenditori();
                    System.out.println("Cerca venditori non implementato.");
                    break;
                case 5:
                    //this.controller.visualizzaOrdini();
                    System.out.println("Visualizza ordini non implementato.");
                    break;
                case 6:
                    //this.controller.effettuaRecensione();
                    System.out.println("Effettua recensione non implementato.");
                    break;
                case 7:
                    //this.controller.creaServizio();
                    System.out.println("Crea un nuovo servizio non implementato.");
                    break;
                case 8:
                    //this.controller.modificaServizio();
                    System.out.println("Modifica un servizio esistente non implementato.");
                    break;
                case 9:
                    //this.controller.eliminaServizio();
                    System.out.println("Elimina un servizio non implementato.");
                    break;
                case 10:
                    //this.controller.visualizzaOrdiniRicevuti();
                    System.out.println("Visualizza gli ordini ricevuti non implementato.");
                    break;
                case 11:
                    //this.controller.iniziaOrdine();
                    System.out.println("Inizia un ordine non implementato.");
                    break;
                case 12:
                    //this.controller.concludiOrdine();
                    System.out.println("Concludi un ordine non implementato.");
                    break;
                case 13:
                    //this.controller.visualizzaRecensioniRicevute();
                    System.out.println("Visualizza le recensioni ricevute non implementato.");
                    break;
                case 14:
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
        Venditore venditoreModificato = controllerVenditore.getVenditore();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Modifica Profilo Venditore");
        System.out.println("Cosa vuoi modificare?");
        System.out.println("1. Nome");
        System.out.println("2. Cognome");
        System.out.println("3. Email");
        System.out.println("4. Telefono");
        System.out.println("5. Descrizione");
        System.out.println("6. Annulla");

        System.out.print("Inserisci la tua scelta: ");
        int scelta = inputScelta();

        switch (scelta) {
            case 1:
                System.out.println("Inserisci il nuovo nome:");
                String nome = scanner.nextLine();
                venditoreModificato.setNome(nome);
                break;
            case 2:
                System.out.println("Inserisci il nuovo cognome:");
                String cognome = scanner.nextLine();
                venditoreModificato.setCognome(cognome);
                break;
            case 3:
                System.out.println("Inserisci la nuova email:");
                String email = scanner.nextLine();
                //controllerVenditore.modificaEmail(email);
                return;
            case 4:
                System.out.println("Inserisci il nuovo numero di telefono:");
                String telefono = scanner.nextLine();
                venditoreModificato.setTelefono(telefono);
                break;
            case 5:
                System.out.println("Inserisci la nuova descrizione:");
                String descrizione = scanner.nextLine();
                if (descrizione.isEmpty()) {
                    System.out.println("La descrizione non può essere vuota.");
                    return;
                }
                venditoreModificato.setDescrizione(descrizione);
                break;
            case 6:
                System.out.println("Modifica annullata.");
                return;
            default:
                System.out.println("Opzione non valida. Riprova.");
                return;

        }
        controllerVenditore.modificaProfilo(venditoreModificato);
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
