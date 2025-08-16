package View;

import java.util.Scanner;

import Controller.ControllerBase;
import Model.*;

public class MenuRegistrazione extends Menu {

    public MenuRegistrazione() {
        super();
    }

    @Override
    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Registrazione Utente ---");

        System.out.print("Inserisci Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci Cognome: ");
        String cognome = scanner.nextLine();

        System.out.print("Inserisci Email: ");
        String email = scanner.nextLine();

        System.out.print("Inserisci Password: ");
        String password = scanner.nextLine();

        System.out.print("Inserisci Numero di Telefono: ");
        String telefono = scanner.nextLine();

        System.out.print("Sei un venditore? (si/no): ");
        String sceltaVenditore = scanner.nextLine();
        boolean venditore;
        switch (sceltaVenditore.toLowerCase()) {
            case "si":
            case "s":
            case "yes":
            case "y":
                venditore = true;
                break;
            default:
                venditore = false;
        }


        Utente nuovoUtente = new Utente(0,nome, cognome, email, password, telefono, venditore);

        ControllerBase controller = ControllerBase.getInstance();

        if( controller.register(nuovoUtente)!= null){
            System.out.println("🔒 Registrazione effettuata con successo!");
        }else{
            System.out.println("❌ Registrazione fallita! L'email già in uso.");
        }

        scanner.close();
    }
}
