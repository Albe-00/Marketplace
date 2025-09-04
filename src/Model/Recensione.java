package Model;
import java.util.Date;

public class Recensione {
    private final int id_recensione;
    private final int id_autore;
    private final int id_venditore;
    private final float voto;
    private final String testo;
    private final Date dataRecensione;

    public Recensione(int id_recensione,int id_autore, int id_venditore, float voto, String testo, Date dataRecensione) {
        this.id_recensione = id_recensione;
        this.id_autore = id_autore;
        this.id_venditore = id_venditore;
        this.voto = voto;
        this.testo = testo;
        this.dataRecensione = dataRecensione;
    }
    public Recensione(int id_autore, int id_venditore, float voto, String testo) {
        this.id_recensione = 0;
        this.id_autore = id_autore;
        this.id_venditore = id_venditore;
        this.voto = voto;
        this.testo = testo;
        this.dataRecensione = null;
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

    public float getVoto() {
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
        System.out.println("Data Recensione: " + dataRecensione);
    }
}
