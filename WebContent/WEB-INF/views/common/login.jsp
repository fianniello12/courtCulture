<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="scripts/validate.js" defer></script>
<link rel="stylesheet" href="styles/login.css" />
<title>CourtCulture-Login</title>
</head>

<body>


<form action="Login" id="loginForm" method="post" onsubmit="return validateLogin()" novalidate>
	
<h1 id="title">Accedi</h1>
	
	
		<% 
		List<String> errors1 = (List<String>) request.getAttribute("errors");
		if (errors1 != null){
			for (String error: errors1){ %>
				<span id="errorServer"><%=error %></span><br>	
			<%
			}
		}
		%>
		
	<div class="input-container">
    	<label for="email" class="input-label">Email</label>
    	<input type="email" id="email" name="email" required
        	onchange="validateFormElem(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)">
    	<span id="errorEmail"></span>
	</div>

	<div class="input-container">
    <label for="password" class="input-label">Password</label>
    <input type="password" id="password" name="password" required
        onchange="validateFormElem(this, loginPasswordPattern, document.getElementById('errorPassword'), loginPasswordErrorMessage)">
    <span id="errorPassword"></span>
</div>
	
	<button type="submit">Accedi</button>
	<p>Non hai un account?	<a href="Registrazione">Registrati</a><a href="Home">Home</a></p>

</form>
</body>
</html>

