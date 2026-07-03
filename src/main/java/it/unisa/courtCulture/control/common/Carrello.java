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
		String azione = request.getParameter("azione");

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
                return;
            }

            ProdottoBean prodotto = prodottoDao.doRetrieveByKey(codice);

            if (prodotto == null) {
                return;
            }

            if (!prodotto.isAttivo()) {
                return;
            }

            if (quantita > prodotto.getQuantitaDisponibile()) {
                return;
            }

            HttpSession session = request.getSession();
            List<CarrelloItemBean> carrello = getCarrello(session);

            boolean trovato = false;

            for (CarrelloItemBean item : carrello) {
                if (item.getCodiceProdotto() == codice && item.getTaglia() == taglia) {

                    int nuovaQuantita = item.getQuantita() + quantita;

                    if (nuovaQuantita > prodotto.getQuantitaDisponibile()) {
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


        } catch (NumberFormatException e) {

        } catch (SQLException e) {
            
        }
    }

    private void aggiornaQuantita(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            int taglia = Integer.parseInt(request.getParameter("taglia"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            HttpSession session = request.getSession();
            List<CarrelloItemBean> carrello = getCarrello(session);

            for (CarrelloItemBean item : carrello) {
                if (item.getCodiceProdotto() == codice && item.getTaglia() == taglia) {

                    if (quantita <= 0) {
                        carrello.remove(item);
                    } else {
                        ProdottoBean prodotto = prodottoDao.doRetrieveByKey(codice);

                        if (prodotto == null || !prodotto.isAttivo()) {
                            return;
                        }

                        if (quantita > prodotto.getQuantitaDisponibile()) {
                            return;
                        }

                        item.setQuantita(quantita);
                    }

                    session.setAttribute("carrello", carrello);

                    
                    return;
                }
            }

            
        } catch (NumberFormatException e) {
            
        } catch (SQLException e) {

        }
    }

    private void rimuoviProdotto(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            int taglia = Integer.parseInt(request.getParameter("taglia"));

            HttpSession session = request.getSession();
            List<CarrelloItemBean> carrello = getCarrello(session);

            carrello.removeIf(item ->
                    item.getCodiceProdotto() == codice && item.getTaglia() == taglia
            );

            session.setAttribute("carrello", carrello);

          
        } catch (NumberFormatException e) {
            
        }
    }

    private void svuotaCarrello(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("carrello");

       
    }

    private void inviaCarrelloJson(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

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
    private List<CarrelloItemBean> getCarrello(HttpSession session) {
        List<CarrelloItemBean> carrello =
                (List<CarrelloItemBean>) session.getAttribute("carrello");

        if (carrello == null) {
            carrello = new ArrayList<>();
            session.setAttribute("carrello", carrello);
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

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");
    }
}


