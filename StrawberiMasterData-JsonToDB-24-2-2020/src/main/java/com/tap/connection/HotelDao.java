package com.tap.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HotelDao {

	public static void main(String[] args) {
//	 String hotel_name="Grande Vista";
//		getCounthotel(hotel_name);
	}

	private static void add_Package_id(String hotel_id, String package_id, String hotel_name, String pcategory, String hotel_name2) {
		Connection conn = null;
		Statement stmt3 = null;
		System.out.println(hotel_id);
		String pid = (hotel_id + "," + package_id);
		System.out.println(pid);
		System.out.println(hotel_name);
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "update fit_hotels set package_id_list ='" + pid + "' where hotel_id='" + hotel_name + "'";
			int rs = stmt3.executeUpdate(sql22);
			System.out.println(rs);
			System.out.println("sssssssssssssssssssssss");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
	}

	public static String check_Hotel_name(String hotel_name, String package_id, String pcat, String hotel_name2) {
		String hotel_id = "p";
		Connection conn = null;
		Statement stmt = null;
		String found = null;
		String pcategory=null;
		try {
			conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			String sql = "select package_id_list,price_category from fit_hotels where hotel_id='" + hotel_name + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				hotel_id = rs.getString("package_id_list");
				pcategory=rs.getString("price_category");
				System.out.println(hotel_id + "\n");

			}
			Set<String> hashSet = new HashSet<String>(Arrays.asList(hotel_id.split(",")));
			if (hotel_id.equalsIgnoreCase("p")) {
				System.out.println("HOTEL NAME NOT FOUND IN DATABASE:\n");
				found = "No";

			} else if (hotel_id != "p") {

				if (hashSet.contains(package_id)) {
					System.out.println("HOTEL NAME Repeated in same package:\n");
					found = "NA";
					if(pcat.equalsIgnoreCase(pcategory))
					{
						System.out.println("HOTEL NAME Repeated in same package with same category:\n");
						found="Yes";	
					}
					
				} else {
					System.out.println("HOTEL ARLREADY PRESENT IN DATABASE \n");
					System.out.println("CHECK PRICE CATEGORY___________ \n");
					if(pcat.equalsIgnoreCase(pcategory))
					{
						System.out.println("HOTEL ARLREADY PRESENT IN DATABASE ADD PACKAGE _IDDDD_______\n");
						add_Package_id(hotel_id, package_id, hotel_name,pcategory,hotel_name2);
						System.out.println("SUCCESSFULLY UPDATED PACKAGE_ID \n");
						found = "Yes";
					}
					else if(pcat!=pcategory)
					{
						System.out.println("CATEGORY IS DIFFERENT IN TWO DIFFERENT PACKAGES");
						found = "NA";
					}
					
				}
			}

			DBConnection.closeResultSet(rs);
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
		return found;

	}

	public static void setHotel_data(String star_rating, String room_category, String price_category, String basis,
			String sharing_mood, String variant_type, String hotel_name, String hotel_id, String package_id,
			String place, String originalcat, String variant_name) {
		Connection conn = null;
		Statement stmt3 = null;
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "insert into fit_hotels values ('','" + package_id + "','" + place + "','" + hotel_name
					+ "','" + hotel_id + "','','" + variant_name + "','" + variant_type + "','" + star_rating + "','"
					+ price_category + "','" + room_category + "','" + basis + "','" + sharing_mood
					+ "','','','','','','','','','','','','','','','','','','','','','','','','','" + originalcat
					+ "','" + basis + "')";
			int rs = stmt3.executeUpdate(sql22);
			System.out.println("SUCCESSFULLY inserted Data:"+rs+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
	}

	public static String[] getplaceName(String package_id) {
		Connection conn = null;
		Statement stmt3 = null;
		String var[] = new String[1];
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "select distinct place_name from fit_package_catalog_places where package_id='" + package_id
					+ "'";
			ResultSet rs22 = stmt3.executeQuery(sql22);

			while (rs22.next()) {
				var[0] = rs22.getString("place_name");
			}
			DBConnection.closeResultSet(rs22);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
		return var;
	}

	public static boolean checkPresent_in_db(String package_id) {

		Connection conn = null;
		Statement stmt3 = null;
		String var = null;
		boolean pid = false;
		String packageidlist = null;

		try {
			conn = DBConnection.getConnection();
			stmt3 = conn.createStatement();
			String sql22 = "select distinct package_id_list from fit_hotels";
			ResultSet rs22 = stmt3.executeQuery(sql22);
			int i = 1;
			while (rs22.next()) {
				var = rs22.getString("package_id_list");
				if (i == 1) {
					packageidlist = var;
					i++;
				} else if (i > 1) {
					packageidlist = packageidlist + "," + var;
					i++;
				}
			}

			if (var != null) {
				System.out.println(packageidlist);
				Set<String> hashSet = new HashSet<String>(Arrays.asList(packageidlist.split(",")));

				if (hashSet.contains(package_id)) {
					System.out.println("PACKAGE NAME DUPLICATE IN DATABASE \n");
					pid = true;
				} else {
					System.out.println("PACKAGE NAME NOT DUPLICATE IN DATABASE:\n");
					pid = false;
				}
			}

			DBConnection.closeResultSet(rs22);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}

		return pid;
	}

	public static int getCounthotel(String hotel_name) {

		Connection conn = null;
		Statement stmt3 = null;
		int var = 0;
		
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "select count(hotel_name) from fit_hotels where hotel_name='" + hotel_name + "'";
			ResultSet rs22 = stmt3.executeQuery(sql22);
			while (rs22.next()) {
				var = rs22.getInt("count(hotel_name)");
			}
			System.out.println(var);
			DBConnection.closeResultSet(rs22);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
		return var;
	}

}
