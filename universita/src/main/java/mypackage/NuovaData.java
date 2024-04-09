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
@WebServlet("/NuovaData")
public class NuovaData extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NuovaData()
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
		String data = request.getParameter("data");
		
		Connection conn = Connessione.getCon();
		try
		{
			PreparedStatement smt = conn.prepareStatement("update appello set Data = ? where (idAppello = ?);");
			smt.setString(1, data);
			smt.setString(2, idappello);
			smt.executeUpdate();
			
			RequestDispatcher rd = request.getRequestDispatcher("modAppello.jsp");
			String messaggio = "La modifica è stata effettuata con successo!";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}