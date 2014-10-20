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
<link rel="stylesheet" type="text/css" href="css/button.css" media="all" />

<title>LMS::Home</title>
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
				What would you like to do? 
			</h1>
		</header>
					
		<div class="form">
			<form id="contactform1" action="applyVacation.jsp" method="post">
				<br> <input class="buttom" name="submit" id="submit"
					value="Apply New Vacation! " type="submit" >
			</form>
		</div>
		<div class="form">
			<form id="contactform2" action="viewVacation.jsp" method="post">
				<br> <input class="buttom" name="submit" id="submit"
					value="Review old Requests!" type="submit" >
			</form>
		</div>
		<div class="form">
			<form id="contactform3" action="approveVacation.jsp" method="post">
				<br> <input class="buttom" name="submit" id="submit"
					value="Approve Pending!      " type="submit" >
			</form>
		</div>
	
		<% if (user.isUserPrivileged()) { %>
		<div class="form">
			<form id="contactform4" action="registration.jsp" method="post">
				<br> <input class="buttom" name="submit" id="submit"
					value="Register New User!   " type="submit" >
			</form>
		</div>
		
		<% } %>
		<div class="nav" align="center">
			<a href="loggedOut.jsp" class="out-button">Sign Out</a>
		</div>
		
	</div>
	
<% } %>
</body>
</html>