<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="hospitalStyle.css">
</head>
<body>

<%
String msg=(String)request.getAttribute("msg");
%>
<div class="container">
   <h2 style="color:green"><%=msg %></h2>

			<br><br>
			<a href="patientRegister.html">Register New Patients</a><br><br>
			<a href="bookAppointment.html">Book Appointment</a><br><br>
			<a href="veiwDoctor.html">veiw available Doctors</a> <br><br>
			<a href="veiwpatient.html">veiw available patients</a>  
</div>

</body>
</html>