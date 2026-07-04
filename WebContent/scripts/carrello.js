document.getElementById("formCarrello").addEventListener(
    "submit",
    function(event) {

        event.preventDefault();

        aggiungiAlCarrello();
    }
);


function aggiungiAlCarrello() {

    var codice = document.getElementById("codice").value;
    var taglia = document.getElementById("taglia").value;
    var quantita = document.getElementById("quantita").value;

    var messaggio = document.getElementById("messaggioCarrello");


    if (taglia === "") {

        messaggio.textContent = "Seleziona una taglia.";

        return;
    }


    var params =
        "azione=add" +
        "&codice=" + encodeURIComponent(codice) +
        "&taglia=" + encodeURIComponent(taglia) +
        "&quantita=" + encodeURIComponent(quantita);


    var xhr = new XMLHttpRequest();


    xhr.onreadystatechange = function() {

        if (xhr.readyState == 4) {

            if (xhr.status == 200) {

                var risposta = JSON.parse(xhr.responseText);


                if (risposta.success) {

                    messaggio.textContent =
                        "Prodotto aggiunto al carrello.";

                } else {

                    messaggio.textContent =
                        risposta.message;
                }

            } else {

                messaggio.textContent =
                    "Errore nella richiesta: " + xhr.statusText;
            }
        }
    };


    xhr.open(
        "POST",
        contextPath + "/Carrello",
        true
    );


    xhr.setRequestHeader(
        "Content-Type",
        "application/x-www-form-urlencoded"
    );


    xhr.setRequestHeader(
        "Connection",
        "close"
    );


    xhr.send(params);
}