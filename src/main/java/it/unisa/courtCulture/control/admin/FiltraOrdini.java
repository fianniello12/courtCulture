package it.unisa.courtCulture.control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.OrdineDaoImpl;
import it.unisa.courtCulture.model.OrdineBean;

/**
 * Servlet implementation class FiltraOrdini
 */
@WebServlet("/FiltraOrdini")
public class FiltraOrdini extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDaoImpl ordineDao;
	
	@Override
    public void init() throws ServletException {

        DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");

        ordineDao = new OrdineDaoImpl(ds);
    }
	
    public FiltraOrdini() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession(false);


	        if (session == null || !session.getAttribute("role").equals("admin")) {

	            response.sendRedirect(request.getContextPath() + "/Login");

	            return;
	        }


	        String filtro =request.getParameter("filtro");


	        try {

	            List<OrdineBean> ordini = null;


	            if (filtro.equals("tutti")) {

	                ordini =ordineDao.doRetrieveAll("data_ordine");


	            } else if (filtro.equals("periodo")) {

	                LocalDate dataDa =LocalDate.parse(request.getParameter("dataDa"));

	                LocalDate dataA =LocalDate.parse(request.getParameter("dataA"));

	                ordini =ordineDao.doRetrieveByPeriodo(dataDa,dataA);


	            } else if (filtro.equals("cliente")) {

	                int idCliente =Integer.parseInt(request.getParameter("idCliente"));

	                ordini =ordineDao.doRetrieveByUtente(idCliente);
	            }


	            request.setAttribute("ordini",ordini);


	            request.getRequestDispatcher("/WelcomeAdmin").forward(request, response);


	        } catch (SQLException e) {

	            throw new ServletException("Errore durante il recupero degli ordini",e);
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
