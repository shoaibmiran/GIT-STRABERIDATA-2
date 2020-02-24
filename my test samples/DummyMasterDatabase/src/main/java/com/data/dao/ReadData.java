package com.data.dao;
import java.sql.*;
import java.util.ArrayList; 
import com.data.model.Item2;

public class ReadData {
	 static ArrayList<Item2> arrlist = new ArrayList<Item2>();
	   public ArrayList<Item2> getArrlist() {
		return arrlist;
	}
	public void setArrlist(ArrayList<Item2> arrlist) {
		ReadData.arrlist = arrlist;
	}
	public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   
	   Item2 item=new Item2();
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	     
	      conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/fitpackages","root","tiger");  

	      stmt = conn.createStatement();

	      String sql = "SELECT item_code, item_name, item_group FROM fit_package_catalog_basic";
	      ResultSet rs = stmt.executeQuery(sql);
	     
	      while(rs.next()){
	    	  
	    	/* String item_code  = rs.getString("item_code");
	         String item_name = rs.getString("item_name");
	         String item_group = rs.getString("item_group");*/
	         item.setItem_code(rs.getString("item_code"));
	         item.setItem_name( rs.getString("item_name"));
	         item.setItem_group(rs.getString("item_group"));
	         arrlist.add(item);
	        
	        /* System.out.print(i+"--Item Code: " + item_code+"\t");
	         System.out.print("Item Name: " + item_name+"\t");
	         System.out.print("Item Group: " + item_group+"\n");
	         System.out.print(i+"--Item Code: " + item.getItem_code()+"\t");
	         System.out.print("Item Name: " + item.getItem_name()+"\t");
	         System.out.print("Item Group: " + item.getItem_group()+"\n");    
	         i++;*/
	         
	      
	      }
	         int i=1;
	         for (Item2 value : arrlist) { 
	        	 System.out.println("----------------"+i+" Pacakge----------------");
	             System.out.println("item code = " + value.getItem_code()+"\t"); 
	             System.out.println("item name = " + value.getItem_name()+"\t"); 
	             System.out.println("item group = " + value.getItem_group()+"\n"); 
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
