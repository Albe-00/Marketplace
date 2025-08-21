package View;

import java.util.Scanner;

import Controller.ControllerBase;
import Model.*;

public class MenuRegistrazione extends Menu {
    private ControllerBase controller;
    public MenuRegistrazione(Scanner scanner) {
        super();
        this.scanner = scanner;
        this.controller = ControllerBase.getInstance();
    }

    @Override
    public void display() {
        System.out.println("--- REGISTRAZIONE ---");

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

        boolean sceltaValida = false;
        boolean registazioneConSuccesso = false;

        while(!sceltaValida) {

            System.out.print("Sei un venditore? (si/no): ");
            String sceltaVenditore = scanner.nextLine();

            switch (sceltaVenditore.toLowerCase()) {
                case "si":
                case "s":
                case "yes":
                case "y":
                    // È un venditore
                    System.out.println("Inserisci una descrizione per il tuo profilo venditore:");
                    String descrizione = scanner.nextLine();

                    Venditore nuovoVenditore = new Venditore(0, nome, cognome, email, password, telefono, descrizione);

                    registazioneConSuccesso = controller.registerVenditore(nuovoVenditore);
                    sceltaValida = true;
                    break;
                case "no":
                case "n":
                    // E' un utente semplice (cliente)

                    Utente nuovoUtente = new Utente(0, nome, cognome, email, password, telefono, false);

                    registazioneConSuccesso = controller.registerUtente(nuovoUtente);
                    sceltaValida = true;
                    break;
                default:
                    System.out.println("Input non valido, riprovare.");
            }
        }
        if( registazioneConSuccesso )
            System.out.println("🔒 Registrazione effettuata con successo!");
        else
            System.out.println("❌ Registrazione fallita! L'email già in uso.");

    }
}
