package it.unisa.courtCulture.control.common;

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

import it.unisa.courtCulture.dao.DettaglioOrdineDaoImpl;
import it.unisa.courtCulture.dao.OrdineDaoImpl;
import it.unisa.courtCulture.dao.ProdottoDaoImpl;
import it.unisa.courtCulture.model.CarrelloItemBean;
import it.unisa.courtCulture.model.OrdineBean;
import it.unisa.courtCulture.model.ProdottoBean;
import it.unisa.courtCulture.model.DettaglioOrdineBean;

/**
 * Servlet implementation class ConfermaOrdine
 */
@WebServlet("/ConfermaOrdine")
public class ConfermaOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private OrdineDaoImpl ordineDao;
    private DettaglioOrdineDaoImpl dettaglioOrdineDao;
    private ProdottoDaoImpl prodottoDao;


    @Override
    public void init() throws ServletException {

        DataSource ds =
                (DataSource) getServletContext()
                .getAttribute("DataSource");


        if (ds == null) {

            throw new ServletException(
                    "DataSource non disponibile"
            );
        }


        ordineDao =
                new OrdineDaoImpl(ds);

        dettaglioOrdineDao =
                new DettaglioOrdineDaoImpl(ds);

        prodottoDao =
                new ProdottoDaoImpl(ds);
    }
    
    public ConfermaOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        

        HttpSession session =request.getSession(false);


        if (session == null) {

            scriviJson(response,false,"Devi effettuare il login per confermare l'ordine");

            return;
        }


        Object idUtenteObject =session.getAttribute("idUtente");


        if (idUtenteObject == null) {

            scriviJson(response,false,"Devi effettuare il login per confermare l'ordine");

            return;
        }


        int idUtente;


        try {

            idUtente =((Number) idUtenteObject).intValue();

        } catch (ClassCastException e) {

            scriviJson(response,false,"Sessione utente non valida");

            return;
        }


      
        @SuppressWarnings("unchecked")
		List<CarrelloItemBean> carrello =(List<CarrelloItemBean>)session.getAttribute("carrello");


        if (carrello == null || carrello.isEmpty()) {

            scriviJson(response,false,"Il carrello è vuoto");

            return;
        }


        

        String indirizzoSpedizione =request.getParameter("indirizzoSpedizione");


        String metodoPagamento =request.getParameter("metodoPagamento");


        if (indirizzoSpedizione == null|| indirizzoSpedizione.trim().isEmpty()) {

            scriviJson(response,false,"Inserisci l'indirizzo di spedizione");

            return;
        }


        if (metodoPagamento == null|| metodoPagamento.trim().isEmpty()) {

            scriviJson(response,false,"Seleziona un metodo di pagamento");

            return;
        }


        indirizzoSpedizione =indirizzoSpedizione.trim();

        metodoPagamento =metodoPagamento.trim();


        

        if (!metodoPagamento.equals("Carta") && !metodoPagamento.equals("PayPal") && !metodoPagamento.equals("Contrassegno")) {

            scriviJson(response,false,"Metodo di pagamento non valido");

            return;
        }


        
        try {

            float totaleOrdine = 0;


            for (CarrelloItemBean item : carrello) {

                ProdottoBean prodotto =prodottoDao.doRetrieveByKey(item.getCodiceProdotto());


                if (prodotto == null) {

                    scriviJson(response,false,"Uno dei prodotti non esiste più");

                    return;
                }


                if (!prodotto.isAttivo()) {

                    scriviJson(response,false,"Il prodotto " + prodotto.getNome() + " non è più disponibile");

                    return;
                }


                if (item.getQuantita() > prodotto.getQuantitaDisponibile()) {

                    scriviJson(response,false,"Quantità non disponibile per " + prodotto.getNome());

                    return;
                }

                totaleOrdine +=prodotto.getPrezzo() * item.getQuantita();
            }


            

            OrdineBean ordine = new OrdineBean();


            ordine.setIdUtente(idUtente);

            ordine.setDataOrdine(LocalDate.now().toString());

            ordine.setStatoOrdine("in elaborazione");

            ordine.setTotaleOrdine(totaleOrdine);

            ordine.setIndirizzoSpedizione(indirizzoSpedizione);

            ordine.setMetodoPagamento(metodoPagamento);


            int idOrdine =ordineDao.doSave(ordine);


            if (idOrdine <= 0) {

                scriviJson(response,false,"Errore durante la creazione dell'ordine");

                return;
            }


           

            for (CarrelloItemBean item : carrello) {

                ProdottoBean prodotto =prodottoDao.doRetrieveByKey(item.getCodiceProdotto());


                DettaglioOrdineBean dettaglio =new DettaglioOrdineBean();


                dettaglio.setIdOrdine(idOrdine);

                dettaglio.setCodiceProdotto(item.getCodiceProdotto());
                
                dettaglio.setTaglia(item.getTaglia());

                dettaglio.setQuantita(item.getQuantita());

                dettaglio.setPrezzoAcquisto(prodotto.getPrezzo());


                dettaglioOrdineDao.doSave(dettaglio);


                

                int nuovaQuantita =prodotto.getQuantitaDisponibile() - item.getQuantita();


                prodotto.setQuantitaDisponibile(nuovaQuantita);


                prodottoDao.doUpdate(prodotto);
            }


            session.removeAttribute("carrello");


            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write("{\"success\": true}");

        } catch (SQLException e) {

            e.printStackTrace();


            scriviJson(response,false,"Errore durante la conferma dell'ordine");
        }
    }


    

    
    private void scriviJson(HttpServletResponse response, boolean success, String message)throws IOException {

    	response.getWriter().write("{" + "\"success\":" + success + "," + "\"message\":\"" + escapeJson(message) + "\"" + "}");

    }


    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }


        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
