package it.unisa.courtCulture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.model.ProdottoBean;


public class ProdottoDaoImpl {
	
	private static final String TABLE_NAME = "prodotto";
    private DataSource ds = null;

    public ProdottoDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    
    public synchronized void doSave(ProdottoBean product) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (nome, descrizione, prezzo, quantita_disponibile, categoria, brand) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, product.getNome());
            preparedStatement.setString(2, product.getDescrizione());
            preparedStatement.setFloat(3, product.getPrezzo());
            preparedStatement.setInt(4, product.getQuantitaDisponibile());
            preparedStatement.setString(5,product.getCategoria());
            preparedStatement.setString(6,product.getBrand());
            preparedStatement.executeUpdate();
        }
    }
    
    public synchronized boolean doUpdateImage(ProdottoBean product) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET path_immagine = ?, mime_type = ? WHERE codice = ?";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getPathImmagine());
            ps.setString(2, product.getMimeType());
            ps.setInt(3, product.getCodice());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }
    
    public synchronized boolean doUpdate(ProdottoBean product) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET nome = ?, descrizione = ?, prezzo = ?,	quantita_disponibile = ?, categoria = ?, brand = ?, attivo = ? WHERE codice = ? ";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,product.getNome());
            ps.setString(2,product.getDescrizione());
            ps.setFloat(3,product.getPrezzo());
            ps.setInt(4,product.getQuantitaDisponibile());
            ps.setString(5,product.getCategoria());
            ps.setString(6,product.getBrand());
            ps.setBoolean(7,product.isAttivo());
            ps.setInt(8, product.getCodice());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }
    
    public synchronized ProdottoBean doRetrieveByKey(int code) throws SQLException {
        ProdottoBean bean = new ProdottoBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setCodice(rs.getInt("codice"));
                    bean.setNome(rs.getString("nome"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getFloat("prezzo"));
                    bean.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                    bean.setCategoria(rs.getString("categoria"));
                    bean.setBrand(rs.getString("brand"));
                    bean.setPathImmagine(rs.getString("path_immagine"));
                    bean.setMimeType(rs.getString("mime_type"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                }
            }
        }
        return bean;
    }
    
    public synchronized List<ProdottoBean> doRetrieveByBrand(String brand) throws SQLException {
    	List<ProdottoBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE brand = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, brand);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	ProdottoBean bean = new ProdottoBean();
                	
                	bean.setCodice(rs.getInt("codice"));
                    bean.setNome(rs.getString("nome"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getFloat("prezzo"));
                    bean.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                    bean.setCategoria(rs.getString("categoria"));
                    bean.setBrand(rs.getNString("brand"));
                    bean.setPathImmagine(rs.getString("path_immagine"));
                    bean.setMimeType(rs.getString("mime_type"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    
                    products.add(bean);
                }
            }
        }
        return products;
    }

    public synchronized List<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException {
    	List<ProdottoBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE categoria = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, categoria);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	ProdottoBean bean = new ProdottoBean();
                	
                    bean.setCodice(rs.getInt("codice"));
                    bean.setNome(rs.getString("nome"));
                    bean.setDescrizione(rs.getString("descrizione"));
                    bean.setPrezzo(rs.getFloat("prezzo"));
                    bean.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                    bean.setCategoria(rs.getString("categoria"));
                    bean.setBrand(rs.getNString("brand"));
                    bean.setPathImmagine(rs.getString("path_immagine"));
                    bean.setMimeType(rs.getString("mime_type"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                    
                    products.add(bean);

                }
            }
        }
        return products;
    }

    public synchronized boolean doDelete(int code) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE codice = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }
    
    public synchronized List<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        List<ProdottoBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            if (order.equals("nome") || order.equals("prezzo") || order.equals("categoria") || order.equals("brand")) {
                selectSQL += " ORDER BY " + order;
            }
        }
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        		ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
            	ProdottoBean bean = new ProdottoBean();
            	bean.setCodice(rs.getInt("codice"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getFloat("prezzo"));
                bean.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setBrand(rs.getNString("brand"));
                bean.setPathImmagine(rs.getString("path_immagine"));
                bean.setMimeType(rs.getString("mime_type"));
                bean.setAttivo(rs.getBoolean("attivo"));
                products.add(bean);
            }
        }
        return products;
    }
    
}
