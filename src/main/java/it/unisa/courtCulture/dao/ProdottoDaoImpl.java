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
                    bean.setBrand(rs.getNString("brand"));
                    bean.setPathImmagine(rs.getString("path_immagine"));
                    bean.setMimeType(rs.getString("mime_type"));
                    bean.setAttivo(rs.getBoolean("attivo"));
                }
            }
        }
        return bean;
    }
    
    public synchronized ProdottoBean doRetrieveByBrand(String brand) throws SQLException {
        ProdottoBean bean = new ProdottoBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE brand = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(7, brand);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setCodice(rs.getInt("code"));
                    bean.setNome(rs.getString("name"));
                    bean.setDescrizione(rs.getString("description"));
                    bean.setPrezzo(rs.getFloat("price"));
                    bean.setQuantitaDisponibile(rs.getInt("quantity"));
                    bean.setPathImmagine(rs.getString("path"));
                    bean.setMimeType(rs.getString("mime_type"));
                }
            }
        }
        return bean;
    }

}
