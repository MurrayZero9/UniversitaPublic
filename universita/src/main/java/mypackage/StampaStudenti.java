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
 * Servlet implementation class StampaStudenti
 */
@WebServlet("/StampaStudenti")
public class StampaStudenti extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private String messaggio;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StampaStudenti()
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
		
		String idAppello = request.getParameter("idAppello");
		String idAppello2 = (String)session.getAttribute("idAppello");
		String idcorso = request.getParameter("idcorso");
		String azione = request.getParameter("azione");
		
		Connection conn = Connessione.getCon();

		try
		{
			/*PreparedStatement smt1=conn.prepareStatement("select stud_prenotato from prenotazione where app_prenotato=CAST(? AS UNSIGNED INTEGER)");
			smt1.setString(1, idAppello);
			ResultSet rs1=smt1.executeQuery();
			while(rs1.next()) {
			String stud=rs1.getString(1);*/
			
			if(azione.equals("1"))
			{
				Statement prepQuery = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet query = prepQuery.executeQuery("select Matricola, nome, cognome from studente");
				
				/*PreparedStatement prepQuery2 = conn.prepareStatement("select stud_prenotato from prenotazione where app_prenotato = ?", ResultSet.TYPE_SCROLL_INSENSITIVE);
				prepQuery2.setString(1, idAppello);
				ResultSet query2 = prepQuery2.executeQuery();
				
				while(query.next())
				{
					System.out.println("Loop 1 " + query.getInt(1));
					while(query2.next())
					{
						System.out.println("Loop 2 " + query2.getInt(1));
						if(query.getInt(1) == query2.getInt(1))
						{
							System.out.println("TROVATO");
							query.deleteRow();
							break;
						}
					}
					
					query2.beforeFirst();
				}
				
				query.beforeFirst();*/
				
				RequestDispatcher rd = request.getRequestDispatcher("modPrenotazioni.jsp");
				request.setAttribute("listaStudenti", query);
				session.setAttribute("idappello", idAppello2);
				rd.forward(request, response);
			}
			else if (azione.equals("2"))
			{
				PreparedStatement smt1 = conn.prepareStatement("select Matricola,nome,cognome from studente join (appello join prenotazione on idappello=app_prenotato) on Matricola=stud_prenotato where appello.idappello = ?");
				smt1.setString(1, idAppello2);
				ResultSet rs1 = smt1.executeQuery();
				
				RequestDispatcher rd = request.getRequestDispatcher("modPrenotazioni.jsp");
				
				session.setAttribute("idappello", idAppello2);
				request.setAttribute("elencoStudenti", rs1);
				request.setAttribute("messaggio", messaggio);
				rd.forward(request, response);
			}
			else
			{
				//Vogliamo prendere la Materia e Data dell'appello selezionato nel form
				PreparedStatement smt = conn.prepareStatement("select Materia,Data from appello where idAppello=CAST(? AS UNSIGNED INTEGER)");
				smt.setString(1, idAppello);
				ResultSet rs = smt.executeQuery();
				rs.next();
				String Materia = rs.getString("Materia");
				String Data = rs.getString("Data");
				
				if(idcorso.equals(Materia))
				{
					//Usiamo la Materia che abbiamo preso nella query precedente per prendere il Nome della Materia
					PreparedStatement smt2 = conn.prepareStatement("select Materia from corso where idcorso=CAST(? AS UNSIGNED INTEGER)");
					smt2.setString(1, Materia);
					ResultSet rs2 = smt2.executeQuery();
					rs2.next();
					String nomeMateria = rs2.getString(1);
					
					//Prendiamo il nome, cognome e matricola degli studenti che si sono prenotati per l'appello che avevamo selezionato nel form
					PreparedStatement smt1 = conn.prepareStatement("select nome,cognome,Matricola from studente join (appello join prenotazione on idappello=app_prenotato) on Matricola=stud_prenotato where appello.idappello = ?");
					smt1.setString(1, idAppello);
					ResultSet rs1 = smt1.executeQuery();
					
					RequestDispatcher rd = request.getRequestDispatcher("professore.jsp");
					
					session.setAttribute("idAppello", idAppello);
					request.setAttribute("Materia", nomeMateria);
					request.setAttribute("Data", Data);
					request.setAttribute("elenco_studenti", rs1);
					request.setAttribute("messaggio", messaggio);
					rd.forward(request, response);
				}
				else
				{
					throw new SQLException();
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			
			//Stampa messaggi di errore nella pagina in cui si è chiamato questo catch
			if(azione.equals("1"))
			{
				
			}
			else if(azione.equals("2"))
			{
				
			}
			else
			{
				home.getErrorMessage(request, response, "ERRORE: Numero non valido o non assegnato.");
			}
		}
	}
	
	
	public void getErrorMessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException
	{
		messaggio = message;
		doPost(request, response);
	}
}
