package DAO;

import Model.Venditore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class VenditoreDAO extends UtenteDAO {
    public VenditoreDAO() {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Object select(int id) {
        String query =  "SELECT * " +
                        "FROM Utente join Venditore on id_venditore = id_utente " +
                        "WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idVenditore = rs.getInt("id_venditore");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telefono = rs.getString("telefono");
                String descrizione = rs.getString("descrizione");
                float rating = rs.getFloat("rating");
                return new Venditore(idVenditore, nome, cognome, email, password, telefono, descrizione, rating);
            } else {
                System.out.println("Venditore con ID " + id + " non trovato.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero del venditore!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> selectAll() {
        List<Object> venditori = new ArrayList<>();
        String query =  "SELECT * " +
                "FROM Utente join Venditore on id_venditore = id_utente ";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idVenditore = rs.getInt("id_venditore");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telefono = rs.getString("telefono");
                String descrizione = rs.getString("descrizione");
                float rating = rs.getFloat("rating");
                venditori.add(new Venditore(idVenditore, nome, cognome, email, password, telefono, descrizione,rating));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei venditori!");
            e.printStackTrace();
        }
        return venditori;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Venditore WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int righeCancellate = stmt.executeUpdate();

            if (righeCancellate > 0) {
                System.out.println("✅ Venditore con ID " + id + " eliminato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'eliminazione del venditore!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int insert(Object obj) {
        String query = "INSERT INTO Venditore (id_venditore,descrizione, rating) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Venditore nuovoVenditore = (Venditore) obj;
            stmt.setInt(1, nuovoVenditore.getId());
            stmt.setString(2, nuovoVenditore.getDescrizione());
            stmt.setFloat(3, nuovoVenditore.getRating());

            int righeInserite = stmt.executeUpdate();

            if (righeInserite > 0) {

                System.out.println("✅ Nuovo venditore inserito con successo.");
                return nuovoVenditore.getId(); // Restituisce l'ID del venditore appena inserito
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'inserimento del venditore!");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean update(Object obj) {
        String query = "UPDATE Venditore SET descrizione = ?, rating = ? WHERE id_venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Venditore venditoreAggiornato = (Venditore) obj;
            stmt.setString(1, venditoreAggiornato.getDescrizione());
            stmt.setFloat(2, venditoreAggiornato.getRating());
            stmt.setInt(3, venditoreAggiornato.getId());

            int righeAggiornate = stmt.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("✅ Venditore aggiornato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'aggiornamento del venditore!");
            e.printStackTrace();
        }
        return false;
    }

    public List<Venditore> cercaVenditori(String ricerca) {
        List<Venditore> risultati = new ArrayList<>();
        String query =  "SELECT * " +
                "FROM Utente join Venditore on id_venditore = id_utente " +
                "WHERE id_venditore LIKE ? OR nome LIKE ? OR cognome LIKE ? OR descrizione LIKE ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + ricerca + "%";

            // Valuta se 'ricerca' contiene un numero
            int idValue;
            try {
                idValue = Integer.parseInt(ricerca); // caso numerico
            } catch (NumberFormatException e) {
                idValue = -1; // così non corrispondera mai ad un id_venditore
            }

            // Imposta i parametri nella query
            stmt.setInt(1, idValue);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idVenditore = rs.getInt("id_venditore");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telefono = rs.getString("telefono");
                String descrizione = rs.getString("descrizione");
                float rating = rs.getFloat("rating");
                risultati.add(new Venditore(idVenditore, nome, cognome, email, password, telefono, descrizione,rating));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return risultati;
    }

}
