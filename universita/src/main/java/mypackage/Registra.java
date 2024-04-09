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
 * Servlet implementation class Registra
 */
@WebServlet("/Registra")
public class Registra extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registra()
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String sesso = request.getParameter("sesso");
		String tipoUtente = request.getParameter("tipo_utente");
		
		Connection conn = Connessione.getCon();
		try
		{
			if(tipoUtente.equalsIgnoreCase("s")) //Registrazione studente
			{
				PreparedStatement smt = conn.prepareStatement("insert into studente (username,password,tipo_utente,nome,cognome,sesso) values (?,?,?,?,?,?)");
				smt.setString(1, username);
				smt.setString(2, password);
				smt.setString(3, "s");
				smt.setString(4, nome);
				smt.setString(5, cognome);
				smt.setString(6, sesso);
				smt.executeUpdate();
			}
			else //Registrazione professore
			{
				PreparedStatement smt = conn.prepareStatement("insert into professore (username,password,tipo_utente,nome,cognome,sesso) values (?,?,?,?,?,?)");
				smt.setString(1, username);
				smt.setString(2, password);
				smt.setString(3, "p");
				smt.setString(4, nome);
				smt.setString(5, cognome);
				smt.setString(6, sesso);
				smt.executeUpdate();
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("registrazione.jsp");
			String messaggio = "La registrazione è stata effettuata con successo!";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}

