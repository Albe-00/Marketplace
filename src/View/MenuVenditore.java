package View;

import Controller.ControllerBase;

import java.util.Scanner;

public class MenuVenditore extends Menu {

    public MenuVenditore() {
        super();
        // this.controller = new ControllerVenditore();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean uscita = false;
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
            int scelta = scanner.nextInt();
            switch (scelta) {
                case 1:
                    //this.controller.visualizzaProfilo();
                    System.out.println("Visualizza profilo non implementato.");
                    break;
                case 2:
                    //this.controller.modificaProfilo();
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
}
