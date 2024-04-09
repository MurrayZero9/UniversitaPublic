package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/Home")
public class Home extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private String messaggio;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Home()
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
		
		int idProfessore;
		try
		{
			idProfessore = (Integer)session.getAttribute("idProfessore");
		}
		catch (Exception e)
		{
			idProfessore = 0;
		}
		
		String matricola = (String)session.getAttribute("matricola");
		//String messaggio = request.getParameter("messaggio");
		
		Connection conn = Connessione.getCon(); //abbiamo stabilito una connessione ad db
		try
		{
			//TODO: Eseguire questo blocco su un metodo dedicato per ridurre ridondanze
			if(matricola != null)
			{
				PreparedStatement smt1 = conn.prepareStatement("select nome,cognome,sesso from studente where matricola = ?");
				smt1.setString(1, matricola);  //abbiamo chiesto la stringa relativa all' username e password
				ResultSet rs1 = smt1.executeQuery(); //restituisce la collection contenente la matricola
				rs1.next(); //seleziono il record contenente la matricola 
				String nome = rs1.getString("nome");
				String cognome = rs1.getString("cognome");
				String sesso = rs1.getString("sesso");
				
				Statement smt2 = conn.createStatement();
				ResultSet rs2 = smt2.executeQuery("select idcorso,materia,nome,cognome from corso join professore on cattedra = idprofessore");
				
				session.setAttribute("matricola", matricola);
				session.setAttribute("nome", nome);
				session.setAttribute("cognome", cognome);
				session.setAttribute("sesso", sesso);
				RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
	            request.setAttribute("tabella_corso", rs2);
	            request.setAttribute("messaggio", messaggio);
	            rd.forward(request,response);
			}
			
			if(idProfessore != 0)
			{
				PreparedStatement smt4 = conn.prepareStatement("select nome,cognome from professore where idProfessore=?"); //verifichiamo il nome del professore alla username e password inserita
				smt4.setInt(1, idProfessore);
				ResultSet rs4 = smt4.executeQuery();
				rs4.next();
				String nome = rs4.getString("nome");
				String cognome = rs4.getString("cognome");
				
				PreparedStatement smt5 = conn.prepareStatement("select idcorso,materia from corso where cattedra=?");
				smt5.setInt(1, idProfessore);
				ResultSet rs5 = smt5.executeQuery();
				rs5.next();
				int idcorso = rs5.getInt("idcorso");
				String materia = rs5.getString("materia");
				
				PreparedStatement smt6 = conn.prepareStatement("select idAppello,Data,Descrizione from appello where Materia=?");
				smt6.setInt(1, idcorso);
				ResultSet appelli = smt6.executeQuery();
				
				session.setAttribute("nome", nome);
				session.setAttribute("cognome", cognome);
				RequestDispatcher rd4 = request.getRequestDispatcher("professore.jsp");
				session.setAttribute("materia", materia);
				session.setAttribute("idcorso", idcorso);
				session.setAttribute("idProfessore", idProfessore);
				request.setAttribute("appelli", appelli);
				request.setAttribute("messaggio", messaggio);
				rd4.forward(request, response);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	//Ricevi il messaggio di errore e fai partire il doPost per ricaricare la pagina per mostrare questo messaggio all'utente.
	public void getErrorMessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException
	{
		messaggio = message;
		doPost(request, response);
	}
}