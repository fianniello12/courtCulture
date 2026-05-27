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
}
