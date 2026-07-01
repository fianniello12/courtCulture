<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>court culture admin</title>
</head>
<body>
<h1>Sei nella pagina admin </h1>

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
</header>

</body>
</html>