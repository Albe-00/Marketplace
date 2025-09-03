package View;
import Controller.ControllerBase;

import java.util.Scanner;

public class MenuLogin extends Menu {
    private final ControllerBase controller;
    public MenuLogin(Scanner scanner) {
        super();
        this.scanner = scanner;
        this.controller = ControllerBase.getInstance();
    }

    @Override
    public void display() {
        System.out.println("-- LOGIN --");
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        if(controller.login(email, password)) {
            System.out.println("Login effettuato con successo!");
        }else {
            System.out.println("Login Fallito. Credenziali non valide. Riprova.");
        }
    }
}
