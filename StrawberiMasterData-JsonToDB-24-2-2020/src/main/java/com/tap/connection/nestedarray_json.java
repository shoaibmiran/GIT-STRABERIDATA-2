package com.tap.connection;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;


public class nestedarray_json {
	
	public static void main(String[] args) throws Exception {
		FileReader reader = new FileReader("db.properties");
		Properties p = new Properties();
		p.load(reader);
		
		String item_name = p.getProperty("basic.item_name");
		
		//System.out.println(item_name);
	try {
        JsonFactory jsonfactory = new JsonFactory();
        File source = new File("employees2.json");

        JsonParser parser = jsonfactory.createJsonParser(source);

        // starting parsing of JSON String
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String token = parser.getCurrentName();

            if (item_name.equals(token)) {
                parser.nextToken();  //next token contains value
                String pname = parser.getText();  //getting text field
                System.out.println("package_name : " + pname);

            }

           /* if ("lastname".equals(token)) {
                parser.nextToken();
                String lname = parser.getText();
                System.out.println("lastname : " + lname);

            }
            if ("phone".equals(token)) {
                parser.nextToken();
                int phone = parser.getIntValue();  // getting numeric field
                System.out.println("phone : " + phone);

            }*/

            if ("costing".equals(token)) {
                //System.out.println("costing :");
                parser.nextToken(); // next token will be '[' which means JSON array

                // parse tokens until you find ']'
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                	 String token1 = parser.getCurrentName();
                	 if ("HTL_NM1".equals(token1)) {
                		 parser.nextToken();  //next token contains value
                         String hotelname = parser.getText();  //getting text field
                         System.out.println("hotelname : " + hotelname);
                    //System.out.println(parser.getText());
                	 }
                         if("CATEGORY".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("BASIS".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("HT_TWIN".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("HT_TRIPLE".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("HT_SINGLE".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("CH_W_BED".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("CH_WO_BED".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                         if("HT_EXTRA".equals(token1)){
                        	 parser.nextToken();  //next token contains value
                             String cat = parser.getText();  //getting text field
                             System.out.println("category : " + cat);
                         }
                	 }
                }
            }
        
        parser.close();

    } catch (JsonGenerationException jge) {
        jge.printStackTrace();
    } catch (JsonMappingException jme) {
        jme.printStackTrace();
    } catch (IOException ioex) {
        ioex.printStackTrace();
    }
}
}

