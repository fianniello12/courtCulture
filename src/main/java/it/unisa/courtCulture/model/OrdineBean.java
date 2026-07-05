package it.unisa.courtCulture.model;

import java.io.Serializable;

public class OrdineBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idOrdine;
	private int idUtente;
	private String dataOrdine;
	private String statoOrdine;
	private String indirizzoSpedizione;
	private String metodoPagamento;
	private float totaleOrdine;
	
	public OrdineBean() {
		
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(String dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public String getStatoOrdine() {
		return statoOrdine;
	}

	public void setStatoOrdine(String statoOrdine) {
		this.statoOrdine = statoOrdine;
	}
	
	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	public float getTotaleOrdine() {
		return totaleOrdine;
	}

	public void setTotaleOrdine(float totaleOrdine) {
		this.totaleOrdine = totaleOrdine;
	}

	
	
	



}