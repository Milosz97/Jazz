<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register new account</title>
</head>
<body>
	If you already have an account you can <a href="index.jsp">login here</a><br>
	<form action="register-servlet" method="post">
		<label>User Name<input type="text" id="UserName" name="UserName" required /></label><br> 
		<label>Password<input type="password" id="Password" name="Password" required /></label><br> 
		<label>Confirm Password<input type="password" id="ConfirmPassword" name="ConfirmPassword" required /></label><br> 
		<label>Email<input type="email" id="Email" name="Email" placeholder="SimpleEmailAddress@gmail.com" required /></label><br>
		<label><input type="submit" value="Register Me" /></label>
	</form>
</body>
</html>