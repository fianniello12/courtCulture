package it.unisa.courtCulture.dao;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.courtCulture.model.ProdottoBean;


public interface ProdottoDao {
	
	public void doSave(ProdottoBean prodotto) throws SQLException;
	
	public boolean doUpdateImage(ProdottoBean prodotto) throws SQLException;
	
	public boolean doUpdate(ProdottoBean product) throws SQLException;
	
	public ProdottoBean doRetrieveByBrand(String brand) throws SQLException;
	
	public ProdottoBean doRetrieveByCategoria(String categoria) throws SQLException;

	public boolean doDelete(int code) throws SQLException;

	public ProdottoBean doRetrieveByKey(int code) throws SQLException;
	
	public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException;
}
