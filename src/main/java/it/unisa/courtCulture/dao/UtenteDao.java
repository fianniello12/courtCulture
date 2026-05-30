package it.unisa.courtCulture.dao;

import java.sql.SQLException;
import java.util.List;

import it.unisa.courtCulture.model.UtenteBean;

public interface UtenteDao {

	public void doSave(UtenteBean prodotto) throws SQLException;
	
	public boolean doUpdate(UtenteBean product) throws SQLException;
	
	public boolean doDelete(int code) throws SQLException;
	
	public UtenteBean doRetrieveByKey(int code) throws SQLException;
	
	public UtenteBean doRetrieveByEmail(String email) throws SQLException;
	
	public UtenteBean doRetrieveByCognome(String cognome) throws SQLException;
	
	public List<UtenteBean> doRetrieveAll(String order) throws SQLException;
}
