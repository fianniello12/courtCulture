<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="registrazione.css" />
<script src="scripts/validate.js" defer></script></head>
<body>

<form action="Registrazione" id="regForm" method="post" onsubmit="return validate()" novalidate>

        <div>
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required pattern="^[A-Za-z]+$" onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorName'), nameErrorMessage )">            
			<span id="errorName"></span>
        </div>

        <br>

        <div>
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" required pattern="^[A-Za-z]+$"
    			onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorCognome'), lastnameErrorMessage )">
			<span id="errorCognome"></span>
        </div>

        <br>

        <div>
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required
					onchange="validateFormElem(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)">
				<span id="errorEmail"></span> </div>

        <br>

        <br>

        <div>
            <label for="indirizzo_spedizione">Indirizzo di spedizione:</label>
            <input type="text" id="indirizzo_spedizione" name="indirizzo_spedizione">
        </div>

        <br>

        <div>
            <label for="metodo_pagamento">Metodo di pagamento:</label>
            <select id="metodo_pagamento" name="metodo_pagamento">
                <option value="Carta di credito">Carta di credito</option>
                <option value="PayPal">PayPal</option>
                <option value="Contrassegno">Contrassegno</option>
            </select>
        </div>
        
        <label for="psw">Password</label>
  		<input type="password" id="psw" name="psw"  
    		onchange="validateFormElem(this, passwordPattern, document.getElementById('errorpsw'), passwordErrorMessage)">
		<span id="errorpsw"></span>
        <br>

        <button type="submit">Registrati</button>

    </form>

    <p>
        Hai già un account?
        <a href="Login">Accedi</a>
    </p>

</body>
</html>