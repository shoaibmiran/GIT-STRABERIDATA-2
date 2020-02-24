package com.tap.model;

import java.util.ArrayList;

public class Item2 {
	private String item_code = null;
	private String item_name = null;
	private String stock_uom = null;
	private String item_group = null;
	private boolean include_item_in_manufacturing=false;
	private boolean is_stock_item=false;
	private String description = null;
	private boolean has_variants=false;
	private String variant_based_on=null;
	private String price_category=null;
	private ArrayList<Item_Hasattributes> attributes;
	private String category = null;
	private boolean is_default=false;
	private String variant_of=null;
	private ArrayList<Item_Defaults> item_defaults;
	private ArrayList<Supplier_Items> supplier_items;
	Item2 item=null;
	
	
	
	public ArrayList<Supplier_Items> getSupplier_items() {
		return supplier_items;
	}

	public void setSupplier_items(ArrayList<Supplier_Items> supplier_items) {
		this.supplier_items = supplier_items;
	}

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}

	
	public String getVariant_of() {
		return variant_of;
	}

	public void setVariant_of(String variant_of) {
		this.variant_of = variant_of;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHas_variants() {
		return has_variants;
	}

	public void setHas_variants(boolean has_variants) {
		this.has_variants = has_variants;
	}

	public String getVariant_based_on() {
		return variant_based_on;
	}

	public void setVariant_based_on(String variant_based_on) {
		this.variant_based_on = variant_based_on;
	}

	public String getPrice_category() {
		return price_category;
	}

	public void setPrice_category(String price_category) {
		this.price_category = price_category;
	}

	public ArrayList<Item_Hasattributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Item_Hasattributes> attributes) {
		this.attributes = attributes;
	}

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
			ArrayList<Item_Defaults> item_defaults,ArrayList<Supplier_Items> supplier_items) {
		this.item_code = item_code;
		this.item_name = item_name;
		this.include_item_in_manufacturing=include_item_in_manufacturing;
		this.is_stock_item=is_stock_item;
		this.stock_uom = stock_uom;		
		this.item_group = item_group;
		this.category = category;
		this.item_defaults = item_defaults;
		this.supplier_items=supplier_items;
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
	
	public void Allattributes(Item_Hasattributes attribute) {
        if(this.attributes == null) {
            this.attributes = new ArrayList<Item_Hasattributes>();
        }
        this.attributes.add(attribute);
    }
	
	public void Allcompany(Item_Defaults item_defaults) {
        if(this.item_defaults == null) {
            this.item_defaults = new ArrayList<Item_Defaults>();
        }
        this.item_defaults.add(item_defaults);
    }
	
}
