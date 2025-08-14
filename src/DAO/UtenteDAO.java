package DAO;

import Model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO extends DAO {

    // Constructor
    public UtenteDAO() {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Object select(int id){
        String query = "SELECT * FROM Utente WHERE id_utente = ?";


        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {
            // Imposta il parametro nella query (1 indica la prima '?' nella query)
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id_utente = rs.getInt("id_utente");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telefono = rs.getString("telefono");
                return new Utente(id_utente, nome, cognome, email, password, telefono);
            }
            else {
                System.out.println("Utente con ID " + id + " non trovato.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero del Utente!");
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Object> selectAll(){
        List<Object> utenti = new ArrayList<>();
        String query = "SELECT * FROM Utente";


        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_utente = rs.getInt("id_utente");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String telefono = rs.getString("telefono");
                utenti.add(new Utente(id_utente, nome, cognome, email, password, telefono));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei clienti!");
            e.printStackTrace();
        }
        return utenti;
    }
    @Override
    public boolean delete(int id){
        String query = "DELETE FROM Utente WHERE id_utente=?;";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1,id);

            // Esegue l'operazione di delete
            int righeCancellate = stmt.executeUpdate();

            if( righeCancellate > 0){
                System.out.println("✅ Utente con ID " + id + " eliminato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero degli utenti!");
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean insert(Object obj){
        String query = "INSERT INTO Utente (nome, cognome, email, password, telefono) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {
            // Cast dell'oggetto a Cliente
            Utente nuovoUtente = (Utente) obj;
            // Imposta i parametri nella query
            stmt.setString(1, nuovoUtente.getNome());
            stmt.setString(2, nuovoUtente.getCognome());
            stmt.setString(3, nuovoUtente.getEmail());
            stmt.setString(4, nuovoUtente.getPassword());
            stmt.setString(5, nuovoUtente.getTelefono());

            // Esegue l'operazione di delete
            int righeInserite = stmt.executeUpdate();

            if( righeInserite > 0){
                System.out.println("✅ nuovo utente inserito con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dei utenti!");
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean update(Object obj){
        String query = "UPDATE cliente SET nome = ?, cognome = ?, email = ?, password = ?, telefono = ? WHERE id = ?;";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Utente utenteAggiornato = (Utente) obj;

            stmt.setString(1, utenteAggiornato.getNome());
            stmt.setString(2, utenteAggiornato.getCognome());
            stmt.setString(3, utenteAggiornato.getEmail());
            stmt.setString(4, utenteAggiornato.getPassword());
            stmt.setString(5, utenteAggiornato.getTelefono());
            stmt.setInt(6, utenteAggiornato.getId()); // WHERE id = ?

            int righeAggiornate = stmt.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("✅ Utente aggiornato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'aggiornamento del utente!");
            e.printStackTrace();
        }
        return false;
    }



}
