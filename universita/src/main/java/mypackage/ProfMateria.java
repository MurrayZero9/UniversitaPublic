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

import com.mysql.jdbc.Statement;


/**
 * Servlet implementation class profMateria
 */
@WebServlet("/ProfMateria")
public class ProfMateria extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfMateria()
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
		//String materia = request.getParameter("materia");
		//System.out.println(materia);
		
		String materia = request.getParameter("idcorso");
		String azione = request.getParameter("azione");
		
		Connection conn = Connessione.getCon();
		try
		{
			if(azione.equals("1"))
			{
				HttpSession session = request.getSession();
				session.setAttribute("idcorso", materia);
				
				RequestDispatcher rd = request.getRequestDispatcher("AddAppello.jsp");
				rd.forward(request, response);
			}
			else if(azione.equals("2"))
			{
				PreparedStatement smt = conn.prepareStatement("select idAppello,Data,Descrizione from appello where Materia=?");
				smt.setInt(1, Integer.parseInt(materia));
				ResultSet appelli = smt.executeQuery();
				
				RequestDispatcher rd = request.getRequestDispatcher("modAppello.jsp");
				request.setAttribute("appelli", appelli);
				rd.forward(request, response);
			}
			else if(azione.equals("3"))
			{
				PreparedStatement smt = conn.prepareStatement("select idAppello,Data,Descrizione from appello where Materia=?");
				smt.setInt(1, Integer.parseInt(materia));
				ResultSet appelli = smt.executeQuery();
				
				RequestDispatcher rd = request.getRequestDispatcher("elimAppello.jsp");
				request.setAttribute("appelli", appelli);
				rd.forward(request, response);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}