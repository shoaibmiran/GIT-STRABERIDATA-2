package com.tap.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tap.model.VariantPrice;

public class VariantPriceDao {

	public static void main(String[] args) {
//		String name[]=getVariant_name("FIT-Singapore-001");
//		System.out.println("vvvvvvv"+name[0]);
//		System.out.println("vvvvvvv"+name[1]);
//		String n="INR 999";
//		String[] currency=n.split("[ ]");
//		System.out.println(currency[0]);
//		System.out.println(currency[1]);
	}

	public static void seVariant_Price(VariantPrice vp) {
		Connection conn = null;
		Statement stmt3 = null;
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "insert into fit_package_catalog_variants_prices values '','" + vp.getPackage_id() + "','"
					+ vp.getIs_default() + "','" + vp.getVariant_name() + "','" + vp.getPrice_category() + "','"
					+ vp.getVariant_type() + "','" + vp.getRoom_category() + "','" + vp.getStar_rating() + "','"
					+ vp.getBasis() + "','" + vp.getSharing_mood() + "','" + vp.getBoarding_type() + "','"
					+ vp.getRoom_service() + "','" + vp.getTransfer_type() + "','" + vp.getVehicle_type() + "','"
					+ vp.getGroup_size() + "','" + vp.getAdult_count() + "','" + vp.getChild_count() + "','"
					+ vp.getInfant_count() + "','" + vp.getMeal_category() + "','" + vp.getMeal_type() + "','"
					+ vp.getSupplier_1() + "','" + vp.getCountry_1() + "','" + vp.getCurrency_1() + "','"
					+ vp.getVal_st_date_1() + "','" + vp.getVal_ed_date_1() + "','" + vp.getSeason_1() + "','"
					+ vp.getPrice_1() + "','" + vp.getSupplier_2() + "','" + vp.getCountry_2() + "','"
					+ vp.getCurrency_2() + "','" + vp.getVal_st_date_2() + "','" + vp.getVal_ed_date_2() + "','"
					+ vp.getSeason_2() + "','" + vp.getPrice_2() + "','" + vp.getSupplier_3() + "','"
					+ vp.getCountry_3() + "','" + vp.getCurrency_3() + "','" + vp.getVal_st_date_3() + "','"
					+ vp.getVal_ed_date_3() + "','" + vp.getSeason_3() + "','" + vp.getPrice_3() + "')";
			int rs = stmt3.executeUpdate(sql22);
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}
	}

	@SuppressWarnings("null")
	public static String[] getVariant_name(String package_id) {
		Connection conn = null;
		Statement stmt3 = null;
		String var = null;
		String packageidlist = null;
		String transfertype = null;
		String vehiceltype = null;
		String dat[] = new String[2];
		String storettype = null;
		try {
			conn = DBConnection.getConnection();
			stmt3 = conn.createStatement();
			String sql22 = "select distinct package_id_list,transfer_type,vehicle_type from fit_transfer_service";
			ResultSet rs22 = stmt3.executeQuery(sql22);
			int i = 1;
			while (rs22.next()) {
				var = rs22.getString("package_id_list");
				packageidlist = var;
				transfertype = rs22.getString("transfer_type");
				vehiceltype = rs22.getString("vehicle_type");
				
//				################to overcome the problem of(, ) in database
				Set<String> hashSet = new HashSet<String>(Arrays.asList(packageidlist.split(", ")));
				if (hashSet.contains(package_id)) {
					System.out.println("TRANSFER TYPE FOUND IN DATABASE \n");
					if (i == 1) {
						storettype = transfertype;

					}
//					if (i == 2) {
//						if (storettype.equalsIgnoreCase(transfertype)) {
//							System.out.println("SMAE TYPE REAPEATED");
//						} else if (storettype != transfertype) {
//							storettype = storettype + "," + transfertype;
//						}
//
//					}

					else if (i > 1) {
						Set<String> hashSet1 = new HashSet<String>(Arrays.asList(storettype.split(",")));
						if (hashSet1.contains(transfertype)) {
							System.out.println("SMAE TYPE REAPEATED");
						} else {
							storettype = storettype + "," + transfertype;
						}

					}
					dat[0] = storettype;
					dat[1] = vehiceltype;
					i++;

				}
				
			//	################to overcome the problem of(,) in database

//				Set<String> hashSet2 = new HashSet<String>(Arrays.asList(packageidlist.split(",")));
//				if (hashSet.contains(package_id)) {
//					System.out.println("TRANSFER TYPE FOUND IN DATABASE \n");
//					if (i == 1) {
//						storettype = transfertype;
//
//					} else if (i > 1) {
//						Set<String> hashSet1 = new HashSet<String>(Arrays.asList(storettype.split(",")));
//						if (hashSet1.contains(transfertype)) {
//							System.out.println("SMAE TYPE REAPEATED");
//						} else {
//							storettype = storettype + "," + transfertype;
//						}
//
//					}
//					dat[0] = storettype;
//					dat[1] = vehiceltype;
//					i++;
//
//				}

			}
			
			DBConnection.closeResultSet(rs22);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}

		return dat;
	}

	public static String getTransfer_type() {
		return null;
	}

	public static void setVariants(String variant_name, String is_default, String currency_1, String price_1,
			String country_1, String val_st_date_1, String val_ed_date_1, String season_1, String vehicle_type,
			String package_id, String star_rating, String room_category, String price_category, String basis,
			String sharing_mood, String variant_type, String ttype) {
		Connection conn = null;
		Statement stmt3 = null;
		try {
			conn = DBConnection.getConnection();

			stmt3 = conn.createStatement();
			String sql22 = "insert into fit_package_catalog_variants_price values('','" + package_id + "','"
					+ is_default + "','" + variant_name + "','" + price_category + "','" + variant_type + "','"
					+ room_category + "','" + star_rating + "','" + basis + "','" + sharing_mood + "','','','','','','"
					+ ttype + "','" + vehicle_type + "','','','','','','','','" + country_1 + "','" + currency_1 + "','"
					+ val_st_date_1 + "','" + val_ed_date_1 + "','" + season_1 + "','" + price_1
					+ "','','','','','','','','','','','','','','')";
			int rs = stmt3.executeUpdate(sql22);
			System.out.println(rs);
			System.out.println("SUCCESSFULLY INSERTED PRICE VARIANTS\n");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt3);
			DBConnection.closeConnection(conn);
		}

	}

	public static boolean checkPricevariantsrepeated(String package_id, String pricecategory, int repeatcat) {
		Connection conn = null;
		Statement stmt3 = null;
		String var = null;
		boolean found = false;
		try {
			conn = DBConnection.getConnection();
			stmt3 = conn.createStatement();
			String sql22 = "select distinct price_category from fit_package_catalog_variants_price where package_id='"
					+ package_id + "'";
			ResultSet rs22 = stmt3.executeQuery(sql22);
			while (rs22.next()) {
				var = rs22.getString("price_category");
				if (var.equalsIgnoreCase(pricecategory) && repeatcat!=1) {
					found = true;
				}
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

		return found;

	}

}
