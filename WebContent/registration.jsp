<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.lms.dto.User"%>
<!DOCTYPE html>
<!--  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/reg2.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/reg1.css" media="all" />

<title>LMS::Employee Registration</title>
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
				<span>Welcome to LMS!</span> Please enter the correct employee
				details for registration.
			</h1>
		</header>
			
		<div class="form">
			<form id="contactform" action="home.jsp" method="post">
				<input class="buttom" name="submit" id="submit"
					value="Go to Home!" type="submit">
			</form>
		</div>
		
		<div class="form">
			<form id="contactform" action="RegistrationHandler" method="post">

				<p class="contact">
					<label for="empId">Employee Id</label>
				</p>
				<input id="empId" name="empId" type="text" required="required">

				<p class="contact">
					<label for="name">Name</label>
				</p>
				<input id="name" name="name" placeholder="First and last name"
					type="text" required="required">

				<p class="contact">
					<label for="email">Email</label>
				</p>
				<input id="email" name="email" placeholder="example@fastenal.com"
					type="email" required="required">

				<p class="contact">
					<label for="department">Department</label>
				</p>
				<input id="department" name="department" placeholder="idc_wms"
				 type="text" required="required">

				<p class="contact">
					<label for="manager">Manage Employee Id</label>
				</p>
				<input id="manager" name="manager" type="text" required="required">

				<p class="contact">
					<label for="password">Create a password</label>
				</p>
				<input type="password" id="password" name="password" required="required">

				<p class="contact">
					<label for="birthDate">Date of Birth</label>
				</p>
				<input type="date" id="birthDate" name="birthDate" required="required">

				<p class="contact">
					<label for="joinDate">Date of Joining</label>
				</p>
				<input type="date" id="joinDate" name="joinDate" required="required"> 
				<select
					class="select-style privilege" name="privilege">
					<option value="reg">Regular Employee</option>
					<option value="hrm">HR Manager</option>
					<option value="adm">Admin</option>
				</select><br> <br>

				<p class="contact">
					<label for="leavesAvail">Total Leaves Available</label>
				</p>
				<input id="leavesAvail" name="leavesAvail" type="number" required="required">

				<p class="contact">
					<label for="leavesUnPlan">Total Leaves Unplanned</label>
				</p>
				<input id="leavesUnPlan" name="leavesUnPlan" type="number" required="required">
				
				<p class="contact">
					<label for="contactNumber">Contact Number</label>
				</p>
				<input id="contactNumber" name="contactNumber" type="text">
				
				<br> <input class="buttom" name="submit" id="submit"
					value="Sign me up!" type="submit">
			</form>
		</div>
	</div>
	<%
		}
	%>
</body>
</html>