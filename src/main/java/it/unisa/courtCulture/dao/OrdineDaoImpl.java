package it.unisa.courtCulture.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.model.OrdineBean;

public class OrdineDaoImpl implements OrdineDao {

    private static final String TABLE_NAME = "ordine";
    private DataSource ds = null;

    public OrdineDaoImpl(DataSource ds) {
        this.ds = ds;
    }

    public synchronized int doSave(OrdineBean ordine) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (id_utente, data_ordine, stato_ordine, totale_ordine, indirizzo_spedizione, metodo_pagamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ordine.getIdUtente());

            if (ordine.getDataOrdine() != null && !ordine.getDataOrdine().isEmpty()) {
                ps.setDate(2, Date.valueOf(ordine.getDataOrdine()));
            } else {
                ps.setDate(2, Date.valueOf(LocalDate.now()));
            }

            if (ordine.getStatoOrdine() != null && !ordine.getStatoOrdine().isEmpty()) {
                ps.setString(3, ordine.getStatoOrdine());
            } else {
                ps.setString(3, "in elaborazione");
            }

            ps.setFloat(4, ordine.getTotaleOrdine());
            
            ps.setString(5,ordine.getIndirizzoSpedizione());


            ps.setString(6,ordine.getMetodoPagamento());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        return -1;
    }

    public synchronized boolean doUpdate(OrdineBean ordine) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME
                + " SET id_utente = ?, data_ordine = ?, stato_ordine = ?, totale_ordine = ? indirizzo_spedizione = ?, metodo_pagamento = ? WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {

            ps.setInt(1, ordine.getIdUtente());

            if (ordine.getDataOrdine() != null && !ordine.getDataOrdine().isEmpty()) {
                ps.setDate(2, Date.valueOf(ordine.getDataOrdine()));
            } else {
                ps.setDate(2, Date.valueOf(LocalDate.now()));
            }

            ps.setString(3, ordine.getStatoOrdine());
            ps.setFloat(4, ordine.getTotaleOrdine());
            ps.setString(5,ordine.getIndirizzoSpedizione());
            ps.setString(6,ordine.getMetodoPagamento());
            ps.setInt(7,ordine.getIdOrdine());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doUpdateStato(int idOrdine, String statoOrdine) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET stato_ordine = ? WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {

            ps.setString(1, statoOrdine);
            ps.setInt(2, idOrdine);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    public synchronized boolean doDelete(int idOrdine) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSQL)) {

            ps.setInt(1, idOrdine);

            int result = ps.executeUpdate();
            return result != 0;
        }
    }

    public synchronized OrdineBean doRetrieveByKey(int idOrdine) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractOrdine(rs);
                }
            }
        }

        return null;
    }

    public synchronized List<OrdineBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<OrdineBean> ordini = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME
                + " WHERE id_utente = ? ORDER BY data_ordine DESC";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {

            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean ordine = extractOrdine(rs);
                    ordini.add(ordine);
                }
            }
        }

        return ordini;
    }

    public synchronized List<OrdineBean> doRetrieveAll(String order) throws SQLException {
        List<OrdineBean> ordini = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            if (order.equals("id_ordine") || order.equals("id_utente") || order.equals("data_ordine") || order.equals("stato_ordine") || order.equals("totale_ordine")) {

                selectSQL += " ORDER BY " + order;
            }
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrdineBean ordine = extractOrdine(rs);
                ordini.add(ordine);
            }
        }

        return ordini;
    }

    private OrdineBean extractOrdine(ResultSet rs) throws SQLException {
        OrdineBean ordine = new OrdineBean();

        ordine.setIdOrdine(rs.getInt("id_ordine"));
        ordine.setIdUtente(rs.getInt("id_utente"));
        ordine.setDataOrdine(rs.getString("data_ordine"));
        ordine.setStatoOrdine(rs.getString("stato_ordine"));
        ordine.setTotaleOrdine(rs.getFloat("totale_ordine"));
        ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
        ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));

        return ordine;
    }
}