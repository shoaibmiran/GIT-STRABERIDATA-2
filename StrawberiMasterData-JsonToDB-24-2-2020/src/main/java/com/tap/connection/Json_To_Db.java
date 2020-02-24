package com.tap.connection;

import com.fasterxml.jackson.databind.*;
import com.tap.model.Hotels;
import com.tap.model.VariantPrice;
import com.fasterxml.jackson.core.*;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;

public class Json_To_Db {

	@SuppressWarnings("null")
	public static void setdata(String package_names) throws JSONException, Exception {
		String places[] = null;
		String hotels[] = null;
		Hotels[] szHotels = null;
		String hotel_name = null;
		String package_id = null;
		Hotels attributes = new Hotels();
		VariantPrice price = new VariantPrice();
		boolean present = false;
		boolean id = false;
		int nofop = 0;
		String continent = null;
		try {
			Set<String> hashSet = new HashSet<String>(Arrays.asList(package_names.split(",")));
			System.out.println("daata" + hashSet);

			JsonFactory f = new MappingJsonFactory();
			JsonParser jp = f.createParser(new File("employees2.json"));
			// JsonParser jp = f.createParser(new File("fit-pkgs.txt"));

			JsonToken current;
			for (int i = 1; i <= 2800; i++) {
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS STARTED--------------------------------------------------------\n");

				current = jp.nextToken();
				if (current != JsonToken.START_OBJECT) {
					System.out.println("Error: root should be object: quiting.");
					return;

				}

				while (jp.nextToken() != JsonToken.END_OBJECT) {

					// move from field name to field value
					current = jp.nextToken();
					/*
					 * if (fieldName1.equals("package_name")) {
					 * attributes.setItem_name(jp.getText()); System.out.println("package_name:" +
					 * attributes.getItem_name() + "\n"); }
					 */

					String fieldName = jp.getCurrentName();
					// move from field name to field value
					// current = jp.nextToken();;
					if (fieldName.equals("package_name")) {
						System.out.println("field Value:" + jp.getText());
						String casecheck = jp.getText().toUpperCase();
						id = hashSet.contains(jp.getText());
						System.out.println(id);

						if (hashSet.contains(casecheck)) {
							package_id = getPackage_id(jp.getText());
							present = HotelDao.checkPresent_in_db(package_id);
							if (package_id == null) {
								System.out.println(jp.getText() + " not found in DB, ignoring this package");
							}
						} else {
							System.out.println(jp.getText() + " not found in PROPERTIES FILe, ignoring this package");
						}
					} else if (fieldName.equals("continent")) {
						continent = jp.getText();
						System.out.println(continent);

					} else if (fieldName.equals("country")) {

						if (continent.equalsIgnoreCase("INDIA")) {
							price.setCountry_1("INDIA");
						} else if (continent != "INDIA") {
							price.setCountry_1(jp.getText());

						}
						System.out.println(jp.getText());
						price.setSeason_1("");
					}

					else if (fieldName.equals("costing")) {
						if (current == JsonToken.START_ARRAY) {
							// For each of the records in the array
							int no = 1;
							while (jp.nextToken() != JsonToken.END_ARRAY) {

								if (package_id == null || id == false || present == true) {
								}

								// read the record into a tree model,
								// this moves the parsing position to the end of it

								else if (package_id != null && present == false) {

									int repeatcat = 1;

									System.out.println("\n----------------------COSTING ARRAY " + no
											+ "  IS STARTED-----------------------------------------\n");
									// And now we have random access to everything in the object
									JsonNode node = jp.readValueAsTree();
									hotel_name = node.get("HTL_NM1").asText();
									System.out.println(hotel_name);
									long count = count_hotels.count(hotel_name, ':');
									System.out.println(count);
									if (count >= 1) {
										System.out.println("No Of Hotels:" + count + "\n");
										int length = hotel_name.length();
										System.out.println(length);
										places = string_split.getplaces(hotel_name);
										hotels = string_split.gethotels(hotel_name);

										// ######################seperating ,,,, in hotel name###############//
										String[] sep = null;
										String original = "";
										String htoriginal = "";
										for (int x = 0; x < hotels.length; x++) {

											sep = hotels[x].split("[,]");
											System.out.println(sep.length);
											for (int d1 = 0; d1 < sep.length; d1++) {
												if (x == 0 && d1 == 0) {
													original = original + sep[d1];
													htoriginal = htoriginal + places[x];
												} else if (x > 0 || (x == 0 && d1 == 1)) {
													original = original + "," + sep[d1];
													htoriginal = htoriginal + "," + places[x];
												}
												System.out.println((original));
											}

										}
										String strArray[] = original.split(",");

										System.out.println("String hotels converted to String array");

										// print elements of String array
										for (int y = 0; y < strArray.length; y++) {
											System.out.println(strArray[y]);
										}
										hotels = strArray;

										String strArray1[] = htoriginal.split(",");

										System.out.println("String places converted to String array");

										// print elements of String array
										for (int y = 0; y < strArray1.length; y++) {
											System.out.println(strArray1[y]);

										}
										places = strArray1;

										// #############################333333333333333333333/////////////

										int l = 1;
										for (int k = 0; k < hotels.length; k++) {

											String str1 = hotels[k];

//											String replace = str1.replace("@ ", "@");

											System.out.println("Hotel Name--" + l + " is ---" + str1.trim());
											l++;
										}

										int m = 1;
										for (int k = 0; k < places.length; k++) {

											String str1 = places[k];
											System.out.println("Place Name--" + m + " is ---" + str1.trim());
											m++;
										}
										System.out.println("Hotel Names-------" + Arrays.toString(hotels));
										System.out.println("Place Names-------" + Arrays.toString(places));
									}

									else if (count == 0) {
										long count11 = count_hotels.count(hotel_name, ',');
										if (count11 == 0) {
											System.out.println("No Of Hotels:1\n");
										} else if (count11 >= 1) {
											count11 = count11 + 1;
											System.out.println("No Of Hotels:" + count11 + "\n");
										}

										int length = hotel_name.length();
										System.out.println(length);

										hotels = string_split.gethotelssingle(hotel_name);

										// ######################seperating ,,,, in hotel name###############//
										String[] sep = null;
										String original = "";
										for (int x = 0; x < hotels.length; x++) {

											sep = hotels[x].split("[,]");
											System.out.println(sep.length);
											for (int d1 = 0; d1 < sep.length; d1++) {
												if (x == 0 && d1 == 0) {
													original = original + sep[d1];
												} else if (x > 0 || (x == 0 && d1 == 1)) {
													original = original + "," + sep[d1];
												}
												System.out.println((original));
											}

										}
										String strArray[] = original.split(",");

										System.out.println("String hotels converted to String array");

										// print elements of String array
										for (int y = 0; y < strArray.length; y++) {
											System.out.println(strArray[y]);
										}
										hotels = strArray;

										System.out.println("Hotel Names singleee-------" + Arrays.toString(hotels));
										System.out.println(hotels.length);
										// places = new String[1];
										places = HotelDao.getplaceName(package_id);
										String p[] = places;
										if (places[0] == null) {
											places = new String[hotels.length];
											for (int u = 0; u < hotels.length; u++) {
												places[u] = "";
											}

										} else if (places[0] != null) {
											places = new String[hotels.length];
											for (int u = 0; u < hotels.length; u++) {
												places[u] = p[0];
											}

										}

									}

									attributes.setOriginal_category(node.get("CATEGORY").asText());

									if (node.get("CATEGORY").asText().equalsIgnoreCase("3 STAR")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("STANDARD")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("MODERATE")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("VALUE")) {
										attributes.setStar_rating("3");
										attributes.setRoom_category("Standard");
										attributes.setPrice_category("Budget");
										attributes.setVariant_name("3*");
									}

									else if (node.get("CATEGORY").asText().equalsIgnoreCase("4 STAR")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("DELUXE")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("FIRST CLASS")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("SUPERIOR")) {
										attributes.setStar_rating("4");
										attributes.setRoom_category("Deluxe");
										attributes.setPrice_category("Economy");
										attributes.setVariant_name("4*");
									}

									else if (node.get("CATEGORY").asText().equalsIgnoreCase("5 STAR")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("PREMIUM")
											|| node.get("CATEGORY").asText().equalsIgnoreCase("LUXURY")) {
										attributes.setStar_rating("5");
										attributes.setRoom_category("Premium");
										attributes.setPrice_category("Luxury");
										attributes.setVariant_name("5*");
									}

									String vname[] = VariantPriceDao.getVariant_name(package_id);
									String ttype1 = null;
									String ttype2 = null;
									String[] words = null;

									if (vname[0] != null && vname[1] != null) {
										words = vname[0].split("[,]");
										System.out.println(words.length);

										if (words.length > 1) {
											ttype1 = words[0];
											ttype2 = words[1];
										} else if (words.length == 1) {
											ttype1 = words[0];
										}

										price.setVehicle_type(vname[1]);

										System.out.println("dfffff" + vname[0]);
										System.out.println("dfffff" + vname[1]);
										System.out.println(ttype1);
										System.out.println(ttype2);
									}
									String sdate = node.get("VALID_FROM").asText();
									String date_from = sdate.substring(0, 10);
									price.setVal_st_date_1(date_from);
									String edate = node.get("VALID_TO").asText();
									String date_end = edate.substring(0, 10);
									price.setVal_ed_date_1(date_end);

									szHotels = new Hotels[hotels.length];

									for (int htCount = 0; htCount < hotels.length; htCount++) {
										szHotels[htCount] = new Hotels();
										int ho = 1;

										boolean pcatfound = VariantPriceDao.checkPricevariantsrepeated(package_id,
												attributes.getPrice_category(), repeatcat);

										if (node.get("HT_TWIN").asText().equalsIgnoreCase("-")
												|| node.get("HT_TWIN").asText().equalsIgnoreCase("NA")) {
											System.out.println(
													"NO INSERTION BECASUSE VALUE OF TWIN SHARING MOOD IS NA OR -");
										}

										else if (node.get("HT_TWIN").asText() != "-"
												|| node.get("HT_TWIN").asText() != "NA") {

											System.out.println("Twin Sharing mode insertion started for hotel  "
													+ (htCount + 1) + "\n");

											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");

											szHotels[htCount].setSharing_mood("Twin");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Base");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");

											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(variantname + ", "
													+ szHotels[htCount].getSharing_mood() + " Sharing");
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");

											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());
											System.out.println("\nTwin Sharing mode insertion ended for hotel  "
													+ (htCount + 1) + "\n");

											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											String[] currency = node.get("HT_TWIN").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());

											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {

												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}
											}
											repeatcat++;
										}
										if (node.get("HT_TRIPLE").asText().equalsIgnoreCase("-")
												|| node.get("HT_TRIPLE").asText().equalsIgnoreCase("NA")) {
											System.out.println(
													"NO INSERTION BECASUSE VALUE OF TRIPLE SHARING MOOD IS NA OR -");
										} else if (node.get("HT_TRIPLE").asText() != "-"
												|| node.get("HT_TRIPLE").asText() != "NA") {
											System.out.println("Triple Sharing mode insertion started for hotel  "
													+ (htCount + 1) + "\n");

											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");
											szHotels[htCount].setSharing_mood("Triple");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Base");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");
											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(variantname + ", "
													+ szHotels[htCount].getSharing_mood() + " Sharing");
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");

											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());

											System.out.println("\nTriple Sharing mode insertion ended for hotel  "
													+ (htCount + 1) + "\n");

											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											System.out.println(price.getIs_default());

											String[] currency = node.get("HT_TRIPLE").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());

											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {
												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}
											}
											repeatcat++;
										}
										if (node.get("HT_SINGLE").asText().equalsIgnoreCase("-")
												|| node.get("HT_SINGLE").asText().equalsIgnoreCase("NA")) {
											System.out.println(
													"NO INSERTION BECASUSE VALUE OF SINGLE SHARING MOOD IS NA OR -");
										} else if (node.get("HT_SINGLE").asText() != "-"
												|| node.get("HT_SINGLE").asText() != "NA") {

											System.out.println("Single Sharing mode insertion started for hotel  "
													+ (htCount + 1) + "\n");

											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");
											szHotels[htCount].setSharing_mood("Single");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Base");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");

											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(variantname + ", "
													+ szHotels[htCount].getSharing_mood() + " Sharing");
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");
											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());
											System.out.println("\nSingle Sharing mode insertion Ended for hotel  "
													+ (htCount + 1) + "\n");
											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											String[] currency = node.get("HT_SINGLE").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());

											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {

												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}
											}
											repeatcat++;
										}
										if (node.get("CH_W_BED").asText().equalsIgnoreCase("-")
												|| node.get("CH_W_BED").asText().equalsIgnoreCase("NA")) {
											System.out.println(
													"NO INSERTION BECASUSE VALUE OF CHILD WITH BED IS NA OR -");
										} else if (node.get("CH_W_BED").asText() != "-"
												|| node.get("CH_W_BED").asText() != "NA") {

											System.out
													.println("Child With Bed Sharing mode insertion started for hotel  "
															+ (htCount + 1) + "\n");

											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");
											szHotels[htCount].setSharing_mood("Child with Bed");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Add-on");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");

											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(
													variantname + ", " + szHotels[htCount].getSharing_mood());
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");
											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());
											System.out
													.println("\nChild With Bed Sharing mode insertion ended for hotel  "
															+ (htCount + 1) + "\n");
											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											String[] currency = node.get("CH_W_BED").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());

											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {

												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}

											}
											repeatcat++;
										}
										if (node.get("CH_WO_BED").asText().equalsIgnoreCase("-")
												|| node.get("CH_WO_BED").asText().equalsIgnoreCase("NA")) {
											System.out.println(
													"NO INSERTION BECASUSE VALUE OF CHILD WITH OUT BED IS NA OR -");
										}

										else if (node.get("CH_WO_BED").asText() != "-"
												|| node.get("CH_WO_BED").asText() != "NA") {
											System.out.println(
													"Child Without Bed Sharing mode insertion started for hotel  "
															+ (htCount + 1) + "\n");

											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");
											szHotels[htCount].setSharing_mood("Child without Bed");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Add-on");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");

											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(
													variantname + ", " + szHotels[htCount].getSharing_mood());
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");
											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());
											System.out.println(
													"\nChild Without Bed Sharing mode insertion ended for hotel  "
															+ (htCount + 1) + "\n");

											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											String[] currency = node.get("CH_WO_BED").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());

											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {

												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}
											}
											repeatcat++;
										}
										if (node.get("HT_EXTRA").asText().equalsIgnoreCase("-")
												|| node.get("HT_EXTRA").asText().equalsIgnoreCase("NA")) {
											System.out.println("NO INSERTION BECASUSE VALUE OF EXTRA BED IS NA OR -");
										} else if (node.get("HT_EXTRA").asText() != "-"
												|| node.get("HT_EXTRA").asText() != "NA") {

											System.out.println("Extra Bed Sharing mode insertion started for hotel  "
													+ (htCount + 1) + "\n");
											System.out.println(
													"original category:" + attributes.getOriginal_category() + "\n");
											System.out.println("StarRAtings: " + attributes.getStar_rating() + "\n");
											System.out
													.println("Room Category: " + attributes.getRoom_category() + "\n");
											System.out.println(
													"Price Category: " + attributes.getPrice_category() + "\n");
											attributes.setBasis(node.get("BASIS").asText());
											System.out.println("BASIS: " + attributes.getBasis() + "\n");
											szHotels[htCount].setSharing_mood("Extra Bed");
											System.out.println(
													"sharing_mode: " + szHotels[htCount].getSharing_mood() + "\n");
											szHotels[htCount].setVariant_type("Add-on");
											System.out.println(
													"variant_type: " + szHotels[htCount].getVariant_type() + "\n");

											String variantname = attributes.getVariant_name();
											szHotels[htCount].setVariant_name(
													variantname + ", " + szHotels[htCount].getSharing_mood());
											System.out.println(
													"variantnamee: " + szHotels[htCount].getVariant_name() + "\n");
											String str1 = hotels[htCount];
											szHotels[htCount].setHotel_name(str1.trim());
											szHotels[htCount].setHotel_id(str1.trim() + "-00" + ho);
											System.out.println("Hotel Name--" + htCount + " is ---" + str1.trim());
											String str11 = places[htCount];
											szHotels[htCount].setPlace(str11.trim());
											System.out.println("Place Name--" + htCount + " is ---" + str11.trim());
											System.out.println(szHotels[htCount].getPlace());
											System.out.println(szHotels[htCount].getHotel_name());
											System.out.println(szHotels[htCount].getHotel_id());
											System.out.println("\nExtra Bed Sharing mode insertion ended for hotel  "
													+ (htCount + 1) + "\n");

											String check = HotelDao.check_Hotel_name(szHotels[htCount].getHotel_id(),
													package_id, attributes.getPrice_category(),
													szHotels[htCount].getHotel_name());
											int hc = 0;
											if (check.equalsIgnoreCase("NA")) {
												hc = HotelDao.getCounthotel(szHotels[htCount].getHotel_name());
												hc = hc + 1;
												szHotels[htCount].setHotel_id(str1.trim() + "-00" + hc);
												System.out.println("Hotel Id--" + htCount + " is ---" + str1.trim());

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											else if (check.equalsIgnoreCase("No")) {

												HotelDao.setHotel_data(attributes.getStar_rating(),
														attributes.getRoom_category(), attributes.getPrice_category(),
														szHotels[htCount].getBasis(),
														szHotels[htCount].getSharing_mood(),
														szHotels[htCount].getVariant_type(),
														szHotels[htCount].getHotel_name(),
														szHotels[htCount].getHotel_id(), package_id,
														szHotels[htCount].getPlace(), attributes.getOriginal_category(),
														szHotels[htCount].getVariant_name());
											}

											// #####################################Variant
											// Price#######################33

											String[] currency = node.get("HT_EXTRA").asText().split("[ ]");
											price.setCurrency_1(currency[0]);
											System.out.println(price.getCurrency_1());

											price.setPrice_1(currency[1]);
											System.out.println(price.getPrice_1());
											System.out.println(price.getCountry_1());
											System.out.println(price.getVal_st_date_1());
											System.out.println(price.getVal_ed_date_1());
											System.out.println(price.getSeason_1());
											System.out.println(price.getVehicle_type());
											if (pcatfound == true) {
												System.out.println(
														"WITHIN AN ARRAY MORE THAN ONE HOTEL SO IGNORE CREATEING PRICE VARAITNS");
												ho++;
											} else if (pcatfound == false) {

												if (vname[0] != null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}

													System.out.println(price.getIs_default());

													String name1 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing, "
															+ words[0] + " Transfer";
													price.setVariant_name(name1);
													price.setTransfer_type(words[0]);
													System.out.println("ttypeeeeee-----" + price.getTransfer_type());

													System.out.println(price.getVariant_name());
													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), price.getVehicle_type(), package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(),
															price.getTransfer_type());

													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);

													if (words.length > 1) {

														price.setIs_default("No");

														System.out.println(price.getIs_default());
														String name2 = variantname + ", "
																+ szHotels[htCount].getSharing_mood() + " Sharing, "
																+ words[1] + " Transfer";
														price.setVariant_name(name2);

														System.out.println(price.getVariant_name());
														price.setTransfer_type(words[1]);
														System.out
																.println("ttypeeeeee-----" + price.getTransfer_type());

														VariantPriceDao.setVariants(price.getVariant_name(),
																price.getIs_default(), price.getCurrency_1(),
																price.getPrice_1(), price.getCountry_1(),
																price.getVal_st_date_1(), price.getVal_ed_date_1(),
																price.getSeason_1(), price.getVehicle_type(),
																package_id, attributes.getStar_rating(),
																attributes.getRoom_category(),
																attributes.getPrice_category(),
																szHotels[htCount].getBasis(),
																szHotels[htCount].getSharing_mood(),
																szHotels[htCount].getVariant_type(),
																price.getTransfer_type());

													}

												}

												else if (vname[0] == null && vname[1] == null) {

													if (ho == 1) {
														price.setIs_default("Yes");
													}

													else if (ho > 1) {
														price.setIs_default("No");
													}
													System.out.println(price.getIs_default());
													String name2 = variantname + ", "
															+ szHotels[htCount].getSharing_mood() + " Sharing";
													price.setVariant_name(name2);

													System.out.println(price.getVariant_name());

													VariantPriceDao.setVariants(price.getVariant_name(),
															price.getIs_default(), price.getCurrency_1(),
															price.getPrice_1(), price.getCountry_1(),
															price.getVal_st_date_1(), price.getVal_ed_date_1(),
															price.getSeason_1(), "", package_id,
															attributes.getStar_rating(), attributes.getRoom_category(),
															attributes.getPrice_category(),
															szHotels[htCount].getBasis(),
															szHotels[htCount].getSharing_mood(),
															szHotels[htCount].getVariant_type(), "");
													System.out.println(
															"hiiiiiii----------------------------------------------------------::::"
																	+ ho++);
												}
											}
											repeatcat++;
										}

									}
									System.out.println("\n----------------------COSTING ARRAY " + no
											+ "  IS ENDED-----------------------------------------\n");
									no++;
								}

							}

						} else {
							System.out.println("Error: records should be an array: skipping.");
							jp.skipChildren();
						}
					}
//					else if (fieldName.equals("seasons")) {
//						if (current == JsonToken.START_ARRAY) {
//							while (jp.nextToken() != JsonToken.END_ARRAY) {
//
//								price.setSeason_1(jp.getText());
//								System.out.println(jp.getText());
//							}
//						}
//					}
					else {
						// System.out.println("Unprocessed/unused property: " + fieldName);
						jp.skipChildren();
					}
				}
				System.out.println("--------------------------------------------------------PACKAGE " + i
						+ " IS ENDED--------------------------------------------------------\n");
			}
			System.out.println("NO OF PACKAGES INSERTED:::::::::" + nofop);
		} catch (IOException ie) {
			ie.printStackTrace();

		}

	}

	private static String getPackage_id(String name) {
		Connection conn = null;
		Statement stmt = null;
		String package_id = null;
		try {
			conn = DBConnection.getConnection();

			stmt = conn.createStatement();
			String sql = "SELECT item_code from fit_package_catalog_basic where item_name='" + name + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				package_id = rs.getString("item_code");
				System.out.println(package_id + "\n");
			}
			DBConnection.closeResultSet(rs);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(conn);
		}
		return package_id;

	}
}