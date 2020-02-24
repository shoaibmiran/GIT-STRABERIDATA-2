package com.data.dao;
import java.sql.*;
import java.util.ArrayList;

import com.data.model.Bundle_Itinerary;
import com.data.model.Item2;

public class Itinerary {
	 static ArrayList<Bundle_Itinerary> arrlist = new ArrayList<Bundle_Itinerary>();
	   public ArrayList<Bundle_Itinerary> getArrlist() {
		return arrlist;
	}
	public void setArrlist(ArrayList<Bundle_Itinerary> arrlist) {
		Itinerary.arrlist = arrlist;
	}
	public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   
	   Bundle_Itinerary itinerary=new Bundle_Itinerary();
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	     
	      conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/fitpackages","root","tiger");  

	      stmt = conn.createStatement();

	      String sql = "SELECT day, package_id, title,category,product FROM fit_package_catalog_itinerary";
	      ResultSet rs = stmt.executeQuery(sql);
	     
	      while(rs.next()){
	    	  
	    	/* String item_code  = rs.getString("item_code");
	         String item_name = rs.getString("item_name");
	         String item_group = rs.getString("item_group");*/
	    	  itinerary.setDay(rs.getString("day"));
	    	  itinerary.setPackage_id(rs.getString("package_id"));
	    	  itinerary.setTitle( rs.getString("title"));
	    	  itinerary.setCategory( rs.getString("category"));
	    	  itinerary.setProduct( rs.getString("product"));
	         arrlist.add(itinerary);
	      }
	        /* System.out.print(i+"--Item Code: " + item_code+"\t");
	         System.out.print("Item Name: " + item_name+"\t");
	         System.out.print("Item Group: " + item_group+"\n");
	         System.out.print(i+"--Item Code: " + item.getItem_code()+"\t");
	         System.out.print("Item Name: " + item.getItem_name()+"\t");
	         System.out.print("Item Group: " + item.getItem_group()+"\n");    
	         i++;*/
	         int i=1;
	         for (Bundle_Itinerary value : arrlist) { 
	        	 System.out.println("----------------"+i+" Pacakge----------------");
	           /*  System.out.println("Day = " + value.getDay()+"\t"); 
	             System.out.println("Package Id = " + value.getPackage_id()+"\t"); 
	             System.out.println("Title = " + value.getTitle()+"\t"); 
	             System.out.println("Category = " + value.getCategory()+"\t");
	             System.out.println("Product = " + value.getProduct()+"\n");*/
	            System.out.println("item group = " +value+"\n");
	             i++;
	         } 
	      
	      
	        
	         
	      rs.close();
	   }catch(SQLException se){
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
