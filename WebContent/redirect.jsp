<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.lms.dto.User"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/reg2.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/reg1.css" media="all" />

<title>LMS::Apply Vacation</title>
</head>
<body>


<% User user = (User) session.getAttribute("user"); %>
<% if (user == null) { 

 	RequestDispatcher req = request.getRequestDispatcher("loggedOut.jsp");
 	req.forward(request, response);

} else { %>

<div class="container">
	<header>
			<h1>
				<span>Welcome to LMS!</span> 
			</h1>
			<% String serverMsg = (String) request.getAttribute("serverMsg"); %>
			<h2><%=serverMsg %> </h2>
			<% session.removeAttribute("serverMsg");%>
		</header>

		<div class="form">
			<form id="contactform1" action="home.jsp" method="post">
				<br> <br> 
				<input class="buttom" name="submit" id="submit"
					value="Go To Home!" type="submit" size="35">
			</form>
		</div>
	</div>
	<% } %>	
</body>
</html>