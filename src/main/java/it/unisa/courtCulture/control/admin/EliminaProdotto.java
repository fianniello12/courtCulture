package it.unisa.courtCulture.control.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EliminaProdotto")
public class EliminaProdotto extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProdottoDaoImpl prodottoDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null
                || !session.getAttribute("role").equals("admin")) {

            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            int codice = Integer.parseInt(request.getParameter("codice"));

            boolean deleted = prodottoDao.doDelete(codice);

            if (!deleted) {
                request.setAttribute("error", "Prodotto non trovato o non eliminato.");
            }

            response.sendRedirect(request.getContextPath() + "/Admin");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Codice prodotto non valido.");
            request.getRequestDispatcher("/admin/welcomeAdmin.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore durante l'eliminazione del prodotto", e);
        }
    }
}