package DAO;

import Model.Ordine;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class OrdineDAO extends DAO {

    public OrdineDAO() {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Object select(int id) {
        String query = "SELECT * FROM ordine WHERE id_ordine = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                int idCliente = rs.getInt("id_cliente");
                int idServizio = rs.getInt("id_servizio");
                Date dataOrdine = rs.getDate("data_ordine");
                Date dataConsegna = rs.getDate("data_consegna");
                String statoOrdine = rs.getString("stato_ordine");

                return new Ordine(idOrdine, idCliente, idServizio, dataOrdine, dataConsegna, statoOrdine);
            } else {
                System.out.println("Ordine con ID " + id + " non trovato.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dell'ordine!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> selectAll() {
        List<Object> ordini = new ArrayList<>();
        String query = "SELECT * FROM ordine";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                int idCliente = rs.getInt("id_cliente");
                int idServizio = rs.getInt("id_servizio");
                Date dataOrdine = rs.getDate("data_ordine");
                Date dataConsegna = rs.getDate("data_consegna");
                String statoOrdine = rs.getString("stato_ordine");

                ordini.add(new Ordine(idOrdine, idCliente, idServizio, dataOrdine, dataConsegna, statoOrdine));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero degli ordini!");
            e.printStackTrace();
        }
        return ordini;
    }

    @Override
    public int insert(Object obj) {
        String query = "INSERT INTO ordine (id_cliente, id_servizio, data_ordine, data_consegna, stato_ordine) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Ordine ordine = (Ordine) obj;
            stmt.setInt(1, ordine.getId_cliente());
            stmt.setInt(2, ordine.getId_servizio());
            stmt.setDate(3, new java.sql.Date(ordine.getDataOrdine().getTime()));
            stmt.setDate(4, ordine.getDataConsegna() != null ? new java.sql.Date(ordine.getDataConsegna().getTime()) : null);
            stmt.setString(5, ordine.getStatoOrdine());

            int righeInserite = stmt.executeUpdate();
            if (righeInserite > 0) {
                System.out.println("✅ Ordine inserito con successo.");
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Restituisce l'ID generato
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'inserimento dell'ordine!");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean update(Object obj) {
        String query = "UPDATE ordine SET id_cliente = ?, id_servizio = ?, data_ordine = ?, data_consegna = ?, stato_ordine = ? WHERE id_ordine = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Ordine ordine = (Ordine) obj;
            stmt.setInt(1, ordine.getId_cliente());
            stmt.setInt(2, ordine.getId_servizio());
            stmt.setDate(3, new java.sql.Date(ordine.getDataOrdine().getTime()));
            stmt.setDate(4, ordine.getDataConsegna() != null ? new java.sql.Date(ordine.getDataConsegna().getTime()) : null);
            stmt.setString(5, ordine.getStatoOrdine());
            stmt.setInt(6, ordine.getId_ordine());

            int righeAggiornate = stmt.executeUpdate();
            if (righeAggiornate > 0) {
                System.out.println("✅ Ordine aggiornato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'aggiornamento dell'ordine!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM ordine WHERE id_ordine = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int righeCancellate = stmt.executeUpdate();
            if (righeCancellate > 0) {
                System.out.println("✅ Ordine eliminato con successo.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante l'eliminazione dell'ordine!");
            e.printStackTrace();
        }
        return false;
    }


    public List<Ordine> selectByCliente(int id_cliente) {
        List<Ordine> risultati = new ArrayList<>();
        String query = "SELECT * FROM ordine WHERE id_cliente = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, id_cliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                int idCliente = rs.getInt("id_cliente");
                int idServizio = rs.getInt("id_servizio");
                Date dataOrdine = rs.getDate("data_ordine");
                Date dataConsegna = rs.getDate("data_consegna");
                String statoOrdine = rs.getString("stato_ordine");

                risultati.add(new Ordine(idOrdine, idCliente, idServizio, dataOrdine, dataConsegna, statoOrdine));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero degli ordini!");
            e.printStackTrace();
        }
        return risultati;
    }

    public List<Ordine> selectByVenditore(int id_Venditore) {
        List<Ordine> risultati = new ArrayList<>();
        String query = "SELECT * FROM ordine WHERE id_Venditore = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, id_Venditore);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                int idCliente = rs.getInt("id_cliente");
                int idServizio = rs.getInt("id_servizio");
                Date dataOrdine = rs.getDate("data_ordine");
                Date dataConsegna = rs.getDate("data_consegna");
                String statoOrdine = rs.getString("stato_ordine");

                risultati.add(new Ordine(idOrdine, idCliente, idServizio, dataOrdine, dataConsegna, statoOrdine));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero degli ordini!");
            e.printStackTrace();
        }
        return risultati;
    }
    // recupera gli ordini di un venditore in base allo stato dell'ordine
    public List<Ordine> selectByVenditoreAndStato(int id_Venditore, String statoOrdine) {
        List<Ordine> risultati = new ArrayList<>();
        String query = "SELECT * " +
                "FROM ordine JOIN servizio ON id_servizio WHERE id_Venditore = ? AND stato_ordine = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, id_Venditore);
            stmt.setString(2, statoOrdine);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                int idCliente = rs.getInt("id_cliente");
                int idServizio = rs.getInt("id_servizio");
                Date dataOrdine = rs.getDate("data_ordine");
                Date dataConsegna = rs.getDate("data_consegna");

                risultati.add(new Ordine(idOrdine, idCliente, idServizio, dataOrdine, dataConsegna, statoOrdine));
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero degli ordini!");
            e.printStackTrace();
        }
        return risultati;
    }

    //recupera l'id del venditore associato all'ordine tramite un join con la tabella servizio
    public int getIdVenditore (int idOrdine) {
        String query = "SELECT id_venditore " +
                "FROM ordine JOIN servizio on id_servizio" +
                "WHERE id_ordine = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idOrdine);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_venditore");
            } else {
                System.out.println("Ordine con ID " + idOrdine + " non trovato.");
                return -1; // Ordine non trovato
            }

        } catch (SQLException e) {
            System.out.println("❌ Errore durante il recupero dell'ID venditore dell'ordine!");
            e.printStackTrace();
        }
        return -1; // Errore durante l'esecuzione della query
    }



}
