package View;

import java.util.Scanner;

public class MenuVenditore extends Menu {

    public MenuVenditore() {
        super();
        // this.controller = new ControllerVenditore();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Menu Venditore --");
    }
}
