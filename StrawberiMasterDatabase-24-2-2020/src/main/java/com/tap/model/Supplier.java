package com.tap.model;

public class Supplier {
	
	private String supplier_name=null;
	private String supplier_group=null;
	
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getSupplier_group() {
		return supplier_group;
	}
	public void setSupplier_group(String supplier_group) {
		this.supplier_group = supplier_group;
	}
	public Supplier(String supplier_name, String supplier_group) {
		this.supplier_name = supplier_name;
		this.supplier_group = supplier_group;
	}
	public Supplier() {
		super();
	}
	
	

}
