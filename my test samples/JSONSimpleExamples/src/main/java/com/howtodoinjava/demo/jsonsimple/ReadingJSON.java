package com.howtodoinjava.demo.jsonsimple;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ReadingJSON {
   public static void main(String args[]) {
      //Creating a JSONParser object
      JSONParser jsonParser = new JSONParser();
      try {
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("employees.json"));
         String id = (String) jsonObject.get("_id");
         String continent = (String) jsonObject.get("continent");
         String package_name = (String) jsonObject.get("package_name");
         Long days = (Long) jsonObject.get("days");
         Long nights = (Long) jsonObject.get("nights");
         String country = (String) jsonObject.get("country");
         //Forming URL
         System.out.println("Contents of the JSON are: ");
         System.out.println("ID :"+id);
         System.out.println("continent: "+continent);
         System.out.println("package_name: "+package_name);
         System.out.println("days: "+days);
         System.out.println("nights: "+nights);
         System.out.println("Country: "+country);
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