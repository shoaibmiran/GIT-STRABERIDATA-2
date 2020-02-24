package com.java.test;

import java.util.ArrayList;
import java.util.Iterator;

public class CatalogManager implements IManageInterface{
	
	ArrayList<Book> array=new ArrayList<Book>();
	
	public CatalogManager(ArrayList<Book> array) {
		this.array = array;
	}

	
	@Override
	public void addBook(Book book) {
		boolean found=array.contains(book.getBookId());
		if(found==false)
		{
			array.add(book);
		}
		
		else if(found==true)
		{
			System.out.println("ALready found");
		}
		
	}


	@Override
	public ArrayList<Book> removeBooks(String genre) {
		boolean found=array.contains(genre);
		array.remove(genre);
		
		Iterator i = array.iterator();
		 while (i.hasNext()) {
	        String str = (String)i.next();
	         if (str.equals("Orange")) {
	            i.remove();
	            System.out.println("\nThe element Orange is removed");
	            break;
	         }
	      }
		return null;
	}

	@Override
	public int updateMagazinePrice(double newPrice) {
		return 0;
	}

	@Override
	public int getDetails(int bookId) {
		return 0;
	}
	
	

}
