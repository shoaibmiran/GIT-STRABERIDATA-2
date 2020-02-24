<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
<body>

<ul>
<li><a href="home.jsp">Home</a></li>
  <li><a href="index.jsp">Register</a></li>
  <li><a href="login.jsp">Login</a></li>
  <li style="float:right"><a class="active" href="#logout">Logout</a></li>
</ul>
<h1>You are successfully logout.....</h1>
</body>
</html>

