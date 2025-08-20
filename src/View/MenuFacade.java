package View;
import Controller.*;

import java.io.IOException;
import java.util.Scanner;

public class MenuFacade extends Menu {
    private Menu menuAttuale;
    private ControllerBase controller;
    public MenuFacade() {
        // fixme controllare se nell'esetendere una classe astratta devo usare super() nel costruttore
        super();
        // istanzia lo scanner che verrà passato ai vari menu per evitare conflitti sullo stream di input
        this.scanner = new Scanner(System.in);
        this.controller = ControllerBase.getInstance();
        menuAttuale = this;
    }
    public void display() {
        int scelta;
        while (true) {
            if( controller.getUtenteCorrente() == null ) {
                System.out.println("Benvenuto nella nostra applicazione!");
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("3. Chiudi applicazione");
                System.out.println("Inserisci la tua scelta: ");

                // Legge l'input dell'utente
                scelta = inputScelta();

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
                        scanner.close();
                        System.out.println("Uscita in corso...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }

            } else {
                //fixme valutare la rimozione dell'accesso all'oggetto utenteCorrente
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

    private int inputScelta() {
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
