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
				Please select the from and to dates 
			</h1>
		</header>
		<div class="form">
			<form id="contactform" action="home.jsp" method="post">
				<input class="buttom" name="submit" id="submit"
					value="Go to Home!" type="submit">
			</form>
		</div>

		<div class="form">
			 <h1><b>Available unplanned leaves: <%=user.getTotalLeavesUnplanned() %> </b></h1>
			
			<form id="contactform1" action="LeaveRequestHandler" method="post">
			
			<br>
			<p class="contact">
				<label for="fromDate">From Date</label>
			</p>
				<input type="date" id="fromDate" name="fromDate" required="required">
			
			<p class="contact">
				<label for="toDate">To Date</label>
			</p>
				<input type="date" id="toDate" name="toDate" required="required">

			<p class="contact">
				<label for="totalLeaves">Total Vacations in duration</label>
			</p>
				<input type="number" id="totalLeaves" name="totalLeaves" required="required">
				
			<p class="contact">
				<label for="paidLeaves">Total Paid Vacations in duration</label>
			</p>
				<input type="number" id="paidLeaves" name="paidLeaves" required="required">
				
			<p class="contact">
				<label for="unpaidLeaves">Total Un-paid Vacations in duration</label>
			</p>
				<input type="number" id="unpaidLeaves" name="unpaidLeaves" required="required"> 

		<p class="contact">
					<label for="remark">Remarks (if any)</label>
				</p>
				<input id="remark" name="remark" type="text">

			<select
					class="select-style privilege" name="leaveType">
					<option value="PLANNED">Planned</option>
					<option value="UNPLANNED">Un-Planned</option>
			</select>
								
				<br> <br> <br>
				<input class="buttom" name="submit" id="submit"
					value="Apply Vacation!" type="submit" size="35">
			</form>
		</div>
		
	</div>
	<% } %>	
</body>
</html>