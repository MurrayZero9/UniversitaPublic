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
@WebServlet("/login")
public class login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login()
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
		String username = request.getParameter("username");  //abbiamo preso username e password
		String password = request.getParameter("password");
		
		Connection conn = Connessione.getCon(); //abbiamo stabilito una connessione ad db
		try
		{     
			Statement smt = conn.createStatement();   //ci siamo presi le user e password dalla tabella studente 
			ResultSet rs = smt.executeQuery("select username,password from studente");
			HttpSession session;
			
			//Login Studenti
			while(rs.next())
			{
                //abbiamo verificato con un check se la user e la password corridspondono a quelle presenti nel db
				if (rs.getString("username").equalsIgnoreCase(username) && rs.getString("password").equalsIgnoreCase(password))
				{
					PreparedStatement smt1 = conn.prepareStatement("select matricola,nome,cognome,sesso from studente where username=?");
					smt1.setString(1, username);  //abbiamo chiesto la stringa relativa all' username e password
					ResultSet rs1 = smt1.executeQuery(); //restituisce la collection contenente la matricola
					rs1.next(); //seleziono il record contenente la matricola 
					String matricola = rs1.getString("matricola");// prendo la matricola e l'assegno a int matricola
					String nome = rs1.getString("nome");
					String cognome = rs1.getString("cognome");
					String sesso = rs1.getString("sesso");
					
					Statement smt2 = conn.createStatement();
					ResultSet rs2 = smt2.executeQuery("select idcorso,materia,nome,cognome from corso join professore on cattedra = idprofessore");
					
					session = request.getSession(true); //se la sessione esiste(esiste l'oggetto session) altrimenti ti crea un oggetto di tipo HttpSession
					session.setAttribute("matricola", matricola);
					session.setAttribute("nome", nome);
					session.setAttribute("cognome", cognome);
					session.setAttribute("sesso", sesso);
					RequestDispatcher rd = request.getRequestDispatcher("studente.jsp"); //con resultset abbiamo preso la tabella dei corsi disponibili
                    request.setAttribute("tabella_corso", rs2);
                    rd.forward(request,response);
                    return;
				}
			}
			
			Statement smt3 = conn.createStatement();
			ResultSet rs3 = smt3.executeQuery("select username,password from professore");
			
			//Login Professori
			while(rs3.next())
			{
				if(rs3.getString("username").equalsIgnoreCase(username) && rs3.getString("password").equalsIgnoreCase(password))
				{
					session = request.getSession(true);
					
					//verifichiamo il nome del professore alla username e password inserita
					PreparedStatement smt4 = conn.prepareStatement("select nome,cognome,idProfessore from professore where username=?");
					smt4.setString(1, username);
					ResultSet rs4 = smt4.executeQuery();
					rs4.next();
					String nome = rs4.getString("nome");
					String cognome = rs4.getString("cognome");
					int idProfessore = rs4.getInt("idProfessore");
					
					//Prendiamo la materia del professore che sta effettuando il login
					PreparedStatement smt5 = conn.prepareStatement("select idcorso,materia from corso where cattedra=?");
					smt5.setInt(1, idProfessore);
					ResultSet rs5 = smt5.executeQuery();
					
					//Controlla se al professore è stata assegnata una Materia.
					//Se viene Assegnato, fai un login normale.
					if(rs5.isBeforeFirst())
					{
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
						rd4.forward(request, response);
					}
					else //Altrimenti, reindirizza nella pagina di completamento della registrazione, dove potrà scegliere la sua materia.
					{
						RequestDispatcher rd4 = request.getRequestDispatcher("registraProf.jsp");
						session.setAttribute("nome", nome);
						session.setAttribute("cognome", cognome);
						session.setAttribute("idProfessore", idProfessore);
						rd4.forward(request, response);
					}
					return;
				}
			}
			
			RequestDispatcher rd3 = request.getRequestDispatcher("index.jsp");
			String messaggio = "username e password non sono presenti";
			request.setAttribute("messaggio", messaggio);
			rd3.forward(request, response);
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
