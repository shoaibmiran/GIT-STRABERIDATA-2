
import java.sql.*;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class RegisterDao {
public static void main(String[] args) {
	
	
	 

     // Creating Credentials 
   //  MongoCredential credential; 
    // credential = MongoCredential.createCredential("sampleUser", "shoaib", 
      //  "password".toCharArray()); 
    // System.out.println("Connected to the database successfully");  
     
     // Accessing the database 

     // Retrieving a collection
    
	
		Connection con;
		try {
			Class.forName("mongodb.jdbc.MongoDriver"); 
			//MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
			//MongoCredential credential; 
			
			 con=DriverManager.getConnection("jdbc:mongo://localhost:27017/shoaib","sampleUser","password");  

		     // credential = MongoCredential.createCredential("sampleUser", "shoaib", 
		      //   "password".toCharArray()); 
		    //  System.out.println("Connected to the database successfully");  
		     
			
			//MongoDatabase database = mongo.getDatabase("shoaib");
			// MongoCollection<Document> collection = database.getCollection("reegister"); 
		     //System.out.println("Collection sampleCollection selected successfully");
		
			PreparedStatement ps=con.prepareStatement("shoaib.reegister.insert(2,'sff','jjj',996,'sdf')");  
			  ps.execute();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		
		}
		
	
     
}
