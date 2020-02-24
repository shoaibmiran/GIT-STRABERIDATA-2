<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "s" uri = "/struts-tags"%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
      <title>Sample</title>
   </head>
   
   <body>
      <form action = "sf">
      <table>
      <tr>
      <td>Enter your Name</td>
      <td><input type = "text" name = "name"/></td>
     </tr>
      <tr>
      <td>Enter your Address</td>
       <td><input type = "text" name = "address"/></td>
     </tr>
     <tr>
     <td><input type = "submit" value = "Submit" style="position:relative;left:220px;"/></td>
     </tr>
      </table>
      </form>
   </body>
</html>

