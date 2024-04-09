<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.sql.*"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
		<!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"-->
	</head>
	
	<body background="./Images/profimg.jpeg">
		<% ResultSet listaStudenti = (ResultSet)request.getAttribute("listaStudenti");
		ResultSet elencoStudenti = (ResultSet)request.getAttribute("elencoStudenti");
		int matricola = (Integer)request.getAttribute("Matricola");
		String idappello = (String)session.getAttribute("idAppello");
		String nomeMateria = (String)request.getAttribute("Materia");
		String Data = (String)request.getAttribute("Data"); 
		String messaggio = (String)request.getAttribute("messaggio");%>
	
		<!-- Container principale -->
		<div class="container" align="center">
			<!-- Header -->
			<form action="Home" method="post" class="inline-element"><input type="submit" class="logbutton" value="Home"></form>
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			<br>&nbsp;
			
			<% if (messaggio != null) {%>
				<br><%=messaggio %>
			<%} %>
			
			<br>&nbsp;<!-- Pagina di aggiunta prenotazione da parte del professore -->
			<% if(listaStudenti != null) {%>
				<table border=1>
					<tr>
					<th>Matricola</th>
					<th>Nome</th>
					<th>Cognome</th>
					</tr>
					<% while(listaStudenti.next()) {%>
						<tr>
						<th><%=listaStudenti.getInt(1)%></th>
						<th><%=listaStudenti.getString("nome")%></th>
						<th><%=listaStudenti.getString("cognome")%></th>
						</tr>
					<%}%>
				</table>
				
				<br>Inserisci la prenotazione della matricola:
				<form action="ModificaPrenotazione" method="post">
					<input type="number" name="matricola">
					<input type="hidden" name="azione" value="1">
				<input type="submit" class="button" id="aggiungi" value="Aggiungi"></form>
			<%} %>
			
			<!-- Pagina di eliminazione della prenotazione -->
			<% if(elencoStudenti != null) {%>
				<table border=1>
					<tr>
					<th>Matricola</th>
					<th>Nome</th>
					<th>Cognome</th>
					</tr>
					<% while(elencoStudenti.next()) {%>
						<tr>
							<th><%=elencoStudenti.getInt(1)%></th>
							<th><%=elencoStudenti.getString("nome")%></th>
							<th><%=elencoStudenti.getString("cognome")%></th>
						</tr>
					<%} %>
				</table>
				
				<br>Inserisci la prenotazione da eliminare:
				<form action="ModificaPrenotazione" method="post">
					<input type="number" name="matricola">
					<input type="hidden" name="azione" value="2">
					<input type="hidden" name="idAppello" value=<%=idappello %>>
				<input type="submit" class="button" id="elimina" value="Elimina"></form>
			<%} %>
		</div>
	</body>
</html>