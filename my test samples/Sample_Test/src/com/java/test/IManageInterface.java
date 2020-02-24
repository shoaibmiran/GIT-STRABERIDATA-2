package com.java.test;

import java.util.ArrayList;

public interface IManageInterface {

	public void addBook(Book book);


	public ArrayList<Book> removeBooks(String genre);

	public int updateMagazinePrice(double newPrice);
	
	public int getDetails(int bookId);

}
