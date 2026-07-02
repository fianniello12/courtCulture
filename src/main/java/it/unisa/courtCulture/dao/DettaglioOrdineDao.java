package it.unisa.courtCulture.dao;

import java.sql.SQLException;
import java.util.List;

import it.unisa.courtCulture.model.DettaglioOrdineBean;

public interface DettaglioOrdineDao {

    public void doSave(DettaglioOrdineBean dettaglio) throws SQLException;

    public boolean doUpdate(DettaglioOrdineBean dettaglio) throws SQLException;

    public boolean doDelete(int idOrdine, int codiceProdotto) throws SQLException;

    public DettaglioOrdineBean doRetrieveByKey(int idOrdine, int codiceProdotto) throws SQLException;

    public List<DettaglioOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException;

    public List<DettaglioOrdineBean> doRetrieveByProdotto(int codiceProdotto) throws SQLException;

    public List<DettaglioOrdineBean> doRetrieveAll(String order) throws SQLException;
}
