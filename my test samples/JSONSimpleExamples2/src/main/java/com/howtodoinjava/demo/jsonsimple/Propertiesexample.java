package com.howtodoinjava.demo.jsonsimple;
import java.util.*;  
import java.io.*;
public class Propertiesexample {
	public static void main(String[] args)throws Exception{  
	    FileReader reader=new FileReader("db.properties");  
	      
	    Properties p=new Properties();  
	   // Properties props = new Properties();
	    p.load(reader);  
	    System.out.println("Name:"+p.getProperty("name").length());  
	    System.out.println("Name:"+p.getProperty("name").length());  
	    System.out.println("Age:"+p.getProperty("age"));
	    System.out.println("Location:"+p.getProperty("location"));  
	    System.out.println("MobileNo:"+p.getProperty("mobileno"));  
	  /*  @SuppressWarnings("unchecked")
	    Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
	    while (enums.hasMoreElements()) {
	      String key = enums.nextElement();
	      String value = props.getProperty(key);
	      System.out.println(key + " : " + value);
	    }*/

	}  

}
