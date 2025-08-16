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
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
        ControllerBase controller = ControllerBase.getInstance();
        Utente user = controller.login(email, password);
        if(user!=null) {

            System.out.println("🔑 Login effettuato con successo! Benvenuto/a " + user.getNome() + " " + user.getCognome());
        }else {
            System.out.println("❌ Credenziali non valide. Riprova.");
        }
    }
}
