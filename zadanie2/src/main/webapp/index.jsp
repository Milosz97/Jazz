<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	Don't have an account?<a href="register.jsp">Register Here</a><br>
	<form action="login-servlet" method="post">
		<label>User Name:<input type="text" id="UserName" name="UserName" required /></label><br> 
		<label>Password:<input type="password" id="Password" name="Password" required /></label><br> 
		<label><input type="submit" value="Login" /></label>
	</form>
</body>
</html>