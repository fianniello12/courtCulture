package it.unisa.courtCulture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.courtCulture.model.ProdottoBean;
import it.unisa.courtCulture.model.UtenteBean;

public class UtenteDaoImpl {
	
	private static final String TABLE_NAME = "utente";
    private DataSource ds = null;

    public UtenteDaoImpl(DataSource ds) {
        this.ds = ds;
    }
	
	public void doSave(UtenteBean utente) throws SQLException{
		String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (email, password, nome, cognome, indirizzo_spedizione, metodo_pagamento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, utente.getEmail());
            preparedStatement.setString(2, utente.getPassword());
            preparedStatement.setString(3, utente.getNome());
            preparedStatement.setString(4, utente.getCognome());
            preparedStatement.setString(5,utente.getIndirizzoSpedizione());
            preparedStatement.setString(6,utente.getMetodoPagamento());
            preparedStatement.executeUpdate();
        }
		
	}
	
	public boolean doUpdate(UtenteBean utente) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET email = ?, password = ?, nome = ?, cognome = ?, indirizzo_spedizione = ?, metodo_pagamento = ? WHERE id= ? ";
        try (Connection conn = ds.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setString(5,utente.getIndirizzoSpedizione());
            ps.setString(6,utente.getMetodoPagamento());
            ps.setInt(7, utente.getId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
	}
	
	public boolean doDelete(int code) throws SQLException{
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
		
	}
	
	public synchronized UtenteBean doRetrieveByKey(int code) throws SQLException{
		
		UtenteBean bean = new UtenteBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ds.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setId(rs.getInt("id"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    bean.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    
                }
            }
        }
      
		return bean;
	}
	
	public synchronized UtenteBean doRetrieveByCognome(String cognome) throws SQLException{
		
	}
	
	public List<UtenteBean> doRetrieveAll(String order) throws SQLException{
		
	}
}
