package com.tap.model;

import java.util.ArrayList;

public class Sight_Seeing_Attribute {

	private String item = null;
	private String description = null;
	private String number_of_days = null;
	private String number_of_nights = null;
	private String validity_from = null;
	private String validity_up_to = null;
	private String minimum_price = null;
	private String maximum_price = null;
	private String book_before_date = null;
	private String created_date = null;
	private String created_by = null;
	private String created_branch = null;
	private String list_of_offer = null;
	private String season = null;
	private String continent = null;
	private String country = null;
	private String state = null;
	private String theme = null;
	private String tags = null;
	private ArrayList<Sight_Seeing_Special_Event> special_event = null;

	public Sight_Seeing_Attribute(String item, String description, String number_of_days, String number_of_nights,
			String validity_from, String validity_up_to, String minimum_price, String maximum_price,
			String book_before_date, String created_date, String created_by, String created_branch,
			String list_of_offer, String season, String continent, String country, String state, String theme,
			String tags, ArrayList<Sight_Seeing_Special_Event> special_event) {
		this.item = item;
		this.description = description;
		this.number_of_days = number_of_days;
		this.number_of_nights = number_of_nights;
		this.validity_from = validity_from;
		this.validity_up_to = validity_up_to;
		this.minimum_price = minimum_price;
		this.maximum_price = maximum_price;
		this.book_before_date = book_before_date;
		this.created_date = created_date;
		this.created_by = created_by;
		this.created_branch = created_branch;
		this.list_of_offer = list_of_offer;
		this.season = season;
		this.continent = continent;
		this.country = country;
		this.state = state;
		this.theme = theme;
		this.tags = tags;
		this.special_event = special_event;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getValidity_from() {
		return validity_from;
	}

	public void setValidity_from(String validity_from) {
		this.validity_from = validity_from;
	}

	public String getValidity_up_to() {
		return validity_up_to;
	}

	public void setValidity_up_to(String validity_up_to) {
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

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_branch() {
		return created_branch;
	}

	public void setCreated_branch(String created_branch) {
		this.created_branch = created_branch;
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

	public ArrayList<Sight_Seeing_Special_Event> getSpecial_event() {
		return special_event;
	}

	public void setSpecial_event(ArrayList<Sight_Seeing_Special_Event> special_event) {
		this.special_event = special_event;
	}

	public Sight_Seeing_Attribute() {
		super();
		// TODO Auto-generated constructor stub
	}

}
