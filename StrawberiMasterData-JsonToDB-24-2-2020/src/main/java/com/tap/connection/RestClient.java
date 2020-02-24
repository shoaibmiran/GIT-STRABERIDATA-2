package com.tap.connection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONException;
import com.tap.model.Basic;


public class RestClient {

	
	public static void main(String args[]) throws JSONException, Exception {
		setBasic_data();
	}
		
	
	private static void setBasic_data() throws JSONException, Exception{
		FileReader reader = new FileReader("db.properties");
		Properties p = new Properties();
		p.load(reader);
		String item_name=p.getProperty("basic.item_name");
		String validity_from=p.getProperty("basic.validity_from");
		String validity_to=p.getProperty("basic.validity_to");
		String number_of_days=p.getProperty("basic.number_of_days");
		String number_of_nights=p.getProperty("basic.number_of_nights");
		String state=p.getProperty("basic.state");
		String country=p.getProperty("basic.country");
		String continent=p.getProperty("basic.continent");
		String currency=p.getProperty("basic.currency");
		String default_selling_price=p.getProperty("basic.default_selling_price");
		String tour_category=p.getProperty("basic.tour_category");
		
		String tour_type=p.getProperty("basic_static.tour_type");
		String isactive=p.getProperty("basic.isactive");
		String created_by=p.getProperty("basic.created_by");
		String created_date=p.getProperty("basic.created_date");
		String description=p.getProperty("basic.description");
		//String season=p.getProperty("basic.season");
		
		String isactive_dummy=null;
		 String date2_validity_up_to=null;
		 String date2_validity_from = null;
		 String creadted_date=null;
		 
		Basic attributes=new Basic();
		int no_of_package = 10;
		try {

			JsonFactory jsonfactory = new JsonFactory();
			File source = new File("Domestic_Packages.txt");

			JsonParser parser = jsonfactory.createJsonParser(source);
			JsonToken current;
			for (int i = 1; i <= no_of_package; i++) {
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				current = parser.nextToken();
				if (current != JsonToken.START_OBJECT) {
					System.out.println("Error: root should be object: quiting.");
					return;
				}

				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String fieldName = parser.getCurrentName();
					current = parser.nextToken();

					if (fieldName.equals(item_name)) {
						attributes.setItem_name(parser.getText());
						System.out.println("Item_name : " + attributes.getItem_name() + "\n");
					}
					else if (fieldName.equals(validity_from)) {
						date2_validity_from=parser.getText();
						System.out.println("Validity-from : " +date2_validity_from + "\n");
					}
					else if (fieldName.equals(validity_to)) {
						date2_validity_up_to=parser.getText();
						System.out.println("validity_to : " + date2_validity_up_to + "\n");
					}
					else if (fieldName.equals(number_of_days)) {
						attributes.setNumber_of_days(parser.getText());
						System.out.println("number_of_days : " + attributes.getNumber_of_days() + "\n");
					}
					else if (fieldName.equals(number_of_nights)) {
						attributes.setNumber_of_nights(parser.getText());
						System.out.println("number_of_nights : " + attributes.getNumber_of_nights() + "\n");
					}
					else if (fieldName.equals(state)) {
						attributes.setState(parser.getText());
						System.out.println("state : " + attributes.getState() + "\n");
					}
					else if (fieldName.equals(country)) {
						attributes.setCountry(parser.getText());
						System.out.println("country : " + attributes.getCountry() + "\n");
					}
					
					
					else if (fieldName.equals(continent)) {
						attributes.setContinent(parser.getText());
						System.out.println("continent : " + attributes.getContinent() + "\n");
					}
					else if (fieldName.equals(currency)) {
						attributes.setCurrency(parser.getText());
						System.out.println("currency : " + attributes.getCurrency() + "\n");
					}
					else if (fieldName.equals(default_selling_price)) {
						attributes.setDefault_selling_price(parser.getText());
						System.out.println("default_selling_price : " + attributes.getDefault_selling_price() + "\n");
					}
					else if (fieldName.equals(tour_category)) {
						attributes.setTour_category(parser.getText());
						System.out.println("tour_category : " + attributes.getTour_category() + "\n");
					}
					else if (fieldName.equals(isactive)) {
						isactive_dummy=parser.getText();
						System.out.println("isactive : " + isactive_dummy + "\n");
					}
					else if (fieldName.equals(created_by)) {
						attributes.setCreated_by(parser.getText());
						System.out.println("created_by : " + attributes.getCreated_by() + "\n");
					}
					
					
					else if (fieldName.equals(created_date)) {
						creadted_date=parser.getText();
						System.out.println("created_date : " +creadted_date + "\n");
					}
					
					else if (fieldName.equals(description)) {
						attributes.setDescription(parser.getText());
						System.out.println("description : " + attributes.getDescription() + "\n");
					}
					
					/*else if (fieldName.equals(season)) {
						attributes.setSeason(parser.getText());
						System.out.println("season : " + attributes.getSeason() + "\n");
					}*/
					else {
						parser.skipChildren();
					}
					
					
				}

				if(isactive_dummy.equalsIgnoreCase("true"))
				{
					attributes.setIsactive("Yes");
					System.out.println("isactive : " + attributes.getIsactive() + "\n");
				}
				else if(isactive_dummy.equalsIgnoreCase("false") || isactive_dummy.equalsIgnoreCase("") )
				{
					attributes.setIsactive("No");
					System.out.println("isactive : " + attributes.getIsactive() + "\n");
				}
				
				
				//date conversion
				String date_from=date2_validity_from.substring(0,10);
				attributes.setValidity_from(date_from);
				System.out.println(date_from);
		       
				String date_upto=date2_validity_up_to.substring(0,10);
				attributes.setValidity_from(date_upto);
				System.out.println(date_upto);
				
				String create=creadted_date.substring(0,10);
				attributes.setCreated_date(create);
				System.out.println(date_from);
				
				//setting static values
				attributes.setTour_type(tour_type);
				System.out.println("tour_type : " + attributes.getTour_type() + "\n");
				
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				
				System.out.println("--------------------------------------------------------INSERTING PACKAGE " + i
						+ " DATA INTO DATABASE IS STARTED--------------------------------------------------------\n");
				
				Insertdata(attributes);
				
				System.out.println("--------------------------------------------------------INSERTING PACKAGE " + i
						+ " DATA INTO DATABASE IS ENDED--------------------------------------------------------\n");
				
			}
		} catch (IOException ie) {
			ie.printStackTrace();

		}
		
	}


	private static void Insertdata(Basic attributes) {
		   Connection conn = null;
		   PreparedStatement stmt=null;
		   //Statement stmt=null;
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitpackagesjson", "root", "tiger");
				
				stmt=conn.prepareStatement("insert into fit_package_catalog_basic values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,)");  
				stmt.setString(1,"");
				stmt.setString(2,"");
				stmt.setString(3,attributes.getItem_name());  
				stmt.setString(4,"");
				stmt.setString(5,attributes.getDescription()); 
				stmt.setString(6,"");
				stmt.setString(7,attributes.getTour_category());  
				stmt.setString(8,attributes.getTour_type());  
				stmt.setString(9,"");
				stmt.setString(10,"");
				stmt.setString(11, attributes.getNumber_of_days());
				stmt.setString(12,attributes.getNumber_of_nights());
				stmt.setString(13, attributes.getValidity_from());
				stmt.setString(14,attributes.getValidity_to());
				stmt.setString(15,"");
				stmt.setString(16,"");
				stmt.setString(17,"");
				stmt.setString(18, attributes.getIsactive());
				stmt.setString(19,attributes.getContinent());
				stmt.setString(20, attributes.getCountry());
				stmt.setString(21,attributes.getState());
				stmt.setString(22,"");
				stmt.setString(23,"");
				stmt.setString(24,"");
				stmt.setString(25,"");
				stmt.setString(26,"");
				stmt.setString(27,"");
				stmt.setString(28,"");
				stmt.setString(29,"");
				stmt.setString(30,"");
				stmt.setString(31,"");
				stmt.setString(32,"");
				stmt.setString(33,"");
				stmt.setString(34,"");
				stmt.setString(35,"");
				stmt.setString(36,"");
				stmt.setString(37,"");
				//stmt.setString(21,attributes.getco());
				 int x=stmt.executeUpdate();  
		     if (x > 0)             
	                System.out.println("Successfully Inserted");             
	            else            
	                System.out.println("Insert Failed"); 
		      
		      
		      /* stmt = conn.createStatement(); 
              
	            // Inserting data in database 
	            String q1 = "insert into fit_package_catalog_basic values('"+""+"','"+""+"','" +attributes.getItem_name()+ "', '" +""+  "', '" +attributes.getDescription()+ "','"+""+"', '" +attributes.getTour_category()+ "','"+attributes.getTour_type()+"','"+""+"','"+""+"','"+attributes.getNumber_of_days()+"','"+attributes.getNumber_of_nights()+"','"+attributes.getValidity_from()+"','"+attributes.getValidity_to()+"')"; 
	            int x = stmt.executeUpdate(q1); 
	            if (x > 0)             
	                System.out.println("Successfully Inserted");             
	            else            
	                System.out.println("Insert Failed"); */

		   }
		   catch(SQLException se){
		      
		      se.printStackTrace();
		   }catch(Exception e){
		     
		      e.printStackTrace();
		   }finally{
		      
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		}
			
	}


