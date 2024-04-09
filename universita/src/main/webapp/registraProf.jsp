<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.sql.*"%>

<html>
	<head>
		<title>Primo Accesso</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
		<!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"-->
	</head>
	
	<body background="./Images/profimg.jpeg">
		<% String nome = (String)session.getAttribute("nome");
		String cognome = (String)session.getAttribute("cognome");
		int idProfessore = (Integer)session.getAttribute("idProfessore");
		String messaggio = (String)request.getAttribute("messaggio");
		%>
		
		<div class="container" align="center">
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			<h1 class="inline-element">Benvenuto <%=nome%> <%=cognome%></h1>
			<br>&nbsp;
			
			<%if(messaggio == null) {%>
				<form action="RegistraMateria" method="post">
					<p>Materia: <input type="text" name="nomemateria" required></p>
					<input type="hidden" name="idProfessore" value="<%=idProfessore %>">
					<input type="Submit" class="button" id="aggiungi" value="Registra">
				</form>
			<%} else {%>
				<%=messaggio %>
				<form action="Home" method="post"><input type="Hidden" name="idProfessore" value=<%=idProfessore %>><input type="Submit" class="logbutton" value="HOME"></form>
				<!-- a href="logout.jsp">Torna alla schermata di login</a-->
			<%} %>
		</div>
	</body>
</html>