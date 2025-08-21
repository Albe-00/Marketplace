package Model;

public class Recensione {
    private int id_recensione;
    private int id_autore;
    private int id_venditore;
    private int voto;
    private String testo;

    public Recensione(int id_recensione,int id_autore, int id_venditore,int voto, String testo ) {
        this.id_recensione = id_recensione;
        this.id_autore = id_autore;
        this.id_venditore = id_venditore;
        this.voto = voto;
        this.testo = testo;
    }
    public Recensione(int id_autore, int id_venditore,int voto, String testo ) {
        this.id_recensione = 0;
        this.id_autore = id_autore;
        this.id_venditore = id_venditore;
        this.voto = voto;
        this.testo = testo;
    }

    public int getId_recensione() {
        return id_recensione;
    }

    public int getId_autore() {
        return id_autore;
    }

    public int getId_venditore() {
        return id_venditore;
    }

    public int getVoto() {
        return voto;
    }

    public String getTesto() {
        return testo;
    }

    public void stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id_recensione);
        System.out.println("ID Autore: " + id_autore);
        System.out.println("Venditore: " + id_venditore);
        System.out.println("Voto: " + voto);
        System.out.println("Testo: " + testo);
    }
}
