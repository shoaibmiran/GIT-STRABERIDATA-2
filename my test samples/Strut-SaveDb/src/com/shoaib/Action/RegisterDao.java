package com.shoaib.Action;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class RegisterDao {

	public static int save(RegisterDto r){ 
		
		int status=0;  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shoaib","root","password");  

		PreparedStatement ps=con.prepareStatement("insert into shoaib.register values(?,?,?,?)");  
	    ps.setString(1,r.getName());  
	    ps.setString(2, r.getEmailid());
	    ps.setString(3, r.getMobileno());
		ps.setString(4,r.getPassword());  
		          
		status=ps.executeUpdate();  
		  
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		    return status;  
		    
		}  
	
      public static String check(RegisterDto r){ 
		
		String s="null";  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shoaib","root","password");  

		PreparedStatement ps=con.prepareStatement("select emailid from shoaib.register where emailid=?");
		ps.setString(1, r.getEmailid());
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			 s=rs.getString(1);
		}
	   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		    return s;  
		}  
}
