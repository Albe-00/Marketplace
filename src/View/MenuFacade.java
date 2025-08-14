package View;
import Controller.*;
import java.util.Scanner;

public class MenuFacade extends Menu {
    Menu menuAttuale;
    public MenuFacade() {
        // fixme controllare se nell'esetendere una classe astratta devo usare super() nel costruttore
        super();
        super.controller = ControllerBase.getInstance();
        menuAttuale = this;
    }
    public void display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Benvenuto nella nostra applicazione!");
            if( controller.getUtenteCorrente() == null ) {
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("3. Chiudi applicazione");
                System.out.println("Inserisci la tua scelta: ");
                int scelta = scanner.nextInt();
                switch (scelta) {
                    case 1:
                        menuAttuale = new MenuLogin();
                        menuAttuale.display();
                        break;
                    case 2:
                        menuAttuale = new MenuRegistrazione();
                        menuAttuale.display();
                        break;
                    case 3:
                        System.out.println("Uscita in corso...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }

            } else if (controller.isUtenteVenditore()) {
                menuAttuale = new MenuVenditore();
                menuAttuale.display();
            }else{
                menuAttuale = new MenuUtente();
                menuAttuale.display();
            }
        }
    }
}
