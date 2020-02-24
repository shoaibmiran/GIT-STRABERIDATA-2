package com.data.model;

import java.util.ArrayList;

public class Product_Bundle {
	private String new_item_code = null;
	private String introduction = null;
	private String id = null;
	private String category = null;
	private String parent_product_bundle = null;
	private String package_type = null;
	private ArrayList<Bundle_Items> items; 
	private ArrayList<Bundle_Itinerary> itinerary;
	private ArrayList<Bundle_Variants> variants;
	private ArrayList<Bundle_Price_List_Details> price_list_details;
	private boolean flights=false;
	private boolean visa=false;
	private boolean hotels=false;
	private boolean sightseeing=false;
	
	public boolean isFlights() {
		return flights;
	}

	public void setFlights(boolean flights) {
		this.flights = flights;
	}

	public boolean isVisa() {
		return visa;
	}

	public void setVisa(boolean visa) {
		this.visa = visa;
	}

	public boolean isHotels() {
		return hotels;
	}

	public void setHotels(boolean hotels) {
		this.hotels = hotels;
	}

	public boolean isSightseeing() {
		return sightseeing;
	}

	public void setSightseeing(boolean sightseeing) {
		this.sightseeing = sightseeing;
	}

	public ArrayList<Bundle_Price_List_Details> getPrice_list_details() {
		return price_list_details;
	}

	public void setPrice_list_details(ArrayList<Bundle_Price_List_Details> price_list_details) {
		this.price_list_details = price_list_details;
	}

	public ArrayList<Bundle_Variants> getVariants() {
		return variants;
	}

	public void setVariants(ArrayList<Bundle_Variants> variants) {
		this.variants = variants;
	}

	public ArrayList<Bundle_Items> getItems() {
		return items;
	}

	public void setItems(ArrayList<Bundle_Items> items) {
		this.items = items;
	}

	
	

	/*public Product_Bundle(String new_item_code, String description, String id, String category,
			String parent_product_bundle, String package_type, ArrayList<Bundle_Items> items,
			ArrayList<Bundle_Itinerary> itinerary) {
		this.new_item_code = new_item_code;
		this.description = description;
		this.id = id;
		this.category = category;
		this.parent_product_bundle = parent_product_bundle;
		this.package_type = package_type;
		this.items = items;
		this.itinerary = itinerary;
	}*/
	
	

	public String getNew_item_code() {
		return new_item_code;
	}

	public Product_Bundle(String new_item_code,String introduction, ArrayList<Bundle_Items> items) {
		this.new_item_code = new_item_code;
		this.introduction = introduction;
		this.items = items;
	}

	public void setNew_item_code(String new_item_code) {
		this.new_item_code = new_item_code;
	}

	
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getParent_product_bundle() {
		return parent_product_bundle;
	}

	public void setParent_product_bundle(String parent_product_bundle) {
		this.parent_product_bundle = parent_product_bundle;
	}

	public String getPackage_type() {
		return package_type;
	}

	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}
	
	

	public ArrayList<Bundle_Itinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(ArrayList<Bundle_Itinerary> itinerary) {
		this.itinerary = itinerary;
	}

	public Product_Bundle() {
		super();
	}
}
