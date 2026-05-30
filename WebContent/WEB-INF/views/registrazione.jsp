<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/Registrazione" method="post">

        <div>
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
        </div>

        <br>

        <div>
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" required>
        </div>

        <br>

        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <br>

        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

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

        <br>

        <button type="submit">Registrati</button>

    </form>

    <p>
        Hai già un account?
        <a href=Login">Accedi</a>
    </p>

</body>
</html>