<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.Collection" %>
<%@ page import="it.unisa.courtCulture.model.ProdottoBean" %>

<%
    String contextPath = request.getContextPath();
    Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shop</title>
<link rel="stylesheet" href="styles/shop.css"> 

</head>
<body>

<header class="navbar">
	<nav class="nav">	
		<ul class="nav-ul">
			<li><a href="Home">home</a></li>
			<% if (session.getAttribute("role") != null 
		            && session.getAttribute("role").equals("admin")) { %>
                <li id="navAdmin">
                    <a href="WelcomeAdmin">admin</a>
                </li>
            <% } %>
			<li id="navLogo">
				<a href="Home">
					<img id="nav-image"src="images/logo-white.png" class="logo-img">
				</a>	
			</li>
			<% if (session.getAttribute("email") == null) { %>
            <li id="navLogin"><a href="Login">accedi</a></li>
        <% } else { %>
            <li id="navLogout"><a href="Logout">logout</a></li>
        <% } %>
			<li id="navCarrello"><a href="Carrello">carrello</a></li>
		</ul>
	</nav>
</header>

<div id="catalog">

    <h1>Shop</h1>
    <h2>Prodotti</h2>

    <div class="products-container">

        <% if (prodotti == null || prodotti.isEmpty()) { %>

            <p id="null">Nessun prodotto disponibile.</p>

        <% } else { %>

            <% for (ProdottoBean prodotto : prodotti) { %>
			<% if(prodotto.isAttivo()){ %>
				<a class="productLink" href="DettaglioProdotto?codice=<%= prodotto.getCodice() %>">
			
                <div class="product-card">

                    <div class="product-image-box">
                        <% if (prodotto.getPathImmagine() != null && !prodotto.getPathImmagine().isEmpty()) { %>
                            <img 
                                src="<%= contextPath %>/<%= prodotto.getPathImmagine() %>" 
                                alt="<%= prodotto.getNome() %>" 
                                class="product-image">
                        <% } else { %>
                            <img 
                                src="<%= contextPath %>/images/no-image.png" 
                                alt="Immagine non disponibile" 
                                class="product-image">
                        <% } %>
                    </div>

                    <div class="product-info">
                        <h3><%= prodotto.getNome() %></h3>

                        <p class="product-brand">
                            <%= prodotto.getBrand() %>
                        </p>

                        <p class="product-description">
                            <%= prodotto.getDescrizione() %>
                        </p>

                        <p class="product-price">
                            € <%= prodotto.getPrezzo() %>
                        </p>

                        <p class="product-quantity">
                            Disponibili: <%= prodotto.getQuantitaDisponibile() %>
                        </p>

                    </div>

                </div>
                </a>
			<% } %>
            <% } %>

        <% } %>

    </div>

</div>

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
	    <a href="Carrello">Carrello</a> 
	</p>
	</div>
</footer>
</body>
</html>