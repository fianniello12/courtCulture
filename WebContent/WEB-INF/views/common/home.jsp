<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Court Culture</title>

<link rel="stylesheet" href="styles/home.css"> 

</head>
<body>

<header class="navbar">
    <nav class="nav">

        <div class="nav-left">
            <a href="Shop">shop</a>
        </div>

        <div class="nav-center">
            <a href="Home">
                <img id="nav-image" src="images/logo-white.png" class="logo-img" alt="Court Culture Logo">
            </a>
        </div>

        <div class="nav-right">

            <% if (session.getAttribute("email") == null) { %>
                <a href="Login">accedi</a>
            <% } else { %>
                <a href="Logout">logout</a>
            <% } %>

            <a href="Carrello">carrello</a>

        </div>

    </nav>
</header>


<section id="main">

<section class="main-images">

	<div class="div-left">
		<img src="images/shai.jpg" class="image-left">
	</div>
	
	<div class="div-right">
		<img src="images/booker.jpg" class="image-right">
	</div>
	
</section>

<section class="description">
	<div class="div-description">
		<div class="description-text">
			<h2 class="title-description">Chi siamo?</h2>
	
			<p class="paragraf-description">Court Culture ha l’obiettivo di esportare tutto il movimento della pallacanestro, fornendo prodotti per chi vive questo sport come stile di vita, sia dentro che fuori dal campo. 
			Il catalogo comprende moltissimi prodotti come scarpe, le iconiche jersey delle squadre NBA, numerosi accessori e tanto altro. 
			Il prodotto principale sono ovviamente le scarpe da gioco, che cercano di unire prestazioni, comfort e design, permettendo agli appassionati di portare il loro stile di vita anche sul parquet.
			Court Culture non si pone solo come un e-commerce, ma punta a diventare un vero e proprio punto di riferimento per tutti gli appassionati di questo sport.
			</p>
		</div>
		
		<img  id="description-image"src="images/durant.jpg">
	</div>
</section>

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
	    <a href="shop">Shop</a> |
	    <a href="Login">Accedi</a> |
	    <a href="carrello">Carrello</a> 
	</p>
	</div>
</footer>
</body>
</html>