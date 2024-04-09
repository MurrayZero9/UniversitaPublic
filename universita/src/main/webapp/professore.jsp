<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<% String nome = (String)session.getAttribute("nome");
		String cognome = (String)session.getAttribute("cognome");
		String materia = (String)session.getAttribute("materia");
		ResultSet appelli = (ResultSet)request.getAttribute("appelli");
		ResultSet elenco = (ResultSet)request.getAttribute("elenco_studenti");
		String nomeMateria = (String)request.getAttribute("Materia");
		String Data = (String)request.getAttribute("Data");
		int idcorso = (Integer)session.getAttribute("idcorso");
		String messaggio = (String)request.getAttribute("messaggio");
		%>
		
		<!-- Torna all'index se non sono stati ricevuti il nome e cognome -->
		<% if(nome==null && cognome==null)
		{
			response.sendRedirect("index.jsp");
		}
		%>
		
		<!-- Container principale della pagina -->
		<div class="container" align="center">
			<!-- Header -->
			<form action="Home" method="post" class="inline-element"><input type="submit" class="logbutton" value="Home"></form>
			<h1 class="inline-element">Bentornato <span class="parola-colorata"><%=nome%> <%=cognome%></span></h1>
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			
			<div align="center">
			<img src="./Images/iconautente.png" class="logo"></div>
			
			<!-- Stampa messaggio generico, solitamente di errore -->
			<% if(messaggio != null) {%>
				<br><%=messaggio %>
			<%} %>

			<!-- Pagina di gestione appelli, questa è la pagina di default -->
			<% if(appelli != null)
			{ %>
				<p> Per la sua materia (<%=materia %>) sono disponibili i seguenti appelli: </p>
				<table border=1>
					<tr>
					<th>ID Appello</th>
					<th>Data</th>
					<th>Descrizione</th>
					</tr>
					<% while(appelli.next())
					{ %>
						<tr>
						<th><%=appelli.getInt(1)%></th>
						<th><%=appelli.getDate("Data")%></th>
						<th><%=appelli.getString("Descrizione") %></th>
						</tr>
					<%}%>
				</table>
				
				<!-- Pulsanti per operare gli appelli, che sono Aggiungi, Modifica e Elimina -->
				<br>Operazioni su Appelli:
				<table>
					<tr>
						<td><form action="ProfMateria" method="post"><input type="hidden" name="idcorso" id="idcorso" value="<%=idcorso%>"><input type="hidden" name="azione" id="azione" value="1"><input type="Submit" id="aggiungi" value="Aggiungi"></form>
						<td><form action="ProfMateria" method="post"><input type="hidden" name="idcorso" id="idcorso" value="<%=idcorso%>"><input type="hidden" name="azione" id="azione" value="2"><input type="Submit" id="modifica" value="Modifica"></form>
						<td><form action="ProfMateria" method="post"><input type="hidden" name="idcorso" id="idcorso" value="<%=idcorso%>"><input type="hidden" name="azione" id="azione" value="3"><input type="Submit" id="elimina" value="Elimina"></form>
					</tr>
				</table>
				
				<!-- Form per vedere o modificare le prenotazioni di un appello -->
				<form action="StampaStudenti" method="post">
					<p>Oppure se vuoi modificare le prenotazioni effettuate di un appello:
					<br><input type="number" name="idAppello"> <input type="hidden" name="azione"> <input type="hidden" name="idcorso" value="<%=idcorso %>"> <input type="submit" class="button" value="Vai"></p>
				</form>
				
				<!-- TODO: Premendo questo pulsante ti mostra la tabella di appelli archiviati, cioè appelli che sono oltre la data attuale -->
				<!--button>Archivio Appelli</button-->
			<%} %>
				
			<!-- Pagina di gestione prenotazioni -->
			<% if(elenco != null)
			{ %>	
				<p>Per l'esame <%=nomeMateria %> in data <%=Data %> si sono prenotati i seguenti studenti: </p>
				<table border=1>
					<tr>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Matricola</th>
					</tr>
					<% while(elenco.next())
					{ %>
						<tr>
							<th><%=elenco.getString("nome")%></th>
							<th><%=elenco.getString("cognome")%></th>
							<th><%=elenco.getString("Matricola")%></th>
						</tr>
					<% }%>
				</table>
				
				<!-- Pulsanti per operare le prenotazioni, che sono Aggiungi e Elimina -->
				<br>Operazioni su Prenotazioni:
				<table>
					<tr>
						<td><form action="StampaStudenti" method="post"><input type="hidden" name="idAppello" id="idcorso" value="<%=idcorso%>"><input type="hidden" name="azione" id="azione" value="1"><input type="Submit" id="aggiungi" value="Aggiungi"></form>
						<td><form action="StampaStudenti" method="post"><input type="hidden" name="idcorso" id="idcorso" value="<%=idcorso%>"><input type="hidden" name="azione" id="azione" value="2"><input type="Submit" id="elimina" value="Elimina"></form>
					</tr>
				</table>
			<%} %>
		</div>
	</body>
</html>