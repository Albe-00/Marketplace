package Model;

public class Utente {

    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private boolean venditore;

    public Utente(int id, String nome, String cognome, String email, String password, String telefono) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.venditore = false; // Default value, can be changed later
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isVenditore() {
        return venditore;
    }
    public void setVenditore(boolean venditore) {
        this.venditore = venditore;
    }
    public void Stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Cognome: " + cognome);
        System.out.println("Email: " + email);
        System.out.println("Telefono: " + telefono);
    }
}
