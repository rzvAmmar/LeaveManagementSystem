<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Deque" %>    
<%@ page import="com.lms.dto.User" %>
<%@ page import="com.lms.dto.LeaveRecord" %>
<%@ page import="com.lms.dto.DAL.LeavesRequest"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/reg2.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/reg1.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/table.css" media="all" />

<title>LMS::View Vacation</title>
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
				Please find your current applied vacations 
			</h1>
		</header>
		<div class="form">
			<form id="contactform" action="home.jsp" method="post">
				<input class="buttom" name="submit" id="submit"
					value="Go to Home!" type="submit">
			</form>
		</div>

		<form method ="post" action="LeaveCancelHandler">
		<table class="bordered">
			<thead>
			<tr>
				<th>From Date</th>
				<th>To Date</th>
				<th>Total Leaves </th>
				<th>Paid Leaves</th>
				<th>LOP</th>
				<th>Vacation Type</th>
				<th>Vacation Status</th>

				<th>Action</th>
			</tr>
			</thead>

			<%
				Deque<LeaveRecord> recs = user.getLeavesRequests();
				session.setAttribute("leaveRecords", recs);
				for (LeaveRecord rec : recs) {
			%>

			<tr>
				<td><%=rec.getFromDate()%></td>
				<td><%=rec.getToDate()%></td>
				<td><%=rec.getTotalLeavesInDuration() %></td>
				<td><%=rec.getPaidLeavesInDuration()%></td>
				<td><%=rec.getUnpaidLeavesInDuration()%></td>
				<td><%=rec.getLeaveType()%></td>
				<td><%=rec.getStatus()%></td>
				<td> 
					<% if (rec.getStatus().equals("PENDING")) { %>
					<select
						class="select-style privilege" name=<%="action-" + rec.getLeaveId()%>>
						<option value="select">SELECT</option>
						<option value="cancel">CANCEL</option>
					</select>
					<% } %>				
				</td>
			</tr>
			<%
				}
			%>

		</table>
		<br><br>
		<div class="form">
				<input class="buttom" name="submit" id="submit"
					value="Update Status!" type="submit">	
		</div>
		</form>

	</div>
	<% } %>	
</body>
</html>