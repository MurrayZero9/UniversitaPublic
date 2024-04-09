<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Area Studenti</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
	</head>
	
	<body background="./Images/studimg.jpeg">
		<%String matricola = (String)session.getAttribute("matricola");
		String nome = (String)session.getAttribute("nome");
		String cognome = (String)session.getAttribute("cognome");
		String sesso = (String)session.getAttribute("sesso");
		ResultSet res = (ResultSet) request.getAttribute("tabella_corso");
		ResultSet res1 = (ResultSet) request.getAttribute("elenco_appelli");
		String materia = (String) session.getAttribute("materia");
		String messaggio = (String) request.getAttribute("messaggio");
		String data = (String) request.getAttribute("data");
		String materia2 = (String) request.getAttribute("materia2");
		%>
		
		
		<% if(matricola==null)
		{
			response.sendRedirect("index.jsp");
		} %>
		
		<!-- Container della pagina -->
		<div class="container" align="center">
			<!-- Header -->
			<form action="Home" method="post" class="inline-element"><input type="submit" class="logbutton" value="Home"></form>
			<h1 class="inline-element"><% if(sesso.equals("M")) {%>Benvenuto <%;} else {%>Benvenuta <%;}%> <%=nome %> <%=cognome %></h1>
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			
			<div align="center">
			<img src="./Images/iconautente.png" class="logo"></div>
			
			<!-- Pagina principale, contenente la lista di Corsi -->
			<% if(res != null) {%>
				<table border=1>
					<tr>
						<th>ID Corso</th>
						<th>Materia</th>
						<th>Nome Docente</th>
						<th>Cognome Docente</th>
					</tr>
					<% while(res.next()) { %>
						<tr>
							<th><%=res.getInt("idcorso") %></th>
							<th><%=res.getString("materia") %></th>
							<th><%=res.getString("nome") %></th>
							<th><%=res.getString("cognome") %></th>
						</tr>
					<%} %>
				</table>
				
				<!-- Form per prenotare lezioni del corso -->
				<br><form action="Prenotazione" method="post">
					Inserisci la prenotazione che vuoi effettuare:
					<input type="number" name="materia">
					<input type="submit" class="button" value="Prenota">
				</form>
			<%} %>
			
			<!-- Lista di lezioni (o appelli) -->
			<% if(res1!=null) {%>
				<p>Per l'esame di <%=materia%> sono disponibili i seguenti appelli:</p>
				<table border=1>
					<tr>
						<th>ID Appello</th>
						<th>Data</th>
						<th>Descrizione</th>
					</tr>
					<%while(res1.next()) {%>
						<tr>
							<th><%=res1.getInt(1)%></th>
							<th><%=res1.getDate("Data") %></th>
							<th><%=res1.getString("Descrizione") %></th>
						</tr>
					<%} %>
				</table>
				
				<!-- Form per prenotare la lezione -->
				<br><form action="Prenota" method="post">
					Inserisci la prenotazione che vuoi effettuare:
					<input type="number" name="appello">
					<input type="submit" class="button" value="Prenota">
				</form>
				<%} %>
				
				<br><!-- Vari messaggi di Errore: il primo stampa un messaggio con la materia -->
				<%if(messaggio != null && materia != null) { %>
					<p><%=messaggio %><%=materia %></p>
				<%} else {%>
					<!-- Messaggio di errore generico -->
					<%if(messaggio != null) {%>
						<%=messaggio %>
					<%} %>
				<%} %>
				
				<!-- Messaggio di prenotazione effettuato con successo -->
				<%if(materia2 != null && data != null){ %>
				<p> Prenotazione effettuata con successo in data <%=data %> per il corso <%=materia2 %></p>
			<%} %>
		</div>
	</body>
</html>