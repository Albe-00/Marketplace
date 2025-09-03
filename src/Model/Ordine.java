package Model;
import java.util.Date;


/* StatoOrdine può essere uno dei seguenti:{
    IN ATTESA,
    ANNULLATO,
    IN LAVORAZIONE,
    RIFIUTATO,
    COMPLETATO
}*/
public class Ordine {
    private final int id_ordine;
    private final int id_cliente;
    private final int id_servizio;
    private final float prezzo;
    private final Date dataOrdine;
    private final Date dataConsegna;
    private String statoOrdine;

    public Ordine(int id_ordine, int id_cliente, int id_servizio, float prezzo, Date dataOrdine, Date dataConsegna, String statoOrdine) {
        this.id_ordine = id_ordine;
        this.id_cliente = id_cliente;
        this.id_servizio= id_servizio;
        this.prezzo = prezzo;
        this.dataOrdine = dataOrdine;
        this.dataConsegna = dataConsegna;
        this.statoOrdine = statoOrdine;

    }
    public Ordine(int id_cliente, int id_servizio, float prezzo, Date dataOrdine) {
        this.id_ordine = 0;
        this.id_cliente = id_cliente;
        this.id_servizio= id_servizio;
        this.prezzo = prezzo;
        this.dataOrdine = dataOrdine;
        this.dataConsegna = null;
        this.statoOrdine = "IN ATTESA";

    }

    public int getId_ordine() {
        return id_ordine;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_servizio() {
        return id_servizio;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }

    public boolean setStatoOrdine(String nuovostatoOrdine) {
        // Controlla se il nuovo stato è nullo o vuoto
        if(nuovostatoOrdine == null || nuovostatoOrdine.isEmpty()) {
            return false; // Non accetta stati nulli o vuoti
        }
        // Controlla se il nuovo stato è uno dei valori validi
        if(!nuovostatoOrdine.equals("IN ATTESA") &&
           !nuovostatoOrdine.equals("ANNULLATO") &&
           !nuovostatoOrdine.equals("IN LAVORAZIONE") &&
           !nuovostatoOrdine.equals("RIFIUTATO") &&
           !nuovostatoOrdine.equals("COMPLETATO")) {
            return false; // Stato non valido
        }
        // Non può essere modificato se già completato
        if(statoOrdine.equals("COMPLETATO") || statoOrdine.equals("RIFIUTATO") || statoOrdine.equals("ANNULLATO")) {
            return false; // Lo stato dell'ordine NON può essere modificato se già completato, rifiutato o annullato
        }
        // Se lo stato è "IN ATTESA", può essere cambiato solo in "IN LAVORAZIONE" , "RIFIUTATO" o "ANNULLATO"
        if(statoOrdine.equals("IN ATTESA") && !(nuovostatoOrdine.equals("IN LAVORAZIONE") || nuovostatoOrdine.equals("RIFIUTATO") || nuovostatoOrdine.equals("ANNULLATO") )) {
            return false;
        }
        // Se lo stato è "IN LAVORAZIONE", può essere cambiato solo in "COMPLETATO"
        if(statoOrdine.equals("IN LAVORAZIONE") && !nuovostatoOrdine.equals("COMPLETATO")) {
            return false;
        }
        this.statoOrdine = nuovostatoOrdine;
        return true;
    }

    public void stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id_ordine);
        System.out.println("ID Cliente: " + id_cliente);
        System.out.println("ID Servizio: " + id_servizio);
        System.out.println("Prezzo: " + prezzo);
        System.out.println("Data Ordine : " + dataOrdine);
        System.out.println("Data Consegna : " + dataConsegna);
        System.out.println("Stato: " + statoOrdine);
    }
}
