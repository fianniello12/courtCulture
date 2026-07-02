package it.unisa.courtCulture.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Shop")
public class Shop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private ProdottoDaoImpl prodottoDao;
    
    public Shop() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	            Collection<ProdottoBean> prodotti = prodottoDao.doRetrieveAll("brand");

	            request.setAttribute("prodotti", prodotti);

	            request.getRequestDispatcher("/WEB-INF/views/common/shop.jsp")
	                   .forward(request, response);

	        } catch (SQLException e) {
	            throw new ServletException("Errore nel recupero dei prodotti", e);
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
