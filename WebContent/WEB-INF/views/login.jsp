<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login...</title>
</head>
<body>
<% 
List<String> errors = (List<String>) request.getAttribute("errors");
if (errors != null){
	for (String error: errors){ %>
		<%=error %> <br>		
	<%
	}
}
%>
<form action="Login" method="post">

	<label>Email</label>
	<input type="email" name="email" required>

	<label>Password</label>
	<input type="password" name="password" required>

	<button type="submit">Accedi</button>
	<p>Non hai un account? <a href=Registrazione>Registrati</a></p>

</form>
</body>
</html>

