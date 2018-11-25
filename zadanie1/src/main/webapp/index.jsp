<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pożyczka</title>
</head>
<body>
<form action="loan" method="post" id="form">
<label>Wnioskowana kwota kredytu: <input type="number" id="amount" name="amount" required/></label><br>
<label>Ilość rat: <input type="number" id="numberOfInstalments" name="numberOfInstalments" required/></label><br>
<label>Oprocentowanie: <input type="number" step="0.001" id="interestRate" name="interestRate" required/></label><br>
<label>Opłata stała: <input type="number" id="constantDue" name="constantDue" required/></label><br>
Rodzaj rat: 
<label>malejąca<input type="radio" id="typeOfLoan" name="typeOfLoan" value="descType" required/></label>
<label>stała<input type="radio" id="typeOfLoan" name="typeOfLoan" value="constType" required/></label><br>
<label>Generuj PDF<input type="checkbox" id="generatePdf" name="generatePdf" value="generate"/></label><br>
<input type="submit" value="wylicz"/>
</form>
</body>
</html>