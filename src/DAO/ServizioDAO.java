package DAO;

import Model.Servizio;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;


public class ServizioDAO extends DAO {
    public ServizioDAO() {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Object select(int id) {
        String query = "SELECT * FROM servizio WHERE id_servizio = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idServizio = rs.getInt("id_servizio");
                int idVenditore = rs.getInt("id_venditore");
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                float prezzo = rs.getFloat("prezzo");
                String categoria = rs.getString("categoria");
                Date dataPubblicazione = rs.getDate("data_pubblicazione");
                boolean visibile = rs.getBoolean("visibile");

                return new Servizio(idServizio, idVenditore, titolo, descrizione, prezzo, categoria, dataPubblicazione, visibile);
            } else {
                System.out.println("Servizio con ID " + id + " non trovato.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero del servizio!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> selectAll() {
        ArrayList<Object> servizi = new ArrayList<>();
        String query = "SELECT * FROM servizio";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idServizio = rs.getInt("id_servizio");
                int idVenditore = rs.getInt("id_venditore");
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                float prezzo = rs.getFloat("prezzo");
                String categoria = rs.getString("categoria");
                Date dataPubblicazione = rs.getDate("data_pubblicazione");
                boolean visibile = rs.getBoolean("visibile");

                servizi.add(new Servizio(idServizio, idVenditore, titolo, descrizione, prezzo, categoria, dataPubblicazione, visibile));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei servizi!");
            e.printStackTrace();
        }
        return servizi;
    }

    @Override
    public int insert(Object obj) {
        String query = "INSERT INTO servizio (id_venditore, titolo, descrizione, prezzo, categoria, data_pubblicazione, visibile) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Servizio s = (Servizio) obj;
            stmt.setInt(1, s.getId_venditore());
            stmt.setString(2, s.getTitolo());
            stmt.setString(3, s.getDescrizione());
            stmt.setDouble(4, s.getPrezzo());
            stmt.setString(5, s.getCategoria());
            stmt.setDate(6, new java.sql.Date(s.getDataPubblicazione().getTime()));
            stmt.setBoolean(7, s.isVisibile());

            int righeInserite = stmt.executeUpdate();
            if (righeInserite > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                System.out.println("✅ Servizio inserito con successo.");
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Restituisce l'ID generato
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'inserimento del servizio!");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean update(Object obj) {
        String query = "UPDATE servizio SET id_venditore = ?, titolo = ?, descrizione = ?, prezzo = ?, categoria = ?, data_pubblicazione = ?, visibile = ? WHERE id_servizio = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Servizio s = (Servizio) obj;
            stmt.setInt(1, s.getId_venditore());
            stmt.setString(2, s.getTitolo());
            stmt.setString(3, s.getDescrizione());
            stmt.setDouble(4, s.getPrezzo());
            stmt.setString(5, s.getCategoria());
            stmt.setDate(6, new java.sql.Date(s.getDataPubblicazione().getTime()));
            stmt.setBoolean(7, s.isVisibile());
            stmt.setInt(8, s.getId_servizio());

            int righeAggiornate = stmt.executeUpdate();
            if (righeAggiornate > 0) {
                System.out.println("✅ Servizio aggiornato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'aggiornamento del servizio!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM servizio WHERE id_servizio = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int righeCancellate = stmt.executeUpdate();
            if (righeCancellate > 0) {
                System.out.println("✅ Servizio eliminato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'eliminazione del servizio!");
            e.printStackTrace();
        }
        return false;
    }

    public List<Servizio> selectByVenditore(int idVenditore) {
        List<Servizio> servizi = new ArrayList<>();
        String query = "SELECT * FROM servizio WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idVenditore);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idServizio = rs.getInt("id_servizio");
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                float prezzo = rs.getFloat("prezzo");
                String categoria = rs.getString("categoria");
                Date dataPubblicazione = rs.getDate("data_pubblicazione");
                boolean visibile = rs.getBoolean("visibile");

                Servizio servizio = new Servizio(idServizio, idVenditore, titolo, descrizione, prezzo, categoria, dataPubblicazione, visibile);
                servizi.add(servizio);
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei servizi del venditore!");
            e.printStackTrace();
        }
        return servizi;
    }

    public List<Servizio> selectServiziVisibiliByVenditore(int idVenditore) {
        List<Servizio> servizi = new ArrayList<>();
        String query = "SELECT * FROM servizio WHERE id_venditore = ? and visibile = TRUE";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idVenditore);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idServizio = rs.getInt("id_servizio");
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                float prezzo = rs.getFloat("prezzo");
                String categoria = rs.getString("categoria");
                Date dataPubblicazione = rs.getDate("data_pubblicazione");
                boolean visibile = rs.getBoolean("visibile");

                Servizio servizio = new Servizio(idServizio, idVenditore, titolo, descrizione, prezzo, categoria, dataPubblicazione, visibile);
                servizi.add(servizio);
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei servizi del venditore!");
            e.printStackTrace();
        }
        return servizi;
    }

    public int countByVenditore(int idVenditore) {

        String query = "SELECT count(*) as numeroServizi FROM servizio WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idVenditore);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt("numeroServizi");

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei servizi del venditore!");
            e.printStackTrace();
        }
        return -1;
    }

    public List<Servizio> cercaServizi(String ricerca) {
        List<Servizio> risultati = new ArrayList<>();

        String query = "SELECT * FROM servizio WHERE visibile = TRUE AND (id_servizio LIKE ? OR titolo LIKE ? OR descrizione LIKE ? OR categoria LIKE ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + ricerca + "%";

            // Prova a capire se 'ricerca' è un numero
            int idValue;
            try {
                idValue = Integer.parseInt(ricerca); // caso numerico
            } catch (NumberFormatException e) {
                idValue = -1; // così non corrispondera mai ad un id_servizio
            }

            stmt.setInt(1, idValue);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Servizio servizio = new Servizio(
                            rs.getInt("id_servizio"),
                            rs.getInt("id_venditore"),
                            rs.getString("titolo"),
                            rs.getString("descrizione"),
                            rs.getFloat("prezzo"),
                            rs.getString("categoria"),
                            rs.getDate("data_pubblicazione"),
                            rs.getBoolean("visibile")
                    );
                    risultati.add(servizio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return risultati;
    }
}
