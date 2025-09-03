package DAO;

import Model.Recensione;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO extends DAO {

    public RecensioneDAO() {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Object select(int id) {
        String query = "SELECT * FROM recensione WHERE id_recensione = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idRecensione = rs.getInt("id_recensione");
                int idAutore = rs.getInt("id_autore");
                int idVenditore = rs.getInt("id_venditore");
                float voto = rs.getFloat("voto");
                String testo = rs.getString("testo");

                return new Recensione(idRecensione, idAutore, idVenditore, voto, testo);
            } else {
                System.out.println("Recensione con ID " + id + " non trovata.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante il recupero della recensione!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> selectAll() {
        List<Object> recensioni = new ArrayList<>();
        String query = "SELECT * FROM recensione";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idRecensione = rs.getInt("id_recensione");
                int idAutore = rs.getInt("id_autore");
                int idVenditore = rs.getInt("id_venditore");
                float voto = rs.getFloat("voto");
                String testo = rs.getString("testo");

                recensioni.add(new Recensione(idRecensione, idAutore, idVenditore, voto, testo));
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante il recupero delle recensioni!");
            e.printStackTrace();
        }
        return recensioni;
    }

    @Override
    public int insert(Object obj) {
        String query = "INSERT INTO recensione (id_autore, id_venditore, voto, testo) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
             ) {

            Recensione r = (Recensione) obj;
            stmt.setInt(1, r.getId_autore());
            stmt.setInt(2, r.getId_venditore());
            stmt.setFloat(3, r.getVoto());
            stmt.setString(4, r.getTesto());

            int righeInserite = stmt.executeUpdate();
            if (righeInserite > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                System.out.println("Recensione inserita con successo.");
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Restituisce l'ID generato
                }
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante l'inserimento della recensione!");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean update(Object obj) {
        String query = "UPDATE recensione SET id_autore = ?, id_venditore = ?, voto = ?, testo = ? WHERE id_recensione = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Recensione r = (Recensione) obj;
            stmt.setInt(1, r.getId_autore());
            stmt.setInt(2, r.getId_venditore());
            stmt.setFloat(3, r.getVoto());
            stmt.setString(4, r.getTesto());
            stmt.setInt(5, r.getId_recensione());

            int righeAggiornate = stmt.executeUpdate();
            if (righeAggiornate > 0) {
                System.out.println("Recensione aggiornata con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante l'aggiornamento della recensione!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM recensione WHERE id_recensione = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int righeCancellate = stmt.executeUpdate();
            if (righeCancellate > 0) {
                System.out.println("Recensione eliminata con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! -Errore durante l'eliminazione della recensione!");
            e.printStackTrace();
        }
        return false;
    }

    public int countByVenditore(int id_venditore) {
        String query = "SELECT COUNT(*) AS numRecensioni FROM recensione WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_venditore);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("numRecensioni"); // prende il valore del COUNT
            }
            else
                return 0; // se non trova nulla ritorna 0

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante il conteggio delle recensioni!");
            e.printStackTrace();
        }

        return -1; // se c'e in errore ritorna -1
    }

    public List<Recensione> selectByVenditore(int id_Venditore) {
        List<Recensione> risultati = new ArrayList<>();
        String query = "SELECT * FROM recensione WHERE id_Venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_Venditore);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRecensione = rs.getInt("id_recensione");
                int idAutore = rs.getInt("id_autore");
                int idVenditore = rs.getInt("id_venditore");
                float voto = rs.getFloat("voto");
                String testo = rs.getString("testo");

                risultati.add(new Recensione(idRecensione, idAutore, idVenditore, voto, testo));
            }

        } catch (SQLException e) {
            System.out.println("!! ERRORE !! - Errore durante il recupero delle recensioni!");
            e.printStackTrace();
        }
        return risultati;
    }
}
