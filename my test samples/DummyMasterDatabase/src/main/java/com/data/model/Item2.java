package com.data.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Item2 {
	private String item_code = null;
	private String item_name = null;
	private String stock_uom = null;
	private String item_group = null;
	private boolean include_item_in_manufacturing=true;
	private boolean is_stock_item=true;
	
	public boolean getInclude_item_in_manufacturing() {
		return include_item_in_manufacturing;
	}

	public void setInclude_item_in_manufacturing(boolean include_item_in_manufacturing) {
		this.include_item_in_manufacturing = include_item_in_manufacturing;
	}

	public boolean getIs_stock_item() {
		return is_stock_item;
	}

	public void setIs_stock_item(boolean is_stock_item) {
		this.is_stock_item = is_stock_item;
	}

	//private boolean has_variants;
	private String category = null;
	private ArrayList<Item_Defaults> item_defaults;
	//private ArrayList<Item_Content> content;
	//private ArrayList<Item_Attributes> attributes;

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<Item_Defaults> getItem_defaults() {
		return item_defaults;
	}

	public Item2(String item_code, String item_name, String stock_uom, String item_group,String category,boolean include_item_in_manufacturing,boolean is_stock_item,
			ArrayList<Item_Defaults> item_defaults) {
		this.item_code = item_code;
		this.item_name = item_name;
		this.include_item_in_manufacturing=include_item_in_manufacturing;
		this.is_stock_item=is_stock_item;
		this.stock_uom = stock_uom;		
		this.item_group = item_group;
		this.category = category;
		this.item_defaults = item_defaults;
	}

	public void setItem_defaults(ArrayList<Item_Defaults> item_defaults) {
		this.item_defaults = item_defaults;
	}

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

	public String getStock_uom() {
		return stock_uom;
	}

	public void setStock_uom(String stock_uom) {
		this.stock_uom = stock_uom;
	}

	public String getItem_group() {
		return item_group;
	}

	public void setItem_group(String item_group) {
		this.item_group = item_group;
	}

	public Item2() {
		super();
	}
}
