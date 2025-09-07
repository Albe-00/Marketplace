package Model;

public class Venditore extends Utente{
    private String descrizione;
    private float rating;

    public Venditore(int id, String nome, String cognome, String email, String password,
                     String telefono,String descrizione,float rating) {
        super(id, nome, cognome, email, password, telefono,true);
        this.descrizione = descrizione;
        this.rating = rating;
    }
    public Venditore(int id, String nome, String cognome, String email, String password,
                     String telefono,String descrizione) {
        super(id, nome, cognome, email, password, telefono,true);
        this.descrizione = descrizione;
        this.rating = 0.0f; // inizialmente il rating è 0
    }
    public Venditore(Utente utente,String descrizione) {
        super(utente.getId(), utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword(), utente.getTelefono(),true);
        this.descrizione = descrizione;
        this.rating = 0.0f; // inizialmente il rating è 0
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione){this.descrizione=descrizione;}
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public boolean isVenditore() {
        return true; // Un oggetto Venditore è sempre un venditore
    }

    @Override
    public void stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Cognome: " + cognome);
        System.out.println("Email: " + email);
        System.out.println("Telefono: " + telefono);
        System.out.println("Descrizione: " + descrizione);
        System.out.println("Rating: " + rating);
        System.out.println("------------------------------------------");
    }
}
