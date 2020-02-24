package com.tap.model;

public class Product_Bundle_Attribute {

	private String product_bundle = null;
	private String state_id = null;
	private String currency = null;
	private String minimum_price = null;
	private String maximum_price = null;
	private String price_description = null;
	private String book_before_date = null;
	private String offer_count = null;
	private String list_of_offer = null;
	private String season = null;
	private String continent = null;
	private String country = null;
	private String state = null;
	private String theme = null;
	private String tags = null;
	private String destination = null;
	private String tour_type = null;
	private String star_rating_ = null;
	private String tour_category = null;
	private String default_selling_price=null;
	private String place=null;
	private String number_of_days=null;
	private String number_of_nights=null;
	
	
	public String getNumber_of_days() {
		return number_of_days;
	}

	public void setNumber_of_days(String number_of_days) {
		this.number_of_days = number_of_days;
	}

	public String getNumber_of_nights() {
		return number_of_nights;
	}

	public void setNumber_of_nights(String number_of_nights) {
		this.number_of_nights = number_of_nights;
	}


	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDefault_selling_price() {
		return default_selling_price;
	}

	public void setDefault_selling_price(String default_selling_price) {
		this.default_selling_price = default_selling_price;
	}

	public String getProduct_bundle() {
		return product_bundle;
	}

	public Product_Bundle_Attribute(String product_bundle,String currency,String minimum_price, String continent,String country,
			 String state,String tour_type,String tour_category) {
		this.product_bundle = product_bundle;
		this.currency = currency;
		this.minimum_price = minimum_price;
		this.continent = continent;
		this.country = country;
		this.state = state;
		this.tour_type = tour_type;
		this.tour_category = tour_category;
	}

	public String getTour_category() {
		return tour_category;
	}

	public void setTour_category(String tour_category) {
		this.tour_category = tour_category;
	}

	public void setProduct_bundle(String product_bundle) {
		this.product_bundle = product_bundle;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMinimum_price() {
		return minimum_price;
	}

	public void setMinimum_price(String minimum_price) {
		this.minimum_price = minimum_price;
	}

	public String getMaximum_price() {
		return maximum_price;
	}

	public void setMaximum_price(String maximum_price) {
		this.maximum_price = maximum_price;
	}

	public String getPrice_description() {
		return price_description;
	}

	public void setPrice_description(String price_description) {
		this.price_description = price_description;
	}

	public String getBook_before_date() {
		return book_before_date;
	}

	public void setBook_before_date(String book_before_date) {
		this.book_before_date = book_before_date;
	}

	public String getOffer_count() {
		return offer_count;
	}

	public void setOffer_count(String offer_count) {
		this.offer_count = offer_count;
	}

	public String getList_of_offer() {
		return list_of_offer;
	}

	public void setList_of_offer(String list_of_offer) {
		this.list_of_offer = list_of_offer;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTour_type() {
		return tour_type;
	}

	public void setTour_type(String tour_type) {
		this.tour_type = tour_type;
	}

	public String getStar_rating_() {
		return star_rating_;
	}

	public void setStar_rating_(String star_rating_) {
		this.star_rating_ = star_rating_;
	}

	public Product_Bundle_Attribute() {
		super();
		// TODO Auto-generated constructor stub
	}

}
