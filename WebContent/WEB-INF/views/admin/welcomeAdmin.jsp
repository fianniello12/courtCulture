<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="it.unisa.courtCulture.model.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unisa.courtCulture.model.OrdineBean" %>
<%@ page import="it.unisa.courtCulture.model.UtenteBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>court culture admin</title>
<link rel="stylesheet" href="styles/WelcomeAdmin.css"> 

</head>
<body>

<%
String contextPath = request.getContextPath();
List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti");
List<OrdineBean> ordini =(List<OrdineBean>) request.getAttribute("ordini");
List<UtenteBean> utenti =(List<UtenteBean>) request.getAttribute("utenti");

%>


<header class="navbar">
    <nav class="nav">	
        <ul class="nav-ul">
			
			<li>
				<a href="Home">home</a>
			</li>
			
            <li>
                <a id="navShop"href="Shop">shop</a>
            </li>

            <li id="navLogo">
                <a href="Home">
                    <img id="nav-image" src="images/logo-white.png" class="logo-img" alt="Court Culture Logo">
                </a>
            </li>

            <% if (session.getAttribute("email") == null) { %>
                <li id="navLogin">
                    <a href="Login">Accedi</a>
                </li>
            <% } else { %>
                <li id="navLogout">
                    <a href="Logout">Logout</a>
                </li>
            <% } %>

            <li id="navCarrello">
                <a href="Carrello">carrello</a>
            </li>

        </ul>
    </nav>
</header>

<section class="admin-product-section">
	<h1>Welcomeback admin </h1>    

	<form action="InserisciProdotto" method="post" enctype="multipart/form-data">

    <label for="nome">Nome:</label>
    <input type="text" name="nome" id="nome" required>

    <label for="descrizione">Descrizione:</label>
    <textarea name="descrizione" id="descrizione" required></textarea>

    <label for="prezzo">Prezzo:</label>
    <input type="number" step="0.01" name="prezzo" id="prezzo" required>

    <label for="quantita">Quantità:</label>
    <input type="number" name="quantita" id="quantita" required>

    <label for="categoria">Categoria:</label>
    <input type="text" name="categoria" id="categoria" required>

    <label for="brand">Brand:</label>
    <input type="text" name="brand" id="brand" required>

    <label for="immagine">Immagine:</label>
    <input type="file" name="immagine" id="immagine" accept="image/*" required>

    <button type="submit">Inserisci prodotto</button>

	</form>
</section>

<section id="table-product"class="admin-products-section">

    <h2>Gestione prodotti</h2>

    <% if (prodotti == null || prodotti.isEmpty()) { %>

        <p>Nessun prodotto presente nel catalogo.</p>

    <% } else { %>

        <table border="1">
            <thead>
                <tr>
                    <th>Codice</th>
                    <th>Immagine</th>
                    <th>Nome</th>
                    <th>Brand</th>
                    <th>Categoria</th>
                    <th>Prezzo</th>
                    <th>Quantità</th>
                    <th>Attivo</th>
                    <th>Azione</th>
                </tr>
            </thead>

            <tbody>
                <% for (ProdottoBean prodotto : prodotti) { %>

                    <% 
    String formId = "modificaProdotto" + prodotto.getCodice(); 
%>

<tr>

     <form action="ModificaProdotto" method="post">

		<td>
			<%= prodotto.getCodice() %>
			<input type="hidden" name="codice" value="<%= prodotto.getCodice() %>">
		</td>

		<td>
			<img src="<%= contextPath %>/ImmagineProdotto?codice=<%= prodotto.getCodice() %>" alt="<%= prodotto.getNome() %>" width="80">
		</td>

        <td>
        	<input type="text" name="nome" value="<%= prodotto.getNome() %>" required>
		</td>

		<td>
			<input type="text" name="brand" value="<%= prodotto.getBrand() %>" required>
		</td>

		<td>
			<input type="text" name="categoria" value="<%= prodotto.getCategoria() %>" required>
		</td>

		<td>
			<input type="number"name="prezzo" value="<%= prodotto.getPrezzo() %>" required>
		</td>

		<td>
			<input type="number" name="quantita" value="<%= prodotto.getQuantitaDisponibile() %>" required>
		</td>

		<td>
			<select name="attivo">
				<option value="true" <%= prodotto.isAttivo() ? "selected" : "" %>>
					Sì
                </option>
				
				<option value="false" <%= !prodotto.isAttivo() ? "selected" : "" %>>
					No
				</option>
            </select>
		</td>

		<td>
			<button type="submit">Modifica</button>
		</form>
		
		<form action="EliminaProdotto" method="post">
			<input type="hidden" name="codice" value="<%= prodotto.getCodice() %>">
			<button type="submit">Elimina</button>
		</form>
   </tr>

   <% } %>
            </tbody>
        </table>

    <% } %>

