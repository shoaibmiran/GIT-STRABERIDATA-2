//package com.howtodoinjava.demo.jsonsimple;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.codehaus.jackson.JsonFactory;
//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.JsonToken;
//import java.io.*;
//
//import org.codehaus.jackson.*;
//import org.codehaus.jackson.map.MappingJsonFactory;
//import org.json.simple.JSONArray;
//
//public class sampleexamplejson {
//
//
//		    public static void main(String[] args) {
//
//		        try{
//
//					JsonFactory jsonfactory = new JsonFactory();
//					File source = new File("employees2.json");
//
//					JsonParser parser = jsonfactory.createJsonParser(source);
//		                JsonToken current;
//		                for(int i=0;i<3;i++)
//		                {
//		                current = parser.nextToken();
//		                if (current != JsonToken.START_OBJECT) {
//		                  System.out.println("Error: root should be object: quiting.");
//		                  return;
//		                }
//
//		                while (parser.nextToken() != JsonToken.END_OBJECT) {
//		                  String fieldName = parser.getCurrentName();
//		                  // move from field name to field value
//		                  current = parser.nextToken();
//		                  if (fieldName.equals("_id")) {
//		                 	 //parser.nextToken();
//		   					String _id = parser.getText();
//		   					System.out.println("_id : " + _id);
//		                   }
//		                  else if (fieldName.equals("continent")) { 
//		                	 //parser.nextToken();
//		  					String continent = parser.getText();
//		  					System.out.println("continent : " + continent);
//		                  }
//		                 else if (fieldName.equals("package_name")) {
//		                	 // parser.nextToken();
//		  					String package_name = parser.getText();
//		  					System.out.println("package_name : " + package_name);
//		                  }
//		                 else   if (fieldName.equals("costing")) {
//		                    if (current == JsonToken.START_ARRAY) {
//		                      // For each of the records in the array
//		                      while (parser.nextToken() != JsonToken.END_ARRAY) {
//		                        
//		                    	  parser.nextToken();
//		      					String fieldName1 = parser.getCurrentName();
//		      					/* if (fieldName1.equals("HT_TRIPLE")) {
//		      	                 	 parser.nextToken();
//		      	   					String  HTL_NM1 = parser.getText();
//		      	   					System.out.println(" HTL_NM1 : " +  HTL_NM1);
//		      	                   }*/
//		      			          
//		      			       
//		      			        Iterator itr2 = ((List) parser).iterator(); 
//		      			          
//		      			        while (itr2.hasNext())  
//		      			        { 
////		      			            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
////		      			            while (itr1.hasNext()) { 
////		      			                Map.Entry pair = itr1.next(); 
////		      			                System.out.println(pair.getKey() + " : " + pair.getValue()); 
//		      			            } 
//		      			        } 
//		      	                  
//		      					
//		                      }
//		                    } else {
//		                      System.out.println("Error: records should be an array: skipping.");
//		                      parser.skipChildren();
//		                    }
//		                  }else {
//		                   // System.out.println("Field Name: " + fieldName);
//		                    parser.skipChildren();
//		                  }
//		                }                
//		              } }catch(IOException ie) {
//		                  ie.printStackTrace();
//
//		              } 
//
//		        }
//		}