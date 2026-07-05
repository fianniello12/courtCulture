DROP DATABASE IF EXISTS courtCulture;
CREATE DATABASE courtCulture;
USE courtCulture;

DROP TABLE IF EXISTS dettaglio_ordine;
DROP TABLE IF EXISTS ordine;
DROP TABLE IF EXISTS prodotto;
DROP TABLE IF EXISTS utente;

CREATE TABLE utente (
	id INT PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	cognome VARCHAR(50) NOT NULL,
	indirizzo_spedizione VARCHAR(255),
	metodo_pagamento VARCHAR(50),
	ruolo VARCHAR(20) DEFAULT 'user'
);

CREATE TABLE prodotto (
	codice INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	descrizione VARCHAR(255),
	prezzo DECIMAL(10,2) DEFAULT 0.00,
	quantita_disponibile INT DEFAULT 0,
	categoria VARCHAR(50),
	brand VARCHAR(50),
	path_immagine VARCHAR(255),
	mime_type VARCHAR(50),
	attivo BOOLEAN DEFAULT TRUE
);


CREATE TABLE ordine (
	id_ordine INT PRIMARY KEY AUTO_INCREMENT,
	id_utente INT NOT NULL,
	data_ordine DATE,
	stato_ordine VARCHAR(50) DEFAULT 'in elaborazione',
	totale_ordine DECIMAL(10,2) DEFAULT 0.00,
	indirizzo_spedizione VARCHAR(255) NOT NULL,
	metodo_pagamento VARCHAR(50) NOT NULL;

	FOREIGN KEY (id_utente) REFERENCES utente(id)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);

CREATE TABLE dettaglio_ordine (
    id_ordine INT NOT NULL,
    codice_prodotto INT NOT NULL,
    taglia INT NOT NULL,
    quantita INT DEFAULT 1,
    prezzo_acquisto DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (
        id_ordine,
        codice_prodotto,
        taglia
    ),

    FOREIGN KEY (id_ordine)
        REFERENCES ordine(id_ordine)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    FOREIGN KEY (codice_prodotto)
        REFERENCES prodotto(codice)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
