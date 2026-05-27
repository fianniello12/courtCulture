package it.unisa.courtCulture.model;

import java.io.Serializable;

public class ProdottoBean implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	private int codice;
	private String nome;
	private String descrizione;
	private float prezzo;
	private int quantitaDisponibile;
	private String categoria;
	private String brand;
	private String pathImmagine;
	private String mimeType;
	private boolean attivo;
	
	public ProdottoBean() {
		
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(int quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPathImmagine() {
		return pathImmagine;
	}

	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public boolean hasImage() {
		return pathImmagine != null && !pathImmagine.isEmpty();
	}

}
