package com.java.test;

public class Magazine {
	
	private String bookId=null;
	private String bookName=null;
	private String genre=null;
	private double price=0.0;
	private String authorName=null;
	private String giftSubscription=null;
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getGiftSubscription() {
		return giftSubscription;
	}
	public void setGiftSubscription(String giftSubscription) {
		this.giftSubscription = giftSubscription;
	}
	public Magazine(String bookId, String bookName, String genre, double price, String authorName,
			String giftSubscription) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.genre = genre;
		this.price = price;
		this.authorName = authorName;
		this.giftSubscription = giftSubscription;
	}
	
	

}
