package DAO;

import Model.Servizio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


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
                double prezzo = rs.getDouble("prezzo");
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
                double prezzo = rs.getDouble("prezzo");
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
    public boolean insert(Object obj) {
        String query = "INSERT INTO servizio (id_venditore, titolo, descrizione, prezzo, categoria, data_pubblicazione, visibile) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

            int righeInserite = stmt.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("✅ Servizio inserito con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'inserimento del servizio!");
            e.printStackTrace();
        }
        return false;
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


}
