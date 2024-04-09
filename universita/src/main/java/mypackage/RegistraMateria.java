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
@WebServlet("/RegistraMateria")
public class RegistraMateria extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistraMateria()
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
		String materia = request.getParameter("nomemateria");
		String idProfessore = request.getParameter("idProfessore");
		
		Connection conn = Connessione.getCon();
		try
		{
			PreparedStatement smt = conn.prepareStatement("insert into corso (Materia,Cattedra) values (?,?)");
			smt.setString(1, materia);
			smt.setString(2, idProfessore);
			smt.executeUpdate();
			
			RequestDispatcher rd = request.getRequestDispatcher("registraProf.jsp");
			String messaggio = "La registrazione è stata completata con successo!";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
