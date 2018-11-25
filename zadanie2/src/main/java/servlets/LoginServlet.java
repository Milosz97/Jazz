package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.UserList;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("UserName");
		String password = request.getParameter("Password");

		if (fieldsEmpty(userName, password)) {
			response.sendRedirect("index.jsp");
		}

		for (User user : UserList.getListOfUsers()) {
			if (user.getUserName().equals(userName)) {
				if (user.getPassword().equals(password)) {
					HttpSession session = request.getSession();
					session.setAttribute("userName", userName);
					session.setAttribute("email", user.getEmail());
					session.setAttribute("typeOfAccount", user.getTypeOfAccount());
					
					response.sendRedirect("user-profile");
				}

			}

		}

		response.sendRedirect("index.jsp");
	}

	private boolean fieldsEmpty(String userName, String password) {
		return userName.equals("") || userName == null || password.equals("") || password == null;
	}

}
