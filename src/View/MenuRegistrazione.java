package View;

import java.util.Scanner;

public class MenuRegistrazione extends Menu {

    public MenuRegistrazione() {
        super();
        // this.controller = new ControllerRegistrazione();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Menu di Registrazione --");
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
    }
}
