package it.unisa.courtCulture.control.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.ProdottoBean;

/**
 * Servlet implementation class ImmagineProdotto
 */
@WebServlet("/ImmagineProdotto")
public class ImmagineProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDaoImpl prodottoDao;
    private Path uploadDirectory;

	
    
    @Override
	public void init() throws ServletException {

	    DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");

	    prodottoDao = new ProdottoDaoImpl(ds);
	    
	    String uploadPath =getServletContext().getInitParameter("productImageUploadPath");
	    
	    if (uploadPath == null || uploadPath.isBlank()) {

            throw new ServletException("Percorso upload immagini non configurato");
        }


        uploadDirectory =Paths.get(uploadPath).toAbsolutePath().normalize();
	}
	    
    public ImmagineProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codiceParam =request.getParameter("codice");


        if (codiceParam == null) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Codice prodotto mancante");

            return;
        }


        try {

            int codice =Integer.parseInt(codiceParam);


            ProdottoBean prodotto =prodottoDao.doRetrieveByKey(codice);


            if (prodotto == null) {

                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Prodotto non trovato");

                return;
            }


            if (!prodotto.hasImage()) {

                sendDefaultImage(response);

                return;
            }


           
            String fileName =Paths.get(prodotto.getPathImmagine()).getFileName().toString();


            Path imagePath =uploadDirectory.resolve(fileName).normalize();


            if (!imagePath.startsWith(uploadDirectory) || !Files.isRegularFile(imagePath)) {

                sendDefaultImage(response);

                return;
            }


            String mimeType =prodotto.getMimeType();


            if (mimeType == null || mimeType.isBlank()) {

                mimeType =Files.probeContentType(imagePath);
            }


            if (mimeType == null) {

                mimeType ="application/octet-stream";
            }


            response.setContentType(mimeType);

            response.setContentLengthLong(
                    Files.size(imagePath)
            );


            try (
                InputStream input =Files.newInputStream(imagePath);

                ServletOutputStream output =response.getOutputStream()
            ) {

                input.transferTo(output);
            }


        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Codice prodotto non valido");


        } catch (SQLException e) {

            throw new ServletException("Errore durante il recupero del prodotto",e);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void sendDefaultImage(HttpServletResponse response) throws IOException {

        InputStream input =getServletContext().getResourceAsStream("/images/no-image.png");

        if (input == null) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Immagine non disponibile");

            return;
        }


        response.setContentType("image/png");


        try (input;ServletOutputStream output =response.getOutputStream()) {

            input.transferTo(output);
        }
    }
}