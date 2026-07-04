const nameOrLastnamePattern = /^[A-Za-zÀ-ÿ\s'’-]{2,50}$/;
const emailPattern = /^\S+@\S+\.\S+$/;
const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
const loginPasswordPattern = /^.{1,}$/;
const addressPattern = /^(?=.*[0-9])[A-Za-zÀ-ÿ0-9\s,.()'’\/-]{5,100}$/;
const cardNumberPattern = /^(?:\d\s*){16}$/;

const nameErrorMessage = "Il nome deve contenere solo lettere";
const lastnameErrorMessage = "Il cognome deve contenere solo lettere";
const emailErrorMessage = "Inserisci una email valida";
const passwordErrorMessage = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola e un numero";
const loginPasswordErrorMessage = "La password non deve essere vuota";
const addressErrorMessage = "L'indirizzo deve contenere almeno il numero civico ed essere valido";
const cardNumberMessage = "Il numero della carta di credito deve contenere essattamente 16 numeri";

function validateRegistrazione() {
    let valid = true;
    let form = document.getElementById("regForm");

    if (!validateFormElem(form.nome, nameOrLastnamePattern, document.getElementById("errorName"), nameErrorMessage)) {
        valid = false;
    }

    if (!validateFormElem(form.cognome, nameOrLastnamePattern, document.getElementById("errorCognome"), lastnameErrorMessage)) {
        valid = false;
    }

    if (!validateFormElem(form.email, emailPattern, document.getElementById("errorEmail"), emailErrorMessage)) {
        valid = false;
    }

    if (!validateFormElem(form.indirizzo_spedizione, addressPattern, document.getElementById("errorAddress"), addressErrorMessage)) {
        valid = false;
    }

    if (!validateFormElem(form.psw, passwordPattern, document.getElementById("errorpsw"), passwordErrorMessage)) {
        valid = false;
    }

	if (!validateFormElem(form.num, cardNumberPattern, document.getElementById("errorNum"), cardNumberMessage)) {
	        valid = false;
	    }
	
    return valid;
}

function validateLogin() {
    let valid = true;
    let form = document.getElementById("loginForm");

    if (!validateFormElem(form.email, emailPattern, document.getElementById("errorEmail"), emailErrorMessage)) {
        valid = false;
    }

    if (!validateFormElem(form.password, loginPasswordPattern, document.getElementById("errorPassword"), loginPasswordErrorMessage)) {
        valid = false;
    }

    return valid;
}

function validateFormElem(formElem, pattern, span, message) {
    const value = formElem.value.trim();

    if (pattern.test(value)) {
        formElem.classList.remove("error");
        span.textContent = "";
        return true;
    }

    formElem.classList.add("error");
    span.textContent = message;
    span.style.color = "red";
    return false;
}