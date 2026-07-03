package it.unisa.courtCulture.control.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;

/**
 * Servlet implementation class DettaglioProdotto
 */
@WebServlet("/DettaglioProdotto")
public class DettaglioProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProdottoDaoImpl prodottoDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
    }
    
    public DettaglioProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	            int codice = Integer.parseInt(request.getParameter("codice"));

	            ProdottoBean prodotto = prodottoDao.doRetrieveByKey(codice);

	            if (prodotto == null) {
	                response.sendRedirect(request.getContextPath() + "/Shop");
	                return;
	            }

	            request.setAttribute("prodotto", prodotto);
	            request.getRequestDispatcher("/WEB-INF/views/common/dettaglioProdotto.jsp").forward(request, response);

	        } catch (NumberFormatException e) {
	            response.sendRedirect(request.getContextPath() + "/Shop");

	        } catch (SQLException e) {
	            throw new ServletException("Errore durante il recupero del prodotto", e);
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
