package Model;

public class Venditore extends Utente{
    String descrizione;
    float rating;

    public Venditore(int id, String nome, String cognome, String email, String password,
                     String telefono,String descrizione, float rating) {
        super(id, nome, cognome, email, password, telefono);
        this.descrizione = "Ciao sono " + nome + " " + cognome + ", sono un venditore.";
        this.rating = 0.0f; // inizialmente il rating è 0
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
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
    public void Stampa() {
        super.Stampa();
        System.out.println("Descrizione: " + descrizione);
        System.out.println("Rating: " + rating);
    }
}
