<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/reg2.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/reg1.css" media="all" />

<title>LMS::Employee Login</title>
</head>
<body>
<% session.removeAttribute("user");%>
<% session.removeAttribute("serverMsg");%>
	<div class="container">
		<header>
			<h1>
				<span>Welcome to LMS!</span>Please enter your credential to login
			</h1>
		</header>

		<div class="form">
			<form action="LoginHandler" method="post">

				<p class="contact">
					<label for="empId">Employee Id</label>
				</p>
				<input id="empId" name="empId" type="text">

				<p class="contact">
					<label for="password">Password</label>
				</p>
				<input type="password" id="password" name="password"> <br>
				<input class="buttom" name="submit" id="submit" value="Log in!"
					type="submit">
			</form>
		</div>
	</div>
</body>
</html>