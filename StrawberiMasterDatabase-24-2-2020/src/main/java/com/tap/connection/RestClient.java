package com.tap.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import com.tap.model.Bundle_Itinerary;
import com.tap.model.Bundle_Price_List_Details;
import com.tap.model.Bundle_Variants;
import com.tap.model.Item2;
import com.tap.model.Item_Defaults;
import com.tap.model.Item_Hasattributes;
import com.tap.model.Item_Price;
import com.tap.model.Items;
import com.tap.model.Land_Package_Attributes;
import com.tap.model.Package_Attribute_Places;
import com.tap.model.Package_Attribute_Special_Event;
import com.tap.model.Price_Countries;
import com.tap.model.Price_List;
import com.tap.model.Product_Bundle;
import com.tap.model.Product_Bundle_Attribute;
import com.tap.model.Supplier_Items;

public class RestClient {

	static String flights;
	static String transfer;
	static String visa;
	static String hotels;
	static String sightseeing;
	static String item_name;
	static String introduction;
	static String passport;
	static String forex;
	static String tour_manager;
	static String insurance;
	static int k = 1;

	public static void main(String args[]) throws JSONException, Exception {
		setItem();
		setAttraction();
		setTemplate_hotel();
		setTemplate_variants();
		setAirportTransfer();
		setAirportTransferVariants();
		setPrice_list_();
		setItem_price_list();
		setProduct_bundle_();
		setProductBundleAttributes();
		setLandPackageAttributes();
	}

	
	private static void setAirportTransfer() {

		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		RestAPI rc = new RestAPI();
		try {
			conn =DBConnection.getConnection();
			stmt=conn.createStatement();
			String sql="select * from fit_transfer_service";
			//String sql="select distinct Transfer_service_id from fit_transfer_service";
			//System.out.println("SQL ------>" +sql);
			
			rs=stmt.executeQuery(sql);
			JSONObject job=null;
			while (rs.next()) {

	        System.out.println("Transfer_service_id  --------> " + rs.getString("Transfer_service_id"));
	        
             Item2 airport_transfer=new Item2();
             
             if(rs.getString("Transfer_service_id") != null) {
               airport_transfer.setItem_code(rs.getString("Transfer_service_id"));
             }else 
             {
               airport_transfer.setItem_code("");
             }
             if(rs.getString("Transfer_service_name") != null) {
                 airport_transfer.setItem_name(rs.getString("Transfer_service_name"));
               }else 
               {
                 airport_transfer.setItem_name("");
               }
//             airport_transfer.setItem_code("EMPIRE-002");
//             airport_transfer.setItem_name("EMPIRE HOTEL TRANSFER");
             airport_transfer.setItem_group("Transport");
             airport_transfer.setStock_uom("Nos");
             airport_transfer.setCategory("Transfer");
             airport_transfer.setDescription("");
             airport_transfer.setPrice_category("");
             airport_transfer.setHas_variants(true);
             airport_transfer.setVariant_based_on("Item Attribute");
             airport_transfer.setInclude_item_in_manufacturing(true);
             airport_transfer.setIs_stock_item(true);
             
             Item_Hasattributes item_hv1=new Item_Hasattributes();
             Item_Hasattributes item_hv2=new Item_Hasattributes();
             Item_Hasattributes item_hv3=new Item_Hasattributes();
             Item_Hasattributes item_hv4=new Item_Hasattributes();
             Item_Hasattributes item_hv5=new Item_Hasattributes();


             item_hv1.setAttribute("Vehicle Type");
             item_hv2.setAttribute("AC Type");
             item_hv3.setAttribute("Transfer Type");
             item_hv4.setAttribute("Group Size");
             item_hv5.setAttribute("Age Group");

             
             airport_transfer.Allattributes(item_hv1);
             airport_transfer.Allattributes(item_hv2);
             airport_transfer.Allattributes(item_hv3);
             airport_transfer.Allattributes(item_hv4);
             airport_transfer.Allattributes(item_hv5);


             Item_Defaults id=new Item_Defaults();
             id.setCompany("Strawberi");
             airport_transfer.Allcompany(id);
             
             job=new JSONObject(airport_transfer);
            
 			System.out.println("Airport Transfer Template----->"+job.toString(1));
 			
 			Response r1 = rc.createItemjson(airport_transfer);
			System.out.println("Status---" + r1.getStatus());
			System.out.println("Status Info---" + r1.getStatusInfo());
			System.out.println("Status Info---" + r1.getStringHeaders());
			System.out.println(r1.readEntity(String.class));
 			System.out.println();

			}
			
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
	}


	private static void setAirportTransferVariants() {
		RestAPI rc = new RestAPI();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			conn =DBConnection.getConnection();
			stmt=conn.createStatement();
			String sql="select * from fit_transfer_service";
			//System.out.println("SQL ------>" +sql);
			
			rs=stmt.executeQuery(sql);
			JSONObject job=null;
			while(rs.next()) {
             
	        
             Item2 airport_transfer=new Item2();
//             if(rs.getString("variant_name").equalsIgnoreCase("") || rs.getString("variant_name").equalsIgnoreCase("NA"))
//             {
//            	 airport_transfer.setItem_code(rs.getString("Transfer_service_id"));
//                 airport_transfer.setItem_name(rs.getString("Transfer_service_id"));
//             }
//             
//             else if(rs.getString("variant_name")!="" || rs.getString("variant_name")!="NA")
//             {
             airport_transfer.setItem_code(rs.getString("Transfer_service_id")+"-"+rs.getString("variant_name"));
             airport_transfer.setItem_name(rs.getString("Transfer_service_name")+"-"+rs.getString("variant_name"));
             airport_transfer.setItem_group("Transport");
             airport_transfer.setStock_uom("Nos");
             airport_transfer.setCategory("Transfer");
             airport_transfer.setPrice_category("");
             airport_transfer.setIs_default(false);
             airport_transfer.setHas_variants(false);
             airport_transfer.setVariant_based_on("Item Attribute");
             airport_transfer.setVariant_of(rs.getString("Transfer_service_id"));
             
             Item_Hasattributes item_hv3=new Item_Hasattributes();
             item_hv3.setVariant_of(rs.getString("Transfer_service_id"));
             item_hv3.setAttribute("Transfer Type");
             item_hv3.setAttribute_value(rs.getString("transfer_type"));
             
             
             airport_transfer.Allattributes(item_hv3);

           
             Item_Defaults id=new Item_Defaults();
             id.setCompany("Strawberi");
             airport_transfer.Allcompany(id);
             
             job=new JSONObject(airport_transfer);
            
  			System.out.println("Airport Transfer Variants----->"+job.toString(1));
  			Response r1 = rc.createItemjson(airport_transfer);
 			System.out.println("Status---" + r1.getStatus());
 			System.out.println("Status Info---" + r1.getStatusInfo());
 			System.out.println("Status Info---" + r1.getStringHeaders());
 			System.out.println(r1.readEntity(String.class));
  			System.out.println();

			}
			
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
		
	}


	private static void setItem_price_list() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Class.forName("com.mysql.jdbc.Driver");

			// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitjsontodb",
			// "root", "tiger");
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT distinct package_id from fit_package_catalog_variants_price";
//			String idd="FIT-Singapore-0015";
//			String sql = "SELECT distinct package_id from fit_package_catalog_variants_price where package_id='"+idd+"'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String package_id = rs.getString("package_id");
				setItem_price_list_main(package_id);

			}
			// rs.close();
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
	}

	private static void setItem_price_list_main(String package_id) {
		Connection conn = null;
		Statement stmt = null;
		RestAPI rc = new RestAPI();
		Item_Price price = new Item_Price();
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM fit_package_catalog_variants_price where package_id='" + package_id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			int j = 1;
			while (rs.next()) {
				System.out.println("--------------------------------------------------------PRICE PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				price.setItem_code(rs.getString("package_id"));
				System.out.println("item_code : " + price.getItem_code() + "\n");
				price.setItem_name(rs.getString("package_id"));
				System.out.println("item_code : " + price.getItem_name() + "\n");

				price.setCurrency(rs.getString("currency_1"));
				System.out.println("currency_1 : " + price.getCurrency() + "\n");
				price.setPrice_list(rs.getString("package_id") + "-Price-List-00" + j + "-01");
				System.out.println("package_id : " + price.getPrice_list() + "\n");
				price.setValid_from(rs.getString("validity_start_date_1"));
				System.out.println("Validity_from : " + price.getValid_from() + "\n");
				price.setValid_upto(rs.getString("validity_end_date_1"));
				System.out.println("Validity_upto : " + price.getValid_upto() + "\n");
				price.setPrice_list_rate(rs.getString("price_1"));
				System.out.println("Rate : " + price.getPrice_list_rate() + "\n");

				price.setSelling(true);
				System.out.println("selling : " + price.isSelling() + "\n");

				JSONObject obj = new JSONObject(price);
				System.out.println("Item price list---" + obj.toString());
				System.out.println("--------------------------------------------------------PRICE PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				System.out.println("--------------------------------------------------------INSERTING ITEMPRICE PACKAGE"
						+ i
						+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

				Response r = rc.createItem_price_list(price);

				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING ITEMPRICE PACKAGE"
						+ i
						+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

				i++;
				j++;
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setAttraction() {
		String stock_uom = "Nos";
		String category = "Sightseeing";
		String company = "Strawberi";
		String item_group = "Sightseeing";
		Connection conn = null;
		Statement stmt = null;
		RestAPI rc = new RestAPI();
		Item2 attributes = new Item2();
		System.out.println("hiiiiiiii");
		try {
			conn = DBConnection.getConnection();

			stmt = conn.createStatement();

			String sql = "SELECT * FROM fit_attraction";
//			String idlist="FIT-Maharashtra-001";
//			String sql = "SELECT * FROM fit_attraction where package_id_list='"+idlist+"'";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			while (rs.next()) {

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				attributes.setItem_code(rs.getString("attraction_id"));
				System.out.println("Item Code : " + attributes.getItem_code() + "\n");
				attributes.setItem_name(rs.getString("Attraction_name"));
				System.out.println("Item Name : " + attributes.getItem_name() + "\n");

				attributes.setItem_group(item_group);
				System.out.println("Item Group : " + attributes.getItem_group() + "\n");

				// adding static values
				attributes.setStock_uom(stock_uom);
				System.out.println("stock_uom : " + attributes.getStock_uom() + "\n");
				attributes.setCategory(category);
				System.out.println("Category : " + attributes.getCategory() + "\n");

				// adding item_default values
				ArrayList<Item_Defaults> item_default = new ArrayList<Item_Defaults>();
				Item_Defaults item = new Item_Defaults();
				item.setCompany(company);
				item_default.add(item);
				System.out.println("company : " + item.getCompany() + "\n");

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

				Response r = rc.createItemjson(
						new Item2(attributes.getItem_code(), attributes.getItem_name(), attributes.getStock_uom(),
								attributes.getItem_group(), attributes.getCategory(), true, true, item_default));
				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

				i++;

			}

			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
	}

	private static void setTemplate_variants() {
		Connection conn = null;
		Statement stmt = null;
		RestAPI rc = new RestAPI();
		String hotel_name = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			// for normal names
			//String c="Child with Bed";
			String sql = "SELECT * from fit_hotels";
			// for unformated name ex. ex'ian hotel
			// String sql = "SELECT * from fit_hotels where no=89";
			// otherwise use query select * from fit_hotels;
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			while (rs.next()) {
				Item2 hotelattr = new Item2();
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				hotel_name = rs.getString("hotel_name");
				hotelattr.setItem_code(rs.getString("hotel_id"));
				System.out.println("Item_code---" + hotelattr.getItem_code());
				hotelattr.setItem_name(rs.getString("hotel_id") + " - " + rs.getString("variant_name"));
				System.out.println("item_name---" + hotelattr.getItem_name() + "\n");

				Item_Hasattributes hasattributes = new Item_Hasattributes();
				hasattributes.setAttribute("Category");
				if (rs.getString("room_category").equalsIgnoreCase("")
						|| rs.getString("room_category").equalsIgnoreCase("na")) {
					hasattributes.setAttribute_value("Standard");
				} else if (rs.getString("room_category") != "" || rs.getString("room_category") != "NA") {
					hasattributes.setAttribute_value(rs.getString("room_category"));
				}
				hasattributes.setVariant_of(hotel_name);
				Item_Hasattributes hasattributes_2 = new Item_Hasattributes();
				hasattributes_2.setAttribute("STAR Rating");
				if (rs.getString("star_rating").equalsIgnoreCase("")
						|| rs.getString("star_rating").equalsIgnoreCase("na")) {
					hasattributes_2.setAttribute_value("3 STAR");
				} else if (rs.getString("star_rating") != "" || rs.getString("star_rating") != "NA") {
					hasattributes_2.setAttribute_value(rs.getString("star_rating") + " STAR");
				}
				hasattributes_2.setVariant_of(hotel_name);

				Item_Hasattributes hasattributes_3 = new Item_Hasattributes();
				hasattributes_3.setAttribute("Sharing Mode");
				String sharing_mode = rs.getString("sharing_mode");
				if (sharing_mode != "" || sharing_mode != "na") {
					hasattributes_3.setAttribute_value(rs.getString("sharing_mode"));
				} else if (sharing_mode.equalsIgnoreCase("") || sharing_mode.equalsIgnoreCase("na")) {
					hasattributes_3.setAttribute_value("Twin");
				}
				hasattributes_3.setVariant_of(hotel_name);

				Item_Hasattributes hasattributes_4 = new Item_Hasattributes();
				hasattributes_4.setAttribute("Boarding Type");
				// hasattributes_4.setAttribute_value("Full Boarding");
				hasattributes_4.setVariant_of(hotel_name);

				Item_Hasattributes hasattributes_7 = new Item_Hasattributes();
				hasattributes_7.setAttribute("Room Service");
				String room_service = rs.getString("room_service");
				if (room_service.equalsIgnoreCase("Yes")) {
					hasattributes_7.setAttribute_value("Room Service");
				} else if (room_service.equalsIgnoreCase("na") || room_service.equalsIgnoreCase("")) {
					hasattributes_7.setAttribute_value("No Room Service");
				}
				hasattributes_7.setVariant_of(hotel_name);

				Item_Hasattributes hasattributes_8 = new Item_Hasattributes();
				hasattributes_8.setAttribute("Extra Night");
				hasattributes_8.setAttribute_value("No Extra Night");
				hasattributes_8.setVariant_of(hotel_name);

				hotelattr.Allattributes(hasattributes);
				hotelattr.Allattributes(hasattributes_2);
				hotelattr.Allattributes(hasattributes_3);
				hotelattr.Allattributes(hasattributes_4);
				hotelattr.Allattributes(hasattributes_7);
				hotelattr.Allattributes(hasattributes_8);

				hotelattr.setItem_group("Hotels");
				System.out.println("item_group---" + hotelattr.getItem_group());
				hotelattr.setHas_variants(false);
				System.out.println("has_variants---" + hotelattr.isHas_variants());
				hotelattr.setStock_uom("Nos");
				System.out.println("stock_uom---" + hotelattr.getStock_uom());
				hotelattr.setIs_default(false);
				System.out.println("Is_default---" + hotelattr.isIs_default());
				hotelattr.setVariant_based_on("Item Attribute");
				System.out.println("variants_based_on---" + hotelattr.getVariant_based_on());
				hotelattr.setCategory("Hotel");
				System.out.println("category---" + hotelattr.getCategory());
				hotelattr.setVariant_of(hotel_name);
				System.out.println("variant_of---" + hotelattr.getVariant_of());
				if (rs.getString("price_category").equalsIgnoreCase("")
						|| rs.getString("price_category").equalsIgnoreCase("na")) {
					hotelattr.setPrice_category("Budget");
				} else if (rs.getString("price_category") != "" || rs.getString("price_category") != "NA") {
					hotelattr.setPrice_category(rs.getString("price_category"));
				}

				System.out.println("price_category---" + hotelattr.getPrice_category());
				hotelattr.setIs_stock_item(true);
				hotelattr.setInclude_item_in_manufacturing(true);

				// adding item_default values
				Item_Defaults item = new Item_Defaults();
				item.setCompany("Strawberi");
				hotelattr.Allcompany(item);
				System.out.println("company : " + item.getCompany() + "\n");

				JSONObject obj = new JSONObject(hotelattr);
				System.out.println("Item template Variants---" + obj + "\n");

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");
				Response r = rc.createItemjson(hotelattr);
				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));

				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

				k++;
				i++;
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setTemplate_hotel() {
		Connection conn = null;
		Statement stmt = null;
		Item2 tempattributes = new Item2();
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT distinct hotel_name from fit_hotels";
			//String sql = "SELECT distinct star_rating,hotel_name from fit_hotels";
//			String h="FIT-Maharashtra-001";
//			String sql = "SELECT distinct hotel_name from fit_hotels where package_id_list='"+h+"'";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			while (rs.next()) {

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");

				tempattributes.setItem_code(rs.getString("hotel_name"));
				System.out.println("Item_code---" + tempattributes.getItem_code());
				tempattributes.setItem_name(rs.getString("hotel_name"));
				System.out.println("item_name---" + tempattributes.getItem_name());
				//String rating=rs.getString("star_rating");
				setHotel_attributes(tempattributes.getItem_code(), tempattributes.getItem_name(), i);
				//setHotel_attributes(tempattributes.getItem_code(), tempattributes.getItem_name(), i,rating);
				i++;
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setHotel_attributes(String hotelid, String hotelname, int i) {
		RestAPI rc = new RestAPI();

		Item2 hotelattr = new Item2();
		Item_Hasattributes hasattributes = new Item_Hasattributes();
		hasattributes.setAttribute("Category");

		Item_Hasattributes hasattributes_2 = new Item_Hasattributes();
		hasattributes_2.setAttribute("STAR Rating");
		Item_Hasattributes hasattributes_3 = new Item_Hasattributes();
		hasattributes_3.setAttribute("Sharing Mode");
		Item_Hasattributes hasattributes_4 = new Item_Hasattributes();
		hasattributes_4.setAttribute("Boarding Type");
		Item_Hasattributes hasattributes_7 = new Item_Hasattributes();
		hasattributes_7.setAttribute("Room Service");
		Item_Hasattributes hasattributes_8 = new Item_Hasattributes();
		hasattributes_8.setAttribute("Extra Night");

		hotelattr.Allattributes(hasattributes);
		hotelattr.Allattributes(hasattributes_2);
		hotelattr.Allattributes(hasattributes_3);
		hotelattr.Allattributes(hasattributes_4);
		hotelattr.Allattributes(hasattributes_7);
		hotelattr.Allattributes(hasattributes_8);
		hotelattr.setItem_code(hotelid);
		hotelattr.setItem_name(hotelname);

		hotelattr.setItem_group("Hotels");
		System.out.println("item_group---" + hotelattr.getItem_group());
		hotelattr.setPrice_category("Budget");
		//hotelattr.setPrice_category(rating);
		System.out.println("price_category---" + hotelattr.getPrice_category());
		// hotelattr.setDescription(desc);

		hotelattr.setHas_variants(true);
		System.out.println("has_variants---" + hotelattr.isHas_variants());
		hotelattr.setVariant_based_on("Item Attribute");
		System.out.println("variants_based_on---" + hotelattr.getVariant_based_on());

		hotelattr.setStock_uom("Nos");
		System.out.println("stock_uom---" + hotelattr.getStock_uom());
		hotelattr.setCategory("Hotel");
		System.out.println("category---" + hotelattr.getCategory());

		// adding item_default values
		Item_Defaults item = new Item_Defaults();
		item.setCompany("Strawberi");
		hotelattr.Allcompany(item);
		System.out.println("company : " + item.getCompany() + "\n");

		JSONObject obj = new JSONObject(hotelattr);
		System.out.println("Item template---" + obj + "\n");
		System.out.println("--------------------------------------------------------PACKAGE " + i
				+ " IS ENDED--------------------------------------------------------\n");
		System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
				+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");
		Response r = rc.createItemjson(hotelattr);
		System.out.println("Status---" + r.getStatus());
		System.out.println("Status Info---" + r.getStatusInfo());
		System.out.println("Status Info---" + r.getStringHeaders());
		System.out.println(r.readEntity(String.class));

		System.out.println("\n");
		System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
				+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");
	}

	private static void setPrice_list_() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			String sql = "SELECT distinct package_id from fit_package_catalog_variants_price";
//			String pid="FIT-Singapore-0015";
//			String sql = "SELECT distinct package_id from fit_package_catalog_variants_price where package_id='"+pid+"'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String package_id = rs.getString("package_id");
				setPrice_list(package_id);
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setPrice_list(String package_id) throws ParseException {
		Connection conn = null;
		Statement stmt = null;
		RestAPI rc = new RestAPI();
		Price_List attributes = new Price_List();
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM fit_package_catalog_variants_price where package_id='" + package_id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			int j = 1;
			while (rs.next()) {
				System.out.println("--------------------------------------------------------PRICE PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");

				attributes.setCurrency(rs.getString("currency_1"));
				System.out.println("currency_1 : " + attributes.getCurrency() + "\n");
				attributes.setPrice_list_name(rs.getString("package_id") + "-Price-List-00" + j + "-01");
				System.out.println("package_id : " + attributes.getPrice_list_name() + "\n");
				attributes.setSeason(rs.getString("season_1"));
				System.out.println("season_1 : " + attributes.getSeason() + "\n");
				attributes.setStart_date(rs.getString("validity_start_date_1"));
				System.out.println("validity_start_date_1 : " + attributes.getStart_date() + "\n");
				attributes.setEnd_date(rs.getString("validity_end_date_1"));
				System.out.println("validity_end_date_1 : " + attributes.getEnd_date() + "\n");

				// adding Price_Countries values
				ArrayList<Price_Countries> countries = new ArrayList<Price_Countries>();
				Price_Countries country = new Price_Countries();
				country.setCountry(rs.getString("country_1"));
				System.out.println("country_1 : " + country.getCountry() + "\n");
				countries.add(country);
				String rating = null;
				String modeshare = null;
				if (rs.getString("star_rating").equalsIgnoreCase("")
						|| rs.getString("star_rating").equalsIgnoreCase("na")) {
					rating = "3";
				} else if (rs.getString("star_rating") != "" || (rs.getString("star_rating") != "NA")) {
					rating = rs.getString("star_rating");
				}

				if (rs.getString("sharing_mode").equalsIgnoreCase("")
						|| rs.getString("sharing_mode").equalsIgnoreCase("na")) {
					modeshare = "Twin";
				} else if (rs.getString("sharing_mode") != "" || (rs.getString("sharing_mode") != "NA")) {
					modeshare = rs.getString("sharing_mode");
				}

				attributes.setPrice_description(
						rating + " Star Hotels " + modeshare + " Sharing , Shared Transfers & Economy Flight");
				attributes.setSelling(true);
				System.out.println("selling : " + attributes.isSelling() + "\n");

				System.out.println("--------------------------------------------------------PRICE PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				System.out.println("--------------------------------------------------------INSERTING PRICE PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

				Response r = rc.createPrice_list(new Price_List(attributes.getPrice_list_name(), countries,
						attributes.getCurrency(), attributes.getSeason(), attributes.getStart_date(),
						attributes.getEnd_date(), attributes.isSelling(), attributes.getPrice_description()));

				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING PRICE PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

				// date conversion///

				/*
				 * DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); Date date =
				 * sdf.parse(attributes.getStart_date()); attributes.setStart_date(new
				 * SimpleDateFormat("yyyy-MM-dd").format(date));
				 * System.out.println("yyyy-MM-dd formatted date : " + new
				 * SimpleDateFormat("yyyy-MM-dd").format(date));
				 * 
				 * DateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy"); Date date1 =
				 * sdf1.parse(attributes.getEnd_date()); attributes.setEnd_date(new
				 * SimpleDateFormat("yyyy-MM-dd").format(date1));
				 * System.out.println("yyyy-MM-dd formatted date : " + new
				 * SimpleDateFormat("yyyy-MM-dd").format(date1));
				 */

				i++;
				j++;
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setProduct_bundle_() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			// String p = "FIT-China-0015";

			String sql = "select count(variants.package_id)as num,basic.item_code,basic.item_name,basic.description,basic.flights,basic.visa,basic.hotels,basic.sightseeing,basic.transfers,basic.passport,basic.forex,basic.insurance,basic.on_site_tour_manager from fit_package_catalog_variants_price variants,fit_package_catalog_basic basic where basic.item_code=variants.package_id group by package_id";
			//String sql = "select count(variants.package_id)as num,basic.item_code,basic.item_name,basic.description,basic.flights,basic.visa,basic.hotels,basic.sightseeing,basic.transfers,basic.passport,basic.forex,basic.insurance,basic.on_site_tour_manager from fit_package_catalog_variants_price variants,fit_package_catalog_basic basic where variants.package_id='"
				//	+ p + "' && basic.item_code=variants.package_id group by package_id";

			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			int count = 0;
			while (rs.next()) {

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				count = rs.getInt(1);
				System.out.println("count : " + count + "\n");
				String item_code = rs.getString("item_code");
				System.out.println("itemcode : " + item_code + "\n");

				item_name = rs.getString("item_name");
				// attributes.setNew_item_code(item_name + " PACKAGE");
				System.out.println("Item Name : " + item_name + "\n");
				introduction = rs.getString("description");
				// attributes.setIntroduction(rs.getString("description"));
				System.out.println("Introduction : " + introduction + "\n");
				flights = rs.getString("flights");
				System.out.println(flights);
				visa = rs.getString("visa");
				System.out.println(visa);
				hotels = rs.getString("hotels");
				System.out.println(hotels);
				sightseeing = rs.getString("sightseeing");
				System.out.println(sightseeing);
				transfer = rs.getString("transfers");
				System.out.println(transfer);
				passport= rs.getString("passport");
				System.out.println(passport);
				forex= rs.getString("forex");
				System.out.println(forex);
				insurance= rs.getString("insurance");
				System.out.println(insurance);
				tour_manager= rs.getString("on_site_tour_manager");
				System.out.println(tour_manager);

				setProduct_bundle(item_code, count);
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");

				i++;

			}

			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
	}

	private static void setItem() {
		String stock_uom = "Nos";
		String category = "Package";
		String company = "Strawberi";

		Connection conn = null;
		Statement stmt = null;
		RestAPI rc = new RestAPI();
		Item2 attributes = new Item2();
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT item_code, item_name,state,country,continent,supplier FROM fit_package_catalog_basic";
//			String itcode="FIT-Tarkarli-001";
//			String sql = "SELECT item_code, item_name,state,country,continent FROM fit_package_catalog_basic where item_code='"+itcode+"'";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 1;
			while (rs.next()) {

				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");
				attributes.setItem_code(rs.getString("item_code"));
				System.out.println("Item Code : " + attributes.getItem_code() + "\n");
				attributes.setItem_name(rs.getString("item_name"));
				System.out.println("Item Name : " + attributes.getItem_name() + "\n");

				attributes.setItem_group(rs.getString("state"));
				if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country").equalsIgnoreCase("")) {
					attributes.setItem_group(rs.getString("continent"));
				} else if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country") != "") {
					attributes.setItem_group(rs.getString("country"));
				}

				else if (rs.getString("state").equalsIgnoreCase("") && rs.getString("country").equalsIgnoreCase("")
						&& rs.getString("continent").equalsIgnoreCase("")) {
					attributes.setItem_group("All Item Groups");
				}

				System.out.println("Item Group : " + attributes.getItem_group() + "\n");
				// adding static values
				attributes.setStock_uom(stock_uom);
				System.out.println("stock_uom : " + attributes.getStock_uom() + "\n");
				attributes.setCategory(category);
				System.out.println("Category : " + attributes.getCategory() + "\n");

				// adding item_default values
				ArrayList<Item_Defaults> item_default = new ArrayList<Item_Defaults>();
				Item_Defaults item = new Item_Defaults();
				item.setCompany(company);
				item_default.add(item);
				System.out.println("company : " + item.getCompany() + "\n");
				
				
				ArrayList<Item_Defaults> item_default_2 = new ArrayList<Item_Defaults>();
				Item_Defaults item_2 = new Item_Defaults();
				item_2.setCompany(company);
				String sup = rs.getString("supplier");
				if (sup.equalsIgnoreCase("") || sup.equalsIgnoreCase("Na")) {
					item_2.setDefault_supplier(company);
				} else if (sup != "" || sup != "NA") {
					item_2.setDefault_supplier(sup);
				}
				item_default_2.add(item_2);
				
				ArrayList<Supplier_Items> supplier_items=new ArrayList<Supplier_Items>();
				Supplier_Items supitem=new Supplier_Items();
				if (sup.equalsIgnoreCase("") || sup.equalsIgnoreCase("Na")) {
					supitem.setSupplier(company);
				} else if (sup != "" || sup != "NA") {
					supitem.setSupplier(sup);
				}
				supplier_items.add(supitem);
				
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

				Response r = rc.createItemjson(new Item2(attributes.getItem_code() + " PACKAGE",
						attributes.getItem_name() + " PACKAGE", attributes.getStock_uom(), attributes.getItem_group(),
						attributes.getCategory(), false, false, item_default));
				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");

				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " Parent Item DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");
				Response r1 = rc.createItemjson(
						new Item2(attributes.getItem_code(), attributes.getItem_name(), attributes.getStock_uom(),
								attributes.getItem_group(), attributes.getCategory(), false, false, item_default_2,supplier_items));
				System.out.println("Status---" + r1.getStatus());
				System.out.println("Status Info---" + r1.getStatusInfo());
				System.out.println("Status Info---" + r1.getStringHeaders());
				System.out.println(r1.readEntity(String.class));
				System.out.println("\n");
				System.out.println("--------------------------------------------------------INSERTING PACKAGE" + i
						+ " Parent Item DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");
				i++;

			}

			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setProduct_bundle(String item_code, int count) {
		String qty = "1.000";
		Product_Bundle attributes = new Product_Bundle();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		RestAPI rc = new RestAPI();
		boolean found = false;
		String var = null;
		try {

			conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			String sql = "SELECT variants.isdefault,variants.price_category,variants.room_category,variants.star_rating,variants.sharing_mode,variants.boarding_type,variants.child,variants.extra_bed,variants.room_service,variants.extra_night,variants.vehicle_type,variants.group_size FROM fit_package_catalog_basic basic,fit_package_catalog_variants_price variants where variants.package_id ='"
					+ item_code + "' && basic.item_code=variants.package_id";
			ResultSet rs = stmt.executeQuery(sql);

			int j = 1;
			//int c = 0;
			//int k = 1;
			int m = 1;
			int b = 1;
			int l = 1;
			while (rs.next()) {
				System.out.println("--------------------------------------------------------VARIANTS ARRAY " + j
						+ " IS STARTED--------------------------------------------------------\n");

				Bundle_Variants varaint = new Bundle_Variants();

				if (rs.getString("price_category").equalsIgnoreCase("")
						|| rs.getString("price_category").equalsIgnoreCase("na")) {
					varaint.setPrice_category("Budget");
					System.out.println("Price_Category: " + varaint.getPrice_category() + "\n");

					varaint.setPackage_variant_id("B-00" + b);
					System.out.println("package_variant_id: " + varaint.getPackage_variant_id() + "\n");
					b++;
				} else if (rs.getString("price_category").equalsIgnoreCase("Economy")) {
					varaint.setPrice_category("Economy");
					System.out.println("Price_Category: " + varaint.getPrice_category() + "\n");

					varaint.setPackage_variant_id("E-00" + m);
					System.out.println("package_variant_id: " + varaint.getPackage_variant_id() + "\n");
					m++;
				} else if (rs.getString("price_category").equalsIgnoreCase("Luxury")) {
					varaint.setPrice_category("Luxury");
					System.out.println("Price_Category: " + varaint.getPrice_category() + "\n");

					varaint.setPackage_variant_id("L-00" + l);
					System.out.println("package_variant_id: " + varaint.getPackage_variant_id() + "\n");
					l++;
				} else if (rs.getString("price_category").equalsIgnoreCase("Budget")) {
					varaint.setPrice_category("Budget");
					System.out.println("Price_Category: " + varaint.getPrice_category() + "\n");

					varaint.setPackage_variant_id("B-00" + b);
					System.out.println("package_variant_id: " + varaint.getPackage_variant_id() + "\n");
					b++;

				}

				if (rs.getString("room_category").equalsIgnoreCase("")
						|| rs.getString("room_category").equalsIgnoreCase("na")) {
					varaint.setCategory("Standard");
					System.out.println("category: " + varaint.getCategory() + "\n");
				} else if (rs.getString("room_category") != "" || (rs.getString("room_category") != "NA")) {
					varaint.setCategory(rs.getString("room_category"));
					System.out.println("category: " + varaint.getCategory() + "\n");
				}

				if (rs.getString("star_rating").equalsIgnoreCase("")
						|| rs.getString("star_rating").equalsIgnoreCase("na")) {
					varaint.setStar_rating("3 STAR");
					System.out.println("star_rating: " + varaint.getStar_rating() + "\n");
				} else if (rs.getString("star_rating") != "" || (rs.getString("star_rating") != "NA")) {
					varaint.setStar_rating(rs.getString("star_rating") + " STAR");
					System.out.println("star_rating: " + varaint.getStar_rating() + "\n");
				}

				if (rs.getString("sharing_mode").equalsIgnoreCase("")
						|| rs.getString("sharing_mode").equalsIgnoreCase("na")) {
					varaint.setSharing_mode("Twin");
					System.out.println("sharing_mode: " + varaint.getSharing_mode() + "\n");
				} else if (rs.getString("sharing_mode") != "" || (rs.getString("sharing_mode") != "NA")) {
					varaint.setSharing_mode(rs.getString("sharing_mode"));
					System.out.println("sharing_mode: " + varaint.getSharing_mode() + "\n");
				}

				varaint.setBoarding_type(rs.getString("boarding_type"));
				System.out.println("boarding_type: " + varaint.getBoarding_type() + "\n");

//				if (rs.getString("child").equalsIgnoreCase("") || rs.getString("child").equalsIgnoreCase("na")) {
//					varaint.setChild("No Child");
//					System.out.println("child: " + varaint.getChild() + "\n");
//				} else if (rs.getString("child").equalsIgnoreCase("Child with bed")) {
//					varaint.setChild("Child With Bed");
//					System.out.println("child: " + varaint.getChild() + "\n");
//				} else if (rs.getString("child").equalsIgnoreCase("Child without bed")) {
//					varaint.setChild("Child w/o Bed");
//					System.out.println("child: " + varaint.getChild() + "\n");
//				}

				if (rs.getString("room_service").equalsIgnoreCase("")
						|| rs.getString("room_service").equalsIgnoreCase("na")) {
					varaint.setRoom_service("No Room Service");
					System.out.println("room_service: " + varaint.getRoom_service() + "\n");
				} else if (rs.getString("room_service") != "" || (rs.getString("room_service") != "NA")) {
					varaint.setRoom_service(rs.getString("room_service"));
					System.out.println("room_service: " + varaint.getRoom_service() + "\n");
				}

				if (rs.getString("extra_night").equalsIgnoreCase("")
						|| rs.getString("extra_night").equalsIgnoreCase("na")) {
					varaint.setExtra_night("No Extra Night");
					System.out.println("extra_night: " + varaint.getExtra_night() + "\n");
				} else if (rs.getString("extra_night") != "" || (rs.getString("extra_night") != "NA")) {
					varaint.setExtra_night(rs.getString("extra_night"));
					System.out.println("extra_night: " + varaint.getExtra_night() + "\n");
				}
//				if (rs.getString("extra_bed").equalsIgnoreCase("")
//						|| rs.getString("extra_bed").equalsIgnoreCase("na")) {
//					varaint.setExtra_bed("No Extra Bed");
//					System.out.println("extra_bed: " + varaint.getExtra_bed() + "\n");
//				} else if (rs.getString("extra_bed") != "" || (rs.getString("extra_bed") != "NA")) {
//					varaint.setExtra_bed(rs.getString("extra_bed"));
//					System.out.println("extra_bed: " + varaint.getExtra_bed() + "\n");
//				}

				varaint.setVehicle_type(rs.getString("vehicle_type"));
				System.out.println("vehicle_type: " + varaint.getVehicle_type() + "\n");
				varaint.setGroup_size(rs.getString("group_size"));
				System.out.println("group_size: " + varaint.getGroup_size() + "\n");
				if (rs.getString("isdefault").equalsIgnoreCase("yes")) {
					varaint.setIs_default(true);
				} else if (rs.getString("isdefault").equalsIgnoreCase("no")) {
					varaint.setIs_default(false);
				}
				System.out.println("is_default: " + varaint.isIs_default() + "\n");

				System.out.println("--------------------------------------------------------VARIANTS ARRAY " + j
						+ " IS ENDED--------------------------------------------------------\n");

				attributes.Allvariants(varaint);

				System.out.println("--------------------------------------------------------PRICELIST ARRAY " + j
						+ " IS STARTED--------------------------------------------------------\n");

				Bundle_Price_List_Details price = new Bundle_Price_List_Details();
				price.setPackage_variant_id(varaint.getPackage_variant_id());
				System.out.println("price_package_variant_id: " + price.getPackage_variant_id() + "\n");
				price.setPrice_list_1(item_code + "-Price-List-00" + j + "-01");

				System.out.println("--------------------------------------------------------PRICELIST ARRAY " + j
						+ " IS ENDED--------------------------------------------------------\n");
				attributes.Allpricelist(price);

				j++;
				//c++;
				//k++;

			}

			////////////////////////// itinenrary///////////////////////////////////

			stmt2 = conn.createStatement();
			// String cat="Transfer";
			String sql2 = "SELECT * FROM fit_package_catalog_itinerary where package_id ='" + item_code + "'";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			Items mainobj2 = new Items();
			mainobj2.setItem_code(item_code + " PACKAGE");
			mainobj2.setQty(qty);
			mainobj2.setCategory("Package");
			attributes.Allitems(mainobj2);
			int m2 = 1;
			//String itm_arr = null;
			while (rs2.next()) {
				System.out.println("--------------------------------------------------------ITINERARY ARRAY " + m2
						+ " IS STARTED--------------------------------------------------------\n");

				if (rs2.getString("category").equalsIgnoreCase("")
						|| rs2.getString("category").equalsIgnoreCase("NA")) {

					found = false;
				}

				if (rs2.getString("category").equalsIgnoreCase("Attraction")) {
					found = false;

					if (rs2.getString("product").equalsIgnoreCase("NA")
							|| rs2.getString("product").equalsIgnoreCase("")) {
						found = false;
					}

					else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {
						found = checkProductAttraction(rs2.getString("product"));
					}

				}

				if (rs2.getString("category").equalsIgnoreCase("Transfer")) {
					found = false;
					if (rs2.getString("product").equalsIgnoreCase("NA")
							|| rs2.getString("product").equalsIgnoreCase("")) {
						found = false;
					}

					else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {
						var = setTransport_dummy(rs2.getString("product"));

						if (var != null) {
							found = true;
						}
					}

				}

				if (rs2.getString("category").equalsIgnoreCase("Hotel")) {
					found = false;
					if (rs2.getString("product").equalsIgnoreCase("NA")
							|| rs2.getString("product").equalsIgnoreCase("")) {
						found = false;
					}

					else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {
						found = checkProducthotels(rs2.getString("product"));
					}

				}

				Bundle_Itinerary itinerary = new Bundle_Itinerary();
				itinerary.setDay_no(rs2.getString("day"));
				System.out.println("day: " + itinerary.getDay_no() + "\n");
				itinerary.setSequence_number(rs2.getString("sequence"));
				System.out.println("sequence number: " + itinerary.getSequence_number() + "\n");
				itinerary.setTitle(rs2.getString("title"));
				System.out.println("title: " + itinerary.getTitle() + "\n");
				itinerary.setPlace(rs2.getString("place"));
				System.out.println("place: " + itinerary.getPlace() + "\n");

				if (rs2.getString("category").equalsIgnoreCase("Attraction")) {
					itinerary.setCategory("Sightseeing");
					System.out.println("category: " + itinerary.getCategory() + "\n");

					if (found == true) {
						itinerary.setProduct(rs2.getString("product"));
						System.out.println("product: " + itinerary.getProduct() + "\n");
					} else if (found == false) {
						itinerary.setProduct("");
						System.out.println("ATTRACTION NOT FOUND IN DB----------------------------------------------"
								+ rs2.getString("product"));
					}

				} else if (rs2.getString("category").equalsIgnoreCase("Transfer")) {
					itinerary.setCategory("Transfer");
					System.out.println("category: " + itinerary.getCategory() + "\n");

					if (found == true) {
//						itinerary.setProduct(rs2.getString("product") + "-" + var);
//						itm_arr = rs2.getString("product") + "-" + var;
						itinerary.setProduct(rs2.getString("product"));
						//itm_arr = rs2.getString("product");
						System.out.println("product: " + itinerary.getProduct() + "\n");
					} else if (found == false) {
						itinerary.setProduct("");
						System.out.println("TRASNFER NOT FOUND IN DB----------------------------------------------"
								+ rs2.getString("product"));
					}
				}

				else if (rs2.getString("category").equalsIgnoreCase("Hotel")) {
					itinerary.setCategory("Hotel");
					System.out.println("category: " + itinerary.getCategory() + "\n");
					if (found == true) {
						String[] sep = null;
						sep = rs2.getString("product").split("[-]");
						itinerary.setProduct(sep[0]);
						System.out.println("product: " + itinerary.getProduct() + "\n");
					} else if (found == false) {
						itinerary.setProduct("");
						System.out.println("HOTELS NOT FOUND IN DB----------------------------------------------"
								+ rs2.getString("product"));
					}
				} else if (rs2.getString("category").equalsIgnoreCase("")
						|| rs2.getString("category").equalsIgnoreCase("NA")) {
					itinerary.setCategory("");
					System.out.println("category: " + itinerary.getCategory() + "\n");
					itinerary.setProduct("");
					System.out.println("CATEGORY NA IN DB----------------------------------------------"
							+ rs2.getString("product"));
				}
				itinerary.setDescription(rs2.getString("description"));
				System.out.println("description: " + itinerary.getDescription() + "\n");

				System.out.println("--------------------------------------------------------ITINERARY ARRAY " + m2
						+ " IS ENDED--------------------------------------------------------\n");

				attributes.Allitinerary(itinerary);

				if (found == true) {
					System.out.println("--------------------------------------------------------ITEMS ARRAY " + m2
							+ " IS STARTED--------------------------------------------------------\n");

					Items mainobj = new Items();
					mainobj.setQty(qty);
					mainobj.setLocation(rs2.getString("place"));
					if (rs2.getString("category").equalsIgnoreCase("Attraction")) {
						mainobj.setCategory("Sightseeing");
						System.out.println("category: " + mainobj.getCategory() + "\n");
						System.out.println("category: " + itinerary.getCategory() + "\n");
						if (rs2.getString("product").equalsIgnoreCase("NA")
								|| rs2.getString("product").equalsIgnoreCase("")) {
							mainobj.setItem_code("");
						} else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {

							mainobj.setItem_code(itinerary.getProduct());
							// mainobj.setItem_code(rs2.getString("product"));
						}
					} else if (rs2.getString("category").equalsIgnoreCase("Transfer")) {
						mainobj.setCategory("Transfer");
						System.out.println("category: " + mainobj.getCategory() + "\n");
						if (rs2.getString("product").equalsIgnoreCase("NA")
								|| rs2.getString("product").equalsIgnoreCase("")) {
							mainobj.setItem_code("");
						} else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {
							// mainobj.setItem_code(itm_arr);
							mainobj.setItem_code(itinerary.getProduct());
						}
					} else if (rs2.getString("category").equalsIgnoreCase("Hotel")) {
						mainobj.setCategory("Hotel");
						System.out.println("category: " + mainobj.getCategory() + "\n");
						if (rs2.getString("product").equalsIgnoreCase("NA")
								|| rs2.getString("product").equalsIgnoreCase("")) {
							mainobj.setItem_code("");
						} else if (rs2.getString("product") != "NA" || rs2.getString("product") != "") {
//						String[] sep = null;
//						sep = rs2.getString("product").split("[-]");
//						mainobj.setItem_code(sep[0]);
							mainobj.setItem_code(itinerary.getProduct());
						}
					}

					System.out.println("itemcode-->" + mainobj.getItem_code());
					System.out.println("qty-->" + mainobj.getQty());
					System.out.println("location-->" + mainobj.getLocation());
					System.out.println("category: " + mainobj.getCategory() + "\n");
					attributes.Allitems(mainobj);
					System.out.println("--------------------------------------------------------ITEMS ARRAY " + m2
							+ " IS ENDED--------------------------------------------------------\n");
					m2++;

				}
			}
			attributes.setNew_item_code(item_code);
			System.out.println(attributes.getNew_item_code());
			attributes.setPackage_name(item_name);
			System.out.println("Item Name : " + item_name + "\n");
			attributes.setIntroduction(introduction);
			System.out.println(attributes.getIntroduction());
			attributes.setCategory("Bundle");
			System.out.println(attributes.getCategory());
			if (visa.equalsIgnoreCase("yes")) {
				attributes.setVisa(true);
				System.out.println(attributes.isVisa());
			}
			if (sightseeing.equalsIgnoreCase("yes")) {
				attributes.setSightseeing(true);
				System.out.println(attributes.isSightseeing());
			}
			if (flights.equalsIgnoreCase("yes")) {
				attributes.setFlights(true);
				System.out.println(attributes.isFlights());
			}
			if (hotels.equalsIgnoreCase("yes")) {
				attributes.setHotels(true);
				System.out.println(attributes.isHotels());
			}
			if (transfer.equalsIgnoreCase("yes")) {
				attributes.setTransfer(true);
				System.out.println(attributes.isTransfer());
			}
			if (passport.equalsIgnoreCase("yes")) {
				attributes.setPassport(true);
				System.out.println(attributes.isPassport());
			}
			if (forex.equalsIgnoreCase("yes")) {
				attributes.setForex(true);
				System.out.println(attributes.isForex());
			}
			if (insurance.equalsIgnoreCase("yes")) {
				attributes.setInsurance(true);
				System.out.println(attributes.isInsurance());
			}
			if (tour_manager.equalsIgnoreCase("yes")) {
				attributes.setTour_manager(true);
				System.out.println(attributes.isTour_manager());
			}
			
			
			JSONObject job = new JSONObject(attributes);
			System.out.println("Product Bundle--> " + job);

			System.out.println("--------------------------------------------------------INSERTING PACKAGE" + j
					+ " DATA IN ERPNEXT SETUP IS STARTED--------------------------------------------------------\n");

			Response r = rc.createProduct_bundle(attributes);
			System.out.println("Status---" + r.getStatus());
			System.out.println("Status Info---" + r.getStatusInfo());
			System.out.println("Status Info---" + r.getStringHeaders());
			System.out.println(r.readEntity(String.class));
			System.out.println("\n");
			System.out.println("--------------------------------------------------------INSERTING PACKAGE" + j
					+ " DATA IN ERPNEXT SETUP IS ENDED--------------------------------------------------------\n");
			DBConnection.closeResultSet(rs2);
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static boolean checkProducthotels(String string) {
		Connection conn = null;
		Statement stmt = null;
		boolean found = false;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT hotel_id from fit_hotels where hotel_id='" + string + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == true) {

				String hotelid = rs.getString("hotel_id");
				System.out.println(hotelid);
				found = true;

			} else if (rs.next() == false) {
				System.out.println("NOT FOUND____________________");
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

		return found;
	}

	private static boolean checkProductAttraction(String string) {
		Connection conn = null;
		Statement stmt = null;
		boolean found = false;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT attraction_id from fit_attraction where attraction_id='" + string + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() == true) {

				String attid = rs.getString("attraction_id");
				System.out.println(attid);
				found = true;

			} else if (rs.next() == false) {
				System.out.println("NOT FOUND_______________________");
			}
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

		return found;
	}

	private static String setTransport_dummy(String product) {
		Connection conn = null;
		Statement stmt3 = null;
		String var = null;
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			System.out.println("checkinfg Variantssssssss-------itemcode----" + product + "\n");
			// String cat="Transfer";
			String sql22 = "SELECT Transfer_service_id FROM fit_transfer_service where Transfer_service_id ='" + product
					+ "'";
			ResultSet rs22 = stmt3.executeQuery(sql22);

			if (rs22.next() == true) {
				// var = rs22.getString("variant_name");
				var = rs22.getString("Transfer_service_id");
				System.out.println("checkinfg Variantssssssss-----------\n");
				// itinerary.setProduct(item_code+"-"+rs22.getString("variant_name"));
				// System.out.println("product: " + itinerary.getProduct() + "\n");
				System.out.println("Transport Variantssssssss-----------\n");
			} else if (rs22.next() == false) {
				System.out.println("NOT FOUNDD_____________________");
			}
			DBConnection.closeResultSet(rs22);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
		return var;
	}

	private static void setProductBundleAttributes() {
		RestAPI rc = new RestAPI();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Product_Bundle_Attribute pba = new Product_Bundle_Attribute();

		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from fit_package_catalog_basic";
//			String codei = "FIT-Singapore-0015";
//			String sql = "select * from fit_package_catalog_basic where item_code='" + codei + "'";
			rs = stmt.executeQuery(sql);
			int i = 1;
			System.out.println(
					"/**************************  setProductBundleAttributes Start Here.  *******************************/");

			while (rs.next()) {

				// System.out.println("item_code : " + rs.getString("item_code"));

				// System.out.println("item_name : " + rs.getString("item_name"));
				pba.setProduct_bundle(rs.getString("item_code"));
				System.out.println("Pacakege  ----------" + pba.getProduct_bundle());

				pba.setCurrency(rs.getString("currency"));
				System.out.println("currency : " + pba.getCurrency());

				pba.setMinimum_price(rs.getString("minimum_price"));
				System.out.println("Minimum  Price : " + pba.getMinimum_price());

				pba.setMaximum_price(rs.getString("maximum_price"));
				System.out.println("Maximum  Price : " + pba.getMaximum_price());

				pba.setSeason(rs.getString("season"));
				System.out.println("Season : " + pba.getSeason());

				pba.setContinent(rs.getString("continent"));
				System.out.println("Continent : " + pba.getContinent());

				pba.setCountry(rs.getString("country"));
				System.out.println("Country : " + pba.getCountry());

				pba.setState(rs.getString("state"));
				System.out.println("State : " + pba.getState());

				pba.setTheme(rs.getString("theme"));
				System.out.println("Theme : " + pba.getTheme());

				pba.setTags(rs.getString("tags"));
				System.out.println("Tags : " + pba.getTags());

				pba.setTour_type(rs.getString("tour_type"));
				System.out.println("tour_type : " + pba.getTour_type());

				pba.setTour_category(rs.getString("tour_category"));
				System.out.println("tour_category : " + pba.getTour_category());

				pba.setStar_rating_(rs.getString("star_rating"));
				System.out.println("Star_rating : " + pba.getStar_rating_());

//				pba.setDestination(rs.getString("description"));
//				System.out.println("description : " + pba.getDestination());

				pba.setDefault_selling_price(rs.getString("default_selling_price"));
				System.out.println("default_selling_price : " + pba.getDefault_selling_price());

				pba.setPrice_description(rs.getString("price_description"));
				System.out.println("price_description : " + pba.getPrice_description());
				pba.setNumber_of_days(rs.getString("number_of_days"));
				System.out.println("number_of_days : " + pba.getNumber_of_days());
				pba.setNumber_of_nights(rs.getString("number_of_nights"));
				System.out.println("number_of_nights : " + pba.getNumber_of_nights());
				JSONObject job = new JSONObject(pba);
				System.out.println("jsob--> " + job);

				Response r = rc.createProduct_bundle_attribute(pba);
				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				i++;

			}
			System.out.println(i);
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}

	}

	private static void setLandPackageAttributes() {
		RestAPI rc = new RestAPI();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			// Land_Package_Attributes landpkgatt=new Land_Package_Attributes();
			JSONObject job = null;

			String sql = "select * from fit_package_catalog_basic";
//	            String itmc="FIT-Gujarat-001";
//	            String sql = "select * from fit_package_catalog_basic where item_code='"+itmc+"'";

			System.out.println("fit_package_catalog_basic----->" + sql);
			rs = stmt.executeQuery(sql);
			int i = 1;

			while (rs.next()) {
				Land_Package_Attributes landpkgatt = new Land_Package_Attributes();

				landpkgatt.setItem(rs.getString("item_code") + " PACKAGE");
				System.out.println("item_code : " + landpkgatt.getItem());
				// System.out.println("item_name : " + rs.getString("item_name"));

				landpkgatt.setNumber_of_days(rs.getString("number_of_days"));
				System.out.println("Number Of Days : " + landpkgatt.getNumber_of_days());

				landpkgatt.setNumber_of_nights(rs.getString("number_of_nights"));
				System.out.println("Number Of Nights : " + landpkgatt.getNumber_of_nights());

				landpkgatt.setValidity_from(rs.getString("validity_from"));
				System.out.println("Validity From : " + landpkgatt.getValidity_from());

				landpkgatt.setValidity_up_to(rs.getString("validity_to"));
				System.out.println("Validity To : " + landpkgatt.getValidity_up_to());

				landpkgatt.setMinimum_price(rs.getString("minimum_price"));
				System.out.println("Minimum  Price : " + landpkgatt.getMinimum_price());

				landpkgatt.setMaximum_price(rs.getString("maximum_price"));
				System.out.println("Maximum  Price : " + landpkgatt.getMaximum_price());

				landpkgatt.setSeason(rs.getString("season"));
				System.out.println("Season : " + landpkgatt.getSeason());

				landpkgatt.setContinent(rs.getString("continent"));
				System.out.println("Continent : " + landpkgatt.getContinent());

				landpkgatt.setCountry(rs.getString("country"));
				System.out.println("Country : " + landpkgatt.getCountry());

				landpkgatt.setState(rs.getString("state"));
				System.out.println("State : " + landpkgatt.getState());

				landpkgatt.setTheme(rs.getString("theme"));
				System.out.println("Theme : " + landpkgatt.getTheme());

				landpkgatt.setTags(rs.getString("tags"));
				System.out.println("Tags : " + landpkgatt.getTags());
				System.out.println("");

				/****************** Places Start ************************/

				Connection conn1 = null;
				Statement stmt1 = null;
				ResultSet rs1 = null;

				try {
					conn1 = DBConnection.getConnection();
					stmt1 = conn1.createStatement();

					String sql1 = "select * from fit_package_catalog_places where package_id='"
							+ rs.getString("item_code") + "'";
					System.out.println("fit_package_catalog_places Query----> " + sql1);
					rs1 = stmt1.executeQuery(sql1);
					System.out.println();

					while (rs1.next()) {
						Package_Attribute_Places pap = new Package_Attribute_Places();
						System.out.println("package_id          -----> " + rs1.getString("package_id"));
						System.out.println("place_name          -----> " + rs1.getString("place_name"));
						System.out.println("sequence_no          -----> " + rs1.getString("sequence_no"));
						System.out.println("number_of_days      -----> " + rs1.getString("number_of_days"));
						System.out.println("number_of_nights    -----> " + rs1.getString("number_of_nights"));
						System.out.println("latitude            -----> " + rs1.getString("latitude"));
						System.out.println("longitude           -----> " + rs1.getString("longitude"));
						System.out.println("zipcode             -----> " + rs1.getString("zipcode"));
						System.out.println("");
						pap.setSequence_number(rs1.getString("sequence_no"));
						pap.setPlace(rs1.getString("place_name"));
						pap.setNumber_of_days(rs1.getString("number_of_days"));
						pap.setNumber_of_nights(rs1.getString("number_of_nights"));
						landpkgatt.AllPalces(pap);
					}
					DBConnection.closeResultSet(rs1);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DBConnection.closeStatement(stmt1);
					DBConnection.closeConnection(conn1);

				}
				/****************** Places End ************************/

				/****************** Calender ************************/

				Connection conn11 = null;
				Statement stmt11 = null;
				ResultSet rs11 = null;
				try {
					conn11 = DBConnection.getConnection();
					stmt11 = conn11.createStatement();

					String sql11 = "select * from fit_package_catalog_calender where package_id='"
							+ rs.getString("item_code") + "'";
					System.out.println("fit_package_catalog_calender Query----> " + sql11);
					rs11 = stmt11.executeQuery(sql11);
					System.out.println();

					if (rs11.next() == false) {
						System.out.println("ResultSet in empty in Java");
						job = new JSONObject(landpkgatt);

					} else {

						do {
							Package_Attribute_Special_Event pase = new Package_Attribute_Special_Event();

							System.out.println("package_id   -----> " + rs11.getString("package_id"));
							System.out.println("place_name   -----> " + rs11.getString("place_name"));
							System.out.println("start_date   -----> " + rs11.getString("start_date"));
							System.out.println("end_date     -----> " + rs11.getString("end_date"));
							System.out.println("type         -----> " + rs11.getString("type"));
							//System.out.println("description  -----> " + rs11.getString("description"));
							System.out.println("status       -----> " + rs11.getString("status"));
							pase.setPlace_name(rs11.getString("place_name"));
							pase.setStart_date(rs11.getString("start_date"));
							pase.setEnd_date(rs11.getString("end_date"));
							pase.setType(rs11.getString("type"));
							pase.setStatus(rs11.getString("status"));
							landpkgatt.AllEvents(pase);
							job = new JSONObject(landpkgatt);
							System.out.println("");

						} while (rs11.next());
					}
					DBConnection.closeResultSet(rs11);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DBConnection.closeStatement(stmt11);
					DBConnection.closeConnection(conn11);

				}

				/****************** Calender ************************/

				System.out.println("Final result of All------------>" + job);
				Response r = rc.createLand_package_attributes(landpkgatt);
				System.out.println("Status---" + r.getStatus());
				System.out.println("Status Info---" + r.getStatusInfo());
				System.out.println("Status Info---" + r.getStringHeaders());
				System.out.println(r.readEntity(String.class));
				System.out.println("\n");
				i++;
			}
			System.out.println(i);
			DBConnection.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);

		}

	}

}