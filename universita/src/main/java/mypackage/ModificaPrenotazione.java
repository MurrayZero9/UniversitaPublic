package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Prenota
 */
@WebServlet("/ModificaPrenotazione")
public class ModificaPrenotazione extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaPrenotazione()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		StampaStudenti stamStud = new StampaStudenti();
		
		String azione = request.getParameter("azione");
		String matricola = request.getParameter("matricola");
		String idAppello = (String) session.getAttribute("idAppello");
		
		Connection conn= Connessione.getCon();
		try
		{
			//Aggiungi una prenotazione
			if(azione.equals("1"))
			{
				PreparedStatement query = conn.prepareStatement("insert into prenotazione (stud_prenotato, app_prenotato) values (?,?)");
				query.setString(1, matricola);
				query.setString(2, idAppello);
	            query.executeUpdate();

	            RequestDispatcher rd = request.getRequestDispatcher("modPrenotazioni.jsp");
	            String messaggio = "La prenotazione e' stata aggiunta con successo!";
	            request.setAttribute("messaggio", messaggio);
	            rd.forward(request, response);
			}
			else //Cancella una prenotazione
			{
				//Prendiamo l'ID della prenotazione in questione, sapendo la matricola e l'appello
				PreparedStatement prepQuery = conn.prepareStatement("select idpren from prenotazione where app_prenotato = ? and stud_prenotato = ?");
				prepQuery.setString(1, idAppello);
				prepQuery.setString(2, matricola);
				ResultSet query = prepQuery.executeQuery();
				query.next();
				
				//Cancelliamo lo statement usando l'id prenotazione preso in precedenza
				PreparedStatement smt = conn.prepareStatement("delete from prenotazione where (idpren = ?);");
				smt.setInt(1, query.getInt(1));
				smt.executeUpdate();
				
				RequestDispatcher rd = request.getRequestDispatcher("modPrenotazioni.jsp");
				String messaggio = "La prenotazione è stata cancellata con successo!";
				request.setAttribute("messaggio", messaggio);
				rd.forward(request, response);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			
			//Stampa messaggio di errore se viene dato un valore nel form non valido
			if(azione.equals("2"))
			{
				stamStud.getErrorMessage(request, response, "ERRORE: valore non valido o non inserito.");
			}
			
		}
	}
}
