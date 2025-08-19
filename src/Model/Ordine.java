package Model;
import java.util.Date;


/* StatoOrdine può essere uno dei seguenti:{
    IN ATTESA,
    IN LAVORAZIONE,
    RIFIUTATO,
    COMPLETATO
}*/
public class Ordine {
    private int id_ordine;
    int id_cliente;
    int id_servizio;
    //fixme aggiungi prezzo dell'ordine
    private Date dataOrdine;
    private Date dataConsegna;
    private String statoOrdine;

    public Ordine(int id_ordine, int id_cliente, int id_servizio, Date dataOrdine,Date dataConsegna, String statoOrdine) {
        this.id_ordine = id_ordine;
        this.id_cliente = id_cliente;
        this.id_servizio= id_servizio;
        this.dataOrdine = dataOrdine;
        this.dataConsegna = dataConsegna;
        this.statoOrdine = statoOrdine;

    }
    public Ordine(int id_cliente, int id_servizio, Date dataOrdine) {
        this.id_ordine = 0;
        this.id_cliente = id_cliente;
        this.id_servizio= id_servizio;
        this.dataOrdine = dataOrdine;
        this.dataConsegna = null;
        this.statoOrdine = "IN ATTESA";

    }

    public int getId_ordine() {
        return id_ordine;
    }

    public void setId_ordine(int id_ordine) {
        this.id_ordine = id_ordine;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_servizio() {
        return id_servizio;
    }

    public void setId_servizio(int servizio) {
        this.id_servizio = servizio;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(String nuovostatoOrdine) {
        // Controlla se il nuovo stato è nullo o vuoto
        if(statoOrdine == null || statoOrdine.isEmpty()) {
            return; // Non accetta stati nulli o vuoti
        }
        // Controlla se il nuovo stato è uno dei valori validi
        if(!statoOrdine.equals("IN ATTESA") &&
           !statoOrdine.equals("IN LAVORAZIONE") &&
           !statoOrdine.equals("RIFIUTATO") &&
           !statoOrdine.equals("COMPLETATO")) {
            return;
        }
        // Non può essere modificato se già completato
        if(statoOrdine.equals("COMPLETATO") || statoOrdine.equals("RIFIUTATO")) {
            return; // Lo stato dell'ordine NON può essere modificato se già completato o rifiutato
        }
        // Se lo stato è "IN ATTESA", può essere cambiato solo in "IN LAVORAZIONE" o "RIFIUTATO"
        if(statoOrdine.equals("IN ATTESA") && !nuovostatoOrdine.equals("IN LAVORAZIONE") && !nuovostatoOrdine.equals("RIFIUTATO")) {
            return;
        }
        // Se lo stato è "IN LAVORAZIONE", può essere cambiato solo in "COMPLETATO"
        if(statoOrdine.equals("IN LAVORAZIONE") && !nuovostatoOrdine.equals("COMPLETATO")) {
            return;
        }
        this.statoOrdine = nuovostatoOrdine;
    }

    public void stampa() {
        System.out.println("------------------------------------------");
        System.out.println("ID: " + id_ordine);
        System.out.println("ID Cliente: " + id_cliente);
        System.out.println("ID Servizio: " + id_servizio);

        System.out.println("Data Ordine : " + dataOrdine);
        System.out.println("Data Consegna : " + dataConsegna);
        System.out.println("Stato: " + statoOrdine);
    }
}
