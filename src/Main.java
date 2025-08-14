import Model.*;
import View.*;
import Controller.*;

enum StatoOrdine {
    IN_ATTESA,
    ACCETTATO,
    RIFIUTATO,
    COMPLETATO
}

public class Main {
    public static void main(String[] args) {

        Menu menu = new MenuFacade();
        menu.display();

    }
}