package it.unisa.courtCulture.control.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.UtenteDaoImpl;
import it.unisa.courtCulture.model.UtenteBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    private UtenteDaoImpl utenteDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile");
        }

        utenteDao = new UtenteDaoImpl(ds);
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/registrazione.jsp");
		dispatcher.forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();

        String email = validateField(request.getParameter("email"), "email", errors);
        String password = validateField(request.getParameter("psw"), "password", errors);
        String nome = validateField(request.getParameter("nome"), "nome", errors);
        String cognome = validateField(request.getParameter("cognome"), "cognome", errors);

        String indirizzoSpedizione = request.getParameter("indirizzo_spedizione");
        String metodoPagamento = request.getParameter("metodo_pagamento");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp");

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            dispatcher.forward(request, response);
            return;
        }

        try {
            UtenteBean utenteEsistente = utenteDao.doRetrieveByEmail(email);

            if (utenteEsistente != null) {
                errors.add("Email già registrata");
                request.setAttribute("errors", errors);
                dispatcher.forward(request, response);
                return;
            }

            UtenteBean nuovoUtente = new UtenteBean();

            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(password);
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome);
            nuovoUtente.setIndirizzoSpedizione(indirizzoSpedizione);
            nuovoUtente.setMetodoPagamento(metodoPagamento);
            nuovoUtente.setRuolo("user");

            utenteDao.doSave(nuovoUtente);

            response.sendRedirect(request.getContextPath() + "/Login");

        } catch (SQLException e) {
            throw new ServletException("Errore durante la registrazione", e);
        }
    }
	
	
	private String validateField(String value, String fieldName, List<String> errors) {
        if (value == null || value.trim().isEmpty()) {
            errors.add("Il campo " + fieldName + " non può essere vuoto");
            return "";
        }
        return value.trim();
	}

}
