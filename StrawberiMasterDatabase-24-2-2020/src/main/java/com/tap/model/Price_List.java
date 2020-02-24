package com.tap.model;

import java.util.ArrayList;

public class Price_List {
	private String price_list_name = null;
	private ArrayList<Price_Countries> countries;
	private String currency = null;
	private String season = null;
	private String start_date = null;
	private String end_date = null;
	private boolean selling=true;
	private String price_description = null;
	


	public Price_List(String price_list_name, ArrayList<Price_Countries> countries, String currency, String season,
			String start_date, String end_date, boolean selling, String price_description) {
		this.price_list_name = price_list_name;
		this.countries = countries;
		this.currency = currency;
		this.season = season;
		this.start_date = start_date;
		this.end_date = end_date;
		this.selling = selling;
		this.price_description = price_description;
	}


	public String getPrice_list_name() {
		return price_list_name;
	}


	public void setPrice_list_name(String price_list_name) {
		this.price_list_name = price_list_name;
	}


	public ArrayList<Price_Countries> getCountries() {
		return countries;
	}


	public void setCountries(ArrayList<Price_Countries> countries) {
		this.countries = countries;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getSeason() {
		return season;
	}


	public void setSeason(String season) {
		this.season = season;
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	public boolean isSelling() {
		return selling;
	}


	/*public Price_List(String price_list_name, ArrayList<Price_Countries> countries, String currency, String season,
			boolean selling) {
		this.price_list_name = price_list_name;
		this.countries = countries;
		this.currency = currency;
		this.season = season;
		this.selling = selling;
	}*/


	public String getPrice_description() {
		return price_description;
	}
	public void setPrice_description(String price_description) {
		this.price_description = price_description;
	}
	public void setSelling(boolean selling) {
		this.selling = selling;
	}
	


	/*public void Allcountries(Price_Countries countries) {
        if(this.countries == null) {
            this.countries = new ArrayList<Price_Countries>();
        }
        this.countries.add(countries);
    }*/
	public Price_List() {
		super();
	}
	
	

}
