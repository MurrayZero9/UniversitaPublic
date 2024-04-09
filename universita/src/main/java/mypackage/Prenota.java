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
@WebServlet("/Prenota")
public class Prenota extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Prenota()
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String appello = request.getParameter("appello");
		String nomeMateria = (String) session.getAttribute("materia");
		String matricola = (String) session.getAttribute("matricola");
		
		Connection conn= Connessione.getCon();
		try
		{
			Statement prenotazioni = conn.createStatement(); 
			ResultSet queryPrenot = prenotazioni.executeQuery("select stud_prenotato,app_prenotato from prenotazione");
			
			while(queryPrenot.next())
			{
				if(queryPrenot.getString("stud_prenotato").equalsIgnoreCase(matricola) && queryPrenot.getString("app_prenotato").equalsIgnoreCase(appello))
				{
					RequestDispatcher rd1 = request.getRequestDispatcher("studente.jsp");
					String messaggio = "ERRORE: La seguente prenotazione è già stata effettuata in precedenza.";
					request.setAttribute("messaggio", messaggio);
					rd1.forward(request, response);
					return;
				}
			}
			
			PreparedStatement smt2 = conn.prepareStatement("insert into prenotazione (stud_prenotato,app_prenotato) values (CAST(? AS UNSIGNED INTEGER),CAST(? AS UNSIGNED INTEGER))");
			smt2.setString(1, matricola);
			smt2.setString(2, appello);
			smt2.executeUpdate();
			
			//Prendi la data dell'appello
			PreparedStatement recuperoData = conn.prepareStatement("select data from appello where idAppello=CAST(? AS UNSIGNED INTEGER)");
			recuperoData.setString(1, appello);
			ResultSet data = recuperoData.executeQuery();
			data.next();
			String dataScelta = data.getString(1);
			
			/*/Prendi la materia del corso in cui si è prenotati l'appello
			PreparedStatement recuperoMateria = conn.prepareStatement("select materia from corso where idcorso=CAST(? AS UNSIGNED INTEGER)");
			recuperoMateria.setString(1, appello);
			ResultSet materia = recuperoMateria.executeQuery();
			materia.next();
			String nomeMateria = materia.getString(1);*/
			
			RequestDispatcher rd2 = request.getRequestDispatcher("studente.jsp");
			request.setAttribute("data", dataScelta);
			request.setAttribute("materia2", nomeMateria);
			rd2.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
