package View;
import Controller.ControllerBase;

import java.util.Scanner;

public class MenuFacade extends Menu {
    private Menu menuAttuale;
    private final ControllerBase controller;

    public MenuFacade() {
        super();
        // istanzia lo scanner che verrà passato ai vari menu per evitare conflitti sullo stream di input
        this.scanner = new Scanner(System.in);
        this.controller = ControllerBase.getInstance();
        menuAttuale = this;
    }

    public void display() {
        int scelta;
        while (true) {
            if( !controller.isUtenteLoggato() ) {
                System.out.println("Benvenuto nella nostra applicazione!");
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("3. Chiudi applicazione");
                System.out.println("Inserisci la tua scelta: ");

                // Legge l'input dell'utente
                scelta = inputInt();

                switch (scelta) {
                    case 1:
                        menuAttuale = new MenuLogin(scanner);
                        menuAttuale.display();
                        break;
                    case 2:
                        menuAttuale = new MenuRegistrazione(scanner);
                        menuAttuale.display();
                        break;
                    case 3:
                        System.out.println("Uscita in corso...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } else {
                if (controller.isUtenteCorrenteVenditore()){
                    menuAttuale = new MenuVenditore(scanner);
                    menuAttuale.display();
                } else {
                    menuAttuale = new MenuUtente(scanner);
                    menuAttuale.display();
                }
            }
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
