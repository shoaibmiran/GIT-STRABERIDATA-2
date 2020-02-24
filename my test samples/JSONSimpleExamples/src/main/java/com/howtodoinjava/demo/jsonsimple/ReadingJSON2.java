package com.howtodoinjava.demo.jsonsimple;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ReadingJSON2 {
   public static void main(String args[]) {
      //Creating a JSONParser object
      JSONParser jsonParser = new JSONParser();
      try {
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("employees2.json"));
         System.out.println("S"+jsonObject);
         String id = (String) jsonObject.get("ID");
         String First_Name = (String) jsonObject.get("First_Name");
         String Last_Name = (String) jsonObject.get("Last_Name");
         //Forming URL
         System.out.println("Contents of the JSON are: ");
         System.out.println("ID :"+id);
         System.out.println("continent: "+First_Name);
         System.out.println("package_name: "+Last_Name);
         
         System.out.println(" ");
      } catch (FileNotFoundException e) {
            e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
}