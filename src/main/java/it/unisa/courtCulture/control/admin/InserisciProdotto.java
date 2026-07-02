package it.unisa.courtCulture.control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;

@WebServlet("/InserisciProdotto")
@MultipartConfig
public class InserisciProdotto extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProdottoDaoImpl prodottoDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/admin/welcomeAdmin.jsp");
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
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            float prezzo = Float.parseFloat(request.getParameter("prezzo").replace(",", "."));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String categoria = request.getParameter("categoria");
            String brand = request.getParameter("brand");

            ProdottoBean prodotto = new ProdottoBean();

            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantitaDisponibile(quantita);
            prodotto.setCategoria(categoria);
            prodotto.setBrand(brand);
            prodotto.setAttivo(true);

            int codiceProdotto = prodottoDao.doSave(prodotto);

            if (codiceProdotto == -1) {
                request.setAttribute("error", "Errore durante l'inserimento del prodotto.");
                request.getRequestDispatcher("/admin/welcomeAdmin.jsp").forward(request, response);
                return;
            }

            Part imagePart = request.getPart("immagine");

            if (imagePart != null && imagePart.getSize() > 0) {
                String originalFileName = imagePart.getSubmittedFileName();
                String extension = getFileExtension(originalFileName);

                String fileName = "prodotto_" + codiceProdotto + extension;

                String uploadPath = getServletContext().getRealPath("/images/prodotti");

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = uploadPath + File.separator + fileName;

                imagePart.write(filePath);

                prodotto.setCodice(codiceProdotto);
                prodotto.setPathImmagine("images/prodotti/" + fileName);
                prodotto.setMimeType(imagePart.getContentType());

                prodottoDao.doUpdateImage(prodotto);
            }

            response.sendRedirect(request.getContextPath() + "/WelcomeAdmin");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Prezzo o quantità non validi.");
            request.getRequestDispatcher("/admin/welcomeAdmin.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore database durante l'inserimento del prodotto", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        }

        return fileName.substring(dotIndex);
    }
}