</section>

<section id="table-orders" class="admin-orders-section">

    <h2>Gestione ordini</h2>

    <form action="FiltraOrdini" method="get">

		<p>Visualizza gli ordini in base alla data d'acquisto</p>

        <input type="hidden" name="filtro" value="tutti">

        <button type="submit">Mostra tutti gli ordini</button>

    </form>

    <form action="FiltraOrdini" method="get">

        <input type="hidden" name="filtro" value="periodo">

        <label for="dataDa">Dalla data:</label>

        <input type="date" id="dataDa" name="dataDa" required>

        <label for="dataA">Alla data:</label>

        <input type="date" id="dataA" name="dataA" required>

        <button type="submit">Cerca per periodo</button>

    </form>


    <form action="FiltraOrdini" method="get">

        <input type="hidden" name="filtro" value="cliente">

        <label for="idCliente">Id cliente:</label>

        <input type="text" id="idCliente" name="idCliente" required>

        <button type="submit">Cerca ordini cliente</button>

    </form>


    <div class="orders-results">

        <% if (ordini == null) { %>

            <p>Seleziona un filtro per visualizzare gli ordini.</p>

        <% } else if (ordini.isEmpty()) { %>

            <p>Nessun ordine trovato.</p>

        <% } else { %>

            <table border="1">

                <thead>

                    <tr>
                        <th>ID ordine</th>
                        <th>ID cliente</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Indirizzo</th>
                        <th>Pagamento</th>
                        <th>Stato</th>
                    </tr>

                </thead>


                <tbody>

                    <% for (OrdineBean ordine : ordini) { %>

                        <tr>

                            <td><%= ordine.getIdOrdine() %></td>

                            <td><%= ordine.getIdUtente() %></td>

                            <td><%= ordine.getDataOrdine() %></td>

                            <td>€ <%= String.format("%.2f",ordine.getTotaleOrdine()) %></td>

                            <td><%= ordine.getIndirizzoSpedizione() %></td>

                            <td><%= ordine.getMetodoPagamento() %></td>

                            <td><%= ordine.getStatoOrdine() %></td>

                        </tr>

                    <% } %>

                </tbody>

            </table>

        <% } %>

    </div>

</section>

<section id="table-utenti"class="admin-utenti-section">

    <h2>Gestione clienti</h2>

    <% if (utenti == null || utenti.isEmpty()) { %>

        <p>Nessun cliente registrato.</p>

    <% } else { %>

        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Email</th>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Indirizzo</th>
                    <th>Metodo di pagamento</th>
                    <th>ruolo</th>
                </tr>
            </thead>

            <tbody>
                <% for (UtenteBean utente: utenti) { %>


<tr>

		<td>
			<%= utente.getId() %>
		</td>

		<td>
			<%= utente.getEmail() %>
		</td>

        <td>
        	<%= utente.getNome() %>
		</td>

		<td>
			<%= utente.getCognome() %>
		</td>

		<td>
			<%= utente.getIndirizzoSpedizione() %>
		</td>

		<td>
			<%= utente.getMetodoPagamento() %>
		</td>

		<td>
			<%= utente.getRuolo() %>
		</td>

		
   </tr>

   <% } %>
            </tbody>
        </table>

    <% } %>

</section>


</body>
</html>