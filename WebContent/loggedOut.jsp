<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Deque" %>    
<%@ page import="com.lms.dto.User"%>
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

<title>LMS::Session Expired</title>
</head>
<body>

<div class="container">
		<header>
			<h1>
				<span>Welcome to LMS!</span>
			</h1>
			<h2>Couldn't Authenticate or Session Expired. Please retry...</h2>
		</header>

		<div class="form">
			<form id="contactform" action="index.jsp" method="post">
				<br> <input class="buttom" name="submit" id="submit"
					value="Go to login!" type="submit">
			</form>
		</div>
	</div>

</body>
</html>