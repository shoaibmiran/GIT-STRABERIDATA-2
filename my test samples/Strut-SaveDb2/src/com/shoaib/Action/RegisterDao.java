package com.shoaib.Action;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;



public class RegisterDao {

	public static WriteResult save(RegisterDto r){ 
		  WriteResult s=null;
		  WriteResult s1=null;
		MongoClient mongo = new MongoClient("localhost", 27017);
		
		DB db = mongo.getDB("shoaib");

		DBCollection table = db.getCollection("lreg");

		BasicDBObject document = new BasicDBObject();
		document.put("name",r.getName());
		document.put("mobileno", r.getAge());
		document.put("emailid", r.getEmailid());
		document.put("password", r.getPassword());
		
		s=table.insert(document); 
		s1=s;
		s=null;
		    return s;
		    
		}  
	
     public static DBObject check(RegisterDto r){ 
	
		DBObject s=null;  
		DBObject s1=null;  
	
     MongoClient mongo = new MongoClient("localhost", 27017);
		
		DB db = mongo.getDB("shoaib");

		DBCollection table = db.getCollection("lreg");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("emailid", r.getEmailid());

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			//System.out.println(cursor.next());
			s=cursor.next();
			//System.out.println(s);
		}
		s1=s;
		s=null;
		    return s;  
		}  
}
