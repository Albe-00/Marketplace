package Model;

public class Utente {

    protected int id;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;
    protected String telefono;
    private boolean venditore;

    public Utente(int id, String nome, String cognome, String email, String password, String telefono, boolean venditore) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.venditore = venditore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEqualPassword(String password) {
        return this.password.equals(password);
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public void stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Cognome: " + cognome);
        System.out.println("Email: " + email);
        System.out.println("Telefono: " + telefono);
        System.out.println("Venditore: " + (venditore ? "Si" : "No"));
        System.out.println("------------------------------------------");
    }
}
