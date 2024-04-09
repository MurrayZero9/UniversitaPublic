<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.sql.*"%>

<html>
	<head>
		<title>Registrazione</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
		<!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"-->
	</head>
	
	<body background="./Images/profimg.jpeg">
		<% String messaggio = (String)request.getAttribute("messaggio"); 
		   String materia = (String)request.getAttribute("idcorso"); %>
		
		<div class="container" align="center">
			<form action="Home" method="post" class="inline-element"><input type="submit" class="logbutton" value="Home"></form>
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			
			<%if(messaggio == null) {%>
				<form action="AddAppello" method="post">
					<p>Data: <input type="date" name="data" required></p>
					<p>Descrizione della Lezione: <input type="text" name="descrizione"></p>
					<input type="hidden" name="materia" id="materia" value="<%=materia %>">
					<input type="Submit" class="button" id="aggiungi" value="Aggiungi">
				</form>
			<%} else {%>
				<%=messaggio %>
			<%} %>
		</div>
	</body>
</html>