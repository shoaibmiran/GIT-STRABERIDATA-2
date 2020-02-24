<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "s" uri = "/struts-tags"%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
}

li {
  float: left;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover:not(.active) {
  background-color: #111;
}

.active {
  background-color: #4CAF50;
}
</style>
</head>
<body bgcolor="cyan">
<ul>
<li><a href="home.jsp">Home</a></li>
  <li><a href="login.jsp">Login</a></li>
 
  <li style="float:right"><a class="active" href="#regg">Register</a></li>
</ul>

<center>
<table>
<s:actionerror/>
<s:form action="reg">  
<s:textfield name="dto.name" label="Enter your Name*"></s:textfield>  
<s:textfield name="dto.emailid" label="Enter Email-Id*" type="email"></s:textfield>
<s:password name="dto.password" label="Enter Password*"></s:password> 
<s:password name="dto.cpassword" label="Confirm Password*"></s:password> 
<s:textfield name="dto.mobileno" label="Enter MobileNo"></s:textfield>
<s:submit value="Register" style="position:relative;left:-40px"></s:submit>  
</s:form></table></center>
</body>
</html>