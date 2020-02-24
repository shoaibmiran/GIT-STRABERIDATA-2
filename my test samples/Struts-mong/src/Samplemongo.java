import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.*;

public class Samplemongo {


	  public static void main(String[] args) {

		  try {

				/**** Connect to MongoDB ****/
				// Since 2.10.0, uses MongoClient
				MongoClient mongo = new MongoClient("localhost", 27017);
				
				/**** Get database ****/
				// if database doesn't exists, MongoDB will create it for you
				DB db = mongo.getDB("Anandtest");

				/**** Get collection / table from 'testdb' ****/
				// if collection doesn't exists, MongoDB will create it for you
				DBCollection table = db.getCollection("user");

				/**** Insert ****/
				// create a document to store key and value
				BasicDBObject document = new BasicDBObject();
				document.put("name", "Anand");
				document.put("age", 24);
				document.put("createdDate", new Date());
				table.insert(document);

				/**** Find and display ****/
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put("name", "Anand");

				DBCursor cursor = table.find(searchQuery);

				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}

				/**** Update ****/
				// search document where name="ANand" and update it with new values
				BasicDBObject query = new BasicDBObject();
				query.put("name", "Anand");
				System.out.println("="+query);

				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("name", "Anand-updated");
				System.out.println("#"+newDocument);

				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", newDocument);
				System.out.println("*"+updateObj);
				
				//System.exit(0);

				table.update(query, updateObj);

				/**** Find and display ****/
				BasicDBObject searchQuery2 
				    = new BasicDBObject().append("name", "Anand-updated");

				DBCursor cursor2 = table.find(searchQuery2);

				while (cursor2.hasNext()) {
					System.out.println(cursor2.next());
				}

				
				System.out.println("Done");

			    } catch (MongoException e) {
				e.printStackTrace();
			    }

			  }
			}