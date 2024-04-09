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

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class Prenota
 */
@WebServlet("/EliminaAppello")
public class EliminaAppello extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaAppello()
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
		String idappello = request.getParameter("idappello");
		
		Connection conn = Connessione.getCon();
		try
		{
			PreparedStatement smt = conn.prepareStatement("delete from appello where (idAppello = ?);");
			smt.setString(1, idappello);
			smt.executeUpdate();
			
			RequestDispatcher rd = request.getRequestDispatcher("elimAppello.jsp");
			String messaggio = "L'appello è stato cancellato con successo!";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (MySQLIntegrityConstraintViolationException e)
		{			
			RequestDispatcher rd = request.getRequestDispatcher("elimAppello.jsp");
			String messaggio = "ERRORE: Non è posssibile cancellare l'appello perchè non sono state cancellate le prenotazioni. Cancella le prenotazioni e riprova.";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}