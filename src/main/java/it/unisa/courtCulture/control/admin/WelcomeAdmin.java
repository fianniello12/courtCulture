package it.unisa.courtCulture.control.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.OrdineDaoImpl;
import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.dao.UtenteDaoImpl;
import it.unisa.courtCulture.model.OrdineBean;
import it.unisa.courtCulture.model.ProdottoBean;
import it.unisa.courtCulture.model.UtenteBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class WelcomeAdmin
 */
@WebServlet("/WelcomeAdmin")
public class WelcomeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDaoImpl prodottoDao;
    private UtenteDaoImpl utenteDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
        utenteDao = new UtenteDaoImpl(ds);
    }
    
    public WelcomeAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {

	            response.sendRedirect(request.getContextPath() + "/Login");
	            return;
	        }

	        try {
	            List<ProdottoBean> prodotti = prodottoDao.doRetrieveAll(null);
	            List<UtenteBean> utenti = utenteDao.doRetrieveAll(null);

	            request.setAttribute("prodotti",prodotti);
	            request.setAttribute("utenti",utenti);

	            request.getRequestDispatcher("/WEB-INF/views/admin/welcomeAdmin.jsp").forward(request, response);
	            
	        } catch (SQLException e) {
	            throw new ServletException("Errore durante il recupero dei prodotti", e);
	        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
