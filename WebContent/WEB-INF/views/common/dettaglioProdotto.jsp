<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="it.unisa.courtCulture.model.ProdottoBean" %>

<%
	String contextPath = request.getContextPath();
    ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles/dettaglioProdotto.css">
<title><%= prodotto.getNome() %></title>
</head>
<body>

<header class="navbar">
    <nav class="nav">	
        <ul class="nav-ul">
			
			<li>
				<a href="Home">home</a>
			</li>
			
            <li>
                <a id="navShop"href="Shop">shop</a>
            </li>
            
            <% if (session.getAttribute("role") != null 
		            && session.getAttribute("role").equals("admin")) { %>
                <li id="navAdmin">
                    <a href="WelcomeAdmin">admin</a>
                </li>
            <% } %>

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

<section class="product">

	<div class="productImage">
	<% if (prodotto.getPathImmagine() != null && !prodotto.getPathImmagine().isEmpty()) { %>
                <img src="<%= contextPath %>/<%= prodotto.getPathImmagine() %>" alt="<%= prodotto.getNome() %>">
            <% } else { %>
                <img src="<%= contextPath %>/images/no-image.png" alt="Immagine non disponibile">
            <% } %>
	</div>
	
	<div class="productInfo">
		<h1><%= prodotto.getNome() %></h1>
		<p><%= prodotto.getDescrizione() %></p>
		<p><%= prodotto.getBrand() %></p>
		<p>Prezzo: €<%= prodotto.getPrezzo() %></p>
		<p>Disponibilità: <%= prodotto.getQuantitaDisponibile() %></p>
	
		<form action="<%= contextPath %>/Carrello" method="post">
                <input type="hidden" name="codice" value="<%= prodotto.getCodice() %>">
                <label for="taglia">Taglia:</label>
        			<select name="taglia" id="taglia" required>
	            			<% for (int taglia = 37; taglia <= 48; taglia++) { %>
	                			<option value="<%= taglia %>" ><%= taglia %></option>
	           			 	<% } %>
        			</select>
        		<label for="quantita">Quantità:</label>
                <input type="number" id="quantita" name="quantita" value="1" min="1" max="<%= prodotto.getQuantitaDisponibile()%>">
                <button type="submit">Aggiungi al carrello</button>
        </form>
	</div>

</section>

<footer class="footer">
	
	<div class="footerLogo">
		<img src="images/footerLogo.png" alt="Court Culture Logo" class="logo-img">
	</div>
	
	<div class="footer-text">
	<p>&copy; 2026 Court Culture - Tutti i diritti riservati</p>
	
	<p>
    	Court Culture è lo store dedicato alla cultura del basket, 
    	per chi vive questo sport come stile di vita.
	</p>

	<p id="footer-info">
    	Spedizioni rapide | Pagamenti sicuri | Reso entro 14 giorni
	</p>

	<p>
	    <a href="Home">Home</a> |
	   <% if (session.getAttribute("email") == null) { %>
            <a href="Login">Accedi</a>
        <% } else { %>
            <a href="Logout">Logout</a>
        <% } %>|
        <a href="Shop">Shop</a> |
	    <a href="Carrello">Carrello</a> 
	</p>
	</div>
</footer>

</body>
</html>