function inizializzaPaginaCarrello() {

    caricaCarrello();

    var formOrdine =document.getElementById("formOrdine");

    if (formOrdine != null) {
        gestisciDatiCarta();
    }
}

function caricaCarrello() {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4) {

            if (xhr.status == 200) {

                var risposta = JSON.parse(xhr.responseText);

                aggiornaCarrelloDOM(risposta);

            } else {

                console.log("Errore: " + xhr.statusText);
            }
        }
    };

    xhr.open("GET", contextPath + "/Carrello?azione=get", true);

    xhr.send(null);
}


function aggiornaCarrelloDOM(risposta) {

    var container = document.getElementById("carrello-container");
    var totale = document.getElementById("totale-carrello");

    container.innerHTML = "";

    if (risposta.items.length == 0) {
        container.innerHTML = "<p>Il carrello è vuoto.</p>";
        totale.textContent = "0.00";
        return;
    }

    for (var i = 0; i < risposta.items.length; i++) {

        var item = risposta.items[i];

		var imagePath = contextPath + "/ImmagineProdotto?codice=" + encodeURIComponent(item.codice);

        var div = document.createElement("div");
        div.className = "cart-item";

        div.innerHTML ='<div class="cart-item-image">' +
                			'<img src="' + imagePath + '" alt="' + item.nome + '">' +
            			'</div>' +

            			'<div class="cart-item-info">' +
                			'<h3>' + item.nome + '</h3>' +
                			'<p>Brand: ' + item.brand + '</p>' +
                			'<p>Taglia: ' + item.taglia + '</p>' +
                			'<p>Prezzo: € ' + Number(item.prezzo).toFixed(2) + '</p>' +

                			'<label>Quantità:</label>' +
                			'<input type="number" min="1" value="' + item.quantita + '"' + ' onchange="aggiornaQuantita(' + item.codice + ',' + item.taglia + ', this.value)">' +

                			'<p>Subtotale: € ' + Number(item.subtotale).toFixed(2) + '</p>' +

                			'<button type="button" onclick="rimuoviProdotto(' + item.codice + ',' + item.taglia + ')">' +
                    			'Rimuovi' +
                			'</button>' +
            			'</div>';

        container.appendChild(div);
    }

    totale.textContent = Number(risposta.totale).toFixed(2);
}


function aggiornaQuantita(codice, taglia, quantita) {

    var params ="azione=update" +"&codice=" + encodeURIComponent(codice) + "&taglia=" + encodeURIComponent(taglia) + "&quantita=" + encodeURIComponent(quantita);

    inviaRichiestaCarrello(params, callbackAggiornamento);
}


function rimuoviProdotto(codice, taglia) {

    var params ="azione=remove" + "&codice=" + encodeURIComponent(codice) + "&taglia=" + encodeURIComponent(taglia);

    inviaRichiestaCarrello(params, callbackAggiornamento);
}


function svuotaCarrello() {
	
	    var params = "azione=clear";

	    inviaRichiestaCarrello(params,callbackAggiornamento);
	}

    


function inviaRichiestaCarrello(params, callback) {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4) {

            if (xhr.status == 200) {

                callback(xhr);

            } else {

                mostraMessaggio("Errore nella richiesta: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", contextPath + "/Carrello", true);

    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

    xhr.send(params);
}


function callbackAggiornamento(xhr) {

    var risposta = JSON.parse(xhr.responseText);

    mostraMessaggio(risposta.message);

    if (risposta.success) {
        caricaCarrello();
    }
}


function confermaOrdine() {

    var indirizzo = document.getElementById("indirizzoSpedizione").value;
    var pagamento = document.getElementById("metodoPagamento").value;

    if (indirizzo.trim() == "") {
        mostraMessaggio("Inserisci l'indirizzo di spedizione.");
        return;
    }

    if (pagamento == "") {
        mostraMessaggio("Seleziona un metodo di pagamento.");
        return;
    }

    var params ="indirizzoSpedizione=" + encodeURIComponent(indirizzo) +"&metodoPagamento=" + encodeURIComponent(pagamento);
		
		if (pagamento != "Contrassegno") {

		    var intestatario = document.getElementById("intestatarioCarta").value;

		    var numeroCarta = document.getElementById("numeroCarta").value;

		    var scadenzaCarta = document.getElementById("scadenzaCarta").value;

		    var cvv = document.getElementById("cvv").value;


		    params += "&intestatarioCarta=" + encodeURIComponent(intestatario) + "&numeroCarta=" + encodeURIComponent(numeroCarta) + "&scadenzaCarta=" + encodeURIComponent(scadenzaCarta) + "&cvv=" + encodeURIComponent(cvv);
		}

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4 ){
			if( xhr.status == 200) {

                var risposta = JSON.parse(xhr.responseText);

                mostraMessaggio(risposta.message);

                if (risposta.success) {

                    window.location.href = contextPath + "/Carrello";
				}
            } else {

                mostraMessaggio("Errore nella conferma dell'ordine: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", contextPath + "/ConfermaOrdine", true);

    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

    xhr.send(params);
}

function gestisciDatiCarta() {

    var metodoPagamento =document.getElementById("metodoPagamento");

    var datiCarta = document.getElementById("datiCarta");


    var intestatarioCarta =document.getElementById("intestatarioCarta");

    var numeroCarta =document.getElementById("numeroCarta");

    var scadenzaCarta = document.getElementById("scadenzaCarta");

    var cvv = document.getElementById("cvv");


    if (metodoPagamento.value != "" && metodoPagamento.value != "Contrassegno"){

        datiCarta.style.display = "block";


        intestatarioCarta.required = true;
        numeroCarta.required = true;
        scadenzaCarta.required = true;
        cvv.required = true;

    } else {

        datiCarta.style.display = "none";


        intestatarioCarta.required = false;
        numeroCarta.required = false;
        scadenzaCarta.required = false;
        cvv.required = false;
    }
}


function mostraMessaggio(testo) {

    var messaggio = document.getElementById("messaggio");

    if (messaggio != null) {
        messaggio.textContent = testo;
    }
}