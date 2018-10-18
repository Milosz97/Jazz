package servlets;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loan")
public class FormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat f = new DecimalFormat("#0.00");

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String amountStr = request.getParameter("amount");
		String numberOfInstalmentsStr = request.getParameter("numberOfInstalments");
		String interestRateStr = request.getParameter("interestRate");
		String constantDueStr = request.getParameter("constantDue");
		String typeOfLoan = request.getParameter("typeOfLoan");

		if (fieldsEmpty(amountStr, numberOfInstalmentsStr, interestRateStr, constantDueStr, typeOfLoan)) {
			response.sendRedirect("/");
		}
		int amount = Integer.parseInt(amountStr);
		int numberOfInstalments = Integer.parseInt(numberOfInstalmentsStr);
		double interestRate = Double.parseDouble(interestRateStr);
		double constantDue = Double.parseDouble(constantDueStr);

		response.setContentType("text/html");
//		if (typeOfLoan.equals("descType")) {
//			for (int i = 1; i <= numberOfInstalments; i++) {
//			}
//		}
		drawTable(amount, numberOfInstalments, interestRate, constantDue, typeOfLoan, response);

	}

	private boolean fieldsEmpty(String amountStr, String numberOfInstalmentsStr, String interestRateStr,
			String constantDueStr, String typeOfLoan) {
		return amountStr.isEmpty() || numberOfInstalmentsStr.isEmpty() || interestRateStr.isEmpty()
				|| constantDueStr.isEmpty() || typeOfLoan.isEmpty();
	}

	private double calcCapitalPartOfDescRate(int amount, int numberOfInstalments) {
		return amount / numberOfInstalments;
	}

	private double calcInterestPartOfDescRate(double remainingAmount, double interestRate) {
		return remainingAmount * interestRate / 12;
	}

	private double calcCapitalPartOfConstRate(double interestRate, int amount, int numberOfInstalments,
			double interestPart) {
		double qFactor = 1 + (interestRate / 12);
		double pow = Math.pow(qFactor, numberOfInstalments);
		return ((amount * pow * ((qFactor) - 1)) / (pow - 1)) - interestPart;
	}

	private double calcInterestPartOfConstRate(double interestRate, double diff) {
		return diff * (interestRate / 12);
	}

	private void drawRow(int i, double constantDue, HttpServletResponse response, double capitalPart,
			double interestPart, double rateSum) throws IOException {
		response.getWriter()
				.println("<tr><td>" + i + "</td><td>" + f.format(capitalPart) + "</td><td>" + f.format(interestPart)
						+ "</td><td>" + f.format(constantDue) + "</td><td>" + f.format(rateSum) + "</td></tr>");
	}

	private void drawTable(int amount, int numberOfInstalments, double interestRate, double constantDue,
			String typeOfLoan, HttpServletResponse response) throws IOException {
		response.getWriter().println(
				"<table border=1><tr><th>Nr raty</th><th>Kwota Kapita³u</th><th>Kwota Odsetek</th><th>Op³aty sta³e</th><th>Ca³kowita kwota raty</th></tr>");
		double capitalPart = 0;
		double interestPart = 0;
		double rateSum = 0;
		double diff = amount;

		if (typeOfLoan.equals("descType")) {
			capitalPart = calcCapitalPartOfDescRate(amount, numberOfInstalments);
		}

		for (int i = 0; i < numberOfInstalments; i++) {
			if (typeOfLoan.equals("descType")) {
				double remainingAmount = amount - (capitalPart * i);
				interestPart = calcInterestPartOfDescRate(remainingAmount, interestRate);

			} else if (typeOfLoan.equals("constType")) {
				interestPart = calcInterestPartOfConstRate(interestRate, diff);
				capitalPart = calcCapitalPartOfConstRate(interestRate, amount, numberOfInstalments, interestPart);
			}
			rateSum = constantDue + capitalPart + interestPart;
			diff -= rateSum;

			drawRow(i + 1, constantDue, response, capitalPart, interestPart, rateSum);
		}
		response.getWriter().println("</table>");
	}
}
