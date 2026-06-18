const nameOrLastnamePattern = /^[A-Za-z]+$/;
const emailPattern = /^\S+@\S+\.\S+$/;
const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

const nameErrorMessage = "Il nome deve contenere solo lettere";
const lastnameErrorMessage = "Il cognome deve contenere solo lettere";
const emailErrorMessage = "Inserisci una email valida";
const passwordErrorMessage = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola e un numero";

function validate() {
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

    if (!validateFormElem(form.psw, passwordPattern, document.getElementById("errorpsw"), passwordErrorMessage)) {
        valid = false;
    }

    return valid;
}

function validateFormElem(formElem, pattern, span, message) {
    if (pattern.test(formElem.value)) {
        formElem.classList.remove("error");
        span.innerHTML = "";
        return true;
    }

    formElem.classList.add("error");
    span.innerHTML = message;
    span.style.color = "red";
    return false;
}