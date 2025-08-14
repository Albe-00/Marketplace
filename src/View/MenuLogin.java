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
        System.out.println("-- Login Menu --");
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
        ControllerBase controller = ControllerBase.getInstance();
        Utente u = controller.login(email, password);
        System.out.println("Login successful for user: " + u.getNome() + " " + u.getCognome());
        if (u.isVenditore()) {
            System.out.println("Welcome to the Vendor Menu!");
            scanner.nextLine();
        } else {
            System.out.println("Welcome to the Customer Menu!");
            scanner.nextLine();
        }
    }
}
