package it.unisa.courtCulture.control;

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

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UtenteDaoImpl utenteDao;
	
	@Override
	public void init() throws ServletException{
		DataSource ds= (DataSource) getServletContext().getAttribute("DataSource");
		
		if(ds==null) {
			throw new ServletException("DataSource non disponibile");
		}
		
		utenteDao = new UtenteDaoImpl(ds);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> errors = new ArrayList<>();
		
		String email= request.getParameter("email");
		String password = request.getParameter("password");
		
		email = validateField(email, "email", errors);
		password = validateField(password, "password", errors);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");		
		
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return; 
		}
		
		try {
			UtenteBean utente = utenteDao.doRetrieveByEmail(email);
		
			if(utente == null) {
				errors.add("email o password errati");
				request.setAttribute("errors", errors);
				dispatcher.forward(request, response);
				return;
			}
			
			if (!utente.getPassword().equals(password)) {
                errors.add("Email o password non validi");
                request.setAttribute("errors", errors);
                dispatcher.forward(request, response);
                return;
            }
			
			request.getSession().setAttribute("utente", utente);
            request.getSession().setAttribute("idUtente", utente.getId());
            request.getSession().setAttribute("role", utente.getRuolo());

            if ("admin".equalsIgnoreCase(utente.getRuolo())) {
                response.sendRedirect(request.getContextPath() + "/admin/welcomeAdmin");
            } else {
                response.sendRedirect(request.getContextPath() + "/common/welcomeUser");
            }
			
		}catch (SQLException e) {
			throw new ServletException("Errore durante il login",e);
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
