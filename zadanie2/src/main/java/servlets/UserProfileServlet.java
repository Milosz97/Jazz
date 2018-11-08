package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TypeOfAccount;


@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StringBuilder sb;
 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		this.sb = new StringBuilder();
		
		String userName = (String)session.getAttribute("userName");
		String emial = (String)session.getAttribute("email");
		TypeOfAccount typeOfAccount = (TypeOfAccount)session.getAttribute("typeOfAccount");
		
		response.setContentType("text/html");

		this.sb.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<title>My Profile Page</title>\r\n" + 
				"</head>\r\n" + 
				"<body>")
				.append("Welcome ")
				.append(userName)
				.append("<br>").append("Your email address is : ")
				.append(emial).append("<br>")
				.append("Your account type is : ")
				.append(typeOfAccount.toString());
		
		if(typeOfAccount.equals(TypeOfAccount.PREMIUM) || typeOfAccount.equals(TypeOfAccount.ADMIN)) {
			this.sb.append("<br><a href=\"premium.jsp\">Premium Page</a>");
		}
		if(typeOfAccount.equals(TypeOfAccount.ADMIN)) {
			this.sb.append("<br><a href=\"premium-panel\">Premium Configuration Panel Page</a>");
		}
		this.sb.append("<br><a href=\"logout\">Logout</a>");
		
		response.getWriter().println(sb.toString());

		
	}

}
