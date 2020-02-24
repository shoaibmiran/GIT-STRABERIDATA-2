package com.tap.model;

public class Item_Group {
	private String item_group_name=null;
	private String parent_item_group=null;
	public String getItem_group_name() {
		return item_group_name;
	}
	public void setItem_group_name(String item_group_name) {
		this.item_group_name = item_group_name;
	}
	public Item_Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getParent_item_group() {
		return parent_item_group;
	}
	public void setParent_item_group(String parent_item_group) {
		this.parent_item_group = parent_item_group;
	}
	public Item_Group(String item_group_name, String parent_item_group) {
		this.item_group_name = item_group_name;
		this.parent_item_group = parent_item_group;
	}
	
	
	

}
