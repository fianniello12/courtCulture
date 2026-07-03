package it.unisa.courtCulture.control.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/ModificaProdotto")
public class ModificaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProdottoDaoImpl prodottoDao;
	
	 @Override
	 public void init() throws ServletException {
       DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
       prodottoDao = new ProdottoDaoImpl(ds);
	 }
	 
    public ModificaProdotto() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

        if (session == null 
                || session.getAttribute("role") == null
                || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {

            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            String nome = request.getParameter("nome");
            String brand = request.getParameter("brand");
            String categoria = request.getParameter("categoria");
            float prezzo = Float.parseFloat(request.getParameter("prezzo").replace(",", "."));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            boolean attivo = Boolean.parseBoolean(request.getParameter("attivo"));

            ProdottoBean prodotto = prodottoDao.doRetrieveByKey(codice);

            if (prodotto == null) {
                response.sendRedirect(request.getContextPath() + "/WelcomeAdmin");
                return;
            }

            prodotto.setNome(nome);
            prodotto.setBrand(brand);
            prodotto.setCategoria(categoria);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantitaDisponibile(quantita);
            prodotto.setAttivo(attivo);

            prodottoDao.doUpdate(prodotto);

            response.sendRedirect(request.getContextPath() + "/WelcomeAdmin");

        } catch (NumberFormatException e) {
            throw new ServletException("Prezzo, quantità o codice non validi", e);

        } catch (SQLException e) {
            throw new ServletException("Errore durante la modifica del prodotto", e);
        }
    }
}

