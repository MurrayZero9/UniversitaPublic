<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.sql.*"%>

<html>
	<head>
		<title>Registrazione</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
	</head>
	
	<body background="./Images/Uniback2.jpeg">
		<% String messaggio = (String)request.getAttribute("messaggio"); %>
		
		<div class="container" align="center">
			<div align="center">
			<img src="./Images/iconautente.png" class="logo"></div>
			
			<%if(messaggio == null) {%>
				<form action="Registra" method="post">
					<p>Nome Utente: <input type="text" name="username"></p>
					<p>Password: <input type="password" name="password"></p>
					<p>Nome: <input type="text" name="nome"></p>
					<p>Cognome: <input type="text" name="cognome"></p>
					<p>Sesso: <input type="radio" id="M" name="sesso" value="M">Maschio <input type="radio" id="F" name="sesso" value="F">Femmina</p>
					<p>Sei: <input type="radio" id="s" name="tipo_utente" value="s">Studente <input type="radio" id="p" name="tipo_utente" value="p">Professore</p>
					<input type="Submit" class="button" id="aggiungi" value="Registra">
				</form>
			<%} else {%>
				<%=messaggio %>
				<a href="index.jsp">Torna alla schermata di Login</a>
			<%} %>
		</div>
	</body>
</html>