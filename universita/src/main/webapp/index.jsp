<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Benvenuto</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
	</head>
	
	<body background="./Images/Uniback2.jpeg">
	
		<div align="center">
		<img src="./Images/uniicon2.png" class="logo"></div>
	
		<!-- Container del Login -->
		<div class="logincontainer" align="center">
			<%
			String messaggio= (String)request.getAttribute("messaggio");
			%>
			<% 
			if(messaggio!=null){%>
				<a style="font-family:helvetica; color:yellow; font-size:20px">
				<%=messaggio%></a>
			<%} %>
			
			<!-- Form per login -->
			<form action="login" method="post">
				<h3>Nome utente <input type="text" name="username"></h3>
				<h3>Password &nbsp;&nbsp;&nbsp;&nbsp;  <input type="password" name="password"></h3>
				<input type="submit" class="logbutton" value="Accedi">
			</form>
			
			<!-- Registrazione -->
			Nuovo utente? <a href="registrazione.jsp">Iscriviti qui!</a>
		</div>
	</body>
</html>