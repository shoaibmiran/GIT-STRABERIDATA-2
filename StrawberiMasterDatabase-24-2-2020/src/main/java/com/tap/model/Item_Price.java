package com.tap.model;

public class Item_Price {
	
	private String item_code=null;
	private String item_name=null;
	private String uom=null;
	private String packing_unit=null;
	private String min_qty=null;
	private String brand=null;
	private String item_description=null;
	private String price_list=null;
	private String customer=null;
	private boolean selling=false;
	private String currency=null;
	private String price_list_rate=null;
	private String valid_from=null;
	private String valid_upto=null;
	private String lead_time_days=null;
	private String note=null;
	private String reference=null;
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getPacking_unit() {
		return packing_unit;
	}
	public void setPacking_unit(String packing_unit) {
		this.packing_unit = packing_unit;
	}
	public String getMin_qty() {
		return min_qty;
	}
	public void setMin_qty(String min_qty) {
		this.min_qty = min_qty;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public String getPrice_list() {
		return price_list;
	}
	public void setPrice_list(String price_list) {
		this.price_list = price_list;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public boolean isSelling() {
		return selling;
	}
	public void setSelling(boolean selling) {
		this.selling = selling;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrice_list_rate() {
		return price_list_rate;
	}
	public void setPrice_list_rate(String price_list_rate) {
		this.price_list_rate = price_list_rate;
	}
	public String getLead_time_days() {
		return lead_time_days;
	}
	public void setLead_time_days(String lead_time_days) {
		this.lead_time_days = lead_time_days;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	public String getValid_from() {
		return valid_from;
	}
	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}
	public String getValid_upto() {
		return valid_upto;
	}
	public void setValid_upto(String valid_upto) {
		this.valid_upto = valid_upto;
	}
	public Item_Price() {
		super();
	}
	public Item_Price(String item_code, String item_name, String uom, String packing_unit, String min_qty, String brand,
			String item_description, String price_list, String customer, boolean selling, String currency,
			String price_list_rate, String valid_from, String valid_upto, String lead_time_days, String note,
			String reference) {
		this.item_code = item_code;
		this.item_name = item_name;
		this.uom = uom;
		this.packing_unit = packing_unit;
		this.min_qty = min_qty;
		this.brand = brand;
		this.item_description = item_description;
		this.price_list = price_list;
		this.customer = customer;
		this.selling = selling;
		this.currency = currency;
		this.price_list_rate = price_list_rate;
		this.valid_from = valid_from;
		this.valid_upto = valid_upto;
		this.lead_time_days = lead_time_days;
		this.note = note;
		this.reference = reference;
	}
	
	
	
	
	
	
}
