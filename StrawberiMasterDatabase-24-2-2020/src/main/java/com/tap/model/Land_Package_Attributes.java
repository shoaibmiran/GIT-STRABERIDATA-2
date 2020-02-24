package com.tap.model;

import java.util.ArrayList;

public class Land_Package_Attributes {
	
	private String item=null;
	private String number_of_days=null;
	private String number_of_nights=null;
	private String state_id=null;
	private String validity_from=null;
	private String validity_up_to=null;
	private String minimum_price=null;
	private String maximum_price=null;
	private String book_before_date=null;
	private String offer_count= null;
	private String list_of_offer_= null;
	private String season= null;
	private String continent= null;
	private String country= null;
	private String state= null;
	private String theme= null;
	private String tags= null;
	private String passport=null;
	private String visa=null;
	private String address_proof=null;
	private String identity_proof=null;
	private String invitation_letter=null;
	
	private ArrayList<Package_Attribute_Places> places =null;
	private ArrayList<Package_Attribute_Special_Event> special_event=null;
	
    public void AllPalces(Package_Attribute_Places pap) {
        if(this.places == null) {
            this.places = new ArrayList<Package_Attribute_Places>();
        }
        this.places.add(pap);
    }
    
    
    
    



	public void AllEvents(Package_Attribute_Special_Event pase) {
        if(this.special_event == null) {
            this.special_event = new ArrayList<Package_Attribute_Special_Event>();
        }
        this.special_event.add(pase);
    }
	/*public Land_Package_Attributes(String item, String number_of_days, String number_of_nights, String state_id,
			String validity_from, String validity_up_to, String minimum_price, String maximum_price,
			String book_before_date, String offer_count, String list_of_offer_, String season, String continent,
			String country, String state, String theme, String tags, String passport, String visa, String address_proof,
			String identity_proof, String invitation_letter, ArrayList<Package_Attribute_Places> places,
			ArrayList<Package_Attribute_Special_Event> special_event) {
		this.item = item;
		this.number_of_days = number_of_days;
		this.number_of_nights = number_of_nights;
		this.state_id = state_id;
		this.validity_from = validity_from;
		this.validity_up_to = validity_up_to;
		this.minimum_price = minimum_price;
		this.maximum_price = maximum_price;
		this.book_before_date = book_before_date;
		this.offer_count = offer_count;
		this.list_of_offer_ = list_of_offer_;
		this.season = season;
		this.continent = continent;
		this.country = country;
		this.state = state;
		this.theme = theme;
		this.tags = tags;
		this.passport = passport;
		this.visa = visa;
		this.address_proof = address_proof;
		this.identity_proof = identity_proof;
		this.invitation_letter = invitation_letter;
		this.places = places;
		this.special_event = special_event;
	}*/
	
	

	public String getItem() {
		return item;
	}

	public Land_Package_Attributes(String item, String number_of_days, String number_of_nights,String validity_from,String validity_up_to,
			 String continent, String country, String state,String minimum_price) {
		this.item = item;
		this.number_of_days = number_of_days;
		this.number_of_nights = number_of_nights;
		this.validity_from = validity_from;
		this.validity_up_to = validity_up_to;
		this.continent = continent;
		this.country = country;
		this.state = state;
		this.minimum_price = minimum_price;
	}

	public void setItem(String item) {
		this.item = item;
	}

	

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

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String  getValidity_from() {
		return validity_from;
	}

	public void setValidity_from(String  validity_from) {
		this.validity_from = validity_from;
	}

	public String  getValidity_up_to() {
		return validity_up_to;
	}

	public void setValidity_up_to(String  validity_up_to) {
		this.validity_up_to = validity_up_to;
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

	public String getList_of_offer_() {
		return list_of_offer_;
	}

	public void setList_of_offer_(String list_of_offer_) {
		this.list_of_offer_ = list_of_offer_;
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

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getVisa() {
		return visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public String getAddress_proof() {
		return address_proof;
	}

	public void setAddress_proof(String address_proof) {
		this.address_proof = address_proof;
	}

	public String getIdentity_proof() {
		return identity_proof;
	}

	public void setIdentity_proof(String identity_proof) {
		this.identity_proof = identity_proof;
	}

	public String getInvitation_letter() {
		return invitation_letter;
	}

	public void setInvitation_letter(String invitation_letter) {
		this.invitation_letter = invitation_letter;
	}

	public ArrayList<Package_Attribute_Places> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Package_Attribute_Places> places) {
		this.places = places;
	}

	public ArrayList<Package_Attribute_Special_Event> getSpecial_event() {
		return special_event;
	}

	public void setSpecial_event(ArrayList<Package_Attribute_Special_Event> special_event) {
		this.special_event = special_event;
	}

	public Land_Package_Attributes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	


	

}
