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
 * Servlet implementation class AddAppello
 */
@WebServlet("/AddAppello")
public class AddAppello extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAppello()
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
		
		//Passando questo valore come session invece che request permette di evitare di perdere il valore salvato. Altrimenti lo salva come null e causa problemi.
		String idcorso = (String)session.getAttribute("idcorso");
		String data = request.getParameter("data");
		String descrizione = request.getParameter("descrizione");
		
		System.out.println(idcorso);
		
		Connection conn = Connessione.getCon();
		try
		{
			PreparedStatement query = conn.prepareStatement("insert into appello (Data, Materia, Descrizione) values (?,?,?)");
			query.setString(1, data);
			query.setString(2, idcorso);
			query.setString(3, descrizione);
			query.executeUpdate();
			
			RequestDispatcher rd = request.getRequestDispatcher("AddAppello.jsp");
			String messaggio = "L'appello è stato aggiunto con successo!";
			request.setAttribute("messaggio", messaggio);
			rd.forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}