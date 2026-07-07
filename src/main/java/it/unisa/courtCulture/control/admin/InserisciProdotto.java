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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;

@WebServlet("/InserisciProdotto")
@MultipartConfig
public class InserisciProdotto extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProdottoDaoImpl prodottoDao;
    private Path uploadDirectory;


    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        prodottoDao = new ProdottoDaoImpl(ds);
        String uploadPath =getServletContext().getInitParameter("productImageUploadPath");
        
        if (uploadPath == null || uploadPath.isBlank()) {

            throw new ServletException("Percorso upload immagini non configurato");
        }


        uploadDirectory =Paths.get(uploadPath).toAbsolutePath().normalize();


        try {

            Files.createDirectories(uploadDirectory);

        } catch (IOException e) {

            throw new ServletException("Impossibile creare la cartella delle immagini",e);
        }
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

        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {

            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            float prezzo = Float.parseFloat(request.getParameter("prezzo"));
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
            	 
            	String mimeType =imagePart.getContentType();


                
            if (mimeType == null || !mimeType.startsWith("image/")) {

            	request.setAttribute("error","Il file selezionato non è un'immagine.");

                request.getRequestDispatcher("/admin/welcomeAdmin.jsp").forward(request, response);

                return;
            }
                 
            String originalFileName =imagePart.getSubmittedFileName();


            String extension =getFileExtension(originalFileName);


            String fileName ="prodotto_"+ codiceProdotto+ extension;


            Path destination =uploadDirectory.resolve(fileName).normalize();


            
            if (!destination.startsWith(uploadDirectory)) {

                throw new ServletException("Percorso immagine non valido");
            }


            try (InputStream input =imagePart.getInputStream()) {

                Files.copy(input,destination,StandardCopyOption.REPLACE_EXISTING);
            }


            prodotto.setCodice(codiceProdotto);

            prodotto.setPathImmagine(fileName);

            prodotto.setMimeType(mimeType);


            prodottoDao.doUpdateImage(prodotto);
        }


        response.sendRedirect(request.getContextPath()+ "/WelcomeAdmin");


    } catch (NumberFormatException e) {

        request.setAttribute("error","Prezzo o quantità non validi.");

        request.getRequestDispatcher("/admin/welcomeAdmin.jsp").forward(request, response);


    } catch (SQLException e) {

        throw new ServletException("Errore database durante l'inserimento del prodotto",e);
    }
}

	
	private String getFileExtension(String fileName) {
	
	    if (fileName == null) {
	        return "";
	    }
	
	
	    int dotIndex =fileName.lastIndexOf(".");
	
	
	    if (dotIndex == -1) {
	        return "";
	    }
	
	
	    return fileName.substring(dotIndex).toLowerCase();
	}
	
	}