package it.unisa.courtCulture.control.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.CarrelloItemBean;
import it.unisa.courtCulture.model.ProdottoBean;

/**
 * Servlet implementation class Carrello
 */
@WebServlet("/Carrello")
public class Carrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDaoImpl prodottoDao;

    @Override
    public void init() throws ServletException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        if (ds == null) {
            throw new ServletException("DataSource non disponibile");
        }

        prodottoDao = new ProdottoDaoImpl(ds);
    }

    
    public Carrello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String azione =request.getParameter("azione");

        if ("get".equals(azione)) {
            inviaCarrelloJson(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/common/carrello.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String azione = request.getParameter("azione");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (azione == null) {
            scriviJson(response, false, "Azione mancante");
            return;
        }
        
        switch (azione) {
        case "add":
            aggiungiProdotto(request, response);
            break;

        case "update":
            aggiornaQuantita(request, response);
            break;

        case "remove":
            rimuoviProdotto(request, response);
            break;

        case "clear":
            svuotaCarrello(request, response);
            break;

        default:
            scriviJson(response, false, "Azione non valida");
            break;
            
        }
        
	}
	
	private void aggiungiProdotto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            int taglia = Integer.parseInt(request.getParameter("taglia"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            if (quantita <= 0) {
                scriviJson(response, false, "Quantità non valida");
                return;
            }

            ProdottoBean prodotto = prodottoDao.doRetrieveByKey(codice);

            if (prodotto == null) {
                scriviJson(response, false, "Prodotto non trovato");
                return;
            }

            if (!prodotto.isAttivo()) {
                scriviJson(response, false, "Prodotto non disponibile");
                return;
            }

            if (quantita > prodotto.getQuantitaDisponibile()) {
                scriviJson(response, false, "Quantità non disponibile");
                return;
            }

            HttpSession session = request.getSession();
            List<CarrelloItemBean> carrello = getCarrello(session);

            boolean trovato = false;

            for (CarrelloItemBean item : carrello) {
                if (item.getCodiceProdotto() == codice && item.getTaglia() == taglia) {

                    int nuovaQuantita = item.getQuantita() + quantita;

                    if (nuovaQuantita > prodotto.getQuantitaDisponibile()) {
                        scriviJson(response, false, "Quantità non disponibile");
                        return;
                    }

                    item.setQuantita(nuovaQuantita);
                    trovato = true;
                    break;
                }
            }

            if (!trovato) {
                CarrelloItemBean item = new CarrelloItemBean();

                item.setCodiceProdotto(prodotto.getCodice());
                item.setNome(prodotto.getNome());
                item.setBrand(prodotto.getBrand());
                item.setPathImmagine(prodotto.getPathImmagine());
                item.setPrezzo(prodotto.getPrezzo());
                item.setTaglia(taglia);
                item.setQuantita(quantita);

                carrello.add(item);
            }

            session.setAttribute("carrello", carrello);
            
            response.getWriter().write("{" + "\"success\":true,"+ "\"message\":\"Prodotto aggiunto al carrello\","+ "\"numeroProdotti\":" + carrello.size() + ","+ "\"totale\":" + calcolaTotale(carrello)+ "}");

        } catch (NumberFormatException e) {

            scriviJson(response,false,"Dati non validi");

        } catch (SQLException e) {

            e.printStackTrace();

            scriviJson(response,false,"Errore database");
        }
    }

	private void aggiornaQuantita(HttpServletRequest request,HttpServletResponse response)throws IOException {

	    try {

	        int codice =Integer.parseInt(request.getParameter("codice"));

	        int taglia =Integer.parseInt(request.getParameter("taglia"));

	        int quantita =Integer.parseInt(request.getParameter("quantita"));


	        if (quantita <= 0) {

	            scriviJson(response,false,"Quantità non valida");

	            return;
	        }


	        HttpSession session =request.getSession();


	        List<CarrelloItemBean> carrello =getCarrello(session);


	        for (CarrelloItemBean item : carrello) {

	            if (item.getCodiceProdotto() == codice && item.getTaglia() == taglia) {

	                ProdottoBean prodotto =prodottoDao.doRetrieveByKey(codice);


	                if (prodotto == null || !prodotto.isAttivo()) {

	                    scriviJson(response,false,"Prodotto non disponibile");

	                    return;
	                }


	                if (quantita > prodotto.getQuantitaDisponibile()) {

	                    scriviJson(response,false,"Quantità non disponibile");

	                    return;
	                }


	                item.setQuantita(quantita);


	                session.setAttribute("carrello",carrello);


	                response.getWriter().write("{" + "\"success\":true," + "\"message\":\"Quantità aggiornata\"," + "\"totale\":" + calcolaTotale(carrello) + "}");

	                return;
	            }
	        }


	        scriviJson(response,false,"Prodotto non presente nel carrello");


	    } catch (NumberFormatException e) {

	        scriviJson(response,false,"Dati non validi");


	    } catch (SQLException e) {

	        e.printStackTrace();

	        scriviJson(response,false,"Errore database");
	    }
	}
	
	private void rimuoviProdotto(HttpServletRequest request,HttpServletResponse response) throws IOException {

	    try {

	        int codice =Integer.parseInt(request.getParameter("codice"));

	        int taglia =Integer.parseInt(request.getParameter("taglia"));


	        HttpSession session =request.getSession();


	        List<CarrelloItemBean> carrello =getCarrello(session);


	        boolean rimosso =carrello.removeIf(item ->item.getCodiceProdotto() == codice && item.getTaglia() == taglia);


	        session.setAttribute("carrello",carrello);


	        if (rimosso) {

	            response.getWriter().write("{" + "\"success\":true," + "\"message\":\"Prodotto rimosso dal carrello\","+ "\"totale\":" + calcolaTotale(carrello) + "}");

	        } else {

	            scriviJson(response,false,"Prodotto non presente nel carrello");
	        }


	    } catch (NumberFormatException e) {

	        scriviJson(response,false,"Dati non validi");
	    }
	}

	private void svuotaCarrello(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session =request.getSession();

	    session.removeAttribute("carrello");

	    response.getWriter().write("{" + "\"success\":true," + "\"message\":\"Carrello svuotato\","+ "\"totale\":0"+ "}");
	}
	

	private void scriviJson(HttpServletResponse response, boolean success, String message) throws IOException {

	    response.getWriter().write("{" + "\"success\":" + success + "," + "\"message\":\""+ escapeJson(message) + "\"" + "}");
	}

	private void inviaCarrelloJson( HttpServletRequest request, HttpServletResponse response) throws IOException {

	    HttpSession session = request.getSession();

	    List<CarrelloItemBean> carrello = getCarrello(session);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    StringBuilder json = new StringBuilder();

	    json.append("{");
	    json.append("\"success\":true,");
	    json.append("\"items\":[");

	    for (int i = 0; i < carrello.size(); i++) {

	        CarrelloItemBean item = carrello.get(i);

	        json.append("{");

	        json.append("\"codice\":").append(item.getCodiceProdotto()).append(",");

	        json.append("\"nome\":\"").append(escapeJson(item.getNome())).append("\",");

	        json.append("\"brand\":\"").append(escapeJson(item.getBrand())).append("\",");

	        json.append("\"pathImmagine\":\"").append(escapeJson(item.getPathImmagine())).append("\",");

	        json.append("\"prezzo\":").append(item.getPrezzo()).append(",");

	        json.append("\"taglia\":").append(item.getTaglia()).append(",");

	        json.append("\"quantita\":").append(item.getQuantita()).append(",");

	        json.append("\"subtotale\":").append(item.getSubtotale());

	        json.append("}");

	        if (i < carrello.size() - 1) {
	            json.append(",");
	        }
	    }

	    json.append("],");

	    json.append("\"totale\":").append(calcolaTotale(carrello));

	    json.append("}");

	    response.getWriter().write(json.toString());
	}
	
	@SuppressWarnings("unchecked")
	private List<CarrelloItemBean> getCarrello(
	        HttpSession session) {

	    List<CarrelloItemBean> carrello =(List<CarrelloItemBean>)session.getAttribute("carrello");

	    if (carrello == null) {

	        carrello = new ArrayList<>();

	        session.setAttribute("carrello",carrello);
	    }

	    return carrello;
	}
	
	private float calcolaTotale(List<CarrelloItemBean> carrello) {

	    float totale = 0;

	    for (CarrelloItemBean item : carrello) {

	        totale += item.getSubtotale();
	    }

	    return totale;
	}
	
	private String escapeJson(String value) {

	    if (value == null) {
	        return "";
	    }

	    return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
	}
	
}
