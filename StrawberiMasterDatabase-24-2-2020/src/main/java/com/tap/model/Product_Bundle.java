package com.tap.model;

import java.util.ArrayList;


public class Product_Bundle {
	private String new_item_code = null;
	
	private String introduction = null;
	private String id = null;
	private String category = null;
	private String parent_product_bundle = null;
	private String package_type = null;
	private boolean flights=false;
	private boolean visa=false;
	private boolean hotels=false;
	private boolean sightseeing=false;
	private boolean transfer=false;
	private boolean passport=false;
	private boolean forex=false;
	private boolean tour_manager=false;
	private boolean insurance=false;
	
	private ArrayList<Bundle_Variants> variants;
	private ArrayList<Bundle_Price_List_Details> price_list_details;
	private ArrayList<Items> items; 
	private ArrayList<Bundle_Itinerary> itinerary;
	
	Product_Bundle attributes;
	
	
	
	public boolean isPassport() {
		return passport;
	}

	public void setPassport(boolean passport) {
		this.passport = passport;
	}

	public boolean isForex() {
		return forex;
	}

	public void setForex(boolean forex) {
		this.forex = forex;
	}

	public boolean isTour_manager() {
		return tour_manager;
	}

	public void setTour_manager(boolean tour_manager) {
		this.tour_manager = tour_manager;
	}

	public boolean isInsurance() {
		return insurance;
	}

	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	private String package_name = null;
	
	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public ArrayList<Bundle_Variants> getVariants() {
		return variants;
	}

	public void setVariants(ArrayList<Bundle_Variants> variants) {
		this.variants = variants;
	}
    public void addPage(ArrayList<Bundle_Variants> bvs) {
        if(this.variants == null) {
            this.variants = new ArrayList<Bundle_Variants>();
        }
        this.variants.addAll(bvs);
    }

	public ArrayList<Bundle_Price_List_Details> getPrice_list_details() {
		return price_list_details;
	}

	public void setPrice_list_details(ArrayList<Bundle_Price_List_Details> price_list_details) {
		this.price_list_details = price_list_details;
	}

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

	public ArrayList<Bundle_Itinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(ArrayList<Bundle_Itinerary> itinerary) {
		this.itinerary = itinerary;
	}

	public ArrayList<Items> getItems() {
		return items;
	}

	public void setItems(ArrayList<Items> items) {
		this.items = items;
	}

	public String getNew_item_code() {
		return new_item_code;
	}

	
	/*public Product_Bundle(boolean flights, boolean visa, boolean hotels, boolean sightseeing) {
		this.flights = flights;
		this.visa = visa;
		this.hotels = hotels;
		this.sightseeing = sightseeing;
	}*/

	public Product_Bundle(Product_Bundle attributes) {
		
		this.attributes=attributes;
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

	public Product_Bundle() {
		super();
	}
	 public void Allitems(Items mainobj) {
	        if(this.items == null) {
	            this.items = new ArrayList<Items>();
	        }
	        this.items.add(mainobj);
	    }
	  public void Allvariants(Bundle_Variants ch) {
	        if(this.variants == null) {
	            this.variants = new ArrayList<Bundle_Variants>();
	        }
	        this.variants.add(ch);
	    }
	  public Product_Bundle(String new_item_code,String package_name,String introduction, ArrayList<Items> items) {
			this.new_item_code = new_item_code;
			this.package_name=package_name;
			this.introduction = introduction;
			this.items = items;
		}
	  
	  public void Allitinerary(Bundle_Itinerary iti) {
	        if(this.itinerary == null) {
	            this.itinerary = new ArrayList<Bundle_Itinerary>();
	        }
	        this.itinerary.add(iti);
	    }
	  
	  public void Allpricelist(Bundle_Price_List_Details plist) {
	        if(this.price_list_details == null) {
	            this.price_list_details = new ArrayList<Bundle_Price_List_Details>();
	        }
	        this.price_list_details.add(plist);
	    }
	 
}
