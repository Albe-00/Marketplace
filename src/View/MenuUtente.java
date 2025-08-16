package View;

import Controller.ControllerBase;

import java.awt.desktop.SystemEventListener;
import java.util.Scanner;
import Controller.ControllerUtente;

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
            //System.out.println("6. Visualizza carrello");//todo si può aggiungere il carrello
            System.out.println("6. Effettua recensione");
            System.out.println("7. Logout");
            System.out.print("Seleziona un'opzione: ");
            int scelta = scanner.nextInt();
            switch (scelta) {
                case 1:
                    controllerUtente.visualizzaProfilo();
                    System.out.println("Visualizza profilo non implementato.");
                    break;
                case 2:
                    controllerUtente.modificaProfilo();
                    System.out.println("Modifica profilo non implementato.");
                    break;
                case 3:
                    controllerUtente.cercaServizi();
                    System.out.println("Cerca servizi non implementato.");
                    break;
                case 4:
                    controllerUtente.cercaVenditori();
                    System.out.println("Cerca venditori non implementato.");
                    break;
                case 5:
                    controllerUtente.visualizzaOrdini();
                    System.out.println("Visualizza ordini non implementato.");
                    break;
                case 6:
                    controllerUtente.effettuaRecensione();
                    System.out.println("Effettua recensione non implementato.");
                    break;
                case 7:
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
