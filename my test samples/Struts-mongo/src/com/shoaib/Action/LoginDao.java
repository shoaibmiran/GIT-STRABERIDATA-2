package com.shoaib.Action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {
	
	  

	public static String login(RegisterDto dto) {
		String s="null";  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shoaib","root","password");  

		PreparedStatement ps=con.prepareStatement("select name from shoaib.register where emailid=? and password=?");
		ps.setString(1, dto.getEmailid());
		ps.setString(2, dto.getPassword());
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			 s=rs.getString(1);
			 dto.setName(s);
		}
	   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		    return s;  
		}

}
