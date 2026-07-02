package it.unisa.courtCulture.dao;

import java.sql.SQLException;
import java.util.List;

import it.unisa.courtCulture.model.ProdottoBean;

public interface ProdottoDao {

    public int doSave(ProdottoBean prodotto) throws SQLException;

    public boolean doUpdateImage(ProdottoBean prodotto) throws SQLException;

    public boolean doUpdate(ProdottoBean product) throws SQLException;

    public List<ProdottoBean> doRetrieveByBrand(String brand) throws SQLException;

    public List<ProdottoBean> doRetrieveByCategoria(String categoria) throws SQLException;

    public boolean doDelete(int code) throws SQLException;

    public ProdottoBean doRetrieveByKey(int code) throws SQLException;

    public List<ProdottoBean> doRetrieveAll(String order) throws SQLException;
}