package it.unisa.courtCulture.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import it.unisa.courtCulture.model.OrdineBean;

public interface OrdineDao {

    public int doSave(OrdineBean ordine) throws SQLException;

    public boolean doUpdate(OrdineBean ordine) throws SQLException;

    public boolean doUpdateStato(int idOrdine, String statoOrdine) throws SQLException;

    public boolean doDelete(int idOrdine) throws SQLException;

    public OrdineBean doRetrieveByKey(int idOrdine) throws SQLException;

    public List<OrdineBean> doRetrieveByUtente(int idUtente) throws SQLException;
    
    public List<OrdineBean> doRetrieveByPeriodo(LocalDate dataDa, LocalDate dataA) throws SQLException;

    public List<OrdineBean> doRetrieveAll(String order) throws SQLException;
}