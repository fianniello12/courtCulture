<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CourtCulture-Registration</title>
<link rel="stylesheet" href="styles/registrazione.css" />
<script src="scripts/validate.js" defer></script>
</head>
<body>


<form action="Registrazione" id="regForm" method="post" onsubmit="return validateRegistrazione()" novalidate>

<h1 id="title">Registrazione</h1>


        <div class="input-container">
            <label for="nome" class="input-label">Nome:</label>
            <input type="text" id="nome" name="nome" required 
            	onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorName'), nameErrorMessage )">            
			<span id="errorName"></span>
        </div>

        <div class="input-container">
            <label for="cognome" class="input-label">Cognome:</label>
            <input type="text" id="cognome" name="cognome" required 
    			onchange="validateFormElem(this, nameOrLastnamePattern, document.getElementById('errorCognome'), lastnameErrorMessage )">
			<span id="errorCognome"></span>
        </div>

        <div class="input-container">
            <label for="email" class="input-label">Email:</label>
            <input type="email" name="email" id="email" required
					onchange="validateFormElem(this, emailPattern, document.getElementById('errorEmail'), emailErrorMessage)">
			<span id="errorEmail"></span> 
		</div>

        <div class="input-container">
            <label for="indirizzo_spedizione" class="input-label">Indirizzo di spedizione:</label>
    		<input type="text" name="indirizzo_spedizione" id="indirizzo_spedizione" placeholder="via, n.Civico Città" required
    			onchange="validateFormElem(this, addressPattern, document.getElementById('errorAddress'), addressErrorMessage)">
		    <span id="errorAddress"></span>
        </div>

        <div class="input-container">
            <label for="metodo_pagamento" class="input-label">Metodo di pagamento:</label>
            <select id="metodo_pagamento" name="metodo_pagamento">
                <option value="Carta di credito">Carta di credito</option>
                <option value="PayPal">PayPal</option>
                <option value="Contrassegno">Contrassegno</option>
            </select>
        </div>

        <div class="input-container">        
        	<label for="psw"  class="input-label">Password</label>
  			<input type="password" id="psw" name="psw" required 
    			onchange="validateFormElem(this, passwordPattern, document.getElementById('errorpsw'), passwordErrorMessage)">
			<span id="errorpsw"></span>
		</div>
		
        <button type="submit">Registrati</button>
 	
 		<p>	Hai già un account?
        	<a href="Login">Accedi</a><a href="Home">Home</a>
    	</p>
    </form>

   

</body>
</html>