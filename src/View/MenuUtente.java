package View;

import java.util.Scanner;

public class MenuUtente extends Menu {

    public MenuUtente() {
        super();
        // this.controller = new ControllerUtente();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Menu Utente --");
    }
}
