package com.howtodoinjava.demo.jsonsimple;

import java.io.*;

import org.codehaus.jackson.*;

public class ParseJson {

	public static void main(String[] args) {

		getData(3);

	}

	private static void getData(int no_of_package) {

		try {
			int j=1;
			JsonFactory jsonfactory = new JsonFactory();
			File source = new File("employees2.json");

			JsonParser parser = jsonfactory.createJsonParser(source);
			JsonToken current;
			for (int i = 1; i <= no_of_package; i++) {
				System.out.println("---------Package " + i + " is Started---------");
				current = parser.nextToken();
				if (current != JsonToken.START_OBJECT) {
					System.out.println("Error: root should be object: quiting.");
					return;
				}

				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String fieldName = parser.getCurrentName();
					// move from field name to field value
					current = parser.nextToken();
					if (fieldName.equals("_id")) {
						// parser.nextToken();
						String _id = parser.getText();
						System.out.println("_id : " + _id);
					} else if (fieldName.equals("continent")) {
						// parser.nextToken();
						String continent = parser.getText();
						System.out.println("continent : " + continent);
					} else if (fieldName.equals("package_name")) {
						// parser.nextToken();
						String package_name = parser.getText();
						System.out.println("package_name : " + package_name);
					}
					// accessing nested array data
					else if (fieldName.equals("costing")) {
						if (current == JsonToken.START_ARRAY) {
							// For each of the records in the array
							parser.nextToken();
							while (parser.nextToken() != JsonToken.END_ARRAY) {
								// read the record into a tree model,
								// this moves the parsing position to the end of it
								// JsonNode node = parser.readValueAsTree();
								// System.out.println(node);
								// And now we have random access to everything in the object
								// System.out.println("POS: " + node.get("HTL_NM1").asText());
							// parser.nextToken();
								//String fieldName1 = parser.getCurrentName();
								
								String costing_array_key = parser.getText();
								//System.out.println("Costing Fields : " + costing_array_key);
								parser.nextToken();
								
								String costing_array_value = parser.getText();
								//System.out.println(costing_array_key+" : " + costing_array_value);
								if(costing_array_key.equals("HTL_NM1"))
								{
									System.out.println("------Costing Array "+j+" Data------");
									System.out.println(costing_array_key+" : " + costing_array_value);
									j++;
								}
								
								else if(costing_array_key.equals("CATEGORY"))
								{
									System.out.println(costing_array_key+" : " + costing_array_value);
								}
								else if(costing_array_key.equals("VALID_FROM"))
								{
									System.out.println(costing_array_key+" : " + costing_array_value);
								}
								else if(costing_array_key.equals("VALID_TO"))
								{
									System.out.println(costing_array_key+" : " + costing_array_value);
								}
								else if(costing_array_key.equals("_id"))
								{
									System.out.println(costing_array_key+" : " + costing_array_value);
								}
							}
						} else {
							System.out.println("Error: records should be an array: skipping.");
							parser.skipChildren();
						}
					}
					// accessing array data
					else if (fieldName.equals("exclusion")) {
						System.out.println("-------------Exclusion Data----------- ");
						if (current == JsonToken.START_ARRAY) {
							while (parser.nextToken() != JsonToken.END_ARRAY) {
								String exclusiondata = parser.getText();
								System.out.println(exclusiondata);
							}
						}
						
					} else {
						// System.out.println("Field Name: " + fieldName);
						parser.skipChildren();
					}
				}
				System.out.println("---------Package " + i + " is Ended---------");
				System.out.println("\n");
			}
		} catch (IOException ie) {
			ie.printStackTrace();

		}

	}

}