document.addEventListener("DOMContentLoaded", function() {

    caricaCarrello();

    var svuotaButton = document.getElementById("svuota-carrello");

    if (svuotaButton != null) {
        svuotaButton.addEventListener("click", svuotaCarrello);
    }

    var formOrdine = document.getElementById("formOrdine");

    if (formOrdine != null) {
        formOrdine.addEventListener("submit", function(event) {
            event.preventDefault();
            confermaOrdine();
        });
    }
});


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

        var imagePath;

        if (item.pathImmagine != null && item.pathImmagine != "") {
            imagePath = contextPath + "/" + item.pathImmagine;
        } else {
            imagePath = contextPath + "/images/no-image.png";
        }

        var div = document.createElement("div");
        div.className = "cart-item";

        div.innerHTML =
            '<div class="cart-item-image">' +
                '<img src="' + imagePath + '" alt="' + item.nome + '">' +
            '</div>' +

            '<div class="cart-item-info">' +
                '<h3>' + item.nome + '</h3>' +
                '<p>Brand: ' + item.brand + '</p>' +
                '<p>Taglia: ' + item.taglia + '</p>' +
                '<p>Prezzo: € ' + Number(item.prezzo).toFixed(2) + '</p>' +

                '<label>Quantità:</label>' +
                '<input type="number" min="1" value="' + item.quantita + '"' +
                    ' onchange="aggiornaQuantita(' + item.codice + ',' + item.taglia + ', this.value)">' +

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

    var params =
        "azione=update" +
        "&codice=" + encodeURIComponent(codice) +
        "&taglia=" + encodeURIComponent(taglia) +
        "&quantita=" + encodeURIComponent(quantita);

    inviaRichiestaCarrello(params, callbackAggiornamento);
}


function rimuoviProdotto(codice, taglia) {

    var params =
        "azione=remove" +
        "&codice=" + encodeURIComponent(codice) +
        "&taglia=" + encodeURIComponent(taglia);

    inviaRichiestaCarrello(params, callbackAggiornamento);
}


function svuotaCarrello() {

    var conferma = confirm("Sei sicuro di voler svuotare il carrello?");

    if (!conferma) {
        return;
    }

    var params = "azione=clear";

    inviaRichiestaCarrello(params, callbackAggiornamento);
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

    xhr.setRequestHeader(
        "Content-Type",
        "application/x-www-form-urlencoded"
    );

    xhr.setRequestHeader("Connection", "close");

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

    var params =
        "indirizzoSpedizione=" + encodeURIComponent(indirizzo) +
        "&metodoPagamento=" + encodeURIComponent(pagamento);

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4) {

            if (xhr.status == 200) {

                var risposta = JSON.parse(xhr.responseText);

                mostraMessaggio(risposta.message);

                if (risposta.success) {

                    caricaCarrello();

                    document.getElementById("formOrdine").reset();
                }

            } else {

                mostraMessaggio(
                    "Errore nella conferma dell'ordine: " + xhr.statusText
                );
            }
        }
    };

    xhr.open("POST", contextPath + "/ConfermaOrdine", true);

    xhr.setRequestHeader(
        "Content-Type",
        "application/x-www-form-urlencoded"
    );

    xhr.setRequestHeader("Connection", "close");

    xhr.send(params);
}


function mostraMessaggio(testo) {

    var messaggio = document.getElementById("messaggio");

    if (messaggio != null) {
        messaggio.textContent = testo;
    }
}