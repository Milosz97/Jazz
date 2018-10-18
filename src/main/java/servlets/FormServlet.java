package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

@WebServlet("/loan")
public class FormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat f = new DecimalFormat("#0.00");
	private StringBuilder sb;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.sb = new StringBuilder();

		String amountStr = request.getParameter("amount");
		String numberOfInstalmentsStr = request.getParameter("numberOfInstalments");
		String interestRateStr = request.getParameter("interestRate");
		String constantDueStr = request.getParameter("constantDue");
		String typeOfLoan = request.getParameter("typeOfLoan");
		String generatePdf = request.getParameter("generatePdf");

		if (fieldsEmpty(amountStr, numberOfInstalmentsStr, interestRateStr, constantDueStr, typeOfLoan)) {
			response.sendRedirect("/");
		}

		int amount = Integer.parseInt(amountStr);
		int numberOfInstalments = Integer.parseInt(numberOfInstalmentsStr);
		double interestRate = Double.parseDouble(interestRateStr);
		double constantDue = Double.parseDouble(constantDueStr);

		response.setContentType("text/html");

		drawTable(amount, numberOfInstalments, interestRate, constantDue, typeOfLoan, response);

		if (generatePdf == null) {
			response.getWriter().println(sb.toString());
		} else {
			this.generatePDF(response);
		}
	}

	private void generatePDF(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");

		try {
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter.getInstance(document, response.getOutputStream());
			// step 3
			document.open();
			// step 4

			CSSResolver cssResolver = new StyleAttrCSSResolver();

			// HTML
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

			// Pipelines
			ElementList elements = new ElementList();
			ElementHandlerPipeline pdf = new ElementHandlerPipeline(elements, null);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

			// XML Worker
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser p = new XMLParser(worker);
			p.parse(new ByteArrayInputStream(this.sb.toString().getBytes()));
			PdfPTable pdfTable = (PdfPTable) elements.get(0);

			document.add(pdfTable);

			// step 5
			document.close();
		} catch (DocumentException de) {
			throw new IOException(de.getMessage());
		}
	}

	private boolean fieldsEmpty(String amountStr, String numberOfInstalmentsStr, String interestRateStr,
			String constantDueStr, String typeOfLoan) {
		return amountStr.equals("") || amountStr == null || numberOfInstalmentsStr.equals("")
				|| numberOfInstalmentsStr == null || interestRateStr.equals("") || interestRateStr == null
				|| constantDueStr.equals("") || constantDueStr == null || typeOfLoan.equals("") || typeOfLoan == null;
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
		this.sb.append("<tr><td>").append(i).append("</td><td>").append(f.format(capitalPart)).append("</td><td>")
				.append(f.format(interestPart)).append("</td><td>").append(f.format(constantDue)).append("</td><td>")
				.append(f.format(rateSum)).append("</td></tr>");
	}

	private void drawTable(int amount, int numberOfInstalments, double interestRate, double constantDue,
			String typeOfLoan, HttpServletResponse response) throws IOException {
		this.sb.append(
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
		this.sb.append("</table>");
	}

}
