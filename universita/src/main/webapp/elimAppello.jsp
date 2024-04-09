<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.sql.*"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Elimina Appelli</title>
		<link rel="stylesheet" href="./CSS/style.css" type="text/css">
		<!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"-->
	</head>
	
	<body background="./Images/profimg.jpeg">
		<% ResultSet appelli = (ResultSet)request.getAttribute("appelli"); 
		String messaggio = (String)request.getAttribute("messaggio"); %>
		
		<div class="container" align="center">
			<form action="Home" method="post" class="inline-element"><input type="submit" class="logbutton" value="Home"></form>
			<a href="logout.jsp"><input type="button" class="logbutton" value="Logout"></a>
			<br>&nbsp;
			
			<% if(messaggio != null) {%> 
				<%=messaggio%> 
			<%} %>
			
			<% if(appelli!=null) { %>
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
							<th><%=appelli.getString("Descrizione")%></th>
						</tr>
					<%}%>
				</table>
				
				<form action="EliminaAppello" method="post">
					<h3>ATTENZIONE: Una volta eliminato l'appello non è più recuperabile!</h3>
					Appello da eliminare: <input type="number" name="idappello"> 
				<input type="submit" class="button" id="elimina" value="Elimina"></form>
			<%} %>
		</div>
	</body>
</html>