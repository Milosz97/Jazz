package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TypeOfAccount;
import model.User;
import model.UserList;

@WebServlet("/premium-panel")
public class PremiumPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuilder sb;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("UserName");
		
		if (!(userName.equals("") || userName == null)) {
			for (User user : UserList.getListOfUsers()) {
				if (user.getUserName().equals(userName)) {
					if (user.getTypeOfAccount().equals(TypeOfAccount.NORMIE)) {
						user.setTypeOfAccount(TypeOfAccount.PREMIUM);
					} else {
						user.setTypeOfAccount(TypeOfAccount.NORMIE);
					}
				}
			}
		}
		response.sendRedirect("/premium-panel");
	}
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			this.sb = new StringBuilder();
			response.setContentType("text/html");
		
		this.sb.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<title>Premium Control Panel</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" );
		this.sb.append("<a href=\"user-profile\">My profile</a>");
		this.sb.append("<br><table border=1>\r\n" 
				+ "    "
				+ "<caption>List Of Users</caption>\r\n" 
				+ "    <tr>\r\n"
				+ "        <th>User Name</th>\r\n" 
				+ "        <th>Email</th>\r\n"
				+ "        <th>Type Of Account</th>\r\n" 
				+ "    </tr>");

		for (User user : UserList.getListOfUsers()) {
			this.sb.append("<tr><td>")
			.append(user.getUserName())
			.append("</td><td>")
			.append(user.getEmail())
			.append("</td><td>")
			.append(user.getTypeOfAccount())
			.append("</td></tr>");
		}
		this.sb.append("</table>");
		
		this.sb.append("<form action=\"premium-panel\" method=\"post\">\r\n" + 
				"<label>Grant/Revoke Premium from : <input type=\"text\" id=\"UserName\" name=\"UserName\" placeholder=\"User Name\" required /></label><br>\r\n" + 
				"<input type=\"submit\" value=\"Confirm\">\r\n" + 
				"</form>\r\n" + 
				"</body>\r\n" + 
				"</html>");
		response.getWriter().println(sb.toString());
	}

}
