package View;
import Controller.ControllerBase;
import Model.Utente;

import java.util.Scanner;

public class MenuLogin extends Menu {

    public MenuLogin() {
        super();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Login Utente --");
        System.out.println("Email : ");
        //String email = scanner.nextLine();
        System.out.println("Password : ");
        //String password = scanner.nextLine();

        //fixme togliere l'assegnazione statica , dopo il test e sostituire con scanner scritti sopra

        // UTENTE SEMPLICE
        String email="paolo.neri@example.com";
        String password="pwd654";
        // VENDITORE
        //String email="mario.rossi@example.com";
        //String password="pwd123";

        ControllerBase controller = ControllerBase.getInstance();
        Utente user = controller.login(email, password);
        if(user!=null) {
            System.out.println("🔑 Login effettuato con successo!");
        }else {
            System.out.println("❌ Credenziali non valide. Riprova.");
        }
    }
}
