package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Prenotazione
 */
@WebServlet("/Prenotazione")
public class Prenotazione extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Prenotazione()
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
		Home home = new Home();
		String materia = request.getParameter("materia");
		
		Connection conn = Connessione.getCon();
		try
		{
			PreparedStatement smt1 = conn.prepareStatement("select materia from corso where idcorso=CAST(? AS UNSIGNED INTEGER)");
			smt1.setString(1, materia);
			ResultSet rs1 = smt1.executeQuery();
			rs1.next();
			String nomeMateria = rs1.getString(1);
			
			PreparedStatement smt = conn.prepareStatement("select idAppello,Data,Descrizione from appello where materia=CAST(? AS UNSIGNED INTEGER)");
			smt.setString(1,materia);
			ResultSet rs = smt.executeQuery(); //questo resultset mi prende appelli e date richiesti nella prepared
			
			RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
			
			//Controlla se il primo record è riempito: altrimenti procedi a informare che la query è vuota, invece di stampare la tabella.
			if(rs.isBeforeFirst())
			{
				session.setAttribute("materia", nomeMateria);
				request.setAttribute("elenco_appelli", rs);
			}
			else
			{
				String messaggio = "Non sono al momento aperte prenotazioni a corsi di ";
				request.setAttribute("messaggio", messaggio);
				session.setAttribute("materia", nomeMateria);
			}
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			home.getErrorMessage(request, response, "ERRORE: valore non valido o non inserito.");
		}
	}
}