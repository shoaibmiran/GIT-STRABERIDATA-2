package com.tap.model;

import java.util.ArrayList;

public class Package_Product_Alternatives {

	private String product_bundle = null;
	private ArrayList<Package_Product_Alternatives_Item> package_product_alternatives_item = null;

	public Package_Product_Alternatives(String product_bundle,
			ArrayList<Package_Product_Alternatives_Item> package_product_alternatives_item) {
		this.product_bundle = product_bundle;
		this.package_product_alternatives_item = package_product_alternatives_item;
	}

	public String getProduct_bundle() {
		return product_bundle;
	}

	public void setProduct_bundle(String product_bundle) {
		this.product_bundle = product_bundle;
	}

	public ArrayList<Package_Product_Alternatives_Item> getPackage_product_alternatives_item() {
		return package_product_alternatives_item;
	}

	public void setPackage_product_alternatives_item(
			ArrayList<Package_Product_Alternatives_Item> package_product_alternatives_item) {
		this.package_product_alternatives_item = package_product_alternatives_item;
	}

	public void Allproductalt(Package_Product_Alternatives_Item iti) {
		if (this.package_product_alternatives_item == null) {
			this.package_product_alternatives_item = new ArrayList<Package_Product_Alternatives_Item>();
		}
		this.package_product_alternatives_item.add(iti);
	}

	public Package_Product_Alternatives() {
		super();
		// TODO Auto-generated constructor stub
	}

}
