<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="it.unisa.courtCulture.model.ProdottoBean" %>
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
Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("prodotti");
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
                    <img id="nav-image" src="<%= contextPath %>/images/logo-white.png" class="logo-img" alt="Court Culture Logo">
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
			<% if (prodotto.getPathImmagine() != null && !prodotto.getPathImmagine().isEmpty()) { %>
				<img src="<%= contextPath %>/<%= prodotto.getPathImmagine() %>" alt="<%= prodotto.getNome() %>" width="80">
			<% } else { %>
				<img src="<%= contextPath %>/images/no-image.png" alt="Immagine non disponibile" width="80">
			<% } %>
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


</body>
</html>