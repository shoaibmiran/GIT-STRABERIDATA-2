package com.shoaib.Action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class LoginDao {
	
	static DBObject s=null; 
	static DBObject s1=null; 

	public static DBObject login(RegisterDto dto) {
		//String s="null";  
		/*try{  
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
	   
		}*/
		
		
		
	     MongoClient mongo = new MongoClient("localhost", 27017);
			
			DB db = mongo.getDB("shoaib");

			DBCollection table = db.getCollection("lreg");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("emailid", dto.getEmailid());
			searchQuery.put("password", dto.getPassword());

			DBCursor cursor = table.find(searchQuery);
			
			while (cursor.hasNext()) {
				//System.out.println(cursor.next());
				s=cursor.next();
				String n=(String) s.get("name");
				dto.setName(n);
				//dto.setName(s);
				//System.out.println(s);
			}
             s1=s;
              s=null;
			    return s1;  
			}  
	
}
