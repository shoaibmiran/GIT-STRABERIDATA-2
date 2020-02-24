package com.tap.model;

import java.util.ArrayList;

public class Airport_Transfer_Item {
	private String item_code = null;
	private String item_name = null;
	private String stock_uom = null;
	private String item_group = null;
	private boolean include_item_in_manufacturing=true;
	private boolean is_stock_item=true;
	
	private String description=null;
	private String price_category=null;
	private int has_variants=0;
	private String variant_based_on=null;
	private String variant_of=null;
	private int is_default=0;
	private ArrayList<Airport_transfer_variants> attributes=null;
	
    public void Allairvariants(Airport_transfer_variants attribute) {
        if(this.attributes == null) {
            this.attributes = new ArrayList<Airport_transfer_variants>();
        }
        this.attributes.add(attribute);
    }
	
	
//	private ArrayList<Item_Hasattributes> attributes=null;
//	
//    public void Allattributes(Item_Hasattributes attribute) {
//        if(this.attributes == null) {
//            this.attributes = new ArrayList<Item_Hasattributes>();
//        }
//        this.attributes.add(attribute);
//    }
	
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
    public void Allitem_defaults(Item_Defaults item_default) {
        if(this.item_defaults == null) {
            this.item_defaults = new ArrayList<Item_Defaults>();
        }
        this.item_defaults.add(item_default);
    }
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<Item_Defaults> getItem_defaults() {
		return item_defaults;
	}

	public Airport_Transfer_Item(String item_code, String item_name, String stock_uom, String item_group,String category,boolean include_item_in_manufacturing,boolean is_stock_item,
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



//	public ArrayList<Item_Hasattributes> getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(ArrayList<Item_Hasattributes> attributes) {
//		this.attributes = attributes;
//	}
    public Airport_Transfer_Item()
    {
    	
    }
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice_category() {
		return price_category;
	}

	public void setPrice_category(String price_category) {
		this.price_category = price_category;
	}

	public int getHas_variants() {
		return has_variants;
	}

	public void setHas_variants(int has_variants) {
		this.has_variants = has_variants;
	}

	public String getVariant_based_on() {
		return variant_based_on;
	}

	public void setVariant_based_on(String variant_based_on) {
		this.variant_based_on = variant_based_on;
	}

	public String getVariant_of() {
		return variant_of;
	}

	public void setVariant_of(String variant_of) {
		this.variant_of = variant_of;
	}

	public int getIs_default() {
		return is_default;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}

	public ArrayList<Airport_transfer_variants> getAttributess() {
		return attributes;
	}

	public void setAttributess(ArrayList<Airport_transfer_variants> attributess) {
		this.attributes = attributess;
	}
}
