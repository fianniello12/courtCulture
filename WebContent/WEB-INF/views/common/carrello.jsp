<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
    boolean isLogged = session.getAttribute("email") != null;
    boolean isAdmin = session.getAttribute("role") != null 
            && session.getAttribute("role").equals("admin");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Carrello</title>

<link rel="stylesheet"href="<%= contextPath %>/styles/carrello.css">

<body>

<header class="navbar">
    <nav class="nav">	
        <ul class="nav-ul">
			
            <li>
                <a href="Home">home</a>
            </li>
			
            <li>
                <a id="navShop" href="Shop">shop</a>
            </li>
            
            <% if (isAdmin) { %>
                <li id="navAdmin">
                    <a href="WelcomeAdmin">admin</a>
                </li>
            <% } %>

            <li id="navLogo">
                <a href="Home">
                    <img id="nav-image" src="images/logo-white.png" class="logo-img" alt="Court Culture Logo">
                </a>
            </li>

            <% if (!isLogged) { %>
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

<main class="cart-page">

    <section class="cart-section">

        <h1>Il tuo carrello</h1>

        <div id="carrello-container">

        </div>

        <div class="cart-summary">
            <h2>Totale: € <span id="totale-carrello">0.00</span></h2>

            <button id="svuota-carrello" type="button">
                Svuota carrello
            </button>
        </div>

        <p id="messaggio"></p>

    </section>

    <section class="order-section">

        <% if (isLogged) { %>

            <h2>Conferma ordine</h2>

            <form id="formOrdine">

                <label for="indirizzoSpedizione">Indirizzo di spedizione:</label>
                <input 
                    type="text" 
                    id="indirizzoSpedizione" 
                    name="indirizzoSpedizione" 
                    placeholder="Inserisci indirizzo completo"
                    required>

                <label for="metodoPagamento">Metodo di pagamento:</label>
                <select 
                    id="metodoPagamento" 
                    name="metodoPagamento" 
                    required>

                    <option value="">Seleziona metodo di pagamento</option>
                    <option value="Carta">Carta</option>
                    <option value="PayPal">PayPal</option>
                    <option value="Contrassegno">Contrassegno</option>

                </select>

                <button type="submit">
                    Conferma ordine
                </button>

            </form>

        <% } else { %>

            <div class="login-required">
                <h2>Vuoi completare l’ordine?</h2>
                <p>Puoi aggiungere prodotti al carrello anche senza login, ma per confermare l’ordine devi accedere.</p>

                <a href="Login" class="login-button">
                    Accedi
                </a>
            </div>

        <% } %>

    </section>

</main>

<footer class="footer">
	
    <div class="footerLogo">
        <img 
            src="images/footerLogo.png" 
            alt="Court Culture Logo" 
            class="logo-img">
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

            <% if (!isLogged) { %>
                <a href="Login">Accedi</a>
            <% } else { %>
                <a href="Logout">Logout</a>
            <% } %>

            |
            <a href="Shop">Shop</a> |
            <a href="Carrello">Carrello</a>
        </p>
    </div>

</footer>

<script>
    var contextPath = "<%= contextPath %>";
</script>

<script src="<%= contextPath %>/scripts/carrelloPage.js"></script>

</body>
</html>