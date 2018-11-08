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

@WebServlet("/register-servlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("UserName");
		String password = request.getParameter("Password");
		String confirmPassword = request.getParameter("ConfirmPassword");
		String email = request.getParameter("Email");
		boolean exist;
		
		exist = checkIfExist(userName);

		if (fieldsEmpty(userName, password, confirmPassword, email)) {
			response.sendRedirect("register.jsp");
		}
		if (!(password.equals(confirmPassword))) {
			response.sendRedirect("register.jsp");
		}
		
		if (exist == true) {
			response.sendRedirect("register.jsp");

		} else {
			User user = new User(userName, password, email, TypeOfAccount.NORMIE);
			UserList.addNewUser(user);
			response.sendRedirect("index.jsp");
		}
	}

	private boolean fieldsEmpty(String userName, String password, String confrimPassword, String email) {
		return userName.equals("") || userName == null || password.equals("") || password == null
				|| confrimPassword.equals("") || confrimPassword == null || email.equals("") || email == null;
	}

	private boolean checkIfExist(String userName) {
		for (User user : UserList.getListOfUsers()) {
			if (user.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
}
