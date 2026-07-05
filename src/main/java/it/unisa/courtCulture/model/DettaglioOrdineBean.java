package it.unisa.courtCulture.model;

import java.io.Serializable;

public class DettaglioOrdineBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idOrdine;
	private int codiceProdotto;
	private int taglia;
	private int quantita;
	private float prezzoAcquisto;
	
	
	
	public DettaglioOrdineBean() {
	
	}



	public int getIdOrdine() {
		return idOrdine;
	}



	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}



	public int getCodiceProdotto() {
		return codiceProdotto;
	}



	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public int getTaglia() {
		return taglia;
	}



	public void setTaglia(int taglia) {
		this.taglia = taglia;
	}

	public int getQuantita() {
		return quantita;
	}



	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}



	public float getPrezzoAcquisto() {
		return prezzoAcquisto;
	}



	public void setPrezzoAcquisto(float prezzoAcquisto) {
		this.prezzoAcquisto = prezzoAcquisto;
	}

	
}
	