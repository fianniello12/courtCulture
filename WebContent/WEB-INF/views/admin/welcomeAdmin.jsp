<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>court culture admin</title>
</head>
<body>

<%
    String contextPath = request.getContextPath();
%>

<header class="navbar">
    <nav class="nav">	
        <ul class="nav-ul">

            <li>
                <a href="<%= contextPath %>/Shop">shop</a>
            </li>

            <li id="navLogo">
                <a href="<%= contextPath %>/Home">
                    <img 
                        id="nav-image"
                        src="<%= contextPath %>/images/logo-white.png" 
                        class="logo-img"
                        alt="Court Culture Logo">
                </a>
            </li>

            <% if (session.getAttribute("email") == null) { %>
                <li id="navLogin">
                    <a href="<%= contextPath %>/Login">Accedi</a>
                </li>
            <% } else { %>
                <li id="navLogout">
                    <a href="<%= contextPath %>/Logout">Logout</a>
                </li>
            <% } %>

            <li id="navCarrello">
                <a href="<%= contextPath %>/Carrello">carrello</a>
            </li>

        </ul>
    </nav>

<h1>Welcomeback admin </h1>    
</header>

<section>
	<form action="InserisciProdotto" method="post" enctype="multipart/form-data">

    <label>Nome:</label>
    <input type="text" name="nome" required>

    <label>Descrizione:</label>
    <textarea name="descrizione" required></textarea>

    <label>Prezzo:</label>
    <input type="number" step="0.01" name="prezzo" required>

    <label>Quantità:</label>
    <input type="number" name="quantita" required>

    <label>Categoria:</label>
    <input type="text" name="categoria" required>

    <label>Brand:</label>
    <input type="text" name="brand" required>

    <label>Immagine:</label>
    <input type="file" name="immagine" accept="image/*" required>

    <button type="submit">Inserisci prodotto</button>

</form>
</section>

</body>
</html>