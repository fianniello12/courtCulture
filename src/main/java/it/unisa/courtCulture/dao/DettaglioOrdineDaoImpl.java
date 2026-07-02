package it.unisa.courtCulture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.model.DettaglioOrdineBean;

public class DettaglioOrdineDaoImpl implements DettaglioOrdineDao {

    private static final String TABLE_NAME = "dettaglio_ordine";
    private DataSource ds = null;

    public DettaglioOrdineDaoImpl(DataSource ds) {
        this.ds = ds;
    }

    public synchronized void doSave(DettaglioOrdineBean dettaglio) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_ordine, codice_prodotto, quantita, prezzo_acquisto) VALUES (?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            ps.setInt(1, dettaglio.getIdOrdine());
            ps.setInt(2, dettaglio.getCodiceProdotto());

            if (dettaglio.getQuantita() > 0) {
                ps.setInt(3, dettaglio.getQuantita());
            } else {
                ps.setInt(3, 1);
            }

            ps.setFloat(4, dettaglio.getPrezzoAcquisto());

            ps.executeUpdate();
        }
    }

    public synchronized boolean doUpdate(DettaglioOrdineBean dettaglio) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME
                + " SET quantita = ?, prezzo_acquisto = ? WHERE id_ordine = ? AND codice_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {

            ps.setInt(1, dettaglio.getQuantita());
            ps.setFloat(2, dettaglio.getPrezzoAcquisto());
            ps.setInt(3, dettaglio.getIdOrdine());
            ps.setInt(4, dettaglio.getCodiceProdotto());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doDelete(int idOrdine, int codiceProdotto) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME
                + " WHERE id_ordine = ? AND codice_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSQL)) {

            ps.setInt(1, idOrdine);
            ps.setInt(2, codiceProdotto);

            int result = ps.executeUpdate();
            return result != 0;
        }
    }

    public synchronized DettaglioOrdineBean doRetrieveByKey(int idOrdine, int codiceProdotto) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME
                + " WHERE id_ordine = ? AND codice_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {

            ps.setInt(1, idOrdine);
            ps.setInt(2, codiceProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractDettaglioOrdine(rs);
                }
            }
        }

        return null;
    }

    public synchronized List<DettaglioOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
        List<DettaglioOrdineBean> dettagli = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DettaglioOrdineBean dettaglio = extractDettaglioOrdine(rs);
                    dettagli.add(dettaglio);
                }
            }
        }

        return dettagli;
    }

    public synchronized List<DettaglioOrdineBean> doRetrieveByProdotto(int codiceProdotto) throws SQLException {
        List<DettaglioOrdineBean> dettagli = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {

            ps.setInt(1, codiceProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DettaglioOrdineBean dettaglio = extractDettaglioOrdine(rs);
                    dettagli.add(dettaglio);
                }
            }
        }

        return dettagli;
    }

    public synchronized List<DettaglioOrdineBean> doRetrieveAll(String order) throws SQLException {
        List<DettaglioOrdineBean> dettagli = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            if (order.equals("id_ordine")
                    || order.equals("codice_prodotto")
                    || order.equals("quantita")
                    || order.equals("prezzo_acquisto")) {

                selectSQL += " ORDER BY " + order;
            }
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DettaglioOrdineBean dettaglio = extractDettaglioOrdine(rs);
                dettagli.add(dettaglio);
            }
        }

        return dettagli;
    }

    private DettaglioOrdineBean extractDettaglioOrdine(ResultSet rs) throws SQLException {
        DettaglioOrdineBean dettaglio = new DettaglioOrdineBean();

        dettaglio.setIdOrdine(rs.getInt("id_ordine"));
        dettaglio.setCodiceProdotto(rs.getInt("codice_prodotto"));
        dettaglio.setQuantita(rs.getInt("quantita"));
        dettaglio.setPrezzoAcquisto(rs.getFloat("prezzo_acquisto"));

        return dettaglio;
    }
}
